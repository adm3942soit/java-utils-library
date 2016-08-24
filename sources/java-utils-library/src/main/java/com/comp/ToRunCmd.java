package com.comp;

import java.io.*;

public class ToRunCmd {


	/**
	 * 
	 * @param cmd = path to file + fileName, like "C:/myFolder/myFile.txt"
	 * @return data from console
	 */
	public static String[] runInCmd(String cmd) {
		String[] ret = new String[1];
		ret[0] = "no data from cmd";
		int j = 0;

		Runtime rt = Runtime.getRuntime();
		Process p;
		try {
			p = rt.exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "Cp866"));
			p.waitFor();
			for (int i = 0; i <= 500; i++) {
				String inputData = br.readLine();
				if (inputData == null) {
					continue;
				}
				if(inputData.equals("")) {
					continue;
				}
				if(j==0) {
					ret[0] = inputData;
					j++;
					continue;
				}
				ret = add(inputData,ret);
				j++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ret;
	}


	/**
	 * 
	 * @return string variable with path to folder, like "C:/myFolder/"
	 */
	public static String getPath() {
		String path = System.getProperty("user.dir");
        System.out.println("!"+path);
		char[] cpath = path.toCharArray();
		for (int i = 0; i < cpath.length; i++) {
			if (cpath[i] == '\\') {
				cpath[i] = '/';
			}
		}
		path = String.valueOf(cpath);
		path += "/";
		System.out.println("path() to run CMD-> "+path);
		return path;
	}


	/**
	 * 
	 * @param name = file name, like "my_file.txt", can consist path to file,
	 *            like "C:\newFolder\my_file.txt"
	 * @param data = data to write in file
	 * @return if everything good -> return true, else=false
	 */
	public static boolean cFile(String name, String data) {
		BufferedWriter myfile;
		try {
			myfile = new BufferedWriter(new FileWriter(name));
			myfile.write(data);
			myfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}


	/**
	 * 
	 * @param fName file name with extention "myTextFile.txt" (can contain path to file "C:/myFolder/myTextFile.txt")
	 * @return array with data from file
	 */
	public static String[] readFile(String fName) {
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
	private static String[] add(String element, String[] buffer) {
		String[] temp = new String[buffer.length + 1];
		for (int i = 0; i < buffer.length; i++) {
			temp[i] = buffer[i];
		}
		temp[temp.length - 1] = element;
		return temp;
	}

}
