package com.gilbos.standa.frontend.controller;

import com.gilbos.standa.frontend.dialogimpl.SettingsYAxisLayout;
import com.gilbos.standa.service.dto.SettingsDTO;
import com.gilbos.standa.service.manager.SettingsManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingsYAxisController {

    @Autowired
    private SettingsManager settingsManager;
    @Autowired
    private SettingsYAxisLayout settingsYAxisLayout;

    @FXML
    private TextField upperLimitField;
    @FXML
    private TextField lowerLimitField;
    @FXML
    private TextField tickUnitField;

    @FXML
    private void initialize() {
        SettingsDTO dto = settingsManager.getSettings();

        upperLimitField.setText(Double.toString(dto.getyAxisUpperBound()));
        lowerLimitField.setText(Double.toString(dto.getyAxisLowerBound()));
        tickUnitField.setText(Double.toString(dto.getyAxisTickUnit()));
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            SettingsDTO dto = new SettingsDTO();
            dto.setyAxisUpperBound(Double.parseDouble(upperLimitField.getText()));
            dto.setyAxisLowerBound(Double.parseDouble(lowerLimitField.getText()));
            dto.setyAxisTickUnit(Double.parseDouble(tickUnitField.getText()));

            settingsManager.setYAxisSettings(dto);

            settingsYAxisLayout.getDialogStage().close();
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
        settingsYAxisLayout.getDialogStage().close();
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

        double upperLimit = 0;
        double lowerLimit = 0;
        double tickUnit = 0;

        if (upperLimitField.getText() == null || upperLimitField.getText().length() == 0) {
            errorMessage += "No valid number for the upper limit!\n";
        } else {
            // try to parse the upper limit into a double.
            try {
                upperLimit = Double.parseDouble(upperLimitField.getText());

                if (upperLimit <= 0) {
                    errorMessage += "No valid number for the upper limit, number must be positive and greater than 0!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid number for the upper limit!\n";
            }
        }

        if (lowerLimitField.getText() == null || lowerLimitField.getText().length() == 0) {
            errorMessage += "No valid number for the lower limit!\n";
        } else {
            // try to parse the lower limit into a double.
            try {
                lowerLimit = Double.parseDouble(lowerLimitField.getText());

                if (lowerLimit < 0) {
                    errorMessage += "No valid number for the lower limit, number must be positive or 0!\n";
                }

                if (lowerLimit >= upperLimit) {
                    errorMessage += "No valid number for the lower limit, number must be less than upper limit value!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid number for the lower limit!\n";
            }
        }

        if (tickUnitField.getText() == null || tickUnitField.getText().length() == 0) {
            errorMessage += "No valid number for the tick unit!\n";
        } else {
            // try to parse the tick unit into a double.
            try {
                tickUnit = Double.parseDouble(tickUnitField.getText());

                if (tickUnit > 0) {

                    double qty = (upperLimit - lowerLimit) / tickUnit;
                    int min = (int) (((upperLimit - lowerLimit) / qty) + 1);

                    if (qty > 100) {
                        errorMessage += "No valid number for the tick unit, minimum number = " + min + "!\n";
                    }
                }

                if (tickUnit < 0) {
                    errorMessage += "No valid number for the tick unit, number must be positive or 0!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid number for the tick unit!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(settingsYAxisLayout.getDialogStage());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
