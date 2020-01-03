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

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.EmpSalaryInfoForPayroll;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.InfoForUploadAttendance;

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
				EmpSalaryInfoForPayroll[] empSalaryInfoForPayroll = Constants.getRestTemplate().postForObject(
						Constants.url + "/getEmployeeListWithEmpSalEnfoForPayRoll", map,
						EmpSalaryInfoForPayroll[].class);
				List<EmpSalaryInfoForPayroll> list = new ArrayList<EmpSalaryInfoForPayroll>(
						Arrays.asList(empSalaryInfoForPayroll));
				model.addAttribute("empList", list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/viewDynamicValue", method = RequestMethod.POST)
	public String viewDynamicValue(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String mav = "payroll/viewDynamicValue";

		try {

			String date = request.getParameter("searchDate");

			String[] monthyear = date.split("-");
			model.addAttribute("date", date);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("month", monthyear[0]);
			map.add("year", monthyear[1]);
			EmpSalaryInfoForPayroll[] empSalaryInfoForPayroll = Constants.getRestTemplate().postForObject(
					Constants.url + "/getEmployeeListWithEmpSalEnfoForPayRoll", map, EmpSalaryInfoForPayroll[].class);
			List<EmpSalaryInfoForPayroll> list = new ArrayList<EmpSalaryInfoForPayroll>(
					Arrays.asList(empSalaryInfoForPayroll));
			model.addAttribute("empList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

}