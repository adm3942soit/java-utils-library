package com.utils.archiv;

import com.utils.date.Dater;
import com.utils.file.Filer;
import com.utils.workWithStr.MyFileNameFilter;
import ua.edu.file.MyFiler;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;

//import java.util.zip.ZipFile;

/**
 * Created by PROGRAMMER II on 16.12.13.
 */
public class ZipArchiv {

    public static void extractFile(InputStream inStream, OutputStream outStream) throws IOException {
        byte[] buf = new byte[1024];
        int l;
        while ((l = inStream.read(buf)) >= 0) {
            outStream.write(buf, 0, l);
        }
        inStream.close();
        outStream.close();
    }

    public static void unzip(String source) {
/*
        String source = "some/compressed/file.zip";
        String destination = "some/destination/folder";
        String password = "password";
*/
        Enumeration enumEntries;
        try {
            ZipFile zipFile = new ZipFile(source);
/*
            if (zipFile.
                    isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination);
*/
            enumEntries = zipFile.entries();
            while (enumEntries.hasMoreElements()) {
                ZipEntry zipentry = (ZipEntry) enumEntries.nextElement();
                if (zipentry.isDirectory()) {
                    System.out.println("Name of Extract directory : " + zipentry.getName());
                    if (!(new File(zipentry.getName())).mkdir())
                        System.err.println("can not create Extract directory : " + zipentry.getName());
                    continue;
                }
                System.out.println("Name of Extract file : " + zipentry.getName());

                extractFile(zipFile.getInputStream(zipentry), new FileOutputStream(zipentry.getName()));
            }
            zipFile.close();

        } catch (ZipException e) {
            System.err.println(e.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void unzip(String source, String where) {
/*
        String source = "some/compressed/file.zip";
        String destination = "some/destination/folder";
        String password = "password";
*/
        Enumeration enumEntries;
        try {
            ZipFile zipFile = new ZipFile(source);
/*
            if (zipFile.
                    isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination);
*/
            enumEntries = zipFile.entries();
            while (enumEntries.hasMoreElements()) {
                ZipEntry zipentry = (ZipEntry) enumEntries.nextElement();
                if (zipentry.isDirectory()) {
                    System.out.println("Name of Extract directory : " + where + File.separator + zipentry.getName());
                    if (!(new File(zipentry.getName())).mkdir())
                        System.err.println("can not create Extract directory : " + zipentry.getName());
                    continue;
                }
                System.out.println("Name of Extract file : " + where + File.separator + zipentry.getName());

                extractFile(zipFile.getInputStream(zipentry), new FileOutputStream(where + File.separator + zipentry.getName()));
            }
            zipFile.close();

        } catch (ZipException e) {
            System.err.println(e.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void mkdirs(File outdir, String path) {
        File d = new File(outdir, path);
        if (!d.exists())
            d.mkdirs();
    }

    private static String dirpart(String name) {
        int s = name.lastIndexOf(File.separatorChar);
        return s == -1 ? null : name.substring(0, s);
    }

    private static final int BUFFER_SIZE = 40960000;

    private static void extractFile(ZipInputStream in, File outdir, String name) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outdir, name)));
        int count = -1;
        while ((count = in.read(buffer)) != -1)
            out.write(buffer, 0, count);
        out.close();
    }

    private static void extractFile(ZipInputStream in, File outdir, String name, String where) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outdir, name)));
        int count = -1;
        while ((count = in.read(buffer)) != -1)
            out.write(buffer, 0, count);
        out.close();
    }

    public static void unzipToDir(String source, String dir) {
/*
        String source = "some/compressed/file.zip";
        String destination = "some/destination/folder";
        String password = "password";
*/
        Enumeration enumEntries;
        try {
            // ZipFile zipFile = new ZipFile(source);
            ZipInputStream zin = new ZipInputStream(new FileInputStream(new File(source)));
/*
            if (zipFile.
                    isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination);
*/

            ZipEntry zipentry;
            //enumEntries = zipFile.entries();
            while ((zipentry = zin.getNextEntry()) != null) {
                // ZipEntry zipentry = (ZipEntry) enumEntries.nextElement();
                String name = zipentry.getName();
                if (zipentry.isDirectory()) {
                    System.out.println("Name of Extract directory : " + zipentry.getName());
/*
                    if(!(new File(dir+File.separator+zipentry.getName())).mkdir())
                        System.err.println("can not create Extract directory : " + zipentry.getName());
*/
                    mkdirs(new File(dir), name);
                    continue;
                }


                String direct = dirpart(name);
                System.out.println("Name of Extract file : " + direct);
                if (direct != null)
                    mkdirs(new File(dir), direct);
                System.out.println("Name of Extract file : " + name);
                extractFile(zin, new File(dir), name);
            }
            zin.close();
/*
            System.out.println("Name of Extract file : " + dir + File.separator + zipentry.getName());

                File destinationFilePath=new File(dir+File.separator+zipentry.getName());
                if(destinationFilePath.exists())
                 extractFile(zipFile.getInputStream(zipentry),
                        new FileOutputStream(destinationFilePath));
                else {
                    destinationFilePath.getParentFile().mkdirs();
                    extractFile(zipFile.getInputStream(zipentry),
                            new FileOutputStream(destinationFilePath));
                }
            }
            zipFile.close();
*/
        } catch (ZipException e) {
            System.err.println(e.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }


    public static boolean unzipToDirfromJar(String source, String dir) {
/*
        String source = "some/compressed/file.zip";
        String destination = "some/destination/folder";
        String password = "password";
*/
        Enumeration enumEntries;
        try {
            ZipFile zipFile = new ZipFile(source);

            InputStream is = ZipArchiv.class.getResourceAsStream(source);
            // ZipInputStream zis = new ZipInputStream(is);

            // ZipEntry zipentry = zis.getNextEntry();
            enumEntries = zipFile.entries();
            ZipEntry zipentry = null;
            while (enumEntries.hasMoreElements()) {
                zipentry = (ZipEntry) enumEntries.nextElement();
                if (!zipentry.getName().equals("install.zip")) continue;
                else break;
            }


            if (zipentry != null && zipentry.getName().equals("install.zip")) {
/*
            if (zipentry.isDirectory()) {
                System.out.println("Name of Extract directory : " + zipentry.getName());
                if(!(new File(dir+File.separator+zipentry.getName())).mkdir())
                    System.preferences.txt.println("can not create Extract directory : " + zipentry.getName());
                continue;
            }
*/
                System.out.println("Name of Extract file : " + dir + File.separator + zipentry.getName());

                File destinationFilePath = new File(dir + File.separator + zipentry.getName());
                if (destinationFilePath.exists())
                    extractFile(zipFile.getInputStream(zipentry)
                            , new FileOutputStream(destinationFilePath));
                else {
                    destinationFilePath.getParentFile().mkdirs();
                    extractFile(zipFile.getInputStream(zipentry),
                            new FileOutputStream(destinationFilePath));
                }
                zipFile.close();
                return true;
            }
            zipFile.close();

        } catch (ZipException e) {
            System.err.println(e.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public static void packZip(File output, List<File> sources, boolean deleteFiles) throws IOException {
        if (sources == null || sources.isEmpty()) return;
        System.out.println("Packaging to " + output.getName());
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(output));
        zipOut.setLevel(Deflater.DEFAULT_COMPRESSION);

        File emptydir = new File(sources.get(0).getParent());

        for (File source : sources) {
            if (source.isDirectory()) {
                zipDir(zipOut, "", source);
                if (deleteFiles || source.listFiles() == null) {
                    if (!source.delete()) {
                        System.err.println("can't  to delete directory!");
                    }
                }
            } else {
                zipFile(zipOut, "", source);
                if (deleteFiles) source.delete();

            }

        }
        if (emptydir.exists()) {
            File[] list = emptydir.listFiles();
            if (list.length == 0) {
                emptydir.delete();
            }
        }
        zipOut.flush();
        zipOut.close();
        System.out.println("Done");
    }


    public static void packZip(File output, List<File> sources, boolean deleteFiles, String curDir) throws IOException {
        if (sources == null) return;
        System.out.println("Packaging to " + output.getName());
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(curDir + File.separator + output));
        zipOut.setLevel(Deflater.DEFAULT_COMPRESSION);

        for (File source : sources) {
            if (source.isDirectory()) {
                zipDir(zipOut, "", source);
                if (deleteFiles || source.listFiles() == null) {
                    if (!source.delete()) {
                        System.err.println("can't  to delete directory!");
                    }
                }
            } else {
                zipFile(zipOut, "", source);
                if (deleteFiles) source.delete();

            }

        }
        zipOut.flush();
        zipOut.close();
        System.out.println("Done");
    }

    public static void packZipDir(File output, File sourceDir, boolean deleteFiles) throws IOException {
        if (sourceDir == null || !sourceDir.exists()) return;
        File[] listFiles = sourceDir.listFiles();
        if (listFiles == null || listFiles.length == 0) return;

        System.out.println("Packaging to " + output.getName());
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(output));
        zipOut.setLevel(Deflater.DEFAULT_COMPRESSION);


        for (File source : listFiles) {
            System.out.println(source.getAbsolutePath());
            if (source.isDirectory()) {
                zipDir(zipOut, "", source);
                if (deleteFiles || source.listFiles() == null) {
                    if (!source.delete()) {
                        System.err.println("can't  to delete directory!");
                    }
                }
            } else {
                zipFile(zipOut, "", source);
                if (deleteFiles) source.delete();

            }

        }
        zipOut.flush();
        zipOut.close();
        System.out.println("Done");
    }

    private static void zipDir(ZipOutputStream zos, String path, File dir) throws IOException {

        if (!dir.canRead()) {
            System.out.println("Cannot read " + dir.getCanonicalPath() + " (maybe because of permissions)");
            return;
        }


        File[] files = dir.listFiles();
        path = buildPath(path, dir.getName());
        System.out.println("Adding Directory " + path);

        for (File source : files) {
            if (source.isDirectory()) {
                zipDir(zos, path, source);
            } else {
                zipFile(zos, path, source);
            }
        }

        System.out.println("Leaving Directory " + path);
    }

    private static void zipFile(ZipOutputStream zos, String path, File file) throws IOException {

        if (!file.canRead()) {
            System.out.println("Cannot read " + file.getCanonicalPath() + " (maybe because of permissions)");
            return;
        }


        System.out.println("Compressing " + file.getName());
        zos.putNextEntry(new ZipEntry(buildPath(path, file.getName())));

        FileInputStream fis = new FileInputStream(file);

        byte[] buffer = new byte[4092];
        int byteCount = 0;
        while ((byteCount = fis.read(buffer)) != -1) {
            zos.write(buffer, 0, byteCount);
            System.out.print('.');
            System.out.flush();
        }
        System.out.println();

        fis.close();
        zos.closeEntry();
    }

    private static String buildPath(String path, String file) {
        if (path == null || path.isEmpty()) {
            return file;
        } else {
            return path + File.separator + file;
        }
    }

    public static List<File> backupFilesToFolder(String filtrName) {
        ArrayList<File> files = new ArrayList<File>();
        String firstLetter = filtrName.substring(0, 1);
        String folder = "backup" + firstLetter.toUpperCase() + filtrName.substring(1);
        File folderDir = new File(folder);
        if (!folderDir.mkdir()) {
            System.err.println("Directory " + folder + " already exist!");

            File[] list = folderDir.listFiles();
            if (list == null || list.length == 0) {
                folderDir.delete();
                System.out.println("Directory " + folder + " deleted!");
            } else for (int i = 0; i < list.length; i++) files.add(list[i]);

            return files;
        } else {
            System.out.println("Directory " + folder + " created!");
        }
        System.out.println(Dater.getDateForLog1() + " created directory " + folder);
        File dir = new File(MyFiler.getCurrentDirectory());
        File[] listFiles = dir.listFiles(new MyFileNameFilter(filtrName, false));
        if (listFiles == null || listFiles.length == 0) {
            listFiles = dir.listFiles(new MyFileNameFilter(filtrName, true));
        }
        int ind = 0;
        System.out.println("renaming " + listFiles.length);
        for (int i = 0; i < listFiles.length; i++) {
            System.out.println("renamed " + listFiles[i].getName());
            if (listFiles[i].getName().indexOf(Dater.getDateForLog1()) == -1
                    ) {
                listFiles[i].renameTo(new File(folderDir + File.separator + listFiles[i].getName()));
                System.out.println("renamed to" + folderDir + File.separator + listFiles[i].getName());
                files.add(ind, new File(folderDir + File.separator + listFiles[i].getName()));
                ind++;
            }
        }

        return files;
    }

    public static List<File> backupAllFilesToFolder(String filtrName) {
        ArrayList<File> files = new ArrayList<File>();
        String firstLetter = filtrName.substring(0, 1);
        String folder = "backup" + firstLetter.toUpperCase() + filtrName.substring(1);
        File folderDir = new File(folder);
        if (!folderDir.mkdir()) {
            System.err.println("Directory " + folder + " already exist!");

            File[] list = folderDir.listFiles();
            if (list == null || list.length == 0) {
                folderDir.delete();
                System.out.println("Directory " + folder + " deleted!");
            } else for (int i = 0; i < list.length; i++) files.add(list[i]);

            return files;
        } else {
            System.out.println("Directory " + folder + " created!");
        }
        System.out.println(Dater.getDateForLog1() + " created directory " + folder);
        File dir = new File(MyFiler.getCurrentDirectory());
        File[] listFiles = dir.listFiles(new MyFileNameFilter(filtrName, false));
        if (listFiles == null || listFiles.length == 0) {
            listFiles = dir.listFiles(new MyFileNameFilter(filtrName, true));
        }
        int ind = 0;
        System.out.println("renaming " + listFiles.length);
        for (int i = 0; i < listFiles.length; i++) {
            System.out.println("renamed " + listFiles[i].getName());
            try {

                Filer.copy(listFiles[i], new File(folderDir + File.separator + listFiles[i].getName()));
                //   renameTo(new File(folderDir + File.separator + listFiles[i].getName()));
                System.out.println("renamed to" + folderDir + File.separator + listFiles[i].getName());
                files.add(ind, new File(folderDir + File.separator + listFiles[i].getName()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            ind++;
//            }
        }

        return files;
    }

    public static List<File> backupFilesToFolder(String filtrName, String curDir) {
        ArrayList<File> files = new ArrayList<File>();
        String firstLetter = filtrName.substring(0, 1);
        int index = filtrName.indexOf(".");
        String folder = "backup" + firstLetter.toUpperCase() + (index == -1 ? filtrName.substring(1) : filtrName.substring(1, index));
        File folderDir = new File(MyFiler.getCurrentDirectory() + File.separator + curDir + File.separator + folder);
        if (!folderDir.mkdir()) {
            System.err.println("Directory " + folder + " already exist!");

            File[] list = folderDir.listFiles();
            if (list == null || list.length == 0) {
                folderDir.delete();
                System.out.println("Directory " + folder + " deleted!");
            } else for (int i = 0; i < list.length; i++) files.add(list[i]);


            return files;
        } else {
            System.out.println("Directory " + folder + " created!");
        }
        System.out.println(Dater.getDateForLog1() + " created directory " + folder);

        File dir = new File(MyFiler.getCurrentDirectory() + File.separator + curDir);

        File[] listFiles = dir.listFiles(new MyFileNameFilter(filtrName, false));

        if (listFiles.length == 0) listFiles = dir.listFiles(new MyFileNameFilter(filtrName, true));

        int ind = 0;

        System.out.println("renaming " + listFiles.length);
        for (int i = 0; i < listFiles.length; i++) {
            System.out.println("renamed " + listFiles[i].getName());
            if (listFiles[i].getName().indexOf(Dater.getDateForLog1()) == -1
                    ) {
                listFiles[i].renameTo(new File(folderDir + File.separator + listFiles[i].getName()));
                System.out.println("renamed to" + folderDir + File.separator + listFiles[i].getName());
                files.add(ind, new File(folderDir + File.separator + listFiles[i].getName()));
                ind++;
            }
        }

        return files;
    }

    public static void archivAdminPanelLog(String nameFileZip) {
        try {
            File zip = new File(nameFileZip);

            boolean checkDateZip = false;

            if (zip.exists()) {

                java.util.Date dateZip = (new java.util.Date(zip.lastModified()));

                java.util.Date today = new java.util.Date();

                if (dateZip.getDate() < today.getDate()) {
                    ZipArchiv.unzip(zip.getAbsolutePath());
                }
                checkDateZip = dateZip.getDate() != today.getDate();
            }


            if (!zip.exists() || checkDateZip) {
                ZipArchiv.packZip(new File(nameFileZip), ZipArchiv.backupFilesToFolder("logAdminPanel"), true);
                int index = nameFileZip.indexOf(".");
                File dir = new File(nameFileZip.substring(0, index));
                if (dir.listFiles() == null || dir.listFiles().length == 0) dir.delete();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static void archivLog(String nameFileZip, String filterName) {
        try {
            File zip = new File(nameFileZip);

            boolean checkDateZip = false;

            if (zip.exists()) {

                java.util.Date dateZip = (new java.util.Date(zip.lastModified()));

                java.util.Date today = new java.util.Date();

                if (dateZip.getDate() < today.getDate()) {
                    ZipArchiv.unzip(zip.getAbsolutePath());
                }
                checkDateZip = dateZip.getDate() != today.getDate();
            }


            if (!zip.exists() || checkDateZip) {
                System.out.println("backupFilesFolder " + filterName);
                ZipArchiv.packZip(new File(nameFileZip), ZipArchiv.backupFilesToFolder(filterName), true);
                int index = nameFileZip.indexOf(".");
                File dir = new File(nameFileZip.substring(0, index));
                if (dir.listFiles() == null || dir.listFiles().length == 0) dir.delete();

            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }


    }

    public static void archiv(String nameFileZip, String filterName) {
        try {
            File zip = new File(nameFileZip);

           // boolean checkDateZip = false;

            if (zip.exists()) {

/*
                java.util.Date dateZip = (new java.util.Date(zip.lastModified()));

                java.util.Date today = new java.util.Date();

                if (dateZip.getDate() < today.getDate()) {
                    ZipArchiv.unzip(zip.getAbsolutePath());
                }
                checkDateZip = dateZip.getDate() != today.getDate();
*/
               zip.delete();
            }


            if (!zip.exists() ) {//|| checkDateZip
                System.out.println("backupFilesFolder " + filterName);
                ZipArchiv.packZip(new File(nameFileZip), ZipArchiv.backupAllFilesToFolder(filterName), true);
                int index = nameFileZip.indexOf(".");
                File dir = new File(nameFileZip.substring(0, index));
                if (dir.listFiles() == null || dir.listFiles().length == 0) dir.delete();

            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }


    }

    public static void archivLog(String nameFileZip, String filterName, String curDir) {
        if (curDir != null && !curDir.isEmpty()) {
            File current = new File(MyFiler.getCurrentDirectory() + File.separator + curDir);
            if (!current.exists()) current.mkdir();

        }
        try {
            File zip = new File(MyFiler.getCurrentDirectory() + File.separator + curDir + File.separator + nameFileZip);

            boolean checkDateZip = false;

            if (zip.exists()) {

                java.util.Date dateZip = (new java.util.Date(zip.lastModified()));

                java.util.Date today = new java.util.Date();

                if (dateZip.getDate() < today.getDate()) {
                    ZipArchiv.unzip(zip.getAbsolutePath(), MyFiler.getCurrentDirectory() + File.separator + curDir);
                }
                checkDateZip = dateZip.getDate() != today.getDate();
            }


            if (!zip.exists() || checkDateZip) {
                System.out.println("backupFilesFolder " + filterName);
                ZipArchiv.packZip(new File(nameFileZip),
                        ZipArchiv.backupFilesToFolder(filterName, curDir),
                        true, curDir);
                int index = filterName.indexOf(".");
                String firstLetter = filterName.substring(0, 1);
                String dirS = "backup" + firstLetter.toUpperCase() + (index == -1 ? filterName.substring(1) : filterName.substring(1, index));

/*
                int index=nameFileZip.indexOf(".");
*/
                File dir = new File(MyFiler.getCurrentDirectory() + File.separator + curDir + File.separator + dirS);
/*
                        MyFiler.getCurrentDirectory()+File.separator+curDir+File.separator+
                            "backup"+nameFileZip.substring(0,1).toUpperCase()+nameFileZip.substring(1,index));
*/
                System.out.println("backupFilesFolder " + dir.getAbsolutePath());
                if (dir.exists() && (dir.listFiles() == null || dir.listFiles().length == 0)) dir.delete();

            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }


    }

    public static void main(String[] args) {
        try {
/*
           packZip(new File("backupLogAdminPanel.zip"),backupFilesToFolder("logAdminPanel"),true);
           File dir=new File("backupLogAdminPanel");
           if(dir.listFiles()==null || dir.listFiles().length==0)dir.delete();
*/
            archivLog("backupOSDefaultPrinterLog.zip",
                    "OSDefaultPrinterLog", "logs/HW/printer/system");
/*
           unzip(MyFiler.getCurrentDirectory()+File.separator+"logs"+File.separator+"commands"+File.separator+"backup_commands.zip",
                   MyFiler.getCurrentDirectory()+File.separator+"logs"+File.separator+"commands");
*/
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

}



