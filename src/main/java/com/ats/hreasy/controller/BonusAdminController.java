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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.common.ReportCostants;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.PayDeduction;
import com.ats.hreasy.model.PayDeductionDetailList;
import com.ats.hreasy.model.PayDeductionDetails;
import com.ats.hreasy.model.Setting;
import com.ats.hreasy.model.ShiftMaster;
import com.ats.hreasy.model.Bonus.BonusApplicable;
import com.ats.hreasy.model.Bonus.BonusCalc;
import com.ats.hreasy.model.Bonus.BonusMaster;
import com.ats.hreasy.model.Bonus.PayBonus;
import com.ats.hreasy.model.Bonus.PayBonusDetails;
import com.ats.hreasy.model.claim.ClaimProof;

@Controller
@Scope("session")
public class BonusAdminController {

	BonusMaster editBonus = new BonusMaster();

	@RequestMapping(value = "/showAddBonus", method = RequestMethod.GET)
	public ModelAndView showAddBonus(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAddBonus", "showBonusList", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {
			model = new ModelAndView("accessDenied");
		} else {

			try {

				model = new ModelAndView("Bonus/addBonus");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/submitBonus", method = RequestMethod.POST)
	public String submitBonus(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = new String();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAddBonus", "showBonusList", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showBonusList";
			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String leaveDateRange = request.getParameter("leaveDateRange");
				String bonusTitle = request.getParameter("bonusTitle");
				String bonusPrcnt = request.getParameter("bonusPrcnt");

				String bonusRemark = new String();
				String[] arrOfStr = leaveDateRange.split("to", 2);
				Boolean ret = false;

				try {
					bonusRemark = request.getParameter("bonusRemark");
				} catch (Exception e) {
					bonusRemark = "";
				}

				if (FormValidation.Validaton(leaveDateRange, "") == true) {

					ret = true;
					System.out.println("leaveDateRange" + ret);
				}
				if (FormValidation.Validaton(bonusTitle, "") == true) {

					ret = true;
					System.out.println("bonusTitle" + ret);
				}

				if (FormValidation.Validaton(bonusPrcnt, "") == true) {

					ret = true;
					System.out.println("bonusPrcnt" + ret);
				}
				String exgratiaPrcnt = request.getParameter("exgratiaPrcnt");
				if (FormValidation.Validaton(exgratiaPrcnt, "") == true) {

					ret = true;
					System.out.println("exgratiaPrcnt" + ret);
				}

				String minDays = request.getParameter("minDays");
				if (FormValidation.Validaton(minDays, "") == true) {

					ret = true;
					System.out.println("minDays" + ret);
				}

				if (ret == false) {

					BonusMaster bonus = new BonusMaster();
					bonus.setMinDays(Integer.parseInt(minDays));
					bonus.setBonusPercentage(Double.parseDouble(bonusPrcnt));
					bonus.setDelStatus(1);
					bonus.setExInt1(0);
					bonus.setExInt2(0);
					bonus.setExVar1("NA");
					bonus.setExVar2("NA");
					bonus.setFyFromdt(arrOfStr[0].toString().trim());
					bonus.setFyTitle(bonusTitle);
					bonus.setFyTodt(arrOfStr[1].toString().trim());
					bonus.setIsCurrent(1);
					bonus.setRemark(bonusRemark);
					bonus.setBonusAppBelowAmount(0);
					bonus.setBonusAppBelowApplicable(0);
					bonus.setBonuSealingLimitAmountPerMonth(0);
					bonus.setBonusSealingLimitApplicable(0);
					bonus.setDedBonusAdvAmtPercentage(0);
					bonus.setExgratiaPercentage(Double.parseDouble(exgratiaPrcnt));

					BonusMaster res = Constants.getRestTemplate().postForObject(Constants.url + "/saveBonus", bonus,
							BonusMaster.class);

					if (res != null) {
						session.setAttribute("successMsg", "BOnus Inserted Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Insert Record");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Insert Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}
		}

		return a;
	}

	@RequestMapping(value = "/showBonusList", method = RequestMethod.GET)
	public ModelAndView showBonusList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showBonusList", "showBonusList", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {
			model = new ModelAndView("accessDenied");
		} else {

			try {

				model = new ModelAndView("Bonus/bonusList");
				BonusMaster[] location = Constants.getRestTemplate().getForObject(Constants.url + "/getAllBonusList",
						BonusMaster[].class);

				List<BonusMaster> bonusList = new ArrayList<BonusMaster>(Arrays.asList(location));

				BonusCalc[] bonusCalc = Constants.getRestTemplate().getForObject(Constants.url + "/getAllBonusCalcList",
						BonusCalc[].class);

				List<BonusCalc> bonusCalcList = new ArrayList<BonusCalc>(Arrays.asList(bonusCalc));
				for (int i = 0; i < bonusList.size(); i++) {

					// encrpt bonusId
					bonusList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(bonusList.get(i).getBonusId())));

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("bonusId", bonusList.get(i).getBonusId());
					BonusApplicable info = Constants.getRestTemplate()
							.postForObject(Constants.url + "/chkIsBonusFinalized", map, BonusApplicable.class);
					int isExFinalized = 0;

					// model.addAttribute("payRollFinal", info.getIsPayrollFinalized());
					try {
						if (info.getIsBonussheetFinalized().equals("1")) {
							bonusList.get(i).setExVar2("1");
						} else {
							bonusList.get(i).setExVar2("2");
						}

					} catch (Exception e) {
						bonusList.get(i).setExVar2("2");
					}

					try {

						if (info.getIsExgretiaFinalized().equals("1")) {
							isExFinalized = 1;
						} else {
							isExFinalized = 2;
						}

					} catch (Exception e) {
						isExFinalized = 2;
					}

					// chk isCal

					int flag = 0;
					for (int j = 0; j < bonusCalcList.size(); j++) {

						if (bonusList.get(i).getBonusId() == bonusCalcList.get(j).getBonusId()) {
							// System.err.println("matched bonus id " + bonusList.get(i).getBonusId());

							flag = 1;
							break;
						}

					}
					if (flag == 1) {
						bonusList.get(i).setExInt2(1);
						if (isExFinalized == 2) {
							bonusList.get(i).setBonusAppBelowAmount(1);
						} else {
							bonusList.get(i).setBonusAppBelowAmount(2);
						}

					}

					// chk isApp

				}

				model.addObject("bonusList", bonusList);
				System.err.println("bonus list" + bonusList.toString());
				Info add = AcessController.checkAccess("showBonusList", "showBonusList", 0, 1, 0, 0, newModuleList);
				Info edit = AcessController.checkAccess("showBonusList", "showBonusList", 0, 0, 1, 0, newModuleList);
				Info delete = AcessController.checkAccess("showBonusList", "showBonusList", 0, 0, 0, 1, newModuleList);

				if (add.isError() == false) {
					System.out.println(" add   Accessable ");
					model.addObject("addAccess", 0);

				}
				if (edit.isError() == false) {
					System.out.println(" edit   Accessable ");
					model.addObject("editAccess", 0);
				}
				if (delete.isError() == false) {
					System.out.println(" delete   Accessable ");
					model.addObject("deleteAccess", 0);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/checkBonusTitle", method = RequestMethod.GET)
	@ResponseBody
	public int checkEmailText(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		int res = 0;

		try {

			String bonusTitle = request.getParameter("bonusTitle");
			// System.out.println("Info" + voucherNo);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("bonusTitle", bonusTitle);

			info = Constants.getRestTemplate().postForObject(Constants.url + "/checkBonusTitle", map, Info.class);
			// System.out.println("Info" + info+"info.isError()"+info.isError());
			if (info.isError() == false) {
				res = 0;// not presents
				// System.out.println("0s" + res);
			} else {
				res = 1;// present
				// System.out.println("1888" + res);
			}

		} catch (Exception e) {
			System.err.println("Exception in checkBonusTitle : " + e.getMessage());
			e.printStackTrace();
		}

		return res;

	}

	@RequestMapping(value = "/deleteBonus", method = RequestMethod.GET)
	public String deleteBonus(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

			Info view = AcessController.checkAccess("deleteBonus", "showBonusList", 0, 0, 0, 1, newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			}

			else {

				a = "redirect:/showBonusList";

				String base64encodedString = request.getParameter("bonusId");
				String bonusId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("bonusId", bonusId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteBonus", map, Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", "Deleted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Delete");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		return a;
	}

	@RequestMapping(value = "/editBonus", method = RequestMethod.GET)
	public ModelAndView editBonus(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editBonus", "showBonusList", 0, 0, 1, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("Bonus/bonusEdit");
				String base64encodedString = request.getParameter("bonusId");
				String bonusId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("bonusId", bonusId);
				editBonus = Constants.getRestTemplate().postForObject(Constants.url + "/getBonusById", map,
						BonusMaster.class);
				model.addObject("editBonus", editBonus);
				model.addObject("dateString", editBonus.getFyFromdt().concat("to").concat(editBonus.getFyTodt()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitEditBonus", method = RequestMethod.POST)
	public String submitEditBonus(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = new String();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editBonus", "showBonusList", 0, 0, 1, 0, newModuleList);

		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showBonusList";
			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String leaveDateRange = request.getParameter("leaveDateRange");
				String bonusTitle = request.getParameter("bonusTitle");
				String bonusPrcnt = request.getParameter("bonusPrcnt");
				String bonusRemark = new String();
				String[] arrOfStr = leaveDateRange.split("to", 2);
				Boolean ret = false;

				try {
					bonusRemark = request.getParameter("bonusRemark");
				} catch (Exception e) {
					bonusRemark = "";
				}

				String exgratiaPrcnt = request.getParameter("exgratiaPrcnt");
				if (FormValidation.Validaton(exgratiaPrcnt, "") == true) {

					ret = true;
					System.out.println("exgratiaPrcnt" + ret);
				}

				if (FormValidation.Validaton(leaveDateRange, "") == true) {

					ret = true;
					System.out.println("leaveDateRange" + ret);
				}
				if (FormValidation.Validaton(bonusTitle, "") == true) {

					ret = true;
					System.out.println("bonusTitle" + ret);
				}
				if (FormValidation.Validaton(bonusPrcnt, "") == true) {

					ret = true;
					System.out.println("bonusPrcnt" + ret);
				}
				String minDays = request.getParameter("minDays");
				if (FormValidation.Validaton(minDays, "") == true) {

					ret = true;
					System.out.println("minDays" + ret);
				}
				if (ret == false) {

					editBonus.setBonusPercentage(Double.parseDouble(bonusPrcnt));
					editBonus.setExgratiaPercentage(Double.parseDouble(exgratiaPrcnt));
					/*
					 * editBonus.setDelStatus(1); editBonus.setExInt1(0); editBonus.setExInt2(0);
					 * editBonus.setExVar1("NA"); editBonus.setExVar2("NA");
					 */
					editBonus.setFyFromdt(arrOfStr[0].toString().trim());
					editBonus.setFyTitle(bonusTitle);
					editBonus.setFyTodt(arrOfStr[1].toString().trim());
					editBonus.setIsCurrent(1);
					editBonus.setMinDays(Integer.parseInt(minDays));
					editBonus.setRemark(bonusRemark);

					BonusMaster res = Constants.getRestTemplate().postForObject(Constants.url + "/saveBonus", editBonus,
							BonusMaster.class);

					if (res != null) {
						session.setAttribute("successMsg", "Bonus Updated Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Insert Record");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Insert Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}
		}

		return a;
	}

	@RequestMapping(value = "/showEmpListToAssignBonus", method = RequestMethod.GET)
	public String showEmpListToAssignBonus(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String mav = null;
		int isfinalized = 0;
		/*
		 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
		 * session.getAttribute("moduleJsonList"); Info view =
		 * AcessController.checkAccess("showEmpListToAssignBonus",
		 * "showEmpListToAssignBonus", 1, 0, 0, 0, newModuleList); ; if (view.isError()
		 * == true) { mav = "accessDenied";
		 * 
		 * } else {
		 */
		mav = "Bonus/assignBonus";

		try {

			String bonusName = null;
			String base64encodedString1 = request.getParameter("bonusId");
			String bonusId = FormValidation.DecodeKey(base64encodedString1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("bonusId", bonusId);
			map.add("flag", 0);

			GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllEmployeeDetailForBonus", map, GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
			model.addAttribute("empdetList", empdetList);

			BonusMaster[] location = Constants.getRestTemplate().getForObject(Constants.url + "/getAllBonusList",
					BonusMaster[].class);

			List<BonusMaster> bonusList = new ArrayList<BonusMaster>(Arrays.asList(location));
			model.addAttribute("bonusList", bonusList);
			model.addAttribute("bonusId", bonusId);
			map = new LinkedMultiValueMap<String, Object>();
			map.add("limitKey", "ammount_format_show");
			Setting getSettingByKey = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey", map,
					Setting.class);
			int amount_round = Integer.parseInt(getSettingByKey.getValue());
			for (int i = 0; i < bonusList.size(); i++) {

				if (bonusList.get(i).getBonusId() == Integer.parseInt(bonusId)) {
					model.addAttribute("bonusName", bonusList.get(i).getFyTitle());
				}

			}

			// new added

			map = new LinkedMultiValueMap<>();
			map.add("bonusId", bonusId);
			map.add("flag", 0);

			BonusCalc[] employeeDoc1 = Constants.getRestTemplate().postForObject(Constants.url + "/getBonusCalcList",
					map, BonusCalc[].class);

			List<BonusCalc> claimProofList1 = new ArrayList<BonusCalc>(Arrays.asList(employeeDoc1));

			for (int i = 0; i < claimProofList1.size(); i++) {

				claimProofList1.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(claimProofList1.get(i).getBonusCalcId())));
				claimProofList1.get(i)
						.setExVar2(FormValidation.Encrypt(String.valueOf(claimProofList1.get(i).getBonusId())));

				// number formatting

				claimProofList1.get(i).setTotalBonusWages(
						(int) (ReportCostants.castNumber(claimProofList1.get(i).getTotalBonusWages(), amount_round)));
				claimProofList1.get(i).setGrossBonusAmt(
						(ReportCostants.castNumber(claimProofList1.get(i).getGrossBonusAmt(), amount_round)));
				claimProofList1.get(i).setDedBonusPujaAmt(
						(ReportCostants.castNumber(claimProofList1.get(i).getDedBonusPujaAmt(), amount_round)));

				claimProofList1.get(i).setDedBonusAdvAmt(
						(ReportCostants.castNumber(claimProofList1.get(i).getDedBonusAdvAmt(), amount_round)));
				claimProofList1.get(i).setDedBonusLossAmt(
						(ReportCostants.castNumber(claimProofList1.get(i).getDedBonusLossAmt(), amount_round)));

				claimProofList1.get(i).setNetBonusAmt(
						(ReportCostants.castNumber(claimProofList1.get(i).getNetBonusAmt(), amount_round)));
				claimProofList1.get(i).setPaidBonusAmt(
						(ReportCostants.castNumber(claimProofList1.get(i).getPaidBonusAmt(), amount_round)));

			}

			model.addAttribute("bonusId", bonusId);
			model.addAttribute("bonusCalcList", claimProofList1);
			map = new LinkedMultiValueMap<>();
			map.add("bonusId", bonusId);
			BonusApplicable info = Constants.getRestTemplate().postForObject(Constants.url + "/chkIsBonusFinalized",
					map, BonusApplicable.class);

			// model.addAttribute("payRollFinal", info.getIsPayrollFinalized());
			try {
				model.addAttribute("bonusAppId", info.getBappNo());
				if (info.getIsBonussheetFinalized().equals("1")) {
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

	@RequestMapping(value = "/submitAssignBonusToEmp", method = RequestMethod.POST)
	public String submitAssignBonusToEmp(HttpServletRequest request, HttpServletResponse response) {
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

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/empBonusSave", map, Info.class);
			// System.err.println("info" + info.toString());
			if (info.isError() == false) {
				// retString = info.getMsg();
				session.setAttribute("successMsg", "Data Inserted Successfully");

			}
			a = "redirect:/showEmpListToAssignBonus?bonusId=" + FormValidation.Encrypt(bonusId);
		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}

		return a;
	}

	@RequestMapping(value = "/submitBonusApplicable", method = RequestMethod.POST)
	public String submitBonusApplicable(HttpServletRequest request, HttpServletResponse response) {
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

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/empBonusAppSaveOrUpdate", map,
					Info.class);
			System.err.println("info" + info.toString());
			if (info.isError() == false) {
				// retString = info.getMsg();
				session.setAttribute("successMsg", "Data Inserted Successfully");

			} else {
				session.setAttribute("successMsg", "Failed to Insert Data");
				a = "redirect:/showEmpListToAssignBonus";
			}
		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}
		a = "redirect:/showEmpListToAssignBonus?bonusId=" + FormValidation.Encrypt(temp);
		return a;
	}

	@RequestMapping(value = "/deleteBonusCalc", method = RequestMethod.GET)
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
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteBonusCalc", map, Info.class);

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

	//// ************************Reward ********************

	@RequestMapping(value = "/showPayBonusTypeList", method = RequestMethod.GET)
	public String showPayBonusTypeList(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String mav = null;
		LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showPayDeductionList", "showPayDeductionList", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {

			try {
				mav = "Bonus/bonusTypeList";

				PayBonus[] pay = Constants.getRestTemplate().getForObject(Constants.url + "/getAllPayBonus",
						PayBonus[].class);
				List<PayBonus> payList = new ArrayList<PayBonus>(Arrays.asList(pay));
				for (int i = 0; i < payList.size(); i++) {

					payList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(payList.get(i).getPayTypeId())));
				}

				model.addAttribute("payList", payList);

				Info add = AcessController.checkAccess("showPayDeductionList", "showPayDeductionList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showPayDeductionList", "showPayDeductionList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showPayDeductionList", "showPayDeductionList", 0, 0, 0, 1,
						newModuleList);

				if (add.isError() == false) {
					System.out.println(" add   Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					System.out.println(" edit   Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					System.out.println(" delete   Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			} catch (Exception e) {
				System.out.println("Exception in showPayDeductionList : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;
	}

	@RequestMapping(value = "/showAddBonusType", method = RequestMethod.GET)
	public String showAddBonusType(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		String mav = new String();

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAddBonusType", "showPayDeductionList", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {
			mav = "accessDenied";
		} else {

			try {

				mav = "Bonus/addBonusPayType";
				model.addAttribute("title", "Add Reward Type");

				PayBonus pay = new PayBonus();
				model.addAttribute("pay", pay);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;
	}

	@RequestMapping(value = "/showEditBonusType", method = RequestMethod.GET)
	public String showEditBonusType(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		String mav = new String();

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showEditBonusType", "showPayDeductionList", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {
			mav = "accessDenied";
		} else {

			try {

				mav = "Bonus/addBonusPayType";

				model.addAttribute("title", "Edit Reward Type");
				String base64encodedString = request.getParameter("bonusTypeId");
				String bonusTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("payBonusTypeId", bonusTypeId);
				PayBonus pay = Constants.getRestTemplate().postForObject(Constants.url + "/getPayBonusById", map,
						PayBonus.class);
				model.addAttribute("pay", pay);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;
	}

	@RequestMapping(value = "/submitBonusType", method = RequestMethod.POST)
	public String submitBonusType(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = new String();

		a = "redirect:/showPayBonusTypeList";
		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String bonusType = request.getParameter("bonusType");
			String bonusRate = request.getParameter("bonusRate");
			int payTypeId = Integer.parseInt(request.getParameter("payTypeId"));

			Boolean ret = false;

			if (FormValidation.Validaton(bonusType, "") == true) {

				ret = true;
				System.out.println("bonusType" + ret);
			}
			if (FormValidation.Validaton(bonusRate, "") == true) {

				ret = true;
				System.out.println("bonusRate" + ret);
			}

			if (ret == false) {

				PayBonus pay = new PayBonus();

				pay.setPayRate(Double.parseDouble(bonusRate));
				pay.setPayTypeId(payTypeId);
				pay.setTypeName(bonusType);
				pay.setIsUsed(1);
				pay.setDelStatus(1);
				pay.setEnterMakerDatetime(sf.format(date));
				pay.setExInt1(0);
				pay.setExInt2(0);
				pay.setExVar1("NA");
				pay.setExVar2("NA");
				PayBonus res = Constants.getRestTemplate().postForObject(Constants.url + "/savePayBonusType", pay,
						PayBonus.class);

				if (res != null) {
					session.setAttribute("successMsg", "Record Inserted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Insert Record");
				}

			} else {
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Insert Record");
		}

		return a;
	}

	@RequestMapping(value = "/deleteBonusType", method = RequestMethod.GET)
	public String deleteBonusType(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

			Info view = AcessController.checkAccess("deleteBonusType", "showPayDeductionList", 0, 0, 0, 1,
					newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			}

			else {

				String base64encodedString = request.getParameter("bonusTypeId");
				String bonusTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("payBonusTypeId", bonusTypeId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deletePayBonus", map,
						Info.class);

				a = "redirect:/showPayBonusTypeList";
				if (info.isError() == false) {
					session.setAttribute("successMsg", "Deleted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Delete");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		return a;
	}

	@RequestMapping(value = "/viewEmpRewarAddList", method = RequestMethod.GET)
	public String viewEmpRewarAddList(HttpServletRequest request, HttpServletResponse responser, Model model) {

		HttpSession session = request.getSession();
		String mav = null;
		LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("viewEmpRewarAddList", "viewEmpRewarAddList", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {

			try {
				mav = "Bonus/rewardEmpList";

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				EmployeeMaster[] empArr = Constants.getRestTemplate().postForObject(Constants.url + "/getAllEmployee",
						map, EmployeeMaster[].class);
				List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>(Arrays.asList(empArr));

				for (int i = 0; i < empList.size(); i++) {

					empList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(empList.get(i).getEmpId())));
				}

				Info edit = AcessController.checkAccess("viewEmpRewarAddList", "viewEmpRewarAddList", 0, 0, 1, 0,
						newModuleList);
				if (edit.isError() == false) {
					System.out.println(" edit   Accessable ");
					model.addAttribute("editAccess", 0);
				}

				model.addAttribute("empList", empList);

			} catch (Exception e) {
				System.out.println("Exception in showPayDeductionList : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;

	}

	@RequestMapping(value = "/empAddReward", method = RequestMethod.GET)
	public String payDeductEmployee(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = null;

		HttpSession session = request.getSession();
 		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("empAddReward", "payRewardDetails", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {

			try {

				String base64encodedString = request.getParameter("empId");
				String empId = FormValidation.DecodeKey(base64encodedString);

				mav = "Bonus/addEmpReward";

				PayBonus[] pay = Constants.getRestTemplate().getForObject(Constants.url + "/getAllPayBonus",
						PayBonus[].class);
				List<PayBonus> payList = new ArrayList<PayBonus>(Arrays.asList(pay));

				model.addAttribute("payList", payList);
				model.addAttribute("empId", empId);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empId", empId);

				EmployeeMaster emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeById", map,
						EmployeeMaster.class);
				model.addAttribute("emp", emp);

			} catch (Exception e) {
				System.out.println("Exception in /submitInsertPayDeductType : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;
	}

	@RequestMapping(value = "/getRewardRate", method = RequestMethod.GET)
	public @ResponseBody PayBonus getRewardRate(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		PayBonus deductionRate = new PayBonus();
		try {

			int deducType = Integer.parseInt(request.getParameter("deductType"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("payBonusTypeId", deducType);
			deductionRate = Constants.getRestTemplate().postForObject(Constants.url + "/getPayBonusById", map,
					PayBonus.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return deductionRate;

	}

	@RequestMapping(value = "/insertRewardDetail", method = RequestMethod.POST)
	public String insertRewardDetail(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String monthyear = request.getParameter("monthyear");
			System.err.println(sf.format(date));
			String a[] = monthyear.split("-");

			int empId = Integer.parseInt(request.getParameter("empId"));
			int payTypeId = Integer.parseInt(request.getParameter("payTypeId"));

			PayBonusDetails pay = new PayBonusDetails();

			pay.setCmpId(1);
			pay.setEmpId(empId);
			pay.setPayTypeId(payTypeId);
			pay.setMonth(Integer.parseInt(a[0]));
			pay.setYear(Integer.parseInt(a[1]));
			pay.setPayRate(Double.parseDouble(request.getParameter("rewardRate")));
			pay.setPayRemark(request.getParameter("remark"));

			pay.setPayApprovalDatetime(sf.format(date));
			pay.setPayApprovalRemark("NA");
			pay.setPayLoginName("NA");
			pay.setPayLoginDateTime(sf.format(date));
			pay.setPayOccurence(0);

			pay.setPayTotal(0);
			pay.setFinalStatus(0);
			pay.setIsPaid(0);

			pay.setDelStatus(1);
			pay.setMakerEnterDatetime(sf.format(date));
			pay.setExInt1(0);
			pay.setExInt2(0);
			pay.setExVar1("NA");
			pay.setExVar2("NA");

			PayBonusDetails savePay = Constants.getRestTemplate().postForObject(Constants.url + "/savePayBonusDetails",
					pay, PayBonusDetails.class);

			if (savePay != null) {
				session.setAttribute("successMsg", "Record Inserted Successfully");
			} else {

				session.setAttribute("errorMsg", "Failed to Insert Record");
			}
		} catch (Exception e) {
			System.out.println("Exception in /payDeductionAdd : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/payRewardDetails";

	}

	@RequestMapping(value = "/deleteEmpReward", method = RequestMethod.GET)
	public String editEmpPayDeduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String a = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("deleteEmpReward", "payRewardDetails", 0, 0, 0, 1, newModuleList);
		if (view.isError() == true) {
			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/payRewardDetails";
			try {
				String base64encodedString = request.getParameter("deductId");
				String dedId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("payId", dedId);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deletePayBonusDet", map,
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
		}
		return a;
	}

	@RequestMapping(value = "/payRewardDetails", method = RequestMethod.GET)
	public String payRewardDetails(HttpServletRequest request, HttpServletResponse responser, Model model) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		String mav = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("payRewardDetails", "payRewardDetails", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {

			try {
				mav = "Bonus/payRewardDetailList";

				// MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				PayBonusDetails[] deductDetailArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllPayPedingDetails", PayBonusDetails[].class);
				List<PayBonusDetails> rewardList = new ArrayList<PayBonusDetails>(Arrays.asList(deductDetailArr));

				for (int i = 0; i < rewardList.size(); i++) {

					rewardList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(rewardList.get(i).getPayId())));
				}

				model.addAttribute("rewardList", rewardList);

				Info add = AcessController.checkAccess("payDeductionDetails", "payDeductionDetails", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("payDeductionDetails", "payDeductionDetails", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("payDeductionDetails", "payDeductionDetails", 0, 0, 0, 1,
						newModuleList);

				if (add.isError() == false) {
					System.out.println(" add   Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					System.out.println(" edit   Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					System.out.println(" delete   Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			} catch (Exception e) {
				System.out.println("Exception in payDeductionDetails : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;

	}

}
