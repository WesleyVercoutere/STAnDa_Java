package com.gilbos.standa.frontend.controller;

import com.gilbos.standa.service.dto.FooterDTO;
import com.gilbos.standa.service.manager.SettingsManager;
import com.gilbos.standa.util.UpdateArgs;
import com.gilbos.standa.util.observer.Observable;
import com.gilbos.standa.util.observer.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FooterController implements Observer {

    @Autowired
    private SettingsManager settingsManager;

    @FXML
    private Label lblFooter;

    @FXML
    private void initialize() {
        settingsManager.addObserver(this);

        lblFooter.setText("");
    }

    @Override
    public void update(Observable o, Object arg) {

        if (UpdateArgs.CHANGED_X_AXIS_VALUES.equals(arg))
            updateLabel();
    }

    private void updateLabel() {
        FooterDTO dto = settingsManager.getFooterDTO();
        StringBuilder sb = new StringBuilder();

        sb.append("Tick unit = ").append(dto.getTickUnit()).append(" [h:mm:ss]");
        sb.append("\t\t").append("Total time = ").append(dto.getTotalAxisTime()).append(" [h:mm:ss]");

        lblFooter.setText(sb.toString());
    }

}
