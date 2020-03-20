/**
 * 
 */
package com.fidelity.portfolio.model;

/**
 * This Factory class returns the portfolio model 
 * based on the configured or provided type
 * 
 * @author Sudeep
 *
 */
public final class PortfolioModelFactory {

	public PortfolioModel getPortfolioModel(PortfolioModelType modelType) throws Exception{
		
		PortfolioModel portfolioModel = null;
		if(modelType.equals(PortfolioModelType.AGE_BASED)){
			portfolioModel = new AgeBasedPortfolioModel();
		}
		//
		//provision for more models
		//
		//if none found then return AgeBased Model as default
		else{
			portfolioModel = new AgeBasedPortfolioModel();
		}
		
		return portfolioModel;
	}
	
}
