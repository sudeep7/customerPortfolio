/**
 * 
 */
package com.fidelity.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fidelity.customer.Customer;
import com.fidelity.fund.Fund;
import com.fidelity.system.SystemConstants;
import com.fidelity.utils.PortfolioUtils;
import com.fidelity.utils.SystemUtils;

/**
 * @author Sudeep
 * 
 */
public class CSVDataLoader implements DataLoader {

	final static Logger logger = Logger.getLogger(CSVDataLoader.class);

	static Properties systemProperties;


	/**Constructor
	 * @param properties
	 * @throws Exception
	 */
	public CSVDataLoader(Properties properties) throws Exception {

		if (properties != null) {
			systemProperties = properties;
		} else {
			systemProperties = SystemUtils.loadSystemProperties();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fidelity.loader.DataLoader#loadCustomers()
	 */
	@Override
	public List<Customer> loadCustomers() throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Loading Customer data from input file. \n");
		}
		// load the customer config
		Document doc = loadCustomerConfig();

		return parseCustomerInput(doc);

	}

	/**
	 * parse the Input CSV w.r.t customer Config
	 * 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	private List<Customer> parseCustomerInput(Document doc) throws Exception {

		List<Customer> customers = new ArrayList<Customer>();

		String csvFile = PortfolioUtils.getInputFileLocation(systemProperties,
				"CUSTOMER");

		if (csvFile == null) {
			throw new Exception(
					"Customer input file does not exists. Please check configurations");
		}

		BufferedReader br = null;
		String line = "";

		try {
			Customer customer;
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				customer = createCustomerRecord(line, doc);
				if (customer != null) {
					customers.add(customer);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Loaded Customer Record : " + customer);
				}

			}

		} catch (FileNotFoundException e) {
			logger.error("Error occured : cannot proceed. ", e);
			throw e;
		} catch (IOException e) {
			logger.error("Error occured : cannot proceed. ", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error occured : cannot proceed. ", e);
			throw e;

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Error occured : cannot proceed. ", e);					
				}
			}
		}

		return customers;

	}

	/**
	 * creates Customer Record
	 * 
	 * @param line
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	private Customer createCustomerRecord(String line, Document doc)
			throws Exception {

		NodeList list = doc.getElementsByTagName("attribute");

		// field details
		String[] fieldNames = new String[list.getLength()];
		int[] startPositions = new int[list.getLength()];
		int[] endPositions = new int[list.getLength()];

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			Element element = (Element) node;
			fieldNames[i] = element.getAttribute("name");
			startPositions[i] = Integer.parseInt(element
					.getAttribute("startPosition"));
			endPositions[i] = Integer.parseInt(element
					.getAttribute("endPosition"));

			if (logger.isDebugEnabled()) {
				logger.debug("Customer config attribute values : \n"
						+ "name : " + fieldNames[i] + " startPosition : "
						+ startPositions[i] + " end Position : "
						+ endPositions[i]);
			}
		}

		Customer customer = populateCustomerRecord(line, fieldNames,
				startPositions, endPositions);

		return customer;
	}

	/**
	 * populates customer record from input customer record line
	 * 
	 * @param line
	 * @param fieldNames
	 * @param startPositions
	 * @param endPositions
	 * @param datatypes
	 * @return
	 * @throws ParseException
	 *             , Exception
	 */
	private Customer populateCustomerRecord(String line, String[] fieldNames,
			int[] startPositions, int[] endPositions) throws ParseException,
			Exception {

		Customer customerRecord = new Customer();

		try {
			for (int i = 0; i < fieldNames.length; i++) {

				String fieldValue = line.substring(startPositions[i],
						endPositions[i]);

				if (fieldNames[i]
						.equalsIgnoreCase(SystemConstants.CUSTOMER_FIRST_NAME)) {
					customerRecord.setFirstName(fieldValue.trim());
				} else if (fieldNames[i]
						.equalsIgnoreCase(SystemConstants.CUSTOMER_LAST_NAME)) {
					customerRecord.setLastName(fieldValue.trim());
				} else if (fieldNames[i]
						.equalsIgnoreCase(SystemConstants.CUSTOMER_DATE_OF_BIRTH)) {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"MMddyyyy");
					Date dateOfBirth = formatter.parse(fieldValue);
					customerRecord.setDateOfBirth(dateOfBirth);
				} else if (fieldNames[i]
						.equalsIgnoreCase(SystemConstants.CUSTOMER_ASSETS)) {
					customerRecord.setAssets(BigDecimal.valueOf(Double
							.valueOf(fieldValue)));

				}

			}
		} catch (Exception e) {
			logger.error("Error Populating the record", e);
			customerRecord = null;
		}

		return customerRecord;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fidelity.loader.DataLoader#loadFunds(java.lang.Short)
	 */
	@Override
	public List<Fund> loadFunds() throws Exception {

		// load the customer config
		Document doc = loadFundConfig();

		return parseFundInput(doc);

	}

	/** Parse the Fund Document
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	private List<Fund> parseFundInput(Document doc) throws Exception {

		List<Fund> funds = new ArrayList<Fund>();

		String csvFile = PortfolioUtils.getInputFileLocation(systemProperties,
				"FUND");

		if (csvFile == null) {
			throw new Exception(
					"FUND input file does not exists. Please check configurations");
		}

		BufferedReader br = null;
		String line = "";

		try {
			Fund fund;
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				fund = createFundRecord(line, doc);
				if (fund != null) {
					funds.add(fund);
				}
			}

		} catch (FileNotFoundException e) {
			logger.error("Error occured : cannot proceed. ", e);
			throw e;
		} catch (IOException e) {
			logger.error("Error occured : cannot proceed. ", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error occured : cannot proceed. ", e);
			throw e;

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Error occured : ", e);
				}
			}
		}

		return funds;
	}

	/**
	 * creates Fund records
	 * 
	 * @param line
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	private Fund createFundRecord(String line, Document doc) throws Exception {
		NodeList list = doc.getElementsByTagName("attribute");

		// field details
		String[] fieldNames = new String[list.getLength()];
		int[] startPositions = new int[list.getLength()];
		int[] endPositions = new int[list.getLength()];

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			Element element = (Element) node;
			fieldNames[i] = element.getAttribute("name");
			startPositions[i] = Integer.parseInt(element
					.getAttribute("startPosition"));
			endPositions[i] = Integer.parseInt(element
					.getAttribute("endPosition"));
		}

		Fund fund = populateFundRecord(line, fieldNames, startPositions,
				endPositions);

		return fund;
	}

	/**
	 * Populates fund Record
	 * 
	 * @param line
	 * @param fieldNames
	 * @param startPositions
	 * @param endPositions
	 * @return
	 */
	private Fund populateFundRecord(String line, String[] fieldNames,
			int[] startPositions, int[] endPositions) {
		Fund fundRecord = new Fund();

		try {
			for (int i = 0; i < fieldNames.length; i++) {

				String fieldValue = line.substring(startPositions[i],
						endPositions[i]);

				if (fieldNames[i].equalsIgnoreCase(SystemConstants.FUND_NAME)) {
					fundRecord.setFundName(fieldValue.trim());
				} else if (fieldNames[i]
						.equalsIgnoreCase(SystemConstants.FUND_NUNMBER)) {
					fundRecord.setFundNumber(Long.valueOf(fieldValue.trim()));
				} else if (fieldNames[i]
						.equalsIgnoreCase(SystemConstants.FUND_TYPE)) {
					fundRecord.setFundType(Short.valueOf(fieldValue.trim()));
				} else if (fieldNames[i]
						.equalsIgnoreCase(SystemConstants.FUND_PRICE)) {
					fundRecord.setFundPrice(BigDecimal.valueOf(Double
							.valueOf(fieldValue)));					
				}

			}
		} catch (Exception e) {
			logger.error(
					"Error in populating Fund Record. Fund will be skipped", e);
			fundRecord = null;
		}

		return fundRecord;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private Document loadFundConfig() throws Exception {

		String fundConfigFilePath = PortfolioUtils.getConfigFileLocation(
				systemProperties, "FUND");

		if (fundConfigFilePath == null) {
			throw new Exception("Fund config File does not Exists");
		}

		Document doc = SystemUtils.parseConfig(fundConfigFilePath);

		return doc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fidelity.loader.DataLoader#loadSystemProperties()
	 */
	@Override
	public Properties loadSystemProperties() throws Exception {
		return SystemUtils.loadSystemProperties();
	}

	/**
	 * Loads Customer config
	 * @throws Exception
	 */
	private static Document loadCustomerConfig() throws Exception {

		String customerConfigFilePath = PortfolioUtils.getConfigFileLocation(
				systemProperties, "CUSTOMER");

		if (customerConfigFilePath == null) {
			throw new Exception("Customer config File does not Exists");
		}

		Document doc = SystemUtils.parseConfig(customerConfigFilePath);

		return doc;

	}

}
