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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.GetWeekShiftChange;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.SelfGroup;
import com.ats.hreasy.model.ShiftMaster;

@Controller
@Scope("session")
public class ShiftController {

	@RequestMapping(value = "/getshiftList", method = RequestMethod.GET)
	public String attendenceImportExel(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "master/showShiftList";

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("locationIds", userObj.getLocationIds());
			ShiftMaster[] shiftMaster = Constants.getRestTemplate()
					.postForObject(Constants.url + "/showShiftListByLocationIds", map, ShiftMaster[].class);

			List<ShiftMaster> shiftList = new ArrayList<>(Arrays.asList(shiftMaster));

			model.addAttribute("shiftList", shiftList);
			model.addAttribute("addAccess", 0);
			model.addAttribute("editAccess", 0);
			model.addAttribute("deleteAccess", 0);
			// System.out.println(month);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;

	}

	@RequestMapping(value = "/deleteShift", method = RequestMethod.GET)
	public String deleteShift(HttpServletRequest request, HttpServletResponse response) {

		try {

			HttpSession session = request.getSession();
			int shiftId = Integer.parseInt(request.getParameter("shiftId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("shiftId", shiftId);
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteShiftTime", map, Info.class);
			if (info.isError() == false) {
				session.setAttribute("successMsg", "Shift Deleted Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Deleted Shift");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/getshiftList";

	}

	@RequestMapping(value = "/addShift", method = RequestMethod.GET)
	public String addShift(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "master/addShift";

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", 1);
			Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
					Location[].class);

			List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));
			model.addAttribute("locationList", locationList);
			model.addAttribute("locationAccess", userObj.getLocationIds().split(","));

			SelfGroup[] selfGroup = Constants.getRestTemplate().getForObject(Constants.url + "/getSelftGroupList",
					SelfGroup[].class);

			List<SelfGroup> selfGroupList = new ArrayList<SelfGroup>(Arrays.asList(selfGroup));
			model.addAttribute("selfGroupList", selfGroupList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;

	}

	@RequestMapping(value = "/getShiftListByLocationIdAndSelftGroupId", method = RequestMethod.GET)
	public @ResponseBody List<ShiftMaster> getShiftListByLocationIdAndSelftGroupId(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		List<ShiftMaster> shiftMasterList = new ArrayList<ShiftMaster>();

		try {

			int locationId = Integer.parseInt(request.getParameter("locationId"));
			int groupId = Integer.parseInt(request.getParameter("groupId"));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("locationId", locationId);
			map.add("groupId", groupId);
			ShiftMaster[] selfGroup = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getShiftListByGroupIdandlocId", map, ShiftMaster[].class);

			shiftMasterList = new ArrayList<ShiftMaster>(Arrays.asList(selfGroup));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return shiftMasterList;

	}

}
