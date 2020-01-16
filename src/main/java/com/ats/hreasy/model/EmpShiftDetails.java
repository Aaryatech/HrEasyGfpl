package com.ats.hreasy.model;

import java.util.List;

 
public class EmpShiftDetails {
	
	private int empId;
	
	private String empName;
	
	
	private String empCode;
	
	
	private int shiftId;
	
	private int day;
	private List<EmployeeMaster> emplist ;
	
	private String shiftName;

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

	public int getShiftId() {
		return shiftId;
	}

	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	

	public List<EmployeeMaster> getEmplist() {
		return emplist;
	}

	public void setEmplist(List<EmployeeMaster> emplist) {
		this.emplist = emplist;
	}

	@Override
	public String toString() {
		return "EmpShiftDetails [empId=" + empId + ", empName=" + empName + ", empCode=" + empCode + ", shiftId="
				+ shiftId + ", day=" + day + ", emplist=" + emplist + ", shiftName=" + shiftName + "]";
	}

 
	 
}
