package com.utils.connection;

import com.utils.encoding.ConvertChar;
import javafx.scene.control.TextArea;
import com.comp.os.DetectorCompOS;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oxana on 04.06.14.
 */
public class PingProgram {

    static List<String> commands = new ArrayList<String>();

    /**
     * Provide the command you want to run as a List of Strings. Here's an example:
     *
     * List<String> commands = new ArrayList<String>();
     * commands.add("/sbin/ping");
     * commands.add("-c");
     * commands.add("5");
     * commands.add("www.google.com");
     * exec.doCommand(commands);
     *
     */
    public void doCommandBySite( String  address)
            throws IOException
    {
        String s = null;
       if(DetectorCompOS.isUnix()) {
           commands.add("ping");//"/sbin/ping");
           commands.add("-c");
           commands.add("5");
       }
       if(DetectorCompOS.isWindows()){
           commands.add("ping");//"/sbin/ping");
       }
        commands.add(address);//"www.google.com"

        ProcessBuilder pb = new ProcessBuilder(commands);
        Process process = pb.start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        while ((s = stdInput.readLine()) != null)
        {
            System.out.println(s);
          //  ConvertChar.convertCodingStream(s);
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null)
        {
            System.out.println(s);
        }
    }
    public static boolean pingSite( String  address)
            throws IOException
    {
        String s = null;
        if(DetectorCompOS.isUnix()) {
            commands.add("ping");//"/sbin/ping");
            commands.add("-c");
            commands.add("5");
        }
        if(DetectorCompOS.isWindows()){
            commands.add("ping");//"/sbin/ping");
        }
        commands.add(address);//"www.google.com"

        ProcessBuilder pb = new ProcessBuilder(commands);
        Process process = pb.start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        while ((s = stdInput.readLine()) != null)
        {
            System.out.println(s);
         //   if(s.contains("TTL="))return true;
            //  ConvertChar.convertCodingStream(s);
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null)
        {
            System.out.println(s);
        }
        int exit=1;
        try {
            exit = process.waitFor();
        }catch(Exception ex){ex.printStackTrace();}
     return exit==0;
    }

    public String[]  doCommandBySite1( String  address)
            throws IOException
    {
        String s = null;
        StringBuffer str=new StringBuffer("");
        if(DetectorCompOS.isUnix()) {
            commands.add("ping");//"/sbin/ping");
            commands.add("-c");
            commands.add("5");
        }
        if(DetectorCompOS.isWindows()){
            commands.add("ping");//"/sbin/ping");
        }
        commands.add(address);//"www.google.com"

        ProcessBuilder pb = new ProcessBuilder(commands);
        Process process = pb.start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        while ((s = stdInput.readLine()) != null)
        {
            System.out.println(s);
            str.append(s);str.append("\n");
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null)
        {
            System.out.println(s);
            str.append(s);str.append("\n");
        }
        return str.toString().split("\n");
    }
    public String[]  doCommandBySite1( String  address, TextArea ta )
            throws IOException
    {
        String s = null;
        StringBuffer str=new StringBuffer("");
        if(DetectorCompOS.isUnix()) {
            commands.add("ping");//"/sbin/ping");
            commands.add("-c");
            commands.add("5");
        }
        if(DetectorCompOS.isWindows()){
            commands.add("ping");//"/sbin/ping");
        }
        commands.add(address);//"www.google.com"

        ProcessBuilder pb = new ProcessBuilder(commands);
        Process process = pb.start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        while ((s = stdInput.readLine()) != null)
        {
            System.out.println(s);
            str.append(s);str.append("\n");
            ta.setText(ta.getText() + s + "\n");
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null)
        {
            System.out.println(s);
            str.append(s);str.append("\n");
            ta.setText(ta.getText() + s + "\n");
        }
        return str.toString().split("\n");
    }
    public static boolean ping(String serverAddress) throws IOException, InterruptedException {
        System.out.println("ping");
        String serverURL= serverAddress;//    public static boolean ping() throws IOException, InterruptedException {
        System.out.println("ping");
        //String serverURL= AdminPanel.getServerAddress();
        //System.out.print("serverURL"+serverURL);
        serverURL=serverURL.replace("http://","");
        //System.out.print("serverURL"+serverURL);
        int index=serverURL.indexOf(":");
        if(index==-1)index= serverURL.indexOf("/");
        serverURL=serverURL.substring(0,index);
        //System.out.print("serverURL"+serverURL);
        Process pingServer = Runtime.getRuntime().exec( "ping "+serverURL );
        System.out.println("pingServer"+serverURL);
        if ( pingServer.waitFor() != 0  ) {
            // Error. Fail to connect. Check server status.
            Process ping1 = Runtime.getRuntime().exec( "ping www.google.com" );
            Process ping2 = Runtime.getRuntime().exec( "ping www.yandex.com" );

            if ( ping1.waitFor() != 0 && ping2.waitFor() != 0 )  {
                // Error. Fail to connect. Check internet connection.
/*
                if(ModemTest.reconnect()){
                    return true;
                }
*/
                return false;

            }
        }
        return true;
    }

    public static void main(String args[])
            throws IOException
    {
        // create the ping command as a list of strings
        PingProgram ping = new PingProgram();
       System.out.println(ping.pingSite("89.209.116.224" +
               ""));
    }
}
