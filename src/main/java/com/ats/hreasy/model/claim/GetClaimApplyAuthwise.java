package com.ats.hreasy.model.claim;

import java.util.Date;

 
public class GetClaimApplyAuthwise {

	 
	private int caHeadId;

	private int empId;

	private String empCode;

	private String empName;

	private int projId;

	private String claimTitle;

	private String projectTitle;

	private String caFromDt;

	private String caToDt;
	
	private int claimStatus;

	private float claimAmount;

	private String circulatedTo;

	private String exVar1;

	private int caIniAuthEmpId;

	private int caFinAuthEmpId;

	private String empPhoto;

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

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getProjId() {
		return projId;
	}

	public void setProjId(int projId) {
		this.projId = projId;
	}

	public String getClaimTitle() {
		return claimTitle;
	}

	public void setClaimTitle(String claimTitle) {
		this.claimTitle = claimTitle;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	 

	public String getCaFromDt() {
		return caFromDt;
	}

	public void setCaFromDt(String caFromDt) {
		this.caFromDt = caFromDt;
	}

	public String getCaToDt() {
		return caToDt;
	}

	public void setCaToDt(String caToDt) {
		this.caToDt = caToDt;
	}

	public int getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(int claimStatus) {
		this.claimStatus = claimStatus;
	}

	public float getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(float claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getCirculatedTo() {
		return circulatedTo;
	}

	public void setCirculatedTo(String circulatedTo) {
		this.circulatedTo = circulatedTo;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public int getCaIniAuthEmpId() {
		return caIniAuthEmpId;
	}

	public void setCaIniAuthEmpId(int caIniAuthEmpId) {
		this.caIniAuthEmpId = caIniAuthEmpId;
	}

	public int getCaFinAuthEmpId() {
		return caFinAuthEmpId;
	}

	public void setCaFinAuthEmpId(int caFinAuthEmpId) {
		this.caFinAuthEmpId = caFinAuthEmpId;
	}

	public String getEmpPhoto() {
		return empPhoto;
	}

	public void setEmpPhoto(String empPhoto) {
		this.empPhoto = empPhoto;
	}

	@Override
	public String toString() {
		return "GetClaimApplyAuthwise [caHeadId=" + caHeadId + ", empId=" + empId + ", empCode=" + empCode
				+ ", empName=" + empName + ", projId=" + projId + ", claimTitle=" + claimTitle + ", projectTitle="
				+ projectTitle + ", caFromDt=" + caFromDt + ", caToDt=" + caToDt + ", claimStatus=" + claimStatus
				+ ", claimAmount=" + claimAmount + ", circulatedTo=" + circulatedTo + ", exVar1=" + exVar1
				+ ", caIniAuthEmpId=" + caIniAuthEmpId + ", caFinAuthEmpId=" + caFinAuthEmpId + ", empPhoto=" + empPhoto
				+ ", getCaHeadId()=" + getCaHeadId() + ", getEmpId()=" + getEmpId() + ", getEmpCode()=" + getEmpCode()
				+ ", getEmpName()=" + getEmpName() + ", getProjId()=" + getProjId() + ", getClaimTitle()="
				+ getClaimTitle() + ", getProjectTitle()=" + getProjectTitle() + ", getCaFromDt()=" + getCaFromDt()
				+ ", getCaToDt()=" + getCaToDt() + ", getClaimStatus()=" + getClaimStatus() + ", getClaimAmount()="
				+ getClaimAmount() + ", getCirculatedTo()=" + getCirculatedTo() + ", getExVar1()=" + getExVar1()
				+ ", getCaIniAuthEmpId()=" + getCaIniAuthEmpId() + ", getCaFinAuthEmpId()=" + getCaFinAuthEmpId()
				+ ", getEmpPhoto()=" + getEmpPhoto() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
