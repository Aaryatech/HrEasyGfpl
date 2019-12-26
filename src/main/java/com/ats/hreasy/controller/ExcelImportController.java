package com.ats.hreasy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.model.Allowances;
import com.ats.hreasy.model.EmpSalAllowance;
import com.ats.hreasy.model.EmpSalaryInfo;
import com.ats.hreasy.model.EmployeeBean;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.EmployeeRelatedTbls;
import com.ats.hreasy.model.TblEmpBankInfo;
import com.ats.hreasy.model.TblEmpInfo;
import com.ats.hreasy.model.TblEmpNominees;

@Controller
@Scope("session")
public class ExcelImportController {

	String curDateTime;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

	@RequestMapping(value = "/excelCustActMap", method = RequestMethod.GET)
	public @ResponseBody List addCustomerActMap(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = null;
		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		List<TblEmpInfo> empListInfo = new ArrayList<TblEmpInfo>();
		List<Allowances> allowanceList = new ArrayList<Allowances>();

		try {
			Allowances[] allowanceArr = Constants.getRestTemplate().getForObject(Constants.url + "/getAllAllowances",
					Allowances[].class);
			allowanceList = new ArrayList<Allowances>(Arrays.asList(allowanceArr));

			FileInputStream file = new FileInputStream(new File("/home/maddy/Documents/imgs/HrEasyDBDocs.xlsx"));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Import Employees
			Row row;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // points to the starting of excel i.e excel first row
				row = (Row) sheet.getRow(i); // sheet number

				String empCode = null;
				if (row.getCell(0) != null)
					empCode = row.getCell(0).getStringCellValue();
				else
					break;

				int empType = 0;
				if (row.getCell(1) != null)
					empType = (int) row.getCell(1).getNumericCellValue();

				int department = 0;
				if (row.getCell(2) != null)
					department = (int) row.getCell(2).getNumericCellValue();

				int designation = 0;
				if (row.getCell(3) != null)
					designation = (int) row.getCell(3).getNumericCellValue();

				int location = 0;
				if (row.getCell(4) != null)
					location = (int) row.getCell(4).getNumericCellValue();

				String surname = null;
				if (row.getCell(5) != null)
					surname = row.getCell(5).getStringCellValue();

				String firstname = null;
				if (row.getCell(6) != null)
					firstname = row.getCell(6).getStringCellValue();

				String middlename = null;
				if (row.getCell(7) != null)
					middlename = row.getCell(7).getStringCellValue();

				String pan = null;
				if (row.getCell(8) != null)
					pan = row.getCell(8).toString();

				String pfno = null;
				if (row.getCell(9) != null)
					pfno = row.getCell(9).toString();

				String esicno = null;
				if (row.getCell(10) != null)
					esicno = row.getCell(10).toString();

				String aadhar = null;
				if (row.getCell(11) != null)
					aadhar = row.getCell(11).toString();

				String uan = null;
				if (row.getCell(12) != null)
					uan = row.getCell(12).toString();

				/************************* Employee Info *******************************/
				String empInfoMiddlename = null;
				if (row.getCell(13) != null)
					empInfoMiddlename = row.getCell(13).getStringCellValue();

				String middlenamerelation = null;
				if (row.getCell(14) != null)
					middlenamerelation = row.getCell(14).getStringCellValue();

				String dob = null;
				if (row.getCell(15) != null)
					dob = row.getCell(15).getStringCellValue();

				String gender = null;
				if (row.getCell(16) != null)
					gender = row.getCell(16).getStringCellValue();

				String maritalStatus = null;
				if (row.getCell(17) != null)
					maritalStatus = row.getCell(17).getStringCellValue();

				String address = null;
				if (row.getCell(18) != null)
					address = row.getCell(18).getStringCellValue();

				String permamnentAddress = null;
				if (row.getCell(19) != null)
					permamnentAddress = row.getCell(19).getStringCellValue();

				String email = null;
				if (row.getCell(65) != null)
					email = row.getCell(65).getStringCellValue();

				String emerName = null;
				if (row.getCell(66) != null)
					emerName = row.getCell(65).getStringCellValue();

				String emerContact1 = null;
				if (row.getCell(67) != null)
					emerContact1 = row.getCell(67).toString();

				String emerContact2 = null;
				if (row.getCell(68) != null)
					emerContact2 = row.getCell(68).toString();

				/************************* Nominees *******************************/
				String name2 = null;
				if (row.getCell(20) != null)
					name2 = row.getCell(20).getStringCellValue();

				String relation2 = null;
				if (row.getCell(21) != null)
					relation2 = row.getCell(21).getStringCellValue();

				String dob2 = null;
				if (row.getCell(22) != null)
					dob2 = row.getCell(22).getStringCellValue();

				String name3 = null;
				if (row.getCell(23) != null)
					name3 = row.getCell(23).getStringCellValue();

				String relation3 = null;
				if (row.getCell(24) != null)
					relation3 = row.getCell(24).getStringCellValue();

				String dob3 = null;
				if (row.getCell(25) != null)
					dob3 = row.getCell(25).getStringCellValue();

				String name4 = null;
				if (row.getCell(26) != null)
					name4 = row.getCell(26).getStringCellValue();

				String relation4 = null;
				if (row.getCell(27) != null)
					relation4 = row.getCell(27).getStringCellValue();

				String dob4 = null;
				if (row.getCell(28) != null)
					dob4 = row.getCell(28).getStringCellValue();

				String name5 = null;
				if (row.getCell(29) != null)
					name5 = row.getCell(29).getStringCellValue();

				String relation5 = null;
				if (row.getCell(30) != null)
					relation5 = row.getCell(30).getStringCellValue();

				String dob5 = null;
				if (row.getCell(31) != null)
					dob5 = row.getCell(31).getStringCellValue();

				String name6 = null;
				if (row.getCell(32) != null)
					name6 = row.getCell(32).getStringCellValue();

				String relation6 = null;
				if (row.getCell(33) != null)
					relation6 = row.getCell(33).getStringCellValue();

				String dob6 = null;
				if (row.getCell(34) != null)
					dob6 = row.getCell(34).getStringCellValue();

				/************************* Employee Bank *******************************/

				long accNo = 0;
				if (row.getCell(35) != null)
					accNo = (long) row.getCell(35).getNumericCellValue();

				int bankId = 0;
				if (row.getCell(36) != null)
					bankId = (int) row.getCell(36).getNumericCellValue();

				/************************* Employee Salary *******************************/
				String cmpLeavDate = null;
				if (row.getCell(37) != null)
					cmpLeavDate = row.getCell(37).getStringCellValue();

				String cmpJoinDate = null;
				if (row.getCell(38) != null)
					cmpLeavDate = row.getCell(38).getStringCellValue();

				String epfJoinDate = null;
				if (row.getCell(39) != null)
					cmpLeavDate = row.getCell(39).getStringCellValue();

				String salBasis = null;
				if (row.getCell(40) != null)
					salBasis = row.getCell(40).getStringCellValue();

				double basic = 0;
				if (row.getCell(55) != null)
					basic = row.getCell(55).getNumericCellValue();

				String pfType = null;
				if (row.getCell(56) != null)
					pfType = row.getCell(56).getStringCellValue();

				double pfEmployeePer = 0;
				if (row.getCell(57) != null)
					pfEmployeePer = row.getCell(57).getNumericCellValue();

				double pfEmployerPer = 0;
				if (row.getCell(58) != null)
					pfEmployerPer = row.getCell(58).getNumericCellValue();

				String esicApplicable = null;
				if (row.getCell(59) != null)
					esicApplicable = row.getCell(59).getStringCellValue();

				String ceilingLimitEmpApplicable = null;
				if (row.getCell(60) != null)
					ceilingLimitEmpApplicable = row.getCell(60).getStringCellValue();

				String ceilingLimitEmployerApplicable = null;
				if (row.getCell(61) != null)
					ceilingLimitEmployerApplicable = row.getCell(61).getStringCellValue();

				String isMlwfApplicable = null;
				if (row.getCell(62) != null)
					isMlwfApplicable = row.getCell(62).getStringCellValue();

				String isPtApplicable = null;
				if (row.getCell(63) != null)
					isPtApplicable = row.getCell(63).getStringCellValue();

				// Employee Allowances
				double dearnessAllwnc = 0;
				if (row.getCell(41) != null)
					dearnessAllwnc = row.getCell(41).getNumericCellValue();

				double cityCopnstnAllwnc = 0;
				if (row.getCell(42) != null)
					cityCopnstnAllwnc = row.getCell(42).getNumericCellValue();

				double entrtainmentAllwnc = 0;
				if (row.getCell(43) != null)
					entrtainmentAllwnc = row.getCell(43).getNumericCellValue();

				double overTimeAllwnc = 0;
				if (row.getCell(44) != null)
					overTimeAllwnc = row.getCell(44).getNumericCellValue();

				double tiffinAllwnc = 0;
				if (row.getCell(45) != null)
					tiffinAllwnc = row.getCell(45).getNumericCellValue();

				double cashAllwnc = 0;
				if (row.getCell(46) != null)
					cashAllwnc = row.getCell(46).getNumericCellValue();

				double projectAllwnc = 0;
				if (row.getCell(47) != null)
					projectAllwnc = row.getCell(47).getNumericCellValue();

				double serventAllwnc = 0;
				if (row.getCell(48) != null)
					serventAllwnc = row.getCell(48).getNumericCellValue();

				double houseRentAllwnc = 0;
				if (row.getCell(49) != null)
					houseRentAllwnc = row.getCell(49).getNumericCellValue();

				double leaveTravelAllwnc = 0;
				if (row.getCell(50) != null)
					leaveTravelAllwnc = row.getCell(50).getNumericCellValue();

				double conveyanceAllwnc = 0;
				if (row.getCell(51) != null)
					conveyanceAllwnc = row.getCell(51).getNumericCellValue();

				double medicalAllwnc = 0;
				if (row.getCell(52) != null)
					medicalAllwnc = row.getCell(52).getNumericCellValue();

				double hostelAllwnc = 0;
				if (row.getCell(54) != null)
					hostelAllwnc = row.getCell(54).getNumericCellValue();

				/***********************************************************************/
				EmployeeMaster emp = new EmployeeMaster();
				emp.setCmpCode(1);
				emp.setEmpCode(empCode);
				emp.setEmpType(empType);
				emp.setDepartId(department);
				emp.setDesignationId(designation);
				emp.setLocationId(location);
				emp.setSurname(surname);
				emp.setFirstName(firstname);
				emp.setMiddleName(middlename);
				emp.setPanCardNo(pan);
				emp.setPfNo(pfno);
				emp.setEsicNo(esicno);
				emp.setAadharNo(aadhar);
				emp.setUan(uan);
				emp.setDelStatus(1);

				MultiValueMap<String, Object> mapEmp = new LinkedMultiValueMap<>();
				mapEmp.add("empCode", empCode);
				EmployeeRelatedTbls checkEmpCode = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmpRelatedInfo", mapEmp, EmployeeRelatedTbls.class);
				System.out.println("checkEmpCode Resp--------" + checkEmpCode);

				if (checkEmpCode != null) {
					emp.setEmpId(checkEmpCode.getEmpId());
				} else {
					emp.setEmpId(0);
				}

				EmployeeMaster empSaveResp = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployee",
						emp, EmployeeMaster.class);
				System.out.println("Emp Resp--------" + empSaveResp);

				/****************************************
				 * Employee Info
				 **********************************************/
				TblEmpInfo empInfo = new TblEmpInfo();
				try {
					empInfo.setEmpId(empSaveResp.getEmpId());
					empInfo.setEmpInfoId(checkEmpCode.getEmpInfoId());
				} catch (Exception e) {
					empInfo.setEmpInfoId(0);
				}

				empInfo.setMiddleName(empInfoMiddlename);
				empInfo.setMiddleNameRelation(middlenamerelation);
				empInfo.setDob(dob);
				empInfo.setGender(gender);
				empInfo.setMaritalStatus(maritalStatus);
				empInfo.setAddress(address);
				empInfo.setPermanentAddress(permamnentAddress);
				empInfo.setEmail(email);
				empInfo.setEmerName(emerName);
				empInfo.setEmerContactNo1(emerContact1);
				empInfo.setEmerContactNo2(emerContact2);
				empInfo.setDelStatus(1);
				TblEmpInfo empInfoSave = Constants.getRestTemplate()
						.postForObject(Constants.url + "/saveEmployeeIdInfo", empInfo, TblEmpInfo.class);
				System.out.println("EmpInfo--------" + empInfoSave);

				/****************************************
				 * Nominee
				 **********************************************/
				TblEmpNominees empNominee = new TblEmpNominees();

				try {
					empNominee.setEmpId(empSaveResp.getEmpId());
					empNominee.setNomineeId(checkEmpCode.getNomineeId());
				} catch (Exception e) {
					empNominee.setNomineeId(0);
				}

				empNominee.setName2(name2);
				empNominee.setRelation2(relation2);
				empNominee.setDob2(dob2);

				empNominee.setName3(name3);
				empNominee.setRelation3(relation3);
				empNominee.setDob3(dob3);

				empNominee.setName4(name4);
				empNominee.setRelation4(relation4);
				empNominee.setDob4(dob4);

				empNominee.setName5(name5);
				empNominee.setRelation5(relation5);
				empNominee.setDob5(dob5);

				empNominee.setName6(name6);
				empNominee.setRelation6(relation6);
				empNominee.setDob6(dob6);
				empNominee.setDelStatus(1);

				TblEmpNominees nominee = Constants.getRestTemplate()
						.postForObject(Constants.url + "/saveEmployeeIdNominee", empNominee, TblEmpNominees.class);

				System.out.println("Emp Nominees-----------" + nominee);

				/****************************************
				 * Employee Bank
				 **********************************************/
				TblEmpBankInfo empBank = new TblEmpBankInfo();

				try {
					empBank.setEmpId(empSaveResp.getEmpId());
					empBank.setBankInfoId(checkEmpCode.getBankInfoId());
				} catch (Exception e) {
					empBank.setBankInfoId(0);
				}

				empBank.setAccNo(String.valueOf(accNo));
				empBank.setBankId(bankId);
				empBank.setDelStatus(1);
				TblEmpBankInfo empBankInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/saveEmployeeIdBank", empBank, TblEmpBankInfo.class);
				System.out.println("Emp Bank-----------" + empBankInfo);

				/****************************************
				 * Employee Salary
				 **********************************************/
				EmpSalaryInfo empSal = new EmpSalaryInfo();

				try {
					empSal.setEmpId(empSaveResp.getEmpId());
					empSal.setSalaryInfoId(checkEmpCode.getSalaryInfoId());
				} catch (Exception e) {
					empSal.setSalaryInfoId(0);
				}
				empSal.setCmpLeavingDate(cmpLeavDate);
				empSal.setCmpJoiningDate(cmpJoinDate);
				empSal.setEpfJoiningDate(epfJoinDate);
				empSal.setSalBasis(salBasis);
				empSal.setBasic(basic);
				empSal.setPfType(pfType);
				empSal.setPfEmpPer(pfEmployeePer);
				empSal.setPfEmplrPer(pfEmployerPer);
				empSal.setEsicApplicable(esicApplicable);
				empSal.setCeilingLimitEmpApplicable(ceilingLimitEmpApplicable);
				empSal.setCeilingLimitEmployerApplicable(ceilingLimitEmployerApplicable);
				empSal.setMlwfApplicable(isMlwfApplicable);
				empSal.setPtApplicable(isPtApplicable);
				empSal.setDelStatus(1);

				EmpSalaryInfo empSalInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/saveEmployeeIdSalary", empSal, EmpSalaryInfo.class);
				System.out.println("Emp SalInfo-----------" + empSalInfo);

				// Salary Allowances
				List<EmpSalAllowance> allowncList = new ArrayList<EmpSalAllowance>();
				EmpSalAllowance empSalAllwance = new EmpSalAllowance();
				int cellAllowanceStrt = 41;
				try {
					

					String empallowanceId = checkEmpCode.getAllowanceId();
					String[] empallowanceIds = empallowanceId.split(",");

					String empSalAllowanceIds = checkEmpCode.getEmpSalAllowanceId();
					System.out.println(empSalAllowanceIds);
					String[] strEmpSalAllowanceIds = empSalAllowanceIds.split(",");
					System.out.println(Arrays.toString(strEmpSalAllowanceIds));

					for (int j = 0; j <= allowanceList.size(); j++) {

						if (empallowanceIds[j] != null) {
							empSalAllwance.setEmpId(empSaveResp.getEmpId());
							empSalAllwance.setEmpSalAllowanceId(Integer.parseInt(strEmpSalAllowanceIds[j]));
							
						} else {
							empSalAllwance.setEmpId(0);
							empSalAllwance.setEmpSalAllowanceId(0);
						}
						empSalAllwance.setAllowanceId(allowanceList.get(j).getAllowanceId());
						if (row.getCell(cellAllowanceStrt) != null) {
							empSalAllwance.setAllowanceValue(row.getCell(cellAllowanceStrt).getNumericCellValue());
						} else {
							empSalAllwance.setAllowanceValue(0.0);
						}

						cellAllowanceStrt++;
						allowncList.add(empSalAllwance);
						System.out.println("EmpSalAllwanceList----------"+allowncList.toString());
					}

				
				} catch (Exception e) {
					// empSellAllwance.setSalaryInfoId(0);
				}
				EmpSalAllowance[] allowance = Constants.getRestTemplate().postForObject(
					Constants.url + "/saveEmpSalAllowanceInfo", allowncList, EmpSalAllowance[].class);
				System.out.println("Allowance--------"+allowance);
			
			} // For Loop End

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
