package com.ats.hreasy.model;

public class LeaveCount {
	
	private int leavecount;
	private int holidaycount;
	public int getLeavecount() {
		return leavecount;
	}
	public void setLeavecount(int leavecount) {
		this.leavecount = leavecount;
	}
	public int getHolidaycount() {
		return holidaycount;
	}
	public void setHolidaycount(int holidaycount) {
		this.holidaycount = holidaycount;
	}
	@Override
	public String toString() {
		return "LeaveCount [leavecount=" + leavecount + ", holidaycount=" + holidaycount + "]";
	}
	
	

}
