package com.ats.hreasy.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.common.RandomString;
import com.ats.hreasy.common.VpsImageUpload;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Allowances;
import com.ats.hreasy.model.Bank;
import com.ats.hreasy.model.Contractor;
import com.ats.hreasy.model.Department;
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.EmpDoctype;
import com.ats.hreasy.model.EmpSalAllowance;
import com.ats.hreasy.model.EmpSalaryInfo;
import com.ats.hreasy.model.EmployeDoc;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.Setting;
import com.ats.hreasy.model.TblEmpBankInfo;
import com.ats.hreasy.model.TblEmpInfo;
import com.ats.hreasy.model.TblEmpNominees;
import com.ats.hreasy.model.User;

@Controller
@Scope("session")
public class EmployeeController {

	Date date = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String currDate = sf.format(date);
	String redirect = "";
	int flag = 0;
	User userRes = new User();

	EmployeeMaster empSave = null;
	// TblEmpInfo empIdInfo = null;
	/*
	 * TblEmpNominees empIdNom = null; TblEmpBankInfo empIdBank = null;
	 * EmpSalaryInfo empIdSal = null; EmpSalAllowance empSalAllowanceId = null;
	 */
	List<Allowances> allowanceList = new ArrayList<Allowances>();
	List<EmpDoctype> empDocList = new ArrayList<EmpDoctype>();
	// List<EmpSalAllowance> empAllowncList = new ArrayList<EmpSalAllowance>();

	/****************************** Employee *********************************/
	@RequestMapping(value = "/showEmployeeList", method = RequestMethod.GET)
	public ModelAndView showEmployeeList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showEmployeeList", "showEmployeeList", 1, 0, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/employeeList");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				EmployeeMaster[] empArr = Constants.getRestTemplate().postForObject(Constants.url + "/getAllEmployee",
						map, EmployeeMaster[].class);
				List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>(Arrays.asList(empArr));

				for (int i = 0; i < empList.size(); i++) {

					empList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(empList.get(i).getEmpId())));
				}

				model.addObject("empList", empList);

				Info add = AcessController.checkAccess("showEmployeeList", "showEmployeeList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showEmployeeList", "showEmployeeList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showEmployeeList", "showEmployeeList", 0, 0, 0, 1,
						newModuleList);

				if (add.isError() == false) {
					//System.out.println(" add   Accessable ");
					model.addObject("addAccess", 0);

				}
				if (edit.isError() == false) {
					//System.out.println(" edit   Accessable ");
					model.addObject("editAccess", 0);
				}
				if (delete.isError() == false) {
					//System.out.println(" delete   Accessable ");
					model.addObject("deleteAccess", 0);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/deleteEmp", method = RequestMethod.GET)
	public String deleteContractor(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String a = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("deleteEmp", "showEmployeeList", 0, 0, 0, 1, newModuleList);
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showEmployeeList";
			try {
				String base64encodedString = request.getParameter("empId");
				String empId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empId", empId);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deleteEmployee", map,
						Info.class);

				if (res.isError()) {
					session.setAttribute("errorMsg", "Failed to Delete");
				} else {
					session.setAttribute("successMsg", "Deleted Successfully");

				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Delete");
			}

		}
		return a;
	}

	@RequestMapping(value = "/employeeAdd", method = RequestMethod.GET)
	public ModelAndView employeeAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			EmployeeMaster emp = new EmployeeMaster();

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("employeeAdd", "showEmployeeList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				// System.err.println("pass is" + randomAlphaNumeric(6));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
						Location[].class);
				List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));

				Department[] department = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAllDepartments", map, Department[].class);
				List<Department> departmentList = new ArrayList<Department>(Arrays.asList(department));

				Designation[] designation = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAllDesignations", map, Designation[].class);
				List<Designation> designationList = new ArrayList<Designation>(Arrays.asList(designation));

				Contractor[] contractor = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAllContractors", map, Contractor[].class);
				List<Contractor> contractorsList = new ArrayList<Contractor>(Arrays.asList(contractor));

				Bank[] bank = Constants.getRestTemplate().postForObject(Constants.url + "/getAllBanks", map,
						Bank[].class);
				List<Bank> bankList = new ArrayList<Bank>(Arrays.asList(bank));

				Allowances[] allowanceArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllAllowances", Allowances[].class);
				allowanceList = new ArrayList<Allowances>(Arrays.asList(allowanceArr));

				EmpDoctype[] empDocArr = Constants.getRestTemplate().postForObject(Constants.url + "/getAllEmpDocTypes",
						map, EmpDoctype[].class);
				empDocList = new ArrayList<EmpDoctype>(Arrays.asList(empDocArr));

				model = new ModelAndView("master/addEmployee");

				model.addObject("locationList", locationList);
				model.addObject("deptList", departmentList);
				model.addObject("designationList", designationList);
				model.addObject("contractorsList", contractorsList);
				model.addObject("bankList", bankList);
				model.addObject("allowanceList", allowanceList);
				model.addObject("empDocList", empDocList);
				model.addObject("emp", emp);
				model.addObject("imgUrl", Constants.imageShowUrl);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/insertEmployeeBasicInfo", method = RequestMethod.POST)
	public String submitInsertEmployeeUserInfo(HttpServletRequest request, HttpServletResponse response) {
		empSave = new EmployeeMaster();
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		EmployeeMaster emp = new EmployeeMaster();

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("employeeAdd", "showEmployeeList", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {

			redirect = "redirect:/accessDenied";

		} else {

			try {
				int empId = 0;
				int contract = 0;
				int deptId = 0;
				int desigId = 0;
				int empType = 0;
				int locId = 0;
				try {
					empId = Integer.parseInt(request.getParameter("empId"));
					contract = Integer.parseInt(request.getParameter("contractor"));
					deptId = Integer.parseInt(request.getParameter("deptId"));
					desigId = Integer.parseInt(request.getParameter("desigId"));
					empType = Integer.parseInt(request.getParameter("empType"));
					locId = Integer.parseInt(request.getParameter("locId"));
				} catch (Exception e) {
					empId = 0;
					contract = 0;
				}

				String mob2 = request.getParameter("mobile2");

				String landline = request.getParameter("landline");

				if (empId > 0) {
					// System.out.println("In Edit");

					if (mob2 == "" || mob2 == null) {
						emp.setMobileNo2("NA");
					} else {
						emp.setMobileNo2(mob2);
					}

					emp.setEmpId(empId);

					emp.setFirstName(request.getParameter("fname"));
					emp.setMiddleName(request.getParameter("mname"));
					emp.setSurname(request.getParameter("sname"));

					emp.setMobileNo1(request.getParameter("mobile1"));
					emp.setResidenceLandNo("NA");
					emp.setAadharNo(request.getParameter("aadhar"));
					emp.setAddedBySupervisorId(0);
					emp.setAddedFrom(1);
					emp.setCmpCode(1);
					emp.setContractorId(contract);
					emp.setCurrentShiftid(0);
					emp.setDepartId(deptId);
					emp.setDesignationId(desigId);
					emp.setEarnLeaveOpeningBalance(0);
					emp.setEmailId("NA");
					emp.setEmpCategory(request.getParameter("empCat"));
					emp.setEmpCode(request.getParameter("empCode").toUpperCase());
					emp.setEmpType(empType);
					emp.setEsicNo(request.getParameter("esic"));
					emp.setExgratiaPerc(0);

					emp.setGrossSalaryEst(0);
					emp.setIsEmp(1);
					emp.setLeavingReason("NA");
					emp.setLocationId(locId);

					emp.setLoginName("NA");
					emp.setLoginTime(currDate);
					emp.setMotherName("NA");
					emp.setNewBasicRate(0);
					emp.setNewDaRate(0);
					emp.setNewHraRate(0);
					emp.setNextShiftid(0);
					emp.setNoticePayAmount(0);
					emp.setPanCardNo(request.getParameter("pan"));
					emp.setPfNo(request.getParameter("pfNo"));
					emp.setPlCalcBase(0);
					emp.setRawData("NA");
					emp.setSalDedAtFullandfinal(0);
					emp.setSocietySerialNo("NA");
					emp.setUan(request.getParameter("uan"));
					emp.setExInt1(0);
					emp.setExInt2(0);
					emp.setExVar1("NA");
					emp.setExVar2("NA");
					emp.setDelStatus(1);

					empSave = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployee", emp,
							EmployeeMaster.class);
					// System.out.println("Edit Save = " + empSave);
					empId = empSave.getEmpId();
					if (empSave != null) {

						StringBuilder sb = new StringBuilder();
						String[] locIds = request.getParameterValues("locId_list");
						for (int i = 0; i < locIds.length; i++) {
							sb = sb.append(locIds[i] + ",");

						}
						String locIdList = sb.toString();
						locIdList = locIdList.substring(0, locIdList.length() - 1);
						userRes.setUserName(empSave.getEmpCode());
						userRes.setLocId(locIdList);
						userRes.setMakerUserId(userObj.getEmpId());
						userRes.setMakerEnterDatetime(sf.format(date));

						userRes = Constants.getRestTemplate().postForObject(Constants.url + "/saveUserInfo", userRes,
								User.class);
						session.setAttribute("successMsg", "Record Updated Successfully");
						String empEncryptId = FormValidation.Encrypt(String.valueOf(empSave.getEmpId()));
						redirect = "redirect:/employeeEdit?empId=" + empEncryptId;
					} else {
						session.setAttribute("errorMsg", "Failed to Update Record");
						redirect = "redirect:/employeeAdd";
					}
				} else {

					// System.out.println("New Enrty");

					if (mob2 == "" || mob2 == null) {
						emp.setMobileNo2("NA");
					} else {
						emp.setMobileNo2(mob2);
					}

					emp.setEmpId(empId);

					emp.setFirstName(request.getParameter("fname"));
					emp.setMiddleName(request.getParameter("mname"));
					emp.setSurname(request.getParameter("sname"));

					emp.setMobileNo1(request.getParameter("mobile1"));
					emp.setResidenceLandNo("NA");
					emp.setAadharNo(request.getParameter("aadhar"));
					emp.setAddedBySupervisorId(0);
					emp.setAddedFrom(1);
					emp.setCmpCode(1);
					emp.setContractorId(contract);
					emp.setCurrentShiftid(0);
					emp.setDepartId(deptId);
					emp.setDesignationId(desigId);
					emp.setEarnLeaveOpeningBalance(0);
					emp.setEmailId("NA");
					emp.setEmpCategory(request.getParameter("empCat"));
					emp.setEmpCode(request.getParameter("empCode"));
					emp.setEmpType(empType);
					emp.setEsicNo(request.getParameter("esic"));
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

					map = new LinkedMultiValueMap<>();
					map.add("limitKey", "Exgratia");
					Setting setlimit = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey",
							map, Setting.class);

					emp.setExgratiaPerc(Float.parseFloat(setlimit.getValue()));

					emp.setGrossSalaryEst(0);
					emp.setIsEmp(1);
					emp.setLeavingReason("NA");
					emp.setLocationId(locId);

					emp.setLoginName("NA");
					emp.setLoginTime(currDate);
					emp.setMotherName("NA");
					emp.setNewBasicRate(0);
					emp.setNewDaRate(0);
					emp.setNewHraRate(0);
					emp.setNextShiftid(0);
					emp.setNoticePayAmount(0);
					emp.setPanCardNo(request.getParameter("pan"));
					emp.setPfNo(request.getParameter("pfNo"));
					emp.setPlCalcBase(0);
					emp.setRawData("NA");
					emp.setSalDedAtFullandfinal(0);
					emp.setSocietySerialNo("NA");
					emp.setUan(request.getParameter("uan"));
					emp.setExInt1(0);
					emp.setExInt2(0);
					emp.setExVar1("NA");
					emp.setExVar2("NA");
					emp.setDelStatus(1);

					empSave = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployee", emp,
							EmployeeMaster.class);
					empId = empSave.getEmpId();
					if (empSave != null) {

						StringBuilder sb = new StringBuilder();
						String[] locIds = request.getParameterValues("locId_list");
						for (int i = 0; i < locIds.length; i++) {
							sb = sb.append(locIds[i] + ",");

						}
						User uinfo = new User();
						uinfo.setEmpId(empSave.getEmpId());
						uinfo.setEmpTypeId(empSave.getEmpType());
						uinfo.setUserName(empSave.getEmpCode());
						String locIdList = sb.toString();
						locIdList = locIdList.substring(0, locIdList.length() - 1);

						RandomString randomString = new RandomString();
						String password = randomString.nextString();
						MessageDigest md = MessageDigest.getInstance("MD5");
						byte[] messageDigest = md.digest(password.getBytes());
						BigInteger number = new BigInteger(1, messageDigest);
						String hashtext = number.toString(16);

						uinfo.setUserName(empSave.getEmpCode());
						uinfo.setUserPwd(hashtext);
						uinfo.setLocId(locIdList);
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

						//System.out.println(locIdList + "" + uinfo.getLocId());
						User res1 = Constants.getRestTemplate().postForObject(Constants.url + "/saveUserInfo", uinfo,
								User.class);

						TblEmpInfo empInfo = new TblEmpInfo();
						empInfo.setEmpId(empSave.getEmpId());
						empInfo.setDelStatus(1);

						TblEmpInfo empIdInfo = Constants.getRestTemplate()
								.postForObject(Constants.url + "/saveEmployeeIdInfo", empInfo, TblEmpInfo.class);

						TblEmpBankInfo empBank = new TblEmpBankInfo();
						empBank.setDelStatus(1);
						empBank.setEmpId(empSave.getEmpId());

						TblEmpBankInfo empIdBank = Constants.getRestTemplate()
								.postForObject(Constants.url + "/saveEmployeeIdBank", empBank, TblEmpBankInfo.class);

						TblEmpNominees empNominee = new TblEmpNominees();
						empNominee.setDelStatus(1);
						empNominee.setEmpId(empSave.getEmpId());

						TblEmpNominees empIdNom = Constants.getRestTemplate().postForObject(
								Constants.url + "/saveEmployeeIdNominee", empNominee, TblEmpNominees.class);

						EmpSalaryInfo empSal = new EmpSalaryInfo();
						empSal.setDelStatus(1);
						empSal.setEmpId(empSave.getEmpId());

						EmpSalaryInfo empIdSal = Constants.getRestTemplate()
								.postForObject(Constants.url + "/saveEmployeeIdSalary", empSal, EmpSalaryInfo.class);

						/*EmpSalAllowance allowance = new EmpSalAllowance();
						allowance.setDelStatus(1);
						allowance.setEmpId(empSave.getEmpId());

						EmpSalAllowance empSalAllowanceId = Constants.getRestTemplate().postForObject(
								Constants.url + "/saveEmpSalAllowanceIds", allowance, EmpSalAllowance.class);*/

						/*
						 * User user = new User(); user.setEmpId(empSave.getEmpId());
						 * 
						 * User empIdUser = Constants.getRestTemplate().postForObject(Constants.url +
						 * "/saveEmployeeIdUser", user, User.class);
						 */

						String empEncryptId = FormValidation.Encrypt(String.valueOf(empId));

						//System.out.println("Emp Encrypt Id---" + empEncryptId);

						session.setAttribute("successMsg", "Record Inserted Successfully");

						redirect = "redirect:/employeeEdit?empId=" + empEncryptId;
					} else {
						session.setAttribute("errorMsg", "Failed to Insert Record");
						redirect = "redirect:/employeeAdd";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//System.out.println("Success----------------" + empSave);
		return redirect;

	}
	/*
	 * static String ALPHA_NUMERIC_STRING =
	 * "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz@#!$";
	 * 
	 * public static String randomAlphaNumeric(int count) {
	 * 
	 * StringBuilder builder = new StringBuilder();
	 * 
	 * while (count-- != 0) {
	 * 
	 * int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
	 * 
	 * builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	 * 
	 * }
	 * 
	 * return builder.toString();
	 * 
	 * }
	 */

	@RequestMapping(value = "/employeeEdit", method = RequestMethod.GET)
	public ModelAndView employeeEdit(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;
		MultiValueMap<String, Object> map = null;
		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("employeeEdit", "showEmployeeList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
						Location[].class);
				List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));
				// System.out.println("Location List-------------"+locationList);

				Department[] department = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAllDepartments", map, Department[].class);
				List<Department> departmentList = new ArrayList<Department>(Arrays.asList(department));
				// System.out.println("DepartmentList List-------------"+departmentList);

				Designation[] designation = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAllDesignations", map, Designation[].class);
				List<Designation> designationList = new ArrayList<Designation>(Arrays.asList(designation));
				// System.out.println("DesignationList List-------------"+designationList);

				Contractor[] contractor = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAllContractors", map, Contractor[].class);
				List<Contractor> contractorsList = new ArrayList<Contractor>(Arrays.asList(contractor));
				// System.out.println("ContractorsList List-------------"+contractorsList);

				Bank[] bank = Constants.getRestTemplate().postForObject(Constants.url + "/getAllBanks", map,
						Bank[].class);
				List<Bank> bankList = new ArrayList<Bank>(Arrays.asList(bank));

				Allowances[] allowanceArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllAllowances", Allowances[].class);
				allowanceList = new ArrayList<Allowances>(Arrays.asList(allowanceArr));
				//System.out.println("allowanceList All-------------" + allowanceList);

				EmpDoctype[] empDocArr = Constants.getRestTemplate().postForObject(Constants.url + "/getAllEmpDocTypes",
						map, EmpDoctype[].class);
				empDocList = new ArrayList<EmpDoctype>(Arrays.asList(empDocArr));

				model = new ModelAndView("master/addEmployee");

				model.addObject("locationList", locationList);
				model.addObject("deptList", departmentList);
				model.addObject("designationList", designationList);
				model.addObject("contractorsList", contractorsList);
				model.addObject("bankList", bankList);
				model.addObject("allowanceList", allowanceList);
				model.addObject("empDocList", empDocList);
				model.addObject("imgUrl", Constants.imageShowUrl);

				/**************************************************
				 * Edit
				 ********************************************/

				String base64encodedString = request.getParameter("empId");
				String empId = FormValidation.DecodeKey(base64encodedString);

				// System.out.println("Encrypt-----" + empId);
				map = new LinkedMultiValueMap<>();
				map.add("empId", Integer.parseInt(empId));

				EmployeeMaster emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeById", map,
						EmployeeMaster.class);
				// System.out.println("Edit Emp-------" + emp);

				TblEmpInfo empPersInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmployeePersonalInfo", map, TblEmpInfo.class);
				// System.out.println("Edit EmpPersonal Info-------" + empPersInfo);

				TblEmpNominees empNom = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeNominee",
						map, TblEmpNominees.class);
				// System.out.println("Edit Emp Nominee Info-------" + empNom);

				TblEmpBankInfo empBank = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmployeeBankInfo", map, TblEmpBankInfo.class);
				// System.out.println("Edit Emp Bank Info-------" + empBank);

				EmpSalaryInfo empSalInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmployeeSalInfo", map, EmpSalaryInfo.class);
				// System.out.println("Edit Emp Salary Info-------" + empSalInfo);

				EmpSalAllowance[] empSalAllowance = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmployeeSalAllowances", map, EmpSalAllowance[].class);

				List<EmpSalAllowance> empAllowncList = new ArrayList<EmpSalAllowance>(Arrays.asList(empSalAllowance));
				// System.out.println("Edit Emp Salary EmpSalAllowance Info-------" +
				// empAllowncList);

				EmployeDoc[] docArr = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeDocs", map,
						EmployeDoc[].class);
				List<EmployeDoc> docList = new ArrayList<EmployeDoc>(Arrays.asList(docArr));

				map = new LinkedMultiValueMap<>();
				map.add("EmpId", Integer.parseInt(empId));
				userRes = Constants.getRestTemplate().postForObject(Constants.url + "/findUserInfoByEmpId", map,
						User.class);
				model.addObject("locationIds", userRes.getLocId().split(","));

				model.addObject("emp", emp);
				model.addObject("empPersInfo", empPersInfo); // model.addObject("empPersInfo", empIdInfo);
				model.addObject("empNom", empNom); // model.addObject("empNom", empIdNom);
				model.addObject("empBank", empBank);

				model.addObject("empAllowanceId", empSalInfo);
				model.addObject("empAllowncList", empAllowncList);
				model.addObject("docList", docList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/employeeDetails", method = RequestMethod.GET)
	public String employeeEdit(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String mav = null;
		MultiValueMap<String, Object> map = null;
		try {

			mav = "master/employeeDetails";
			map = new LinkedMultiValueMap<>();
			map.add("companyId", 1);

			Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
					Location[].class);
			List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));
			// System.out.println("Location List-------------"+locationList);

			Department[] department = Constants.getRestTemplate().postForObject(Constants.url + "/getAllDepartments",
					map, Department[].class);
			List<Department> departmentList = new ArrayList<Department>(Arrays.asList(department));
			// System.out.println("DepartmentList List-------------"+departmentList);

			Designation[] designation = Constants.getRestTemplate().postForObject(Constants.url + "/getAllDesignations",
					map, Designation[].class);
			List<Designation> designationList = new ArrayList<Designation>(Arrays.asList(designation));
			// System.out.println("DesignationList List-------------"+designationList);

			Contractor[] contractor = Constants.getRestTemplate().postForObject(Constants.url + "/getAllContractors",
					map, Contractor[].class);
			List<Contractor> contractorsList = new ArrayList<Contractor>(Arrays.asList(contractor));
			// System.out.println("ContractorsList List-------------"+contractorsList);

			Bank[] bank = Constants.getRestTemplate().postForObject(Constants.url + "/getAllBanks", map, Bank[].class);
			List<Bank> bankList = new ArrayList<Bank>(Arrays.asList(bank));

			Allowances[] allowanceArr = Constants.getRestTemplate().getForObject(Constants.url + "/getAllAllowances",
					Allowances[].class);
			allowanceList = new ArrayList<Allowances>(Arrays.asList(allowanceArr));
			//System.out.println("allowanceList All-------------" + allowanceList);

			EmpDoctype[] empDocArr = Constants.getRestTemplate().postForObject(Constants.url + "/getAllEmpDocTypes",
					map, EmpDoctype[].class);
			empDocList = new ArrayList<EmpDoctype>(Arrays.asList(empDocArr));

			model.addAttribute("locationList", locationList);
			model.addAttribute("deptList", departmentList);
			model.addAttribute("designationList", designationList);
			model.addAttribute("contractorsList", contractorsList);
			model.addAttribute("bankList", bankList);
			model.addAttribute("allowanceList", allowanceList);
			model.addAttribute("empDocList", empDocList);
			model.addAttribute("imgUrl", Constants.imageShowUrl);

			/**************************************************
			 * Edit
			 ********************************************/

			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);

			// System.out.println("Encrypt-----" + empId);
			map = new LinkedMultiValueMap<>();
			map.add("empId", Integer.parseInt(empId));

			EmployeeMaster emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeById", map,
					EmployeeMaster.class);
			// System.out.println("Edit Emp-------" + emp);

			TblEmpInfo empPersInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmployeePersonalInfo", map, TblEmpInfo.class);
			// System.out.println("Edit EmpPersonal Info-------" + empPersInfo);

			TblEmpNominees empNom = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeNominee",
					map, TblEmpNominees.class);
			// System.out.println("Edit Emp Nominee Info-------" + empNom);

			TblEmpBankInfo empBank = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeBankInfo",
					map, TblEmpBankInfo.class);
			// System.out.println("Edit Emp Bank Info-------" + empBank);

			EmpSalaryInfo empSalInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeSalInfo",
					map, EmpSalaryInfo.class);
			// System.out.println("Edit Emp Salary Info-------" + empSalInfo);

			EmpSalAllowance[] empSalAllowance = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmployeeSalAllowances", map, EmpSalAllowance[].class);

			List<EmpSalAllowance> empAllowncList = new ArrayList<EmpSalAllowance>(Arrays.asList(empSalAllowance));
			// System.out.println("Edit Emp Salary EmpSalAllowance Info-------" +
			// empAllowncList);

			EmployeDoc[] docArr = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeDocs", map,
					EmployeDoc[].class);
			List<EmployeDoc> docList = new ArrayList<EmployeDoc>(Arrays.asList(docArr));

			map = new LinkedMultiValueMap<>();
			map.add("EmpId", Integer.parseInt(empId));
			userRes = Constants.getRestTemplate().postForObject(Constants.url + "/findUserInfoByEmpId", map,
					User.class);
			model.addAttribute("locationIds", userRes.getLocId().split(","));

			model.addAttribute("emp", emp);
			model.addAttribute("empPersInfo", empPersInfo); // model.addObject("empPersInfo", empIdInfo);
			model.addAttribute("empNom", empNom); // model.addObject("empNom", empIdNom);
			model.addAttribute("empBank", empBank);

			model.addAttribute("empAllowanceId", empSalInfo);
			model.addAttribute("empAllowncList", empAllowncList);
			model.addAttribute("docList", docList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/submitEmpOtherInfo", method = RequestMethod.POST)
	public String submitEmpOtherInfo(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {

			int empId = 0;
			int empInfoId = 0;
			try {
				empInfoId = Integer.parseInt(request.getParameter("empOtherInfoId"));
				empId = Integer.parseInt(request.getParameter("empId"));
			} catch (Exception e) {
				e.printStackTrace();
				empInfoId = 0;
			}

			TblEmpInfo empInfo = new TblEmpInfo();
			// if(empIdInfo!=null) {

			empInfo.setEmpInfoId(empInfoId); // empInfo.setEmpInfoId(empIdInfo.getEmpInfoId());
			empInfo.setEmpId(empId);// empInfo.setEmpId(empIdInfo.getEmpId());
			empInfo.setMiddleName(request.getParameter("midname"));
			empInfo.setMiddleNameRelation(request.getParameter("relation"));
			empInfo.setDob(request.getParameter("dob"));
			empInfo.setGender(request.getParameter("gender"));
			empInfo.setMaritalStatus(request.getParameter("maritalstatus"));
			empInfo.setEmail(request.getParameter("email"));
			empInfo.setAddress(request.getParameter("caddress"));
			empInfo.setPermanentAddress(request.getParameter("paddress"));
			empInfo.setEmpQualification(request.getParameter("qualification"));
			empInfo.setEmerName(request.getParameter("emergencyPerson"));
			empInfo.setEmerContactAddr(request.getParameter("emergencyPersonAddress"));
			empInfo.setEmerContactNo1(request.getParameter("contact1"));
			empInfo.setEmerContactNo2(request.getParameter("contact2"));
			empInfo.setBloodGroup(request.getParameter("bloodgroup"));
			empInfo.setUniformSize(request.getParameter("uniformsize"));

			empInfo.setDelStatus(1);
			empInfo.setExInt1(0);
			empInfo.setExInt2(0);
			empInfo.setExVar1("NA");
			empInfo.setExVar2("NA");

			//System.out.println("TblEmpInfo----" + empInfo);
			TblEmpInfo empIdInfo = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdInfo",
					empInfo, TblEmpInfo.class);
			if (empIdInfo != null) {
				session.setAttribute("successMsg", "Record Updated Successfully");

				String empEncryptId = FormValidation.Encrypt(String.valueOf(empId));
				//System.out.println("Emp Encrypt Id---" + empEncryptId);

				redirect = "redirect:/employeeEdit?empId=" + empEncryptId;

			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
				redirect = "redirect:/employeeAdd";
			}

			/*
			 * }else { redirect = "redirect:/employeeAdd"; }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Update Record");
		}
		return redirect;

	}

	@RequestMapping(value = "/submitEmpRelationInfo", method = RequestMethod.POST)
	public String submitEmpRelationInfo(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			int empId = 0;
			int empNomineeId = 0;
			try {
				empNomineeId = Integer.parseInt(request.getParameter("empNomId"));
				empId = Integer.parseInt(request.getParameter("empId"));
			} catch (Exception e) {
				e.printStackTrace();
				empNomineeId = 0;
			}

			TblEmpNominees empNominee = new TblEmpNominees();

			empNominee.setNomineeId(empNomineeId); // empNominee.setNomineeId(empIdNom.getNomineeId());
			empNominee.setEmpId(empId); // empNominee.setEmpId(empIdNom.getEmpId());

			empNominee.setName(request.getParameter("name"));
			empNominee.setDob(request.getParameter("dob"));
			empNominee.setRelation(request.getParameter("relation"));
			empNominee.setOccupation1(request.getParameter("occupation"));

			empNominee.setName2(request.getParameter("name2"));
			empNominee.setDob2(request.getParameter("dob2"));
			empNominee.setRelation2(request.getParameter("relation2"));
			empNominee.setOccupation2(request.getParameter("occupation2"));

			empNominee.setName3(request.getParameter("name3"));
			empNominee.setDob3(request.getParameter("dob3"));
			empNominee.setRelation3(request.getParameter("relation3"));
			empNominee.setOccupation3(request.getParameter("occupation3"));

			empNominee.setName4(request.getParameter("name4"));
			empNominee.setDob4(request.getParameter("dob4"));
			empNominee.setRelation4(request.getParameter("relation4"));
			empNominee.setOccupation4(request.getParameter("occupation4"));

			empNominee.setName5(request.getParameter("name5"));
			empNominee.setDob5(request.getParameter("dob5"));
			empNominee.setRelation5(request.getParameter("relation5"));
			empNominee.setOccupation5(request.getParameter("occupation5"));

			empNominee.setName6(request.getParameter("name6"));
			empNominee.setDob6(request.getParameter("dob6"));
			empNominee.setRelation6(request.getParameter("relation6"));
			empNominee.setOccupation6(request.getParameter("occupation6"));

			empNominee.setDelStatus(1);
			empNominee.setExInt1(0);
			empNominee.setExInt2(0);
			empNominee.setExVar1("NA");
			empNominee.setExVar2("NA");

			TblEmpNominees empIdNom = Constants.getRestTemplate()
					.postForObject(Constants.url + "/saveEmployeeIdNominee", empNominee, TblEmpNominees.class);
			if (empIdNom != null) {
				session.setAttribute("successMsg", "Record Updated Successfully");
				String empEncryptId = FormValidation.Encrypt(String.valueOf(empId));
				//System.out.println("Emp Encrypt Id---" + empEncryptId);

				redirect = "redirect:/employeeEdit?empId=" + empEncryptId;
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
				redirect = "redirect:/employeeAdd";
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return redirect;

	}

	@RequestMapping(value = "/submitEmpBankInfo", method = RequestMethod.POST)
	public String submitEmpBankInfo(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();

			int empId = 0;
			int empBankId = 0;
			try {
				empBankId = Integer.parseInt(request.getParameter("empBankId"));
				empId = Integer.parseInt(request.getParameter("empId"));
			} catch (Exception e) {
				e.printStackTrace();
				empBankId = 0;
			}

			TblEmpBankInfo empBank = new TblEmpBankInfo();

			empBank.setEmpId(empId);
			empBank.setBankInfoId(empBankId);
			empBank.setBankId(Integer.parseInt(request.getParameter("bankId")));
			empBank.setAccNo(request.getParameter("accNo"));

			empBank.setDelStatus(1);
			empBank.setExInt1(0);
			empBank.setExInt2(0);
			empBank.setExVar1("NA");
			empBank.setExVar2("NA");

			//System.out.println("TblEmpBankInfo----" + empBank);
			TblEmpBankInfo empIdBank = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdBank",
					empBank, TblEmpBankInfo.class);
			if (empIdBank != null) {
				session.setAttribute("successMsg", "Record Updated Successfully");

				String empEncryptId = FormValidation.Encrypt(String.valueOf(empId));
				//System.out.println("Emp Encrypt Id---" + empEncryptId);

				redirect = "redirect:/employeeEdit?empId=" + empEncryptId;
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
				redirect = "redirect:/employeeAdd";
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return redirect;

	}

	@RequestMapping(value = "/insertEmployeeAllowancesInfo", method = RequestMethod.POST)
	public String insertEmployeeBasicInfo(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			EmpSalaryInfo empSal = new EmpSalaryInfo();

			int empId = 0;
			int empSalInfoId = 0;

			double basic = 0;
			double pfEmpPer = 0;
			double pfEmployerPer = 0;

			double empEsicPer = 0;
			double employerEsicPer = 0;

			try {
				empId = Integer.parseInt(request.getParameter("empId"));
				empSalInfoId = Integer.parseInt(request.getParameter("empSalId"));

				basic = Double.parseDouble(request.getParameter("basic"));
				pfEmpPer = Double.parseDouble(request.getParameter("pfEmpPer"));
				pfEmployerPer = Double.parseDouble(request.getParameter("pfEmployerPer"));

				empEsicPer = Double.parseDouble(request.getParameter("empEsicPer"));
				employerEsicPer = Double.parseDouble(request.getParameter("employerEsicPer"));

			} catch (Exception e) {
				basic = 0;
				pfEmpPer = 0;
				pfEmployerPer = 0;
				empSalInfoId = 0;
				empEsicPer = 0;
				employerEsicPer = 0;
			}

			/* if(empIdSal!=null) { */

			empSal.setSalaryInfoId(empSalInfoId); // empSal.setSalaryInfoId(empIdSal.getSalaryInfoId());
			empSal.setEmpId(empId); // empSal.setEmpId(empIdSal.getEmpId());
			empSal.setBasic(basic);
			empSal.setSocietyContribution(Double.parseDouble(request.getParameter("societyContri")));
			empSal.setPfApplicable(request.getParameter("pfApplicable"));
			empSal.setPfType(request.getParameter("pfType"));
			empSal.setPfEmpPer(pfEmpPer);
			empSal.setPfEmplrPer(pfEmployerPer);
			empSal.setEsicApplicable(request.getParameter("esicApplicable"));
			empSal.setMlwfApplicable(request.getParameter("mlwfApplicable"));
			empSal.setEmployeeEsicPercentage(empEsicPer);
			empSal.setEmployerEsicPercentage(employerEsicPer);
			empSal.setPtApplicable(request.getParameter("ptApplicable"));
			empSal.setEpfJoiningDate(request.getParameter("epfJoinDate"));
			empSal.setSalBasis(request.getParameter("salBasis"));
			empSal.setCmpJoiningDate(request.getParameter("joinDate"));
			empSal.setCmpLeavingDate(request.getParameter("leaveDate"));
			empSal.setLeavingReason(request.getParameter("leaveReason"));
			empSal.setLeavingReasonEsic(request.getParameter("lrEsic"));
			empSal.setLeavingReasonPf(request.getParameter("lrForPF"));

			empSal.setDelStatus(1);
			empSal.setExInt1(0);
			empSal.setExInt2(0);
			empSal.setExVar1("NA");
			empSal.setExVar2("NA");

			EmpSalaryInfo empIdSal = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdSalary",
					empSal, EmpSalaryInfo.class);

			if (empIdSal != null) {

				int allwnSalId = 0;
				List<EmpSalAllowance> allowncList = new ArrayList<EmpSalAllowance>();

				for (int i = 0; i < allowanceList.size(); i++) {

					EmpSalAllowance empSellAllwance = new EmpSalAllowance();
					double allwncValue = 0;
					try {
						allwncValue = Double
								.parseDouble(request.getParameter("allownces" + allowanceList.get(i).getAllowanceId()));
					} catch (Exception e) {
						allwncValue = 0;
					}
					try {
						allwnSalId = Integer.parseInt(
								request.getParameter("empSalAllownaceId" + allowanceList.get(i).getAllowanceId()));
					} catch (Exception e) {
						allwnSalId = 0;
					}

					if (allwncValue >= 0) {

						empSellAllwance.setEmpSalAllowanceId(allwnSalId);
						empSellAllwance.setEmpId(Integer.parseInt(request.getParameter("empId")));
						empSellAllwance.setAllowanceId(allowanceList.get(i).getAllowanceId());
						empSellAllwance.setAllowanceValue(allwncValue);
						empSellAllwance.setMakerEnterDatetime(currDate);

						empSellAllwance.setDelStatus(1);
						empSellAllwance.setExInt1(0);
						empSellAllwance.setExInt2(0);
						empSellAllwance.setExVar1("NA");
						empSellAllwance.setExVar2("NA");

						allowncList.add(empSellAllwance);

					}

				}

				EmpSalAllowance[] allowance = Constants.getRestTemplate().postForObject(
						Constants.url + "/saveEmpSalAllowanceInfo", allowncList, EmpSalAllowance[].class);

				if (allowance != null) {

					session.setAttribute("successMsg", "Record Updated Successfully");
					String empEncryptId = FormValidation.Encrypt(String.valueOf(empId));
					//System.out.println("Emp Encrypt Id---" + empEncryptId);

					redirect = "redirect:/employeeEdit?empId=" + empEncryptId;
				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
					redirect = "redirect:/employeeAdd";
				}
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
				redirect = "redirect:/employeeAdd";
			}

			/*
			 * }else { redirect = "redirect:/employeeAdd"; }
			 */
		} catch (Exception e) {
			e.printStackTrace();

		}
		return redirect;

	}

	/***************************************************************************************/
	@RequestMapping(value = "/submitInsertEmpDoc", method = RequestMethod.POST)
	public String submitInsertEmpDoc(@RequestParam("doc") List<MultipartFile> doc, HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();

		try {
			String imageName = new String();
			String img = new String();
			VpsImageUpload upload = new VpsImageUpload();

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

			// LoginResponse obj = (LoginResponse) session.getAttribute("UserDetail");
			// List<EmployeDoc> list = new ArrayList<>();

			// String base64encodedString = request.getParameter("empId");
			// int empId = Integer.parseInt(FormValidation.DecodeKey(base64encodedString));

			List<EmployeDoc> list = new ArrayList<EmployeDoc>();
			int docId = 0;
			int empId = Integer.parseInt(request.getParameter("empId"));
			int docTypeId = 0;
			for (int j = 0; j < empDocList.size(); j++) {

				docTypeId = Integer.parseInt(request.getParameter("docType" + empDocList.get(j).getDoctypeId()));
				 
				try {
					docId = Integer.parseInt(request.getParameter("empDocId" + empDocList.get(j).getDoctypeId()));

				} catch (Exception e) {
					docId = 0;

				}

				// System.out.println(docId + " - " + empId + " - " + docTypeId);
				img = doc.get(j).getOriginalFilename();
				imageName = empId + "_" + docTypeId + "_" + doc.get(j).getOriginalFilename() + "_" + dateTimeInGMT.format(date);

				System.out.println(img);

				// System.out.println("Image Upload");
				try {

					if (img != "" && img != null) {

						Info info = upload.saveUploadedImge(doc.get(j), Constants.imageSaveUrl, imageName,
								Constants.allextension, 0, 0, 0, 0, 0);

						if (info.isError() == false) { 
							
							EmployeDoc employeDoc = new EmployeDoc();
							employeDoc.setDocId(docId);
							employeDoc.setEmpId(empId);
							employeDoc.setDoctypeId(docTypeId);
							employeDoc.setIsActive(1);
							employeDoc.setDelStatus(1);
							employeDoc.setMakerUserId(100);
							employeDoc.setMakerEnterDatetime(sf.format(date));
							employeDoc.setDocImage(imageName);

							employeDoc.setExInt1(0);
							employeDoc.setExInt2(0);
							employeDoc.setExVar1("NA");
							employeDoc.setExVar2("NA");

							list.add(employeDoc);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			EmployeDoc[] res = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmpDocList", list,
					EmployeDoc[].class);
			if (res != null) {
				session.setAttribute("successMsg", "Record Updated Successfully");
				String empEncryptId = FormValidation.Encrypt(String.valueOf(empId)); 
				redirect = "redirect:/employeeEdit?empId=" + empEncryptId;
			} else { 
				session.setAttribute("errorMsg", "Failed to Update Record");
				redirect = "redirect:/employeeAdd";
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Insert Record");
		}
		return redirect;
	}

	@RequestMapping(value = "/getUniqEmpCodeResp", method = RequestMethod.GET)
	public @ResponseBody int getUniqEmpCodeResp(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		int flag = 0;
		try {
			String empCode = request.getParameter("empCode");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empCode", empCode);
			EmployeeMaster res = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpInfoByEmpCode", map,
					EmployeeMaster.class);

			if (res != null) {
				flag = 0;
			} else {
				flag = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;

	}
}
