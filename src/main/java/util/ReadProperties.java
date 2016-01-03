package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sachin on 11/14/2015.
 */
public class ReadProperties {

    private static final Properties props = loadProperties();

    private static final Properties loadProperties(){
        Properties props = new Properties();
        InputStream is = ReadProperties.class.getResourceAsStream("/app.properties");
        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static final String getProp(String key){
        return props.getProperty(key);
    }
}
