package vitalijus.config;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";

    public static final String DB_LIMIT = "db.limit";
    private static final Properties properties = new Properties();

    public synchronized static String getProperties(String name){
        if (properties.isEmpty()){
            try (InputStream inputStream = Config.class.getClassLoader()
                    .getResourceAsStream("dao.properties")){
                properties.load(inputStream);
            }catch (Exception ex){
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
        return properties.getProperty(name);
    }
}
