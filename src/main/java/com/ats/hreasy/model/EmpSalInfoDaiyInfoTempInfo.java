package com.ats.hreasy.model;

import java.util.List;
 
 

public class EmpSalInfoDaiyInfoTempInfo {
	
	// tbl_salary_dynamic_temp 
		private String uuid;
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
		private double grossSalaryDytemp;
		private double epsWages;
		private double esicWagesDec;
		private float employeePf;
		private double employerEps;
		private double employerPf;
		private float esic;
		private double employerEsic;
		private int esicStatus;
		private int pfStatus;
		private double mlwf;
		private double tds;
		private double itded;
		private double fund;
		private double totPfAdminCh;
		private double totEdliCh;
		private double totEdliAdminCh;
		private int ncpDaysDytemp;
		private int statusDytemp;
		private double ptDed;
		private double advanceDed;
		private double loanDed;
		private double miscExpDed;
		private int miscExpDedDeduct;
		private double netSalary;
		private String isLocked; 
		private String mlwfApplicableDytemp;
		private String ptApplicableDytemp;
		private double payDed;
		private String commentsForItBonus;
		private double societyContributionDytemp;
		private String empCategory;
		private double basicDefault;
		private double abDeduction;
		private double epfPercentage;
		private double epsEmployeePercentage;
		private double productionInsentive;
		private double epfEmployerPercentage;
		private double epsEmployerPercentage;
		private double presentInsentive;
		private double nightAllow; 
		private double epsDefault; 
		private double epmloyerEpfDefault; 
		private double epmloyerEpfExtra; 
		private double pfAdminChPercentage; 
		private double edliPercentage; 
		private double edliAdminPercentage;  
		private double employerEsicPercentageSal; 
		private double employeeEsicPercentageSal;
		private double employerMlwf;
		private double adjustMinus;
		private double adjustPlus;
		private double reward;
		
		// tbl_emp_salary_info
		private int salaryInfoId; 
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

		// tbl_attt_summary_daily
		private int sumDailyId;
		private int companyId; 
		private String empName;
		private int month;
		private int year;
		private float workingDays;
		private float presentDays;
		private float weeklyOff;
		private float paidHoliday;
		private float paidLeave;
		private float legalStrike;
		private float layOff;
		private float unpaidHoliday;
		private float unpaidLeave;
		private int absentDays;
		private float payableDays;
		private float ncpDays;
		private float totlateMins;
		private float totlateDays;
		private float totoutMins;
		private float totworkingHrs;
		private float tototHrs;
		private float totOthr;
		private int totLate;
		private String recStatus;
		private String loginName;
		private String loginTime;
		private int status;
		private String importDate;
		private int recStatusPaid;
		private int totalDaysInmonth;
		private int lateDedLeavePaid;
		private float holidayPresent;
		private float weeklyOffPresent;
		private float fullNight;
		private float halfNight;
		private float holidayPresentHalf;
		private float weeklyOffPresentHalf;
		private float weeklyOffHolidayOff;
		private float weeklyOffHolidayOffPresent;
		private float weeklyOffHolidayOffPresentHalfday;
		private float hdpresentHdleave;
		private int totEarlyGoing;
		private String atsummUid;
		private int calculationDone; 
		private String dob; 
		private List<SalAllownceTemp> getAllowanceTempList;
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
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
		public double getGrossSalaryDytemp() {
			return grossSalaryDytemp;
		}
		public void setGrossSalaryDytemp(double grossSalaryDytemp) {
			this.grossSalaryDytemp = grossSalaryDytemp;
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
		public int getNcpDaysDytemp() {
			return ncpDaysDytemp;
		}
		public void setNcpDaysDytemp(int ncpDaysDytemp) {
			this.ncpDaysDytemp = ncpDaysDytemp;
		}
		public int getStatusDytemp() {
			return statusDytemp;
		}
		public void setStatusDytemp(int statusDytemp) {
			this.statusDytemp = statusDytemp;
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
		public String getMlwfApplicableDytemp() {
			return mlwfApplicableDytemp;
		}
		public void setMlwfApplicableDytemp(String mlwfApplicableDytemp) {
			this.mlwfApplicableDytemp = mlwfApplicableDytemp;
		}
		public String getPtApplicableDytemp() {
			return ptApplicableDytemp;
		}
		public void setPtApplicableDytemp(String ptApplicableDytemp) {
			this.ptApplicableDytemp = ptApplicableDytemp;
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
		public double getSocietyContributionDytemp() {
			return societyContributionDytemp;
		}
		public void setSocietyContributionDytemp(double societyContributionDytemp) {
			this.societyContributionDytemp = societyContributionDytemp;
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
		public int getSalaryInfoId() {
			return salaryInfoId;
		}
		public void setSalaryInfoId(int salaryInfoId) {
			this.salaryInfoId = salaryInfoId;
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
		public String getSalBasis() {
			return salBasis;
		}
		public void setSalBasis(String salBasis) {
			this.salBasis = salBasis;
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
		public double getGrossSalary() {
			return grossSalary;
		}
		public void setGrossSalary(double grossSalary) {
			this.grossSalary = grossSalary;
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
		public int getDelStatus() {
			return delStatus;
		}
		public void setDelStatus(int delStatus) {
			this.delStatus = delStatus;
		}
		public int getSumDailyId() {
			return sumDailyId;
		}
		public void setSumDailyId(int sumDailyId) {
			this.sumDailyId = sumDailyId;
		}
		public int getCompanyId() {
			return companyId;
		}
		public void setCompanyId(int companyId) {
			this.companyId = companyId;
		}
		public String getEmpName() {
			return empName;
		}
		public void setEmpName(String empName) {
			this.empName = empName;
		}
		public int getMonth() {
			return month;
		}
		public void setMonth(int month) {
			this.month = month;
		}
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		public float getWorkingDays() {
			return workingDays;
		}
		public void setWorkingDays(float workingDays) {
			this.workingDays = workingDays;
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
		public float getLegalStrike() {
			return legalStrike;
		}
		public void setLegalStrike(float legalStrike) {
			this.legalStrike = legalStrike;
		}
		public float getLayOff() {
			return layOff;
		}
		public void setLayOff(float layOff) {
			this.layOff = layOff;
		}
		public float getUnpaidHoliday() {
			return unpaidHoliday;
		}
		public void setUnpaidHoliday(float unpaidHoliday) {
			this.unpaidHoliday = unpaidHoliday;
		}
		public float getUnpaidLeave() {
			return unpaidLeave;
		}
		public void setUnpaidLeave(float unpaidLeave) {
			this.unpaidLeave = unpaidLeave;
		}
		public int getAbsentDays() {
			return absentDays;
		}
		public void setAbsentDays(int absentDays) {
			this.absentDays = absentDays;
		}
		public float getPayableDays() {
			return payableDays;
		}
		public void setPayableDays(float payableDays) {
			this.payableDays = payableDays;
		}
		public float getNcpDays() {
			return ncpDays;
		}
		public void setNcpDays(float ncpDays) {
			this.ncpDays = ncpDays;
		}
		public float getTotlateMins() {
			return totlateMins;
		}
		public void setTotlateMins(float totlateMins) {
			this.totlateMins = totlateMins;
		}
		public float getTotlateDays() {
			return totlateDays;
		}
		public void setTotlateDays(float totlateDays) {
			this.totlateDays = totlateDays;
		}
		public float getTotoutMins() {
			return totoutMins;
		}
		public void setTotoutMins(float totoutMins) {
			this.totoutMins = totoutMins;
		}
		public float getTotworkingHrs() {
			return totworkingHrs;
		}
		public void setTotworkingHrs(float totworkingHrs) {
			this.totworkingHrs = totworkingHrs;
		}
		public float getTototHrs() {
			return tototHrs;
		}
		public void setTototHrs(float tototHrs) {
			this.tototHrs = tototHrs;
		}
		public float getTotOthr() {
			return totOthr;
		}
		public void setTotOthr(float totOthr) {
			this.totOthr = totOthr;
		}
		public int getTotLate() {
			return totLate;
		}
		public void setTotLate(int totLate) {
			this.totLate = totLate;
		}
		public String getRecStatus() {
			return recStatus;
		}
		public void setRecStatus(String recStatus) {
			this.recStatus = recStatus;
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
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getImportDate() {
			return importDate;
		}
		public void setImportDate(String importDate) {
			this.importDate = importDate;
		}
		public int getRecStatusPaid() {
			return recStatusPaid;
		}
		public void setRecStatusPaid(int recStatusPaid) {
			this.recStatusPaid = recStatusPaid;
		}
		public int getTotalDaysInmonth() {
			return totalDaysInmonth;
		}
		public void setTotalDaysInmonth(int totalDaysInmonth) {
			this.totalDaysInmonth = totalDaysInmonth;
		}
		public int getLateDedLeavePaid() {
			return lateDedLeavePaid;
		}
		public void setLateDedLeavePaid(int lateDedLeavePaid) {
			this.lateDedLeavePaid = lateDedLeavePaid;
		}
		public float getHolidayPresent() {
			return holidayPresent;
		}
		public void setHolidayPresent(float holidayPresent) {
			this.holidayPresent = holidayPresent;
		}
		public float getWeeklyOffPresent() {
			return weeklyOffPresent;
		}
		public void setWeeklyOffPresent(float weeklyOffPresent) {
			this.weeklyOffPresent = weeklyOffPresent;
		}
		public float getFullNight() {
			return fullNight;
		}
		public void setFullNight(float fullNight) {
			this.fullNight = fullNight;
		}
		public float getHalfNight() {
			return halfNight;
		}
		public void setHalfNight(float halfNight) {
			this.halfNight = halfNight;
		}
		public float getHolidayPresentHalf() {
			return holidayPresentHalf;
		}
		public void setHolidayPresentHalf(float holidayPresentHalf) {
			this.holidayPresentHalf = holidayPresentHalf;
		}
		public float getWeeklyOffPresentHalf() {
			return weeklyOffPresentHalf;
		}
		public void setWeeklyOffPresentHalf(float weeklyOffPresentHalf) {
			this.weeklyOffPresentHalf = weeklyOffPresentHalf;
		}
		public float getWeeklyOffHolidayOff() {
			return weeklyOffHolidayOff;
		}
		public void setWeeklyOffHolidayOff(float weeklyOffHolidayOff) {
			this.weeklyOffHolidayOff = weeklyOffHolidayOff;
		}
		public float getWeeklyOffHolidayOffPresent() {
			return weeklyOffHolidayOffPresent;
		}
		public void setWeeklyOffHolidayOffPresent(float weeklyOffHolidayOffPresent) {
			this.weeklyOffHolidayOffPresent = weeklyOffHolidayOffPresent;
		}
		public float getWeeklyOffHolidayOffPresentHalfday() {
			return weeklyOffHolidayOffPresentHalfday;
		}
		public void setWeeklyOffHolidayOffPresentHalfday(float weeklyOffHolidayOffPresentHalfday) {
			this.weeklyOffHolidayOffPresentHalfday = weeklyOffHolidayOffPresentHalfday;
		}
		public float getHdpresentHdleave() {
			return hdpresentHdleave;
		}
		public void setHdpresentHdleave(float hdpresentHdleave) {
			this.hdpresentHdleave = hdpresentHdleave;
		}
		public int getTotEarlyGoing() {
			return totEarlyGoing;
		}
		public void setTotEarlyGoing(int totEarlyGoing) {
			this.totEarlyGoing = totEarlyGoing;
		}
		public String getAtsummUid() {
			return atsummUid;
		}
		public void setAtsummUid(String atsummUid) {
			this.atsummUid = atsummUid;
		}
		public int getCalculationDone() {
			return calculationDone;
		}
		public void setCalculationDone(int calculationDone) {
			this.calculationDone = calculationDone;
		}
		public String getDob() {
			return dob;
		}
		public void setDob(String dob) {
			this.dob = dob;
		}
		public List<SalAllownceTemp> getGetAllowanceTempList() {
			return getAllowanceTempList;
		}
		public void setGetAllowanceTempList(List<SalAllownceTemp> getAllowanceTempList) {
			this.getAllowanceTempList = getAllowanceTempList;
		}
		public double getAbDeduction() {
			return abDeduction;
		}
		public void setAbDeduction(double abDeduction) {
			this.abDeduction = abDeduction;
		}
		public double getEpfPercentage() {
			return epfPercentage;
		}
		public void setEpfPercentage(double epfPercentage) {
			this.epfPercentage = epfPercentage;
		}
		public double getEpsEmployeePercentage() {
			return epsEmployeePercentage;
		}
		public void setEpsEmployeePercentage(double epsEmployeePercentage) {
			this.epsEmployeePercentage = epsEmployeePercentage;
		}
		public double getProductionInsentive() {
			return productionInsentive;
		}
		public void setProductionInsentive(double productionInsentive) {
			this.productionInsentive = productionInsentive;
		}
		public double getEpfEmployerPercentage() {
			return epfEmployerPercentage;
		}
		public void setEpfEmployerPercentage(double epfEmployerPercentage) {
			this.epfEmployerPercentage = epfEmployerPercentage;
		}
		public double getEpsEmployerPercentage() {
			return epsEmployerPercentage;
		}
		public void setEpsEmployerPercentage(double epsEmployerPercentage) {
			this.epsEmployerPercentage = epsEmployerPercentage;
		}
		public double getPresentInsentive() {
			return presentInsentive;
		}
		public void setPresentInsentive(double presentInsentive) {
			this.presentInsentive = presentInsentive;
		}
		public double getNightAllow() {
			return nightAllow;
		}
		public void setNightAllow(double nightAllow) {
			this.nightAllow = nightAllow;
		}
		public double getEpsDefault() {
			return epsDefault;
		}
		public void setEpsDefault(double epsDefault) {
			this.epsDefault = epsDefault;
		}
		public double getEpmloyerEpfDefault() {
			return epmloyerEpfDefault;
		}
		public void setEpmloyerEpfDefault(double epmloyerEpfDefault) {
			this.epmloyerEpfDefault = epmloyerEpfDefault;
		}
		public double getEpmloyerEpfExtra() {
			return epmloyerEpfExtra;
		}
		public void setEpmloyerEpfExtra(double epmloyerEpfExtra) {
			this.epmloyerEpfExtra = epmloyerEpfExtra;
		}
		public double getPfAdminChPercentage() {
			return pfAdminChPercentage;
		}
		public void setPfAdminChPercentage(double pfAdminChPercentage) {
			this.pfAdminChPercentage = pfAdminChPercentage;
		}
		public double getEdliPercentage() {
			return edliPercentage;
		}
		public void setEdliPercentage(double edliPercentage) {
			this.edliPercentage = edliPercentage;
		}
		public double getEdliAdminPercentage() {
			return edliAdminPercentage;
		}
		public void setEdliAdminPercentage(double edliAdminPercentage) {
			this.edliAdminPercentage = edliAdminPercentage;
		}
		public double getEmployerEsicPercentageSal() {
			return employerEsicPercentageSal;
		}
		public void setEmployerEsicPercentageSal(double employerEsicPercentageSal) {
			this.employerEsicPercentageSal = employerEsicPercentageSal;
		}
		public double getEmployeeEsicPercentageSal() {
			return employeeEsicPercentageSal;
		}
		public void setEmployeeEsicPercentageSal(double employeeEsicPercentageSal) {
			this.employeeEsicPercentageSal = employeeEsicPercentageSal;
		}
		public double getEmployerMlwf() {
			return employerMlwf;
		}
		public void setEmployerMlwf(double employerMlwf) {
			this.employerMlwf = employerMlwf;
		}
		public double getAdjustMinus() {
			return adjustMinus;
		}
		public void setAdjustMinus(double adjustMinus) {
			this.adjustMinus = adjustMinus;
		}
		public double getAdjustPlus() {
			return adjustPlus;
		}
		public void setAdjustPlus(double adjustPlus) {
			this.adjustPlus = adjustPlus;
		}
		public double getReward() {
			return reward;
		}
		public void setReward(double reward) {
			this.reward = reward;
		}
		@Override
		public String toString() {
			return "EmpSalInfoDaiyInfoTempInfo [uuid=" + uuid + ", id=" + id + ", cmpId=" + cmpId + ", empId=" + empId
					+ ", empCode=" + empCode + ", empType=" + empType + ", contractorId=" + contractorId + ", departId="
					+ departId + ", designationId=" + designationId + ", locationId=" + locationId + ", calcMonth="
					+ calcMonth + ", calcYear=" + calcYear + ", salTypeId=" + salTypeId + ", attSumId=" + attSumId
					+ ", basicCal=" + basicCal + ", performanceBonus=" + performanceBonus + ", otWages=" + otWages
					+ ", miscExpAdd=" + miscExpAdd + ", bonusCal=" + bonusCal + ", exgretiaCal=" + exgretiaCal
					+ ", daArreasCal=" + daArreasCal + ", incrementArreasCal=" + incrementArreasCal + ", epfWages="
					+ epfWages + ", epfWagesEmployer=" + epfWagesEmployer + ", esicWagesCal=" + esicWagesCal
					+ ", grossSalaryDytemp=" + grossSalaryDytemp + ", epsWages=" + epsWages + ", esicWagesDec="
					+ esicWagesDec + ", employeePf=" + employeePf + ", employerEps=" + employerEps + ", employerPf="
					+ employerPf + ", esic=" + esic + ", employerEsic=" + employerEsic + ", esicStatus=" + esicStatus
					+ ", pfStatus=" + pfStatus + ", mlwf=" + mlwf + ", tds=" + tds + ", itded=" + itded + ", fund="
					+ fund + ", totPfAdminCh=" + totPfAdminCh + ", totEdliCh=" + totEdliCh + ", totEdliAdminCh="
					+ totEdliAdminCh + ", ncpDaysDytemp=" + ncpDaysDytemp + ", statusDytemp=" + statusDytemp
					+ ", ptDed=" + ptDed + ", advanceDed=" + advanceDed + ", loanDed=" + loanDed + ", miscExpDed="
					+ miscExpDed + ", miscExpDedDeduct=" + miscExpDedDeduct + ", netSalary=" + netSalary + ", isLocked="
					+ isLocked + ", mlwfApplicableDytemp=" + mlwfApplicableDytemp + ", ptApplicableDytemp="
					+ ptApplicableDytemp + ", payDed=" + payDed + ", commentsForItBonus=" + commentsForItBonus
					+ ", societyContributionDytemp=" + societyContributionDytemp + ", empCategory=" + empCategory
					+ ", basicDefault=" + basicDefault + ", abDeduction=" + abDeduction + ", epfPercentage="
					+ epfPercentage + ", epsEmployeePercentage=" + epsEmployeePercentage + ", productionInsentive="
					+ productionInsentive + ", epfEmployerPercentage=" + epfEmployerPercentage
					+ ", epsEmployerPercentage=" + epsEmployerPercentage + ", presentInsentive=" + presentInsentive
					+ ", nightAllow=" + nightAllow + ", epsDefault=" + epsDefault + ", epmloyerEpfDefault="
					+ epmloyerEpfDefault + ", epmloyerEpfExtra=" + epmloyerEpfExtra + ", pfAdminChPercentage="
					+ pfAdminChPercentage + ", edliPercentage=" + edliPercentage + ", edliAdminPercentage="
					+ edliAdminPercentage + ", employerEsicPercentageSal=" + employerEsicPercentageSal
					+ ", employeeEsicPercentageSal=" + employeeEsicPercentageSal + ", employerMlwf=" + employerMlwf
					+ ", adjustMinus=" + adjustMinus + ", adjustPlus=" + adjustPlus + ", reward=" + reward
					+ ", salaryInfoId=" + salaryInfoId + ", salaryTypeId=" + salaryTypeId + ", basic=" + basic + ", da="
					+ da + ", hra=" + hra + ", spa=" + spa + ", pfApplicable=" + pfApplicable + ", pfType=" + pfType
					+ ", pfEmpPer=" + pfEmpPer + ", pfEmplrPer=" + pfEmplrPer + ", esicApplicable=" + esicApplicable
					+ ", cmpJoiningDate=" + cmpJoiningDate + ", cmpLeavingDate=" + cmpLeavingDate + ", epfJoiningDate="
					+ epfJoiningDate + ", leavingReason=" + leavingReason + ", salBasis=" + salBasis
					+ ", ceilingLimitEmpApplicable=" + ceilingLimitEmpApplicable + ", ceilingLimitEmployerApplicable="
					+ ceilingLimitEmployerApplicable + ", leavingReasonEsic=" + leavingReasonEsic + ", leavingReasonPf="
					+ leavingReasonPf + ", mlwfApplicable=" + mlwfApplicable + ", ptApplicable=" + ptApplicable
					+ ", grossSalary=" + grossSalary + ", societyContribution=" + societyContribution
					+ ", basicCompany=" + basicCompany + ", hraCompany=" + hraCompany + ", daCompany=" + daCompany
					+ ", employeeEsicPercentage=" + employeeEsicPercentage + ", employerEsicPercentage="
					+ employerEsicPercentage + ", delStatus=" + delStatus + ", sumDailyId=" + sumDailyId
					+ ", companyId=" + companyId + ", empName=" + empName + ", month=" + month + ", year=" + year
					+ ", workingDays=" + workingDays + ", presentDays=" + presentDays + ", weeklyOff=" + weeklyOff
					+ ", paidHoliday=" + paidHoliday + ", paidLeave=" + paidLeave + ", legalStrike=" + legalStrike
					+ ", layOff=" + layOff + ", unpaidHoliday=" + unpaidHoliday + ", unpaidLeave=" + unpaidLeave
					+ ", absentDays=" + absentDays + ", payableDays=" + payableDays + ", ncpDays=" + ncpDays
					+ ", totlateMins=" + totlateMins + ", totlateDays=" + totlateDays + ", totoutMins=" + totoutMins
					+ ", totworkingHrs=" + totworkingHrs + ", tototHrs=" + tototHrs + ", totOthr=" + totOthr
					+ ", totLate=" + totLate + ", recStatus=" + recStatus + ", loginName=" + loginName + ", loginTime="
					+ loginTime + ", status=" + status + ", importDate=" + importDate + ", recStatusPaid="
					+ recStatusPaid + ", totalDaysInmonth=" + totalDaysInmonth + ", lateDedLeavePaid="
					+ lateDedLeavePaid + ", holidayPresent=" + holidayPresent + ", weeklyOffPresent=" + weeklyOffPresent
					+ ", fullNight=" + fullNight + ", halfNight=" + halfNight + ", holidayPresentHalf="
					+ holidayPresentHalf + ", weeklyOffPresentHalf=" + weeklyOffPresentHalf + ", weeklyOffHolidayOff="
					+ weeklyOffHolidayOff + ", weeklyOffHolidayOffPresent=" + weeklyOffHolidayOffPresent
					+ ", weeklyOffHolidayOffPresentHalfday=" + weeklyOffHolidayOffPresentHalfday + ", hdpresentHdleave="
					+ hdpresentHdleave + ", totEarlyGoing=" + totEarlyGoing + ", atsummUid=" + atsummUid
					+ ", calculationDone=" + calculationDone + ", dob=" + dob + ", getAllowanceTempList="
					+ getAllowanceTempList + "]";
		}
		
		

}
