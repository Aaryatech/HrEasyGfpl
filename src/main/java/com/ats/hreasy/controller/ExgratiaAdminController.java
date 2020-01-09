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
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.Bonus.BonusMaster;

@Controller
@Scope("session")
public class ExgratiaAdminController {

	@RequestMapping(value = "/showEmpListToAssignExgratia", method = RequestMethod.GET)
	public String showEmpListToAssignBonus(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String mav = null;
		/*
		 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
		 * session.getAttribute("moduleJsonList"); Info view =
		 * AcessController.checkAccess("showEmpListToAssignBonus",
		 * "showEmpListToAssignBonus", 1, 0, 0, 0, newModuleList);
		 * 
		 * if (view.isError() == true) { mav = "accessDenied";
		 * 
		 * } else {
		 */
		mav = "Bonus/assignExgratia";

		try {
			int bonusId = 0;

			BonusMaster[] location = Constants.getRestTemplate().getForObject(Constants.url + "/getAllBonusList",
					BonusMaster[].class);

			List<BonusMaster> bonusList = new ArrayList<BonusMaster>(Arrays.asList(location));
			model.addAttribute("bonusList", bonusList);
			try {
				bonusId = Integer.parseInt(request.getParameter("bonusId"));

			} catch (Exception e) {
				bonusId = 0;
			}
			model.addAttribute("bonusId", bonusId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("bonusId", bonusId);
			GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllEmployeeDetailForBonus", map, GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
			model.addAttribute("empdetList", empdetList);

		} catch (Exception e) {

			e.printStackTrace();
		}

		// }
		return mav;
	}

	
	@RequestMapping(value = "/submitAssignExgratiaToEmp", method = RequestMethod.POST)
	public String submitAssignExgratiaToEmp(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		// String retString = null;
		String a = null;
		String bonusId = null;
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {

			try {
				bonusId = request.getParameter("bonusId");
			} catch (Exception e) {
				e.printStackTrace();

			}
			// System.out.println("work date**" + workDate);

			String[] empId = request.getParameterValues("empId");

			StringBuilder sb = new StringBuilder();

			List<Integer> empIdList = new ArrayList<>();

			for (int i = 0; i < empId.length; i++) {
				sb = sb.append(empId[i] + ",");
				empIdList.add(Integer.parseInt(empId[i]));

				// System.out.println("empId id are**" + empId[i]);

			}

			String items = sb.toString();

			items = items.substring(0, items.length() - 1);

			StringBuilder sbEmp = new StringBuilder();

			// System.out.println("empId id are**" + empIdList.toString());
			// System.out.println("shiftId id are**" + shiftId);

			map.add("empIdList", items);
			map.add("bonusId", Integer.parseInt(bonusId));
			map.add("companyId", 1);
			map.add("userId", userObj.getEmpId());

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/empExgratiaUpdateToBonusSave", map, Info.class);
			// System.err.println("info" + info.toString());
			if (info.isError() == false) {
				// retString = info.getMsg();
				session.setAttribute("successMsg", "Data Inserted Successfully");

				a = "redirect:/showAddBonusNextStep?bonusId=" + FormValidation.Encrypt(bonusId);
			} else {
				session.setAttribute("successMsg", "Failed to Insert Data");
				a = "redirect:/showEmpListToAssignBonus";
			}
		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}

		return a;
	}
}
