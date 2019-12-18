package com.ats.hreasy.model;

public class EmpDoctype {
	
	private int doctypeId;
	private int companyId;
	private String doctypeKey;
	private String doctypeName;
	private String doctypeNote;
	private int isValue;
	private int isImage;
	private int imageSizeWidth;
	private int imageSizeLength;
	private int isRemarks;
	private int delStatus;
	private int isActive;
	private int makerUserId;
	private String makerEnterDatetime;
	private int isRequired;
	private int orderBy;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	public int getDoctypeId() {
		return doctypeId;
	}
	public void setDoctypeId(int doctypeId) {
		this.doctypeId = doctypeId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getDoctypeKey() {
		return doctypeKey;
	}
	public void setDoctypeKey(String doctypeKey) {
		this.doctypeKey = doctypeKey;
	}
	public String getDoctypeName() {
		return doctypeName;
	}
	public void setDoctypeName(String doctypeName) {
		this.doctypeName = doctypeName;
	}
	public String getDoctypeNote() {
		return doctypeNote;
	}
	public void setDoctypeNote(String doctypeNote) {
		this.doctypeNote = doctypeNote;
	}
	public int getIsValue() {
		return isValue;
	}
	public void setIsValue(int isValue) {
		this.isValue = isValue;
	}
	public int getIsImage() {
		return isImage;
	}
	public void setIsImage(int isImage) {
		this.isImage = isImage;
	}
	public int getImageSizeWidth() {
		return imageSizeWidth;
	}
	public void setImageSizeWidth(int imageSizeWidth) {
		this.imageSizeWidth = imageSizeWidth;
	}
	public int getImageSizeLength() {
		return imageSizeLength;
	}
	public void setImageSizeLength(int imageSizeLength) {
		this.imageSizeLength = imageSizeLength;
	}
	public int getIsRemarks() {
		return isRemarks;
	}
	public void setIsRemarks(int isRemarks) {
		this.isRemarks = isRemarks;
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
	public int getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}
	public int getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
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
	@Override
	public String toString() {
		return "EmpDoctype [doctypeId=" + doctypeId + ", companyId=" + companyId + ", doctypeKey=" + doctypeKey
				+ ", doctypeName=" + doctypeName + ", doctypeNote=" + doctypeNote + ", isValue=" + isValue
				+ ", isImage=" + isImage + ", imageSizeWidth=" + imageSizeWidth + ", imageSizeLength=" + imageSizeLength
				+ ", isRemarks=" + isRemarks + ", delStatus=" + delStatus + ", isActive=" + isActive + ", makerUserId="
				+ makerUserId + ", makerEnterDatetime=" + makerEnterDatetime + ", isRequired=" + isRequired
				+ ", orderBy=" + orderBy + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + "]";
	}


}
