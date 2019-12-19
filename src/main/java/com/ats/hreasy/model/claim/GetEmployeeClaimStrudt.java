package com.ats.hreasy.model.claim;

 
public class GetEmployeeClaimStrudt {

	 
	private int clmStructDetailsId;

	private int clmTypeId;

	private float clmAmt;

	private int clmStructHeadId;

	private String claimStructName;

	private String claimTypeTitle;

	private String claimTypeTitleShort;

	public int getClmStructDetailsId() {
		return clmStructDetailsId;
	}

	public void setClmStructDetailsId(int clmStructDetailsId) {
		this.clmStructDetailsId = clmStructDetailsId;
	}

	public int getClmTypeId() {
		return clmTypeId;
	}

	public void setClmTypeId(int clmTypeId) {
		this.clmTypeId = clmTypeId;
	}

	public float getClmAmt() {
		return clmAmt;
	}

	public void setClmAmt(float clmAmt) {
		this.clmAmt = clmAmt;
	}

	public int getClmStructHeadId() {
		return clmStructHeadId;
	}

	public void setClmStructHeadId(int clmStructHeadId) {
		this.clmStructHeadId = clmStructHeadId;
	}

	public String getClaimStructName() {
		return claimStructName;
	}

	public void setClaimStructName(String claimStructName) {
		this.claimStructName = claimStructName;
	}

	public String getClaimTypeTitle() {
		return claimTypeTitle;
	}

	public void setClaimTypeTitle(String claimTypeTitle) {
		this.claimTypeTitle = claimTypeTitle;
	}

	public String getClaimTypeTitleShort() {
		return claimTypeTitleShort;
	}

	public void setClaimTypeTitleShort(String claimTypeTitleShort) {
		this.claimTypeTitleShort = claimTypeTitleShort;
	}

	@Override
	public String toString() {
		return "GetEmployeeClaimStrudt [clmStructDetailsId=" + clmStructDetailsId + ", clmTypeId=" + clmTypeId
				+ ", clmAmt=" + clmAmt + ", clmStructHeadId=" + clmStructHeadId + ", claimStructName=" + claimStructName
				+ ", claimTypeTitle=" + claimTypeTitle + ", claimTypeTitleShort=" + claimTypeTitleShort + "]";
	}
	
	
	
	
}
