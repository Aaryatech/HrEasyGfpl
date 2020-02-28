package com.ats.hreasy.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.DateConvertor;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.common.VpsImageUpload;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.AuthorityInformation;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.GetAuthorityIds;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.Advance.Advance;
import com.ats.hreasy.model.claim.ClaimApply;
import com.ats.hreasy.model.claim.ClaimApplyHeader;
import com.ats.hreasy.model.claim.ClaimDetail;
import com.ats.hreasy.model.claim.ClaimProof;
import com.ats.hreasy.model.claim.ClaimStructureDetail;
import com.ats.hreasy.model.claim.ClaimTemp;
import com.ats.hreasy.model.claim.ClaimTrail;
import com.ats.hreasy.model.claim.GetClaimApplyAuthwise;
import com.ats.hreasy.model.claim.GetClaimHead;
import com.ats.hreasy.model.claim.GetClaimTrailStatus;
import com.ats.hreasy.model.claim.GetEmployeeClaimStrudt;
import com.ats.hreasy.model.claim.GetEmployeeInfo;
import com.ats.hreasy.model.claim.TempClaimDetail;
import com.ats.hreasy.model.claim.EmployeeInfo;

@Controller
@Scope("session")
public class ClaimApplicationController {
	List<TempClaimDetail> tempDocList = new ArrayList<TempClaimDetail>();

	double tot_amt = 0.0;
	ClaimTemp ct = new ClaimTemp();
	ArrayList<String> a = new ArrayList<String>();
	List<MultipartFile> imgList = new ArrayList<MultipartFile>();
	List<ClaimProof> proofList = new ArrayList<ClaimProof>();
	ClaimApplyHeader docHead = new ClaimApplyHeader();

	@RequestMapping(value = "/showApplyForClaim", method = RequestMethod.GET)
	public ModelAndView showEmpList(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showApplyForClaim", "showApplyForClaim", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("claim/applyForClaim");

			try {
				GetEmployeeInfo temp = new GetEmployeeInfo();
				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				map.add("companyId", 1);
				map.add("empId", userObj.getEmpId());

				GetEmployeeInfo[] employeeDepartment = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmpInfoClaimAuthWise", map, GetEmployeeInfo[].class);

				List<GetEmployeeInfo> employeeDepartmentlist = new ArrayList<GetEmployeeInfo>(
						Arrays.asList(employeeDepartment));

				int flag = 1;

				map = new LinkedMultiValueMap<>();
				map.add("empId", userObj.getEmpId());

				GetEmployeeInfo editEmp = Constants.getRestTemplate().postForObject(Constants.url + "/GetEmployeeInfo",
						map, GetEmployeeInfo.class);
				model.addObject("editEmp", editEmp);
				// System.err.println("self" + editEmp.toString());
				// System.err.println("oteher" + employeeDepartmentlist.toString());

				for (int i = 0; i < employeeDepartmentlist.size(); i++) {
					if (employeeDepartmentlist.get(i).getEmpId() == userObj.getEmpId()) {
						flag = 0;
						System.err.println(" matched");
						employeeDepartmentlist.remove(i);
						break;
					}

				}
				/* if(flag == 1) { */
				System.err.println("not matched");

				temp.setCompanyId(editEmp.getCompanyId());
				temp.setCompanyName(editEmp.getCompanyName());
				temp.setEmpCategory(editEmp.getEmpCategory());
				temp.setEmpCatId(editEmp.getEmpCatId());
				temp.setEmpCode(editEmp.getEmpCode());
				temp.setEmpDept(editEmp.getEmpDept());
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
				temp.setEmpDeptShortName(editEmp.getEmpDeptShortName());
				temp.setEmpCatShortName(editEmp.getEmpCatShortName());
				temp.setEmpTypeShortName(editEmp.getEmpTypeShortName());
				// employeeDepartmentlist.add(temp);
				/* } */

				temp.setExVar1(FormValidation.Encrypt(String.valueOf(editEmp.getEmpId())));
				// System.err.println("temp list is claim " + temp.toString());
				for (int i = 0; i < employeeDepartmentlist.size(); i++) {
					// System.out.println("employeeDepartmentlist.get(i).getEmpId()"+employeeDepartmentlist.get(i).getEmpId());
					employeeDepartmentlist.get(i).setExVar1(
							FormValidation.Encrypt(String.valueOf(employeeDepartmentlist.get(i).getEmpId())));
				}

				model.addObject("empList", employeeDepartmentlist);
				// System.err.println("emp list is " + employeeDepartment.toString());
				model.addObject("tempList", temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/showClaimApply", method = RequestMethod.GET)
	public ModelAndView showClaimApply(HttpServletRequest request, HttpServletResponse response) {
		tempDocList = new ArrayList<TempClaimDetail>();
		ModelAndView model = new ModelAndView("claim/claimApply");

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			String base64encodedString = request.getParameter("empId");
			String empId = FormValidation.DecodeKey(base64encodedString);
			// System.err.println("empIDDDD list " + empId);
			// neew claim types

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);

			GetEmployeeClaimStrudt[] employeeDoc = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEmpClaimStructure", map, GetEmployeeClaimStrudt[].class);

			List<GetEmployeeClaimStrudt> claimTypeList = new ArrayList<GetEmployeeClaimStrudt>(
					Arrays.asList(employeeDoc));
			// System.out.println("getEmpClaimStructure list " + claimTypeList.toString());
			model.addObject("claimTypeList", claimTypeList);
			map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			EmployeeMaster editEmp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpInfoById", map,
					EmployeeMaster.class);
			model.addObject("editEmp", editEmp);
			// System.out.println("empInfo list " + editEmp.toString());

			/*
			 * map = new LinkedMultiValueMap<>(); map.add("companyId",
			 * userObj.getCompanyId()); map.add("empId", empId); ProjectHeader[]
			 * employeeDoc1 = Constants.getRestTemplate() .postForObject(Constants.url +
			 * "/getProjectsListByCompanyIdAndEmpId", map, ProjectHeader[].class);
			 * 
			 * List<ProjectHeader> proTypeList = new
			 * ArrayList<ProjectHeader>(Arrays.asList(employeeDoc1));
			 * System.out.println("proTypeList list " + proTypeList.toString());
			 * model.addObject("projectTypeList", proTypeList);
			 */
			map = new LinkedMultiValueMap<String, Object>();
			map.add("empId", empId);

			AuthorityInformation authorityInformation = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAuthorityInfoByEmpId", map, AuthorityInformation.class);
			model.addObject("authorityInformation", authorityInformation);

			// System.err.println("authorityInformation is " +
			// authorityInformation.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getClaimTypeById", method = RequestMethod.GET)
	public @ResponseBody GetEmployeeClaimStrudt getClaimTypeById(HttpServletRequest request,
			HttpServletResponse response) {
		GetEmployeeClaimStrudt tempDocList = new GetEmployeeClaimStrudt();
		try {
			String empId = request.getParameter("empId");
			String claimTypeId = request.getParameter("claimTypeId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);
			map.add("typeId", claimTypeId);

			tempDocList = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpClaimStructureByClaimType",
					map, GetEmployeeClaimStrudt.class);

		} catch (Exception e) {
			System.err.println("Exce In atempDocList  temp List " + e.getMessage());
			e.printStackTrace();
		}
		System.err.println(" enq Item List " + tempDocList.toString());

		return tempDocList;

	}

	/*
	 * @RequestMapping(value = "/insertSubmitClaim", method = RequestMethod.POST)
	 * public String submitInsertLeave(HttpServletRequest request,
	 * HttpServletResponse response) { String empId1 =
	 * request.getParameter("empId"); try { HttpSession session =
	 * request.getSession(); Date date = new Date(); SimpleDateFormat sf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); SimpleDateFormat dateTimeInGMT = new
	 * SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	 * 
	 * LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
	 * 
	 * // String compName = request.getParameter("1"); String claimDate =
	 * request.getParameter("claimDate"); int projectTypeId =
	 * Integer.parseInt(request.getParameter("projectTypeId")); int claimTypeId =
	 * Integer.parseInt(request.getParameter("claimTypeId")); int claimAmt =
	 * Integer.parseInt(request.getParameter("claimAmt")); int empId =
	 * Integer.parseInt(request.getParameter("empId"));
	 * 
	 * // get Authority ids
	 * 
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
	 * map.add("empId", empId); map.add("companyId", userObj.getCompanyId());
	 * 
	 * GetAuthorityIds editEmp =
	 * Constants.getRestTemplate().postForObject(Constants.url + "/getClaimAuthIds",
	 * map, GetAuthorityIds.class);
	 * 
	 * int stat = 0;
	 * 
	 * if (editEmp.getFinAuthEmpId() == userObj.getEmpId()) { stat = 3; } else if
	 * (editEmp.getIniAuthEmpId() == userObj.getEmpId()) { stat = 2; } else { stat =
	 * 1; }
	 * 
	 * System.out.println("stat is " + stat); String remark = null;
	 * 
	 * // System.out.println("dayType" + dayTypeName);
	 * 
	 * try { remark = request.getParameter("claimRemark"); } catch (Exception e) {
	 * remark = "NA"; }
	 * 
	 * Boolean ret = false;
	 * 
	 * if (FormValidation.Validaton(claimDate, "") == true) {
	 * 
	 * ret = true; System.out.println("claimDate" + ret); } if
	 * (FormValidation.Validaton(request.getParameter("projectTypeId"), "") == true)
	 * {
	 * 
	 * ret = true; System.out.println("projectTypeId" + ret); }
	 * 
	 * if (FormValidation.Validaton(request.getParameter("claimTypeId"), "") ==
	 * true) {
	 * 
	 * ret = true; System.out.println("claimTypeId" + ret); }
	 * 
	 * if (FormValidation.Validaton(request.getParameter("claimAmt"), "") == true) {
	 * 
	 * ret = true; System.out.println("claimAmt" + ret); }
	 * 
	 * if (ret == false) {
	 * 
	 * ClaimApply leaveSummary = new ClaimApply();
	 * 
	 * leaveSummary.setEmpId(empId); leaveSummary.setClaimAmount(claimAmt);
	 * leaveSummary.setClaimDate(DateConvertor.convertToYMD(claimDate));
	 * leaveSummary.setClaimFinalStatus(stat); leaveSummary.setClaimRemarks(remark);
	 * leaveSummary.setClaimTypeId(claimTypeId);
	 * leaveSummary.setProjectId(projectTypeId); leaveSummary.setCirculatedTo("1");
	 * 
	 * leaveSummary.setExInt1(stat); leaveSummary.setExInt2(1);
	 * leaveSummary.setExInt3(1); leaveSummary.setExVar1("NA");
	 * leaveSummary.setExVar2("NA"); leaveSummary.setExVar3("NA");
	 * leaveSummary.setIsActive(1); leaveSummary.setDelStatus(1);
	 * leaveSummary.setMakerUserId(userObj.getUserId());
	 * leaveSummary.setMakerEnterDatetime(sf.format(date));
	 * 
	 * ClaimApply res = Constants.getRestTemplate().postForObject(Constants.url +
	 * "/saveClaimApply", leaveSummary, ClaimApply.class);
	 * 
	 * if (res != null) { ClaimTrail lt = new ClaimTrail();
	 * 
	 * lt.setEmpRemarks(remark); ; lt.setClaimId(res.getClaimId());
	 * lt.setClaimStatus(stat); lt.setEmpId(empId); lt.setExInt1(1);
	 * lt.setExInt2(1); lt.setExInt3(1); lt.setExVar1("NA"); lt.setExVar2("NA");
	 * lt.setExVar3("NA");
	 * 
	 * lt.setMakerUserId(userObj.getUserId());
	 * lt.setMakerEnterDatetime(sf.format(date));
	 * 
	 * ClaimTrail res1 = Constants.getRestTemplate().postForObject(Constants.url +
	 * "/saveClaimTrail", lt, ClaimTrail.class); if (res1 != null) {
	 * 
	 * map = new LinkedMultiValueMap<>(); map.add("claimId", res.getClaimId());
	 * map.add("trailId", res1.getClaimTrailPkey()); Info info =
	 * Constants.getRestTemplate().postForObject(Constants.url +
	 * "/updateClaimTrailId", map, Info.class);
	 * 
	 * } }
	 * 
	 * } else { session.setAttribute("errorMsg", "Failed to Insert Record"); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * // return "redirect:/showApplyForClaim"; return
	 * "redirect:/showClaimList?empId=" + FormValidation.Encrypt(empId1);
	 * 
	 * }
	 */

	//////////////////////////////// **********************Claim
	//////////////////////////////// Approvals******************************//////////////////

	@RequestMapping(value = "/showClaimApprovalByAuthority", method = RequestMethod.GET)
	public ModelAndView showClaimApprovalByAuthority(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showClaimApprovalByAuthority", "showClaimApprovalByAuthority", 1, 0, 0,
				0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("claim/claimApprovalByAuthorities");

			try {
				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empId", userObj.getEmpId());

				GetClaimApplyAuthwise[] employeeDoc = Constants.getRestTemplate().postForObject(
						Constants.url + "/getClaimApplyListForPending", map, GetClaimApplyAuthwise[].class);

				List<GetClaimApplyAuthwise> claimList = new ArrayList<GetClaimApplyAuthwise>(
						Arrays.asList(employeeDoc));

				for (int i = 0; i < claimList.size(); i++) {
					claimList.get(i)
							.setCirculatedTo(FormValidation.Encrypt(String.valueOf(claimList.get(i).getCaHeadId())));
					claimList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(claimList.get(i).getEmpId())));
					// claimList.get(i).setClaimDate(DateConvertor.convertToDMY(claimList.get(i).getClaimDate()));
					
					claimList.get(i).setCaFromDt(DateConvertor.convertToDMY(claimList.get(i).getCaFromDt()));
					claimList.get(i).setCaToDt(DateConvertor.convertToDMY(claimList.get(i).getCaToDt()));
					
				 
				}

				// for Info

				map = new LinkedMultiValueMap<>();
				map.add("empId", userObj.getEmpId());

				GetClaimApplyAuthwise[] employeeDoc1 = Constants.getRestTemplate().postForObject(
						Constants.url + "/getClaimApplyListForInformation", map, GetClaimApplyAuthwise[].class);

				List<GetClaimApplyAuthwise> claimList1 = new ArrayList<GetClaimApplyAuthwise>(
						Arrays.asList(employeeDoc1));
				System.out.println("claimList1 " + claimList1.toString());
				for (int i = 0; i < claimList1.size(); i++) {

					claimList1.get(i)
							.setCirculatedTo(FormValidation.Encrypt(String.valueOf(claimList1.get(i).getCaHeadId())));
					claimList1.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(claimList1.get(i).getEmpId())));
					// claimList1.get(i).setClaimDate(DateConvertor.convertToDMY(claimList.get(i).getClaimDate()));
					
					claimList1.get(i).setCaFromDt(DateConvertor.convertToDMY(claimList1.get(i).getCaFromDt()));
					claimList1.get(i).setCaToDt(DateConvertor.convertToDMY(claimList1.get(i).getCaToDt()));
					
					
			 
				}

				model.addObject("list2Count", claimList1.size());
				model.addObject("claimListForApproval1", claimList1);
				model.addObject("claimListForApproval", claimList);
				model.addObject("list1Count", claimList.size());
				model.addObject("empIdOrig", userObj.getEmpId());

				/// System.out.println("lv leaveList list1 info " + claimList1.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/approveClaimByAuth", method = RequestMethod.GET)
	public ModelAndView approveLeaveByInitialAuth(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("claim/claimApprovalRemark");
		try {

			int empId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("empId")));
			int claimId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("claimId")));
			String stat = request.getParameter("stat");

			model.addObject("empId", empId);
			model.addObject("claimId", claimId);
			model.addObject("stat", stat);
			// System.out.println("empId" + empId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("claimId", claimId);
			GetClaimTrailStatus[] employeeDoc = Constants.getRestTemplate().postForObject(
					Constants.url + "/getEmpClaimInfoListByTrailEmpId", map, GetClaimTrailStatus[].class);

			List<GetClaimTrailStatus> employeeList = new ArrayList<GetClaimTrailStatus>(Arrays.asList(employeeDoc));

			model.addObject("employeeList", employeeList);
			// System.out.println("trail *******" + employeeList.toString());

			GetClaimTrailStatus trailHead = new GetClaimTrailStatus();

			trailHead = employeeList.get(0);
			trailHead.setCaFromDt(DateConvertor.convertToDMY(trailHead.getCaFromDt()));
			trailHead.setCaToDt(DateConvertor.convertToDMY(trailHead.getCaToDt()));
			// System.out.println("trailHead*******" + trailHead.toString());

			model.addObject("lvEmp", trailHead);
			map = new LinkedMultiValueMap<>();
			map.add("claimId", claimId);

			ClaimDetail[] employeeDoc1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getClaimDetailListByEmpId", map, ClaimDetail[].class);

			List<ClaimDetail> claimDetList = new ArrayList<ClaimDetail>(Arrays.asList(employeeDoc1));
			// System.err.println("claim list" + claimDetList.toString());

			for (int i = 0; i < claimDetList.size(); i++) {

				claimDetList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(claimDetList.get(i).getClaimId())));

			}

			model.addObject("claimDetList", claimDetList);

			map = new LinkedMultiValueMap<>();
			map.add("empId", empId);

			ClaimStructureDetail[] claimStructureDetail = Constants.getRestTemplate().postForObject(
					Constants.url + "/getClaimStructureDetailByEmpId", map, ClaimStructureDetail[].class);
			List<ClaimStructureDetail> claimTypeList = new ArrayList<ClaimStructureDetail>(
					Arrays.asList(claimStructureDetail));
			model.addObject("claimTypeList", claimTypeList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	/*
	 * @RequestMapping(value = "/approveClaimByAuth1", method = RequestMethod.POST)
	 * public String insertLeave(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * 
	 * try { HttpSession session = request.getSession(); LoginResponse userObj =
	 * (LoginResponse) session.getAttribute("userInfo");
	 * 
	 * Date date = new Date(); SimpleDateFormat sf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); SimpleDateFormat dateTimeInGMT = new
	 * SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); System.err.println("emp data :::" +
	 * request.getParameter("empId")); int empId =
	 * Integer.parseInt((request.getParameter("empId"))); int claimId =
	 * Integer.parseInt((request.getParameter("claimId"))); String stat =
	 * request.getParameter("stat"); int stat1 = Integer.parseInt(stat);
	 * 
	 * String msg = null; if (stat1 == 2 || stat1 == 3) { msg = "Approved"; } else
	 * if (stat1 == 8 || stat1 == 9) { msg = "Rejected"; } else if (stat1 == 7) {
	 * msg = "Cancelled"; }
	 * 
	 * String remark = null; try { remark = request.getParameter("remark"); } catch
	 * (Exception e) { remark = "NA"; } System.err.println("link data :::" + empId +
	 * claimId + stat);
	 * 
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
	 * map.add("claimId", claimId); map.add("status", stat); Info info =
	 * Constants.getRestTemplate().postForObject(Constants.url +
	 * "/updateClaimStatus", map, Info.class);
	 * 
	 * if (info.isError() == false) { ClaimTrail lt = new ClaimTrail();
	 * 
	 * lt.setEmpRemarks(remark);
	 * 
	 * lt.setClaimId(claimId); ;
	 * 
	 * lt.setClaimStatus(Integer.parseInt(stat)); lt.setEmpId(empId);
	 * lt.setExInt1(1); lt.setExInt2(1); lt.setExInt3(1); lt.setExVar1("NA");
	 * lt.setExVar2("NA"); lt.setExVar3("NA");
	 * 
	 * lt.setMakerUserId(userObj.getUserId());
	 * lt.setMakerEnterDatetime(sf.format(date));
	 * 
	 * ClaimTrail res1 = Constants.getRestTemplate().postForObject(Constants.url +
	 * "/saveClaimTrail", lt, ClaimTrail.class); if (res1 != null) {
	 * 
	 * map = new LinkedMultiValueMap<>(); map.add("claimId", claimId);
	 * map.add("trailId", res1.getClaimTrailPkey()); Info info1 =
	 * Constants.getRestTemplate().postForObject(Constants.url +
	 * "/updateClaimTrailId", map, Info.class);
	 * 
	 * if (info1.isError() == false) { session.setAttribute("successMsg", "Record "
	 * + msg + " Successfully"); } else { session.setAttribute("errorMsg",
	 * "Failed to " + msg + " Record"); }
	 * 
	 * } }
	 * 
	 * else { session.setAttribute("errorMsg", "Failed Transaction"); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return "redirect:/showClaimApprovalByAuthority";
	 * 
	 * }
	 */

	@RequestMapping(value = "/approveClaimByAuth1", method = RequestMethod.POST)
	public String insertLeave(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			// System.err.println("emp data :::" + request.getParameter("empId"));
			int empId = Integer.parseInt((request.getParameter("empId")));
			int claimId = Integer.parseInt((request.getParameter("claimId")));
			String stat = request.getParameter("stat");
			String month = null;
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			int stat1 = Integer.parseInt(stat);
			if (stat1 == 3) {
				month = request.getParameter("date");
				String temp[] = month.split("-");
				map.add("month", temp[0]);
				map.add("year", temp[1]);

			} else {
				map.add("month", 0);
				map.add("year", 0);
			}

			String msg = null;
			if (stat1 == 2 || stat1 == 3) {
				msg = "Approved";
			} else if (stat1 == 8 || stat1 == 9) {
				msg = "Rejected";
			} else if (stat1 == 7) {
				msg = "Cancelled";
			}

			String remark = null;
			try {
				remark = request.getParameter("remark");
			} catch (Exception e) {
				remark = "NA";
			}

			map.add("claimId", claimId);
			map.add("status", stat);
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateClaimStatus", map,
					Info.class);

			if (info.isError() == false) {
				// System.err.println("link data :::" + empId + claimId + stat);
				ClaimTrail lt = new ClaimTrail();

				lt.setEmpRemarks(remark);

				lt.setClaimId(claimId);
				;

				lt.setClaimStatus(Integer.parseInt(stat));
				lt.setEmpId(empId);
				lt.setExInt1(1);
				lt.setExInt2(1);
				lt.setExInt3(1);
				lt.setExVar1("NA");
				lt.setExVar2("NA");
				lt.setExVar3("NA");

				lt.setMakerUserId(userObj.getUserId());
				lt.setMakerEnterDatetime(sf.format(date));

				ClaimTrail res1 = Constants.getRestTemplate().postForObject(Constants.url + "/saveClaimTrail", lt,
						ClaimTrail.class);
				if (res1 != null) {

					map = new LinkedMultiValueMap<>();
					map.add("claimId", claimId);
					map.add("trailId", res1.getClaimTrailPkey());
					Info info1 = Constants.getRestTemplate().postForObject(Constants.url + "/updateClaimTrailId", map,
							Info.class);

					if (info1.isError() == false) {
						session.setAttribute("successMsg", "Record " + msg + " Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to " + msg + " Record");
					}

				}
			}

			else {
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showClaimApprovalByAuthority";

	}

	//////////////////////////////// **********************Claim
	//////////////////////////////// Proof******************************//////////////////

	@RequestMapping(value = "/showClaimList", method = RequestMethod.GET)
	public ModelAndView showClaimList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("claim/claimHistory");
		try {

			int empId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("empId")));
			// System.err.println("emp idis " + empId);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", empId);

			GetClaimHead[] employeeDoc1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getClaimHeadListByEmpId", map, GetClaimHead[].class);

			List<GetClaimHead> claimList1 = new ArrayList<GetClaimHead>(Arrays.asList(employeeDoc1));
			// System.err.println("claim list" + claimList1.toString());

			for (int i = 0; i < claimList1.size(); i++) {

				claimList1.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(claimList1.get(i).getCaHeadId())));
				claimList1.get(i).setClaimFromDate(DateConvertor.convertToDMY(claimList1.get(i).getClaimFromDate()));
				claimList1.get(i).setClaimToDate(DateConvertor.convertToDMY(claimList1.get(i).getClaimToDate()));

			}

			model.addObject("claimList1", claimList1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/showClaimDetailList", method = RequestMethod.GET)
	public ModelAndView showClaimDetailList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("claim/claimHisClaimDetils");
		try {

			int claimId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("claimId")));
			// System.err.println("claimId idis " + claimId);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("claimId", claimId);

			ClaimDetail[] employeeDoc1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getClaimDetailListByEmpId", map, ClaimDetail[].class);

			List<ClaimDetail> claimList1 = new ArrayList<ClaimDetail>(Arrays.asList(employeeDoc1));
			System.err.println("ClaimDetail list" + claimList1.toString());

			for (int i = 0; i < claimList1.size(); i++) {

				claimList1.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(claimList1.get(i).getClaimId())));

			}

			model.addObject("claimList1", claimList1);

			map = new LinkedMultiValueMap<>();
			map.add("claimId", claimId);
			GetClaimTrailStatus[] employeeDoc = Constants.getRestTemplate().postForObject(
					Constants.url + "/getEmpClaimInfoListByTrailEmpId", map, GetClaimTrailStatus[].class);

			List<GetClaimTrailStatus> employeeList = new ArrayList<GetClaimTrailStatus>(Arrays.asList(employeeDoc));

			model.addObject("employeeList", employeeList);
			// System.out.println("trail *******" + employeeList.toString());

			GetClaimTrailStatus trailHead = new GetClaimTrailStatus();

			trailHead = employeeList.get(0);
			trailHead.setCaFromDt(DateConvertor.convertToDMY(trailHead.getCaFromDt()));
			trailHead.setCaToDt(DateConvertor.convertToDMY(trailHead.getCaToDt()));
			// System.out.println("trailHead*******" + trailHead.toString());

			model.addObject("lvEmp", trailHead);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}
	// ************************************claim new
	// process************************************

	@RequestMapping(value = "/addClaimDetailProcess", method = RequestMethod.GET)
	public @ResponseBody List<TempClaimDetail> addClaimDetail(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));
			// System.err.println("in addClaimDetail******** " + isEdit + isDelete);
			if (isDelete == 1) {
				int key = Integer.parseInt(request.getParameter("key"));

				tempDocList.remove(key);

			} else if (isEdit == 1) {
				int index = Integer.parseInt(request.getParameter("index"));

				float claimAmt = Float.parseFloat(request.getParameter("claimAmt"));
				int claimTypeId = Integer.parseInt(request.getParameter("claimTypeId"));
				String claimRemark = request.getParameter("claimRemark");
				String lvTypeName = request.getParameter("lvTypeName");

				tempDocList.get(index).setRemark(claimRemark);
				tempDocList.get(index).setClaimAmount(claimAmt);
				tempDocList.get(index).setTypeId(claimTypeId);
				tempDocList.get(index).setLvTypeName(lvTypeName);
			}

			else {

				float claimAmt = Float.parseFloat(request.getParameter("claimAmt"));
				String claimRemark = request.getParameter("claimRemark");
				int claimTypeId = Integer.parseInt(request.getParameter("claimTypeId"));
				String lvTypeName = request.getParameter("lvTypeName");

				TempClaimDetail tempDoc = new TempClaimDetail();
				tempDoc.setClaimAmount(claimAmt);
				tempDoc.setRemark(claimRemark);
				tempDoc.setTypeId(claimTypeId);
				tempDoc.setLvTypeName(lvTypeName);
				tempDocList.add(tempDoc);
			}

		} catch (Exception e) {
			System.err.println("Exce In atempDocList  temp List " + e.getMessage());
			e.printStackTrace();
		}
		// System.err.println(" enq Item List " + tempDocList.toString());

		return tempDocList;

	}

	@RequestMapping(value = "/getClaimForEdit", method = RequestMethod.GET)
	public @ResponseBody TempClaimDetail getClaimForEdit(HttpServletRequest request, HttpServletResponse response) {

		int index = Integer.parseInt(request.getParameter("index"));

		return tempDocList.get(index);

	}

	@RequestMapping(value = "/showClaimProof", method = RequestMethod.POST)
	public ModelAndView showClaimProof(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("claim/claimProof");
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

			proofList = new ArrayList<ClaimProof>();

			// int projectTypeId = Integer.parseInt(request.getParameter("projectTypeId"));
			int empId = Integer.parseInt(request.getParameter("empId"));
			String claim_title = request.getParameter("claim_title");
			String leaveDateRange = request.getParameter("claimDate");

			String[] arrOfStr = leaveDateRange.split("to", 2);
			// System.err.println("date1 is " + arrOfStr[0].toString().trim());
			// System.err.println("date2 is " + arrOfStr[1].toString().trim());

			docHead.setCirculatedTo("1");
			docHead.setCafromDt(DateConvertor.convertToYMD(arrOfStr[0].toString().trim()));
			docHead.setCaToDt(DateConvertor.convertToYMD(arrOfStr[1].toString().trim()));
			docHead.setClaimTitle(claim_title);
			docHead.setDelStatus(1);
			docHead.setIsActive(1);
			docHead.setEmpId(empId);
			docHead.setProjId(1);
			docHead.setExInt1(1);
			docHead.setExInt2(1);
			docHead.setExInt3(1);
			docHead.setExVar1("NA");
			docHead.setExVar2("NA");
			docHead.setExVar3("NA");
			docHead.setMakerUserId(userObj.getUserId());
			docHead.setMakerEnterDatetime(sf.format(date));

			List<ClaimApply> docDetailList = new ArrayList<>();

			for (int i = 0; i < tempDocList.size(); i++) {

				ClaimApply dDetail = new ClaimApply();

				dDetail.setIsActive(1);
				dDetail.setDelStatus(1);
				dDetail.setExInt1(0);
				dDetail.setExInt2(0);
				dDetail.setExInt3(0);
				dDetail.setExVar1("NA");
				dDetail.setExVar2("NA");
				dDetail.setExVar3("NA");
				dDetail.setMakerUserId(userObj.getUserId());
				dDetail.setMakerEnterDatetime(sf.format(date));
				dDetail.setClaimAmount(tempDocList.get(i).getClaimAmount());
				dDetail.setClaimRemarks(tempDocList.get(i).getRemark());
				dDetail.setClaimTypeId(tempDocList.get(i).getTypeId());
				dDetail.setMakerUserId(userObj.getUserId());
				dDetail.setMakerEnterDatetime(sf.format(date));

				docDetailList.add(dDetail);

				tot_amt = tot_amt + tempDocList.get(i).getClaimAmount();

			}

			docHead.setDetailList(docDetailList);

			float amt = 0;

			for (int i = 0; i < docHead.getDetailList().size(); i++) {

				amt = amt + docHead.getDetailList().get(i).getClaimAmount();

			}
			docHead.setClaimAmount((float) amt);
			// System.err.println("temp List " + tempDocList.toString());
			// System.err.println("head List " + docHead.toString());
		} catch (Exception e) {
			e.printStackTrace();
			docHead.setClaimAmount(0);
		}
		return model;
	}

	@RequestMapping(value = "/uploadOtherMediaProccessForClaim", method = RequestMethod.POST)
	public void uploadOtherMediaProccessForClaim(@RequestParam("file") List<MultipartFile> file,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

			String imageName = new String();

			// System.out.println("sdfsdfsdf" + file.get(0).getOriginalFilename());
			VpsImageUpload upload = new VpsImageUpload();

			imageName = dateTimeInGMT.format(date) + "_" + file.get(0).getOriginalFilename();

			// System.out.println("sdfsdfsdf" + file.get(0).getOriginalFilename());
			imageName = dateTimeInGMT.format(date) + "_" + file.get(0).getOriginalFilename();

			ClaimProof company = new ClaimProof();

			company.setIsActive(1);
			company.setDelStatus(1);
			company.setMakerUserId(userObj.getUserId());
			company.setMakerEnterDatetime(sf.format(date));

			try {
				upload.saveUploadedImge(file.get(0), Constants.imageSaveUrl, imageName, Constants.values, 0, 0, 0, 0,
						0);
				company.setCpDocPath(imageName);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			proofList.add(company);

			// System.err.println("temp claim imaglist:::" + imgList.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// return "redirect:/fileUpload";
		// return "{}";

	}

	@RequestMapping(value = "/uploadClaimProof", method = RequestMethod.POST)
	public String uploadClaimProof(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			Date date = new Date();
			SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			VpsImageUpload upload = new VpsImageUpload();

			// int claimId = Integer.parseInt(request.getParameter("claimId"));

			String remark = request.getParameter("remark");

			int empId = ct.getEmpId();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("empId", docHead.getEmpId());
			map.add("companyId", 1);

			GetAuthorityIds editEmp = Constants.getRestTemplate().postForObject(Constants.url + "/getClaimAuthIds", map,
					GetAuthorityIds.class);

			int stat = 0;

			if (editEmp.getFinAuthEmpId() == userObj.getEmpId()) {
				stat = 3;

			} else if (editEmp.getIniAuthEmpId() == userObj.getEmpId()) {
				stat = 2;
			} else {
				stat = 1;
			}

			docHead.setClaimStatus(stat);
			String dt = docHead.getCafromDt();

			String temp[] = dt.split("-");

			if (stat == 3) {
				docHead.setMonth(Integer.parseInt(temp[1]));
				docHead.setYear(Integer.parseInt(temp[0]));
				docHead.setIsPaid(0);
			} else {
				docHead.setMonth(0);
				docHead.setYear(0);
				docHead.setIsPaid(0);
			}

			ClaimApplyHeader res = Constants.getRestTemplate()
					.postForObject(Constants.url + "/saveClaimHeaderAndDetail", docHead, ClaimApplyHeader.class);

			// get Authority ids

			if (res != null) {
				// System.out.println("claim saved success");
				ClaimTrail lt = new ClaimTrail();

				lt.setEmpRemarks(docHead.getClaimTitle());
				;
				lt.setClaimId(res.getCaHeadId());
				lt.setClaimStatus(stat);
				lt.setEmpId(docHead.getEmpId());
				lt.setExInt1(1);
				lt.setExInt2(1);
				lt.setExInt3(1);
				lt.setExVar1("NA");
				lt.setExVar2("NA");
				lt.setExVar3("NA");

				lt.setMakerUserId(userObj.getUserId());
				lt.setMakerEnterDatetime(sf.format(date));

				ClaimTrail res1 = Constants.getRestTemplate().postForObject(Constants.url + "/saveClaimTrail", lt,
						ClaimTrail.class);
				// System.err.println("trail detail::" + res1.toString());

				if (res1.isError() == false) {
					// System.out.println("claim trail saved success");
					map = new LinkedMultiValueMap<>();
					map.add("claimId", res.getCaHeadId());
					map.add("trailId", res1.getClaimTrailPkey());
					Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateClaimTrailId", map,
							Info.class);

				}
			}

			///////////////// proof image
			// System.err.println("img list final ::" + imgList.toString());
			for (int i = 0; i < proofList.size(); i++) {

				proofList.get(i).setClaimId(res.getCaHeadId());

			}
			List<ClaimProof> res1 = Constants.getRestTemplate().postForObject(Constants.url + "/saveClaimProof",
					proofList, List.class);

			// System.err.println("res1 claim is" + res1.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showClaimList?empId=" + FormValidation.Encrypt(String.valueOf(docHead.getEmpId()));
	}

	@RequestMapping(value = "/showClaimProofAgain", method = RequestMethod.GET)
	public ModelAndView showClaimProofAgain(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("claim/claimProofAgain");
		int claimId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("claimId")));
		model.addObject("claimId", claimId);

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("claimId", claimId);

			ClaimProof[] employeeDoc1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getClaimProofByClaimId", map, ClaimProof[].class);

			List<ClaimProof> claimProofList1 = new ArrayList<ClaimProof>(Arrays.asList(employeeDoc1));
			/// System.err.println("claimProofList1 list" + claimProofList1.toString());
			for (int i = 0; i < claimProofList1.size(); i++) {

				claimProofList1.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(claimProofList1.get(i).getCpId())));
				claimProofList1.get(i)
						.setExVar2(FormValidation.Encrypt(String.valueOf(claimProofList1.get(i).getClaimId())));

			}

			model.addObject("claimProofList1", claimProofList1);
			model.addObject("fileUrl", Constants.imageShowUrl);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/uploadClaimProofAgain", method = RequestMethod.POST)
	public void uploadClaimProof(@RequestParam("file") List<MultipartFile> file, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			Date date = new Date();
			SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			VpsImageUpload upload = new VpsImageUpload();
			// System.out.println("sdfsdfsdf" + file.get(0).getOriginalFilename());

			int claimId = Integer.parseInt(request.getParameter("claimId"));
			// String remark = request.getParameter("remark");

			Boolean ret = false;

			if (FormValidation.Validaton(file.get(0).getOriginalFilename(), "") == true) {
				ret = true;
			}

			if (ret == false) {

				ClaimProof company = new ClaimProof();

				company.setCpDocRemark(null);
				company.setClaimId(claimId);
				company.setIsActive(1);
				company.setDelStatus(1);
				company.setMakerUserId(userObj.getUserId());
				company.setMakerEnterDatetime(sf.format(date));

				String imageName = new String();
				imageName = dateTimeInGMT.format(date) + "_" + file.get(0).getOriginalFilename();

				try {
					upload.saveUploadedImge(file.get(0), Constants.imageSaveUrl, imageName, Constants.values, 0, 0, 0,
							0, 0);
					company.setCpDocPath(imageName);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				ClaimProof res = Constants.getRestTemplate().postForObject(Constants.url + "/saveSingleClaimProof",
						company, ClaimProof.class);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// *************end*****************************

	@RequestMapping(value = "/deleteClaimProof", method = RequestMethod.GET)
	public String deletdeleteClaimProofeEmployee(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String claimId1 = new String();
		try {

			String base64encodedString = request.getParameter("claimProofId");
			String claimProofId = FormValidation.DecodeKey(base64encodedString);
			String base64encodedString1 = request.getParameter("claimId");
			claimId1 = FormValidation.DecodeKey(base64encodedString1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("cpId", claimProofId);
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteClaimProof", map, Info.class);

			if (info.isError() == false) {
				session.setAttribute("successMsg", "Record Deleted Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Delete");
			}
			System.out.println("claimProofId " + claimProofId + " claimId1 " + claimId1);

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		return "redirect:/showClaimProofAgain?claimId=" + FormValidation.Encrypt(claimId1);
	}

	/// *******************************History******************************************

	@RequestMapping(value = "/claimDetailHistory", method = RequestMethod.GET)
	public ModelAndView claimDetailHistory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("claim/claim_trail_history");

		try {

			int empId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("empId")));
			int claimId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("claimId")));
			String stat = request.getParameter("stat");

			model.addObject("empId", empId);
			model.addObject("claimId", claimId);
			model.addObject("stat", stat);
			// System.out.println("empId" + empId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("claimId", claimId);
			GetClaimTrailStatus[] employeeDoc = Constants.getRestTemplate().postForObject(
					Constants.url + "/getEmpClaimInfoListByTrailEmpId", map, GetClaimTrailStatus[].class);

			List<GetClaimTrailStatus> employeeList = new ArrayList<GetClaimTrailStatus>(Arrays.asList(employeeDoc));

			model.addObject("employeeList", employeeList);
			// System.out.println("trail *******" + employeeList.toString());

			GetClaimTrailStatus trailHead = new GetClaimTrailStatus();

			trailHead = employeeList.get(0);
			trailHead.setCaFromDt(DateConvertor.convertToDMY(trailHead.getCaFromDt()));
			trailHead.setCaToDt(DateConvertor.convertToDMY(trailHead.getCaToDt()));
			// System.out.println("trailHead*******" + trailHead.toString());

			model.addObject("lvEmp", trailHead);
			map = new LinkedMultiValueMap<>();
			map.add("claimId", claimId);

			ClaimDetail[] employeeDoc1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getClaimDetailListByEmpId", map, ClaimDetail[].class);

			List<ClaimDetail> claimDetList = new ArrayList<ClaimDetail>(Arrays.asList(employeeDoc1));
			// System.err.println("claim list" + claimDetList.toString());

			for (int i = 0; i < claimDetList.size(); i++) {

				claimDetList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(claimDetList.get(i).getClaimId())));

			}

			model.addObject("claimDetList", claimDetList);

			map = new LinkedMultiValueMap<>();
			map.add("empId", empId);

			ClaimStructureDetail[] claimStructureDetail = Constants.getRestTemplate().postForObject(
					Constants.url + "/getClaimStructureDetailByEmpId", map, ClaimStructureDetail[].class);
			List<ClaimStructureDetail> claimTypeList = new ArrayList<ClaimStructureDetail>(
					Arrays.asList(claimStructureDetail));
			model.addObject("claimTypeList", claimTypeList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/showClaimHistDetailList", method = RequestMethod.GET)
	public ModelAndView showClaimHistDetailList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("claim/claimDetailHistoryList");
		int claimId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("claimId")));
		model.addObject("claimId", claimId);

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("claimId", claimId);
			GetClaimTrailStatus[] employeeDoc = Constants.getRestTemplate().postForObject(
					Constants.url + "/getEmpClaimInfoListByTrailEmpId", map, GetClaimTrailStatus[].class);

			List<GetClaimTrailStatus> employeeList = new ArrayList<GetClaimTrailStatus>(Arrays.asList(employeeDoc));

			model.addObject("employeeList", employeeList);

			MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<>();
			map1.add("claimId", claimId);

			GetClaimApplyAuthwise lvEmp = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getClaimApplyDetailsByClaimId", map1, GetClaimApplyAuthwise.class);
			// lvEmp.setClaimDate(DateConvertor.convertToDMY(lvEmp.getClaimDate()));
			model.addObject("lvEmp", lvEmp);
			// System.out.println("emp leave details" + lvEmp.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/*
	 * @RequestMapping(value = "/claimDetailHistory", method = RequestMethod.GET)
	 * public ModelAndView claimDetailHistory(HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * ModelAndView model = new ModelAndView("claim/claim_trail_history");
	 * 
	 * try {
	 * 
	 * String base64encodedString = request.getParameter("claimId"); String claimId
	 * = FormValidation.DecodeKey(base64encodedString);
	 * 
	 * int retun = Integer.parseInt(request.getParameter("retun"));
	 * model.addObject("retun", retun);
	 * 
	 * System.out.println("ID: " + claimId); MultiValueMap<String, Object> map = new
	 * LinkedMultiValueMap<>(); map.add("claimId", claimId); GetClaimTrailStatus[]
	 * employeeDoc = Constants.getRestTemplate().postForObject( Constants.url +
	 * "/getEmpClaimInfoListByTrailEmpId", map, GetClaimTrailStatus[].class);
	 * 
	 * List<GetClaimTrailStatus> employeeList = new
	 * ArrayList<GetClaimTrailStatus>(Arrays.asList(employeeDoc));
	 * 
	 * model.addObject("employeeList", employeeList);
	 * System.out.println("trail *******" + employeeList.toString());
	 * 
	 * GetClaimTrailStatus trailHead = new GetClaimTrailStatus();
	 * 
	 * trailHead = employeeList.get(0);
	 * trailHead.setCaFromDt(DateConvertor.convertToDMY(trailHead.getCaFromDt()));
	 * trailHead.setCaToDt(DateConvertor.convertToDMY(trailHead.getCaToDt()));
	 * System.out.println("trailHead*******" + trailHead.toString());
	 * 
	 * model.addObject("lvEmp", trailHead);
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return model; }
	 */

	// 1st list

	// 1st list
	/*
	 * @RequestMapping(value = "/showClaimHistDetailList", method =
	 * RequestMethod.GET) public ModelAndView
	 * showClaimHistDetailList(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * ModelAndView model = new ModelAndView("claim/claimDetailHistoryList"); int
	 * claimId =
	 * Integer.parseInt(FormValidation.DecodeKey(request.getParameter("claimId")));
	 * model.addObject("claimId", claimId);
	 * 
	 * try {
	 * 
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
	 * map.add("claimId", claimId); GetClaimTrailStatus[] employeeDoc =
	 * Constants.getRestTemplate().postForObject( Constants.url +
	 * "/getEmpClaimInfoListByTrailEmpId", map, GetClaimTrailStatus[].class);
	 * 
	 * List<GetClaimTrailStatus> employeeList = new
	 * ArrayList<GetClaimTrailStatus>(Arrays.asList(employeeDoc));
	 * 
	 * model.addObject("employeeList", employeeList);
	 * System.out.println("trail *******" + employeeList.toString());
	 * 
	 * GetClaimTrailStatus trailHead = new GetClaimTrailStatus();
	 * 
	 * trailHead = employeeList.get(0);
	 * trailHead.setCaFromDt(DateConvertor.convertToDMY(trailHead.getCaFromDt()));
	 * trailHead.setCaToDt(DateConvertor.convertToDMY(trailHead.getCaToDt()));
	 * System.out.println("trailHead*******" + trailHead.toString());
	 * 
	 * model.addObject("lvEmp", trailHead);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return model; }
	 */

	/*
	 * @RequestMapping(value = "/showClaimApprovalByAdmin", method =
	 * RequestMethod.GET) public ModelAndView
	 * showClaimApprovalByAdmin(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * ModelAndView model = new ModelAndView("claim/showClaimApprovalByAdmin");
	 * 
	 * // for pending try { HttpSession session = request.getSession();
	 * LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
	 * 
	 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
	 * session.getAttribute("moduleJsonList"); Info view =
	 * AcessController.checkAccess("showClaimApprovalByAdmin",
	 * "showClaimApprovalByAdmin", 1, 0, 0, 0, newModuleList);
	 * 
	 * if (view.isError() == true) {
	 * 
	 * model = new ModelAndView("accessDenied");
	 * 
	 * } else {
	 * 
	 * 
	 * GetClaimApplyAuthwise[] employeeDoc =
	 * Constants.getRestTemplate().getForObject( Constants.url +
	 * "/getClaimApplyListForPendingForAdmin", GetClaimApplyAuthwise[].class);
	 * 
	 * List<GetClaimApplyAuthwise> claimList = new
	 * ArrayList<GetClaimApplyAuthwise>(Arrays.asList(employeeDoc));
	 * 
	 * for (int i = 0; i < claimList.size(); i++) {
	 * 
	 * claimList.get(i)
	 * .setCirculatedTo(FormValidation.Encrypt(String.valueOf(claimList.get(i).
	 * getCaHeadId())));
	 * claimList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(claimList.
	 * get(i).getEmpId())));
	 * claimList.get(i).setCaFromDt(DateConvertor.convertToDMY(claimList.get(i).
	 * getCaFromDt()));
	 * claimList.get(i).setCaToDt(DateConvertor.convertToDMY(claimList.get(i).
	 * getCaToDt()));
	 * 
	 * } model.addObject("claimListForApproval", claimList);
	 * model.addObject("list1Count", claimList.size());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return model; }
	 */

	@RequestMapping(value = "/showClaimListToChangeDate", method = RequestMethod.GET)
	public String showClaimListToChangeDate(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();

		String mav = new String();

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showClaimListToChangeDate", "showClaimListToChangeDate", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {
			try {

				mav = "claim/changeClaimYearmonth";
				ClaimApplyHeader[] employeeDoc1 = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getClaimHeaderToChangeDate", ClaimApplyHeader[].class);

				List<ClaimApplyHeader> claimList1 = new ArrayList<ClaimApplyHeader>(Arrays.asList(employeeDoc1));
				// System.err.println("claim list" + claimList1.toString());

				for (int i = 0; i < claimList1.size(); i++) {

					claimList1.get(i)
							.setExVar2(FormValidation.Encrypt(String.valueOf(claimList1.get(i).getCaHeadId())));
					
					claimList1.get(i).setCafromDt(DateConvertor.convertToDMY(claimList1.get(i).getCafromDt()));
					claimList1.get(i).setCaToDt(DateConvertor.convertToDMY(claimList1.get(i).getCaToDt()));

				}

				model.addAttribute("claimList1", claimList1);
				
				Info add = AcessController.checkAccess("showClaimListToChangeDate", "showClaimListToChangeDate", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showClaimListToChangeDate", "showClaimListToChangeDate", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showClaimListToChangeDate", "showClaimListToChangeDate", 0, 0, 0, 1,
						newModuleList);

				if (add.isError() == false) {
					System.out.println(" add   Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					System.out.println(" edit   Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					System.out.println(" delete   Accessable ");
					model.addAttribute("deleteAccess", 0);

				}


			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;

	}

	@RequestMapping(value = "/updateClmPaidDate", method = RequestMethod.POST)
	public String updateClmPaidDate(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		try {

			Date date2 = new Date();
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String workDate1 = request.getParameter("workDate1");
			int clmHeadId = Integer.parseInt(request.getParameter("clmHeadId"));
			// System.out.println("updateClmPaidDate" + workDate1);
			String temp[] = workDate1.split("-");
			Boolean ret = false;

			if (FormValidation.Validaton(workDate1, "") == true) {

				ret = true;
				System.out.println("workDate1" + ret);
			}

			if (FormValidation.Validaton(request.getParameter("clmHeadId"), "") == true) {

				ret = true;
				System.out.println("clmHeadId" + ret);
			}

			if (ret == false) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("dateTimeUpdate", sf2.format(date2));
				map.add("userId", userObj.getEmpId());
				map.add("clmHeadId", clmHeadId);
				map.add("month", String.valueOf(temp[1]));
				map.add("year", String.valueOf(temp[2]));

				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateClmPaidDate", map,
						Info.class);

				if (info != null) {
					session.setAttribute("successMsg", "Advance Skipped  Successfully");
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

		return "redirect:/showClaimListToChangeDate";
	}

}
