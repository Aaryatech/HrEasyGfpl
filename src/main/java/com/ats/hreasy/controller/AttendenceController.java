package com.ats.hreasy.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
 
import com.ats.hreasy.common.Constants;
import com.ats.hreasy.common.VpsImageUpload;
import com.ats.hreasy.model.Designation;
import com.ats.hreasy.model.FileUploadedData;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.InfoForUploadAttendance;
import com.ats.hreasy.model.LoginResponse;

@Controller
@Scope("session")
public class AttendenceController {

	@RequestMapping(value = "/attendenceImportExel", method = RequestMethod.GET)
	public String attendenceImportExel(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = "attendence/attendenceImportExel";

		try {

			int month = Integer.parseInt(request.getParameter("selectMonth"));

			Date dt = new Date();
			Calendar temp = Calendar.getInstance();
			temp.setTime(dt);
			int year = temp.get(Calendar.YEAR);

			Date firstDay = new GregorianCalendar(year, month - 1, 1).getTime();
			Date lastDay = new GregorianCalendar(year, month, 0).getTime();

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate", sf.format(firstDay));
			map.add("toDate", sf.format(lastDay));
			InfoForUploadAttendance infoForUploadAttendance = Constants.getRestTemplate().postForObject(
					Constants.url + "/getInformationOfUploadedAttendance", map, InfoForUploadAttendance.class);

			temp = Calendar.getInstance();
			temp.setTime(firstDay);
			year = temp.get(Calendar.YEAR);
			month = temp.get(Calendar.MONTH);

			String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August",
					"September", "October", "November", "December" };
			String monthName = monthNames[month];

			model.addAttribute("monthName", monthName);
			model.addAttribute("year", year);
			model.addAttribute("month", month + 1);
			model.addAttribute("infoForUploadAttendance", infoForUploadAttendance);

			// System.out.println(month);

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

			Date dt = new Date();
			Calendar temp = Calendar.getInstance();
			temp.setTime(dt);
			int year = temp.get(Calendar.YEAR);
			int month = temp.get(Calendar.MONTH);

			/*
			 * Date firstDay = new Date(year, month, 1); Date lastDay = new Date(year, month
			 * + 1, 0);
			 */

			Date firstDay = new GregorianCalendar(year, month - 1, 1).getTime();
			Date lastDay = new GregorianCalendar(year, month, 0).getTime();

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate", sf.format(firstDay));
			map.add("toDate", sf.format(lastDay));
			InfoForUploadAttendance infoForUploadAttendance = Constants.getRestTemplate().postForObject(
					Constants.url + "/getInformationOfUploadedAttendance", map, InfoForUploadAttendance.class);

			temp = Calendar.getInstance();
			temp.setTime(firstDay);
			year = temp.get(Calendar.YEAR);
			month = temp.get(Calendar.MONTH);

			String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August",
					"September", "October", "November", "December" };
			String monthName = monthNames[month];

			model.addAttribute("monthName", monthName);
			model.addAttribute("year", year);
			model.addAttribute("infoForUploadAttendance", infoForUploadAttendance);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;

	}

	@RequestMapping(value = "/addDefaultAttendance", method = RequestMethod.POST)
	@ResponseBody
	public Info addDefaultAttendance(HttpServletRequest request, HttpServletResponse response, Model model) {

		Info info = new Info();

		try {

			int month = Integer.parseInt(request.getParameter("month"));
			int year = Integer.parseInt(request.getParameter("year"));
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date firstDay = new GregorianCalendar(year, month - 1, 1).getTime();
			Date lastDay = new GregorianCalendar(year, month, 0).getTime();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate", sf.format(firstDay));
			map.add("toDate", sf.format(lastDay));
			map.add("userId", userObj.getUserId());
			info = Constants.getRestTemplate().postForObject(Constants.url + "/initiallyInsertDailyRecord", map,
					Info.class);
			System.out.println(info); 
		} catch (Exception e) {
			e.printStackTrace();
			info = new Info();
			info.setError(true);
			info.setMsg("failed");
		}
		return info;

	}
	@RequestMapping(value = "/attUploadCSV", method = RequestMethod.POST)
	@ResponseBody
	public String attUploadCSV(@RequestParam("file") List<MultipartFile> file,HttpServletRequest request, HttpServletResponse response) {

	 

		try {
			
			SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date date = new Date();
			VpsImageUpload upload = new VpsImageUpload();
			
			String imageName = new String();
			imageName = dateTimeInGMT.format(date) + "_" + file.get(0).getOriginalFilename();
			System.out.println("imageName " + imageName);
			try {
				upload.saveUploadedFiles(file.get(0), Constants.imageSaveUrl, imageName);
				String fileIn = "/home/lenovo/Documents/attendance/"+imageName;
				String line = null;

				FileReader fileReader = new FileReader(fileIn);
				BufferedReader bufferedReader = new BufferedReader(fileReader);

				List<FileUploadedData> fileUploadedDataList = new ArrayList<>();
				
				FileUploadedData fileUploadedData = new FileUploadedData();
				
				
				while ((line = bufferedReader.readLine()) != null) {

					// System.out.println(bufferedReader.readLine());
					try {
						fileUploadedData = new FileUploadedData();
						String[] temp = line.split(",");
						String empCode = temp[0];
						String ename = temp[1];
						String logDate = temp[2];
						String inTime = temp[3];
						String outTime = temp[4];

						fileUploadedData.setEmpCode(empCode);
						fileUploadedData.setEmpName(ename);
						fileUploadedData.setLogDate(logDate);
						fileUploadedData.setInTime(inTime);
						fileUploadedData.setOutTime(outTime);
						
						fileUploadedDataList.add(fileUploadedData);
						

					} catch (Exception e) {

					}

				}
				bufferedReader.close();
				
				System.out.println(fileUploadedDataList.toString());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		 
		}
		return "0";

	}
 
}