package com.comp;


import org.apache.log4j.Logger;

import java.io.File;


public class CPUID {
	private static final Logger log = Logger.getLogger(CPUID.class);
	public static String[] getCPUID() {
		String fileName = //MyFiler.getCurrentDirectory()+System.getProperty("file.separator")+
                "res/"+
                     //  System.getProperty("file.separator")+
                       "wmicpuid.vbs";
        log.info("!"+fileName);
		String addCmd = "cscript ";
		String data = "";
		data = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")" + "\n"
				+ "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")"	+ "\n"
				+"For Each objItem in colItems" + "\n"
				+ "Wscript.Echo \"\" & objitem.Caption" + "\n"
				+ "Wscript.Echo \"\" & objItem.ProcessorId" + "\n"
				+ "Next";

		ToRunCmd.cFile(fileName, data);
        File file=new File(ToRunCmd.getPath() + fileName);
        if(file.exists())log.info("File found");
        else log.info("File not found");
		String[] fromCMD = ToRunCmd.runInCmd(addCmd + file.getAbsolutePath());
		
		String[] fromCMDClear = new String[fromCMD.length-2];
		for(int i=0;i<fromCMDClear.length;i++) {
			fromCMDClear[i] = fromCMD[i+2];
		}

		return fromCMDClear;
	}
	public static String[] getCPUID(String path) {
		String fileName = //MyFiler.getCurrentDirectory()+System.getProperty("file.separator")+
				path+File.separator+
						"wmicpuid.vbs";
		log.info("!"+fileName);
		String addCmd = "cscript ";
		String data = "";
		data = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")" + "\n"
				+ "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")"	+ "\n"
				+"For Each objItem in colItems" + "\n"
				+ "Wscript.Echo \"\" & objitem.Caption" + "\n"
				+ "Wscript.Echo \"\" & objItem.ProcessorId" + "\n"
				+ "Next";

		ToRunCmd.cFile(fileName, data);
		File file=new File(ToRunCmd.getPath() + fileName);
		if(file.exists())log.info("File found");
		else log.info("File not found");
		String[] fromCMD = ToRunCmd.runInCmd(addCmd + file.getAbsolutePath());

		String[] fromCMDClear = new String[fromCMD.length-2];
		for(int i=0;i<fromCMDClear.length;i++) {
			fromCMDClear[i] = fromCMD[i+2];
		}

		return fromCMDClear;
	}

}
