package com.ats.hreasy.model;

public class LeaveAuthority {

	private int laPkey;

	private int empId;

	private int companyId;

	private int iniAuthEmpId;

	private int finAuthEmpId;

	private String repToEmpIds;

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

	public int getLaPkey() {
		return laPkey;
	}

	public void setLaPkey(int laPkey) {
		this.laPkey = laPkey;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getIniAuthEmpId() {
		return iniAuthEmpId;
	}

	public void setIniAuthEmpId(int iniAuthEmpId) {
		this.iniAuthEmpId = iniAuthEmpId;
	}

	public int getFinAuthEmpId() {
		return finAuthEmpId;
	}

	public void setFinAuthEmpId(int finAuthEmpId) {
		this.finAuthEmpId = finAuthEmpId;
	}

	public String getRepToEmpIds() {
		return repToEmpIds;
	}

	public void setRepToEmpIds(String repToEmpIds) {
		this.repToEmpIds = repToEmpIds;
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
		return "LeaveAuthority [laPkey=" + laPkey + ", empId=" + empId + ", companyId=" + companyId + ", iniAuthEmpId="
				+ iniAuthEmpId + ", finAuthEmpId=" + finAuthEmpId + ", repToEmpIds=" + repToEmpIds + ", delStatus="
				+ delStatus + ", isActive=" + isActive + ", makerUserId=" + makerUserId + ", makerEnterDatetime="
				+ makerEnterDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1="
				+ exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", error=" + error + "]";
	}

}
