package com.gilbos.standa.frontend;

import com.gilbos.standa.service.manager.FileManager;
import com.gilbos.standa.service.manager.FxmlManager;
import com.gilbos.standa.util.UpdateArgs;
import com.gilbos.standa.util.observer.Observable;
import com.gilbos.standa.util.observer.Observer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RootLayout implements Observer {

    private FxmlManager mgr;
    private FileManager fileManager;

    private Stage primaryStage;
    private BorderPane rootLayout;

    public RootLayout(FxmlManager fxmlManager, FileManager fileManager) {
        this.mgr = fxmlManager;
        this.fileManager = fileManager;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void init(Stage stage) {
        fileManager.addObserver(this);

        this.primaryStage = stage;

        createPrimaryStage();
        createRootLayout();
        createMenuBar();
        createFooter();
    }

    private void createPrimaryStage() {
        primaryStage.setTitle("SmarTwist® data analyzer");
        primaryStage.getIcons().add(new Image("/favicon/G.png"));
        primaryStage.setMaximized(true);
    }

    private void createRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/RootLayout.fxml"));

            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMenuBar() {
        Parent menuBar = mgr.loadView("/FXML/MenuBarLayout.fxml");
        rootLayout.setTop(menuBar);
    }

    private void createFooter() {
        Parent footer = mgr.loadView("/FXML/FooterLayout.fxml");
        rootLayout.setBottom(footer);
    }

    public void createChartFlow() {
        Parent chart = mgr.loadView("/FXML/ChartLayout.fxml");
        rootLayout.setCenter(chart);
    }

    public void createSideBar() {
        Parent chartLegend = mgr.loadView("/FXML/ChartLegendLayout.fxml");
        rootLayout.setLeft(chartLegend);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (UpdateArgs.FILES_NEW.equals(arg) || UpdateArgs.FILES_NEW_ERRORS.equals(arg)) {
            createChartFlow();
            createSideBar();
        }

        if (UpdateArgs.FILES_NEW_ERRORS.equals(arg) || UpdateArgs.FILES_ADDED_ERRORS.equals(arg)) {
            Exception e = new Exception("Errors in CSV file name");
            showAlert(e);
        }

        if (UpdateArgs.CSV_LINE_ERROR.equals(arg)) {
            Exception e = new Exception("Errors in CSV entry");
            showAlert(e);
        }
    }

    private void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(getPrimaryStage());
        alert.setTitle("Invalid CSV");
        alert.setHeaderText("Select other csv");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

}
