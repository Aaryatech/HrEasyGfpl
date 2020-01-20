package com.ats.hreasy.model.report;
 
public class GetYearlyAdvance {

	 
	private int empId;
	
	private int month ;
	
	private int year;
 	 
	private String empName;
	
	private String empCode;
	
	private String advAmount;

 
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getAdvAmount() {
		return advAmount;
	}

	public void setAdvAmount(String advAmount) {
		this.advAmount = advAmount;
	}

	@Override
	public String toString() {
		return "GetYearlyAdvance [ month=" + month + ", year=" + year + ", empId=" + empId
				+ ", empName=" + empName + ", empCode=" + empCode + ", advAmount=" + advAmount + "]";
	}
	
	 
	
	
}
