package com.ats.hreasy.model;

public class Allowances {

	private int allowanceId;
	private String name;
	private String shortName;
	private int short_no;
	private String description;
	private int is_taxable;
	private int del_status;
	private int is_active;
	private String makerEnterDatetime;
	

	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;

	private float grossSalPer;

	private float taxPer;
	public int getAllowanceId() {
		return allowanceId;
	}
	public void setAllowanceId(int allowanceId) {
		this.allowanceId = allowanceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public int getShort_no() {
		return short_no;
	}
	public void setShort_no(int short_no) {
		this.short_no = short_no;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIs_taxable() {
		return is_taxable;
	}
	public void setIs_taxable(int is_taxable) {
		this.is_taxable = is_taxable;
	}
	public int getDel_status() {
		return del_status;
	}
	public void setDel_status(int del_status) {
		this.del_status = del_status;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
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
	public float getGrossSalPer() {
		return grossSalPer;
	}
	public void setGrossSalPer(float grossSalPer) {
		this.grossSalPer = grossSalPer;
	}
	public float getTaxPer() {
		return taxPer;
	}
	public void setTaxPer(float taxPer) {
		this.taxPer = taxPer;
	}
	@Override
	public String toString() {
		return "Allowances [allowanceId=" + allowanceId + ", name=" + name + ", shortName=" + shortName + ", short_no="
				+ short_no + ", description=" + description + ", is_taxable=" + is_taxable + ", del_status="
				+ del_status + ", is_active=" + is_active + ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", grossSalPer="
				+ grossSalPer + ", taxPer=" + taxPer + "]";
	}
	 
	
	
	
}
