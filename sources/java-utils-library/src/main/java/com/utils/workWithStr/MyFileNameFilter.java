package com.utils.workWithStr;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by PROGRAMMER II on 16.12.13.
 */
public class MyFileNameFilter implements FilenameFilter{

        private String filter="";
        private boolean ends=false;//starts
        private boolean starts=true;

        public MyFileNameFilter(String filtr, boolean ends){

            this.filter=filtr;
            this.ends=ends;
            this.starts=!this.ends;
        }
        @Override
        public boolean accept(File dir, String fileName) {
            return this.starts?fileName.startsWith(this.filter):fileName.endsWith(this.filter);
        }


}
