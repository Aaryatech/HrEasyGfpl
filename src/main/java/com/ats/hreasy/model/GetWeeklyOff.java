package com.ats.hreasy.model;


public class GetWeeklyOff {

	private int woId;

	private int companyId;

	private String woType;

	private String locId;

	private String woPresently;

	private String woDay;

	private String woRemarks;

	private int woIsUsed;

	private int delStatus;

	private int isActive;

	private int makerUserId;

	private String makerEnterDatetime;

	private int exInt1;
	private int exInt2;

	private int exInt3;

	private String exVar1;

	private String exVar2;

	private String exVar3;

	private String locName;
	
	
	private String weekOffCat;


	public int getWoId() {
		return woId;
	}

	public void setWoId(int woId) {
		this.woId = woId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getWoType() {
		return woType;
	}

	public void setWoType(String woType) {
		this.woType = woType;
	}

	public String getLocId() {
		return locId;
	}

	public void setLocId(String locId) {
		this.locId = locId;
	}

	public String getWoPresently() {
		return woPresently;
	}

	public void setWoPresently(String woPresently) {
		this.woPresently = woPresently;
	}

	public String getWoDay() {
		return woDay;
	}

	public void setWoDay(String woDay) {
		this.woDay = woDay;
	}

	public String getWoRemarks() {
		return woRemarks;
	}

	public void setWoRemarks(String woRemarks) {
		this.woRemarks = woRemarks;
	}

	public int getWoIsUsed() {
		return woIsUsed;
	}

	public void setWoIsUsed(int woIsUsed) {
		this.woIsUsed = woIsUsed;
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

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}
	
	

	public String getWeekOffCat() {
		return weekOffCat;
	}

	public void setWeekOffCat(String weekOffCat) {
		this.weekOffCat = weekOffCat;
	}

	@Override
	public String toString() {
		return "GetWeeklyOff [woId=" + woId + ", companyId=" + companyId + ", woType=" + woType + ", locId=" + locId
				+ ", woPresently=" + woPresently + ", woDay=" + woDay + ", woRemarks=" + woRemarks + ", woIsUsed="
				+ woIsUsed + ", delStatus=" + delStatus + ", isActive=" + isActive + ", makerUserId=" + makerUserId
				+ ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3
				+ ", locName=" + locName + ", weekOffCat=" + weekOffCat + "]";
	}

	 

}
