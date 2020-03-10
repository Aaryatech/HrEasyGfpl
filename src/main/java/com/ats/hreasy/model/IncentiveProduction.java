package com.ats.hreasy.model;

public class IncentiveProduction {
	
	private int id;
	private String attDate;
	private int qty;
	private String salDbCr;
	private float amt;
	private String exVar1;
	private int exInt1;
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
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getSalDbCr() {
		return salDbCr;
	}
	public void setSalDbCr(String salDbCr) {
		this.salDbCr = salDbCr;
	}
	public float getAmt() {
		return amt;
	}
	public void setAmt(float amt) {
		this.amt = amt;
	}
	public String getExVar1() {
		return exVar1;
	}
	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}
	public int getExInt1() {
		return exInt1;
	}
	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}
	@Override
	public String toString() {
		return "IncentiveProduction [id=" + id + ", attDate=" + attDate + ", qty=" + qty + ", salDbCr=" + salDbCr
				+ ", amt=" + amt + ", exVar1=" + exVar1 + ", exInt1=" + exInt1 + "]";
	}
	
	
}
