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
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.EmployeDoc;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.PayDeduction;

@Controller
@Scope("session")
public class PayDeductionController {
	Date date = new Date();	
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String currDate = sf.format(date);
	String redirect = "";
	
	@RequestMapping(value = "/showPayDeductionList", method = RequestMethod.GET)
	public ModelAndView showPayDeductionList(HttpServletRequest request, HttpServletResponse responser) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		ModelAndView model = null;
		try {
			model = new ModelAndView("master/payDeductionList");
		
			PayDeduction[] pay = Constants.getRestTemplate().getForObject(Constants.url + "/getAllPayDeduction",
					PayDeduction[].class);			
			List<PayDeduction> payList = new ArrayList<PayDeduction>(Arrays.asList(pay));
			System.out.println("list-----------"+payList);
			for (int i = 0; i < payList.size(); i++) {

				payList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(payList.get(i).getDedTypeId())));
			}
			
			model.addObject("payList", payList);
					
			model.addObject("addAccess", 0);
			model.addObject("editAccess", 0);
			model.addObject("deleteAccess", 0);
			

		} catch (Exception e) {
			System.out.println("Exception in showPayDeductionList : "+e.getMessage());
			e.printStackTrace();
		}

		return model;

	}
	
	@RequestMapping(value = "/payDeductionAdd", method = RequestMethod.GET)
	public ModelAndView employeeAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;
		
		try {
			
			PayDeduction pay = new PayDeduction();
			
			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("departmentAdd", "showDepartmentList", 0, 1, 0,
			 * 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */
			model = new ModelAndView("master/addPayDeduction");
			model.addObject("pay", pay);
			
			//}
		} catch (Exception e) {
			System.out.println("Exception in /submitInsertPayDeductType : "+e.getMessage());
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/submitInsertPayDeductType", method = RequestMethod.POST)  
	public String submitInsertPayDeductType(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		try {
			int typId = 0;
			try {
				typId =  Integer.parseInt(request.getParameter("payDeductTypeId"));
			}catch (Exception e) {
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
					
				PayDeduction savePay = Constants.getRestTemplate().postForObject(Constants.url + "/saveDeductnPaymentType", pay,
						PayDeduction.class);
				
				if (savePay != null ) {
						session.setAttribute("successMsg", "Record Inserted Successfully");
				} else {
					
					session.setAttribute("errorMsg", "Failed to Insert Record");
				}
		}catch (Exception e) {
			System.out.println("Exception in /payDeductionAdd : "+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showPayDeductionList";
		
	}
	
	
	@RequestMapping(value = "/editPayDeduct", method = RequestMethod.GET)
	public ModelAndView editPayDeduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addPayDeduction");
			
			String base64encodedString = request.getParameter("typeId");
			String typeId = FormValidation.DecodeKey(base64encodedString);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("typeId", typeId);
			PayDeduction pay = Constants.getRestTemplate().postForObject(Constants.url + "/getPayDeductionById", map,
					PayDeduction.class);
			model.addObject("pay", pay);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
		
	}
	
	@RequestMapping(value = "/deletePayDeduct", method = RequestMethod.GET)
	public String deletePayDeduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
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
		
		
		return "redirect:/showPayDeductionList";
	}
}
