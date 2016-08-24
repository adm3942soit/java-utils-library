package com.utils.encoding;

import com.utils.file.Filer;

import java.io.*;
import java.nio.charset.Charset;


public class ConvertChar {
	private String encoding;

	/**
	 * Default constructor set encoding "ISO-8859-1"
	 */

	public ConvertChar() {
		this("ISO-8859-1");
	}

	/**
	 * @param encoding            ID
	 *            - String type seting encoding
	 */

	public ConvertChar(String encoding) {
		this.encoding = encoding;
	}

	public String convertFrom(String input) {
		String string = null;
		try {
			byte[] data = input.getBytes(encoding);
			string = new String(data);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}

	public String convertTo(String input) {
		String string = null;
		try {
			byte[] data = input.getBytes();
			string = new String(data, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}
    public static String getEncoding( String poundSign){
        String[] encodings = {
                "Cp866",
                "KOI8_R",
                "Cp1250",
                "Cp1251",
                "Cp1252", // Windows-1252
                "UTF8", // Unicode UTF-8
                "UTF-16",
                "UTF_32",
                "WIN",
                //"UTF-16BE", // Unicode UTF-16, big endian
                "ISO8859_1",
                "ASCII"
        };
        Charset charset = Charset.defaultCharset();
        System.out.println("Default encoding: " + charset + " (Aliases: "
                + charset.aliases() + ")");
        try {
            //String poundSign = "\u00A3";
            for (String encoding : encodings) {
                System.out.format("%10s%3s  ", encoding, poundSign);
                byte[] encoded = poundSign.getBytes(encoding);
/*
                for (byte b : encoded) {
                    System.out.format("%02x ", b);
                }
*/
                String newStr=new String(encoded, "UTF8");
                System.out.println(newStr);
                newStr=new String(encoded);
                if(newStr.equals(poundSign) ) return encoding;
                System.out.println(newStr);
            }
        }catch(UnsupportedEncodingException ex){
            System.out.println(ex.getMessage());
        }
     return "";
    }
    public static String decodeFromStream(
            InputStream stream, Charset encoding)
            throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[] buffer = new byte[4];
        while (true) {
            // read bytes into buffer
            int r = stream.read(buffer);
            if (r < 0) {
                break;
            }
            // decode byte data into char data
            String data = new String(buffer, 0, r, encoding);
            builder.append(data);
        }
        return builder.toString();
    }
    public static String decodeToUtf8(String original, String encoding){
        Charset utf8 = Charset.forName("UTF-8");
        Charset startCoding = Charset.forName(encoding);
        try {
            byte[] encoded = original.getBytes(encoding);//
            InputStream stream = new ByteArrayInputStream(encoded);


            try {
                // decode the data
                String decoded = decodeFromStream(stream, utf8);
/*
            if (!original.equals(decoded)) {
                throw new IllegalStateException("Lost data");
            }
*/
                return decoded;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
    return original;
    }
    public static String decode(String original, String encodingFrom, String encodingTo){
        Charset endCoding = Charset.forName(encodingTo);
        Charset startCoding = Charset.forName(encodingFrom);
        try {
            byte[] encoded = original.getBytes(encodingFrom);//
            InputStream stream = new ByteArrayInputStream(encoded);


            try {
                // decode the data
                String decoded = decodeFromStream(stream, endCoding);
/*
            if (!original.equals(decoded)) {
                throw new IllegalStateException("Lost data");
            }
*/
                return decoded;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return original;
    }
    public static String convertCoding(String original, String encodingFrom, String encodingTo){
        byte[] byteArray=original.getBytes();//Charset.forName(encodingFrom)
        System.out.println(org.apache.commons.codec.binary.BinaryCodec.toAsciiString(byteArray));
        return new String(byteArray, Charset.forName(encodingTo));
    }

    public static void convertCodingStream(String original){//, String encodingFrom, String encodingTo
        try{
        InputStreamReader in = new InputStreamReader(new StringBufferInputStream(original));
        String cod=in.getEncoding();
        byte[] bytes=original.getBytes(cod);
            String[] encodings = {
                    "Cp866",
                    "KOI8_R",
                    "Cp1250",
                    "Cp1251",
                    "Cp1252", // Windows-1252
                    "UTF8", // Unicode UTF-8
                    "UTF-16",
                    "UTF_32",
                   // "WIN",
                    "UTF-16BE", // Unicode UTF-16, big endian
                    "ISO8859_1",
                    "ASCII"
            };
        for(String cod1:encodings) {
            System.out.println(new String(bytes, cod1)+"\n");
        }
        }catch(Exception ex){}

    }

    public static void convert(
            String infile, //input file name, if null reads from console/stdin
            String outfile, //output file name, if null writes to console/stdout
            String from,   //encoding of input file (e.g. UTF-8/windows-1251, etc)
            String to)     //encoding of output file (e.g. UTF-8/windows-1251, etc)
            throws IOException, UnsupportedEncodingException
    {
        // set up byte streams
        InputStream in;
        if(infile != null) {
            File inf=new File(infile);
            if(!inf.exists())inf.createNewFile();

            in = new FileInputStream(infile);
        }
        else
            in=System.in;
        OutputStream out;
        if(outfile != null){
            File outf=new File(outfile);
            if(!outf.exists())outf.createNewFile();
            out=new FileOutputStream(outfile);}
        else
            out=System.out;

        // Use default encoding if no encoding is specified.
        if(from == null) from=System.getProperty("file.encoding");
        if(to == null) to=System.getProperty("file.encoding");

        // Set up character stream
        Reader r=new BufferedReader(new InputStreamReader(in, from));
        Writer w=new BufferedWriter(new OutputStreamWriter(out, to));

        // Copy characters from input to output.  The InputStreamReader
        // converts from the input encoding to Unicode,, and the OutputStreamWriter
        // converts from Unicode to the output encoding.  Characters that cannot be
        // represented in the output encoding are output as '?'
        char[] buffer=new char[4096];
        int len;
        while((len=r.read(buffer)) != -1)
            w.write(buffer, 0, len);
        r.close();
        w.flush();
        w.close();

    }
    public static void convertInString(
            String infile, //input file name, if null reads from console/stdin
            String outStr, //output file name, if null writes to console/stdout
            String from,   //encoding of input file (e.g. UTF-8/windows-1251, etc)
            String to)     //encoding of output file (e.g. UTF-8/windows-1251, etc)
            throws IOException, UnsupportedEncodingException
    {
        // set up byte streams
        InputStream in;
        if(infile != null)
            in=new FileInputStream(infile);
        else
            in=System.in;
        OutputStream out;
        if(outStr != null)
            out=System.out;
        else
            out=System.out;

        // Use default encoding if no encoding is specified.
        if(from == null) from=System.getProperty("file.encoding");
        if(to == null) to=System.getProperty("file.encoding");

        // Set up character stream
        Reader r=new BufferedReader(new InputStreamReader(in, from));
        Writer w=new BufferedWriter(new OutputStreamWriter(out, to));

        // Copy characters from input to output.  The InputStreamReader
        // converts from the input encoding to Unicode,, and the OutputStreamWriter
        // converts from Unicode to the output encoding.  Characters that cannot be
        // represented in the output encoding are output as '?'
        char[] buffer=new char[4096];
        int len;
        while((len=r.read(buffer)) != -1)
            w.write(buffer, 0, len);
        r.close();
        w.flush();
        w.close();
    }

public  static String decodeFromFile(String original){
   String result="";
    File file=new File("test.txt");
    try {
        if (!file.exists()) file.createNewFile();
        Filer.rewriteFile(file, original);
        ConvertChar.convert("test.txt", "testout.txt", "UTF-8", "ISO-8859-1");
        InputStream stream = new FileInputStream("testout.txt");
        // decode the data
        result = ConvertChar.decodeFromStream(stream, Charset.forName("windows-1251"));
    }catch(Exception ex){
        ex.printStackTrace();
    }
    System.out.println("!!!!"+result);
    return result;
}
    public static void main(String args[]){
        //System.out.println("Cod:"+getEncoding("ПАТ Киевстар"));;
/*
        Charset utf8 = Charset.forName("UTF-8");
        String original = "мфо";
        byte[] encoded = original.getBytes(utf8);
        // create mock file input stream
        InputStream stream = new ByteArrayInputStream(encoded);
       try {
           // decode the data
           String decoded = decodeFromStream(stream, utf8);
           if (!original.equals(decoded)) {
               throw new IllegalStateException("Lost data");
           }

       }catch(Exception ex){
           ex.printStackTrace();
       }
*/     try {
            //convertCoding("Г_Г'Г' Г_Г+Г<Г_Г_Г  Г-Г  Г+Г_Г°ГўГ_Г°Г_","UTF8", "UTF8");
/*
            System.out.println(convertFromUTF8("Г_Г'Г' Г_Г+Г<Г_Г_Г  Г-Г  Г+Г_Г°ГўГ_Г°Г_"));
            System.out.println(convertToUTF8("Г_Г'Г' Г_Г+Г<Г_Г_Г  Г-Г  Г+Г_Г°ГўГ_Г°Г_"));
*/
            convert("test.txt", "testout.txt", "UTF-8", "ISO-8859-1");//windows-1251
            InputStream stream=new FileInputStream("testout.txt");
                // decode the data
            String decoded = decodeFromStream(stream, Charset.forName("windows-1251" +
                    ""));
            System.out.println("!!!!"+decoded);
/*
            if (!original.equals(decoded)) {
                throw new IllegalStateException("Lost data");
            }
*/

            String[] encodings = {
                    "Cp866",
                    "KOI8_R",
                    "Cp1250",
                    "Cp1251",
                    "Cp1252", // Windows-1252
                    "UTF8", // Unicode UTF-8
                    "UTF-16",
                    "UTF_32",
//                    "WIN",
                    "UTF-16BE", // Unicode UTF-16, big endian
                    "ISO-8859-1",
                    "ASCII"
            };
            String[]root=encodings;


/*
        for (String encoding1 : root) {
          for (String encoding : encodings) {

              System.out.println(decode("Г_Г'Г' Г_Г+Г<Г_Г_Г  Г-Г  Г+Г_Г°ГўГ_Г°Г_", encoding1,encoding));

          }
        }
*/

    //        System.out.println("!!!"+new String(.getBytes("UTF-8"), "Cp1251"));
/*
            String par=new String(("Г_Г'Г' Г_Г+Г<Г_Г_Г  Г-Г  Г+Г_Г°ГўГ_Г°Г_"));

            byte[] out = UnicodeUtil.convert(par.getBytes("UTF-8"), "ISO-8859-1"); //Sha
            System.out.println(new String(out));
*/
        }catch(Exception ex){}
        }

        }
