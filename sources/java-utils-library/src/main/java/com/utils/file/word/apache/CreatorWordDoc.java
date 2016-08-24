package com.utils.file.word.apache;

import com.utils.debug.Printer;
import com.utils.file.word.Const;
import com.utils.file.word.ExcelFile;
import com.utils.file.word.Presentation;
import ua.edu.file.MyFiler;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.poifs.property.DocumentProperty;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static com.utils.file.word.Const.flag;
import static com.utils.file.word.Const.getExtensionFromName;

/**
 * Created by oksana.dudnik on 10/8/2015.
 */
public class CreatorWordDoc {


    public static boolean createWordDoc(String name, String content) {
        Printer.setDEBUG(true);
        String ext = getExtensionFromName(name);
        if (!name.contains(MyFiler.slash)) {
            name = File.separator + name;
        }

        if (ext.equals("txt")) {
            return MyFiler.createFile(name);
        } else if (ext.equals("doc") || ext.equals("docx")) {

            POIFSFileSystem fs = new POIFSFileSystem();
            DirectoryEntry directory = fs.getRoot();
            Printer.debug("DocReader", "createWordDoc", directory.toString());
            try {


                directory.createDocument("WordDocument", new ByteArrayInputStream(
                        content != null ? content.getBytes() : new String("").getBytes()));


                FileOutputStream out = new FileOutputStream(name);
                Printer.debug("DocReader", "createWordDoc", name);
                fs.writeFilesystem(out);
                out.flush();
                out.close();
                return Const.ok;
            } catch (IOException ex1) {
                Printer.debug(ex1.getMessage());
                return Const.err;
            }

        } else {
            if(flag==Const.XLS || flag==Const.XLSX){
                return ExcelFile.create(name);
            }
            if(flag==Const.PPT || flag==Const.PPTX){
                return Presentation.createPresentation(name);
            }
            if(flag==Const.PDF){return Const.err;}
            if(flag==Const.DJVU){return Const.err;}
        }

        return Const.ok;
    }
}
