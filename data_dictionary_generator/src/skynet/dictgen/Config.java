package skynet.dictgen;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Config{

	static Properties prop = new Properties();
	
	public static void load() throws IOException{
		prop.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
	}
	
	public static String get(String key){
		String result= prop.getProperty(key);
		try {
			return new String(result.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return "";
	}
	
}
