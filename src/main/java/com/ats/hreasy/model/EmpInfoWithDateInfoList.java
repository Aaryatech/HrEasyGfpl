package com.ats.hreasy.model;

import java.util.List;

public class EmpInfoWithDateInfoList {
	
	private String empCode;
	private List<DailyDailyInfomationForChart> sttsList;
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public List<DailyDailyInfomationForChart> getSttsList() {
		return sttsList;
	}
	public void setSttsList(List<DailyDailyInfomationForChart> sttsList) {
		this.sttsList = sttsList;
	}
	@Override
	public String toString() {
		return "EmpInfoWithDateInfoList [empCode=" + empCode + ", sttsList=" + sttsList + "]";
	}
	
	

}
