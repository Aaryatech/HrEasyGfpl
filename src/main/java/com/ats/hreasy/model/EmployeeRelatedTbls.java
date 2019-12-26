package com.ats.hreasy.model;

import java.util.List;

public class EmployeeRelatedTbls {

	private int empId;
	private String empCode;
	private int empInfoId;
	private int nomineeId;
	private int bankInfoId;
	private int salaryInfoId;
	private String empSalAllowanceId;
	private String docId;
	private int userId;
	private String allowanceId;
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
	public int getEmpInfoId() {
		return empInfoId;
	}
	public void setEmpInfoId(int empInfoId) {
		this.empInfoId = empInfoId;
	}
	public int getNomineeId() {
		return nomineeId;
	}
	public void setNomineeId(int nomineeId) {
		this.nomineeId = nomineeId;
	}
	public int getBankInfoId() {
		return bankInfoId;
	}
	public void setBankInfoId(int bankInfoId) {
		this.bankInfoId = bankInfoId;
	}
	public int getSalaryInfoId() {
		return salaryInfoId;
	}
	public void setSalaryInfoId(int salaryInfoId) {
		this.salaryInfoId = salaryInfoId;
	}
	
	public String getEmpSalAllowanceId() {
		return empSalAllowanceId;
	}
	public void setEmpSalAllowanceId(String empSalAllowanceId) {
		this.empSalAllowanceId = empSalAllowanceId;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAllowanceId() {
		return allowanceId;
	}
	public void setAllowanceId(String allowanceId) {
		this.allowanceId = allowanceId;
	}
	@Override
	public String toString() {
		return "EmployeeRelatedTbls [empId=" + empId + ", empCode=" + empCode + ", empInfoId=" + empInfoId
				+ ", nomineeId=" + nomineeId + ", bankInfoId=" + bankInfoId + ", salaryInfoId=" + salaryInfoId
				+ ", empSalAllowanceId=" + empSalAllowanceId + ", docId=" + docId + ", userId=" + userId
				+ ", allowanceId=" + allowanceId + "]";
	}
	
		
}
