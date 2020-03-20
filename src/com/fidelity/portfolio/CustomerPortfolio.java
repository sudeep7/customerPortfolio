/**
 * 
 */
package com.fidelity.portfolio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Sudeep
 *This class is customer portfolio bean
 */
public class CustomerPortfolio implements Serializable {

	/**
	 * Default
	 */
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String portfolioName;
	private String equityFundName;
	private BigDecimal equityFundShare;
	private String bondFundName;
	private BigDecimal bondFundShare;
	private String cashFundName;
	private BigDecimal cashFundShare;
	
	
	/**constructor
	 * 
	 * @param firstName
	 * @param lastName
	 * @param portfolioName
	 * @param equityFundName
	 * @param equityFundShare
	 * @param bondFundName
	 * @param bondFundShare
	 * @param cashFundName
	 * @param cashFundShare
	 */
	public CustomerPortfolio(String firstName, String lastName,
			String portfolioName, String equityFundName,
			BigDecimal equityFundShare, String bondFundName, BigDecimal bondFundShare,
			String cashFundName, BigDecimal cashFundShare) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.portfolioName = portfolioName;
		this.equityFundName = equityFundName;
		this.equityFundShare = equityFundShare;
		this.bondFundName = bondFundName;
		this.bondFundShare = bondFundShare;
		this.cashFundName = cashFundName;
		this.cashFundShare = cashFundShare;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the portfolioName
	 */
	public String getPortfolioName() {
		return portfolioName;
	}


	/**
	 * @param portfolioName the portfolioName to set
	 */
	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}


	/**
	 * @return the equityFundName
	 */
	public String getEquityFundName() {
		return equityFundName;
	}


	/**
	 * @param equityFundName the equityFundName to set
	 */
	public void setEquityFundName(String equityFundName) {
		this.equityFundName = equityFundName;
	}


	/**
	 * @return the equityFundShare
	 */
	public BigDecimal getEquityFundShare() {
		return equityFundShare;
	}


	/**
	 * @param equityFundShare the equityFundShare to set
	 */
	public void setEquityFundShare(BigDecimal equityFundShare) {
		this.equityFundShare = equityFundShare;
	}


	/**
	 * @return the bondFundName
	 */
	public String getBondFundName() {
		return bondFundName;
	}


	/**
	 * @param bondFundName the bondFundName to set
	 */
	public void setBondFundName(String bondFundName) {
		this.bondFundName = bondFundName;
	}


	/**
	 * @return the bondFundShare
	 */
	public BigDecimal getBondFundShare() {
		return bondFundShare;
	}


	/**
	 * @param bondFundShare the bondFundShare to set
	 */
	public void setBondFundShare(BigDecimal bondFundShare) {
		this.bondFundShare = bondFundShare;
	}


	/**
	 * @return the cashFundName
	 */
	public String getCashFundName() {
		return cashFundName;
	}


	/**
	 * @param cashFundName the cashFundName to set
	 */
	public void setCashFundName(String cashFundName) {
		this.cashFundName = cashFundName;
	}


	/**
	 * @return the cashFundShare
	 */
	public BigDecimal getCashFundShare() {
		return cashFundShare;
	}


	/**
	 * @param cashFundShare the cashFundShare to set
	 */
	public void setCashFundShare(BigDecimal cashFundShare) {
		this.cashFundShare = cashFundShare;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerPortfolio [firstName=" + firstName + ", lastName="
				+ lastName + ", portfolioName=" + portfolioName
				+ ", equityFundName=" + equityFundName + ", equityFundShare="
				+ equityFundShare + ", bondFundName=" + bondFundName
				+ ", bondFundShare=" + bondFundShare + ", cashFundName="
				+ cashFundName + ", cashFundShare=" + cashFundShare + "]";
	}
	
	
}
