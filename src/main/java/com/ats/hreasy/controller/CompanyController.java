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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.common.VpsImageUpload;
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.EmployeDoc;
import com.ats.hreasy.model.MstCompany;

@Controller
@Scope("session")
public class CompanyController {

	Date date = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String currDate = sf.format(date);
	String encryptCompId = new String();
	@RequestMapping(value = "/showCompanyList", method = RequestMethod.GET)
	public ModelAndView showLocationList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("showCompanyList", "showCompanyList", 1,
			 * 0, 0, 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */
			model = new ModelAndView("master/companyList");
			
			MstCompany[] company = Constants.getRestTemplate().getForObject(Constants.url + "/getAllCompanies", 
					MstCompany[].class);

			List<MstCompany> companyList = new ArrayList<MstCompany>(Arrays.asList(company));

			for (int i = 0; i < companyList.size(); i++) {

				companyList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(companyList.get(i).getCompanyId())));
			}

			model.addObject("addAccess", 0);
			model.addObject("editAccess", 0);
			model.addObject("deleteAccess", 0);
			model.addObject("companyList", companyList);

			/*
			 * Info add = AcessController.checkAccess("showCompanyList",
			 * "showCompanyList", 0, 1, 0, 0, newModuleList); Info edit =
			 * AcessController.checkAccess("showCompanyList", "showCompanyList", 0,
			 * 0, 1, 0, newModuleList); Info delete =
			 * AcessController.checkAccess("showCompanyList", "showCompanyList", 0,
			 * 0, 0, 1, newModuleList);
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
	
	@RequestMapping(value = "/companyAdd", method = RequestMethod.GET)
	public ModelAndView locationAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {
			MstCompany company = new MstCompany();
			/*List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("dsesignationAdd", "showDesignationList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {*/
				model = new ModelAndView("master/companyAdd");
				model.addObject("company", company);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/insertCompanyInfo", method = RequestMethod.POST)
	public String insertEmployeeBasicInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
				HttpSession session = request.getSession();
				MstCompany company = new MstCompany();
				int compId = 0; 
				try {
					compId = Integer.parseInt(request.getParameter("companyId"));
				}catch (Exception e) {
					e.printStackTrace();
					compId = 0;
				}
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", compId);
				
				MstCompany comp =  Constants.getRestTemplate().postForObject(Constants.url + "/getCompanyById", map,
						MstCompany.class);
				
				company.setCompanyId(compId);
				
				company.setCompanyName(request.getParameter("companyName"));				
				company.setNameSd(request.getParameter("companyShortName"));
				company.setLongAdd1(request.getParameter("companyAddress1"));
				company.setLongAdd2(request.getParameter("companyAddress2"));
				company.setLongAdd3(request.getParameter("companyAddress3"));
				company.setShortAddress(request.getParameter("shortAddress"));
				company.setLandline1(request.getParameter("landline1"));
				company.setLandline2(request.getParameter("landline2"));
				company.setFaxNo(request.getParameter("fax"));
				company.setPanNo(request.getParameter("pan"));
				
				company.setIsParentCompany("yes");
				company.setLogo(comp.getLogo());
				company.setLetterHead(comp.getLetterHead());
				company.setReportHeader(comp.getReportHeader());
				company.setReportFooter(comp.getReportFooter());
				
				company.setTanNo(comp.getTanNo());
				company.setPtNo(comp.getPtNo());
				company.setServiceTaxNo(comp.getServiceTaxNo());
				company.setVatNo(comp.getVatNo());
				company.setCstNo(comp.getCstNo());
				company.setGstNo(comp.getGstNo());
				company.setIsPfApplicable(comp.getIsPfApplicable());
				company.setPfNo(comp.getPfNo());
				company.setPfCoverageDate(comp.getPfCoverageDate());
				company.setPfSignatory1(comp.getPfSignatory1());
				company.setPfSignatory2(comp.getPfSignatory2());
				company.setPfSignatory3(comp.getPfSignatory3());
				company.setIsEsicApplicable(comp.getIsEsicApplicable());
				company.setEsicNo(comp.getEsicNo());
				company.setEsicCoverageDate(comp.getEsicCoverageDate());
				company.setEsicSignatory1(comp.getEsicSignatory1());
				company.setEsicSignatory2(comp.getEsicSignatory2());
				company.setEsicSignatory3(comp.getEsicSignatory3());
				
				company.setCpName(comp.getCpName());
				company.setCpDesignation(comp.getCpDesignation());
				company.setCpMobile(comp.getCpMobile());
				company.setCmpBankAccount(comp.getCmpBankAccount());
				company.setCpEmail1(comp.getCpEmail1());
				company.setCpEmail2(comp.getCpEmail2());
				
				company.setManagerUnderAct(comp.getManagerUnderAct());
				company.setManagerAddress(comp.getManagerAddress());
				
				company.setDelStatus(1);
				company.setExInt1(0);
				company.setExInt2(0);
				company.setExVar1("NA");
				company.setExVar2("NA");
				company.setMakerEnterDdatetime(currDate);
				
				MstCompany saveComp = Constants.getRestTemplate().postForObject(Constants.url + "/saveNewCompany", company,
						MstCompany.class);
				
				if (saveComp != null) {
					session.setAttribute("successMsg", "Record Updated Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
				}
				encryptCompId = FormValidation.Encrypt(String.valueOf(compId));
				System.out.println("Set CompId 1: "+encryptCompId);
		}catch (Exception e) {
			System.out.println("Exception in insertEmployeeBasicInfo : "+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/editCompanyInfo?compId="+encryptCompId;
		
	}
	
	
	@RequestMapping(value = "/editCompanyInfo", method = RequestMethod.GET)
	public ModelAndView addCompanyInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		try {
			String base64encodedString = request.getParameter("compId");
			String companyId = FormValidation.DecodeKey(base64encodedString);
			System.out.println("Get CompId : "+companyId);
		
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", companyId);
			
			MstCompany company =  Constants.getRestTemplate().postForObject(Constants.url + "/getCompanyById", map,
					MstCompany.class);
			
			model = new ModelAndView("master/companyAdd");
			model.addObject("company", company);
		}catch (Exception e) {
			System.out.println("Exception in addCompanyInfo : "+e.getMessage());
			e.printStackTrace();
		}
		
		return model;
		
	}
	
	@RequestMapping(value = "/insertCompanyFundsInfo", method = RequestMethod.POST)
	public String insertCompanyFundsInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
				HttpSession session = request.getSession();
				MstCompany company = new MstCompany();
				int compId = 0; 
				try {
					compId = Integer.parseInt(request.getParameter("companyId"));
				}catch (Exception e) {
					e.printStackTrace();
					compId = 0;
				}
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", compId);
				
				MstCompany comp =  Constants.getRestTemplate().postForObject(Constants.url + "/getCompanyById", map,
						MstCompany.class);
				
				company.setCompanyId(compId);
				
				company.setCompanyName(comp.getCompanyName());
				company.setNameSd(comp.getNameSd());
				company.setLongAdd1(comp.getLongAdd1());
				company.setLongAdd2(comp.getLongAdd2());
				company.setLongAdd3(comp.getLongAdd3());
				company.setShortAddress(comp.getShortAddress());
				company.setLandline1(comp.getLandline1());
				company.setLandline2(comp.getLandline2());
				company.setFaxNo(comp.getFaxNo());
				company.setPanNo(comp.getPanNo());
				
				company.setIsParentCompany("yes");
				company.setLogo(comp.getLogo());
				company.setLetterHead(comp.getLetterHead());
				company.setReportHeader(comp.getReportHeader());
				company.setReportFooter(comp.getReportFooter());
			
				company.setTanNo(request.getParameter("tanNo"));
				company.setPtNo(request.getParameter("ptNo"));
				company.setServiceTaxNo(request.getParameter("serviceTaxNo"));
				company.setVatNo(request.getParameter("vatNo"));
				company.setCstNo(request.getParameter("cstNo"));
				company.setGstNo(request.getParameter("gstNo"));
				company.setIsPfApplicable(request.getParameter("isPfApplicable"));
				company.setPfNo(request.getParameter("pfNo"));
				company.setPfCoverageDate(request.getParameter("pfCoveregDate"));
				company.setPfSignatory1(request.getParameter("pfSignatory1"));
				company.setPfSignatory2(request.getParameter("pfSignatory2"));
				company.setPfSignatory3(request.getParameter("pfSignatory3"));
				company.setIsEsicApplicable(Integer.parseInt(request.getParameter("isEsicApplicable")));
				company.setEsicNo(request.getParameter("esicNo"));
				company.setEsicCoverageDate(request.getParameter("esicCoverageDate"));
				company.setEsicSignatory1(request.getParameter("esicSignatory1"));
				company.setEsicSignatory2(request.getParameter("esicSignatory2"));
				company.setEsicSignatory3(request.getParameter("esicSignatory3"));
				
				company.setCpName(comp.getCpName());
				company.setCpDesignation(comp.getCpDesignation());
				company.setCpMobile(comp.getCpMobile());
				company.setCmpBankAccount(comp.getCmpBankAccount());
				company.setCpEmail1(comp.getCpEmail1());
				company.setCpEmail2(comp.getCpEmail2());
				
				company.setManagerUnderAct(comp.getManagerUnderAct());
				company.setManagerAddress(comp.getManagerAddress());
				
				company.setDelStatus(1);
				company.setExInt1(0);
				company.setExInt2(0);
				company.setExVar1("NA");
				company.setExVar2("NA");
				company.setMakerEnterDdatetime(currDate);
				
				MstCompany saveComp = Constants.getRestTemplate().postForObject(Constants.url + "/saveNewCompany", company,
						MstCompany.class);
				
				if (saveComp != null) {
					session.setAttribute("successMsg", "Record Updated Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
				}
				
				encryptCompId = FormValidation.Encrypt(String.valueOf(compId));
				System.out.println("Set CompId 2: "+encryptCompId);
		}catch (Exception e) {
			System.out.println("Exception in insertEmployeeBasicInfo : "+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/editCompanyInfo?compId="+encryptCompId;
		
	}
	
	@RequestMapping(value = "/insertCompanyBankInfo", method = RequestMethod.POST)
	public String insertCompanyBankInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
				HttpSession session = request.getSession();
				MstCompany company = new MstCompany();
				int compId = 0; 
				try {
					compId = Integer.parseInt(request.getParameter("companyId"));
				}catch (Exception e) {
					e.printStackTrace();
					compId = 0;
				}
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", compId);
				
				MstCompany comp =  Constants.getRestTemplate().postForObject(Constants.url + "/getCompanyById", map,
						MstCompany.class);
				
				company.setCompanyId(compId);
				
				company.setCompanyName(comp.getCompanyName());
				company.setNameSd(comp.getNameSd());
				company.setLongAdd1(comp.getLongAdd1());
				company.setLongAdd2(comp.getLongAdd2());
				company.setLongAdd3(comp.getLongAdd3());
				company.setShortAddress(comp.getShortAddress());
				company.setLandline1(comp.getLandline1());
				company.setLandline2(comp.getLandline2());
				company.setFaxNo(comp.getFaxNo());
				company.setPanNo(comp.getPanNo());
				
				company.setIsParentCompany("yes");
				company.setLogo(comp.getLogo());
				company.setLetterHead(comp.getLetterHead());
				company.setReportHeader(comp.getReportHeader());
				company.setReportFooter(comp.getReportFooter());
			
				company.setTanNo(comp.getTanNo());
				company.setPtNo(comp.getPtNo());
				company.setServiceTaxNo(comp.getServiceTaxNo());
				company.setVatNo(comp.getVatNo());
				company.setCstNo(comp.getCstNo());
				company.setGstNo(comp.getGstNo());
				company.setIsPfApplicable(comp.getIsPfApplicable());
				company.setPfNo(comp.getPfNo());
				company.setPfCoverageDate(comp.getPfCoverageDate());
				company.setPfSignatory1(comp.getPfSignatory1());
				company.setPfSignatory2(comp.getPfSignatory2());
				company.setPfSignatory3(comp.getPfSignatory3());
				company.setIsEsicApplicable(comp.getIsEsicApplicable());
				company.setEsicNo(comp.getEsicNo());
				company.setEsicCoverageDate(comp.getEsicCoverageDate());
				company.setEsicSignatory1(comp.getEsicSignatory1());
				company.setEsicSignatory2(comp.getEsicSignatory2());
				company.setEsicSignatory3(comp.getEsicSignatory3());
				
				company.setCpName(request.getParameter("person"));
				company.setCpDesignation(request.getParameter("designation"));
				company.setCpMobile(request.getParameter("mobile"));
				company.setCmpBankAccount(request.getParameter("accno"));
				company.setCpEmail1(request.getParameter("email1"));
				company.setCpEmail2(request.getParameter("email2"));
				
				company.setManagerUnderAct(comp.getManagerUnderAct());
				company.setManagerAddress(comp.getManagerAddress());
				
				company.setDelStatus(1);
				company.setExInt1(0);
				company.setExInt2(0);
				company.setExVar1("NA");
				company.setExVar2("NA");
				company.setMakerEnterDdatetime(currDate);
				MstCompany saveComp = Constants.getRestTemplate().postForObject(Constants.url + "/saveNewCompany", company,
						MstCompany.class);
				if (saveComp != null) {
					session.setAttribute("successMsg", "Record Updated Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
				}
				
				encryptCompId = FormValidation.Encrypt(String.valueOf(compId));
				System.out.println("Set CompId 2: "+encryptCompId);
		}catch (Exception e) {
			System.out.println("Exception in insertEmployeeBasicInfo : "+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/editCompanyInfo?compId="+encryptCompId;
		
	}
	
	@RequestMapping(value = "/insertCompanyManagerInfo", method = RequestMethod.POST)
	public String insertCompanyManagerInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
				
				MstCompany company = new MstCompany();
				HttpSession session = request.getSession();
				
				int compId = 0; 
				try {
					compId = Integer.parseInt(request.getParameter("companyId"));
				}catch (Exception e) {
					e.printStackTrace();
					compId = 0;
				}
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", compId);
				
				MstCompany comp =  Constants.getRestTemplate().postForObject(Constants.url + "/getCompanyById", map,
						MstCompany.class);
				
				company.setCompanyId(compId);
				
				company.setCompanyName(comp.getCompanyName());
				company.setNameSd(comp.getNameSd());
				company.setLongAdd1(comp.getLongAdd1());
				company.setLongAdd2(comp.getLongAdd2());
				company.setLongAdd3(comp.getLongAdd3());
				company.setShortAddress(comp.getShortAddress());
				company.setLandline1(comp.getLandline1());
				company.setLandline2(comp.getLandline2());
				company.setFaxNo(comp.getFaxNo());
				company.setPanNo(comp.getPanNo());
				
				company.setIsParentCompany("yes");
				company.setLogo(comp.getLogo());
				company.setLetterHead(comp.getLetterHead());
				company.setReportHeader(comp.getReportHeader());
				company.setReportFooter(comp.getReportFooter());
			
				company.setTanNo(comp.getTanNo());
				company.setPtNo(comp.getPtNo());
				company.setServiceTaxNo(comp.getServiceTaxNo());
				company.setVatNo(comp.getVatNo());
				company.setCstNo(comp.getCstNo());
				company.setGstNo(comp.getGstNo());
				company.setIsPfApplicable(comp.getIsPfApplicable());
				company.setPfNo(comp.getPfNo());
				company.setPfCoverageDate(comp.getPfCoverageDate());
				company.setPfSignatory1(comp.getPfSignatory1());
				company.setPfSignatory2(comp.getPfSignatory2());
				company.setPfSignatory3(comp.getPfSignatory3());
				company.setIsEsicApplicable(comp.getIsEsicApplicable());
				company.setEsicNo(comp.getEsicNo());
				company.setEsicCoverageDate(comp.getEsicCoverageDate());
				company.setEsicSignatory1(comp.getEsicSignatory1());
				company.setEsicSignatory2(comp.getEsicSignatory2());
				company.setEsicSignatory3(comp.getEsicSignatory3());
				
				company.setCpName(comp.getCpName());
				company.setCpDesignation(comp.getCpDesignation());
				company.setCpMobile(comp.getCpMobile());
				company.setCmpBankAccount(comp.getCmpBankAccount());
				company.setCpEmail1(comp.getCpEmail1());
				company.setCpEmail2(comp.getCpEmail2());
				
				company.setManagerUnderAct(request.getParameter("manager"));
				company.setManagerAddress(request.getParameter("managerAddress"));
				
				
				company.setDelStatus(1);
				company.setExInt1(0);
				company.setExInt2(0);
				company.setExVar1("NA");
				company.setExVar2("NA");
				company.setMakerEnterDdatetime(currDate);
				MstCompany saveComp = Constants.getRestTemplate().postForObject(Constants.url + "/saveNewCompany", company,
						MstCompany.class);
				
				if (saveComp != null) {
					session.setAttribute("successMsg", "Record Updated Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
				}
				
				encryptCompId = FormValidation.Encrypt(String.valueOf(compId));
				System.out.println("Set CompId 2: "+encryptCompId);
		}catch (Exception e) {
			System.out.println("Exception in insertEmployeeBasicInfo : "+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/editCompanyInfo?compId="+encryptCompId;
		
	}
	
	
	
	@RequestMapping(value = "/insertCompanyLogo", method = RequestMethod.POST)
	public String insertCompanyLogo(@RequestParam("logo") List<MultipartFile> logo,  HttpServletRequest request, HttpServletResponse response) {
		
		try {
				HttpSession session = request.getSession();
				VpsImageUpload upload = new VpsImageUpload();
			
				MstCompany company = new MstCompany();
				int compId = 0; 
				try {
					compId = Integer.parseInt(request.getParameter("companyId"));
				}catch (Exception e) {
					e.printStackTrace();
					compId = 0;
				}
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", compId);
				
				MstCompany comp =  Constants.getRestTemplate().postForObject(Constants.url + "/getCompanyById", map,
						MstCompany.class);
				
				company.setCompanyId(compId);
				
				company.setCompanyName(comp.getCompanyName());
				company.setNameSd(comp.getNameSd());
				company.setLongAdd1(comp.getLongAdd1());
				company.setLongAdd2(comp.getLongAdd2());
				company.setLongAdd3(comp.getLongAdd3());
				company.setShortAddress(comp.getShortAddress());
				company.setLandline1(comp.getLandline1());
				company.setLandline2(comp.getLandline2());
				company.setFaxNo(comp.getFaxNo());
				company.setPanNo(comp.getPanNo());
				
				company.setIsParentCompany("yes");				
				company.setLetterHead(comp.getLetterHead());
				company.setReportHeader(comp.getReportHeader());
				company.setReportFooter(comp.getReportFooter());
			
				company.setTanNo(comp.getTanNo());
				company.setPtNo(comp.getPtNo());
				company.setServiceTaxNo(comp.getServiceTaxNo());
				company.setVatNo(comp.getVatNo());
				company.setCstNo(comp.getCstNo());
				company.setGstNo(comp.getGstNo());
				company.setIsPfApplicable(comp.getIsPfApplicable());
				company.setPfNo(comp.getPfNo());
				company.setPfCoverageDate(comp.getPfCoverageDate());
				company.setPfSignatory1(comp.getPfSignatory1());
				company.setPfSignatory2(comp.getPfSignatory2());
				company.setPfSignatory3(comp.getPfSignatory3());
				company.setIsEsicApplicable(comp.getIsEsicApplicable());
				company.setEsicNo(comp.getEsicNo());
				company.setEsicCoverageDate(comp.getEsicCoverageDate());
				company.setEsicSignatory1(comp.getEsicSignatory1());
				company.setEsicSignatory2(comp.getEsicSignatory2());
				company.setEsicSignatory3(comp.getEsicSignatory3());
				
				company.setCpName(comp.getCpName());
				company.setCpDesignation(comp.getCpDesignation());
				company.setCpMobile(comp.getCpMobile());
				company.setCmpBankAccount(comp.getCmpBankAccount());
				company.setCpEmail1(comp.getCpEmail1());
				company.setCpEmail2(comp.getCpEmail2());
				
				company.setManagerUnderAct(comp.getManagerUnderAct());
				company.setManagerAddress(comp.getManagerAddress());
				
				String img = logo.get(0).getOriginalFilename();
				// logo = request.getParameter("logo");				 
				try {
					
					if(img!="" && img!=null) {
						 
							upload.saveUploadedImge(logo.get(0), Constants.imageSaveUrl, img, Constants.allextension,
									0, 0, 0, 0, 0);

							company.setLogo(img);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				company.setDelStatus(1);
				company.setExInt1(0);
				company.setExInt2(0);
				company.setExVar1("NA");
				company.setExVar2("NA");
				company.setMakerEnterDdatetime(currDate);
				MstCompany saveComp = Constants.getRestTemplate().postForObject(Constants.url + "/saveNewCompany", company,
						MstCompany.class);
				
				if (saveComp != null) {
					session.setAttribute("successMsg", "Record Updated Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
				}
				
				encryptCompId = FormValidation.Encrypt(String.valueOf(compId));
				//System.out.println("Set CompId 2: "+encryptCompId);
		}catch (Exception e) {
			System.out.println("Exception in insertEmployeeBasicInfo : "+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/editCompanyInfo?compId="+encryptCompId;
		
	}
}
