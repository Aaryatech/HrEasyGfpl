package com.ats.hreasy.controller;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.Bank;
import com.ats.hreasy.model.Contractor;
import com.ats.hreasy.model.Department;
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.TblEmpBankInfo;
import com.ats.hreasy.model.TblEmpInfo;
import com.ats.hreasy.model.TblEmpNominees;

@Controller
@Scope("session")
public class EmployeeController {

	Date date = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String currDate = sf.format(date);
	/******************************Employee*********************************/
	@RequestMapping(value = "/showEmployeeList", method = RequestMethod.GET)
	public ModelAndView showEmployeeList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("showEmployeeList", "showEmployeeList", 1,
			 * 0, 0, 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */
			model = new ModelAndView("master/employeeList");
			
			/*Bank[] bank = Constants.getRestTemplate().getForObject(Constants.url + "/getAllBanks",
					Bank[].class);

			List<Bank> bankList = new ArrayList<Bank>(Arrays.asList(bank));

			for (int i = 0; i < bankList.size(); i++) {

				bankList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(bankList.get(i).getBankId())));
			}*/

			model.addObject("addAccess", 0);
			model.addObject("editAccess", 0);
			model.addObject("deleteAccess", 0);
			

			/*
			 * Info add = AcessController.checkAccess("showEmployeeList",
			 * "showEmployeeList", 0, 1, 0, 0, newModuleList); Info edit =
			 * AcessController.checkAccess("showEmployeeList", "showEmployeeList", 0,
			 * 0, 1, 0, newModuleList); Info delete =
			 * AcessController.checkAccess("showEmployeeList", "showEmployeeList", 0,
			 * 0, 0, 1, newModuleList);
			 * 
			 * if (add.isError() == false) { System.out.println(" add   Accessable ");
			 * model.addObject("addAccess", 0);
			 * 
			 * } if (edit.isError() == false) { System.out.println(" edit   Accessable ");
			 * model.addObject("editAccess", 0); } if (delete.isError() == false) {
			 * System.out.println(" delete   Accessable "); model.addObject("deleteAccess",
			 * 0);
			 * 
			 * }
			 */
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/employeeAdd", method = RequestMethod.GET)
	public ModelAndView employeeAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {
			EmployeeMaster emp = new EmployeeMaster();
			
			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("departmentAdd", "showDepartmentList", 0, 1, 0,
			 * 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", 1);
			
			Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
			Location[].class);
			List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));
			System.out.println("Location List-------------"+locationList);
			
			Department[] department = Constants.getRestTemplate().postForObject(Constants.url + "/getAllDepartments", map,
			Department[].class);
			List<Department> departmentList = new ArrayList<Department>(Arrays.asList(department));
			System.out.println("DepartmentList List-------------"+departmentList);
			
			Designation[] designation = Constants.getRestTemplate().postForObject(Constants.url + "/getAllDesignations", map, 
			Designation[].class);
			List<Designation> designationList = new ArrayList<Designation>(Arrays.asList(designation));
			System.out.println("DesignationList List-------------"+designationList);
			
			Contractor[] contractor = Constants.getRestTemplate().postForObject(Constants.url + "/getAllContractors", map , 
			Contractor[].class);
			List<Contractor> contractorsList = new ArrayList<Contractor>(Arrays.asList(contractor));
			System.out.println("ContractorsList List-------------"+contractorsList);
			
			model = new ModelAndView("master/addEmployee");
			model.addObject("locationList", locationList);
			model.addObject("deptList", departmentList);
			model.addObject("designationList", designationList);
			model.addObject("contractorsList", contractorsList);
			model.addObject("emp", emp);
			
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	@RequestMapping(value = "/employeeEdit", method = RequestMethod.GET)
	public ModelAndView employeeEdit(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {
			Bank bank = new Bank();
			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("departmentAdd", "showDepartmentList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", 1);
					Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
					Location[].class);
					List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));
					
					Department[] department = Constants.getRestTemplate().postForObject(Constants.url + "/getAllDepartments", map,
							Department[].class);
							List<Department> departmentList = new ArrayList<Department>(Arrays.asList(department));
			
				model = new ModelAndView("master/employeeAdd");
				model.addObject("locationList", locationList);
				model.addObject("deptList", departmentList);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	
	@RequestMapping(value= "/insertEmployeeBasicInfo", method = RequestMethod.POST)  
	public String submitInsertEmployeeUserInfo(HttpServletRequest request, HttpServletResponse response){
		EmployeeMaster empSave = new EmployeeMaster();
		String redirect = "";
		try {
			
				EmployeeMaster emp =new EmployeeMaster();
				int empId = 0;
				int contract = 0;
				int deptId= 0;
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
				}catch (Exception e) {
					empId = 0;
					contract = 0;
				}
				
				String mob2 = request.getParameter("mobile2");
				System.out.println("Mobile-----"+mob2);
				String landline = request.getParameter("landline");
				
				if( mob2 == ""  || mob2==null) {
					emp.setMobileNo2("NA");
				}else {
					emp.setMobileNo2(mob2);
				}
				
				if(landline=="" || landline==null) {
					emp.setResidenceLandNo("NA");
				}else {
					emp.setResidenceLandNo(landline);
				}
				
				emp.setEmpId(empId);
				
				emp.setFirstName(request.getParameter("fname"));
				emp.setMiddleName(request.getParameter("mname"));
				emp.setSurname(request.getParameter("sname"));
				
				emp.setMobileNo1(request.getParameter("mobile1"));				
				
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
				
				
				 empSave =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployee", emp,
						EmployeeMaster.class);
				 System.out.println("res = "+empSave);
				
				 
				String encryptEmpId = FormValidation.Encrypt(String.valueOf(empId));
				System.out.println("Encrypted Employee = -------------"+encryptEmpId);
				
				if(empSave!=null) {
					TblEmpInfo empInfo = new TblEmpInfo();
					empInfo.setEmpId(empSave.getEmpId());
					
					TblEmpInfo empIdInfo =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdInfo", empInfo,
							TblEmpInfo.class);
					
					TblEmpBankInfo empBank = new TblEmpBankInfo();
					empBank.setEmpId(empSave.getEmpId());
					
					TblEmpBankInfo empIdBank =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdBank", empBank,
							TblEmpBankInfo.class);
					
					TblEmpNominees empNominee = new TblEmpNominees();
					empNominee.setEmpId(empSave.getEmpId());
					
					TblEmpNominees empIdNom =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdNominee", empNominee,
							TblEmpNominees.class);
					
					System.out.println("Success");
					redirect="redirect:/employeeAdd";
					//redirect="redirect:/employeeAdd/empId="+encryptEmpId+"&tabId="+1;
				}else {
					System.err.println("Fail");
					redirect="redirect:/employeeAdd";
				}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Success----------------"+empSave);
		return redirect;
		
	}
	
	
	@RequestMapping(value= "/employeeAdd/{empId}/{tabId}", method = RequestMethod.POST)  
	public@ResponseBody Info submitEmpOtherInfo(HttpServletRequest request, HttpServletResponse response){
		try {
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
