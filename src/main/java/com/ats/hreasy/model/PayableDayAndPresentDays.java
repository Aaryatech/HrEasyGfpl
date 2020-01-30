package com.ats.hreasy.model;
 

public class PayableDayAndPresentDays {
	 
	private String uuid; 
	private double payableDays; 
	private double presentDays; 
	private int plCalcBase; 
	private boolean isError;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public double getPayableDays() {
		return payableDays;
	}
	public void setPayableDays(double payableDays) {
		this.payableDays = payableDays;
	}
	public double getPresentDays() {
		return presentDays;
	}
	public void setPresentDays(double presentDays) {
		this.presentDays = presentDays;
	}
	public int getPlCalcBase() {
		return plCalcBase;
	}
	public void setPlCalcBase(int plCalcBase) {
		this.plCalcBase = plCalcBase;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	@Override
	public String toString() {
		return "PayableDayAndPresentDays [uuid=" + uuid + ", payableDays=" + payableDays + ", presentDays="
				+ presentDays + ", plCalcBase=" + plCalcBase + ", isError=" + isError + "]";
	}
	
	

}
