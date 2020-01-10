package com.ats.hreasy.common;

import java.text.DecimalFormat;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;

public class ReportCostants {

	public static Font headFontData = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
	public static Font tableHeaderFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	public static final BaseColor tableHeaderFontBaseColor = BaseColor.WHITE;
	public static final BaseColor baseColorTableHeader = BaseColor.BLUE;

	public static final Font reportNameFont = new Font(FontFamily.TIMES_ROMAN, 14.0f, Font.UNDERLINE, BaseColor.BLACK);

	public static float marginLeft = 50;
	public static float marginRight = 45;
	public static float marginTop = 50;
	public static float marginBottom = 80;

	public static double castNumber(double val, int amount_round) {
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		switch (amount_round) {
		case 1:
			val = Double.parseDouble(String.valueOf(Math.round(val)));
			break;
		case 2:
			
			val = Double.parseDouble(df.format(val));
			break;
		case 3:
			val = Double.parseDouble(String.valueOf(Math.ceil(val)));
			break;
		case 4:
			val = Double.parseDouble(String.valueOf(Math.floor(val)));
			break;
		}
		return val;
	}

}
