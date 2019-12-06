package com.ats.hreasy.model;
 

public class AuthorityInformation {
	 
	private int empId; 
	private String leaveInitialAuth; 
	private String leaveFinalAuth; 
	private String claimInitialAuth; 
	private String claimFinalAuth;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getLeaveInitialAuth() {
		return leaveInitialAuth;
	}
	public void setLeaveInitialAuth(String leaveInitialAuth) {
		this.leaveInitialAuth = leaveInitialAuth;
	}
	public String getLeaveFinalAuth() {
		return leaveFinalAuth;
	}
	public void setLeaveFinalAuth(String leaveFinalAuth) {
		this.leaveFinalAuth = leaveFinalAuth;
	}
	public String getClaimInitialAuth() {
		return claimInitialAuth;
	}
	public void setClaimInitialAuth(String claimInitialAuth) {
		this.claimInitialAuth = claimInitialAuth;
	}
	public String getClaimFinalAuth() {
		return claimFinalAuth;
	}
	public void setClaimFinalAuth(String claimFinalAuth) {
		this.claimFinalAuth = claimFinalAuth;
	}
	@Override
	public String toString() {
		return "AuthorityInformation [empId=" + empId + ", leaveInitialAuth=" + leaveInitialAuth + ", leaveFinalAuth="
				+ leaveFinalAuth + ", claimInitialAuth=" + claimInitialAuth + ", claimFinalAuth=" + claimFinalAuth
				+ "]";
	}
	
	

}
