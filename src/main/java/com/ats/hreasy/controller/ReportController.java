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

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.ExceUtil;
import com.ats.hreasy.common.ExportToExcel;
import com.ats.hreasy.common.ItextPageEvent;
import com.ats.hreasy.common.ReportCostants;
import com.ats.hreasy.model.DailyAttendance;
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
}
