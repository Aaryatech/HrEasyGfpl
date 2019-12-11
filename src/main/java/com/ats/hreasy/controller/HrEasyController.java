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
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.Contractor;
import com.ats.hreasy.model.Department;
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.Info;

@Controller
@Scope("session")
public class HrEasyController {

	Date date = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String currDate = sf.format(date);
	
	/******************************Designation********************************/
	@RequestMapping(value = "/showDesignationList", method = RequestMethod.GET)
	public ModelAndView showLocationList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("showDesignationList", "showDesignationList", 1,
			 * 0, 0, 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */
			model = new ModelAndView("master/designationList");
			
			Designation[] designation = Constants.getRestTemplate().getForObject(Constants.url + "/getAllDesignations",
					Designation[].class);

			List<Designation> designationList = new ArrayList<Designation>(Arrays.asList(designation));

			for (int i = 0; i < designationList.size(); i++) {

				designationList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(designationList.get(i).getDesigId())));
			}

			model.addObject("addAccess", 0);
			model.addObject("editAccess", 0);
			model.addObject("deleteAccess", 0);
			model.addObject("designationList", designationList);

			/*
			 * Info add = AcessController.checkAccess("showDesignationList",
			 * "showDesignationList", 0, 1, 0, 0, newModuleList); Info edit =
			 * AcessController.checkAccess("showDesignationList", "showDesignationList", 0,
			 * 0, 1, 0, newModuleList); Info delete =
			 * AcessController.checkAccess("showDesignationList", "showDesignationList", 0,
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
	
	
	@RequestMapping(value = "/dsesignationAdd", method = RequestMethod.GET)
	public ModelAndView locationAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {
			Designation desig = new Designation();
			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("dsesignationAdd", "showDesignationList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/
				model = new ModelAndView("master/designationAdd");
				model.addObject("desig", desig);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	@RequestMapping(value="/submitInsertDesignation", method=RequestMethod.POST)
	public String submitInsertDesignation(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		try {

			Designation desig = new Designation();

			desig.setCompanyId(1);
			desig.setDelStatus(1);
			desig.setDesigId(Integer.parseInt(request.getParameter("desigId")));
			desig.setExInt1(0);
			desig.setExInt2(0);
			desig.setExVar1("NA");
			desig.setExVar2("NA");
			desig.setIsActive(1);
			desig.setMakerEnterDatetime(currDate);
			desig.setName(request.getParameter("desigName"));
			desig.setNameSd(request.getParameter("desigShortName"));
			desig.setRemarks(request.getParameter("remark"));

			Designation saveDesig = Constants.getRestTemplate().postForObject(Constants.url + "/saveDesignation", desig,
					Designation.class);
			if (saveDesig != null) {
				session.setAttribute("successMsg", "Designation Updated Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
			}

		}catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Update Record");
		}
	
		return "redirect:/showDesignationList";
		
	}
	
	
	@RequestMapping(value = "/editDesignation", method = RequestMethod.GET)
	public ModelAndView editDesignation(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Designation desig = new Designation();
		ModelAndView model = null;
		try {
			model = new ModelAndView("master/designationAdd");
			
			String base64encodedString = request.getParameter("desigId");
			String desigId = FormValidation.DecodeKey(base64encodedString);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("desigId", desigId);
			map.add("companyId", 1);
			desig = Constants.getRestTemplate().postForObject(Constants.url + "/getDesignationById", map,
					Designation.class);
			model.addObject("desig", desig);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
		
	}
	
	
	@RequestMapping(value = "/deleteDesignation", method = RequestMethod.GET)
	public String deleteDesignation(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String base64encodedString = request.getParameter("desigId");
			String desigId = FormValidation.DecodeKey(base64encodedString);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("desigId", desigId);
			
			Integer emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpByDesignationId", map,
					Integer.class);
			
			System.out.println("Emp-------------------"+emp);
			if(emp==0) {
			
				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deleteDesignationById", map,
						Info.class);
				
				if (res.isError()) {
					session.setAttribute("errorMsg", "Failed to Delete");
				} else {
					session.setAttribute("successMsg", "Deleted Successfully");
					
				}
			}else {
				session.setAttribute("errorMsg", "Failed to Delete - Designation assigned to "+ emp +" employees ");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		
		
		return "redirect:/showDesignationList";
	}

	
	
	/******************************Contractor********************************/
	@RequestMapping(value = "/showContractorsList", method = RequestMethod.GET)
	public ModelAndView showContractorsList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("showContractorsList", "showContractorsList", 1,
			 * 0, 0, 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */
			model = new ModelAndView("master/contractorList");
			
			Contractor[] contractor = Constants.getRestTemplate().getForObject(Constants.url + "/getAllContractors",
					Contractor[].class);

			List<Contractor> contractorsList = new ArrayList<Contractor>(Arrays.asList(contractor));

			for (int i = 0; i < contractorsList.size(); i++) {

				contractorsList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(contractorsList.get(i).getContractorId())));
			}

			model.addObject("addAccess", 0);
			model.addObject("editAccess", 0);
			model.addObject("deleteAccess", 0);
			model.addObject("contractorsList", contractorsList);

			/*
			 * Info add = AcessController.checkAccess("showContractorsList",
			 * "showContractorsList", 0, 1, 0, 0, newModuleList); Info edit =
			 * AcessController.checkAccess("showContractorsList", "showContractorsList", 0,
			 * 0, 1, 0, newModuleList); Info delete =
			 * AcessController.checkAccess("showContractorsList", "showContractorsList", 0,
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
	
	
	@RequestMapping(value = "/contractorAdd", method = RequestMethod.GET)
	public ModelAndView contractorAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {
			Contractor contract = new Contractor();
			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("dsesignationAdd", "showDesignationList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/
				model = new ModelAndView("master/addContractor");
				model.addObject("contract", contract);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/insertContractor", method=RequestMethod.POST)
	public String insertContractor(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		try {

			Contractor contract = new Contractor();

			contract.setContractorId(Integer.parseInt(request.getParameter("contractorId")));
			contract.setAddress(request.getParameter("address"));
			contract.setCompanyId(1);
			contract.setDelStatus(1);
			contract.setDuration(request.getParameter("duration"));
			contract.setEmail(request.getParameter("email"));
			contract.setEsicNo(request.getParameter("esic"));
			contract.setIsActive(1);
			contract.setLicenceNo(request.getParameter("licenceNo"));
			contract.setMakerEnterDatetime(currDate);
			contract.setMobileNo(request.getParameter("mobileNo"));
			contract.setOfficeNo(request.getParameter("officeNo"));
			contract.setOrgName(request.getParameter("organisation"));
			contract.setOwner(request.getParameter("owner"));
			contract.setPanNo(request.getParameter("panNo"));
			contract.setPfNo(request.getParameter("pf"));
			contract.setRemark(request.getParameter("remark"));
			contract.setService(request.getParameter("service"));
			contract.setVatNo(request.getParameter("vat"));
			contract.setExInt1(0);
			contract.setExInt2(0);
			contract.setExVar1("NA");
			contract.setExVar2("NA");
			
			Contractor saveDesig = Constants.getRestTemplate().postForObject(Constants.url + "/saveContractor", contract,
					Contractor.class);
			
			if (saveDesig != null) {
				session.setAttribute("successMsg", "Contractor Updated Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
			}

		}catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Update Record");
		}
	
		return "redirect:/showContractorsList";
		
	}
	
	
	@RequestMapping(value = "/editContractor", method = RequestMethod.GET)
	public ModelAndView editContractor(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Contractor contract = new Contractor();
		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addContractor");
			
			String base64encodedString = request.getParameter("contractor");
			String contractorId = FormValidation.DecodeKey(base64encodedString);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("contractorId", contractorId);
		
			contract = Constants.getRestTemplate().postForObject(Constants.url + "/getContractorById", map,
					Contractor.class);
			model.addObject("contract", contract);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
		
	}
	
	
	@RequestMapping(value = "/deleteContractor", method = RequestMethod.GET)
	public String deleteContractor(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String base64encodedString = request.getParameter("contractor");
			String contractorId = FormValidation.DecodeKey(base64encodedString);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("contractorId", contractorId);
			
			Integer emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpByContractorId", map,
					Integer.class);
			
			System.out.println("Emp-------------------"+emp);
			if(emp==0) {
			
			
				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deleteContractor", map,
						Info.class);
				
				if (res.isError()) {
					session.setAttribute("errorMsg", "Failed to Delete");
				} else {
					session.setAttribute("successMsg", "Deleted Successfully");
					
				}
			}else {
				session.setAttribute("errorMsg", "Failed to Delete - Contractor contain "+ emp +" employees");
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		
		
		return "redirect:/showContractorsList";
	}

	
	/******************************Department********************************/
	@RequestMapping(value = "/showDepartmentList", method = RequestMethod.GET)
	public ModelAndView showDepartmentList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("showDepartmentList", "showDepartmentList", 1,
			 * 0, 0, 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */
			model = new ModelAndView("master/departmentList");
			
			Department[] department = Constants.getRestTemplate().getForObject(Constants.url + "/getAllDepartments",
					Department[].class);

			List<Department> departmentList = new ArrayList<Department>(Arrays.asList(department));

			for (int i = 0; i < departmentList.size(); i++) {

				departmentList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(departmentList.get(i).getDepartId())));
			}

			model.addObject("addAccess", 0);
			model.addObject("editAccess", 0);
			model.addObject("deleteAccess", 0);
			model.addObject("departmentList", departmentList);

			/*
			 * Info add = AcessController.checkAccess("showDepartmentList",
			 * "showDepartmentList", 0, 1, 0, 0, newModuleList); Info edit =
			 * AcessController.checkAccess("showDepartmentList", "showDepartmentList", 0,
			 * 0, 1, 0, newModuleList); Info delete =
			 * AcessController.checkAccess("showDepartmentList", "showDepartmentList", 0,
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
	
	@RequestMapping(value = "/departmentAdd", method = RequestMethod.GET)
	public ModelAndView departmentAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {
			Department dept = new Department();
			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("departmentAdd", "showDepartmentList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/
				model = new ModelAndView("master/addDepartment");
				model.addObject("dept", dept);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/submitInsertDepartment", method=RequestMethod.POST)
	public String submitInsertDpartment(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		try {

			Department dept = new Department();

			dept.setCompanyId(1);
			dept.setDelStatus(1);
			dept.setDepartId(Integer.parseInt(request.getParameter("deptId")));
			dept.setExInt1(0);
			dept.setExInt2(0);
			dept.setExVar1("NA");
			dept.setExVar2("NA");
			dept.setIsActive(1);
			dept.setMakerEnterDatetime(currDate);
			dept.setName(request.getParameter("deptName"));
			dept.setNameSd(request.getParameter("deptShortName"));
			dept.setRemarks(request.getParameter("remark"));

			Department saveDepart = Constants.getRestTemplate().postForObject(Constants.url + "/saveDepartment", dept,
					Department.class);
			if (saveDepart != null) {
				session.setAttribute("successMsg", "Department Updated Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
			}

		}catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Update Record");
		}
	
		return "redirect:/showDepartmentList";
		
	}
	
	@RequestMapping(value = "/editDepartment", method = RequestMethod.GET)
	public ModelAndView editDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Department dept = new Department();
		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addDepartment");
			
			String base64encodedString = request.getParameter("deptId");
			String deptId = FormValidation.DecodeKey(base64encodedString);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("deptId", deptId);
		
			dept = Constants.getRestTemplate().postForObject(Constants.url + "/getDepartmentById", map,
					Department.class);
			model.addObject("dept", dept);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
		
	}
	
	@RequestMapping(value = "/deleteDepartment", method = RequestMethod.GET)
	public String deleteDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String base64encodedString = request.getParameter("deptId");
			String deptId = FormValidation.DecodeKey(base64encodedString);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("deptId", deptId);
			
			Integer emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpByDeptId", map,
					Integer.class);
			
			System.out.println("Emp-------------------"+emp);
			if(emp==0) {
			
			
				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deleteDepartment", map,
						Info.class);
				
				if (res.isError()) {
					session.setAttribute("errorMsg", "Failed to Delete");
				} else {
					session.setAttribute("successMsg", "Deleted Successfully");
					
				}
			}else {
				session.setAttribute("errorMsg", "Failed to Delete - Department assigned to "+ emp +" employees");
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		
		
		return "redirect:/showDepartmentList";
	}
}