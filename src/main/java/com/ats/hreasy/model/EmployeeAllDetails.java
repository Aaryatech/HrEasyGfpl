package com.ats.hreasy.model;

import java.util.List;

public class EmployeeAllDetails {

	ViewEmployee empDtl;
	List<EmpSalAllowance> empAllowncDtl;
	
	public ViewEmployee getEmpDtl() {
		return empDtl;
	}
	public void setEmpDtl(ViewEmployee empDtl) {
		this.empDtl = empDtl;
	}
	public List<EmpSalAllowance> getEmpAllowncDtl() {
		return empAllowncDtl;
	}
	public void setEmpAllowncDtl(List<EmpSalAllowance> empAllowncDtl) {
		this.empAllowncDtl = empAllowncDtl;
	}
	@Override
	public String toString() {
		return "EmployeeAllDetails [empDtl=" + empDtl + ", empAllowncDtl=" + empAllowncDtl + "]";
	}

}
