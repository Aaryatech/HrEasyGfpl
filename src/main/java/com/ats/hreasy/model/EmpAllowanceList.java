package com.ats.hreasy.model;

public class EmpAllowanceList {
	
	private int allowanceId;
	private String allowanceName;
	private double value;
	public int getAllowanceId() {
		return allowanceId;
	}
	public void setAllowanceId(int allowanceId) {
		this.allowanceId = allowanceId;
	}
	public String getAllowanceName() {
		return allowanceName;
	}
	public void setAllowanceName(String allowanceName) {
		this.allowanceName = allowanceName;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "EmpAllowanceList [allowanceId=" + allowanceId + ", allowanceName=" + allowanceName + ", value=" + value
				+ "]";
	}
	
	

}
