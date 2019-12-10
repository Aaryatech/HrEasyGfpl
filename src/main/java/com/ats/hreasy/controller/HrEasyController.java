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
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", 1);
			Designation[] designation = Constants.getRestTemplate().postForObject(Constants.url + "/getAllDesignations",map,
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
			map.add("companyId", 1);
				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deleteDesignationById", map,
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
		
		
		return "redirect:/showDesignationList";
	}

	/******************************Designation********************************/
	
	
	/******************************Contractor********************************/
	
	
	/******************************Contractor********************************/
}
