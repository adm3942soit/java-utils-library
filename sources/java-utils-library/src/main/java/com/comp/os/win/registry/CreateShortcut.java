package com.comp.os.win.registry;


import net.jimmc.jshortcut.JShellLink;
import com.comp.os.DetectorCompOS;

import java.io.File;


/**
 * Created by PROGRAMMER II on 20.03.2014.
 */
public class CreateShortcut {
    public static JShellLink link;

    public  String filePath;

    public CreateShortcut(String choosingPathFile, String choosingPathLib) {
            try {
                if(!DetectorCompOS.isWindows()) return;

                String env=System.getenv("JSHORTCUT_HOME");
                System.out.println(env);
     //           if(env==null || env.isEmpty() || !env.equals(choosingPathLib)) {
                    createVariableEnviroment("HKLM\\SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment",
                            "JSHORTCUT_HOME",
                            "",
                            false);

                    System.out.println("deleteVariableEnviroment");


                    createVariableEnviroment("HKLM\\SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment",
                            "JSHORTCUT_HOME",
                            choosingPathLib,
                            true);
                    System.setProperty("JSHORTCUT_HOME", choosingPathLib);
                    System.out.println("createVariableEnviroment" + choosingPathLib);
                    System.out.println("link = new JShellLink()");
                    try {
                        link = new JShellLink();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("link = new JShellLink()" + link);
                    System.out.println("filePath = " + choosingPathFile);
                    filePath = choosingPathFile;//"C:\\SpecGroup\\install\\paySystem\\paysystem.jar"
                    System.out.println("1filePath = " + choosingPathFile);
/*
                }else{
                    createVariableEnviroment("HKLM\\SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment",
                            "JSHORTCUT_HOME",
                            "",
                            false);

                    createVariableEnviroment("HKLM\\SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment",
                            "JSHORTCUT_HOME",
                            choosingPathLib,
                            true);

                    try {
                        link = new JShellLink();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("link = new JShellLink()" + link);
                    System.out.println("filePath = " + choosingPathFile);
                    filePath = choosingPathFile;//"C:\\SpecGroup\\install\\paySystem\\paysystem.jar"
                    System.out.println("1filePath = " + choosingPathFile);

                }
*/

            } catch (Exception e) {


        }
        }
    public static void createVariableEnviroment(String pathToVar,String nameVariable, String path, boolean create){
        String[] s=new String[11];
        File file=new File(path);
        ProcessBuilder pb;
        if( create )
        {
            //HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run
            s[0] = "cmd";
            s[1] = "/C ";
            s[2] = "reg";
            s[3] = "add";
            s[4] = pathToVar;
            s[5] = "/v";
            s[6] = nameVariable;
            s[7] = "/t";
            s[8] = "REG_SZ";
            s[9] = "/d";
            s[10] = "\""+file+"\"";


/*
            s = "cmd /C " + "reg add " +pathToVar+" /v " +
                    nameVariable + " /t REG_SZ /d " + "\"" + file + "\"";
*/
            pb = new ProcessBuilder(s[0],s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8],s[9],s[10]);
        }
        else
        {
            s[0] = "cmd";
            s[1] = "/C";
            s[2] = "reg";
            s[3] = "delete";
            s[4] = pathToVar;
            s[5] = "/v";
            s[6] = nameVariable;
            s[7] = "/f\r\n";

/*
            s = "cmd /C " + "reg delete "+pathToVar+" " +
                    "/v " + nameVariable + " /f\r\n";
*/
            pb = new ProcessBuilder(s[0],s[1],s[2],s[3],s[4],s[5],s[6],s[7]);
        }
        try{
            Process process=pb.start();
            process.waitFor();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    public static void writeVariableEnviroment(String pathToVar,String nameVariable, String value, boolean create){
        if(!DetectorCompOS.isWindows())return;
        String[] s=new String[11];

        ProcessBuilder pb;
        if( create )
        {
            //HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run
            s[0] = "cmd";
            s[1] = "/C ";
            s[2] = "reg";
            s[3] = "add";
            s[4] = pathToVar;
            s[5] = "/v";
            s[6] = nameVariable;
            s[7] = "/t";
            s[8] = "REG_SZ";
            s[9] = "/d";
            s[10] = "\""+value+"\"";
            System.out.println("value"+value);

/*
            s = "cmd /C " + "reg add " +pathToVar+" /v " +
                    nameVariable + " /t REG_SZ /d " + "\"" + file + "\"";
*/
            pb = new ProcessBuilder(s[0],s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8],s[9],s[10]);
        }
        else
        {
            s[0] = "cmd";
            s[1] = "/C";
            s[2] = "reg";
            s[3] = "delete";
            s[4] = pathToVar;
            s[5] = "/v";
            s[6] = nameVariable;
            s[7] = "/f\r\n";

/*
            s = "cmd /C " + "reg delete "+pathToVar+" " +
                    "/v " + nameVariable + " /f\r\n";
*/
            pb = new ProcessBuilder(s[0],s[1],s[2],s[3],s[4],s[5],s[6],s[7]);
        }
        try{
            Process process=pb.start();
            process.waitFor();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    //not checked
    public static String readRegistry(int where,String nameReg, String valueName){
        String value=null;
        try{

/*
            value = WinRegistry.readString(
                    WinRegistry.HKEY_LOCAL_MACHINE,                             //HKEY
                    "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",           //Key
                    valueName);//ValueName
*/
            value = WinRegistry.readString(
                    where,                             //HKEY
                    nameReg,           //Key
                    valueName);//ValueName

            System.out.println("Windows Distribution = " + value);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return value;
    }

    public boolean writeProgramIntoProgramFiles(String name){
        System.out.println(JShellLink.getDirectory("program_files"));
        File dirProgram=new File(JShellLink.getDirectory("program_files")+File.separator+name);
        link.setFolder(dirProgram.getAbsolutePath());
        link.setName(name);
        link.setPath(filePath);
        link.save();
/*
        dirProgram=new File(JShellLink.getDirectory("programs")+File.separator+name);
        link.setFolder(dirProgram.getAbsolutePath());
        link.setName(name);
        link.setPath(filePath);
        link.save();
*/
      return true;

    }
    public  void copyShortcutIntoStartup(String name){

        System.out.println(JShellLink.getDirectory("programs")+File.separator+"Startup");
        File dir=new File(JShellLink.getDirectory("programs")+File.separator+"Startup");
        if(dir.exists())
            link.setFolder(JShellLink.getDirectory("programs")+File.separator+"Startup");
        else{

            dir=new File(JShellLink.getDirectory("programs")+File.separator+"Автозагрузка");
            if(dir.exists())
             link.setFolder(dir.getAbsolutePath());
            else return;
        }
        link.setName(name);
        link.setPath(filePath);
        link.save();

/*      System.out.println(System.getProperty("user.home"));
        link.setFolder(System.getProperty("user.home")+File.separator+"AppData"+File.separator+"Roaming"+File.separator+"Microsoft"+File.separator+"Windows"+File.separator+"Start Menu"+File.separator+"Программы"+
                File.separator+"Startup"+File.separator);
        link.save();
*/


    }
    public void createDesktopShortcut(String name) {
        //if(!DetectorCompOS.isWindows()) return;
        try {
            System.out.println(JShellLink.getDirectory("desktop"));
            link.setFolder(JShellLink.getDirectory("desktop"));

            link.setName(name);

            link.setPath(filePath);

            link.save();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }
        public static void main(String[] args) {
        //    if(args==null)return;

//            CreateShortcut cr=new CreateShortcut("C:\\SpecGroup\\install\\paySystem\\paysystem.jar","C:\\SpecGroup\\install\\paySystem\\lib" );
/*
            cr.createDesktopShortcut("paysystem.jar");
            cr.copyShortcutIntoStartup("paysystem.jar");
*/
  //          cr.writeProgramIntoProgramFiles("paysystem");
            System.out.println(CreateShortcut.readRegistry(WinRegistry.HKEY_LOCAL_MACHINE,
                    "SYSTEM\\CURRENTCONTROLSET\\CONTROL\\CLASS\\{4D36E96D-E325-11CE-BFC1-08002BE10318}\\0000",
                    "UserInit"));

        }



}
