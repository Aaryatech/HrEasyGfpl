package com.ats.hreasy.model;
 

public class MstWeeklyOff {
	
	 
	private int empTypeId ; 
	private String name ; 
	private String category ; 
	private String attType; 
	private String lmApplicable; 
	private String halfDay; 
	private String whWork;  
	private String minWorkHr;  
	private String minworkApplicable;  
	private String otApplicable ; 
	private String otTime; 
	private String details; 
	private String otType; 
	private int companyId;  
	private int weeklyHolidayLateAllowed;  
	private int weeklyHolidayLateAllowedMin; 
	private int earlyGoingAllowed; 
	private int earlyGoingMin; 
	private int maxLateTimeAllowed; 
	private int status;

	public int getEmpTypeId() {
		return empTypeId;
	}

	public void setEmpTypeId(int empTypeId) {
		this.empTypeId = empTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAttType() {
		return attType;
	}

	public void setAttType(String attType) {
		this.attType = attType;
	}

	public String getLmApplicable() {
		return lmApplicable;
	}

	public void setLmApplicable(String lmApplicable) {
		this.lmApplicable = lmApplicable;
	}

	public String getHalfDay() {
		return halfDay;
	}

	public void setHalfDay(String halfDay) {
		this.halfDay = halfDay;
	}

	public String getWhWork() {
		return whWork;
	}

	public void setWhWork(String whWork) {
		this.whWork = whWork;
	}

	public String getMinWorkHr() {
		return minWorkHr;
	}

	public void setMinWorkHr(String minWorkHr) {
		this.minWorkHr = minWorkHr;
	}

	public String getMinworkApplicable() {
		return minworkApplicable;
	}

	public void setMinworkApplicable(String minworkApplicable) {
		this.minworkApplicable = minworkApplicable;
	}

	public String getOtApplicable() {
		return otApplicable;
	}

	public void setOtApplicable(String otApplicable) {
		this.otApplicable = otApplicable;
	}

	public String getOtTime() {
		return otTime;
	}

	public void setOtTime(String otTime) {
		this.otTime = otTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getOtType() {
		return otType;
	}

	public void setOtType(String otType) {
		this.otType = otType;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getWeeklyHolidayLateAllowed() {
		return weeklyHolidayLateAllowed;
	}

	public void setWeeklyHolidayLateAllowed(int weeklyHolidayLateAllowed) {
		this.weeklyHolidayLateAllowed = weeklyHolidayLateAllowed;
	}

	public int getWeeklyHolidayLateAllowedMin() {
		return weeklyHolidayLateAllowedMin;
	}

	public void setWeeklyHolidayLateAllowedMin(int weeklyHolidayLateAllowedMin) {
		this.weeklyHolidayLateAllowedMin = weeklyHolidayLateAllowedMin;
	}

	public int getEarlyGoingAllowed() {
		return earlyGoingAllowed;
	}

	public void setEarlyGoingAllowed(int earlyGoingAllowed) {
		this.earlyGoingAllowed = earlyGoingAllowed;
	}

	public int getEarlyGoingMin() {
		return earlyGoingMin;
	}

	public void setEarlyGoingMin(int earlyGoingMin) {
		this.earlyGoingMin = earlyGoingMin;
	}

	public int getMaxLateTimeAllowed() {
		return maxLateTimeAllowed;
	}

	public void setMaxLateTimeAllowed(int maxLateTimeAllowed) {
		this.maxLateTimeAllowed = maxLateTimeAllowed;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MstWeeklyOff [empTypeId=" + empTypeId + ", name=" + name + ", category=" + category + ", attType="
				+ attType + ", lmApplicable=" + lmApplicable + ", halfDay=" + halfDay + ", whWork=" + whWork
				+ ", minWorkHr=" + minWorkHr + ", minworkApplicable=" + minworkApplicable + ", otApplicable="
				+ otApplicable + ", otTime=" + otTime + ", details=" + details + ", otType=" + otType + ", companyId="
				+ companyId + ", weeklyHolidayLateAllowed=" + weeklyHolidayLateAllowed
				+ ", weeklyHolidayLateAllowedMin=" + weeklyHolidayLateAllowedMin + ", earlyGoingAllowed="
				+ earlyGoingAllowed + ", earlyGoingMin=" + earlyGoingMin + ", maxLateTimeAllowed=" + maxLateTimeAllowed
				+ ", status=" + status + "]";
	}

}
