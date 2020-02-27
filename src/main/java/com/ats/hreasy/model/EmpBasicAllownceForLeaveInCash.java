package com.ats.hreasy.model;

public class EmpBasicAllownceForLeaveInCash {
	private int empId;
	private double basic;
	private double allowanceValue;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public double getBasic() {
		return basic;
	}
	public void setBasic(double basic) {
		this.basic = basic;
	}
	public double getAllowanceValue() {
		return allowanceValue;
	}
	public void setAllowanceValue(double allowanceValue) {
		this.allowanceValue = allowanceValue;
	}
	@Override
	public String toString() {
		return "EmpBasicAllownceForLeaveInCash [empId=" + empId + ", basic=" + basic + ", allowanceValue="
				+ allowanceValue + "]";
	}
	
	
}
