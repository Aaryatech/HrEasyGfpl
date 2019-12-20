package com.ats.hreasy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.ShiftMaster;
import com.ats.hreasy.model.TblEmpInfo;

@Controller
@Scope("session")
public class AdvanceAdminController {
	
	@RequestMapping(value = "/showEmpListToAddAdvance", method = RequestMethod.GET)
	public ModelAndView showEmpListToAssignShift(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("advance/employeeListForAdvance");

		try {
			List<ShiftMaster> shiftList = new ArrayList<>();
			GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
			model.addObject("empdetList", empdetList);
 
			//System.err.println("sh list"+shiftList.toString());
			
			for (int i = 0; i < empdetList.size(); i++) {

				empdetList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(empdetList.get(i).getEmpId())));
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	
	@RequestMapping(value = "/showAddAdvance", method = RequestMethod.GET)
	public ModelAndView showAddAdvance(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("advance/addAdvance");

		try {
			
			String base64encodedString = request.getParameter("empTypeId");
			String empTypeId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", 1);
 			TblEmpInfo empPersInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeePersonalInfo", map,
					TblEmpInfo.class);			
			System.out.println("Edit EmpPersonal Info-------"+ empPersInfo);
			model.addObject("empPersInfo", empPersInfo);
			 
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	

}
