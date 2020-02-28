package com.ats.hreasy.controller;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.hreasy.common.AcessController;
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.FormValidation;
import com.ats.hreasy.common.VpsImageUpload;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.MstCompanySub;

@Controller
@Scope("session")
public class CompanySubController {

	Date date = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String currDate = sf.format(date);
	String encryptCompId = new String();

	@RequestMapping(value = "/showSubCompanyList", method = RequestMethod.GET)
	public ModelAndView showLocationList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showSubCompanyList", "showSubCompanyList", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/companySubList");

				MstCompanySub[] company = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getAllSubCompanies", MstCompanySub[].class);

				List<MstCompanySub> companyList = new ArrayList<MstCompanySub>(Arrays.asList(company));

				for (int i = 0; i < companyList.size(); i++) {

					companyList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(companyList.get(i).getCompanyId())));
				}
				model.addObject("companyList", companyList);

				 

				Info add = AcessController.checkAccess("showSubCompanyList", "showSubCompanyList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showSubCompanyList", "showSubCompanyList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showSubCompanyList", "showSubCompanyList", 0, 0, 0, 1,
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

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/deleteSubCompany", method = RequestMethod.GET)
	public String deleteSubCompany(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String a = new String();

		 
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

		Info view = AcessController.checkAccess("deleteSubCompany", "showSubCompanyList", 0, 0, 0, 1, newModuleList);
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		}

		else {

 
		a = "redirect:/showSubCompanyList";
		try {
			String base64encodedString = request.getParameter("companyId");
			String companyId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);
			map.add("companyId", 1);

			Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deleteSubCompany", map, Info.class);

			if (res.isError()) {
				session.setAttribute("errorMsg", res.getMsg());
			} else {
				session.setAttribute("successMsg", res.getMsg());

			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		}
		return a;
	}

	@RequestMapping(value = "/activeDeactiveCompany", method = RequestMethod.GET)
	public String activeDeactiveCompany(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String a = null;

		a = "redirect:/showSubCompanyList";
		try {
			String base64encodedString = request.getParameter("companyId");
			String companyId = FormValidation.DecodeKey(base64encodedString);
			System.err.println("id is" + companyId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", Integer.parseInt(companyId));
			map.add("companyId", 1);

			Info res = Constants.getRestTemplate().postForObject(Constants.url + "/changeCompActive", map, Info.class);

			if (res.isError()) {
				session.setAttribute("errorMsg", res.getMsg());
			} else {
				session.setAttribute("successMsg", res.getMsg());

			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}

		return a;
	}

	@RequestMapping(value = "/companySubAdd", method = RequestMethod.GET)
	public ModelAndView locationAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {
			MstCompanySub company = new MstCompanySub();

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("companySubAdd", "showSubCompanyList", 0, 1, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/companySubAdd");
				model.addObject("company", company);
				session.setAttribute("tabFlag", 0);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/insertSubCompanyInfo", method = RequestMethod.POST)
	public String insertSubCompanyInfo(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		session.setAttribute("tabFlag", 0);

		String a = new String();
		try {

			MstCompanySub company = new MstCompanySub();
			int compId = 0;
			try {
				compId = Integer.parseInt(request.getParameter("companyId"));
				System.err.println("compId**" + compId);
			} catch (Exception e) {
				e.printStackTrace();
				compId = 0;

			}

			if (compId != 0) {

				encryptCompId = FormValidation.Encrypt(String.valueOf(compId));
				System.out.println("Set CompId 1: " + encryptCompId);
				a = "redirect:/editSubCompanyInfo?compId=" + encryptCompId + "&redirectFlag=1";
			} else {
				a = "redirect:/showSubCompanyList";
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", compId);

			MstCompanySub comp = Constants.getRestTemplate().postForObject(Constants.url + "/getSubCompanyById", map,
					MstCompanySub.class);

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
			if (compId != 0) {

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
			}
			company.setDelStatus(1);
			company.setExInt1(0);
			company.setExInt2(0);
			company.setExVar1("NA");
			company.setExVar2("NA");
			company.setMakerEnterDdatetime(currDate);
			company.setIsActive(1);

			MstCompanySub saveComp = Constants.getRestTemplate().postForObject(Constants.url + "/saveSubNewCompany",
					company, MstCompanySub.class);

			if (saveComp != null) {
				session.setAttribute("successMsg", "Record Updated Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
			}

		} catch (Exception e) {
			System.out.println("Exception in insertEmployeeBasicInfo : " + e.getMessage());
			e.printStackTrace();
		}

		return a;

	}

	@RequestMapping(value = "/editSubCompanyInfo", method = RequestMethod.GET)
	public ModelAndView addCompanyInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		HttpSession session = request.getSession();
		try {
			
			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editSubCompanyInfo", "showSubCompanyList", 0, 0, 1, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

			String redirectFlag = request.getParameter("redirectFlag");
			if (Integer.parseInt(redirectFlag) == 2) {
				session.setAttribute("tabFlag", 0);
			}

			String base64encodedString = request.getParameter("compId");
			String companyId = FormValidation.DecodeKey(base64encodedString);
			System.out.println("Get CompId : " + companyId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", companyId);

			MstCompanySub company = Constants.getRestTemplate().postForObject(Constants.url + "/getSubCompanyById", map,
					MstCompanySub.class);

			model = new ModelAndView("master/companySubAdd");
			model.addObject("company", company);

			model.addObject("viewUrl", Constants.companyLogoShowUrl);

			System.out.println(" company : " + company.toString());
			}
		} catch (Exception e) {
			System.out.println("Exception in addCompanyInfo : " + e.getMessage());
			e.printStackTrace();
		}

		return model;

	}

	@RequestMapping(value = "/insertSubCompanyFundsInfo", method = RequestMethod.POST)
	public String insertSubCompanyFundsInfo(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute("tabFlag", 2);
		try {

			MstCompanySub company = new MstCompanySub();
			int compId = 0;
			try {
				compId = Integer.parseInt(request.getParameter("companyId"));
			} catch (Exception e) {
				e.printStackTrace();
				compId = 0;
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", compId);

			MstCompanySub comp = Constants.getRestTemplate().postForObject(Constants.url + "/getSubCompanyById", map,
					MstCompanySub.class);

			company.setCompanyId(compId);
			company.setIsParentCompany("yes");
			if (compId != 0) {

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
				company.setLogo(comp.getLogo());
				company.setLetterHead(comp.getLetterHead());
				company.setReportHeader(comp.getReportHeader());
				company.setReportFooter(comp.getReportFooter());

				company.setCpName(comp.getCpName());
				company.setCpDesignation(comp.getCpDesignation());
				company.setCpMobile(comp.getCpMobile());
				company.setCmpBankAccount(comp.getCmpBankAccount());
				company.setCpEmail1(comp.getCpEmail1());
				company.setCpEmail2(comp.getCpEmail2());

				company.setManagerUnderAct(comp.getManagerUnderAct());
				company.setManagerAddress(comp.getManagerAddress());
				company.setIsActive(comp.getIsActive());

			}

			company.setTanNo(request.getParameter("tanNo"));
			company.setPtNo(request.getParameter("ptNo"));
			company.setServiceTaxNo(request.getParameter("serviceTaxNo"));
			company.setVatNo(request.getParameter("vatNo"));
			company.setCstNo(request.getParameter("cstNo"));
			company.setGstNo(request.getParameter("gstNo"));
			company.setIsPfApplicable(request.getParameter("isPfApplicable"));

			if (Integer.parseInt(request.getParameter("isPfApplicable")) == 1) {
				company.setPfNo(request.getParameter("pfNo"));
				company.setPfCoverageDate(request.getParameter("pfCoveregDate"));
				company.setPfSignatory1(request.getParameter("pfSignatory1"));
				company.setPfSignatory2(request.getParameter("pfSignatory2"));
				company.setPfSignatory3(request.getParameter("pfSignatory3"));
			}

			company.setIsEsicApplicable(request.getParameter("isEsicApplicable"));
			if (Integer.parseInt(request.getParameter("isEsicApplicable")) == 1) {

				company.setEsicNo(request.getParameter("esicNo"));
				company.setEsicCoverageDate(request.getParameter("esicCoverageDate"));
				company.setEsicSignatory1(request.getParameter("esicSignatory1"));
				company.setEsicSignatory2(request.getParameter("esicSignatory2"));
				company.setEsicSignatory3(request.getParameter("esicSignatory3"));
			}
			company.setDelStatus(1);
			company.setExInt1(0);
			company.setExInt2(0);
			company.setExVar1("NA");
			company.setExVar2("NA");
			company.setMakerEnterDdatetime(currDate);

			MstCompanySub saveComp = Constants.getRestTemplate().postForObject(Constants.url + "/saveSubNewCompany",
					company, MstCompanySub.class);

			if (saveComp != null) {
				session.setAttribute("successMsg", "Record Updated Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
			}

			encryptCompId = FormValidation.Encrypt(String.valueOf(compId));
			System.out.println("Set CompId 2: " + encryptCompId);
		} catch (Exception e) {
			System.out.println("Exception in insertEmployeeBasicInfo : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/editSubCompanyInfo?compId=" + encryptCompId + "&redirectFlag=1";

	}

	@RequestMapping(value = "/insertSubCompanyBankInfo", method = RequestMethod.POST)
	public String insertCompanyBankInfo(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute("tabFlag", 3);
		try {

			MstCompanySub company = new MstCompanySub();
			int compId = 0;
			try {
				compId = Integer.parseInt(request.getParameter("companyId"));
			} catch (Exception e) {
				e.printStackTrace();
				compId = 0;
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", compId);

			MstCompanySub comp = Constants.getRestTemplate().postForObject(Constants.url + "/getSubCompanyById", map,
					MstCompanySub.class);
			company.setCompanyId(compId);
			if (compId != 0) {

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
				company.setManagerUnderAct(comp.getManagerUnderAct());
				company.setManagerAddress(comp.getManagerAddress());
				company.setIsActive(comp.getIsActive());
			}

			System.err.println("mobile" + request.getParameter("mobile"));
			company.setIsParentCompany("yes");
			company.setCpName(request.getParameter("person"));
			company.setCpDesignation(request.getParameter("designation"));
			company.setCpMobile(request.getParameter("mobile"));
			company.setCmpBankAccount(request.getParameter("accno"));
			company.setCpEmail1(request.getParameter("email1"));
			company.setCpEmail2(request.getParameter("email2"));

			company.setDelStatus(1);
			company.setExInt1(0);
			company.setExInt2(0);
			company.setExVar1("NA");
			company.setExVar2("NA");
			company.setMakerEnterDdatetime(currDate);

			MstCompanySub saveComp = Constants.getRestTemplate().postForObject(Constants.url + "/saveSubNewCompany",
					company, MstCompanySub.class);
			if (saveComp != null) {
				session.setAttribute("successMsg", "Record Updated Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
			}

			encryptCompId = FormValidation.Encrypt(String.valueOf(compId));
			System.out.println("Set CompId 2: " + encryptCompId);
		} catch (Exception e) {
			System.out.println("Exception in insertEmployeeBasicInfo : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/editSubCompanyInfo?compId=" + encryptCompId + "&redirectFlag=1";

	}

	@RequestMapping(value = "/insertSubCompanyManagerInfo", method = RequestMethod.POST)
	public String insertCompanyManagerInfo(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		session.setAttribute("tabFlag", 4);
		try {

			MstCompanySub company = new MstCompanySub();

			int compId = 0;
			try {
				compId = Integer.parseInt(request.getParameter("companyId"));
			} catch (Exception e) {
				e.printStackTrace();
				compId = 0;
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", compId);

			MstCompanySub comp = Constants.getRestTemplate().postForObject(Constants.url + "/getSubCompanyById", map,
					MstCompanySub.class);

			company.setCompanyId(compId);
			if (compId != 0) {
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
				company.setIsActive(comp.getIsActive());
			}

			company.setManagerUnderAct(request.getParameter("manager"));
			company.setManagerAddress(request.getParameter("managerAddress"));

			company.setDelStatus(1);
			company.setExInt1(0);
			company.setExInt2(0);
			company.setExVar1("NA");
			company.setExVar2("NA");
			company.setMakerEnterDdatetime(currDate);

			MstCompanySub saveComp = Constants.getRestTemplate().postForObject(Constants.url + "/saveSubNewCompany",
					company, MstCompanySub.class);

			if (saveComp != null) {
				session.setAttribute("successMsg", "Record Updated Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
			}

			encryptCompId = FormValidation.Encrypt(String.valueOf(compId));
			System.out.println("Set CompId 2: " + encryptCompId);
		} catch (Exception e) {
			System.out.println("Exception in insertEmployeeBasicInfo : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/editSubCompanyInfo?compId=" + encryptCompId + "&redirectFlag=1";

	}

	@RequestMapping(value = "/insertSubCompanyLogo", method = RequestMethod.POST)
	public String insertSubCompanyLogo(@RequestParam("logo") List<MultipartFile> logo, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute("tabFlag", 1);
		try {

			VpsImageUpload upload = new VpsImageUpload();

			int compId = 0;
			try {
				compId = Integer.parseInt(request.getParameter("companyId"));
			} catch (Exception e) {
				e.printStackTrace();
				compId = 0;
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("companyId", compId);

			MstCompanySub comp = Constants.getRestTemplate().postForObject(Constants.url + "/getSubCompanyById", map,
					MstCompanySub.class);

			String img = logo.get(0).getOriginalFilename();
			System.out.println("img " + img);
			// logo = request.getParameter("logo");
			try {

				if (img.trim() != "") {

					Info info = upload.saveUploadedImge(logo.get(0), Constants.companyLogoSaveUrl, img, Constants.values, 0,
							0, 0, 0, 0);

					if (info.isError() == false) {
						comp.setLogo(img);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			MstCompanySub saveComp = Constants.getRestTemplate().postForObject(Constants.url + "/saveSubNewCompany",
					comp, MstCompanySub.class);

			if (saveComp != null) {
				session.setAttribute("successMsg", "Record Updated Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Update Record");
			}

			encryptCompId = FormValidation.Encrypt(String.valueOf(compId));
			// System.out.println("Set CompId 2: "+encryptCompId);
		} catch (Exception e) {
			System.out.println("Exception in insertEmployeeBasicInfo : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/editSubCompanyInfo?compId=" + encryptCompId + "&redirectFlag=1";

	}

}
