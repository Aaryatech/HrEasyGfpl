package com.ats.hreasy.model.claim;

import java.util.Date;

 
import com.fasterxml.jackson.annotation.JsonFormat;

 public class GetClaimTrailStatus {
	
 	private int claimTrailPkey;
	
	private int claimId ;
	
	private int empId;
	
	private String empRemarks;
	
	private String empCode;
	
	private String caFromDt;
	
	private String caToDt;
	
	private String projectTitle;
	
	private int claimStatus;
	
	private String claimTitle;
	
	private String claimAmount;
	
	private String userName;
	
	private String makerEnterDatetime;
	
	private String empFname;
	
	private String empMname;
	
	private String empSname;
	
  
	public String getClaimTitle() {
		return claimTitle;
	}

	public void setClaimTitle(String claimTitle) {
		this.claimTitle = claimTitle;
	}

	public String getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}

	public int getClaimTrailPkey() {
		return claimTrailPkey;
	}

	public void setClaimTrailPkey(int claimTrailPkey) {
		this.claimTrailPkey = claimTrailPkey;
	}

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

	public String getEmpRemarks() {
		return empRemarks;
	}

	public void setEmpRemarks(String empRemarks) {
		this.empRemarks = empRemarks;
	}

	public int getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(int claimStatus) {
		this.claimStatus = claimStatus;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

 	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}

	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
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

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	@Override
	public String toString() {
		return "GetClaimTrailStatus [claimTrailPkey=" + claimTrailPkey + ", claimId=" + claimId + ", empId=" + empId
				+ ", empRemarks=" + empRemarks + ", empCode=" + empCode + ", caFromDt=" + caFromDt + ", caToDt="
				+ caToDt + ", projectTitle=" + projectTitle + ", claimStatus=" + claimStatus + ", claimTitle="
				+ claimTitle + ", claimAmount=" + claimAmount + ", userName=" + userName + ", makerEnterDatetime="
				+ makerEnterDatetime + ", empFname=" + empFname + ", empMname=" + empMname + ", empSname=" + empSname
				+ ", getClaimTitle()=" + getClaimTitle() + ", getClaimAmount()=" + getClaimAmount()
				+ ", getClaimTrailPkey()=" + getClaimTrailPkey() + ", getClaimId()=" + getClaimId() + ", getEmpId()="
				+ getEmpId() + ", getEmpRemarks()=" + getEmpRemarks() + ", getClaimStatus()=" + getClaimStatus()
				+ ", getUserName()=" + getUserName() + ", getMakerEnterDatetime()=" + getMakerEnterDatetime()
				+ ", getEmpFname()=" + getEmpFname() + ", getEmpMname()=" + getEmpMname() + ", getEmpSname()="
				+ getEmpSname() + ", getEmpCode()=" + getEmpCode() + ", getCaFromDt()=" + getCaFromDt()
				+ ", getCaToDt()=" + getCaToDt() + ", getProjectTitle()=" + getProjectTitle() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	 
	 
 
	 
	
}
