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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.Allowances;
import com.ats.hreasy.model.Bank;
import com.ats.hreasy.model.Contractor;
import com.ats.hreasy.model.Department;
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.EmpSalAllowance;
import com.ats.hreasy.model.EmpSalaryInfo;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
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
	int flag=0;
	EmployeeMaster empSave=null;
	TblEmpInfo empIdInfo  = null;
	TblEmpNominees empIdNom = null;
	TblEmpBankInfo empIdBank  = null;
	EmpSalaryInfo empIdSal = null;
	EmpSalAllowance empSalAllowanceId = null;
	List<Allowances> allowanceList = new ArrayList<Allowances>();
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
		EmployeeMaster emp=null;
		try {
			if(empSave!=null) {
				emp = new EmployeeMaster();
			}
			else
			{
				emp=empSave;
			}
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
			//System.out.println("Location List-------------"+locationList);
			
			Department[] department = Constants.getRestTemplate().postForObject(Constants.url + "/getAllDepartments", map,
			Department[].class);
			List<Department> departmentList = new ArrayList<Department>(Arrays.asList(department));
			//System.out.println("DepartmentList List-------------"+departmentList);
			
			Designation[] designation = Constants.getRestTemplate().postForObject(Constants.url + "/getAllDesignations", map, 
			Designation[].class);
			List<Designation> designationList = new ArrayList<Designation>(Arrays.asList(designation));
			//System.out.println("DesignationList List-------------"+designationList);
			
			Contractor[] contractor = Constants.getRestTemplate().postForObject(Constants.url + "/getAllContractors", map , 
			Contractor[].class);
			List<Contractor> contractorsList = new ArrayList<Contractor>(Arrays.asList(contractor));
			//System.out.println("ContractorsList List-------------"+contractorsList);
			
			
			Bank[] bank = Constants.getRestTemplate().postForObject(Constants.url + "/getAllBanks", map, 
					Bank[].class);
			List<Bank> bankList = new ArrayList<Bank>(Arrays.asList(bank));
			
			Allowances[] allowanceArr = Constants.getRestTemplate().getForObject(Constants.url + "/getAllAllowances", 
					Allowances[].class);
			allowanceList = new ArrayList<Allowances>(Arrays.asList(allowanceArr));
			
			model = new ModelAndView("master/addEmployee");
			model.addObject("locationList", locationList);
			model.addObject("deptList", departmentList);
			model.addObject("designationList", designationList);
			model.addObject("contractorsList", contractorsList);
			model.addObject("bankList", bankList);
			model.addObject("allowanceList", allowanceList);
			model.addObject("emp", emp);
			model.addObject("flag", flag);
			
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	
	
	@RequestMapping(value= "/insertEmployeeBasicInfo", method = RequestMethod.POST)  
	public String submitInsertEmployeeUserInfo(HttpServletRequest request, HttpServletResponse response){
		 empSave = new EmployeeMaster();
		
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
					flag=1;
					TblEmpInfo empInfo = new TblEmpInfo();
					empInfo.setEmpId(empSave.getEmpId());
					
					 empIdInfo =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdInfo", empInfo,
							TblEmpInfo.class);
					
					TblEmpBankInfo empBank = new TblEmpBankInfo();
					empBank.setEmpId(empSave.getEmpId());
					
					empIdBank =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdBank", empBank,
							TblEmpBankInfo.class);
					
					TblEmpNominees empNominee = new TblEmpNominees();
					empNominee.setEmpId(empSave.getEmpId());
					
					empIdNom =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdNominee", empNominee,
							TblEmpNominees.class);
					
					EmpSalaryInfo empSal = new EmpSalaryInfo();
					empSal.setEmpId(empSave.getEmpId());
					
					empIdSal =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdSalary", empSal,
					EmpSalaryInfo.class);
					
					EmpSalAllowance allowance = new EmpSalAllowance();
					allowance.setEmpId(empSave.getEmpId());
					
				//	empSalAllowanceId =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmpSalAllowanceIds", allowance,
					//		EmpSalAllowance.class);
					
					User user = new User();
					user.setEmpId(empSave.getEmpId());
					
					User empIdUser =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdUser", user,
							User.class);
										
					
					System.out.println("Success");
					//redirect="redirect:/employeeAdd";
					redirect="redirect:/employeeEdit";
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
	
	@RequestMapping(value = "/employeeEdit", method = RequestMethod.GET)
	public ModelAndView employeeEdit(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {
			
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
		//	System.out.println("Location List-------------"+locationList);
			
			Department[] department = Constants.getRestTemplate().postForObject(Constants.url + "/getAllDepartments", map,
			Department[].class);
			List<Department> departmentList = new ArrayList<Department>(Arrays.asList(department));
			//System.out.println("DepartmentList List-------------"+departmentList);
			
			Designation[] designation = Constants.getRestTemplate().postForObject(Constants.url + "/getAllDesignations", map, 
			Designation[].class);
			List<Designation> designationList = new ArrayList<Designation>(Arrays.asList(designation));
			//System.out.println("DesignationList List-------------"+designationList);
			
			Contractor[] contractor = Constants.getRestTemplate().postForObject(Constants.url + "/getAllContractors", map , 
			Contractor[].class);
			List<Contractor> contractorsList = new ArrayList<Contractor>(Arrays.asList(contractor));
			//System.out.println("ContractorsList List-------------"+contractorsList);
			

			Bank[] bank = Constants.getRestTemplate().postForObject(Constants.url + "/getAllBanks", map, 
					Bank[].class);
			List<Bank> bankList = new ArrayList<Bank>(Arrays.asList(bank));
			
			Allowances[] allowanceArr = Constants.getRestTemplate().getForObject(Constants.url + "/getAllAllowances", 
					Allowances[].class);
			allowanceList = new ArrayList<Allowances>(Arrays.asList(allowanceArr));
			
			model = new ModelAndView("master/addEmployee");
			model.addObject("locationList", locationList);
			model.addObject("deptList", departmentList);
			model.addObject("designationList", designationList);
			model.addObject("contractorsList", contractorsList);
			model.addObject("bankList", bankList);
			model.addObject("allowanceList", allowanceList);
			
			model.addObject("emp", empSave);
			model.addObject("empPersInfo", empIdInfo);
			model.addObject("empNom", empIdNom);
			model.addObject("empBank", empIdBank);
			model.addObject("empAllowanceId", empSalAllowanceId);
			flag=1;
			model.addObject("flag", flag);
			
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	@RequestMapping(value= "/submitEmpOtherInfo", method = RequestMethod.POST)  
	public String submitEmpOtherInfo(HttpServletRequest request, HttpServletResponse response){
		
		try {
			TblEmpInfo empInfo = new TblEmpInfo();
			if(empIdInfo!=null) {
				empInfo.setEmpInfoId(empIdInfo.getEmpInfoId());
				empInfo.setEmpId(empIdInfo.getEmpId());
				empInfo.setMiddleName(request.getParameter("midname"));
				empInfo.setMiddleNameRelation(request.getParameter("relation"));
				empInfo.setDob(DateConvertor.convertToYMD(request.getParameter("dob")));
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
				empInfo.setUniform_size(request.getParameter("uniformsize"));
					
					System.out.println("TblEmpInfo----"+empInfo);
					 empIdInfo =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdInfo", empInfo,
								TblEmpInfo.class);
					if(empIdInfo!=null) {
						System.out.println("Sucess---------"+empIdInfo);
						redirect = "redirect:/employeeEdit";
					}else {
						System.err.println("Fail----------"+empIdInfo);
						redirect = "redirect:/employeeEdit";
					}
			
			}else {
				redirect = "redirect:/employeeAdd";
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return redirect;
		
	}
	
	
	
	@RequestMapping(value= "/submitEmpRelationInfo", method = RequestMethod.POST)  
	public String submitEmpRelationInfo(HttpServletRequest request, HttpServletResponse response){
		
		try {
			TblEmpNominees empNominee = new TblEmpNominees();
			if(empIdNom!=null) {
				
				empNominee.setNomineeId(empIdNom.getNomineeId());
				empNominee.setEmpId(empIdNom.getEmpId());
				
				
				empNominee.setName(request.getParameter("name"));
				empNominee.setDob(DateConvertor.convertToYMD(request.getParameter("dob")));
				empNominee.setRelation(request.getParameter("relation"));
				empNominee.setOccupation1(request.getParameter("occupation"));
				
				empNominee.setName2(request.getParameter("name2"));
				empNominee.setDob2(DateConvertor.convertToYMD(request.getParameter("dob2")));
				empNominee.setRelation2(request.getParameter("relation2"));
				empNominee.setOccupation2(request.getParameter("occupation2"));
				
				
				empNominee.setName3(request.getParameter("name3"));
				empNominee.setDob3(DateConvertor.convertToYMD(request.getParameter("dob3")));
				empNominee.setRelation3(request.getParameter("relation3"));
				empNominee.setOccupation3(request.getParameter("occupation3"));
				
				empNominee.setName4(request.getParameter("name4"));
				empNominee.setDob4(DateConvertor.convertToYMD(request.getParameter("dob4")));
				empNominee.setRelation4(request.getParameter("relation4"));
				empNominee.setOccupation4(request.getParameter("occupation4"));
				
				empNominee.setName5(request.getParameter("name5"));
				empNominee.setDob5(DateConvertor.convertToYMD(request.getParameter("dob5")));
				empNominee.setRelation5(request.getParameter("relation5"));
				empNominee.setOccupation5(request.getParameter("occupation5"));
				
				empNominee.setName6(request.getParameter("name6"));
				empNominee.setDob6(DateConvertor.convertToYMD(request.getParameter("dob6")));
				empNominee.setRelation6(request.getParameter("relation6"));
				empNominee.setOccupation6(request.getParameter("occupation6"));
					
				
					System.out.println("TblEmpNominees----"+empNominee);
					empIdNom =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdNominee", empNominee,
							 TblEmpNominees.class);
					if(empIdInfo!=null) {
						System.out.println("Sucess---------"+empIdInfo);
						redirect = "redirect:/employeeEdit";
					}else {
						System.err.println("Fail----------"+empIdInfo);
						redirect = "redirect:/employeeAdd";
					}
			
			}else {
				redirect = "redirect:/employeeAdd";
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return redirect;
		
	}
	
	@RequestMapping(value= "/submitEmpBankInfo", method = RequestMethod.POST)  
	public String submitEmpBankInfo(HttpServletRequest request, HttpServletResponse response){
		
		try {
			TblEmpBankInfo empBank = new TblEmpBankInfo();
			if(empIdBank!=null) {
				empBank.setBankId(Integer.parseInt(request.getParameter("bankId")));
				empBank.setEmpId(empIdBank.getEmpId());
				empBank.setBankInfoId(empIdBank.getBankInfoId());
				empBank.setAccNo(Integer.parseInt(request.getParameter("accNo")));
				
					System.out.println("TblEmpBankInfo----"+empBank);
					empIdBank =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdBank", empBank,
							TblEmpBankInfo.class);
					if(empIdInfo!=null) {
						System.out.println("Sucess---------"+empIdInfo);
						redirect = "redirect:/employeeEdit";
					}else {
						System.err.println("Fail----------"+empIdInfo);
						redirect = "redirect:/employeeEdit";
					}
			
			}else {
				redirect = "redirect:/employeeAdd";
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return redirect;
		
	}
	
	@RequestMapping(value= "/insertEmployeeAllowancesInfo", method = RequestMethod.POST)  
	public String insertEmployeeBasicInfo(HttpServletRequest request, HttpServletResponse response){
		
		try {
			EmpSalaryInfo empSal = new EmpSalaryInfo();
			double basic = 0;
			double pfEmpPer = 0;
			double pfEmployerPer = 0;
			
			double empEsicPer = 0;
			double employerEsicPer = 0;
			
			try {
				 basic = Double.parseDouble(request.getParameter("basic"));
				 pfEmpPer = Double.parseDouble(request.getParameter("pfEmpPer"));
				 pfEmployerPer = Double.parseDouble(request.getParameter("pfEmployerPer"));				
				
				 empEsicPer = Double.parseDouble(request.getParameter("empEsicPer"));
				 employerEsicPer = Double.parseDouble(request.getParameter("employerEsicPer"));
				
			}catch (Exception e) {
				basic = 0;
				 pfEmpPer = 0;
				 pfEmployerPer = 0;
				
				 empEsicPer = 0;
				 employerEsicPer = 0;
			}
			
			
			if(empIdSal!=null) {
			
				empSal.setSalaryInfoId(empIdSal.getSalaryInfoId());
				empSal.setEmpId(empIdSal.getEmpId());
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
			
				
					System.out.println("TblEmpBankInfo----"+empSal);
					empIdSal =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployeeIdSalary", empSal,
							EmpSalaryInfo.class);
				
					
					
					if(empIdSal!=null) {
						System.out.println("Sucess---------"+empIdSal);
						int allwnSalId = 0;
						List<EmpSalAllowance> allowncList = new ArrayList<EmpSalAllowance>();
						for (int i = 0; i < allowanceList.size(); i++) {
							
							
							System.out.println("--------------"+allowanceList);
							EmpSalAllowance empSellAllwance =  new EmpSalAllowance();
							
							double allwncValue= 0;
							try {
								allwncValue = Double.parseDouble(request.getParameter("allownces"+allowanceList.get(i).getAllowanceId()));
							}catch (Exception e) {
								allwncValue = 0;
							}
							
							
						if (allwncValue > 0) {
							try {
								allwnSalId = Integer.parseInt(request.getParameter("empSalAllownaceId"));
							}catch (Exception e) {
								allwnSalId = 0;
							}
								empSellAllwance.setEmpSalAllowanceId(allwnSalId);							
								empSellAllwance.setEmpId(Integer.parseInt(request.getParameter("empId")));
								empSellAllwance.setAllowanceId(allowanceList.get(i).getAllowanceId());
								empSellAllwance.setAllowanceValue(allwncValue);	
								empSellAllwance.setMakerEnterDatetime(currDate);
								
								allowncList.add(empSellAllwance);
								
							}
							
							System.out.println("allowncList------"+allowncList);
						}
						
						
						List<Allowances> allowance = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmpSalAllowanceInfo", allowncList,
							List.class);
						redirect = "redirect:/employeeEdit";
					}else {
						System.err.println("Fail----------"+empIdSal);
						redirect = "redirect:/employeeEdit";
					}
			
			}else {
				redirect = "redirect:/employeeAdd";
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return redirect;
		
	}
	
	
	
	
}
