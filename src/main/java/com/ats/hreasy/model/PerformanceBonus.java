package com.ats.hreasy.model;

public class PerformanceBonus {
	private int id;
	private String attDate;
	private int otHr;
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
	@Override
	public String toString() {
		return "PerformanceBonus [id=" + id + ", attDate=" + attDate + ", otHr=" + otHr + "]";
	}
	
	
}
