package com.ats.hreasy.model.claim;

import java.util.List;

 
 
public class ClaimStructureHeader {
	
	 
	private int clmStructHeadId;

 	private int companyId;

 	private String claimStructName;

 	private int delStatus;

 	private int isActive;

 	private int makerUserId;

 	private String makerDatetime;

 	private int exInt1;

 	private int exInt2;

 	private String exVar1;

 	private String exVar2;

	 
	List<ClaimStructureDetail> detailList;

	public int getClmStructHeadId() {
		return clmStructHeadId;
	}

	public void setClmStructHeadId(int clmStructHeadId) {
		this.clmStructHeadId = clmStructHeadId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getClaimStructName() {
		return claimStructName;
	}

	public void setClaimStructName(String claimStructName) {
		this.claimStructName = claimStructName;
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

	public String getMakerDatetime() {
		return makerDatetime;
	}

	public void setMakerDatetime(String makerDatetime) {
		this.makerDatetime = makerDatetime;
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

	public List<ClaimStructureDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ClaimStructureDetail> detailList) {
		this.detailList = detailList;
	}

	@Override
	public String toString() {
		return "ClaimStructureHeader [clmStructHeadId=" + clmStructHeadId + ", companyId=" + companyId
				+ ", claimStructName=" + claimStructName + ", delStatus=" + delStatus + ", isActive=" + isActive
				+ ", makerUserId=" + makerUserId + ", makerDatetime=" + makerDatetime + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", detailList=" + detailList
				+ "]";
	}
	
	
	


}
