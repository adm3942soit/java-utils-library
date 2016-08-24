package com.utils.files;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class MyPropertiesSave {

	private Properties prop = new Properties();
	private String fileName="";

	public MyPropertiesSave(String fileName){
		this.fileName=fileName;
	}
	
	public void saveProperties(String[] key, String[] value) {
		if(key==null || value==null){
			return;
		}
		
		for(int i=0;i<key.length;i++){
			prop.setProperty(key[i], value[i]);	
		}
		
		OutputStream out=null;
		try {
			out = new FileOutputStream(fileName);
			prop.store(out, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
