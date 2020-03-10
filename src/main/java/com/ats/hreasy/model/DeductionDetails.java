package com.ats.hreasy.model;

public class DeductionDetails {

	private int dedId;
	private String typeName;
	private float dedRate;
	private String dedRemark;
	private String exVar1;
	private int exInt1;
	public int getDedId() {
		return dedId;
	}
	public void setDedId(int dedId) {
		this.dedId = dedId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public float getDedRate() {
		return dedRate;
	}
	public void setDedRate(float dedRate) {
		this.dedRate = dedRate;
	}
	public String getDedRemark() {
		return dedRemark;
	}
	public void setDedRemark(String dedRemark) {
		this.dedRemark = dedRemark;
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
		return "DeductionDetails [dedId=" + dedId + ", typeName=" + typeName + ", dedRate=" + dedRate + ", dedRemark="
				+ dedRemark + ", exVar1=" + exVar1 + ", exInt1=" + exInt1 + "]";
	}
	
	
}
