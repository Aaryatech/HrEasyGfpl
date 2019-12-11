package com.ats.hreasy.model;

public class Department {

	private int departId;
	private String name;
	private String nameSd;
	private String remarks;
	private int isActive;
	private int companyId;
	private int delStatus;
	private String makerEnterDatetime;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	
	public int getDepartId() {
		return departId;
	}
	public void setDepartId(int departId) {
		this.departId = departId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameSd() {
		return nameSd;
	}
	public void setNameSd(String nameSd) {
		this.nameSd = nameSd;
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
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
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
	
	
	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}
	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
	}
	@Override
	public String toString() {
		return "Department [departId=" + departId + ", name=" + name + ", nameSd=" + nameSd + ", remarks=" + remarks
				+ ", isActive=" + isActive + ", companyId=" + companyId + ", delStatus=" + delStatus
				+ ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", getDepartId()=" + getDepartId() + ", getName()="
				+ getName() + ", getNameSd()=" + getNameSd() + ", getRemarks()=" + getRemarks() + ", getIsActive()="
				+ getIsActive() + ", getCompanyId()=" + getCompanyId() + ", getDelStatus()=" + getDelStatus()
				+ ", getExInt1()=" + getExInt1() + ", getExInt2()=" + getExInt2() + ", getExVar1()=" + getExVar1()
				+ ", getExVar2()=" + getExVar2() + ", getMakerEnterDatetime()=" + getMakerEnterDatetime()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
