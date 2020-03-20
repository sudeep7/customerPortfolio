/**
 * 
 */
package com.fidelity.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.fidelity.system.SystemConstants;

/**
 * @author Sudeep
 * 
 */
public class SystemUtils {

	final static Logger logger = Logger.getLogger(SystemUtils.class);
	/**
	 * loads the System properties
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Properties loadSystemProperties() throws Exception {
		Properties properties = null;
		try {
			FileReader reader = new FileReader(SystemConstants.SYSTEM_CONFIG);
			properties = new Properties();
			properties.load(reader);

		} catch (IOException e) {
			logger.error("SystemProperties not initialized. Cannot proceed.", e);
			throw new Exception(
					"SystemProperties not initialized. Cannot proceed.");
		}
		return properties;
	}

	/**
	 * creates the new Executor SErvice
	 * 
	 * @param systemPorperties
	 * @throws NumberFormatException
	 */
	public static ExecutorService getExecutorService(Properties systemPorperties)
			throws NumberFormatException {

		int threadSize = Integer.parseInt(systemPorperties
				.getProperty(SystemConstants.THREAD_POOL_SIZE));

		return Executors.newFixedThreadPool(threadSize);

	}
	
	/**
	 * parse the Customer config and return the Document object
	 * 
	 * @param customerConfigFilePath
	 * @return
	 * @throws Exception
	 */
	public static Document parseConfig(String customerConfigFilePath)
			throws Exception {

		Document doc = null;
		try {
			File fXmlFile = new File(customerConfigFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			logger.error(customerConfigFilePath + " cannot be parsed. Cannot proceed.", e);
			throw e;
			//e.printStackTrace();
		}
		return doc;

	}

}
