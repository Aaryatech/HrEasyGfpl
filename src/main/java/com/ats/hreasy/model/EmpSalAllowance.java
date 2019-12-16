package com.ats.hreasy.model;

public class EmpSalAllowance {

	private int empSalAllowanceId;
	private int empId;
	private int allowanceId;
	private Double allowanceValue;
	private String makerEnterDatetime;
	public int getEmpSalAllowanceId() {
		return empSalAllowanceId;
	}
	public void setEmpSalAllowanceId(int empSalAllowanceId) {
		this.empSalAllowanceId = empSalAllowanceId;
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
	public Double getAllowanceValue() {
		return allowanceValue;
	}
	public void setAllowanceValue(Double allowanceValue) {
		this.allowanceValue = allowanceValue;
	}
	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}
	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
	}
	@Override
	public String toString() {
		return "EmpSalAllowance [empSalAllowanceId=" + empSalAllowanceId + ", empId=" + empId + ", allowanceId="
				+ allowanceId + ", allowanceValue=" + allowanceValue + ", makerEnterDatetime=" + makerEnterDatetime
				+ "]";
	}
	
	
	
}
