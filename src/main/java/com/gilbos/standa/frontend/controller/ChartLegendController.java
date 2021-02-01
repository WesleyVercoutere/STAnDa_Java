package com.gilbos.standa.frontend.controller;

import com.gilbos.standa.service.dto.FilterDTO;
import com.gilbos.standa.service.manager.FileManager;
import com.gilbos.standa.service.manager.FilterManager;
import com.gilbos.standa.util.UpdateArgs;
import com.gilbos.standa.util.observer.Observable;
import com.gilbos.standa.util.observer.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChartLegendController implements Observer {

    private List<CheckBox> checkBoxList;

    @Autowired
    private FilterManager filterManager;
    @Autowired
    private FileManager fileManager;

    @FXML
    private CheckBox toggleAll;
    @FXML
    private VBox paneForCheckBoxes;

    public ChartLegendController() {
        this.checkBoxList = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        filterManager.addObserver(this);
        fileManager.addObserver(this);

        toggleAll.setSelected(true);
        toggleAll.setText("Toggle All");
        toggleAll.setOnAction(e -> checkBoxList.forEach(cb -> cb.setSelected(toggleAll.selectedProperty().get())));

        setCheckBoxes();
    }

    /**
     *
     */
    private void setCheckBoxes() {
        checkBoxList.clear();
        paneForCheckBoxes.getChildren().clear();
        toggleAll.setSelected(true);

        FilterDTO dto = filterManager.getFilters();
        dto.getSmarTwistIds().forEach(this::setCheckBox);
        checkBoxList.forEach(this::setCheckBoxListeners);
        paneForCheckBoxes.getChildren().addAll(checkBoxList);
    }

    private void setCheckBox(String id) {
        CheckBox cb = new CheckBox(id);
        cb.setSelected(true);

        checkBoxList.add(cb);
    }

    private void setCheckBoxListeners(CheckBox cb) {
        //Set listener for toggling
    	cb.selectedProperty().addListener((observable, oldValue, newValue) ->
              filterManager.toggleLineVisibility(cb.getText(), cb.isSelected()));

        //Highlight line on hover
        cb.setOnMouseEntered(e -> filterManager.highlightLine(cb.getText()));

        //Reset color on mouse leave
        cb.setOnMouseExited(e -> filterManager.resetColors());
    }

    @Override
    public void update(Observable o, Object arg) {

        if (UpdateArgs.FILTERS_CHANGED.equals(arg)) {
            setCheckBoxes();
        }

        if (UpdateArgs.FILES_ADDED.equals(arg)) {
            setCheckBoxes();
        }

    }

}
