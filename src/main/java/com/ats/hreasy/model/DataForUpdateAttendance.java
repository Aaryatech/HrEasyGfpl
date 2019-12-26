package com.ats.hreasy.model;

import java.util.List;
 

public class DataForUpdateAttendance {
	
	private String fromDate;
	private String toDate;
	private int month;
	private int year;
	private int userId;
	private int empId;
	private List<FileUploadedData> fileUploadedDataList;
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
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
	public List<FileUploadedData> getFileUploadedDataList() {
		return fileUploadedDataList;
	}
	public void setFileUploadedDataList(List<FileUploadedData> fileUploadedDataList) {
		this.fileUploadedDataList = fileUploadedDataList;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	@Override
	public String toString() {
		return "DataForUpdateAttendance [fromDate=" + fromDate + ", toDate=" + toDate + ", month=" + month + ", year="
				+ year + ", userId=" + userId + ", empId=" + empId + ", fileUploadedDataList=" + fileUploadedDataList
				+ "]";
	}
	
	

}
