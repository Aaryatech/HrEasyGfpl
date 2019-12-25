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
	private int user_id;
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
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "EmployeeRelatedTbls [empId=" + empId + ", empCode=" + empCode + ", empInfoId=" + empInfoId
				+ ", nomineeId=" + nomineeId + ", bankInfoId=" + bankInfoId + ", salaryInfoId=" + salaryInfoId
				+ ", empSalAllowanceId=" + empSalAllowanceId + ", docId=" + docId + ", user_id=" + user_id + "]";
	}
	
	
	
}
