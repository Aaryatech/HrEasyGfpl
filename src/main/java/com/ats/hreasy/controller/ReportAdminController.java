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
import com.ats.hreasy.model.EmpLeaveHistoryRep;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetDailyDailyRecord;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveHistTemp;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.Advance.GetAdvance;
import com.ats.hreasy.model.report.EmpAttendeanceRep;
import com.ats.hreasy.model.report.GetYearlyAdvance;
import com.ats.hreasy.model.report.GetYearlyAdvanceNew;
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
			document.add(new Paragraph("Month-Year" + month));

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

			PdfPTable table = new PdfPTable(9);

			table.setHeaderRows(1);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.0f, 3.0f, 5.5f, 3.0f, 3.0f, 3.0f, 3.0f, 3.5f, 3.5f });
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

			hcell = new PdfPCell(new Phrase("Prev Skipped By", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Prev Skip DateTime", tableHeaderFont));
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

				cell = new PdfPCell(new Phrase("" + prog.getSkipId(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getSkipRemarks(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDesignation(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getSkipLoginTime(), headFontData));
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
			document.add(new Paragraph("Month-Year" + month));

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
				rowData.add("Skip Count");
				rowData.add("Skip Remarks");
				rowData.add("Prev Skipped By ");
				rowData.add("Prev Skipped DateTime ");

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
					rowData.add("" + progList.get(i).getSkipId());
					rowData.add("" + progList.get(i).getSkipRemarks());

					rowData.add("" + progList.get(i).getDesignation());

					rowData.add("" + progList.get(i).getSkipLoginTime());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Month-Year:" + month, "", 'I');

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
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getFebCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getMarchCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getMayCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getMarchCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getJunCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getJulCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getAugCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getSepCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + prog.getOctCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getNovCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDecCount(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getTotal(), headFontData));
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
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getEmpPresent(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getPaidLeave(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getUnpaidLeave(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getWeekOff(), headFontData));
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

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Date:" + arrOfStr[0]+"To"+arrOfStr[1], "", 'D');

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
