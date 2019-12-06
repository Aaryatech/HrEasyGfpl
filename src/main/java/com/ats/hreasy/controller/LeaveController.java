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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveSummary;
import com.ats.hreasy.model.LeaveType;
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
		//LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			String valueType = request.getParameter("valueType");
			/*System.err.println("compId:  " + userObj.getCompanyId());
			System.err.println("valueType:  " + valueType);*/

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
		//LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		try {

			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("leaveTypeAdd", "showLeaveTypeList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/

				model = new ModelAndView("leave/leaveTypeAdd");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", 1);

				LeaveSummary[] employeeDoc = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLeaveSummaryList", map, LeaveSummary[].class);

				List<LeaveSummary> sumList = new ArrayList<LeaveSummary>(Arrays.asList(employeeDoc));
				 
				model.addObject("sumList", sumList);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertLeaveType", method = RequestMethod.POST)
	public String submitInsertLeaveType(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			//LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
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
			//LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			/*Info view = AcessController.checkAccess("showLeaveTypeList", "showLeaveTypeList", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/

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

				}*/
		//	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/editLeaveType", method = RequestMethod.GET)
	public ModelAndView editLeaveType(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;

		HttpSession session = request.getSession();
		//LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		try {

			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editLeaveType", "showLeaveTypeList", 0, 0, 1, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/
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
			//}
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
			
			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("deleteLeaveType", "showLeaveTypeList", 0, 0, 0, 1, newModuleList);
			if (view.isError() == true) {
				a = "redirect:/accessDenied";

			}

			else {*/
				a = "redirect:/showLeaveTypeList";

				String base64encodedString = request.getParameter("typeId");
				String typeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("lvTypeId", typeId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteLeaveType", map,
						Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", "Record Deleted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Delete");
				}
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	@RequestMapping(value = "/submitEditLeaveType", method = RequestMethod.POST)
	public String submitEditLeaveType(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			//LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

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

/*	@RequestMapping(value = "/showApplyForLeave", method = RequestMethod.GET)
	public ModelAndView showEmpList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/appplyForLeave");

		try {
			GetEmployeeInfo temp = new GetEmployeeInfo();
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showApplyForLeave", "showApplyForLeave", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				map.add("companyId", userObj.getCompanyId());
				map.add("locIdList", userObj.getLocationIds());
				map.add("empId", userObj.getEmpId());

				GetEmployeeInfo[] employeeDepartment = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmpInfoAuthWise", map, GetEmployeeInfo[].class);

				List<GetEmployeeInfo> employeeDepartmentlist = new ArrayList<GetEmployeeInfo>(
						Arrays.asList(employeeDepartment));

				int flag = 1;

				map = new LinkedMultiValueMap<>();
				map.add("empId", userObj.getEmpId());

				GetEmployeeInfo editEmp = Constants.getRestTemplate().postForObject(Constants.url + "/GetEmployeeInfo",
						map, GetEmployeeInfo.class);
				model.addObject("editEmp", editEmp);
				for (int i = 0; i < employeeDepartmentlist.size(); i++) {
					if (employeeDepartmentlist.get(i).getEmpId() == userObj.getEmpId()) {
						flag = 0;
						System.err.println(" matched");
						employeeDepartmentlist.remove(i);
						break;
					}

				}

				 if (flag == 1) { 
				// System.err.println("not matched");

				temp.setCompanyId(editEmp.getCompanyId());
				temp.setCompanyName(editEmp.getCompanyName());
				temp.setEmpCategory(editEmp.getEmpCategory());
				temp.setEmpCatId(editEmp.getEmpCatId());
				temp.setEmpCode(editEmp.getEmpCode());
				temp.setEmpDept(editEmp.getEmpDept());
				temp.setEmpDeptShortName(editEmp.getEmpDeptShortName());
				temp.setEmpCatShortName(editEmp.getEmpCatShortName());
				temp.setEmpTypeShortName(editEmp.getEmpTypeShortName());
				temp.setEmpDeptId(editEmp.getEmpDeptId());
				temp.setEmpEmail(editEmp.getEmpEmail());
				temp.setEmpFname(editEmp.getEmpFname());
				temp.setEmpId(editEmp.getEmpId());
				temp.setEmpMname(editEmp.getEmpMname());
				temp.setEmpMobile1(editEmp.getEmpMobile1());
				temp.setEmpPrevExpYrs(editEmp.getEmpPrevExpYrs());
				temp.setEmpRatePerhr(editEmp.getEmpRatePerhr());
				temp.setEmpSname(editEmp.getEmpSname());
				temp.setEmpType(editEmp.getEmpType());
				temp.setEmpTypeId(editEmp.getEmpTypeId());
				// employeeDepartmentlist.add(temp);
				 } 

				temp.setExVar1(FormValidation.Encrypt(String.valueOf(editEmp.getEmpId())));
				for (int i = 0; i < employeeDepartmentlist.size(); i++) {
					// System.out.println("employeeDepartmentlist.get(i).getEmpId()"+employeeDepartmentlist.get(i).getEmpId());
					employeeDepartmentlist.get(i).setExVar1(
							FormValidation.Encrypt(String.valueOf(employeeDepartmentlist.get(i).getEmpId())));
				}

				model.addObject("empList", employeeDepartmentlist);
				model.addObject("tempList", temp);
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
	}*/
	/*

	List<LeaveHistory> leaveHistoryList = new ArrayList<LeaveHistory>();

	@RequestMapping(value = "/leaveApply", method = RequestMethod.GET)
	public ModelAndView showApplyLeave(HttpServletRequest request, HttpServletResponse response) {

		float temp_round = 0;
		ModelAndView model = new ModelAndView("leave/leaveApplication");
		HttpSession session = request.getSession();
		Date date = new Date();
		try {
			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);

			EmployeeInfo editEmp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpInfoById", map,
					EmployeeInfo.class);
			model.addObject("editEmp", editEmp);

			model.addObject("empId", empId);

			map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("currYrId", session.getAttribute("currYearId"));

			LeaveHistory[] leaveHistory = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLeaveHistoryList", map, LeaveHistory[].class);

			leaveHistoryList = new ArrayList<LeaveHistory>(Arrays.asList(leaveHistory));
			System.err.println("leaveHistoryList**********" + leaveHistoryList.toString());

			if (leaveHistoryList.isEmpty()) {
				model.addObject("lvsId", 0);
			} else {
				model.addObject("lvsId", leaveHistoryList.get(0).getLvsId());
			}
//authinfo
			map = new LinkedMultiValueMap<String, Object>();
			map.add("empId", empId);

			AuthorityInformation authorityInformation = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAuthorityInfoByEmpId", map, AuthorityInformation.class);
			model.addObject("authorityInformation", authorityInformation);

			System.err.println("authorityInformation**********" + authorityInformation.toString());
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

			CalenderYear currYr = Constants.getRestTemplate().getForObject(Constants.url + "getcurrentyear",
					CalenderYear.class);

			SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = yy.parse(currYr.getCalYrFromDate());
			Date toDate = yy.parse(currYr.getCalYrToDate());
			Date joiningDate = yy.parse(editEmp.getEmpJoiningDate());

			map = new LinkedMultiValueMap<>();
			map.add("limitKey", "casualleave");
			Setting casualLeaveId = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey", map,
					Setting.class);

			if (joiningDate.compareTo(fromDate) > 0 && joiningDate.compareTo(toDate) < 0) {

				Calendar startCalendar = new GregorianCalendar();
				startCalendar.setTime(fromDate);
				Calendar endCalendar = new GregorianCalendar();
				endCalendar.setTime(joiningDate);

				int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
				int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

				System.err.println(diffMonth + 1);

				for (int i = 0; i < leaveHistoryList.size(); i++) {
					if (leaveHistoryList.get(i).getLvTypeId() == Integer.parseInt(casualLeaveId.getValue())) {

						float leavePerMonth = leaveHistoryList.get(i).getLvsAllotedLeaves() / 12;
						float minusLeave = leavePerMonth * (diffMonth + 1);

						temp_round = leaveHistoryList.get(i).getLvsAllotedLeaves() - minusLeave;
						float valueRounded = Math.round(temp_round);
						leaveHistoryList.get(i).setLvsAllotedLeaves((valueRounded));

					}
				}

			}
			model.addObject("leaveHistoryList", leaveHistoryList);
			model.addObject("currYr", currYr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getHolidayAndWeeklyOffList", method = RequestMethod.GET)
	public @ResponseBody HolidayAndWeeklyOff getHolidayAndWeeklyOffList(HttpServletRequest request,
			HttpServletResponse response) {

		HolidayAndWeeklyOff HolidayAndWeeklyOff = new HolidayAndWeeklyOff();

		try {
			String empId = request.getParameter("empId");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			System.out.println(map);
			Holiday[] holi = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getHolidayByEmpIdAndFromDateTodate", map, Holiday[].class);
			List<Holiday> li = new ArrayList<>(Arrays.asList(holi));

			WeeklyOff[] wee = Constants.getRestTemplate().postForObject(Constants.url + "/getWeeklyOffListByEmpId", map,
					WeeklyOff[].class);
			List<WeeklyOff> list = new ArrayList<>(Arrays.asList(wee));
			HolidayAndWeeklyOff.setHolidayList(li);
			HolidayAndWeeklyOff.setWeeklyList(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return HolidayAndWeeklyOff;
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
			System.out.println(map);
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
			HttpSession session = request.getSession();
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			// String compName = request.getParameter("1");
			String leaveDateRange = request.getParameter("leaveDateRange");
			String dayTypeName = request.getParameter("dayTypeName");
			float noOfDays = Float.parseFloat(request.getParameter("noOfDays"));
			int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));

			System.out.println("leaveTypeId" + leaveTypeId);
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

			System.out.println("stat is " + stat);
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

				leaveSummary.setCalYrId((int) session.getAttribute("currYearId"));
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

	@RequestMapping(value = "/chkNumber", method = RequestMethod.GET)
	public @ResponseBody String chkNumber(HttpServletRequest request, HttpServletResponse response) {
		// LeaveHistory editEmp = new LeaveHistory();

		String balance = new String();

		try {
			// System.out.println("In ChkNumber");

			int leaveTypeId = Integer.parseInt(request.getParameter("inputValue"));
			// int lvsId = Integer.parseInt(request.getParameter("lvsId"));

			System.out.println("In ChkNumber" + leaveTypeId);

			// System.out.println("In ChkNumber" + lvsId);

			
			 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			 * map.add("leaveTypeId", leaveTypeId); map.add("lvsId", lvsId);
			 * 
			 * editEmp = Constants.getRestTemplate().postForObject(Constants.url +
			 * "/getLeaveHistoryByLeaveTypeId", map, LeaveHistory.class);
			 

			for (int i = 0; i < leaveHistoryList.size(); i++) {
				if (leaveTypeId == leaveHistoryList.get(i).getLvTypeId()) {
					balance = String.valueOf(leaveHistoryList.get(i).getBalLeave()
							+ leaveHistoryList.get(i).getLvsAllotedLeaves() - leaveHistoryList.get(i).getSactionLeave()
							- leaveHistoryList.get(i).getAplliedLeaeve());
				}
			}

			// System.err.println("data is" + editEmp.toString());
		} catch (Exception e) {
			e.printStackTrace();
			balance = "0";
		}

		return balance;
	}

	// ****************************Leave History
	// Report**********************************

	@RequestMapping(value = "/empInfoHistoryReport", method = RequestMethod.GET)
	public ModelAndView empInfoHistory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("Report/empHistoryReport");

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("empInfoHistoryReport",
					"empInfoHistoryReport", 1, 0, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("locationId", userObj.getLocationIds());
				CalenderYear[] calenderYear = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getCalculateYearList", CalenderYear[].class);
				List<CalenderYear> calYearList = new ArrayList<CalenderYear>(Arrays.asList(calenderYear));
	
				EmployeeInfo[] employeeInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmpInfoByLocId", map, EmployeeInfo[].class);
	
				List<EmployeeInfo> employeeInfoList = new ArrayList<EmployeeInfo>(Arrays.asList(employeeInfo));
				model.addObject("calYearList", calYearList);
				model.addObject("employeeInfoList", employeeInfoList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/empInfoHistoryReportList", method = RequestMethod.GET)
	public @ResponseBody List<EmpLeaveHistoryRep> empInfoHistoryReportList(HttpServletRequest request,
			HttpServletResponse response) {

		List<EmpLeaveHistoryRep> employeeInfoList = new ArrayList<EmpLeaveHistoryRep>();
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		try {

			int empId = Integer.parseInt(request.getParameter("empId"));
			int calYrId = Integer.parseInt(request.getParameter("calYrId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("calYrId", calYrId);

			EmpLeaveHistoryRep[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLeaveHistoryRep", map, EmpLeaveHistoryRep[].class);

			employeeInfoList = new ArrayList<EmpLeaveHistoryRep>(Arrays.asList(employeeInfo));
			System.out.println("employeeInfoList" + employeeInfoList.toString());

			map = new LinkedMultiValueMap<>();
			map.add("locationId", userObj.getLocationIds());
			EmployeeInfo[] emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpInfoByLocId", map,
					EmployeeInfo[].class);

			List<EmployeeInfo> empList1 = new ArrayList<EmployeeInfo>(Arrays.asList(emp));

			List<LeaveHistTemp> finalList = new ArrayList<LeaveHistTemp>();

			for (int i = 0; i < empList1.size(); i++) {
				LeaveHistTemp fin = new LeaveHistTemp();
				fin.setEmpName(empList1.get(i).getEmpFname().concat(" ").concat(empList1.get(i).getEmpSname()));

				List<EmpLeaveHistoryRep> subList = new ArrayList<EmpLeaveHistoryRep>();
				for (int j = 0; j < employeeInfoList.size(); j++) {

					if (empList1.get(i).getEmpId() == employeeInfoList.get(j).getEmpId()) {
						subList.add(employeeInfoList.get(j));
					}

				}
				fin.setRec(subList);
				finalList.add(fin);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeInfoList;
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
			map.add("locationId", userObj.getLocationIds());
			EmployeeInfo[] emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpInfoByLocId", map,
					EmployeeInfo[].class);

			List<EmployeeInfo> empList1 = new ArrayList<EmployeeInfo>(Arrays.asList(emp));

			List<LeaveHistTemp> finalList = new ArrayList<LeaveHistTemp>();

			for (int i = 0; i < empList1.size(); i++) {
				LeaveHistTemp fin = new LeaveHistTemp();
				fin.setEmpName(empList1.get(i).getEmpFname().concat(" ").concat(empList1.get(i).getEmpSname()));

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

	}*/

}