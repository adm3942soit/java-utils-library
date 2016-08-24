package com.comp.os.linux;



import com.comp.os.DetectorCompOS;
import com.utils.file.Filer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by oxana on 03.07.14.
 */
public class LinuxCorrector {
    public static boolean updateOff(){
        if(!DetectorCompOS.isUnix())return false;
        File settingsLinux=new File("/etc/apt/apt.conf.d/10periodic");
        String str="APT::Periodic::Update-Package-Lists \"1\";";
        String offStr="APT::Periodic::Update-Package-Lists \"0\";";
        int countLiter=str.length()+1;
        try {
            if (settingsLinux.exists()) {
                StringBuffer strFile = new StringBuffer("");
                FileReader fileReader = new FileReader(settingsLinux);
                int i = -1;
                while ((i = fileReader.read()) != -1) {
                    strFile.append((char) i);
                }

                if(strFile.toString().contains(str)) {
                    int index = strFile.indexOf(str);

                    String newStrFile=strFile.substring(0,index)+"\n"+offStr+"\n"+
                            strFile.substring(index+countLiter+1);
                    String rights = Filer.getRightsFile(settingsLinux);
                    if (!settingsLinux.canWrite()) {
                        Filer.setRights(settingsLinux, "777", true, false);
                    }
                    FileWriter fileWriter1 = new FileWriter(settingsLinux);
                    fileWriter1.write(newStrFile);
                    fileWriter1.flush();
                    fileWriter1.close();
                    Filer.setRights(settingsLinux, rights, true, false);

                }else return true;

            }
        }catch(Exception ex){ex.printStackTrace();return false;}
        return true;

    }
    public static boolean updateOn(){
        if(!DetectorCompOS.isUnix())return false;
        File settingsLinux=new File("/etc/apt/apt.conf.d/10periodic");
        String str="APT::Periodic::Update-Package-Lists \"1\";";
        String offStr="APT::Periodic::Update-Package-Lists \"0\";";
        int countLiter=str.length()+1;
        try {
            if (settingsLinux.exists()) {
                StringBuffer strFile = new StringBuffer("");
                FileReader fileReader = new FileReader(settingsLinux);
                int i = -1;
                while ((i = fileReader.read()) != -1) {
                    strFile.append((char) i);
                }

                if(strFile.toString().contains(offStr)) {
                    int index = strFile.indexOf(offStr);

                    String newStrFile=strFile.substring(0,index)+"\n"+str+"\n"+
                            strFile.substring(index+countLiter+1);
                    String rights = Filer.getRightsFile(settingsLinux);
                    if (!settingsLinux.canWrite()) {
                        Filer.setRights(settingsLinux, "777", true, false);
                    }
                    FileWriter fileWriter1 = new FileWriter(settingsLinux);
                    fileWriter1.write(newStrFile);
                    fileWriter1.flush();
                    fileWriter1.close();
                    Filer.setRights(settingsLinux, rights, true, false);

                }else return true;

            }
        }catch(Exception ex){ex.printStackTrace();return false;}
        return true;

    }

    public static boolean setUbuntuNoSleep(){
        if(!DetectorCompOS.isUnix())return false;
        File settingsLinux=new File("/etc/default/acpi-support");
        String str="ACPI_SLEEP=true";
        int countLiter=15;
        try {
            if (settingsLinux.exists()) {
                StringBuffer strFile = new StringBuffer("");
                FileReader fileReader = new FileReader(settingsLinux);
                int i = -1;
                while ((i = fileReader.read()) != -1) {
                    strFile.append((char) i);
                }

                if(strFile.toString().contains("\n"+str)) {
                    int index = strFile.indexOf("\n"+str);
                    String newStrFile=strFile.substring(0,index)+"\n"+"#"+str+strFile.substring(index+countLiter+1);
                    String rights = Filer.getRightsFile(settingsLinux);
                    if (!settingsLinux.canWrite()) {
                        Filer.setRights(settingsLinux, "777", true, false);
                    }
                    FileWriter fileWriter1 = new FileWriter(settingsLinux);
                    fileWriter1.write(newStrFile);
                    fileWriter1.flush();
                    fileWriter1.close();
                    Filer.setRights(settingsLinux, rights, true, false);

                }else return true;

            }
        }catch(Exception ex){ex.printStackTrace();return false;}
        return true;

    }
public static void main(String[]args){
    updateOff();
    //updateOn();
}
}
