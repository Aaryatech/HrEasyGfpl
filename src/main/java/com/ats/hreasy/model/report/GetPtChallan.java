package com.ats.hreasy.model.report;
 
public class GetPtChallan {
 
	
	 
 	private int slabId ;
	  
 	private int minVal ;
	
 	private int maxVal;
 	
 	
 	private double total;

  	private int empCount;

	 
	public int getSlabId() {
		return slabId;
	}

	public void setSlabId(int slabId) {
		this.slabId = slabId;
	}

	public int getMinVal() {
		return minVal;
	}

	public void setMinVal(int minVal) {
		this.minVal = minVal;
	}

	public int getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(int maxVal) {
		this.maxVal = maxVal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getEmpCount() {
		return empCount;
	}

	public void setEmpCount(int empCount) {
		this.empCount = empCount;
	}

	@Override
	public String toString() {
		return "GetPtChallan [slabId=" + slabId + ", minVal=" + minVal + ", maxVal=" + maxVal + ", total=" + total
				+ ", empCount=" + empCount + "]";
	}

	 
}
