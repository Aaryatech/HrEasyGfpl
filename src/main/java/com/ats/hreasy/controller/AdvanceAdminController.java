package com.ats.hreasy.controller;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.EmpShiftDetails;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.InfoForUploadAttendance;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.ShiftMaster;
import com.ats.hreasy.model.TblEmpInfo;
import com.ats.hreasy.model.User;
import com.ats.hreasy.model.Advance.Advance;
import com.ats.hreasy.model.Advance.AdvanceDetails;
import com.ats.hreasy.model.Advance.GetAdvance;

@Controller
@Scope("session")
public class AdvanceAdminController {

	@RequestMapping(value = "/showEmpListToAddAdvance", method = RequestMethod.GET)
	public ModelAndView showEmpListToAssignShift(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showEmpListToAddAdvance", "showEmpListToAddAdvance", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("Advance/employeeListForAdvance");

			Info edit = AcessController.checkAccess("showEmpListToAddAdvance", "showEmpListToAddAdvance", 0, 0, 1, 0,
					newModuleList);
			if (edit.isError() == false) {
				System.out.println(" edit   Accessable ");
				model.addObject("editAccess", 0);
			}

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
		}
		return model;
	}

	@RequestMapping(value = "/showAddAdvance", method = RequestMethod.GET)
	public ModelAndView showAddAdvance(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAddAdvance", "showEmpListToAddAdvance", 0, 0, 1, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			model = new ModelAndView("Advance/addAdvance");
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
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertAdvance", method = RequestMethod.POST)
	public String submitInsertAdvance(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAddAdvance", "showEmpListToAddAdvance", 0, 0, 1, 0, newModuleList);
		String a = new String();
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showEmpListToAddAdvance";
		}

		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			Date date2 = new Date();
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String voucherNo = request.getParameter("voucherNo");
			String remark = request.getParameter("remark");
			String advanceAmt = request.getParameter("advanceAmt");
			int empId = Integer.parseInt(request.getParameter("empId"));

			String month = request.getParameter("date");
			String advDate = request.getParameter("advDate");

			String temp[] = month.split("-");
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
				adv.setAdvDate(DateConvertor.convertToYMD(advDate));
				adv.setAdvRemainingAmount(Double.parseDouble(advanceAmt));
				adv.setAdvRemarks(remark);
				adv.setCmpId(1);
				adv.setEmpId(empId);
				adv.setDedMonth(Integer.parseInt(temp[0]));
				adv.setDedYear(Integer.parseInt(temp[1]));
				adv.setExInt1(0);
				adv.setExInt2(0);
				adv.setExVar1("NA");
				adv.setExVar2("NA");
				adv.setVoucherNo(voucherNo);
				adv.setIsDed(0);
				adv.setIsUsed(0);
				adv.setLoginName(String.valueOf(userObj.getEmpId()));
				adv.setLoginTime(sf2.format(date2));
				adv.setSkipId(0);
				/*
				 * adv.setSkipLoginName("0"); adv.setSkipLoginTime("0000-00-00 00:00:00");
				 * adv.setSkipRemarks("");
				 */
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

		return a;
	}

	@RequestMapping(value = "/showEmpAdvancePendingList", method = RequestMethod.GET)
	public ModelAndView showEmpAdvancePendingList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showEmpAdvancePendingList", "showEmpAdvancePendingList", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("Advance/advancePendingList");

			Info add = AcessController.checkAccess("showEmpAdvancePendingList", "showEmpAdvancePendingList", 0, 1, 0, 0,
					newModuleList);
			Info edit = AcessController.checkAccess("showEmpAdvancePendingList", "showEmpAdvancePendingList", 0, 0, 1,
					0, newModuleList);
			Info delete = AcessController.checkAccess("showEmpAdvancePendingList", "showEmpAdvancePendingList", 0, 0, 0,
					1, newModuleList);

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
			try {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				GetAdvance[] empdetList1 = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getPendingAdvance", map, GetAdvance[].class);

				List<GetAdvance> empdetList = new ArrayList<GetAdvance>(Arrays.asList(empdetList1));
				model.addObject("empdetList", empdetList);
				// System.out.println(" Advance Info-------" + empdetList.toString());
				for (int i = 0; i < empdetList.size(); i++) {

					empdetList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(empdetList.get(i).getId())));
					empdetList.get(i).setExVar2(FormValidation.Encrypt(String.valueOf(empdetList.get(i).getEmpId())));
					empdetList.get(i).setAdvDate(DateConvertor.convertToDMY(empdetList.get(i).getAdvDate()));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/deleteAdvance", method = RequestMethod.GET)
	public String deleteLocation(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

			Info view = AcessController.checkAccess("deleteAdvance", "showEmpAdvancePendingList", 0, 0, 0, 1,
					newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			}

			else {

				a = "redirect:/showEmpAdvancePendingList";
				// }
				String base64encodedString = request.getParameter("advId");
				String advId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("advId", advId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteAdvance", map,
						Info.class);

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
		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAdvanceHistory", "showAdvanceHistory", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("Advance/advanceHistory");
			try {

				GetEmployeeDetails[] empdetList2 = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

				List<GetEmployeeDetails> empdetList3 = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList2));
				model.addObject("empdetList", empdetList3);
				// System.out.println(" Info-------" + empdetList3.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/getAdvanceHistory", method = RequestMethod.GET)
	public @ResponseBody List<GetAdvance> empInfoHistoryReportList(HttpServletRequest request,
			HttpServletResponse response) {

		List<GetAdvance> employeeInfoList = new ArrayList<GetAdvance>();
		ModelAndView model = null;
		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAdvanceHistory", "showAdvanceHistory", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("Advance/advanceHistory");
			try {

				int empId = Integer.parseInt(request.getParameter("empId"));
				String calYrId = request.getParameter("calYrId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empId", empId);
				map.add("calYrId", calYrId);
				map.add("companyId", 1);

				GetAdvance[] employeeInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAdvanceHistory", map, GetAdvance[].class);

				employeeInfoList = new ArrayList<GetAdvance>(Arrays.asList(employeeInfo));
				// System.out.println("employeeInfoList" + employeeInfoList.toString());

				for (int i = 0; i < employeeInfoList.size(); i++) {

					employeeInfoList.get(i)
							.setAdvDate(DateConvertor.convertToDMY(employeeInfoList.get(i).getAdvDate()));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return employeeInfoList;
	}

	@RequestMapping(value = "/showSkipAdvance", method = RequestMethod.GET)
	public ModelAndView showSkipAdvance(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showSkipAdvance", "showEmpAdvancePendingList", 0, 0, 1, 0,
				newModuleList);
		String skipStr = new String();

		List<AdvanceDetails> advDetList = new ArrayList<>();
		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			model = new ModelAndView("Advance/skipAdvance");

			try {

				String base64encodedString = request.getParameter("advId");
				String advId = FormValidation.DecodeKey(base64encodedString);

				String base64encodedString1 = request.getParameter("empId");
				String empId = FormValidation.DecodeKey(base64encodedString1);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empId", empId);
				GetEmployeeDetails empPersInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
				// System.out.println("Edit EmpPersonal Info-------" + empPersInfo.toString());

				String empPersInfoString = empPersInfo.getEmpCode().concat(" ").concat(empPersInfo.getFirstName())
						.concat(empPersInfo.getSurname()).concat("-[").concat(empPersInfo.getEmpDesgn().concat("]"));
				model.addObject("empPersInfo", empPersInfo);
				model.addObject("empPersInfoString", empPersInfoString);

				map = new LinkedMultiValueMap<>();
				map.add("advId", advId);
				Advance advList = Constants.getRestTemplate().postForObject(Constants.url + "/getAdvanceById", map,
						Advance.class);

				map = new LinkedMultiValueMap<>();
				map.add("advId", advId);

				AdvanceDetails[] employeeInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAdvanceDetailsByAdvanceIdId", map, AdvanceDetails[].class);

				advDetList = new ArrayList<AdvanceDetails>(Arrays.asList(employeeInfo));
				model.addObject("advDetList", advDetList);

				model.addObject("count", advDetList.size());
				// System.err.println(advDetList.toString()+"***"+advId);
				advList.setAdvDate(DateConvertor.convertToDMY(advList.getAdvDate()));
				model.addObject("advList", advList);
				String a = Month.of(advList.getDedMonth()).name();
				model.addObject("monthName", a);

				if (advDetList.size() == 0) {
					skipStr = "-";

				} else {
					for (int i = 1; i < advDetList.size(); i++) {
						skipStr = skipStr.concat(",").concat(advDetList.get(i).getSkipRemarks());

					}

				}

				model.addObject("skipStr", skipStr);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertAdvanceSkip", method = RequestMethod.POST)
	public String submitInsertAdvanceSkip(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showSkipAdvance", "showEmpAdvancePendingList", 0, 0, 1, 0,
				newModuleList);
		String a = new String();
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showEmpAdvancePendingList";

			try {

				Date date2 = new Date();
				SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

				String remark = request.getParameter("remark");
				int count = Integer.parseInt(request.getParameter("count"));
				int advId = Integer.parseInt(request.getParameter("advId"));

				String skipStr = request.getParameter("skipStr");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("advId", advId);
				Advance advList = Constants.getRestTemplate().postForObject(Constants.url + "/getAdvanceById", map,
						Advance.class);

				String dedMonth = String.valueOf(advList.getDedMonth());
				String dedYear = String.valueOf(advList.getDedYear());

				if (dedMonth.length() == 1) {
					dedMonth = "0".concat(dedMonth);
				}
				String tempDate = "01-".concat(dedMonth).concat("-").concat(dedYear);
				LocalDate localDate = LocalDate.parse(DateConvertor.convertToYMD(tempDate));
				LocalDate oneMonthLater = localDate.plusMonths(1);

				Boolean ret = false;

				if (FormValidation.Validaton(remark, "") == true) {

					ret = true;
					System.out.println("remark" + ret);
				}
				if (ret == false) {
					AdvanceDetails advDet = new AdvanceDetails();

					advDet.setExInt1(0);
					advDet.setExInt2(0);
					advDet.setExVar1("NA");
					advDet.setExVar2("NA");
					advDet.setAdvId(advId);
					advDet.setDelStatus(1);
					advDet.setSkipRemarks(remark);
					advDet.setSkipLoginId(userObj.getEmpId());
					advDet.setSkipLoginTime(sf.format(date2));

					AdvanceDetails res = Constants.getRestTemplate()
							.postForObject(Constants.url + "/saveAdvanceDetails", advDet, AdvanceDetails.class);

					if (res != null) {

						map = new LinkedMultiValueMap<>();
						map.add("dateTimeUpdate", sf2.format(date2));
						map.add("userId", userObj.getEmpId());
						map.add("advId", advId);
						map.add("dedMonth", oneMonthLater.getMonthValue());
						map.add("dedYear", oneMonthLater.getYear());
						map.add("count", count + 1);
						map.add("skipStr", skipStr.concat(remark));

						Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateSkipAdvance", map,
								Info.class);

						if (info.isError() == false) {
							session.setAttribute("successMsg", "Advance Skipped  Successfully");

						} else {
							session.setAttribute("errorMsg", "Failed to Insert Record");
						}

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

	// ***********Change Pass********************

	@RequestMapping(value = "/changePass", method = RequestMethod.GET)
	public ModelAndView changePass(HttpServletRequest request, HttpServletResponse res) {
		HttpSession session = request.getSession();

		ModelAndView mav = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("changePass", "changePass", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			mav = new ModelAndView("accessDenied");

		} else {
			try {
				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

				mav = new ModelAndView("changePassword");
				mav.addObject("empId", userObj.getUserId());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;
	}

	@RequestMapping(value = "/checkPass", method = RequestMethod.POST)
	public @ResponseBody User updateLeaveLimit(HttpServletRequest request, HttpServletResponse response) {

		User user1 = new User();
		try {
			System.err.println("in  checkPass is ");
			String empId = (request.getParameter("empId"));
			String password = (request.getParameter("password"));

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(password.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// System.out.println(hashtext);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("password", hashtext);

			user1 = Constants.getRestTemplate().postForObject(Constants.url + "/getUserInfoByEmpIdPass", map,
					User.class);
			System.err.println("info is " + user1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user1;
	}

	@RequestMapping(value = "/submitUpdatePass", method = RequestMethod.POST)
	public String submitInsertCompany(HttpServletRequest request, HttpServletResponse response) {

		try {
			/*
			 * HttpSession session = request.getSession(); LoginResponse userObj =
			 * (LoginResponse) session.getAttribute("UserDetail"); Date date = new Date();
			 * SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 * SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			 * VpsImageUpload upload = new VpsImageUpload();
			 */
			String empId = request.getParameter("empId");
			String password = request.getParameter("password");
			String currPass = request.getParameter("currPass");
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			Pattern p = Pattern.compile("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$");
			Matcher m = p.matcher(password);

			if (currPass.equals(userObj.getUserPwd()) && m.matches()) {

				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] messageDigest = md.digest(password.getBytes());
				BigInteger number = new BigInteger(1, messageDigest);
				String hashtext = number.toString(16);

				System.out.println(hashtext);

				System.out.println("in if password " + password + " currPass " + currPass + " m.find() " + m.matches());
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empId", userObj.getUserId());
				map.add("password", hashtext);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateUserPass", map,
						Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", "password change successfully.");
				} else {
					session.setAttribute("errorMsg", "something wrong while changing password.");
				}
			} else {

				System.out
						.println("in else password " + password + " currPass " + currPass + " m.find() " + m.matches());
				session.setAttribute("errorMsg", "something wrong while changing password.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/changePass";
	}

	@RequestMapping(value = "/showEmpShiftDetails", method = RequestMethod.GET)
	public String attendanceSelectMonth(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = null;
		HttpSession session = request.getSession();

		mav = "master/empShiftDetails";

		try {

			GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));

			model.addAttribute("employeeInfoList", empdetList);
			String date = null;
			String empId = null;
			int daysNext = 0;
			int daysToday = 0;

			String today = new String();
			String nextMonthDay = new String();
			String today1 = new String();
			String nextMonthDay1 = new String();

			try {
				empId = request.getParameter("empId");
			} catch (Exception e) {
				empId = "0";

			}
			LocalDate date3 = null;

			Date dateToday = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sf2 = new SimpleDateFormat("MM-yyyy");

			today = String.valueOf(sf.format(dateToday));
			today1 = String.valueOf(sf2.format(dateToday));

			 System.err.println("today" + dateToday);

			model.addAttribute("today", today1);
			LocalDate localDate = LocalDate.parse((today));
			LocalDate oneMonthLater = localDate.plusMonths(1);

			nextMonthDay = String.valueOf(oneMonthLater);
			System.err.println("nextMonthDay" + nextMonthDay);
			
			
			String temp[]=nextMonthDay.split("-");
			

			model.addAttribute("nextMonthDay", temp[1].concat("-").concat(temp[0]));
			// System.err.println("nextMonthDay" +
			// DateConvertor.convertToDMY(nextMonthDay));

			String[] a = today.split("-");
			String year = a[0];
			String month = a[1];
			if (month.length() == 1) {
				month = "0".concat(month);
			}
			// System.err.println("**" + year + month);
			date3 = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt("01"));
			daysToday = date3.lengthOfMonth();

			a = nextMonthDay.split("-");
			year = a[0];
			String month1 = a[1];
			if (month1.length() == 1) {
				month1 = "0".concat(month1);
			}
			date3 = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month1), Integer.parseInt("01"));

			daysNext = date3.lengthOfMonth();

			try {
				date = request.getParameter("date");
				if (date.equals("1")) {
					model.addAttribute("days", daysToday);
					model.addAttribute("selDate", daysToday);
					model.addAttribute("monthVal", month);

				} else {
					model.addAttribute("days", daysNext);
					model.addAttribute("selDate", nextMonthDay);
					model.addAttribute("monthVal", month1);
				}

			} catch (Exception e) {
				date = null;
				model.addAttribute("days", 0);

			}

			model.addAttribute("date", date);
			model.addAttribute("empId", empId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map.add("nextMonthDay", nextMonthDay);
			map.add("today", today);
			map.add("companyId", 1);
			map.add("empRes", empId);
			map.add("daysNext", daysNext);
			map.add("daysToday", daysToday);

			EmpShiftDetails[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpShiftDetails", map, EmpShiftDetails[].class);

			ArrayList<EmpShiftDetails> daysList = new ArrayList<EmpShiftDetails>(Arrays.asList(employeeInfo));

			model.addAttribute("daysList", daysList);
			// System.err.println("nextMonthDay" +daysList.toString());
			if (daysList != null) {

				map = new LinkedMultiValueMap<>();
				map.add("empRes", empId);
				map.add("companyId", 1);

				EmployeeMaster[] empArr = Constants.getRestTemplate().postForObject(Constants.url + "/getAllEmp", map,
						EmployeeMaster[].class);
				List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>(Arrays.asList(empArr));

				model.addAttribute("empList", empList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;

	}

}
