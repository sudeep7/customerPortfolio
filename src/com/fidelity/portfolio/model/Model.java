package com.fidelity.portfolio.model;

import java.io.Serializable;

/**
 * Base bean for model.
 * @author Sudeep
 *
 */
public class Model implements Serializable{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	protected String modelName;
	protected float equityFundPercentage;
	protected float bondFundPercentage;
	protected float cashFundPercentage;
	
	
	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}
	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	/**
	 * @return the equityFundPercentage
	 */
	public float getEquityFundPercentage() {
		return equityFundPercentage;
	}
	/**
	 * @param equityFundPercentage the equityFundPercentage to set
	 */
	public void setEquityFundPercentage(float equityFundPercentage) {
		this.equityFundPercentage = equityFundPercentage;
	}
	/**
	 * @return the bondFundPercentage
	 */
	public float getBondFundPercentage() {
		return bondFundPercentage;
	}
	/**
	 * @param bondFundPercentage the bondFundPercentage to set
	 */
	public void setBondFundPercentage(float bondFundPercentage) {
		this.bondFundPercentage = bondFundPercentage;
	}
	/**
	 * @return the cashFundPercentage
	 */
	public float getCashFundPercentage() {
		return cashFundPercentage;
	}
	/**
	 * @param cashFundPercentage the cashFundPercentage to set
	 */
	public void setCashFundPercentage(float cashFundPercentage) {
		this.cashFundPercentage = cashFundPercentage;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Model [modelName=" + modelName + ", equityFundPercentage="
				+ equityFundPercentage + ", bondFundPercentage="
				+ bondFundPercentage + ", cashFundPercentage="
				+ cashFundPercentage + "]";
	}
	
}
