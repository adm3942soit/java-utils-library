package com.comp;


public class MBID {
	
	public static String[] getMBModel(){
		String cmdName =
                //MyFiler.getCurrentDirectory()+System.getProperty("file.separator")+
                  //       "res"+System.getProperty("file.separator")+
                "MBModel.bat";
		String[] fromCMD = ToRunCmd.runInCmd(cmdName);
		
		String[] fromCMDClear = new String[fromCMD.length-1];
		for(int i=0;i<fromCMDClear.length;i++) {
			fromCMDClear[i] = fromCMD[i+1].substring(34);
		}		
		return fromCMDClear;
	}
	
	public static String[] getMBBIOSVersion(){
		String cmdName =
                //MyFiler.getCurrentDirectory()+System.getProperty("file.separator")+
                //"res"+System.getProperty("file.separator")+
                        "MBBIOSVersion.bat";
		String[] fromCMD = ToRunCmd.runInCmd(cmdName);
		
		String[] fromCMDClear = new String[fromCMD.length-1];
		for(int i=0;i<fromCMDClear.length;i++) {
			fromCMDClear[i] = fromCMD[i+1].substring(34);
		}		
		return fromCMDClear;
	}
}
