package com.ats.hreasy.model.Bonus;

 
public class PayBonus {

	 
	private int payTypeId;
	private String typeName;
	private double payRate;
	private int isUsed;
	private int delStatus;
	private String enterMakerDatetime;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	
 	private boolean error;
	
	
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public int getPayTypeId() {
		return payTypeId;
	}
	public void setPayTypeId(int payTypeId) {
		this.payTypeId = payTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public double getPayRate() {
		return payRate;
	}
	public void setPayRate(double payRate) {
		this.payRate = payRate;
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
		return "PayBonus [payTypeId=" + payTypeId + ", typeName=" + typeName + ", payRate=" + payRate + ", isUsed="
				+ isUsed + ", delStatus=" + delStatus + ", enterMakerDatetime=" + enterMakerDatetime + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}
	
	
	
}
