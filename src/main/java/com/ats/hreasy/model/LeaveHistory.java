package com.ats.hreasy.model;


public class LeaveHistory {
	
	
	 private int lvTypeId;
		
		private int lvsId;
		
		private String lvTitleShort;
		
		private String lvTitle;
		
		private float lvsAllotedLeaves;
		
		private float balLeave;
		
		private float aplliedLeaeve;
		
		private float sactionLeave;
		
		private int isFile;
		
		private int maxAccumulateCarryforward;
		
		private int isCarryforward;
		
		private int maxCarryforward;

		private String lvsName;
		
		private int isInCash;
		
		public int getLvsId() {
			return lvsId;
		}

		public void setLvsId(int lvsId) {
			this.lvsId = lvsId;
		}

		public String getLvTitle() {
			return lvTitle;
		}

		public void setLvTitle(String lvTitle) {
			this.lvTitle = lvTitle;
		}

		public int getLvTypeId() {
			return lvTypeId;
		}

		public void setLvTypeId(int lvTypeId) {
			this.lvTypeId = lvTypeId;
		}

		public String getLvTitleShort() {
			return lvTitleShort;
		}

		public void setLvTitleShort(String lvTitleShort) {
			this.lvTitleShort = lvTitleShort;
		}

		public float getLvsAllotedLeaves() {
			return lvsAllotedLeaves;
		}

		public void setLvsAllotedLeaves(float lvsAllotedLeaves) {
			this.lvsAllotedLeaves = lvsAllotedLeaves;
		}

		public float getBalLeave() {
			return balLeave;
		}

		public void setBalLeave(float balLeave) {
			this.balLeave = balLeave;
		}

		public float getAplliedLeaeve() {
			return aplliedLeaeve;
		}

		public void setAplliedLeaeve(float aplliedLeaeve) {
			this.aplliedLeaeve = aplliedLeaeve;
		}

		public float getSactionLeave() {
			return sactionLeave;
		}

		public void setSactionLeave(float sactionLeave) {
			this.sactionLeave = sactionLeave;
		}

		public int getIsFile() {
			return isFile;
		}

		public void setIsFile(int isFile) {
			this.isFile = isFile;
		}

		public int getMaxAccumulateCarryforward() {
			return maxAccumulateCarryforward;
		}

		public void setMaxAccumulateCarryforward(int maxAccumulateCarryforward) {
			this.maxAccumulateCarryforward = maxAccumulateCarryforward;
		}

		public int getIsCarryforward() {
			return isCarryforward;
		}

		public void setIsCarryforward(int isCarryforward) {
			this.isCarryforward = isCarryforward;
		}

		public int getMaxCarryforward() {
			return maxCarryforward;
		}

		public void setMaxCarryforward(int maxCarryforward) {
			this.maxCarryforward = maxCarryforward;
		}

		public String getLvsName() {
			return lvsName;
		}

		public void setLvsName(String lvsName) {
			this.lvsName = lvsName;
		}

		public int getIsInCash() {
			return isInCash;
		}

		public void setIsInCash(int isInCash) {
			this.isInCash = isInCash;
		}

		@Override
		public String toString() {
			return "LeaveHistory [lvTypeId=" + lvTypeId + ", lvsId=" + lvsId + ", lvTitleShort=" + lvTitleShort
					+ ", lvTitle=" + lvTitle + ", lvsAllotedLeaves=" + lvsAllotedLeaves + ", balLeave=" + balLeave
					+ ", aplliedLeaeve=" + aplliedLeaeve + ", sactionLeave=" + sactionLeave + ", isFile=" + isFile
					+ ", maxAccumulateCarryforward=" + maxAccumulateCarryforward + ", isCarryforward=" + isCarryforward
					+ ", maxCarryforward=" + maxCarryforward + ", lvsName=" + lvsName + ", isInCash=" + isInCash + "]";
		}

		
		
		


	}
