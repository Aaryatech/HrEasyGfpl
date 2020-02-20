package com.ats.hreasy.model.report;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

 
 public class LoanDedReport {

	 
	private String uniqueId;

	private int empId;

	private String empCode;

	private String empName;

	private int currentTotpaid;

	private int currentOutstanding;

	private int loanAmt;
	private int loanEmi;

	private String  loanRepayStart;

	private String loanRepayEnd;
	
	
	private int loanEmiIntrest ;


	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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

	public int getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(int loanAmt) {
		this.loanAmt = loanAmt;
	}

	public int getLoanEmi() {
		return loanEmi;
	}

	public void setLoanEmi(int loanEmi) {
		this.loanEmi = loanEmi;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
 	public String getLoanRepayStart() {
		return loanRepayStart;
	}

	public void setLoanRepayStart(String loanRepayStart) {
		this.loanRepayStart = loanRepayStart;
	}
	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")

	public String getLoanRepayEnd() {
		return loanRepayEnd;
	}

	public void setLoanRepayEnd(String loanRepayEnd) {
		this.loanRepayEnd = loanRepayEnd;
	}

	public int getLoanEmiIntrest() {
		return loanEmiIntrest;
	}

	public void setLoanEmiIntrest(int loanEmiIntrest) {
		this.loanEmiIntrest = loanEmiIntrest;
	}

	@Override
	public String toString() {
		return "LoanDedReport [uniqueId=" + uniqueId + ", empId=" + empId + ", empCode=" + empCode + ", empName="
				+ empName + ", currentTotpaid=" + currentTotpaid + ", currentOutstanding=" + currentOutstanding
				+ ", loanAmt=" + loanAmt + ", loanEmi=" + loanEmi + ", loanRepayStart=" + loanRepayStart
				+ ", loanRepayEnd=" + loanRepayEnd + ", loanEmiIntrest=" + loanEmiIntrest + "]";
	}

	 
	

}
