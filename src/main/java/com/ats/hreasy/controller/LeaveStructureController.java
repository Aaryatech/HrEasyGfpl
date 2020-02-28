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
import org.springframework.ui.Model;
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
import com.ats.hreasy.model.Allowances;
import com.ats.hreasy.model.CalenderYear;
import com.ats.hreasy.model.EmpBasicAllownceForLeaveInCash;
import com.ats.hreasy.model.EmpInfo;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetEmployeeDetails;
import com.ats.hreasy.model.GetLeaveAuthority;
import com.ats.hreasy.model.GetStructureAllotment;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveAuthority;
import com.ats.hreasy.model.LeaveBalanceCal;
import com.ats.hreasy.model.LeaveCashReport;
import com.ats.hreasy.model.LeaveHistory;
import com.ats.hreasy.model.LeaveStructureDetails;
import com.ats.hreasy.model.LeaveStructureHeader;
import com.ats.hreasy.model.LeaveType;
import com.ats.hreasy.model.LeavesAllotment;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.Setting;

@Controller
@Scope("session")
public class LeaveStructureController {

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	String curDate = dateFormat.format(new Date());
	String dateTime = dateFormat.format(now);
	List<LeaveStructureDetails> tempDetailList = new ArrayList<LeaveStructureDetails>();
	LeaveAuthority leaveAuthority = new LeaveAuthority();

	List<LeaveType> leaveTypeList;

	@RequestMapping(value = "/addLeaveStructure", method = RequestMethod.GET)
	public ModelAndView addLeaveStructure(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("addLeaveStructure", "showLeaveStructureList", 0, 1, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("leave/add_leave_structure");
				tempDetailList = new ArrayList<LeaveStructureDetails>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				LeaveType[] leaveArray = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLeaveTypeListIsStructure", map, LeaveType[].class);

				leaveTypeList = new ArrayList<>(Arrays.asList(leaveArray));

				model.addObject("leaveTypeList", leaveTypeList);

				model.addObject("title", "Add Leave Structure");

				Allowances[] allowanceArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllAllowances", Allowances[].class);
				List<Allowances> allowanceList = new ArrayList<Allowances>(Arrays.asList(allowanceArr));
				model.addObject("allowanceList", allowanceList);
			}

		} catch (Exception e) {

			System.err.println("exception In addLeaveStructureHeader at leave structure Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertLeaveStructure", method = RequestMethod.POST)
	public String insertLeaveStructure(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		String a = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("addLeaveStructure", "showLeaveStructureList", 0, 0, 1, 0,
				newModuleList);
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showLeaveStructureList";
			try {

				String lvsName = request.getParameter("lvsName");
				String[] allownsIds = request.getParameterValues("allowanceIds");

				Boolean ret = false;

				if (FormValidation.Validaton(lvsName, "") == true) {

					ret = true;

				}

				if (ret == false) {

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("limitKey", "default_previlage");
					Setting defaultPrv = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey",
							map, Setting.class);

					LeaveStructureHeader head = new LeaveStructureHeader();
					List<LeaveStructureDetails> detailList = new ArrayList<>();

					head.setCompanyId(1);
					head.setDelStatus(1);
					head.setIsActive(1);
					head.setLvsName(lvsName);
					head.setMakerDatetime(dateTime);
					head.setMakerUserId(1);

					LeaveStructureDetails detail = new LeaveStructureDetails();

					if (Integer.parseInt(defaultPrv.getValue()) == 1) {

						detail = new LeaveStructureDetails();
						detail.setDelStatus(1);
						detail.setExInt1(0);
						detail.setExInt2(0);
						detail.setExVar1("NA");
						detail.setExVar2("NA");
						detail.setIsActive(1);
						detail.setLvsAllotedLeaves(0);
						detail.setLvTypeId(1);
						detail.setMakerUserId(userObj.getUserId());
						detail.setMakerDatetime(dateTime);
						detailList.add(detail);

					}

					detail = new LeaveStructureDetails();
					detail.setDelStatus(1);
					detail.setExInt1(0);
					detail.setExInt2(0);
					detail.setExVar1("NA");
					detail.setExVar2("NA");
					detail.setIsActive(1);
					detail.setLvsAllotedLeaves(0);
					detail.setLvTypeId(2);
					detail.setMakerUserId(userObj.getUserId());
					detail.setMakerDatetime(dateTime);
					detailList.add(detail);

					for (int i = 0; i < leaveTypeList.size(); i++) {

						int noOfLeaves = 0;
						int minlv = 0;
						int maxlv = 0;
						int maxCarryForwardAccumadat = 0;

						try {
							noOfLeaves = (Integer
									.parseInt(request.getParameter("noOfLeaves" + leaveTypeList.get(i).getLvTypeId())));

						} catch (Exception e) {
							noOfLeaves = 0;
						}

						try {

							minlv = Integer.parseInt(request.getParameter("min" + leaveTypeList.get(i).getLvTypeId()));

						} catch (Exception e) {
							minlv = 0;
						}

						try {

							maxlv = Integer.parseInt(request.getParameter("max" + leaveTypeList.get(i).getLvTypeId()));

						} catch (Exception e) {
							maxlv = 0;
						}

						try {
							maxCarryForwardAccumadat = (Integer.parseInt(
									request.getParameter("maxCarryForword" + leaveTypeList.get(i).getLvTypeId())));

						} catch (Exception e) {
							maxCarryForwardAccumadat = 0;
						}

						// System.err.println("lv" + noOfLeaves + "min" + minlv + "max" + maxlv);
						detail = new LeaveStructureDetails();
						try {
							int inCash = Integer
									.parseInt(request.getParameter("isInCash" + leaveTypeList.get(i).getLvTypeId()));
							detail.setExInt1(1);
						} catch (Exception e) {
							detail.setExInt1(0);
						}

						detail.setDelStatus(1);

						detail.setExInt2(0);
						detail.setExVar1("NA");
						detail.setExVar2("NA");
						detail.setIsActive(1);

						detail.setLvsAllotedLeaves(noOfLeaves);
						detail.setMaxNoDays(maxlv);
						detail.setMinNoDays(minlv);
						detail.setMaxAccumulateCarryforward(maxCarryForwardAccumadat);
						detail.setLvTypeId(leaveTypeList.get(i).getLvTypeId());
						detail.setMakerUserId(userObj.getUserId());
						detail.setMakerDatetime(dateTime);
						detailList.add(detail);
					}

					String id = new String();

					if (allownsIds != null) {
						for (int i = 0; i < allownsIds.length; i++) {
							id = id + allownsIds[i] + ",";
						}

						head.setExVar1(id.substring(0, id.length() - 1));
					} else {
						head.setExVar1("0");
					}

					head.setDetailList(detailList);

					LeaveStructureHeader res = Constants.getRestTemplate()
							.postForObject(Constants.url + "saveLeaveStruture", head, LeaveStructureHeader.class);

					if (res != null) {
						session.setAttribute("successMsg", "Record Inserted Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Insert Record");
					}

				}
			} catch (Exception e) {

				System.err.println("Exce In submitInsertLeaveStructure method  " + e.getMessage());
				e.printStackTrace();

			}
		}

		return "redirect:/showLeaveStructureList";

	}

	@RequestMapping(value = "/showLeaveStructureList", method = RequestMethod.GET)
	public ModelAndView showLeaveStructureList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			HttpSession session = request.getSession();

			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showLeaveStructureList", "showLeaveStructureList", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("leave/leave_structure_list");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				LeaveStructureHeader[] summary = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getStructureList", map, LeaveStructureHeader[].class);

				List<LeaveStructureHeader> leaveSummarylist = new ArrayList<>(Arrays.asList(summary));

				for (int i = 0; i < leaveSummarylist.size(); i++) {

					leaveSummarylist.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(leaveSummarylist.get(i).getLvsId())));
				}

				model.addObject("lvStructureList", leaveSummarylist);

				Info add = AcessController.checkAccess("showLeaveStructureList", "showLeaveStructureList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showLeaveStructureList", "showLeaveStructureList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showLeaveStructureList", "showLeaveStructureList", 0, 0, 0,
						1, newModuleList);

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

	LeaveStructureHeader editStructure = new LeaveStructureHeader();

	@RequestMapping(value = "/editLeaveStructure", method = RequestMethod.GET)
	public ModelAndView editLeaveStructure(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;
		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editLeaveStructure", "showLeaveStructureList", 0, 0, 1, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("leave/edit_leave_structure");
				String base64encodedString = request.getParameter("lvsId");
				String lvsId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("lvsId", lvsId);
				editStructure = Constants.getRestTemplate().postForObject(Constants.url + "/getStructureById", map,
						LeaveStructureHeader.class);
				model.addObject("editStructure", editStructure);

				model.addObject("editStructureDetail", editStructure.getDetailList());

				map.add("companyId", 1);
				LeaveType[] leaveArray = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLeaveTypeListIsStructure", map, LeaveType[].class);
				leaveTypeList = new ArrayList<>(Arrays.asList(leaveArray));

				model.addObject("leaveTypeList", leaveTypeList);

				Allowances[] allowanceArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllAllowances", Allowances[].class);
				List<Allowances> allowanceList = new ArrayList<Allowances>(Arrays.asList(allowanceArr));
				model.addObject("allowanceList", allowanceList);

				try {
					String[] allownceIds = editStructure.getExVar1().split(",");
					model.addObject("allownceIds", allownceIds);

				} catch (Exception e) {

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/deleteLeaveStructure", method = RequestMethod.GET)
	public String deleteLeaveStructure(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("deleteLeaveStructure", "showLeaveStructureList", 0, 0, 0, 1,
					newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			} else {

				a = "redirect:/showLeaveStructureList";
				String base64encodedString = request.getParameter("lvsId");
				String lvsId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("lvsId", lvsId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteLeaveStructure", map,
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

	@RequestMapping(value = "/editInsertLeaveStructure", method = RequestMethod.POST)
	public String editInsertLeaveStructure(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		String a = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editLeaveStructure", "showLeaveStructureList", 0, 0, 1, 0,
				newModuleList);
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showLeaveStructureList";

			try {
				System.err.println("Inside insert editInsertLeaveStructure method");

				String lvsName = request.getParameter("lvsName");
				String[] allownsIds = request.getParameterValues("allowanceIds");

				Boolean ret = false;

				if (FormValidation.Validaton(lvsName, "") == true) {

					ret = true;
					System.out.println("lvsName" + ret);
				}

				if (ret == false) {

					editStructure.setLvsName(lvsName);

					String id = new String();

					if (allownsIds != null) {
						for (int i = 0; i < allownsIds.length; i++) {
							id = id + allownsIds[i] + ",";
						}

						editStructure.setExVar1(id.substring(0, id.length() - 1));
					} else {
						editStructure.setExVar1("0");
					}

					for (int i = 0; i < leaveTypeList.size(); i++) {
						int flag = 0;

						for (int j = 0; j < editStructure.getDetailList().size(); j++) {

							try {

								if (editStructure.getDetailList().get(j).getLvTypeId() == leaveTypeList.get(i)
										.getLvTypeId()) {
									flag = 1;

									try {
										editStructure.getDetailList().get(j)
												.setLvsAllotedLeaves(Integer.parseInt(request.getParameter(
														"noOfLeaves" + leaveTypeList.get(i).getLvTypeId())));
									} catch (Exception e) {

									}
									try {
										editStructure.getDetailList().get(j).setMaxNoDays(Integer.parseInt(
												request.getParameter("max" + leaveTypeList.get(i).getLvTypeId())));
									} catch (Exception e) {

									}
									try {
										editStructure.getDetailList().get(j).setMinNoDays(Integer.parseInt(
												request.getParameter("min" + leaveTypeList.get(i).getLvTypeId())));
									} catch (Exception e) {

									}

									try {
										editStructure.getDetailList().get(j)
												.setMaxAccumulateCarryforward(Integer.parseInt(request.getParameter(
														"maxCarryForword" + leaveTypeList.get(i).getLvTypeId())));
									} catch (Exception e) {

									}

									try {
										int incash = Integer.parseInt(
												request.getParameter("isInCash" + leaveTypeList.get(i).getLvTypeId()));
										editStructure.getDetailList().get(j).setExInt1(1);
									} catch (Exception e) {
										editStructure.getDetailList().get(j).setExInt1(0);
									}

								}

							} catch (Exception e) {
								// editStructure.getDetailList().get(i).setLvsAllotedLeaves(noOfLeaves1);
							}
						}

						if (flag == 0) {

							int noOfLeaves = 0;
							int minlv = 0;
							int maxlv = 0;
							int maxCarryForwardAccumadat = 0;

							try {
								noOfLeaves = (Integer.parseInt(
										request.getParameter("noOfLeaves" + leaveTypeList.get(i).getLvTypeId())));
							} catch (Exception e) {
								noOfLeaves = 0;
							}

							try {
								minlv = (Integer
										.parseInt(request.getParameter("min" + leaveTypeList.get(i).getLvTypeId())));
							} catch (Exception e) {
								minlv = 0;
							}

							try {
								maxlv = (Integer
										.parseInt(request.getParameter("max" + leaveTypeList.get(i).getLvTypeId())));
							} catch (Exception e) {
								maxlv = 0;
							}

							try {
								maxCarryForwardAccumadat = (Integer.parseInt(
										request.getParameter("maxCarryForword" + leaveTypeList.get(i).getLvTypeId())));
							} catch (Exception e) {
								maxCarryForwardAccumadat = 0;
							}

							LeaveStructureDetails detail = new LeaveStructureDetails();
							detail.setDelStatus(1);
							detail.setIsActive(1);
							detail.setLvsAllotedLeaves(noOfLeaves);
							detail.setMaxAccumulateCarryforward(maxCarryForwardAccumadat);
							detail.setMaxNoDays(maxlv);
							detail.setMinNoDays(minlv);
							detail.setLvTypeId(leaveTypeList.get(i).getLvTypeId());
							detail.setMakerDatetime(dateTime);
							detail.setLvsId(editStructure.getLvsId());
							detail.setMakerUserId(userObj.getUserId());

							try {
								int inCash = Integer.parseInt(
										request.getParameter("isInCash" + leaveTypeList.get(i).getLvTypeId()));
								detail.setExInt1(1);
							} catch (Exception e) {
								detail.setExInt1(0);
							}

							editStructure.getDetailList().add(detail);

						}

					}

					// System.out.println(editStructure);

					LeaveStructureHeader res = Constants.getRestTemplate().postForObject(
							Constants.url + "saveLeaveStruture", editStructure, LeaveStructureHeader.class);

					if (res != null) {
						session.setAttribute("successMsg", "Record Updated Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Update Record");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
				}
			} catch (Exception e) {

				System.err.println("Exce In editInsertLeaveStructure method  " + e.getMessage());
				e.printStackTrace();

			}
		}
		return a;

	}

	// leave_authority
	@RequestMapping(value = "/addLeaveAuthority", method = RequestMethod.GET)
	public ModelAndView addLeaveAuthority(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;

		try {
			HttpSession session = request.getSession();

			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("addLeaveAuthority", "leaveAuthorityList", 0, 1, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				model = new ModelAndView("leave/authority_add");

				GetEmployeeDetails[] employeeMaster = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getEmplistForAssignAuthority", GetEmployeeDetails[].class);

				List<GetEmployeeDetails> empList = new ArrayList<GetEmployeeDetails>(Arrays.asList(employeeMaster));

				model.addObject("empList", empList);

				GetEmployeeDetails[] empInfoError = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getEmpInfoListForLeaveAuth", GetEmployeeDetails[].class);

				List<GetEmployeeDetails> employeeInfo = new ArrayList<>(Arrays.asList(empInfoError));
				model.addObject("empListAuth", employeeInfo);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitAuthorityList", method = RequestMethod.POST)
	public String submitAuthorityList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("addLeaveAuthority", "leaveAuthorityList", 0, 1, 0, 0, newModuleList);
		String a = null;

		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/leaveAuthorityList";
			try {

				int iniAuthEmpId = Integer.parseInt(request.getParameter("iniAuthEmpId"));

				int finAuthEmpId = Integer.parseInt(request.getParameter("finAuthEmpId"));

				String[] empIds = request.getParameterValues("empIds");
				String[] repToEmpIds = request.getParameterValues("repToEmpIds");

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < empIds.length; i++) {
					sb = sb.append(empIds[i] + ",");

				}
				String empIdList = sb.toString();
				empIdList = empIdList.substring(0, empIdList.length() - 1);

				sb = new StringBuilder();

				for (int i = 0; i < repToEmpIds.length; i++) {
					sb = sb.append(repToEmpIds[i] + ",");

				}
				String repToEmpIdsList = sb.toString();
				repToEmpIdsList = repToEmpIdsList.substring(0, repToEmpIdsList.length() - 1);

				String[] arrOfStr = empIdList.split(",");
				LeaveAuthority leaves = new LeaveAuthority();

				for (int j = 0; j < arrOfStr.length; j++) {

					leaves.setDelStatus(1);
					leaves.setEmpId(Integer.parseInt(arrOfStr[j]));

					leaves.setExVar1("NA");
					leaves.setExVar2("NA");
					leaves.setExVar3("NA");
					leaves.setIsActive(1);
					leaves.setMakerUserId(userObj.getUserId());
					leaves.setMakerEnterDatetime(dateTime);
					leaves.setIniAuthEmpId(iniAuthEmpId);
					leaves.setFinAuthEmpId(finAuthEmpId);
					leaves.setCompanyId(1);
					leaves.setRepToEmpIds(repToEmpIdsList);

					LeaveAuthority res = Constants.getRestTemplate()
							.postForObject(Constants.url + "/saveLeaveAuthority", leaves, LeaveAuthority.class);

					if (res != null) {
						session.setAttribute("successMsg", "Record Inserted Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Insert Record");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/leaveAuthorityList";
	}

	@RequestMapping(value = "/leaveAuthorityList", method = RequestMethod.GET)
	public ModelAndView leaveAuthorityList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		HttpSession session = request.getSession();

		LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("leaveAuthorityList", "leaveAuthorityList", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("leave/authority_list");
			try {
				// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				map.add("locIdList", 1);

				GetLeaveAuthority[] empInfoError = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLeaveAuthorityList", map, GetLeaveAuthority[].class);

				List<GetLeaveAuthority> empLeaveAuth = new ArrayList<>(Arrays.asList(empInfoError));

				for (int i = 0; i < empLeaveAuth.size(); i++) {
					empLeaveAuth.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(empLeaveAuth.get(i).getEmpId())));
				}

				model.addObject("empLeaveAuth", empLeaveAuth);
				Info add = AcessController.checkAccess("leaveAuthorityList", "leaveAuthorityList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("leaveAuthorityList", "leaveAuthorityList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("leaveAuthorityList", "leaveAuthorityList", 0, 0, 0, 1,
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

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/editLeaveAuthority", method = RequestMethod.GET)
	public ModelAndView editLeaveAuthority(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editLeaveAuthority", "leaveAuthorityList", 0, 0, 1, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("leave/edit_authority");
			try {

				// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

				String base64encodedString = request.getParameter("empId");
				String empId = FormValidation.DecodeKey(base64encodedString);
				// System.out.println("empId" + empId);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				GetEmployeeDetails[] employeeMaster = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getEmplistForAssignAuthority", GetEmployeeDetails[].class);

				List<GetEmployeeDetails> empList = new ArrayList<GetEmployeeDetails>(Arrays.asList(employeeMaster));

				model.addObject("empList", empList);
				map.add("empIdList", empId);
				GetEmployeeDetails[] empInfoError = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmpInfoListByEmpIdList", map, GetEmployeeDetails[].class);

				List<GetEmployeeDetails> employeeInfo = new ArrayList<>(Arrays.asList(empInfoError));
				model.addObject("empListAuth", employeeInfo);
				model.addObject("space", " ");

				model.addObject("empIdForEdit", empId);

				map = new LinkedMultiValueMap<>();
				map.add("empId", empId);
				leaveAuthority = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLeaveAuthorityListByEmpId", map, LeaveAuthority.class);
				model.addObject("leaveAuthority", leaveAuthority);
				// System.out.println(leaveAuthority.toString());

				List<Integer> reportingIdList = Stream.of(leaveAuthority.getRepToEmpIds().split(","))
						.map(Integer::parseInt).collect(Collectors.toList());

				model.addObject("reportingIdList", reportingIdList);
				// System.out.println("reportingIdList" + reportingIdList.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/editSubmitAuthorityList", method = RequestMethod.POST)
	public String editSubmitAuthorityList(HttpServletRequest request, HttpServletResponse response) {
		String a = null;
		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editLeaveAuthority", "leaveAuthorityList", 0, 0, 1, 0, newModuleList);
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/leaveAuthorityList";
			try {

				int iniAuthEmpId = Integer.parseInt(request.getParameter("iniAuthEmpId"));

				int finAuthEmpId = Integer.parseInt(request.getParameter("finAuthEmpId"));

				String[] repToEmpIds = request.getParameterValues("repToEmpIds");

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < repToEmpIds.length; i++) {
					sb = sb.append(repToEmpIds[i] + ",");

				}
				String repToEmpIdsList = sb.toString();
				repToEmpIdsList = repToEmpIdsList.substring(0, repToEmpIdsList.length() - 1);

				leaveAuthority.setRepToEmpIds(repToEmpIdsList);
				leaveAuthority.setFinAuthEmpId(finAuthEmpId);
				leaveAuthority.setIniAuthEmpId(iniAuthEmpId);

				LeaveAuthority res = Constants.getRestTemplate().postForObject(Constants.url + "/saveLeaveAuthority",
						leaveAuthority, LeaveAuthority.class);

				if (res != null) {
					session.setAttribute("successMsg", "Record Updated Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Upadate Record");
				}

			} catch (

			Exception e) {
				e.printStackTrace();
			}
		}

		return a;
	}

	@RequestMapping(value = "/leaveStructureAllotment", method = RequestMethod.GET)
	public ModelAndView leaveStructureAllotment(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;

		try {

			HttpSession session = request.getSession();

			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("leaveStructureAllotment", "leaveStructureAllotment", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				model = new ModelAndView("leave/leave_structure_allot_list");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				LeaveStructureHeader[] lvStrSummery = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getStructureList", map, LeaveStructureHeader[].class);

				List<LeaveStructureHeader> lSummarylist = new ArrayList<>(Arrays.asList(lvStrSummery));
				model.addObject("lStrList", lSummarylist);

				GetStructureAllotment[] summary = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getStructureAllotmentList", GetStructureAllotment[].class);

				List<GetStructureAllotment> leaveSummarylist = new ArrayList<>(Arrays.asList(summary));
				model.addObject("lvStructureList", leaveSummarylist);

				LeavesAllotment[] leavesAllotmentArray = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getLeaveAllotmentByCurrentCalender", LeavesAllotment[].class);

				List<LeavesAllotment> calAllotList = new ArrayList<>(Arrays.asList(leavesAllotmentArray));
				model.addObject("calAllotList", calAllotList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitStructureList", method = RequestMethod.POST)
	public String submitStructureList(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			CalenderYear calculateYear = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getCalculateYearListIsCurrent", CalenderYear.class);
			// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			int lvsId = Integer.parseInt(request.getParameter("lvsId"));

			String[] empIds = request.getParameterValues("empIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < empIds.length; i++) {
				sb = sb.append(empIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			String[] arrOfStr = items.split(",");

			Boolean ret = false;

			if (ret == false) {

				LeavesAllotment leavesAllotment = new LeavesAllotment();
				for (int i = 0; i < arrOfStr.length; i++) {

					leavesAllotment.setCalYrId(calculateYear.getCalYrId());

					leavesAllotment.setDelStatus(1);
					leavesAllotment.setEmpId(Integer.parseInt(arrOfStr[i]));
					leavesAllotment.setExVar1("NA");
					leavesAllotment.setExVar2("NA");
					leavesAllotment.setExVar3("NA");
					leavesAllotment.setIsActive(1);
					leavesAllotment.setMakerUserId(1);
					leavesAllotment.setMakerEnterDatetime(dateTime);
					leavesAllotment.setLvsId(lvsId);

					LeavesAllotment res = Constants.getRestTemplate().postForObject(
							Constants.url + "/saveLeaveAllotment", leavesAllotment, LeavesAllotment.class);

					if (res != null) {
						session.setAttribute("successMsg", "Record Inserted Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Insert Record");
					}
				}
			} else {
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/leaveStructureAllotment";
	}

	List<LeaveHistory> previousleavehistorylist = new ArrayList<>();
	int empId = 0;
	int structId = 0;

	@RequestMapping(value = "/leaveYearEnd", method = RequestMethod.GET)
	public ModelAndView leaveYearEnd(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("leaveYearEnd", "leaveYearEnd", 1, 0, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				model = new ModelAndView("leave/leaveYearEnd");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				EmployeeMaster[] employeeInfo = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getemplistwhichisnotyearend", EmployeeMaster[].class);

				List<EmployeeMaster> employeeInfoList = new ArrayList<EmployeeMaster>(Arrays.asList(employeeInfo));
				model.addObject("employeeInfoList", employeeInfoList);

				map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				LeaveStructureHeader[] lvStrSummery = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getStructureList", map, LeaveStructureHeader[].class);
				List<LeaveStructureHeader> lSummarylist = new ArrayList<>(Arrays.asList(lvStrSummery));
				model.addObject("lStrList", lSummarylist);

				try {
					empId = Integer.parseInt(request.getParameter("empId"));

					map = new LinkedMultiValueMap<>();
					map.add("empId", empId);

					LeaveHistory[] leaveHistory = Constants.getRestTemplate()
							.postForObject(Constants.url + "/getPreviousleaveHistory", map, LeaveHistory[].class);
					previousleavehistorylist = new ArrayList<>(Arrays.asList(leaveHistory));
					model.addObject("previousleavehistorylist", previousleavehistorylist);
					model.addObject("empId", empId);

					String previousStructName = new String();

					try {
						previousStructName = previousleavehistorylist.get(0).getLvsName();
						model.addObject("previousStructName", previousStructName);
					} catch (Exception e) {

					}
				} catch (Exception e) {

					model.addObject("empId", 0);

				}

				try {
					map = new LinkedMultiValueMap<>();
					map.add("empId", empId);
					GetEmployeeDetails empPersInfo = Constants.getRestTemplate().postForObject(
							Constants.url + "/getAllEmployeeDetailByEmpId", map, GetEmployeeDetails.class);
					model.addObject("empDeatil", empPersInfo);

					structId = Integer.parseInt(request.getParameter("structId"));
					model.addObject("structId", structId);

					map = new LinkedMultiValueMap<>();
					map.add("lvsId", structId);
					LeaveStructureHeader leaveStructureById = Constants.getRestTemplate()
							.postForObject(Constants.url + "/getStructureById", map, LeaveStructureHeader.class);
					model.addObject("leaveStructureById", leaveStructureById);

					map = new LinkedMultiValueMap<>();
					map.add("lvsId", previousleavehistorylist.get(0).getLvsId());
					map.add("empId", empId);
					EmpBasicAllownceForLeaveInCash empBasicAllownceForLeaveInCash = Constants.getRestTemplate()
							.postForObject(Constants.url + "/getEmployeeBasicAndAllownceValueByEmpIdAndStructId", map,
									EmpBasicAllownceForLeaveInCash.class);
					model.addObject("empBasicAllownceForLeaveInCash", empBasicAllownceForLeaveInCash);

					map = new LinkedMultiValueMap<>();
					map.add("limitKey", "monthday");
					Setting dayInMonth = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey",
							map, Setting.class);
					model.addObject("day", dayInMonth.getValue());
					
				} catch (Exception e) {
					model.addObject("structId", 0);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getPreviousYearHistory", method = RequestMethod.GET)
	@ResponseBody
	public List<LeaveHistory> getPreviousYearHistory(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("leave/leaveYearEnd");
		previousleavehistorylist = new ArrayList<>();

		try {
			empId = Integer.parseInt(request.getParameter("empId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);

			LeaveHistory[] employeeInfo = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getPreviousleaveHistory", map, LeaveHistory[].class);
			previousleavehistorylist = new ArrayList<>(Arrays.asList(employeeInfo));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return previousleavehistorylist;
	}

	@RequestMapping(value = "/submitYearEndAndAssignNewStructure", method = RequestMethod.POST)
	public String submitYearEndAndAssignNewStructure(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			CalenderYear calculateYear = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getCalculateYearListIsCurrent", CalenderYear.class);

			/*
			 * empId = Integer.parseInt(request.getParameter("empId")); int structId =
			 * Integer.parseInt(request.getParameter("structId"));
			 */

			if (previousleavehistorylist.size() > 0) {

				LeavesAllotment leavesAllotment = new LeavesAllotment();
				leavesAllotment.setCalYrId(calculateYear.getCalYrId());
				leavesAllotment.setDelStatus(1);
				leavesAllotment.setEmpId(empId);
				leavesAllotment.setExVar1("NA");
				leavesAllotment.setExVar2("NA");
				leavesAllotment.setExVar3("NA");
				leavesAllotment.setIsActive(1);
				leavesAllotment.setMakerUserId(userObj.getUserId());
				leavesAllotment.setMakerEnterDatetime(dateTime);
				leavesAllotment.setLvsId(structId);

				List<LeaveBalanceCal> leavBalList = new ArrayList<>();

				for (int i = 0; i < previousleavehistorylist.size(); i++) {
					LeaveBalanceCal leaveBalanceCal = new LeaveBalanceCal();
					leaveBalanceCal.setCalYrId(leavesAllotment.getCalYrId());
					leaveBalanceCal.setDelStatus(1);
					leaveBalanceCal.setEmpId(empId);
					leaveBalanceCal.setIsActive(1);
					leaveBalanceCal.setLvAlloted(0);
					leaveBalanceCal.setLvbalId(0);
					/*
					 * leaveBalanceCal.setLvCarryFwd(Float.parseFloat(
					 * request.getParameter("carryfrwd" +
					 * previousleavehistorylist.get(i).getLvTypeId())));
					 */
					leaveBalanceCal.setLvCarryFwdRemarks("Null");
					leaveBalanceCal.setOpBal(Float.parseFloat(
							request.getParameter("carryfrwd" + previousleavehistorylist.get(i).getLvTypeId())));
					try {
						float incashValue = Float.parseFloat(
								request.getParameter("inCash" + previousleavehistorylist.get(i).getLvTypeId()));
						float leaveInCashCount = Float.parseFloat(request
								.getParameter("inCashleavCount" + previousleavehistorylist.get(i).getLvTypeId()));
						leaveBalanceCal.setLvEncash(leaveInCashCount);
						leaveBalanceCal.setLvEncashRemarks(String.valueOf(incashValue));
					} catch (Exception e) {

						e.printStackTrace();
						leaveBalanceCal.setLvEncash(0);
						leaveBalanceCal.setLvEncashRemarks(String.valueOf(0));
					}
					leaveBalanceCal.setExInt1(1);
					leaveBalanceCal.setMakerUserId(1);
					leaveBalanceCal.setMakerEnterDatetime(dateTime);
					leaveBalanceCal.setLvTypeId(previousleavehistorylist.get(i).getLvTypeId());
					leavBalList.add(leaveBalanceCal);
				}

				LeavesAllotment res = Constants.getRestTemplate().postForObject(
						Constants.url + "/saveNewLeaveAllotment", leavesAllotment, LeavesAllotment.class);
				LeaveBalanceCal[] leaveBalanceCalres = Constants.getRestTemplate()
						.postForObject(Constants.url + "/saveNewBalRecord", leavBalList, LeaveBalanceCal[].class);
				if (res != null) {
					session.setAttribute("successMsg", "Stucture Allocate Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Allocate Stucture");
				}
			} else {

				session.setAttribute("errorMsg", "Failed to Assign");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/leaveYearEnd";
	}

	int yearId;

	@RequestMapping(value = "/getPendingListOfleaveCash", method = RequestMethod.GET)
	public String fixAttendaceByDateAndEmp(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();

		String mav = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("getPendingListOfleaveCash", "getPendingListOfleaveCash", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {
			mav = "leave/leavecashpendinglist";

			try {

				CalenderYear[] calenderYear = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getCalculateYearList", CalenderYear[].class);
				List<CalenderYear> calYearList = new ArrayList<CalenderYear>(Arrays.asList(calenderYear));
				model.addAttribute("calYearList", calYearList);

				try {
					yearId = Integer.parseInt(request.getParameter("calYrId"));
					model.addAttribute("yearId", yearId);
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

					map = new LinkedMultiValueMap<>();
					map.add("yearId", yearId);
					LeaveCashReport[] leaveCashReport = Constants.getRestTemplate()
							.postForObject(Constants.url + "/getPendingListOfleaveCash", map, LeaveCashReport[].class);
					List<LeaveCashReport> list = new ArrayList<>(Arrays.asList(leaveCashReport));
					model.addAttribute("list", list);
				} catch (Exception e) {
					// e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;

	}

	@RequestMapping(value = "/submitinchashamt", method = RequestMethod.POST)
	public String submitinchashamt(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();

		String mav = new String();
		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("getPendingListOfleaveCash", "getPendingListOfleaveCash", 1, 0, 0,
					0, newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "redirect:/getPendingListOfleaveCash";

				String[] empIds = request.getParameterValues("selectEmp");

				String date = request.getParameter("date");

				String ids = new String();

				for (int i = 0; i < empIds.length; i++) {
					ids = ids + empIds[i] + ",";

				}
				ids = ids.substring(0, ids.length() - 1);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("yearId", yearId);
				map.add("date", DateConvertor.convertToYMD(date));
				map.add("empId", ids);

				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateIsPaidIncash", map,
						Info.class);
				if (info.isError() == false) {
					session.setAttribute("successMsg", "Paid Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Paid Cash");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;

	}

	@RequestMapping(value = "/getPaidListOfleaveCash", method = RequestMethod.GET)
	public String getPaidListOfleaveCash(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();

		String mav = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("getPaidListOfleaveCash", "getPaidListOfleaveCash", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {
			mav = "leave/leavecashpaidlist";

			try {

				CalenderYear[] calenderYear = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getCalculateYearList", CalenderYear[].class);
				List<CalenderYear> calYearList = new ArrayList<CalenderYear>(Arrays.asList(calenderYear));
				model.addAttribute("calYearList", calYearList);

				try {
					int yearId = Integer.parseInt(request.getParameter("calYrId"));
					model.addAttribute("yearId", yearId);
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map = new LinkedMultiValueMap<>();
					map.add("yearId", yearId);
					LeaveCashReport[] leaveCashReport = Constants.getRestTemplate()
							.postForObject(Constants.url + "/getPaidListOfleaveCash", map, LeaveCashReport[].class);
					List<LeaveCashReport> list = new ArrayList<>(Arrays.asList(leaveCashReport));

					for (int i = 0; i < list.size(); i++) {
						list.get(i).setPaidDate(DateConvertor.convertToDMY(list.get(i).getPaidDate()));
					}
					model.addAttribute("list", list);
				} catch (Exception e) {
					// e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;

	}

}
