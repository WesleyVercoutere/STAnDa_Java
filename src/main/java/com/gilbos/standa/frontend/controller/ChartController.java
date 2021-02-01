package com.gilbos.standa.frontend.controller;

import com.gilbos.standa.frontend.dialogimpl.SettingsLayout;
import com.gilbos.standa.frontend.dialogimpl.SettingsYAxisLayout;
import com.gilbos.standa.service.dto.AverageDTO;
import com.gilbos.standa.service.dto.SettingsDTO;
import com.gilbos.standa.service.manager.FileManager;
import com.gilbos.standa.service.manager.FilterManager;
import com.gilbos.standa.service.manager.SettingsManager;
import com.gilbos.standa.service.manager.SmarTwistManager;
import com.gilbos.standa.util.DateUtil;
import com.gilbos.standa.util.UpdateArgs;
import com.gilbos.standa.util.observer.Observable;
import com.gilbos.standa.util.observer.Observer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import org.gillius.jfxutils.chart.AxisConstraint;
import org.gillius.jfxutils.chart.AxisConstraintStrategies;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.ChartZoomManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class ChartController implements Observer {

    private SmarTwistManager smarTwistManager;
    private FileManager fileManager;
    private FilterManager filterManager;
    private SettingsManager settingsManager;
    private SettingsYAxisLayout settingsYAxisLayout;
    private SettingsLayout settingsLayout;

    @FXML
    private AnchorPane chartPane;

    private Axis<Number> xAxis;
    private Axis<Number> yAxis;
    private LineChart<Number, Number> chart;

    private boolean axisClicked;
    private Rectangle selectRect;

    public ChartController(SmarTwistManager smarTwistManager,
                           FileManager fileManager,
                           FilterManager filterManager,
                           SettingsManager settingsManager,
                           SettingsYAxisLayout settingsYAxisLayout,
                           SettingsLayout settingsLayout) {
        super();

        this.smarTwistManager = smarTwistManager;
        this.fileManager = fileManager;
        this.filterManager = filterManager;
        this.settingsManager = settingsManager;
        this.settingsYAxisLayout = settingsYAxisLayout;
        this.settingsLayout = settingsLayout;

        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        chart = new LineChart<>(xAxis, yAxis);
        selectRect = new Rectangle();
    }

    @FXML
    private void initialize() {
        fileManager.addObserver(this);
        filterManager.addObserver(this);
        settingsManager.addObserver(this);

        axisClicked = false;

        createChart();
        updateChart();
        setupChartHandling();
    }

    @Override
    public void update(Observable o, Object arg) {

        if (UpdateArgs.FILES_ADDED.equals(arg) || UpdateArgs.FILES_ADDED_ERRORS.equals(arg)) {
            updateChart();
        }

        if (UpdateArgs.FILTERS_CHANGED.equals(arg)) {
            updateData();
        }

        if (UpdateArgs.CHANGED_Y_AXIS_VALUES.equals(arg)) {
            setYAxis();
        }

        if (UpdateArgs.CHANGED_X_AXIS_VALUES.equals(arg)) {
            setxAxisTickUnit();
        }

        if (UpdateArgs.CHANGED_SETTINGS.equals(arg)) {
            updateData();
        }

        if (UpdateArgs.HOVER.equals(arg)) {
            highlightLine();
        }

        if (UpdateArgs.UNHOVER.equals(arg)) {
            setColors();
        }

        if (UpdateArgs.UPDATE_VISIBILITY.equals(arg)) {
            toggleLineVisibility();
        }
    }

    private void updateChart() {
        setDataToChart();
        setXAxis();
        setYAxis();
        setColors();
        setupHoverLine();
    }

    private void updateData() {
        setDataToChart();
        setColors();
        setupHoverLine();
    }

    private void createChart() {
        chart.setLegendVisible(false);
        chart.setAnimated(false);

        chart.prefWidthProperty().bind(chartPane.widthProperty());
        chart.prefHeightProperty().bind(chartPane.heightProperty());

        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    private void setXAxis() {
        xAxis.autoRangingProperty().setValue(false);
        ((NumberAxis) xAxis).lowerBoundProperty().setValue(settingsManager.getSettings().getxAxisMinLowerBound());
        ((NumberAxis) xAxis).upperBoundProperty().setValue(settingsManager.getSettings().getxAxisMaxUpperBound());
        ((NumberAxis) xAxis).setTickUnit(settingsManager.getSettings().getxAxisTickUnit());

        ((NumberAxis) xAxis).setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return (DateUtil.fromEpochMilli(object.longValue()));
            }

            @Override
            public Number fromString(String string) {
                return 0;
            }
        });
    }

    private void setxAxisTickUnit() {
        ((NumberAxis) xAxis).setTickUnit(settingsManager.getSettings().getxAxisTickUnit());
    }

    private void setYAxis() {
        SettingsDTO dto = settingsManager.getSettings();

        yAxis.autoRangingProperty().setValue(false);
        ((NumberAxis) yAxis).setTickUnit(dto.getyAxisTickUnit());
        ((NumberAxis) yAxis).setLowerBound(dto.getyAxisLowerBound());
        ((NumberAxis) yAxis).setUpperBound(dto.getyAxisUpperBound());
    }

    private void setDataToChart() {
        chart.getData().clear();
        chart.setCreateSymbols(settingsManager.getSettings().isShowSymbols());

        smarTwistManager.getFilteredData().stream().forEach(dto -> {
            addSeries(dto.getTack1(), dto.getId(), "Tack1");
            addSeries(dto.getTack2(), dto.getId(), "Tack2");
            addSeries(dto.getTwistS(), dto.getId(), "TwistS");
            addSeries(dto.getTwistZ(), dto.getId(), "TwistZ");
            addSeries(dto.getCtsS(), dto.getId(), "CTS-S");
            addSeries(dto.getCtsZ(), dto.getId(), "CTS-Z");
        });

        if (settingsManager.getSettings().isShowAverage() && filterManager.getFilters().isAllFiltersSelected())
            addAverage();

        if (settingsManager.getSettings().isShowManualAverage())
            addManualAverage();
    }

    private void addSeries(Map<Long, Double> flowData, String id, String type) {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append("_").append(type);

        if (!flowData.isEmpty()) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(sb.toString());
            flowData.forEach((k, v) -> series.getData().add(new Data<>(k, v)));
            chart.getData().add(series);
        }
    }

    private void addAverage() {
        AverageDTO dto = smarTwistManager.getAverages();

        addSeries(dto.getAverage(), "Average", "Test");
        addSeries(dto.getLowerbound(), "Lower", "Test");
        addSeries(dto.getUpperbound(), "Upper", "Test");
    }

    private void addManualAverage() {
        Map<Long, Double> average = new TreeMap<>();
        average.put((long) settingsManager.getSettings().getxAxisMinLowerBound(), Double.valueOf(settingsManager.getSettings().getManualAverage()));
        average.put((long) settingsManager.getSettings().getxAxisMaxUpperBound(), Double.valueOf(settingsManager.getSettings().getManualAverage()));

        Map<Long, Double> lower = new TreeMap<>();
        lower.put((long) settingsManager.getSettings().getxAxisMinLowerBound(), settingsManager.getSettings().getLowerBound());
        lower.put((long) settingsManager.getSettings().getxAxisMaxUpperBound(), settingsManager.getSettings().getLowerBound());

        Map<Long, Double> upper = new TreeMap<>();
        upper.put((long) settingsManager.getSettings().getxAxisMinLowerBound(), settingsManager.getSettings().getUpperBound());
        upper.put((long) settingsManager.getSettings().getxAxisMaxUpperBound(), settingsManager.getSettings().getUpperBound());

        addSeries(average, "Average", "Test");
        addSeries(lower, "Lower", "Test");
        addSeries(upper, "Upper", "Test");
    }

    private void setColors() {
        if (smarTwistManager.getFilteredData().size() > 1) {
            int number = 0;
            String previousMachine = "";

            for (XYChart.Series<Number, Number> series : chart.getData()) {
                String[] seriesName = series.getName().split("_");

                String machineNumber = seriesName[0];
                String type = seriesName[1];

                if (!previousMachine.equals(machineNumber)) {
                    previousMachine = machineNumber;
                    number++;
                }

                if (number > 8)
                    number = 1;

                StringBuilder styleClass = new StringBuilder();
                styleClass.append(type).append("_").append("machine").append(number);

                Node line = series.getNode();
                line.getStyleClass().remove("highlight");
                line.getStyleClass().addAll(styleClass.toString(), "line_width");
            }
        }
    }

    private void setupChartHandling() {
        xAxis.setOnMouseClicked(this::handleXAxis);
        yAxis.setOnMouseClicked(this::showYAxisSettings);
        chart.setOnMouseClicked(this::showSettings);

        setupChangeListener();
        setupZoom();
        setupPan();
    }

    private void setupZoom() {
        ChartZoomManager zoomer = new ChartZoomManager(chartPane, selectRect, chart);
        zoomer.setMouseWheelZoomAllowed(true);
        zoomer.setAxisConstraintStrategy(AxisConstraintStrategies.getFixed(AxisConstraint.Horizontal));
        zoomer.setMouseWheelAxisConstraintStrategy(AxisConstraintStrategies.getFixed(AxisConstraint.Horizontal));
        zoomer.setMouseFilter(e -> {
            if (e.getButton() != MouseButton.PRIMARY && e.getButton() != MouseButton.SECONDARY) {

                System.out.println("zooming");
                // let it through
            } else {
                e.consume();
            }
        });
        zoomer.start();
    }

    private void setupPan() {
        ChartPanManager panner = new ChartPanManager(chart);
        panner.setAxisConstraintStrategy(AxisConstraintStrategies.getFixed(AxisConstraint.Horizontal));
        panner.setMouseFilter(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                // let it through
            } else {
                e.consume();
            }
        });
        panner.start();
    }

    private void setupChangeListener() {
        ((NumberAxis) xAxis).lowerBoundProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.longValue() < settingsManager.getSettings().getxAxisMinLowerBound()) {
                ((NumberAxis) xAxis).lowerBoundProperty().setValue(settingsManager.getSettings().getxAxisMinLowerBound());
            }

            updateXAxisProperties();
        });

        ((NumberAxis) xAxis).upperBoundProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.longValue() > settingsManager.getSettings().getxAxisMaxUpperBound()) {
                ((NumberAxis) xAxis).upperBoundProperty().setValue(settingsManager.getSettings().getxAxisMaxUpperBound());
            }

            updateXAxisProperties();
        });
    }

    private void updateXAxisProperties() {
        SettingsDTO dto = new SettingsDTO();
        dto.setxAxisLowerBound(((NumberAxis) xAxis).lowerBoundProperty().getValue());
        dto.setxAxisUpperBound(((NumberAxis) xAxis).upperBoundProperty().getValue());

        settingsManager.updateXAxisSettings(dto);
    }

    private void handleXAxis(MouseEvent e) {
        if ((e.getButton() == MouseButton.SECONDARY)) {
            setXAxis();
        }

        if ((e.getButton() == MouseButton.PRIMARY) && (e.getClickCount() == 2)) {
            axisClicked = true;
        }

    }

    private void showYAxisSettings(MouseEvent e) {
        if ((e.getButton() == MouseButton.PRIMARY) && (e.getClickCount() == 2)) {
            axisClicked = true;
            settingsYAxisLayout.show();
        }
    }

    private void showSettings(MouseEvent e) {
        if ((e.getButton() == MouseButton.PRIMARY) && (e.getClickCount() == 2)) {
            if (!axisClicked) {
                settingsLayout.show();
            }
        }

        axisClicked = false;
    }

    private void setupHoverLine() {
        chart.getData().forEach(serie -> {
            String[] seriesName = serie.getName().split("_");
            String machineNumber = seriesName[0];
            String type = seriesName[1];

            serie.getNode().setOnMouseEntered(event -> {
                double pos = event.getX();
                double value = (double) chart.getXAxis().getValueForDisplay(pos);

                StringBuilder sb = new StringBuilder();
                sb.append(machineNumber).append(" ").append(type).append("\n");
                sb.append(DateUtil.fromEpochMilli((long) value));

                Tooltip tooltip = new Tooltip(sb.toString());
                Tooltip.install(serie.getNode(), tooltip);
            });
        });
    }

    private void highlightLine() {
        String id = filterManager.getFilters().getHoveredSt();

        for (XYChart.Series<Number, Number> series : chart.getData()) {
            String[] seriesName = series.getName().split("_");

            String SmarTwistId = seriesName[0];
            Node line = series.getNode();

            if (id.equals(SmarTwistId)) {
                line.getStyleClass().clear();
                line.getStyleClass().add("highlight");
                line.toFront();
            }
        }
    }

    public void toggleLineVisibility() {
        List<String> st = filterManager.getFilters().getVisisbilitySt();

        for (XYChart.Series<Number, Number> series : chart.getData()) {
            String[] seriesName = series.getName().split("_");

            String machineNumber = seriesName[0];
            Node line = series.getNode();

            if (st.contains(machineNumber)) {
                line.setVisible(false);
            } else {
                line.setVisible(true);
            }
        }
    }

}
