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
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.ShiftMaster;
import com.ats.hreasy.model.Bonus.BonusCalc;
import com.ats.hreasy.model.Bonus.BonusMaster;

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

				if (ret == false) {

					BonusMaster bonus = new BonusMaster();

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

					BonusMaster res = Constants.getRestTemplate().postForObject(Constants.url + "/saveBonus", bonus,
							BonusMaster.class);

					if (res != null) {
						session.setAttribute("successMsg", "Location Inserted Successfully");
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

				for (int i = 0; i < bonusList.size(); i++) {

					bonusList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(bonusList.get(i).getBonusId())));
				}

				BonusCalc[] bonusCalc = Constants.getRestTemplate().getForObject(Constants.url + "/getAllBonusCalcList",
						BonusCalc[].class);

				List<BonusCalc> bonusCalcList = new ArrayList<BonusCalc>(Arrays.asList(bonusCalc));
				for (int i = 0; i < bonusList.size(); i++) {

					for (int j = 0; j < bonusCalcList.size(); j++) {

						if (bonusList.get(i).getBonusId() == bonusCalcList.get(j).getBonusId()) {
							bonusList.get(i).setExInt2(1);
						} else {
							bonusList.get(i).setExInt2(0);
						}

					}

				}

				model.addObject("bonusList", bonusList);
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
				//System.out.println("0s" + res);
			} else {
				res = 1;//present
			//	System.out.println("1888" + res);
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

				if (ret == false) {

					editBonus.setBonusPercentage(Double.parseDouble(bonusPrcnt));
					/*
					 * editBonus.setDelStatus(1); editBonus.setExInt1(0); editBonus.setExInt2(0);
					 * editBonus.setExVar1("NA"); editBonus.setExVar2("NA");
					 */
					editBonus.setFyFromdt(arrOfStr[0].toString().trim());
					editBonus.setFyTitle(bonusTitle);
					editBonus.setFyTodt(arrOfStr[1].toString().trim());
					editBonus.setIsCurrent(1);
					editBonus.setRemark(bonusRemark);

					BonusMaster res = Constants.getRestTemplate().postForObject(Constants.url + "/saveBonus", editBonus,
							BonusMaster.class);

					if (res != null) {
						session.setAttribute("successMsg", "Location Inserted Successfully");
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
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showEmpListToAssignBonus", "showEmpListToAssignBonus", 1, 0, 0, 0,
				newModuleList);
		String mav = null;
		if (view.isError() == true) {
			mav = "accessDenied";

		} else {
			mav = "Bonus/assignBonus";

			try {

				GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

				List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
				model.addAttribute("empdetList", empdetList);

				BonusMaster[] location = Constants.getRestTemplate().getForObject(Constants.url + "/getAllBonusList",
						BonusMaster[].class);

				List<BonusMaster> bonusList = new ArrayList<BonusMaster>(Arrays.asList(location));
				model.addAttribute("bonusList", bonusList);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		return mav;
	}

}
