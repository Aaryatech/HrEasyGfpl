package com.ats.hreasy.model;

public class PayDeduction {
	
	private int dedTypeId;
	private String typeName;
	private double dedRate;
	private int isUsed;
	private int delStatus;
	private String enterMakerDatetime;
	private int exInt1;
	private int exInt2;
	private String exVar1;	
	private String exVar2;

	public int getDedTypeId() {
		return dedTypeId;
	}

	public void setDedTypeId(int dedTypeId) {
		this.dedTypeId = dedTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public double getDedRate() {
		return dedRate;
	}

	public void setDedRate(double dedRate) {
		this.dedRate = dedRate;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getEnterMakerDatetime() {
		return enterMakerDatetime;
	}

	public void setEnterMakerDatetime(String enterMakerDatetime) {
		this.enterMakerDatetime = enterMakerDatetime;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	@Override
	public String toString() {
		return "PayDeduction [dedTypeId=" + dedTypeId + ", typeName=" + typeName + ", dedRate=" + dedRate + ", isUsed="
				+ isUsed + ", delStatus=" + delStatus + ", enterMakerDatetime=" + enterMakerDatetime + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}

	
}
