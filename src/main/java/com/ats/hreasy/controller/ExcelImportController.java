package com.ats.hreasy.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.RandomString;
import com.ats.hreasy.common.VpsImageUpload;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Allowances;
import com.ats.hreasy.model.DataForUpdateAttendance;
import com.ats.hreasy.model.EmpSalAllowance;
import com.ats.hreasy.model.EmpSalaryInfo;
import com.ats.hreasy.model.EmployeeBean;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.EmployeeRelatedTbls;
import com.ats.hreasy.model.FileUploadedData;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.TblEmpBankInfo;
import com.ats.hreasy.model.TblEmpInfo;
import com.ats.hreasy.model.TblEmpNominees;
import com.ats.hreasy.model.User;
import com.itextpdf.text.pdf.codec.Base64.InputStream;

@Controller
@Scope("session")
public class ExcelImportController {

	String curDateTime;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

	@RequestMapping(value = "/showEmpFileUpload", method = RequestMethod.GET)
	public String shoeEmpFileUpload(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String mav = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showEmpFileUpload", "showEmpFileUpload", 1, 0, 0, 0, newModuleList);
		if (view.isError() == true) {

			mav = "accessDenied";

		} else {

			try {

				mav = "fileUpload/empFileUpload";

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;
	}

	@RequestMapping(value = "/empDetailUploadCSV", method = RequestMethod.POST)
	public String empDetailUploadCSV(@RequestParam("fileNew") List<MultipartFile> fileNew, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();

		try {
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date date = new Date();
			VpsImageUpload upload = new VpsImageUpload();
			String imageName = new String();
			imageName = dateTimeInGMT.format(date) + "_" + fileNew.get(0).getOriginalFilename();

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			upload.saveUploadedFiles(fileNew.get(0), Constants.docSaveUrl, imageName);

			String fileIn = Constants.docSaveUrl + imageName;

			MultiValueMap<String, Object> map = null;

			List<Allowances> allowanceList = new ArrayList<Allowances>();
			System.err.println("-------" + imageName);

			// temp.xls2020-02-27_19:30:03_abc.xls
			FileInputStream file = new FileInputStream(new File(Constants.docSaveUrl + imageName));

			// Create Workbook instance holding reference to .xlsx file
			HSSFWorkbook workbook = new HSSFWorkbook(file);

			HSSFSheet sheet = workbook.getSheetAt(0);

			// Import Employees
			System.err.println(sheet.getLastRowNum());

			Allowances[] allowanceArr = Constants.getRestTemplate().getForObject(Constants.url + "/getAllAllowances",
					Allowances[].class);
			allowanceList = new ArrayList<Allowances>(Arrays.asList(allowanceArr));

			/*
			 * int res = Constants.getRestTemplate().getForObject(Constants.url +
			 * "/getMaxEmp", Integer.class);
			 * 
			 * 
			 * 
			 */

			/*
			 * Row rowhead = (Row) sheet.getRow(0);
			 * 
			 * int hraId=0; int daId=0; int eduId=0; int tifffAllId=0; int travelAllId=0;
			 * int conAllId=0; int mobAllId=0; int othallId=0;
			 * 
			 * 
			 * 
			 * for(int k=0;k<allowanceList.size();k++) {
			 * 
			 * //23
			 * 
			 * if(rowhead.getCell(23).getStringCellValue().equals(allowanceList.get(k).
			 * getName())) { daId=allowanceList.get(k).getAllowanceId(); }else
			 * if(rowhead.getCell(24).getStringCellValue().equals(allowanceList.get(k).
			 * getName())) { hraId=allowanceList.get(k).getAllowanceId(); }else
			 * if(rowhead.getCell(25).getStringCellValue().equals(allowanceList.get(k).
			 * getName())) { eduId=allowanceList.get(k).getAllowanceId(); }else
			 * if(rowhead.getCell(26).getStringCellValue().equals(allowanceList.get(k).
			 * getName())) { tifffAllId=allowanceList.get(k).getAllowanceId(); }else
			 * if(rowhead.getCell(27).getStringCellValue().equals(allowanceList.get(k).
			 * getName())) { travelAllId=allowanceList.get(k).getAllowanceId(); }else
			 * if(rowhead.getCell(28).getStringCellValue().equals(allowanceList.get(k).
			 * getName())) { conAllId=allowanceList.get(k).getAllowanceId(); }else
			 * if(rowhead.getCell(29).getStringCellValue().equals(allowanceList.get(k).
			 * getName())) { mobAllId=allowanceList.get(k).getAllowanceId(); }else
			 * if(rowhead.getCell(30).getStringCellValue().equals(allowanceList.get(k).
			 * getName())) { othallId=allowanceList.get(k).getAllowanceId(); }
			 * 
			 * }
			 */

			Row row;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				// points to the starting of excel i.e excel first row
				row = (Row) sheet.getRow(i); // sheet number

				/* ********m_employees ****************************/
				int empCode = 0;
				if (row.getCell(0) != null)
					empCode = (int) row.getCell(0).getNumericCellValue();
				else
					break;

				System.err.println("empCode" + empCode);
				if (empCode != 0) {
					System.err.println("entry " + i);

					MultiValueMap<String, Object> mapEmp = new LinkedMultiValueMap<>();
					mapEmp.add("empCode", empCode);
					EmployeeRelatedTbls checkEmpCode = Constants.getRestTemplate()
							.postForObject(Constants.url + "/getEmpRelatedInfo", mapEmp, EmployeeRelatedTbls.class);
					System.out.println("checkEmpCode Resp--------" + checkEmpCode);

					String surname = null;
					if (row.getCell(3) != null)
						surname = row.getCell(3).getStringCellValue();

					String firstname = null;
					if (row.getCell(1) != null)
						firstname = row.getCell(1).getStringCellValue();

					String middlename = null;
					if (row.getCell(2) != null)
						middlename = row.getCell(2).getStringCellValue();

					String pan = null;
					if (row.getCell(4) != null)
						pan = row.getCell(4).toString();

					String pfno = null;
					if (row.getCell(5) != null)
						pfno = row.getCell(5).toString();

					String esicno = null;
					if (row.getCell(6) != null)
						esicno = row.getCell(6).toString();

					long aadhar = 0;
					if (row.getCell(7) != null)
						aadhar = (long) row.getCell(7).getNumericCellValue();

					long uan = 0;
					if (row.getCell(8) != null)
						uan = (long) row.getCell(8).getNumericCellValue();

					EmployeeMaster emp = new EmployeeMaster();
					emp.setCmpCode(1);
					emp.setEmpCode(String.valueOf(empCode));
					emp.setEmpType(0);
					emp.setDepartId(0);
					emp.setDesignationId(0);
					emp.setLocationId(0);
					emp.setSurname(surname);
					emp.setFirstName(firstname);
					emp.setMiddleName(middlename);
					emp.setPanCardNo(pan);
					emp.setPfNo(pfno);
					emp.setEsicNo(esicno);
					emp.setAadharNo(String.valueOf(aadhar));
					emp.setUan(String.valueOf(uan));
					emp.setDelStatus(1);
					emp.setSubCmpId(0);
					emp.setMobileNo1("0");
					emp.setLeavingReason("NA");
					emp.setEmailId(null);
					emp.setGrossSalaryEst(0);
					emp.setIsEmp(1);
					emp.setNextShiftid(0);
					emp.setCurrentShiftid(0);
					emp.setNewBasicRate(0);
					emp.setNewDaRate(0);
					emp.setNewHraRate(0);
					emp.setSalDedAtFullandfinal(0);
					emp.setAddedFrom(0);
					emp.setNoticePayAmount(0);
					emp.setAddedBySupervisorId(0);
					emp.setLoginName(String.valueOf(userObj.getUserId()));
					emp.setLoginTime(dateTimeInGMT.format(date));
					emp.setPlCalcBase(0);
					emp.setRawData("NA");
					emp.setExVar1("0");
					emp.setExVar2("0");


					if (checkEmpCode != null) {
						emp.setEmpId(checkEmpCode.getEmpId());

					} else {
						emp.setEmpId(0);
					}

					EmployeeMaster empSaveResp = Constants.getRestTemplate()
							.postForObject(Constants.url + "/saveEmployee", emp, EmployeeMaster.class);

					/************************* Employee User Info *******************************/
					RandomString randomString = new RandomString();
					String password = "1947";
					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] messageDigest = md.digest(password.getBytes());
					BigInteger number = new BigInteger(1, messageDigest);
					String hashtext = number.toString(16);
					User uinfo = new User();
					uinfo.setEmpId(empSaveResp.getEmpId());
					uinfo.setEmpTypeId(empSaveResp.getEmpType());
					uinfo.setUserName(empSaveResp.getEmpCode());
					uinfo.setUserPwd(hashtext);
					uinfo.setLocId("0");
					uinfo.setExInt1(1);
					uinfo.setExInt2(1);
					uinfo.setExInt3(1);
					uinfo.setExVar1("NA");
					uinfo.setExVar2("NA");
					uinfo.setExVar3("NA");
					uinfo.setIsActive(1);
					uinfo.setDelStatus(1);
					uinfo.setMakerUserId(userObj.getEmpId());
					uinfo.setMakerEnterDatetime(sf.format(date));

					if (checkEmpCode != null) {
						uinfo.setUser_id(checkEmpCode.getUserId());

					} else {
						uinfo.setUser_id(0);
					}

					// System.out.println(locIdList + "" + uinfo.getLocId());
					User res1 = Constants.getRestTemplate().postForObject(Constants.url + "/saveUserInfo", uinfo,
							User.class);

					/************************* Employee othet Info *******************************/
					String empInfoMiddlename = null;
					if (row.getCell(9) != null)
						empInfoMiddlename = row.getCell(9).getStringCellValue();

					String middlenamerelation = null;
					if (row.getCell(10) != null)
						middlenamerelation = row.getCell(10).getStringCellValue();

					String dob = null;
					if (row.getCell(11) != null)
						dob = row.getCell(11).getStringCellValue();

					String gender = null;
					if (row.getCell(12) != null)
						gender = row.getCell(12).getStringCellValue();

					String address = null;
					if (row.getCell(13) != null)
						address = row.getCell(13).getStringCellValue();

					String permamnentAddress = null;
					if (row.getCell(14) != null)
						permamnentAddress = row.getCell(14).getStringCellValue();

					String email = null;
					if (row.getCell(35) != null)
						email = row.getCell(35).getStringCellValue();

					String emerNam = null;
					if (row.getCell(36) != null)
						emerNam = row.getCell(36).getStringCellValue();
					String emerCon = null;

					DataFormatter formatter = new DataFormatter();

					if (row.getCell(37) != null)
						emerCon = formatter.formatCellValue(row.getCell(37));
					System.err.println("--" + emerCon);

					TblEmpInfo empInfo = new TblEmpInfo();
					empInfo.setEmpId(empSaveResp.getEmpId());
					empInfo.setMiddleName(empInfoMiddlename);
					empInfo.setMiddleNameRelation(middlenamerelation);
					empInfo.setDob(dob);
					empInfo.setGender(gender);
					empInfo.setAddress(address);
					empInfo.setPermanentAddress(permamnentAddress);
					empInfo.setDelStatus(1);
					empInfo.setEmail(email);
					empInfo.setEmerName(emerNam);

					if (checkEmpCode != null) {
						empInfo.setEmpInfoId(checkEmpCode.getEmpInfoId());

					} else {
						empInfo.setEmpInfoId(0);
					}

					empInfo.setEmerContactNo1(emerCon);
					TblEmpInfo empInfoSave = Constants.getRestTemplate()
							.postForObject(Constants.url + "/saveEmployeeIdInfo", empInfo, TblEmpInfo.class);
					System.out.println("EmpInfo--------" + empInfoSave);

					/************************* Employee Bank *******************************/

					long accNo = 0;
					if (row.getCell(15) != null)
						accNo = (long) row.getCell(15).getNumericCellValue();
					System.err.println("accNo" + accNo);
					String banlName = null;
					if (row.getCell(16) != null)
						banlName = row.getCell(16).getStringCellValue();

					TblEmpBankInfo empBank = new TblEmpBankInfo();

					empBank.setEmpId(empSaveResp.getEmpId());

					empBank.setAccNo(String.valueOf(accNo));
					empBank.setBankId(0);
					empBank.setDelStatus(1);
					empBank.setExVar1(banlName);

					if (checkEmpCode != null) {
						empBank.setBankInfoId(checkEmpCode.getBankInfoId());

					} else {
						empBank.setBankInfoId(0);
					}
					TblEmpBankInfo empBankInfo = Constants.getRestTemplate()
							.postForObject(Constants.url + "/saveEmployeeIdBank", empBank, TblEmpBankInfo.class);

					/****************************************
					 * Nominee
					 **********************************************/
					/*
					 * String name2 = null; if (row.getCell(20) != null) name2 =
					 * row.getCell(20).getStringCellValue();
					 * 
					 * String relation2 = null; if (row.getCell(21) != null) relation2 =
					 * row.getCell(21).getStringCellValue();
					 * 
					 * String dob2 = null; if (row.getCell(22) != null) dob2 =
					 * row.getCell(22).getStringCellValue();
					 * 
					 * String name3 = null; if (row.getCell(23) != null) name3 =
					 * row.getCell(23).getStringCellValue();
					 * 
					 * String relation3 = null; if (row.getCell(24) != null) relation3 =
					 * row.getCell(24).getStringCellValue();
					 * 
					 * String dob3 = null; if (row.getCell(25) != null) dob3 =
					 * row.getCell(25).getStringCellValue();
					 * 
					 * String name4 = null; if (row.getCell(26) != null) name4 =
					 * row.getCell(26).getStringCellValue();
					 * 
					 * String relation4 = null; if (row.getCell(27) != null) relation4 =
					 * row.getCell(27).getStringCellValue();
					 * 
					 * String dob4 = null; if (row.getCell(28) != null) dob4 =
					 * row.getCell(28).getStringCellValue();
					 * 
					 * String name5 = null; if (row.getCell(29) != null) name5 =
					 * row.getCell(29).getStringCellValue();
					 * 
					 * String relation5 = null; if (row.getCell(30) != null) relation5 =
					 * row.getCell(30).getStringCellValue();
					 * 
					 * String dob5 = null; if (row.getCell(31) != null) dob5 =
					 * row.getCell(31).getStringCellValue();
					 * 
					 * String name6 = null; if (row.getCell(32) != null) name6 =
					 * row.getCell(32).getStringCellValue();
					 * 
					 * String relation6 = null; if (row.getCell(33) != null) relation6 =
					 * row.getCell(33).getStringCellValue();
					 * 
					 * String dob6 = null; if (row.getCell(34) != null) dob6 =
					 * row.getCell(34).getStringCellValue();
					 */
					TblEmpNominees empNominee = new TblEmpNominees();

					/*
					 * try { empNominee.setEmpId(empSaveResp.getEmpId());
					 * empNominee.setNomineeId(checkEmpCode.getNomineeId()); } catch (Exception e) {
					 * empNominee.setNomineeId(0); }
					 */

					/*
					 * empNominee.setName2(name2); empNominee.setRelation2(relation2);
					 * empNominee.setDob2(dob2);
					 * 
					 * empNominee.setName3(name3); empNominee.setRelation3(relation3);
					 * empNominee.setDob3(dob3);
					 * 
					 * empNominee.setName4(name4); empNominee.setRelation4(relation4);
					 * empNominee.setDob4(dob4);
					 * 
					 * empNominee.setName5(name5); empNominee.setRelation5(relation5);
					 * empNominee.setDob5(dob5);
					 * 
					 * empNominee.setName6(name6); empNominee.setRelation6(relation6);
					 * empNominee.setDob6(dob6);
					 */
					empNominee.setEmpId(empSaveResp.getEmpId());
					empNominee.setDelStatus(1);

					if (checkEmpCode != null) {
						empNominee.setNomineeId(checkEmpCode.getNomineeId());

					} else {
						empNominee.setNomineeId(0);
					}
					TblEmpNominees nominee = Constants.getRestTemplate()
							.postForObject(Constants.url + "/saveEmployeeIdNominee", empNominee, TblEmpNominees.class);

					System.out.println("Emp Nominees-----------" + nominee);

					/****************************************
					 * Employee Salary
					 **********************************************/

					/************************* Employee Salary *******************************/
					String cmpLeavDate = null;
					if (row.getCell(17) != null)
						cmpLeavDate = row.getCell(17).getStringCellValue();

					String cmpJoinDate = null;
					if (row.getCell(18) != null)
						cmpJoinDate = row.getCell(18).getStringCellValue();

					String epfJoinDate = null;
					if (row.getCell(19) != null)
						epfJoinDate = row.getCell(19).getStringCellValue();

					String salBasis = null;
					if (row.getCell(20) != null)
						salBasis = row.getCell(20).getStringCellValue();

					int grossSal = 0;
					if (row.getCell(21) != null)
						grossSal = (int) row.getCell(21).getNumericCellValue();

					double basic = 0;
					if (row.getCell(22) != null)
						basic = row.getCell(22).getNumericCellValue();

					String pfApplicable = null;
					if (row.getCell(31) != null)
						pfApplicable = row.getCell(31).getStringCellValue();

					String esicApplicable = null;
					if (row.getCell(32) != null)
						esicApplicable = row.getCell(32).getStringCellValue();

					String isMlwfApplicable = null;
					if (row.getCell(33) != null)
						isMlwfApplicable = row.getCell(33).getStringCellValue();

					String isPtApplicable = null;
					if (row.getCell(34) != null)
						isPtApplicable = row.getCell(34).getStringCellValue();

					// Employee Allowances
					double dearnessAllwnc = 0;
					if (row.getCell(23) != null)
						dearnessAllwnc = row.getCell(23).getNumericCellValue();

					double houseRentAllwnc = 0;
					if (row.getCell(24) != null)
						houseRentAllwnc = row.getCell(24).getNumericCellValue();

					double educationAllwnc = 0;
					if (row.getCell(25) != null)
						educationAllwnc = row.getCell(25).getNumericCellValue();

					double tiffinAllwnc = 0;
					if (row.getCell(26) != null)
						tiffinAllwnc = row.getCell(26).getNumericCellValue();

					double leaveTravelAllwnc = 0;
					if (row.getCell(27) != null)
						leaveTravelAllwnc = row.getCell(27).getNumericCellValue();

					double conveyanceAllwnc = 0;
					if (row.getCell(28) != null)
						conveyanceAllwnc = row.getCell(28).getNumericCellValue();
					double mobileAllw = 0;
					if (row.getCell(29) != null)
						mobileAllw = row.getCell(29).getNumericCellValue();

					double otherAll = 0;
					if (row.getCell(30) != null)
						otherAll = row.getCell(30).getNumericCellValue();

					EmpSalaryInfo empSal = new EmpSalaryInfo();

					if (checkEmpCode != null) {
						empSal.setSalaryInfoId(checkEmpCode.getSalaryInfoId());

					} else {
						empSal.setSalaryInfoId(0);
					}
					empSal.setEmpId(empSaveResp.getEmpId());
					empSal.setCmpLeavingDate(cmpLeavDate);
					empSal.setCmpJoiningDate(cmpJoinDate);
					empSal.setEpfJoiningDate(epfJoinDate);
					empSal.setSalBasis(salBasis);
					empSal.setBasic(basic);
					empSal.setPfType("0");
					empSal.setPfEmpPer(0);
					empSal.setPfEmplrPer(0);
					empSal.setEsicApplicable(esicApplicable);
					empSal.setCeilingLimitEmpApplicable("no");
					empSal.setCeilingLimitEmployerApplicable("no");
					empSal.setMlwfApplicable(isMlwfApplicable);
					empSal.setPtApplicable(isPtApplicable);
					empSal.setDelStatus(1);
					empSal.setPfApplicable(pfApplicable);
					empSal.setGrossSalary(grossSal);
					empSal.setSalaryTypeId(1);
					EmpSalaryInfo empSalInfo = Constants.getRestTemplate()
							.postForObject(Constants.url + "/saveEmployeeIdSalary", empSal, EmpSalaryInfo.class);
					System.out.println("Emp SalInfo-----------" + empSalInfo);

					// Salary Allowances
					map = new LinkedMultiValueMap<>();
					map.add("empId", empSaveResp.getEmpId());
					EmpSalAllowance[] empSalAllowance = Constants.getRestTemplate()
							.postForObject(Constants.url + "/getEmployeeSalAllowances", map, EmpSalAllowance[].class);

					List<EmpSalAllowance> empAllowncList = new ArrayList<EmpSalAllowance>(
							Arrays.asList(empSalAllowance));

					List<EmpSalAllowance> allowncList = new ArrayList<EmpSalAllowance>();
					EmpSalAllowance empSalAllwance = new EmpSalAllowance();
					try {

						int keyVal1 = 0;
						int keyVal2 = 0;
						int keyVal3 = 0;
						int keyVal4 = 0;
						int keyVal5 = 0;
						int keyVal6 = 0;
						int keyVal7 = 0;
						int keyVal8 = 0;
						for (int k = 0; k < empAllowncList.size(); k++) {

							if (empAllowncList.get(k).getAllowanceId() == 1) {
								keyVal1 = empAllowncList.get(k).getEmpSalAllowanceId();
							} else if (empAllowncList.get(k).getAllowanceId() == 1) {
								keyVal1 = empAllowncList.get(k).getEmpSalAllowanceId();
							} else if (empAllowncList.get(k).getAllowanceId() == 9) {
								keyVal2 = empAllowncList.get(k).getEmpSalAllowanceId();
							} else if (empAllowncList.get(k).getAllowanceId() == 14) {
								keyVal3 = empAllowncList.get(k).getEmpSalAllowanceId();
							} else if (empAllowncList.get(k).getAllowanceId() == 5) {
								keyVal4 = empAllowncList.get(k).getEmpSalAllowanceId();
							} else if (empAllowncList.get(k).getAllowanceId() == 10) {
								keyVal5 = empAllowncList.get(k).getEmpSalAllowanceId();
							} else if (empAllowncList.get(k).getAllowanceId() == 11) {
								keyVal6 = empAllowncList.get(k).getEmpSalAllowanceId();
							} else if (empAllowncList.get(k).getAllowanceId() == 19) {
								keyVal7 = empAllowncList.get(k).getEmpSalAllowanceId();
							} else if (empAllowncList.get(k).getAllowanceId() == 173) {
								keyVal8 = empAllowncList.get(k).getEmpSalAllowanceId();
							}

						}

						empSalAllwance = new EmpSalAllowance();

						empSalAllwance.setEmpSalAllowanceId(keyVal1);
						empSalAllwance.setAllowanceId(1);
						empSalAllwance.setAllowanceValue(dearnessAllwnc);
						empSalAllwance.setEmpId(empSaveResp.getEmpId());
						empSalAllwance.setDelStatus(1);
						empSalAllwance.setExInt1(0);
						empSalAllwance.setExInt2(0);
						allowncList.add(empSalAllwance);

						empSalAllwance = new EmpSalAllowance();
						empSalAllwance.setEmpSalAllowanceId(keyVal2);
						empSalAllwance.setAllowanceId(9);
						empSalAllwance.setAllowanceValue(houseRentAllwnc);
						empSalAllwance.setEmpId(empSaveResp.getEmpId());
						empSalAllwance.setDelStatus(1);
						empSalAllwance.setExInt1(0);
						empSalAllwance.setExInt2(0);
						allowncList.add(empSalAllwance);

						empSalAllwance = new EmpSalAllowance();
						empSalAllwance.setEmpSalAllowanceId(keyVal3);

						empSalAllwance.setAllowanceId(14);
						empSalAllwance.setAllowanceValue(educationAllwnc);
						empSalAllwance.setEmpId(empSaveResp.getEmpId());
						empSalAllwance.setDelStatus(1);
						empSalAllwance.setExInt1(0);
						empSalAllwance.setExInt2(0);
						allowncList.add(empSalAllwance);

						empSalAllwance = new EmpSalAllowance();
						empSalAllwance.setEmpSalAllowanceId(keyVal4);

						empSalAllwance.setAllowanceId(5);
						empSalAllwance.setAllowanceValue(tiffinAllwnc);
						empSalAllwance.setEmpId(empSaveResp.getEmpId());
						empSalAllwance.setDelStatus(1);
						empSalAllwance.setExInt1(0);
						empSalAllwance.setExInt2(0);
						allowncList.add(empSalAllwance);

						empSalAllwance = new EmpSalAllowance();
						empSalAllwance.setEmpSalAllowanceId(keyVal5);

						empSalAllwance.setAllowanceId(10);
						empSalAllwance.setAllowanceValue(leaveTravelAllwnc);
						empSalAllwance.setEmpId(empSaveResp.getEmpId());
						empSalAllwance.setDelStatus(1);
						empSalAllwance.setExInt1(0);
						empSalAllwance.setExInt2(0);
						allowncList.add(empSalAllwance);

						empSalAllwance = new EmpSalAllowance();
						empSalAllwance.setEmpSalAllowanceId(keyVal6);

						empSalAllwance.setAllowanceId(11);
						empSalAllwance.setAllowanceValue(conveyanceAllwnc);
						empSalAllwance.setEmpId(empSaveResp.getEmpId());
						empSalAllwance.setDelStatus(1);
						empSalAllwance.setExInt1(0);
						empSalAllwance.setExInt2(0);
						allowncList.add(empSalAllwance);

						empSalAllwance = new EmpSalAllowance();
						empSalAllwance.setEmpSalAllowanceId(keyVal7);

						empSalAllwance.setAllowanceId(19);
						empSalAllwance.setAllowanceValue(otherAll);
						empSalAllwance.setEmpId(empSaveResp.getEmpId());
						empSalAllwance.setDelStatus(1);
						empSalAllwance.setExInt1(0);
						empSalAllwance.setExInt2(0);
						allowncList.add(empSalAllwance);

						empSalAllwance = new EmpSalAllowance();
						empSalAllwance.setEmpSalAllowanceId(keyVal8);

						empSalAllwance.setAllowanceId(173);
						empSalAllwance.setAllowanceValue(mobileAllw);
						empSalAllwance.setEmpId(empSaveResp.getEmpId());
						empSalAllwance.setDelStatus(1);
						empSalAllwance.setExInt1(0);
						empSalAllwance.setExInt2(0);
						allowncList.add(empSalAllwance);

					} catch (Exception e) {
						// empSellAllwance.setSalaryInfoId(0);
					}
					EmpSalAllowance[] allowance = Constants.getRestTemplate().postForObject(
							Constants.url + "/saveEmpSalAllowanceInfo", allowncList, EmpSalAllowance[].class);
					System.out.println("Allowance--------" + allowance);
				}
			} // For Loop End

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showEmpFileUpload";
	}
}