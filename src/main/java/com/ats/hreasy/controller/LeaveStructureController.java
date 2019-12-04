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

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveAuthority;
import com.ats.hreasy.model.LeaveStructureDetails;
import com.ats.hreasy.model.LeaveStructureHeader;
import com.ats.hreasy.model.LeaveType;
 

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
		//LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		ModelAndView model = null;

		try {

			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("addLeaveStructure", "showLeaveStructureList", 0, 1, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/
				model = new ModelAndView("leave/add_leave_structure");
				tempDetailList = new ArrayList<LeaveStructureDetails>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				LeaveType[] leaveArray = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getLeaveTypeListIsStructure", map, LeaveType[].class);

				leaveTypeList = new ArrayList<>(Arrays.asList(leaveArray));

				model.addObject("leaveTypeList", leaveTypeList);

				model.addObject("title", "Add Leave Structure");
			//}

		} catch (Exception e) {

			System.err.println("exception In addLeaveStructureHeader at leave structure Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertLeaveStructure", method = RequestMethod.POST)
	public String insertLeaveStructure(HttpServletRequest request, HttpServletResponse response) {
		try {

			HttpSession session = request.getSession();
			//LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			String lvsName = request.getParameter("lvsName");

			Boolean ret = false;

			if (FormValidation.Validaton(lvsName, "") == true) {

				ret = true;

			}

			if (ret == false) {

				LeaveStructureHeader head = new LeaveStructureHeader();

				head.setCompanyId(1);
				head.setDelStatus(1);
				head.setIsActive(1);
				head.setLvsName(lvsName);
				head.setMakerDatetime(dateTime);
				head.setMakerUserId(1);

				List<LeaveStructureDetails> detailList = new ArrayList<>();
				for (int i = 0; i < leaveTypeList.size(); i++) {

					int noOfLeaves = 0;
					try {
						noOfLeaves = (Integer
								.parseInt(request.getParameter("noOfLeaves" + leaveTypeList.get(i).getLvTypeId())));
					} catch (Exception e) {
						noOfLeaves = 0;
					}

					/* if (noOfLeaves > 0) { */
					LeaveStructureDetails detail = new LeaveStructureDetails();
					detail.setDelStatus(1);
					detail.setExInt1(0);
					detail.setExInt2(0);
					detail.setExVar1("NA");
					detail.setExVar2("NA");
					detail.setIsActive(1);

					detail.setLvsAllotedLeaves(
							Integer.parseInt(request.getParameter("noOfLeaves" + leaveTypeList.get(i).getLvTypeId())));

					detail.setLvTypeId(leaveTypeList.get(i).getLvTypeId());
					detail.setMakerUserId(1);
					detail.setMakerDatetime(dateTime);
					detailList.add(detail);
					/* } */
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

		return "redirect:/showLeaveStructureList";

	}

	@RequestMapping(value = "/showLeaveStructureList", method = RequestMethod.GET)
	public ModelAndView showLeaveStructureList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			HttpSession session = request.getSession();
			/*LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showLeaveStructureList", "showLeaveStructureList", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/
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

				model.addObject("addAccess", 0);
				model.addObject("editAccess", 0);
				model.addObject("deleteAccess", 0);
				model.addObject("lvStructureList", leaveSummarylist);
				
				/*Info add = AcessController.checkAccess("showLeaveStructureList", "showLeaveStructureList", 0, 1, 0, 0,
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

				}*/
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	LeaveStructureHeader editStructure = new LeaveStructureHeader();

	@RequestMapping(value = "/editLeaveStructure", method = RequestMethod.GET)
	public ModelAndView editLeaveStructure(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		//LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;
		try {

			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editLeaveStructure", "showLeaveStructureList", 0, 0, 1, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/
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

				System.out.println("editStructure" + editStructure.toString());
				System.out.println("editStructureDetail" + editStructure.getDetailList().toString());
			//}
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
			
			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList"); 
			Info view = AcessController.checkAccess("deleteLeaveStructure", "showLeaveStructureList", 0, 0, 0, 1,
					newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			} else {*/
				a = "redirect:/showLeaveStructureList";
				String base64encodedString = request.getParameter("lvsId");
				String lvsId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("lvsId", lvsId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteLeaveStructure", map,
						Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", "Deleted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Delete");
				}
			//}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		return a;
	}

	@RequestMapping(value = "/editInsertLeaveStructure", method = RequestMethod.POST)
	public String editInsertLeaveStructure(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.err.println("Inside insert editInsertLeaveStructure method");
			HttpSession session = request.getSession();
			//LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			String lvsName = request.getParameter("lvsName");

			Boolean ret = false;

			if (FormValidation.Validaton(lvsName, "") == true) {

				ret = true;
				System.out.println("lvsName" + ret);
			}

			if (ret == false) {

				editStructure.setLvsName(lvsName);

				for (int i = 0; i < leaveTypeList.size(); i++) {
					int flag = 0;
					int noOfLeaves = 0;
					try {
						noOfLeaves = (Integer
								.parseInt(request.getParameter("noOfLeaves" + leaveTypeList.get(i).getLvTypeId())));
					} catch (Exception e) {
						noOfLeaves = 0;
					}

					for (int j = 0; j < editStructure.getDetailList().size(); j++) {

						try {

							if (editStructure.getDetailList().get(j).getLvTypeId() == leaveTypeList.get(i)
									.getLvTypeId()) {
								flag = 1;
								editStructure.getDetailList().get(j).setLvsAllotedLeaves(Integer.parseInt(
										request.getParameter("noOfLeaves" + leaveTypeList.get(i).getLvTypeId())));
							}

						} catch (Exception e) {
							// editStructure.getDetailList().get(i).setLvsAllotedLeaves(noOfLeaves1);
						}
					}
					/* if (noOfLeaves > 0) { */
					if (flag == 0) {
						LeaveStructureDetails detail = new LeaveStructureDetails();
						detail.setDelStatus(1);
						detail.setIsActive(1);
						detail.setLvsAllotedLeaves(noOfLeaves);
						detail.setLvTypeId(leaveTypeList.get(i).getLvTypeId());
						detail.setMakerDatetime(dateTime);
						detail.setLvsId(editStructure.getLvsId());
						detail.setMakerUserId(1);

						// LeaveStructureDetails resDetails = Constants.getRestTemplate().postForObject(
						// Constants.url + "saveLeaveStructureDetail", detail,
						// LeaveStructureDetails.class);
						editStructure.getDetailList().add(detail);
						System.out.println(detail.toString());
						/* } */

					}
					// System.err.println("editStructure" +
					// editStructure.getDetailList().toString());

				}

				//System.out.println(editStructure);
				LeaveStructureHeader res = Constants.getRestTemplate()
						.postForObject(Constants.url + "saveLeaveStruture", editStructure, LeaveStructureHeader.class);

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

		return "redirect:/showLeaveStructureList";

	}

	/*@RequestMapping(value = "/leaveStructureAllotment", method = RequestMethod.GET)
	public ModelAndView leaveStructureAllotment(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/leave_structure_allot_list");

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("leaveStructureAllotment",
					"leaveStructureAllotment", 1, 0, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", userObj.getCompanyId());
				LeaveStructureHeader[] lvStrSummery = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getStructureList", map, LeaveStructureHeader[].class);

				List<LeaveStructureHeader> lSummarylist = new ArrayList<>(Arrays.asList(lvStrSummery));
				model.addObject("lStrList", lSummarylist);

				map = new LinkedMultiValueMap<>();
				map.add("companyId", userObj.getCompanyId());
				map.add("locIdList", userObj.getLocationIds());
				map.add("typeId", 1);

				GetStructureAllotment[] summary = Constants.getRestTemplate().postForObject(
						Constants.url + "/getStructureAllotmentList", map, GetStructureAllotment[].class);

				List<GetStructureAllotment> leaveSummarylist = new ArrayList<>(Arrays.asList(summary));
				model.addObject("lvStructureList", leaveSummarylist);

				map = new LinkedMultiValueMap<>();
				map.add("calYrId", session.getAttribute("currYearId"));
				LeavesAllotment[] leavesAllotmentArray = Constants.getRestTemplate().postForObject(
						Constants.url + "/getLeaveAllotmentByCurrentCalender", map, LeavesAllotment[].class);

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
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
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

					leavesAllotment.setCalYrId((int) session.getAttribute("currYearId"));

					leavesAllotment.setDelStatus(1);
					leavesAllotment.setEmpId(Integer.parseInt(arrOfStr[i]));
					leavesAllotment.setExVar1("NA");
					leavesAllotment.setExVar2("NA");
					leavesAllotment.setExVar3("NA");
					leavesAllotment.setIsActive(1);
					leavesAllotment.setMakerUserId(userObj.getUserId());
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

	// leave_authority

	@RequestMapping(value = "/addLeaveAuthority", method = RequestMethod.GET)
	public ModelAndView addLeaveAuthority(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/authority_add");

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("addLeaveAuthority",
					"addLeaveAuthority", 1, 0, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", userObj.getCompanyId());
				map.add("locIdList", userObj.getLocationIds());

				GetEmployeeInfo[] employeeDepartment = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmpInfoList", map, GetEmployeeInfo[].class);

				List<GetEmployeeInfo> employeeDepartmentlist = new ArrayList<GetEmployeeInfo>(
						Arrays.asList(employeeDepartment));

				model.addObject("empList", employeeDepartmentlist);

				map = new LinkedMultiValueMap<>();
				map.add("companyId", userObj.getCompanyId());
				map.add("locIdList", userObj.getLocationIds());
				GetEmployeeInfo[] empInfoError = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmpInfoListForLeaveAuth", map, GetEmployeeInfo[].class);

				List<GetEmployeeInfo> employeeInfo = new ArrayList<>(Arrays.asList(empInfoError));
				model.addObject("empListAuth", employeeInfo);
				
				Info add = AcessController.checkAccess("addLeaveAuthority", "addLeaveAuthority", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("addLeaveAuthority", "addLeaveAuthority", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("addLeaveAuthority", "addLeaveAuthority", 0, 0, 0,
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

	@RequestMapping(value = "/submitAuthorityList", method = RequestMethod.POST)
	public String submitAuthorityList(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
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
				leaves.setCompanyId(userObj.getCompanyId());
				leaves.setRepToEmpIds(repToEmpIdsList);

				LeaveAuthority res = Constants.getRestTemplate().postForObject(Constants.url + "/saveLeaveAuthority",
						leaves, LeaveAuthority.class);

				if (res != null) {
					session.setAttribute("successMsg", "Record Inserted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Insert Record");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/leaveAuthorityList";
	}

	@RequestMapping(value = "/leaveAuthorityList", method = RequestMethod.GET)
	public ModelAndView leaveAuthorityList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/authority_list");

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", userObj.getCompanyId());
			map.add("locIdList", userObj.getLocationIds());

			GetLeaveAuthority[] empInfoError = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getLeaveAuthorityList", map, GetLeaveAuthority[].class);

			List<GetLeaveAuthority> empLeaveAuth = new ArrayList<>(Arrays.asList(empInfoError));

			for (int i = 0; i < empLeaveAuth.size(); i++) {
				//System.out.println("i loop for" + i + empLeaveAuth.get(i).getEmpFname());

				empLeaveAuth.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(empLeaveAuth.get(i).getEmpId())));

				
				 * String repTo=empLeaveAuth.get(i).getRepToEmpIds();
				 * 
				 * List<String> idsList = Arrays.asList(repTo.split(","));
				 * System.err.println("idsList"+idsList.toString()); List<String> list = new
				 * ArrayList<String>();
				 * 
				 * 
				 * for(int j=0;j<idsList.size();j++) {
				 * System.err.println("j loop for::"+idsList.get(j)); map = new
				 * LinkedMultiValueMap<>(); map.add("empId",idsList.get(j));
				 * 
				 * 
				 * GetEmployeeInfo empName =
				 * Constants.getRestTemplate().postForObject(Constants.url + "/GetEmployeeInfo",
				 * map, GetEmployeeInfo.class);
				 * 
				 * list.add(empName.getEmpFname());
				 * 
				 * System.err.println("emp name list "+list.toString()); }
				 * 
				 * model.addObject("list", list);
				 * 
				 
			}

			model.addObject("empLeaveAuth", empLeaveAuth);
			 
			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			 
			Info edit = AcessController.checkAccess("addLeaveAuthority", "addLeaveAuthority", 0, 0, 1, 0,
					newModuleList);
			  
			if (edit.isError() == false) {
				System.out.println(" edit   Accessable ");
				model.addObject("editAccess", 0);
			}
			 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/editLeaveAuthority", method = RequestMethod.GET)
	public ModelAndView editLeaveAuthority(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/edit_authority");

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);
			// System.out.println("empId" + empId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", userObj.getCompanyId());
			map.add("locIdList", userObj.getLocationIds());

			GetEmployeeInfo[] employeeDepartment = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpInfoList", map, GetEmployeeInfo[].class);

			List<GetEmployeeInfo> employeeDepartmentlist = new ArrayList<GetEmployeeInfo>(
					Arrays.asList(employeeDepartment));

			model.addObject("empList", employeeDepartmentlist);

			map = new LinkedMultiValueMap<>();
			map.add("companyId", userObj.getCompanyId());
			map.add("empIdList", empId);
			GetEmployeeInfo[] empInfoError = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpInfoListByEmpIdList", map, GetEmployeeInfo[].class);

			List<GetEmployeeInfo> employeeInfo = new ArrayList<>(Arrays.asList(empInfoError));
			model.addObject("empListAuth", employeeInfo);
			model.addObject("space", " ");

			model.addObject("empIdForEdit", empId);

			map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			leaveAuthority = Constants.getRestTemplate().postForObject(Constants.url + "/getLeaveAuthorityListByEmpId",
					map, LeaveAuthority.class);
			model.addObject("leaveAuthority", leaveAuthority);
			System.out.println(leaveAuthority.toString());

			List<Integer> reportingIdList = Stream.of(leaveAuthority.getRepToEmpIds().split(",")).map(Integer::parseInt)
					.collect(Collectors.toList());

			model.addObject("reportingIdList", reportingIdList);
			System.out.println("reportingIdList" + reportingIdList.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/editSubmitAuthorityList", method = RequestMethod.POST)
	public String editSubmitAuthorityList(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
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

		return "redirect:/leaveAuthorityList";
	}

	// **************************** Allotment for probationary
	// emp*******************************

	@RequestMapping(value = "/leaveStructureAllotmentForProbationary", method = RequestMethod.GET)
	public ModelAndView leaveStructureAllotmentForProbationary(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/leave_structure_allot_list_for_Prob");

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("leaveStructureAllotmentForProbationary",
					"leaveStructureAllotmentForProbationary", 1, 0, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", userObj.getCompanyId());
				LeaveStructureHeader[] lvStrSummery = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getStructureList", map, LeaveStructureHeader[].class);

				List<LeaveStructureHeader> lSummarylist = new ArrayList<>(Arrays.asList(lvStrSummery));
				for (int i = 0; i < lSummarylist.size(); i++) {
					if (lSummarylist.get(i).getLvsId() == 4) {
						lSummarylist.remove(i);

					}

				}
				model.addObject("lStrList", lSummarylist);

				map = new LinkedMultiValueMap<>();
				map.add("companyId", userObj.getCompanyId());
				map.add("locIdList", userObj.getLocationIds());
				map.add("typeId", 2);
				GetStructureAllotment[] summary = Constants.getRestTemplate().postForObject(
						Constants.url + "/getStructureAllotmentList", map, GetStructureAllotment[].class);

				List<GetStructureAllotment> leaveSummarylist = new ArrayList<>(Arrays.asList(summary));
				model.addObject("lvStructureList", leaveSummarylist);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitStructureListForProb", method = RequestMethod.POST)
	public String submitStructureListForProb(HttpServletRequest request, HttpServletResponse response) {

		try {

			Info info1 = new Info();
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
			int lvsId = Integer.parseInt(request.getParameter("lvsId"));

			String[] empIds = request.getParameterValues("empIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < empIds.length; i++) {
				sb = sb.append(empIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			String[] arrOfStr = items.split(",");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map.add("lvsId", lvsId);
			LeaveStructureDetails[] probLeaveStruct = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getStructureDetailsList", map, LeaveStructureDetails[].class);

			List<LeaveStructureDetails> probLeaveStructList = new ArrayList<>(Arrays.asList(probLeaveStruct));

			Boolean ret = false;

			if (ret == false) {

				for (int i = 0; i < arrOfStr.length; i++) {
					map = new LinkedMultiValueMap<>();
					map.add("lvsId", lvsId);
					map.add("empId", arrOfStr[i]);
					Info info = Constants.getRestTemplate()
							.postForObject(Constants.url + "/updateLeaveStructureAllotment", map, Info.class);

					if (info.isError() == false) {

						map.add("empId", arrOfStr[i]);
						LeaveBalanceCal[] lvBalCal = Constants.getRestTemplate()
								.postForObject(Constants.url + "/getLeavebalByEmpIdList", map, LeaveBalanceCal[].class);

						List<LeaveBalanceCal> LeaveBalanceCalList = new ArrayList<>(Arrays.asList(lvBalCal));

						for (int p = 0; p < probLeaveStructList.size(); p++) {
							for (int q = 0; q < LeaveBalanceCalList.size(); q++) {
								if (probLeaveStructList.get(p).getLvTypeId() == LeaveBalanceCalList.get(q)
										.getLvTypeId()) {

									map = new LinkedMultiValueMap<>();
									map.add("lvTypeId", LeaveBalanceCalList.get(q).getLvTypeId());
									map.add("empId", arrOfStr[i]);
									map.add("noDays", probLeaveStructList.get(p).getLvsAllotedLeaves());
									info1 = Constants.getRestTemplate()
											.postForObject(Constants.url + "/updateLeaveBalCal", map, Info.class);

								}

							}
						}

						if (info.isError() == false) {
							System.err.println("curr date is *****" + curDate);
							map = new LinkedMultiValueMap<>();
							map.add("empId", arrOfStr[i]);
							map.add("joinDate", curDate);
							Info info2 = Constants.getRestTemplate()
									.postForObject(Constants.url + "/updateEmployeeJoiningDate", map, Info.class);

						}

					}
				}
			} else {
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/leaveStructureAllotmentForProbationary";
	}

	@RequestMapping(value = "/leaveYearEnd", method = RequestMethod.GET)
	public ModelAndView leaveYearEnd(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("leave/leaveYearEnd");

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("leaveYearEnd", "leaveYearEnd", 1, 0, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("locationId", userObj.getLocationIds());

				EmployeeInfo[] employeeInfo = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getemplistwhichisnotyearend", map, EmployeeInfo[].class);

				List<EmployeeInfo> employeeInfoList = new ArrayList<EmployeeInfo>(Arrays.asList(employeeInfo));
				model.addObject("employeeInfoList", employeeInfoList);

				map = new LinkedMultiValueMap<>();
				map.add("companyId", userObj.getCompanyId());
				LeaveStructureHeader[] lvStrSummery = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getStructureList", map, LeaveStructureHeader[].class);
				List<LeaveStructureHeader> lSummarylist = new ArrayList<>(Arrays.asList(lvStrSummery));
				model.addObject("lStrList", lSummarylist);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	List<LeaveHistory> previousleavehistorylist = new ArrayList<>();
	int empId = 0;

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
			LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

			empId = Integer.parseInt(request.getParameter("empId"));
			int structId = Integer.parseInt(request.getParameter("structId"));

			if (previousleavehistorylist.size() > 0) {

				LeavesAllotment leavesAllotment = new LeavesAllotment();
				leavesAllotment.setCalYrId((int) session.getAttribute("currYearId"));
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
					leaveBalanceCal.setLvCarryFwd(Float.parseFloat(
							request.getParameter("carryfrwd" + previousleavehistorylist.get(i).getLvTypeId())));
					leaveBalanceCal.setLvCarryFwdRemarks("Null");
					leaveBalanceCal.setLvEncash(Float.parseFloat(
							request.getParameter("inchashLv" + previousleavehistorylist.get(i).getLvTypeId())));
					leaveBalanceCal.setOpBal(Float.parseFloat(
							request.getParameter("carryfrwd" + previousleavehistorylist.get(i).getLvTypeId())));
					leaveBalanceCal.setMakerUserId(1);
					leaveBalanceCal.setMakerEnterDatetime(dateTime);
					leaveBalanceCal.setLvEncashRemarks("Null");
					leaveBalanceCal.setLvTypeId(previousleavehistorylist.get(i).getLvTypeId());
					leavBalList.add(leaveBalanceCal);
				}

				LeavesAllotment res = Constants.getRestTemplate().postForObject(
						Constants.url + "/saveNewLeaveAllotment", leavesAllotment, LeavesAllotment.class);
				LeaveBalanceCal[] leaveBalanceCalres = Constants.getRestTemplate()
						.postForObject(Constants.url + "/saveNewBalRecord", leavBalList, LeaveBalanceCal[].class);
				if (res != null) {
					session.setAttribute("successMsg", "Record Inserted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Insert Record");
				}
			} else {

				session.setAttribute("errorMsg", "Failed to Assign");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/leaveYearEnd";
	}*/

	/*
	 * @RequestMapping(value = "/addLeaveStructureHeader", method =
	 * RequestMethod.GET) public ModelAndView
	 * addLeaveStructureHeader(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * ModelAndView model = null; try { tempDetailList = new
	 * ArrayList<LeaveStructureDetails>();
	 * 
	 * model = new ModelAndView("leave/leave_structure");
	 * 
	 * LeaveType[] leaveArray = Constants.getRestTemplate()
	 * .getForObject(Constants.url + "/getLeaveTypeListIsStructure",
	 * LeaveType[].class);
	 * 
	 * List<LeaveType> leaveTypeList = new ArrayList<>(Arrays.asList(leaveArray));
	 * 
	 * model.addObject("leaveTypeList", leaveTypeList);
	 * 
	 * model.addObject("title", "Add Leave Structure");
	 * 
	 * } catch (Exception e) {
	 * 
	 * System.err.
	 * println("exception In addLeaveStructureHeader at leave structure Contr" +
	 * e.getMessage());
	 * 
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * return model;
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/addStrDetail", method = RequestMethod.GET)
	 * public @ResponseBody List<LeaveStructureDetails>
	 * addStrDetail(HttpServletRequest request, HttpServletResponse response) {
	 * 
	 * try {
	 * 
	 * int isDelete = Integer.parseInt(request.getParameter("isDelete"));
	 * 
	 * int isEdit = Integer.parseInt(request.getParameter("isEdit"));
	 * 
	 * if (isDelete == 1) { System.out.println("IsDelete" + isDelete); int key =
	 * Integer.parseInt(request.getParameter("key"));
	 * 
	 * tempDetailList.remove(key);
	 * 
	 * } else if (isEdit == 1) { System.out.println("hii is edit"); int index =
	 * Integer.parseInt(request.getParameter("index"));
	 * 
	 * int noOfLeaves = Integer.parseInt(request.getParameter("noOfLeaves")); int
	 * lvTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
	 * 
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
	 * map.add("lvTypeId", lvTypeId); LeaveType editLeaveType =
	 * Constants.getRestTemplate().postForObject(Constants.url +
	 * "/getLeaveTypeById", map, LeaveType.class);
	 * 
	 * tempDetailList.get(index).setLvTypeId(lvTypeId);
	 * tempDetailList.get(index).setLvsAllotedLeaves(noOfLeaves);
	 * tempDetailList.get(index).setExVar1(editLeaveType.getLvTitle());
	 * 
	 * }
	 * 
	 * else {
	 * 
	 * int noOfLeaves = Integer.parseInt(request.getParameter("noOfLeaves")); int
	 * lvTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
	 * 
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
	 * map.add("lvTypeId", lvTypeId); LeaveType editLeaveType =
	 * Constants.getRestTemplate().postForObject(Constants.url +
	 * "/getLeaveTypeById", map, LeaveType.class);
	 * 
	 * LeaveStructureDetails tempDetail = new LeaveStructureDetails();
	 * tempDetail.setLvTypeId(lvTypeId); tempDetail.setLvsAllotedLeaves(noOfLeaves);
	 * tempDetail.setLvsDetailsId(0);
	 * tempDetail.setExVar1(editLeaveType.getLvTitle()); tempDetail.setDelStatus(1);
	 * tempDetail.setExInt1(1); tempDetail.setExInt2(1); tempDetail.setExVar2("NA");
	 * tempDetail.setIsActive(1); tempDetail.setMakerDatetime(dateTime);
	 * tempDetail.setMakerUserId(1);
	 * 
	 * tempDetailList.add(tempDetail); }
	 * 
	 * } catch (Exception e) { System.err.println("Exce In atempDocList  temp List "
	 * + e.getMessage()); e.printStackTrace(); }
	 * System.err.println(" enq Item List " + tempDetailList.toString());
	 * 
	 * return tempDetailList;
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/getLeaveStructureForEdit", method =
	 * RequestMethod.GET) public @ResponseBody LeaveStructureDetails
	 * getLeaveStructureForEdit(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * int index = Integer.parseInt(request.getParameter("index"));
	 * 
	 * return tempDetailList.get(index);
	 * 
	 * }
	 * 
	 * // submitInsertLeaveStructure
	 * 
	 * @RequestMapping(value = "/submitInsertLeaveStructure", method =
	 * RequestMethod.POST) public String
	 * submitInsertLeaveStructure(HttpServletRequest request, HttpServletResponse
	 * response) { try {
	 * System.err.println("Inside insert submitInsertLeaveStructure method");
	 * 
	 * String lvsName = request.getParameter("lvsName");
	 * 
	 * LeaveStructureHeader head = new LeaveStructureHeader();
	 * 
	 * head.setCompanyId(1); head.setDelStatus(1); head.setIsActive(1);
	 * head.setLvsName(lvsName); head.setMakerDatetime(dateTime);
	 * head.setMakerUserId(1);
	 * 
	 * head.setDetailList(tempDetailList);
	 * 
	 * LeaveStructureHeader docInsertRes = Constants.getRestTemplate()
	 * .postForObject(Constants.url + "saveLeaveStruture", head,
	 * LeaveStructureHeader.class); System.out.println("docInsertRes" +
	 * docInsertRes.toString());
	 * 
	 * } catch (Exception e) {
	 * 
	 * System.err.println("Exce In submitInsertLeaveStructure method  " +
	 * e.getMessage()); e.printStackTrace();
	 * 
	 * }
	 * 
	 * return "redirect:/addLeaveStructureHeader";
	 * 
	 * }
	 */

}
