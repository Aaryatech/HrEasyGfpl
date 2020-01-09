package com.ats.hreasy.model.Bonus;

public class BonusMaster {

	private int bonusId;
	private String fyTitle;
	private String fyFromdt;

	private String fyTodt;

	private int isCurrent;

	private String remark;

	private double bonusPercentage;

	private int delStatus;

	private int exInt1;
	private int exInt2;

	private String exVar1;;
	private String exVar2;

	private boolean error;

	private int minDays;

	private double exgratiaPercentage;

	private int bonusAppBelowAmount;

	private int bonusAppBelowApplicable;

	private int bonusSealingLimitApplicable;

	private int bonuSealingLimitAmountPerMonth;

	private int dedBonusPujaAmtPercentage;

	private int dedBonusAdvAmtPercentage;

	private int dedBonusLossAmtPercentage;

	public int getBonusId() {
		return bonusId;
	}

	public void setBonusId(int bonusId) {
		this.bonusId = bonusId;
	}

	public String getFyTitle() {
		return fyTitle;
	}

	public void setFyTitle(String fyTitle) {
		this.fyTitle = fyTitle;
	}

	public String getFyFromdt() {
		return fyFromdt;
	}

	public void setFyFromdt(String fyFromdt) {
		this.fyFromdt = fyFromdt;
	}

	public String getFyTodt() {
		return fyTodt;
	}

	public void setFyTodt(String fyTodt) {
		this.fyTodt = fyTodt;
	}

	public int getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(int isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getBonusPercentage() {
		return bonusPercentage;
	}

	public void setBonusPercentage(double bonusPercentage) {
		this.bonusPercentage = bonusPercentage;
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

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public int getMinDays() {
		return minDays;
	}

	public void setMinDays(int minDays) {
		this.minDays = minDays;
	}

	public double getExgratiaPercentage() {
		return exgratiaPercentage;
	}

	public void setExgratiaPercentage(double exgratiaPercentage) {
		this.exgratiaPercentage = exgratiaPercentage;
	}

	public int getBonusAppBelowAmount() {
		return bonusAppBelowAmount;
	}

	public void setBonusAppBelowAmount(int bonusAppBelowAmount) {
		this.bonusAppBelowAmount = bonusAppBelowAmount;
	}

	public int getBonusAppBelowApplicable() {
		return bonusAppBelowApplicable;
	}

	public void setBonusAppBelowApplicable(int bonusAppBelowApplicable) {
		this.bonusAppBelowApplicable = bonusAppBelowApplicable;
	}

	 

	public int getBonusSealingLimitApplicable() {
		return bonusSealingLimitApplicable;
	}

	public void setBonusSealingLimitApplicable(int bonusSealingLimitApplicable) {
		this.bonusSealingLimitApplicable = bonusSealingLimitApplicable;
	}

	public int getBonuSealingLimitAmountPerMonth() {
		return bonuSealingLimitAmountPerMonth;
	}

	public void setBonuSealingLimitAmountPerMonth(int bonuSealingLimitAmountPerMonth) {
		this.bonuSealingLimitAmountPerMonth = bonuSealingLimitAmountPerMonth;
	}

	public int getDedBonusPujaAmtPercentage() {
		return dedBonusPujaAmtPercentage;
	}

	public void setDedBonusPujaAmtPercentage(int dedBonusPujaAmtPercentage) {
		this.dedBonusPujaAmtPercentage = dedBonusPujaAmtPercentage;
	}

	public int getDedBonusAdvAmtPercentage() {
		return dedBonusAdvAmtPercentage;
	}

	public void setDedBonusAdvAmtPercentage(int dedBonusAdvAmtPercentage) {
		this.dedBonusAdvAmtPercentage = dedBonusAdvAmtPercentage;
	}

	public int getDedBonusLossAmtPercentage() {
		return dedBonusLossAmtPercentage;
	}

	public void setDedBonusLossAmtPercentage(int dedBonusLossAmtPercentage) {
		this.dedBonusLossAmtPercentage = dedBonusLossAmtPercentage;
	}

	@Override
	public String toString() {
		return "BonusMaster [bonusId=" + bonusId + ", fyTitle=" + fyTitle + ", fyFromdt=" + fyFromdt + ", fyTodt="
				+ fyTodt + ", isCurrent=" + isCurrent + ", remark=" + remark + ", bonusPercentage=" + bonusPercentage
				+ ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", error=" + error + ", minDays=" + minDays + ", exgratiaPercentage="
				+ exgratiaPercentage + ", bonusAppBelowAmount=" + bonusAppBelowAmount + ", bonusAppBelowApplicable="
				+ bonusAppBelowApplicable + ", bonusAealingLimitApplicable=" + bonusSealingLimitApplicable
				+ ", bonuSealingLimitAmountPerMonth=" + bonuSealingLimitAmountPerMonth + ", dedBonusPujaAmtPercentage="
				+ dedBonusPujaAmtPercentage + ", dedBonusAdvAmtPercentage=" + dedBonusAdvAmtPercentage
				+ ", dedBonusLossAmtPercentage=" + dedBonusLossAmtPercentage + "]";
	}

}
