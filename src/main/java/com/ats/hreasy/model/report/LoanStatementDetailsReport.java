package com.ats.hreasy.model.report;

public class LoanStatementDetailsReport {
	
	private int id;
	private int  empId;
	private String empCode;
	private String empName;
	private String loanApplNo;
	private float loanAmt;
	private String loanAddDate;
	private float currentOutstanding;
	private float currentTotpaid;
	private float loanEmiIntrest;
	private float loanEmi;
	private String loanRepayStart;
	private String loanRepayEnd;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getLoanApplNo() {
		return loanApplNo;
	}
	public void setLoanApplNo(String loanApplNo) {
		this.loanApplNo = loanApplNo;
	}
	public float getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(float loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getLoanAddDate() {
		return loanAddDate;
	}
	public void setLoanAddDate(String loanAddDate) {
		this.loanAddDate = loanAddDate;
	}
	public float getCurrentOutstanding() {
		return currentOutstanding;
	}
	public void setCurrentOutstanding(float currentOutstanding) {
		this.currentOutstanding = currentOutstanding;
	}
	public float getCurrentTotpaid() {
		return currentTotpaid;
	}
	public void setCurrentTotpaid(float currentTotpaid) {
		this.currentTotpaid = currentTotpaid;
	}
	public float getLoanEmiIntrest() {
		return loanEmiIntrest;
	}
	public void setLoanEmiIntrest(float loanEmiIntrest) {
		this.loanEmiIntrest = loanEmiIntrest;
	}
	public float getLoanEmi() {
		return loanEmi;
	}
	public void setLoanEmi(float loanEmi) {
		this.loanEmi = loanEmi;
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
	@Override
	public String toString() {
		return "LoanStatementDetailsReport [id=" + id + ", empId=" + empId + ", empCode=" + empCode + ", empName="
				+ empName + ", loanApplNo=" + loanApplNo + ", loanAmt=" + loanAmt + ", loanAddDate=" + loanAddDate
				+ ", currentOutstanding=" + currentOutstanding + ", currentTotpaid=" + currentTotpaid
				+ ", loanEmiIntrest=" + loanEmiIntrest + ", loanEmi=" + loanEmi + ", loanRepayStart=" + loanRepayStart
				+ ", loanRepayEnd=" + loanRepayEnd + "]";
	}
	
}


