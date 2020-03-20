/**
 * 
 */
package com.fidelity.portfolio;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.fidelity.customer.Customer;
import com.fidelity.fund.Fund;
import com.fidelity.portfolio.model.Model;
import com.fidelity.portfolio.model.PortfolioModel;

/**
 * @author Sudeep
 * 
 */
public class CustomerPortfolioProcessingTask implements Runnable {

	final static Logger logger = Logger
			.getLogger(CustomerPortfolioProcessingTask.class);
	// fields
	Customer customer;
	List<Fund> equityFunds;
	List<Fund> bondFunds;
	List<Fund> cashFunds;
	List<CustomerPortfolio> customerPortfolios;
	PortfolioModel portfolioModel;
	private static Integer equityFundCounter = 0;
	private static Integer bondFundCounter = 0;
	private static Integer cashFundCounter = 0;

	/**
	 * @param customer
	 * @param equityFunds
	 * @param bondFunds
	 * @param cashFunds
	 * @param customerPortfolios
	 * @param portfolioModel
	 */
	public CustomerPortfolioProcessingTask(Customer customer,
			List<Fund> equityFunds, List<Fund> bondFunds, List<Fund> cashFunds,
			List<CustomerPortfolio> customerPortfolios,
			PortfolioModel portfolioModel) {
		this.customer = customer;
		this.equityFunds = equityFunds;
		this.bondFunds = bondFunds;
		this.cashFunds = cashFunds;
		this.customerPortfolios = customerPortfolios;
		this.portfolioModel = portfolioModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		try {
			Model model = portfolioModel.applyPortfolioModel(customer);

			CustomerPortfolio customerPortfolo = applyModel(customer, model);

			if (customerPortfolo != null) {
				customerPortfolios.add(customerPortfolo);
			}

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Error occurred while processing task : "
						+ e);
			}
		}

	}

	/**
	 * apply model and return Customer Portfolio after asset divisions	
	 * @param customer
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private CustomerPortfolio applyModel(Customer customer, Model model)
			throws Exception {

		try {
			BigDecimal equityFundAmount = (customer.getAssets()
					.multiply(BigDecimal.valueOf(
							model.getEquityFundPercentage()).divide(
							BigDecimal.valueOf(100))));
			BigDecimal bondFundAmount = customer.getAssets().multiply(
					BigDecimal.valueOf(model.getBondFundPercentage()).divide(
							BigDecimal.valueOf(100)));
			BigDecimal cashFundAmount = customer.getAssets().multiply(
					BigDecimal.valueOf(model.getCashFundPercentage()).divide(
							BigDecimal.valueOf(100)));

			Fund equityFund = equityFunds.get(getEquityFundCounter());
			Fund bondFund = bondFunds.get(getBondFundCounter());
			Fund cashFund = cashFunds.get(getCashFundCounter());

			BigDecimal equityFundShare = equityFundAmount.divide(
					equityFund.getFundPrice(), 3);
			BigDecimal bondFundShare = bondFundAmount.divide(
					bondFund.getFundPrice(), 3);
			BigDecimal cashFundShare = cashFundAmount.divide(
					cashFund.getFundPrice(), 3);

			CustomerPortfolio customerPortfolio = new CustomerPortfolio(
					customer.getFirstName(), customer.getLastName(),
					model.getModelName(), equityFund.getFundName(),
					equityFundShare, bondFund.getFundName(), bondFundShare,
					cashFund.getFundName(), cashFundShare);

			if (logger.isDebugEnabled()) {
				logger.debug("Customer Portfolio created :" + customerPortfolio);
			}

			return customerPortfolio;
		} catch (Exception e) {
			logger.error(
					"Error Occured while creating Customer Portfolio, Record will be skipped : ",
					e);

		}
		return null;
	}

	/**
	 * @return
	 */
	private int getCashFundCounter() {

		if (cashFundCounter >= equityFunds.size() - 1) {
			cashFundCounter = 0;
		}
		return cashFundCounter++;
	}

	/**
	 * @return
	 */
	private int getBondFundCounter() {
		if (bondFundCounter >= bondFunds.size() - 1) {
			bondFundCounter = 0;
		}
		return bondFundCounter++;
	}

	/**
	 * @return
	 */
	private int getEquityFundCounter() {
		if (equityFundCounter >= equityFunds.size() - 1) {
			equityFundCounter = 0;
		}
		return equityFundCounter++;
	}

	/**
	 * @return the customerPortfolios
	 */
	public List<CustomerPortfolio> getCustomerPortfolios() {
		return customerPortfolios;
	}

	/**
	 * @param customerPortfolios
	 *            the customerPortfolios to set
	 */
	public void setCustomerPortfolios(List<CustomerPortfolio> customerPortfolios) {
		this.customerPortfolios = customerPortfolios;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	 * @return the cashFund
	 */
	public List<Fund> getCashFunds() {
		return cashFunds;
	}

	
	/**
	 * @param cashFunds
	 */
	public void setCashFunds(List<Fund> cashFunds) {
		this.cashFunds = cashFunds;
	}

	/**
	 * @return the portfolioModel
	 */
	public PortfolioModel getPortfolioModel() {
		return portfolioModel;
	}

	/**
	 * @param portfolioModel
	 *            the portfolioModel to set
	 */
	public void setPortfolioModel(PortfolioModel portfolioModel) {
		this.portfolioModel = portfolioModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerPortfolioProcessingTask [customer=" + customer
				+ ", equityFunds=" + equityFunds + ", bondFunds=" + bondFunds
				+ ", cashFunds=" + cashFunds + ", customerPortfolios="
				+ customerPortfolios + ", portfolioModel=" + portfolioModel
				+ "]";
	}

}
