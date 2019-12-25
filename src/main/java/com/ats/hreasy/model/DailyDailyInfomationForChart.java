package com.ats.hreasy.model;

public class DailyDailyInfomationForChart {
	
	private String date;
	private String inTime;
	private String outTime;
	private String lateMin;
	private String workingMin;
	private String otMin;
	private String status;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getLateMin() {
		return lateMin;
	}
	public void setLateMin(String lateMin) {
		this.lateMin = lateMin;
	}
	public String getWorkingMin() {
		return workingMin;
	}
	public void setWorkingMin(String workingMin) {
		this.workingMin = workingMin;
	}
	public String getOtMin() {
		return otMin;
	}
	public void setOtMin(String otMin) {
		this.otMin = otMin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "DailyDailyInfomationForChart [date=" + date + ", inTime=" + inTime + ", outTime=" + outTime
				+ ", lateMin=" + lateMin + ", workingMin=" + workingMin + ", otMin=" + otMin + ", status=" + status
				+ "]";
	}
	
	

}
