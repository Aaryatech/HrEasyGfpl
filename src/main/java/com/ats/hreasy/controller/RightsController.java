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
import com.ats.hreasy.model.AccessRightSubModule;
import com.ats.hreasy.model.EmpType;
import com.ats.hreasy.model.Info;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@Scope("session")
public class RightsController {

	List<AccessRightModule> moduleList = new ArrayList<>();
	EmpType editEmpType = new EmpType();

	@RequestMapping(value = "/addRightsRole", method = RequestMethod.GET)
	public String addRightsRole(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String mav = new String();

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("addRightsRole", "showAccessRoleList", 0, 1, 0, 0, newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "master/addRightsRole";
				AccessRightModule[] accessRightModule = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getModuleAndSubModuleList", AccessRightModule[].class);

				moduleList = new ArrayList<AccessRightModule>(Arrays.asList(accessRightModule));

				model.addAttribute("moduleList", moduleList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/getSubmoduleList", method = RequestMethod.GET)
	public @ResponseBody List<Integer> getSubmoduleList(HttpServletRequest request, HttpServletResponse response) {

		List<Integer> list = new ArrayList<>();
		try {

			int moduleId = Integer.parseInt(request.getParameter("moduleId"));
			// System.out.println(moduleId);
			for (int i = 0; i < moduleList.size(); i++) {

				if (moduleList.get(i).getModuleId() == moduleId) {

					for (int j = 0; j < moduleList.get(i).getAccessRightSubModuleList().size(); j++) {

						list.add(moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId());
					}
					break;
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;

	}

	@RequestMapping(value = "/submitInsertAccessRole", method = RequestMethod.POST)
	public String submitInsertAccessRole(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view1 = AcessController.checkAccess("addRightsRole", "showAccessRoleList", 0, 1, 0, 0, newModuleList);
		String a = null;

		if (view1.isError() == true) {

			a = "redirect:/accessDenied";

		} else {
			a="redirect:/showAccessRoleList";
		}
		try {

			List<AccessRightModule> moduleJsonList = new ArrayList<>();

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String empTypeName = request.getParameter("empTypeName");
			String empShortName = request.getParameter("empShortName");
			int comoffallowed = Integer.parseInt(request.getParameter("comoffallowed"));
			String remark = request.getParameter("remark");

			Boolean ret = false;

			if (FormValidation.Validaton(empTypeName, "") == true) {

				ret = true;
				System.out.println("locName" + ret);
			}
			if (FormValidation.Validaton(empShortName, "") == true) {

				ret = true;
				System.out.println("locShortName" + ret);
			}

			if (ret == false) {

				EmpType empType = new EmpType();

				empType.setEmpTypeName(empTypeName);
				empType.setEmpTypeShortName(empShortName);
				empType.setCompOffRequestAllowed(comoffallowed);
				empType.setIsActive(1);
				empType.setDelStatus(1);
				empType.setMakerUserId(1);
				empType.setCompanyId(1);
				empType.setEmpTypeRemarks(remark);
				empType.setEmpTypeAccess("");
				empType.setMakerEnterDatetime(sf.format(date));

				for (int i = 0; i < moduleList.size(); i++) {

					AccessRightModule moduleJson = new AccessRightModule();
					int isPresent = 0;

					List<AccessRightSubModule> subModuleJsonList = new ArrayList<>();

					for (int j = 0; j < moduleList.get(i).getAccessRightSubModuleList().size(); j++) {

						AccessRightSubModule subModuleJson = new AccessRightSubModule();

						String view = request
								.getParameter(moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
										+ "view" + moduleList.get(i).getModuleId());

						try {
							if (view.equals("1")) {
								isPresent = 1;

								subModuleJson.setView(Integer.parseInt(view));
								subModuleJson.setSubModuleId(
										moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId());
								subModuleJson.setModuleId(moduleList.get(i).getModuleId());
								subModuleJson.setSubModulName(
										moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModulName());
								subModuleJson.setSubModuleDesc(
										moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleDesc());
								subModuleJson.setSubModuleMapping(
										moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleMapping());
								subModuleJson.setOrderBy(
										moduleList.get(i).getAccessRightSubModuleList().get(j).getOrderBy());

								try {
									int add = Integer.parseInt(request.getParameter(
											moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
													+ "add" + moduleList.get(i).getModuleId()));
									subModuleJson.setAddApproveConfig(add);
								} catch (Exception e) {
									subModuleJson.setAddApproveConfig(0);
								}

								try {
									int edit = Integer.parseInt(request.getParameter(
											moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
													+ "edit" + moduleList.get(i).getModuleId()));
									subModuleJson.setEditReject(edit);
								} catch (Exception e) {
									subModuleJson.setEditReject(0);
								}

								try {
									int delete = Integer.parseInt(request.getParameter(
											moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
													+ "delete" + moduleList.get(i).getModuleId()));
									subModuleJson.setDeleteRejectApprove(delete);
								} catch (Exception e) {
									subModuleJson.setDeleteRejectApprove(0);
								}

								subModuleJsonList.add(subModuleJson);

							}
						} catch (Exception e) {

						}

					}

					if (isPresent == 1) {
						moduleJson.setModuleId(moduleList.get(i).getModuleId());
						moduleJson.setModuleName(moduleList.get(i).getModuleName());
						moduleJson.setModuleDesc(moduleList.get(i).getModuleDesc());
						moduleJson.setOrderBy(moduleList.get(i).getOrderBy());
						moduleJson.setIconDiv(moduleList.get(i).getIconDiv());
						moduleJson.setAccessRightSubModuleList(subModuleJsonList);
						moduleJsonList.add(moduleJson);
					}

				}

				if (moduleJsonList != null && !moduleJsonList.isEmpty()) {

					ObjectMapper mapper = new ObjectMapper();
					try {

						String newsLetterJSON = mapper.writeValueAsString(moduleJsonList);

						System.out.println("JSON  " + newsLetterJSON);
						empType.setEmpTypeAccess(newsLetterJSON);

					} catch (JsonProcessingException e) {

						e.printStackTrace();
					}

					EmpType res = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmpType", empType,
							EmpType.class);

					if (res.isError() == false) {
						session.setAttribute("successMsg", "Record Inserted Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Insert Record");
					}

				} else {
					session.setAttribute("errorMsg", "Select Minimum One View Access.");
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

	@RequestMapping(value = "/showAccessRoleList", method = RequestMethod.GET)
	public String showEmpTypeList(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();

		String mav = null;

		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("showAccessRoleList", "showAccessRoleList", 1, 0, 0, 0,
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "master/accessrollist";
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", 1);
				EmpType[] EmpType = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpTypeList", map,
						EmpType[].class);

				List<EmpType> empTypelist = new ArrayList<EmpType>(Arrays.asList(EmpType));

				for (int i = 0; i < empTypelist.size(); i++) {

					empTypelist.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(empTypelist.get(i).getEmpTypeId())));
				}

				model.addAttribute("empTypelist", empTypelist);

				Info add = AcessController.checkAccess("showAccessRoleList", "showAccessRoleList", 0, 1, 0, 0,
						newModuleList);
				Info edit = AcessController.checkAccess("showAccessRoleList", "showAccessRoleList", 0, 0, 1, 0,
						newModuleList);
				Info delete = AcessController.checkAccess("showAccessRoleList", "showAccessRoleList", 0, 0, 0, 1,
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

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/deleteAccessRole", method = RequestMethod.GET)
	public String deleteEmpType(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = null;
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");

		Info view = AcessController.checkAccess("deleteAccessRole", "showAccessRoleList", 0, 0, 0, 1, newModuleList);

		try {

			if (view.isError() == true) {

				mav = "redirect:/accessDenied";

			}

			else {

				mav = "redirect:/showAccessRoleList";
				String base64encodedString = request.getParameter("empTypeId");
				String empTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empTypeId", empTypeId);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteEmpType", map,
						Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", "Deleted Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Delete");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to Delete");
		}
		return mav;
	}

	@RequestMapping(value = "/editAccessRole", method = RequestMethod.GET)
	public String editAccessRole(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
			Info view = AcessController.checkAccess("editAccessRole", "showAccessRoleList", 0, 0, 1, 0, newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "master/accessRoleEdit";

				String base64encodedString = request.getParameter("empTypeId");
				String empTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("empTypeId", empTypeId);
				editEmpType = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpTypeById", map,
						EmpType.class);
				model.addAttribute("editEmpType", editEmpType);

				List<AccessRightModule> moduleJsonList = new ArrayList<AccessRightModule>();

				try {

					AccessRightModule[] moduleJson = null;
					ObjectMapper mapper = new ObjectMapper();
					moduleJson = mapper.readValue(editEmpType.getEmpTypeAccess(), AccessRightModule[].class);
					moduleJsonList = new ArrayList<AccessRightModule>(Arrays.asList(moduleJson));

				} catch (Exception e) {

				}

				AccessRightModule[] accessRightModule = Constants.getRestTemplate()
						.getForObject(Constants.url + "/getModuleAndSubModuleList", AccessRightModule[].class);

				moduleList = new ArrayList<AccessRightModule>(Arrays.asList(accessRightModule));

				for (int i = 0; i < moduleList.size(); i++) {

					for (int j = 0; j < moduleJsonList.size(); j++) {

						if (moduleList.get(i).getModuleId() == moduleJsonList.get(j).getModuleId()) {

							// System.out.println("match Module " + moduleList.get(i).getModuleName());

							for (int k = 0; k < moduleList.get(i).getAccessRightSubModuleList().size(); k++) {

								for (int m = 0; m < moduleJsonList.get(j).getAccessRightSubModuleList().size(); m++) {

									if (moduleList.get(i).getAccessRightSubModuleList().get(k)
											.getSubModuleId() == moduleJsonList.get(j).getAccessRightSubModuleList()
													.get(m).getSubModuleId()) {

										moduleList.get(i).getAccessRightSubModuleList().get(k)
												.setAddApproveConfig(moduleJsonList.get(j).getAccessRightSubModuleList()
														.get(m).getAddApproveConfig());
										moduleList.get(i).getAccessRightSubModuleList().get(k).setView(
												moduleJsonList.get(j).getAccessRightSubModuleList().get(m).getView());
										moduleList.get(i).getAccessRightSubModuleList().get(k)
												.setEditReject(moduleJsonList.get(j).getAccessRightSubModuleList()
														.get(m).getEditReject());
										moduleList.get(i).getAccessRightSubModuleList().get(k)
												.setDeleteRejectApprove(moduleJsonList.get(j)
														.getAccessRightSubModuleList().get(m).getDeleteRejectApprove());
										break;
									}

								}

							}

							break;
						}

					}

				}
				model.addAttribute("allModuleList", moduleList);
				model.addAttribute("moduleJsonList", moduleJsonList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/submitEditAccessRole", method = RequestMethod.POST)
	public String submitEditAccessRole(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		List<AccessRightModule> newModuleList = (List<AccessRightModule>) session.getAttribute("moduleJsonList");
		Info view1 = AcessController.checkAccess("editAccessRole", "showAccessRoleList", 0, 0, 1, 0, newModuleList);
		String a = null;
		if (view1.isError() == true) {

			a = "redirect:/accessDenied";

		} else {

			a = "redirect:/showAccessRoleList";
			try {

				List<AccessRightModule> moduleJsonList = new ArrayList<>();

				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String empTypeName = request.getParameter("empTypeName");
				String empShortName = request.getParameter("empShortName");
				int comoffallowed = Integer.parseInt(request.getParameter("comoffallowed"));
				String remark = request.getParameter("remark");

				Boolean ret = false;

				if (FormValidation.Validaton(empTypeName, "") == true) {

					ret = true;

				}
				if (FormValidation.Validaton(empShortName, "") == true) {

					ret = true;

				}

				if (ret == false) {

					editEmpType.setEmpTypeName(empTypeName);
					editEmpType.setEmpTypeShortName(empShortName);
					editEmpType.setCompOffRequestAllowed(comoffallowed);
					editEmpType.setCompanyId(1);
					editEmpType.setEmpTypeRemarks(remark);
					editEmpType.setEmpTypeAccess("");
					editEmpType.setMakerEnterDatetime(sf.format(date));

					for (int i = 0; i < moduleList.size(); i++) {

						AccessRightModule moduleJson = new AccessRightModule();
						int isPresent = 0;

						List<AccessRightSubModule> subModuleJsonList = new ArrayList<>();

						for (int j = 0; j < moduleList.get(i).getAccessRightSubModuleList().size(); j++) {

							AccessRightSubModule subModuleJson = new AccessRightSubModule();

							String view = request.getParameter(
									moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId() + "view"
											+ moduleList.get(i).getModuleId());

							try {
								if (view.equals("1")) {
									isPresent = 1;

									subModuleJson.setView(Integer.parseInt(view));
									subModuleJson.setSubModuleId(
											moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId());
									subModuleJson.setModuleId(moduleList.get(i).getModuleId());
									subModuleJson.setSubModulName(
											moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModulName());
									subModuleJson.setSubModuleDesc(
											moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleDesc());
									subModuleJson.setSubModuleMapping(moduleList.get(i).getAccessRightSubModuleList()
											.get(j).getSubModuleMapping());
									subModuleJson.setOrderBy(
											moduleList.get(i).getAccessRightSubModuleList().get(j).getOrderBy());

									try {
										int add = Integer.parseInt(request.getParameter(
												moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
														+ "add" + moduleList.get(i).getModuleId()));
										subModuleJson.setAddApproveConfig(add);
									} catch (Exception e) {
										subModuleJson.setAddApproveConfig(0);
									}

									try {
										int edit = Integer.parseInt(request.getParameter(
												moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
														+ "edit" + moduleList.get(i).getModuleId()));
										subModuleJson.setEditReject(edit);
									} catch (Exception e) {
										subModuleJson.setEditReject(0);
									}

									try {
										int delete = Integer.parseInt(request.getParameter(
												moduleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
														+ "delete" + moduleList.get(i).getModuleId()));
										subModuleJson.setDeleteRejectApprove(delete);
									} catch (Exception e) {
										subModuleJson.setDeleteRejectApprove(0);
									}

									subModuleJsonList.add(subModuleJson);

								}
							} catch (Exception e) {

							}

						}

						if (isPresent == 1) {
							moduleJson.setModuleId(moduleList.get(i).getModuleId());
							moduleJson.setModuleName(moduleList.get(i).getModuleName());
							moduleJson.setModuleDesc(moduleList.get(i).getModuleDesc());
							moduleJson.setOrderBy(moduleList.get(i).getOrderBy());
							moduleJson.setIconDiv(moduleList.get(i).getIconDiv());
							moduleJson.setAccessRightSubModuleList(subModuleJsonList);
							moduleJsonList.add(moduleJson);
						}

					}

					if (moduleJsonList != null && !moduleJsonList.isEmpty()) {

						ObjectMapper mapper = new ObjectMapper();
						try {

							String newsLetterJSON = mapper.writeValueAsString(moduleJsonList);

							System.out.println("JSON  " + newsLetterJSON);
							editEmpType.setEmpTypeAccess(newsLetterJSON);

						} catch (JsonProcessingException e) {

							e.printStackTrace();
						}

						EmpType res = Constants.getRestTemplate().postForObject(Constants.url + "/saveEmpType",
								editEmpType, EmpType.class);

						if (res.isError() == false) {
							session.setAttribute("successMsg", "Record Updated Successfully");
						} else {
							session.setAttribute("errorMsg", "Failed to Updated Record");
						}

					} else {
						session.setAttribute("errorMsg", "Select Minimum One View Access.");
					}

				} else {
					session.setAttribute("errorMsg", "Failed to Updated Record");
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Failed to Updated Record");
			}
		}
		return a;
	}

}
