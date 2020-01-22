package com.ats.hreasy.model.graph;

public class EmpAdvanceGraph {

	 
	private double advanceAmt;

	private int month;

	private int year;
	
	private String date;

	public double getAdvanceAmt() {
		return advanceAmt;
	}

	public void setAdvanceAmt(double advanceAmt) {
		this.advanceAmt = advanceAmt;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "EmpAdvanceGraph [advanceAmt=" + advanceAmt + ", month=" + month + ", year=" + year + ", date=" + date
				+ "]";
	}
	
	
	
	
}
