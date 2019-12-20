package com.ats.hreasy.model;
 

 
public class SalaryTypesMaster {

	 
	private int salTypeId;

	private String salTypeName;

	private String description;

	private int companyId;

	private double workinghr;

	private int calSal;

	private int showInPayslipPdf;

	private int showExcel;

	private int delStatus;

	public int getSalTypeId() {
		return salTypeId;
	}

	public void setSalTypeId(int salTypeId) {
		this.salTypeId = salTypeId;
	}

	public String getSalTypeName() {
		return salTypeName;
	}

	public void setSalTypeName(String salTypeName) {
		this.salTypeName = salTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public double getWorkinghr() {
		return workinghr;
	}

	public void setWorkinghr(double workinghr) {
		this.workinghr = workinghr;
	}

	public int getCalSal() {
		return calSal;
	}

	public void setCalSal(int calSal) {
		this.calSal = calSal;
	}

	public int getShowInPayslipPdf() {
		return showInPayslipPdf;
	}

	public void setShowInPayslipPdf(int showInPayslipPdf) {
		this.showInPayslipPdf = showInPayslipPdf;
	}

	public int getShowExcel() {
		return showExcel;
	}

	public void setShowExcel(int showExcel) {
		this.showExcel = showExcel;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "SalaryTypesMaster [salTypeId=" + salTypeId + ", salTypeName=" + salTypeName + ", description="
				+ description + ", companyId=" + companyId + ", workinghr=" + workinghr + ", calSal=" + calSal
				+ ", showInPayslipPdf=" + showInPayslipPdf + ", showExcel=" + showExcel + ", delStatus=" + delStatus
				+ "]";
	}
	
	

}
