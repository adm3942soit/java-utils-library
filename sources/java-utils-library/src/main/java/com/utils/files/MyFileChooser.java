package com.utils.files;

import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;

public class MyFileChooser {

	public File forRead(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Zip files (*.zip)", "*.zip");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			return file;
		}
		return null;
	}

}
