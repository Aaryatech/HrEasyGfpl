package com.ats.hreasy.model.Advance;
 
public class AdvanceDetails {
	
	
	 
  	private int advDetailId ;
 	
	private int advId ;
	
	private int skipLoginId ;
	
	private String skipLoginTime;
 	
 	private String skipRemarks;
 	
	private int delStatus; 
	
	
	private int exInt1;
	
	private int exInt2;
	
	
	private String exVar1; 
	
	private String exVar2;

	public int getAdvDetailId() {
		return advDetailId;
	}

	public void setAdvDetailId(int advDetailId) {
		this.advDetailId = advDetailId;
	}

	public int getAdvId() {
		return advId;
	}

	public void setAdvId(int advId) {
		this.advId = advId;
	}

	public int getSkipLoginId() {
		return skipLoginId;
	}

	public void setSkipLoginId(int skipLoginId) {
		this.skipLoginId = skipLoginId;
	}

	public String getSkipLoginTime() {
		return skipLoginTime;
	}

	public void setSkipLoginTime(String skipLoginTime) {
		this.skipLoginTime = skipLoginTime;
	}

	public String getSkipRemarks() {
		return skipRemarks;
	}

	public void setSkipRemarks(String skipRemarks) {
		this.skipRemarks = skipRemarks;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
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
		return "AdvanceDetails [advDetailId=" + advDetailId + ", advId=" + advId + ", skipLoginId=" + skipLoginId
				+ ", skipLoginTime=" + skipLoginTime + ", skipRemarks=" + skipRemarks + ", delStatus=" + delStatus
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	} 
	 
	

}
