package dumpLog4j;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationBuilder;

import dumpSuit.App;

public class App {
	public static final  Logger logger = LogManager.getLogger(App.class);
	

	public static void main(String[] args) {
		initApp();	
	    System.out.println("Working Directory = " + System.getProperty("user.dir"));
	 
		logger.error("This is error message comming from ");
		logger.info("This is info message comming from ");
		logger.trace("This is trace message comming from ");
	}

	public static Properties convertResourceBundleToProperties(ResourceBundle resource) {
		Enumeration iter;
		if (resource == null) {
			return null;
		}
		Properties props = new Properties();
		iter = resource.getKeys();
		while (iter.hasMoreElements()) {
			String key = (String) iter.nextElement();
			String value = resource.getString(key);
			props.put(key, value);
		}
		return props;
	}

	public static void initApp() {
		ResourceBundle bundle = ResourceBundle.getBundle("log4j");

		Properties properties = convertResourceBundleToProperties(bundle);
			
		
		
		LoggerContext context = (LoggerContext)LogManager.getContext(false);
		Configuration config = new PropertiesConfigurationBuilder()
		            .setConfigurationSource(ConfigurationSource.NULL_SOURCE)
		            .setRootProperties(properties)
		            .setLoggerContext(context)
		            .build();
		 context.setConfiguration(config);
		 Configurator.initialize(config);

	}
}