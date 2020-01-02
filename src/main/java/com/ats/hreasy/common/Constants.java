


package com.ats.hreasy.common;
  
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

public class Constants {
 
	public static final String url="http://localhost:8094/";
 	public static final String imageSaveUrl = "/home/lenovo/Downloads/";
	public static final String getImageSaveUrl = "/home/lenovo/Downloads/";
	public static final String REPORT_SAVE = "/home/lenovo/pdf/abc.pdf";
	public static RestTemplate rest = new RestTemplate();
	public static String[] allextension; 
	public static String[] values = { "jpg", "jpeg", "gif", "png" };
	 
	 public static RestTemplate getRestTemplate() {
		rest=new RestTemplate();
		rest.getInterceptors().add(new BasicAuthorizationInterceptor("aaryatech", "Aaryatech@1cr"));
		return rest;

		} 
	 
	
}
