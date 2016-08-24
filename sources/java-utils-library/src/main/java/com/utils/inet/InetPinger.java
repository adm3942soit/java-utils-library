package com.utils.inet;

import com.comp.os.DetectorCompOS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: Programer
 * Date: 24.12.13
 * Time: 9:31
 */
public class InetPinger {
    public static boolean WIFI=false;
    public static boolean isWIFI(){
        WIFI=startWIFIInet();
        return WIFI;
    }
    static String[] connectCmd={
            "netsh interface set interface name = &quot;wireless&quot; admin = ENABLED",
            "netsh wlan connect name=&quot;SGSOFT&quot;",
            "sudo ifconfig wlan0 up"
    };
    private static boolean runInCmd(String str)throws Exception{
        Runtime rt = Runtime.getRuntime();
        Process p=rt.exec(str);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "Cp866"));
        p.waitFor();
        for (int i = 0; i <= 500; i++) {
            String str1 = br.readLine();
            if (str1 == null) {
                continue;
            }
            System.out.println(str1);
        }
        System.out.println("runInCmd"+p.exitValue());
        return p.exitValue()==0;
    }

    public static boolean   startWIFIInet() {
        boolean wifi=false;
        boolean start1=false, start2=false;
        try{
            if(DetectorCompOS.isWindows()) {
                if (connectCmd.length > 0)
                    start1 = runInCmd(connectCmd[0]);
                if (connectCmd.length > 1 && !connectCmd[1].equals("")) {
                    start2 = runInCmd(connectCmd[1]);
                }
                wifi=start1&&start2;
            }else{
                start1=true;
                if (connectCmd.length > 2 && !connectCmd[2].equals("")) {
                    start2= runInCmd(connectCmd[2]);
                }
                wifi=start2;
            }

        }catch(Exception ex){

            ex.printStackTrace();
            return  false;
        }
        System.out.println("WIFI"+wifi);
        return wifi;
    }

    public static boolean pingServer(String url) throws IOException, InterruptedException {
        System.out.println("pingServer");
        String serverURL=url;
        serverURL=serverURL.replace("http://","");
        int index=serverURL.indexOf(":");
        if(index==-1)index= serverURL.indexOf("/");
        serverURL=serverURL.substring(0,index);
        Process pingServer=null;
        if(DetectorCompOS.isWindows())
            pingServer = Runtime.getRuntime().exec( "ping "+serverURL );
        if(DetectorCompOS.isUnix())
            pingServer = Runtime.getRuntime().exec( "ping -c 5 "+serverURL );
        System.out.println("pingServer"+serverURL);
        //TerminalState state = null;

        if ( pingServer.waitFor() != 0  ) {
            // Error. Fail to connect. Check server status.
            //    state = TerminalState.SERVER_CONNECTION_ERROR;
            System.out.println("SERVER_CONNECTION_ERROR");
            return false;
        }
        return true;
    }

    public static boolean canConnect(InetAddress address, int port) {
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(address, port);
        try {

            socket.connect(socketAddress, 2000);
        }
        catch (IOException e) {

            return false;
        }
        finally {

            if (socket.isConnected()) {
                try {
                    socket.close();
                }
                catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
        return true;
    }
/*
    public static boolean isServerReachable(String url){
        try {
            if(!isWIFI()) {
                //terminal = Terminal.getInstance();
                String serverURL = url;
                String[] part = serverURL.split(":");

                serverURL = part[1].substring(2);
                InetAddress address = InetAddress.getByName(serverURL);
                System.out.println(part[2].substring(0,part[2].length()-1));
                boolean connect=canConnect(address, Integer.valueOf(part[2].substring(0,part[2].length()-1)));
                System.out.println("Connecting "+connect);
                return connect;
            } else{
                return pingServer();
            }

        }
        catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
*/

    public static boolean isServerReachable(String url){
        try {
                String serverURL = url;
                String[] part = serverURL.split(":");
                System.out.println(part.length);
                if(part.length<=2) {return pingServer(url);}
                System.out.println(part[1]);
                serverURL = part[1].substring(2);
                InetAddress address = InetAddress.getByName(serverURL);
                System.out.println(part[2].substring(0,part[2].length()-1));
                boolean connect=canConnect(address, Integer.valueOf(part[2].substring(0,part[2].length()-1)));
                System.out.println("Connecting "+connect);
                return connect;
        }
        catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public static boolean isInternetReachable()
    {
        try {
            //make a URL to a known source
            URL url = new URL("http://www.google.com");

            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            urlConnect.setConnectTimeout(7000);
            //trying to retrieve data from the source. If there
            //is no connection, this line will fail
            Object objData = urlConnect.getContent();

        } catch (UnknownHostException e) {

           //// e.printStackTrace();

           // return reconnect();
            return false;
        }
        catch (IOException e) {

            e.printStackTrace();
            //return reconnect();
            return false;
        }
        return true;
    }
/*
    private static boolean reconnect(){
        if(!isWIFI()) {
            if (ModemTest.reconnect()) {
              return true;
            } else {
                return false;
            }
        }else{
             // return WIFI;
            return startWIFIInet();
        }

    }
*/
    public static boolean isInternetReachableViaInternetAddress()
    {
        try {

            InetAddress address = InetAddress.getByName("java.sun.com");

            if(address == null)
            {
                return false;
            }

        } catch (UnknownHostException e) {

            e.printStackTrace();
            return false;
        }
        catch (IOException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void main(String args[]){
       System.out.println(isInternetReachable());
        System.out.println(isInternetReachableViaInternetAddress());
    }
}
