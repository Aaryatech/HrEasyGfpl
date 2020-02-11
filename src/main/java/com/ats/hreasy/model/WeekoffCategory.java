package com.ats.hreasy.model;
 
public class WeekoffCategory {
	 
 
	private int woCatId;

	private String woCatName;

	private String woCatShortName;

	private int delStatus;

	private int isActive;

	private int makerUserId;

	private String makerEnterDatetime;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	private boolean isError;

	private int companyId;

	private String remark;

	public int getWoCatId() {
		return woCatId;
	}

	public void setWoCatId(int woCatId) {
		this.woCatId = woCatId;
	}

	public String getWoCatName() {
		return woCatName;
	}

	public void setWoCatName(String woCatName) {
		this.woCatName = woCatName;
	}

	public String getWoCatShortName() {
		return woCatShortName;
	}

	public void setWoCatShortName(String woCatShortName) {
		this.woCatShortName = woCatShortName;
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

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
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
		return "WeekoffCategory [woCatId=" + woCatId + ", woCatName=" + woCatName + ", woCatShortName=" + woCatShortName
				+ ", delStatus=" + delStatus + ", isActive=" + isActive + ", makerUserId=" + makerUserId
				+ ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", isError=" + isError + ", companyId=" + companyId
				+ ", remark=" + remark + "]";
	}
	
	
	
	

}
