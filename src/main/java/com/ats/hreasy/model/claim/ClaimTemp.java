package com.ats.hreasy.model.claim;

public class ClaimTemp {

	private int claimProjectId;
	
	private int claimTypeId;
	
	private int empId;

	private int ClaimAmt;
	
	private String ClaimDate;
	
	private String claimRemark;

	public int getClaimProjectId() {
		return claimProjectId;
	}

	public void setClaimProjectId(int claimProjectId) {
		this.claimProjectId = claimProjectId;
	}

	public int getClaimTypeId() {
		return claimTypeId;
	}

	public void setClaimTypeId(int claimTypeId) {
		this.claimTypeId = claimTypeId;
	}

	public int getClaimAmt() {
		return ClaimAmt;
	}

	public void setClaimAmt(int claimAmt) {
		ClaimAmt = claimAmt;
	}

	public String getClaimDate() {
		return ClaimDate;
	}

	public void setClaimDate(String claimDate) {
		ClaimDate = claimDate;
	}

	public String getClaimRemark() {
		return claimRemark;
	}

	public void setClaimRemark(String claimRemark) {
		this.claimRemark = claimRemark;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "ClaimTemp [claimProjectId=" + claimProjectId + ", claimTypeId=" + claimTypeId + ", empId=" + empId
				+ ", ClaimAmt=" + ClaimAmt + ", ClaimDate=" + ClaimDate + ", claimRemark=" + claimRemark
				+ ", getClaimProjectId()=" + getClaimProjectId() + ", getClaimTypeId()=" + getClaimTypeId()
				+ ", getClaimAmt()=" + getClaimAmt() + ", getClaimDate()=" + getClaimDate() + ", getClaimRemark()="
				+ getClaimRemark() + ", getEmpId()=" + getEmpId() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	 
}
