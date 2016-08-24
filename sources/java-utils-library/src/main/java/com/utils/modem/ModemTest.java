package com.utils.modem;

import ua.edu.file.MyFiler;
import org.smslib.modem.SerialModemGateway;
import com.comp.os.DetectorCompOS;
import com.utils.archiv.ZipArchiv;
import com.utils.date.Dater;

import com.utils.workWithStr.Stringer;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

//import gnu.io.*;
//import processing.app.Preferences;

/**
 * Created by PROGRAMMER II on 09.01.14.
 */
public class ModemTest {
    public final static int[] persentSignalLevel={0,3,6,10,13,16,19,23,26,29,32,35,39,42,45,48,52,55,58,61,65,68,71,74,81,84,87,90,94,97,100};
    public static String NumberPid="";
   // private static Connect loadedJAXB=new Connect();
    public ModemTest() {
        File prt=new File("modem.prn");
//        try{
            /*Для перезагрузки модема с последующей перерегистрацией в сети оператора рекомендуется пользоваться командой

            AT+CFUN=1,1

            Первое число в этой команде означает режим, в который необходимо выйти модему после перезагрузки - в данном случае полная работоспособность. Существуют еще различные режимы сна, которые можно использовать для временного отключения модема. Для полного описания см. соответствующий документ.

            Второе число в этой команде - указание модему на необходимость перезагрузки. Единица - перезагрузить.
             Идентификация производителя: AT+CGMI
              Запрос на идентификацию модели: AT+CGMM
               текущий статус активност AT+CPAS
               ответы:
0–готово (возможны команды из TA/TE)
1–недоступно (невозможны команды)
2–неизвестно
3–дозвон (звонок активен)
4–в режиме соединения
5–в спящем режиме (сокращенный набор функций)
                  запросить регистрацию в местной сети АТ+COPS=0
            */

        //String [] test=new CommTest(System.out).testResponse1("АТ+CPAS\r");
        //    String [] test=new CommTest(System.out).testResponse1("AT+STGI = 1");
       // String [] test=new CommTest(System.out).testResponse1("АТ#CONNECTIONSTART");
        String [] test=new CommTest(System.out).testResponse1("АТ#CONNECTIONSTOP");

//        String [] test=new CommTest(System.out).testResponse1("AT+CIMI");//AT+CPIN?");
/*

            int level= ua.com.bpgroup.ps.terminal.usbPort.CommTest.getLevelSignal("COM1", 115200, "Pantech", "UM190");
            System.out.println("Level signal" +level);
*/
            //TabPeripherialModel.getInstance().btnTestModem();

/*
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
*/

    }

    public static int signalLevelPersentGetFromRSSI(Float RSSI){
       int i=RSSI.intValue();
       if(i<0)return 0;
       if(i>=31)return 100;
       return persentSignalLevel[i];
    }
    public static boolean modemConnectionUp(){
       String [] test=new CommTest(System.out).testResponse1("АТ+CPAS\r");
      // int level= CommTest.getLevelSignal(CommTest.NameModemComPort, 115200, "Pantech", "UM190");

       if(CommTest.TestModemOk && CommTest.ModemResponse.contains("+CPAS: 0"))return true;
       if(CommTest.sendCommand("ATO")&& CommTest.ModemResponse.contains("CONNECT"))return true;
       if(CommTest.ModemResponse.contains("NO CARRIER"))
        {
           // if(CommTest.sendCommand("АТ#CONNECTIONSTART") && CommTest.ModemResponse.contains("OK"))return true;
/*
            CommTest.sendCommand("AT+CREG=1"); //отчет о регистрации
            CommTest.sendCommand("AT+CFUN=1"); //включить весь набор функций мобильного устройства\n" +
            CommTest.sendCommand("AT+COPS=0");// запросить автоматический выбор оператора и регистрацию
            CommTest.sendCommand("AT+COPS?");// получить название оператора
*/
           //!!!! CommTest.sendCommand("ATD*43*BS#");
            CommTest.sendCommand("AT+CGMI");  //Данная команда идентифицирует производителя

            if(!CommTest.TestModemOk)return false;
            String[] manufacturer= CommTest.ModemResponse.split("OK");

            CommTest.sendCommand("AT+CGMM");  //Данная команда идентифицирует model
            if(!CommTest.TestModemOk)return false;
            String[] model= CommTest.ModemResponse.split("OK");
            SerialModemGateway gateway = CommTest.getGateway(CommTest.NameModemComPort, CommTest.bauds[CommTest.bauds.length - 1], manufacturer[0].trim(), model[0].trim());
            if(CommTest.TestModemOk &&
                    (CommTest.ModemResponse.contains("+CPAS: 0")
                     ||
                            CommTest.ModemResponse.contains("+CPAS: 3")
                     ||
                            CommTest.ModemResponse.contains("+CPAS: 4")
                    )

                    )return true;
        }
        System.out.println(CommTest.ModemResponse);
     return false;
    }
    public static  void getNamesConnnections(){
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (interfaces.hasMoreElements()) {
            NetworkInterface nic = interfaces.nextElement();

            System.out.print("Interface Name : [" + nic.getDisplayName() + "]");
            try {
                System.out.println(", Is connected : [" + nic.isUp() + "]");
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }
    public static final String nameFileZip= "backupLogConnect.zip";

    public static final String nameFileConnectLog="logConnect";
    public static boolean connect(String nameInternet)
    {
        if(!DetectorCompOS.isWindows())return false;
     try{

        ZipArchiv.archivLog(nameFileZip, nameFileConnectLog);

        ProcessBuilder processBuilder=new ProcessBuilder("cmd","/C", "rasdial.exe", nameInternet);

        processBuilder.directory(new File(MyFiler.getCurrentDirectory()));//+File.separator+nameFolderForZip));
        String nameFileLogTime= nameFileConnectLog+"_"+ Dater.getDateForLog1()+".txt";
        File log = new File(nameFileLogTime);

        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        assert processBuilder.redirectOutput().file() == log;

        Process process=processBuilder.start();

        InputStream is = process.getInputStream();
        //InputStreamReader reader=new InputStreamReader(is, "DOS");

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
        process.waitFor();
        System.out.println("process.exitValue()"+process.exitValue());
        return process.exitValue()==0;
     }catch (Exception ex){
       System.err.println(ex.getMessage());
     }
    return false;
    }
/*
    public static boolean disconnect(){
        if(DetectorCompOS.isWindows()) {
            String nameInternet = getNameInternetConnection();
            System.out.println(nameInternet);
            if (!nameInternet.isEmpty())
                return disconnect(nameInternet);
        }
        if(DetectorCompOS.isUnix()){
            return disconnectInUnix();//connectInUnix(false);
        }

        return false;
    }
*/
    public static boolean disconnect(String nameInternet)
    {
        try{
            System.out.println("!!!!!!!!");
            ZipArchiv.archivLog(nameFileZip, nameFileConnectLog);
            System.out.println("!!!!!!!!");
            ProcessBuilder processBuilder=new ProcessBuilder("cmd","/C", "rasdial.exe", nameInternet, "/DISCONNECT");

            processBuilder.directory(new File(MyFiler.getCurrentDirectory()));//+File.separator+nameFolderForZip));
            String nameFileLogTime= nameFileConnectLog+"_"+ Dater.getDateForLog1()+".txt";
            File log = new File(nameFileLogTime);

            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
            assert processBuilder.redirectOutput().file() == log;

            Process process=processBuilder.start();

            InputStream is = process.getInputStream();
            //InputStreamReader reader=new InputStreamReader(is, "DOS");

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
            process.waitFor();
            System.out.println("disconnect process.exitValue()"+process.exitValue());
           // NetError=process.exitValue();

            return process.exitValue()==0;
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public static boolean dialConnect()
    {
        try{
            if(!DetectorCompOS.isWindows()) return false;
            ZipArchiv.archivLog(nameFileZip, nameFileConnectLog);

            ProcessBuilder processBuilder=new ProcessBuilder("cmd","/C", "rasphone.exe");

            processBuilder.directory(new File(MyFiler.getCurrentDirectory()));//+File.separator+nameFolderForZip));
            String nameFileLogTime= nameFileConnectLog+"_"+ Dater.getDateForLog1()+".txt";
            File log = new File(nameFileLogTime);

            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
            assert processBuilder.redirectOutput().file() == log;

            Process process=processBuilder.start();

            InputStream is = process.getInputStream();
            //InputStreamReader reader=new InputStreamReader(is, "DOS");

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
            process.waitFor();
            System.out.println("process.exitValue()"+process.exitValue());
            return process.exitValue()==0;
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return false;
    }


/*
    public static String getNameInternetConnection(){
        try{
            System.out.println(MyFiler.getCurrentDirectory() + File.separator + Connect.fileConnectXML );
            loadedJAXB=loadedJAXB.load(MyFiler.getCurrentDirectory() + File.separator + Connect.fileConnectXML);
        }catch (Exception ex){
           System.err.println(MyFiler.getCurrentDirectory()+File.separator+ Connect.fileConnectXML);
          return "";
        }
        return loadedJAXB!=null?loadedJAXB.getName():"";
    }
*/

/*
    public static boolean reconnect(){
      if(DetectorCompOS.isWindows()) {
          String nameInternet = getNameInternetConnection();
          System.out.println("nameInternet" + nameInternet);

          if (!nameInternet.isEmpty())
              return connect(nameInternet);
          //check  rasphone.exe
      }
      if(DetectorCompOS.isUnix()){
          return connectInUnix();
      }

     return false;
    }
*/
    public static boolean dialReconnect(){
            return   dialConnect();
    }
    public static boolean wifiInUnix(){
/*
        /usr/bin/netctl
        sudo netctl start home
        sudo poweroff
        Cmnd_Alias  POWER       =   /usr/bin/shutdown -h now, /usr/bin/halt, /usr/bin/poweroff, /usr/bin/reboot
*/
        return false;
    }
    public static boolean connectInUnix(boolean start)
    {
        if(DetectorCompOS.isWindows())return false;
        try{
            //passsword off
            //sudo visudo
            //Defaults:USER_NAME      !authenticate
            ZipArchiv.archivLog(nameFileZip, nameFileConnectLog);

            Process process;
            System.out.println("sudo ALL=NOPASSWD:ALL cd /");
            process=Runtime.getRuntime().exec("sudo cd /etc/init.d");
            process.waitFor();

            System.out.println("sudo ALL=NOPASSWD:ALL service");
            ///etc/init.d/networking restart
            ProcessBuilder processBuilder=new ProcessBuilder("sudo","service","networking",start?"start":"stop");

            processBuilder.directory(new File(MyFiler.getCurrentDirectory()));//+File.separator+nameFolderForZip));
            String nameFileLogTime= nameFileConnectLog+"_"+ Dater.getDateForLog1()+".txt";
            File log = new File(nameFileLogTime);

            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
            assert processBuilder.redirectOutput().file() == log;

            process=processBuilder.start();

            InputStream is = process.getInputStream();
            //InputStreamReader reader=new InputStreamReader(is, "DOS");

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
            process.waitFor();
            System.out.println("process.exitValue()"+process.exitValue());
            return process.exitValue()==0;
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return false;
    }
public static boolean connectInUnix(){
    if(DetectorCompOS.isWindows())return false;
    try {

        NumberPid=getLastNumberPidFromFile();
        if(!(NumberPid.isEmpty() || NumberPid.equals("-1"))){
            return true;
        }

        String startTime= Dater.getTime();
        System.out.println("startTime"+startTime);

        ZipArchiv.archivLog(nameFileZip, nameFileConnectLog);

        //Process process = Runtime.getRuntime().exec("sudo wvdial");
        ProcessBuilder processBuilder=new ProcessBuilder("sudo", "wvdial");
        processBuilder.directory(new File(MyFiler.getCurrentDirectory()));//+File.separator+nameFolderForZip));
        String nameFileLogTime= nameFileConnectLog+"_"+ Dater.getDateForLog1()+".txt";
        File log = new File(nameFileLogTime);

        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        assert processBuilder.redirectOutput().file() == log;

        Process process=processBuilder.start();

        InputStream is = process.getInputStream();
        //InputStreamReader reader=new InputStreamReader(is, "DOS");
        StringBuffer strFile=new StringBuffer("");
        int in = -1;
        while ((in = is.read()) != -1) {
            System.out.print("InputStream");

            System.out.print((char) in);
            strFile.append((char) in);

        }
        is = process.getErrorStream();
        in = -1;
        while ((in = is.read()) != -1) {
            System.out.print("ErrorStream");
            System.out.print((char) in);
            //strFile.append((char) in);
        }

        //process.waitFor();
        getNumberPidFromFile(startTime);

        if(NumberPid.isEmpty() || NumberPid.equals("-1"))System.out.println("internet unsuccessfull" );
        else System.out.println("internet successfull id " + NumberPid);

        int codeExit=-1;
        try {
            codeExit=process.exitValue();
        }catch(IllegalThreadStateException ex){
            System.err.println("exit Value"+codeExit);
        }
       return (codeExit==0?true:false);
    }catch(Exception ex){ex.printStackTrace();}
    return false;
}

    private static String getNumberPidFromFile(String startTime){

        String nameFileLogTime= nameFileConnectLog+"_"+ Dater.getDateForLog1()+".txt";
        try {
            File file=new File(nameFileLogTime);
            if(!file.exists()){
                NumberPid="-1";return NumberPid;
            }

            FileReader fileReader= new FileReader(file);
            StringBuffer strFile= new StringBuffer("");
            int smb=-1;
            while( (smb=fileReader.read())!=-1){
                strFile.append((char)smb);
            }
            String[] start=strFile.toString().split("Starting pppd at ");
            System.out.println("start.length"+start.length);
            if(start==null || start.length==0){
                NumberPid="-1";return NumberPid;
            }
            int index=start[start.length-1].indexOf(":");
            if(index==-1){
                NumberPid="-1";return NumberPid;
            }
            String timeStr=start[start.length-1].substring(index-2,index-2+9);
            System.out.println("timeStr"+timeStr);
            if(Dater.timeGreater(startTime,timeStr)){
                System.out.println("Dater.timeGreater(startTime,timeStr)"+Dater.timeGreater(startTime,timeStr));
                NumberPid="-1";
                return NumberPid;
            }
            System.out.println("!Dater.timeGreater(startTime,timeStr)"+Dater.timeGreater(startTime,timeStr));
            String[]ss=strFile.toString().split("Pid of pppd:");
            if(ss==null || ss.length==0){
                NumberPid="-1";return NumberPid;
            }

            if(ss!=null && ss.length>0){

                String strWithNumberPid=ss[ss.length-1].trim();
                String strWithNumberPidResult= Stringer.trimFromBothSide(strWithNumberPid);//"";
/*
                String strWithNumberPidResult="";
                int i=0;
                while(strWithNumberPid.charAt(i)==' '){
                    i++;
                }
                strWithNumberPidResult=(i>0?strWithNumberPid.substring(i):strWithNumberPid);
*/
                System.out.println("strWithNumberPidResult "+strWithNumberPidResult+"!");
                String[] sss=strWithNumberPidResult.split(" ");
                System.out.println("sss "+sss+"!"+sss.length);
                if(sss!=null && sss.length>0 && strWithNumberPidResult.indexOf("Connected")!=-1)
                    NumberPid=sss[0];

                //check if exist disconnecting

                if(strWithNumberPidResult.indexOf("Disconnecting")!=-1)NumberPid="-1";
            }
        }catch (Exception ex ){ex.printStackTrace();}
        System.out.println("NumberPid"+NumberPid);
      return NumberPid;
    }
    private static String getLastNumberPidFromFile(){

        String nameFileLogTime= nameFileConnectLog+"_"+ Dater.getDateForLog1()+".txt";
        try {
            File file=new File(nameFileLogTime);
            if(!file.exists()){
                NumberPid="-1";return NumberPid;
            }
            FileReader fileReader= new FileReader(file);
            StringBuffer strFile= new StringBuffer("");
            int smb=-1;
            while( (smb=fileReader.read())!=-1){
                strFile.append((char)smb);
            }
            String[] start=strFile.toString().split("Starting pppd at ");
            System.out.println("start.length"+start.length);
            if(start==null || start.length==0){
                NumberPid="-1";return NumberPid;
            }
            int index=start[start.length-1].indexOf(":");
            if(index==-1){
                NumberPid="-1";return NumberPid;
            }
            String timeStr=start[start.length-1].substring(index-2,index-2+9);
            System.out.println("timeStr"+timeStr);
/*
            if(Dater.timeGreater(startTime,timeStr)){
                System.out.println("Dater.timeGreater(startTime,timeStr)"+Dater.timeGreater(startTime,timeStr));
                NumberPid="-1";
                return NumberPid;
            }
            System.out.println("!Dater.timeGreater(startTime,timeStr)"+Dater.timeGreater(startTime,timeStr));
*/
            ////String[]ss=strFile.toString().split("Pid of pppd:");
            String[]ss=start[start.length-1].split("Pid of pppd:");
            if(ss==null || ss.length==0){
                NumberPid="-1";return NumberPid;
            }

            if(ss!=null && ss.length>0){

                String strWithNumberPid=ss[ss.length-1].trim();

                String strWithNumberPidResult= Stringer.trimFromBothSide(strWithNumberPid);//"";
/*
                int i=0;
                while(strWithNumberPid.charAt(i)==' '){
                    i++;
                }
                strWithNumberPidResult=(i>0?strWithNumberPid.substring(i):strWithNumberPid);
*/
                System.out.println("strWithNumberPidResult "+strWithNumberPidResult+"!");
                String[] sss=strWithNumberPidResult.split(" ");
                System.out.println("sss "+sss+"!"+sss.length);
                if(sss!=null && sss.length>0 && strWithNumberPidResult.indexOf("Connected")!=-1)
                    NumberPid=sss[0];

                //check if exist disconnecting

                if(strWithNumberPidResult.indexOf("Disconnecting")!=-1)NumberPid="-1";
            }
        }catch (Exception ex ){ex.printStackTrace();}
        System.out.println("last NumberPid"+NumberPid);
        return NumberPid;
    }

    public static boolean disconnectInUnix(){

        if(NumberPid.isEmpty() || NumberPid.equals("-1")){
            NumberPid=getLastNumberPidFromFile();
            if(NumberPid.isEmpty() || NumberPid.equals("-1"))return true;
        }
        ZipArchiv.archivLog(nameFileZip, nameFileConnectLog);
        //ProcessBuilder processBuilder=new ProcessBuilder("echo","sudo","killall","-TERM","modem-manager");//NumberPid,
        ProcessBuilder processBuilder=new ProcessBuilder("sudo","killall","wvdial");//NumberPid,

        processBuilder.directory(new File(MyFiler.getCurrentDirectory()));//+File.separator+nameFolderForZip));
        String nameFileLogTime= nameFileConnectLog+"_"+ Dater.getDateForLog1()+".txt";
        File log = new File(nameFileLogTime);

        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        assert processBuilder.redirectOutput().file() == log;
        Process process=null;
        try {

            process=processBuilder.start();

            InputStream is = process.getInputStream();
            //InputStreamReader reader=new InputStreamReader(is, "DOS");

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
            process.waitFor();
        }catch(Exception ex){ex.printStackTrace();}

        int codeExit=process.exitValue();
        System.out.println("process.exitValue()"+codeExit);
        if(codeExit==0)NumberPid="-1";
        return (process==null?false:codeExit==0);
    }

    public static void main(String[] args) {
        //new ModemTest();
/*
        boolean up=modemConnectionUp();
        System.out.println(CommTest.ModemResponse);
        System.out.println("\n modem up "+up);
*/

/*
        getNamesConnnections();
*/
/*
     if(DetectorCompOS.isWindows()) {
         String nameInternet = getNameInternetConnection();
         System.out.println(nameInternet);
         if (!nameInternet.isEmpty())
             connect(nameInternet);
     }
     if(DetectorCompOS.isUnix()) {
         boolean con = connectInUnix();
         System.out.println("connected "+con);

        con=disconnectInUnix();
        System.out.println("Disconnected "+con);


     }
*/
      ////  getBalanceInDigit();
    }
    public static String getModemManufacturer(){
        CommTest.sendCommand("AT+CGMI");  //Данная команда идентифицирует производителя
        if(!CommTest.TestModemOk)return "";
        String[] manufacturer=CommTest.ModemResponse.split("\n\r");
        //if(manufacturer.length>0)manufacturer=manufacturer[0].split("\r");
        int i=0;
        while(i<manufacturer.length){
            System.out.println("!"+manufacturer[i]);
            if(!manufacturer[i].isEmpty()) break;
            i++;}
        return manufacturer.length>0?Stringer.trunkLeaderMes("AT+CGMI",manufacturer[i].trim()):"";
    }
    public static String getModemModel(){
        CommTest.sendCommand("AT+CGMM");  //Данная команда идентифицирует производителя
        if(!CommTest.TestModemOk)return "";
        String[] model=CommTest.ModemResponse.split("\n\r");
        //if(model.length>0)model=model[0].split("\r");
        int i=0;
        while(i<model.length){System.out.println("!"+model[i]);
            if(!model[i].isEmpty()) break;
            i++;}
        return model.length>0? Stringer.trunkLeaderMes("AT+CGMM",model[i].trim()):"";
    }
/*
    private static String runUSSD(String str){
        ModemAP modem = new LoadConfig().modemLoadState();
        try {
            modem.sendToModem(str);

            modem.closeModem();
        } catch (Exception e) {
            return modem.error;
        }
        return modem.response;
    }
*/

/*
    public static String getBalanceInDigit(){
        String balance=runUSSD("AT+CUSD=1,*100#,15\r");
        System.out.println(balance);
        int index=balance.indexOf("Vash balans");
        if(index==-1)return "";
        balance=balance.substring(index);
        int i=0;
        StringBuffer   digits=new StringBuffer("");
        while(i<balance.length()){
            if(new Character(balance.charAt(i)).isDigit(balance.charAt(i))){
                digits.append(balance.charAt(i));
            }else {
                if (!digits.toString().isEmpty() && balance.charAt(i)!=',') break;
                if(balance.charAt(i)==',')digits.append(".");

            }
            i++;

        }
        balance=digits.toString();
        System.out.println(balance);
    return balance;
    }
*/

}
