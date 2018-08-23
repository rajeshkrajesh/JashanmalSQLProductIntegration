package com.rixyncs.Sqlconnector;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
//common function used in the package are available here

public class Utility {
	
			public static Properties loadProperties() throws IOException{
			
		    Properties mainProperties = new Properties();
			String path ="C:/Users/RAAA/Desktop/Jashan/JashanmalIntegration/Rix.properties";
			//String path ="./Rix.properties";
		    FileInputStream file = new FileInputStream(path);
		    mainProperties.load(file);
		    file.close();
		    return mainProperties;
		 }
	}





