package com.ats.hreasy.model.report;
 
public class EsiSumaryRep {
	
	private String keyNew;
	private int noEmp;

	private double empContribution;

	private double emperContribution;

	private double totalContribution;

	private int calcMonth;

	private int calcYear;

	public String getKeyNew() {
		return keyNew;
	}

	public void setKeyNew(String keyNew) {
		this.keyNew = keyNew;
	}

	public int getNoEmp() {
		return noEmp;
	}

	public void setNoEmp(int noEmp) {
		this.noEmp = noEmp;
	}

	public double getEmpContribution() {
		return empContribution;
	}

	public void setEmpContribution(double empContribution) {
		this.empContribution = empContribution;
	}

	public double getEmperContribution() {
		return emperContribution;
	}

	public void setEmperContribution(double emperContribution) {
		this.emperContribution = emperContribution;
	}

	public double getTotalContribution() {
		return totalContribution;
	}

	public void setTotalContribution(double totalContribution) {
		this.totalContribution = totalContribution;
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

	@Override
	public String toString() {
		return "EsiSumaryRep [keyNew=" + keyNew + ", noEmp=" + noEmp + ", empContribution=" + empContribution
				+ ", emperContribution=" + emperContribution + ", totalContribution=" + totalContribution
				+ ", calcMonth=" + calcMonth + ", calcYear=" + calcYear + "]";
	}

	

}
