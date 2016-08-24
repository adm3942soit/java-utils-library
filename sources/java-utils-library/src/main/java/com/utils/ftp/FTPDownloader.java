package com.utils.ftp;

import ua.edu.file.MyFiler;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import com.utils.date.Dater;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by PROGRAMMER II on 20.08.2014.
 */
public class FTPDownloader {
    public static void getDataFiles(String server, String username, String password,
                                    String folder, String destinationFolder, Calendar start, Calendar end ) {
        try {
            // Connect and logon to FTP Server
            FTPClient ftp = new FTPClient();
            ftp.connect( server );
            ftp.login( username, password );
            System.out.println("Connected to " + server + ".");
            System.out.print(ftp.getReplyString());

            // List the files in the directory
            ftp.changeWorkingDirectory(folder);
            FTPFile[] files = ftp.listFiles();
            System.out.println("Number of files in dir: " + files.length);
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
            for(int i=0; i<files.length; i++) {
                Date fileDate = files[i].getTimestamp().getTime();

                // Download a file from the FTP Server
                System.out.print( df.format( files[ i ].getTimestamp().getTime() ) );
                System.out.println( "\t" + files[ i ].getName() );
                File file = new File(destinationFolder + File.separator + files[i].getName());
                FileOutputStream fos = new FileOutputStream( file );
                ftp.retrieveFile(files[ i ].getName(), fos);
                fos.close();
                file.setLastModified(fileDate.getTime());
            }

            // Logout from the FTP Server and disconnect
            ftp.logout();
            ftp.disconnect();
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
    }
    public static boolean getData(String server, int port, String username, String password,
                                    String folder, String newFullNameFile, String nameFile) {
        boolean ok=false;
        try {
            // Connect and logon to FTP Server
            FTPClient ftp = new FTPClient();
            ftp.connect( server , port);

            showServerReply(ftp);
            int replyCode = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Connect failed");
                return false;
            }
            ok=ftp.login(username, password);
            if(!ok){
                System.out.println("Could not login to the server");
                return false;
            }
            System.out.println("Connected to " + server + ".");
            System.out.print(ftp.getReplyString());

            // List the files in the directory
            ok=ftp.changeWorkingDirectory("../");
            if (ok) {
                System.out.println("Successfully changed working directory.");
            } else {
                System.out.println("Failed to change working directory. See server's reply.");
            }
            FTPFile[] files = ftp.listFiles();
            ok=ftp.changeWorkingDirectory("../");
            if (ok) {
                System.out.println("Successfully changed working directory.");
            } else {
                System.out.println("Failed to change working directory. See server's reply.");
            }
            files = ftp.listFiles();

            ok=ftp.changeWorkingDirectory(folder);
            showServerReply(ftp);
            if (ok) {
                System.out.println("Successfully changed working directory.");
            } else {
                System.out.println("Failed to change working directory. See server's reply.");
            }

            files = ftp.listFiles();
            System.out.println("Number of files in dir: " + files.length);
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
            ok=false;
            for(int i=0; i<files.length; i++) {
                Date fileDate = files[i].getTimestamp().getTime();
                  if(nameFile.equals(files[i].getName())) {
                      // Download a file from the FTP Server
                      System.out.print(df.format(files[i].getTimestamp().getTime()));
                      System.out.println("\t" + files[i].getName());
                      File file = new File(newFullNameFile);
                      FileOutputStream fos = new FileOutputStream(file);
                      System.out.println(Dater.getTimeHM()+" Starting downloadFile.");
                      if(ftp.retrieveFile(files[i].getName(), fos)) {
                          System.out.println(Dater.getTimeHM()+" Successfully downloadFile.");
                          fos.close();
                          file.setLastModified(fileDate.getTime());
                          ok = true;
                      }else{
                          System.out.println(Dater.getTimeHM()+" Failed downloadFile.");
                          ok=false;
                          fos.close();
                      }
                      break;
                  }
            }

            // Logout from the FTP Server and disconnect
            ftp.logout();
            ftp.disconnect();
        }
        catch( Exception e ) {
            e.printStackTrace();
            return false;
        }
       if(ok)return true;
       else return false;
    }
    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
/*
    File outArxFile=new File("c:/text.txt");
    URL ur = new URL("ftp://user:password@127.0.0.1:/in/" + "text.txt");
    URLConnection urlc = ur.openConnection();

    BufferedInputStream in = new BufferedInputStream(new FileInputStream(outArxFile));
    BufferedOutputStream z = new BufferedOutputStream(urlc.getOutputStream());
    int by;
    while ((by = in.read()) != -1) {
        z.write(by);
    }
    in.close();
    z.close();
*/
/*
File outArxFile=new File("c:/TEXT.TXT");
    FTPClient ftpClient = new FTPClient();
    ftpClient.connect("127.0.0.1");
    ftpClient.login("user", "rassword");
    ftpClient.changeWorkingDirectory("in");
    InputStream in = new FileInputStream(outArxFile);
    ftpClient.appendFile("TEXT.TXT", in);
    ftpClient.logout();
    ftpClient.disconnect();
    in.close();

*/
    public static void  main(String[] args){
        getData("192.168.1.13", 3345, "sgpay", "sgp@yp@ss","/var/www/html/catalog/payment_system",
                MyFiler.getCurrentDirectory()+File.separator+"paysystemNew.jar","paysystem.jar");
    }
}
