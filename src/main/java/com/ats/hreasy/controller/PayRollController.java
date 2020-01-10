package com.ats.hreasy.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.ExceUtil;
import com.ats.hreasy.common.ExportToExcel;
import com.ats.hreasy.common.ReportCostants;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Allowances;
import com.ats.hreasy.model.EmpSalInfoDaiyInfoTempInfo;
import com.ats.hreasy.model.EmpSalaryInfoForPayroll;
import com.ats.hreasy.model.GetSalDynamicTempRecord;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.InfoForUploadAttendance;
import com.ats.hreasy.model.PayRollDataForProcessing;

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
				PayRollDataForProcessing payRollDataForProcessing = Constants.getRestTemplate().postForObject(
						Constants.url + "/getEmployeeListWithEmpSalEnfoForPayRoll", map,
						PayRollDataForProcessing.class);
				List<EmpSalaryInfoForPayroll> list = payRollDataForProcessing.getList();

				model.addAttribute("empList", list);
				model.addAttribute("allownceList", payRollDataForProcessing.getAllowancelist());
				// System.out.println(payRollDataForProcessing.getList());
			} else {
				Allowances[] allowances = Constants.getRestTemplate().getForObject(Constants.url + "/getAllAllowances",
						Allowances[].class);
				List<Allowances> allowancelist = new ArrayList<>(Arrays.asList(allowances));
				model.addAttribute("allownceList", allowancelist);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/viewDynamicValue", method = RequestMethod.POST)
	public String viewDynamicValue(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "payroll/viewDynamicValue";

		try {

			String date = request.getParameter("searchDate");

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
			//System.out.println(map);
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

		String mav = "payroll/generatePayRoll";

		try {

			String date = request.getParameter("searchDate");
			String empIds = request.getParameter("empIds");
			String[] monthyear = date.split("-");
			request.setAttribute("month", monthyear[0]);
			request.setAttribute("year", monthyear[1]);
			request.setAttribute("empIds", empIds);

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
			model.addAttribute("empIds", empIds);
			model.addAttribute("date", date);
			// model.addAttribute("empList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/exelForPayroll", method = RequestMethod.GET)
	public void exelForClientWiseClaim(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Payroll List ";

		try {

			HttpSession session = request.getSession();
			EmpSalInfoDaiyInfoTempInfo[] getSalDynamicTempRecord = (EmpSalInfoDaiyInfoTempInfo[]) session
					.getAttribute("payrollexelList");
			int amount_round = (int) session.getAttribute("amount_round");
			List<EmpSalInfoDaiyInfoTempInfo> list = new ArrayList<>(Arrays.asList(getSalDynamicTempRecord));

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("EMP Code");
			rowData.add("EMP Namel");
			rowData.add("Basic");
			rowData.add("OT AMT");
			rowData.add("Fund");
			rowData.add("Gross Earning");
			rowData.add("Claim ADD");
			rowData.add("Adv");
			rowData.add("Loan");
			rowData.add("IT Ded");
			rowData.add("Pay Ded");
			rowData.add("PT");
			rowData.add("PF");
			rowData.add("ESIC");
			rowData.add("MLWF");
			rowData.add("Gross Ded");
			rowData.add("Performance Bonus");
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

				rowData.add(
						"" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getOtWages(), amount_round)));
				rowData.add("" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getFund(), amount_round)));
				rowData.add("" + String.format("%.2f",
						ReportCostants.castNumber(list.get(i).getGrossSalaryDytemp(), amount_round)));
				rowData.add(""
						+ String.format("%.2f", ReportCostants.castNumber(list.get(i).getMiscExpAdd(), amount_round)));
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
				rowData.add(
						"" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getEpfWages(), amount_round)));
				rowData.add("" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getEsic(), amount_round)));
				rowData.add("" + String.format("%.2f", ReportCostants.castNumber(list.get(i).getMlwf(), amount_round)));
				double finalDed = list.get(i).getAdvanceDed() + list.get(i).getLoanDed() + list.get(i).getItded()
						+ list.get(i).getPayDed() + list.get(i).getPtDed() + list.get(i).getEpfWages()
						+ list.get(i).getEsic() + list.get(i).getMlwf();
				rowData.add("" + String.format("%.2f", ReportCostants.castNumber(finalDed, amount_round)));
				rowData.add("" + String.format("%.2f",
						ReportCostants.castNumber(list.get(i).getPerformanceBonus(), amount_round)));
				rowData.add(""
						+ String.format("%.2f", ReportCostants.castNumber(list.get(i).getNetSalary(), amount_round)));
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

				cnt = cnt + 1;
			}

			XSSFWorkbook wb = null;
			try {
				//System.out.println("exportToExcelList" + exportToExcelList.toString());

				wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " ", "", 'S');

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

}
