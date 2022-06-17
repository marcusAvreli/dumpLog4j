package dumpLog4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.properties.PropertiesConfiguration;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationBuilder;

/**
 * Hello world!
 *
 */
public class App {
	public static Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) {
		String loadFrom = "resourceBundle";
		if (loadFrom.equals("properties")) {
			loadFromProperties();
		}
		if (loadFrom.equals("xml")) {
			loadFromXml();
		}
		if (loadFrom.equals("propertiesObject")) {
			loadFromPropertiesObject();
		}
		if (loadFrom.equals("resourceBundle")) {
			loadFromResourceBundle();
		}

		// Logger logger = LogManager.getRootLogger();

		logger.error("This is error message comming from " + loadFrom);
		logger.info("This is info message comming from " + loadFrom);
		logger.trace("This is trace message comming from " + loadFrom);

	}

	public static void loadFromProperties() {
		String propertiesFile = "./resources/log4j2.properties";
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(propertiesFile), new File(propertiesFile));
			Configurator.initialize(null, source);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadFromXml() {
		String logConfigurationFile = "./resources/log4j2.xml";
		System.out.println("Hello World!");
		InputStream inputStream = null;

		ConfigurationSource source = null;
		try {
			inputStream = new FileInputStream(logConfigurationFile);
			source = new ConfigurationSource(inputStream);
			Configurator.initialize(null, source);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadFromPropertiesObject() {
		Properties properties = new Properties();

		properties.setProperty("status", "TRACE");

		properties.setProperty("appenders", "CONSOLE");

		properties.setProperty("appender.CONSOLE.name", "ConsoleAppender");
		properties.setProperty("appender.CONSOLE.type", "Console");
		properties.setProperty("appender.CONSOLE.layout.type", "PatternLayout");
		properties.setProperty("appender.CONSOLE.layout.pattern", "%r [%t] %p %c %notEmpty{%x }- %m%n");

		properties.setProperty("rootLogger.level", "ALL");
		properties.setProperty("rootLogger.appenderRefs", "theConsoleRef");
		properties.setProperty("rootLogger.appenderRef.theConsoleRef.ref", "ConsoleAppender");

		PropertiesConfigurationBuilder pcb = new PropertiesConfigurationBuilder();

		// Q1: Is it correct to call setConfigurationSource with 'null'?
		pcb.setConfigurationSource(null).setRootProperties(properties);
		PropertiesConfiguration config = pcb.build();
		Configurator.initialize(config);
	}

	public static void loadFromResourceBundle() {
		
		ResourceBundle bundle = ResourceBundle.getBundle("log4j2");

		Properties properties = convertResourceBundleToProperties(bundle);
		PropertiesConfigurationBuilder pcb = new PropertiesConfigurationBuilder();
		
		pcb.setConfigurationSource(null).setRootProperties(properties);
		PropertiesConfiguration config = pcb.build();
		Configurator.initialize(config);
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
}
