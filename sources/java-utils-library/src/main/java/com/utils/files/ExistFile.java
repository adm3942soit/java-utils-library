package com.utils.files;

import java.io.File;

public class ExistFile {

	public boolean isFilePresent(String path){
		if(path==null){
			return false;
		}
		
		File file = new File(path);
		if(file.exists() && file.isFile()){
			return true;
		}
		
		//
		//show screen with file browser!
		//copy file to right path!
		
		return false;
	}
}
