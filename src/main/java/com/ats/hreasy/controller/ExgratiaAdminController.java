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
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.common.ReportCostants;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.Setting;
import com.ats.hreasy.model.Bonus.BonusApplicable;
import com.ats.hreasy.model.Bonus.BonusCalc;
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
			String base64encodedString1 = request.getParameter("bonusId");
			String bonusId = FormValidation.DecodeKey(base64encodedString1);

			BonusMaster[] location = Constants.getRestTemplate().getForObject(Constants.url + "/getAllBonusList",
					BonusMaster[].class);

			List<BonusMaster> bonusList = new ArrayList<BonusMaster>(Arrays.asList(location));
			model.addAttribute("bonusList", bonusList);

			model.addAttribute("bonusId", bonusId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("limitKey", "ammount_format_show");
			Setting getSettingByKey = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey", map,
					Setting.class);
			int amount_round = Integer.parseInt(getSettingByKey.getValue());
			for (int i = 0; i < bonusList.size(); i++) {

				if (bonusList.get(i).getBonusId() == Integer.parseInt(bonusId)) {
					model.addAttribute("bonusName", bonusList.get(i).getFyTitle());
				}

			}

			map = new LinkedMultiValueMap<>();
			map.add("bonusId", bonusId);
			map.add("flag", 1);
			GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllEmployeeDetailForBonus", map, GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
			model.addAttribute("empdetList", empdetList);

			int isfinalized = 0;

			map = new LinkedMultiValueMap<>();
			map.add("bonusId", bonusId);
			map.add("flag", 1);
			BonusCalc[] employeeDoc1 = Constants.getRestTemplate().postForObject(Constants.url + "/getBonusCalcList",
					map, BonusCalc[].class);

			List<BonusCalc> claimProofList1 = new ArrayList<BonusCalc>(Arrays.asList(employeeDoc1));

			for (int i = 0; i < claimProofList1.size(); i++) {

				claimProofList1.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(claimProofList1.get(i).getBonusCalcId())));
				claimProofList1.get(i)
						.setExVar2(FormValidation.Encrypt(String.valueOf(claimProofList1.get(i).getBonusId())));

				claimProofList1.get(i).setTotalExgretiaWages(String.valueOf(
						(ReportCostants.castNumber(claimProofList1.get(i).getTotalBonusWages(), amount_round))));
				claimProofList1.get(i).setGrossExgretiaAmt(
						(ReportCostants.castNumber(claimProofList1.get(i).getGrossExgretiaAmt(), amount_round)));
				claimProofList1.get(i).setNetExgretiaAmt(
						(ReportCostants.castNumber(claimProofList1.get(i).getNetExgretiaAmt(), amount_round)));

				claimProofList1.get(i).setPaidExgretiaAmt(
						(ReportCostants.castNumber(claimProofList1.get(i).getPaidExgretiaAmt(), amount_round)));
			}

			model.addAttribute("bonusId", bonusId);
			model.addAttribute("bonusCalcList", claimProofList1);
			map = new LinkedMultiValueMap<>();
			map.add("bonusId", bonusId);
			BonusApplicable info = Constants.getRestTemplate().postForObject(Constants.url + "/chkIsBonusFinalized",
					map, BonusApplicable.class);

		//	System.err.println("BonusApplicable**" + info.toString());

			try {
				model.addAttribute("bonusAppId", info.getBappNo());
				if (info.getIsExgretiaFinalized().equals("1")) {
					System.err.println("1");
					isfinalized = 1;
					model.addAttribute("isfinalized", isfinalized);
				} else {
					System.err.println("2");
					isfinalized = 2;
					model.addAttribute("isfinalized", isfinalized);
				}

			} catch (Exception e) {
				System.err.println("3");
				isfinalized = 3;
				model.addAttribute("bonusAppId", 0);
				model.addAttribute("isfinalized", isfinalized);
			}

			System.err.println("isfinalized" + isfinalized);

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

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/empExgratiaUpdateToBonusSave", map,
					Info.class);
			// System.err.println("info" + info.toString());
			if (info.isError() == false) {
				// retString = info.getMsg();
				session.setAttribute("successMsg", "Data Inserted Successfully");

			} else {
				session.setAttribute("successMsg", "Failed to Insert Data");
			}
			a = "redirect:/showEmpListToAssignExgratia?bonusId=" + FormValidation.Encrypt(bonusId);
		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}

		return a;
	}

	@RequestMapping(value = "/submitExgratisApplicable", method = RequestMethod.POST)
	public String submitExgratisApplicable(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String temp = null;
		String a = null;
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {
			int bonusId = Integer.parseInt(request.getParameter("bonusIdNew"));
			temp = request.getParameter("bonusIdNew");
			int isFinal = Integer.parseInt(request.getParameter("isFinal"));
			String startDate = request.getParameter("startDate");
			String remark = request.getParameter("remark");
			int bonusAppId = Integer.parseInt(request.getParameter("bonusAppId"));

			map.add("bonusAppId", bonusAppId);
			map.add("startDate", startDate);
			map.add("isFinal", isFinal);
			map.add("bonusId", bonusId);
			map.add("remark", remark);
			map.add("companyId", 1);
			map.add("dateTime", sf.format(date));
			map.add("userId", userObj.getUserId());

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/empBonusAppUpdateForExgratia", map,
					Info.class);
			System.err.println("info" + info.toString());
			if (info.isError() == false) {
				// retString = info.getMsg();
				session.setAttribute("successMsg", "Data Inserted Successfully");

			} else {
				session.setAttribute("successMsg", "Failed to Insert Data");
				a = "redirect:/showEmpListToAssignExgratia";
			}
		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}
		a = "redirect:/showEmpListToAssignExgratia?bonusId=" + FormValidation.Encrypt(temp);
		return a;
	}

	@RequestMapping(value = "/deleteBonusCalcExgratia", method = RequestMethod.GET)
	public String deleteBonusCalc(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList");
			 * 
			 * Info view = AcessController.checkAccess("deleteBonus", "showBonusList", 0, 0,
			 * 0, 1, newModuleList); if (view.isError() == true) {
			 * 
			 * a = "redirect:/accessDenied";
			 * 
			 * }
			 * 
			 * else {
			 */

			String base64encodedString = request.getParameter("bonusCalcId");
			String bonusCalcId = FormValidation.DecodeKey(base64encodedString);
			String base64encodedString1 = request.getParameter("bonusId");
			String bonusId = FormValidation.DecodeKey(base64encodedString1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("bonusCalcId", bonusCalcId);
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteBonusCalcExratia", map,
					Info.class);

			a = "redirect:/showEmpListToAssignBonus?bonusId=" + FormValidation.Encrypt(bonusId);
			if (info.isError() == false) {
				session.setAttribute("successMsg", "Deleted Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Delete");
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		return a;
	}

	BonusCalc editBonusCalc = new BonusCalc();

	@RequestMapping(value = "/showEditExgratia", method = RequestMethod.GET)
	public ModelAndView showEditExgratia(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("editBonus", "showBonusList", 0, 0, 1, 0,
			 * newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */

			model = new ModelAndView("Bonus/exgratiaEdit");
			String base64encodedString = request.getParameter("bonusCalcId");
			String bonusCalcId = FormValidation.DecodeKey(base64encodedString);
			/*
			 * String base64encodedString1 = request.getParameter("bonusId"); String bonusId
			 * = FormValidation.DecodeKey(base64encodedString1);
			 */
			/*
			 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
			 * Object>(); map.add("limitKey", "ammount_format_show"); Setting
			 * getSettingByKey = Constants.getRestTemplate().postForObject(Constants.url +
			 * "/getSettingByKey", map, Setting.class); int amount_round =
			 * Integer.parseInt(getSettingByKey.getValue());
			 */
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("bonusCalcId", bonusCalcId);
			editBonusCalc = Constants.getRestTemplate().postForObject(Constants.url + "/getBonusCalcByCalcId", map,
					BonusCalc.class);
			model.addObject("editBonusCalc", editBonusCalc);
			model.addObject("bonusId", editBonusCalc.getBonusId());

			map = new LinkedMultiValueMap<>();
			map.add("bonusId", editBonusCalc.getBonusId());
			BonusMaster editBonus = Constants.getRestTemplate().postForObject(Constants.url + "/getBonusById", map,
					BonusMaster.class);
			model.addObject("editBonus", editBonus);

			map = new LinkedMultiValueMap<>();
			map.add("empId", editBonusCalc.getEmpId());
			EmployeeMaster editEmp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpInfoById", map,
					EmployeeMaster.class);
			model.addObject("editEmp", editEmp);
			/* } */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitEditExgratia", method = RequestMethod.POST)
	public String submitEditExgratia(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		//System.err.println("hii ****");
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String temp = null;
		String a = null;
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {
			int bonusId = Integer.parseInt(request.getParameter("bonusId"));
			temp = request.getParameter("bonusId");
			int bonusCalcId = Integer.parseInt(request.getParameter("bonusCalcId"));

			//System.err.println("bonusCalcId ****" + bonusCalcId);
			double exPrcnt = Double.parseDouble(request.getParameter("exgratiaPrcnt"));
			// double exgratiaAmt = Double.parseDouble(request.getParameter("exgratiaAmt"));

			map.add("bonusId", bonusId);
			map.add("bonusCalcId", bonusCalcId);
			map.add("exPrcnt", exPrcnt);
			/*
			 * map.add("exgratiaAmt1", exgratiaAmt);
			 */ map.add("companyId", 1);
			map.add("dateTime", sf.format(date));
			map.add("userId", userObj.getUserId());

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/empBonusCalcUpdateForExgratia", map,
					Info.class);
			//System.err.println("info" + info.toString());
			if (info.isError() == false) {
				// retString = info.getMsg();
				session.setAttribute("successMsg", "Data Inserted Successfully");

			} else {
				session.setAttribute("successMsg", "Failed to Insert Data");
				a = "redirect:/showEmpListToAssignExgratia";
			}
		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}
		a = "redirect:/showEmpListToAssignExgratia?bonusId=" + FormValidation.Encrypt(temp);
		return a;
	}

}
