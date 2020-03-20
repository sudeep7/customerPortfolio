/**
 * 
 */
package com.fidelity.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.fidelity.fund.Fund;
import com.fidelity.portfolio.model.PortfolioModel;
import com.fidelity.portfolio.model.PortfolioModelFactory;
import com.fidelity.portfolio.model.PortfolioModelType;

/**
 * @author Sudeep
 * 
 */
public class PortfolioUtils {

	/**
	 * get config File Location
	 * 
	 * @param systemProperties
	 * @return
	 * @throws Exception
	 */
	public static String getConfigFileLocation(
			Properties systemProperties, String source) throws Exception {

		String baseLocation = systemProperties.getProperty("BASE_LOCATION");
		String customerConfigLocation = systemProperties
				.getProperty(source +"_CONFIGURATION_LOCATION");
		String configFileName = systemProperties
				.getProperty(source +"_CONFIGURATION_FILE");

		return baseLocation + customerConfigLocation + configFileName;
	}


	/** get the input file location
	 * @param systemProperties
	 * @return
	 * @throws Exception
	 */
	public static String getInputFileLocation(
			Properties systemProperties, String source)throws Exception {
		
		String baseLocation = systemProperties.getProperty("BASE_LOCATION");
		String customerInputLocation = systemProperties
				.getProperty(source +"_INPUT_LOCATION");
		String inputFileName = systemProperties
				.getProperty(source +"_INPUT_FILE");

		return baseLocation + customerInputLocation + inputFileName;
		
	}



	/** return subset of all funds based on fund Type
	 * @param allFunds 
	 * @param fundType
	 * @return
	 * @throws Exception
	 */
	public static List<Fund> getFunds(List<Fund> allFunds, Short fundType) throws Exception {
		
		List<Fund> funds = new LinkedList<Fund>();
		
		for(Fund fund : allFunds){
			if(fund.getFundType().equals(fundType)){
				funds.add(fund);
			}
		}
		return funds =Collections.synchronizedList(funds);
	}


	/**get the applicable portfolio model that is configured in systemProperties
	 * 
	 * @param systemPorperties
	 * @return
	 * @throws Exception
	 */
	public static PortfolioModel getApplicablePortfolioModel(Properties systemPorperties) throws Exception{
		
		PortfolioModel model = null;
		String configuredModel = systemPorperties.getProperty("PORTFOLIO_MODEL_TYPE");
		if(configuredModel !=null && PortfolioModelType.valueOf(configuredModel) != null){			
			
			PortfolioModelFactory modelFactory = new PortfolioModelFactory();
			model = modelFactory.getPortfolioModel(PortfolioModelType.valueOf(configuredModel));			
			
		}
		
		return model;
	}



}
