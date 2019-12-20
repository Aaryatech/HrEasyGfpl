package com.ats.hreasy.controller;

import java.text.DateFormat;
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
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.SalaryTypesMaster;
import com.ats.hreasy.model.ShiftMaster;
 
@Controller
@Scope("session")
public class EmployeeShiftAssignController {
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	String curDate = dateFormat.format(new Date());
	String dateTime = dateFormat.format(now);

	@RequestMapping(value = "/showEmpListToAssignShift", method = RequestMethod.GET)
	public ModelAndView showEmpListToAssignShift(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/assignShiftToEmp");

		try {
			List<ShiftMaster> shiftList = new ArrayList<>();
			GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
			model.addObject("empdetList", empdetList);

			ShiftMaster[] empdetList2 = Constants.getRestTemplate().getForObject(Constants.url + "/getAllShiftList",
					ShiftMaster[].class);

			shiftList = new ArrayList<ShiftMaster>(Arrays.asList(empdetList2));
			model.addObject("shiftList", shiftList);
			//System.err.println("sh list"+shiftList.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	@RequestMapping(value = "/submitAssignShiftToEmp", method = RequestMethod.POST)
	public String addCustLoginDetail(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {
 
			String shiftId = null;
			try {
				shiftId = request.getParameter("shiftId");
			} catch (Exception e) {
				e.printStackTrace();
				 
			}
			// System.out.println("work date**" + workDate);

			String[] empId = request.getParameterValues("empId");

			StringBuilder sb = new StringBuilder();
			
			List<Integer> empIdList=new ArrayList<>();

			for (int i = 0; i < empId.length; i++) {
				sb = sb.append(empId[i] + ",");
				empIdList.add(Integer.parseInt(empId[i]));

			//	System.out.println("empId id are**" + empId[i]);

			}

			String items = sb.toString();

			items = items.substring(0, items.length() - 1);

			StringBuilder sbEmp = new StringBuilder();
			
			//System.out.println("empId id are**" + empIdList.toString());
			//System.out.println("shiftId id are**" + shiftId);
		  
			map.add("empIdList", items);
			map.add("shiftId", shiftId);
		  
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/shiftAssignmentUpdate", map,
					Info.class);
 
		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showEmpListToAssignShift";
	}
	
	
	@RequestMapping(value = "/showEmpListToAssignSalStruct", method = RequestMethod.GET)
	public ModelAndView showEmpListToAssignSalStruct(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/assignSalStructToEmp");

		try {
			List<SalaryTypesMaster> shiftList = new ArrayList<>();
			GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
			model.addObject("empdetList", empdetList);

			SalaryTypesMaster[] empdetList2 = Constants.getRestTemplate().getForObject(Constants.url + "/getSalryTypesMst",
					SalaryTypesMaster[].class);

			shiftList = new ArrayList<SalaryTypesMaster>(Arrays.asList(empdetList2));
			model.addObject("shiftList", shiftList);
			System.err.println("sh list"+shiftList.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	@RequestMapping(value = "/submitAssignSalStructToEmp", method = RequestMethod.POST)
	public String submitAssignSalStructToEmp(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {
 
			String shiftId = null;
			try {
				shiftId = request.getParameter("shiftId");
			} catch (Exception e) {
				e.printStackTrace();
				 
			}
			// System.out.println("work date**" + workDate);

			String[] empId = request.getParameterValues("empId");

			StringBuilder sb = new StringBuilder();
			
			List<Integer> empIdList=new ArrayList<>();

			for (int i = 0; i < empId.length; i++) {
				sb = sb.append(empId[i] + ",");
				empIdList.add(Integer.parseInt(empId[i]));

			//	System.out.println("empId id are**" + empId[i]);

			}

			String items = sb.toString();

			items = items.substring(0, items.length() - 1);

			StringBuilder sbEmp = new StringBuilder();
			
			//System.out.println("empId id are**" + empIdList.toString());
			//System.out.println("shiftId id are**" + shiftId);
		  
			map.add("empIdList", items);
			map.add("structId", shiftId);
		  
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/salStructAssignmentUpdate", map,
					Info.class);
 
		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showEmpListToAssignSalStruct";
	}
	
	

}
