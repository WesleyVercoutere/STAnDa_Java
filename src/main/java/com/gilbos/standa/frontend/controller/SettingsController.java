package com.gilbos.standa.frontend.controller;

import com.gilbos.standa.frontend.dialogimpl.SettingsLayout;
import com.gilbos.standa.service.dto.SettingsDTO;
import com.gilbos.standa.service.manager.SettingsManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingsController {

    @Autowired
    private SettingsManager settingsManager;
    @Autowired
    private SettingsLayout settingsLayout;

    @FXML
    private TextField tfTimeBetweenSamples;
    @FXML
    private TextField tfTimeToZero;
    @FXML
    private ComboBox<Boolean> cbShowSymbols;

    @FXML
    private ComboBox<Boolean> cbManualAverage;
    @FXML
    private TextField tfManualAverageValue;
    @FXML
    private TextField tfManualBoundaries;

    @FXML
    private ComboBox<Boolean> cbAverage;
    @FXML
    private TextField tfBoundariesTack;
    @FXML
    private TextField tfBoundariesTwist;

    @FXML
    private CheckBox cbColdSample;
    @FXML
    private CheckBox cbTeachingSample;
    @FXML
    private CheckBox cbSpeedChangeSample;
    @FXML
    private CheckBox cbMonitorSample;


    @FXML
    private void initialize() {
        SettingsDTO dto = settingsManager.getSettings();

        cbShowSymbols.getItems().addAll(true, false);
        cbManualAverage.getItems().addAll(true, false);
        cbAverage.getItems().addAll(true, false);

        tfTimeBetweenSamples.setText(dto.getTimeBetweenSamples());
        tfTimeToZero.setText(dto.getTimeToZero());
        cbShowSymbols.setValue(dto.isShowSymbols());

        cbManualAverage.setValue(dto.isShowManualAverage());
        tfManualAverageValue.setText(dto.getManualAverage());
        tfManualBoundaries.setText(dto.getManualBoundary());

        cbAverage.setValue(dto.isShowAverage());
        tfBoundariesTack.setText(dto.getBoundaryTack());
        tfBoundariesTwist.setText(dto.getBoundaryTwist());

        cbColdSample.setSelected(dto.isShowColdSamples());
        cbTeachingSample.setSelected(dto.isShowTeachingSamples());
        cbSpeedChangeSample.setSelected(dto.isShowSpeedChangeSamples());
        cbMonitorSample.setSelected(dto.isShowMonitorSamples());
    }

    @FXML
    private void handleOk() {

        if (isInputValid()) {
            SettingsDTO dto = new SettingsDTO();
            dto.setTimeBetweenSamples(tfTimeBetweenSamples.getText());
            dto.setTimeToZero(tfTimeToZero.getText());
            dto.setShowSymbols(cbShowSymbols.getValue());

            dto.setShowManualAverage(cbManualAverage.getValue());
            if (!tfManualAverageValue.getText().isEmpty())
                dto.setManualAverage(tfManualAverageValue.getText());
            if (!tfManualBoundaries.getText().isEmpty())
                dto.setManualBoundary(tfManualBoundaries.getText());

            dto.setShowAverage(cbAverage.getValue());
            if (!tfBoundariesTack.getText().isEmpty())
                dto.setBoundaryTack(tfBoundariesTack.getText());
            if (!tfBoundariesTwist.getText().isEmpty())
                dto.setBoundaryTwist(tfBoundariesTwist.getText());

            dto.setShowColdSamples(cbColdSample.isSelected());
            dto.setShowTeachingSamples(cbTeachingSample.isSelected());
            dto.setShowSpeedChangeSamples(cbSpeedChangeSample.isSelected());
            dto.setShowMonitorSamples(cbMonitorSample.isSelected());

            settingsManager.setGeneralSettings(dto);
            settingsLayout.getDialogStage().close();
        }
    }

    @FXML
    private void handleOkWithKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleOk();
        }
    }

    @FXML
    private void handleCancel() {
        settingsLayout.getDialogStage().close();
    }

    @FXML
    private void handleCancelWithKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleCancel();
        }
    }

    @FXML
    private void handleKeys(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            handleCancel();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        long timeBetweenSamples = 0;
        long timeToZero = 0;

        if (tfTimeBetweenSamples.getText() == null || tfTimeBetweenSamples.getText().length() == 0) {
            errorMessage += "No valid number for time between samples!\n";
        } else {
            // try to parse the upper limit into a double.
            try {
                timeBetweenSamples = Long.parseLong(tfTimeBetweenSamples.getText());

                if (timeBetweenSamples <= 0) {
                    errorMessage += "No valid number for time between samples, number must be positive and greater than 0!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid number for time between samples!\n";
            }
        }

        if (tfTimeToZero.getText() == null || tfTimeToZero.getText().length() == 0) {
            errorMessage += "No valid number for time to zero!\n";
        } else {
            // try to parse the lower limit into a double.
            try {
                timeToZero = Long.parseLong(tfTimeToZero.getText());

                if (timeToZero < 0) {
                    errorMessage += "No valid number for time to zero, number must be positive or 0!\n";
                }

                if (timeToZero < timeBetweenSamples) {
                    errorMessage += "No valid number for time to zero, number must be greather than time betweens samples!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid number for time to zero!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(settingsLayout.getDialogStage());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
