/**
 * 
 */
package com.fidelity.loader;

import java.util.List;
import java.util.Properties;

import com.fidelity.customer.Customer;
import com.fidelity.fund.Fund;

/**
 * This will define the data loading contract for the purpose of data loading
 * @author Sudeep
 *
 */
public interface DataLoader {

	/**This will load the list of customers
	 * @return
	 * @throws Exception
	 */
	public List<Customer> loadCustomers() throws Exception;
	
	/** This will load the list of Funds
	 * @return
	 * @throws Exception
	 */
	public List<Fund> loadFunds() throws Exception;
	
	/**This will load the System Properties
	 * @return
	 * @throws Exception
	 */
	public Properties loadSystemProperties() throws Exception;
	
	
}
