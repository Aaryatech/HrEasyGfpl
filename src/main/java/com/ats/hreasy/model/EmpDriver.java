package com.ats.hreasy.model;

 
 
  
public class EmpDriver {
 
	private int empDriverId;

	private int designationId;

	private int empId;

	private String licenceNo;

	private String licenceIssueDate;

	private String licenceExpDate;

	private String vehicleNo;

	private String vehicleType;

	private int vehicleTblId;

	private String exVar1;
	private String exVar2;

	private int delStatus;
	private String makerEnterDatetime;
	private int exInt1;
	private int exInt2;

	private int makerUserId;

	public int getEmpDriverId() {
		return empDriverId;
	}

	public void setEmpDriverId(int empDriverId) {
		this.empDriverId = empDriverId;
	}

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	 

	public String getLicenceIssueDate() {
		return licenceIssueDate;
	}

	public void setLicenceIssueDate(String licenceIssueDate) {
		this.licenceIssueDate = licenceIssueDate;
	}

	public String getLicenceExpDate() {
		return licenceExpDate;
	}

	public void setLicenceExpDate(String licenceExpDate) {
		this.licenceExpDate = licenceExpDate;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getVehicleTblId() {
		return vehicleTblId;
	}

	public void setVehicleTblId(int vehicleTblId) {
		this.vehicleTblId = vehicleTblId;
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

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}

	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
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

	public int getMakerUserId() {
		return makerUserId;
	}

	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}

	@Override
	public String toString() {
		return "EmpDriver [empDriverId=" + empDriverId + ", designationId=" + designationId + ", empId=" + empId
				+ ", licenceNo=" + licenceNo + ", licenceIssueDate=" + licenceIssueDate + ", licenceExpDate="
				+ licenceExpDate + ", vehicleNo=" + vehicleNo + ", vehicleType=" + vehicleType + ", vehicleTblId="
				+ vehicleTblId + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", delStatus=" + delStatus
				+ ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", makerUserId=" + makerUserId + "]";
	}
	
	
	

}
