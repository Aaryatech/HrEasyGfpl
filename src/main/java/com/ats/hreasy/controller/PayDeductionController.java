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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.EmployeDoc;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.PayDeduction;
import com.ats.hreasy.model.PayDeductionDetailList;
import com.ats.hreasy.model.PayDeductionDetails;

@Controller
@Scope("session")
public class PayDeductionController {
	Date date = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String currDate = sf.format(date);
	String redirect = "";

	Date d = new Date();
	int year = d.getYear();
	int currentYear = year + 1900;

	@RequestMapping(value = "/showPayDeductionList", method = RequestMethod.GET)
	public ModelAndView showPayDeductionList(HttpServletRequest request, HttpServletResponse responser) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		ModelAndView model = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showPayDeductionList", "showPayDeductionList", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			try {
				model = new ModelAndView("master/payDeductionList");

				PayDeduction[] pay = Constants.getRestTemplate().getForObject(Constants.url + "/getAllPayDeduction",
						PayDeduction[].class);
				List<PayDeduction> payList = new ArrayList<PayDeduction>(Arrays.asList(pay));
				System.out.println("list-----------" + payList);
				for (int i = 0; i < payList.size(); i++) {

					payList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(payList.get(i).getDedTypeId())));
				}

				model.addObject("payList", payList);

				Info add = AcessController.checkAccess("showPayDeductionList", "showPayDeductionList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showPayDeductionList", "showPayDeductionList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showPayDeductionList", "showPayDeductionList", 0, 0, 0, 1,
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
				System.out.println("Exception in showPayDeductionList : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return model;

	}
	
	
	@RequestMapping(value = "/editEmpPayDeduct", method = RequestMethod.GET)
	public ModelAndView editPayDeduct(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editEmpPayDeduct", "payDeductionDetails", 0, 0, 1, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			try {
				PayDeduction[] payDeductArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllPayDeduction", PayDeduction[].class);
				List<PayDeduction> payDeductList = new ArrayList<PayDeduction>(Arrays.asList(payDeductArr));

				String base64encodedString = request.getParameter("deductId");
				String dedId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("dedId", dedId);

				PayDeductionDetailList deduct = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getEmpPayDeductionById", map, PayDeductionDetailList.class);

				model = new ModelAndView("dailywork/editEmpPayDeduct");
				model.addObject("currentYear", currentYear);
				model.addObject("deduct", deduct);
				model.addObject("payDeductList", payDeductList);

				// }
			} catch (Exception e) {
				System.out.println("Exception in /editEmpPayDeduct : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return model;
	}

	
	
	@RequestMapping(value = "/deletePayDeduct", method = RequestMethod.GET)
	public String deletePayDeduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		String a = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("deletePayDeduct", "showPayDeductionList", 0, 0, 0, 1, newModuleList);

		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showPayDeductionList";

			try {
				String base64encodedString = request.getParameter("typeId");
				String typeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("typeId", typeId);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deletePayDeduction", map,
						Info.class);

				if (res.isError()) {
					session.setAttribute("errorMsg", "Failed to Delete");
				} else {
					session.setAttribute("successMsg", "Deleted Successfully");

				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Delete");
			}
		}

		return a;
	} 
	@RequestMapping(value = "/payDeductionAdd", method = RequestMethod.GET)
	public ModelAndView employeeAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			PayDeduction pay = new PayDeduction();

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("payDeductionAdd", "showPayDeductionList", 0, 1, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/addPayDeduction");
				model.addObject("pay", pay);
				model.addObject("title", "Add Pay Deduction Type");

			}
		} catch (Exception e) {
			System.out.println("Exception in /submitInsertPayDeductType : " + e.getMessage());
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertPayDeductType", method = RequestMethod.POST)
	public String submitInsertPayDeductType(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String a = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("payDeductionAdd", "showPayDeductionList", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showPayDeductionList";
			try {
				int typId = 0;
				try {
					typId = Integer.parseInt(request.getParameter("payDeductTypeId"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				PayDeduction pay = new PayDeduction();

				pay.setDedTypeId(typId);
				pay.setTypeName(request.getParameter("payDeductType"));
				pay.setDedRate(Double.parseDouble(request.getParameter("deductRate")));
				pay.setIsUsed(1);
				pay.setDelStatus(1);
				pay.setEnterMakerDatetime(currDate);
				pay.setExInt1(0);
				pay.setExInt2(0);
				pay.setExVar1("NA");
				pay.setExVar2("NA");

				PayDeduction savePay = Constants.getRestTemplate()
						.postForObject(Constants.url + "/saveDeductnPaymentType", pay, PayDeduction.class);

				if (savePay != null) {
					session.setAttribute("successMsg", "Record Inserted Successfully");
				} else {

					session.setAttribute("errorMsg", "Failed to Insert Record");
				}
			} catch (Exception e) {
				System.out.println("Exception in /payDeductionAdd : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return a;

	}

	@RequestMapping(value = "/editPayDeduct", method = RequestMethod.GET)
	public ModelAndView editPayDeduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		ModelAndView model = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editPayDeduct", "showPayDeductionList", 0, 0, 1, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			try {
				model = new ModelAndView("master/addPayDeduction");

				String base64encodedString = request.getParameter("typeId");
				String typeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("typeId", typeId);
				PayDeduction pay = Constants.getRestTemplate().postForObject(Constants.url + "/getPayDeductionById",
						map, PayDeduction.class);
				model.addObject("pay", pay);
				model.addObject("title", "Edit Pay Deduction Type");


			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;

	}

	

	@RequestMapping(value = "/getPayDeductionTypeRate", method = RequestMethod.GET)
	public @ResponseBody PayDeduction getPayDeductionTypeRate(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		PayDeduction deductionRate = new PayDeduction();
		try {

			int deducType = Integer.parseInt(request.getParameter("deductType"));
			System.out.println("DedId----------" + deducType);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("typeId", deducType);
			deductionRate = Constants.getRestTemplate().postForObject(Constants.url + "/getPayDeductionById", map,
					PayDeduction.class);
			System.out.println("Rate=============" + deductionRate.getDedRate());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return deductionRate;

	}

	/***************************************
	 * PayDeductionDetails
	 *************************************/
	@RequestMapping(value = "/viewPayDeduction", method = RequestMethod.GET)
	public ModelAndView viewPayDeduction(HttpServletRequest request, HttpServletResponse responser) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("viewPayDeduction", "viewPayDeduction", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			try {
				model = new ModelAndView("dailywork/payDeductEmpList");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);

				EmployeeMaster[] empArr = Constants.getRestTemplate().postForObject(Constants.url + "/getAllEmployee",
						map, EmployeeMaster[].class);
				List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>(Arrays.asList(empArr));

				for (int i = 0; i < empList.size(); i++) {

					empList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(empList.get(i).getEmpId())));
				}

				Info edit = AcessController.checkAccess("viewPayDeduction", "viewPayDeduction", 0, 0, 1, 0,
						newModuleList);
				if (edit.isError() == false) {
					System.out.println(" edit   Accessable ");
					model.addObject("editAccess", 0);
				}

				model.addObject("empList", empList);

			} catch (Exception e) {
				System.out.println("Exception in showPayDeductionList : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return model;

	}

	@RequestMapping(value = "/deleteEmpPayDeduct", method = RequestMethod.GET)
	public String editEmpPayDeduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String a = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("deleteEmpPayDeduct", "payDeductionDetails", 0, 0, 0, 1, newModuleList);
		if (view.isError() == true) {
			a = "redirect:/accessDenied";

		} else {
			a = "redirect:/payDeductionDetails";
			try {
				String base64encodedString = request.getParameter("deductId");
				String dedId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("dedId", dedId);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deletePayDeductionDetailById",
						map, Info.class);

				if (res.isError()) {
					session.setAttribute("errorMsg", "Failed to Delete");
				} else {
					session.setAttribute("successMsg", "Deleted Successfully");

				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Delete");
			}
		}
		return a;
	}

	@RequestMapping(value = "/payDeductEmployee", method = RequestMethod.GET)
	public ModelAndView payDeductEmployee(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("payDeductEmployee", "viewPayDeduction", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			try {

				String base64encodedString = request.getParameter("empId");
				String empId = FormValidation.DecodeKey(base64encodedString);
				
				
			


				/*
				 * PayDeductionDetails pay = new PayDeductionDetails(); model.addObject("pay",
				 * pay);
				 */
				PayDeduction[] payDeductArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllPayDeduction", PayDeduction[].class);
				List<PayDeduction> payDeductList = new ArrayList<PayDeduction>(Arrays.asList(payDeductArr));

				model = new ModelAndView("dailywork/addEmpPayDeduct");
 			
				model.addObject("empId", empId);
				model.addObject("payDeductList", payDeductList);
				
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empId", empId);

				EmployeeMaster emp = Constants.getRestTemplate().postForObject(Constants.url + "/getEmployeeById", map,
						EmployeeMaster.class);
				model.addObject("emp", emp);

				// }
			} catch (Exception e) {
				System.out.println("Exception in /submitInsertPayDeductType : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/insertPayDeductDetail", method = RequestMethod.POST)
	public String insertPayDeductDetail(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			int dedId = 0;
			int empId = 0;
			int dedTypeId = 0;
			String monthyear =request.getParameter("monthyear");
			
			String a[]=monthyear.split("-");
 			try {
				dedId = Integer.parseInt(request.getParameter("dedId"));
				empId = Integer.parseInt(request.getParameter("empId"));
				dedTypeId = Integer.parseInt(request.getParameter("dedTypeId"));
  			} catch (Exception e) {
				e.printStackTrace();

				dedId = 0;
				empId = 0;
				dedTypeId = 0;
 				year = 0;
			}
			PayDeductionDetails pay = new PayDeductionDetails();

			pay.setDedId(dedId);
			pay.setCmpId(1);
			pay.setEmpId(empId);
			pay.setDedTypeId(dedTypeId);
			pay.setMonth(Integer.parseInt(a[0]));
			pay.setYear(Integer.parseInt(a[1]));
			pay.setDedRate(Double.parseDouble(request.getParameter("dedRate")));
			pay.setDedRemark(request.getParameter("remark"));

			pay.setDedApprovalDatetime(currDate);
			pay.setDedApprovalRemark("NA");
			pay.setDedApprovedBy("NA");
			pay.setDedLoginName("NA");
			pay.setDedLoginDteTime(currDate);
			pay.setDedOccurence(0);

			pay.setDedTotal(0);
			pay.setFinalStatus(1);
			pay.setIsDeducted(0);

			pay.setDelStatus(1);
			pay.setMakerEnterDatetime(currDate);
			pay.setExInt1(0);
			pay.setExInt2(0);
			pay.setExVar1("NA");
			pay.setExVar2("NA");

			PayDeductionDetails savePay = Constants.getRestTemplate()
					.postForObject(Constants.url + "/savePayDeductnDetail", pay, PayDeductionDetails.class);

			if (savePay != null) {
				session.setAttribute("successMsg", "Record Inserted Successfully");
			} else {

				session.setAttribute("errorMsg", "Failed to Insert Record");
			}
		} catch (Exception e) {
			System.out.println("Exception in /payDeductionAdd : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/viewPayDeduction";

	}

	@RequestMapping(value = "/payDeductionDetails", method = RequestMethod.GET)
	public ModelAndView payDeductionDetails(HttpServletRequest request, HttpServletResponse responser) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		ModelAndView model = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("payDeductionDetails", "payDeductionDetails", 1, 0, 0, 0,
				newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			try {
				model = new ModelAndView("dailywork/payDeductDetailList");

				// MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				PayDeductionDetailList[] deductDetailArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllEmpPayDeductDetail", PayDeductionDetailList[].class);
				List<PayDeductionDetailList> deductList = new ArrayList<PayDeductionDetailList>(
						Arrays.asList(deductDetailArr));

				for (int i = 0; i < deductList.size(); i++) {

					deductList.get(i)
							.setEncryptedId(FormValidation.Encrypt(String.valueOf(deductList.get(i).getDedId())));
				}

				model.addObject("deductList", deductList);
				Info add = AcessController.checkAccess("payDeductionDetails", "payDeductionDetails", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("payDeductionDetails", "payDeductionDetails", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("payDeductionDetails", "payDeductionDetails", 0, 0, 0, 1,
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
				System.out.println("Exception in payDeductionDetails : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return model;

	}

	

}
