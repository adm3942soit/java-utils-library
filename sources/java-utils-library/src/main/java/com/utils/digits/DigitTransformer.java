package com.utils.digits;

/**
 * Created by PROGRAMMER II on 08.09.2014.
 */
public class DigitTransformer {

    public static int binaryToDecimal(String binary){
       return  Integer.parseInt(binary, 2);
    }
    public static int octalToDecimal(String octal){
        return  Integer.parseInt(octal, 2);
    }
    public static int hexadecimalToDecimal(String hexadecimal){
        return  Integer.parseInt(hexadecimal, 2);
    }
    public static String decimalIntoBin(float a){
        //float a = 2011.56f;
        int left = (int) a;
        boolean divideOneMoreTime = left >= 2;
        String bin = "";
        while (divideOneMoreTime) {
            bin = left % 2 + bin;
            left /= 2;
            if (left < 2) {
                divideOneMoreTime = false;
            }
        }
        bin += ".";
        float right = (float) a - (int) a;
        for (int i = 0; i < 20; i++) {
            right = right * 2 - (int) right * 2;
            bin = bin + (int) right;
            if (right == 1.0) {
                break;
            }
        }
        System.out.println(bin);
        return bin;
    }
    public static String hexaDecimalToBin(Integer hexa){

//        Integer i = 0xCAFE;
        System.out.println(Integer.toBinaryString(hexa));
        return Integer.toBinaryString(hexa);
    }
    public static int binToHexaDecimal(String bin){
//        Integer i = 0xCAFE;

        System.out.println(Integer.valueOf(Integer.toHexString(Integer.parseInt(bin, 2))));
        return Integer.valueOf(Integer.toHexString(Integer.parseInt(bin, 2)));
    }
    public static void main(String[] args){
        //02 08 10 00 10 00 03 08
        hexaDecimalToBin(0x10);
        //02 08 11 7F 1C 10 03 6A
        hexaDecimalToBin(0x11);
        hexaDecimalToBin(0x7F);
        hexaDecimalToBin(0x1C);
        hexaDecimalToBin(0x10);

        binToHexaDecimal("1000100");

      //  decimalIntoBin()

    }

}
