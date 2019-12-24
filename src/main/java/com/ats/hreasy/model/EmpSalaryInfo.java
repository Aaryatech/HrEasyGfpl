package com.ats.hreasy.model;

public class EmpSalaryInfo {

	private int salaryInfoId;
	private int empId;
	private int salaryTypeId;
	private double basic;
	private double da;
	private double hra;
	private double spa;
	private String pfApplicable;
	private String pfType;
	private double pfEmpPer;
	private double pfEmplrPer;
	private String esicApplicable;	
	private String cmpJoiningDate;
	private String cmpLeavingDate;
	private String epfJoiningDate;
	private String leavingReason;
	private String salBasis;
	private String ceilingLimitEmpApplicable;
	private String ceilingLimitEmployerApplicable;
	private String leavingReasonEsic;
	private String leavingReasonPf;
	private String mlwfApplicable;
	private String ptApplicable;
	private double grossSalary;
	private double societyContribution;
	private double basicCompany;
	private double hraCompany;
	private double daCompany;
	private double employeeEsicPercentage;
	private double employerEsicPercentage;

	private int delStatus;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;	
	
	public int getSalaryInfoId() {
		return salaryInfoId;
	}

	public void setSalaryInfoId(int salaryInfoId) {
		this.salaryInfoId = salaryInfoId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getSalaryTypeId() {
		return salaryTypeId;
	}

	public void setSalaryTypeId(int salaryTypeId) {
		this.salaryTypeId = salaryTypeId;
	}

	public double getBasic() {
		return basic;
	}

	public void setBasic(double basic) {
		this.basic = basic;
	}

	public double getDa() {
		return da;
	}

	public void setDa(double da) {
		this.da = da;
	}

	public double getHra() {
		return hra;
	}

	public void setHra(double hra) {
		this.hra = hra;
	}

	public double getSpa() {
		return spa;
	}

	public void setSpa(double spa) {
		this.spa = spa;
	}

	public String getPfApplicable() {
		return pfApplicable;
	}

	public void setPfApplicable(String pfApplicable) {
		this.pfApplicable = pfApplicable;
	}

	public String getPfType() {
		return pfType;
	}

	public void setPfType(String pfType) {
		this.pfType = pfType;
	}

	public double getPfEmpPer() {
		return pfEmpPer;
	}

	public void setPfEmpPer(double pfEmpPer) {
		this.pfEmpPer = pfEmpPer;
	}

	public double getPfEmplrPer() {
		return pfEmplrPer;
	}

	public void setPfEmplrPer(double pfEmplrPer) {
		this.pfEmplrPer = pfEmplrPer;
	}

	public String getEsicApplicable() {
		return esicApplicable;
	}

	public void setEsicApplicable(String esicApplicable) {
		this.esicApplicable = esicApplicable;
	}

	public String getCmpJoiningDate() {
		return cmpJoiningDate;
	}

	public void setCmpJoiningDate(String cmpJoiningDate) {
		this.cmpJoiningDate = cmpJoiningDate;
	}

	public String getCmpLeavingDate() {
		return cmpLeavingDate;
	}

	public void setCmpLeavingDate(String cmpLeavingDate) {
		this.cmpLeavingDate = cmpLeavingDate;
	}

	public String getEpfJoiningDate() {
		return epfJoiningDate;
	}

	public void setEpfJoiningDate(String epfJoiningDate) {
		this.epfJoiningDate = epfJoiningDate;
	}

	public String getLeavingReason() {
		return leavingReason;
	}

	public void setLeavingReason(String leavingReason) {
		this.leavingReason = leavingReason;
	}


	public String getCeilingLimitEmpApplicable() {
		return ceilingLimitEmpApplicable;
	}

	public void setCeilingLimitEmpApplicable(String ceilingLimitEmpApplicable) {
		this.ceilingLimitEmpApplicable = ceilingLimitEmpApplicable;
	}

	public String getCeilingLimitEmployerApplicable() {
		return ceilingLimitEmployerApplicable;
	}

	public void setCeilingLimitEmployerApplicable(String ceilingLimitEmployerApplicable) {
		this.ceilingLimitEmployerApplicable = ceilingLimitEmployerApplicable;
	}

	public String getLeavingReasonEsic() {
		return leavingReasonEsic;
	}

	public void setLeavingReasonEsic(String leavingReasonEsic) {
		this.leavingReasonEsic = leavingReasonEsic;
	}

	public String getLeavingReasonPf() {
		return leavingReasonPf;
	}

	public void setLeavingReasonPf(String leavingReasonPf) {
		this.leavingReasonPf = leavingReasonPf;
	}

	public String getMlwfApplicable() {
		return mlwfApplicable;
	}

	public void setMlwfApplicable(String mlwfApplicable) {
		this.mlwfApplicable = mlwfApplicable;
	}

	public String getPtApplicable() {
		return ptApplicable;
	}

	public void setPtApplicable(String ptApplicable) {
		this.ptApplicable = ptApplicable;
	}

	public double getSocietyContribution() {
		return societyContribution;
	}

	public void setSocietyContribution(double societyContribution) {
		this.societyContribution = societyContribution;
	}

	public double getBasicCompany() {
		return basicCompany;
	}

	public void setBasicCompany(double basicCompany) {
		this.basicCompany = basicCompany;
	}

	public double getHraCompany() {
		return hraCompany;
	}

	public void setHraCompany(double hraCompany) {
		this.hraCompany = hraCompany;
	}

	public double getDaCompany() {
		return daCompany;
	}

	public void setDaCompany(double daCompany) {
		this.daCompany = daCompany;
	}

	public double getEmployeeEsicPercentage() {
		return employeeEsicPercentage;
	}

	public void setEmployeeEsicPercentage(double employeeEsicPercentage) {
		this.employeeEsicPercentage = employeeEsicPercentage;
	}

	public double getEmployerEsicPercentage() {
		return employerEsicPercentage;
	}

	public void setEmployerEsicPercentage(double employerEsicPercentage) {
		this.employerEsicPercentage = employerEsicPercentage;
	}

	public String getSalBasis() {
		return salBasis;
	}

	public void setSalBasis(String salBasis) {
		this.salBasis = salBasis;
	}

	public double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
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

	@Override
	public String toString() {
		return "EmpSalaryInfo [salaryInfoId=" + salaryInfoId + ", empId=" + empId + ", salaryTypeId=" + salaryTypeId
				+ ", basic=" + basic + ", da=" + da + ", hra=" + hra + ", spa=" + spa + ", pfApplicable=" + pfApplicable
				+ ", pfType=" + pfType + ", pfEmpPer=" + pfEmpPer + ", pfEmplrPer=" + pfEmplrPer + ", esicApplicable="
				+ esicApplicable + ", cmpJoiningDate=" + cmpJoiningDate + ", cmpLeavingDate=" + cmpLeavingDate
				+ ", epfJoiningDate=" + epfJoiningDate + ", leavingReason=" + leavingReason + ", salBasis=" + salBasis
				+ ", ceilingLimitEmpApplicable=" + ceilingLimitEmpApplicable + ", ceilingLimitEmployerApplicable="
				+ ceilingLimitEmployerApplicable + ", leavingReasonEsic=" + leavingReasonEsic + ", leavingReasonPf="
				+ leavingReasonPf + ", mlwfApplicable=" + mlwfApplicable + ", ptApplicable=" + ptApplicable
				+ ", grossSalary=" + grossSalary + ", societyContribution=" + societyContribution + ", basicCompany="
				+ basicCompany + ", hraCompany=" + hraCompany + ", daCompany=" + daCompany + ", employeeEsicPercentage="
				+ employeeEsicPercentage + ", employerEsicPercentage=" + employerEsicPercentage + ", delStatus="
				+ delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ "]";
	}

	

}
