package com.ats.hreasy.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Allowances;
import com.ats.hreasy.model.EmpSalaryInfoForPayroll;
import com.ats.hreasy.model.GetSalDynamicTempRecord;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.InfoForUploadAttendance;
import com.ats.hreasy.model.PayRollDataForProcessing;

@Controller
@Scope("session")
public class PayRollController {

	@RequestMapping(value = "/selectMonthForPayRoll", method = RequestMethod.GET)
	public String selectMonthForPayRoll(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String mav = "payroll/selectMonthShoEmpInfo";

		try {

			String date = request.getParameter("selectMonth");

			if (date != null) {
				String[] monthyear = date.split("-");
				model.addAttribute("date", date);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("month", monthyear[0]);
				map.add("year", monthyear[1]);
				PayRollDataForProcessing payRollDataForProcessing = Constants.getRestTemplate().postForObject(
						Constants.url + "/getEmployeeListWithEmpSalEnfoForPayRoll", map,
						PayRollDataForProcessing.class);
				List<EmpSalaryInfoForPayroll> list = payRollDataForProcessing.getList();

				model.addAttribute("empList", list);
				model.addAttribute("allownceList", payRollDataForProcessing.getAllowancelist());
				// System.out.println(payRollDataForProcessing.getList());
			} else {
				Allowances[] allowances = Constants.getRestTemplate().getForObject(Constants.url + "/getAllAllowances",
						Allowances[].class);
				List<Allowances> allowancelist = new ArrayList<>(Arrays.asList(allowances));
				model.addAttribute("allownceList", allowancelist);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/viewDynamicValue", method = RequestMethod.POST)
	public String viewDynamicValue(HttpServletRequest request, HttpServletResponse response, Model model) {

		 
		String mav = "payroll/viewDynamicValue";

		try {

			String date = request.getParameter("searchDate");

			String[] monthyear = date.split("-");
			model.addAttribute("date", date);

			String[] selectEmp = request.getParameterValues("selectEmp");
			String empIds = "0";
			
			for(int i=0 ; i<selectEmp.length ; i++) {
				empIds=empIds+","+selectEmp[i];
			}
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("month", monthyear[0]);
			map.add("year", monthyear[1]);
			map.add("empIds", empIds);
			//System.out.println(map);
			Info insertTemp = Constants.getRestTemplate().postForObject(Constants.url + "/insertPayrollIntempTable",
					map, Info.class);
			if (insertTemp.isError() == false) {
				GetSalDynamicTempRecord[] getSalDynamicTempRecord = Constants.getRestTemplate().postForObject(
						Constants.url + "/getSalDynamicTempRecord", map, GetSalDynamicTempRecord[].class);
				List<GetSalDynamicTempRecord> list = new ArrayList<>(Arrays.asList(getSalDynamicTempRecord));
				model.addAttribute("empList", list);
			}
			// model.addAttribute("empList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/editBonus", method = RequestMethod.POST)
	public @ResponseBody GetSalDynamicTempRecord editBonus(HttpServletRequest request, HttpServletResponse response, Model model) {

		 
		 
		GetSalDynamicTempRecord getSalDynamicTempRecordById = new GetSalDynamicTempRecord();
		
		try {

			int tempSalDaynamicId = Integer.parseInt(request.getParameter("tempSalDaynamicId"));
 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("tempSalDaynamicId", tempSalDaynamicId); 
			getSalDynamicTempRecordById = Constants.getRestTemplate().postForObject(Constants.url + "/getSalDynamicTempRecordById",
					map, GetSalDynamicTempRecord.class);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getSalDynamicTempRecordById;
	}
	
	@RequestMapping(value = "/saveBonusDetail", method = RequestMethod.POST)
	public @ResponseBody Info saveBonusDetail(HttpServletRequest request, HttpServletResponse response, Model model) {
 
		Info info = new Info();
		
		try {

			int tempSalDaynamicId = Integer.parseInt(request.getParameter("tempSalDaynamicId"));
			float itAmt =  Float.parseFloat(request.getParameter("itAmt"));
			float perBonus =  Float.parseFloat(request.getParameter("perBonus"));
 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("tempSalDaynamicId", tempSalDaynamicId); 
			map.add("itAmt", itAmt); 
			map.add("perBonus", perBonus); 
			info = Constants.getRestTemplate().postForObject(Constants.url + "/updateBonusAmt",
					map, Info.class);
			 
		} catch (Exception e) {
			e.printStackTrace();
			info = new Info();
			info.setError(true);
			info.setMsg("failed");
		}
		return info;
	}

}
