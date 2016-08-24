package com.utils.screen;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by PROGRAMMER II on 04.08.2014.
 */
public class Screenshort {
    public File file;
    public static String  nameFile="screen.png";
   // public static String  nameFile2="screen.jpg";

    public Screenshort(){

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screenSize = toolkit.getScreenSize();

        Rectangle rect = new Rectangle(0, 0, screenSize.width, screenSize.height);
        try {
            Robot robot = new Robot();

            BufferedImage image = robot.createScreenCapture(rect);

            //Save the screenshot as a png
            file = new File(nameFile);
            ImageIO.write(image, "png", file);

/*
            file = new File(nameFile2);
            ImageIO.write(image, "jpg", file);
*/
        }catch(Exception ex){ex.printStackTrace();}

    }
    public static void main(String [] args){
        new Screenshort();
    }
}
