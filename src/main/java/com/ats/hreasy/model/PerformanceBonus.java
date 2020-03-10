package com.ats.hreasy.model;

public class PerformanceBonus {
	private int id;
	private String attDate;
	private int otHr;
	private int exInt1;
	private String exVar1;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAttDate() {
		return attDate;
	}
	public void setAttDate(String attDate) {
		this.attDate = attDate;
	}
	public int getOtHr() {
		return otHr;
	}
	public void setOtHr(int otHr) {
		this.otHr = otHr;
	}
	public int getExInt1() {
		return exInt1;
	}
	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}
	public String getExVar1() {
		return exVar1;
	}
	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}
	@Override
	public String toString() {
		return "PerformanceBonus [id=" + id + ", attDate=" + attDate + ", otHr=" + otHr + ", exInt1=" + exInt1
				+ ", exVar1=" + exVar1 + "]";
	}
	
}
