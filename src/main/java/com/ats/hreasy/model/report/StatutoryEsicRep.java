package com.ats.hreasy.model.report;

 
public class StatutoryEsicRep {
	private String keyNew;
	private int empId;

	private String empName;

	private String empCode;

	private String esicNo;

	private double netSalary;

	private double esicWagesCal;

	private double employerEsic;

	private int presentDays;
	private int month;

	private int year;

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

	public String getKeyNew() {
		return keyNew;
	}

	public void setKeyNew(String keyNew) {
		this.keyNew = keyNew;
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

	public String getEsicNo() {
		return esicNo;
	}

	public void setEsicNo(String esicNo) {
		this.esicNo = esicNo;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public double getEsicWagesCal() {
		return esicWagesCal;
	}

	public void setEsicWagesCal(double esicWagesCal) {
		this.esicWagesCal = esicWagesCal;
	}

	public double getEmployerEsic() {
		return employerEsic;
	}

	public void setEmployerEsic(double employerEsic) {
		this.employerEsic = employerEsic;
	}

	public int getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(int presentDays) {
		this.presentDays = presentDays;
	}

	@Override
	public String toString() {
		return "StatutoryEsicRep [keyNew=" + keyNew + ", empId=" + empId + ", empName=" + empName + ", empCode="
				+ empCode + ", esicNo=" + esicNo + ", netSalary=" + netSalary + ", esicWagesCal=" + esicWagesCal
				+ ", employerEsic=" + employerEsic + ", presentDays=" + presentDays + ", month=" + month + ", year="
				+ year + "]";
	}

}
