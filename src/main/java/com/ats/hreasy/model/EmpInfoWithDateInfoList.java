package com.ats.hreasy.model;

import java.util.List;

public class EmpInfoWithDateInfoList {
	
	private String empCode;
	private int empId;
	private String empName;
	
	private List<DailyDailyInfomationForChart> sttsList;
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public List<DailyDailyInfomationForChart> getSttsList() {
		return sttsList;
	}
	public void setSttsList(List<DailyDailyInfomationForChart> sttsList) {
		this.sttsList = sttsList;
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
	@Override
	public String toString() {
		return "EmpInfoWithDateInfoList [empCode=" + empCode + ", empId=" + empId + ", empName=" + empName
				+ ", sttsList=" + sttsList + "]";
	}
	
	

}
