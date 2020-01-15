package com.ats.hreasy.model;

import java.util.List;
  
public class GetPayrollGeneratedList {
	
	private int id;
	private int cmpId;
	private int empId;
	private String empCode;
	private int empType;
	private int contractorId;
	private int departId;
	private int designationId;
	private int locationId;
	private int calcMonth;
	private int calcYear;
	private int salTypeId;
	private int attSumId;
 	private double basicCal; 
	private double performanceBonus; 
	private double otWages; 
	private double miscExpAdd;
	private double bonusCal;
	private double exgretiaCal;
	private double daArreasCal;
	private double incrementArreasCal;
	private double epfWages;
	private double epfWagesEmployer;
	private double esicWagesCal;
	private double grossSalary;
	private double  epsWages;
	private double esicWagesDec;
	private float employeePf;
	private double employerEps;
	private double employerPf;
 	private float esic;
	private double employerEsic;
	private int  esicStatus;
 	private int  pfStatus;
 	private double mlwf;
	private double tds;
	private double itded;
	private double fund;
	private double totPfAdminCh;
	private double totEdliCh;
	private double totEdliAdminCh;
 	private int  ncpDays;
 	private int  status;
	private double ptDed;
	private double advanceDed;
	private double loanDed;
	private double miscExpDed;
	private int  miscExpDedDeduct;
	private double netSalary;
	private String isLocked;
	private String  loginName;
	private String  loginTime;
	private String  mlwfApplicable;
	private String ptApplicable;
	private double payDed; 
	private String  commentsForItBonus;
	private double societyContribution;
	private String  empCategory;
	private double basicDefault;
	
	private String  name; 
	private String  empTypeName; 
	private String  departName; 
	private String  designName;
	
	private float payableDays; 
	private float presentDays;
	private float weeklyOff;
	private float paidHoliday;
	private float paidLeave;
	private float unpaidLeave;
	 
	List<SalAllownceCal> payrollAllownceList;
	private String moneyInword;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCmpId() {
		return cmpId;
	}

	public void setCmpId(int cmpId) {
		this.cmpId = cmpId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public int getEmpType() {
		return empType;
	}

	public void setEmpType(int empType) {
		this.empType = empType;
	}

	public int getContractorId() {
		return contractorId;
	}

	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}

	public int getDepartId() {
		return departId;
	}

	public void setDepartId(int departId) {
		this.departId = departId;
	}

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public int getCalcMonth() {
		return calcMonth;
	}

	public void setCalcMonth(int calcMonth) {
		this.calcMonth = calcMonth;
	}

	public int getCalcYear() {
		return calcYear;
	}

	public void setCalcYear(int calcYear) {
		this.calcYear = calcYear;
	}

	public int getSalTypeId() {
		return salTypeId;
	}

	public void setSalTypeId(int salTypeId) {
		this.salTypeId = salTypeId;
	}

	public int getAttSumId() {
		return attSumId;
	}

	public void setAttSumId(int attSumId) {
		this.attSumId = attSumId;
	}

	public double getBasicCal() {
		return basicCal;
	}

	public void setBasicCal(double basicCal) {
		this.basicCal = basicCal;
	}

	public double getPerformanceBonus() {
		return performanceBonus;
	}

	public void setPerformanceBonus(double performanceBonus) {
		this.performanceBonus = performanceBonus;
	}

	public double getOtWages() {
		return otWages;
	}

	public void setOtWages(double otWages) {
		this.otWages = otWages;
	}

	public double getMiscExpAdd() {
		return miscExpAdd;
	}

	public void setMiscExpAdd(double miscExpAdd) {
		this.miscExpAdd = miscExpAdd;
	}

	public double getBonusCal() {
		return bonusCal;
	}

	public void setBonusCal(double bonusCal) {
		this.bonusCal = bonusCal;
	}

	public double getExgretiaCal() {
		return exgretiaCal;
	}

	public void setExgretiaCal(double exgretiaCal) {
		this.exgretiaCal = exgretiaCal;
	}

	public double getDaArreasCal() {
		return daArreasCal;
	}

	public void setDaArreasCal(double daArreasCal) {
		this.daArreasCal = daArreasCal;
	}

	public double getIncrementArreasCal() {
		return incrementArreasCal;
	}

	public void setIncrementArreasCal(double incrementArreasCal) {
		this.incrementArreasCal = incrementArreasCal;
	}

	public double getEpfWages() {
		return epfWages;
	}

	public void setEpfWages(double epfWages) {
		this.epfWages = epfWages;
	}

	public double getEpfWagesEmployer() {
		return epfWagesEmployer;
	}

	public void setEpfWagesEmployer(double epfWagesEmployer) {
		this.epfWagesEmployer = epfWagesEmployer;
	}

	public double getEsicWagesCal() {
		return esicWagesCal;
	}

	public void setEsicWagesCal(double esicWagesCal) {
		this.esicWagesCal = esicWagesCal;
	}

	public double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public double getEpsWages() {
		return epsWages;
	}

	public void setEpsWages(double epsWages) {
		this.epsWages = epsWages;
	}

	public double getEsicWagesDec() {
		return esicWagesDec;
	}

	public void setEsicWagesDec(double esicWagesDec) {
		this.esicWagesDec = esicWagesDec;
	}

	public float getEmployeePf() {
		return employeePf;
	}

	public void setEmployeePf(float employeePf) {
		this.employeePf = employeePf;
	}

	public double getEmployerEps() {
		return employerEps;
	}

	public void setEmployerEps(double employerEps) {
		this.employerEps = employerEps;
	}

	public double getEmployerPf() {
		return employerPf;
	}

	public void setEmployerPf(double employerPf) {
		this.employerPf = employerPf;
	}

	public float getEsic() {
		return esic;
	}

	public void setEsic(float esic) {
		this.esic = esic;
	}

	public double getEmployerEsic() {
		return employerEsic;
	}

	public void setEmployerEsic(double employerEsic) {
		this.employerEsic = employerEsic;
	}

	public int getEsicStatus() {
		return esicStatus;
	}

	public void setEsicStatus(int esicStatus) {
		this.esicStatus = esicStatus;
	}

	public int getPfStatus() {
		return pfStatus;
	}

	public void setPfStatus(int pfStatus) {
		this.pfStatus = pfStatus;
	}

	public double getMlwf() {
		return mlwf;
	}

	public void setMlwf(double mlwf) {
		this.mlwf = mlwf;
	}

	public double getTds() {
		return tds;
	}

	public void setTds(double tds) {
		this.tds = tds;
	}

	public double getItded() {
		return itded;
	}

	public void setItded(double itded) {
		this.itded = itded;
	}

	public double getFund() {
		return fund;
	}

	public void setFund(double fund) {
		this.fund = fund;
	}

	public double getTotPfAdminCh() {
		return totPfAdminCh;
	}

	public void setTotPfAdminCh(double totPfAdminCh) {
		this.totPfAdminCh = totPfAdminCh;
	}

	public double getTotEdliCh() {
		return totEdliCh;
	}

	public void setTotEdliCh(double totEdliCh) {
		this.totEdliCh = totEdliCh;
	}

	public double getTotEdliAdminCh() {
		return totEdliAdminCh;
	}

	public void setTotEdliAdminCh(double totEdliAdminCh) {
		this.totEdliAdminCh = totEdliAdminCh;
	}

	public int getNcpDays() {
		return ncpDays;
	}

	public void setNcpDays(int ncpDays) {
		this.ncpDays = ncpDays;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getPtDed() {
		return ptDed;
	}

	public void setPtDed(double ptDed) {
		this.ptDed = ptDed;
	}

	public double getAdvanceDed() {
		return advanceDed;
	}

	public void setAdvanceDed(double advanceDed) {
		this.advanceDed = advanceDed;
	}

	public double getLoanDed() {
		return loanDed;
	}

	public void setLoanDed(double loanDed) {
		this.loanDed = loanDed;
	}

	public double getMiscExpDed() {
		return miscExpDed;
	}

	public void setMiscExpDed(double miscExpDed) {
		this.miscExpDed = miscExpDed;
	}

	public int getMiscExpDedDeduct() {
		return miscExpDedDeduct;
	}

	public void setMiscExpDedDeduct(int miscExpDedDeduct) {
		this.miscExpDedDeduct = miscExpDedDeduct;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
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

	public double getPayDed() {
		return payDed;
	}

	public void setPayDed(double payDed) {
		this.payDed = payDed;
	}

	public String getCommentsForItBonus() {
		return commentsForItBonus;
	}

	public void setCommentsForItBonus(String commentsForItBonus) {
		this.commentsForItBonus = commentsForItBonus;
	}

	public double getSocietyContribution() {
		return societyContribution;
	}

	public void setSocietyContribution(double societyContribution) {
		this.societyContribution = societyContribution;
	}

	public String getEmpCategory() {
		return empCategory;
	}

	public void setEmpCategory(String empCategory) {
		this.empCategory = empCategory;
	}

	public double getBasicDefault() {
		return basicDefault;
	}

	public void setBasicDefault(double basicDefault) {
		this.basicDefault = basicDefault;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmpTypeName() {
		return empTypeName;
	}

	public void setEmpTypeName(String empTypeName) {
		this.empTypeName = empTypeName;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public List<SalAllownceCal> getPayrollAllownceList() {
		return payrollAllownceList;
	}

	public void setPayrollAllownceList(List<SalAllownceCal> payrollAllownceList) {
		this.payrollAllownceList = payrollAllownceList;
	}

	public float getPayableDays() {
		return payableDays;
	}

	public void setPayableDays(float payableDays) {
		this.payableDays = payableDays;
	}

	public float getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(float presentDays) {
		this.presentDays = presentDays;
	}

	public float getWeeklyOff() {
		return weeklyOff;
	}

	public void setWeeklyOff(float weeklyOff) {
		this.weeklyOff = weeklyOff;
	}

	public float getPaidHoliday() {
		return paidHoliday;
	}

	public void setPaidHoliday(float paidHoliday) {
		this.paidHoliday = paidHoliday;
	}

	public float getPaidLeave() {
		return paidLeave;
	}

	public void setPaidLeave(float paidLeave) {
		this.paidLeave = paidLeave;
	}

	public float getUnpaidLeave() {
		return unpaidLeave;
	}

	public void setUnpaidLeave(float unpaidLeave) {
		this.unpaidLeave = unpaidLeave;
	}

	public String getMoneyInword() {
		return moneyInword;
	}

	public void setMoneyInword(String moneyInword) {
		this.moneyInword = moneyInword;
	}

	@Override
	public String toString() {
		return "GetPayrollGeneratedList [id=" + id + ", cmpId=" + cmpId + ", empId=" + empId + ", empCode=" + empCode
				+ ", empType=" + empType + ", contractorId=" + contractorId + ", departId=" + departId
				+ ", designationId=" + designationId + ", locationId=" + locationId + ", calcMonth=" + calcMonth
				+ ", calcYear=" + calcYear + ", salTypeId=" + salTypeId + ", attSumId=" + attSumId + ", basicCal="
				+ basicCal + ", performanceBonus=" + performanceBonus + ", otWages=" + otWages + ", miscExpAdd="
				+ miscExpAdd + ", bonusCal=" + bonusCal + ", exgretiaCal=" + exgretiaCal + ", daArreasCal="
				+ daArreasCal + ", incrementArreasCal=" + incrementArreasCal + ", epfWages=" + epfWages
				+ ", epfWagesEmployer=" + epfWagesEmployer + ", esicWagesCal=" + esicWagesCal + ", grossSalary="
				+ grossSalary + ", epsWages=" + epsWages + ", esicWagesDec=" + esicWagesDec + ", employeePf="
				+ employeePf + ", employerEps=" + employerEps + ", employerPf=" + employerPf + ", esic=" + esic
				+ ", employerEsic=" + employerEsic + ", esicStatus=" + esicStatus + ", pfStatus=" + pfStatus + ", mlwf="
				+ mlwf + ", tds=" + tds + ", itded=" + itded + ", fund=" + fund + ", totPfAdminCh=" + totPfAdminCh
				+ ", totEdliCh=" + totEdliCh + ", totEdliAdminCh=" + totEdliAdminCh + ", ncpDays=" + ncpDays
				+ ", status=" + status + ", ptDed=" + ptDed + ", advanceDed=" + advanceDed + ", loanDed=" + loanDed
				+ ", miscExpDed=" + miscExpDed + ", miscExpDedDeduct=" + miscExpDedDeduct + ", netSalary=" + netSalary
				+ ", isLocked=" + isLocked + ", loginName=" + loginName + ", loginTime=" + loginTime
				+ ", mlwfApplicable=" + mlwfApplicable + ", ptApplicable=" + ptApplicable + ", payDed=" + payDed
				+ ", commentsForItBonus=" + commentsForItBonus + ", societyContribution=" + societyContribution
				+ ", empCategory=" + empCategory + ", basicDefault=" + basicDefault + ", name=" + name
				+ ", empTypeName=" + empTypeName + ", departName=" + departName + ", designName=" + designName
				+ ", payableDays=" + payableDays + ", presentDays=" + presentDays + ", weeklyOff=" + weeklyOff
				+ ", paidHoliday=" + paidHoliday + ", paidLeave=" + paidLeave + ", unpaidLeave=" + unpaidLeave
				+ ", payrollAllownceList=" + payrollAllownceList + ", moneyInword=" + moneyInword + "]";
	}
	
	

}