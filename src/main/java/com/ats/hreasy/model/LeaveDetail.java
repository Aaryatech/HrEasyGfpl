package com.ats.hreasy.model;

import java.util.List;

public class LeaveDetail {
	
	private int leaveId;
	
	private int calYrId;
	
	private int empId;
	
	private int lvTypeId;
	
	private String leaveDuration;
	
	private String leaveFromdt;
	
	private String leaveTodt ;
	
	private float leaveNumDays;
	
	private String leaveEmpReason;
	
	private int finalStatus;
	
	private String circulatedTo;
		
	private int delStatus;
	
	private int isActive;
	
	private int makerUserId ;
	
	private String makerEnterDatetime;
	
	private int exInt1;
	
	private int exInt2;
	
	private int exInt3;
	
	private String exVar1; 
	
	private String exVar2; 
	
	private String exVar3;
	
	private String empFname;
	
	private String empMname;
	
	private String empSname;
	
	private String empCode;
	
	private String empPhoto;
	
	private String empDeptName;
	
	private String lvTitle;
	
	private String userName;
	
	List<GetLeaveStatus>  GetLeaveStatusList;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public int getCalYrId() {
		return calYrId;
	}

	public void setCalYrId(int calYrId) {
		this.calYrId = calYrId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getLvTypeId() {
		return lvTypeId;
	}

	public void setLvTypeId(int lvTypeId) {
		this.lvTypeId = lvTypeId;
	}

	public String getLeaveDuration() {
		return leaveDuration;
	}

	public void setLeaveDuration(String leaveDuration) {
		this.leaveDuration = leaveDuration;
	}

	public String getLeaveFromdt() {
		return leaveFromdt;
	}

	public void setLeaveFromdt(String leaveFromdt) {
		this.leaveFromdt = leaveFromdt;
	}

	public String getLeaveTodt() {
		return leaveTodt;
	}

	public void setLeaveTodt(String leaveTodt) {
		this.leaveTodt = leaveTodt;
	}

	public float getLeaveNumDays() {
		return leaveNumDays;
	}

	public void setLeaveNumDays(float leaveNumDays) {
		this.leaveNumDays = leaveNumDays;
	}

	public String getLeaveEmpReason() {
		return leaveEmpReason;
	}

	public void setLeaveEmpReason(String leaveEmpReason) {
		this.leaveEmpReason = leaveEmpReason;
	}

	public int getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(int finalStatus) {
		this.finalStatus = finalStatus;
	}

	public String getCirculatedTo() {
		return circulatedTo;
	}

	public void setCirculatedTo(String circulatedTo) {
		this.circulatedTo = circulatedTo;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getMakerUserId() {
		return makerUserId;
	}

	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}

	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}

	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public int getExInt3() {
		return exInt3;
	}

	public void setExInt3(int exInt3) {
		this.exInt3 = exInt3;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
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

	
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpPhoto() {
		return empPhoto;
	}

	public void setEmpPhoto(String empPhoto) {
		this.empPhoto = empPhoto;
	}

	public String getEmpDeptName() {
		return empDeptName;
	}

	public void setEmpDeptName(String empDeptName) {
		this.empDeptName = empDeptName;
	}

	public String getLvTitle() {
		return lvTitle;
	}

	public void setLvTitle(String lvTitle) {
		this.lvTitle = lvTitle;
	}

	
	public List<GetLeaveStatus> getGetLeaveStatusList() {
		return GetLeaveStatusList;
	}

	public void setGetLeaveStatusList(List<GetLeaveStatus> getLeaveStatusList) {
		GetLeaveStatusList = getLeaveStatusList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "LeaveDetail [leaveId=" + leaveId + ", calYrId=" + calYrId + ", empId=" + empId + ", lvTypeId="
				+ lvTypeId + ", leaveDuration=" + leaveDuration + ", leaveFromdt=" + leaveFromdt + ", leaveTodt="
				+ leaveTodt + ", leaveNumDays=" + leaveNumDays + ", leaveEmpReason=" + leaveEmpReason + ", finalStatus="
				+ finalStatus + ", circulatedTo=" + circulatedTo + ", delStatus=" + delStatus + ", isActive=" + isActive
				+ ", makerUserId=" + makerUserId + ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exVar3=" + exVar3 + ", empFname=" + empFname + ", empMname=" + empMname + ", empSname=" + empSname
				+ ", empCode=" + empCode + ", empPhoto=" + empPhoto + ", empDeptName=" + empDeptName + ", lvTitle="
				+ lvTitle + ", userName=" + userName + ", GetLeaveStatusList=" + GetLeaveStatusList + ", getLeaveId()="
				+ getLeaveId() + ", getCalYrId()=" + getCalYrId() + ", getEmpId()=" + getEmpId() + ", getLvTypeId()="
				+ getLvTypeId() + ", getLeaveDuration()=" + getLeaveDuration() + ", getLeaveFromdt()="
				+ getLeaveFromdt() + ", getLeaveTodt()=" + getLeaveTodt() + ", getLeaveNumDays()=" + getLeaveNumDays()
				+ ", getLeaveEmpReason()=" + getLeaveEmpReason() + ", getFinalStatus()=" + getFinalStatus()
				+ ", getCirculatedTo()=" + getCirculatedTo() + ", getDelStatus()=" + getDelStatus() + ", getIsActive()="
				+ getIsActive() + ", getMakerUserId()=" + getMakerUserId() + ", getMakerEnterDatetime()="
				+ getMakerEnterDatetime() + ", getExInt1()=" + getExInt1() + ", getExInt2()=" + getExInt2()
				+ ", getExInt3()=" + getExInt3() + ", getExVar1()=" + getExVar1() + ", getExVar2()=" + getExVar2()
				+ ", getExVar3()=" + getExVar3() + ", getEmpFname()=" + getEmpFname() + ", getEmpMname()="
				+ getEmpMname() + ", getEmpSname()=" + getEmpSname() + ", getEmpCode()=" + getEmpCode()
				+ ", getEmpPhoto()=" + getEmpPhoto() + ", getEmpDeptName()=" + getEmpDeptName() + ", getLvTitle()="
				+ getLvTitle() + ", getGetLeaveStatusList()=" + getGetLeaveStatusList() + ", getUserName()="
				+ getUserName() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
}
