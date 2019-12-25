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
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.Advance.Advance;
import com.ats.hreasy.model.Advance.GetAdvance;
import com.ats.hreasy.model.Loan.GetLoan;
import com.ats.hreasy.model.Loan.LoanCalculation;
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
					.concat(" ").concat(empPersInfo.getSurname()).concat(" [").concat(empPersInfo.getEmpDesgn())
					.concat("]");
			model.addObject("empPersInfo", empPersInfo);
			model.addObject("empPersInfoString", empPersInfoString);
			model.addObject("todaysDate", sf.format(date));

			map = new LinkedMultiValueMap<>();
			map.add("empId", empTypeId);
			LoanMain empPersInfo1 = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpLoanHistory", map,
					LoanMain.class);

			model.addObject("prevLoan", empPersInfo1);
			System.out.println("  LoanMain Info-------" + empPersInfo1.toString());
			LoanMain appNo = Constants.getRestTemplate().getForObject(Constants.url + "/getLastApplicationNumber",
					LoanMain.class);

			model.addObject("appNo", Integer.parseInt(appNo.getLoanApplNo()) + 1);

		} catch (Exception e) {
			e.printStackTrace();
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

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("roi", roi);
			map.add("tenure", tenure);
			map.add("loanAmt", loanAmt);
			loan = Constants.getRestTemplate().postForObject(Constants.url + "/calLoan", map, LoanCalculation.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return loan;
	}

	@RequestMapping(value = "/submitInsertLoan", method = RequestMethod.POST)
	public String submitInsertLoan(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

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

			String leaveDateRange = request.getParameter("cuttingDate");

			String[] arrOfStr = leaveDateRange.split("to", 2);
			System.err.println("date1 is " + arrOfStr[0].toString().trim());
			System.err.println("date2 is " + arrOfStr[1].toString().trim());

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

			if (ret == false) {

				LoanMain adv = new LoanMain();
				int temp = Integer.parseInt(loanAmt) / Integer.parseInt(tenure);

				adv.setAllData("");
				adv.setCurrentOutstanding(Integer.parseInt(repayAmt));
				adv.setCurrentTotpaid(0);
				adv.setLoanAddDate(sf2.format(date2));
				adv.setLoanAmt(Integer.parseInt(loanAmt));
				adv.setLoanEmi(temp);
				adv.setLoanEmiIntrest(Integer.parseInt(emi));
				adv.setLoanRepayAmt(Integer.parseInt(repayAmt));
				adv.setLoanRepayEnd(arrOfStr[1].toString());
				adv.setLoanRepayStart(arrOfStr[0].toString());
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
				adv.setLoanApplNo(appNo);

				adv.setLoginName(String.valueOf(userObj.getEmpId()));
				adv.setLoginTime(sf2.format(date2));
				adv.setSkipId(0);
				adv.setSkipLoginName("0");
				adv.setSkipLoginTime("0000-00-00 00:00:00");
				adv.setSkipRemarks("");
				adv.setDelStatus(1);

				LoanMain res = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmpLoan", adv,
						LoanMain.class);

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

		return "redirect:/showEmpListToAddLoan";
	}

	@RequestMapping(value = "/showLoanHistory", method = RequestMethod.GET)
	public ModelAndView showAdvanceHistory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Loan/loanHistory");

		try {

			GetEmployeeDetails[] empdetList2 = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList3 = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList2));
			model.addObject("empdetList", empdetList3);
			// System.out.println(" Info-------" + empdetList3.toString());

		} catch (Exception e) {
			e.printStackTrace();
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
			System.out.println("employeeInfoList" + employeeInfoList.toString());

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
		GetLoan loan=new GetLoan();
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
			map.add("empId",empId);

			LoanMain[] employeeInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanHistoryEmpWiseDetail",
					map, LoanMain[].class);

			employeeInfoList = new ArrayList<LoanMain>(Arrays.asList(employeeInfo));
			System.out.println("employeeInfoList" + employeeInfoList.toString());
			model.addObject("loanList", employeeInfoList);
			
			map = new LinkedMultiValueMap<>();
			map.add("status", status);
			map.add("calYrId", calYrId);
			map.add("companyId", 1);
			map.add("empId",empId);
			loan = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanHistoryEmpWiseSpec", map, GetLoan.class);
			model.addObject("empDeatil", loan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	///Loan Actionss
	@RequestMapping(value = "/showCompLoanList", method = RequestMethod.GET)
	public ModelAndView showCompLoanList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Loan/companyLoanList");
		List<GetLoan> employeeInfoList = new ArrayList<GetLoan>();
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
  			map.add("companyId", 1);
 
			GetLoan[] employeeInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanHistoryEmpWiseForCompany",
					map,GetLoan[].class);

			employeeInfoList = new ArrayList<GetLoan>(Arrays.asList(employeeInfo));
			System.out.println("employeeInfoList" + employeeInfoList.toString());
		 
			model.addObject("loanList", employeeInfoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	 


	@RequestMapping(value = "/showLoanListForAction", method = RequestMethod.GET)
	public ModelAndView showLoanListForAction(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Loan/loanListForAction");
		List<LoanMain> employeeInfoList = new ArrayList<LoanMain>();
		GetLoan loan=new GetLoan();
  		try {

  			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);
		 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("status", "Active");
 			map.add("companyId", 1);
 
			LoanMain[] employeeInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanHistoryEmpWiseDetail",
					map, LoanMain[].class);

			employeeInfoList = new ArrayList<LoanMain>(Arrays.asList(employeeInfo));
			System.out.println("employeeInfoList" + employeeInfoList.toString());
			model.addObject("loanList", employeeInfoList);
			
			map = new LinkedMultiValueMap<>();
 			map.add("companyId", 1);
			map.add("empId",empId);
			loan = Constants.getRestTemplate().postForObject(Constants.url + "/getLoanHistoryEmpWiseSpec", map, GetLoan.class);
			model.addObject("empDeatil", loan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
