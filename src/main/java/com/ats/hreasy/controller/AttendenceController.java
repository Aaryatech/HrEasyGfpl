package com.ats.hreasy.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Scope("session")
public class AttendenceController {

	@RequestMapping(value = "/attendenceImportExel", method = RequestMethod.GET)
	public String attendenceImportExel(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "attendence/attendenceImportExel";

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;

	}

	@RequestMapping(value = "/submitImportExel", method = RequestMethod.POST)
	public String submitInsertEmpDoc(@RequestParam("doc") List<MultipartFile> doc, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			/*
			 * FileInputStream fis = new FileInputStream(new
			 * File("/home/lenovo/Downloads/Employee Type Wise Claim Report-2019-11-14.xlsx"
			 * ));
			 * 
			 * XSSFWorkbook workbook = new XSSFWorkbook(fis); XSSFSheet spreadsheet =
			 * workbook.getSheetAt(0); Iterator < Row > rowIterator =
			 * spreadsheet.iterator();
			 * 
			 * while (rowIterator.hasNext()) { XSSFRow row = (XSSFRow) rowIterator.next();
			 * Iterator < Cell > cellIterator = row.cellIterator();
			 * 
			 * while ( cellIterator.hasNext()) { Cell cell = cellIterator.next();
			 * 
			 * switch (cell.getCellType()) { case Cell.CELL_TYPE_NUMERIC:
			 * System.out.print(cell.getNumericCellValue() + " \t\t "); break;
			 * 
			 * case Cell.CELL_TYPE_STRING: System.out.print( cell.getStringCellValue() +
			 * " \t\t "); break; } } System.out.println(); } fis.close();
			 */

			String fileIn = "/home/lenovo/Downloads/Attendance Logs final NOV 19.csv";
			String line = null;

			FileReader fileReader = new FileReader(fileIn);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {

				// System.out.println(bufferedReader.readLine());
				try {
					String[] temp = line.split(",");
					String studentID = temp[0];
					String firstName = temp[1];
					String lastName = temp[2];
					String finalMark = temp[3];
					String finalGrade = temp[4];

					System.out
							.println(studentID + " " + firstName + " " + lastName + " " + finalMark + " " + finalGrade);

				} catch (Exception e) {

				}

			}
			bufferedReader.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return "redirect:/attendenceImportExel";
	}
	
	@RequestMapping(value = "/attendanceSelectMonth", method = RequestMethod.GET)
	public String attendanceSelectMonth(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "attendence/attendanceSelectMonth";

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;

	}

}
