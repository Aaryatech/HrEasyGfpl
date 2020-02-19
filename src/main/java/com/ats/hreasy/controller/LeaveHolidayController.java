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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.CalenderYear;
import com.ats.hreasy.model.GetHoliday;
import com.ats.hreasy.model.Holiday;
import com.ats.hreasy.model.HolidayCategory;
import com.ats.hreasy.model.HolidayMaster;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;

@Controller
@Scope("session")
public class LeaveHolidayController {
	Holiday editHoliday = new Holiday();

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	String curDate = dateFormat.format(new Date());
	String dateTime = dateFormat.format(now);

	List<HolidayMaster> holiList = new ArrayList<>();

	@RequestMapping(value = "/holidayAdd", method = RequestMethod.GET)
	public ModelAndView holidayAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("holidayAdd", "showHolidayList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/holiday");

				// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				/*
				 * map.add("companyId", 1); Location[] location =
				 * Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList",
				 * map, Location[].class);
				 * 
				 * List<Location> locationList = new
				 * ArrayList<Location>(Arrays.asList(location));
				 * 
				 * for (int i = 0; i < locationList.size(); i++) {
				 * 
				 * locationList.get(i)
				 * .setExVar1(FormValidation.Encrypt(String.valueOf(locationList.get(i).getLocId
				 * ()))); }
				 * 
				 * model.addObject("locationList", locationList);
				 */

				HolidayMaster[] holListArray = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getHolidayMaster", HolidayMaster[].class);

				holiList = new ArrayList<>(Arrays.asList(holListArray));
				model.addObject("holiList", holiList);

				map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				HolidayCategory[] holi = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getHolidayCategoryList", map, HolidayCategory[].class);

				List<HolidayCategory> holidayCatList = new ArrayList<HolidayCategory>(Arrays.asList(holi));

				model.addObject("holidayCatList", holidayCatList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertHoliday", method = RequestMethod.POST)
	public String submitInsertHoliday(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("holidayAdd", "showHolidayList", 0, 1, 0, 0, newModuleList);
		String a = null;
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showHolidayList";
			try {

				String[] holidayIds = request.getParameterValues("locId");
				int holcatId = Integer.parseInt(request.getParameter("hoCatId"));
				String holidayRemark = request.getParameter("holidayRemark");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
						Location[].class);

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < location.length; i++) {
					sb = sb.append(location[i].getLocId() + ",");

				}
				String locIdList = sb.toString();
				locIdList = locIdList.substring(0, locIdList.length() - 1);

				CalenderYear calculateYear = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getCalculateYearListIsCurrent", CalenderYear.class);

				StringBuilder dates = new StringBuilder();

				List<HolidayMaster> newHoliList = new ArrayList<>();

				for (int j = 0; j < holidayIds.length; j++) {

					for (int i = 0; i < holiList.size(); i++) {

						if (Integer.parseInt(holidayIds[j]) == holiList.get(i).getHolidayId()) {

							dates = dates.append(DateConvertor.convertToYMD(holiList.get(i).getHolidayDate()) + ",");
							newHoliList.add(holiList.get(i));
							break;
						}
					}

				}
				String holidayDates = dates.toString();
				holidayDates = holidayDates.substring(0, holidayDates.length() - 1);

				map = new LinkedMultiValueMap<>();
				map.add("dates", holidayDates);
				map.add("holcatId", holcatId);
				Holiday[] holidayRes = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getHolidayListByDates", map, Holiday[].class);

				List<Holiday> saveList = new ArrayList<>();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

				for (int i = 0; i < newHoliList.size(); i++) {

					Date date1 = dd.parse(newHoliList.get(i).getHolidayDate());
					int flag = 0;

					for (int j = 0; j < holidayRes.length; j++) {
						Date date2 = sf.parse(holidayRes[j].getHolidayFromdt());
						if (date1.compareTo(date2) == 0) {
							flag = 1;
							break;
						}

					}

					if (flag == 0) {

						Holiday holiday = new Holiday();
						holiday.setCalYrId(calculateYear.getCalYrId());
						holiday.setCompanyId(1);
						holiday.setDelStatus(1);

						holiday.setExVar1("NA");
						holiday.setExVar2(newHoliList.get(i).getHolidayName());
						holiday.setExVar3("NA");
						holiday.setHolidayFromdt(DateConvertor.convertToYMD(newHoliList.get(i).getHolidayDate()));
						holiday.setHolidayTodt(DateConvertor.convertToYMD(newHoliList.get(i).getHolidayDate()));
						holiday.setHolidayRemark(holidayRemark);
						holiday.setIsActive(1);
						holiday.setLocId(locIdList);
						holiday.setMakerEnterDatetime(dateTime);
						holiday.setMakerUserId(userObj.getUserId());
						holiday.setExInt1(holcatId);
						holiday.setExInt2(newHoliList.get(i).getHolidayId());
						saveList.add(holiday);

					}
				}

				// System.out.println(saveList);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/saveHolidayList", saveList,
						Info.class);

				if (res.isError() == false) {
					session.setAttribute("successMsg", "Transaction Successfully");
				} else {
					session.setAttribute("errorMsg", "Transaction Failed Failed");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return a;
	}

	@RequestMapping(value = "/editHoliday", method = RequestMethod.GET)
	public ModelAndView editHoliday(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editHoliday", "showHolidayList", 0, 0, 1, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/holiday_edit");

				// LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

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

				map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				HolidayCategory[] holi = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getHolidayCategoryList", map, HolidayCategory[].class);

				List<HolidayCategory> holiList = new ArrayList<HolidayCategory>(Arrays.asList(holi));

				model.addObject("holiList", holiList);
				// getCalculateYearList

				/*
				 * CalenderYear[] calculateYear = Constants.getRestTemplate()
				 * .getForObject(Constants.url + "/getCalculateYearListIsCurrent",
				 * CalenderYear[].class);
				 * 
				 * List<CalenderYear> yearList = new ArrayList<>(Arrays.asList(calculateYear));
				 * 
				 * model.addObject("yearList", yearList);
				 */
				String base64encodedString = request.getParameter("holidayId");
				String holidayId = FormValidation.DecodeKey(base64encodedString);

				map = new LinkedMultiValueMap<>();
				map.add("holidayId", holidayId);
				editHoliday = Constants.getRestTemplate().postForObject(Constants.url + "/getHolidayById", map,
						Holiday.class);
				model.addObject("editHoliday", editHoliday);
				System.out.println(editHoliday);
				editHoliday.setHolidayFromdt(DateConvertor.convertToDMY(editHoliday.getHolidayFromdt()));
				editHoliday.setHolidayTodt(DateConvertor.convertToDMY(editHoliday.getHolidayTodt()));
				System.out.println(editHoliday);
				List<Integer> locIdList = Stream.of(editHoliday.getLocId().split(",")).map(Integer::parseInt)
						.collect(Collectors.toList());

				model.addObject("locIdList", locIdList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/showHolidayList", method = RequestMethod.GET)
	public ModelAndView showHolidayList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		try {
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showHolidayList", "showHolidayList", 1, 0, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/holiday_list");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				GetHoliday[] holListArray = Constants.getRestTemplate().postForObject(Constants.url + "/getHolidayList",
						map, GetHoliday[].class);

				List<GetHoliday> holList = new ArrayList<>(Arrays.asList(holListArray));

				for (int i = 0; i < holList.size(); i++) {

					holList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(holList.get(i).getHolidayId())));
				}

				model.addObject("holList", holList);
				Info add = AcessController.checkAccess("showHolidayList", "showHolidayList", 0, 1, 0, 0, newModuleList);
				Info edit = AcessController.checkAccess("showHolidayList", "showHolidayList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showHolidayList", "showHolidayList", 0, 0, 0, 1,
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
				// System.out.println("HolidayList" + holList.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitEditHoliday", method = RequestMethod.POST)
	public String submitEditHoliday(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editHoliday", "showHolidayList", 0, 0, 1, 0, newModuleList);
		String a = null;
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showHolidayList";
			try {

				String dateRange = request.getParameter("dateRange");
				String[] arrOfStr = dateRange.split("to", 2);

				String holidayRemark = request.getParameter("holidayRemark");
				String holidayTitle = request.getParameter("holidayTitle");

				String[] locIds = request.getParameterValues("locId");

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < locIds.length; i++) {
					sb = sb.append(locIds[i] + ",");

				}
				String locIdList = sb.toString();
				locIdList = locIdList.substring(0, locIdList.length() - 1);

				Boolean ret = false;

				if (FormValidation.Validaton(dateRange, "") == true) {

					ret = true;

				}

				if (FormValidation.Validaton(holidayTitle, "") == true) {

					ret = true;

				}

				if (ret == false) {

					editHoliday.setHolidayFromdt(DateConvertor.convertToYMD(arrOfStr[0].toString().trim()));
					editHoliday.setHolidayTodt(DateConvertor.convertToYMD(arrOfStr[1].toString().trim()));
					editHoliday.setHolidayRemark(holidayRemark);
					editHoliday.setLocId(locIdList);
					editHoliday.setExVar2(holidayTitle);

					editHoliday.setExInt1(Integer.parseInt(request.getParameter("hoCatId")));
					Holiday res = Constants.getRestTemplate().postForObject(Constants.url + "/saveHoliday", editHoliday,
							Holiday.class);

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
				session.setAttribute("errorMsg", "Failed to Update Record");
			}
		}

		return a;
	}

	@RequestMapping(value = "/deleteHoliday", method = RequestMethod.GET)
	public String deleteHoliday(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("deleteHoliday", "showHolidayList", 0, 0, 0, 1, newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			} else {

				a = "redirect:/showHolidayList";

				String base64encodedString = request.getParameter("holidayId");
				String holidayId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("holidayId", holidayId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteHoliday", map,
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

	@RequestMapping(value = "/addHolidayMaster", method = RequestMethod.GET)
	public ModelAndView addHolidayMaster(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("addHolidayMaster", "showHolidayMasterList", 0, 1, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/addHolidayMaster");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertHolidayMaster", method = RequestMethod.POST)
	public String submitInsertHolidayMaster(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		String a = new String();

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("submitInsertHolidayMaster", "showHolidayMasterList", 0, 1, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showHolidayMasterList";
			try {

				String dateRange = request.getParameter("dateRange");
				String holidayTitle = request.getParameter("holidayTitle");

				Boolean ret = false;

				if (FormValidation.Validaton(dateRange, "") == true) {

					ret = true;

				}

				if (FormValidation.Validaton(holidayTitle, "") == true && ret == false) {

					ret = true;

				}

				if (ret == false) {

					HolidayMaster holiday = new HolidayMaster();
					holiday.setDelStatus(1);
					holiday.setHolidayName(holidayTitle);
					holiday.setHolidayDate(dateRange);

					HolidayMaster res = Constants.getRestTemplate().postForObject(Constants.url + "/saveHolidayMaster",
							holiday, HolidayMaster.class);

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

	@RequestMapping(value = "/showHolidayMasterList", method = RequestMethod.GET)
	public ModelAndView showHolidayMasterList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		try {
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showHolidayMasterList", "showHolidayMasterList", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/showHolidayMasterList");

				HolidayMaster[] holListArray = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getHolidayMaster", HolidayMaster[].class);

				List<HolidayMaster> holList = new ArrayList<>(Arrays.asList(holListArray));

				model.addObject("holList", holList);
				Info add = AcessController.checkAccess("showHolidayMasterList", "showHolidayMasterList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showHolidayMasterList", "showHolidayMasterList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showHolidayMasterList", "showHolidayMasterList", 0, 0, 0, 1,
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
				// System.out.println("HolidayList" + holList.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/deleteHolidayMaster", method = RequestMethod.GET)
	public String deleteHolidayMaster(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("deleteHolidayMaster", "showHolidayMasterList", 0, 0, 0, 1,
					newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			} else {

				a = "redirect:/showHolidayMasterList";

				int holidayId = Integer.parseInt(request.getParameter("holidayId"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("holidayId", holidayId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteHolidayMaster", map,
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

	HolidayMaster editHolidayMaster = new HolidayMaster();

	@RequestMapping(value = "/editHolidayMaster", method = RequestMethod.GET)
	public ModelAndView editHolidayMaster(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editHolidayMaster", "showHolidayMasterList", 0, 0, 1, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/editHolidayMaster");

				int holidayId = Integer.parseInt(request.getParameter("holidayId"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map = new LinkedMultiValueMap<>();
				map.add("holidayId", holidayId);
				editHolidayMaster = Constants.getRestTemplate().postForObject(Constants.url + "/getHolidayMasterById",
						map, HolidayMaster.class);
				model.addObject("editHoliday", editHolidayMaster);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitEditHolidayMaster", method = RequestMethod.POST)
	public String submitEditHolidayMaster(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		String a = new String();

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("submitInsertHolidayMaster", "showHolidayMasterList", 0, 1, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showHolidayMasterList";
			try {

				String dateRange = request.getParameter("dateRange");
				String holidayTitle = request.getParameter("holidayTitle");

				Boolean ret = false;

				if (FormValidation.Validaton(dateRange, "") == true) {

					ret = true;

				}

				if (FormValidation.Validaton(holidayTitle, "") == true && ret == false) {

					ret = true;

				}

				if (ret == false) {

					editHolidayMaster.setHolidayName(holidayTitle);
					editHolidayMaster.setHolidayDate(dateRange);

					HolidayMaster res = Constants.getRestTemplate().postForObject(Constants.url + "/saveHolidayMaster",
							editHolidayMaster, HolidayMaster.class);

					if (res != null) {
						session.setAttribute("successMsg", "Record Updated Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Updated Record");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Updated Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return a;
	}

}
