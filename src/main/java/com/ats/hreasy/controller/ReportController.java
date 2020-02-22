package com.ats.hreasy.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Month;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.ExceUtil;
import com.ats.hreasy.common.ExportToExcel;
import com.ats.hreasy.common.ItextPageEvent;
import com.ats.hreasy.common.ReportCostants;
import com.ats.hreasy.model.DailyAttendance;
import com.ats.hreasy.model.graph.EmpDailyAttendanceGraph;
import com.ats.hreasy.model.graph.EmpDefaultSalaryGraph;
import com.ats.hreasy.model.report.EmpLateMarkDetails;
import com.ats.hreasy.model.report.EmpOtReg;
import com.ats.hreasy.model.report.LoanStatementDetailsReport;
import com.ats.hreasy.model.report.PendingLoanReport;
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
public class ReportController {
	

	@RequestMapping(value = "/showPendingLoanRep", method = RequestMethod.GET)
	public void showEmpLateMarkRep(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Pending Loan Details";

		HttpSession session = request.getSession();

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("fromDate", DateConvertor.convertToYMD(arrOfStr[0]));
			map.add("toDate", DateConvertor.convertToYMD(arrOfStr[1]));
			
			PendingLoanReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpPendingLoanReport", map, PendingLoanReport[].class);
			List<PendingLoanReport> empLoanList = new ArrayList<>(Arrays.asList(resArray));
			System.err.println("data:" + empLoanList.toString());
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
			table.setWidths(new float[] { 1.5f, 2.2f, 4.0f, 2.5f, 4.0f, 2.0f, 2.0f, 2.0f , 2.0f});
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

			hcell = new PdfPCell(new Phrase("Department", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Designation", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Amount", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("EMI", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Paid Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Pending Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < empLoanList.size(); i++) {

				PendingLoanReport prog = empLoanList.get(i);

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

				cell = new PdfPCell(new Phrase("" + prog.getFirstName()+" "+prog.getMiddleName()+" "+prog.getSurname(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDepatarment(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getDesignation(), headFontData));
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

				cell = new PdfPCell(new Phrase("" + prog.getCurrentTotpaid(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + prog.getCurrentOutstanding(), headFontData));
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
			document.add(new Paragraph("Date : " + arrOfStr[0] + "To" + arrOfStr[1]));

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
				rowData.add("Department");
				rowData.add("Designation");
				rowData.add("Amount");
				rowData.add("EMI");
				rowData.add("Paid Amt");
				rowData.add("Pending Amt");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				
				float ttlLoanAmt = 0;
				float ttlEmiAmt = 0;
				float ttlPaid = 0;
				float ttlPending = 0;
				int cnt = 1;
				for (int i = 0; i < empLoanList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;
					rowData.add("" + (i + 1));
					rowData.add("" + empLoanList.get(i).getEmpCode());
					rowData.add("" + empLoanList.get(i).getFirstName()+" "+empLoanList.get(i).getMiddleName()+" "+empLoanList.get(i).getSurname()  );
					rowData.add("" + empLoanList.get(i).getDepatarment());
					rowData.add("" + empLoanList.get(i).getDesignation());
					rowData.add("" + empLoanList.get(i).getLoanAmt());
					rowData.add("" + empLoanList.get(i).getLoanEmi());
					rowData.add("" + empLoanList.get(i).getCurrentTotpaid());
					rowData.add("" + empLoanList.get(i).getCurrentOutstanding());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					ttlLoanAmt = ttlLoanAmt+empLoanList.get(i).getLoanAmt();
					ttlEmiAmt = ttlEmiAmt+empLoanList.get(i).getLoanEmi();
					ttlPaid = ttlPaid+empLoanList.get(i).getCurrentTotpaid();
					ttlPending = ttlPending+empLoanList.get(i).getCurrentOutstanding();
				}
				
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				rowData.add("" +"Total");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + ttlLoanAmt);
				rowData.add("" + ttlEmiAmt);
				rowData.add("" + ttlPaid);
				rowData.add("" + ttlPending);
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

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

			System.err.println("Exce in showPendingLoanRep " + e.getMessage());
			e.printStackTrace();

		}
	}
	
	/**************************************Loan Statement Details**********************************/
	@RequestMapping(value = "/showLoanStatement", method = RequestMethod.GET)
	public void showLoanStatement(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Loan Statement Details";

		HttpSession session = request.getSession();

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("fromDate", DateConvertor.convertToYMD(arrOfStr[0]));
			map.add("toDate", DateConvertor.convertToYMD(arrOfStr[1]));
			
			LoanStatementDetailsReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLoanStatemnetReport", map, LoanStatementDetailsReport[].class);
			List<LoanStatementDetailsReport> empLoanList = new ArrayList<LoanStatementDetailsReport>(Arrays.asList(resArray));
			System.err.println("data:" + empLoanList.toString());
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
			table.setWidths(new float[] { 1.5f, 2.2f, 4.0f, 2.5f, 4.0f, 2.0f, 2.0f, 2.0f , 2.0f});
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

			hcell = new PdfPCell(new Phrase("Application No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Loan Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Paid Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Pending Amt", tableHeaderFont));
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

			int index = 0;
			for (int i = 0; i < empLoanList.size(); i++) {

				LoanStatementDetailsReport prog = empLoanList.get(i);

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

				cell = new PdfPCell(new Phrase("" + prog.getLoanApplNo(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanAmt(), headFontData));
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

				cell = new PdfPCell(new Phrase("" + prog.getLoanEmiIntrest(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + prog.getLoanEmiIntrest(), headFontData));
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
			document.add(new Paragraph("Date : " + arrOfStr[0] + "To" + arrOfStr[1]));

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
				rowData.add("Application No.");
				rowData.add("Loan Amt");
				rowData.add("Paid Amt");
				rowData.add("Pending Amt");
				rowData.add("Loan EMI");
				rowData.add("Loan EMI Interest");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				
				float ttlLoanAmt = 0;
				float ttlEmiAmt = 0;
				float ttlPaid = 0;
				float ttlPending = 0;
				float ttlEmiInterest = 0;
				int cnt = 1;
				for (int i = 0; i < empLoanList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;
					rowData.add("" + (i + 1));
					rowData.add("" + empLoanList.get(i).getEmpCode());
					rowData.add("" + empLoanList.get(i).getEmpName());
					rowData.add("" + empLoanList.get(i).getLoanApplNo());
					rowData.add("" + empLoanList.get(i).getLoanAmt());
					rowData.add("" + empLoanList.get(i).getCurrentTotpaid());
					rowData.add("" + empLoanList.get(i).getCurrentOutstanding());
					rowData.add("" + empLoanList.get(i).getLoanEmi());
					rowData.add("" + empLoanList.get(i).getLoanEmiIntrest());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					ttlLoanAmt = ttlLoanAmt+empLoanList.get(i).getLoanAmt();
					ttlEmiAmt = ttlEmiAmt+empLoanList.get(i).getLoanEmi();
					ttlPaid = ttlPaid+empLoanList.get(i).getCurrentTotpaid();
					ttlPending = ttlPending+empLoanList.get(i).getCurrentOutstanding();
					ttlEmiInterest = ttlEmiInterest+empLoanList.get(i).getLoanEmiIntrest();
				}
				
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				rowData.add("" +"Total");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + ttlLoanAmt);
				rowData.add("" + ttlPaid);				
				rowData.add("" + ttlPending);
				rowData.add("" + ttlEmiAmt);
				rowData.add("" + ttlEmiInterest);
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

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

			System.err.println("Exce in showPendingLoanRep " + e.getMessage());
			e.printStackTrace();

		}
	}
	
	/****************************Employee Over Time Register Report***********************/
	@RequestMapping(value = "/showEmpOtReg", method = RequestMethod.GET)
	public void showEmpOtReg(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employees Overtime Register";

		HttpSession session = request.getSession();

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("fromDate", DateConvertor.convertToYMD(arrOfStr[0]));
			map.add("toDate", DateConvertor.convertToYMD(arrOfStr[1]));
			
			EmpOtReg[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpOtRegDetails", map, EmpOtReg[].class);
			List<EmpOtReg> empOtList = new ArrayList<EmpOtReg>(Arrays.asList(resArray));
			System.err.println("data:" + empOtList.toString());
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
			table.setWidths(new float[] { 1.5f, 2.2f, 4.0f, 2.5f, 2.0f, 2.0f});
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

			hcell = new PdfPCell(new Phrase("Designation", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Month", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
		
			hcell = new PdfPCell(new Phrase("OT Hours", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			
			

			int index = 0;
			for (int i = 0; i < empOtList.size(); i++) {

				EmpOtReg prog = empOtList.get(i);

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

				cell = new PdfPCell(new Phrase("" + prog.getDesignation(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + prog.getMonth(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + prog.getOtHr(), headFontData));
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
			document.add(new Paragraph("Date : " + arrOfStr[0] + "To" + arrOfStr[1]));

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
				rowData.add("Designation");
				rowData.add("Month");
				rowData.add("OT Hours");
				

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);				
				
				float ttlOtHrs = 0;
				
				int cnt = 1;
				for (int i = 0; i < empOtList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;
					rowData.add("" + (i + 1));
					rowData.add("" + empOtList.get(i).getEmpCode());
					rowData.add("" + empOtList.get(i).getEmpName());
					rowData.add("" + empOtList.get(i).getDesignation());
					rowData.add("" + empOtList.get(i).getMonth());
					rowData.add("" + empOtList.get(i).getOtHr());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					
					ttlOtHrs = ttlOtHrs+empOtList.get(i).getOtHr();
				}
				
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				rowData.add("" +"Total");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + ttlOtHrs);
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

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

			System.err.println("Exce in showEmpOtReg " + e.getMessage());
			e.printStackTrace();

		}
	}
	
	
	/****************************Employee Lare Mark Report***********************/
	@RequestMapping(value = "/showEmpLateMark", method = RequestMethod.GET)
	public void showEmpLateMark(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employees Late Mark";

		HttpSession session = request.getSession();

		String leaveDateRange = request.getParameter("leaveDateRange");
		String[] arrOfStr = leaveDateRange.split("to", 2);

		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("companyId", 1);
			map.add("fromDate", DateConvertor.convertToYMD(arrOfStr[0]));
			map.add("toDate", DateConvertor.convertToYMD(arrOfStr[1]));
			
			EmpLateMarkDetails[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpLateMarkDetails", map, EmpLateMarkDetails[].class);
			List<EmpLateMarkDetails> empOtList = new ArrayList<EmpLateMarkDetails>(Arrays.asList(resArray));
			System.err.println("data:" + empOtList.toString());
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
			table.setWidths(new float[] { 1.5f, 2.2f, 4.0f, 2.5f, 2.0f, 2.0f});
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

			hcell = new PdfPCell(new Phrase("Designation", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Month", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
		
			hcell = new PdfPCell(new Phrase("Late Hours", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			
			

			int index = 0;
			for (int i = 0; i < empOtList.size(); i++) {

				EmpLateMarkDetails prog = empOtList.get(i);

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

				cell = new PdfPCell(new Phrase("" + prog.getDesignation(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + prog.getMonth(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + prog.getLateHr(), headFontData));
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
			document.add(new Paragraph("Date : " + arrOfStr[0] + "To" + arrOfStr[1]));

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
				rowData.add("Designation");
				rowData.add("Month");
				rowData.add("Late Hours");
				

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);				
				
				float ttlLateHrs = 0;
				
				int cnt = 1;
				for (int i = 0; i < empOtList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;
					rowData.add("" + (i + 1));
					rowData.add("" + empOtList.get(i).getEmpCode());
					rowData.add("" + empOtList.get(i).getEmpName());
					rowData.add("" + empOtList.get(i).getDesignation());
					rowData.add("" + empOtList.get(i).getMonth());
					rowData.add("" + empOtList.get(i).getLateHr());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					
					ttlLateHrs = ttlLateHrs+empOtList.get(i).getLateHr();
				}
				
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				rowData.add("" +"Total");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + ttlLateHrs);
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

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

			System.err.println("Exce in showEmpOtReg " + e.getMessage());
			e.printStackTrace();

		}
	}
	
	
	@RequestMapping(value = "/showEmpLateMarkByEmpId", method = RequestMethod.GET)
	public void showEmpLateMarkByEmpId(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Employees Late Mark";

		HttpSession session = request.getSession();
		int empId = 0;
		try {
			empId = Integer.parseInt(request.getParameter("empId1"));

		} catch (Exception e) {
			empId = 0;

		}
		String fromDate = request.getParameter("datepickerFromRep");
		String toDate = request.getParameter("datepickerToRep");
		//System.out.println("Dates---------------"+fromDate+" "+toDate);
		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("empId", empId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			
			EmpLateMarkDetails[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpLateMarkDetailsByEmpId", map, EmpLateMarkDetails[].class);
			List<EmpLateMarkDetails> empOtList = new ArrayList<EmpLateMarkDetails>(Arrays.asList(resArray));
			System.err.println("data:" + empOtList.toString());
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
			table.setWidths(new float[] { 1.5f, 2.2f, 4.0f, 2.5f, 2.0f, 2.0f});
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

			hcell = new PdfPCell(new Phrase("Designation", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Month", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
		
			hcell = new PdfPCell(new Phrase("Late Hours", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);
			
			

			int index = 0;
			for (int i = 0; i < empOtList.size(); i++) {

				EmpLateMarkDetails prog = empOtList.get(i);

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

				cell = new PdfPCell(new Phrase("" + prog.getDesignation(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + prog.getMonth(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + prog.getLateHr(), headFontData));
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
			document.add(new Paragraph("Date : " + fromDate + "To " + toDate));

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
				rowData.add("Designation");
				rowData.add("Month");
				rowData.add("Late Hours");
				

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);				
				
				float ttlLateHrs = 0;
				
				int cnt = 1;
				for (int i = 0; i < empOtList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;
					rowData.add("" + (i + 1));
					rowData.add("" + empOtList.get(i).getEmpCode());
					rowData.add("" + empOtList.get(i).getEmpName());
					rowData.add("" + empOtList.get(i).getDesignation());
					rowData.add("" + empOtList.get(i).getMonth());
					rowData.add("" + empOtList.get(i).getLateHr());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					
					ttlLateHrs = ttlLateHrs+empOtList.get(i).getLateHr();
				}
				
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				rowData.add("" +"Total");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + ttlLateHrs);
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date:" + fromDate + "To " + toDate, "", 'H');

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

			System.err.println("Exce in showEmpOtReg " + e.getMessage());
			e.printStackTrace();

		}
	}
	
	/**************************************Loan Statement Details By EmpId**********************************/
	@RequestMapping(value = "/showEmpLoanStatDetailByEmp", method = RequestMethod.GET)
	public void showEmpLoanStatDetailByEmp(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Loan Statement Details";

		HttpSession session = request.getSession();
		int empId = 0;
		try {
			empId = Integer.parseInt(request.getParameter("empId1"));

		} catch (Exception e) {
			empId = 0;

		}
		String fromDate = request.getParameter("datepickerFromRep");
		String toDate = request.getParameter("datepickerToRep");
		//System.out.println("Dates---------------"+fromDate+" "+toDate);
		Boolean ret = false;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("empId", empId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			
			LoanStatementDetailsReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLoanStatemnetReportByEmpId", map, LoanStatementDetailsReport[].class);
			List<LoanStatementDetailsReport> empLoanList = new ArrayList<LoanStatementDetailsReport>(Arrays.asList(resArray));
			System.err.println("data:" + empLoanList.toString());
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
			table.setWidths(new float[] { 1.5f, 2.2f, 4.0f, 2.5f, 4.0f, 2.0f, 2.0f, 2.0f , 2.0f});
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

			hcell = new PdfPCell(new Phrase("Application No.", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Loan Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Paid Amt", tableHeaderFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(ReportCostants.baseColorTableHeader);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Pending Amt", tableHeaderFont));
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

			int index = 0;
			for (int i = 0; i < empLoanList.size(); i++) {

				LoanStatementDetailsReport prog = empLoanList.get(i);

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

				cell = new PdfPCell(new Phrase("" + prog.getLoanApplNo(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + prog.getLoanAmt(), headFontData));
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

				cell = new PdfPCell(new Phrase("" + prog.getLoanEmiIntrest(), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + prog.getLoanEmiIntrest(), headFontData));
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
			document.add(new Paragraph("Date : " + fromDate + "To" + toDate));

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
				rowData.add("Application No.");
				rowData.add("Loan Amt");
				rowData.add("Paid Amt");
				rowData.add("Pending Amt");
				rowData.add("Loan EMI");
				rowData.add("Loan EMI Interest");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				
				float ttlLoanAmt = 0;
				float ttlEmiAmt = 0;
				float ttlPaid = 0;
				float ttlPending = 0;
				float ttlEmiInterest = 0;
				int cnt = 1;
				for (int i = 0; i < empLoanList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;
					rowData.add("" + (i + 1));
					rowData.add("" + empLoanList.get(i).getEmpCode());
					rowData.add("" + empLoanList.get(i).getEmpName());
					rowData.add("" + empLoanList.get(i).getLoanApplNo());
					rowData.add("" + empLoanList.get(i).getLoanAmt());
					rowData.add("" + empLoanList.get(i).getCurrentTotpaid());
					rowData.add("" + empLoanList.get(i).getCurrentOutstanding());
					rowData.add("" + empLoanList.get(i).getLoanEmi());
					rowData.add("" + empLoanList.get(i).getLoanEmiIntrest());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					ttlLoanAmt = ttlLoanAmt+empLoanList.get(i).getLoanAmt();
					ttlEmiAmt = ttlEmiAmt+empLoanList.get(i).getLoanEmi();
					ttlPaid = ttlPaid+empLoanList.get(i).getCurrentTotpaid();
					ttlPending = ttlPending+empLoanList.get(i).getCurrentOutstanding();
					ttlEmiInterest = ttlEmiInterest+empLoanList.get(i).getLoanEmiIntrest();
				}
				
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				rowData.add("" +"Total");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + "");
				rowData.add("" + ttlLoanAmt);
				rowData.add("" + ttlPaid);				
				rowData.add("" + ttlPending);
				rowData.add("" + ttlEmiAmt);
				rowData.add("" + ttlEmiInterest);
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

				XSSFWorkbook wb = null;
				try {

					wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
							"Date:" + fromDate + "To" + toDate, "", 'H');

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

			System.err.println("Exce in showPendingLoanRep " + e.getMessage());
			e.printStackTrace();

		}
	}
	/*****************************************************************************/
	@RequestMapping(value = "/getEmpDefaultSalGraph", method = RequestMethod.GET)
	public @ResponseBody List<EmpDefaultSalaryGraph>  getEmpAttnGraph(HttpServletRequest request,
			HttpServletResponse response) {

		List<EmpDefaultSalaryGraph> defSalList = new ArrayList<EmpDefaultSalaryGraph>();
		
		String fromDate = new String();
		String toDate = new String();
		HttpSession session = request.getSession();
		String[] shortMonths = new DateFormatSymbols().getShortMonths();
		try {
			int empId = Integer.parseInt(request.getParameter("empId"));

			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");

			// System.err.println("fromDate"+fromDate);
			// System.err.println("toDate"+toDate);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("companyId", 1);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);

			EmpDefaultSalaryGraph[] salArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getDefaultSalByEmpId", map, EmpDefaultSalaryGraph[].class);

			List<EmpDefaultSalaryGraph> salList = new ArrayList<EmpDefaultSalaryGraph>(Arrays.asList(salArr));
			
			for (int i = 0; i < salList.size(); i++) {
				EmpDefaultSalaryGraph emp = new EmpDefaultSalaryGraph();
				
				int mnth = salList.get(i).getMonth();
				int yr = salList.get(i).getYear();
				
				String c = Month.of(mnth).name();
				
				emp.setDate(shortMonths[mnth-1].concat("-").concat(String.valueOf(yr)));
				emp.setDefaultSalAmt(salList.get(i).getDefaultSalAmt());				
				defSalList.add(emp);
			}
			
			System.out.println("List-----------"+defSalList);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return defSalList;

	}
	
	
	@RequestMapping(value = "/getEmpGrossSalGraph", method = RequestMethod.GET)
	public @ResponseBody List<EmpDefaultSalaryGraph>  getEmpGrossSalGraph(HttpServletRequest request,
			HttpServletResponse response) {

		List<EmpDefaultSalaryGraph> defSalList = new ArrayList<EmpDefaultSalaryGraph>();
		
		String fromDate = new String();
		String toDate = new String();
		HttpSession session = request.getSession();
		String[] shortMonths = new DateFormatSymbols().getShortMonths();
		try {
			int empId = Integer.parseInt(request.getParameter("empId"));

			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");

			// System.err.println("fromDate"+fromDate);
			// System.err.println("toDate"+toDate);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("companyId", 1);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);

			EmpDefaultSalaryGraph[] salArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getGrossSalByEmpId", map, EmpDefaultSalaryGraph[].class);

			List<EmpDefaultSalaryGraph> salList = new ArrayList<EmpDefaultSalaryGraph>(Arrays.asList(salArr));
			
			for (int i = 0; i < salList.size(); i++) {
				EmpDefaultSalaryGraph emp = new EmpDefaultSalaryGraph();
				
				int mnth = salList.get(i).getMonth();
				int yr = salList.get(i).getYear();
				
				String c = Month.of(mnth).name();
				
				emp.setDate(shortMonths[mnth-1].concat("-").concat(String.valueOf(yr)));
				emp.setDefaultSalAmt(salList.get(i).getDefaultSalAmt());				
				defSalList.add(emp);
			}
			
			System.out.println("List-----------"+defSalList);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return defSalList;

	}
	
	
	
	
}
