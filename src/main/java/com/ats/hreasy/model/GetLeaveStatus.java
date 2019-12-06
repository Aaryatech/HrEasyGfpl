package com.ats.hreasy.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GetLeaveStatus {

	private int trailPkey;
	
	private int leaveId ;
	
	private int empId;
	
	private String empRemarks;
	
	private int leaveStatus;
	
	private int makerUserId ;
		
	private String makerEnterDatetime;
	
	private String empFname;
	
	private String empMname;
	
	private String empSname;
	
	private String empPhoto;
	
	private String userName;

	public int getTrailPkey() {
		return trailPkey;
	}

	public void setTrailPkey(int trailPkey) {
		this.trailPkey = trailPkey;
	}

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpRemarks() {
		return empRemarks;
	}

	public void setEmpRemarks(String empRemarks) {
		this.empRemarks = empRemarks;
	}

	public int getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(int leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
 
	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}

	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
	}

	public String getEmpFname() {
		return empFname;
	}

	public void setEmpFname(String empFname) {
		this.empFname = empFname;
	}

	public String getEmpMname() {
		return empMname;
	}

	public void setEmpMname(String empMname) {
		this.empMname = empMname;
	}

	public String getEmpSname() {
		return empSname;
	}

	public void setEmpSname(String empSname) {
		this.empSname = empSname;
	}

	public String getEmpPhoto() {
		return empPhoto;
	}

	public void setEmpPhoto(String empPhoto) {
		this.empPhoto = empPhoto;
	}

	public int getMakerUserId() {
		return makerUserId;
	}

	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "GetLeaveStatus [trailPkey=" + trailPkey + ", leaveId=" + leaveId + ", empId=" + empId + ", empRemarks="
				+ empRemarks + ", leaveStatus=" + leaveStatus + ", makerUserId=" + makerUserId + ", makerEnterDatetime="
				+ makerEnterDatetime + ", empFname=" + empFname + ", empMname=" + empMname + ", empSname=" + empSname
				+ ", empPhoto=" + empPhoto + ", userName=" + userName + "]";
	}

}


