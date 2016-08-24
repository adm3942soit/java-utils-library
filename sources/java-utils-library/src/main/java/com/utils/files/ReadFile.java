package com.utils.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
	
	/**
	 * 
	 * @param fName file name with extention "myTextFile.txt" (can contain path to file "C:/myFolder/myTextFile.txt")
	 * @return array with data from file
	 */
	public String[] readFile(String fName) {
		String ids[] = new String[1];
		ids[0] = "no data from file";
		int i = 0;

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fName));
			while (reader.ready()) {
				String str = reader.readLine();
				if (str != null) {
					if(!str.equals("")) {
						if (i == 0) {
							ids[0] = str;
							i++;
							continue;
						}
						ids = add(str, ids);
						i++;
					}					
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			//�������� ����� ���� ������ �����,
			//����������� ���� � ����������� �����
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	/**
	 * 
	 * @param element string element need to add to existing array
	 * @param buffer source array
	 * @return source array + element 
	 */
	private String[] add(String element, String[] buffer) {
		String[] temp = new String[buffer.length + 1];
		for (int i = 0; i < buffer.length; i++) {
			temp[i] = buffer[i];
		}
		temp[temp.length - 1] = element;
		return temp;
	}
	
}
