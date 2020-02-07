package com.ats.hreasy.model.graph;

public class EmpLoanGraph {

	 
	private double loanAmt;

	private int month;

	private int year;
	
	private String date;

	public double getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
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
		return "EmpLoanGraph [loanAmt=" + loanAmt + ", month=" + month + ", year=" + year + ", date=" + date + "]";
	}
	
	
	
}
