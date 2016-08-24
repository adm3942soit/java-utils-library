package com.comp.os;

import ua.edu.file.MyFiler;
import com.utils.file.Filer;


import java.io.File;

/**
 * Created by oxana on 25.06.14.
 */
public class Reloader {
    private static String nameFileZip="backupLogReboot.zip";
    public static boolean execute(){
       String cmdReboot="";
        if (DetectorCompOS.isWindows()) {
            cmdReboot = "shutdown.exe -r -f -t 0";
        } else
        if (DetectorCompOS.isUnix()) {
            File file=new File(MyFiler.getCurrentDirectory()+System.getProperty("file.separator")+"reboot.sh");
            Filer.setRights(file, "777", true, false);
            cmdReboot = file.getAbsolutePath();//shutdown -r now";"bash -c "+
        }
        try {
                Runtime.getRuntime().exec(cmdReboot);
/*
                ZipArchiv.archivAdminPanelLog(nameFileZip);

                ProcessBuilder pb= new ProcessBuilder(cmdReboot);

                pb.directory(new File(MyFiler.getCurrentDirectory()));
                String nameFileLog= "logReboot_"+ ua.com.bpgroup.com.utils.Dater.getDateForLog1()+".txt";
                File log = new File(nameFileLog);
                if(!log.exists())log.createNewFile();
                pb.redirectErrorStream(true);
                pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
                assert pb.redirectOutput().file() == log;

                Process process=pb.start();

                InputStream is = process.getInputStream();
                int in = -1;
                while ((in = is.read()) != -1) {
                    System.out.print("InputStream");
                    System.out.print((char) in);
                }
                is = process.getErrorStream();
                in = -1;
                while ((in = is.read()) != -1) {
                    System.out.print("ErrorStream");
                    System.out.print((char) in);
                }
*/

            // System.out.println("reboot"+process.exitValue());
        } catch (Exception ex) {
            ex.printStackTrace();

        return false;}
      return true;
    }
    public static boolean execute(String directory){
        String cmdReboot="";
        if (DetectorCompOS.isWindows()) {
            cmdReboot = "shutdown.exe -r -f -t 0";
        } else
        if (DetectorCompOS.isUnix()) {
            File file=new File(directory+System.getProperty("file.separator")+"reboot.sh");
            Filer.setRights(file,"777", true, false);
            cmdReboot = file.getAbsolutePath();//shutdown -r now";"bash -c "+
        }
        try {
            Runtime.getRuntime().exec(cmdReboot);
/*
                ZipArchiv.archivAdminPanelLog(nameFileZip);

                ProcessBuilder pb= new ProcessBuilder(cmdReboot);

                pb.directory(new File(MyFiler.getCurrentDirectory()));
                String nameFileLog= "logReboot_"+ ua.com.bpgroup.com.utils.Dater.getDateForLog1()+".txt";
                File log = new File(nameFileLog);
                if(!log.exists())log.createNewFile();
                pb.redirectErrorStream(true);
                pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
                assert pb.redirectOutput().file() == log;

                Process process=pb.start();

                InputStream is = process.getInputStream();
                int in = -1;
                while ((in = is.read()) != -1) {
                    System.out.print("InputStream");
                    System.out.print((char) in);
                }
                is = process.getErrorStream();
                in = -1;
                while ((in = is.read()) != -1) {
                    System.out.print("ErrorStream");
                    System.out.print((char) in);
                }
*/

            // System.out.println("reboot"+process.exitValue());
        } catch (Exception ex) {
            ex.printStackTrace();

            return false;}
        return true;
    }

    public static void main(String[] args){
        execute();
    }
}
