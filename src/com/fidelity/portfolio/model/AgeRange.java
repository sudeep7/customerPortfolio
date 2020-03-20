/**
 * 
 */
package com.fidelity.portfolio.model;

import java.io.Serializable;

/**
 * @author Sudeep
 *
 */
public class AgeRange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int fromAge ;
	private int toAge;
	private Model ageBasedMappingModel;
	/**
	 * @param fromAge
	 * @param toAge
	 * @param ageBasedMappingModel
	 */
	public AgeRange(int fromAge, int toAge, Model ageBasedMappingModel) {
		this.fromAge = fromAge;
		this.toAge = toAge;
		this.ageBasedMappingModel = ageBasedMappingModel;
	}
	/**
	 * @return the fromAge
	 */
	public int getFromAge() {
		return fromAge;
	}
	/**
	 * @param fromAge the fromAge to set
	 */
	public void setFromAge(int fromAge) {
		this.fromAge = fromAge;
	}
	/**
	 * @return the toAge
	 */
	public int getToAge() {
		return toAge;
	}
	/**
	 * @param toAge the toAge to set
	 */
	public void setToAge(int toAge) {
		this.toAge = toAge;
	}
	/**
	 * @return the ageBasedMappingModel
	 */
	public Model getAgeBasedMappingModel() {
		return ageBasedMappingModel;
	}
	/**
	 * @param ageBasedMappingModel the ageBasedMappingModel to set
	 */
	public void setAgeBasedMappingModel(Model ageBasedMappingModel) {
		this.ageBasedMappingModel = ageBasedMappingModel;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AgeRange [fromAge=" + fromAge + ", toAge=" + toAge
				+ ", ageBasedMappingModel=" + ageBasedMappingModel + "]";
	}
	
	
}
