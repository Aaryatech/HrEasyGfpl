package com.ats.hreasy.model;

public class TblEmpBankInfo {

	private int bankInfoId;
	private int empId;
	private String accNo;
	private int bankId;

	public int getBankInfoId() {
		return bankInfoId;
	}

	public void setBankInfoId(int bankInfoId) {
		this.bankInfoId = bankInfoId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	@Override
	public String toString() {
		return "TblEmpBankInfo [bankInfoId=" + bankInfoId + ", empId=" + empId + ", accNo=" + accNo + ", bankId="
				+ bankId + "]";
	}
	
	
}
