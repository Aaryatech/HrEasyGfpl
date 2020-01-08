package com.ats.hreasy.common;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class FormValidation {

	public static Boolean Validaton(String str, String type) {

		try {
			if (str != null && !str.trim().isEmpty()) {

				if (type.equals("email")) {

					String ePattern = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
					java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
					java.util.regex.Matcher m = p.matcher(str);

					if (m.matches()) {
						return false;
					} else {
						return true;
					}

				}
				if (type.equals("mobile")) {

					String ePattern = "^[1-9]{1}[0-9]{9}$";
					java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
					java.util.regex.Matcher m = p.matcher(str);
					if (m.matches()) {
						return false;
					} else {
						return true;
					}
				}

				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}

	public static String DecodeKey(String str) {

		String decrypt = new String();
		try {

			Decoder theDecoder = Base64.getDecoder();
			byte[] byteArray = theDecoder.decode(str);
			decrypt = new String(byteArray, StandardCharsets.UTF_8); 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return decrypt;

	}
	
	
	
	

	public static String Encrypt(String str) {

		String encrypt = new String();
		try {

			Encoder theEncoder = Base64.getEncoder();
			byte[] theArray = str.getBytes(StandardCharsets.UTF_8);
			encrypt = theEncoder.encodeToString(theArray);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return encrypt;

	}

}
