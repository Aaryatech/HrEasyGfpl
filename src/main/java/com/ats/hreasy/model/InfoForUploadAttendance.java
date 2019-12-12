package com.ats.hreasy.model;
 
public class InfoForUploadAttendance {
	 
	private int totalEmp ; 
	private int dateDiff ;  
	private int updatedByStep1;  
	private int updatedByFile;
	
	public int getTotalEmp() {
		return totalEmp;
	}
	public void setTotalEmp(int totalEmp) {
		this.totalEmp = totalEmp;
	}
	public int getDateDiff() {
		return dateDiff;
	}
	public void setDateDiff(int dateDiff) {
		this.dateDiff = dateDiff;
	}
	 
	public int getUpdatedByFile() {
		return updatedByFile;
	}
	public void setUpdatedByFile(int updatedByFile) {
		this.updatedByFile = updatedByFile;
	}
	public int getUpdatedByStep1() {
		return updatedByStep1;
	}
	public void setUpdatedByStep1(int updatedByStep1) {
		this.updatedByStep1 = updatedByStep1;
	}
	@Override
	public String toString() {
		return "InfoForUploadAttendance [totalEmp=" + totalEmp + ", dateDiff=" + dateDiff + ", updatedByStep1="
				+ updatedByStep1 + ", updatedByFile=" + updatedByFile + "]";
	}
	
	

}
