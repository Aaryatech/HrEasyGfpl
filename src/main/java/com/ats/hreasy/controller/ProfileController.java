package com.ats.hreasy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Contractor;
import com.ats.hreasy.model.Department;
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.EmpSalAllowance;
import com.ats.hreasy.model.EmpSalaryInfo;
import com.ats.hreasy.model.EmpType;
import com.ats.hreasy.model.EmployeDoc;
import com.ats.hreasy.model.EmployeeAllDetails;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.MstCompany;
import com.ats.hreasy.model.MstEmpType;
import com.ats.hreasy.model.SkillRates;
import com.ats.hreasy.model.TblEmpBankInfo;
import com.ats.hreasy.model.TblEmpInfo;
import com.ats.hreasy.model.TblEmpNominees;
import com.ats.hreasy.model.User;
import com.ats.hreasy.model.ViewEmployee;

import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Controller
@Scope("session")
public class ProfileController {

	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
	public String getProfile(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "profilemodal";

		MultiValueMap<String, Object> map = null;
		EmployeeAllDetails empAllDtls = null;
		try {

			empAllDtls = new EmployeeAllDetails();
			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);

			System.out.println("Decrypt-----"+ empId);

			map = new LinkedMultiValueMap<>();
			map.add("empId", Integer.parseInt(empId));

			ViewEmployee empInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeAllInfo", map,
					ViewEmployee.class);
			
			System.out.println("empInfo--------"+ empInfo);
			String accessblLoc = getAccessblLoc(Integer.parseInt(empId));
			//System.out.println("Accessable Location--------" + accessblLoc);
			empInfo.setAcciessbleLocations(accessblLoc);
			
			model.addAttribute("empInfo", empInfo);
			
			map = new LinkedMultiValueMap<>();
			map.add("empId", Integer.parseInt(empId));
			EmpSalAllowance[] empSalAllowance = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmployeeSalAllowances", map, EmpSalAllowance[].class);

			List<EmpSalAllowance> empAllowncList = new ArrayList<EmpSalAllowance>(Arrays.asList(empSalAllowance));
			System.out.println("EmpSalAllowance Info-------" + empAllowncList);
			model.addAttribute("empAllowncList", empAllowncList);

			//System.err.println("Employee Detail Data-----------" + empAllDtls);
		
		} catch (Exception e) {
			e.getMessage();
		}
		
	
		return mav;

	}

	@RequestMapping(value = "/getProfilenormal", method = RequestMethod.GET)
	public String getProfilenormal(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "profilenormal";

		MultiValueMap<String, Object> map = null;
		EmployeeAllDetails empAllDtls = null;
		try {

			empAllDtls = new EmployeeAllDetails();
			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);

			System.out.println("Decrypt-----" + empId);

			map = new LinkedMultiValueMap<>();
			map.add("empId", Integer.parseInt(empId));

			ViewEmployee empInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeAllInfo", map,
					ViewEmployee.class);
			
			//System.out.println("empInfo--------" + empInfo);
			String accessblLoc = getAccessblLoc(Integer.parseInt(empId));
			System.out.println("Accessable Location--------" + accessblLoc);
			empInfo.setAcciessbleLocations(accessblLoc);
			
			model.addAttribute("empInfo", empInfo);
			
			map = new LinkedMultiValueMap<>();
			map.add("empId", Integer.parseInt(empId));
			EmpSalAllowance[] empSalAllowance = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmployeeSalAllowances", map, EmpSalAllowance[].class);

			List<EmpSalAllowance> empAllowncList = new ArrayList<EmpSalAllowance>(Arrays.asList(empSalAllowance));
			//System.out.println("EmpSalAllowance Info-------" + empAllowncList);
			model.addAttribute("empAllowncList", empAllowncList);

			//System.err.println("Employee Detail Data-----------" + empAllDtls);
		
		} catch (Exception e) {
			e.getMessage();
		}
		
	
		return mav;

	}


	public String getAccessblLoc(int empId) {
		MultiValueMap<String, Object> map = null;

		map = new LinkedMultiValueMap<>();
		map.add("EmpId", empId);
		User user = Constants.getRestTemplate().postForObject(Constants.url + "/findUserInfoByEmpId", map, User.class);
		System.out.println("Accessable Location Ids--------"+user.getLocId());

		map.add("locIds", user.getLocId());
		Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationsByIds", map,
				Location[].class);

		List<Location> locList = new ArrayList<Location>(Arrays.asList(location));
		//System.out.println("Accessable Location Found--------"+locList);

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < location.length; i++) {
			sb.append(location[i].getLocName());
			sb.append(" / ");
		}
		String str = sb.toString();
		//System.out.println("Built String-------------"+str);
		return str;
	}

	public String getEmpAccessRoll(int empTypeId) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("empTypeId", empTypeId);
		EmpType empType = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpTypeById", map,
				EmpType.class);
		return empType.getEmpTypeName();
	}

	public String getEmpType(int empType) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("empTypeId", empType);
		MstEmpType empTypeList = Constants.getRestTemplate().postForObject(Constants.url + "/getMstEmpTypeById", map,
				MstEmpType.class);
		return empTypeList.getName();
	}

	public String getEmpSkill(int skillId) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("skillId", skillId);
		SkillRates skill = Constants.getRestTemplate().postForObject(Constants.url + "/getSkillById", map,
				SkillRates.class);
		return skill.getName();
	}

	public String getContractor(int contractorId) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("contractorId", contractorId);
		Contractor contractor = Constants.getRestTemplate().postForObject(Constants.url + "/getContractorById", map,
				Contractor.class);
		return contractor.getOwner();
	}

	public String getDesignation(int designationId) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("desigId", designationId);
		Designation designation = Constants.getRestTemplate().postForObject(Constants.url + "/getDesignationById", map,
				Designation.class);
		return designation.getName();
	}

	public String getLocation(int locationId) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("locId", locationId);
		Location location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationById", map,
				Location.class);
		return location.getLocName();
	}

	public String getDepartment(int departId) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("deptId", departId);
		Department department = Constants.getRestTemplate().postForObject(Constants.url + "/getDepartmentById", map,
				Department.class);
		return department.getName();
	}

	public String getCompany(int cmpCode) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("companyId", cmpCode);
		MstCompany comp = Constants.getRestTemplate().postForObject(Constants.url + "/getCompanyById", map,
				MstCompany.class);

		return comp.getCompanyName();
	}

	@RequestMapping(value = "/showEmployeeProfile", method = RequestMethod.GET)
	@ResponseBody
	public EmployeeAllDetails showEmployeeProfile(HttpServletRequest request, HttpServletResponse response) {
		MultiValueMap<String, Object> map = null;
		EmployeeAllDetails empAllDtls = null;
		try {

			empAllDtls = new EmployeeAllDetails();
			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);

			//System.out.println("Decrypt-----" + empId);

			map = new LinkedMultiValueMap<>();
			map.add("empId", Integer.parseInt(empId));

			ViewEmployee empInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeAllInfo", map,
					ViewEmployee.class);
			
			//System.out.println("empInfo--------" + empInfo);
			String accessblLoc = getAccessblLoc(Integer.parseInt(empId));
			//System.out.println("Accessable Location--------" + accessblLoc);
			empInfo.setAcciessbleLocations(accessblLoc);
			empAllDtls.setEmpDtl(empInfo);

			map = new LinkedMultiValueMap<>();
			map.add("empId", Integer.parseInt(empId));
			EmpSalAllowance[] empSalAllowance = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmployeeSalAllowances", map, EmpSalAllowance[].class);

			List<EmpSalAllowance> empAllowncList = new ArrayList<EmpSalAllowance>(Arrays.asList(empSalAllowance));
			System.out.println("EmpSalAllowance Info-------" + empAllowncList);
			empAllDtls.setEmpAllowncDtl(empAllowncList);

			//System.err.println("Employee Detail Data-----------" + empAllDtls);
		
		} catch (Exception e) {
			e.getMessage();
		}
		return empAllDtls;

	}
	
	@RequestMapping(value = "/getPieChart", method = RequestMethod.GET)
	public String getPieChart(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "showPieChart";
		return mav;
	}
}
