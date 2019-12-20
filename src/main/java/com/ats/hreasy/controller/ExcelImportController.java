package com.ats.hreasy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("session")
public class ExcelImportController {

	String curDateTime;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
	
	@RequestMapping(value = "/excelCustActMap", method = RequestMethod.GET)
	public @ResponseBody List addCustomerActMap(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = null;

		// List<Task> custActMapList = null;
		try {

			FileInputStream file = new FileInputStream(new File("/home/maddy/Documents/imgs/HrEasyDBDocs.xlsx"));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			
			Row ro = null;
			System.err.println("sheet.getLastRowNum() " + sheet.getLastRowNum());
			for (int i = sheet.getFirstRowNum()+1; i <= sheet.getLastRowNum(); i++) {
				ro=sheet.getRow(i);
				
				
				///int srNo = (int) ro.getCell(0).getNumericCellValue();
				String empNo = (String) ro.getCell(0).getStringCellValue();
				System.out.println("Value----"+empNo);

				
			} // end of for Loop Row Index;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
