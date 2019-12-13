package com.ats.hreasy.model;
 

public class LvmSumUp {

	 
	private int id; 
	private String name; 
	private String nameSd; 
	private String isUsed; 
	private int ispermanent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameSd() {
		return nameSd;
	}
	public void setNameSd(String nameSd) {
		this.nameSd = nameSd;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	public int getIspermanent() {
		return ispermanent;
	}
	public void setIspermanent(int ispermanent) {
		this.ispermanent = ispermanent;
	}
	@Override
	public String toString() {
		return "LvmSumUp [id=" + id + ", name=" + name + ", nameSd=" + nameSd + ", isUsed=" + isUsed + ", ispermanent="
				+ ispermanent + "]";
	}
	
	
	
}
