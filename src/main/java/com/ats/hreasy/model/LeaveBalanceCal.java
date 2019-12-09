package com.ats.hreasy.model;

public class LeaveBalanceCal {

	private int lvbalId;

	private int calYrId;

	private int empId;

	private int lvTypeId;

	private float opBal;

	private float lvAlloted;

	private float lvEncash;

	private String lvEncashRemarks;

	private float lvCarryFwd;

	private String lvCarryFwdRemarks;

	private int delStatus;

	private int isActive;

	private int makerUserId;

	private String makerEnterDatetime;

	private int exInt1;

	private int exInt2;

	private int exInt3;

	private String exVar1;

	private String exVar2;

	private String exVar3;

	private boolean error;

	public int getLvbalId() {
		return lvbalId;
	}

	public void setLvbalId(int lvbalId) {
		this.lvbalId = lvbalId;
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

	public float getOpBal() {
		return opBal;
	}

	public void setOpBal(float opBal) {
		this.opBal = opBal;
	}

	public float getLvAlloted() {
		return lvAlloted;
	}

	public void setLvAlloted(float lvAlloted) {
		this.lvAlloted = lvAlloted;
	}

	public float getLvEncash() {
		return lvEncash;
	}

	public void setLvEncash(float lvEncash) {
		this.lvEncash = lvEncash;
	}

	public String getLvEncashRemarks() {
		return lvEncashRemarks;
	}

	public void setLvEncashRemarks(String lvEncashRemarks) {
		this.lvEncashRemarks = lvEncashRemarks;
	}

	public float getLvCarryFwd() {
		return lvCarryFwd;
	}

	public void setLvCarryFwd(float lvCarryFwd) {
		this.lvCarryFwd = lvCarryFwd;
	}

	public String getLvCarryFwdRemarks() {
		return lvCarryFwdRemarks;
	}

	public void setLvCarryFwdRemarks(String lvCarryFwdRemarks) {
		this.lvCarryFwdRemarks = lvCarryFwdRemarks;
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

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "LeaveBalanceCal [lvbalId=" + lvbalId + ", calYrId=" + calYrId + ", empId=" + empId + ", lvTypeId="
				+ lvTypeId + ", opBal=" + opBal + ", lvAlloted=" + lvAlloted + ", lvEncash=" + lvEncash
				+ ", lvEncashRemarks=" + lvEncashRemarks + ", lvCarryFwd=" + lvCarryFwd + ", lvCarryFwdRemarks="
				+ lvCarryFwdRemarks + ", delStatus=" + delStatus + ", isActive=" + isActive + ", makerUserId="
				+ makerUserId + ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1=" + exInt1 + ", exInt2="
				+ exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3
				+ ", error=" + error + "]";
	}

}
