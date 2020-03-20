/**
 * 
 */
package com.fidelity.portfolio.model;

import com.fidelity.customer.Customer;
import com.fidelity.portfolio.CustomerPortfolio;

/**
 * @author Sudeep
 *
 */
public interface PortfolioModel {

	/**Creates the Portfolio Model
	 * 
	 * @throws Exception
	 */
	public void createPorfolioModel() throws Exception;	
	
	/** get the Customer Portfolio
	 * @return
	 * @throws Exception
	 */
	public CustomerPortfolio getCustomerPortfolio() throws Exception;
	
	
	/**Apply the portfolio model to customer
	 * @param cust
	 * @return
	 * @throws Exception
	 */
	public Model applyPortfolioModel(Customer cust) throws Exception;
	
	
	
}
