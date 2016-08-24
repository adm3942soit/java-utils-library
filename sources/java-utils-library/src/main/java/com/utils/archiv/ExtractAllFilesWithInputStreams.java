/*
 * Copyright 2010 Srikanth Reddy Lingala  
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package com.utils.archiv;

import ua.edu.file.MyFiler;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.unzip.UnzipUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Example demonstrating the use of InputStreams 
 * to extract files from the ZipFile
 */
public class ExtractAllFilesWithInputStreams {
	
	private final int BUFF_SIZE = 40960000;
	
	public ExtractAllFilesWithInputStreams(String fullNameZip, String whereDir) {
		
		ZipInputStream is = null;
		OutputStream os = null;
		
		try {
			ZipFile zipFile = new ZipFile(fullNameZip);
			String destinationPath = whereDir;
			
/*
			// If zip file is password protected then set the password
			if (zipFile.isEncrypted()) {
				zipFile.setPassword("password");
			}
*/

			//Get a list of FileHeader. FileHeader is the header information for all the
			//files in the ZipFile
			List fileHeaderList = zipFile.getFileHeaders();
			
			// Loop through all the fileHeaders
			for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
				if (fileHeader != null) {
					
					//Build the output file
					String outFilePath = destinationPath +
                            System.getProperty("file.separator") +
                            fileHeader.getFileName();
					File outFile = new File(outFilePath);
					
					//Checks if the file is a directory
					if (fileHeader.isDirectory()) {
						//This functionality is up to your requirements
						//For now I create the directory
						outFile.mkdirs();
						continue;
					}
					
					//Check if the directories(including parent directories)
					//in the output file path exists
					File parentDir = outFile.getParentFile();
					if (!parentDir.exists()) {
						parentDir.mkdirs();
					}
					
					//Get the InputStream from the ZipFile
					is = zipFile.getInputStream(fileHeader);
					//Initialize the output stream
					os = new FileOutputStream(outFile);
					
					int readLen = -1;
					byte[] buff = new byte[BUFF_SIZE];
					
					//Loop until End of File and write the contents to the output stream
					while ((readLen = is.read(buff)) != -1) {
						os.write(buff, 0, readLen);
					}
					
					//Please have a look into this method for some important comments
					closeFileHandlers(is, os);
					
					//To restore File attributes (ex: last modified file time, 
					//read only flag, etc) of the extracted file, a utility class
					//can be used as shown below
					UnzipUtil.applyFileAttributes(fileHeader, outFile);
					
					System.out.println("Done extracting: " + fileHeader.getFileName());
				} else {
					System.err.println("fileheader is null. Shouldn't be here");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeFileHandlers(is, os);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void closeFileHandlers(ZipInputStream is, OutputStream os) throws IOException{
		//Close output stream
		if (os != null) {
			os.close();
			os = null;
		}
		
		//Closing inputstream also checks for CRC of the the just extracted file.
		//If CRC check has to be skipped (for ex: to cancel the unzip operation, etc)
		//use method is.close(boolean skipCRCCheck) and set the flag,
		//skipCRCCheck to false
		//NOTE: It is recommended to close outputStream first because Zip4j throws 
		//an exception if CRC check fails
		if (is != null) {
			is.close();
			is = null;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        File theZIPFile=new File(MyFiler.getCurrentDirectory()+File.separator+"install"+File.separator+"install.zip");
        File theTargetFolder=new File(MyFiler.getCurrentDirectory()+File.separator+ "install");

		new ExtractAllFilesWithInputStreams(theZIPFile.getAbsolutePath(), theTargetFolder.getAbsolutePath());
	}

}
