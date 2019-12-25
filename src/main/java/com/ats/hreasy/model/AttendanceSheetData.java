package com.ats.hreasy.model;

import java.util.List;
 

public class AttendanceSheetData {
	
	private List<String> dates;
	private List<EmpInfoWithDateInfoList> infomationList;
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	public List<EmpInfoWithDateInfoList> getInfomationList() {
		return infomationList;
	}
	public void setInfomationList(List<EmpInfoWithDateInfoList> infomationList) {
		this.infomationList = infomationList;
	}
	@Override
	public String toString() {
		return "AttendanceSheetData [dates=" + dates + ", infomationList=" + infomationList + "]";
	}
	
	

}
