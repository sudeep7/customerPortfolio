/**
 * 
 */
package com.fidelity.system;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import com.fidelity.customer.Customer;
import com.fidelity.fund.Fund;
import com.fidelity.loader.DataLoader;
import com.fidelity.portfolio.CustomerPortfolio;
import com.fidelity.portfolio.CustomerPortfolioProcessingTask;
import com.fidelity.portfolio.model.PortfolioModel;
import com.fidelity.utils.PortfolioUtils;
import com.fidelity.utils.SystemUtils;

/**
 * @author Sudeep
 * 
 */
public class PortfolioSystem {

	final static Logger logger = Logger.getLogger(PortfolioSystem.class);

	private Properties properties;
	private List<Fund> equityFunds;
	private List<Fund> bondFunds;
	private List<Fund> cashFunds;
	private List<Customer> customers;

	private List<CustomerPortfolio> customerPortfolios;
	DataLoader loader;
	Properties systemPorperties;

	/**
	 * This will start the Execution of the system
	 * 
	 * @throws Exception
	 */
	public void execute() throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("LOADING DATA FROM INPUT \n");
		}
		loadData();
		if (logger.isDebugEnabled()) {
			logger.debug("LOADING DATA FROM INPUT \n");
		}
		customerPortfolioProcessing();
		processOutput();

	}

	/**
	 * @param properties
	 * @param loader
	 * @throws Exception
	 */
	public PortfolioSystem(Properties properties, DataLoader loader)
			throws Exception {
		if (loader != null) {
			setLoader(loader);
		} else {
			throw new Exception("Dataloader not set. Cannot proceed");
		}
		if (properties != null) {
			systemPorperties = properties;
		} else {
			systemPorperties = SystemUtils.loadSystemProperties();
		}
	}

	/**
	 * This method will process the output and write to desired
	 * 
	 * @throws Exception
	 */
	private void processOutput() throws Exception {

		StringBuilder csvString = new StringBuilder();
		for (CustomerPortfolio portfolio : customerPortfolios) {
			csvString.append(String.format("%0$-" + 10 + "s",
					portfolio.getLastName()));
			csvString.append(String.format("%0$-" + 10 + "s",
					portfolio.getFirstName()));
			csvString.append(String.format("%0$-" + 20 + "s",
					portfolio.getPortfolioName()));
			csvString.append(String.format("%0$-" + 20 + "s",
					portfolio.getEquityFundName()));
			csvString.append(String.format("%0$" + 11 + "s",
					portfolio.getEquityFundShare().toString())
					.replace(" ", "0"));
			csvString.append(String.format("%0$-" + 20 + "s",
					portfolio.getBondFundName()));
			csvString.append(String.format("%0$" + 11 + "s",
					portfolio.getBondFundShare().toString()).replace(" ", "0"));
			csvString.append(String.format("%0$-" + 21 + "s",
					portfolio.getCashFundName()));
			csvString.append(String.format("%0$" + 11 + "s",
					portfolio.getCashFundShare().toString()).replace(" ", "0"));

			csvString.append("\n\r");

		}
		if (logger.isDebugEnabled()) {
			logger.debug("CSV file output: \n" + csvString);
		}
		writeOutputCSV(csvString);

	}

	/**
	 * @param csvString
	 * @throws IOException
	 */
	private void writeOutputCSV(StringBuilder csvString) throws IOException {
		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {

			String filePath = systemPorperties.getProperty("BASE_LOCATION")
					+ systemPorperties.getProperty("PORTFOLIO_OUTPUT_LOCATION")
					+ systemPorperties.getProperty("PORTFOLIO_OUTPUT_FILE");

			file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(csvString.toString());
			bw.close();

		} catch (IOException e) {
			logger.error(
					"Exception Occured while writing Output file. Cannot proceed.",
					e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					logger.error(
							"Exception Occured while closing Buffered writed.",
							e);
				}
			}
		}
	}

	/**
	 * This will process all the customer records for portfolio creation and
	 * processing
	 * 
	 * @throws Exception
	 */
	private void customerPortfolioProcessing() throws Exception {

		customerPortfolios = Collections
				.synchronizedList(new ArrayList<CustomerPortfolio>(customers
						.size()));

		ExecutorService threadExecutor = SystemUtils
				.getExecutorService(systemPorperties);

		PortfolioModel portfolioModel = PortfolioUtils
				.getApplicablePortfolioModel(systemPorperties);

		portfolioModel.createPorfolioModel();

		for (Customer customer : customers) {

			CustomerPortfolioProcessingTask task = new CustomerPortfolioProcessingTask(
					customer, equityFunds, bondFunds, cashFunds,
					customerPortfolios, portfolioModel);
			threadExecutor.execute(task);
		}

		threadExecutor.shutdown();
		while (!threadExecutor.isTerminated()) {
		}

	}

	/**
	 * This Method will load all the date from the data sources for system
	 * config, Customer, fund and portfolio model
	 * 
	 * @throws Exception
	 */
	private void loadData() throws Exception {

		loadSystemProperties();

		loadFunds();

		loadCustomers();

		// PortfolioUtils.loadPortfolioModel();
	}

	/**
	 * loads Customer
	 * 
	 * @throws Exception
	 */
	private void loadCustomers() throws Exception {
		setCustomers(getLoader().loadCustomers());
	}

	/**
	 * load funds
	 * 
	 * @throws Exception
	 */
	private void loadFunds() throws Exception {
		List<Fund> allFunds = getLoader().loadFunds();
		setEquityFunds(PortfolioUtils.getFunds(allFunds,
				SystemConstants.EQUITY_FUNDS));
		setBondFunds(PortfolioUtils.getFunds(allFunds,
				SystemConstants.BOND_FUNDS));
		setCashFunds(PortfolioUtils.getFunds(allFunds,
				SystemConstants.CASH_FUNDS));
	}

	/**
	 * this loads the system config
	 * 
	 * @param properties2
	 * @throws Exception
	 */
	private void loadSystemProperties() throws Exception {
		setProperties(getLoader().loadSystemProperties());
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the equityFunds
	 */
	public List<Fund> getEquityFunds() {
		return equityFunds;
	}

	/**
	 * @param equityFunds
	 *            the equityFunds to set
	 */
	public void setEquityFunds(List<Fund> equityFunds) {
		this.equityFunds = equityFunds;
	}

	/**
	 * @return the bondFunds
	 */
	public List<Fund> getBondFunds() {
		return bondFunds;
	}

	/**
	 * @param bondFunds
	 *            the bondFunds to set
	 */
	public void setBondFunds(List<Fund> bondFunds) {
		this.bondFunds = bondFunds;
	}

	/**
	 * @return the cashFunds
	 */
	public List<Fund> getCashFunds() {
		return cashFunds;
	}

	/**
	 * @param cashFunds
	 *            the cashFunds to set
	 */
	public void setCashFunds(List<Fund> cashFunds) {
		this.cashFunds = cashFunds;
	}

	/**
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @param customers
	 *            the customers to set
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	/**
	 * @return the customerPortfolio
	 */
	public List<CustomerPortfolio> getCustomerPortfolios() {
		return customerPortfolios;
	}

	/**
	 * @param customerPortfolio
	 *            the customerPortfolio to set
	 */
	public void setCustomerPortfolios(List<CustomerPortfolio> customerPortfolios) {
		this.customerPortfolios = customerPortfolios;
	}

	/**
	 * @return the loader
	 */
	public DataLoader getLoader() {
		return loader;
	}

	/**
	 * @param loader
	 *            the loader to set
	 */
	public void setLoader(DataLoader loader) {
		this.loader = loader;
	}

}
