package com.ats.hreasy.model;
 

public class HolidayMaster {
	 
	private int holidayId; 
	private String holidayDate;
	private String holidayName; 
	private int delStatus;
	public int getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(int holidayId) {
		this.holidayId = holidayId;
	}
	 
	public String getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	@Override
	public String toString() {
		return "HolidayMaster [holidayId=" + holidayId + ", holidayDate=" + holidayDate + ", holidayName=" + holidayName
				+ ", delStatus=" + delStatus + "]";
	}
	
	

}
