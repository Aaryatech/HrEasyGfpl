package com.ats.hreasy.model;
 

public class SalAllownceTemp {
	
	private int empSalAllowanceId; 
	private int tblSalaryDynamicTempId; 
	private int empId; 
	private int allowanceId; 
	private String shortName; 
	private double allowanceValue; 
	private double allowanceValueCal; 
	private String makerEnterDatetime; 
	private int delStatus; 
	private int exInt1; 
	private int exInt2; 
	private String exVar1; 
	private String exVar2;
	
	public int getEmpSalAllowanceId() {
		return empSalAllowanceId;
	}
	public void setEmpSalAllowanceId(int empSalAllowanceId) {
		this.empSalAllowanceId = empSalAllowanceId;
	}
	public int getTblSalaryDynamicTempId() {
		return tblSalaryDynamicTempId;
	}
	public void setTblSalaryDynamicTempId(int tblSalaryDynamicTempId) {
		this.tblSalaryDynamicTempId = tblSalaryDynamicTempId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getAllowanceId() {
		return allowanceId;
	}
	public void setAllowanceId(int allowanceId) {
		this.allowanceId = allowanceId;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public double getAllowanceValue() {
		return allowanceValue;
	}
	public void setAllowanceValue(double allowanceValue) {
		this.allowanceValue = allowanceValue;
	}
	public double getAllowanceValueCal() {
		return allowanceValueCal;
	}
	public void setAllowanceValueCal(double allowanceValueCal) {
		this.allowanceValueCal = allowanceValueCal;
	}
	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}
	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
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
	@Override
	public String toString() {
		return "SalAllownceTemp [empSalAllowanceId=" + empSalAllowanceId + ", tblSalaryDynamicTempId="
				+ tblSalaryDynamicTempId + ", empId=" + empId + ", allowanceId=" + allowanceId + ", shortName="
				+ shortName + ", allowanceValue=" + allowanceValue + ", allowanceValueCal=" + allowanceValueCal
				+ ", makerEnterDatetime=" + makerEnterDatetime + ", delStatus=" + delStatus + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}
	
	

}
