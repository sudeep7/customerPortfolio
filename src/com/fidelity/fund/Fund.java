/**
 * 
 */
package com.fidelity.fund;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This class is basic fund bean
 * 
 * @author Sudeep
 *
 */
public class Fund implements Serializable {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	private String fundName;
	private Long fundNumber;
	private Short fundType;
	private BigDecimal fundPrice;
	/**
	 * @return the fundName
	 */
	public String getFundName() {
		return fundName;
	}
	/**
	 * @param fundName the fundName to set
	 */
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	/**
	 * @return the fundNumber
	 */
	public Long getFundNumber() {
		return fundNumber;
	}
	/**
	 * @param fundNumber the fundNumber to set
	 */
	public void setFundNumber(Long fundNumber) {
		this.fundNumber = fundNumber;
	}
	/**
	 * @return the fundType
	 */
	public Short getFundType() {
		return fundType;
	}
	/**
	 * @param fundType the fundType to set
	 */
	public void setFundType(Short fundType) {
		this.fundType = fundType;
	}
	/**
	 * @return the fundPrice
	 */
	public BigDecimal getFundPrice() {
		return fundPrice;
	}
	/**
	 * @param fundPrice the fundPrice to set
	 */
	public void setFundPrice(BigDecimal fundPrice) {
		this.fundPrice = fundPrice;
	}
	
	/** constructor
	 * @param fundName
	 * @param fundNumber
	 * @param fundType
	 * @param fundPrice
	 */
	public Fund(String fundName, Long fundNumber, Short fundType,
			BigDecimal fundPrice) {
		super();
		this.fundName = fundName;
		this.fundNumber = fundNumber;
		this.fundType = fundType;
		this.fundPrice = fundPrice;
	}
	public Fund() {
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Fund [fundName=" + fundName + ", fundNumber=" + fundNumber
				+ ", fundType=" + fundType + ", fundPrice=" + fundPrice + "]";
	}
	
	
	
}
