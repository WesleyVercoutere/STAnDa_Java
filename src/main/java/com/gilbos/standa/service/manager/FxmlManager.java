package com.gilbos.standa.service.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class FxmlManager {

    @Autowired
    private ApplicationContext context;

    private Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean); //Spring now FXML Controller Factory
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node of
     * that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    public Parent loadView(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = this.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootNode;
    }

}
