package com.ats.hreasy.model.claim;

 
public class GetClaimApplyAuthWise {
	

 
	private int claimId;
		
	private int empId;
	
	private int projectId;

	private String claimDate;

	private float claimAmount;

	private String claimRemarks;

	private int claimFinalStatus;

	private String circulatedTo;
	
	private String claimTypeTitle;
	
	private String empFname;
	
	private String empMname;
	
	private String empSname;
	
	private String empCode;
	
	private String empPhoto;
		
	private String projectTypeTitle;

	public int getClaimId() {
		return claimId;
	}

	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(String claimDate) {
		this.claimDate = claimDate;
	}

	public float getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(float claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getClaimRemarks() {
		return claimRemarks;
	}

	public void setClaimRemarks(String claimRemarks) {
		this.claimRemarks = claimRemarks;
	}

	public int getClaimFinalStatus() {
		return claimFinalStatus;
	}

	public void setClaimFinalStatus(int claimFinalStatus) {
		this.claimFinalStatus = claimFinalStatus;
	}

	public String getCirculatedTo() {
		return circulatedTo;
	}

	public void setCirculatedTo(String circulatedTo) {
		this.circulatedTo = circulatedTo;
	}

	public String getClaimTypeTitle() {
		return claimTypeTitle;
	}

	public void setClaimTypeTitle(String claimTypeTitle) {
		this.claimTypeTitle = claimTypeTitle;
	}

	public String getEmpFname() {
		return empFname;
	}

	public void setEmpFname(String empFname) {
		this.empFname = empFname;
	}

	public String getEmpMname() {
		return empMname;
	}

	public void setEmpMname(String empMname) {
		this.empMname = empMname;
	}

	public String getEmpSname() {
		return empSname;
	}

	public void setEmpSname(String empSname) {
		this.empSname = empSname;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpPhoto() {
		return empPhoto;
	}

	public void setEmpPhoto(String empPhoto) {
		this.empPhoto = empPhoto;
	}

	public String getProjectTypeTitle() {
		return projectTypeTitle;
	}

	public void setProjectTypeTitle(String projectTypeTitle) {
		this.projectTypeTitle = projectTypeTitle;
	}

	@Override
	public String toString() {
		return "GetClaimApplyAuthWise [claimId=" + claimId + ", empId=" + empId + ", projectId=" + projectId
				+ ", claimDate=" + claimDate + ", claimAmount=" + claimAmount + ", claimRemarks=" + claimRemarks
				+ ", claimFinalStatus=" + claimFinalStatus + ", circulatedTo=" + circulatedTo + ", claimTypeTitle="
				+ claimTypeTitle + ", empFname=" + empFname + ", empMname=" + empMname + ", empSname=" + empSname
				+ ", empCode=" + empCode + ", empPhoto=" + empPhoto + ", projectTypeTitle=" + projectTypeTitle + "]";
	}
	
	
}
