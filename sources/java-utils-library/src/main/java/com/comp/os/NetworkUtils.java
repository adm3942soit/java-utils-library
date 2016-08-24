package com.comp.os;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.StringTokenizer;


/**
 * Created by PROGRAMMER II on 28.01.14.
 */
public class NetworkUtils {
    private final static int MACADDR_LENGTH = 17;
    private final static String WIN_OSNAME = "Windows";
    private final static String WIN_MACADDR_REG_EXP =
            "^[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}$";
    private final static String WIN_MACADDR_EXEC = "ipconfig /all";


    public final static String getMacAddress() throws IOException {
        String os = System.getProperty("os.name");
        try {
            if (os.startsWith(WIN_OSNAME)) {
                return winMacAddress(winIpConfigCommand());
            }
            // other OS left as an exercise !
            //   LINUX -->    else if (os.startsWith("Linux")) { ...
            //                ...
            //                Process p = Runtime.getRuntime().exec("ifconfig");
            //   MAC OSX -->  else if(os.startsWith("Mac OS X")) { ...
            //                ...
            //                Process p = Runtime.getRuntime().exec("ifconfig");
            else {
                throw new IOException("OS not supported : " + os);
            }
        }
        catch(ParseException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    private final static String winMacAddress(String ipConfigOutput)
            throws ParseException {
        String localHost = null;
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        }
        catch(UnknownHostException ex) {
            ex.printStackTrace();
            throw new ParseException(ex.getMessage(), 0);
        }

        StringTokenizer tokenizer =
                new StringTokenizer(ipConfigOutput, "\n");
        String lastMacAddress = null;

        while(tokenizer.hasMoreTokens()) {
            String line = tokenizer.nextToken().trim();

            // see if line contains IP address
            if (line.endsWith(localHost) && lastMacAddress != null) {
                return lastMacAddress;
            }

            // see if line contains MAC address
            int macAddressPosition = line.indexOf(":");
            if(macAddressPosition <= 0) continue;

            String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
            if (winIsMacAddress(macAddressCandidate)) {
                lastMacAddress = macAddressCandidate;
                continue;
            }
        }

        ParseException ex = new ParseException
                ("cannot read MAC address from [" + ipConfigOutput + "]", 0);
        ex.printStackTrace();
        throw ex;
    }


    private final static boolean winIsMacAddress(String macAddressCandidate) {
        if (macAddressCandidate.length() != MACADDR_LENGTH)    return false;
        if (!macAddressCandidate.matches(WIN_MACADDR_REG_EXP)) return false;
        return true;
    }


    private final static String winIpConfigCommand() throws IOException {
        Process p = Runtime.getRuntime().exec(WIN_MACADDR_EXEC);
        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

        StringBuffer buffer= new StringBuffer();
        for (;;) {
            int c = stdoutStream.read();
            if (c == -1) break;
            buffer.append((char)c);
        }
        String outputText = buffer.toString();
        stdoutStream.close();
        return outputText;
    }
    public static String getIP(){
        try {
        System.out.println("  IP/Localhost: "
                + InetAddress.getLocalHost().getHostAddress());
           return InetAddress.getLocalHost().getHostAddress();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
     return "";
    }
    public static String getHostName(){
        try {
        String computerName;
        computerName = InetAddress.getLocalHost().getHostName();
        System.out.println(computerName);
         return computerName;
        } catch(Throwable t) {
                t.printStackTrace();
            }
     return "";
    }
    public static String getUserName(){
        String user=System.getProperty("user.name");
     return user;
    }
    public final static void main(String[] args) {

        try {
            System.out.println("MAC ADDRESS");
            System.out.println("  OS          : "
                    + System.getProperty("os.name"));
            System.out.println("  IP/Localhost: "
                    + InetAddress.getLocalHost().getHostAddress());
/*
            System.out.println("  MAC Address : "
                    + getMacAddress());
*/
            String computerName;
            computerName = InetAddress.getLocalHost().getHostName();
            System.out.println(computerName);

            System.out.println(getUserName());
        }
        catch(Throwable t) {
            t.printStackTrace();
        }



    }
}
