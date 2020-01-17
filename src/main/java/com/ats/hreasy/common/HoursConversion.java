package com.ats.hreasy.common;

public class HoursConversion {

	public static String convertHoursToMin(String str) {
		String min = new String();

		try {

			String[] result = str.split(":");
			int hours = Integer.parseInt(result[0]);
			int minutes = Integer.parseInt(result[1]);
			min = String.valueOf((hours * 60) + minutes);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return min;

	}

	public static String convertMinToHours(String minutes1) {
		String min = new String();
		int minutes = Integer.parseInt(minutes1);

		try {
			String hrs = String.valueOf(minutes / 60);
			String rem = String.valueOf(minutes % 60);
			System.out.println("prev hrs **" + hrs);
			System.out.println("prev rem  **" + rem);
			if (String.valueOf(hrs).length() == 1) {
				hrs = "0".concat(hrs);
				System.out.println("hrs after **" + hrs);

			}
			if (String.valueOf(rem).length() == 1) {
				rem = "0".concat(rem);
				System.out.println("rem after **" + rem);
 			}
 			min = hrs.concat(":").concat(rem);

			/// System.out.println("final hrs**" + min);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return min;

	}

}
