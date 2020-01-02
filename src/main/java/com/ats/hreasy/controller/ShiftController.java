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
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
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

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("getshiftList", "getshiftList", 1, 0, 0, 0, newModuleList);

			if (view.isError() == true) {

				mav = "redirect:/accessDenied";

			} else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("locationIds", userObj.getLocationIds());
				ShiftMaster[] shiftMaster = Constants.getRestTemplate()
						.postForObject(Constants.url + "/showShiftListByLocationIds", map, ShiftMaster[].class);

				List<ShiftMaster> shiftList = new ArrayList<>(Arrays.asList(shiftMaster));

				model.addAttribute("shiftList", shiftList);

				Info add = AcessController.checkAccess("getshiftList", "getshiftList", 0, 1, 0, 0, newModuleList);
				Info edit = AcessController.checkAccess("getshiftList", "getshiftList", 0, 0, 1, 0, newModuleList);
				Info delete = AcessController.checkAccess("getshiftList", "getshiftList", 0, 0, 0, 1, newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			}

			// System.out.println(month);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;

	}

	@RequestMapping(value = "/deleteShift", method = RequestMethod.GET)
	public String deleteShift(HttpServletRequest request, HttpServletResponse response) {

		String ret = new String();
		try {

			HttpSession session = request.getSession();
			int shiftId = Integer.parseInt(request.getParameter("shiftId"));

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

			Info view = AcessController.checkAccess("deleteShift", "getshiftList", 0, 0, 0, 1, newModuleList);
			if (view.isError() == true) {
				ret = "redirect:/accessDenied";
			} else {
				ret = "redirect:/getshiftList";
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("shiftId", shiftId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteShiftTime", map,
						Info.class);
				if (info.isError() == false) {
					session.setAttribute("successMsg", "Shift Deleted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Deleted Shift");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	@RequestMapping(value = "/addShift", method = RequestMethod.GET)
	public String addShift(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "master/addShift";

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("addShift", "getshiftList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				mav = "redirect:/accessDenied";

			} else {
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
			}

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

	@RequestMapping(value = "/submitShiftTiming", method = RequestMethod.POST)
	public String submitShiftTiming(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();

		String mav = "redirect:/getshiftList";

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("addShift", "getshiftList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				mav = "redirect:/accessDenied";

			} else {
				mav = "redirect:/getshiftList";

				int locationId = Integer.parseInt(request.getParameter("locId"));
				String shiftName = request.getParameter("shiftName");
				String intime = request.getParameter("intime");
				String outtime = request.getParameter("outtime");
				String hfdayhour = request.getParameter("hfdayhour");
				String othour = request.getParameter("othour");
				int lateMin = Integer.parseInt(request.getParameter("lateMin"));
				int groupId = Integer.parseInt(request.getParameter("groupId"));

				String changeWith = request.getParameter("changeWith");

				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

				ShiftMaster shiftMaster = new ShiftMaster();
				shiftMaster.setShiftname(shiftName);
				shiftMaster.setLocationId(locationId);
				shiftMaster.setTotime(outtime + ":00");
				shiftMaster.setFromtime(intime + ":00");
				shiftMaster.setMaxLateTimeAllowed(lateMin);
				shiftMaster.setStatus(1);
				shiftMaster.setSelfGroupId(groupId);
				shiftMaster.setOtCalculatedTime(outtime + ":00");
				shiftMaster.setCompanyId(1);
				String[] hfhour = hfdayhour.split(":");
				String[] ot = othour.split(":");

				shiftMaster.setShiftHalfdayHr(
						String.valueOf((Integer.parseInt(hfhour[0]) * 60) + Integer.parseInt(hfhour[1])));
				shiftMaster.setShiftOtHour((Integer.parseInt(ot[0]) * 60) + Integer.parseInt(ot[1]));

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat yyDtTm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String inDttime = sf.format(date) + " " + shiftMaster.getFromtime();
				String outDttime = sf.format(date) + " " + shiftMaster.getTotime();

				Date inDt = yyDtTm.parse(inDttime);// Set start date
				Date outDt = yyDtTm.parse(outDttime);

				if (inDt.compareTo(outDt) > 0) {
					outDt.setTime(outDt.getTime() + 1000 * 60 * 60 * 24);
				}

				long durationBetweenInOut = outDt.getTime() - inDt.getTime();
				long diffHoursBetweenInOut = durationBetweenInOut / (60 * 60 * 1000);
				long diffMinutesBetweenInOut = (durationBetweenInOut / (60 * 1000) % 60) + (diffHoursBetweenInOut * 60);

				shiftMaster.setShiftHr(String.valueOf(diffMinutesBetweenInOut));
				try {
					int ischange = Integer.parseInt(request.getParameter("ischange"));
					if (ischange == 1 && changeWith != "") {
						shiftMaster.setChangewith(Integer.parseInt(changeWith));
						shiftMaster.setChangeable(ischange);
					}
				} catch (Exception e) {

				}

				System.out.println(shiftMaster);

				ShiftMaster save = Constants.getRestTemplate().postForObject(Constants.url + "/saveShiftMaster",
						shiftMaster, ShiftMaster.class);

				if (save != null) {
					session.setAttribute("successMsg", "Shift Inserted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Inserted Shift");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Inserted Shift");
		}
		return mav;

	}

}
