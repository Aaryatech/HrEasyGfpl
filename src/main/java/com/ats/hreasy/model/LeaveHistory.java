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

		@Override
		public String toString() {
			return "LeaveHistory [lvTypeId=" + lvTypeId + ", lvTitleShort=" + lvTitleShort + ", lvTitle=" + lvTitle
					+ ", lvsAllotedLeaves=" + lvsAllotedLeaves + ", balLeave=" + balLeave + ", aplliedLeaeve="
					+ aplliedLeaeve + ", sactionLeave=" + sactionLeave + ", getLvTitle()=" + getLvTitle()
					+ ", getLvTypeId()=" + getLvTypeId() + ", getLvTitleShort()=" + getLvTitleShort()
					+ ", getLvsAllotedLeaves()=" + getLvsAllotedLeaves() + ", getBalLeave()=" + getBalLeave()
					+ ", getAplliedLeaeve()=" + getAplliedLeaeve() + ", getSactionLeave()=" + getSactionLeave()
					+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
					+ "]";
		}

		
		
		


	}
