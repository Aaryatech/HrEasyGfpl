package com.ats.hreasy.model;


public class RewardDetail {

	private String id;
	private String typeName;
	private int qty;
	private String salDbCr;
	private float amt;
	private String exVar1;
	private int exInt1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
		return "RewardDetail [id=" + id + ", typeName=" + typeName + ", qty=" + qty + ", salDbCr=" + salDbCr + ", amt="
				+ amt + ", exVar1=" + exVar1 + ", exInt1=" + exInt1 + "]";
	}
	
	
}
