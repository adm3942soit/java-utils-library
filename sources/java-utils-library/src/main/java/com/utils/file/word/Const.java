package com.utils.file.word;

import com.utils.debug.Printer;

/**
 * Created by oksana.dudnik on 10/8/2015.
 */
public class Const {
    public static int flag=-1;
    public static final int TEXT=0;
    public static final int DOC=1;
    public static final int DOCX=11;
    public static final int XLS=2;
    public static final int XLSX=21;
    public static final int PPT=3;
    public static final int PPTX=31;
    public static final int PDF=4;
    public static final int DJVU=5;
    public static boolean ok = true;
    public static boolean err = false;


    public static String getExtensionFromName(String nameFile) {
        if (nameFile == null || nameFile.isEmpty()) {
            Printer.debug("I don't know name file");
            return null;
        }
        if (nameFile.endsWith(".txt")){flag=TEXT; return "txt";}
        else {
            if (nameFile.endsWith(".doc")){flag=DOC; return "doc";}
            else {
                if (nameFile.endsWith(".docx")){flag=DOCX;  return "docx";}
                else {
                    if (nameFile.endsWith(".xls")) {flag=XLS; return "xls";}
                    else {
                        if (nameFile.endsWith(".xlsx")) {flag=XLSX; return "xlsx";}
                        else {
                            if (nameFile.endsWith(".pdf")){flag=PDF;  return "pdf";}
                            else {
                                if (nameFile.endsWith(".ppt")){flag=PPT;  return "ppt";}
                                else {
                                    if (nameFile.endsWith(".pptx")){flag=PPTX;  return "pptx";}
                                    else {
                                        if (nameFile.endsWith(".djvu")){flag=DJVU;  return "djvu";}
                                        else {
                                            int indexPoint = nameFile.indexOf(".");
                                            if (indexPoint != -1) {
                                                String ext = nameFile.substring(indexPoint + 1);
                                                return ext;
                                            } else return "";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
