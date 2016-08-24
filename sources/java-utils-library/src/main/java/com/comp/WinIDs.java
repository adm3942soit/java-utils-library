package com.comp;


public class WinIDs {

	public static String[] getWinLicenceKey(){
		//from cmd, file.bat
		
		String fileName = //"res\\" +
                "WinLicenceKey.bat";
		String data = "";
		
		data = 
				"@echo off" + "\n" +
				"setlocal enabledelayedexpansion" + "\n" +
				"for /f \"tokens=3\" %%i in ('reg.exe query \"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\" /v DigitalProductId') do ("+
				"\n" + "set x=%%~i " + "\n" + ")"+ "\n" +
				"for /l %%z in (104, 2, 132) do (" + "\n" +
				"set /a array[%%z]=0x!x:~%%z,2!" + "\n" +
				")" + "\n" +
				"call :GetKey"+ "\n" +
				"echo ProductKey: %GetKey%"+ "\n" +
				"rem pause >nul"+ "\n" +
				"endlocal" + "\n" +
				"exit /b 0"+ "\n" +
				":GetKey"+ "\n" +
				"setlocal enabledelayedexpansion"+ "\n" +
				"set out=%~0"+ "\n" +
				"set pc=BCDFGHJKMPQRTVWXY2346789"+ "\n" +
				"set x=0"+ "\n" +
				"for /l %%i in (0, 1, 28) do ("+ "\n" +
				"if !x! gtr 28 goto :Break"+ "\n" +
				"set a=0"+ "\n" +
				"for /l %%j in (132, -2, 104) do ("+ "\n" +
				"set /a a=array[%%j] + !a! * 256"+ "\n" +
				"set /a array[%%j]=\"( !a! / 24 ) & 255\""+ "\n" +
				"set /a a%%=24"+ "\n" +
				"set /a n=%%j"+ "\n" +
				")"+ "\n" +
				"for %%z in (!a!) do set key=!pc:~%%z,1!!key!"+ "\n" +
				"set /a f=\"( !x! + 2 ) %% 6\""+ "\n" +
				"if !f! equ 0 if !x! lss 28 ("+ "\n" +
				"set /a x+=1"+ "\n" +
				"set key=-!key!"+ "\n" +
				")"+ "\n" +
				"<nul set /p sTemp=."+ "\n" +
				"set /a x+=1"+ "\n" +
				")"+ "\n" +
				":Break"+ "\n" +
				"echo."+ "\n" +
				"endlocal & set %out:~1%=%key%"+ "\n" +
				"exit /b";	
		
		ToRunCmd.cFile(fileName, data);
		String[] fromCMD = ToRunCmd.runInCmd(ToRunCmd.getPath() + fileName);
		
		String[] fromCMDClear = new String[fromCMD.length-1];
		for(int i=0;i<fromCMDClear.length;i++) {
			fromCMDClear[i] = fromCMD[i+1].substring(12);
		}
		
		return fromCMDClear;
	}
	
	public static String[] getWinProductKey(){
		//from wmi
		String cmdName = "res\\WinProductKey.bat";
		String[] fromCMD = ToRunCmd.runInCmd(cmdName);
		
		String[] fromCMDClear = new String[fromCMD.length-1];
		for(int i=0;i<fromCMDClear.length;i++) {
			fromCMDClear[i] = fromCMD[i+1].substring(34);
		}		
		return fromCMDClear;
	}
	
	
	public static String[] getWinInstallDate(){
		//from wmi
		String cmdName = "res\\WinInstallDate.bat";
		String[] fromCMD = ToRunCmd.runInCmd(cmdName);
		
		String[] fromCMDClear = new String[fromCMD.length-1];
		for(int i=0;i<fromCMDClear.length;i++) {
			fromCMDClear[i] = fromCMD[i+1].substring(34);
		}		
		return fromCMDClear;
	}	
}
