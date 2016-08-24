package com.utils.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

	/**
	 * 
	 * @param name = file name, like "my_file.txt", can consist path to file,
	 *            like "C:\newFolder\my_file.txt"
	 * @param data = data to write in file
	 * @return if everything good -> return true, else=false
	 */
	public boolean cFile(String name, String data) {
		BufferedWriter myfile;
		File f;
		try {
			f=new File(name);
			myfile = new BufferedWriter(new FileWriter(f));
			myfile.write(data);
			myfile.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
