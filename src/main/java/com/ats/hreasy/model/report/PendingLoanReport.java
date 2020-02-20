package com.ats.hreasy.model.report;

public class PendingLoanReport {
	
	private String id;
	private String empCode;
	private String firstName;
	private String middleName;
	private String surname;
	private String designation;
	private String depatarment;
	private float currentTotpaid;
	private float currentOutstanding;
	private float loanAmt;
	private float loanEmi;
	private String loanRepayStart;
	private String loanRepayEnd;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getDepatarment() {
		return depatarment;
	}
	public void setDepatarment(String depatarment) {
		this.depatarment = depatarment;
	}
	public float getCurrentTotpaid() {
		return currentTotpaid;
	}
	public void setCurrentTotpaid(float currentTotpaid) {
		this.currentTotpaid = currentTotpaid;
	}
	public float getCurrentOutstanding() {
		return currentOutstanding;
	}
	public void setCurrentOutstanding(float currentOutstanding) {
		this.currentOutstanding = currentOutstanding;
	}
	public float getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(float loanAmt) {
		this.loanAmt = loanAmt;
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
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	@Override
	public String toString() {
		return "PendingLoanReport [id=" + id + ", empCode=" + empCode + ", firstName=" + firstName + ", middleName="
				+ middleName + ", surname=" + surname + ", designation=" + designation + ", depatarment=" + depatarment
				+ ", currentTotpaid=" + currentTotpaid + ", currentOutstanding=" + currentOutstanding + ", loanAmt="
				+ loanAmt + ", loanEmi=" + loanEmi + ", loanRepayStart=" + loanRepayStart + ", loanRepayEnd="
				+ loanRepayEnd + "]";
	}
	
	
}
