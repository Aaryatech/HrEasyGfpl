package com.ats.hreasy.controller;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.ExceUtil;
import com.ats.hreasy.common.ExportToExcel;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.common.ItextPageEvent;
import com.ats.hreasy.common.ReportCostants;
import com.ats.hreasy.common.UpateAttendaceCommon;
import com.ats.hreasy.common.VpsImageUpload;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.AuthorityInformation;
import com.ats.hreasy.model.CalenderYear;
import com.ats.hreasy.model.DailyAttendance;
import com.ats.hreasy.model.DataForUpdateAttendance;
import com.ats.hreasy.model.EmpLeaveHistoryRep;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.FileUploadedData;
import com.ats.hreasy.model.GetAuthorityIds;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.GetLeaveApplyAuthwise;
import com.ats.hreasy.model.GetLeaveStatus;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveApply;
import com.ats.hreasy.model.LeaveCount;
import com.ats.hreasy.model.LeaveDetail;
import com.ats.hreasy.model.LeaveHistTemp;
import com.ats.hreasy.model.LeaveHistory;
import com.ats.hreasy.model.LeaveSummary;
import com.ats.hreasy.model.LeaveTrail;
import com.ats.hreasy.model.LeaveType;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.PayableDayAndPresentDays;
import com.ats.hreasy.model.Setting;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@Scope("session")
public class LeaveController {

	LeaveType editLeaveType = new LeaveType();

	List<AccessRightModule> moduleList = new ArrayList<>();

	@RequestMapping(value = "/checkUniqueLeaveType", method = RequestMethod.GET)
	public @ResponseBody Info checkUniqueLeaveType(HttpServletRequest request, HttpServletResponse response) {

		LeaveType leaveType = new LeaveType();
		Info info = new Info();
		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			String valueType = request.getParameter("valueType");
			/*
			 * System.err.println("compId:  " + userObj.getCompanyId());
			 * System.err.println("valueType:  " + valueType);
			 */

			map.add("valueType", valueType);
			map.add("compId", 1);

			leaveType = Constants.getRestTemplate().postForObject(Constants.url + "checkUniqueShortName", map,
					LeaveType.class);
			if (leaveType != null) {
				info.setError(false);
				System.out.println("false");
			} else {
				info.setError(true);
			}

		} catch (Exception e) {
			info.setError(true);
			info.setMsg("failed");
			e.printStackTrace();
		}

		return info;

	}

	// ***************************************LeaveType*********************************************

	@RequestMapping(value = "/leaveTypeAdd", method = RequestMethod.GET)
	public ModelAndView empDocAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("leaveTypeAdd", "showLeaveTypeList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("leave/leaveTypeAdd");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", 1);

				LeaveSummary[] employeeDoc = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLeaveSummaryList", map, LeaveSummary[].class);

				List<LeaveSummary> sumList = new ArrayList<LeaveSummary>(Arrays.asList(employeeDoc));

				model.addObject("sumList", sumList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertLeaveType", method = RequestMethod.POST)
	public String submitInsertLeaveType(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("leaveTypeAdd", "showLeaveTypeList", 0, 1, 0, 0, newModuleList);
		String a = null;
		if (view.isError() == true) {
			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showLeaveTypeList";

			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

				String compName = request.getParameter("1");
				String leaveTypeTitle = request.getParameter("leaveTypeTitle");
				String leaveShortTypeTitle = request.getParameter("leaveShortTypeTitle");
				// int WprkingHrs = Integer.parseInt(request.getParameter("leaveWorlHrs"));
				int summId = Integer.parseInt(request.getParameter("summId"));
				String leaveColor = request.getParameter("leaveColor");
				String remark = null;

				System.out.println("color    " + leaveColor);
				int isStructured = Integer.parseInt(request.getParameter("isStructured"));
				int isFile = Integer.parseInt(request.getParameter("isFile"));
				try {
					remark = request.getParameter("remark");
				} catch (Exception e) {
					remark = "NA";
				}

				Boolean ret = false;

				if (FormValidation.Validaton(leaveTypeTitle, "") == true) {

					ret = true;
					System.out.println("locShortName" + ret);
				}
				if (FormValidation.Validaton(leaveShortTypeTitle, "") == true) {

					ret = true;
					System.out.println("add" + ret);
				}

				if (FormValidation.Validaton(request.getParameter("leaveColor"), "") == true) {

					ret = true;
					System.out.println("add" + ret);
				}

				if (FormValidation.Validaton(request.getParameter("isFile"), "") == true) {

					ret = true;
					System.out.println("isFile" + ret);
				}

				if (ret == false) {

					LeaveType leaveSummary = new LeaveType();

					leaveSummary.setCompanyId(1);
					leaveSummary.setIsStructured(isStructured);
					leaveSummary.setLvColor(leaveColor);
					leaveSummary.setLvTitle(leaveTypeTitle);
					leaveSummary.setLvTitleShort(leaveShortTypeTitle);
					leaveSummary.setLvWorkingHrs(0);
					leaveSummary.setLvSumupId(summId);
					leaveSummary.setLvRmarks(remark);
					leaveSummary.setExInt1(1);
					leaveSummary.setExInt2(1);
					leaveSummary.setExInt3(1);
					leaveSummary.setExVar1("NA");
					leaveSummary.setExVar2("NA");
					leaveSummary.setExVar3("NA");
					leaveSummary.setIsActive(1);
					leaveSummary.setDelStatus(1);
					leaveSummary.setMakerUserId(userObj.getUserId());
					leaveSummary.setMakerEnterDatetime(sf.format(date));
					leaveSummary.setIsFile(isFile);

					LeaveType res = Constants.getRestTemplate().postForObject(Constants.url + "/saveLeaveType",
							leaveSummary, LeaveType.class);

					if (res.isError() == false) {
						session.setAttribute("successMsg", "Record Inserted Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Insert Record");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Insert Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return a;

	}

	@RequestMapping(value = "/showLeaveTypeList", method = RequestMethod.GET)
	public ModelAndView showLeaveSummaryList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			HttpSession session = request.getSession();
			// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

			Info view = AcessController.checkAccess("showLeaveTypeList", "showLeaveTypeList", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("leave/leaveTypeList");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				LeaveType[] leaveSummary = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLeaveTypeListIsStructure", map, LeaveType[].class);

				List<LeaveType> leaveSummarylist = new ArrayList<LeaveType>(Arrays.asList(leaveSummary));

				for (int i = 0; i < leaveSummarylist.size(); i++) {

					leaveSummarylist.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(leaveSummarylist.get(i).getLvTypeId())));
				}

				model.addObject("lvTypeList", leaveSummarylist);

				Info add = AcessController.checkAccess("showLeaveTypeList", "showLeaveTypeList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showLeaveTypeList", "showLeaveTypeList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showLeaveTypeList", "showLeaveTypeList", 0, 0, 0, 1,
						newModuleList);

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

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/editLeaveType", method = RequestMethod.GET)
	public ModelAndView editLeaveType(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editLeaveType", "showLeaveTypeList", 0, 0, 1, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("leave/editLeaveType");
				String base64encodedString = request.getParameter("typeId");
				String lvTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("lvTypeId", lvTypeId);
				editLeaveType = Constants.getRestTemplate().postForObject(Constants.url + "/getLeaveTypeById", map,
						LeaveType.class);
				model.addObject("editCompany", editLeaveType);

				map = new LinkedMultiValueMap<>();
				map.add("compId", 1);

				LeaveSummary[] employeeDoc = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLeaveSummaryList", map, LeaveSummary[].class);

				List<LeaveSummary> sumList = new ArrayList<LeaveSummary>(Arrays.asList(employeeDoc));
				System.out.println("lv sum list " + sumList);
				model.addObject("sumList", sumList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/deleteLeaveType", method = RequestMethod.GET)
	public String deleteLeaveType(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("deleteLeaveType", "showLeaveTypeList", 0, 0, 0, 1, newModuleList);
			if (view.isError() == true) {
				a = "redirect:/accessDenied";

			}

			else {

				a = "redirect:/showLeaveTypeList";

				String base64encodedString = request.getParameter("typeId");
				String typeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("lvTypeId", typeId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteLeaveType", map,
						Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", info.getMsg());
				} else {
					session.setAttribute("errorMsg", info.getMsg());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	@RequestMapping(value = "/submitEditLeaveType", method = RequestMethod.POST)
	public String submitEditLeaveType(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editLeaveType", "showLeaveTypeList", 0, 0, 1, 0, newModuleList);
		String a = null;
		if (view.isError() == true) {
			a = "redirect:/accessDenied";

		}

		else {

			a = "redirect:/showLeaveTypeList";
			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

				String compName = request.getParameter("1");
				String leaveTypeTitle = request.getParameter("leaveTypeTitle");
				String leaveShortTypeTitle = request.getParameter("leaveShortTypeTitle");
				// int WprkingHrs = Integer.parseInt(request.getParameter("leaveWorlHrs"));
				int summId = Integer.parseInt(request.getParameter("summId"));
				String leaveColor = request.getParameter("leaveColor");
				int isFile = Integer.parseInt(request.getParameter("isFile"));

				String remark = null;
				System.out.println("color    " + leaveColor);
				int isStructured = Integer.parseInt(request.getParameter("isStructured"));
				try {
					remark = request.getParameter("remark");
				} catch (Exception e) {
					remark = "NA";
				}

				Boolean ret = false;

				if (FormValidation.Validaton(leaveTypeTitle, "") == true) {

					ret = true;
					System.out.println("locShortName" + ret);
				}
				if (FormValidation.Validaton(leaveShortTypeTitle, "") == true) {

					ret = true;
					System.out.println("add" + ret);
				}

				if (FormValidation.Validaton(request.getParameter("leaveColor"), "") == true) {

					ret = true;
					System.out.println("add" + ret);
				}

				if (FormValidation.Validaton(request.getParameter("isFile"), "") == true) {

					ret = true;
					System.out.println("add" + ret);
				}

				if (ret == false) {

					editLeaveType.setCompanyId(1);
					editLeaveType.setIsStructured(isStructured);
					editLeaveType.setLvColor(leaveColor);
					editLeaveType.setLvTitle(leaveTypeTitle);
					editLeaveType.setLvTitleShort(leaveShortTypeTitle);
					editLeaveType.setLvWorkingHrs(0);
					editLeaveType.setLvSumupId(summId);
					editLeaveType.setLvRmarks(remark);
					editLeaveType.setMakerUserId(userObj.getUserId());
					editLeaveType.setMakerEnterDatetime(sf.format(date));
					editLeaveType.setIsFile(isFile);

					LeaveType res = Constants.getRestTemplate().postForObject(Constants.url + "/saveLeaveType",
							editLeaveType, LeaveType.class);

					if (res.isError() == false) {
						session.setAttribute("successMsg", "Record Updated Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Update Record");
					}
				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return a;

	}

	// ******************************Apply for
	// leave***********************************************

	@RequestMapping(value = "/showApplyForLeave", method = RequestMethod.GET)
	public ModelAndView showEmpList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/appplyForLeave");

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showApplyForLeave", "showApplyForLeave", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empId", userObj.getEmpId());

				GetEmployeeDetails[] employeeDepartment = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getAuthorityWiseEmpListByEmpId", map, GetEmployeeDetails[].class);

				List<GetEmployeeDetails> employeeDepartmentlist = new ArrayList<GetEmployeeDetails>(
						Arrays.asList(employeeDepartment));

				for (int i = 0; i < employeeDepartmentlist.size(); i++) {
					// System.out.println("employeeDepartmentlist.get(i).getEmpId()"+employeeDepartmentlist.get(i).getEmpId());
					employeeDepartmentlist.get(i).setRawData(
							FormValidation.Encrypt(String.valueOf(employeeDepartmentlist.get(i).getEmpId())));
				}

				model.addObject("empList", employeeDepartmentlist);
				model.addObject("empId", userObj.getEmpId());

				Info add = AcessController.checkAccess("showApplyForLeave", "showApplyForLeave", 0, 1, 0, 0,
						newModuleList);

				if (add.isError() == false) {
					System.out.println(" add   Accessable ");
					model.addObject("addAccess", 0);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/showLeaveHistList", method = RequestMethod.GET)
	public ModelAndView showClaimList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/empLeaveHistory");
		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			List<LeaveDetail> employeeInfoList = new ArrayList<LeaveDetail>();
			int empId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("empId")));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			LeaveDetail[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLeaveListByEmpId", map, LeaveDetail[].class);

			employeeInfoList = new ArrayList<LeaveDetail>(Arrays.asList(employeeInfo));

			for (int i = 0; i < employeeInfoList.size(); i++) {

				employeeInfoList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(employeeInfoList.get(i).getLeaveId())));
			}

			EmployeeMaster editEmp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpInfoByEmpId",
					map, EmployeeMaster.class);
			model.addObject("fname", editEmp.getFirstName());
			model.addObject("sname", editEmp.getSurname());
			model.addObject("leaveHistoryList", employeeInfoList);
			// model.addObject("empId1",empId1);

			model.addObject("empId", empId);

			model.addObject("loginEmpId", userObj.getEmpId());
			model.addObject("encryptEmpId", FormValidation.Encrypt(String.valueOf(empId)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/showLeaveHistDetailList", method = RequestMethod.GET)
	public ModelAndView showLeaveHistDetailList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/empLeaveHistoryDetail");

		try {

			String base64encodedString = request.getParameter("leaveId");
			String leaveId = FormValidation.DecodeKey(base64encodedString);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("leaveId", leaveId);
			GetLeaveStatus[] employeeDoc = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpInfoListByTrailEmpId", map, GetLeaveStatus[].class);

			List<GetLeaveStatus> employeeList = new ArrayList<GetLeaveStatus>(Arrays.asList(employeeDoc));
			model.addObject("employeeList", employeeList);

			MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<>();
			map1.add("leaveId", leaveId);

			GetLeaveApplyAuthwise lvEmp = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLeaveApplyDetailsByLeaveId", map1, GetLeaveApplyAuthwise.class);

			lvEmp.setLeaveFromdt(DateConvertor.convertToDMY(lvEmp.getLeaveFromdt()));
			lvEmp.setLeaveTodt(DateConvertor.convertToDMY(lvEmp.getLeaveTodt()));
			String empId1 = FormValidation.Encrypt(String.valueOf(lvEmp.getEmpId()));
			model.addObject("lvEmp", lvEmp);
			model.addObject("empId1", empId1);
			model.addObject("imageUrl", Constants.leaveDocShowUrl);
			// System.out.println("emp leave details" + lvEmp.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	List<LeaveHistory> leaveHistoryList = new ArrayList<LeaveHistory>();

	@RequestMapping(value = "/leaveApply", method = RequestMethod.GET)
	public ModelAndView showApplyLeave(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/leaveApplication");

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);

			// System.out.println(empId);

			CalenderYear calculateYear = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getCalculateYearListIsCurrent", CalenderYear.class);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			model.addObject("empId", empId);

			map = new LinkedMultiValueMap<>();
			map.add("empId", empId);

			EmployeeMaster editEmp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpInfoByEmpId",
					map, EmployeeMaster.class);

			model.addObject("editEmp", editEmp);

			map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("currYrId", calculateYear.getCalYrId());

			LeaveHistory[] leaveHistory = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLeaveHistoryList", map, LeaveHistory[].class);

			leaveHistoryList = new ArrayList<LeaveHistory>(Arrays.asList(leaveHistory));

			if (leaveHistoryList.isEmpty()) {
				model.addObject("lvsId", 0);
			} else {
				model.addObject("lvsId", leaveHistoryList.get(0).getLvsId());
			}

			/*map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("fromDate", calculateYear.getCalYrFromDate());
			map.add("toDate", calculateYear.getCalYrToDate());
			PayableDayAndPresentDays payableDayAndPresentDays = Constants.getRestTemplate().postForObject(
					Constants.url + "/getPayableDayandPresentDayByEmpId", map, PayableDayAndPresentDays.class);

			if (payableDayAndPresentDays.isError() == false) {
				for (int i = 0; i < leaveHistoryList.size(); i++) {
					if (leaveHistoryList.get(i).getLvTypeId() == 1) {

						leaveHistoryList.get(i)
								.setLvsAllotedLeaves((int) (payableDayAndPresentDays.getPayableDays() / 24));
						break;
					}
				}
			}*/

			map = new LinkedMultiValueMap<String, Object>();
			map.add("empId", empId);

			AuthorityInformation authorityInformation = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAuthorityInfoByEmpId", map, AuthorityInformation.class);
			model.addObject("authorityInformation", authorityInformation);

			if (authorityInformation.equals(null)) {
				model.addObject("authId", 0);
			} else {
				model.addObject("authId", 1);
			}

			//

			map = new LinkedMultiValueMap<>();
			map.add("limitKey", "LEAVELIMIT");
			Setting setlimit = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey", map,
					Setting.class);
			model.addObject("setlimit", setlimit);

			map = new LinkedMultiValueMap<>();
			map.add("limitKey", "CONTILEAVE");
			Setting isContinueLeave = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey", map,
					Setting.class);
			model.addObject("CONTILEAVE", isContinueLeave);

			System.out.println(isContinueLeave);

			/*
			 * CalenderYear currYr = Constants.getRestTemplate().getForObject(Constants.url
			 * + "getcurrentyear", CalenderYear.class);
			 */

			model.addObject("leaveHistoryList", leaveHistoryList);
			model.addObject("currYr", calculateYear);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/chkNumber", method = RequestMethod.GET)
	public @ResponseBody Info chkNumber(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		try {

			int leaveTypeId = Integer.parseInt(request.getParameter("inputValue"));

			for (int i = 0; i < leaveHistoryList.size(); i++) {
				if (leaveTypeId == leaveHistoryList.get(i).getLvTypeId()) {
					String balance = String.valueOf(leaveHistoryList.get(i).getBalLeave()
							+ leaveHistoryList.get(i).getLvsAllotedLeaves() - leaveHistoryList.get(i).getSactionLeave()
							- leaveHistoryList.get(i).getAplliedLeaeve());
					int isRequered = leaveHistoryList.get(i).getIsFile();
					info.setMsg(balance + "," + isRequered);
					info.setError(false);
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			info.setError(true);
			info.setMsg(0 + "," + 0);
		}

		return info;
	}

	@RequestMapping(value = "/checkDatesRange", method = RequestMethod.GET)
	public @ResponseBody Info checkDatesRange(HttpServletRequest request, HttpServletResponse response) {

		Info leaveResponse = new Info();

		try {

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String empId = request.getParameter("empId");
			String typeId = request.getParameter("typeId");
			String noOfDays = request.getParameter("noOfDays");
			String shortName = new String();

			for (int i = 0; i < leaveHistoryList.size(); i++) {
				if (Integer.parseInt(typeId) == leaveHistoryList.get(i).getLvTypeId()) {
					shortName = leaveHistoryList.get(i).getLvTitleShort();
					break;
				}
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("empId", empId);
			map.add("leaveTypeId", typeId);
			map.add("shortName", shortName);
			map.add("noOfDays", noOfDays);
			System.out.println(map);
			leaveResponse = Constants.getRestTemplate()
					.postForObject(Constants.url + "/checkDateForRepetedLeaveValidation", map, Info.class);

			System.out.println(leaveResponse);

		} catch (Exception e) {
			e.printStackTrace();

		}

		return leaveResponse;
	}

	@RequestMapping(value = "/calholidayWebservice", method = RequestMethod.GET)
	public @ResponseBody LeaveCount calholidayWebservice(HttpServletRequest request, HttpServletResponse response) {

		LeaveCount leaveCount = new LeaveCount();

		try {
			String empId = request.getParameter("empId");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			leaveCount = Constants.getRestTemplate().postForObject(Constants.url + "/calculateHolidayBetweenDate", map,
					LeaveCount.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveCount;
	}

	@RequestMapping(value = "/insertLeave", method = RequestMethod.POST)
	public String insertLeave(@RequestParam("documentFile") List<MultipartFile> documentFile,
			HttpServletRequest request, HttpServletResponse response) {
		String empId1 = request.getParameter("empId");
		try {

			CalenderYear calculateYear = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getCalculateYearListIsCurrent", CalenderYear.class);

			Info infoImage = new Info();

			HttpSession session = request.getSession();
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
			SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			int fileRequired = Integer.parseInt(request.getParameter("fileRequired"));

			Boolean ret = false;

			if (fileRequired == 1) {

				if (documentFile.get(0).getOriginalFilename() != "") {

					VpsImageUpload upload = new VpsImageUpload();
					String imageName = dateTimeInGMT.format(date)+"_"+documentFile.get(0).getOriginalFilename();
					infoImage = upload.saveUploadedImge(documentFile.get(0), Constants.leaveDocSaveUrl, imageName,
							Constants.values, 0, 0, 0, 0, 0);
					infoImage.setMsg(imageName);
				} else {
					infoImage.setError(true);
				}

			} else {
				infoImage.setMsg("-");
				infoImage.setError(false);
			}

			// String compName = request.getParameter("1");
			String leaveDateRange = request.getParameter("leaveDateRange");
			String dayTypeName = request.getParameter("dayTypeName");
			float noOfDays = Float.parseFloat(request.getParameter("noOfDays"));
			int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));

			int noOfDaysExclude = Integer.parseInt(request.getParameter("noOfDaysExclude"));
			int empId = Integer.parseInt(request.getParameter("empId"));

			// get Authority ids

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);

			GetAuthorityIds editEmp = Constants.getRestTemplate().postForObject(Constants.url + "/getAuthIdByEmpId",
					map, GetAuthorityIds.class);
			int stat = 0;

			if (editEmp.getFinAuthEmpId() == userObj.getEmpId()) {
				stat = 3;
			} else if (editEmp.getIniAuthEmpId() == userObj.getEmpId()) {
				stat = 2;
			} else {
				stat = 1;
			}

			String remark = null;

			String[] arrOfStr = leaveDateRange.split("to", 2);

			System.out.println("dayType" + dayTypeName);

			try {
				remark = request.getParameter("leaveRemark");
			} catch (Exception e) {
				remark = "NA";
			}

			if (FormValidation.Validaton(leaveDateRange, "") == true) {

				ret = true;

			}
			if (FormValidation.Validaton(request.getParameter("noOfDays"), "") == true) {

				ret = true;

			}

			if (FormValidation.Validaton(request.getParameter("noOfDaysExclude"), "") == true) {

				ret = true;

			}

			if (FormValidation.Validaton(request.getParameter("leaveTypeId"), "") == true) {

				ret = true;

			}

			if (ret == false && infoImage.isError() == false) {

				LeaveApply leaveSummary = new LeaveApply();

				leaveSummary.setCalYrId(calculateYear.getCalYrId());
				leaveSummary.setEmpId(empId);
				leaveSummary.setFinalStatus(1);
				leaveSummary.setLeaveNumDays(noOfDays);
				leaveSummary.setCirculatedTo("1");
				leaveSummary.setLeaveDuration(dayTypeName);
				leaveSummary.setLeaveEmpReason(remark);
				leaveSummary.setLvTypeId(leaveTypeId);
				leaveSummary.setLeaveFromdt(DateConvertor.convertToYMD(arrOfStr[0].toString().trim()));
				leaveSummary.setLeaveTodt(DateConvertor.convertToYMD(arrOfStr[1].toString().trim()));

				leaveSummary.setExInt1(stat);
				leaveSummary.setExInt2(1);
				leaveSummary.setExInt3(1);
				leaveSummary.setExVar1("NA");
				leaveSummary.setExVar2("NA");
				leaveSummary.setExVar3(infoImage.getMsg());
				leaveSummary.setIsActive(1);
				leaveSummary.setDelStatus(1);
				leaveSummary.setMakerUserId(userObj.getUserId());
				leaveSummary.setMakerEnterDatetime(sf.format(date));

				LeaveApply res = Constants.getRestTemplate().postForObject(Constants.url + "/saveLeaveApply",
						leaveSummary, LeaveApply.class);

				if (res != null) {
					LeaveTrail lt = new LeaveTrail();

					lt.setEmpRemarks(remark);
					System.err.println("res.getLeaveId()" + res.getLeaveId());
					lt.setLeaveId(res.getLeaveId());

					lt.setLeaveStatus(stat);
					lt.setEmpId(empId);
					lt.setExInt1(1);
					lt.setExInt2(1);
					lt.setExInt3(1);
					lt.setExVar1("NA");
					lt.setExVar2("NA");
					lt.setExVar3("NA");

					lt.setMakerUserId(userObj.getUserId());
					lt.setMakerEnterDatetime(sf.format(date));
					LeaveTrail res1 = Constants.getRestTemplate().postForObject(Constants.url + "/saveLeaveTrail", lt,
							LeaveTrail.class);
					if (res1 != null) {

						map = new LinkedMultiValueMap<>();
						map.add("leaveId", res.getLeaveId());
						map.add("trailId", res1.getTrailPkey());
						Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateTrailId", map,
								Info.class);
						if (info.isError() == false) {
							session.setAttribute("successMsg", "Leave Apply Successfully");
						} else {
							session.setAttribute("errorMsg", "Failed to Apply Leave");
						}

						if (stat == 3) {
							UpateAttendaceCommon upateAttendaceCommon = new UpateAttendaceCommon();

							Info updateAttendaceInfo = upateAttendaceCommon.changeInDailyDailyAfterLeaveTransaction(
									arrOfStr[0], arrOfStr[1], empId, userObj.getUserId());
						}

					}
				}

			} else {
				session.setAttribute("errorMsg", "Failed to Apply Leave");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// return "redirect:/showApplyForLeave";
		return "redirect:/showLeaveHistList?empId=" + FormValidation.Encrypt(empId1);

	}

	@RequestMapping(value = "/showLeaveApprovalByAuthority", method = RequestMethod.GET)
	public ModelAndView showLeaveApprovalByInitialAuthority(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;

		// for pending
		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showLeaveApprovalByAuthority", "showLeaveApprovalByAuthority", 1,
					0, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				model = new ModelAndView("leave/leaveApproveByInitial");
				CalenderYear calculateYear = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getCalculateYearListIsCurrent", CalenderYear.class);
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empId", userObj.getEmpId());
				map.add("currYrId", calculateYear.getCalYrId());

				GetLeaveApplyAuthwise[] employeeDoc = Constants.getRestTemplate().postForObject(
						Constants.url + "/getLeaveApplyListForPending", map, GetLeaveApplyAuthwise[].class);

				List<GetLeaveApplyAuthwise> leaveList = new ArrayList<GetLeaveApplyAuthwise>(
						Arrays.asList(employeeDoc));

				for (int i = 0; i < leaveList.size(); i++) {

					leaveList.get(i)
							.setCirculatedTo(FormValidation.Encrypt(String.valueOf(leaveList.get(i).getLeaveId())));
					leaveList.get(i)
							.setLeaveTypeName(FormValidation.Encrypt(String.valueOf(leaveList.get(i).getEmpId())));
					leaveList.get(i).setLeaveFromdt(DateConvertor.convertToDMY(leaveList.get(i).getLeaveFromdt()));
					leaveList.get(i).setLeaveTodt(DateConvertor.convertToDMY(leaveList.get(i).getLeaveTodt()));
				}
				model.addObject("leaveListForApproval", leaveList);
				model.addObject("list1Count", leaveList.size());

				// for Info

				model.addObject("empIdOrig", userObj.getEmpId());

				map = new LinkedMultiValueMap<>();
				map.add("empId", userObj.getEmpId());
				map.add("currYrId", calculateYear.getCalYrId());
				GetLeaveApplyAuthwise[] employeeDoc1 = Constants.getRestTemplate().postForObject(
						Constants.url + "/getLeaveApplyListForInformation", map, GetLeaveApplyAuthwise[].class);

				List<GetLeaveApplyAuthwise> leaveList1 = new ArrayList<GetLeaveApplyAuthwise>(
						Arrays.asList(employeeDoc1));

				for (int i = 0; i < leaveList1.size(); i++) {

					leaveList1.get(i)
							.setCirculatedTo(FormValidation.Encrypt(String.valueOf(leaveList1.get(i).getLeaveId())));
					leaveList1.get(i)
							.setLeaveTypeName(FormValidation.Encrypt(String.valueOf(leaveList1.get(i).getEmpId())));
					leaveList1.get(i).setLeaveFromdt(DateConvertor.convertToDMY(leaveList1.get(i).getLeaveFromdt()));
					leaveList1.get(i).setLeaveTodt(DateConvertor.convertToDMY(leaveList1.get(i).getLeaveTodt()));
				}

				model.addObject("list2Count", leaveList1.size());
				model.addObject("leaveListForApproval1", leaveList1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/empDetailHistory", method = RequestMethod.GET)
	public ModelAndView empDetailHistory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/empDetailHistory");

		try {

			String base64encodedString = request.getParameter("leaveId");
			String leaveId = FormValidation.DecodeKey(base64encodedString);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("leaveId", leaveId);
			GetLeaveStatus[] employeeDoc = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpInfoListByTrailEmpId", map, GetLeaveStatus[].class);

			List<GetLeaveStatus> employeeList = new ArrayList<GetLeaveStatus>(Arrays.asList(employeeDoc));
			model.addObject("employeeList", employeeList);

			MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<>();
			map1.add("leaveId", leaveId);

			GetLeaveApplyAuthwise lvEmp = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLeaveApplyDetailsByLeaveId", map1, GetLeaveApplyAuthwise.class);
			lvEmp.setLeaveFromdt(DateConvertor.convertToDMY(lvEmp.getLeaveFromdt()));
			lvEmp.setLeaveTodt(DateConvertor.convertToDMY(lvEmp.getLeaveTodt()));
			model.addObject("lvEmp", lvEmp);
			model.addObject("imageUrl", Constants.leaveDocShowUrl);
			// System.out.println("emp leave details" + lvEmp.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/empInfoHistoryReport", method = RequestMethod.GET)
	public ModelAndView empInfoHistory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("empInfoHistoryReport", "empInfoHistoryReport", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				model = new ModelAndView("Report/empHistoryReport");
				CalenderYear[] calenderYear = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getCalculateYearList", CalenderYear[].class);
				List<CalenderYear> calYearList = new ArrayList<CalenderYear>(Arrays.asList(calenderYear));

				EmployeeMaster[] employeeInfo = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getEmplistForAssignAuthorityAll", EmployeeMaster[].class);

				List<EmployeeMaster> employeeInfoList = new ArrayList<EmployeeMaster>(Arrays.asList(employeeInfo));
				model.addObject("calYearList", calYearList);
				model.addObject("employeeInfoList", employeeInfoList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/showEmpLeaveHistoryRep", method = RequestMethod.POST)
	public void showEmpLeaveHistoryRep(HttpServletRequest request, HttpServletResponse response) {
		List<EmpLeaveHistoryRep> progList = new ArrayList<EmpLeaveHistoryRep>();
		String reportName = "Employee Leave Pending Report";
		List<EmpLeaveHistoryRep> employeeInfoList = new ArrayList<EmpLeaveHistoryRep>();
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		try {

			int empId = Integer.parseInt(request.getParameter("empId"));
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

}