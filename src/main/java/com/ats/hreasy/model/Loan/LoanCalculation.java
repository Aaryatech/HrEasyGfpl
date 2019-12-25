package com.ats.hreasy.model.Loan;

public class LoanCalculation {
	

	private float repayAmt;
	
	private float emiAmt;

	public float getRepayAmt() {
		return repayAmt;
	}

	public void setRepayAmt(float repayAmt) {
		this.repayAmt = repayAmt;
	}

	public float getEmiAmt() {
		return emiAmt;
	}

	public void setEmiAmt(float empAmt) {
		this.emiAmt = empAmt;
	}

	@Override
	public String toString() {
		return "LoanCalculation [repayAmt=" + repayAmt + ", emiAmt=" + emiAmt + "]";
	}
	
	
	
	
	
	
	
	
	

}
