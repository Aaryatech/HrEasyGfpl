package com.ats.hreasy.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.Advance.GetAdvance;
import com.ats.hreasy.model.Loan.LoanMain;

@Controller
@Scope("session")
class LoanAdminController {
	
	@RequestMapping(value = "/showEmpListToAddLoan", method = RequestMethod.GET)
	public ModelAndView showEmpListToAddLoan(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Loan/empListToAddLoan");

		try {
			GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
			model.addObject("empdetList", empdetList);

			// System.err.println("sh list"+shiftList.toString());

			for (int i = 0; i < empdetList.size(); i++) {

				empdetList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(empdetList.get(i).getEmpId())));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	

	@RequestMapping(value = "/showAddLoan", method = RequestMethod.GET)
	public ModelAndView showAddAdvance(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Loan/addLoan");
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
		try {

			String base64encodedString = request.getParameter("empId");
			String empTypeId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empTypeId);
			GetEmployeeDetails empPersInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
			// System.out.println("Edit EmpPersonal Info-------"+ empPersInfo.toString());

			String empPersInfoString = empPersInfo.getEmpCode().concat(" ").concat(empPersInfo.getFirstName())
					.concat(" ").concat(empPersInfo.getSurname()).concat("[").concat(empPersInfo.getEmpDesgn()).concat("[");
			model.addObject("empPersInfo", empPersInfo);
			model.addObject("empPersInfoString", empPersInfoString);
			model.addObject("todaysDate", sf.format(date));
			
			
			map = new LinkedMultiValueMap<>();
			map.add("empId", empTypeId);
			LoanMain empPersInfo1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpLoanHistory", map, LoanMain.class);
			
			model.addObject("prevLoan", empPersInfo1);
			System.out.println("  LoanMain Info-------"+ empPersInfo1.toString());
			LoanMain appNo = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getLastApplicationNumber", LoanMain.class);
			
			model.addObject("appNo", appNo.getLoanApplNo()+1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/loanCalculation", method = RequestMethod.GET)
	public @ResponseBody List<GetAdvance> loanCalculation(HttpServletRequest request,
			HttpServletResponse response) {

		List<GetAdvance> employeeInfoList = new ArrayList<GetAdvance>();

		try {

			String roi =  request.getParameter("roi");
			String tenure = request.getParameter("tenure");
			String loanAmt = request.getParameter("loanAmt");


			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("roi", roi);
			map.add("tenure", tenure);
			map.add("loanAmt", loanAmt);

			GetAdvance[] employeeInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getAdvanceHistory",
					map, GetAdvance[].class);

			employeeInfoList = new ArrayList<GetAdvance>(Arrays.asList(employeeInfo));
			// System.out.println("employeeInfoList" + employeeInfoList.toString());

			for (int i = 0; i < employeeInfoList.size(); i++) {

				employeeInfoList.get(i).setAdvDate(DateConvertor.convertToDMY(employeeInfoList.get(i).getAdvDate()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeInfoList;
	}

	


}
