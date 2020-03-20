/**
 * 
 */
package com.fidelity.portfolio.model;

import java.util.List;

/** This Class has the list of models which may 
 * apply on the implemented PortfolioModel
 * 
 * Note: At thsi point this  class does not have any abstract methods other than 
 * 		present in the PortfolioModel Interface, but this may change 
 * 		as other types of Portfolio Model emerges
 * 
 * @author Sudeep
 *
 */
public abstract class AbstractPortfolioModel implements PortfolioModel{

	protected List<Model> models;

	/**
	 * @return the models
	 */
	protected List<Model> getModels() {
		return models;
	}

	/**
	 * @param models the models to set
	 */
	protected void setModels(List<Model> models) {
		this.models = models;
	}
	
	
	
}
