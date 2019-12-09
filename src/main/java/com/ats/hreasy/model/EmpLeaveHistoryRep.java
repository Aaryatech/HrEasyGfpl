package com.ats.hreasy.model;
 
 
public class EmpLeaveHistoryRep {
 	private String recId;
	
	private int  empId;
	
	private String empName;
	
	private int lvTypeId;
	
	private String lvTitleShort;
	
	private String lvTitle;
	
	private float lvsAllotedLeaves;
	
	private int lvsId;
	
	private float balLeave;
	
	private float sactionLeave;
	
	private float aplliedLeaeve;

	

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getLvTypeId() {
		return lvTypeId;
	}

	public void setLvTypeId(int lvTypeId) {
		this.lvTypeId = lvTypeId;
	}

	public String getLvTitleShort() {
		return lvTitleShort;
	}

	public void setLvTitleShort(String lvTitleShort) {
		this.lvTitleShort = lvTitleShort;
	}

	public String getLvTitle() {
		return lvTitle;
	}

	public void setLvTitle(String lvTitle) {
		this.lvTitle = lvTitle;
	}

	 
	public int getLvsId() {
		return lvsId;
	}

	public void setLvsId(int lvsId) {
		this.lvsId = lvsId;
	}

	 

	public float getLvsAllotedLeaves() {
		return lvsAllotedLeaves;
	}

	public void setLvsAllotedLeaves(float lvsAllotedLeaves) {
		this.lvsAllotedLeaves = lvsAllotedLeaves;
	}

	public float getBalLeave() {
		return balLeave;
	}

	public void setBalLeave(float balLeave) {
		this.balLeave = balLeave;
	}

	public float getSactionLeave() {
		return sactionLeave;
	}

	public void setSactionLeave(float sactionLeave) {
		this.sactionLeave = sactionLeave;
	}

	public float getAplliedLeaeve() {
		return aplliedLeaeve;
	}

	public void setAplliedLeaeve(float aplliedLeaeve) {
		this.aplliedLeaeve = aplliedLeaeve;
	}

	@Override
	public String toString() {
		return "EmpLeaveHistoryRep [recId=" + recId + ", empId=" + empId + ", empName=" + empName + ", lvTypeId="
				+ lvTypeId + ", lvTitleShort=" + lvTitleShort + ", lvTitle=" + lvTitle + ", lvsAllotedLeaves="
				+ lvsAllotedLeaves + ", lvsId=" + lvsId + ", balLeave=" + balLeave + ", sactionLeave=" + sactionLeave
				+ ", aplliedLeaeve=" + aplliedLeaeve + "]";
	}

 
	
	

}
