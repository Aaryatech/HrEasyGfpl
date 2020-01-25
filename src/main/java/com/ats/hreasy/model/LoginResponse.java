package com.ats.hreasy.model;
 

public class LoginResponse {
	
	 
	private int empId ; 
	private String empCode ; 
	private String firstName ; 
	private String middleName; 
	private String surname; 
	private String motherName; 
	private String emailId;  
	private int userId; 
	private String locationIds; 
	private boolean isError;
	
	private String userPwd;  
	private int designType;  
	private String hodDeptIds;

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public boolean getIsError() {
		return isError;
	}

	public void setIsError(boolean isError) {
		this.isError = isError;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public String getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
	}

	public int getDesignType() {
		return designType;
	}

	public void setDesignType(int designType) {
		this.designType = designType;
	}

	public String getHodDeptIds() {
		return hodDeptIds;
	}

	public void setHodDeptIds(String hodDeptIds) {
		this.hodDeptIds = hodDeptIds;
	}

	@Override
	public String toString() {
		return "LoginResponse [empId=" + empId + ", empCode=" + empCode + ", firstName=" + firstName + ", middleName="
				+ middleName + ", surname=" + surname + ", motherName=" + motherName + ", emailId=" + emailId
				+ ", userId=" + userId + ", locationIds=" + locationIds + ", isError=" + isError + ", userPwd="
				+ userPwd + ", designType=" + designType + ", hodDeptIds=" + hodDeptIds + "]";
	}

 
	
	

}
