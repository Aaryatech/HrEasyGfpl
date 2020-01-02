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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.claim.ClaimStructureDetail;
import com.ats.hreasy.model.claim.ClaimStructureHeader;
import com.ats.hreasy.model.claim.ClaimType;
import com.ats.hreasy.model.claim.GetClaimStructureAllotment;

@Controller
@Scope("session")
public class ClaimStructureAndAllotController {

	List<ClaimStructureDetail> tempDetailList = new ArrayList<ClaimStructureDetail>();

	List<ClaimType> claimTypeList = new ArrayList<ClaimType>();

	@RequestMapping(value = "/addClaimStructure", method = RequestMethod.GET)
	public ModelAndView addLeaveStructure(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		ModelAndView model = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("addClaimStructure", "showClaimStructureList", 0, 1, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			try {

				model = new ModelAndView("claim/add_claim_structure");
				tempDetailList = new ArrayList<ClaimStructureDetail>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				ClaimType[] employeeDoc = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getClaimListByCompanyId", map, ClaimType[].class);

				claimTypeList = new ArrayList<ClaimType>(Arrays.asList(employeeDoc));
				System.out.println("claimTypeList list " + claimTypeList.toString());
				model.addObject("claimTypeList", claimTypeList);

				model.addObject("title", "Add Claim Structure");

			} catch (Exception e) {

				System.err.println("exception In addClaimStructure at claim structure Contr" + e.getMessage());

				e.printStackTrace();

			}
		}
		return model;

	}

	@RequestMapping(value = "/insertClaimStructure", method = RequestMethod.POST)
	public String insertClaimStructure(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("addClaimStructure", "showClaimStructureList", 0, 1, 0, 0,
				newModuleList);
		String a = null;
		if (view.isError() == true) {

			a = "redirect:/accessDenied";
		} else {

			a = "redirect:/showClaimStructureList";

			try {

				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String curDate = dateFormat.format(new Date());
				String dateTime = dateFormat.format(now);

				String clmsName = request.getParameter("clmsName");

				ClaimStructureHeader head = new ClaimStructureHeader();

				head.setCompanyId(1);
				head.setDelStatus(1);
				head.setIsActive(1);
				head.setClaimStructName(clmsName);
				head.setMakerDatetime(dateTime);
				head.setMakerUserId(userObj.getUserId());

				List<ClaimStructureDetail> detailList = new ArrayList<>();
				for (int i = 0; i < claimTypeList.size(); i++) {

					float amt = 0;
					try {
						amt = (Integer.parseInt(request.getParameter("amt" + claimTypeList.get(i).getClaimTypeId())));
					} catch (Exception e) {
						amt = 0;
					}

					/* if (noOfLeaves > 0) { */
					ClaimStructureDetail detail = new ClaimStructureDetail();
					detail.setDelStatus(1);
					detail.setExInt1(0);
					detail.setExInt2(0);
					detail.setExVar1("NA");
					detail.setExVar2("NA");
					detail.setIsActive(1);

					detail.setClmAmt(
							Integer.parseInt(request.getParameter("amt" + claimTypeList.get(i).getClaimTypeId())));

					detail.setClmTypeId(claimTypeList.get(i).getClaimTypeId());
					detail.setMakerUserId(userObj.getUserId());
					detail.setMakerDatetime(dateTime);
					detailList.add(detail);

				}

				head.setDetailList(detailList);

				ClaimStructureHeader res = Constants.getRestTemplate()
						.postForObject(Constants.url + "saveClaimStruture", head, ClaimStructureHeader.class);

				if (res != null) {
					session.setAttribute("successMsg", "Record Inserted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Insert Record");
				}

			} catch (Exception e) {

				System.err.println("Exce In insertClaimStructure method  " + e.getMessage());
				e.printStackTrace();

			}
		}
		return a;

	}

	@RequestMapping(value = "/showClaimStructureList", method = RequestMethod.GET)
	public ModelAndView showClaimStructureList(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showClaimStructureList", "showClaimStructureList", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			try {
				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

				model = new ModelAndView("claim/claim_structure_list");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				ClaimStructureHeader[] summary = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getClaimStructHeadList", map, ClaimStructureHeader[].class);

				List<ClaimStructureHeader> clmSummarylist = new ArrayList<>(Arrays.asList(summary));

				for (int i = 0; i < clmSummarylist.size(); i++) {

					clmSummarylist.get(i).setExVar1(
							FormValidation.Encrypt(String.valueOf(clmSummarylist.get(i).getClmStructHeadId())));
				}

				model.addObject("clmSummarylist", clmSummarylist);

				Info add = AcessController.checkAccess("showClaimStructureList", "showClaimStructureList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showClaimStructureList", "showClaimStructureList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showClaimStructureList", "showClaimStructureList", 0, 0, 0,
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

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	ClaimStructureHeader editStructure = new ClaimStructureHeader();

	@RequestMapping(value = "/editClaimStructure", method = RequestMethod.GET)
	public ModelAndView editClaimStructure(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editClaimStructure", "showClaimStructureList", 0, 0, 1, 0,
				newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			try {
				model = new ModelAndView("claim/edit_claim_structure");
				String base64encodedString = request.getParameter("clmHeadId");
				String headId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("headId", headId);
				editStructure = Constants.getRestTemplate().postForObject(Constants.url + "/getClaimStructureById", map,
						ClaimStructureHeader.class);
				model.addObject("editStructure", editStructure);

				model.addObject("editStructureDetail", editStructure.getDetailList());

				map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				ClaimType[] employeeDoc = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getClaimListByCompanyId", map, ClaimType[].class);

				ArrayList<ClaimType> claimTypeList1 = new ArrayList<ClaimType>(Arrays.asList(employeeDoc));

				model.addObject("claimTypeList", claimTypeList1);

				// System.out.println("editStructure" + editStructure.toString());
				// System.out.println("editStructureDetail" +
				// editStructure.getDetailList().toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return model;
	}

	@RequestMapping(value = "/insertEditedClaimSummary", method = RequestMethod.POST)
	public String insertEditedClaimSummary(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("addClaimStructure", "showClaimStructureList", 0, 1, 0, 0,
				newModuleList);
		String a = null;
		if (view.isError() == true) {

			a = "redirect:/accessDenied";
		} else {

			a = "redirect:/showClaimStructureList";
			System.err.println("hii");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String curDate = dateFormat.format(new Date());
			String dateTime = dateFormat.format(now);
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			String clmsName = request.getParameter("clmsName");

			Boolean ret = false;

			if (FormValidation.Validaton(clmsName, "") == true) {

				ret = true;

			}

			int flag = 0;
			if (ret == false) {
				// System.err.println("hii "+editStructure.getDetailList().size());
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				ClaimType[] employeeDoc = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getClaimListByCompanyId", map, ClaimType[].class);

				ArrayList<ClaimType> claimTypeList1 = new ArrayList<ClaimType>(Arrays.asList(employeeDoc));

				// System.err.println("hii "+claimTypeList1.size());
				editStructure.setCompanyId(1);
				editStructure.setClaimStructName(clmsName);
				editStructure.setMakerDatetime(dateTime);
				editStructure.setMakerUserId(userObj.getUserId());

				List<ClaimStructureDetail> detailList = new ArrayList<>();
				for (int i = 0; i < claimTypeList1.size(); i++) {

					float amt = 0;
					try {
						for (int j = 0; j < editStructure.getDetailList().size(); j++) {

							if (editStructure.getDetailList().get(j).getClmTypeId() == claimTypeList1.get(i)
									.getClaimTypeId()) {

								flag = 1;
								System.err.println("flag set");

								editStructure.getDetailList().get(j).setClmAmt(Float.parseFloat(
										request.getParameter("amt" + claimTypeList1.get(i).getClaimTypeId())));

								editStructure.getDetailList().get(j).setMakerUserId(userObj.getUserId());
								editStructure.getDetailList().get(j).setMakerDatetime(dateTime);
							}
						}
					} catch (Exception e) {
						amt = 0;
					}
					if (flag == 0) {
						System.err.println("flag not set");
						ClaimStructureDetail detail = new ClaimStructureDetail();

						detail.setClmAmt(
								Integer.parseInt(request.getParameter("amt" + claimTypeList1.get(i).getClaimTypeId())));

						detail.setClmTypeId(claimTypeList1.get(i).getClaimTypeId());
						detail.setMakerUserId(userObj.getUserId());
						detail.setMakerDatetime(dateTime);
						editStructure.getDetailList().add(detail);
					}

				}
			}

			ClaimStructureHeader res = Constants.getRestTemplate().postForObject(Constants.url + "saveClaimStruture",
					editStructure, ClaimStructureHeader.class);

			if (res != null) {
				session.setAttribute("successMsg", "Record Inserted Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}
		}
		/*
		 * } catch (Exception e) {
		 * 
		 * System.err.println("Exce In insertClaimStructure method  " + e.getMessage());
		 * e.printStackTrace();
		 * 
		 * }
		 */
		return a;

	}

	@RequestMapping(value = "/deleteClaimStructure", method = RequestMethod.GET)
	public String deleteClaimStructure(HttpServletRequest request, HttpServletResponse response) {
		String a = null;
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("deleteClaimStructure", "showClaimStructureList", 0, 0, 0, 1,
				newModuleList);
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			try {
				a = "redirect:/showClaimStructureList";
				String base64encodedString = request.getParameter("clmHeadId");
				String clmsId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("clmsId", clmsId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteClaimStructure", map,
						Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", "Deleted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Delete");
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Delete");
			}
		}

		return a;
	}

	// ***********************Allot***************************************

	@RequestMapping(value = "/claimStructureAllotment", method = RequestMethod.GET)
	public ModelAndView leaveStructureAllotment(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("claimStructureAllotment", "claimStructureAllotment", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("claim/claim_structure_allot_list");
			try {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				ClaimStructureHeader[] lvStrSummery = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getClaimStructHeadList", map, ClaimStructureHeader[].class);

				List<ClaimStructureHeader> lSummarylist = new ArrayList<>(Arrays.asList(lvStrSummery));
				model.addObject("lStrList", lSummarylist);

				map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				map.add("locIdList", userObj.getLocationIds());

				GetClaimStructureAllotment[] summary = Constants.getRestTemplate().postForObject(
						Constants.url + "/getClaimStructureAllotmentList", map, GetClaimStructureAllotment[].class);

				List<GetClaimStructureAllotment> leaveSummarylist = new ArrayList<>(Arrays.asList(summary));

				model.addObject("lvStructureList", leaveSummarylist);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return model;
	}

	@RequestMapping(value = "/submitClaimStructureList", method = RequestMethod.POST)
	public String submitClaimStructureList(HttpServletRequest request, HttpServletResponse response) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String curDate = dateFormat.format(new Date());
		String dateTime = dateFormat.format(now);

		List<Integer> empList = null;

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
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

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map = new LinkedMultiValueMap<>();
			map.add("empList", items);
			map.add("userId", userObj.getUserId());
			map.add("dateTime", dateTime);
			map.add("lvsId", lvsId);

			Info res1 = Constants.getRestTemplate().postForObject(Constants.url + "/saveAllotmet", map, Info.class);

			if (res1 != null) {
				session.setAttribute("successMsg", "Record Inserted Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Insert Record");
			}

		}

		/*
		 * catch (Exception e) { e.printStackTrace(); }
		 */

		return "redirect:/claimStructureAllotment";
	}

}
