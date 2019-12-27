package com.ats.hreasy.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ats.hreasy.model.DailyAttendance;
import com.ats.hreasy.model.DataForUpdateAttendance;
import com.ats.hreasy.model.FileUploadedData;
import com.ats.hreasy.model.Info;

public class UpateAttendaceCommon {
	
	public Info changeInDailyDailyAfterLeaveTransaction(String fromDate, String toDate, int empId, int userId) {

		Info finalRes = new Info();
		try {

			SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

			Date fmdt = dd.parse(fromDate);
			Date todt = dd.parse(toDate);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("empId", empId);
			DailyAttendance[] dailyAttendance = Constants.getRestTemplate().postForObject(
					Constants.url + "/getDailyDailyRecordBetweenDateAndByEmpId", map, DailyAttendance[].class);
			List<DailyAttendance> dailyAttendanceList = new ArrayList<>(Arrays.asList(dailyAttendance));

			List<FileUploadedData> fileUploadedDataList = new ArrayList<>();

			for (int i = 0; i < dailyAttendanceList.size(); i++) {

				FileUploadedData fileUploadedData = new FileUploadedData();
				fileUploadedData.setEmpCode(dailyAttendanceList.get(i).getEmpCode());
				fileUploadedData.setEmpName(dailyAttendanceList.get(i).getEmpName());
				fileUploadedData.setLogDate(DateConvertor.convertToDMY(dailyAttendanceList.get(i).getAttDate()));
				fileUploadedData.setInTime(dailyAttendanceList.get(i).getInTime().substring(0, 5));
				fileUploadedData.setOutTime(dailyAttendanceList.get(i).getOutTime().substring(0, 5));
				fileUploadedDataList.add(fileUploadedData);
			}

			DataForUpdateAttendance dataForUpdateAttendance = new DataForUpdateAttendance();
			dataForUpdateAttendance.setFromDate(DateConvertor.convertToYMD(fromDate));
			dataForUpdateAttendance.setToDate(DateConvertor.convertToYMD(toDate));
			dataForUpdateAttendance.setMonth(0);
			dataForUpdateAttendance.setYear(0);
			dataForUpdateAttendance.setUserId(userId);
			dataForUpdateAttendance.setFileUploadedDataList(fileUploadedDataList);
			dataForUpdateAttendance.setEmpId(empId);
			// System.out.println(dataForUpdateAttendance);
			Info dailydailyinfo = Constants.getRestTemplate().postForObject(
					Constants.url + "/importAttendanceByFileAndUpdate", dataForUpdateAttendance, Info.class);

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			if (dailydailyinfo.isError() == false) {

				for (Date m = fmdt; m.compareTo(todt) <= 0;) {

					Calendar a = Calendar.getInstance();
					a.setTime(m);
					int year = a.get(Calendar.YEAR);
					int month = a.get(Calendar.MONTH) + 1;
					// System.out.println(m + " " + year + " " + k);

					Date firstDay = new GregorianCalendar(year, month - 1, 1).getTime();
					Date lastDay = new GregorianCalendar(year, month, 0).getTime();
					map = new LinkedMultiValueMap<String, Object>();
					map.add("fromDate", sf.format(firstDay));
					map.add("toDate", sf.format(lastDay));
					map.add("userId", userId);
					map.add("month", month);
					map.add("year", year);
					map.add("empId", empId);
					// System.out.println(map);

					Info sumryinfo = Constants.getRestTemplate()
							.postForObject(Constants.url + "/finalUpdateDailySumaryRecord", map, Info.class);

					String dt = "0" + "-" + (month + 1) + "-" + year;
					m = dd.parse(dt);
					m.setTime(m.getTime() + 1000 * 60 * 60 * 24);

				}
			}

			finalRes.setError(false);
			finalRes.setMsg("success");
		} catch (Exception e) {
			finalRes.setError(true);
			finalRes.setMsg("failed");
			e.printStackTrace();
		}
		return finalRes;
	}

}
