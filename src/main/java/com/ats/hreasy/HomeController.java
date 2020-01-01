package com.ats.hreasy;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.RandomString;
import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.EmpType;
import com.ats.hreasy.model.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles requests for the application home page.
 */
@Controller
@Scope("session")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/*
	 * @RequestMapping(value = "/", method = RequestMethod.GET) public String
	 * home(Locale locale, Model model) {
	 * logger.info("Welcome home! The client locale is {}.", locale);
	 * 
	 * Date date = new Date(); DateFormat dateFormat =
	 * DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	 * 
	 * String formattedDate = dateFormat.format(date);
	 * 
	 * model.addAttribute("serverTime", formattedDate );
	 * 
	 * return "home"; }
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		String mav = "login";

		return mav;
	}

	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public String loginProcess(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		HttpSession session = request.getSession();
		try {
			
			String name = request.getParameter("username");
			String password = request.getParameter("password");

			if (name.equalsIgnoreCase("") || password.equalsIgnoreCase("") || name == null || password == null) {

				mav = "redirect:/";
				session.setAttribute("errorMsg", "Login Failed");
			} else {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				 
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] messageDigest = md.digest(password.getBytes());
				BigInteger number = new BigInteger(1, messageDigest);
				String hashtext = number.toString(16);
				
				map.add("username", name);
				map.add("password", hashtext);

				LoginResponse userObj = Constants.getRestTemplate().postForObject(Constants.url + "loginProcess", map,
						LoginResponse.class);

				if (userObj.getIsError() == false) {

					mav = "redirect:/dashboard";
					session.setAttribute("userInfo", userObj);
					
					
					map = new LinkedMultiValueMap<>();
					map.add("empTypeId", 1);
					EmpType editEmpType = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpTypeById", map,
							EmpType.class); 
					List<AccessRightModule> moduleJsonList = new ArrayList<AccessRightModule>();

					try {

						AccessRightModule[] moduleJson = null;
						ObjectMapper mapper = new ObjectMapper();
						moduleJson = mapper.readValue(editEmpType.getEmpTypeAccess(), AccessRightModule[].class);
						moduleJsonList = new ArrayList<AccessRightModule>(Arrays.asList(moduleJson));

					} catch (Exception e) {

						e.printStackTrace();
					}
					session.setAttribute("moduleJsonList", moduleJsonList);
					//System.err.println("Hoem**"+userObj.toString());

				} else {
					mav = "redirect:/";
					session.setAttribute("errorMsg", "Login Failed");
				}

			}

		} catch (Exception e) {
			mav = "redirect:/";
			session.setAttribute("errorMsg", "Login Failed");
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "welcome";

		try {

			/*String testString = request.getParameter("pass");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(testString.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			System.out.println(hashtext);*/
			
			/*RandomString randomString = new RandomString();
			String password = randomString.nextString();
			System.out.println(password);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {

		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/sessionTimeOut", method = RequestMethod.GET)
	public String sessionTimeOut(HttpSession session) {
		System.out.println("User Logout");

		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/setSubModId", method = RequestMethod.GET)
	public @ResponseBody void setSubModId(HttpServletRequest request, HttpServletResponse response) {
		int subModId = Integer.parseInt(request.getParameter("subModId"));
		int modId = Integer.parseInt(request.getParameter("modId"));
		/*
		 * System.out.println("subModId " + subModId); System.out.println("modId " +
		 * modId);
		 */
		HttpSession session = request.getSession();
		session.setAttribute("sessionModuleId", modId);
		session.setAttribute("sessionSubModuleId", subModId);
		session.removeAttribute("exportExcelList");
	}
	
}
