package reporting;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ReadProperties {

	
	private String pathToResources = System.getProperty("user.dir")+
		File.separator + "src" +
		File.separator + "test" +
		File.separator + "resources" +
		File.separator + "properties";
	 
	public String getProperty(String propFilename, String sKey) {
		Properties props = new Properties();
		String sVal = "";
		try (FileInputStream fs = new FileInputStream(propFilename)) {
			
			props.load(fs);
			sVal = props.getProperty(sKey);
			if(!Objects.equals(sVal, "")) {
				return sVal;
			}
		} catch (IOException var10)
		{
			var10.printStackTrace();
			return sVal;
		}
		return null;
	}
	
	
	public  String getApplicationProperty(String sKey) {
		String sFilePath = pathToResources + File.separator + "application.properties";		
		return getProperty(sFilePath,sKey);
	}

}

