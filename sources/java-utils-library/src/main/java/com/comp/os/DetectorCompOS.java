package com.comp.os;


import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by PROGRAMMER II on 17.12.13.
 */
public class DetectorCompOS {
    private static final String REGQUERY_UTIL = "reg query ";
    private static final String REGSTR_TOKEN = "REG_SZ";
    private static final String REGDWORD_TOKEN = "REG_DWORD";

    private static final String PERSONAL_FOLDER_CMD = REGQUERY_UTIL +
            "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\"
            + "Explorer\\Shell Folders\" /v Personal";
    private static final String CPU_SPEED_CMD = REGQUERY_UTIL +
            "\"HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\""
            + " /v ~MHz";
    private static final String CPU_NAME_CMD = REGQUERY_UTIL +
            "\"HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\""
            + " /v ProcessorNameString";
    private static final String WIN_SERVICE_PACK_VERSION = REGQUERY_UTIL +
            "\"HKLM\\Software\\Microsoft\\Windows NT\\CurrentVersion\""
            + " /v CSDVersion";

    private static final String WIN_KEY = REGQUERY_UTIL +
            "\"HKLM\\Software\\Microsoft\\Windows NT\\CurrentVersion\""
            + " /v ProductId";

    //####################################################################
/*
    public static String[] getModemManufacturerModel(){
      String [] modems=new String [2];
      String nameModem="";
        if(isWindows()){
            nameModem=WinRegistryWrapper.WinRegQueryValueEx(WinRegistryWrapper.HKEY_LOCAL_MACHINE,
                                                          "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Unimodem\\DeviceSpecific",
                                                          "");
            modems=Registry.getSubKeys(Registry.REGISTRY_ROOT_KEY.LOCAL_MACHINE,
                    "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Unimodem\\DeviceSpecific");
            int i=0;
            while(modems!=null && i<modems.length){
              System.out.println(modems[i]);
             i++;
            }
        }


    return  modems;
    }
*/
    //####################################################################
/*
    public static String getKeyWindows(){
     String key="";
        if(isWindows()){
            key=WinRegistryWrapper.WinRegQueryValueEx(
                    WinRegistryWrapper.HKEY_LOCAL_MACHINE,
                    "SOFTWARE\\Microsoft\\Windows\\CurrentVersion",
                    "ProductId");
        }
     return key;
    }
*/
    //####################################################################
  public static String getOpSystem(){
   String operSystem=System.getProperty("os.name");
  return operSystem;
  }
    //####################################################################
    public static boolean isWindows(){

        String os = System.getProperty("os.name").toLowerCase();
        //windows
        return (os.indexOf( "win" ) >= 0);

    }
    public static boolean isWindows7(){

        String os = System.getProperty("os.name").toLowerCase();
        //windows
        return (os.indexOf( "win" ) >= 0 && os.indexOf( "7" ) >= 0) ;

    }
    public static boolean isWindowsXP(){

        String os = System.getProperty("os.name").toLowerCase();
        //windows
        return (os.indexOf( "win" ) >= 0 && os.indexOf( "XP" ) >= 0) ;

    }

    //####################################################################
    public static boolean isMac(){

        String os = System.getProperty("os.name").toLowerCase();
        //Mac
        return (os.indexOf( "mac" ) >= 0);

    }
    //####################################################################
    public static boolean isUnix (){

        String os = System.getProperty("os.name").toLowerCase();
        //linux or unix
        return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);

    }
    //####################################################################
    public static String getOSVerion() {
        String os = System.getProperty("os.version");
        return os;
    }
    //####################################################################
    public static String getMACAddress()
    {
    InetAddress ip;
    try {

        ip = InetAddress.getLocalHost();
        System.out.println("Current IP address : " + ip.getHostAddress());

        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

        byte[] mac = network.getHardwareAddress();
        if(mac!=null)
        {
        System.out.print("Current MAC address : ");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        System.out.println(sb.toString());
        return sb.toString();

        }else {
            System.out.print("Current MAC address : ");
          try{
            String command = "ipconfig /all";
            Process p = Runtime.getRuntime().exec(command);

            BufferedReader inn = new BufferedReader(new InputStreamReader(p.getInputStream()));
            Pattern pattern = Pattern.compile(".*Physical Addres.*: (.*)");

            while (true) {
                String line = inn.readLine();

                if (line == null)
                    break;
                Matcher mm = pattern.matcher(line);

                if (mm.matches()) {
                    System.out.println("!Current MAC address : ");
                    System.out.println(mm.group(1));
                    return mm.group(1);
                }
            }

          }catch (IOException ex){
              System.err.println(ex.getMessage());
          }
        }
    } catch (UnknownHostException e) {

        e.printStackTrace();

    } catch (SocketException e){

        e.printStackTrace();

    }


        return "";
    }

    public static String getServicePackVersion(){
     String value="";
        if(isWindows()){
         try {
            Process process = Runtime.getRuntime().exec(WIN_SERVICE_PACK_VERSION);
            StreamReader reader = new StreamReader(process.getInputStream());

            reader.start();
            process.waitFor();
            reader.join();

            String result = reader.getResult();
            int p = result.indexOf(REGSTR_TOKEN);

            if (p == -1)
                return null;

            return result.substring(p + REGSTR_TOKEN.length()).trim();
        }
        catch (Exception e) {
            return null;
        }

        }
     return value;

    }
    //WIN_KEY
    public static String getWindowsKey(){
      String key="";
        if(isWindows()){
            try {
                Process process = Runtime.getRuntime().exec(WIN_KEY);
                StreamReader reader = new StreamReader(process.getInputStream());

                reader.start();
                process.waitFor();
                reader.join();

                String result = reader.getResult();
                int p = result.indexOf(REGSTR_TOKEN);

                if (p == -1)
                    return null;

                return result.substring(p + REGSTR_TOKEN.length()).trim();
            }
            catch (Exception e) {
                return null;
            }

        }

    return key;
    }
    //####################################################################
   public static String getHardwareId(){
     StringBuffer hardwareId=new StringBuffer("");
     if(isWindows()){
     hardwareId.append("CPU Name : "+getCPUName()
            // +","+"CPU Speed : "+getCPUSpeed()+ " Mhz"
             +",");}
     hardwareId.append(getOpSystem()+", version: "+getOSVerion());
     if(isWindows())
     {
         hardwareId.append(","+getServicePackVersion());
         hardwareId.append(","+getWindowsKey());
     }
       hardwareId.append(","+NetworkUtils.getHostName());
      // hardwareId.append(","+NetworkUtils.getIP());
       hardwareId.append(","+NetworkUtils.getUserName());

 /*     if(isWindows()){
          hardwareId.append(","+HardWare4Win.getSerialNumber());
      }
      if(isUnix()){
          hardwareId.append(","+Hardware4Linux.getSerialNumber());
      }
*/
      // hardwareId.append(","+getMACAddress());
     return hardwareId.toString();
   }
    //####################################################################
    public static void main(String[] args) {
        if(isWindows()){
            System.out.println("This is Windows"+getOpSystem());
            System.out.println("It's version is: " + getOSVerion());
        }else if(isMac()){
            System.out.println("This is Macintosh");
            System.out.println("It's version is: " + getOSVerion());
        }else if(isUnix ()){
            System.out.println("This is Unix or Linux OS"+getOpSystem());
            System.out.println("It's version is: " + getOSVerion());
        }else{
            System.out.println("This is unknown OS");
        }
        System.out.println("Version: " + getOSVerion());

        System.out.println("Personal directory : "
                + getCurrentUserPersonalFolderPath());
        System.out.println("CPU Name : " + getCPUName());
        System.out.println("CPU Speed : " + getCPUSpeed() + " Mhz");
       // getModemManufacturerModel();
        System.out.println(getServicePackVersion());

        System.out.println(getWindowsKey());
        getMACAddress();
        getHardwareId();
/*
        System.out.println("CPUID "+CPUID.getCPUID());
        System.out.println("SerialNumberLogicDisk "+HDDID.getHddID());
*/
    }
//####################################################################
public static String getCurrentUserPersonalFolderPath() {
    try {
        Process process = Runtime.getRuntime().exec(PERSONAL_FOLDER_CMD);
        StreamReader reader = new StreamReader(process.getInputStream());

        reader.start();
        process.waitFor();
        reader.join();

        String result = reader.getResult();
        int p = result.indexOf(REGSTR_TOKEN);

        if (p == -1)
            return null;

        return result.substring(p + REGSTR_TOKEN.length()).trim();
    }
    catch (Exception e) {
        return null;
    }
}
    //####################################################################
    public static String getCPUSpeed() {
        try {
            Process process = Runtime.getRuntime().exec(CPU_SPEED_CMD);
            StreamReader reader = new StreamReader(process.getInputStream());

            reader.start();
            process.waitFor();
            reader.join();

            String result = reader.getResult();
            int p = result.indexOf(REGDWORD_TOKEN);

            if (p == -1)
                return null;

// CPU speed in Mhz (minus 1) in HEX notation, convert it to DEC
            String temp = result.substring(p + REGDWORD_TOKEN.length()).trim();
            return Integer.toString
                    ((Integer.parseInt(temp.substring("0x".length()), 16) + 1));
        }
        catch (Exception e) {
            return null;
        }
    }
    //####################################################################
    public static String getCPUName() {
        try {
            Process process = Runtime.getRuntime().exec(CPU_NAME_CMD);
            StreamReader reader = new StreamReader(process.getInputStream());

            reader.start();
            process.waitFor();
            reader.join();

            String result = reader.getResult();
            int p = result.indexOf(REGSTR_TOKEN);

            if (p == -1){
                return null;}

            return result.substring(p + REGSTR_TOKEN.length()).trim();
        }
        catch (Exception e) {
            return null;
        }
    }
    //####################################################################
    static class StreamReader extends Thread {
        private InputStream is;
        private StringWriter sw;

        StreamReader(InputStream is) {
            this.is = is;
            sw = new StringWriter();
        }

        public void run() {
            try {
                int c;
                while ((c = is.read()) != -1)
                    sw.write(c);
            }
            catch (IOException e) { ; }
        }

        String getResult() {
            return sw.toString();
        }
    }
//####################################################################

}
