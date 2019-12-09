package com.ats.hreasy.model;

import java.util.List;

public class LeaveHistTemp {

	private String empName;

	List<EmpLeaveHistoryRep> rec;

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public List<EmpLeaveHistoryRep> getRec() {
		return rec;
	}

	public void setRec(List<EmpLeaveHistoryRep> rec) {
		this.rec = rec;
	}

	@Override
	public String toString() {
		return "LeaveHistTemp [empName=" + empName + ", rec=" + rec + "]";
	}
	
	
	

}
