package com.ats.hreasy.common;
  
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

public class Constants {
 
	public static final String url="http://localhost:8093/"; 
	public static RestTemplate rest = new RestTemplate(); 
	 
	 public static RestTemplate getRestTemplate() {
		rest=new RestTemplate();
		rest.getInterceptors().add(new BasicAuthorizationInterceptor("aaryatech", "Aaryatech@1cr"));
		return rest;

		} 
	 
	
}