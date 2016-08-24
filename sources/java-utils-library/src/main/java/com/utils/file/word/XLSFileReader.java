package com.utils.file.word;

/**
 * Created with IntelliJ IDEA.
 * User: Asya
 * Date: 4/26/12
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */


import com.utils.file.Filer;
import com.utils.file.word.apache.CreatorWordDoc;
import ua.edu.file.MyFiler;
import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;


public class XLSFileReader {

    public static String nameXls;
    static Workbook wb;
    /*constructor create file*/
    public XLSFileReader(String nameXLSFile) throws FileNotFoundException,
                                                    IOException,
            InvalidFormatException {

        InputStream inp = new FileInputStream(nameXLSFile);
        wb = WorkbookFactory.create(inp);


    }
    public  boolean createXls(String nameXLSFile){
       // CellReference crInput = new CellReference(cell);
        Sheet sheet = (Sheet) wb.getSheetAt(0);
/*
        Row rowInput = sheet.getRow(crInput.getRow());
        Cell cellInput = rowInput.getCell(crInput.getCol());
        cellInput.setCellValue(val);
*/
        try{
        FileOutputStream fileOut = new FileOutputStream(nameXLSFile);
        wb.write(fileOut);
        fileOut.close();
        }catch (Exception x){
            x.printStackTrace();
        }
/*
        double cellContents = cellInput.getNumericCellValue();
        inp.close();
        return cellContents;
*/
        return true;
    }
    public static void main(String[]args){
        //CreatorWordDoc.createWordDoc(MyFiler.getCurrentDirectory() + File.separator + "firstXls.xls", "Hello from library!");
        if(!new File(MyFiler.getCurrentDirectory() + File.separator + "firstXls.xls").exists()){
            Filer.createFile(MyFiler.getCurrentDirectory() + File.separator + "firstXls.xls");
        }
        try {
            XLSFileReader reader = new XLSFileReader(MyFiler.getCurrentDirectory() + File.separator + "firstXls.xls");
            //ExcelFile.create(MyFiler.getCurrentDirectory() + File.separator + "firstXls.xls");
            reader.createXls(MyFiler.getCurrentDirectory() + File.separator + "firstXls.xls");
        }catch(Exception ex){ex.printStackTrace();}
    }
}
