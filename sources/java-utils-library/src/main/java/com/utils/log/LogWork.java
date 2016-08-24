package com.utils.log;

import com.utils.date.Dater;
import com.utils.encoding.ConvertChar;
import com.utils.file.Filer;

import java.io.*;


/**
 * Created by PROGRAMMER II on 18.09.2014.
 */
public class LogWork {
    private static Dater date=new Dater();
    public static LogWork log=new LogWork();
    private static InFile inFile;
    private static String nameLog="";
    private static File file;
    private static BufferedWriter bufferedWriter;
//########################################################################################################
    public LogWork(){
        inFile = new InFile();
    }
    public static LogWork getInstance(){
        return log;
    }

    //########################################################################################################
    public synchronized Boolean openFile(String name) { //BufferedWriter

        this.nameLog=name+"_"+date.getDD_MM_YYYY()+".txt";

        if(inFile.openFile(nameLog)){
            file=new File(nameLog);
            if(file.getParentFile()!=null && !file.getParentFile().exists()){
                file.getParentFile().mkdir();
            }
            try {
                FileWriter fw = new FileWriter(file, true);
                bufferedWriter = new BufferedWriter(fw);
            }catch(Exception ex){ex.printStackTrace();}
            return true;
        }
        return false;
    }
    //########################################################################################################
    public boolean isOpened(){
        return getLogFile()!=null;
    }
    //########################################################################################################
    public synchronized BufferedWriter openFile(String name, java.util.Date date) {
        this.nameLog=name+"_"+getDD_MM_YYYY(date)+".txt";
        try {
            file = new File(this.nameLog);
            FileWriter fw = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedWriter;
    }
    public String getDD_MM_YYYY(java.util.Date date ) {
        return getDay(date)+"_"+(getMonth(date)+1)+"_"+getYear(date)+"";
    }
    private int getDay(java.util.Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance(
                java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(date);
        return calendar.get(java.util.Calendar.DAY_OF_MONTH);
    }
    private int getMonth(java.util.Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance(
                java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(date);
        return calendar.get(java.util.Calendar.MONTH);
    }
    private int getYear(java.util.Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance(
                java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(date);
        return calendar.get(java.util.Calendar.YEAR);
    }
    public String getFullDate() {
        return java.util.Calendar.getInstance().getTime()+"";
    }
    //########################################################################################################
    public synchronized BufferedWriter openFileBufferedWrite(String name) {
        openFile(name);
        return bufferedWriter;
    }
    //########################################################################################################
    public File getLogFile(){
        return file!=null && file.exists()?file:null;
    }
    //########################################################################################################
    public void createFile(String name){
        file=new File(name);
        try {
            if(!file.exists())
                 Filer.createFile(name);
        }catch (Exception ex){ex.printStackTrace();}

    }
    //########################################################################################################
    public static String getNameLog() {
        return nameLog;
    }
    //########################################################################################################
    public synchronized boolean writeInFile (String str) {
        str = date.getFullDate() + " : "+str;
        if(inFile.writeInFile(str)){
            return true;
        }
      //  inFile.print(str);
        return false;
    }
    //########################################################################################################
    public synchronized String readFromFile(){
        return Filer.readFile(file, true, false);
    }
    //########################################################################################################
    public synchronized void writeInTodayFile (String str) {
        inFile.print(str);
    }
    //########################################################################################################
    public synchronized boolean writeEmptyLine () {
        if(inFile.writeEmptyLine("\n")){
            return true;
        }
        return false;
    }
    //########################################################################################################
    public boolean closeFile() {
        if(inFile.closeFile("________________________________\n")) {
            return true;
        }
        return false;
    }
    //########################################################################################################
    public synchronized String readLastStrFromFile(){
        StringBuffer result=new StringBuffer("");
        BufferedReader bufferedReader=null;
        String lastStr="";
        if(file.exists()){
            System.out.print("f.exists()");
            try {
                FileReader fileReader=new FileReader(file);
                System.out.println("f "+file.getAbsolutePath());
                bufferedReader=new BufferedReader(fileReader);
                System.out.println("bufferedReader"+bufferedReader);
                if(bufferedReader==null)return result.toString();
                while((lastStr=bufferedReader.readLine())!=null){
                    //lastStr=new ConvertChar("UTF-8").convertFrom(lastStr);
                    System.out.print("last str"+lastStr);
                    result=new StringBuffer(lastStr);
                }
            }catch (FileNotFoundException ex){
                System.out.println(ex.getMessage());
            }catch (IOException exx){
                System.out.println(exx.getMessage());
            }
            finally {
                try {
                    if (bufferedReader != null)bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return result.toString();
        }
//        System.out.print("!f.exists()");
        return result.toString();
    }
    //########################################################################################################
    public synchronized String readLastStrIncasFromFile(){
        String[]str=readStrFromFile();
        int i=0;
        for(;i<1000;i++){
            if(str[i]==null)break;
        }
        while(i!=0 && str[--i].indexOf("incas")==-1){}
        return str[i];
    }
    //########################################################################################################
    public synchronized String[] readStrFromFile(){
        String[]str=new String[1000];
        //StringBuffer result=new StringBuffer("");
        BufferedReader bufferedReader=null;
        String lastStr="";
        if(file.exists()){
            //System.out.print("f.exists()");
            try {
                FileReader fileReader=new FileReader(file);
                System.out.println("f "+file.getAbsolutePath());
                bufferedReader=new BufferedReader(fileReader);
                System.out.println("bufferedReader"+bufferedReader);
                if(bufferedReader==null)return null;
                int i=0;
                while((lastStr=bufferedReader.readLine())!=null){
                    //lastStr=new ConvertChar("UTF-8").convertFrom(lastStr);
                    System.out.print("last str"+lastStr);
                    //result=new StringBuffer(lastStr);
                    str[i]=lastStr;i++;
                }
                str[i]=null;
            }catch (FileNotFoundException ex){
                System.out.println(ex.getMessage());
            }catch (IOException exx){
                System.out.println(exx.getMessage());
            }
            finally {
                try {
                    if (bufferedReader != null)bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return str;
        }
        return str;
    }
    //########################################################################################################
}
