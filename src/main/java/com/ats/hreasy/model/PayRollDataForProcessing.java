package com.ats.hreasy.model;

import java.util.List;
 
public class PayRollDataForProcessing {
	
	private List<EmpSalaryInfoForPayroll> list ;
	private List<Allowances> allowancelist ;
	private List<GetPayrollGeneratedList> payrollGeneratedList ;
	
	public List<EmpSalaryInfoForPayroll> getList() {
		return list;
	}
	public void setList(List<EmpSalaryInfoForPayroll> list) {
		this.list = list;
	}
	public List<Allowances> getAllowancelist() {
		return allowancelist;
	}
	public void setAllowancelist(List<Allowances> allowancelist) {
		this.allowancelist = allowancelist;
	}
	public List<GetPayrollGeneratedList> getPayrollGeneratedList() {
		return payrollGeneratedList;
	}
	public void setPayrollGeneratedList(List<GetPayrollGeneratedList> payrollGeneratedList) {
		this.payrollGeneratedList = payrollGeneratedList;
	}
	@Override
	public String toString() {
		return "PayRollDataForProcessing [list=" + list + ", allowancelist=" + allowancelist + ", payrollGeneratedList="
				+ payrollGeneratedList + "]";
	}
	
	

}
