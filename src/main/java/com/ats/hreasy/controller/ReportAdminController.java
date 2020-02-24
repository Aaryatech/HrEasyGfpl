package com.ats.hreasy.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.ExceUtil;
import com.ats.hreasy.common.ExportToExcel;
import com.ats.hreasy.common.ItextPageEvent;
import com.ats.hreasy.common.ReportCostants;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.CalenderYear;
import com.ats.hreasy.model.DailyAttendance;
import com.ats.hreasy.model.EmpLeaveHistoryRep;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetDailyDailyRecord;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveApply;
import com.ats.hreasy.model.LeaveHistTemp;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.MstCompanySub;
import com.ats.hreasy.model.PayDeductionDetails;
import com.ats.hreasy.model.Advance.AdvanceDetails;
import com.ats.hreasy.model.Advance.GetAdvance;
import com.ats.hreasy.model.Bonus.BonusCalc;
import com.ats.hreasy.model.Bonus.BonusMaster;
import com.ats.hreasy.model.report.EmpAttendeanceRep;
import com.ats.hreasy.model.report.EsiSumaryRep;
import com.ats.hreasy.model.report.GetLoanReport;
import com.ats.hreasy.model.report.GetPtChallan;
import com.ats.hreasy.model.report.GetSalaryCalcReport;
import com.ats.hreasy.model.report.GetYearlyAdvance;
import com.ats.hreasy.model.report.GetYearlyAdvanceNew;
import com.ats.hreasy.model.report.GetYearlyLoan;
import com.ats.hreasy.model.report.LoanDedReport;
import com.ats.hreasy.model.report.StatutoryEsicRep;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@Scope("session")
public class ReportAdminController {

	@RequestMapping(value = "/showReportsDashbord", method = RequestMethod.GET)
	public ModelAndView showQueryBasedReports(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("Report/repDash");
			HttpSession session = request.getSession();

			MstCompanySub[] company = Constants.getRestTemplate().getForObject(Constants.url + "/getAllSubCompanies",
					MstCompanySub[].class);

			List<MstCompanySub> companyList = new ArrayList<MstCompanySub>(Arrays.asList(company));
			model.addObject("companySubList", companyList);

			BonusMaster[] location = Constants.getRestTemplate().getForObject(Constants.url + "/getAllBonusList",
					BonusMaster[].class);
			List<BonusMaster> bonusList = new ArrayList<BonusMaster>(Arrays.asList(location));

			CalenderYear[] calenderYear = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getCalculateYearList", CalenderYear[].class);
			List<CalenderYear> calYearList = new ArrayList<CalenderYear>(Arrays.asList(calenderYear));

			model.addObject("calYearList", calYearList);

			model.addObject("bonusList", bonusList);

		} catch (Exception e) {

			System.err.println("Exce in showReports " + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/showAdvancePaymentRep", method = RequestMethod.GET)
	public void showStudentParticipatedNssNccReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee Advance Payment";

		HttpSession session = request.getSession();

		String month = request.getParameter("date");

		String temp[] = month.split("-");
		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("month", temp[0]);
			map.add("year", temp[1]);
			GetAdvance[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getAdvanceReport", map,
					GetAdvance[].class);
			List<GetAdvance> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize.A4);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 5.5f, 3.0f, 3.0f, 3.0f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Advance Date", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Advance Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Is Ded", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				GetAdvance prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase("" + prog.getFirstName().concat(" ").concat(prog.getSurname()), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getAdvDate(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getAdvAmount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getExVar1(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Month-Year: " + month));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Adv Date");
				rowData.add("Adv Amount");
				rowData.add("Is Ded");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getFirstName().concat(" ").concat(progList.get(i).getSurname()));
					rowData.add("" + progList.get(i).getAdvDate());
					rowData.add("" + progList.get(i).getAdvAmount());
					rowData.add("" + progList.get(i).getExVar1());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Month-Year:" + month, "", 'F');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showAdvanceSkipRep", method = RequestMethod.GET)
	public void showAdvanceSkipRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee Advance Skip Report";

		HttpSession session = request.getSession();

		String month = request.getParameter("date");

		String temp[] = month.split("-");
		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("month", temp[0]);
			map.add("year", temp[1]);
			GetAdvance[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getAdvanceReport", map,
					GetAdvance[].class);
			List<GetAdvance> progList = new ArrayList<>(Arrays.asList(resArray));

			AdvanceDetails[] resArray1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAllAdvanceDetails", map, AdvanceDetails[].class);
			List<AdvanceDetails> progList1 = new ArrayList<>(Arrays.asList(resArray1));

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(9);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 5.5f, 3.0f, 3.0f, 2.0f, 3.0f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Advance Date", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Advance Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Skip Count", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Skip Remarks", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Skipped By", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Skip DateTime", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			double totAdv = 0;

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				PdfPCell cell;
				// System.err.println("I " + i);
				int flag = 0;
				GetAdvance prog = progList.get(i);
				for (int k = 0; k < progList1.size(); k++) {

					if (progList1.get(k).getAdvId() == prog.getId()) {

						flag = flag + 1;
						if (flag == 1) {

							index++;
							cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);

							table.addCell(cell);

							cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);

							table.addCell(cell);

							cell = new PdfPCell(new Phrase(
									"" + prog.getFirstName().concat(" ").concat(prog.getSurname()), headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);

							table.addCell(cell);

							cell = new PdfPCell(new Phrase("" + prog.getAdvDate(), headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);

							table.addCell(cell);

							cell = new PdfPCell(new Phrase("" + prog.getAdvAmount(), headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

							table.addCell(cell);

							cell = new PdfPCell(new Phrase("" + prog.getSkipId(), headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

							table.addCell(cell);
						} else {

							cell = new PdfPCell(new Phrase("", headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);

							table.addCell(cell);

							cell = new PdfPCell(new Phrase("", headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);

							table.addCell(cell);

							cell = new PdfPCell(new Phrase("", headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);

							table.addCell(cell);

							cell = new PdfPCell(new Phrase("", headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);

							table.addCell(cell);

							cell = new PdfPCell(new Phrase("", headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

							table.addCell(cell);

							cell = new PdfPCell(new Phrase("", headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

							table.addCell(cell);
						}

						cell = new PdfPCell(new Phrase("" + progList1.get(k).getSkipRemarks(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + progList1.get(k).getExVar1(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + progList1.get(k).getSkipLoginTime(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);

						table.addCell(cell);

					}

				}

				if (flag == 0) {

					index++;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase("" + prog.getFirstName().concat(" ").concat(prog.getSurname()), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAdvDate(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAdvAmount(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getSkipId(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + "-", headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + "-", headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + "-", headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}
				totAdv = totAdv + prog.getAdvAmount();
			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Month-Year: " + month));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);
			document.add(new Paragraph("Total Advance Amt: " + totAdv));
			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Adv Date");
				rowData.add("Adv Amount");
				rowData.add("Skip Count");
				rowData.add("Skip Remarks");
				rowData.add("Prev Skipped By ");
				rowData.add("Prev Skipped DateTime ");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				double advAmt = 0;
				for (int i = 0; i < progList.size(); i++) {
					System.err.println("i:" + i);

					int flag = 0;

					for (int k = 0; k < progList1.size(); k++) {

						if (progList1.get(k).getAdvId() == progList.get(i).getId()) {
							flag = flag + 1;
							expoExcel = new ExportToExcel();
							rowData = new ArrayList<String>();
							if (flag == 1) {

								cnt = cnt + i;
								rowData.add("" + (i + 1));
								rowData.add("" + progList.get(i).getEmpCode());
								rowData.add("" + progList.get(i).getFirstName().concat(" ")
										.concat(progList.get(i).getSurname()));
								rowData.add("" + progList.get(i).getAdvDate());
								rowData.add("" + progList.get(i).getAdvAmount());
								rowData.add("" + progList.get(i).getSkipId());
							} else {
								rowData.add("" + " ");
								rowData.add("" + " ");
								rowData.add("" + " ");
								rowData.add("" + " ");
								rowData.add("" + " ");
								rowData.add("" + " ");
							}
							rowData.add("" + progList1.get(k).getSkipRemarks());

							rowData.add("" + progList1.get(k).getExVar1());

							rowData.add("" + progList1.get(k).getSkipLoginTime());
							expoExcel.setRowData(rowData);
							exportToExcelList.add(expoExcel);
						}

					}

					if (flag == 0) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getEmpCode());
						rowData.add(
								"" + progList.get(i).getFirstName().concat(" ").concat(progList.get(i).getSurname()));
						rowData.add("" + progList.get(i).getAdvDate());
						rowData.add("" + progList.get(i).getAdvAmount());
						rowData.add("" + progList.get(i).getSkipId());
						rowData.add("-");
						rowData.add("-");
						rowData.add("-");
						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					advAmt = advAmt + progList.get(i).getAdvAmount();

				}
				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Month-Year: " + month,
							"Total Advance: " + advAmt, 'I');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

//***Attendence Report
	@RequestMapping(value = "/showEmpAttendRegisterRep", method = RequestMethod.GET)
	public void showEmpAttendRegisterRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "ATTENDANCE REGISTER";

		HttpSession session = request.getSession();

		String date = request.getParameter("date");

		String temp[] = date.split("-");

		String year = temp[1];
		String month = temp[0];
		if (month.length() == 1) {
			month = "0".concat(month);
		}

		LocalDate date3 = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt("01"));
		int daysToday = date3.lengthOfMonth();

		String fromDate = year.concat("-").concat(month).concat("-").concat("01");
		String toDate = year.concat("-").concat(month).concat("-").concat(String.valueOf(daysToday));

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			GetDailyDailyRecord[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAttendenceRegReport", map, GetDailyDailyRecord[].class);
			List<GetDailyDailyRecord> progList = new ArrayList<>(Arrays.asList(resArray));
			// System.err.println("daily rec" + progList.toString());
			map = new LinkedMultiValueMap<>();
			map.add("empRes", -1);
			map.add("companyId", 1);

			EmployeeMaster[] empArr = Constants.getRestTemplate().postForObject(Constants.url + "/getAllEmp", map,
					EmployeeMaster[].class);
			List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>(Arrays.asList(empArr));

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize.A1);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));
			// System.err.println("daysToday" + daysToday);
			PdfPTable table = new PdfPTable(3 + daysToday);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			float f[];
			f = new float[3 + daysToday];
			f[0] = 2.0f;
			f[1] = 3.0f;
			f[2] = 6.0f;

			for (int i = 3; i <= 2 + daysToday; i++) {
				f[i] = 2.5f;

				// System.err.println("float Array" + f[i]);

			}

			table.setWidths(f);
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			for (int j = 1; j <= daysToday; j++) {

				hcell = new PdfPCell(new Phrase(String.valueOf(j), tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

				table.addCell(hcell);

			}

			int index = 0;
			for (int i = 0; i < empList.size(); i++) {

				EmployeeMaster prog = empList.get(i);
				int empId = prog.getEmpId();

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase("" + prog.getFirstName().concat(" ").concat(prog.getSurname()), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				for (int j = 1; j <= daysToday; j++) {

					int flag = 0;
					String stat = null;
					for (int p = 0; p < progList.size(); p++) {
						int dayCurr = progList.get(p).getEmpType();

						if (dayCurr == j && empId == progList.get(p).getEmpId()) {

							flag = 1;
							stat = progList.get(p).getAttStatus();
							break;
						}
					}
					if (flag == 1) {
						cell = new PdfPCell(new Phrase("" + stat, headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
					} else {
						cell = new PdfPCell(new Phrase("" + "-", headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
					}

				}

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Month-Year:" + date));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");

				for (int j = 1; j <= daysToday; j++) {
					rowData.add(String.valueOf(j));
				}
				char colCoun = 'A';

				/*
				 * if(daysToday==28) { colCoun='AB'; }else if(daysToday==30) {
				 * 
				 * colCoun="AE"; }else { colCoun="AF"; }
				 */
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;

				for (int i = 0; i < empList.size(); i++) {
					int emp = empList.get(i).getEmpId();
					rowData.add("" + (i + 1));
					rowData.add("" + empList.get(i).getEmpCode());
					rowData.add("" + empList.get(i).getFirstName().concat(" ").concat(empList.get(i).getSurname()));

					for (int j = 1; j <= daysToday; j++) {

						int flag = 0;
						String stat = null;
						for (int l = 0; l < progList.size(); l++) {
							int dayCurr = progList.get(p).getEmpType();

							if (dayCurr == j && emp == progList.get(l).getEmpId()) {

								flag = 1;
								stat = progList.get(l).getAttStatus();
								break;
							}
						}
						if (flag == 1) {

							rowData.add("" + stat);
						} else {
							rowData.add("" + "-");
						}

					}

					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Month-Year:" + date, "", colCoun);

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date1 + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showAdvancePaymentYearlyRep", method = RequestMethod.GET)
	public void showAdvancePaymentYearlyRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Yearly Advance Amount";

		HttpSession session = request.getSession();

		String month = request.getParameter("date");

		String temp[] = month.split("-");
		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("year", temp[1]);
			GetYearlyAdvanceNew[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAdvanceYearlyReport", map, GetYearlyAdvanceNew[].class);
			List<GetYearlyAdvanceNew> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(16);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 5.5f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f,
					2.2f, 2.2f, 2.2f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Jan", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Feb", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("March", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("April", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("May", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("June", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("July", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Aug", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Sep", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Oct", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Nov", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Dec", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				GetYearlyAdvanceNew prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getJanCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getFebCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getMarchCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getMayCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getMarchCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getJunCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getJulCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getAugCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getSepCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getOctCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getNovCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDecCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getTotal(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Year:" + temp[1]));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Jan");
				rowData.add("Feb");
				rowData.add("March");
				rowData.add("April");
				rowData.add("May");
				rowData.add("June");
				rowData.add("July");
				rowData.add("Aug");
				rowData.add("Sep");
				rowData.add("Oct");
				rowData.add("Nov");
				rowData.add("Dec");
				rowData.add("Total");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getJanCount());
					rowData.add("" + progList.get(i).getFebCount());
					rowData.add("" + progList.get(i).getMarchCount());
					rowData.add("" + progList.get(i).getAprCount());

					rowData.add("" + progList.get(i).getMayCount());
					rowData.add("" + progList.get(i).getJunCount());

					rowData.add("" + progList.get(i).getJulCount());
					rowData.add("" + progList.get(i).getAugCount());

					rowData.add("" + progList.get(i).getSepCount());
					rowData.add("" + progList.get(i).getOctCount());

					rowData.add("" + progList.get(i).getNovCount());
					rowData.add("" + progList.get(i).getDecCount());
					rowData.add("" + progList.get(i).getTotal());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Year:" + temp[1], "", 'P');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showEmpLateMarkRep", method = RequestMethod.GET)
	public void showEmpLateMarkRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee Late Mark";

		HttpSession session = request.getSession();

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("fromDate", DateConvertor.convertToYMD(arrOfStr[0]));
			map.add("toDate", DateConvertor.convertToYMD(arrOfStr[1]));
			DailyAttendance[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getEmpLateMarkReport", map, DailyAttendance[].class);
			List<DailyAttendance> progList = new ArrayList<>(Arrays.asList(resArray));
			System.err.println("data:" + progList.toString());
			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize.A4);
			document.setMargins(5, 5, 2, 2);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(8);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1.5f, 2.2f, 4.0f, 2.5f, 4.0f, 2.0f, 2.0f, 2.0f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr. No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Date", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Shift Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Late Min", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("In Time", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Out Time", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {

				DailyAttendance prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + DateConvertor.convertToDMY(prog.getAttDate()), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getCurrentShiftname(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLateMin(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getInTime(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getOutTime(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Date" + arrOfStr[0] + "To" + arrOfStr[1]));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();
				rowData.add("Sr.No.");
				rowData.add("Employee Code");
				rowData.add("Employee Name");
				rowData.add("Date");
				rowData.add("Shift Name");
				rowData.add("Late Min");
				rowData.add("In Time");
				rowData.add("Out Time");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;
					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + DateConvertor.convertToDMY(progList.get(i).getAttDate()));
					rowData.add("" + progList.get(i).getCurrentShiftname());
					rowData.add("" + progList.get(i).getLateMin());
					rowData.add("" + progList.get(i).getInTime());
					rowData.add("" + progList.get(i).getOutTime());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date:" + arrOfStr[0] + "To" + arrOfStr[1], "", 'H');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showEmpAttendanceRep", method = RequestMethod.GET)
	public void showEmpAttendanceRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "DAILY ATTENDANCE REPORT";

		HttpSession session = request.getSession();

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("fromDate", DateConvertor.convertToYMD(arrOfStr[0]));
			map.add("toDate", DateConvertor.convertToYMD(arrOfStr[1]));
			EmpAttendeanceRep[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getDailyAttendenceReport", map, EmpAttendeanceRep[].class);
			List<EmpAttendeanceRep> progList = new ArrayList<>(Arrays.asList(resArray));
			System.err.println("data:" + progList.toString());
			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize.A4);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 3.0f, 3.0f, 3.0f, 3.0f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Employee Present", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee On Leave", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Absent ", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee On Week-off", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {

				EmpAttendeanceRep prog = progList.get(i);

				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase("" + prog.getEmpPresent(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getPaidLeave(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getUnpaidLeave(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getWeekOff(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Date: " + arrOfStr[0] + "To" + arrOfStr[1]));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Employee Present");
				rowData.add("Employee On Leave");
				rowData.add("Employee Absent");
				rowData.add("Employee On Week-off");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + progList.get(i).getEmpPresent());
					rowData.add("" + progList.get(i).getPaidLeave());
					rowData.add("" + progList.get(i).getUnpaidLeave());
					rowData.add("" + progList.get(i).getWeekOff());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date:" + arrOfStr[0] + "To" + arrOfStr[1], "", 'D');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	// *******************************Loan Reports****************************

	@RequestMapping(value = "/showEmpLoanRep", method = RequestMethod.GET)
	public void showEmpLoanRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "EMPLOYEE LOAN REPORT";

		HttpSession session = request.getSession();

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("fromDate", DateConvertor.convertToYMD(arrOfStr[0]));
			map.add("toDate", DateConvertor.convertToYMD(arrOfStr[1]));
			EmpAttendeanceRep[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getDailyAttendenceReport", map, EmpAttendeanceRep[].class);
			List<EmpAttendeanceRep> progList = new ArrayList<>(Arrays.asList(resArray));
			System.err.println("data:" + progList.toString());
			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize.A4);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 3.0f, 3.0f, 3.0f, 3.0f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Employee Present", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee On Leave", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Absent ", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee On Week-off", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {

				EmpAttendeanceRep prog = progList.get(i);

				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase("" + prog.getEmpPresent(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getPaidLeave(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getUnpaidLeave(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getWeekOff(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Date: " + arrOfStr[0] + "To" + arrOfStr[1]));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Employee Present");
				rowData.add("Employee On Leave");
				rowData.add("Employee Absent");
				rowData.add("Employee On Week-off");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + progList.get(i).getEmpPresent());
					rowData.add("" + progList.get(i).getPaidLeave());
					rowData.add("" + progList.get(i).getUnpaidLeave());
					rowData.add("" + progList.get(i).getWeekOff());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date:" + arrOfStr[0] + "To" + arrOfStr[1], "", 'D');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showLoanRep", method = RequestMethod.GET)
	public void showLoanRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee Loan Report";

		HttpSession session = request.getSession();

		String month = request.getParameter("date");

		String temp[] = month.split("-");
		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("month", temp[0]);
			map.add("year", temp[1]);
			GetLoanReport[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getLoanReport", map,
					GetLoanReport[].class);
			List<GetLoanReport> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(10);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 5.5f, 3.5f, 3.5f, 3.5f, 3.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Application No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Loan Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Loan Repay Start", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Loan Repay End", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Current Total Paid", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Current Total Outstanding", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Status", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				GetLoanReport prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanApplNo(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanAmt(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanRepayStart(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanRepayEnd(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getCurrentTotpaid(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getCurrentOutstanding(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanStatus(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Month-Year: " + month));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Application No");
				rowData.add("Loan Amt");
				rowData.add("Loan Repay Start");
				rowData.add("Loan Repay End");
				rowData.add("Current Total Paid");
				rowData.add("Current Total Outstanding");
				rowData.add("Status");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getLoanApplNo());
					rowData.add("" + progList.get(i).getLoanAmt());
					rowData.add("" + progList.get(i).getLoanRepayStart());
					rowData.add("" + progList.get(i).getLoanRepayEnd());
					rowData.add("" + progList.get(i).getCurrentTotpaid());
					rowData.add("" + progList.get(i).getCurrentOutstanding());
					rowData.add("" + progList.get(i).getLoanStatus());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Month-Year:" + month, "", 'J');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showLoanDedRep", method = RequestMethod.GET)
	public void showLoanDedRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Loan Deduction Report";

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);
		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("fromDate", DateConvertor.convertToYMD(arrOfStr[0]));
			map.add("toDate", DateConvertor.convertToYMD(arrOfStr[1]));
			LoanDedReport[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getLoanDedReport",
					map, LoanDedReport[].class);
			List<LoanDedReport> progList = new ArrayList<>(Arrays.asList(resArray));
			double loanAmtTot = 0;
			double loanEmiAmtTot = 0;
			double loanEmiIntrestAmtTot = 0;
			double paindingTot = 0;
			double paidTot = 0;

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(9);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 5.5f, 3.5f, 3.5f, 3.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Payment Date", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Loan Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Loan EMI", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Loan EMI Interest", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Paid Amount", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Pending Amount", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			  loanAmtTot = 0;
			  loanEmiAmtTot = 0;
			  loanEmiIntrestAmtTot = 0;
			  paindingTot = 0;
			  paidTot = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				LoanDedReport prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanRepayStart(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanAmt(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanEmi(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanEmiIntrest(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getCurrentTotpaid(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getCurrentOutstanding(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				loanAmtTot = loanAmtTot + prog.getLoanAmt();
				loanEmiAmtTot = loanEmiAmtTot + prog.getLoanEmi();
				loanEmiIntrestAmtTot = loanEmiIntrestAmtTot + prog.getLoanEmiIntrest();
				paindingTot = paindingTot + prog.getCurrentOutstanding();
				paidTot = paidTot + prog.getCurrentTotpaid();

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Date Range: " + leaveDateRange));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);
			document.add(new Paragraph("Loan Total Amt: " + loanAmtTot));

			document.add(new Paragraph("Total Emi Amt: " + loanEmiAmtTot));

			document.add(new Paragraph("Total Emi Interest: " + loanEmiIntrestAmtTot));

			document.add(new Paragraph("Total Paid : " + paindingTot));

			document.add(new Paragraph("Total Pending : " + paidTot));

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				loanAmtTot = 0;
				loanEmiAmtTot = 0;
				loanEmiIntrestAmtTot = 0;
				paindingTot = 0;
				paidTot = 0;

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Loan Payment Date");

				rowData.add("Loan Amt");
				rowData.add("Loan Emi");
				rowData.add("Loan Emi Interest");
				rowData.add("Paid Amount");
				rowData.add("Pending Amount");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getLoanRepayStart());
					rowData.add("" + progList.get(i).getLoanAmt());
					rowData.add("" + progList.get(i).getLoanEmi());
					rowData.add("" + progList.get(i).getLoanEmiIntrest());
					rowData.add("" + progList.get(i).getCurrentTotpaid());
					rowData.add("" + progList.get(i).getCurrentOutstanding());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					loanAmtTot = loanAmtTot + progList.get(i).getLoanAmt();
					loanEmiAmtTot = loanEmiAmtTot + progList.get(i).getLoanEmi();
					loanEmiIntrestAmtTot = loanEmiIntrestAmtTot + progList.get(i).getLoanEmiIntrest();
					paindingTot = paindingTot + progList.get(i).getCurrentOutstanding();
					paidTot = paidTot + progList.get(i).getCurrentTotpaid();

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Date Range:" + leaveDateRange,
							",Total Loan Amt:" + loanAmtTot + ",Total Emi Amt " + loanEmiAmtTot
									+ ",Total Emi Interest Amt" + loanEmiIntrestAmtTot + ",Total Paid" + paidTot
									+ ",Total Pending" + paindingTot,
							'I');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	/*
	 * @RequestMapping(value = "/showLoanYearlyRep", method = RequestMethod.GET)
	 * public void showLoanYearlyRep(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * String reportName = "Yearly Loan Amount";
	 * 
	 * HttpSession session = request.getSession();
	 * 
	 * String month = request.getParameter("date");
	 * 
	 * String temp[] = month.split("-"); Boolean ret = false; try {
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
	 * Object>(); map.add("companyId", 1); map.add("year", temp[1]); GetYearlyLoan[]
	 * resArray = Constants.getRestTemplate() .postForObject(Constants.url +
	 * "getLoanYearlyReport", map, GetYearlyLoan[].class); List<GetYearlyLoan>
	 * progList = new ArrayList<>(Arrays.asList(resArray));
	 * 
	 * 
	 * map = new LinkedMultiValueMap<>(); map.add("empRes", -1);
	 * map.add("companyId", 1);
	 * 
	 * EmployeeMaster[] empArr =
	 * Constants.getRestTemplate().postForObject(Constants.url + "/getAllEmp", map,
	 * EmployeeMaster[].class); List<EmployeeMaster> empList = new
	 * ArrayList<EmployeeMaster>(Arrays.asList(empArr));
	 * 
	 * String header = ""; String title = "                 ";
	 * 
	 * DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy"); String repDate =
	 * DF2.format(new Date());
	 * 
	 * Document document = new Document(PageSize._11X17); document.setMargins(5, 5,
	 * 0, 0); document.setMarginMirroring(false);
	 * 
	 * String FILE_PATH = Constants.REPORT_SAVE; File file = new File(FILE_PATH);
	 * 
	 * PdfWriter writer = null;
	 * 
	 * FileOutputStream out = new FileOutputStream(FILE_PATH); try { writer =
	 * PdfWriter.getInstance(document, out); } catch (DocumentException e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * ItextPageEvent event = new ItextPageEvent(header, title, "", "");
	 * 
	 * writer.setPageEvent(event); // writer.add(new
	 * Paragraph("Curricular Aspects"));
	 * 
	 * PdfPTable table = new PdfPTable(16);
	 * 
	 * table.setHeaderRows(1);
	 * 
	 * table.setWidthPercentage(100); table.setWidths(new float[] { 2.0f, 3.0f,
	 * 5.5f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f,
	 * 2.2f }); Font headFontData = ReportCostants.headFontData;// new
	 * Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, // BaseColor.BLACK); Font
	 * tableHeaderFont = ReportCostants.tableHeaderFont; // new
	 * Font(FontFamily.HELVETICA, 12, Font.BOLD, // BaseColor.BLACK);
	 * tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);
	 * 
	 * PdfPCell hcell = new PdfPCell();
	 * hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	 * 
	 * hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Jan", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Feb", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("March", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell); hcell = new PdfPCell(new Phrase("April",
	 * tableHeaderFont)); hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("May", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("June", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell); hcell = new PdfPCell(new Phrase("July",
	 * tableHeaderFont)); hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Aug", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Sep", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell); hcell = new PdfPCell(new Phrase("Oct",
	 * tableHeaderFont)); hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Nov", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Dec", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Total", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * 
	 * 
	 * 
	 * int index = 0;
	 * 
	 * for (int j = 0; j < empList.size(); j++) {
	 * 
	 * int empId=empList.get(j).getEmpId();
	 * 
	 * for (int i = 0; i < progList.size(); i++) { GetYearlyLoan prog =
	 * progList.get(i);
	 * 
	 * 
	 * if(prog.getEmpId()==empId &&) {
	 * 
	 * }
	 * 
	 * 
	 * 
	 * // System.err.println("I " + i);
	 * 
	 * 
	 * index++; PdfPCell cell; cell = new PdfPCell(new Phrase(String.valueOf(index),
	 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + prog.getJanCount(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + prog.getFebCount(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + prog.getMarchCount(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + prog.getMayCount(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * table.addCell(cell); cell = new PdfPCell(new Phrase("" +
	 * prog.getMarchCount(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * table.addCell(cell); cell = new PdfPCell(new Phrase("" + prog.getJunCount(),
	 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * table.addCell(cell); cell = new PdfPCell(new Phrase("" + prog.getJulCount(),
	 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * table.addCell(cell); cell = new PdfPCell(new Phrase("" + prog.getAugCount(),
	 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * table.addCell(cell); cell = new PdfPCell(new Phrase("" + prog.getSepCount(),
	 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * table.addCell(cell); cell = new PdfPCell(new Phrase("" + prog.getOctCount(),
	 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + prog.getNovCount(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + prog.getDecCount(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + prog.getTotal(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(cell);
	 * 
	 * }}
	 * 
	 * document.open(); Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f,
	 * Font.UNDERLINE, BaseColor.BLACK);
	 * 
	 * Paragraph name = new Paragraph(reportName, hf);
	 * name.setAlignment(Element.ALIGN_CENTER); document.add(name); document.add(new
	 * Paragraph("\n")); document.add(new Paragraph("Year:" + temp[1]));
	 * 
	 * document.add(new Paragraph("\n")); DateFormat DF = new
	 * SimpleDateFormat("dd-MM-yyyy");
	 * 
	 * document.add(table);
	 * 
	 * int totalPages = writer.getPageNumber();
	 * 
	 * // System.out.println("Page no " + totalPages);
	 * 
	 * document.close(); int p = Integer.parseInt(request.getParameter("p")); //
	 * System.err.println("p " + p);
	 * 
	 * if (p == 1) {
	 * 
	 * if (file != null) {
	 * 
	 * String mimeType = URLConnection.guessContentTypeFromName(file.getName());
	 * 
	 * if (mimeType == null) {
	 * 
	 * mimeType = "application/pdf";
	 * 
	 * }
	 * 
	 * response.setContentType(mimeType);
	 * 
	 * response.addHeader("content-disposition",
	 * String.format("inline; filename=\"%s\"", file.getName()));
	 * 
	 * response.setContentLength((int) file.length());
	 * 
	 * InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	 * 
	 * try { FileCopyUtils.copy(inputStream, response.getOutputStream()); } catch
	 * (IOException e) { // System.out.println("Excep in Opening a Pdf File");
	 * e.printStackTrace(); } } } else {
	 * 
	 * List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
	 * 
	 * ExportToExcel expoExcel = new ExportToExcel(); List<String> rowData = new
	 * ArrayList<String>();
	 * 
	 * rowData.add("Sr. No"); rowData.add("Emp Code"); rowData.add("Emp Name");
	 * rowData.add("Jan"); rowData.add("Feb"); rowData.add("March");
	 * rowData.add("April"); rowData.add("May"); rowData.add("June");
	 * rowData.add("July"); rowData.add("Aug"); rowData.add("Sep");
	 * rowData.add("Oct"); rowData.add("Nov"); rowData.add("Dec");
	 * rowData.add("Total");
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel); int cnt = 1;
	 * for (int i = 0; i < progList.size(); i++) { expoExcel = new ExportToExcel();
	 * rowData = new ArrayList<String>(); cnt = cnt + i;
	 * 
	 * rowData.add("" + (i + 1)); rowData.add("" + progList.get(i).getEmpCode());
	 * rowData.add("" + progList.get(i).getEmpName()); rowData.add("" +
	 * progList.get(i).getJanCount()); rowData.add("" +
	 * progList.get(i).getFebCount()); rowData.add("" +
	 * progList.get(i).getMarchCount()); rowData.add("" +
	 * progList.get(i).getAprCount());
	 * 
	 * rowData.add("" + progList.get(i).getMayCount()); rowData.add("" +
	 * progList.get(i).getJunCount());
	 * 
	 * rowData.add("" + progList.get(i).getJulCount()); rowData.add("" +
	 * progList.get(i).getAugCount());
	 * 
	 * rowData.add("" + progList.get(i).getSepCount()); rowData.add("" +
	 * progList.get(i).getOctCount());
	 * 
	 * rowData.add("" + progList.get(i).getNovCount()); rowData.add("" +
	 * progList.get(i).getDecCount()); rowData.add("" + progList.get(i).getTotal());
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
	 * 
	 * }
	 * 
	 * XSSFWorkbook wb = null; try {
	 * 
	 * wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Year:" +
	 * temp[1], "", 'P');
	 * 
	 * ExceUtil.autoSizeColumns(wb, 3);
	 * response.setContentType("application/vnd.ms-excel"); String date = new
	 * SimpleDateFormat("yyyy-MM-dd").format(new Date());
	 * response.setHeader("Content-disposition", "attachment; filename=" +
	 * reportName + "-" + date + ".xlsx"); wb.write(response.getOutputStream());
	 * 
	 * } catch (IOException ioe) { throw new
	 * RuntimeException("Error writing spreadsheet to output stream"); } finally {
	 * if (wb != null) { wb.close(); } } }
	 * 
	 * } catch (Exception e) {
	 * 
	 * System.err.println("Exce in showProgReport " + e.getMessage());
	 * e.printStackTrace();
	 * 
	 * } }
	 */
	// ******************PF Statement***********************

	@RequestMapping(value = "/showEmpPfRep", method = RequestMethod.GET)
	public void showEmpPfRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee PF Statement";
		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);
		int cmpId = 0;
		try {
			cmpId = Integer.parseInt(request.getParameter("subCmpId"));

		} catch (Exception e) {
			cmpId = 0;
		}

		String cmpName = "-";
		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", cmpId);
			map.add("fromDate", arrOfStr[0]);
			map.add("toDate", arrOfStr[1]);
			GetSalaryCalcReport[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getPfStatement",
					map, GetSalaryCalcReport[].class);
			List<GetSalaryCalcReport> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 6.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("EPF Wages", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee's Contribution PF", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("EPF %", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				GetSalaryCalcReport prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEpfWages(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmployeePf(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEpfPercentage(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				if (cmpId != 0) {
					cmpName = prog.getCompanyName();

				}

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Date Range: " + leaveDateRange));
			document.add(new Paragraph("Company Name: " + cmpName));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("EPF Wages");
				rowData.add("Employee's Contribution PF");
				rowData.add("EPF %");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getEpfWages());
					rowData.add("" + progList.get(i).getEmployeePf());
					rowData.add("" + progList.get(i).getEpfPercentage());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					if (cmpId != 0) {
						cmpName = progList.get(i).getCompanyName();

					}

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date Range:" + leaveDateRange + " Company Name:" + cmpName, "", 'F');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showEmployerPfRep", method = RequestMethod.GET)
	public void showEmployerPfRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employeer PF  Statement";

		HttpSession session = request.getSession();

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);
		int cmpId = 0;
		try {
			cmpId = Integer.parseInt(request.getParameter("subCmpId"));

		} catch (Exception e) {
			cmpId = 0;
		}
		String cmpName = "-";

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", cmpId);
			map.add("fromDate", arrOfStr[0]);
			map.add("toDate", arrOfStr[1]);
			GetSalaryCalcReport[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getPfStatement",
					map, GetSalaryCalcReport[].class);
			List<GetSalaryCalcReport> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 5.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("EPS Wages", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employeer's Contribution PF", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("EPF %", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				GetSalaryCalcReport prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmployerEps(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmployerPf(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEpfEmployerPercentage(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				if (cmpId != 0) {
					cmpName = prog.getCompanyName();

				}

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Date Range: " + leaveDateRange));
			document.add(new Paragraph("Company Name: " + cmpName));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("EPS Wages");
				rowData.add("Employeer's Contribution PF");
				rowData.add("EPF %");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getEmployerEps());
					rowData.add("" + progList.get(i).getEmployerPf());
					rowData.add("" + progList.get(i).getEpfEmployerPercentage());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					if (cmpId != 0) {

						cmpName = progList.get(i).getCompanyName();
					}

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"DateRange:" + leaveDateRange + " Company Name:" + cmpName, "", 'F');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showEmployeeEmployerPfRep", method = RequestMethod.GET)
	public void showEmployeeEmployerPfRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee Employeer PF  Statement";

		HttpSession session = request.getSession();

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);
		int cmpId = 0;
		try {
			cmpId = Integer.parseInt(request.getParameter("subCmpId"));

		} catch (Exception e) {
			cmpId = 0;
		}

		String cmpName = "-";

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", cmpId);
			map.add("fromDate", arrOfStr[0]);
			map.add("toDate", arrOfStr[1]);
			GetSalaryCalcReport[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getPfStatement",
					map, GetSalaryCalcReport[].class);
			List<GetSalaryCalcReport> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 5.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("PFable Salary/Wages", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee's Contribution PF", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employeer's Contribution PF", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				GetSalaryCalcReport prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEpfWages(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmployeePf(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmployerPf(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				if (cmpId != 0) {
					cmpName = prog.getCompanyName();

				}

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("DateRange: " + leaveDateRange));
			document.add(new Paragraph("Company Name: " + cmpName));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("PFable Salary/Wages");
				rowData.add("Employee's Contribution PF");
				rowData.add("Employeer's Contribution PF");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getEpfWages());
					rowData.add("" + progList.get(i).getEmployeePf());
					rowData.add("" + progList.get(i).getEmployerPf());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					if (cmpId != 0) {

						cmpName = progList.get(i).getCompanyName();
					}

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"DateRange:" + leaveDateRange + " Company Name:" + cmpName, "", 'F');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showEmpPfRepEmpWise", method = RequestMethod.GET)
	public void showEmpPfRepEmpWise(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee PF Statement";

		int empId = 0;
		try {
			empId = Integer.parseInt(request.getParameter("empId1"));

		} catch (Exception e) {
			empId = 0;

		}
		String fromDate = request.getParameter("datepickerFromRep");
		String toDate = request.getParameter("datepickerToRep");

		System.err.println("empId :" + empId);

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("empId", empId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);

			GetSalaryCalcReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getEmpPfStatement", map, GetSalaryCalcReport[].class);
			List<GetSalaryCalcReport> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 6.5f, 3.0f, 3.0f, 3.0f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("EPF Wages", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee's Contribution PF", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("EPF %", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				GetSalaryCalcReport prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEpfWages(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmployeePf(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEpfPercentage(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("DateRange: " + fromDate + "To" + toDate));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("EPF Wages");
				rowData.add("Employee's Contribution PF");
				rowData.add("EPF %");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getEpfWages());
					rowData.add("" + progList.get(i).getEmployeePf());
					rowData.add("" + progList.get(i).getEpfPercentage());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date Range:" + fromDate + "To" + toDate, "", 'F');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/getPtChallanRep", method = RequestMethod.GET)
	public void getPtChallanRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Professsional Tax Challen";

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);
		int cmpId = 0;
		try {
			cmpId = Integer.parseInt(request.getParameter("subCmpId"));

		} catch (Exception e) {
			cmpId = 0;
		}

		System.err.println("cmpId" + cmpId);

		String cmpName = "-";

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", cmpId);
			map.add("fromDate", arrOfStr[0]);
			map.add("toDate", arrOfStr[1]);

			GetPtChallan[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getPtChallanRep", map,
					GetPtChallan[].class);
			List<GetPtChallan> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 5.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("PT Limit", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("No. of Employees", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total Amount", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				GetPtChallan prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getMinVal() + "-" + prog.getMaxVal(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getTotal(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("DateRange: " + leaveDateRange));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("PT Limit");
				rowData.add("NO. of Employees");
				rowData.add("Total Amount");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getMinVal() + "-" + progList.get(i).getMaxVal());
					rowData.add("" + progList.get(i).getEmpCount());
					rowData.add("" + progList.get(i).getTotal());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Date Range:" + leaveDateRange, "",
							'D');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showEmpPTStatRep", method = RequestMethod.GET)
	public void showEmpPTStatRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Professional Tax Statement";
		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);
		int cmpId = 0;
		try {
			cmpId = Integer.parseInt(request.getParameter("subCmpId"));

		} catch (Exception e) {
			cmpId = 0;
		}

		System.err.println("cmpId" + cmpId);

		String cmpName = "-";
		Boolean ret = false;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			if (cmpId != 0) {
				map.add("companyId", cmpId);
				MstCompanySub comp = Constants.getRestTemplate().postForObject(Constants.url + "/getSubCompanyById",
						map, MstCompanySub.class);
				cmpName = comp.getCompanyName();
			}

			map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", cmpId);
			map.add("fromDate", arrOfStr[0]);
			map.add("toDate", arrOfStr[1]);
			GetSalaryCalcReport[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getPfStatement",
					map, GetSalaryCalcReport[].class);
			List<GetSalaryCalcReport> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 6.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Month-Year", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("PT calculated On", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("PT Amount", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				GetSalaryCalcReport prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getCalcMonth() + "-" + prog.getCalcYear(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getGrossSalary(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getPtDed(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				if (cmpId != 0) {
					cmpName = prog.getCompanyName();

				}

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Date Range: " + leaveDateRange));
			document.add(new Paragraph("Company Name: " + cmpName));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Month-Year %");
				rowData.add("PT calculated On");
				rowData.add("PT Amount");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getCalcMonth() + "-" + progList.get(i).getCalcYear());
					rowData.add("" + progList.get(i).getGrossSalary());
					rowData.add("" + progList.get(i).getPtDed());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					if (cmpId != 0) {
						cmpName = progList.get(i).getCompanyName();

					}

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date Range:" + leaveDateRange + " Company Name:" + cmpName, "", 'F');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showMlwfStatRep", method = RequestMethod.GET)
	public void showMlwfStatRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "MLWF Statement";
		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);
		int cmpId = 0;
		try {
			cmpId = Integer.parseInt(request.getParameter("subCmpId"));

		} catch (Exception e) {
			cmpId = 0;
		}

		System.err.println("cmpId" + cmpId);

		String cmpName = "-";
		Boolean ret = false;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			if (cmpId != 0) {
				map.add("companyId", cmpId);
				MstCompanySub comp = Constants.getRestTemplate().postForObject(Constants.url + "/getSubCompanyById",
						map, MstCompanySub.class);
				cmpName = comp.getCompanyName();
			}

			map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", cmpId);
			map.add("fromDate", arrOfStr[0]);
			map.add("toDate", arrOfStr[1]);
			GetSalaryCalcReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getMLWFStatement", map, GetSalaryCalcReport[].class);
			List<GetSalaryCalcReport> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(8);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 6.5f, 3.5f, 3.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Father Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("DOB", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee's LWF", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employeer's LWF", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				GetSalaryCalcReport prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getNameSd(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				if (prog.getCompanyName() != null) {
					cell = new PdfPCell(
							new Phrase("" + DateConvertor.convertToDMY(prog.getCompanyName()), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(new Phrase("" + "-", headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				cell = new PdfPCell(new Phrase("" + prog.getMlwf(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmployerMlwf(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmployerMlwf() + prog.getMlwf(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Date Range: " + leaveDateRange));
			document.add(new Paragraph("Company Name: " + cmpName));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Father's Name");
				rowData.add("DOB");
				rowData.add("Employee's LWF");
				rowData.add("Employeer's LWF");
				rowData.add("Total");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getNameSd());
					if (progList.get(i).getCompanyName() != null) {
						rowData.add("" + DateConvertor.convertToDMY(progList.get(i).getCompanyName()));
					} else {
						rowData.add("" + "-");
					}
					rowData.add("" + progList.get(i).getMlwf());
					rowData.add("" + progList.get(i).getEmployerMlwf());
					rowData.add("" + progList.get(i).getEmployerMlwf() + progList.get(i).getMlwf());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date Range:" + leaveDateRange + " Company Name:" + cmpName, "", 'H');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showStatutoryEsicRep", method = RequestMethod.GET)
	public void showStatutoryEsicRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Statutory ESIC Statement";
		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);
		int cmpId = 0;
		try {
			cmpId = Integer.parseInt(request.getParameter("subCmpId"));

		} catch (Exception e) {
			cmpId = 0;
		}

		double salWagesTot = 0;
		double empCalTot = 0;
		double empr = 0;
		double tot = 0;

		System.err.println("cmpId" + cmpId);

		String cmpName = "-";
		Boolean ret = false;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			if (cmpId != 0) {
				map.add("companyId", cmpId);
				MstCompanySub comp = Constants.getRestTemplate().postForObject(Constants.url + "/getSubCompanyById",
						map, MstCompanySub.class);
				cmpName = comp.getCompanyName();
			}

			map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", cmpId);
			map.add("fromDate", arrOfStr[0]);
			map.add("toDate", arrOfStr[1]);
			StatutoryEsicRep[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getStatutoryEsic",
					map, StatutoryEsicRep[].class);
			List<StatutoryEsicRep> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(9);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 7.0f, 3.5f, 2f, 3.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("ESIC No", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Present Days", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Month-Year", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Sal/Wages", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee's Contr.ESIC", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employeer's Contr.ESIC", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			  salWagesTot = 0;
			  empCalTot = 0;
			  empr = 0;
			  tot = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				StatutoryEsicRep prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEsicNo(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getPresentDays(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getMonth() + "-" + prog.getYear(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getNetSalary(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEsicWagesCal(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmployerEsic(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				salWagesTot = salWagesTot + prog.getNetSalary();
				empCalTot = empCalTot + prog.getEsicWagesCal();
				empr = empr + prog.getEmployerEsic();

			}
			tot = salWagesTot + empCalTot + empr;

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Date Range: " + leaveDateRange));
			document.add(new Paragraph("Company Name: " + cmpName));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);
			document.add(new Paragraph("Total Salary Wages: " + salWagesTot));
			document.add(new Paragraph("Employee Contri: " + empCalTot));
			document.add(new Paragraph("Employeer Contri: " + empr));
			document.add(new Paragraph("Total: " + tot));

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {
				salWagesTot = 0;
				empCalTot = 0;
				empr = 0;
				tot = 0;

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("ESIC No");
				rowData.add("Present Days");
				rowData.add("Month-Year");
				rowData.add("Sal/Wages");
				rowData.add("Employee's Contr.ESIC");
				rowData.add("Employeer's Contr.ESIC");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getEsicNo());
					rowData.add("" + progList.get(i).getPresentDays());
					rowData.add("" + progList.get(i).getMonth() + "-" + progList.get(i).getYear());
					rowData.add("" + progList.get(i).getNetSalary());
					rowData.add("" + progList.get(i).getEsicWagesCal());
					rowData.add("" + progList.get(i).getEmployerEsic());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					salWagesTot = salWagesTot + progList.get(i).getNetSalary();
					empCalTot = empCalTot + progList.get(i).getEsicWagesCal();
					empr = empr + progList.get(i).getEmployerEsic();

				}

				tot = salWagesTot + empCalTot + empr;

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date Range:" + leaveDateRange + " Company Name:" + cmpName,
							"Total Sal Wages:" + salWagesTot + ",Employee Contribution:" + empCalTot
									+ ",Employeer Contribution:" + empr + ",Total" + tot,
							'I');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	// ***Bonus*********************

	@RequestMapping(value = "/showBonusRep", method = RequestMethod.GET)
	public void showBonusRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee Bonus Details";
		String bonusId = request.getParameter("bonusId");

		double bonusAmt = 0;

		Boolean ret = false;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map = new LinkedMultiValueMap<String, Object>();
			map.add("bonusId", bonusId);

			BonusMaster bonus = Constants.getRestTemplate().postForObject(Constants.url + "/getBonusById", map,
					BonusMaster.class);

			BonusCalc[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getBonusReportByBonusId",
					map, BonusCalc[].class);
			List<BonusCalc> progList = new ArrayList<>(Arrays.asList(resArray));

			System.err.println("progList" + progList.toString());

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(9);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 7.0f, 3.5f, 2f, 3.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;
			new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont;

			new Font(FontFamily.HELVETICA, 12, Font.BOLD,

					BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total Bonus Days ", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Bonus Wages", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Gross Bonus Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Paid Bonus Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Paid Bonus Date", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Is Bonus Sheet finalized", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			bonusAmt = 0;
			for (int i = 0; i < progList.size(); i++) { //
				System.err.println("I " + i);
				BonusCalc prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getCompanyEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getTotalBonusDays(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getTotalBonusWages(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getGrossBonusAmt(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getPaidBonusAmt(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getPaidBonusDate(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getIsBonussheetFinalized(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				bonusAmt = bonusAmt + prog.getTotalBonusWages();

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));

			document.add(new Paragraph("Bonus Title: " + bonus.getFyTitle()));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			document.add(new Paragraph("Total Bonus Amt: " + bonusAmt));

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p")); //
			System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) { // System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {
				bonusAmt = 0;

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Total Bonus Days");
				rowData.add("Bonus Wages");
				rowData.add("Gross Bonus Amt");
				rowData.add("Paid Bonus Amt");
				rowData.add("Paid Bonus Date");
				rowData.add("Is Bonus Sheet finalized");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				bonusAmt=0;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getCompanyEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getTotalBonusDays());
					rowData.add("" + progList.get(i).getTotalBonusWages());
					rowData.add("" + progList.get(i).getGrossBonusAmt());
					rowData.add("" + progList.get(i).getPaidBonusAmt());
					rowData.add("" + progList.get(i).getPaidBonusDate());
					rowData.add("" + progList.get(i).getIsBonussheetFinalized());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					bonusAmt = bonusAmt + progList.get(i).getTotalBonusWages();
				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Bonus Title:" + bonus.getFyTitle(),
							"Total Bonus Amt:" + bonusAmt, 'I');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showExgratiaRep", method = RequestMethod.GET)
	public void showExgratiaRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee Bonus Details";
		String bonusId = request.getParameter("bonusId");

		double bonusAmt = 0;

		Boolean ret = false;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map = new LinkedMultiValueMap<String, Object>();
			map.add("bonusId", bonusId);

			BonusMaster bonus = Constants.getRestTemplate().postForObject(Constants.url + "/getBonusById", map,
					BonusMaster.class);

			BonusCalc[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getBonusReportByBonusId",
					map, BonusCalc[].class);
			List<BonusCalc> progList = new ArrayList<>(Arrays.asList(resArray));

			System.err.println("progList" + progList.toString());

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(9);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 7.0f, 3.5f, 2f, 3.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;
			new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont;

			new Font(FontFamily.HELVETICA, 12, Font.BOLD,

					BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total Exgratia Days ", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Exgratia Wages", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Gross Exgratia Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Paid Exgratia Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Paid Exgratia Date", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Is Exgratia Sheet finalized", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) { //
				System.err.println("I " + i);
				BonusCalc prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getCompanyEmpCode(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getTotalExgretiaDays(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getTotalExgretiaWages(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getGrossExgretiaAmt(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getPaidExgretiaAmt(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getPaidExgretiaDate(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getIsExgretiaFinalized(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				bonusAmt = bonusAmt + Double.parseDouble(prog.getTotalExgretiaWages());

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));

			document.add(new Paragraph("Bonus Title: " + bonus.getFyTitle()));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			document.add(new Paragraph("Total Exgratia Amt: " + bonusAmt));

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p")); //
			System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) { // System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {
				bonusAmt = 0;

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Total Exgratia Days");
				rowData.add("Exgratia Wages");
				rowData.add("Gross Exgratia Amt");
				rowData.add("Paid Exgratia Amt");
				rowData.add("Paid Exgratia Date");
				rowData.add("Is Exgratia Finalized");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getCompanyEmpCode());
					rowData.add("" + progList.get(i).getEmpName());
					rowData.add("" + progList.get(i).getTotalExgretiaDays());
					rowData.add("" + progList.get(i).getTotalExgretiaWages());
					rowData.add("" + progList.get(i).getGrossExgretiaAmt());
					rowData.add("" + progList.get(i).getPaidExgretiaAmt());
					rowData.add("" + progList.get(i).getPaidExgretiaDate());
					rowData.add("" + progList.get(i).getIsExgretiaFinalized());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					bonusAmt = bonusAmt + Double.parseDouble(progList.get(i).getTotalExgretiaWages());
				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Exgratia Title:" + bonus.getFyTitle(), "Total Exgratia Amt:" + bonusAmt, 'I');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showEsiSummaryRep", method = RequestMethod.GET)
	public void showEsiSummaryRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "ESI Statement";
		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);
		int cmpId = 0;
		try {
			cmpId = Integer.parseInt(request.getParameter("subCmpId"));

		} catch (Exception e) {
			cmpId = 0;
		}

		System.err.println("cmpId" + cmpId);

		String cmpName = "-";
		Boolean ret = false;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			if (cmpId != 0) {
				map.add("companyId", cmpId);
				MstCompanySub comp = Constants.getRestTemplate().postForObject(Constants.url + "/getSubCompanyById",
						map, MstCompanySub.class);
				cmpName = comp.getCompanyName();
			}

			map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", cmpId);
			map.add("fromDate", arrOfStr[0]);
			map.add("toDate", arrOfStr[1]);
			EsiSumaryRep[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getEsiSummaryReport",
					map, EsiSumaryRep[].class);
			List<EsiSumaryRep> progList = new ArrayList<>(Arrays.asList(resArray));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 6.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Count", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Month-Year", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee's Contribution", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employeer's Contribution", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				EsiSumaryRep prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getNoEmp(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getCalcMonth() + "-" + prog.getCalcYear(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpContribution(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmperContribution(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getTotalContribution(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Date Range: " + leaveDateRange));
			document.add(new Paragraph("Company Name: " + cmpName));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Employee Count");
				rowData.add("Month-Year");
				rowData.add("Employee's Contribution");
				rowData.add("Employeer's Contribution");
				rowData.add("Total");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getNoEmp());
					rowData.add("" + progList.get(i).getCalcMonth() + "-" + progList.get(i).getCalcYear());
					rowData.add("" + progList.get(i).getEmpContribution());
					rowData.add("" + progList.get(i).getEmperContribution());
					rowData.add("" + progList.get(i).getTotalContribution());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date Range:" + leaveDateRange + " Company Name:" + cmpName, "", 'F');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

//leave 

	@RequestMapping(value = "/showEmpLeaveHistoryRepNew", method = RequestMethod.GET)
	public void showEmpLeaveHistoryRep(HttpServletRequest request, HttpServletResponse response) {
		List<EmpLeaveHistoryRep> progList = new ArrayList<EmpLeaveHistoryRep>();
		String reportName = "Employee Leave Pending Report";
		List<EmpLeaveHistoryRep> employeeInfoList = new ArrayList<EmpLeaveHistoryRep>();
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		try {

			int empId = Integer.parseInt(request.getParameter("empId1"));
			int calYrId = Integer.parseInt(request.getParameter("calYrId"));
			String cal_yr = request.getParameter("cal_yr");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("calYrId", calYrId);

			EmpLeaveHistoryRep[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLeaveHistoryRep", map, EmpLeaveHistoryRep[].class);

			employeeInfoList = new ArrayList<EmpLeaveHistoryRep>(Arrays.asList(employeeInfo));
			// System.out.println("employeeInfoList" + employeeInfoList.toString());

			map = new LinkedMultiValueMap<>();

			EmployeeMaster[] emp = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getEmplistForAssignAuthorityAll", EmployeeMaster[].class);

			List<EmployeeMaster> empList1 = new ArrayList<EmployeeMaster>(Arrays.asList(emp));

			List<LeaveHistTemp> finalList = new ArrayList<LeaveHistTemp>();

			for (int i = 0; i < empList1.size(); i++) {
				LeaveHistTemp fin = new LeaveHistTemp();
				fin.setEmpName(empList1.get(i).getFirstName().concat(" ").concat(empList1.get(i).getSurname()));

				List<EmpLeaveHistoryRep> subList = new ArrayList<EmpLeaveHistoryRep>();
				for (int j = 0; j < employeeInfoList.size(); j++) {

					if (empList1.get(i).getEmpId() == employeeInfoList.get(j).getEmpId()) {
						subList.add(employeeInfoList.get(j));
					}

				}
				fin.setRec(subList);
				finalList.add(fin);
			}

			// System.out.println("final ****" + finalList.toString());
			Document document = new Document(PageSize.A4);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(8);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.0f, 5.0f, 4.5f, 4.5f, 4.5f, 4.5f, 4.5f, 4.5f });
				Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
				// BaseColor.BLACK);
				Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																		// BaseColor.BLACK);
				tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Leave Type", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Carry Forward", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Earned", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Approved", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Applied", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Balanced", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < finalList.size(); i++) {
					// System.err.println("I " + i);
					LeaveHistTemp prog = finalList.get(i);

					for (int j = 0; j < prog.getRec().size(); j++) {
						index++;
						PdfPCell cell;
						cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);

						table.addCell(cell);

						if (j == 0) {
							cell = new PdfPCell(new Phrase("" + prog.getEmpName(), headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);

							table.addCell(cell);
						} else {
							cell = new PdfPCell(new Phrase("", headFontData));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);

							table.addCell(cell);
						}

						cell = new PdfPCell(new Phrase("" + prog.getRec().get(j).getLvTitle(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + prog.getRec().get(j).getBalLeave(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + prog.getRec().get(j).getLvsAllotedLeaves(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + prog.getRec().get(j).getSactionLeave(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + prog.getRec().get(j).getAplliedLeaeve(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

						table.addCell(cell);

						float x = prog.getRec().get(j).getBalLeave() + prog.getRec().get(j).getLvsAllotedLeaves()
								- prog.getRec().get(j).getSactionLeave() - prog.getRec().get(j).getAplliedLeaeve();

						cell = new PdfPCell(new Phrase("" + x, headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

						table.addCell(cell);

					}

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));

				document.add(new Paragraph("Year:" + cal_yr));
				document.add(new Paragraph("\n"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

				document.add(table);

				int totalPages = writer.getPageNumber();

				// System.out.println("Page no " + totalPages);

				document.close();
				int p = Integer.parseInt(request.getParameter("p"));
				// System.err.println("p " + p);

				if (p == 1) {

					if (file != null) {

						String mimeType = URLConnection.guessContentTypeFromName(file.getName());

						if (mimeType == null) {

							mimeType = "application/pdf";

						}

						response.setContentType(mimeType);

						response.addHeader("content-disposition",
								String.format("inline; filename=\"%s\"", file.getName()));

						response.setContentLength((int) file.length());

						InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

						try {
							FileCopyUtils.copy(inputStream, response.getOutputStream());
						} catch (IOException e) {
							// System.out.println("Excep in Opening a Pdf File");
							e.printStackTrace();
						}
					}
				} else {

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Employee Name");
					rowData.add("Leave Type");
					rowData.add("Carry Forward");
					rowData.add("Earned");
					rowData.add("Approved");
					rowData.add("Applied");
					rowData.add("Balanced");
					expoExcel.setRowData(rowData);

					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < finalList.size(); i++) {

						for (int j = 0; j < finalList.get(i).getRec().size(); j++) {
							expoExcel = new ExportToExcel();
							rowData = new ArrayList<String>();

							rowData.add("" + (cnt));
							if (j == 0) {
								rowData.add("" + finalList.get(i).getEmpName());

							} else {
								rowData.add("");
							}

							rowData.add("" + finalList.get(i).getRec().get(j).getLvTitle());
							rowData.add("" + finalList.get(i).getRec().get(j).getBalLeave());
							rowData.add("" + finalList.get(i).getRec().get(j).getLvsAllotedLeaves());
							rowData.add("" + finalList.get(i).getRec().get(j).getSactionLeave());
							rowData.add("" + finalList.get(i).getRec().get(j).getAplliedLeaeve());
							float a = finalList.get(i).getRec().get(j).getBalLeave()
									+ finalList.get(i).getRec().get(j).getLvsAllotedLeaves()
									- finalList.get(i).getRec().get(j).getSactionLeave()
									- finalList.get(i).getRec().get(j).getAplliedLeaeve();

							rowData.add("" + a);

							expoExcel.setRowData(rowData);
							cnt = cnt + 1;
							exportToExcelList.add(expoExcel);
						}

					}

					XSSFWorkbook wb = null;
					try {
						System.out.println("exportToExcelList" + exportToExcelList.toString());

						wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " Date:" + cal_yr + "", "",
								'H');

						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						response.setHeader("Content-disposition",
								"attachment; filename=" + reportName + "-" + date + ".xlsx");
						wb.write(response.getOutputStream());

					} catch (IOException ioe) {
						throw new RuntimeException("Error writing spreadsheet to output stream");
					} finally {
						if (wb != null) {
							wb.close();
						}
					}

				}

			} catch (DocumentException ex) {

				// System.out.println("Pdf Generation Error: " + ex.getMessage());

				ex.printStackTrace();

			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showEmpLeaveApplicationRep", method = RequestMethod.GET)
	public void showEmpLeaveApplicationRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee Leave Application Tracking";
		int empId = Integer.parseInt(request.getParameter("empId1"));
		int calYrId = Integer.parseInt(request.getParameter("calYrId"));
		String cal_yr = request.getParameter("cal_yr");

		Boolean ret = false;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);

			EmployeeMaster emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeById", map,
					EmployeeMaster.class);

			map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("calYrId", calYrId);

			LeaveApply[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLeaveApplicationEmpReport", map, LeaveApply[].class);

			List<LeaveApply> progList = new ArrayList<LeaveApply>(Arrays.asList(employeeInfo));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 3.0f, 2.5f, 6.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("From Date", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("To Date ", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("No of Days", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Status", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {

				// System.err.println("I " + i);
				LeaveApply prog = progList.get(i);
				int stat = progList.get(i).getFinalStatus();
				String status = new String();
				if (stat == 1) {
					status = " applied & pending for approval";

				} else if (stat == 7) {
					status = "Cancelled by employee";
				} else if (stat == 2) {
					status = "Approved By  Intial Authority";
				} else if (stat == 8) {
					status = "Rejected By  Intial Authority";
				} else if (stat == 3) {
					status = "Approved By  Final Authority";
				} else {
					status = "Rejected By  Final Authority";
				}

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLeaveFromdt(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLeaveTodt(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLeaveNumDays(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + status, headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph(
					"Employee Name: " + emp.getFirstName() + " " + emp.getMiddleName() + " " + emp.getSurname()));
			document.add(new Paragraph("Calender Year: " + cal_yr));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);

			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("From Date");
				rowData.add("To Date");
				rowData.add("No of Day");
				rowData.add("Status");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				for (int i = 0; i < progList.size(); i++) {

					int stat = progList.get(i).getFinalStatus();
					String status = new String();
					if (stat == 1) {
						status = " applied & pending for approval";

					} else if (stat == 7) {
						status = "Cancelled by employee";
					} else if (stat == 2) {
						status = "Approved By  Intial Authority";
					} else if (stat == 8) {
						status = "Rejected By  Intial Authority";
					} else if (stat == 3) {
						status = "Approved By  Final Authority";
					} else {
						status = "Rejected By  Final Authority";
					}

					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getLeaveFromdt());
					rowData.add("" + progList.get(i).getLeaveTodt());
					rowData.add("" + progList.get(i).getLeaveNumDays());
					rowData.add("" + status);

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Cal Year:" + cal_yr + "Emp Name"
							+ emp.getFirstName() + " " + emp.getMiddleName() + " " + emp.getSurname(), "", 'E');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/showEmpDedRep", method = RequestMethod.GET)
	public void showEmpDedRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee Payment Deduction";

 		Boolean ret = false;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			int dedTot=0;

			int empId =0;
			try {
				empId = Integer.parseInt(request.getParameter("empId1"));

			} catch (Exception e) {
				empId = 0;

			}
			 
			
			String fromDate = request.getParameter("datepickerFromRep");
			String toDate = request.getParameter("datepickerToRep");

			map = new LinkedMultiValueMap<>();
			map.add("empId", empId);

			EmployeeMaster emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeById", map,
					EmployeeMaster.class);

			map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("flag", 0);

			PayDeductionDetails[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpPayDed", map, PayDeductionDetails[].class);

			List<PayDeductionDetails> progList = new ArrayList<PayDeductionDetails>(Arrays.asList(employeeInfo));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(8);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 6.5f, 3.5f, 3.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Deduction Month-Year", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Deduction Type", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Deduction Rate", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Deduction Total", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Deduction Remark", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				PayDeductionDetails prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);
				
				
				cell = new PdfPCell(new Phrase("" + prog.getMakerEnterDatetime(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getExVar1(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

			

				cell = new PdfPCell(new Phrase("" + prog.getMonth() + "-" + prog.getYear(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getExVar2(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDedRate(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDedTotal(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDedRemark(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);
				
				dedTot=dedTot+prog.getDedTotal();

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph(
					"Employee Name: " + emp.getFirstName() + " " + emp.getMiddleName() + " " + emp.getSurname()));
			document.add(new Paragraph("Year: " + fromDate +" to "+ toDate));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);
			
			
			document.add(new Paragraph("Total Deduction Amt: " + dedTot));


			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Deduction Month-Year");
				rowData.add("Deduction Type");
				rowData.add("Deduction Rate");
				rowData.add("Deduction Total");
				rowData.add("Deduction Remark");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				  dedTot=0; 
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getMakerEnterDatetime());
					rowData.add("" + progList.get(i).getExVar1());
					rowData.add("" + progList.get(i).getMonth() + "-" + progList.get(i).getYear());

					rowData.add("" + progList.get(i).getExVar2());
					rowData.add("" + progList.get(i).getDedRate());
					rowData.add("" + progList.get(i).getDedTotal());
					rowData.add("" + progList.get(i).getDedRemark());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					dedTot=dedTot+ progList.get(i).getDedTotal();
 				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " Year:" + fromDate +" to "+ toDate + "Emp Name"
							+ emp.getFirstName() + " " + emp.getMiddleName() + " " + emp.getSurname(), "Total Deduction Amt: " + dedTot, 'H');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}
	
	
	
	@RequestMapping(value = "/showEmpDedRepAll", method = RequestMethod.GET)
	public void showEmpDedRepAll(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employee Payment Deduction";

 		Boolean ret = false;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			int dedTot=0; 
			String leaveDateRange = request.getParameter("leaveDateRange");
			String[] arrOfStr = leaveDateRange.split("to", 2);
		 
			map = new LinkedMultiValueMap<>();
			map.add("empId", 0);
			map.add("fromDate", arrOfStr[0]);
			map.add("toDate", arrOfStr[1]);
			map.add("flag", 1);

			PayDeductionDetails[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpPayDed", map, PayDeductionDetails[].class);

			List<PayDeductionDetails> progList = new ArrayList<PayDeductionDetails>(Arrays.asList(employeeInfo));

			String header = "";
			String title = "                 ";
			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			Document document = new Document(PageSize._11X17);
			document.setMargins(5, 5, 0, 0);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(8);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 6.5f, 3.5f, 3.5f, 3.5f, 3.5f, 3.5f });
			Font headFontData = ReportCostants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			// BaseColor.BLACK);
			Font tableHeaderFont = ReportCostants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
			tableHeaderFont.setColor(ReportCostants.tableHeaderFontBaseColor);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Code", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Deduction Month-Year", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Deduction Type", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Deduction Rate", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Deduction Total", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Deduction Remark", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < progList.size(); i++) {
				// System.err.println("I " + i);
				PayDeductionDetails prog = progList.get(i);

				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);
				
				
				cell = new PdfPCell(new Phrase("" + prog.getMakerEnterDatetime(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getExVar1(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

			

				cell = new PdfPCell(new Phrase("" + prog.getMonth() + "-" + prog.getYear(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getExVar2(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDedRate(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDedTotal(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDedRemark(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);
				
				dedTot=dedTot+prog.getDedTotal();

			}

			document.open();
			Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

			Paragraph name = new Paragraph(reportName, hf);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph(
					"" ));
			document.add(new Paragraph("Year: " + leaveDateRange));

			document.add(new Paragraph("\n"));
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(table);
			
			
			document.add(new Paragraph("Total Deduction Amt: " + dedTot));


			int totalPages = writer.getPageNumber();

			// System.out.println("Page no " + totalPages);

			document.close();
			int p = Integer.parseInt(request.getParameter("p"));
			// System.err.println("p " + p);

			if (p == 1) {

				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						// System.out.println("Excep in Opening a Pdf File");
						e.printStackTrace();
					}
				}
			} else {

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Emp Code");
				rowData.add("Emp Name");
				rowData.add("Deduction Month-Year");
				rowData.add("Deduction Type");
				rowData.add("Deduction Rate");
				rowData.add("Deduction Total");
				rowData.add("Deduction Remark");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				int cnt = 1;
				  dedTot=0; 
				for (int i = 0; i < progList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;

					rowData.add("" + (i + 1));
					rowData.add("" + progList.get(i).getMakerEnterDatetime());
					rowData.add("" + progList.get(i).getExVar1());
					rowData.add("" + progList.get(i).getMonth() + "-" + progList.get(i).getYear());

					rowData.add("" + progList.get(i).getExVar2());
					rowData.add("" + progList.get(i).getDedRate());
					rowData.add("" + progList.get(i).getDedTotal());
					rowData.add("" + progList.get(i).getDedRemark());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					dedTot=dedTot+ progList.get(i).getDedTotal();
 				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " Year:" + leaveDateRange, "Total Deduction Amt: " + dedTot, 'H');

					ExceUtil.autoSizeColumns(wb, 3);
					response.setContentType("application/vnd.ms-excel");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					response.setHeader("Content-disposition",
							"attachment; filename=" + reportName + "-" + date + ".xlsx");
					wb.write(response.getOutputStream());

				} catch (IOException ioe) {
					throw new RuntimeException("Error writing spreadsheet to output stream");
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}
	}

}
