package com.ats.hreasy.model;

public class GetStructureAllotment {

	private int empId;

	private String empCode;

	private String empFname;

	private String empMname;

	private String empSname;

	private String empDeptName;

	private String empCatName;

	private String lvsName;

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

	public String getEmpFname() {
		return empFname;
	}

	public void setEmpFname(String empFname) {
		this.empFname = empFname;
	}

	public String getEmpMname() {
		return empMname;
	}

	public void setEmpMname(String empMname) {
		this.empMname = empMname;
	}

	public String getEmpSname() {
		return empSname;
	}

	public void setEmpSname(String empSname) {
		this.empSname = empSname;
	}

	public String getEmpDeptName() {
		return empDeptName;
	}

	public void setEmpDeptName(String empDeptName) {
		this.empDeptName = empDeptName;
	}

	public String getEmpCatName() {
		return empCatName;
	}

	public void setEmpCatName(String empCatName) {
		this.empCatName = empCatName;
	}

	public String getLvsName() {
		return lvsName;
	}

	public void setLvsName(String lvsName) {
		this.lvsName = lvsName;
	}

	@Override
	public String toString() {
		return "GetStructureAllotment [empId=" + empId + ", empCode=" + empCode + ", empFname=" + empFname
				+ ", empMname=" + empMname + ", empSname=" + empSname + ", empDeptName=" + empDeptName + ", empCatName="
				+ empCatName + ", lvsName=" + lvsName + "]";
	}

}
