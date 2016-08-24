package com.utils.file;



import com.comp.os.DetectorCompOS;

import java.io.*;


/**
 * Created by PROGRAMMER II on 25.03.2014.
 */
public class Filer {
    public static String getExtensionFile(File file){
        if(file.exists()){
            int index=file.getName().indexOf(".");
            if(index==-1)return "";
            System.out.println(file.getName().substring(index+1));
            return file.getName().substring(index+1);
        }else return "";

    }
    public static boolean createFile(String path){
        File file=new File(path);
        if(file.exists())return true;
        boolean ok=false;
        try {
            if(!file.getParentFile().exists()) file.getParentFile().mkdir();

            if (DetectorCompOS.isWindows()){
                ok= file.createNewFile();
            return ok;}

            if(DetectorCompOS.isUnix()){
                Process process=Runtime.getRuntime().exec("sudo touch " + file.getAbsolutePath());
                process.waitFor();
                return process.exitValue()==0;

            }
        }catch(Exception ex){ex.printStackTrace();}
        return false;
    }
    public static void copy(File source, File dest) throws IOException {
        if(!dest.exists()){

               if(DetectorCompOS.isUnix()){
                   Process process=Runtime.getRuntime().exec("sudo chmod -R 777 " + dest.getParentFile().getAbsolutePath());
                  try {
                      process.waitFor();
                  }catch(Exception ex){ex.printStackTrace();}
               }
            dest.createNewFile();}
        if(DetectorCompOS.isUnix()){
            Process process=Runtime.getRuntime().exec("sudo chmod -R 777 "+dest.getAbsolutePath());
            try {
                process.waitFor();
            }catch(Exception ex){ex.printStackTrace();}

        }
        FileInputStream is = new FileInputStream(source);
        try {
            FileOutputStream os = new FileOutputStream(dest);
            try {
                byte[] buffer = new byte[4096];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } finally {
                os.close();
            }
        } finally {
            is.close();
        }
    }
    public static boolean deleteFileInLinux(File source){
        if(!source.exists()) return true;
        String cmd="sudo rm -f "+source.getAbsolutePath();
        Process process=null;
        try {
            process=Runtime.getRuntime().exec(cmd);
            process.waitFor();
            return process.exitValue()==0;
        }catch(Exception ex){ex.printStackTrace(); return false;}

    }

    public static boolean copyInEtcLinux(File source, String dest){
        //sudo cp ~/SpecGroupSvnProject/experimental/paysystem/paySystem.sh /etc/paySystem.sh
        String cmd="sudo cp "+source.getAbsolutePath()+" "+dest;
        Process process=null;
        try {
            process=Runtime.getRuntime().exec(cmd);
            process.waitFor();
            return process.exitValue()==0;
        }catch(Exception ex){ex.printStackTrace(); return false;}

    }
    public static String  getRightsFile(File source){
        if(!DetectorCompOS.isUnix())return "";
        String rightsCmd="ls -l "+source.getAbsolutePath();
        boolean ok=false;
        StringBuffer buffer=new StringBuffer("");
        try {
            Process process = Runtime.getRuntime().exec(rightsCmd);
            process.waitFor();
            InputStreamReader reader=new InputStreamReader(process.getInputStream());
            int i=-1;
            while((i=reader.read())!=-1){
                buffer.append((char)i);
            }
            ok = (process.exitValue() == 0);
            System.out.println(ok);
            if(!ok)return null;
        }catch(Exception ex){ex.printStackTrace();}
        System.out.println(buffer.toString());

        if(buffer.toString().contains("-r--r-----"))return "440";
        if(buffer.toString().contains("-r--r--r--"))return "444";
        if(buffer.toString().contains("-r-xr--r--"))return "544";
        if(buffer.toString().contains("-r-xr-xr--"))return "554";
        if(buffer.toString().contains("-r-xr-xr-x"))return "555";
        if(buffer.toString().contains("-rw-r--r--"))return "644";
        if(buffer.toString().contains("-rw-rw-r--"))return "664";
        if(buffer.toString().contains("-rw-rw-rw-"))return "666";
        if(buffer.toString().contains("-rwxr--r--"))return "744";
        if(buffer.toString().contains("-rwxrw-r--"))return "764";
        if(buffer.toString().contains("-rwxrw-rw-"))return "766";
        if(buffer.toString().contains("-rwxrwxr--"))return "774";
        if(buffer.toString().contains("-rwxr-xr-x"))return "755";
        if(buffer.toString().contains("-rwxr-xr--"))return "754";
        if(buffer.toString().contains("-rwxrwxrwx"))return "777";
        return buffer.toString();
    }
    public static  String readFile(File source, boolean su, boolean exec){
        if(!source.exists() )return null;
        boolean ok=false;

        String rights=getRightsFile(source);
       // System.out.println(rights);
        if(!source.canRead()){
         //  System.out.println("!source.canRead()");
           if(DetectorCompOS.isUnix()) {
               String chmodCmd = exec ? "pkexec chmod 777 " + source.getAbsolutePath() : su ? "sudo chmod 777 " + source.getAbsolutePath() : "chmod 777 " + source.getAbsolutePath();
               try {
                   Process process = Runtime.getRuntime().exec(chmodCmd);
                   process.waitFor();
                   ok = (process.exitValue() == 0);
                   if (!ok) return null;
               } catch (Exception ex) {
                   ex.printStackTrace();
                   return null;
               }
           }
           if(DetectorCompOS.isWindows()){
               if(!source.setReadable(true, false))return null;
           }
        }
        StringBuffer buffer=new StringBuffer("");
        try {
            FileReader reader = new FileReader(source);
            int i=-1;
            while((i=reader.read())!=-1){
                buffer.append((char)i);
            }

        }catch(Exception ex ){ex.printStackTrace(); return null;}
        if(DetectorCompOS.isUnix()) {

            String chmodCmd = exec ? "pkexec chmod " + rights + " " + source.getAbsolutePath() : su ? "sudo chmod " + rights + " " + source.getAbsolutePath() : "chmod " + rights + " " + source.getAbsolutePath();
            try {
                Process process = Runtime.getRuntime().exec(chmodCmd);
                process.waitFor();
                ok = (process.exitValue() == 0);
                // if(!ok)return buffer.toString();
                rights = getRightsFile(source);
                System.out.println(rights);

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

        }
      return buffer.toString();
    }
    public static boolean setRights(File source, String rights, boolean su, boolean exec){
       if(!DetectorCompOS.isUnix()) return false;
       if(!source.exists())return false;
        String chmodCmd=exec?"pkexec chmod "+rights+" "+source.getAbsolutePath():su?"sudo chmod "+rights+" "+source.getAbsolutePath():"chmod "+rights+" "+source.getAbsolutePath();
        boolean ok=false;
        try {
            Process process = Runtime.getRuntime().exec(chmodCmd);
            process.waitFor();
            ok = (process.exitValue() == 0);
            return ok;
        }catch(Exception ex){ex.printStackTrace(); return false;}

    }
    public static boolean rewriteFile(File source, String str){
        if(!source.exists())return false;
        String rights=getRightsFile(source);
        System.out.println(rights);

        if(!source.canWrite()){
            setRights(source,"777", false, false);
            if(DetectorCompOS.isWindows()){
                if(!source.setWritable(true, false))return false;
            }
        }
        try {
            FileWriter writer = new FileWriter(source);
            writer.append(str);
            writer.flush();
            writer.close();
            setRights(source,rights, false, false);
            return true;
        }catch(Exception ex){ex.printStackTrace();return false;}

    }

    public static boolean appendFile(File source, String str){
        if(!source.exists())return false;
        String rights=getRightsFile(source);
        System.out.println(rights);

        if(!source.canWrite()){
            setRights(source,"777", false, false);
            if(DetectorCompOS.isWindows()){
                if(!source.setWritable(true, false))return false;
            }
        }
        try {
            FileWriter writer = new FileWriter(source, true);
            writer.append("\n"+str);
            writer.flush();
            writer.close();
            setRights(source,rights, false, false);
            return true;
        }catch(Exception ex){ex.printStackTrace();return false;}

    }
    public static boolean appendFile(File source, String str, boolean withPS){
        if(!source.exists())return false;
        String rights=getRightsFile(source);
        System.out.println(rights);

        if(!source.canWrite()){
            setRights(source,"777", false, false);
            if(DetectorCompOS.isWindows()){
                if(!source.setWritable(true, false))return false;
            }
        }
        try {
            FileWriter writer = new FileWriter(source, true);

            writer.append(withPS?"\n"+str:str);
            writer.flush();
            writer.close();
            setRights(source,rights, false, false);
            return true;
        }catch(Exception ex){ex.printStackTrace();return false;}

    }

    public static boolean noPasswordInLinux(){
       if(!DetectorCompOS.isUnix()) return false;
       boolean ok=false;
       Process  process=null;
       String fileName="/etc/sudoers";
       File source=new File(fileName);
       String userName=System.getProperty("user.name");
       String noPassword=userName+" ALL=(ALL) NOPASSWD:ALL";

       String rights=getRightsFile(source);

       String chownCmd="pkexec chown "+userName+":"+userName+" "+fileName;


       try {
           process=Runtime.getRuntime().exec(chownCmd);
           process.waitFor();
           ok=(process.exitValue()==0);
           setRights(source, "777", false, false);
           String strFile=readFile(new File(fileName), false, false) ;
           System.out.println("readFile "+strFile);
           if(strFile!=null && !strFile.contains(noPassword)){
               appendFile(new File(fileName),noPassword);
           }

           setRights(source, rights, false, false);

           chownCmd="pkexec chown "+"root:root "+fileName;
           process=Runtime.getRuntime().exec(chownCmd);
           process.waitFor();
           ok=(process.exitValue()==0);
           getRightsFile(source);
       }catch(Exception ex){ex.printStackTrace();return false;}
       return true;
    }
    public static boolean writeIntoAutoexecLinux( String str){
        if(!DetectorCompOS.isUnix())return false;
        File autoexecLinux=new File("/etc/rc.local");
        try {
            if (autoexecLinux.exists()) {
                StringBuffer strFile = new StringBuffer("");
                FileReader fileReader = new FileReader(autoexecLinux);
                int i = -1;
                while ((i = fileReader.read()) != -1) {
                    strFile.append((char) i);
                }

                if(strFile.toString().contains(str)) return true;

                int index = strFile.indexOf("\n" + "exit");
                String newStrFile = new String();
                if (index != -1) {

                    newStrFile = strFile.substring(0, index) + "\n" + str+ "\n" + strFile.substring(index);//directory + System.getProperty("file.separator") + "paySystem.sh" +
                }
                String rights = getRightsFile(autoexecLinux);
                if (!autoexecLinux.canWrite()) {
                    setRights(autoexecLinux, "777", true, false);
                }
                FileWriter fileWriter1 = new FileWriter(autoexecLinux);
                fileWriter1.write(newStrFile);
                fileWriter1.flush();
                fileWriter1.close();
                setRights(autoexecLinux, rights, true, false);
            } else return false;
        }catch(Exception ex){ex.printStackTrace();return false;}
      return true;
    }
    public static boolean clearAutoexecLinux(String str){
        if(!DetectorCompOS.isUnix())return false;
        File autoexecLinux=new File("/etc/rc.local");
        try {
            if (autoexecLinux.exists()) {
                StringBuffer strFile = new StringBuffer("");
                FileReader fileReader = new FileReader(autoexecLinux);
                int i = -1;
                while ((i = fileReader.read()) != -1) {
                    strFile.append((char) i);
                }

                if(!strFile.toString().contains(str))return true;
                int index= strFile.indexOf(str);
                int index1 = strFile.indexOf("\n"+"exit");
                String newStrFile = new String();
                if(index1!=-1 && index!=-1)
                newStrFile = strFile.substring(0, index) + "\n" + strFile.substring(index1);//directory + System.getProperty("file.separator") + "paySystem.sh" +
                else
                newStrFile =
                "#!/bin/sh -e"+"\n"+

                "# rc.local"+

                "\n" + "exit 0";
                String rights = getRightsFile(autoexecLinux);
                if (!autoexecLinux.canWrite()) {
                    setRights(autoexecLinux, "777", true, false);
                }
                FileWriter fileWriter1 = new FileWriter(autoexecLinux);
                fileWriter1.write(newStrFile);
                fileWriter1.flush();
                fileWriter1.close();
                setRights(autoexecLinux, rights, true, false);
            } else return false;
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
                    String rights = getRightsFile(settingsLinux);
                    if (!settingsLinux.canWrite()) {
                        setRights(settingsLinux, "777", true, false);
                    }
                    FileWriter fileWriter1 = new FileWriter(settingsLinux);
                    fileWriter1.write(newStrFile);
                    fileWriter1.flush();
                    fileWriter1.close();
                    setRights(settingsLinux, rights, true, false);

                }else return true;

            }
            }catch(Exception ex){ex.printStackTrace();return false;}
            return true;

    }
    public static void main(String[] args){
        getExtensionFile(new File("test.txt"));
/*
        String directory="/home/oxana/SpecGroupSvnProject/experimental/paysystem";
        String pathLib =
                directory;
        String directoryAutoStart=System.getProperty("user.home")
                +System.getProperty("file.separator")
                +".config"
                +System.getProperty("file.separator")
                +"autostart";
        File autoStart=new File(directoryAutoStart);
        if(!autoStart.exists()){
            autoStart.mkdirs();
        }

        File paySystemStartUp=new File(directoryAutoStart+System.getProperty("file.separator")+"paySystem.desktop");
*/
/*
                + System.getProperty("file.separator")
                + "install"
                + System.getProperty("file.separator")
                + "paySystem";
*/
/*
       try {
           Filer.copy(new File(pathLib + System.getProperty("file.separator") + "paySystem.sh"),
                   new File("/etc/profile.d/paySystem.sh"));
       }catch(Exception ex){ex.printStackTrace();}
*/
        //System.out.println(getRightsFile(new File("/etc/sudoers")));
        //System.out.println(readFile(new File("/etc/sudoers"), true, true));
        //System.out.println(noPasswordInLinux());
       try {
          // noPasswordInLinux();
/*
           Filer.copyInUnix(new File(pathLib + System.getProperty("file.separator") + "paySystem.sh"),
                   new File("/etc/paySystem.sh"));
*/
/*
           File file=new File("/etc/paySystem.sh");
           Filer.setRights(file,"777", true, false);
*/

          // System.out.println(copyInEtcLinux(new File ("/home/oxana/SpecGroupSvnProject/experimental/paysystem/paySystem.sh"), "/etc/paySystem.sh"));
               //sudo cp ~/SpecGroupSvnProject/experimental/paysystem/paySystem.sh /etc/paySystem.sh

/*
           Process process= Runtime.getRuntime().exec("sudo chmod u+x "+pathLib+System.getProperty("file.separator")+"paySystem.sh");
           process.waitFor();
           process= Runtime.getRuntime().exec("sudo chmod u+x "+pathLib+System.getProperty("file.separator")+"paysystem.jar");
           process.waitFor();

           if(!paySystemStartUp.exists()) paySystemStartUp.createNewFile();
           FileWriter fileWriter=new FileWriter(paySystemStartUp);
           fileWriter.write("[Desktop Entry]" +"\n"
                   +"Type=Application"+"\n"
                   +"Exec="+pathLib+System.getProperty("file.separator")+"paySystem.sh"+"\n"
                   +"Hidden=false"+"\n"
                   +"NoDisplay=false"+"\n"
                   +"X-GNOME-Autostart-enabled=true"+"\n"
                   +"Name[en_US]=paySystem"+"\n"
                   +"Name=paySystem"+"\n"
                   +"Comment[en_US]=paySystem"+"\n"
                   +"Comment=paySystem");
           fileWriter.flush();
           fileWriter.close();
           System.out.println(Filer.copyInEtcLinux(paySystemStartUp,"/etc/xdg/autostart/paySystem.desktop"));
*/

/*
System.out.println(Filer.copyInEtcLinux(new File (pathLib+System.getProperty("file.separator")+"paySystem.sh"), "/etc/profile.d/paySystem.sh"));
System.out.println(Filer.copyInEtcLinux(new File (pathLib+System.getProperty("file.separator")+"paySystem.sh"), "/etc/init.d/paySystem.sh"));
System.out.println(Filer.copyInEtcLinux(new File (pathLib+System.getProperty("file.separator")+"paySystem.sh"), "/etc/xdg/autostart/paySystem.sh"));
System.out.println(Filer.copyInEtcLinux(new File (pathLib+System.getProperty("file.separator")+"paySystem.sh"), "/etc/paySystem.sh"));
System.out.println(Filer.copyInEtcLinux(paySystemStartUp,"/etc/xdg/autostart/paySystem.desktop"));



*/

         //  writeIntoAutoexecLinux("paySystem.sh");
//           clearAutoexecLinux("paySystem.sh");
/*
           if(Filer.deleteFileInLinux(new File("/etc/xdg/autostart/paySystem.desktop")))
           //while (!(new File("/etc/xdg/autostart/paySystem.desktop")).delete())
           {System.out.println("delete "+"/etc/xdg/autostart/paySystem.desktop");} ;
*/
/*
           if(Filer.deleteFileInLinux(new File("/etc/profile.d/paySystem.sh")))
           //while (!(new File("/etc/profile.d/paySystem.sh")).delete())
           {System.out.println("delete "+"/etc/profile.d/paySystem.sh");}
           if(Filer.deleteFileInLinux(new File("/etc/init.d/paySystem.sh")))
           //while (!(new File("/etc/init.d/paySystem.sh")).delete())
           {System.out.println("delete "+"/etc/init.d/paySystem.sh");}
           if(Filer.deleteFileInLinux(new File("/etc/xdg/autostart/paySystem.sh")))
           //while (!(new File("/etc/xdg/autostart/paySystem.sh")).delete())
           {System.out.println("delete "+"/etc/xdg/autostart/paySystem.sh");}
           if(Filer.deleteFileInLinux(new File(System.getProperty("user.home") + System.getProperty("file.separator") + ".config" + System.getProperty("file.separator") + "autostart" +
                   System.getProperty("file.separator") + "paySystem.desktop")))
//                  while (!(new File(System.getProperty("user.home") + System.getProperty("file.separator") + ".config" + System.getProperty("file.separator") + "autostart" +
           //                        System.getProperty("file.separator") + "paySystem.desktop")).delete())
           {System.out.println("delete "+"/home/oxana/.config/paySystem.desktop");}
           if(Filer.deleteFileInLinux(new File("/etc/paySystem.sh")))
           //while (!(new File("/etc/paySystem.sh")).delete())
           {System.out.println("delete "+"/etc/paySystem.sh");}
*/
//           Filer.clearAutoexecLinux("paySystem.sh");
           setUbuntuNoSleep();

       }catch(Exception ex){ex.printStackTrace();}
    }
}

