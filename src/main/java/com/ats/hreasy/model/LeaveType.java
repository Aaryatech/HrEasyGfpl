package com.ats.hreasy.model;

public class LeaveType {
	
	private int lvTypeId ;
	
	private int companyId ;
	
	private String lvTitle;
	
	private String lvTitleShort;
	
	private int lvSumupId;
	
	private int lvWorkingHrs;
	
	private String lvColor;
	
	private int isStructured;
	private int isFile;
	
	private String lvRmarks;
	
	private int delStatus;
	
	private int isActive;
	
	private int makerUserId ;
	
	private String makerEnterDatetime;
	
	private int exInt1;
	
	private int exInt2;
	
	private int exInt3;
	
	private String exVar1; 
	
	private String exVar2; 
	
	private String exVar3;
	
	private boolean error;


	
	public int getIsFile() {
		return isFile;
	}

	public void setIsFile(int isFile) {
		this.isFile = isFile;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}


	public int getLvTypeId() {
		return lvTypeId;
	}

	public void setLvTypeId(int lvTypeId) {
		this.lvTypeId = lvTypeId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}


	public void setLvSumupId(int lvSumupId) {
		this.lvSumupId = lvSumupId;
	}

	public int getLvWorkingHrs() {
		return lvWorkingHrs;
	}

	public void setLvWorkingHrs(int lvWorkingHrs) {
		this.lvWorkingHrs = lvWorkingHrs;
	}


	public String getLvTitle() {
		return lvTitle;
	}

	public void setLvTitle(String lvTitle) {
		this.lvTitle = lvTitle;
	}

	public String getLvTitleShort() {
		return lvTitleShort;
	}

	public void setLvTitleShort(String lvTitleShort) {
		this.lvTitleShort = lvTitleShort;
	}

	public String getLvColor() {
		return lvColor;
	}

	public void setLvColor(String lvColor) {
		this.lvColor = lvColor;
	}

	public int getLvSumupId() {
		return lvSumupId;
	}

	public int getIsStructured() {
		return isStructured;
	}

	public void setIsStructured(int isStructured) {
		this.isStructured = isStructured;
	}

	

	public String getLvRmarks() {
		return lvRmarks;
	}

	public void setLvRmarks(String lvRmarks) {
		this.lvRmarks = lvRmarks;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getMakerUserId() {
		return makerUserId;
	}

	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}

	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}

	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public int getExInt3() {
		return exInt3;
	}

	public void setExInt3(int exInt3) {
		this.exInt3 = exInt3;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}

	@Override
	public String toString() {
		return "LeaveType [lvTypeId=" + lvTypeId + ", companyId=" + companyId + ", lvTitle=" + lvTitle
				+ ", lvTitleShort=" + lvTitleShort + ", lvSumupId=" + lvSumupId + ", lvWorkingHrs=" + lvWorkingHrs
				+ ", lvColor=" + lvColor + ", isStructured=" + isStructured + ", isFile=" + isFile + ", lvRmarks="
				+ lvRmarks + ", delStatus=" + delStatus + ", isActive=" + isActive + ", makerUserId=" + makerUserId
				+ ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", error="
				+ error + "]";
	}

	 
	
	
}
	