package com.ats.hreasy.common;
  
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

public class Constants {
 
	public static final String url="http://localhost:8094/";
	//public static final String url="http://115.124.111.55:8080/esayWebApi/"; 
	public static String REPORT_SAVE = "/home/lenovo/Documents/pdf/Report.pdf";
	public static final String imageSaveUrl = "/home/lenovo/Documents/attendance/";
	
	public static RestTemplate rest = new RestTemplate();
	public static String[] allextension;
	public static String[] values;
	public static Object getImageSaveUrl; 
	 
	 public static RestTemplate getRestTemplate() {
		rest=new RestTemplate();
		rest.getInterceptors().add(new BasicAuthorizationInterceptor("aaryatech", "Aaryatech@1cr"));
		return rest;

		} 
	 
	
}
