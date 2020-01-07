package com.ats.hreasy.model.Advance;

 
 
public class Advance {
	

private int id;
	
	private int cmpId; 
	
	private int empId ;
 	
	private String voucherNo ;
 	
	private String advDate;
 	
	private double advAmount ;
 	
	private double advRemainingAmount;
 	
	private String advRemarks;
 	
	private int dedMonth ;
 	
 	private int isDed ;
 	
 	private int isUsed;
 	
 	private String loginName ;
 	
 	private String loginTime;
 	
 	private int skipId ;
 	
 	private String skipLoginName ;
 	
 	private String skipLoginTime ;
 	
	private int dedYear ;
 	
 	private String skipRemarks ;
 	
	private int exInt1;
	
	private int exInt2;
	
	
	private String exVar1; 
	
	private String exVar2; 
	
	private int delStatus; 
	
	
	

	public int getDelStatus() {
		return delStatus;
	}


	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getCmpId() {
		return cmpId;
	}


	public void setCmpId(int cmpId) {
		this.cmpId = cmpId;
	}


	public int getEmpId() {
		return empId;
	}


	public void setEmpId(int empId) {
		this.empId = empId;
	}


	public String getVoucherNo() {
		return voucherNo;
	}


	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}


	public String getAdvDate() {
		return advDate;
	}


	public void setAdvDate(String advDate) {
		this.advDate = advDate;
	}


	public double getAdvAmount() {
		return advAmount;
	}


	public void setAdvAmount(double advAmount) {
		this.advAmount = advAmount;
	}


	public double getAdvRemainingAmount() {
		return advRemainingAmount;
	}


	public void setAdvRemainingAmount(double advRemainingAmount) {
		this.advRemainingAmount = advRemainingAmount;
	}


	public String getAdvRemarks() {
		return advRemarks;
	}


	public void setAdvRemarks(String advRemarks) {
		this.advRemarks = advRemarks;
	}

 


	public int getIsDed() {
		return isDed;
	}


	public void setIsDed(int isDed) {
		this.isDed = isDed;
	}


	public int getIsUsed() {
		return isUsed;
	}


	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getLoginTime() {
		return loginTime;
	}


	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}


	public int getSkipId() {
		return skipId;
	}


	public void setSkipId(int skipId) {
		this.skipId = skipId;
	}


	public String getSkipLoginName() {
		return skipLoginName;
	}


	public void setSkipLoginName(String skipLoginName) {
		this.skipLoginName = skipLoginName;
	}


	public String getSkipLoginTime() {
		return skipLoginTime;
	}


	public void setSkipLoginTime(String skipLoginTime) {
		this.skipLoginTime = skipLoginTime;
	}


	public String getSkipRemarks() {
		return skipRemarks;
	}


	public void setSkipRemarks(String skipRemarks) {
		this.skipRemarks = skipRemarks;
	}


	public int getDedMonth() {
		return dedMonth;
	}


	public void setDedMonth(int dedMonth) {
		this.dedMonth = dedMonth;
	}


	public int getDedYear() {
		return dedYear;
	}


	public void setDedYear(int dedYear) {
		this.dedYear = dedYear;
	}


	@Override
	public String toString() {
		return "Advance [id=" + id + ", cmpId=" + cmpId + ", empId=" + empId + ", voucherNo=" + voucherNo + ", advDate="
				+ advDate + ", advAmount=" + advAmount + ", advRemainingAmount=" + advRemainingAmount + ", advRemarks="
				+ advRemarks + ", dedMonth=" + dedMonth + ", isDed=" + isDed + ", isUsed=" + isUsed + ", loginName="
				+ loginName + ", loginTime=" + loginTime + ", skipId=" + skipId + ", skipLoginName=" + skipLoginName
				+ ", skipLoginTime=" + skipLoginTime + ", dedYear=" + dedYear + ", skipRemarks=" + skipRemarks
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", delStatus=" + delStatus + "]";
	}

	

}
