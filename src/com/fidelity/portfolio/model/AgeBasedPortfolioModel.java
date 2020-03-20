/**
 * 
 */
package com.fidelity.portfolio.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fidelity.customer.Customer;
import com.fidelity.portfolio.CustomerPortfolio;
import com.fidelity.utils.PortfolioUtils;
import com.fidelity.utils.SystemUtils;

/**
 * @author Sudeep
 *
 */
public class AgeBasedPortfolioModel implements PortfolioModel{

	
	List<AgeRange> ageRangeList;
	Properties systemProperties;
	
	
	
	/* (non-Javadoc)
	 * @see com.fidelity.portfolio.model.PortfolioModel#createPorfolioModel()
	 */
	@Override
	public void createPorfolioModel() throws Exception {
		
		//load systemProperties
		systemProperties = SystemUtils.loadSystemProperties();
		
		//load config
		Document doc = loadPortfolioConfig();
		
		//parse portfolio model config
		ageRangeList = parsePortfolioConfig(doc);	
		
	}

	/** pasrse the 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	private List<AgeRange> parsePortfolioConfig(Document doc) throws Exception{
		
		List<AgeRange> ageRanges = new ArrayList<AgeRange>();

		NodeList list = doc.getElementsByTagName("ageRange");

		for (int i = 0; i < list.getLength(); i++) {
			
			//parse Age Range
			Node node = list.item(i);			
			Element element = (Element) node;
			int minAge = Integer.parseInt(element.getAttribute("min"));
			int maxAge = Integer.parseInt(null != element.getAttribute("max")?element.getAttribute("max") : "0");
			String modelName = element.getAttribute("model");
			
			//parse Fund Types
			NodeList fundTypes = element.getElementsByTagName("fundType");			
			Model pModel = new Model();
			pModel.setModelName(modelName);
			for(int j = 0; j < fundTypes.getLength(); j++){
				Node fundNode = fundTypes.item(j);
				Element fundElement = (Element) fundNode;
				String type = fundElement.getAttribute("type");
				float percentage = Float.parseFloat(fundElement.getAttribute("percentage"));
				if(type.equalsIgnoreCase("equity")){
					pModel.setEquityFundPercentage(percentage);					
				}else if(type.equalsIgnoreCase("bond")){
					pModel.setBondFundPercentage(percentage);
				}else if(type.equalsIgnoreCase("cash")){
					pModel.setCashFundPercentage(percentage);
				}
				
				System.out.println(type + "   " + percentage);
		    }
			AgeRange ageRange = new AgeRange(minAge, maxAge, pModel);
			ageRanges.add(ageRange);
		}

		return ageRanges;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private Document loadPortfolioConfig() throws Exception {
		
		String fundConfigFilePath = PortfolioUtils
				.getConfigFileLocation(systemProperties, "PORTFOLIO_MODEL");

		if (fundConfigFilePath == null) {
			throw new Exception("Fund config File does not Exists");
		}

		Document doc = SystemUtils.parseConfig(fundConfigFilePath);

		return doc;
	}
	
	@Override
	public synchronized CustomerPortfolio getCustomerPortfolio() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	/**
	 * @return the ageRangeList
	 */
	public List<AgeRange> getAgeRangeList() {
		return ageRangeList;
	}

	/**
	 * @param ageRangeList the ageRangeList to set
	 */
	public void setAgeRangeList(List<AgeRange> ageRangeList) {
		this.ageRangeList = ageRangeList;
	}

	@Override
	public synchronized Model applyPortfolioModel(Customer cust)
			throws Exception {

		int ageOfCustomer = calculateAgeOfCustomer(cust);
		
		Model applicableModel = getApplicableModelBasedOnAge(ageOfCustomer);
		return applicableModel;
	}


	/** finds the correct model to apply for customer
	 * @param ageOfCustomer
	
	 * @return
	 */
	private Model getApplicableModelBasedOnAge(int ageOfCustomer) {
		
		for(AgeRange ageRange: ageRangeList){
			if(ageOfCustomer >= ageRange.getFromAge() && ageOfCustomer <= ageRange.getToAge()){
				return ageRange.getAgeBasedMappingModel();
			}
		}
		return null;
	}

	/** Calculate the age of the Customer through date of birth
	 * @param cust
	 * @return
	 */
	private int calculateAgeOfCustomer(Customer cust) {
		
		@SuppressWarnings("deprecation")
		int currentYear = new Date().getYear()+1900;
		@SuppressWarnings("deprecation")
		int dateOfbirthYear =  cust.getDateOfBirth().getYear()+1900;	

		return currentYear - dateOfbirthYear;
		
		
	}

	
	
	
}
