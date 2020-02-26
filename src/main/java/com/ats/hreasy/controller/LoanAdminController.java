package com.ats.hreasy.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
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

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.common.ReportCostants;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.Setting;
import com.ats.hreasy.model.Advance.Advance;
import com.ats.hreasy.model.Advance.GetAdvance;
import com.ats.hreasy.model.Loan.GetLoan;
import com.ats.hreasy.model.Loan.LoanCalculation;
import com.ats.hreasy.model.Loan.LoanDetails;
import com.ats.hreasy.model.Loan.LoanMain;

@Controller
@Scope("session")
class LoanAdminController {

	@RequestMapping(value = "/showEmpListToAddLoan", method = RequestMethod.GET)
	public ModelAndView showEmpListToAddLoan(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showEmpListToAddLoan", "showEmpListToAddLoan", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("Loan/empListToAddLoan");
			Info edit = AcessController.checkAccess("showEmpListToAddLoan", "showEmpListToAddLoan", 0, 0, 1, 0,
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

	@RequestMapping(value = "/showAddLoan", method = RequestMethod.GET)
	public ModelAndView showAddAdvance(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView model = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAddLoan", "showEmpListToAddLoan", 0, 0, 1, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("Loan/addLoan");
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			try {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("limitKey", "loan_number");
				Setting getSettingByKey = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey",
						map, Setting.class);

				model.addObject("appNo", getSettingByKey.getValue());

				String base64encodedString = request.getParameter("empId");
				String empTypeId = FormValidation.DecodeKey(base64encodedString);

				map = new LinkedMultiValueMap<>();
				map.add("empId", empTypeId);
				GetEmployeeDetails empPersInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
				// System.out.println("Edit EmpPersonal Info-------"+ empPersInfo.toString());

				String empPersInfoString = empPersInfo.getEmpCode().concat(" ").concat(empPersInfo.getFirstName())
						.concat(" ").concat(empPersInfo.getSurname()).concat(" [").concat(empPersInfo.getEmpDesgn())
						.concat("]");
				model.addObject("empPersInfo", empPersInfo);
				model.addObject("empPersInfoString", empPersInfoString);
				model.addObject("todaysDate", sf.format(date));

				map = new LinkedMultiValueMap<>();
				map.add("empId", empTypeId);
				LoanMain empPersInfo1 = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpLoanHistory",
						map, LoanMain.class);

				model.addObject("prevLoan", empPersInfo1);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/loanCalculation", method = RequestMethod.GET)
	public @ResponseBody LoanCalculation loanCalculation(HttpServletRequest request, HttpServletResponse response) {

		LoanCalculation loan = new LoanCalculation();

		try {

			String roi = request.getParameter("roi");
			String tenure = request.getParameter("tenure");
			String loanAmt = request.getParameter("loanAmt");
			String startDate = request.getParameter("startDate");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("roi", roi);
			map.add("tenure", tenure);
			map.add("loanAmt", loanAmt);
			map.add("startDate", DateConvertor.convertToYMD(startDate));

			loan = Constants.getRestTemplate().postForObject(Constants.url + "/calLoan", map, LoanCalculation.class);
			loan.setCalDate(DateConvertor.convertToDMY(loan.getCalDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loan;
	}

	@RequestMapping(value = "/submitInsertLoan", method = RequestMethod.POST)
	public String submitInsertLoan(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		ModelAndView model = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAddLoan", "showEmpListToAddLoan", 0, 0, 1, 0, newModuleList);
		String a = new String();
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {
			a = "redirect:/showEmpListToAddLoan";
			;
			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");

				Date date2 = new Date();
				SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String emi = request.getParameter("emi");
				String loanAmt = request.getParameter("loanAmt");
				String remark = request.getParameter("remark");
				String tenure = request.getParameter("tenure");
				String roi = request.getParameter("roi");
				String repayAmt = request.getParameter("repayAmt");
				String appNo = request.getParameter("appNo");
				int empId = Integer.parseInt(request.getParameter("empId"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("limitKey", "loan_number");
				Setting getSettingByKey = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey",
						map, Setting.class);

				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				/*
				 * String[] arrOfStr = leaveDateRange.split("to", 2);
				 * System.err.println("date1 is " + arrOfStr[0].toString().trim());
				 * System.err.println("date2 is " + arrOfStr[1].toString().trim());
				 */

				Boolean ret = false;

				if (FormValidation.Validaton(emi, "") == true) {

					ret = true;
					System.out.println("emi" + ret);
				}
				if (FormValidation.Validaton(loanAmt, "") == true) {

					ret = true;
					System.out.println("loanAmt" + ret);
				}
				if (FormValidation.Validaton(remark, "") == true) {

					ret = true;
					System.out.println("remark" + ret);
				}
				if (FormValidation.Validaton(tenure, "") == true) {

					ret = true;
					System.out.println("tenure" + ret);
				}
				if (FormValidation.Validaton(roi, "") == true) {

					ret = true;
					System.out.println("roi" + ret);
				}
				if (FormValidation.Validaton(repayAmt, "") == true) {

					ret = true;
					System.out.println("repayAmt" + ret);
				}

				if (FormValidation.Validaton(startDate, "") == true) {

					ret = true;
					System.out.println("startDate" + ret);
				}

				if (FormValidation.Validaton(endDate, "") == true) {

					ret = true;
					System.out.println("endDate" + ret);
				}

				if (ret == false) {

					LoanMain adv = new LoanMain();
					int temp = Integer.parseInt(loanAmt) / Integer.parseInt(tenure);

					adv.setAllData("");
					adv.setCurrentOutstanding(Integer.parseInt(repayAmt));
					adv.setCurrentTotpaid(0);
					adv.setLoanAddDate(sf.format(date2));
					adv.setLoanAmt(Integer.parseInt(loanAmt));
					adv.setLoanEmi(temp);
					adv.setLoanEmiIntrest(Integer.parseInt(emi));
					adv.setLoanRepayAmt(Integer.parseInt(repayAmt));
					adv.setLoanRepayEnd(endDate);
					adv.setLoanRepayStart(startDate);
					adv.setLoanRoi(Double.parseDouble(roi));
					adv.setLoanStatus("Active");
					adv.setLoanTenure(Integer.parseInt(tenure));

					adv.setRemark(remark);
					adv.setCmpId(1);
					adv.setEmpId(empId);

					adv.setExInt1(0);
					adv.setExInt2(0);
					adv.setExVar1("NA");
					adv.setExVar2("NA");
					adv.setLoanApplNo(getSettingByKey.getValue());

					adv.setLoginName(String.valueOf(userObj.getEmpId()));
					adv.setLoginTime(sf2.format(date2));
					adv.setSkipId(0);
					adv.setDelStatus(1);

					LoanMain res = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmpLoan", adv,
							LoanMain.class);

					if (res != null) {
						session.setAttribute("successMsg", "Loan Inserted Successfully");
						map = new LinkedMultiValueMap<>();
						map.add("settingId", getSettingByKey.getSettingId());
						map.add("val", Integer.parseInt(getSettingByKey.getValue()) + 1);

						Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateSetting", map,
								Info.class);

					} else {
						session.setAttribute("errorMsg", "Failed to Insert Loan");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Insert Loan");
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}
		}
		return a;
	}

	@RequestMapping(value = "/showLoanHistory", method = RequestMethod.GET)
	public ModelAndView showAdvanceHistory(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showLoanHistory", "showLoanHistory", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("Loan/loanHistory");

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

	@RequestMapping(value = "/getLoanHistory", method = RequestMethod.GET)
	public @ResponseBody List<GetLoan> getLoanHistory(HttpServletRequest request, HttpServletResponse response) {

		List<GetLoan> employeeInfoList = new ArrayList<GetLoan>();

		try {

			String calYrId = request.getParameter("calYrId");
			String status = request.getParameter("status");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("status", status);
			map.add("calYrId", calYrId);
			map.add("companyId", 1);

			GetLoan[] employeeInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanHistoryEmpWise",
					map, GetLoan[].class);

			employeeInfoList = new ArrayList<GetLoan>(Arrays.asList(employeeInfo));
			// System.out.println("employeeInfoList" + employeeInfoList.toString());

			for (int i = 0; i < employeeInfoList.size(); i++) {

				employeeInfoList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(employeeInfoList.get(i).getEmpId())));
				employeeInfoList.get(i).setExVar2(FormValidation.Encrypt(status));

				employeeInfoList.get(i).setExVar3(FormValidation.Encrypt(calYrId));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeInfoList;
	}

	@RequestMapping(value = "/showLoanDetailHistory", method = RequestMethod.GET)
	public ModelAndView showLoanDetailHistory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Loan/loanDetailHistory");
		List<LoanMain> employeeInfoList = new ArrayList<LoanMain>();
		GetLoan loan = new GetLoan();
		try {

			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);
			String base64encodedString1 = request.getParameter("status");
			String status = FormValidation.DecodeKey(base64encodedString1);
			String base64encodedString2 = request.getParameter("calYrId");
			String calYrId = FormValidation.DecodeKey(base64encodedString2);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("status", status);
			map.add("calYrId", calYrId);
			map.add("companyId", 1);
			map.add("empId", empId);

			LoanMain[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLoanHistoryEmpWiseDetail", map, LoanMain[].class);

			employeeInfoList = new ArrayList<LoanMain>(Arrays.asList(employeeInfo));
			// System.out.println("employeeInfoList" + employeeInfoList.toString());
			model.addObject("loanList", employeeInfoList);

			map = new LinkedMultiValueMap<>();
			map.add("status", status);
			map.add("calYrId", calYrId);
			map.add("companyId", 1);
			map.add("empId", empId);
			loan = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanHistoryEmpWiseSpec", map,
					GetLoan.class);
			model.addObject("empDeatil", loan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/// Loan Actionss
	@RequestMapping(value = "/showCompLoanList", method = RequestMethod.GET)
	public ModelAndView showCompLoanList(HttpServletRequest request, HttpServletResponse response) {

		List<GetLoan> employeeInfoList = new ArrayList<GetLoan>();
		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showCompLoanList", "showCompLoanList", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("Loan/companyLoanList");
			Info edit = AcessController.checkAccess("showCompLoanList", "showCompLoanList", 0, 0, 1, 0, newModuleList);
			if (edit.isError() == false) {
				System.out.println(" edit   Accessable ");
				model.addObject("editAccess", 0);
			}
			try {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				GetLoan[] employeeInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLoanHistoryEmpWiseForCompany", map, GetLoan[].class);

				employeeInfoList = new ArrayList<GetLoan>(Arrays.asList(employeeInfo));
				System.out.println("employeeInfoList" + employeeInfoList.toString());

				model.addObject("loanList", employeeInfoList);

				/*
				 * map = new LinkedMultiValueMap<String, Object>(); map.add("limitKey",
				 * "ammount_format_show"); Setting getSettingByKey =
				 * Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey",
				 * map, Setting.class); int amount_round =
				 * Integer.parseInt(getSettingByKey.getValue());
				 */
				for (int i = 0; i < employeeInfoList.size(); i++) {

					employeeInfoList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(employeeInfoList.get(i).getEmpId())));

					/*
					 * employeeInfoList.get(i).setLoanAmt((int)
					 * (ReportCostants.castNumber(employeeInfoList.get(i).getLoanAmt(),
					 * amount_round))); employeeInfoList.get(i).setLoanRepayAmt((int)
					 * (ReportCostants.castNumber(employeeInfoList.get(i).getLoanRepayAmt(),
					 * amount_round))); employeeInfoList.get(i).setLoanEmi((int)
					 * (ReportCostants.castNumber(employeeInfoList.get(i).getLoanEmi(),
					 * amount_round))); employeeInfoList.get(i).setCurrentOutstanding((int)
					 * (ReportCostants.castNumber(employeeInfoList.get(i).getCurrentOutstanding(),
					 * amount_round)));
					 */

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/showLoanListForAction", method = RequestMethod.GET)
	public ModelAndView showLoanListForAction(HttpServletRequest request, HttpServletResponse response) {
		Date date2 = new Date();
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<LoanMain> employeeInfoList = new ArrayList<LoanMain>();
		GetLoan loan = new GetLoan();

		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("MM-yyyy");
		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showLoanListForAction", "showCompLoanList", 0, 0, 1, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("Loan/loanListForAction");
			try {

				String base64encodedString = request.getParameter("empId");
				String empId = FormValidation.DecodeKey(base64encodedString);
				System.err.println("empId showLoanListForAction" + empId);
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				map.add("empId", empId);
				loan = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanHistoryEmpWiseSpecForCompany",
						map, GetLoan.class);
				model.addObject("empDeatil", loan);

				map.add("companyId", 1);
				map.add("empId", empId);

				LoanMain[] employeeInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLoanHistoryEmpWiseDetailComp", map, LoanMain[].class);

				employeeInfoList = new ArrayList<LoanMain>(Arrays.asList(employeeInfo));
				//System.out.println("employeeInfoList" + employeeInfoList.toString());
				model.addObject("loanList", employeeInfoList);

				for (int i = 0; i < employeeInfoList.size(); i++) {
					employeeInfoList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(employeeInfoList.get(i).getId())));
					employeeInfoList.get(i)
							.setExVar2(FormValidation.Encrypt(String.valueOf(employeeInfoList.get(i).getEmpId())));

					String a =new String();
					
					
					if(employeeInfoList.get(i).getSkipLoginTime()!=null) {
						Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(employeeInfoList.get(i).getSkipLoginTime());

						// System.out.println("date------" + sf.format(date));
						  a = sf.format(date1);
					}else {
						a="";
					}
					

					// System.out.println("a------" + a);

					if (sf.format(date).equals(a)) {
						employeeInfoList.get(i).setExInt1(1);
					} else {
						employeeInfoList.get(i).setExInt1(2);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	// Loan Skipping

	@RequestMapping(value = "/showSkipLoan", method = RequestMethod.GET)
	public ModelAndView showSkipLoan(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Loan/skipLoan");

		try {

			String base64encodedString = request.getParameter("id");
			String id = FormValidation.DecodeKey(base64encodedString);

			String base64encodedString1 = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString1);
			//System.out.println("showSkipLoan empId -------" + empId);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			GetEmployeeDetails empPersInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
			//System.out.println("  EmpPersonal Info-------" + empPersInfo.toString());

			String empPersInfoString = empPersInfo.getEmpCode().concat(" ").concat(empPersInfo.getFirstName())
					.concat(" ").concat(empPersInfo.getSurname()).concat("[")
					.concat(empPersInfo.getEmpDesgn().concat("]"));
			model.addObject("empPersInfo", empPersInfo);
			model.addObject("empPersInfoString", empPersInfoString);
			model.addObject("encEmpId", FormValidation.Encrypt(String.valueOf(empPersInfo.getEmpId())));

			map = new LinkedMultiValueMap<>();
			map.add("id", id);
			LoanMain advList = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanById", map,
					LoanMain.class);

			//System.err.println("-----" + advList.toString());
			model.addObject("advList", advList);

			String skipStr = new String();
			if (advList.getSkipId() == 0) {
				skipStr = "-";
				model.addObject("skipStr", skipStr);
			} else {
				String abc = new String();
				String csv = advList.getSkipRemarks();

				String[] elements = csv.split(",");

				List<String> fixedLenghtList = Arrays.asList(elements);

				ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);

				model.addObject("listOfString", listOfString);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	// Loan ForeClose
	@RequestMapping(value = "/showForeCloseLoan", method = RequestMethod.GET)
	public ModelAndView showForeCloseLoan(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Loan/foreCloseLoan");

		try {

			String base64encodedString = request.getParameter("id");
			String id = FormValidation.DecodeKey(base64encodedString);

			String base64encodedString1 = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			GetEmployeeDetails empPersInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
			// System.out.println("Edit EmpPersonal Info-------" + empPersInfo.toString());
			model.addObject("encEmpId", FormValidation.Encrypt(String.valueOf(empPersInfo.getEmpId())));

			String empPersInfoString = empPersInfo.getEmpCode().concat(" ").concat(empPersInfo.getFirstName())
					.concat(" ").concat(empPersInfo.getSurname()).concat("[")
					.concat(empPersInfo.getEmpDesgn().concat("]"));
			model.addObject("empPersInfo", empPersInfo);
			model.addObject("empPersInfoString", empPersInfoString);

			map = new LinkedMultiValueMap<>();
			map.add("id", id);
			LoanMain advList = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanById", map,
					LoanMain.class);

			model.addObject("advList", advList);

			map = new LinkedMultiValueMap<>();
			map.add("loanId", id);
			LoanDetails[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpLoanDetailByMainId", map, LoanDetails[].class);

			List<LoanDetails> laonDetalList = new ArrayList<LoanDetails>(Arrays.asList(employeeInfo));

			model.addObject("laonDetalList", laonDetalList);
			for (int i = 0; i < laonDetalList.size(); i++) {

				int monthNumber = laonDetalList.get(i).getMonths();
				String a = Month.of(monthNumber).name();
				laonDetalList.get(i).setLoginName(a);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitLoanForeClose", method = RequestMethod.POST)
	public String submitInsertAdvanceSkip(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		String empId = null;
		try {

			Date date2 = new Date();
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy");

			Date date1 = new Date();
			SimpleDateFormat sf1 = new SimpleDateFormat("MM");

			String remark = request.getParameter("remark");
			String closeDate = request.getParameter("joiningDate");
			String foreclose_amt = request.getParameter("foreclose_amt");
			int id = Integer.parseInt(request.getParameter("id"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map = new LinkedMultiValueMap<>();
			map.add("id", id);
			LoanMain advList = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanById", map,
					LoanMain.class);

			Boolean ret = false;
			empId = FormValidation.Encrypt(String.valueOf(advList.getEmpId()));
			if (FormValidation.Validaton(remark, "") == true) {

				ret = true;
				System.out.println("remark" + ret);
			}
			if (ret == false) {
				LoanDetails adv = new LoanDetails();
				adv.setAmountEmi(Integer.parseInt(foreclose_amt));
				adv.setLoanMainId(advList.getId());
				adv.setLoginName(String.valueOf(userObj.getEmpId()));
				adv.setLoginTime(sf2.format(date2));
				adv.setMonths(Integer.parseInt(sf1.format(date1)));
				adv.setPayType("FC");
				adv.setRemarks(remark);
				adv.setSkippAmoount(0);
				adv.setSkippMonthYear("0000-00-00 00:00:00");
				adv.setSkippRemark("");
				adv.setDelStatus(1);
				adv.setYears(Integer.parseInt(sf.format(date)));

				LoanDetails res = Constants.getRestTemplate().postForObject(Constants.url + "/saveLoanDetail", adv,
						LoanDetails.class);

				if (res != null) {
					session.setAttribute("successMsg", "Loan Closed Successfully");

					map = new LinkedMultiValueMap<>();
					map.add("dateTimeUpdate", sf2.format(date2));
					map.add("userId", userObj.getEmpId());
					map.add("loanId", id);
					map.add("closeDate", DateConvertor.convertToYMD(closeDate));
					map.add("currentTotpaid", advList.getCurrentTotpaid() + Integer.parseInt(foreclose_amt));
					map.add("currentOut", advList.getCurrentOutstanding() - Integer.parseInt(foreclose_amt));
					map.add("repayAmt", advList.getLoanRepayAmt());
					Info info = Constants.getRestTemplate()
							.postForObject(Constants.url + "/updateLoanMainAfterForeclose", map, Info.class);

				} else {
					session.setAttribute("errorMsg", "Failed to Close Loan");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Close Loan");
		}

		return "redirect:/showLoanListForAction?empId=" + empId;
	}

	@RequestMapping(value = "/submitLoanPartialPay", method = RequestMethod.POST)
	public String submitLoanPartialPay(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
	//	System.out.println("inside");
		String empId = null;
		try {

			Date date2 = new Date();
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy");

			Date date1 = new Date();
			SimpleDateFormat sf1 = new SimpleDateFormat("MM");

			String remark = request.getParameter("remark");
			String closeDate = request.getParameter("joiningDate");
			//System.err.println("closeDate" + closeDate);
			String partialAmt = request.getParameter("partialAmt");
			int id = Integer.parseInt(request.getParameter("id"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map = new LinkedMultiValueMap<>();
			map.add("id", id);
			LoanMain advList = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanById", map,
					LoanMain.class);

			Boolean ret = false;
			empId = FormValidation.Encrypt(String.valueOf(advList.getEmpId()));
			if (FormValidation.Validaton(remark, "") == true) {

				ret = true;
				System.out.println("remark" + ret);
			}
			if (ret == false) {
				LoanDetails adv = new LoanDetails();
				adv.setAmountEmi(Integer.parseInt(partialAmt));
				adv.setLoanMainId(advList.getId());
				adv.setLoginName(String.valueOf(userObj.getEmpId()));
				adv.setLoginTime(sf2.format(date2));
				adv.setMonths(Integer.parseInt(sf1.format(date1)));
				if ((advList.getCurrentOutstanding() - Integer.parseInt(partialAmt)) == 0) {
					adv.setPayType("FC");
				} else {
					adv.setPayType("PP");
				}

				adv.setRemarks(remark);
				adv.setSkippAmoount(0);
				adv.setSkippMonthYear("0000-00-00 00:00:00");
				adv.setSkippRemark("");
				adv.setDelStatus(1);

				adv.setYears(Integer.parseInt(sf.format(date)));

				LoanDetails res = Constants.getRestTemplate().postForObject(Constants.url + "/saveLoanDetail", adv,
						LoanDetails.class);

				if (res != null) {
					session.setAttribute("successMsg", "Loan Paid Successfully");

					map = new LinkedMultiValueMap<>();
					map.add("dateTimeUpdate", sf2.format(date2));
					map.add("userId", userObj.getEmpId());
					map.add("loanId", id);
					map.add("closeDate", DateConvertor.convertToYMD(closeDate));
					map.add("currentTotpaid", advList.getCurrentTotpaid() + Integer.parseInt(partialAmt));
					map.add("currentOut", advList.getCurrentOutstanding() - Integer.parseInt(partialAmt));
					map.add("repayAmt", advList.getLoanRepayAmt());
					Info info = Constants.getRestTemplate()
							.postForObject(Constants.url + "/updateLoanMainAfterForeclose", map, Info.class);

				} else {
					session.setAttribute("errorMsg", "Failed to Pay Loan");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Pay Loan");
		}

		return "redirect:/showLoanListForAction?empId=" + empId;
	}

	@RequestMapping(value = "/submitSkipLoan", method = RequestMethod.POST)
	public String submitSkipLoan(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		String empId = null;
		try {

			Date date2 = new Date();
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String remark = request.getParameter("remark");
			int id = Integer.parseInt(request.getParameter("id"));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map = new LinkedMultiValueMap<>();
			map.add("id", id);
			LoanMain advList = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanById", map,
					LoanMain.class);

			Boolean ret = false;
			empId = FormValidation.Encrypt(String.valueOf(advList.getEmpId()));

			String repayDate = advList.getLoanRepayEnd();
			// System.err.println("one repayDate "+repayDate);
			LocalDate localDate = LocalDate.parse(DateConvertor.convertToYMD(repayDate));
			LocalDate oneMonthLater = localDate.plusMonths(1);
			// System.err.println("one month later"+oneMonthLater);
			if (FormValidation.Validaton(remark, "") == true) {

				ret = true;
				System.out.println("remark" + ret);
			}

			if (ret == false) {
				String skipStr = new String();
				if (advList.getSkipId() == 0) {
					skipStr = remark;
				} else {
					skipStr = advList.getSkipRemarks().concat(",").concat(remark);
				}
				int count = advList.getSkipId() + 1;

				map = new LinkedMultiValueMap<>();
				map.add("dateTimeUpdate", sf2.format(date2));
				map.add("userId", userObj.getEmpId());
				map.add("skipStr", skipStr);
				map.add("count", count);
				map.add("advId", id);
				map.add("repayEnd", String.valueOf(oneMonthLater));

				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateSkipLoan", map,
						Info.class);

				if (info != null) {
					session.setAttribute("successMsg", "Loan Skipped  Successfully");
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

		return "redirect:/showLoanListForAction?empId=" + empId;
	}

	// Loan PartialPay
	@RequestMapping(value = "/showPartialPayLoan", method = RequestMethod.GET)
	public ModelAndView showPartialPayLoan(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Loan/partialPayLoan");

		try {

			String base64encodedString = request.getParameter("id");
			String id = FormValidation.DecodeKey(base64encodedString);

			String base64encodedString1 = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			GetEmployeeDetails empPersInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
			// System.out.println("Edit EmpPersonal Info-------" + empPersInfo.toString());
			model.addObject("encEmpId", FormValidation.Encrypt(String.valueOf(empPersInfo.getEmpId())));

			String empPersInfoString = empPersInfo.getEmpCode().concat(" ").concat(empPersInfo.getFirstName())
					.concat(" ").concat(empPersInfo.getSurname()).concat("[")
					.concat(empPersInfo.getEmpDesgn().concat("]"));
			model.addObject("empPersInfo", empPersInfo);
			model.addObject("empPersInfoString", empPersInfoString);

			map = new LinkedMultiValueMap<>();
			map.add("id", id);
			LoanMain advList = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanById", map,
					LoanMain.class);

			model.addObject("advList", advList);

			map = new LinkedMultiValueMap<>();
			map.add("loanId", id);
			LoanDetails[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpLoanDetailByMainId", map, LoanDetails[].class);

			List<LoanDetails> laonDetalList = new ArrayList<LoanDetails>(Arrays.asList(employeeInfo));

			model.addObject("laonDetalList", laonDetalList);
			for (int i = 0; i < laonDetalList.size(); i++) {

				int monthNumber = laonDetalList.get(i).getMonths();
				String a = Month.of(monthNumber).name();
				laonDetalList.get(i).setLoginName(a);

			}
			// System.out.println("laonDetalList-------" + laonDetalList.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/calDateForPartialPay", method = RequestMethod.GET)
	public @ResponseBody Info calDateForPartialPay(HttpServletRequest request, HttpServletResponse response) {

		Info employeeInfo = new Info();

		try {

			String currentOutstanding = request.getParameter("currentOutstanding");
			String loanEmi = (request.getParameter("loanEmi"));
			String partialAmt = request.getParameter("partialAmt");
			String endDate = request.getParameter("endDate");
			String loanId = request.getParameter("loanId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("currentOutstanding", currentOutstanding);
			map.add("loanEmi", loanEmi);
			map.add("partialAmt", partialAmt);
			map.add("endDate", DateConvertor.convertToYMD(endDate));
			map.add("loanId", loanId);

			employeeInfo = Constants.getRestTemplate().postForObject(Constants.url + "/calDatePartialPay", map,
					Info.class);

			if (employeeInfo.isError() == false) {

				employeeInfo.setMsg(DateConvertor.convertToDMY(employeeInfo.getMsg()));
				System.err.println("employeeInfo***" + employeeInfo.toString());
			} else {
				employeeInfo.setMsg("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeInfo;
	}

	@RequestMapping(value = "/showRepayLoan", method = RequestMethod.GET)
	public ModelAndView showRepayLoan(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Loan/loanRepaySch");

		try {

			String base64encodedString = request.getParameter("id");
			String id = FormValidation.DecodeKey(base64encodedString);

			String base64encodedString1 = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			GetEmployeeDetails empPersInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
			// System.out.println("Edit EmpPersonal Info-------" + empPersInfo.toString());
			model.addObject("encEmpId", FormValidation.Encrypt(String.valueOf(empPersInfo.getEmpId())));

			String empPersInfoString = empPersInfo.getEmpCode().concat(" ").concat(empPersInfo.getFirstName())
					.concat(" ").concat(empPersInfo.getSurname()).concat("[")
					.concat(empPersInfo.getEmpDesgn().concat("]"));
			model.addObject("empPersInfo", empPersInfo);
			model.addObject("empPersInfoString", empPersInfoString);

			map = new LinkedMultiValueMap<>();
			map.add("id", id);
			LoanMain advList = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanById", map,
					LoanMain.class);

			model.addObject("advList", advList);

			map = new LinkedMultiValueMap<>();
			map.add("loanId", id);
			LoanDetails[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpLoanDetailByMainId", map, LoanDetails[].class);

			List<LoanDetails> laonDetalList = new ArrayList<LoanDetails>(Arrays.asList(employeeInfo));

			model.addObject("laonDetalList", laonDetalList);
			for (int i = 0; i < laonDetalList.size(); i++) {

				int monthNumber = laonDetalList.get(i).getMonths();
				String a = Month.of(monthNumber).name();
				laonDetalList.get(i).setLoginName(a);

			}

			Date date2 = new Date();
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			LocalDate localDate = LocalDate.parse(DateConvertor.convertToYMD(advList.getLoanRepayStart()));
			List<String> dateList = new ArrayList<>();
			for (int i = 0; i < advList.getLoanTenure(); i++) {

				LocalDate oneMonthLater = localDate.plusMonths(i);

				String x = String.valueOf(oneMonthLater.getMonth()).concat("-")
						.concat(String.valueOf(oneMonthLater.getYear()));
				dateList.add(x);

			}
			model.addObject("dateList", dateList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

}
