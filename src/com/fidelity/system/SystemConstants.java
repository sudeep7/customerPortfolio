/**
 * 
 */
package com.fidelity.system;

/**
 * @author Sudeep
 *
 */
public interface SystemConstants {

	public static String SYSTEM_CONFIG = "D:/workspace/CustomerPortfolio/src/com/fidelity/configuration/SystemConfig.properties";
	public static Short EQUITY_FUNDS = 1;
	public static Short BOND_FUNDS = 2;
	public static Short CASH_FUNDS = 3;
	
	//customer columns
	public static String CUSTOMER_FIRST_NAME = "firstName";
	public static String CUSTOMER_LAST_NAME = "lastName";
	public static String CUSTOMER_DATE_OF_BIRTH = "dateOfBirth";
	public static String CUSTOMER_ASSETS = "assets";
	
	//fund Columns
	public static String FUND_NAME = "fundName";
	public static String FUND_NUNMBER = "fundNumber";
	public static String FUND_PRICE = "fundPrice";
	public static String FUND_TYPE = "fundType";
	
	//THREAD POOL SIZE
	public static String THREAD_POOL_SIZE = "THREAD_COUNT";

}
