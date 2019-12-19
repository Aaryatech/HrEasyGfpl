package com.ats.hreasy.model.claim;

public class TempClaimDetail {

	private int claimDetailId;

 	private String remark;
	
	private String lvTypeName;

	private int typeId;

	private float claimAmount;

	public int getClaimDetailId() {
		return claimDetailId;
	}

	public void setClaimDetailId(int claimDetailId) {
		this.claimDetailId = claimDetailId;
	}

	 
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public float getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(float claimAmount) {
		this.claimAmount = claimAmount;
	}
	 
	public String getLvTypeName() {
		return lvTypeName;
	}

	public void setLvTypeName(String lvTypeName) {
		this.lvTypeName = lvTypeName;
	}

	 
}
