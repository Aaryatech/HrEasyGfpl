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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.ShiftMaster;
import com.ats.hreasy.model.TblEmpInfo;
import com.ats.hreasy.model.Advance.Advance;
import com.ats.hreasy.model.Advance.GetAdvance;

@Controller
@Scope("session")
public class AdvanceAdminController {

	@RequestMapping(value = "/showEmpListToAddAdvance", method = RequestMethod.GET)
	public ModelAndView showEmpListToAssignShift(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Advance/employeeListForAdvance");

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

	@RequestMapping(value = "/showAddAdvance", method = RequestMethod.GET)
	public ModelAndView showAddAdvance(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Advance/addAdvance");

		try {

			String base64encodedString = request.getParameter("empId");
			String empTypeId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empTypeId);
			GetEmployeeDetails empPersInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
			// System.out.println("Edit EmpPersonal Info-------"+ empPersInfo.toString());

			String empPersInfoString = empPersInfo.getEmpCode().concat(" ").concat(empPersInfo.getFirstName())
					.concat(" ").concat(empPersInfo.getSurname());
			model.addObject("empPersInfo", empPersInfo);
			model.addObject("empPersInfoString", empPersInfoString);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertAdvance", method = RequestMethod.POST)
	public String submitInsertAdvance(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			Date date2 = new Date();
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String voucherNo = request.getParameter("voucherNo");
			String month = request.getParameter("month");
			String remark = request.getParameter("remark");
			String advanceAmt = request.getParameter("advanceAmt");
			int empId = Integer.parseInt(request.getParameter("empId"));

			Boolean ret = false;

			if (FormValidation.Validaton(voucherNo, "") == true) {

				ret = true;
				System.out.println("voucherNo" + ret);
			}
			if (FormValidation.Validaton(month, "") == true) {

				ret = true;
				System.out.println("month" + ret);
			}
			if (FormValidation.Validaton(remark, "") == true) {

				ret = true;
				System.out.println("remark" + ret);
			}
			if (FormValidation.Validaton(advanceAmt, "") == true) {

				ret = true;
				System.out.println("advanceAmt" + ret);
			}

			if (ret == false) {

				Advance adv = new Advance();
				adv.setAdvAmount(Double.parseDouble(advanceAmt));
				adv.setAdvDate(sf.format(date));
				adv.setAdvRemainingAmount(Double.parseDouble(advanceAmt));
				adv.setAdvRemarks(remark);
				adv.setCmpId(1);
				adv.setEmpId(empId);
				adv.setDedMonth(sf.format(date));
				adv.setDedYear(sf.format(date));
				adv.setEmpId(empId);
				adv.setExInt1(0);
				adv.setExInt2(0);
				adv.setExVar1("NA");
				adv.setExVar2("NA");
				adv.setVoucherNo(voucherNo);
				adv.setIsDed(0);
				adv.setIsUsed(0);
				adv.setLoginName("1");
				adv.setLoginTime(sf2.format(date2));
				adv.setSkipId(0);
				adv.setSkipLoginName("1");
				adv.setSkipLoginTime(sf2.format(date2));
				adv.setSkipRemarks("");
				adv.setDelStatus(1);

				Advance res = Constants.getRestTemplate().postForObject(Constants.url + "/saveMstEmpAdvance", adv,
						Advance.class);

				if (res != null) {
					session.setAttribute("successMsg", "Advance Inserted Successfully");
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

		return "redirect:/showEmpListToAddAdvance";
	}

	@RequestMapping(value = "/showEmpAdvancePendingList", method = RequestMethod.GET)
	public ModelAndView showEmpAdvancePendingList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Advance/advancePendingList");

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", 1);
			GetAdvance[] empdetList1 = Constants.getRestTemplate().postForObject(Constants.url + "/getPendingAdvance",
					map, GetAdvance[].class);

			List<GetAdvance> empdetList = new ArrayList<GetAdvance>(Arrays.asList(empdetList1));
			model.addObject("empdetList", empdetList);
			// System.out.println(" Advance Info-------" + empdetList.toString());
			for (int i = 0; i < empdetList.size(); i++) {

				empdetList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(empdetList.get(i).getId())));
				empdetList.get(i).setExVar2(FormValidation.Encrypt(String.valueOf(empdetList.get(i).getEmpId())));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/showSkipAdvance", method = RequestMethod.GET)
	public ModelAndView showSkipAdvance(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Advance/skipAdvance");

		try {

			String base64encodedString = request.getParameter("advId");
			String advId = FormValidation.DecodeKey(base64encodedString);

			String base64encodedString1 = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			GetEmployeeDetails empPersInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
			System.out.println("Edit EmpPersonal Info-------" + empPersInfo.toString());

			String empPersInfoString = empPersInfo.getEmpCode().concat(" ").concat(empPersInfo.getFirstName())
					.concat(" ").concat(empPersInfo.getSurname()).concat("[")
					.concat(empPersInfo.getEmpDesgn().concat("]"));
			model.addObject("empPersInfo", empPersInfo);
			model.addObject("empPersInfoString", empPersInfoString);

			map = new LinkedMultiValueMap<>();
			map.add("advId", advId);
			Advance advList = Constants.getRestTemplate().postForObject(Constants.url + "/getAdvanceById", map,
					Advance.class);

			model.addObject("advList", advList);

			System.out.println("Adv   Info-------" + empPersInfo.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/deleteAdvance", method = RequestMethod.GET)
	public String deleteLocation(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

			/*
			 * Info view = AcessController.checkAccess("deleteLocation", "showLocationList",
			 * 0, 0, 0, 1, newModuleList); if (view.isError() == true) {
			 * 
			 * a = "redirect:/accessDenied";
			 * 
			 * }
			 * 
			 * else {
			 */
			a = "redirect:/showEmpAdvancePendingList";
			// }
			String base64encodedString = request.getParameter("advId");
			String advId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("advId", advId);
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteAdvance", map, Info.class);

			if (info.isError() == false) {
				session.setAttribute("successMsg", "Deleted Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Delete");
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		return a;
	}

	@RequestMapping(value = "/checkVoucherNo", method = RequestMethod.GET)
	@ResponseBody
	public int checkEmailText(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		int res = 0;

		try {

			String voucherNo = request.getParameter("voucherNo");
			// System.out.println("Info" + voucherNo);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("voucherNo", voucherNo);

			info = Constants.getRestTemplate().postForObject(Constants.url + "/checkCustPhone", map, Info.class);
			// System.out.println("Info" + info+"info.isError()"+info.isError());
			if (info.isError() == false) {
				res = 0;// exists
				System.out.println("0s" + res);
			} else {
				res = 1;
				System.out.println("1888" + res);
			}

		} catch (Exception e) {
			System.err.println("Exception in checkEmailText : " + e.getMessage());
			e.printStackTrace();
		}

		return res;

	}

	@RequestMapping(value = "/showAdvanceHistory", method = RequestMethod.GET)
	public ModelAndView showAdvanceHistory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Advance/advanceHistory");

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", 1);
			GetAdvance[] empdetList = Constants.getRestTemplate().postForObject(Constants.url + "/getAdvanceHistory",
					map, GetAdvance[].class);

			List<GetAdvance> empdetList1 = new ArrayList<GetAdvance>(Arrays.asList(empdetList));
			model.addObject("advanceList", empdetList1);
			System.out.println("  Advance Info-------" + empdetList1.toString());
			GetEmployeeDetails[] empdetList2 = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList3 = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList2));
			model.addObject("empdetList", empdetList3);
			System.out.println("   Info-------" + empdetList3.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

}
