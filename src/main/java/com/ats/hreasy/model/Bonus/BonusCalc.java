package com.ats.hreasy.model.Bonus;

 

public class BonusCalc {

	private int bonusCalcId;

	private int companyId;

	private int bonusId;

 
	private int empId;

	private String companyEmpCode;

	//private String pfNo;

	private String location;

	private String empName;

	private int currAge;

	private String currDesignation;

	private String bonusDetails;
	private int totalBonusDays ;
	private int totalBonusWages;

	private String bonusApplicable;

	private double grossBonusAmt;

	private double dedBonusPujaAmt;

	private double dedBonusAdvAmt;

	private double dedBonusLossAmt;

	private double netBonusAmt;

	private double paidBonusAmt;

	private String paidBonusDate;

	private String isBonussheetFinalized;

	private String exgretiaDetails;

	private int totalExgretiaDays;

	private String totalExgretiaWages;

	private String exgretiaApplicable;

	private double grossExgretiaAmt ;

	private double dedExgretiaAmt;

	private String dedReason;

	private double netExgretiaAmt;
	
	private double exgratiaPrcnt;

	private double paidExgretiaAmt;

	private String paidExgretiaDate;

	private String isExgretiaFinalized;

	private int recStatus;

	private int loginIdBonus;

	private int loginIdExgretia;

	private String loginTimeBonus;

	private String loginTimeExgretia;

	private int delStatus;

	private int exInt1;
	private int exInt2;

	private String exVar1;;
	private String exVar2;

	public double getExgratiaPrcnt() {
		return exgratiaPrcnt;
	}

	public void setExgratiaPrcnt(double exgratiaPrcnt) {
		this.exgratiaPrcnt = exgratiaPrcnt;
	}

	public int getBonusCalcId() {
		return bonusCalcId;
	}

	public void setBonusCalcId(int bonusCalcId) {
		this.bonusCalcId = bonusCalcId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getBonusId() {
		return bonusId;
	}

	public void setBonusId(int bonusId) {
		this.bonusId = bonusId;
	}
 

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getCompanyEmpCode() {
		return companyEmpCode;
	}

	public void setCompanyEmpCode(String companyEmpCode) {
		this.companyEmpCode = companyEmpCode;
	}

	/*
	 * public String getPfNo() { return pfNo; }
	 * 
	 * public void setPfNo(String pfNo) { this.pfNo = pfNo; }
	 */
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getCurrAge() {
		return currAge;
	}

	public void setCurrAge(int currAge) {
		this.currAge = currAge;
	}

	public String getCurrDesignation() {
		return currDesignation;
	}

	public void setCurrDesignation(String currDesignation) {
		this.currDesignation = currDesignation;
	}

	public String getBonusDetails() {
		return bonusDetails;
	}

	public void setBonusDetails(String bonusDetails) {
		this.bonusDetails = bonusDetails;
	}

	public int getTotalBonusWages() {
		return totalBonusWages;
	}

	public void setTotalBonusWages(int totalBonusWages) {
		this.totalBonusWages = totalBonusWages;
	}

	public String getBonusApplicable() {
		return bonusApplicable;
	}

	public void setBonusApplicable(String bonusApplicable) {
		this.bonusApplicable = bonusApplicable;
	}

	public double getGrossBonusAmt() {
		return grossBonusAmt;
	}

	public void setGrossBonusAmt(double grossBonusAmt) {
		this.grossBonusAmt = grossBonusAmt;
	}

	public double getDedBonusPujaAmt() {
		return dedBonusPujaAmt;
	}

	public void setDedBonusPujaAmt(double dedBonusPujaAmt) {
		this.dedBonusPujaAmt = dedBonusPujaAmt;
	}

	public double getDedBonusAdvAmt() {
		return dedBonusAdvAmt;
	}

	public void setDedBonusAdvAmt(double dedBonusAdvAmt) {
		this.dedBonusAdvAmt = dedBonusAdvAmt;
	}

	public double getDedBonusLossAmt() {
		return dedBonusLossAmt;
	}

	public void setDedBonusLossAmt(double dedBonusLossAmt) {
		this.dedBonusLossAmt = dedBonusLossAmt;
	}

	public double getNetBonusAmt() {
		return netBonusAmt;
	}

	public void setNetBonusAmt(double netBonusAmt) {
		this.netBonusAmt = netBonusAmt;
	}

	public double getPaidBonusAmt() {
		return paidBonusAmt;
	}

	public void setPaidBonusAmt(double paidBonusAmt) {
		this.paidBonusAmt = paidBonusAmt;
	}

 
	public String getPaidBonusDate() {
		return paidBonusDate;
	}

	public void setPaidBonusDate(String paidBonusDate) {
		this.paidBonusDate = paidBonusDate;
	}

	public String getIsBonussheetFinalized() {
		return isBonussheetFinalized;
	}

	public void setIsBonussheetFinalized(String isBonussheetFinalized) {
		this.isBonussheetFinalized = isBonussheetFinalized;
	}

	public String getExgretiaDetails() {
		return exgretiaDetails;
	}

	public void setExgretiaDetails(String exgretiaDetails) {
		this.exgretiaDetails = exgretiaDetails;
	}

	public int getTotalExgretiaDays() {
		return totalExgretiaDays;
	}

	public void setTotalExgretiaDays(int totalExgretiaDays) {
		this.totalExgretiaDays = totalExgretiaDays;
	}

	public String getTotalExgretiaWages() {
		return totalExgretiaWages;
	}

	public void setTotalExgretiaWages(String totalExgretiaWages) {
		this.totalExgretiaWages = totalExgretiaWages;
	}

	public String getExgretiaApplicable() {
		return exgretiaApplicable;
	}

	public void setExgretiaApplicable(String exgretiaApplicable) {
		this.exgretiaApplicable = exgretiaApplicable;
	}

	 

	public double getGrossExgretiaAmt() {
		return grossExgretiaAmt;
	}

	public void setGrossExgretiaAmt(double grossExgretiaAmt) {
		this.grossExgretiaAmt = grossExgretiaAmt;
	}

	public double getDedExgretiaAmt() {
		return dedExgretiaAmt;
	}

	public void setDedExgretiaAmt(double dedExgretiaAmt) {
		this.dedExgretiaAmt = dedExgretiaAmt;
	}

	public String getDedReason() {
		return dedReason;
	}

	public void setDedReason(String dedReason) {
		this.dedReason = dedReason;
	}

	public double getNetExgretiaAmt() {
		return netExgretiaAmt;
	}

	public void setNetExgretiaAmt(double netExgretiaAmt) {
		this.netExgretiaAmt = netExgretiaAmt;
	}

	public double getPaidExgretiaAmt() {
		return paidExgretiaAmt;
	}

	public void setPaidExgretiaAmt(double paidExgretiaAmt) {
		this.paidExgretiaAmt = paidExgretiaAmt;
	}

 
	public String getPaidExgretiaDate() {
		return paidExgretiaDate;
	}

	public void setPaidExgretiaDate(String paidExgretiaDate) {
		this.paidExgretiaDate = paidExgretiaDate;
	}

	public String getIsExgretiaFinalized() {
		return isExgretiaFinalized;
	}

	public void setIsExgretiaFinalized(String isExgretiaFinalized) {
		this.isExgretiaFinalized = isExgretiaFinalized;
	}

	public int getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(int recStatus) {
		this.recStatus = recStatus;
	}

	public int getLoginIdBonus() {
		return loginIdBonus;
	}

	public void setLoginIdBonus(int loginIdBonus) {
		this.loginIdBonus = loginIdBonus;
	}

	public int getLoginIdExgretia() {
		return loginIdExgretia;
	}

	public void setLoginIdExgretia(int loginIdExgretia) {
		this.loginIdExgretia = loginIdExgretia;
	}

	public String getLoginTimeBonus() {
		return loginTimeBonus;
	}

	public void setLoginTimeBonus(String loginTimeBonus) {
		this.loginTimeBonus = loginTimeBonus;
	}

	public String getLoginTimeExgretia() {
		return loginTimeExgretia;
	}

	public void setLoginTimeExgretia(String loginTimeExgretia) {
		this.loginTimeExgretia = loginTimeExgretia;
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
	
	

	public int getTotalBonusDays() {
		return totalBonusDays;
	}

	public void setTotalBonusDays(int totalBonusDays) {
		this.totalBonusDays = totalBonusDays;
	}

	@Override
	public String toString() {
		return "BonusCalc [bonusCalcId=" + bonusCalcId + ", companyId=" + companyId + ", bonusId=" + bonusId
				+ ", empId=" + empId + ", companyEmpCode=" + companyEmpCode + ", location=" + location + ", empName="
				+ empName + ", currAge=" + currAge + ", currDesignation=" + currDesignation + ", bonusDetails="
				+ bonusDetails + ", totalBonusDays=" + totalBonusDays + ", totalBonusWages=" + totalBonusWages
				+ ", bonusApplicable=" + bonusApplicable + ", grossBonusAmt=" + grossBonusAmt + ", dedBonusPujaAmt="
				+ dedBonusPujaAmt + ", dedBonusAdvAmt=" + dedBonusAdvAmt + ", dedBonusLossAmt=" + dedBonusLossAmt
				+ ", netBonusAmt=" + netBonusAmt + ", paidBonusAmt=" + paidBonusAmt + ", paidBonusDate=" + paidBonusDate
				+ ", isBonussheetFinalized=" + isBonussheetFinalized + ", exgretiaDetails=" + exgretiaDetails
				+ ", totalExgretiaDays=" + totalExgretiaDays + ", totalExgretiaWages=" + totalExgretiaWages
				+ ", exgretiaApplicable=" + exgretiaApplicable + ", grossExgretiaAmt=" + grossExgretiaAmt
				+ ", dedExgretiaAmt=" + dedExgretiaAmt + ", dedReason=" + dedReason + ", netExgretiaAmt="
				+ netExgretiaAmt + ", exgratiaPrcnt=" + exgratiaPrcnt + ", paidExgretiaAmt=" + paidExgretiaAmt
				+ ", paidExgretiaDate=" + paidExgretiaDate + ", isExgretiaFinalized=" + isExgretiaFinalized
				+ ", recStatus=" + recStatus + ", loginIdBonus=" + loginIdBonus + ", loginIdExgretia=" + loginIdExgretia
				+ ", loginTimeBonus=" + loginTimeBonus + ", loginTimeExgretia=" + loginTimeExgretia + ", delStatus="
				+ delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ "]";
	}

	 
	 

}
