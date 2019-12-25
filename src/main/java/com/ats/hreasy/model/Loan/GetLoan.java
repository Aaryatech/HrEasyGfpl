package com.ats.hreasy.model.Loan;

public class GetLoan {

	private int empId;

	private int loanAmt;

	private int loanRepayAmt;

	private int loanEmi;

 	private String empCode;

	private String firstName;

	private String middleName;

	private String surname;

	private String designation;
	
	private String exVar1;
	private String exVar2;
	private String exVar3;
	
	private int currentOutstanding;
	
	
	

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

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(int loanAmt) {
		this.loanAmt = loanAmt;
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

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getCurrentOutstanding() {
		return currentOutstanding;
	}

	public void setCurrentOutstanding(int currentOutstanding) {
		this.currentOutstanding = currentOutstanding;
	}

	@Override
	public String toString() {
		return "GetLoan [empId=" + empId + ", loanAmt=" + loanAmt + ", loanRepayAmt=" + loanRepayAmt + ", loanEmi="
				+ loanEmi + ", empCode=" + empCode + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", surname=" + surname + ", designation=" + designation + ", currentOutstanding=" + currentOutstanding
				+ "]";
	}
	
	
	 

}
