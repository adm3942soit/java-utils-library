package com.utils.encoding;



import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: PROGRAMMER II
 * Date: 13.11.13
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */
public class ResourceBundleControl extends ResourceBundle.Control {

        public ResourceBundle newBundle
                (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException
        {
            // The below is a copy of the default implementation.
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            ResourceBundle bundle = null;
            InputStream stream = null;
            if (reload) {
                URL url = loader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                try {
                    // Only this line is changed to make it to read properties files as UTF-8.

                    String strFile=ConvertChar.decodeFromStream(stream, Charset.forName("windows-1251"));
                    System.out.println("bundle "+strFile);

                    StringReader streamString=new StringReader(strFile);
                    bundle = new PropertyResourceBundle(streamString);//new InputStreamReader(stream, "UTF-8")
                } finally {
                    stream.close();
                }
            }
            return bundle;
        }

}
