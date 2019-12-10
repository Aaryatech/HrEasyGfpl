package com.ats.hreasy.model;

public class Designation {	
	
	private int desigId;
	private String name;
	private String remarks;
	private int isActive;
	private String nameSd;
	private int delStatus;
	private int companyId;
	private String makerEnterDatetime;
	private int exInt1;
	private String exVar1;
	private int exInt2;
	private String exVar2;
	
	public int getDesigId() {
		return desigId;
	}
	public void setDesigId(int desigId) {
		this.desigId = desigId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getNameSd() {
		return nameSd;
	}
	public void setNameSd(String nameSd) {
		this.nameSd = nameSd;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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
	public String getExVar1() {
		return exVar1;
	}
	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}
	public int getExInt2() {
		return exInt2;
	}
	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}
	public String getExVar2() {
		return exVar2;
	}
	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}
	@Override
	public String toString() {
		return "Designation [desigId=" + desigId + ", name=" + name + ", remarks=" + remarks + ", isActive=" + isActive
				+ ", nameSd=" + nameSd + ", delStatus=" + delStatus + ", companyId=" + companyId
				+ ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1=" + exInt1 + ", exVar1=" + exVar1
				+ ", exInt2=" + exInt2 + ", exVar2=" + exVar2 + "]";
	}
	
	
}
