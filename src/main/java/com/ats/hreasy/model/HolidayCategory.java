package com.ats.hreasy.model;


public class HolidayCategory {

	private int hoCatId;

	private String hoCatName;

	private int delStatus;

	private int isActive;

	private int makerUserId;

	private String makerEnterDatetime;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;
	 
 	private boolean isError;

	private String hoCatShortName;
	
	private int  companyId ;
	
	
	private String  remark ;
 
	public String getHoCatShortName() {
		return hoCatShortName;
	}

	public void setHoCatShortName(String hoCatShortName) {
		this.hoCatShortName = hoCatShortName;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public int getHoCatId() {
		return hoCatId;
	}

	public void setHoCatId(int hoCatId) {
		this.hoCatId = hoCatId;
	}

	public String getHoCatName() {
		return hoCatName;
	}

	public void setHoCatName(String hoCatName) {
		this.hoCatName = hoCatName;
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

	
	
	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "HolidayCategory [hoCatId=" + hoCatId + ", hoCatName=" + hoCatName + ", delStatus=" + delStatus
				+ ", isActive=" + isActive + ", makerUserId=" + makerUserId + ", makerEnterDatetime="
				+ makerEnterDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2="
				+ exVar2 + ", isError=" + isError + ", hoCatShortName=" + hoCatShortName + ", companyId=" + companyId
				+ ", remark=" + remark + ", getHoCatShortName()=" + getHoCatShortName() + ", isError()=" + isError()
				+ ", getHoCatId()=" + getHoCatId() + ", getHoCatName()=" + getHoCatName() + ", getDelStatus()="
				+ getDelStatus() + ", getIsActive()=" + getIsActive() + ", getMakerUserId()=" + getMakerUserId()
				+ ", getMakerEnterDatetime()=" + getMakerEnterDatetime() + ", getExInt1()=" + getExInt1()
				+ ", getExInt2()=" + getExInt2() + ", getExVar1()=" + getExVar1() + ", getExVar2()=" + getExVar2()
				+ ", getCompanyId()=" + getCompanyId() + ", getRemark()=" + getRemark() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	 

	  
}
