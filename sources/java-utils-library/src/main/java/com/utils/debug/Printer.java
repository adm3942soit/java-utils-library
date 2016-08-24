package com.utils.debug;



import com.utils.file.Filer;

import java.io.File;

/**
 * Created by Oxana on 9/1/15.
 */
public class Printer {
    public static Boolean DEBUG=true;
    public static void debug(String message){

        System.out.println(message);

    }
    public static void debug(String methodName,String message){

        System.out.println("From method:"+methodName+" "+
                message);

    }
    public static void debug(String className,String methodName,String message){

        System.out.println("From class:"+className+" "+
                "in method:"+methodName+" "+
                message);

    }

    public static void intoConsole(String message){

        System.out.println(message);

    }

    public static void intoConsole(String className, String methodName,
                                   String message){

        System.out.println(
                "From class:"+className+" "
                +"in method:"+methodName+" "
                +message

              );

    }
    public static void intoFile(String className, String methodName,
                                String message){

        Filer.appendFile(new File("debug"),
                "From class:" + className + " "
                        + "in method:" + methodName + " "

                        + message);

    }

    public static void setDEBUG(Boolean DEBUG) {
        Printer.DEBUG = DEBUG;
    }
}
