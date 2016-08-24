package com.utils.file.word;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;

import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Asya
 * Date: 5/18/12
 * Time: 10:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class Presentation {

    public Presentation() {
    }

    public Presentation(String nameFile) {
        String ext= Const.getExtensionFromName(nameFile);
        if(ext==null || !ext.contains("ppt"))return;
        try{
            SlideShow slideShow = new SlideShow();
            Slide slide = slideShow.createSlide();
            FileOutputStream out = new FileOutputStream(nameFile);
            slideShow.write(out);
            out.close();
        }catch(Exception e){}
    }
    public static boolean createPresentation(String nameFile){
        String ext= Const.getExtensionFromName(nameFile);
        if(ext==null || !ext.contains("ppt"))return false;
        try{
            SlideShow slideShow = new SlideShow();
            Slide slide = slideShow.createSlide();
            FileOutputStream out = new FileOutputStream(nameFile);
            slideShow.write(out);
            out.close();

        }catch(Exception e){
            return false;
        }
     return true;
    }



}
