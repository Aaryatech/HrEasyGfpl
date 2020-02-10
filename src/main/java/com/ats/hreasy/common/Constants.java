package com.ats.hreasy.common;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

public class Constants {

	public static final String url = "http://localhost:8094/";
	//public static final String url="http://107.180.95.11:8080/HrEsayWebApi/";
	public static String REPORT_SAVE = "/home/lenovo/Documents/pdf/Report.pdf";
	public static final String imageSaveUrl = "/home/lenovo/Documents/attendance/";
	public static final String imageShowUrl = "http://localhost:8080/attendance/";
	public static final String ReportURL = "http://localhost:8080/hreasy/";//gfpl
	public static RestTemplate rest = new RestTemplate();
	public static String[] allextension = { "txt", "doc", "pdf", "xls", "jpg", "jpeg", "gif", "png" };
	public static String[] values = { "jpg", "jpeg", "gif", "png" };
	public static Object getImageSaveUrl;

	public static RestTemplate getRestTemplate() {
		rest = new RestTemplate();
		rest.getInterceptors().add(new BasicAuthorizationInterceptor("aaryatech", "Aaryatech@1cr"));
		return rest;

	}

}
