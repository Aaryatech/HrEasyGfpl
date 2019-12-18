package com.ats.hreasy.model;

public class EmployeDoc {

	private int docId;
	
	private int empId;
	
	private int doctypeId;
	
	private String docValue;
	
	private String docImage;
	
	private String docRemarks;
	
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

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getDoctypeId() {
		return doctypeId;
	}

	public void setDoctypeId(int doctypeId) {
		this.doctypeId = doctypeId;
	}

	public String getDocValue() {
		return docValue;
	}

	public void setDocValue(String docValue) {
		this.docValue = docValue;
	}

	public String getDocImage() {
		return docImage;
	}

	public void setDocImage(String docImage) {
		this.docImage = docImage;
	}

	public String getDocRemarks() {
		return docRemarks;
	}

	public void setDocRemarks(String docRemarks) {
		this.docRemarks = docRemarks;
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

	@Override
	public String toString() {
		return "EmployeDoc [docId=" + docId + ", empId=" + empId + ", doctypeId=" + doctypeId + ", docValue=" + docValue
				+ ", docImage=" + docImage + ", docRemarks=" + docRemarks + ", delStatus=" + delStatus + ", isActive="
				+ isActive + ", makerUserId=" + makerUserId + ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exVar3=" + exVar3 + "]";
	}

	
}
