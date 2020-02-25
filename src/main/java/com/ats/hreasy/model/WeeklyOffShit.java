package com.ats.hreasy.model;
 
public class WeeklyOffShit {
	
	 
	private int id;

 	private int month;

 	private int year;

 	private String weekofffromdate;

	private String weekoffshiftdate;

	private int cmpId;

	private String reason;

	private String loginTime;

	private int locationId;
	
	private int delStatus;
	
	private int empId;



	 
	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getWeekofffromdate() {
		return weekofffromdate;
	}

	public void setWeekofffromdate(String weekofffromdate) {
		this.weekofffromdate = weekofffromdate;
	}

	public String getWeekoffshiftdate() {
		return weekoffshiftdate;
	}

	public void setWeekoffshiftdate(String weekoffshiftdate) {
		this.weekoffshiftdate = weekoffshiftdate;
	}

	public int getCmpId() {
		return cmpId;
	}

	public void setCmpId(int cmpId) {
		this.cmpId = cmpId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	@Override
	public String toString() {
		return "WeeklyOffShit [id=" + id + ", month=" + month + ", year=" + year + ", weekofffromdate="
				+ weekofffromdate + ", weekoffshiftdate=" + weekoffshiftdate + ", cmpId=" + cmpId + ", reason=" + reason
				+ ", loginTime=" + loginTime + ", locationId=" + locationId + ", delStatus=" + delStatus + ", empId="
				+ empId + "]";
	}

	 
	 
}
