package com.utils.file.word;

import com.utils.debug.Printer;
import com.utils.file.word.apache.CreatorWordDoc;
import ua.edu.file.MyFiler;
import org.apache.poi.hwpf.extractor.WordExtractor;


import java.io.File;
import java.io.FileInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Asya
 * Date: 4/26/12
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocReader {

    public static String[] text;
    public static WordExtractor extractor = null ;
    private static boolean ret;

    //***************** String[] createDocFile(String fullName)****************************************
/*
    public static boolean createDocFile(String fullName, String[] text)
    {

     return ret;
    }
*/
    //***************** String[] readDocFile(String fullName)****************************************
    public static String[] readDocFile(String fullName)
    {
       //  if(!MyFiler.checkNameFile(fullName)) return null;
        try {

            File file = new File(fullName);
            FileInputStream fis=new FileInputStream(file.getAbsolutePath());
            //POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fullName));
            //POIFSFileSystem fs = HWPFDocument.verifyAndBuildPOIFS(fis);
           // HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(fis);//document
            String text = extractor.getText();
            Printer.intoConsole(text);
/*
            for(int i=0;i<text.length;i++){
                if(text[i] != null)
                    Printer.intoConsole("MyFiler", "readDocFile", text[i]);
            }
*/
        }
        catch(Exception ex)
        {
            Printer.intoConsole("MyFiler", "readDocFile", ex.getMessage());
        }


        return text;
    }
public static void main(String[]args){


    CreatorWordDoc.createWordDoc(MyFiler.getCurrentDirectory()+File.separator+"firstWordDoc.doc", "Hello from library!");
    String[]ss=readDocFile(MyFiler.getCurrentDirectory()+File.separator+"firstWordDoc.doc");
    if(ss!=null)
    for(String s:ss){
        Printer.intoConsole(s);
    }
}

}
