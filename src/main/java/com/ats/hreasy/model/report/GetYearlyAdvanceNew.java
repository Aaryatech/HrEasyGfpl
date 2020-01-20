package com.ats.hreasy.model.report;

public class GetYearlyAdvanceNew {

	private int empId;

	private String empName;

	private String empCode;

	private String janCount;
	private String febCount;
	private String marchCount;
	private String aprCount;
	private String mayCount;
	private String junCount;
	private String julCount;
	private String augCount;
	private String sepCount;
	private String octCount;
	private String novCount;
	private String decCount;
	
	private double total;


	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getJanCount() {
		return janCount;
	}

	public void setJanCount(String janCount) {
		this.janCount = janCount;
	}

	public String getFebCount() {
		return febCount;
	}

	public void setFebCount(String febCount) {
		this.febCount = febCount;
	}

	public String getMarchCount() {
		return marchCount;
	}

	public void setMarchCount(String marchCount) {
		this.marchCount = marchCount;
	}

	public String getAprCount() {
		return aprCount;
	}

	public void setAprCount(String aprCount) {
		this.aprCount = aprCount;
	}

	public String getMayCount() {
		return mayCount;
	}

	public void setMayCount(String mayCount) {
		this.mayCount = mayCount;
	}

	public String getJunCount() {
		return junCount;
	}

	public void setJunCount(String junCount) {
		this.junCount = junCount;
	}

	public String getJulCount() {
		return julCount;
	}

	public void setJulCount(String julCount) {
		this.julCount = julCount;
	}

	public String getAugCount() {
		return augCount;
	}

	public void setAugCount(String augCount) {
		this.augCount = augCount;
	}

	public String getSepCount() {
		return sepCount;
	}

	public void setSepCount(String sepCount) {
		this.sepCount = sepCount;
	}

	public String getOctCount() {
		return octCount;
	}

	public void setOctCount(String octCount) {
		this.octCount = octCount;
	}

	public String getNovCount() {
		return novCount;
	}

	public void setNovCount(String novCount) {
		this.novCount = novCount;
	}

	public String getDecCount() {
		return decCount;
	}

	public void setDecCount(String decCount) {
		this.decCount = decCount;
	}

	@Override
	public String toString() {
		return "GetYearlyAdvanceNew [empId=" + empId + ", empName=" + empName + ", empCode=" + empCode + ", janCount="
				+ janCount + ", febCount=" + febCount + ", marchCount=" + marchCount + ", aprCount=" + aprCount
				+ ", mayCount=" + mayCount + ", junCount=" + junCount + ", julCount=" + julCount + ", augCount="
				+ augCount + ", sepCount=" + sepCount + ", octCount=" + octCount + ", novCount=" + novCount
				+ ", decCount=" + decCount + ", total=" + total + "]";
	}

 
}
