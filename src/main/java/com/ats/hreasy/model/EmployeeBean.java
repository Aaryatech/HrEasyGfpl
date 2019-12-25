package com.ats.hreasy.model;

public class EmployeeBean {

	EmployeeMaster emp;
	
	TblEmpInfo empInfo;
	
	TblEmpNominees empNominee;
	
	TblEmpBankInfo empBank;
	
	EmpSalaryInfo empSal;
	
	EmpSalAllowance empSalAllownc;	
	
	EmployeDoc empDoc;

	public EmployeeMaster getEmp() {
		return emp;
	}

	public void setEmp(EmployeeMaster emp) {
		this.emp = emp;
	}

	public TblEmpInfo getEmpInfo() {
		return empInfo;
	}

	public void setEmpInfo(TblEmpInfo empInfo) {
		this.empInfo = empInfo;
	}

	public TblEmpNominees getEmpNominee() {
		return empNominee;
	}

	public void setEmpNominee(TblEmpNominees empNominee) {
		this.empNominee = empNominee;
	}

	public TblEmpBankInfo getEmpBank() {
		return empBank;
	}

	public void setEmpBank(TblEmpBankInfo empBank) {
		this.empBank = empBank;
	}

	public EmpSalaryInfo getEmpSal() {
		return empSal;
	}

	public void setEmpSal(EmpSalaryInfo empSal) {
		this.empSal = empSal;
	}

	public EmpSalAllowance getEmpSalAllownc() {
		return empSalAllownc;
	}

	public void setEmpSalAllownc(EmpSalAllowance empSalAllownc) {
		this.empSalAllownc = empSalAllownc;
	}

	public EmployeDoc getEmpDoc() {
		return empDoc;
	}

	public void setEmpDoc(EmployeDoc empDoc) {
		this.empDoc = empDoc;
	}

	@Override
	public String toString() {
		return "EmployeeBean [emp=" + emp + ", empInfo=" + empInfo + ", empNominee=" + empNominee + ", empBank="
				+ empBank + ", empSal=" + empSal + ", empSalAllownc=" + empSalAllownc + ", empDoc=" + empDoc + "]";
	}
	
	
}
