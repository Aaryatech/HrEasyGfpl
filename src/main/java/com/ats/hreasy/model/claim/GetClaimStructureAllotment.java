package com.ats.hreasy.model.claim;
 

 public class GetClaimStructureAllotment {
	 
	 
 
  	private int empId;

	private String empCode;

	private String empFname;

	private String empMname;

	private String empSname;

	private String empDeptName;

	private String empCatName;

	private String clmsName;

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

	public String getClmsName() {
		return clmsName;
	}

	public void setClmsName(String clmsName) {
		this.clmsName = clmsName;
	}

	@Override
	public String toString() {
		return "GetClaimStructureAllotment [empId=" + empId + ", empCode=" + empCode + ", empFname=" + empFname
				+ ", empMname=" + empMname + ", empSname=" + empSname + ", empDeptName=" + empDeptName + ", empCatName="
				+ empCatName + ", clmsName=" + clmsName + "]";
	}
	
	
	
	
}
