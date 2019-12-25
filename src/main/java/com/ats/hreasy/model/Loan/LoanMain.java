package com.ats.hreasy.model.Loan;

 
 
public class LoanMain {
	 
	private int id;

	private int cmpId;

	private int empId;

	private String loanApplNo;

	private int loanAmt;

	private double loanRoi;

	private int loanTenure;

	private String loanRepayStart;

	private String loanRepayEnd;

	private int loanRepayAmt;

	private int loanEmi;

	private int loanEmiIntrest;

	private int currentTotpaid;

	private int currentOutstanding;

	private String loanStatus;

	private String loginName;

	private String loginTime;

	private String allData;

	private String remark;

	private int skipId;
	
	private String loanAddDate;
	
	private String skipLoginName;
	
	private String skipLoginTime;
	
	private String skipRemarks;

	private int delStatus;
	
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;


	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
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

	public String getLoanApplNo() {
		return loanApplNo;
	}

	public void setLoanApplNo(String loanApplNo) {
		this.loanApplNo = loanApplNo;
	}

	public int getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(int loanAmt) {
		this.loanAmt = loanAmt;
	}

	public double getLoanRoi() {
		return loanRoi;
	}

	public void setLoanRoi(double loanRoi) {
		this.loanRoi = loanRoi;
	}

	public int getLoanTenure() {
		return loanTenure;
	}

	public void setLoanTenure(int loanTenure) {
		this.loanTenure = loanTenure;
	}

	public String getLoanRepayStart() {
		return loanRepayStart;
	}

	public void setLoanRepayStart(String loanRepayStart) {
		this.loanRepayStart = loanRepayStart;
	}

	public String getLoanRepayEnd() {
		return loanRepayEnd;
	}

	public void setLoanRepayEnd(String loanRepayEnd) {
		this.loanRepayEnd = loanRepayEnd;
	}

	public int getLoanRepayAmt() {
		return loanRepayAmt;
	}

	public void setLoanRepayAmt(int loanRepayAmt) {
		this.loanRepayAmt = loanRepayAmt;
	}

	public int getLoanEmi() {
		return loanEmi;
	}

	public void setLoanEmi(int loanEmi) {
		this.loanEmi = loanEmi;
	}

	public int getLoanEmiIntrest() {
		return loanEmiIntrest;
	}

	public void setLoanEmiIntrest(int loanEmiIntrest) {
		this.loanEmiIntrest = loanEmiIntrest;
	}

	public int getCurrentTotpaid() {
		return currentTotpaid;
	}

	public void setCurrentTotpaid(int currentTotpaid) {
		this.currentTotpaid = currentTotpaid;
	}

	public int getCurrentOutstanding() {
		return currentOutstanding;
	}

	public void setCurrentOutstanding(int currentOutstanding) {
		this.currentOutstanding = currentOutstanding;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
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

	public String getAllData() {
		return allData;
	}

	public void setAllData(String allData) {
		this.allData = allData;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSkipId() {
		return skipId;
	}

	public void setSkipId(int skipId) {
		this.skipId = skipId;
	}

	public String getLoanAddDate() {
		return loanAddDate;
	}

	public void setLoanAddDate(String loanAddDate) {
		this.loanAddDate = loanAddDate;
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
		return "LoanMain [id=" + id + ", cmpId=" + cmpId + ", empId=" + empId + ", loanApplNo=" + loanApplNo
				+ ", loanAmt=" + loanAmt + ", loanRoi=" + loanRoi + ", loanTenure=" + loanTenure + ", loanRepayStart="
				+ loanRepayStart + ", loanRepayEnd=" + loanRepayEnd + ", loanRepayAmt=" + loanRepayAmt + ", loanEmi="
				+ loanEmi + ", loanEmiIntrest=" + loanEmiIntrest + ", currentTotpaid=" + currentTotpaid
				+ ", currentOutstanding=" + currentOutstanding + ", loanStatus=" + loanStatus + ", loginName="
				+ loginName + ", loginTime=" + loginTime + ", allData=" + allData + ", remark=" + remark + ", skipId="
				+ skipId + ", loanAddDate=" + loanAddDate + ", skipLoginName=" + skipLoginName + ", skipLoginTime="
				+ skipLoginTime + ", skipRemarks=" + skipRemarks + ", delStatus=" + delStatus + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}

	 
}
