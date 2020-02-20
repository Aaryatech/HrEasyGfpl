package com.ats.hreasy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
 
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
import com.ats.hreasy.model.Setting;
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

					map = new LinkedMultiValueMap<String, Object>();
					map.add("limitKey", "header_color");
					Setting getSettingByKey = Constants.getRestTemplate()
							.postForObject(Constants.url + "/getSettingByKey", map, Setting.class);

					mav = "redirect:/dashboard";
					session.setAttribute("userInfo", userObj);
					session.setAttribute("headerColor", getSettingByKey.getValue());

					map = new LinkedMultiValueMap<>();
					map.add("empTypeId", 1);
					EmpType editEmpType = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpTypeById",
							map, EmpType.class);
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
					// System.err.println("Hoem**"+userObj.toString());

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

			/*
			 * String testString = request.getParameter("pass"); MessageDigest md =
			 * MessageDigest.getInstance("MD5"); byte[] messageDigest =
			 * md.digest(testString.getBytes()); BigInteger number = new BigInteger(1,
			 * messageDigest); String hashtext = number.toString(16);
			 * 
			 * System.out.println(hashtext);
			 */
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

	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDenied(HttpServletRequest request, HttpServletResponse response) {
		String mav = "accessDenied";
		return mav;
	}

	@RequestMapping(value = "/sendMail", method = RequestMethod.GET)
	public @ResponseBody String sendMail(HttpServletRequest request, HttpServletResponse response) {
		final String emailSMTPserver = "smtp.gmail.com";
		final String emailSMTPPort = "587";
		final String mailStoreType = "imaps";

		final String username = "purchase.monginis1@gmail.com";
		final String password = "purchase1234#";

		/*final String username = "akshaykasar72@gmail.com";
		final String password = "Mh151772";*/

		System.out.println("username** " + username);
		System.out.println("password** " + password);

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");

		/*
		 * Session session = Session.getDefaultInstance(props, new
		 * javax.mail.Authenticator() { protected PasswordAuthentication
		 * getPasswordAuthentication() { return new PasswordAuthentication(username,
		 * password); } });
		 * 
		 * try { Store mailStore = session.getStore(mailStoreType);
		 * mailStore.connect(emailSMTPserver, username, password);
		 * 
		 * String subject = " Order For ";
		 * 
		 * Message mimeMessage = new MimeMessage(session); mimeMessage.setFrom(new
		 * InternetAddress(username)); //
		 * mimeMessage.setRecipients(Message.RecipientType.TO, //
		 * InternetAddress.parse(vendorList.getVendorEmail()));
		 * mimeMessage.setRecipients(Message.RecipientType.TO,
		 * InternetAddress.parse("akshaykasar72@gmail.com"));
		 * mimeMessage.setSubject(subject); mimeMessage.setFileName("PO Print");
		 * Multipart multipart = new MimeMultipart();
		 * 
		 * MimeBodyPart messageBodyPart = new MimeBodyPart(); messageBodyPart = new
		 * MimeBodyPart(); StringBuilder sb = new StringBuilder();
		 * sb.append("<html><body style='color : blue;'>"); sb.append("Dear Sir, <br>" +
		 * "	Kindly dispatch the goods as per attached PO. We have changed our system as per FSSAI gudlines. Kindly follow following instructions while dispatching the material.<br>"
		 * +
		 * "	1. COA-Chemical Analysis Report is compulsory with all the raw materials. Kindly note that if COA is not sent, payment will be delayed.<br>"
		 * +
		 * "	2. New Software has been installed at our end, so send material as per PO quantity only. If excess material is sent we will not be able to accept it, as there is no facility in new software to inward excess material.<br>"
		 * + "	3. All bills shall compulsory carry our PO number. <br>" +
		 * "	4. All bills shall compulsory carry your FDA/ FSSAI license no. (This is important if your are supplying edible raw material which is used as raw material in our manufacturing process). Note that if your bill is without FSSAI no your payment will be put on hold.<br>\r\n"
		 * + "	<br>" + "	<br>" + "	डिअर सर, <br>" +
		 * "	माल पाठविताना खालील पॉईंट्स वर कृपया लक्ष द्यावे-- <br>" +
		 * "	1. मटेरियल सोबात COA (केमिकल अनेलीसिस ) रिपोर्ट पाठवणे. COA मटेरियल सोबत नाही आला तर, पेमेंट मध्ये दिरंगाई होईल याची नोंद घ्यावी.<br>"
		 * +
		 * "	2. परचेस ऑर्डर मध्ये जि Quantity आहे त्यानुसार बिल बनवणे, Quantity जर परचेस ऑर्डर नुसार जास्त आली तर माल परत केला जाईल ,कारण लक्षात घ्या आमच्या कडे नवीन सॉफ्टवेअर इन्स्टॉल केला आहे व त्या मध्ये परचेस ऑर्डर च्या जास्त माल इनवॉर्ड करता येत नाही.<br>"
		 * +
		 * "	३. आमच्या कडे नवीन सॉफ्टवेअर इन्स्टॉल झाल्या कारणाने, बिल बनवतानी परचेसे ऑर्डर नंबर टाकणे आवश्यक आहे  <br>"
		 * +
		 * "	4. तुम्ही जर आम्हाला खाद्य पदार्थ पाठवत/ सप्लाय  असाल तर तुमचा FSSAI license no तुमच्या बिलावर येणे अनिवार्य आहे.बिना FSSAI license नो च्या बिल आल्यास पेमेंट होल्ड वर जाईल."
		 * );
		 * 
		 * sb.append("</body></html>"); messageBodyPart.setContent("" + sb,
		 * "text/html; charset=utf-8"); multipart.addBodyPart(messageBodyPart);
		 * mimeMessage.setContent(multipart);
		 * 
		 * Transport.send(mimeMessage); } catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 */
		return "success";
	}
	
	
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public @ResponseBody String downloadFile(HttpServletRequest request, HttpServletResponse response) {
		 
		try {
			/*URL url = new URL("http://97.74.228.55:8080/uploads/ITEM/15:03:14-download.jpg");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();*/
			 
			String dataDirectory = "/home/lenovo/Downloads/";
			/*request.getServletContext().getRealPath("/WEB-INF/downloads/pdf/");*/
			String fileName  = "8_StandardReport.xls";
	        Path file = Paths.get(dataDirectory, fileName);
	        if (Files.exists(file)) 
	        {
	            response.setContentType("application/pdf");
	            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
	            try
	            {
	                Files.copy(file, response.getOutputStream());
	                response.getOutputStream().flush();
	            } 
	            catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

}
