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
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Allowances;
import com.ats.hreasy.model.Bank;
import com.ats.hreasy.model.CalenderYear;
import com.ats.hreasy.model.Contractor;
import com.ats.hreasy.model.Department;
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.EmpDoctype;
import com.ats.hreasy.model.EmpSalAllowance;
import com.ats.hreasy.model.EmpSalaryInfo;
import com.ats.hreasy.model.EmployeDoc;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.TblEmpBankInfo;
import com.ats.hreasy.model.TblEmpInfo;
import com.ats.hreasy.model.TblEmpNominees;
import com.ats.hreasy.model.User;
import com.ats.hreasy.model.Advance.GetAdvance;
import com.ats.hreasy.model.graph.EmpAdvanceGraph;
import com.ats.hreasy.model.graph.EmpDailyAttendanceGraph;
import com.ats.hreasy.model.graph.EmpLoanGraph;

@Controller
@Scope("session")
public class GraphsController {
 
	
	@RequestMapping(value = "/showEmpGraphs", method = RequestMethod.GET)
	public String  showEmpGraphs(HttpServletRequest request, HttpServletResponse response,Model model) {

		HttpSession session = request.getSession();
		session.setAttribute("tabEmpFlag", 0);
		String  mav = null;
		MultiValueMap<String, Object> map = null;
		try {
 
				mav = "Report/empReportsGraph";
				
				
				String base64encodedString = request.getParameter("empId");
				String empId = FormValidation.DecodeKey(base64encodedString);
				
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy");
				Date now = new Date();
				String curDate = dateFormat.format(new Date());
				
				model.addAttribute("empId",empId);
				model.addAttribute("fromDate","01-".concat(curDate));
				model.addAttribute("toDate","12-".concat(curDate));
				
				
				 map = new LinkedMultiValueMap<>();
				map.add("empId", empId);
				GetEmployeeDetails empPersInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
				model.addAttribute("empDeatil",empPersInfo);
				
				
				CalenderYear[] calenderYear = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getCalculateYearList", CalenderYear[].class);
				List<CalenderYear> calYearList = new ArrayList<CalenderYear>(Arrays.asList(calenderYear));

			 
				model.addAttribute("calYearList", calYearList);
 			 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	
	
	@RequestMapping(value = "/getEmpAttnGraph", method = RequestMethod.GET)
	public @ResponseBody List<EmpDailyAttendanceGraph>  getEmpAttnGraph(HttpServletRequest request,
			HttpServletResponse response) {
		
		List<EmpDailyAttendanceGraph> employeeInfoList=new ArrayList<>();
		 String fromDate=new String();
		 String toDate=new String();
		 HttpSession session =request.getSession();
		try {
			int empId = Integer.parseInt(request.getParameter("empId"));
			 
			fromDate =request.getParameter("fromDate");
			toDate =request.getParameter("toDate");
			
			//System.err.println("fromDate"+fromDate);
		//	System.err.println("toDate"+toDate);
		   
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
 			map.add("companyId", 1);
 			map.add("fromDate", "01-".concat(fromDate));
 		    map.add("toDate", "30-".concat(toDate));

			EmpDailyAttendanceGraph[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpAttendanceGraphNew", map, EmpDailyAttendanceGraph[].class);

			employeeInfoList = new ArrayList<EmpDailyAttendanceGraph>(Arrays.asList(employeeInfo));
		 
			 
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
				return employeeInfoList;
		
	}
	
	
	
	@RequestMapping(value = "/getEmpAdvanceGraph", method = RequestMethod.GET)
	public @ResponseBody List<EmpAdvanceGraph>  getEmpAdvanceGraph(HttpServletRequest request,
			HttpServletResponse response) {
		
		List<EmpAdvanceGraph> employeeInfoList=new ArrayList<>();
		 String fromDate=new String();
		 String toDate=new String();
		try {
			int empId = Integer.parseInt(request.getParameter("empId"));
			 
			fromDate =request.getParameter("fromDate");
			toDate =request.getParameter("toDate");
		 
		   
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
 			map.add("companyId", 1);
 			map.add("fromDate", "01-".concat(fromDate));
 		    map.add("toDate", "30-".concat(toDate));

 		   EmpAdvanceGraph[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpAdvanceGraphNew", map, EmpAdvanceGraph[].class);

			employeeInfoList = new ArrayList<EmpAdvanceGraph>(Arrays.asList(employeeInfo));
			 
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
				return employeeInfoList;
		
	}
	
	
	@RequestMapping(value = "/getEmpLoanGraph", method = RequestMethod.GET)
	public @ResponseBody List<EmpLoanGraph>  getEmpLoanGraph(HttpServletRequest request,
			HttpServletResponse response) {
		
		List<EmpLoanGraph> employeeInfoList=new ArrayList<>();
		 String fromDate=new String();
		 String toDate=new String();
		try {
			int empId = Integer.parseInt(request.getParameter("empId"));
			 
			fromDate =request.getParameter("fromDate");
			toDate =request.getParameter("toDate");
		 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
 			map.add("companyId", 1);
 			map.add("fromDate", "01-".concat(fromDate));
 		    map.add("toDate", "30-".concat(toDate));

 		   EmpLoanGraph[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpLoanGraph", map, EmpLoanGraph[].class);

			employeeInfoList = new ArrayList<EmpLoanGraph>(Arrays.asList(employeeInfo));
			 
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
				return employeeInfoList;
		
	}
}
