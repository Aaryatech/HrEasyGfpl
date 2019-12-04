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

import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.GetWeeklyOff;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.WeeklyOff;

@Controller
@Scope("session")
public class WeeklyOffController {

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

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("addWeeklyOff", "showWeeklyOffList", 0, 1, 0, 0,
			 * newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */
			model = new ModelAndView("master/weekly_off_add");

			// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", 1);
			Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
					Location[].class);

			List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));

			for (int i = 0; i < locationList.size(); i++) {

				locationList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(locationList.get(i).getLocId())));
			}

			model.addObject("locationList", locationList);
			//model.addObject("locationAccess", userObj.getLocationIds().split(","));
			model.addObject("locationAccess", "2,3".split(","));

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertWeeklyOff", method = RequestMethod.POST)
	public String submitInsertWeeklyOff(HttpServletRequest request, HttpServletResponse response) {

		try {
			System.out.println("inside submitInsertWeeklyOff");
			HttpSession session = request.getSession();
			// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

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
				save.setMakerUserId(1);

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

		return "redirect:/showWeeklyOffList";
	}

	@RequestMapping(value = "/showWeeklyOffList", method = RequestMethod.GET)
	public ModelAndView showWeeklyOffList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("showWeeklyOffList", "showWeeklyOffList", 1, 0,
			 * 0, 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */
			model = new ModelAndView("master/weekly_off_list");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", 1);
			//map.add("locIdList", userObj.getLocationIds());
			map.add("locIdList", "2,3");
			GetWeeklyOff[] holListArray = Constants.getRestTemplate().postForObject(Constants.url + "/getWeeklyOffList",
					map, GetWeeklyOff[].class);

			List<GetWeeklyOff> weekOffList = new ArrayList<>(Arrays.asList(holListArray));

			for (int i = 0; i < weekOffList.size(); i++) {

				weekOffList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(weekOffList.get(i).getWoId())));
			}

			model.addObject("addAccess", 0);
			model.addObject("editAccess", 0);
			model.addObject("deleteAccess", 0);
			model.addObject("weekOffList", weekOffList);
			/*
			 * Info add = AcessController.checkAccess("showWeeklyOffList",
			 * "showWeeklyOffList", 0, 1, 0, 0, newModuleList); Info edit =
			 * AcessController.checkAccess("showWeeklyOffList", "showWeeklyOffList", 0, 0,
			 * 1, 0, newModuleList); Info delete =
			 * AcessController.checkAccess("showWeeklyOffList", "showWeeklyOffList", 0, 0,
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

	@RequestMapping(value = "/deleteWeeklyOff", method = RequestMethod.GET)
	public String deleteWeeklyOff(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("deleteWeeklyOff", "showWeeklyOffList", 0, 0, 0,
			 * 1, newModuleList); if (view.isError() == true) {
			 * 
			 * a = "redirect:/accessDenied";
			 * 
			 * } else {
			 */
			a = "redirect:/showWeeklyOffList";
			String base64encodedString = request.getParameter("woId");
			String woId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("woId", woId);
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteWeeklyOff", map, Info.class);

			if (info.isError() == false) {
				session.setAttribute("successMsg", "Deleted Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Delete");
			}
			// }
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
			/*LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editWeeklyOff", "showWeeklyOffList", 0, 0, 1, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/
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
				//model.addObject("locationAccess", userObj.getLocationIds().split(","));
				model.addObject("locationAccess", "2,3".split(","));
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitEditInsertWeeklyOff", method = RequestMethod.POST)
	public String submitEditInsertWeeklyOff(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

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

		return "redirect:/showWeeklyOffList";
	}

}
