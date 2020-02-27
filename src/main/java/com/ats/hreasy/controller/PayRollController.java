package com.ats.hreasy.controller;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.ExceUtil;
import com.ats.hreasy.common.ExportToExcel;
import com.ats.hreasy.common.ReportCostants;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Allowances;
import com.ats.hreasy.model.EmpSalInfoDaiyInfoTempInfo;
import com.ats.hreasy.model.EmpSalaryInfoForPayroll;
import com.ats.hreasy.model.GetPayrollGeneratedList;
import com.ats.hreasy.model.GetSalDynamicTempRecord;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.InfoForUploadAttendance;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.MstCompany;
import com.ats.hreasy.model.MstCompanySub;
import com.ats.hreasy.model.PayRollDataForProcessing;

@Controller
@Scope("session")
public class PayRollController {

	@RequestMapping(value = "/selectMonthForPayRoll", method = RequestMethod.GET)
	public String selectMonthForPayRoll(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String mav = "redirect:/accessDenied";

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("selectMonthForPayRoll", "selectMonthForPayRoll", 1, 0, 0, 0,
					newModuleList);
			if (view.isError() == false) {

				mav = "payroll/selectMonthShoEmpInfo";
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
					Allowances[] allowances = Constants.getRestTemplate()
							.getForObject(Constants.url + "/getAllAllowances", Allowances[].class);
					List<Allowances> allowancelist = new ArrayList<>(Arrays.asList(allowances));
					model.addAttribute("allownceList", allowancelist);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/viewDynamicValue", method = RequestMethod.POST)
	public String viewDynamicValue(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "redirect:/accessDenied";

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("selectMonthForPayRoll", "selectMonthForPayRoll", 1, 0, 0, 0,
					newModuleList);
			if (view.isError() == false) {

				String date = request.getParameter("searchDate");
				mav = "payroll/viewDynamicValue";
				String[] monthyear = date.split("-");
				model.addAttribute("date", date);

				String[] selectEmp = request.getParameterValues("selectEmp");
				String empIds = "0";

				for (int i = 0; i < selectEmp.length; i++) {
					empIds = empIds + "," + selectEmp[i];
				}
				empIds = empIds.substring(2, empIds.length());

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("month", monthyear[0]);
				map.add("year", monthyear[1]);
				map.add("empIds", empIds);
				map.add("userId", userObj.getUserId());
				// System.out.println(map);
				Info insertTemp = Constants.getRestTemplate().postForObject(Constants.url + "/insertPayrollIntempTable",
						map, Info.class);
				if (insertTemp.isError() == false) {
					GetSalDynamicTempRecord[] getSalDynamicTempRecord = Constants.getRestTemplate().postForObject(
							Constants.url + "/getSalDynamicTempRecord", map, GetSalDynamicTempRecord[].class);
					List<GetSalDynamicTempRecord> list = new ArrayList<>(Arrays.asList(getSalDynamicTempRecord));
					model.addAttribute("empList", list);
				}
				model.addAttribute("empIds", empIds);
				model.addAttribute("date", date);
			}

			// model.addAttribute("empList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/editBonus", method = RequestMethod.POST)
	public @ResponseBody GetSalDynamicTempRecord editBonus(HttpServletRequest request, HttpServletResponse response,
			Model model) {

		GetSalDynamicTempRecord getSalDynamicTempRecordById = new GetSalDynamicTempRecord();

		try {

			int tempSalDaynamicId = Integer.parseInt(request.getParameter("tempSalDaynamicId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("tempSalDaynamicId", tempSalDaynamicId);
			getSalDynamicTempRecordById = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getSalDynamicTempRecordById", map, GetSalDynamicTempRecord.class);

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
			float itAmt = Float.parseFloat(request.getParameter("itAmt"));
			float perBonus = Float.parseFloat(request.getParameter("perBonus"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("tempSalDaynamicId", tempSalDaynamicId);
			map.add("itAmt", itAmt);
			map.add("perBonus", perBonus);
			info = Constants.getRestTemplate().postForObject(Constants.url + "/updateBonusAmt", map, Info.class);

		} catch (Exception e) {
			e.printStackTrace();
			info = new Info();
			info.setError(true);
			info.setMsg("failed");
		}
		return info;
	}

	@RequestMapping(value = "/generatePayRoll", method = RequestMethod.POST)
	public String generatePayRoll(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = "redirect:/accessDenied";

		try {

			HttpSession session = request.getSession();

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("selectMonthForPayRoll", "selectMonthForPayRoll", 1, 0, 0, 0,
					newModuleList);
			if (view.isError() == false) {

				mav = "payroll/generatePayRoll";
				String date = request.getParameter("searchDate");
				String empIds = request.getParameter("empIds");
				String[] monthyear = date.split("-");
				request.setAttribute("month", monthyear[0]);
				request.setAttribute("year", monthyear[1]);
				request.setAttribute("empIds", empIds);
				model.addAttribute("empIds", empIds);
				model.addAttribute("date", date);
			}

			/*
			 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
			 * Object>(); map.add("month", monthyear[0]); map.add("year", monthyear[1]);
			 * map.add("empIds", empIds);
			 * 
			 * EmpSalInfoDaiyInfoTempInfo[] getSalDynamicTempRecord =
			 * Constants.getRestTemplate() .postForObject(Constants.url +
			 * "/calculateSalary", map, EmpSalInfoDaiyInfoTempInfo[].class);
			 * List<EmpSalInfoDaiyInfoTempInfo> list = new
			 * ArrayList<>(Arrays.asList(getSalDynamicTempRecord));
			 * model.addAttribute("empList", list);
			 */

			// model.addAttribute("empList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/insertFinalPayRollAndDeleteFroTemp", method = RequestMethod.POST)
	public String insertFinalPayRollAndDeleteFroTemp(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String mav = "redirect:/accessDenied";

		HttpSession session = request.getSession();
		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("selectMonthForPayRoll", "selectMonthForPayRoll", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == false) {

				mav = "redirect:/selectMonthForPayRoll";
				EmpSalInfoDaiyInfoTempInfo[] getSalDynamicTempRecord = (EmpSalInfoDaiyInfoTempInfo[]) session
						.getAttribute("payrollexelList");
				List<EmpSalInfoDaiyInfoTempInfo> list = new ArrayList<>(Arrays.asList(getSalDynamicTempRecord));
				Info info = Constants.getRestTemplate()
						.postForObject(Constants.url + "/insertFinalPayRollAndDeleteFroTemp", list, Info.class);

				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
				String empIds = request.getParameter("empIds");
				String searchDate = request.getParameter("searchDate");
				String[] dt = searchDate.split("-");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("userId", userObj.getUserId());
				map.add("empIds", empIds);
				map.add("month", Integer.parseInt(dt[0]));
				map.add("year", Integer.parseInt(dt[1]));
				info = Constants.getRestTemplate().postForObject(Constants.url + "/updateIsPaidInPaydeClaimAdvLoan",
						map, Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", "Payroll Generated Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Generated Payroll");
				}
			}

		} catch (Exception e) {
			session.setAttribute("errorMsg", "Failed to Generated Payroll");
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/exelForPayroll", method = RequestMethod.GET)
	public void exelForClientWiseClaim(HttpServletRequest request, HttpServletResponse response) {

		try {

			HttpSession session = request.getSession();
			EmpSalInfoDaiyInfoTempInfo[] getSalDynamicTempRecord = (EmpSalInfoDaiyInfoTempInfo[]) session
					.getAttribute("payrollexelList");
			int amount_round = (int) session.getAttribute("amount_round");
			String monthAndYear = (String) session.getAttribute("monthAndYear");
			List<EmpSalInfoDaiyInfoTempInfo> list = new ArrayList<>(Arrays.asList(getSalDynamicTempRecord));
			String reportName = "Payroll List for " + monthAndYear;

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("EMP Code");
			rowData.add("EMP Name");
			rowData.add("Basic");
			rowData.add("Allowance");
			rowData.add("Absent Deduction");
			rowData.add("Gross Earning");
			rowData.add("Adv");
			rowData.add("Loan");
			rowData.add("IT Ded");
			rowData.add("Pay Ded");
			rowData.add("PT");
			rowData.add("PF");
			rowData.add("ESIC");
			rowData.add("MLWF");
			rowData.add("Society Contribution");
			rowData.add("Gross Ded");
			rowData.add("Claim Add");
			rowData.add("Performance Bonus");
			rowData.add("Production Incentive");
			rowData.add("Performance Incentive");
			rowData.add("Reward");
			rowData.add("Net Salary");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			int cnt = 1;
			float empTotal = 0;

			for (int i = 0; i < list.size(); i++) {

				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add("" + cnt);
				rowData.add("" + list.get(i).getEmpCode());
				rowData.add("" + list.get(i).getEmpName());

				rowData.add(
						"" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getBasicCal(), amount_round)));
				double totalAllow = 0;
				for (int j = 0; j < list.get(i).getGetAllowanceTempList().size(); j++) {
					totalAllow = totalAllow + list.get(i).getGetAllowanceTempList().get(j).getAllowanceValueCal();
				}
				rowData.add("" + String.format("%.2f", ReportCostants.castNumber(totalAllow, amount_round)));
				rowData.add(""
						+ String.format("%.2f", ReportCostants.castNumber(list.get(i).getAbDeduction(), amount_round)));
				rowData.add("" + String.format("%.2f",
						ReportCostants.castNumber(list.get(i).getGrossSalaryDytemp(), amount_round)));
				rowData.add(""
						+ String.format("%.2f", ReportCostants.castNumber(list.get(i).getAdvanceDed(), amount_round)));
				rowData.add(
						"" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getLoanDed(), amount_round)));
				rowData.add(
						"" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getItded(), amount_round)));
				rowData.add(
						"" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getPayDed(), amount_round)));
				rowData.add(
						"" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getPtDed(), amount_round)));
				rowData.add(""
						+ String.format("%.2f", ReportCostants.castNumber(list.get(i).getEmployeePf(), amount_round)));
				rowData.add("" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getEsic(), amount_round)));
				rowData.add("" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getMlwf(), amount_round)));
				rowData.add("" + String.format("%.2f",
						ReportCostants.castNumber(list.get(i).getSocietyContribution(), amount_round)));

				double finalDed = list.get(i).getAdvanceDed() + list.get(i).getLoanDed() + list.get(i).getItded()
						+ list.get(i).getPayDed() + list.get(i).getPtDed() + list.get(i).getEmployeePf()
						+ list.get(i).getEsic() + list.get(i).getMlwf() + list.get(i).getSocietyContribution();
				rowData.add("" + String.format("%.2f", ReportCostants.castNumber(finalDed, amount_round)));
				rowData.add(""
						+ String.format("%.2f", ReportCostants.castNumber(list.get(i).getMiscExpAdd(), amount_round)));
				rowData.add("" + String.format("%.2f",
						ReportCostants.castNumber(list.get(i).getPerformanceBonus(), amount_round)));
				rowData.add("" + String.format("%.2f",
						ReportCostants.castNumber(list.get(i).getProductionInsentive(), amount_round)));
				rowData.add(
						"" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getOtWages(), amount_round)));
				rowData.add(
						"" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getReward(), amount_round)));
				rowData.add(""
						+ String.format("%.2f", ReportCostants.castNumber(list.get(i).getNetSalary(), amount_round)));
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

				cnt = cnt + 1;
			}

			XSSFWorkbook wb = null;
			try {
				// System.out.println("exportToExcelList" + exportToExcelList.toString());

				wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " ", "", 'W');

				ExceUtil.autoSizeColumns(wb, 3);
				response.setContentType("application/vnd.ms-excel");
				String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				response.setHeader("Content-disposition", "attachment; filename=" + reportName + "-" + date + ".xlsx");
				wb.write(response.getOutputStream());

			} catch (IOException ioe) {
				throw new RuntimeException("Error writing spreadsheet to output stream");
			} finally {
				if (wb != null) {
					wb.close();
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/listOfGeneratedPayroll", method = RequestMethod.GET)
	public String listOfGeneratedPayroll(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String mav = "redirect:/accessDenied";

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("selectMonthForPayRoll", "selectMonthForPayRoll", 1, 0, 0, 0,
					newModuleList);
			if (view.isError() == false) {

				mav = "payroll/listOfGeneratedPayroll";
				String date = request.getParameter("selectMonth");

				MstCompanySub[] companyList = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllSubCompanies", MstCompanySub[].class);
				model.addAttribute("companyList", companyList);

				if (date != null) {
					String[] monthyear = date.split("-");
					model.addAttribute("date", date);

					int companyId = Integer.parseInt(request.getParameter("subCmpId"));
					request.setAttribute("companyId", companyId);
					request.setAttribute("month", monthyear[0]);
					request.setAttribute("year", monthyear[1]);
					model.addAttribute("companyId", companyId);
					/*
					 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
					 * Object>(); map.add("month", monthyear[0]); map.add("year", monthyear[1]);
					 * PayRollDataForProcessing payRollDataForProcessing =
					 * Constants.getRestTemplate().postForObject( Constants.url +
					 * "/getPayrollGenratedList", map, PayRollDataForProcessing.class);
					 * List<GetPayrollGeneratedList> list =
					 * payRollDataForProcessing.getPayrollGeneratedList();
					 * 
					 * model.addAttribute("empList", list); model.addAttribute("allownceList",
					 * payRollDataForProcessing.getAllowancelist());
					 */
					// System.out.println(payRollDataForProcessing.getList());
				} else {

					request.removeAttribute("month");
					request.removeAttribute("year");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/excelForGeneratedPayroll/{vals}", method = RequestMethod.GET)
	public void excelForGeneratedPayroll(@PathVariable("vals") String vals, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			String[] ids = vals.split(",");
			HttpSession session = request.getSession();
			PayRollDataForProcessing payRollDataForProcessing = (PayRollDataForProcessing) session
					.getAttribute("payRollDataForProcessing");
			int amount_round = (int) session.getAttribute("amount_round");
			String monthAndYear = (String) session.getAttribute("monthAndYear");
			List<GetPayrollGeneratedList> list = payRollDataForProcessing.getPayrollGeneratedList();
			String reportName = "Generated Payroll List for " + monthAndYear;

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("EMP Code");
			rowData.add("EMP Name");
			rowData.add("EMP Type");
			rowData.add("Department");
			rowData.add("Designation");
			rowData.add("Salary Basis");
			rowData.add("PT");
			rowData.add("PF");
			rowData.add("ESIC");
			rowData.add("MLWF");
			rowData.add("Basic");
			for (int i = 0; i < payRollDataForProcessing.getAllowancelist().size(); i++) {
				rowData.add("" + payRollDataForProcessing.getAllowancelist().get(i).getShortName());

			}
			rowData.add("Absent Deduction");
			rowData.add("Gross Earning");
			rowData.add("Adv");
			rowData.add("Loan");
			rowData.add("IT Ded");
			rowData.add("Pay Ded");
			rowData.add("PT");
			rowData.add("PF");
			rowData.add("ESIC");
			rowData.add("MLWF");
			rowData.add("Society Contribution");
			rowData.add("Gross Ded");
			rowData.add("Claim ADD");
			rowData.add("Performance Bonus");
			rowData.add("Production Incentive");
			rowData.add("Performance Incentive");
			rowData.add("Reward");
			rowData.add("Net Salary");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			int cnt = 1;
			float empTotal = 0;

			for (int i = 0; i < list.size(); i++) {
				for (int m = 0; m < ids.length; m++) {

					if (Integer.parseInt(ids[m]) == list.get(i).getEmpId()) {

						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						rowData.add("" + cnt);
						rowData.add("" + list.get(i).getEmpCode());
						rowData.add("" + list.get(i).getName());
						rowData.add("" + list.get(i).getEmpTypeName());
						rowData.add("" + list.get(i).getDepartName());
						rowData.add("" + list.get(i).getDesignName());
						rowData.add("" + list.get(i).getEmpCategory());
						rowData.add("" + list.get(i).getPtApplicable());
						if (list.get(i).getPfStatus() == 1) {
							rowData.add("Yes");
						} else {
							rowData.add("No");
						}

						if (list.get(i).getEsicStatus() == 1) {
							rowData.add("Yes");
						} else {
							rowData.add("No");
						}

						rowData.add(list.get(i).getMlwfApplicable());

						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getBasicCal(), amount_round)));

						for (int k = 0; k < payRollDataForProcessing.getAllowancelist().size(); k++) {
							int find = 0;
							for (int j = 0; j < list.get(i).getPayrollAllownceList().size(); j++) {
								if (list.get(i).getPayrollAllownceList().get(j)
										.getAllowanceId() == payRollDataForProcessing.getAllowancelist().get(k)
												.getAllowanceId()) {
									rowData.add(String.format("%.2f",
											ReportCostants.castNumber(
													list.get(i).getPayrollAllownceList().get(j).getAllowanceValueCal(),
													amount_round)));
									find = 1;
									break;

								}
							}
							if (find == 0) {
								rowData.add(String.format("%.2f", ReportCostants.castNumber(0, amount_round)));
							}

						}
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getAbDeduction(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getGrossSalary(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getAdvanceDed(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getLoanDed(), amount_round)));

						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getItded(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getPayDed(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getPtDed(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getEmployeePf(), amount_round)));

						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getEsic(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getMlwf(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getSocietyContribution(), amount_round)));
						double finalDed = list.get(i).getAdvanceDed() + list.get(i).getLoanDed()
								+ list.get(i).getItded() + list.get(i).getPayDed() + list.get(i).getPtDed()
								+ list.get(i).getEmployeePf() + list.get(i).getEsic() + list.get(i).getMlwf()
								+ list.get(i).getSocietyContribution();

						rowData.add("" + String.format("%.2f", ReportCostants.castNumber(finalDed, amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getMiscExpAdd(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getPerformanceBonus(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getProductionInsentive(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getOtWages(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getReward(), amount_round)));
						rowData.add("" + String.format("%.2f",
								ReportCostants.castNumber(list.get(i).getNetSalary(), amount_round)));

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

						cnt = cnt + 1;
					}
				}
			}

			XSSFWorkbook wb = null;
			try {
				// System.out.println("exportToExcelList" + exportToExcelList.toString());

				wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " ", "", 'Z');

				ExceUtil.autoSizeColumns(wb, 3);
				response.setContentType("application/vnd.ms-excel");
				String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				response.setHeader("Content-disposition", "attachment; filename=" + reportName + "-" + date + ".xlsx");
				wb.write(response.getOutputStream());

			} catch (IOException ioe) {
				throw new RuntimeException("Error writing spreadsheet to output stream");
			} finally {
				if (wb != null) {
					wb.close();
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/pdf/generatedPayrollPdf/{id}/{selectMonth}", method = RequestMethod.GET)
	public ModelAndView poPdf(@PathVariable int[] id, @PathVariable String selectMonth, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("payroll/generatedPayrollPdf");
		try {

			String[] monthyear = selectMonth.split("-");

			String empIds = "0";

			for (int i = 0; i < id.length; i++) {
				empIds = empIds + "," + id[i];
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("month", monthyear[0]);
			map.add("year", monthyear[1]);
			map.add("empIds", empIds);
			PayRollDataForProcessing payRollDataForProcessing = Constants.getRestTemplate().postForObject(
					Constants.url + "/getPayrollGenratedListByEmpIds", map, PayRollDataForProcessing.class);
			List<GetPayrollGeneratedList> list = payRollDataForProcessing.getPayrollGeneratedList();
			model.addObject("list", list);

			/*map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			MstCompany companyInfo = Constants.getRestTemplate().postForObject(Constants.url + "/getCompanyById", map,
					MstCompany.class);
			model.addObject("companyInfo", companyInfo);*/
			
			MstCompanySub[] companyList = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAllSubCompanies", MstCompanySub[].class);
			model.addObject("companyList", companyList);

			model.addObject("logoUrl", Constants.imageShowUrl);
			
			String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August",
					"September", "October", "November", "December" };
			String monthName = monthNames[Integer.parseInt(monthyear[0]) - 1];
			model.addObject("monthName", monthName);
			model.addObject("year", monthyear[1]);
			// System.out.println(list);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	private Dimension format = PD4Constants.A4;
	private boolean landscapeValue = false;
	private int topValue = 8;
	private int leftValue = 0;
	private int rightValue = 0;
	private int bottomValue = 8;
	private String unitsValue = "m";
	private String proxyHost = "";
	private int proxyPort = 0;

	private int userSpaceWidth = 850;
	private static int BUFFER_SIZE = 1024;

	@RequestMapping(value = "/pdfForReport", method = RequestMethod.GET)
	public void showPDF(HttpServletRequest request, HttpServletResponse response) {

		String url = request.getParameter("url");
		File f = new File(Constants.REPORT_SAVE);
		try {
			runConverter(Constants.ReportURL + url, f, request, response);
			// runConverter("www.google.com", f,request,response);

		} catch (IOException e) {

			System.out.println("Pdf conversion exception " + e.getMessage());
		}

		ServletContext context = request.getSession().getServletContext();
		String appPath = context.getRealPath("");

		String filePath = Constants.REPORT_SAVE;

		String fullPath = appPath + filePath;
		File downloadFile = new File(filePath);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(downloadFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			// get MIME type of the file
			String mimeType = context.getMimeType(fullPath);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/pdf";
			}
			// System.out.println("MIME type: " + mimeType);

			String headerKey = "Content-Disposition";

			// response.addHeader("Content-Disposition", "attachment;filename=report.pdf");
			response.setContentType("application/pdf");

			OutputStream outStream;

			outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void runConverter(String urlstring, File output, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (urlstring.length() > 0) {
			if (!urlstring.startsWith("http://") && !urlstring.startsWith("file:")) {
				urlstring = "http://" + urlstring;
			}
			// System.out.println("PDF URL " + urlstring);
			java.io.FileOutputStream fos = new java.io.FileOutputStream(output);

			PD4ML pd4ml = new PD4ML();

			try {

				PD4PageMark footer = new PD4PageMark();
				pd4ml.enableSmartTableBreaks(true);
				footer.setPageNumberTemplate("page $[page] of $[total]");
				footer.setTitleAlignment(PD4PageMark.LEFT_ALIGN);
				footer.setPageNumberAlignment(PD4PageMark.RIGHT_ALIGN);
				footer.setInitialPageNumber(1);
				footer.setFontSize(8);
				footer.setAreaHeight(15);

				pd4ml.setPageFooter(footer);

			} catch (Exception e) {
				System.out.println("Pdf conversion method excep " + e.getMessage());
			}
			try {
				pd4ml.setPageSize(landscapeValue ? pd4ml.changePageOrientation(format) : format);
				// pd4ml.setPageSize(new java.awt.Dimension(15*72, 11*72));
			} catch (Exception e) {
				System.out.println("Pdf conversion ethod excep " + e.getMessage());
			}

			if (unitsValue.equals("mm")) {
				pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));
			} else {
				pd4ml.setPageInsets(new Insets(topValue, leftValue, bottomValue, rightValue));
			}

			pd4ml.setHtmlWidth(userSpaceWidth);

			pd4ml.render(urlstring, fos);

		}
	}

}
