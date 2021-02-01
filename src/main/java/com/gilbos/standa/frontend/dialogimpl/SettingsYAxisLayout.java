package com.gilbos.standa.frontend.dialogimpl;

import com.gilbos.standa.frontend.Dialog;
import com.gilbos.standa.frontend.RootLayout;
import com.gilbos.standa.service.manager.FxmlManager;
import org.springframework.stereotype.Component;

@Component
public class SettingsYAxisLayout extends Dialog {

	public SettingsYAxisLayout(FxmlManager fxmlManager, RootLayout rootLayout) {
		super(fxmlManager, rootLayout);
	}

	@Override
	public void setProperties() {
		setFxml("/FXML/SettingsYAxisLayout.fxml");
		setTitle("Set y-axis boundaries");
	}

}
