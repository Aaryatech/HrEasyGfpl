package com.ats.hreasy.model.graph;

public class EmpDailyAttendanceGraph {

	private double workingDays;
	private double presentdays;
	private double paidHoliday;
	private double unpaidHoliday;
	private double paidLeave;
	private double unpaidLeave;
	private double monthDays;
	private double payableDaysDays;
	

	private int month;

	private int year;
	
	private String date;
	
	private int  lateMarks;
	
	 
	public double getPayableDaysDays() {
		return payableDaysDays;
	}

	public void setPayableDaysDays(double payableDaysDays) {
		this.payableDaysDays = payableDaysDays;
	}

	public int getLateMarks() {
		return lateMarks;
	}

	public void setLateMarks(int lateMarks) {
		this.lateMarks = lateMarks;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(double workingDays) {
		this.workingDays = workingDays;
	}

	public double getPresentdays() {
		return presentdays;
	}

	public void setPresentdays(double presentdays) {
		this.presentdays = presentdays;
	}

	public double getPaidHoliday() {
		return paidHoliday;
	}

	public void setPaidHoliday(double paidHoliday) {
		this.paidHoliday = paidHoliday;
	}

	public double getUnpaidHoliday() {
		return unpaidHoliday;
	}

	public void setUnpaidHoliday(double unpaidHoliday) {
		this.unpaidHoliday = unpaidHoliday;
	}

	public double getPaidLeave() {
		return paidLeave;
	}

	public void setPaidLeave(double paidLeave) {
		this.paidLeave = paidLeave;
	}

	public double getUnpaidLeave() {
		return unpaidLeave;
	}

	public void setUnpaidLeave(double unpaidLeave) {
		this.unpaidLeave = unpaidLeave;
	}

	public double getMonthDays() {
		return monthDays;
	}

	public void setMonthDays(double monthDays) {
		this.monthDays = monthDays;
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

	@Override
	public String toString() {
		return "EmpDailyAttendanceGraph [workingDays=" + workingDays + ", presentdays=" + presentdays + ", paidHoliday="
				+ paidHoliday + ", unpaidHoliday=" + unpaidHoliday + ", paidLeave=" + paidLeave + ", unpaidLeave="
				+ unpaidLeave + ", monthDays=" + monthDays + ", payableDaysDays=" + payableDaysDays + ", month=" + month
				+ ", year=" + year + ", date=" + date + ", lateMarks=" + lateMarks + "]";
	}

	 
	 
}
