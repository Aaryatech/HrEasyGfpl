package com.ats.hreasy.controller;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.common.UpateAttendaceCommon;
import com.ats.hreasy.model.GetLeaveApplyAuthwise;
import com.ats.hreasy.model.GetLeaveStatus;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveTrail;
import com.ats.hreasy.model.LoginResponse;

@Controller
@Scope("session")
public class LeaveApprovalController {

	@RequestMapping(value = "/approveLeaveByInitialAuth", method = RequestMethod.GET)
	public ModelAndView approveLeaveByInitialAuth(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/leaveApprovalRemark");
		try {

			int empId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("empId")));
			int leaveId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("leaveId")));
			String stat = request.getParameter("stat");

			model.addObject("empId", empId);
			model.addObject("leaveId", leaveId);
			model.addObject("stat", stat);

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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/approveLeaveByInitialAuth1", method = RequestMethod.POST)
	public String approveLeaveByInitialAuth1(HttpServletRequest request, HttpServletResponse response) {

		String ret = "redirect:/showLeaveApprovalByAuthority";

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

			int empId = Integer.parseInt((request.getParameter("empId")));
			int leaveId = Integer.parseInt((request.getParameter("leaveId")));
			String stat = request.getParameter("stat");
			String remark = null;
			try {
				remark = request.getParameter("remark");
			} catch (Exception e) {
				remark = "NA";
			}
			int stat1 = Integer.parseInt(stat);

			String msg = null;

			if (stat1 == 2 || stat1 == 3) {
				msg = "Approved";
			} else if (stat1 == 8 || stat1 == 9) {
				msg = "Rejected";
			} else if (stat1 == 7) {
				msg = "Cancelled";
				ret = "redirect:/showLeaveHistList?empId=" + FormValidation.Encrypt(String.valueOf(empId));
			}
			System.err.println("link data :::" + empId + leaveId + stat);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("leaveId", leaveId);
			map.add("status", stat);
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateLeaveStatus", map,
					Info.class);

			if (info.isError() == false) {
				LeaveTrail lt = new LeaveTrail();

				lt.setEmpRemarks(remark);

				lt.setLeaveId(leaveId);

				lt.setLeaveStatus(Integer.parseInt(stat));
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
					map.add("leaveId", leaveId);
					map.add("trailId", res1.getTrailPkey());
					Info info1 = Constants.getRestTemplate().postForObject(Constants.url + "/updateTrailId", map,
							Info.class);

					if (info1.isError() == false) {
						session.setAttribute("successMsg", "Leave " + msg + " Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to " + msg + " Leave");
					}

					if (stat1 == 3 || stat1 == 7) {

						map = new LinkedMultiValueMap<>();
						map.add("leaveId", leaveId);
						GetLeaveApplyAuthwise lvEmp = Constants.getRestTemplate().postForObject(
								Constants.url + "/getLeaveApplyDetailsByLeaveId", map, GetLeaveApplyAuthwise.class);

						UpateAttendaceCommon upateAttendaceCommon = new UpateAttendaceCommon();
						Info updateAttendaceInfo = upateAttendaceCommon.changeInDailyDailyAfterLeaveTransaction(
								DateConvertor.convertToDMY(lvEmp.getLeaveFromdt()),
								DateConvertor.convertToDMY(lvEmp.getLeaveTodt()), empId, userObj.getUserId());

					}

				}
			}

			else {
				session.setAttribute("errorMsg", "Failed to " + msg + " Record");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;

	}

}
