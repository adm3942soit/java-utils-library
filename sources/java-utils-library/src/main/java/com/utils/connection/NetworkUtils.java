package ua.com.hardware.connection;

import java.io.*;

/**
 * Created by PROGRAMMER II on 15.07.2014.
 */
public class NetworkUtils {
    public static final String pathToConnectScripts ="scripts/connections/getNetworkConnections.vbs";//MyFiler.getCurrentDirectory()+

            //System.getProperty("file.separator")+
/*
            "scripts"+
                    System.getProperty("file.separator")+"connections"+
*/
                    //System.getProperty("file.separator")+

    ClassLoader classLoader = getClass().getClassLoader();
    public String[] getNetworkConnections() {
        String[] networkConnections = {"no connections available"};//new String[1];//
        int num = 0;

        BufferedWriter myfile;
        try {

            File file = new File(classLoader.getResource(pathToConnectScripts).getFile());
/*
            String path = file.getPath();//choosingDir + File.separator +
                    //"install" + File.separator +
                    //"paySystem" + File.separator +
                    ;
            File file = new File(path);
*/
            if (file.exists()) {
                myfile = new BufferedWriter(new FileWriter(file.getName()
                ));


                myfile.write(
                        "FileName = \"c:\\testfile1.txt\"" + "\n" +
                                "Set objShellApp = CreateObject(\"Shell.Application\")" + "\n" +
                                "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")" + "\n" +
                                "Set File = objFSO.OpenTextFile(FileName, 2, True)" + "\n" +
                                "Set colFolders = objShellApp.NameSpace(49).Items" + "\n" +
                                "For Each objFolder in colFolders" + "\n" +
                                "File.WriteLine objFolder.Name" + "\n" +
                                "wscript.echo objFolder.Name" + "\n" +
                                "Next" + "\n" +
                                "File.Close"
                );

                myfile.close();

                // String path=System.getProperty("user.dir");
/*
                char[] cpath = path.toCharArray();
                for (int i = 0; i < cpath.length; i++) {
                    if (cpath[i] == '\\') {
                        cpath[i] = '/';
                    }

                }
                path = String.valueOf(cpath);
                System.out.println("getNetworkConnections " + path);
*/

                String str = "cscript" + " " + file.getName();//+"/"+pathToConnectScripts;
                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec(str);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "Cp866"));
                p.waitFor();
                for (int ii = 0; ii <= 50; ii++) {
                    String str1 = br.readLine();
                    if (str1 == null) {
                        continue;
                    }
                    if (ii >= 3) {
//					System.out.println(str1);
                        if (num == 0) {
                            networkConnections[0] = str1;
//						System.out.println(str1);
                        }
                        if (num > 0) {
                            networkConnections = add(str1, networkConnections);
//						System.out.println(str1);
                        }
                        num++;
                    }
                }
                if (num != 0) {
                    return networkConnections;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        networkConnections[0] = "no connections available";
        return networkConnections;
    }
    private static String[] add(String element, String[] buffer) {
        String[] temp = new String[buffer.length + 1];// each time i add i define a temp that's 1 larger,
        for (int i = 0; i < buffer.length; i++) {
            temp[i] = buffer[i];
        }
        temp[temp.length - 1] = element; // copy, and set the last element:
        return temp;
    }

}
