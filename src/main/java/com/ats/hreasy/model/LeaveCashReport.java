package com.ats.hreasy.model;
 

public class LeaveCashReport {
	 
	private int empId; 
	private float leaveCount; 
	private float cash; 
	private String empCode;
	private String firstName; 
	private String surname;
	private String paidDate;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public float getLeaveCount() {
		return leaveCount;
	}
	public void setLeaveCount(float leaveCount) {
		this.leaveCount = leaveCount;
	}
	public float getCash() {
		return cash;
	}
	public void setCash(float cash) {
		this.cash = cash;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	@Override
	public String toString() {
		return "LeaveCashReport [empId=" + empId + ", leaveCount=" + leaveCount + ", cash=" + cash + ", empCode="
				+ empCode + ", firstName=" + firstName + ", surname=" + surname + ", paidDate=" + paidDate + "]";
	}
	
	
}
