package Pegas;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();
    static{
        loadProperties();
    }
    private PropertiesUtil(){};
    private static void loadProperties(){
       try(InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")){
           PROPERTIES.load(is);
       }catch(IOException ex){
           throw new RuntimeException("Can't load properties");
       }
    }
    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }
}
