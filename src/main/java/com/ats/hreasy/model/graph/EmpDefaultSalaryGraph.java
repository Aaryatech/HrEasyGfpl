package com.ats.hreasy.model.graph;

public class EmpDefaultSalaryGraph {

	private String id;
	private String date;
	private double defaultSalAmt;

	private int month;

	private int year;

	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getDefaultSalAmt() {
		return defaultSalAmt;
	}

	public void setDefaultSalAmt(double defaultSalAmt) {
		this.defaultSalAmt = defaultSalAmt;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "EmpDefaultSalaryGraph [id=" + id + ", date=" + date + ", defaultSalAmt=" + defaultSalAmt + ", month="
				+ month + ", year=" + year + "]";
	}

	
}
