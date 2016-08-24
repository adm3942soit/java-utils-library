package com.utils.files;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class MyPropertiesLoad {

	private Properties prop = new Properties();
	private String fileName="config/properties/config.properties";
    public final String[] terminalConfigKeys={"UILang","TechBreakLang"};
    HashMap<String, String> res = null;

    public HashMap<String, String> getRes() {
        return res;
    }

    public MyPropertiesLoad(){
        res=loadProperties(terminalConfigKeys);
    }
	public boolean IsUITechRu(){
        if(res==null){res=loadProperties(terminalConfigKeys);}
        if(res.get(terminalConfigKeys[1])!=null && res.get(terminalConfigKeys[1]).equals("ENGLISH"))return false;
        else return true;
    }

    public boolean IsUIAllRu(){
        if(res==null)res=loadProperties(terminalConfigKeys);
        if(res.get(terminalConfigKeys[0])!=null && res.get(terminalConfigKeys[0]).equals("ENGLISH"))return false;
        else return true;
    }
	public MyPropertiesLoad(String fileName){
		this.fileName=fileName;
	}

	public HashMap<String, String> loadProperties(String[] key){
		if(key==null){
			HashMap<String, String> res = new HashMap<String,String>();
			res.put("NO KEY", "NO VALUE");
			return res;
		}
		
		InputStream in=null;
		
		try {
			in = new FileInputStream(new File(new File(".").getCanonicalPath()+File.separator+fileName));
			prop.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		HashMap<String, String> res = new HashMap<String,String>();
		for(int i=0;i<key.length;i++){
			res.put(key[i], prop.getProperty(key[i]));
		}
		
		return res;
	}
}
