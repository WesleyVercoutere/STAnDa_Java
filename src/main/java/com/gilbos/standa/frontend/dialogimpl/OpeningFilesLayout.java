package com.gilbos.standa.frontend.dialogimpl;

import com.gilbos.standa.frontend.Dialog;
import com.gilbos.standa.frontend.RootLayout;
import com.gilbos.standa.service.manager.FileManager;
import com.gilbos.standa.service.manager.FxmlManager;
import com.gilbos.standa.util.UpdateArgs;
import com.gilbos.standa.util.observer.Observable;
import com.gilbos.standa.util.observer.Observer;
import org.springframework.stereotype.Component;

@Component
public class OpeningFilesLayout extends Dialog implements Observer {

	private FileManager fileManager;

	public OpeningFilesLayout(FxmlManager fxmlManager, RootLayout rootLayout, FileManager fileManager) {
		super(fxmlManager, rootLayout);
		this.fileManager = fileManager;

		this.init();
	}

	private void init() {
		fileManager.addObserver(this);
	}

	@Override
	public void setProperties() {
		setFxml("/FXML/OpeningFilesLayout.fxml");
		setTitle("Opening files");
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (UpdateArgs.START_READING_FILES.equals(arg)) {
			show();
		}
		
		if (UpdateArgs.END_READING_FILES.equals(arg)) {
			getDialogStage().close();
		}
	}

}
