package com.gilbos.standa.frontend.controller;

import com.gilbos.standa.frontend.RootLayout;
import com.gilbos.standa.service.dto.CsvFileDTO;
import com.gilbos.standa.service.dto.FilterDTO;
import com.gilbos.standa.service.manager.*;
import com.gilbos.standa.util.UpdateArgs;
import com.gilbos.standa.util.observer.Observable;
import com.gilbos.standa.util.observer.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MenuBarController implements Observer {

    private String selectAll = "All";

    private RootLayout root;
    private FileManager fileManager;
    private FilterManager filterManager;
    private SmarTwistManager smarTwistManager;
    private FlowErrorManager flowErrorManager;
    private STCAverageManager stcAverageManager;

    @FXML
    private ComboBox<String> cBoxSmarTwistId;
    @FXML
    private ComboBox<String> cBoxRecipe;
    @FXML
    private ComboBox<String> cBoxType;
    @FXML
    private ComboBox<String> cBoxSpeed;

    public MenuBarController(RootLayout rootLayout,
                             FileManager fileManager,
                             FilterManager filterManager,
                             SmarTwistManager smarTwistManager,
                             FlowErrorManager flowErrorManager,
                             STCAverageManager stcAverageManager) {
        this.root = rootLayout;
        this.fileManager = fileManager;
        this.filterManager = filterManager;
        this.smarTwistManager = smarTwistManager;
        this.flowErrorManager = flowErrorManager;
        this.stcAverageManager = stcAverageManager;
    }


    private EventHandler<ActionEvent> cbEventUpdate = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            updateFilters();
        }
    };

    @FXML
    private void initialize() {
        fileManager.addObserver(this);
        filterManager.addObserver(this);
    }

    /**
     * FXML button methods
     */
    @FXML
    private void openFiles() {
        List<File> files = openDialog();

        if (files == null)
            return;

        handleFiles(files);
    }

    @FXML
    private void openFolder() {
        File folder = openFolderDialog();

        if (folder == null)
            return;

        handleFolder(folder);
    }

    @FXML
    private void exportData() {
//		JFileChooser fc = new JSystemFileChooser();
//
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
//		fc.setFileFilter(filter);
//
//		if(!Settings.readProperty("directory").equals("")) {
//			fc.setCurrentDirectory(new File(Settings.readProperty("directory")));
//		}
//
//		int option = fc.showSaveDialog(null);
//
//		if(option == JFileChooser.APPROVE_OPTION){
//			if (fc.getSelectedFile() != null) {
//
//				List<String> selectedMachines = getMainApp().getChartLegendController().getSelectedMachines();
//				Axis x = getMainApp().getChartController().getChart().getXAxis();
//				double axisLowerBound = ((NumberAxis)x).getLowerBound();
//				double axisUpperBound = ((NumberAxis)x).getUpperBound();
//
//
//				String filename = fc.getSelectedFile().toString();
//				getModel().export(filename,selectedMachines, axisLowerBound, axisUpperBound);
//			}
//		}
    }

    @FXML
    private void flowErrors() {
        File file = saveDialog();

        if (file != null) {
            flowErrorManager.exportErrorsToFile(file.getAbsolutePath());
        }
    }

    @FXML
    private void calculateSTCAverage() {
        File file = saveDialog();

        if (file != null) {
            stcAverageManager.exportAveragesToFile(file.getAbsolutePath());
        }
    }

    /**
     * helper methods
     */
    private List<File> openDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV Files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files", Arrays.asList("*.csv", "*.CSV")));

        return fileChooser.showOpenMultipleDialog(root.getPrimaryStage());
    }

    private File openFolderDialog() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Open csv");

        return chooser.showDialog(root.getPrimaryStage());
    }

    private File saveDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text files", Arrays.asList("*.txt")));

        return fileChooser.showSaveDialog(root.getPrimaryStage());
    }

    private void handleFiles(List<File> files) {
        List<CsvFileDTO> dtos = files.stream().map(e -> new CsvFileDTO(e.getAbsolutePath(), e.getName()))
                .collect(Collectors.toList());

        if (fileManager.isEmpty()) {
            fileManager.addFiles(dtos);
        } else {
            String arg = showAddData();
            fileManager.addFiles(arg, dtos);
        }
    }

    private void handleFolder(File folder) {
        List<File> filesInFolder = new ArrayList<>();

        try {
            filesInFolder = Files.walk(Paths.get(folder.getAbsolutePath()))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        handleFiles(filesInFolder);
    }

    private String showAddData() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(root.getPrimaryStage());
        alert.setTitle("Add data");
        alert.setHeaderText("Add chosen csv data to current data, or set new data?");

        ButtonType addBtn = new ButtonType("Add");
        ButtonType newBtn = new ButtonType("New");
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(addBtn, newBtn, cancelBtn);

        Optional<ButtonType> result = alert.showAndWait();

        return result.get().getText();
    }

    private void updateComboBox() {
        FilterDTO dto = filterManager.getFilters();

        updateCBSmarTwistId(dto);
        updateCBRecipe(dto);
        updateCBType(dto);
        updateCBSpeed(dto);
    }

    private void updateCBSmarTwistId(FilterDTO dto) {
        cBoxSmarTwistId.getItems().clear();
        cBoxSmarTwistId.getItems().add(selectAll);
        cBoxSmarTwistId.getItems().addAll(dto.getSmarTwistIds());
        cBoxSmarTwistId.setValue(dto.getSelectedST());
    }

    private void updateCBRecipe(FilterDTO dto) {
        cBoxRecipe.getItems().clear();
        cBoxRecipe.getItems().add(selectAll);
        cBoxRecipe.getItems().addAll(dto.getRecipes());
        cBoxRecipe.setValue(dto.getSelectedRecipe());
    }

    private void updateCBType(FilterDTO dto) {
        cBoxType.getItems().clear();
        cBoxType.getItems().add(selectAll);
        cBoxType.getItems().addAll(dto.getTypes());
        cBoxType.setValue(dto.getSelectedType());
    }

    private void updateCBSpeed(FilterDTO dto) {
        cBoxSpeed.getItems().clear();
        cBoxSpeed.getItems().add(selectAll);
        cBoxSpeed.getItems().addAll(dto.getSpeeds());
        cBoxSpeed.setValue(dto.getSelectedSpeed());
    }

    private void createComboBoxListeners() {
        cBoxSmarTwistId.addEventHandler(ActionEvent.ACTION, cbEventUpdate);
        cBoxRecipe.addEventHandler(ActionEvent.ACTION, cbEventUpdate);
        cBoxType.addEventHandler(ActionEvent.ACTION, cbEventUpdate);
        cBoxSpeed.addEventHandler(ActionEvent.ACTION, cbEventUpdate);
    }

    private void removeComboBoxListeners() {
        cBoxSmarTwistId.removeEventHandler(ActionEvent.ACTION, cbEventUpdate);
        cBoxRecipe.removeEventHandler(ActionEvent.ACTION, cbEventUpdate);
        cBoxType.removeEventHandler(ActionEvent.ACTION, cbEventUpdate);
        cBoxSpeed.removeEventHandler(ActionEvent.ACTION, cbEventUpdate);
    }

    private void updateFilters() {
        FilterDTO dto = new FilterDTO();
        dto.setSelectedST(cBoxSmarTwistId.getValue());
        dto.setSelectedRecipe(cBoxRecipe.getValue());
        dto.setSelectedType(cBoxType.getValue());
        dto.setSelectedSpeed(cBoxSpeed.getValue());

        filterManager.updateSelectedFilters(dto);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (UpdateArgs.FILES_NEW.equals(arg) || UpdateArgs.FILES_NEW_ERRORS.equals(arg)) {
            removeComboBoxListeners();
            filterManager.setSelectedFiltersToAll();
            updateComboBox();
            createComboBoxListeners();
        }

        if (UpdateArgs.FILES_ADDED.equals(arg) || UpdateArgs.FILES_ADDED_ERRORS.equals(arg)) {
            removeComboBoxListeners();
            updateComboBox();
            createComboBoxListeners();
        }

        if (UpdateArgs.FILTERS_CHANGED.equals(arg)) {
            removeComboBoxListeners();
            updateComboBox();
            createComboBoxListeners();
        }
    }

}
