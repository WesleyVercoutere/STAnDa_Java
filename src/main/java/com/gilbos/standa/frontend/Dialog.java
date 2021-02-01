package com.gilbos.standa.frontend;

import com.gilbos.standa.service.manager.FxmlManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class Dialog {

    private FxmlManager fxmlMgr;
    private RootLayout root;

    private String fxml;
    private String title;
    private Stage dialogStage;

    public Dialog(FxmlManager fxmlManager, RootLayout rootLayout) {
        this.fxmlMgr = fxmlManager;
        this.root = rootLayout;

        setProperties();
    }

    protected void setFxml(String fxml) {
        this.fxml = fxml;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    public abstract void setProperties();

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void show() {
        Parent page = fxmlMgr.loadView(fxml);

        dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.getIcons().add(new Image("/favicon/G.png"));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(root.getPrimaryStage());

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

}
