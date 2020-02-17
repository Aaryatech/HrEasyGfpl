package com.ats.hreasy.model.report;

 
public class GetSalaryCalcReport {
 
	private int id;
	private int cmpId;
	private int empId;
	private String empCode;

	private int calcMonth;
	private int calcYear;

	private double epfWages;
	private double epfWagesEmployer;
	private double esicWagesCal;
	private double grossSalary;
	private double epsWages;
	private double esicWagesDec;
	private double employeePf;
	private double employerEps;
	private double employerPf;
	private double esic;
	private double employerEsic;
	private int esicStatus;
	private int pfStatus;

	private double epfPercentage;
	private double epsEmployeePercentage;
	private double epfEmployerPercentage;
	private double epsEmployerPercentage;

	private String empName;

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

	public double getEmployeePf() {
		return employeePf;
	}

	public void setEmployeePf(double employeePf) {
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

	public double getEsic() {
		return esic;
	}

	public void setEsic(double esic) {
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

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Override
	public String toString() {
		return "GetSalaryCalcReport [id=" + id + ", cmpId=" + cmpId + ", empId=" + empId + ", empCode=" + empCode
				+ ", calcMonth=" + calcMonth + ", calcYear=" + calcYear + ", epfWages=" + epfWages
				+ ", epfWagesEmployer=" + epfWagesEmployer + ", esicWagesCal=" + esicWagesCal + ", grossSalary="
				+ grossSalary + ", epsWages=" + epsWages + ", esicWagesDec=" + esicWagesDec + ", employeePf="
				+ employeePf + ", employerEps=" + employerEps + ", employerPf=" + employerPf + ", esic=" + esic
				+ ", employerEsic=" + employerEsic + ", esicStatus=" + esicStatus + ", pfStatus=" + pfStatus
				+ ", epfPercentage=" + epfPercentage + ", epsEmployeePercentage=" + epsEmployeePercentage
				+ ", epfEmployerPercentage=" + epfEmployerPercentage + ", epsEmployerPercentage="
				+ epsEmployerPercentage + ", empName=" + empName + "]";
	}

}
