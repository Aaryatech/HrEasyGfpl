package com.ats.hreasy.model;

import java.util.List;
 
public class VariousList {
	
	private List<ShiftMaster> shiftList;
	private List<MstWeeklyOff> mstWeeklyOffList;
	private List<Holiday> holidayList;
	private List<LvType> lvTypeList;
	private List<LvmSumUp> LvmSumUpList;
	private List<DailyAttendance> dailyAttendanceList;
	private List<SummaryDailyAttendance> summaryDailyAttendanceList;
	
	public List<ShiftMaster> getShiftList() {
		return shiftList;
	}
	public void setShiftList(List<ShiftMaster> shiftList) {
		this.shiftList = shiftList;
	}
	public List<MstWeeklyOff> getMstWeeklyOffList() {
		return mstWeeklyOffList;
	}
	public void setMstWeeklyOffList(List<MstWeeklyOff> mstWeeklyOffList) {
		this.mstWeeklyOffList = mstWeeklyOffList;
	}
	public List<Holiday> getHolidayList() {
		return holidayList;
	}
	public void setHolidayList(List<Holiday> holidayList) {
		this.holidayList = holidayList;
	}
	public List<LvType> getLvTypeList() {
		return lvTypeList;
	}
	public void setLvTypeList(List<LvType> lvTypeList) {
		this.lvTypeList = lvTypeList;
	}
	public List<LvmSumUp> getLvmSumUpList() {
		return LvmSumUpList;
	}
	public void setLvmSumUpList(List<LvmSumUp> lvmSumUpList) {
		LvmSumUpList = lvmSumUpList;
	}
	public List<DailyAttendance> getDailyAttendanceList() {
		return dailyAttendanceList;
	}
	public void setDailyAttendanceList(List<DailyAttendance> dailyAttendanceList) {
		this.dailyAttendanceList = dailyAttendanceList;
	}
	public List<SummaryDailyAttendance> getSummaryDailyAttendanceList() {
		return summaryDailyAttendanceList;
	}
	public void setSummaryDailyAttendanceList(List<SummaryDailyAttendance> summaryDailyAttendanceList) {
		this.summaryDailyAttendanceList = summaryDailyAttendanceList;
	}
	@Override
	public String toString() {
		return "VariousList [shiftList=" + shiftList + ", mstWeeklyOffList=" + mstWeeklyOffList + ", holidayList="
				+ holidayList + ", lvTypeList=" + lvTypeList + ", LvmSumUpList=" + LvmSumUpList
				+ ", dailyAttendanceList=" + dailyAttendanceList + ", summaryDailyAttendanceList="
				+ summaryDailyAttendanceList + "]";
	}
	
	

}
