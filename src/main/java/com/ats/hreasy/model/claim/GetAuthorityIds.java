package com.ats.hreasy.model.claim;

 
public class GetAuthorityIds {
	
	
 
	private int empId;
	
	private int iniAuthEmpId;
	
	private int finAuthEmpId;
	
	private String repToEmpIds;
	
	

	public String getRepToEmpIds() {
		return repToEmpIds;
	}

	public void setRepToEmpIds(String repToEmpIds) {
		this.repToEmpIds = repToEmpIds;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getIniAuthEmpId() {
		return iniAuthEmpId;
	}

	public void setIniAuthEmpId(int iniAuthEmpId) {
		this.iniAuthEmpId = iniAuthEmpId;
	}

	public int getFinAuthEmpId() {
		return finAuthEmpId;
	}

	public void setFinAuthEmpId(int finAuthEmpId) {
		this.finAuthEmpId = finAuthEmpId;
	}

	@Override
	public String toString() {
		return "GetAuthorityIds [empId=" + empId + ", iniAuthEmpId=" + iniAuthEmpId + ", finAuthEmpId=" + finAuthEmpId
				+ ", getEmpId()=" + getEmpId() + ", getIniAuthEmpId()=" + getIniAuthEmpId() + ", getFinAuthEmpId()="
				+ getFinAuthEmpId() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	


}
