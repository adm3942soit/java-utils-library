package com.utils.modem;

/**
 * Created with IntelliJ IDEA.
 * User: PROGRAMMER II
 * Dater: 03.12.13
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */

import org.smslib.*;
import org.smslib.helper.CommPortIdentifier;
import org.smslib.helper.SerialPort;
import org.smslib.modem.SerialModemGateway;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Formatter;

public class CommTest
{
    public static Boolean TestModemOk=false;
    public static String NameModemComPort="";
    public static String ModemResponse="";
    private static final String _NO_DEVICE_FOUND = "  no device found";

    private static Formatter _formatter = new Formatter(System.out);

    static CommPortIdentifier portId;

    static Enumeration<?> portList;

    public static int bauds[] = { 9600, 14400, 19200, 28800, 33600, 38400, 56000, 57600, 115200 };

    public CommTest(PrintStream printStream) {
        _formatter = new Formatter(printStream!=null?printStream:System.out);
    }

    private static Enumeration<?> getCleanPortIdentifiers()
    {
        return CommPortIdentifier.getPortIdentifiers();
    }
    private static String[] strResponse= new String[10];

    public static String[] testResponse(String messToModem)
    {
        NameModemComPort="";
        _formatter.format("\nSearching for devices...");
        portList = getCleanPortIdentifiers();
       // _formatter.format("Ports not found!");
        int numberPort=0;
        boolean ok=false;
        while (ok=portList.hasMoreElements())
        {
            _formatter.format(ok?"\nFound ports...":"Ports not found!");
            portId = (CommPortIdentifier)portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                _formatter.format("%nFound port: %-5s%n", portId.getName());
/*
                for (int i = 0; i < bauds.length; i++)
                {
*/
                   int i=8;
                    SerialPort serialPort = null;
                    _formatter.format("       Trying at %6d...", bauds[i]);
                    try
                    {
                        InputStream inStream;
                        OutputStream outStream;
                        int c;
                        String response;
                        serialPort = portId.open("SMSLibCommTester", 1971);
                        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
                        serialPort.setSerialPortParams(bauds[i], SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                        inStream = serialPort.getInputStream();
                        outStream = serialPort.getOutputStream();
                        serialPort.enableReceiveTimeout(1000);
                        c = inStream.read();
                        while (c != -1)
                            c = inStream.read();
                        outStream.write('A');
                        outStream.write('T');
                        outStream.write('\r');
                        Thread.sleep(1000);
                        response = "";
                        StringBuilder sb = new StringBuilder();
                        c = inStream.read();
                        while (c != -1)
                        {
                            sb.append((char) c);
                            c = inStream.read();
                        }
                        response = sb.toString();
                        TestModemOk=false;
                        if (response.indexOf("OK") >= 0)
                        {
                            try
                            {
                                _formatter.format("  Getting Info...");
                                outStream.write('A');
                                outStream.write('T');
                                outStream.write('+');
                                for (int j=0;j<messToModem.length();j++)
                                {
                                //outStream.write('C');
                                //outStream.write('G');
                                //outStream.write('M');
                                //outStream.write('M');
                                 outStream.write(messToModem.charAt(j));
                                }
                                outStream.write('\r');
                                response = "";
                                c = inStream.read();
                                while (c != -1)
                                {
                                    response += (char) c;
                                    c = inStream.read();
                                }
                                strResponse[numberPort]=response.replaceAll("\\s+OK\\s+", "").replaceAll("\n", "").replaceAll("\r", "");

                                _formatter.format(" Response: " + strResponse[numberPort]);
                                TestModemOk=true;
                                NameModemComPort=portId.getName();
                                ModemResponse=strResponse[numberPort];
                                if(!strResponse[numberPort].isEmpty())break;

                            }
                            catch (Exception e)
                            {
                                _formatter.format(_NO_DEVICE_FOUND);
                            }
                        }
                        else
                        {
                            if (response.indexOf("ERROR") >= 0){
                                _formatter.format("Response:"+response);
                                ModemResponse=response;
                            }else _formatter.format(_NO_DEVICE_FOUND);
                        }
                    }
                    catch (Exception e)
                    {
                        _formatter.format(_NO_DEVICE_FOUND);
                        Throwable cause = e;
                        while (cause.getCause() != null)
                        {
                            cause = cause.getCause();
                        }
                        _formatter.format(" (" + cause.getMessage() + ")");
                    }
                    finally
                    {
                        if (serialPort != null)
                        {
                            serialPort.close();
                        }
                    }

                //}
            }
            numberPort++;
            if(TestModemOk)break;
        }
        _formatter.format("\nTest complete.");

        return strResponse;
    }
    public static String[] testResponse1(String messToModem)
    {
        NameModemComPort="";
        _formatter.format("\nSearching for devices...");
        portList = getCleanPortIdentifiers();
        //_formatter.format("Ports not found!");
        int numberPort=0;
        boolean ok=false;
        while (ok=portList.hasMoreElements())
        {
            _formatter.format(ok?"\nFound ports...":"Ports not found!");
            portId = (CommPortIdentifier)portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                _formatter.format("%nFound port: %-5s%n", portId.getName());

                for (int i = 0; i < bauds.length; i++)
                {

                //int i=8;
                SerialPort serialPort = null;
                _formatter.format("\n Trying at %6d...", bauds[i]);
                try
                {
                    InputStream inStream;
                    OutputStream outStream;
                    int c;
                    String response;
                    serialPort = portId.open("SMSLibCommTester", 1971);
                    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
                    serialPort.setSerialPortParams(bauds[i], SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    inStream = serialPort.getInputStream();
                    outStream = serialPort.getOutputStream();
                    serialPort.enableReceiveTimeout(1000);
                    TestModemOk=false;
                    c = inStream.read();
                    while (c != -1)
                        c = inStream.read();
                    outStream.write('A');
                    outStream.write('T');
                    outStream.write('\r');
                    Thread.sleep(1000);
                    response = "";
                    StringBuilder sb = new StringBuilder();
                    c = inStream.read();
                    while (c != -1)
                    {
                        sb.append((char) c);
                        c = inStream.read();
                    }
                    response = sb.toString();
                    if (response.indexOf("OK") >= 0)
                    {
                        try
                        {
                            _formatter.format("  Getting Info...");
/*
                            outStream.write('A');
                            outStream.write('T');
                            outStream.write('+');
*/
                            for (int j=0;j<messToModem.length();j++)
                            {
                                //outStream.write('C');
                                //outStream.write('G');
                                //outStream.write('M');
                                //outStream.write('M');
                                outStream.write(messToModem.charAt(j));
                            }
                            outStream.write('\r');
                            response = "";
                            c = inStream.read();
                            while (c != -1)
                            {
                                response += (char) c;
                                c = inStream.read();
                            }
                            strResponse[numberPort]=response;//.replaceAll("\\s+OK\\s+", "").replaceAll("\n", "").replaceAll("\r", "");

                            _formatter.format(" Response: " + strResponse[numberPort]);
                            TestModemOk=true;
                            ModemResponse=response;
                            NameModemComPort=portId.getName();
                            break;

                        }
                        catch (Exception e)
                        {
                            _formatter.format(_NO_DEVICE_FOUND);
                        }
                    }
                    else
                    {
                        if (response.indexOf("ERROR") >= 0){
                            TestModemOk=false;
                            _formatter.format("Response(error):"+response);
                            ModemResponse=response;
                        }else {
                            if(response.indexOf("BUSY") >= 0){
                                ModemResponse=response;
                                _formatter.format("Response(busy!):"+response);
                                NameModemComPort=portId.getName();
                            }else
                            _formatter.format(_NO_DEVICE_FOUND);}
                    }

                }
                catch (Exception e)
                {

                    Throwable cause = e;
                    if(cause.getMessage().contains("PortInUse")){
                        _formatter.format("Port in usage...");
                        _formatter.format(" (" + cause.getMessage() + ")");
                        NameModemComPort=portId.getName();

                    }
                    else{
                    _formatter.format(_NO_DEVICE_FOUND);
                    while (cause.getCause() != null)
                    {
                        cause = cause.getCause();
                    }
                    _formatter.format(" (" + cause.getMessage() + ")");
                    }
                }
                finally
                {
                    if (serialPort != null)
                    {
                        serialPort.close();
                    }
                }

                }
                if(TestModemOk)break;
            }
            numberPort++;
            if(TestModemOk)break;
        }
        _formatter.format("\nTest complete.");

        return strResponse;
    }
/*
    private static void runStr(String comPort,String str){
        ModemAP modem = new LoadConfig().modemLoadStateWithoutInit();
        modem.setPortComName(comPort);
        modem.initModem();

        System.out.println(modem.getPortComName()+"!!!!!");
        try {
            modem.sendToModem(str);
            modem.closeModem();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!modem.response.isEmpty()){
            TestModemOk=true;
            NameModemComPort=comPort;
            ModemResponse=modem.response;
            System.out.println("ModemResponse : "+ModemResponse);

        }
        if(!modem.error.isEmpty() && !NameModemComPort.equals(comPort)){
            NameModemComPort=comPort;
            ModemResponse=modem.error;
        }

    }
    private static void runStr(String comPort,String str,int time){
        ModemAP modem = new LoadConfig().modemLoadStateWithoutInit();
        modem.setPortComName(comPort);
        modem.initModem();

        System.out.println(modem.getPortComName()+"!!!!!");
        try {
            modem.sendToModem(str);
            modem.closeModem(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!modem.response.isEmpty()){
            TestModemOk=true;
            NameModemComPort=comPort;
            ModemResponse=modem.response;
            System.out.println("ModemResponse : "+ModemResponse);

        }
        if(!modem.error.isEmpty() && !NameModemComPort.equals(comPort)){
            NameModemComPort=comPort;
            ModemResponse=modem.error;
        }

    }
*/

    private static String con="CONNECT";

    public static String getNameModemComPort(){
        NameModemComPort="";
        ModemResponse="";
        TestModemOk=false;
        try
        {
            Class.forName("com.sun.comm.Win32Driver");
        } catch (ClassNotFoundException e1)
        {
            System.out.println("Exception: "+e1.getMessage());
        }

        Enumeration portList1 = getCleanPortIdentifiers();
        _formatter.format("%nFound ports ");

        while (portList1.hasMoreElements())
        {
            portId = (CommPortIdentifier)portList1.nextElement();
            _formatter.format("%nFound port: %-5s%n", portId.getName());
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {

                NameModemComPort=portId.getName();
                //sendCommand("+++");
                sendCommand("ATDT*99#");
                System.out.println("\nModemResponse!"+ModemResponse+"!");
                if(ModemResponse.equals(""))continue;
                else
                if(ModemResponse.indexOf(con)!=-1){
                    System.out.println("indexOf="+ModemResponse.indexOf(con));
                    // NameModemComPort=portId.getName();
                    _formatter.format("%n!port: %-5s%n", NameModemComPort);
                    return NameModemComPort;
                }
            }

        }

        return "";
    }


/*
    public static String testResponseJSSC(String messToModem, int time)
    {
        NameModemComPort="";
        TestModemOk=false;
        ModemResponse="";
        _formatter.format("\nSearching for devices...");

       String[]  portListString = SerialPortList.getPortNames();

        int numberPort=0;
        for(;numberPort < portListString.length;numberPort++)
        {
            if(!portListString[numberPort].contains("COM1"))
            {
                _formatter.format("%nFound port: %-5s%n", portListString[numberPort]);
                runStr(portListString[numberPort],messToModem.equals("")?"AT+CGMM\r":messToModem, time);
                if(TestModemOk){NameModemComPort=portListString[numberPort];break;}
            }
        }
        _formatter.format("\nTest complete.");
        _formatter.format("\nModemResponse : "+ModemResponse);
        return ModemResponse;
    }
*/
/*
    public static String testResponseJSSC(String portName, String messToModem, int time)
    {
        NameModemComPort=portName;
        TestModemOk=false;
        ModemResponse="";
        _formatter.format("\nSearching for devices...");

*/
/*
        portListString = SerialPortList.getPortNames();

        int numberPort=0;
        for(;numberPort < portListString.length;numberPort++)
        {

            _formatter.format("%nFound port: %-5s%n", portListString[numberPort]);

            if(TestModemOk){NameModemComPort=portListString[numberPort];break;}
        }
*//*

        runStr(portName,messToModem.equals("")?"AT+CGMM\r":messToModem, time);
        _formatter.format("\nTest complete.");
        _formatter.format("\nModemResponse : "+ModemResponse);
        return ModemResponse;
    }
*/

    public static Boolean sendCommand(String command){
        _formatter.format("\nSearching port...");
        portList = getCleanPortIdentifiers();
        if(portList==null )return false;
        while(portList.hasMoreElements())
        {
            portId = (CommPortIdentifier)portList.nextElement();
            if (!portId.getName().equals(NameModemComPort)) continue;
            _formatter.format("%nFound port: %-5s%n", portId.getName());

            for (int i = 0; i < bauds.length; i++)
            {
                SerialPort serialPort = null;
                _formatter.format("\n Trying at %6d...", bauds[i]);
                try
                {
                    InputStream inStream;
                    OutputStream outStream;
                    int c;
                    String response;
                    serialPort = portId.open("SMSLibCommTester", 1971);
                    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
                    serialPort.setSerialPortParams(bauds[i], SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    inStream = serialPort.getInputStream();
                    outStream = serialPort.getOutputStream();
                    serialPort.enableReceiveTimeout(1000);
                    c = inStream.read();
                    while (c != -1)
                        c = inStream.read();
                    outStream.write('A');
                    outStream.write('T');
                    outStream.write('\r');
                    Thread.sleep(1000);
                    response = "";
                    StringBuilder sb = new StringBuilder();
                    c = inStream.read();
                    while (c != -1)
                    {
                        sb.append((char) c);
                        c = inStream.read();
                    }
                    response = sb.toString();
                    if (response.indexOf("OK") >= 0)
                    {
                        try
                        {
                            _formatter.format("  Getting Info...");
/*
                            outStream.write('A');
                            outStream.write('T');
                            outStream.write('+');
*/
                            for (int j=0;j<command.length();j++)
                            {
                                //outStream.write('C');
                                //outStream.write('G');
                                //outStream.write('M');
                                //outStream.write('M');
                                outStream.write(command.charAt(j));
                            }
                            outStream.write('\r');
                            response = "";
                            c = inStream.read();
                            while (c != -1)
                            {
                                response += (char) c;
                                c = inStream.read();
                            }
                            _formatter.format(" Response: " + response);
                            ModemResponse=response;
                            TestModemOk=true;
                            NameModemComPort=portId.getName();

                        }
                        catch (Exception e)
                        {
                            _formatter.format(_NO_DEVICE_FOUND);
                        }
                    }
                    else
                    {
                        if (response.indexOf("ERROR") >= 0){
                            TestModemOk=false;
                            ModemResponse=response;
                            _formatter.format("Response(error):"+response);
                        }else _formatter.format(_NO_DEVICE_FOUND);

                    }

                }catch (Exception e)
                {
                        _formatter.format(_NO_DEVICE_FOUND);
                        Throwable cause = e;
                        while (cause.getCause() != null)
                        {
                            cause = cause.getCause();
                        }
                        _formatter.format(" (" + cause.getMessage() + ")");
                }
                finally
                {
                        if (serialPort != null)
                        {
                            serialPort.close();
                        }
                }
            }
        }

      if(TestModemOk)return true;
     return false;
 }

 public static SerialModemGateway getGateway(String comPortName, int boudRate, String manufacturer, String model){
     USSDNotification ussdNotification = new USSDNotification();
     SerialModemGateway gateway = new SerialModemGateway("modem."+comPortName, comPortName, boudRate, manufacturer, model);
     gateway.setInbound(true);
     gateway.setOutbound(true);
     try{
     System.out.println("gateway.queryBalance()"+gateway.queryBalance());
         System.out.println(gateway.getImsi());
         //gateway.sendUSSDCommand()
     }catch (Exception ex){
         System.err.println(ex.getMessage());
     }
   //  gateway.setSimPin("0000");
     //Service.getInstance().setOutboundMessageNotification(outboundNotification);
     try {
     Service service=Service.getInstance();
     service.setUSSDNotification(ussdNotification);

     service.addGateway(gateway);
     Service.getInstance().startService();

}catch (Exception ex){
   ex.printStackTrace();
}
   return gateway;
 }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
public static int getLevelSignal(String comPortName, int boudRate, String manufacturer, String model){
   int persent=0;
    float maney=0.0f;
    SerialModemGateway gateway = getGateway( comPortName, boudRate, manufacturer, model);

    try {
        if(gateway==null)return persent;
        persent=gateway.getSignalLevel();
        maney=gateway.queryBalance();

    }catch (Exception ex){
        //ex.printStackTrace();
        System.out.println("  Signal Level: " + persent + " dBm");
        System.out.println("  Balans: " + maney );
        System.out.println(" No response from device..."+ex.getMessage() );
        return persent;
    }
    System.out.println("  Signal Level: " + persent + " dBm");
    System.out.println("  Balans: " + maney );

    return persent;
}
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
public String readMessagesFromAllDevices(){
String sms="";

    return sms;
}
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(AGateway gateway, OutboundMessage msg)
        {
            System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
            System.out.println(msg);
        }
    }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
public boolean sendSms(String comPortName, int boudRate, String manufacturer, String model, String message)throws Exception{


    OutboundNotification outboundNotification = new OutboundNotification();
    System.out.println("Example: Send message from a serial gsm modem.");
    System.out.println(Library.getLibraryDescription());
    System.out.println("Version: " + Library.getLibraryVersion());

    //SerialModemGateway gateway = new SerialModemGateway("modem.com3", "COM3", 115200, "Huawei", "");
    SerialModemGateway gateway = getGateway( comPortName, boudRate, manufacturer, model);
    gateway.setInbound(true);
    gateway.setOutbound(true);
    gateway.setSimPin("0000");
    // Explicit SMSC address set is required for some modems.
    // Below is for VODAFONE GREECE - be sure to set your own!
    gateway.setSmscNumber("+947500001");
    Service.getInstance().setOutboundMessageNotification(outboundNotification);
    Service.getInstance().addGateway(gateway);
    Service.getInstance().startService();
    System.out.println();
    System.out.println("Modem Information:");
    System.out.println("  Manufacturer: " + gateway.getManufacturer());
    System.out.println("  Model: " + gateway.getModel());
    System.out.println("  Serial No: " + gateway.getSerialNo());
    System.out.println("  SIM IMSI: " + gateway.getImsi());
    System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
    System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
    System.out.println();
    // Send a message synchronously.
    OutboundMessage msg = new OutboundMessage("0094757599108", message);
    Service.getInstance().sendMessage(msg);
    System.out.println(msg);
    // Or, send out a WAP SI message.
    //OutboundWapSIMessage wapMsg = new OutboundWapSIMessage("306974000000",  new URL("http://www.smslib.org/"), "Visit SMSLib now!");
    //Service.getInstance().sendMessage(wapMsg);
    //System.out.println(wapMsg);
    // You can also queue some asynchronous messages to see how the callbacks
    // are called...
    //msg = new OutboundMessage("309999999999", "Wrong number!");
    //srv.queueMessage(msg, gateway.getGatewayId());
    //msg = new OutboundMessage("308888888888", "Wrong number!");
    //srv.queueMessage(msg, gateway.getGatewayId());
    System.out.println("Now Sleeping - Hit <enter> to terminate.");
    System.in.read();
    Service.getInstance().stopService();

 return false;
}
    public static class USSDNotification implements IUSSDNotification
    {
        public void process(AGateway gateway, USSDResponse response) {
            System.out.println("USSD handler called from Gateway: " + gateway.getGatewayId());
            System.out.println(response);
        }
    }
 public static void main(String [] args){
     testResponse("CUSD=1,*100#,15\n");
 }
}