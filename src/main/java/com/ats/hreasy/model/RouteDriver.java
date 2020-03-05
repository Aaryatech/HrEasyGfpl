package com.ats.hreasy.model;

public class RouteDriver {
	private int routeId;
	private String routeName;
	private String shortName;
	private int routeLength;
	private float rateCost;
	private int approxTimetaken;
	private String approxOuttime;
	private String approxIntime;
	private int aproxStationpoint;
	private int extraInt1;
	private int extraInt2;
	private int extraInt3;
	private String extraVar1;
	private String extraVar2;
	private String extraVar3;
	private int delStatus;
	private String loginTime;
	private int loginId;
	
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public int getRouteLength() {
		return routeLength;
	}
	public void setRouteLength(int routeLength) {
		this.routeLength = routeLength;
	}
	public float getRateCost() {
		return rateCost;
	}
	public void setRateCost(float rateCost) {
		this.rateCost = rateCost;
	}
	public int getApproxTimetaken() {
		return approxTimetaken;
	}
	public void setApproxTimetaken(int approxTimetaken) {
		this.approxTimetaken = approxTimetaken;
	}
	public String getApproxOuttime() {
		return approxOuttime;
	}
	public void setApproxOuttime(String approxOuttime) {
		this.approxOuttime = approxOuttime;
	}
	public String getApproxIntime() {
		return approxIntime;
	}
	public void setApproxIntime(String approxIntime) {
		this.approxIntime = approxIntime;
	}
	public int getAproxStationpoint() {
		return aproxStationpoint;
	}
	public void setAproxStationpoint(int aproxStationpoint) {
		this.aproxStationpoint = aproxStationpoint;
	}
	
	public int getExtraInt1() {
		return extraInt1;
	}
	public void setExtraInt1(int extraInt1) {
		this.extraInt1 = extraInt1;
	}
	public int getExtraInt2() {
		return extraInt2;
	}
	public void setExtraInt2(int extraInt2) {
		this.extraInt2 = extraInt2;
	}
	public int getExtraInt3() {
		return extraInt3;
	}
	public void setExtraInt3(int extraInt3) {
		this.extraInt3 = extraInt3;
	}
	public String getExtraVar1() {
		return extraVar1;
	}
	public void setExtraVar1(String extraVar1) {
		this.extraVar1 = extraVar1;
	}
	public String getExtraVar2() {
		return extraVar2;
	}
	public void setExtraVar2(String extraVar2) {
		this.extraVar2 = extraVar2;
	}
	public String getExtraVar3() {
		return extraVar3;
	}
	public void setExtraVar3(String extraVar3) {
		this.extraVar3 = extraVar3;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	@Override
	public String toString() {
		return "RouteDriver [routeId=" + routeId + ", routeName=" + routeName + ", shortName=" + shortName
				+ ", routeLength=" + routeLength + ", rateCost=" + rateCost + ", approxTimetaken=" + approxTimetaken
				+ ", approxOuttime=" + approxOuttime + ", approxIntime=" + approxIntime + ", aproxStationpoint="
				+ aproxStationpoint + ", extraInt1=" + extraInt1 + ", extraInt2=" + extraInt2 + ", extraInt3="
				+ extraInt3 + ", extraVar1=" + extraVar1 + ", extraVar2=" + extraVar2 + ", extraVar3=" + extraVar3
				+ ", delStatus=" + delStatus + ", loginTime=" + loginTime + ", loginId=" + loginId + "]";
	}
	
}
