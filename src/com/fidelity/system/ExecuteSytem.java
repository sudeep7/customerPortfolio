/**
 * 
 */
package com.fidelity.system;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.fidelity.loader.CSVDataLoader;
import com.fidelity.utils.SystemUtils;

/**
 * @author Sudeep
 *
 */
public class ExecuteSytem {

	/**
	 * @param args
	 */
	final static Logger logger = Logger.getLogger(ExecuteSytem.class);
	public static void main(String[] args) {
		
		PortfolioSystem system;
		
		try {
			
			logger.info("SYSTEM STARTING.....\n\n\n");
			
			Properties systemProperties = SystemUtils.loadSystemProperties();
			system = new PortfolioSystem(systemProperties, new CSVDataLoader(systemProperties));
			
			system.execute();
			
		}catch(Exception ex){

			logger.error("Exception Occured. Cannot proceed.", ex);
		}	
		logger.info("SYSTEM FINISHED EXECUTION.....\n\n\n");
		
	}

}
