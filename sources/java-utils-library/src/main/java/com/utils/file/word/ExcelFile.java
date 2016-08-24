package com.utils.file.word;

/**
 * Created with IntelliJ IDEA.
 * User: Asya
 * Date: 5/16/12
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */

import ua.edu.file.MyFiler;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.web.servlet.ModelAndView;


import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import static com.utils.debug.Printer.intoConsole;


public class ExcelFile {

    public static String fileName= MyFiler.getCurrentDirectory()+ File.separator
            +"DataBases"+ File.separator+"excelFile.xls";
    public static boolean checkExtension(String nameFile){
        String ext= Const.getExtensionFromName(nameFile);
        if(ext==null || !ext.contains("xls"))return false;
     return true;
    }

    public static boolean read(String nameFile)
    {
        if(!checkExtension(nameFile)) return false;
        try{

        Workbook wb = WorkbookFactory.create(new File(nameFile));
        Sheet mySheet = wb.getSheetAt(0);
        Iterator<Row> rowIter = mySheet.rowIterator();
        System.out.println(mySheet.getRow(1).getCell(0));

        }catch (InvalidFormatException ifex){
           intoConsole(ifex.getMessage());
          return false;

        }
        catch (IOException ex)
        {
            intoConsole(ex.getMessage());
            return false;
        }
      return true;
    }
    private static void showExcelData(List sheetData) {
        //
        // Iterates the data and print it out to the console.
        //
        for (int i = 0; i < sheetData.size(); i++) {
            List list = (List) sheetData.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell = (XSSFCell) list.get(j);
                System.out.print(cell.getRichStringCellValue().getString());
                if (j < list.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("");
        }
    }
    public static boolean create(String nameFile)
    {
       if(!checkExtension(nameFile))return false;
       FileInputStream fis = null;
       List sheetData = new ArrayList();
       try{
        fis = new FileInputStream(nameFile);
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
       // HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Excel Sheet");
//        HSSFRow rowhead = sheet.createRow((short) 0);
        Iterator rows = sheet.rowIterator();
           while (rows.hasNext()) {
               Row row = (XSSFRow) rows.next();
               Iterator cells = row.cellIterator();

               List data = new ArrayList();
               while (cells.hasNext()) {
                   Cell cell = (XSSFCell) cells.next();
                   data.add(cell);
               }

               sheetData.add(data);
           }

    }catch (FileNotFoundException ex){
           intoConsole(ex.getMessage());
           return false;
       }catch (IOException e)
       {
           intoConsole(e.getMessage());
           return false;
       }
    finally {
        if (fis != null) {
           try{
            fis.close();
           }catch (IOException e){
               intoConsole(e.getMessage());
               return false;

           }
        }
    }
    showExcelData(sheetData);
    return true;
    }
    private Vector importExcelSheet(ModelAndView mv)
    {
        Vector cellVectorHolder = new Vector();
        try
        {
            HSSFWorkbook myWorkBook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream("E:/Project/SpringHibernet/MultiplexTicketBookingNew/web/excelSheets/Country.xlsx")));
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();
            System.out.println(mySheet.getRow(1).getCell(0));
            while(rowIter.hasNext())
            {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                Vector cellStoreVector=new Vector();
                while(cellIter.hasNext())
                {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    cellStoreVector.addElement(myCell);
                }
                cellVectorHolder.addElement(cellStoreVector);
            }
        }
        catch (Exception e)
        {
            mv.addObject("msg", e.getMessage());
        }
        return cellVectorHolder;
    }

/*
    protected ModelAndView Submit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
    {
        ModelAndView mv=new ModelAndView();
        SessionFactory session;
        try
        {
            if(request.getParameter("import")!=null)
            {
                session=NewHibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();

                Vector dataHolder=importExcelSheet(mv);
                for (int i=0;i<dataHolder.size(); i++)
                {
                    Vector cellStoreVector=(Vector)dataHolder.elementAt(i);
                    for (int j=0; j < cellStoreVector.size();j++)
                    {
                        HSSFCell myCell = (HSSFCell)cellStoreVector.elementAt(j);
                        String st = myCell.toString();
                        System.out.println(st.substring(0,1)+"\t");
                    }
                    System.out.println();
                }

                session.flush();
                session.getTransaction().commit();
            }
        }
        catch (Exception e)
        {
            mv.addObject("msg", e.getMessage());
        }
        return mv;
    }
*/
    public static void main(String[] args) {
        try {
            try {
                Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            }
            catch (Exception e) {
                System.err.println ("Unable to load driver.");
                e.printStackTrace ();
                return;
            }
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test", "root", "root");
            PreparedStatement psmnt = null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("Select * from student");

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Roll No");
            rowhead.createCell((short) 1).setCellValue("Name");
            rowhead.createCell((short) 2).setCellValue("Class");
            rowhead.createCell((short) 3).setCellValue("Marks");
            rowhead.createCell((short) 4).setCellValue("Grade");

            int index = 1;
            while (rs.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(rs.getInt(1));
                row.createCell((short) 1).setCellValue(rs.getString(2));
                row.createCell((short) 2).setCellValue(rs.getString(3));
                row.createCell((short) 3).setCellValue(rs.getInt(4));
                row.createCell((short) 4).setCellValue(rs.getString(5));
                index++;
            }
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            System.out.println("Data is saved in excel file.");
            rs.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}