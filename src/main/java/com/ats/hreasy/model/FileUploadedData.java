package com.ats.hreasy.model;

public class FileUploadedData {
	
	private String empCode;
	private String empName;
	private String logDate;
	private String inTime;
	private String outTime;
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	@Override
	public String toString() {
		return "FileUploadedData [empCode=" + empCode + ", empName=" + empName + ", logDate=" + logDate + ", inTime="
				+ inTime + ", outTime=" + outTime + "]";
	}
	
	

}
