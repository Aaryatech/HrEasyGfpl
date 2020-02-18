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
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.HolidayCategory;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveType;
import com.ats.hreasy.model.Location;
import com.ats.hreasy.model.LoginResponse;
import com.ats.hreasy.model.SelfGroup;
import com.ats.hreasy.model.WeekoffCategory;
import com.ats.hreasy.model.Bonus.BonusMaster;

@Controller
@Scope("session")
public class MasterController {

	Location editLocation = new Location();

	@RequestMapping(value = "/locationAdd", method = RequestMethod.GET)
	public ModelAndView locationAdd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("locationAdd", "showLocationList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				model = new ModelAndView("master/locationAdd");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertLocation", method = RequestMethod.POST)
	public String submitInsertLocation(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("locationAdd", "showLocationList", 0, 1, 0, 0, newModuleList);
		String a = new String();
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showLocationList";
			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String locName = request.getParameter("locName");
				String locShortName = request.getParameter("locShortName");
				String add = request.getParameter("add");
				String prsnName = request.getParameter("prsnName");
				String contactNo = request.getParameter("contactNo");
				String email = request.getParameter("email");
				String remark = request.getParameter("remark");

				Boolean ret = false;

				if (FormValidation.Validaton(locName, "") == true) {

					ret = true;
					System.out.println("locName" + ret);
				}
				if (FormValidation.Validaton(locShortName, "") == true) {

					ret = true;
					System.out.println("locShortName" + ret);
				}
				if (FormValidation.Validaton(add, "") == true) {

					ret = true;
					System.out.println("add" + ret);
				}
				if (FormValidation.Validaton(prsnName, "") == true) {

					ret = true;
					System.out.println("prsnName" + ret);
				}
				if (FormValidation.Validaton(contactNo, "mobile") == true) {

					ret = true;
					System.out.println("contactNo" + ret);
				}
				if (FormValidation.Validaton(email, "email") == true) {

					ret = true;
					System.out.println("email" + ret);
				}

				if (ret == false) {

					Location location = new Location();

					location.setLocName(locName);
					location.setLocNameShort(locShortName);
					location.setLocShortAddress(add);
					location.setLocHrContactPerson(prsnName);
					location.setLocHrContactNumber(contactNo);
					location.setLocHrContactEmail(email);
					location.setLocRemarks(remark);
					location.setIsActive(1);
					location.setDelStatus(1);
					location.setMakerUserId(userObj.getUserId());
					location.setCompId(1);
					location.setMakerEnterDatetime(sf.format(date));

					Location res = Constants.getRestTemplate().postForObject(Constants.url + "/saveLocation", location,
							Location.class);

					if (res != null) {
						session.setAttribute("successMsg", "Location Inserted Successfully");
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
		}

		return a;
	}

	@RequestMapping(value = "/showLocationList", method = RequestMethod.GET)
	public ModelAndView showLocationList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showLocationList", "showLocationList", 1, 0, 0, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/locationList");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				Location[] location = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationList", map,
						Location[].class);

				List<Location> locationList = new ArrayList<Location>(Arrays.asList(location));

				for (int i = 0; i < locationList.size(); i++) {

					locationList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(locationList.get(i).getLocId())));
				}

				/*
				 * model.addObject("addAccess", 0); model.addObject("editAccess", 0);
				 * model.addObject("deleteAccess", 0);
				 */
				model.addObject("locationList", locationList);

				Info add = AcessController.checkAccess("showLocationList", "showLocationList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showLocationList", "showLocationList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showLocationList", "showLocationList", 0, 0, 0, 1,
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

	@RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
	public String deleteLocation(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

			Info view = AcessController.checkAccess("deleteLocation", "showLocationList", 0, 0, 0, 1, newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			}

			else {

				a = "redirect:/showLocationList";

				String base64encodedString = request.getParameter("locId");
				String locId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("locId", locId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteLocation", map,
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

	@RequestMapping(value = "/editLocation", method = RequestMethod.GET)
	public ModelAndView editLocation(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editLocation", "showLocationList", 0, 0, 1, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/locationEdit");
				String base64encodedString = request.getParameter("locId");
				String locId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("locId", locId);
				editLocation = Constants.getRestTemplate().postForObject(Constants.url + "/getLocationById", map,
						Location.class);
				model.addObject("editLocation", editLocation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitEditLocation", method = RequestMethod.POST)
	public String submitEditLocation(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editLocation", "showLocationList", 0, 0, 1, 0, newModuleList);
		String a = new String();
		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showLocationList";
			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String locName = request.getParameter("locName");
				String locShortName = request.getParameter("locShortName");
				String add = request.getParameter("add");
				String prsnName = request.getParameter("prsnName");
				String contactNo = request.getParameter("contactNo");
				String email = request.getParameter("email");
				String remark = request.getParameter("remark");

				Boolean ret = false;

				if (FormValidation.Validaton(locName, "") == true) {

					ret = true;
					System.out.println("locName" + ret);
				}
				if (FormValidation.Validaton(locShortName, "") == true) {

					ret = true;
					System.out.println("locShortName" + ret);
				}
				if (FormValidation.Validaton(add, "") == true) {

					ret = true;
					System.out.println("add" + ret);
				}
				if (FormValidation.Validaton(prsnName, "") == true) {

					ret = true;
					System.out.println("prsnName" + ret);
				}
				if (FormValidation.Validaton(contactNo, "mobile") == true) {

					ret = true;
					System.out.println("contactNo" + ret);
				}
				if (FormValidation.Validaton(email, "email") == true) {

					ret = true;
					System.out.println("email" + ret);
				}

				if (ret == false) {

					editLocation.setLocName(locName);
					editLocation.setLocNameShort(locShortName);
					editLocation.setLocShortAddress(add);
					editLocation.setLocHrContactPerson(prsnName);
					editLocation.setLocHrContactNumber(contactNo);
					editLocation.setLocHrContactEmail(email);
					editLocation.setLocRemarks(remark);
					editLocation.setMakerUserId(1);
					editLocation.setMakerEnterDatetime(sf.format(date));

					Location res = Constants.getRestTemplate().postForObject(Constants.url + "/saveLocation",
							editLocation, Location.class);

					if (res != null) {
						session.setAttribute("successMsg", "Location Updated Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Update Record");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Update Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Update Record");
			}
		}

		return a;
	}

	// *************self Grup*******************************

	SelfGroup editSelf = new SelfGroup();

	@RequestMapping(value = "/showAddSelfGrp", method = RequestMethod.GET)
	public ModelAndView showAddSelfGrp(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAddSelfGrp", "showSelfGrpList", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {
			model = new ModelAndView("accessDenied");

		} else {

			try {

				model = new ModelAndView("master/addSelfGroup");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertSelfGrp", method = RequestMethod.POST)
	public String submitInsertSelfGrp(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = new String();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showAddSelfGrp", "showSelfGrpList", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showSelfGrpList";
			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String selfGrpTitle = request.getParameter("grpName");

				Boolean ret = false;

				if (FormValidation.Validaton(selfGrpTitle, "") == true) {

					ret = true;
					System.out.println("selfGrpTitle" + ret);
				}

				if (ret == false) {

					SelfGroup bonus = new SelfGroup();

					bonus.setName(selfGrpTitle);
					;
					bonus.setDelStatus(1);
					bonus.setExInt1(0);
					bonus.setExInt2(0);
					bonus.setExVar1("NA");
					bonus.setExVar2("NA");

					SelfGroup res = Constants.getRestTemplate().postForObject(Constants.url + "/saveSelfGrp", bonus,
							SelfGroup.class);

					if (res != null) {
						session.setAttribute("successMsg", "Record Inserted Successfully");
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
		}

		return a;
	}

	@RequestMapping(value = "/showSelfGrpList", method = RequestMethod.GET)
	public ModelAndView showSelfGrpLists(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("showSelfGrpList", "showSelfGrpList", 1, 0, 0, 0, newModuleList);

		if (view.isError() == true) {

			model = new ModelAndView("accessDenied");

		} else {

			try {

				model = new ModelAndView("master/selfGroupList");

				Info add = AcessController.checkAccess("showLocationList", "showLocationList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showLocationList", "showLocationList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showLocationList", "showLocationList", 0, 0, 0, 1,
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
				SelfGroup[] location = Constants.getRestTemplate().getForObject(Constants.url + "/getSelftGroupList",
						SelfGroup[].class);

				List<SelfGroup> locationList = new ArrayList<SelfGroup>(Arrays.asList(location));

				for (int i = 0; i < locationList.size(); i++) {

					locationList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(locationList.get(i).getSelftGroupId())));
				}
				model.addObject("groupList", locationList);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/deleteSelfGrp", method = RequestMethod.GET)
	public String deleteSelfGrp(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

			Info view = AcessController.checkAccess("deleteSelfGrp", "showSelfGrpList", 0, 0, 0, 1, newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			}

			else {

				a = "redirect:/showSelfGrpList";

				String base64encodedString = request.getParameter("grpId");
				String bonusId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("bonusId", bonusId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteSelfGroup", map,
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

	@RequestMapping(value = "/editSelfGrp", method = RequestMethod.GET)
	public ModelAndView editBonus(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editSelfGrp", "showSelfGrpList", 0, 0, 1, 0, newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/editSelfGroup");
				String base64encodedString = request.getParameter("grpId");
				String bonusId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("bonusId", bonusId);
				editSelf = Constants.getRestTemplate().postForObject(Constants.url + "/getSelfGroupById", map,
						SelfGroup.class);
				model.addObject("editSelf", editSelf);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitInsertEditSelfGrp", method = RequestMethod.POST)
	public String submitInsertEditSelfGrp(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = new String();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editSelfGrp", "showSelfGrpList", 0, 1, 0, 0, newModuleList);

		if (view.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showSelfGrpList";
			try {

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String selfGrpTitle = request.getParameter("grpName");

				Boolean ret = false;

				if (FormValidation.Validaton(selfGrpTitle, "") == true) {

					ret = true;
					System.out.println("selfGrpTitle" + ret);
				}

				if (ret == false) {

					editSelf.setName(selfGrpTitle);
					;
					/*
					 * editSelf.setDelStatus(1); editSelf.setExInt1(0); editSelf.setExInt2(0);
					 * editSelf.setExVar1("NA"); editSelf.setExVar2("NA");
					 */

					SelfGroup res = Constants.getRestTemplate().postForObject(Constants.url + "/saveSelfGrp", editSelf,
							SelfGroup.class);

					if (res != null) {
						session.setAttribute("successMsg", "Record Inserted Successfully");
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
		}

		return a;
	}

	@RequestMapping(value = "/checkUniqueField", method = RequestMethod.GET)
	public @ResponseBody Info checkUniqueDept(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		HttpSession session = request.getSession();
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			String valueType = request.getParameter("valueType");
			String inputValue = request.getParameter("inputValue");
			int primaryKey = Integer.parseInt(request.getParameter("primaryKey"));
			int isEdit = Integer.parseInt(request.getParameter("isEdit"));
			/*
			 * System.err.println("compId:  " + userObj.getCompanyId());
			 * System.err.println("valueType:  " + valueType);
			 */
			map.add("inputValue", inputValue);
			map.add("valueType", valueType);
			map.add("isEditCall", isEdit);
			map.add("primaryKey", primaryKey);
			map.add("compId", 1);

			info = Constants.getRestTemplate().postForObject(Constants.url + "checkUniqueDeptDesgn", map, Info.class);

		} catch (Exception e) {
			info.setError(true);
			info.setMsg("1");
			e.printStackTrace();
		}

		return info;

	}

	// ***************************Holiday Category*************************

	HolidayCategory editHoliCat = new HolidayCategory();

	@RequestMapping(value = "/holidayCategoryAdd", method = RequestMethod.GET)
	public String holidayCategoryAdd(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String mav = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("holidayCategoryAdd", "showHolidayCatList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				HolidayCategory holi = new HolidayCategory();
				mav = "master/holidayCategoryAdd";
				model.addAttribute("title", "Add Holiday Category");
				model.addAttribute("holi", holi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/submitInsertHolidayCategory", method = RequestMethod.POST)
	public String submitInsertHolidayCategorys(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		String a = new String();
	 
		a = "redirect:/showHolidayCatList";
		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String hoCatName = request.getParameter("hoCatName");
			String hoShortName = request.getParameter("hoShortName");
			String remark = request.getParameter("remark");

			int hoCatId = Integer.parseInt(request.getParameter("hoCatId"));

			Boolean ret = false;

			if (FormValidation.Validaton(hoCatName, "") == true) {

				ret = true;
				System.out.println("hoCatName" + ret);
			}
			if (FormValidation.Validaton(hoShortName, "") == true) {

				ret = true;
				System.out.println("hoShortName" + ret);
			}

			if (ret == false) {

				HolidayCategory location = new HolidayCategory();

				location.setHoCatId(hoCatId);
				location.setHoCatName(hoCatName);
				location.setHoCatShortName(hoShortName);
				location.setRemark(remark);

				location.setCompanyId(1);
				location.setExInt1(0);
				location.setExInt2(0);
				location.setIsActive(1);
				location.setDelStatus(1);
				location.setMakerUserId(userObj.getUserId());
				location.setMakerEnterDatetime(sf.format(date));

				location.setExVar2("");
				location.setExVar1("");

				HolidayCategory res = Constants.getRestTemplate().postForObject(Constants.url + "/saveHolidayCat",
						location, HolidayCategory.class);

				if (res != null) {
					session.setAttribute("successMsg", "HolidayCategory Inserted Successfully");
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
	 
		return a;
	}

	@RequestMapping(value = "/showHolidayCatList", method = RequestMethod.GET)
	public ModelAndView showHolidayCatList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showHolidayCatList", "showHolidayCatList", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				model = new ModelAndView("master/holidayCatList");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", 1);
				HolidayCategory[] location = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getHolidayCategoryList", map, HolidayCategory[].class);

				List<HolidayCategory> locationList = new ArrayList<HolidayCategory>(Arrays.asList(location));

				for (int i = 0; i < locationList.size(); i++) {

					locationList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(locationList.get(i).getHoCatId())));
				}

				model.addObject("holiList", locationList);

				Info add = AcessController.checkAccess("showHolidayCatList", "showHolidayCatList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showHolidayCatList", "showHolidayCatList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showHolidayCatList", "showHolidayCatList", 0, 0, 0, 1,
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

	@RequestMapping(value = "/deleteHolidayCat", method = RequestMethod.GET)
	public String deleteHolidayCat(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

			Info view = AcessController.checkAccess("deleteHolidayCat", "showHolidayCatList", 0, 0, 0, 1,
					newModuleList);
			if (view.isError() == true) {

				a = "redirect:/accessDenied";

			}

			else {

				a = "redirect:/showHolidayCatList";

				String base64encodedString = request.getParameter("hoCatId");
				String hoCatId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("hoCatId", hoCatId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteHolidayCategory", map,
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

	@RequestMapping(value = "/editHolidayCat", method = RequestMethod.GET)
	public String editDesignation(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Model model) {

		String mav = null;

		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view = AcessController.checkAccess("editHolidayCat", "showHolidayCatList", 0, 0, 1, 0, newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {

			try {
				mav = "master/holidayCategoryAdd";

				String base64encodedString = request.getParameter("hoCatId");
				String hoCatId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("hoCatId", hoCatId);
				editHoliCat = Constants.getRestTemplate().postForObject(Constants.url + "/getHolidayCategoryHoCatId",
						map, HolidayCategory.class);
				model.addAttribute("holi", editHoliCat);
				model.addAttribute("title", "Edit Holiday Category");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;

	}

	// ***************************Weekoff Category*************************

	WeekoffCategory editWeekCat = new WeekoffCategory();

	@RequestMapping(value = "/weekoffCategoryAdd", method = RequestMethod.GET)
	public String weekoffCategoryAdd(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String mav = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("locationAdd", "showLocationList", 0, 1, 0, 0,
			 * newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */

			WeekoffCategory holi = new WeekoffCategory();
			mav = "master/weekoffCategoryAdd";
			model.addAttribute("title", "Add Weekoff Category");
			model.addAttribute("holi", holi);
			/* } */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/submitInsertWeekoffCategory", method = RequestMethod.POST)
	public String submitInsertWeekoffCategory(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");
		String a = new String();
		/*
		 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
		 * session.getAttribute("moduleJsonList"); Info view =
		 * AcessController.checkAccess("locationAdd", "showLocationList", 0, 1, 0, 0,
		 * newModuleList); if (view.isError() == true) {
		 * 
		 * a = "redirect:/accessDenied";
		 * 
		 * } else {
		 */
		a = "redirect:/showWeekoffCatList";
		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String woShortName = request.getParameter("woShortName");
			String woCatName = request.getParameter("woCatName");
			String remark = request.getParameter("remark");

			int woCatId = Integer.parseInt(request.getParameter("woCatId"));

			Boolean ret = false;

			if (FormValidation.Validaton(woShortName, "") == true) {

				ret = true;
				System.out.println("woShortName" + ret);
			}
			if (FormValidation.Validaton(woCatName, "") == true) {

				ret = true;
				System.out.println("hoShortName" + ret);
			}

			if (ret == false) {

				WeekoffCategory location = new WeekoffCategory();

				location.setWoCatId(woCatId);
				location.setWoCatName(woCatName);
				location.setWoCatShortName(woShortName);
				location.setRemark(remark);

				location.setCompanyId(1);
				location.setExInt1(0);
				location.setExInt2(0);
				;
				location.setIsActive(1);
				location.setDelStatus(1);
				location.setMakerUserId(userObj.getUserId());
				location.setMakerEnterDatetime(sf.format(date));

				location.setExVar2("");
				;
				location.setExVar1("");

				WeekoffCategory res = Constants.getRestTemplate().postForObject(Constants.url + "/saveWeekoffCat",
						location, WeekoffCategory.class);

				if (res != null) {
					if (woCatId == 0) {
						session.setAttribute("successMsg", "Weekoff Category Inserted Successfully");
					} else {
						session.setAttribute("successMsg", "Weekoff Category Updated Successfully");

					}

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
		// }

		return a;
	}

	@RequestMapping(value = "/showWeekoffCatList", method = RequestMethod.GET)
	public ModelAndView showWeekoffCatList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		// LoginResponse userObj = (LoginResponse) session.getAttribute("UserDetail");

		ModelAndView model = null;

		try {

			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList"); Info view =
			 * AcessController.checkAccess("showLocationList", "showLocationList", 1, 0, 0,
			 * 0, newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */

			model = new ModelAndView("master/weekoffCatList");

			WeekoffCategory[] location = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getWeekoffCategoryList", WeekoffCategory[].class);

			List<WeekoffCategory> locationList = new ArrayList<WeekoffCategory>(Arrays.asList(location));

			for (int i = 0; i < locationList.size(); i++) {

				locationList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(locationList.get(i).getWoCatId())));
			}

			model.addObject("holiList", locationList);

			/*
			 * Info add = AcessController.checkAccess("showLocationList",
			 * "showLocationList", 0, 1, 0, 0, newModuleList); Info edit =
			 * AcessController.checkAccess("showLocationList", "showLocationList", 0, 0, 1,
			 * 0, newModuleList); Info delete =
			 * AcessController.checkAccess("showLocationList", "showLocationList", 0, 0, 0,
			 * 1, newModuleList);
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
			 * 
			 * }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/deleteWeekoffCat", method = RequestMethod.GET)
	public String deleteWeekoffCat(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String a = null;

		try {
			/*
			 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
			 * session.getAttribute("moduleJsonList");
			 * 
			 * Info view = AcessController.checkAccess("deleteLocation", "showLocationList",
			 * 0, 0, 0, 1, newModuleList); if (view.isError() == true) {
			 * 
			 * a = "redirect:/accessDenied";
			 * 
			 * }
			 * 
			 * else {
			 */
			a = "redirect:/showWeekoffCatList";

			String base64encodedString = request.getParameter("woCatId");
			String woCatId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("woCatId", woCatId);
			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteWeekoffCategory", map,
					Info.class);

			if (info.isError() == false) {
				session.setAttribute("successMsg", info.getMsg());
			} else {
				session.setAttribute("errorMsg", info.getMsg());
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		return a;
	}

	@RequestMapping(value = "/editWeekoffCat", method = RequestMethod.GET)
	public String editWeekoffCat(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Model model) {

		String mav = null;

		/*
		 * List<AccessRightModule> newModuleList = (List<AccessRightModule>)
		 * session.getAttribute("moduleJsonList"); Info view =
		 * AcessController.checkAccess("editDesignation", "showDesignationList", 0, 0,
		 * 1, 0, newModuleList);
		 * 
		 * if (view.isError() == true) {
		 * 
		 * model = new ModelAndView("accessDenied");
		 * 
		 * } else {
		 */
		try {
			mav = "master/weekoffCategoryAdd";

			String base64encodedString = request.getParameter("woCatId");
			String woCatId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("woCatId", woCatId);
			editWeekCat = Constants.getRestTemplate().postForObject(Constants.url + "/getWeekoffCategoryHoCatId", map,
					WeekoffCategory.class);
			model.addAttribute("holi", editWeekCat);
			model.addAttribute("title", "Edit Weekoff Category");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;

	}

}
