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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
 
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.AuthorityInformation;
import com.ats.hreasy.model.CalenderYear;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetAuthorityIds;
import com.ats.hreasy.model.GetLeaveApplyAuthwise;
import com.ats.hreasy.model.GetLeaveStatus;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveApply;
import com.ats.hreasy.model.LeaveCount;
import com.ats.hreasy.model.LeaveDetail;
import com.ats.hreasy.model.LeaveHistory;
import com.ats.hreasy.model.LeaveSummary;
import com.ats.hreasy.model.LeaveTrail;
import com.ats.hreasy.model.LeaveType;
import com.ats.hreasy.model.LoginResponse;
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

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("leaveTypeAdd", "showLeaveTypeList", 0, 1, 0, 0,
			 * newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */

			model = new ModelAndView("leave/leaveTypeAdd");
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", 1);

			LeaveSummary[] employeeDoc = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLeaveSummaryList", map, LeaveSummary[].class);

			List<LeaveSummary> sumList = new ArrayList<LeaveSummary>(Arrays.asList(employeeDoc));

			model.addObject("sumList", sumList);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertLeaveType", method = RequestMethod.POST)
	public String submitInsertLeaveType(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
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
				leaveSummary.setMakerUserId(1);
				leaveSummary.setMakerEnterDatetime(sf.format(date));

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

		return "redirect:/showLeaveTypeList";

	}

	@RequestMapping(value = "/showLeaveTypeList", method = RequestMethod.GET)
	public ModelAndView showLeaveSummaryList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			HttpSession session = request.getSession();
			// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			/*
			 * Info view = AcessController.checkAccess("showLeaveTypeList",
			 * "showLeaveTypeList", 1, 0, 0, 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */

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

			model.addObject("addAccess", 0);
			model.addObject("editAccess", 0);
			model.addObject("deleteAccess", 0);
			model.addObject("lvTypeList", leaveSummarylist);

			/*
			 * Info add = AcessController.checkAccess("showLeaveTypeList",
			 * "showLeaveTypeList", 0, 1, 0, 0, newModuleList); Info edit =
			 * AcessController.checkAccess("showLeaveTypeList", "showLeaveTypeList", 0, 0,
			 * 1, 0, newModuleList); Info delete =
			 * AcessController.checkAccess("showLeaveTypeList", "showLeaveTypeList", 0, 0,
			 * 0, 1, newModuleList);
			 * 
			 * if (add.isError() == false) { System.out.println(" add   Accessable ");
			 * model.addObject("addAccess", 0);
			 * 
			 * } if (edit.isError() == false) { System.out.println(" edit   Accessable ");
			 * model.addObject("editAccess", 0); } if (delete.isError() == false) {
			 * System.out.println(" delete   Accessable "); model.addObject("deleteAccess",
			 * 0);
			 * 
			 * }
			 */
			// }
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

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("editLeaveType", "showLeaveTypeList", 0, 0, 1, 0,
			 * newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */
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
			// }
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

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("deleteLeaveType", "showLeaveTypeList", 0, 0, 0,
			 * 1, newModuleList); if (view.isError() == true) { a =
			 * "redirect:/accessDenied";
			 * 
			 * }
			 * 
			 * else {
			 */
			a = "redirect:/showLeaveTypeList";

			String base64encodedString = request.getParameter("typeId");
			String typeId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("lvTypeId", typeId);
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteLeaveType", map, Info.class);

			if (info.isError() == false) {
				session.setAttribute("successMsg", "Record Deleted Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Delete");
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	@RequestMapping(value = "/submitEditLeaveType", method = RequestMethod.POST)
	public String submitEditLeaveType(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

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

			if (ret == false) {

				editLeaveType.setCompanyId(1);
				editLeaveType.setIsStructured(isStructured);
				editLeaveType.setLvColor(leaveColor);
				editLeaveType.setLvTitle(leaveTypeTitle);
				editLeaveType.setLvTitleShort(leaveShortTypeTitle);
				editLeaveType.setLvWorkingHrs(0);
				editLeaveType.setLvSumupId(summId);
				editLeaveType.setLvRmarks(remark);
				editLeaveType.setMakerUserId(1);
				editLeaveType.setMakerEnterDatetime(sf.format(date));

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

		return "redirect:/showLeaveTypeList";

	}

	// ******************************Apply for
	// leave***********************************************

	@RequestMapping(value = "/showApplyForLeave", method = RequestMethod.GET)
	public ModelAndView showEmpList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/appplyForLeave");

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("showApplyForLeave", "showApplyForLeave", 1, 0,
			 * 0, 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", userObj.getEmpId());

			EmployeeMaster[] employeeDepartment = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAuthorityWiseEmpListByEmpId", map, EmployeeMaster[].class);

			List<EmployeeMaster> employeeDepartmentlist = new ArrayList<EmployeeMaster>(
					Arrays.asList(employeeDepartment));

			for (int i = 0; i < employeeDepartmentlist.size(); i++) {
				// System.out.println("employeeDepartmentlist.get(i).getEmpId()"+employeeDepartmentlist.get(i).getEmpId());
				employeeDepartmentlist.get(i)
						.setRawData(FormValidation.Encrypt(String.valueOf(employeeDepartmentlist.get(i).getEmpId())));
			}

			model.addObject("empList", employeeDepartmentlist);
			model.addObject("empId", userObj.getEmpId());
			model.addObject("addAccess", 0);

			/*
			 * Info add = AcessController.checkAccess("showApplyForLeave",
			 * "showApplyForLeave", 0, 1, 0, 0, newModuleList);
			 * 
			 * 
			 * if (add.isError() == false) { System.out.println(" add   Accessable ");
			 * model.addObject("addAccess", 0);
			 * 
			 * }
			 */

			// }
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

			model.addObject("fname", userObj.getFirstName());
			model.addObject("sname", userObj.getSurname());
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
			model.addObject("editEmp", userObj);
			
			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);

			System.out.println(empId);
			
			CalenderYear calculateYear = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getCalculateYearListIsCurrent", CalenderYear.class);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			model.addObject("empId", empId);

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
	public @ResponseBody String chkNumber(HttpServletRequest request, HttpServletResponse response) {
		  
		String balance = new String();

		try {
			 

			int leaveTypeId = Integer.parseInt(request.getParameter("inputValue"));
			 
			for (int i = 0; i < leaveHistoryList.size(); i++) {
				if (leaveTypeId == leaveHistoryList.get(i).getLvTypeId()) {
					balance = String.valueOf(leaveHistoryList.get(i).getBalLeave()
							+ leaveHistoryList.get(i).getLvsAllotedLeaves() - leaveHistoryList.get(i).getSactionLeave()
							- leaveHistoryList.get(i).getAplliedLeaeve());
				}
			}

			 
		} catch (Exception e) {
			e.printStackTrace();
			balance = "0";
		}

		return balance;
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
	public String insertLeave(HttpServletRequest request, HttpServletResponse response) {
		String empId1 = request.getParameter("empId");
		try {
			
			CalenderYear calculateYear = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getCalculateYearListIsCurrent", CalenderYear.class);
			
			HttpSession session = request.getSession();
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

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

			Boolean ret = false;

			if (FormValidation.Validaton(leaveDateRange, "") == true) {

				ret = true;
				System.out.println("leaveDateRange" + ret);
			}
			if (FormValidation.Validaton(request.getParameter("noOfDays"), "") == true) {

				ret = true;
				System.out.println("noOfDays" + ret);
			}

			if (FormValidation.Validaton(request.getParameter("noOfDaysExclude"), "") == true) {

				ret = true;
				System.out.println("add" + ret);
			}

			if (FormValidation.Validaton(request.getParameter("leaveTypeId"), "") == true) {

				ret = true;
				System.out.println("add" + ret);
			}

			if (ret == false) {

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
				leaveSummary.setExVar3("NA");
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
							session.setAttribute("successMsg", "Record Inserted Successfully");
						} else {
							session.setAttribute("errorMsg", "Failed to Insert Record");
						}

					}
				}

			} else {
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// return "redirect:/showApplyForLeave";
		return "redirect:/showLeaveHistList?empId=" + FormValidation.Encrypt(empId1);

	}

}