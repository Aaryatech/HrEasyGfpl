package com.ats.hreasy.model.claim;
 
public class ClientWiseClaimReport {
	
	 
	private int custId ; 
	
 
	private String custName;
	
 
	private float claimAmount;

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public float getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(float claimAmount) {
		this.claimAmount = claimAmount;
	}

	@Override
	public String toString() {
		return "ClientWiseClaimReport [custId=" + custId + ", custName=" + custName + ", claimAmount=" + claimAmount
				+ "]";
	}
	
	

}
