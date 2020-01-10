package com.ats.hreasy.model.Bonus;
 
public class BonusApplicable {

	 
	private int bappNo;

	private int companyId;

	private int bonusId;

	private String bonusFormula;

	private double bonusAppBelowAmount;

	private String bonusSealingLimitApplicable;

	private double bonusSealingLimitAmount;

	private double bonusPercentage;

	private String exgretiaFormula;

	private double exgretiaPercentage;
	
	private double  dedExgretiaAmtPercentage;


	private int payrollMonth;

	private int payrollYear;

	private String isPayrollFinalized;

	private String isBonussheetFinalized;

	private String isExgretiaFinalized;

	private int bonusItSub;

	private int exgretiaItSub;

	private int loginIdBonus;

	private int loginIdExgretia;

	private String loginTimeBonus;

	private String loginTimeExgretia;

	private String bonusRemark;

	private String exgretiaRemark;

	public int getBappNo() {
		return bappNo;
	}

	public void setBappNo(int bappNo) {
		this.bappNo = bappNo;
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

	public String getBonusFormula() {
		return bonusFormula;
	}

	public void setBonusFormula(String bonusFormula) {
		this.bonusFormula = bonusFormula;
	}

	public double getBonusAppBelowAmount() {
		return bonusAppBelowAmount;
	}

	public void setBonusAppBelowAmount(double bonusAppBelowAmount) {
		this.bonusAppBelowAmount = bonusAppBelowAmount;
	}

	public String getBonusSealingLimitApplicable() {
		return bonusSealingLimitApplicable;
	}

	public void setBonusSealingLimitApplicable(String bonusSealingLimitApplicable) {
		this.bonusSealingLimitApplicable = bonusSealingLimitApplicable;
	}

	public double getBonusSealingLimitAmount() {
		return bonusSealingLimitAmount;
	}

	public void setBonusSealingLimitAmount(double bonusSealingLimitAmount) {
		this.bonusSealingLimitAmount = bonusSealingLimitAmount;
	}

	public double getBonusPercentage() {
		return bonusPercentage;
	}

	public void setBonusPercentage(double bonusPercentage) {
		this.bonusPercentage = bonusPercentage;
	}

	public String getExgretiaFormula() {
		return exgretiaFormula;
	}

	public void setExgretiaFormula(String exgretiaFormula) {
		this.exgretiaFormula = exgretiaFormula;
	}

	public double getExgretiaPercentage() {
		return exgretiaPercentage;
	}

	public void setExgretiaPercentage(double exgretiaPercentage) {
		this.exgretiaPercentage = exgretiaPercentage;
	}

	public int getPayrollMonth() {
		return payrollMonth;
	}

	public void setPayrollMonth(int payrollMonth) {
		this.payrollMonth = payrollMonth;
	}

	public int getPayrollYear() {
		return payrollYear;
	}

	public void setPayrollYear(int payrollYear) {
		this.payrollYear = payrollYear;
	}

	public String getIsPayrollFinalized() {
		return isPayrollFinalized;
	}

	public void setIsPayrollFinalized(String isPayrollFinalized) {
		this.isPayrollFinalized = isPayrollFinalized;
	}

	public String getIsBonussheetFinalized() {
		return isBonussheetFinalized;
	}

	public void setIsBonussheetFinalized(String isBonussheetFinalized) {
		this.isBonussheetFinalized = isBonussheetFinalized;
	}

	public String getIsExgretiaFinalized() {
		return isExgretiaFinalized;
	}

	public void setIsExgretiaFinalized(String isExgretiaFinalized) {
		this.isExgretiaFinalized = isExgretiaFinalized;
	}

	public int getBonusItSub() {
		return bonusItSub;
	}

	public void setBonusItSub(int bonusItSub) {
		this.bonusItSub = bonusItSub;
	}

	public int getExgretiaItSub() {
		return exgretiaItSub;
	}

	public void setExgretiaItSub(int exgretiaItSub) {
		this.exgretiaItSub = exgretiaItSub;
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

	public String getBonusRemark() {
		return bonusRemark;
	}

	public void setBonusRemark(String bonusRemark) {
		this.bonusRemark = bonusRemark;
	}

	public String getExgretiaRemark() {
		return exgretiaRemark;
	}

	public void setExgretiaRemark(String exgretiaRemark) {
		this.exgretiaRemark = exgretiaRemark;
	}
	
	
	

	public double getDedExgretiaAmtPercentage() {
		return dedExgretiaAmtPercentage;
	}

	public void setDedExgretiaAmtPercentage(double dedExgretiaAmtPercentage) {
		this.dedExgretiaAmtPercentage = dedExgretiaAmtPercentage;
	}

	@Override
	public String toString() {
		return "BonusApplicable [bappNo=" + bappNo + ", companyId=" + companyId + ", bonusId=" + bonusId
				+ ", bonusFormula=" + bonusFormula + ", bonusAppBelowAmount=" + bonusAppBelowAmount
				+ ", bonusSealingLimitApplicable=" + bonusSealingLimitApplicable + ", bonusSealingLimitAmount="
				+ bonusSealingLimitAmount + ", bonusPercentage=" + bonusPercentage + ", exgretiaFormula="
				+ exgretiaFormula + ", exgretiaPercentage=" + exgretiaPercentage + ", dedExgretiaAmtPercentage="
				+ dedExgretiaAmtPercentage + ", payrollMonth=" + payrollMonth + ", payrollYear=" + payrollYear
				+ ", isPayrollFinalized=" + isPayrollFinalized + ", isBonussheetFinalized=" + isBonussheetFinalized
				+ ", isExgretiaFinalized=" + isExgretiaFinalized + ", bonusItSub=" + bonusItSub + ", exgretiaItSub="
				+ exgretiaItSub + ", loginIdBonus=" + loginIdBonus + ", loginIdExgretia=" + loginIdExgretia
				+ ", loginTimeBonus=" + loginTimeBonus + ", loginTimeExgretia=" + loginTimeExgretia + ", bonusRemark="
				+ bonusRemark + ", exgretiaRemark=" + exgretiaRemark + "]";
	}

	 
	
	

}
