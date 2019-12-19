package com.ats.hreasy.model.claim;

 
public class GetClaimHead {

	 
	private int caHeadId;

	private int empId;

	private int projectId;

	private String projectTitle;

	private String claimTitle;

	private String claimFromDate;

	private String claimToDate;

	private float claimAmount;

	private int claimFinalStatus;

	private String exVar1;

	private String empFname;

	private String empSname;

	private String empCode;

	private String empDeptName;

	public int getCaHeadId() {
		return caHeadId;
	}

	public void setCaHeadId(int caHeadId) {
		this.caHeadId = caHeadId;
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

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getClaimTitle() {
		return claimTitle;
	}

	public void setClaimTitle(String claimTitle) {
		this.claimTitle = claimTitle;
	}

	public String getClaimFromDate() {
		return claimFromDate;
	}

	public void setClaimFromDate(String claimFromDate) {
		this.claimFromDate = claimFromDate;
	}

	public String getClaimToDate() {
		return claimToDate;
	}

	public void setClaimToDate(String claimToDate) {
		this.claimToDate = claimToDate;
	}

	public float getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(float claimAmount) {
		this.claimAmount = claimAmount;
	}

	public int getClaimFinalStatus() {
		return claimFinalStatus;
	}

	public void setClaimFinalStatus(int claimFinalStatus) {
		this.claimFinalStatus = claimFinalStatus;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getEmpFname() {
		return empFname;
	}

	public void setEmpFname(String empFname) {
		this.empFname = empFname;
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

	public String getEmpDeptName() {
		return empDeptName;
	}

	public void setEmpDeptName(String empDeptName) {
		this.empDeptName = empDeptName;
	}

	@Override
	public String toString() {
		return "GetClaimHead [caHeadId=" + caHeadId + ", empId=" + empId + ", projectId=" + projectId
				+ ", projectTitle=" + projectTitle + ", claimTitle=" + claimTitle + ", claimFromDate=" + claimFromDate
				+ ", claimToDate=" + claimToDate + ", claimAmount=" + claimAmount + ", claimFinalStatus="
				+ claimFinalStatus + ", exVar1=" + exVar1 + ", empFname=" + empFname + ", empSname=" + empSname
				+ ", empCode=" + empCode + ", empDeptName=" + empDeptName + ", getCaHeadId()=" + getCaHeadId()
				+ ", getEmpId()=" + getEmpId() + ", getProjectId()=" + getProjectId() + ", getProjectTitle()="
				+ getProjectTitle() + ", getClaimTitle()=" + getClaimTitle() + ", getClaimFromDate()="
				+ getClaimFromDate() + ", getClaimToDate()=" + getClaimToDate() + ", getClaimAmount()="
				+ getClaimAmount() + ", getClaimFinalStatus()=" + getClaimFinalStatus() + ", getExVar1()=" + getExVar1()
				+ ", getEmpFname()=" + getEmpFname() + ", getEmpSname()=" + getEmpSname() + ", getEmpCode()="
				+ getEmpCode() + ", getEmpDeptName()=" + getEmpDeptName() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	 
}
