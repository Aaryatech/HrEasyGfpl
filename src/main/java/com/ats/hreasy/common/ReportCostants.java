package com.ats.hreasy.common;

 
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
public class ReportCostants {
	
	public static Font headFontData = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
	public static Font tableHeaderFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	public static final BaseColor tableHeaderFontBaseColor=BaseColor.WHITE;
	public static final BaseColor baseColorTableHeader=BaseColor.BLUE;

	public static final Font reportNameFont = new Font(FontFamily.TIMES_ROMAN, 14.0f, Font.UNDERLINE, BaseColor.BLACK);

	
	public static float marginLeft=50;
	public static float marginRight=45;
	public static float marginTop=50;
	public static float marginBottom=80;
	

}
