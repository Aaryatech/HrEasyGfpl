/*
 * package com.ats.hreasy.controller;
 * 
 * import java.io.BufferedInputStream;
 * 
 * import java.io.File; import java.io.FileInputStream; import
 * java.io.FileOutputStream; import java.io.IOException; import
 * java.io.InputStream; import java.net.URLConnection; import
 * java.text.DateFormat; import java.text.SimpleDateFormat; import
 * java.util.ArrayList; import java.util.Arrays; import java.util.Date; import
 * java.util.List;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse; import
 * javax.servlet.http.HttpSession;
 * 
 * import org.apache.poi.xssf.usermodel.XSSFWorkbook; import
 * org.springframework.context.annotation.Scope; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import org.springframework.util.FileCopyUtils;
 * import org.springframework.util.LinkedMultiValueMap; import
 * org.springframework.util.MultiValueMap; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.servlet.ModelAndView;
 * 
 * import com.ats.hreasy.model.claim.ClaimType; import
 * com.itextpdf.text.BaseColor; import com.itextpdf.text.Document; import
 * com.itextpdf.text.DocumentException; import com.itextpdf.text.Element; import
 * com.itextpdf.text.Font; import com.itextpdf.text.PageSize; import
 * com.itextpdf.text.Paragraph; import com.itextpdf.text.Phrase; import
 * com.itextpdf.text.Font.FontFamily; import com.itextpdf.text.pdf.PdfPCell;
 * import com.itextpdf.text.pdf.PdfPTable; import
 * com.itextpdf.text.pdf.PdfWriter;
 * 
 * @Controller
 * 
 * @Scope("session") public class ClaimReportController {
 * 
 * List<ClaimType> claimTypelist = new ArrayList<>(); List<EmployeeWithClaim>
 * list = new ArrayList<>(); String date; String date1; List<EmployeeWithClaim>
 * projectWiseList = new ArrayList<>(); List<GetProjectHeader> projectHeaderList
 * = new ArrayList<GetProjectHeader>();
 * 
 * @RequestMapping(value = "/emptypewisereport", method = RequestMethod.GET)
 * public String emptypewisereport(HttpServletRequest request,
 * HttpServletResponse response, Model model) {
 * 
 * String mav = "claimreport/emptypewisereport";
 * 
 * try {
 * 
 * date = request.getParameter("date"); HttpSession session =
 * request.getSession(); LoginResponse userObj = (LoginResponse)
 * session.getAttribute("UserDetail");
 * 
 * if (date != null) {
 * 
 * System.out.println(date); String[] dates = date.split(" to ");
 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
 * map.add("locationId", userObj.getLocationIds()); map.add("fromDate",
 * DateConvertor.convertToYMD(dates[0])); map.add("toDate",
 * DateConvertor.convertToYMD(dates[1]));
 * 
 * EmployeeWithClaim[] employeeWithClaim = Constants.getRestTemplate()
 * .postForObject(Constants.url + "/employeeTypeWiseClaimReport", map,
 * EmployeeWithClaim[].class);
 * 
 * list = new ArrayList<>(Arrays.asList(employeeWithClaim));
 * 
 * map = new LinkedMultiValueMap<>(); map.add("companyId",
 * userObj.getCompanyId()); ClaimType[] claimTypeListArray =
 * Constants.getRestTemplate() .postForObject(Constants.url +
 * "/getClaimListByCompanyId", map, ClaimType[].class);
 * 
 * claimTypelist = new ArrayList<ClaimType>(Arrays.asList(claimTypeListArray));
 * 
 * model.addAttribute("date", date); model.addAttribute("list", list);
 * model.addAttribute("claimTypelist", claimTypelist); }
 * 
 * } catch (Exception e) { e.printStackTrace(); } return mav; }
 * 
 * @RequestMapping(value = "/exelForEmployeeTypeWiseClaim", method =
 * RequestMethod.GET) public void
 * exelForEmployeeTypeWiseClaim(HttpServletRequest request, HttpServletResponse
 * response) {
 * 
 * String reportName = "Employee Type Wise Claim Report";
 * 
 * try {
 * 
 * List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
 * 
 * ExportToExcel expoExcel = new ExportToExcel(); List<String> rowData = new
 * ArrayList<String>();
 * 
 * rowData.add("Sr. No"); rowData.add("Employee Name");
 * 
 * for (int i = 0; i < claimTypelist.size(); i++) {
 * rowData.add(claimTypelist.get(i).getClaimTypeTitle()); }
 * rowData.add("Total");
 * 
 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
 * 
 * int cnt = 1; float total = 0; char alphabet = 'C';
 * 
 * for (int i = 0; i < list.size(); i++) {
 * 
 * float empTotal = 0;
 * 
 * expoExcel = new ExportToExcel(); rowData = new ArrayList<String>();
 * rowData.add("" + cnt); rowData.add("" + list.get(i).getEmpName());
 * 
 * for (int j = 0; j < list.get(i).getList().size(); j++) {
 * 
 * for (int k = 0; k < claimTypelist.size(); k++) {
 * 
 * if (list.get(i).getList().get(j).getTypeId() ==
 * claimTypelist.get(k).getClaimTypeId()) { rowData.add("" +
 * list.get(i).getList().get(j).getAmt()); empTotal = empTotal +
 * list.get(i).getList().get(j).getAmt(); break; } }
 * 
 * } rowData.add("" + empTotal); expoExcel.setRowData(rowData);
 * exportToExcelList.add(expoExcel); total = total + empTotal; cnt = cnt + 1; }
 * 
 * expoExcel = new ExportToExcel(); rowData = new ArrayList<String>();
 * 
 * rowData.add("-"); rowData.add("Total");
 * 
 * for (int i = 0; i < claimTypelist.size(); i++) {
 * 
 * float typeTotal = 0; alphabet++; System.out.println(alphabet); for (int j =
 * 0; j < list.size(); j++) { for (int k = 0; k < list.get(j).getList().size();
 * k++) {
 * 
 * if (list.get(j).getList().get(k).getTypeId() ==
 * claimTypelist.get(i).getClaimTypeId()) { typeTotal = typeTotal +
 * list.get(j).getList().get(k).getAmt(); break; } }
 * 
 * }
 * 
 * rowData.add("" + typeTotal); } rowData.add("" + total);
 * 
 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
 * 
 * XSSFWorkbook wb = null; try { System.out.println("exportToExcelList" +
 * exportToExcelList.toString());
 * 
 * wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " Date:" +
 * date + "", "", alphabet);
 * 
 * ExceUtil.autoSizeColumns(wb, 3);
 * response.setContentType("application/vnd.ms-excel"); String date = new
 * SimpleDateFormat("yyyy-MM-dd").format(new Date());
 * response.setHeader("Content-disposition", "attachment; filename=" +
 * reportName + "-" + date + ".xlsx"); wb.write(response.getOutputStream());
 * 
 * } catch (IOException ioe) { throw new
 * RuntimeException("Error writing spreadsheet to output stream"); } finally {
 * if (wb != null) { wb.close(); } }
 * 
 * } catch (Exception e) {
 * 
 * System.err.println("Exce in showProgReport " + e.getMessage());
 * e.printStackTrace();
 * 
 * }
 * 
 * }
 * 
 * @RequestMapping(value = "/empProjectwisereport", method = RequestMethod.GET)
 * public String empProjectwisereport(HttpServletRequest request,
 * HttpServletResponse response, Model model) {
 * 
 * String mav = "claimreport/empProjectwisereport";
 * 
 * try {
 * 
 * date1 = request.getParameter("date"); HttpSession session =
 * request.getSession(); LoginResponse userObj = (LoginResponse)
 * session.getAttribute("UserDetail");
 * 
 * if (date1 != null) {
 * 
 * String[] dates = date1.split(" to "); MultiValueMap<String, Object> map = new
 * LinkedMultiValueMap<>(); map.add("locationId", userObj.getLocationIds());
 * map.add("fromDate", DateConvertor.convertToYMD(dates[0])); map.add("toDate",
 * DateConvertor.convertToYMD(dates[1]));
 * 
 * EmployeeWithClaim[] employeeWithClaim =
 * Constants.getRestTemplate().postForObject( Constants.url +
 * "/employeeProjectWiseClaimReport", map, EmployeeWithClaim[].class);
 * 
 * projectWiseList = new ArrayList<>(Arrays.asList(employeeWithClaim));
 * 
 * map = new LinkedMultiValueMap<>(); map.add("companyId",
 * userObj.getCompanyId());
 * 
 * GetProjectHeader[] proHeaderArray = Constants.getRestTemplate()
 * .postForObject(Constants.url + "/getProjectAllListByCompanyId", map,
 * GetProjectHeader[].class); projectHeaderList = new
 * ArrayList<GetProjectHeader>(Arrays.asList(proHeaderArray));
 * 
 * model.addAttribute("date", date1); model.addAttribute("list",
 * projectWiseList); model.addAttribute("projectHeaderList", projectHeaderList);
 * }
 * 
 * } catch (Exception e) { e.printStackTrace(); } return mav; }
 * 
 * @RequestMapping(value = "/exelForEmployeeProjectWiseClaim", method =
 * RequestMethod.GET) public void
 * exelForEmployeeProjectWiseClaim(HttpServletRequest request,
 * HttpServletResponse response) {
 * 
 * String reportName = "Employee Project Wise Claim Report";
 * 
 * try {
 * 
 * List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
 * 
 * ExportToExcel expoExcel = new ExportToExcel(); List<String> rowData = new
 * ArrayList<String>();
 * 
 * rowData.add("Sr. No"); rowData.add("Employee Name");
 * 
 * for (int i = 0; i < projectHeaderList.size(); i++) {
 * rowData.add(projectHeaderList.get(i).getProjectTitle()); }
 * rowData.add("Total");
 * 
 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
 * 
 * int cnt = 1; float total = 0; char alphabet = 'C';
 * 
 * for (int i = 0; i < projectWiseList.size(); i++) {
 * 
 * float empTotal = 0;
 * 
 * expoExcel = new ExportToExcel(); rowData = new ArrayList<String>();
 * rowData.add("" + cnt); rowData.add("" + projectWiseList.get(i).getEmpName());
 * 
 * for (int j = 0; j < projectWiseList.get(i).getList().size(); j++) {
 * 
 * for (int k = 0; k < projectHeaderList.size(); k++) {
 * 
 * if (projectWiseList.get(i).getList().get(j).getTypeId() ==
 * projectHeaderList.get(k) .getProjectId()) { rowData.add("" +
 * projectWiseList.get(i).getList().get(j).getAmt()); empTotal = empTotal +
 * projectWiseList.get(i).getList().get(j).getAmt(); break; } }
 * 
 * } rowData.add("" + empTotal); expoExcel.setRowData(rowData);
 * exportToExcelList.add(expoExcel); total = total + empTotal; cnt = cnt + 1; }
 * 
 * expoExcel = new ExportToExcel(); rowData = new ArrayList<String>();
 * 
 * rowData.add("-"); rowData.add("Total");
 * 
 * for (int i = 0; i < projectHeaderList.size(); i++) {
 * 
 * float typeTotal = 0; alphabet++; System.out.println(alphabet); for (int j =
 * 0; j < projectWiseList.size(); j++) { for (int k = 0; k <
 * projectWiseList.get(j).getList().size(); k++) {
 * 
 * if (projectWiseList.get(j).getList().get(k).getTypeId() ==
 * projectHeaderList.get(i) .getProjectId()) { typeTotal = typeTotal +
 * projectWiseList.get(j).getList().get(k).getAmt(); break; } }
 * 
 * }
 * 
 * rowData.add("" + typeTotal); } rowData.add("" + total);
 * 
 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
 * 
 * XSSFWorkbook wb = null; try { System.out.println("exportToExcelList" +
 * exportToExcelList.toString());
 * 
 * wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " Date:" +
 * date1 + "", "", alphabet);
 * 
 * ExceUtil.autoSizeColumns(wb, 3);
 * response.setContentType("application/vnd.ms-excel"); String date = new
 * SimpleDateFormat("yyyy-MM-dd").format(new Date());
 * response.setHeader("Content-disposition", "attachment; filename=" +
 * reportName + "-" + date + ".xlsx"); wb.write(response.getOutputStream());
 * 
 * } catch (IOException ioe) { throw new
 * RuntimeException("Error writing spreadsheet to output stream"); } finally {
 * if (wb != null) { wb.close(); } }
 * 
 * } catch (Exception e) {
 * 
 * System.err.println("Exce in showProgReport " + e.getMessage());
 * e.printStackTrace();
 * 
 * }
 * 
 * }
 * 
 * String date3; List<ClientWiseClaimReport> clientwisereportlist = new
 * ArrayList<>();
 * 
 * @RequestMapping(value = "/clientWiseClaimReport", method = RequestMethod.GET)
 * public String clientWiseClaimReport(HttpServletRequest request,
 * HttpServletResponse response, Model model) {
 * 
 * String mav = "claimreport/clientWiseClaimReport";
 * 
 * try {
 * 
 * date3 = request.getParameter("date");
 * 
 * if (date3 != null) {
 * 
 * String[] dates = date3.split(" to "); MultiValueMap<String, Object> map = new
 * LinkedMultiValueMap<>(); map.add("fromDate",
 * DateConvertor.convertToYMD(dates[0])); map.add("toDate",
 * DateConvertor.convertToYMD(dates[1])); ClientWiseClaimReport[]
 * employeeWithClaim = Constants.getRestTemplate() .postForObject(Constants.url
 * + "/clientWiseClaimReport", map, ClientWiseClaimReport[].class);
 * 
 * clientwisereportlist = new ArrayList<>(Arrays.asList(employeeWithClaim));
 * 
 * model.addAttribute("date", date3); model.addAttribute("clientwisereportlist",
 * clientwisereportlist); }
 * 
 * } catch (Exception e) { e.printStackTrace(); } return mav; }
 * 
 * @RequestMapping(value = "/exelForClientWiseClaim", method =
 * RequestMethod.GET) public void exelForClientWiseClaim(HttpServletRequest
 * request, HttpServletResponse response) {
 * 
 * String reportName = "Client Wise Claim Report";
 * 
 * try {
 * 
 * List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
 * 
 * ExportToExcel expoExcel = new ExportToExcel(); List<String> rowData = new
 * ArrayList<String>();
 * 
 * rowData.add("Sr. No"); rowData.add("Client Name"); rowData.add("Total");
 * 
 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
 * 
 * int cnt = 1; float empTotal = 0;
 * 
 * for (int i = 0; i < clientwisereportlist.size(); i++) {
 * 
 * expoExcel = new ExportToExcel(); rowData = new ArrayList<String>();
 * rowData.add("" + cnt); rowData.add("" +
 * clientwisereportlist.get(i).getCustName()); rowData.add("" +
 * clientwisereportlist.get(i).getClaimAmount()); expoExcel.setRowData(rowData);
 * exportToExcelList.add(expoExcel);
 * 
 * empTotal = empTotal + clientwisereportlist.get(i).getClaimAmount(); cnt = cnt
 * + 1; }
 * 
 * expoExcel = new ExportToExcel(); rowData = new ArrayList<String>();
 * 
 * rowData.add("-"); rowData.add("Total"); rowData.add("" + empTotal);
 * 
 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
 * 
 * XSSFWorkbook wb = null; try { System.out.println("exportToExcelList" +
 * exportToExcelList.toString());
 * 
 * wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " Date:" +
 * date3 + "", "", 'C');
 * 
 * ExceUtil.autoSizeColumns(wb, 3);
 * response.setContentType("application/vnd.ms-excel"); String date = new
 * SimpleDateFormat("yyyy-MM-dd").format(new Date());
 * response.setHeader("Content-disposition", "attachment; filename=" +
 * reportName + "-" + date + ".xlsx"); wb.write(response.getOutputStream());
 * 
 * } catch (IOException ioe) { throw new
 * RuntimeException("Error writing spreadsheet to output stream"); } finally {
 * if (wb != null) { wb.close(); } }
 * 
 * } catch (Exception e) {
 * 
 * System.err.println("Exce in showProgReport " + e.getMessage());
 * e.printStackTrace();
 * 
 * }
 * 
 * }
 * 
 * }
 */