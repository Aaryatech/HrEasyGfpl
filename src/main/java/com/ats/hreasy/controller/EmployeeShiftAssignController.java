package com.ats.hreasy.controller;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.common.HoursConversion;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.HolidayCategory;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.MstEmpType;
import com.ats.hreasy.model.SalaryTypesMaster;
import com.ats.hreasy.model.ShiftMaster;
import com.ats.hreasy.model.WeekoffCategory;

@Controller
@Scope("session")
public class EmployeeShiftAssignController {
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	String curDate = dateFormat.format(new Date());
	String dateTime = dateFormat.format(now);
	MstEmpType editMstType = new MstEmpType();

	@RequestMapping(value = "/showEmpListToAssignShift", method = RequestMethod.GET)
	public ModelAndView showEmpListToAssignShift(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showEmpListToAssignShift", "showEmpListToAssignShift", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("master/assignShiftToEmp");

			try {

				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
						Location[].class);

				List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));
				model.addObject("locationList", locationList);
				model.addObject("locationAccess", userObj.getLocationIds().split(","));

				try {
					int locationId = Integer.parseInt(request.getParameter("locId"));

					map = new LinkedMultiValueMap<String, Object>();
					map.add("locationIds", locationId);

					List<ShiftMaster> shiftList = new ArrayList<>();
					GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
							.postForObject(Constants.url + "/getEmpDetailListByLocId", map, GetEmployeeDetails[].class);

					List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
					model.addObject("empdetList", empdetList);

					ShiftMaster[] shiftMaster = Constants.getRestTemplate()
							.postForObject(Constants.url + "/showShiftListByLocationIds", map, ShiftMaster[].class);
					shiftList = new ArrayList<ShiftMaster>(Arrays.asList(shiftMaster));
					model.addObject("shiftList", shiftList);
					model.addObject("locationId", locationId);
				} catch (Exception e) {

					// e.printStackTrace();
				}

				// System.err.println("sh list"+shiftList.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/submitAssignShiftToEmp", method = RequestMethod.POST)
	public String addCustLoginDetail(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {

			String shiftId = null;
			try {
				shiftId = request.getParameter("shiftId");
			} catch (Exception e) {
				e.printStackTrace();

			}
			// System.out.println("work date**" + workDate);

			String[] empId = request.getParameterValues("empId");

			StringBuilder sb = new StringBuilder();

			List<Integer> empIdList = new ArrayList<>();

			for (int i = 0; i < empId.length; i++) {
				sb = sb.append(empId[i] + ",");
				empIdList.add(Integer.parseInt(empId[i]));

				// System.out.println("empId id are**" + empId[i]);

			}

			String items = sb.toString();

			items = items.substring(0, items.length() - 1);

			StringBuilder sbEmp = new StringBuilder();

			// System.out.println("empId id are**" + empIdList.toString());
			// System.out.println("shiftId id are**" + shiftId);

			map.add("empIdList", items);
			map.add("shiftId", shiftId);

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/shiftAssignmentUpdate", map,
					Info.class);

		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showEmpListToAssignShift";
	}

	@RequestMapping(value = "/showEmpListToAssignSalStruct", method = RequestMethod.GET)
	public ModelAndView showEmpListToAssignSalStruct(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showEmpListToAssignSalStruct", "showEmpListToAssignSalStruct", 1, 0, 0,
				0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("master/assignSalStructToEmp");

			try {
				List<SalaryTypesMaster> shiftList = new ArrayList<>();
				GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllEmployeeDetail", GetEmployeeDetails[].class);

				List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
				model.addObject("empdetList", empdetList);

				SalaryTypesMaster[] empdetList2 = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getSalryTypesMst", SalaryTypesMaster[].class);

				shiftList = new ArrayList<SalaryTypesMaster>(Arrays.asList(empdetList2));
				model.addObject("shiftList", shiftList);
				System.err.println("sh list" + shiftList.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/submitAssignSalStructToEmp", method = RequestMethod.POST)
	public String submitAssignSalStructToEmp(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {

			String shiftId = null;
			try {
				shiftId = request.getParameter("shiftId");
			} catch (Exception e) {
				e.printStackTrace();

			}
			// System.out.println("work date**" + workDate);

			String[] empId = request.getParameterValues("empId");

			StringBuilder sb = new StringBuilder();

			List<Integer> empIdList = new ArrayList<>();

			for (int i = 0; i < empId.length; i++) {
				sb = sb.append(empId[i] + ",");
				empIdList.add(Integer.parseInt(empId[i]));

				// System.out.println("empId id are**" + empId[i]);

			}

			String items = sb.toString();

			items = items.substring(0, items.length() - 1);

			StringBuilder sbEmp = new StringBuilder();

			// System.out.println("empId id are**" + empIdList.toString());
			// System.out.println("shiftId id are**" + shiftId);

			map.add("empIdList", items);
			map.add("structId", shiftId);

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/salStructAssignmentUpdate", map,
					Info.class);

		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showEmpListToAssignSalStruct";
	}

	////

	@RequestMapping(value = "/mstEmpTypeAdd", method = RequestMethod.GET)
	public ModelAndView mstEmpTypeAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("mstEmpTypeAdd", "showMstEmpTypeList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/mstEmpTypeAdd");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitMstEmpTypeAdd", method = RequestMethod.POST)
	public String submitMstEmpTypeAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("mstEmpTypeAdd", "showMstEmpTypeList", 0, 1, 0, 0, newModuleList);
		String a = null;
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showMstEmpTypeList";

			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String lateMark = request.getParameter("lateMark");
				String weekOffWork = request.getParameter("weekOffWork");
				String otType = null;
				String minWorkHr = request.getParameter("minHr");
				String otApplicable = request.getParameter("otApplicable");
				if (otApplicable.equals("Yes")) {
					otType = request.getParameter("otType");
				} else {
					otType = "";
				}
				String typeName = request.getParameter("typeName");
				String halfDayDed = request.getParameter("halfDayDed");
				String minWorkRule = request.getParameter("minWorkRule");
				String woRemarks = request.getParameter("woRemarks");

				Boolean ret = false;

				if (FormValidation.Validaton(lateMark, "") == true) {

					ret = true;
					System.out.println("lateMark" + ret);
				}
				if (FormValidation.Validaton(weekOffWork, "") == true) {

					ret = true;
					System.out.println("weekOffWork" + ret);
				}

				if (FormValidation.Validaton(minWorkHr, "") == true) {

					ret = true;
					System.out.println("minWorkHr" + ret);
				}
				if (FormValidation.Validaton(otApplicable, "") == true) {

					ret = true;
					System.out.println("otApplicable" + ret);
				}
				if (FormValidation.Validaton(typeName, "") == true) {

					ret = true;
					System.out.println("typeName" + ret);
				}
				if (FormValidation.Validaton(halfDayDed, "") == true) {

					ret = true;
					System.out.println("halfDayDed" + ret);
				}
				if (FormValidation.Validaton(minWorkRule, "") == true) {

					ret = true;
					System.out.println("minWorkRule" + ret);
				}
				if (FormValidation.Validaton(woRemarks, "") == true) {

					ret = true;
					System.out.println("woRemarks" + ret);
				}
				//System.err.println("minWorkHr"+minWorkHr);

				if (ret == false) {
					//String mnghr1 = HoursConversion.convertHoursToMin(minWorkHr);
					//System.err.println("mnghr1"+mnghr1);
					MstEmpType mstEmpType = new MstEmpType();

					mstEmpType.setAttType("0");
					mstEmpType.setCategory("0");
					mstEmpType.setName(typeName);
					;
					mstEmpType.setCompanyId(1);
					mstEmpType.setDetails(woRemarks);
					;
					mstEmpType.setHalfDay(halfDayDed);
					mstEmpType.setLmApplicable(lateMark);
					mstEmpType.setOtApplicable(otApplicable);
					mstEmpType.setOtType(otType);
					mstEmpType.setDelStatus(1);
					mstEmpType.setWhWork(weekOffWork);
					mstEmpType.setMinWorkHr(minWorkHr);
					// mstEmpType.setEarlyGoingAllowed();
					// .setEarlyGoingMin(earlyGoingMin);
					// mstEmpType.setEmpTypeId(1);
					// mstEmpType.setMaxLateTimeAllowed(maxLateTimeAllowed);
					mstEmpType.setMinworkApplicable(minWorkRule);
					mstEmpType.setOtTime("0");
					mstEmpType.setMaxLateTimeAllowed(0);
					// mstEmpType.setStatus(status);
					// mstEmpType.setWeeklyHolidayLateAllowed(weeklyHolidayLateAllowed);
					// mstEmpType.setWeeklyHolidayLateAllowedMin(weeklyHolidayLateAllowedMin);

					// mstEmpType.setOtTime(otTime);
					// mstEmpType.setEmpTypeId(empTypeId);

					MstEmpType res = Constants.getRestTemplate().postForObject(Constants.url + "/saveMstEmpType",
							mstEmpType, MstEmpType.class);

					if (res != null) {
						session.setAttribute("successMsg", "Employee Type Inserted Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Insert Record");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Insert Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}
		}
		return a;
	}

	@RequestMapping(value = "/showMstEmpTypeList", method = RequestMethod.GET)
	public ModelAndView showMstEmpTypeList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showMstEmpTypeList", "showMstEmpTypeList", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/mstEmpTypeList");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				MstEmpType[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getMstEmpTypeList",
						map, MstEmpType[].class);

				List<MstEmpType> locationList = new ArrayList<MstEmpType>(Arrays.asList(location));

				model.addObject("locationList", locationList);

				for (int i = 0; i < locationList.size(); i++) {

					locationList.get(i)
							.setAttType(FormValidation.Encrypt(String.valueOf(locationList.get(i).getEmpTypeId())));
					//editMstType.setMinWorkHr(HoursConversion.convertMinToHours(editMstType.getMinWorkHr()));

					/*
					 * locationList.get(i)
					 * .setMinWorkHr(HoursConversion.convertMinToHours(locationList.get(i).
					 * getMinWorkHr()));
					 */
				}

				Info add = AcessController.checkAccess("showMstEmpTypeList", "showMstEmpTypeList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showMstEmpTypeList", "showMstEmpTypeList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showMstEmpTypeList", "showMstEmpTypeList", 0, 0, 0, 1,
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

	@RequestMapping(value = "/deleteLMstEmpType", method = RequestMethod.GET)
	public String deleteLocation(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

			Info view = AcessController.checkAccess("deleteLMstEmpType", "showMstEmpTypeList", 0, 0, 0, 1,
					newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			}

			else {

				a = "redirect:/showMstEmpTypeList";

				String base64encodedString = request.getParameter("empTypeId");
				String empTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empTypeId", empTypeId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteLMstEmpType", map,
						Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", info.getMsg());
				} else {
					session.setAttribute("errorMsg", info.getMsg());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		return a;
	}

	@RequestMapping(value = "/editMstType", method = RequestMethod.GET)
	public ModelAndView editMstType(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editMstType", "showMstEmpTypeList", 0, 0, 1, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/mstEmpTypeEdit");
				String base64encodedString = request.getParameter("empTypeId");
				String empTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empTypeId", empTypeId);
				editMstType = Constants.getRestTemplate().postForObject(Constants.url + "/getMstEmpTypeById", map,
						MstEmpType.class);

				/*
				 * editMstType.setMinWorkHr(HoursConversion.convertMinToHours(editMstType.
				 * getMinWorkHr()));
				 */
				model.addObject("employee", editMstType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitEditMstEmpTypeAdd", method = RequestMethod.POST)
	public String submitEditMstEmpTypeAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		String a = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editMstType", "showMstEmpTypeList", 0, 1, 0, 0, newModuleList);
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showMstEmpTypeList";
			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String lateMark = request.getParameter("lateMark");
				String weekOffWork = request.getParameter("weekOffWork");
				String otType = null;
				String minWorkHr = request.getParameter("minHr");
				String otApplicable = request.getParameter("otApplicable");
				String typeName = request.getParameter("typeName");
				String halfDayDed = request.getParameter("halfDayDed");
				String minWorkRule = request.getParameter("minWorkRule");
				String woRemarks = request.getParameter("woRemarks");
				String empTypeId = request.getParameter("empTypeId");
				if (otApplicable.equals("Yes")) {
					otType = request.getParameter("otType");
				} else {
					otType = "";
				}

				System.err.println("empTypeId" + empTypeId);

				Boolean ret = false;

				if (FormValidation.Validaton(lateMark, "") == true) {

					ret = true;
					System.out.println("lateMark" + ret);
				}
				if (FormValidation.Validaton(weekOffWork, "") == true) {

					ret = true;
					System.out.println("weekOffWork" + ret);
				}

				if (FormValidation.Validaton(minWorkHr, "") == true) {

					ret = true;
					System.out.println("minWorkHr" + ret);
				}
				if (FormValidation.Validaton(otApplicable, "") == true) {

					ret = true;
					System.out.println("otApplicable" + ret);
				}
				if (FormValidation.Validaton(typeName, "") == true) {

					ret = true;
					System.out.println("typeName" + ret);
				}
				if (FormValidation.Validaton(halfDayDed, "") == true) {

					ret = true;
					System.out.println("halfDayDed" + ret);
				}
				if (FormValidation.Validaton(minWorkRule, "") == true) {

					ret = true;
					System.out.println("minWorkRule" + ret);
				}
				if (FormValidation.Validaton(woRemarks, "") == true) {

					ret = true;
					System.out.println("woRemarks" + ret);
				}

				if (ret == false) {
					//String mnghr1 = HoursConversion.convertHoursToMin(minWorkHr);
					MstEmpType mstEmpType = new MstEmpType();

					mstEmpType.setAttType("0");
					mstEmpType.setCategory("0");
					mstEmpType.setName(typeName);
					;
					mstEmpType.setCompanyId(1);
					mstEmpType.setDetails(woRemarks);
					;
					mstEmpType.setHalfDay(halfDayDed);
					mstEmpType.setLmApplicable(lateMark);
					mstEmpType.setOtApplicable(otApplicable);
					mstEmpType.setOtType(otType);
					mstEmpType.setDelStatus(1);
					mstEmpType.setWhWork(weekOffWork);
					mstEmpType.setEmpTypeId(Integer.parseInt(empTypeId));
					mstEmpType.setMinWorkHr(minWorkHr);
					// mstEmpType.setEarlyGoingAllowed();
					// .setEarlyGoingMin(earlyGoingMin);
					// mstEmpType.setEmpTypeId(1);
					// mstEmpType.setMaxLateTimeAllowed(maxLateTimeAllowed);
					mstEmpType.setMinworkApplicable(minWorkRule);
					mstEmpType.setOtTime("0");
					mstEmpType.setMaxLateTimeAllowed(0);
					// mstEmpType.setStatus(status);
					// mstEmpType.setWeeklyHolidayLateAllowed(weeklyHolidayLateAllowed);
					// mstEmpType.setWeeklyHolidayLateAllowedMin(weeklyHolidayLateAllowedMin);

					// mstEmpType.setOtTime(otTime);
					// mstEmpType.setEmpTypeId(empTypeId);

					MstEmpType res = Constants.getRestTemplate().postForObject(Constants.url + "/saveMstEmpType",
							mstEmpType, MstEmpType.class);

					if (res != null) {
						session.setAttribute("successMsg", "Employee Type Inserted Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Insert Record");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Insert Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}
		}

		return "redirect:/showMstEmpTypeList";
	}

	
	
	@RequestMapping(value = "/assignWeekoffCategory", method = RequestMethod.GET)
	public String assignWeekoffCategory(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String ret = new String();
		/*
		 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
		 * session.getAttribute("moduleJsonList"); Info view =
		 * AcessController.checkAccess("showEmpListToAssignSalStruct",
		 * "showEmpListToAssignSalStruct", 1, 0, 0, 0, newModuleList);
		 * 
		 * if (view.isError() == true) {
		 * 
		 * model = new ModelAndView("accessDenied");
		 * 
		 * } else {
		 */
		ret = "master/assignWeekoffCategory";

		try {

			GetEmployeeDetails[] empdetList1 = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAllEmplistForWeekOffCatAssign", GetEmployeeDetails[].class);

			List<GetEmployeeDetails> empdetList = new ArrayList<GetEmployeeDetails>(Arrays.asList(empdetList1));
			model.addAttribute("empdetList", empdetList);

			WeekoffCategory[] location1 = Constants.getRestTemplate().getForObject(Constants.url + "/getWeekoffCategoryList",
					WeekoffCategory[].class);

			List<WeekoffCategory> locationList1 = new ArrayList<WeekoffCategory>(Arrays.asList(location1));
 
			model.addAttribute("holiList", locationList1);

 
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }
		return ret;
	}
	
	
	@RequestMapping(value = "/submitAssignWeekoffCatToEmp", method = RequestMethod.POST)
	public String submitAssignHolidayCatToEmp(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {

			String holiCatId = null;
			try {
				holiCatId = request.getParameter("holiCatId");
			} catch (Exception e) {
				e.printStackTrace();

			}
			// System.out.println("work date**" + workDate);

			String[] empId = request.getParameterValues("empId");

			StringBuilder sb = new StringBuilder();

			List<Integer> empIdList = new ArrayList<>();

			for (int i = 0; i < empId.length; i++) {
				sb = sb.append(empId[i] + ",");
				empIdList.add(Integer.parseInt(empId[i]));

				// System.out.println("empId id are**" + empId[i]);

			}

			String items = sb.toString();

			items = items.substring(0, items.length() - 1);

			StringBuilder sbEmp = new StringBuilder();

			// System.out.println("empId id are**" + empIdList.toString());
			// System.out.println("shiftId id are**" + shiftId);

			map.add("empIdList", items);
			map.add("holiCatId", holiCatId);

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/weekoffCatAssignmentUpdate", map,
					Info.class);

		} catch (Exception e) {
			System.err.println("Exce in Saving Cust Login Detail " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/assignWeekoffCategory";
	}
}
