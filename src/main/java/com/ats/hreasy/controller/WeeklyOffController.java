package com.ats.hreasy.controller;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.GetWeekShiftChange;
import com.ats.hreasy.model.GetWeeklyOff;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.WeeklyOff;
import com.ats.hreasy.model.WeeklyOffShit;
import com.ats.hreasy.model.WeekoffCategory;
import com.ats.hreasy.model.Loan.GetLoan;

@Controller
@Scope("session")
public class WeeklyOffController {

	String locId = "0";
	String month = "0";
	String year = "0";
	String weekoffCatId = "0";
	String monthyear = "0";
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	String curDate = dateFormat.format(new Date());
	String dateTime = dateFormat.format(now);

	WeeklyOff editWeeklyOff = new WeeklyOff();

	@RequestMapping(value = "/addWeeklyOff", method = RequestMethod.GET)
	public ModelAndView addWeeklyOff(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView model = null;
		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("addWeeklyOff", "showWeeklyOffList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/weekly_off_add");

				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
						Location[].class);

				List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));

				for (int i = 0; i < locationList.size(); i++) {

					locationList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(locationList.get(i).getLocId())));
				}

				// System.out.println(userObj);
				model.addObject("locationList", locationList);
				model.addObject("locationAccess", userObj.getLocationIds().split(","));
				// model.addObject("locationAccess", "2,3".split(","));

				WeekoffCategory[] location1 = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getWeekoffCategoryList", WeekoffCategory[].class);

				List<WeekoffCategory> locationList1 = new ArrayList<WeekoffCategory>(Arrays.asList(location1));

				model.addObject("holiList", locationList1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertWeeklyOff", method = RequestMethod.POST)
	public String submitInsertWeeklyOff(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("addWeeklyOff", "showWeeklyOffList", 0, 1, 0, 0, newModuleList);
		String a = null;
		if (view.isError() == true) {
			a = "redirect:/accessDenied";

		} else {
			a = "redirect:/showWeeklyOffList";
			try {
				System.out.println("inside submitInsertWeeklyOff");
				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

				String woDay = request.getParameter("woDay");
				String woPresently = request.getParameter("woPresently");
				String woRemarks = null;
				woRemarks = request.getParameter("woRemarks");
				String woType = request.getParameter("woType");

				String[] locIds = request.getParameterValues("locId");

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < locIds.length; i++) {
					sb = sb.append(locIds[i] + ",");

				}
				String locIdList = sb.toString();
				locIdList = locIdList.substring(0, locIdList.length() - 1);

				Boolean ret = false;

				if (FormValidation.Validaton(woDay, "") == true) {

					ret = true;
				}

				if (FormValidation.Validaton(woPresently, "") == true) {

					ret = true;
				}

				if (ret == false) {

					WeeklyOff save = new WeeklyOff();
					save.setCompanyId(1);
					save.setDelStatus(1);
					save.setIsActive(1);
					save.setLocId(locIdList);
					save.setWoType(woType);
					save.setWoRemarks(woRemarks);
					save.setWoIsUsed(1);
					save.setWoDay(woDay);
					save.setWoPresently(woPresently);
					save.setMakerEnterDatetime(dateTime);
					save.setMakerUserId(userObj.getUserId());
					save.setExInt1(Integer.parseInt(request.getParameter("woCatId")));

					WeeklyOff res = Constants.getRestTemplate().postForObject(Constants.url + "/saveWeeklyOff", save,
							WeeklyOff.class);

					if (res != null) {
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

	@RequestMapping(value = "/showWeeklyOffList", method = RequestMethod.GET)
	public ModelAndView showWeeklyOffList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showWeeklyOffList", "showWeeklyOffList", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/weekly_off_list");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				map.add("locIdList", userObj.getLocationIds());
				// map.add("locIdList", "2,3");
				GetWeeklyOff[] holListArray = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getWeeklyOffList", map, GetWeeklyOff[].class);

				List<GetWeeklyOff> weekOffList = new ArrayList<>(Arrays.asList(holListArray));

				for (int i = 0; i < weekOffList.size(); i++) {

					weekOffList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(weekOffList.get(i).getWoId())));
				}

				model.addObject("weekOffList", weekOffList);

				Info add = AcessController.checkAccess("showWeeklyOffList", "showWeeklyOffList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showWeeklyOffList", "showWeeklyOffList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showWeeklyOffList", "showWeeklyOffList", 0, 0, 0, 1,
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

	@RequestMapping(value = "/deleteWeeklyOff", method = RequestMethod.GET)
	public String deleteWeeklyOff(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("deleteWeeklyOff", "showWeeklyOffList", 0, 0, 0, 1, newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			} else {

				a = "redirect:/showWeeklyOffList";
				String base64encodedString = request.getParameter("woId");
				String woId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("woId", woId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteWeeklyOff", map,
						Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", "Deleted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Delete");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		return a;
	}

	@RequestMapping(value = "/editWeeklyOff", method = RequestMethod.GET)
	public ModelAndView editWeeklyOff(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		// model.addObject("weighImageUrl", Constants.imageSaveUrl);

		try {
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editWeeklyOff", "showWeeklyOffList", 0, 0, 1, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/weekly_off_edit");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
						Location[].class);

				List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));

				for (int i = 0; i < locationList.size(); i++) {

					locationList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(locationList.get(i).getLocId())));
				}

				model.addObject("locationList", locationList);

				String base64encodedString = request.getParameter("woId");
				String woId = FormValidation.DecodeKey(base64encodedString);

				map = new LinkedMultiValueMap<>();
				map.add("woId", woId);
				editWeeklyOff = Constants.getRestTemplate().postForObject(Constants.url + "/getWeeklyOffById", map,
						WeeklyOff.class);
				model.addObject("editWeeklyOff", editWeeklyOff);

				List<Integer> locIdList = Stream.of(editWeeklyOff.getLocId().split(",")).map(Integer::parseInt)
						.collect(Collectors.toList());

				model.addObject("locIdList", locIdList);
				model.addObject("locationAccess", userObj.getLocationIds().split(","));
				// model.addObject("locationAccess", "2,3".split(","));

				WeekoffCategory[] location1 = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getWeekoffCategoryList", WeekoffCategory[].class);

				List<WeekoffCategory> locationList1 = new ArrayList<WeekoffCategory>(Arrays.asList(location1));

				model.addObject("holiList", locationList1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitEditInsertWeeklyOff", method = RequestMethod.POST)
	public String submitEditInsertWeeklyOff(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editWeeklyOff", "showWeeklyOffList", 0, 0, 1, 0, newModuleList);
		String a = null;
		if (view.isError() == true) {
			a = "redirect:/accessDenied";
		} else {
			a = "redirect:/showWeeklyOffList";

			try {

				String woDay = request.getParameter("woDay");
				String woPresently = request.getParameter("woPresently");
				String woRemarks = null;
				woRemarks = request.getParameter("woRemarks");
				String woType = request.getParameter("woType");

				String[] locIds = request.getParameterValues("locId");

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < locIds.length; i++) {
					sb = sb.append(locIds[i] + ",");

				}
				String locIdList = sb.toString();
				locIdList = locIdList.substring(0, locIdList.length() - 1);

				Boolean ret = false;

				if (FormValidation.Validaton(woDay, "") == true) {

					ret = true;
				}

				if (FormValidation.Validaton(woPresently, "") == true) {

					ret = true;
				}

				if (ret == false) {

					editWeeklyOff.setLocId(locIdList);
					editWeeklyOff.setWoDay(woDay);
					editWeeklyOff.setWoPresently(woPresently);
					editWeeklyOff.setWoRemarks(woRemarks);
					editWeeklyOff.setWoType(woType);
					editWeeklyOff.setExInt1(Integer.parseInt(request.getParameter("woCatId")));
					WeeklyOff res = Constants.getRestTemplate().postForObject(Constants.url + "/saveWeeklyOff",
							editWeeklyOff, WeeklyOff.class);

					if (res != null) {
						session.setAttribute("successMsg", "Record Updated Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Update Record");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Update Record");
			}
		}
		return a;
	}

	// Change Weekly off

	@RequestMapping(value = "/showChangeWeekOff", method = RequestMethod.GET)
	public ModelAndView showLocationList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showChangeWeekOff", "showWeekOffShift", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("master/changeWeeklyOff");
			/*
			 * WeekoffCategory[] location1 =
			 * Constants.getRestTemplate().getForObject(Constants.url +
			 * "/getWeekoffCategoryList", WeekoffCategory[].class); List<WeekoffCategory>
			 * locationList1 = new ArrayList<WeekoffCategory>(Arrays.asList(location1));
			 * model.addObject("holiList", locationList1);
			 */

			GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));

			model.addObject("employeeInfoList", empdetList);

			try {

				month = "0";
				year = "0";
				String empId = "0";
				monthyear = "0";

				try {
					monthyear = request.getParameter("monthyear");
					String a[] = monthyear.split("-");
					month = a[0];
					year = a[1];

				} catch (Exception e) {
					month = "0";
					year = "0";
				}

				try {
					empId = request.getParameter("empId");

				} catch (Exception e) {
					empId = "0";
				}

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				/*
				 * map.add("companyId", 1); Location[] location =
				 * Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList",
				 * map, Location[].class);
				 * 
				 * List<Location> locationList = new
				 * ArrayList<Location>(Arrays.asList(location));
				 * 
				 * 
				 * model.addObject("locationList", locationList);
				 */
				model.addObject("month", (month));
				model.addObject("empId",empId);
				model.addObject("year", (year));
				model.addObject("monthyear", monthyear);
				/*
				 * model.addObject("locId", locId);
				 * 
				 * model.addObject("weekoffCatId", weekoffCatId);
				 */
				map = new LinkedMultiValueMap<>();
				map.add("month", Integer.parseInt(month));
				map.add("year", Integer.parseInt(year));
				map.add("empId", Integer.parseInt(empId));

				String[] strArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getWeeklyOffDatesToChange", map, String[].class);

				List<String> dateList = new ArrayList<String>(Arrays.asList(strArr));
				model.addObject("dateList", (dateList));
				// System.err.println("datelist"+dateList.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertWeeklyOffChange", method = RequestMethod.POST)
	public String submitInsertWeeklyOffChange(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showChangeWeekOff", "showWeekOffShift", 0, 1, 0, 0, newModuleList);
		String a = null;
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showChangeWeekOff";
			try {

				String changeDate = request.getParameter("changeDate");

				String reason = request.getParameter("reason");

				String dateFrom = request.getParameter("tempDate");
				int empId = Integer.parseInt(request.getParameter("empIdTemp"));

				Boolean ret = false;

				if (FormValidation.Validaton(changeDate, "") == true) {
					System.err.println("changeDate");
					ret = true;
				}

				if (FormValidation.Validaton(reason, "") == true) {
					System.err.println("reason");
					ret = true;
				}

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empId", empId);

				EmployeeMaster emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeById", map,
						EmployeeMaster.class);

				if (ret == false) {

					WeeklyOffShit weekShft = new WeeklyOffShit();

					weekShft.setCmpId(emp.getHolidayCategory());
					weekShft.setLocationId(emp.getLocationId());

					weekShft.setDelStatus(1);
					weekShft.setEmpId(empId);
					weekShft.setLoginTime(dateTime);
					weekShft.setMonth(Integer.parseInt(month));
					weekShft.setReason(reason);
					weekShft.setWeekofffromdate(DateConvertor.convertToYMD(dateFrom));
					weekShft.setWeekoffshiftdate(DateConvertor.convertToYMD(changeDate));
					weekShft.setYear(Integer.parseInt(year));

					WeeklyOffShit res = Constants.getRestTemplate().postForObject(Constants.url + "/saveWeeklyOffShit",
							weekShft, WeeklyOffShit.class);

					if (res != null) {
						
						map = new LinkedMultiValueMap<>();
						map.add("id", res.getId());
						map.add("userId",userObj.getUserId() );

						Info inf = Constants.getRestTemplate().postForObject(Constants.url + "/updateAttendaceOfWeeklyOffInDailyDaily", map,
								Info.class);
						
						
						session.setAttribute("successMsg", "Record Updated Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Update Record");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Update Record");
			}
		}
		return a;
	}

	@RequestMapping(value = "/showWeekOffShift", method = RequestMethod.GET)
	public ModelAndView showWeekOffShift(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView model = null;
		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showWeekOffShift", "showWeekOffShift", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/shiftedWeekOffList");
				

				GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

				List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));

				model.addObject("employeeInfoList", empdetList);

				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

				Info add = AcessController.checkAccess("showWeeklyOffList", "showWeeklyOffList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showWeeklyOffList", "showWeeklyOffList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showWeeklyOffList", "showWeeklyOffList", 0, 0, 0, 1,
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

	@RequestMapping(value = "/getWeekOffChangeDetails", method = RequestMethod.GET)
	public @ResponseBody List<GetWeekShiftChange> getLoanHistory(HttpServletRequest request,
			HttpServletResponse response) {

		List<GetWeekShiftChange> employeeInfoList = new ArrayList<GetWeekShiftChange>();

		try {

			String calYrId[] = request.getParameter("monthyear").split("-");
			String empId = request.getParameter("empId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("month", calYrId[0]);
			map.add("year", calYrId[1]);
 			map.add("empId", Integer.parseInt(empId));

			GetWeekShiftChange[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAllWeekOffShifted", map, GetWeekShiftChange[].class);

			employeeInfoList = new ArrayList<GetWeekShiftChange>(Arrays.asList(employeeInfo));
			// System.out.println("employeeInfoList" + employeeInfoList.toString());

			for (int i = 0; i < employeeInfoList.size(); i++) {

				employeeInfoList.get(i)
						.setLoginTime(FormValidation.Encrypt(String.valueOf(employeeInfoList.get(i).getId())));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeInfoList;
	}

}
