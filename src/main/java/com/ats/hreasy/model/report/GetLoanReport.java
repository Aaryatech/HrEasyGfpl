package com.ats.hreasy.model.report;

 
public class GetLoanReport {
	 
	private int id; 
	
 	private int empId;
 
 	private String empCode;

	private String empName;
 
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
 
	private String remark;

	private int skipId;
	
 	
	private String skipLoginName;
	
	private String skipLoginTime;
	
	private String skipRemarks;
	
	
	private String  skipUserName;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
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

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getSkipUserName() {
		return skipUserName;
	}

	public void setSkipUserName(String skipUserName) {
		this.skipUserName = skipUserName;
	}

	@Override
	public String toString() {
		return "GetLoanReport [empId=" + empId + ", empCode=" + empCode + ", empName=" + empName + ", loanApplNo="
				+ loanApplNo + ", loanAmt=" + loanAmt + ", loanRoi=" + loanRoi + ", loanTenure=" + loanTenure
				+ ", loanRepayStart=" + loanRepayStart + ", loanRepayEnd=" + loanRepayEnd + ", loanRepayAmt="
				+ loanRepayAmt + ", loanEmi=" + loanEmi + ", loanEmiIntrest=" + loanEmiIntrest + ", currentTotpaid="
				+ currentTotpaid + ", currentOutstanding=" + currentOutstanding + ", loanStatus=" + loanStatus
				+ ", remark=" + remark + ", skipId=" + skipId + ", skipLoginName=" + skipLoginName + ", skipLoginTime="
				+ skipLoginTime + ", skipRemarks=" + skipRemarks + ", skipUserName=" + skipUserName + "]";
	}
 
	 

}
