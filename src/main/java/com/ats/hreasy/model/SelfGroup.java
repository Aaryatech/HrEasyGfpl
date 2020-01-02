package com.ats.hreasy.model;
 

public class SelfGroup {
	
	 
	private int selftGroupId; 
	private String name; 
	private int delStatus;
	public int getSelftGroupId() {
		return selftGroupId;
	}
	public void setSelftGroupId(int selftGroupId) {
		this.selftGroupId = selftGroupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	@Override
	public String toString() {
		return "SelfGroup [selftGroupId=" + selftGroupId + ", name=" + name + ", delStatus=" + delStatus + "]";
	}
	
	

}
