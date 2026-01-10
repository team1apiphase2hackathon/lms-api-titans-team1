package config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	 private static Properties properties = new Properties();

	    static {
	        try {
	            InputStream input =
	                    ConfigReader.class
	                            .getClassLoader()
	                            .getResourceAsStream("env.properties");

	            properties.load(input);

	        } catch (Exception e) {
	            throw new RuntimeException("❌ Failed to load env.properties");
	        }
	    }

	    public static String get(String key) {
	        return properties.getProperty(key);
	    }
	    
}
