package com.comp;


import com.comp.os.DetectorCompOS;

import java.io.File;


public class HDDID {

	public static String[] getSerialNumberLogicDisk() {
		String drive = "C";
		String fileName =
                        "res/getSerialNumberLogicDisk.vbs";
		String addCmd = "cscript //NoLogo ";
		String data = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
				+ "Set colDrives = objFSO.Drives\n"
				+ "Set objDrive = colDrives.item(\""
				+ drive
				+ "\")\n"
				+ "Wscript.Echo objDrive.SerialNumber";
		
		ToRunCmd.cFile(fileName, data);
		String[] fromCMD = ToRunCmd.runInCmd(addCmd + ToRunCmd.getPath() + fileName);
		
		return fromCMD;		
	}
	public static String[] getSerialNumberLogicDisk(String path) {
		String drive = "C";
		String fileName =
				path+ File.separator+"getSerialNumberLogicDisk.vbs";
		String addCmd = "cscript //NoLogo ";
		String data = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
				+ "Set colDrives = objFSO.Drives\n"
				+ "Set objDrive = colDrives.item(\""
				+ drive
				+ "\")\n"
				+ "Wscript.Echo objDrive.SerialNumber";

		ToRunCmd.cFile(fileName, data);
		String[] fromCMD = ToRunCmd.runInCmd(addCmd + ToRunCmd.getPath() + fileName);

		return fromCMD;
	}

    ClassLoader classLoader = getClass().getClassLoader();
	public String[] getHddID() {
		String fileName =
                "res/GetHDDsIDs.exe";

		String[] fromCMD = ToRunCmd.runInCmd(
                DetectorCompOS.isUnix()?"sudo "+ToRunCmd.getPath() + fileName:
                ToRunCmd.getPath() + fileName);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		fileName =
                   "res/"+

                        "HddID.txt";
		fromCMD = ToRunCmd.readFile(fileName);
		return fromCMD;
	}
	public String[] getHddID(String path) {
		String fileName =path+File.separator+
				"GetHDDsIDs.exe";

		String[] fromCMD = ToRunCmd.runInCmd(
				DetectorCompOS.isUnix()?"sudo "+ToRunCmd.getPath() + fileName:
						ToRunCmd.getPath() + fileName);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		fileName =path+File.separator+"HddID.txt";
		fromCMD = ToRunCmd.readFile(fileName);
		return fromCMD;
	}

    public static void main(String []args){
        String[] hwId=new HDDID().getHddID();



        int i=0;
        while(hwId!=null && i<hwId.length) {
            System.out.println(hwId[i]);
            i++;
        }
    }
}

