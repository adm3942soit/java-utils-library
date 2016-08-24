package com.comp.os.linux;

import com.comp.os.DetectorCompOS;
import com.utils.workWithStr.Stringer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by oxana on 5/29/14.
 */
public class WvdialConfiguration {
    private static File getConfig(){
        if(!DetectorCompOS.isUnix()) return null;
        File file=new File("/etc/wvdial.conf");
        if(!file.exists()){
            System.out.println("File " + file.getName() + " not exist!");
            return null;
        }

        return file;
    }
    private static String getConfig(File file){
        if(file==null)return "";
        try {

            FileReader fileReader = new FileReader(file);
            StringBuffer strFile = new StringBuffer("");
            int res = -1;
            while ((res = fileReader.read()) != -1) {
                strFile.append((char) res);
            }
            System.out.println(strFile.toString());
            return strFile.toString();
        }catch (Exception ex ){ex.printStackTrace();}
       return "";
    }
    public static String getInitString(){
        if(!DetectorCompOS.isUnix()) return "";
        StringBuffer str=new StringBuffer("");

                String strFile=getConfig(getConfig());
                if(strFile.isEmpty()) return "";

                int index = strFile.indexOf("Init3");

                    if (index != -1) {
                        String s=strFile.substring(index);
                        index = (s).indexOf("=");
                        System.out.println(s);

                        if (index != -1) {
                            int index1 = (s).indexOf("\n");
                            System.out.println(s.substring(index+1, index1).toString());

                            String strInit= Stringer.trimFromBothSide(s.substring(index + 1, index1).toString());


                            for(int i=0;i< strInit.length();i++){
                                if(strInit.charAt(i)!='\"')str.append(strInit.charAt(i));
                                else {//str.append((char)92);
                                      str.append((char)34);}
                            }
                            StringBuffer str1=new StringBuffer("");
                            int i=0;
                            System.out.println("!!!!"+str.toString());
                            char smb1=(char)92;
                            char smb2=(char)34;
                            StringBuffer text=new StringBuffer(smb1).append(smb2);
                            String[] parts=str.toString().split(text.toString());
                            System.out.println("!!!!"+parts.length);
                            while(parts!=null && i<parts.length) {
                                index=-1;
                                if((index=parts[i].indexOf((char)92))==-1)str1.append(parts[i]);
                                else {str1.append(parts[i].substring((index!=0?0:1),(index==0?parts[i].length():index)));
                                    str1.append((char)34);}
                                i++;
                            }
                            System.out.println("!!!!"+str1.toString());
                            //str=str1;

                            return str1.toString();
                        }
                    }

     return "";
    }
    public static String getNameProvider(String initStr){
       if(initStr==null ||initStr.isEmpty())return "";
       if(initStr.indexOf("utel")!=-1)return "Utel";
       if(initStr.indexOf("mts.com")!=-1)return "Mts";
       if(initStr.indexOf("umc")!=-1)return "UMC";
       if(initStr.indexOf("internet.beeline")!=-1)return "Beeline";
       if(initStr.indexOf("internet")!=-1)return "life";
       if(initStr.indexOf("djuice")!=-1)return "Djuice";
       if(initStr.indexOf("jeans")!=-1)return "Jeans";
       if(initStr.indexOf("kyivstar")!=-1)return "Kyivstar";
      return "";
    }
    private static int getIndexStr(String strFile, String reg){
        if(strFile==null || strFile.isEmpty())return -1;

        if(reg==null || reg.isEmpty())return -1;
        String [] sStrFile=strFile.split("\n");

        int i=0;
        while(sStrFile!=null && i<sStrFile.length){

            if(sStrFile[i].indexOf(reg)!=-1) return i;
            i++;
        }

      return -1;
    }
    public static boolean writeInitString(String str){
        if(!DetectorCompOS.isUnix())return false;
        System.out.println("writeInitString");
        String strFile=getConfig(getConfig());
        if(strFile==null || strFile.isEmpty()) return false;
        String strResult="";
        FileWriter fileWriter;
        String [] sStrFile=strFile.split("\n");

            try {
                String initStr="";
                fileWriter=new FileWriter(getConfig());

                int index3 = getIndexStr(strFile, "Init3");//strFile.indexOf("Init3");

                if(index3==-1){
                    int index2 = getIndexStr(strFile, "Init2");//strFile.indexOf("Init2");

                    if(index2==-1){
                     int  index1 = getIndexStr(strFile, "Init1");//strFile.indexOf("Init1");

                        if(index1==-1){
                            initStr="Init1 = "+str+"\n";
                            strResult=initStr+strFile;

                        }else{
                            initStr="Init2 = "+str+"\n";
                            StringBuffer s1=new StringBuffer("");
                            StringBuffer s2=new StringBuffer("");
                            int i=0;

                            while(sStrFile!=null && i<=index1 ){
                                s1.append(sStrFile[i]);s1.append("\n");i++;
                            }
                            i=index1+1;

                            while(sStrFile!=null && i<sStrFile.length ){
                                s2.append(sStrFile[i]);s2.append("\n");i++;
                            }

                            strResult=s1.toString()+initStr+s2.toString();

                        }


                    }else{
                        initStr="Init3 = "+str+"\n";

                        StringBuffer s1=new StringBuffer("");
                        StringBuffer s2=new StringBuffer("");
                        int i=0;
                        while(sStrFile!=null && i<=index2 ){
                            s1.append(sStrFile[i]);s1.append("\n");i++;
                        }

                        i=index2+1;
                        while(sStrFile!=null && i<sStrFile.length ){
                            s2.append(sStrFile[i]);s2.append("\n");i++;
                        }

                        strResult=s1.toString()+initStr+s2.toString();

                    }
                }else{
                    initStr="Init3 = "+str+"\n";

                    StringBuffer s1=new StringBuffer("");
                    StringBuffer s2=new StringBuffer("");
                    int i=0;
                    while(sStrFile!=null && i<index3 ){
                        s1.append(sStrFile[i]);s1.append("\n");i++;
                    }

                    i=index3+1;
                    while(sStrFile!=null && i<sStrFile.length ){
                        s2.append(sStrFile[i]);s2.append("\n");i++;
                    }

                    strResult=s1.toString()+initStr+s2.toString();


                }

                System.out.println("writeInitString "+"\n"+strResult+"!!");

                fileWriter.write(strResult);
                fileWriter.flush();
                fileWriter.close();

            }catch (Exception ex){ex.printStackTrace();}




       return false;
    }
    public static void main(String[] args){
        writeInitString("!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
