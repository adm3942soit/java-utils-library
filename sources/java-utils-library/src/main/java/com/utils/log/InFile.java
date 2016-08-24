package com.utils.log;

import com.utils.date.Dater;
import com.utils.file.Filer;
import ua.edu.file.MyFiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class InFile {

	private static BufferedWriter bufferedWriter;
    private static File file;
    private static String nameFile="";
    //#############################################################################################
    public InFile(){

    }
    //#############################################################################################
	public boolean openFile(String name) {
        nameFile=MyFiler.getCurrentDirectory()+File.separator+name;
		try {
			file = new File(nameFile);
            if(!file.exists()){
                Filer.createFile(nameFile);
            }
			FileWriter fileWriter = new FileWriter(file, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
            return false;
		}
	}
    //#############################################################################################
    public boolean writeInFile (String str) {
		try {
            if(bufferedWriter==null){System.out.print("!!!bufferedWriter==null");
                                      return false;}

            bufferedWriter.write(str + "\n");
            System.out.println(str);
            bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
    //#############################################################################################
    public void print(String message) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(Dater.getTime() + " : " + message);
            writer.newLine();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //#############################################################################################
    public File getFile() {
       if(file==null || !file.exists())System.out.println("file log not exist!");
        return file;
    }

//#############################################################################################
	public boolean writeEmptyLine (String str) {
		try {
            if(bufferedWriter==null)return false;
			bufferedWriter.write(str);
            bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
//#############################################################################################
	public boolean closeFile(String str) {
		if (bufferedWriter != null) {
			try {
				writeInFile(str);
                bufferedWriter.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return false;
	}
}
