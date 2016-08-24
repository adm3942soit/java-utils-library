package com.utils.task;

import com.utils.file.Filer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by PROGRAMMER II on 18.08.2014.
 */
public class TaskManager {
    public static final String taskFile="logs/taskManager.txt";
    static File file=new File(taskFile);
    public static void showAllProcessesRunningOnWindows(){
        //this function prints the contents of tasklist including pid's
        try {

            if(!file.exists())file.createNewFile();

            Runtime runtime = Runtime.getRuntime();
            String cmds[] = {"cmd", "/c", "tasklist"};
            Process proc = runtime.exec(cmds);
            InputStream inputstream = proc.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            String line; StringBuffer ss=new StringBuffer("");
            while ((line = bufferedreader.readLine()) != null) {
                //System.out.println(line);
                ss.append(line+"\n");
            }
            Filer.rewriteFile(file,ss.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Cannot query the tasklist for some reason.");
        }
    }
    public static boolean isRunJavaProcessByPid(int pid){
        showAllProcessesRunningOnWindows();
        String strFile=Filer.readFile(file, true, false);
        if(strFile==null || strFile.isEmpty())return false;
        String[] strFiles=strFile.split("\n");
        for(String line:strFiles){
            if(line.contains("java.exe")&& line.contains(" "+pid+" "+"Console")) return true;
        }

        return false;
    }
}
