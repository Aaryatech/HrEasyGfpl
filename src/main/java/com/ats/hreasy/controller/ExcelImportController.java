package com.ats.hreasy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.hreasy.common.Constants;
import com.ats.hreasy.model.EmployeeBean;
import com.ats.hreasy.model.EmployeeMaster;
import com.ats.hreasy.model.EmployeeRelatedTbls;
import com.ats.hreasy.model.TblEmpInfo;

@Controller
@Scope("session")
public class ExcelImportController {

	String curDateTime;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

	@RequestMapping(value = "/excelCustActMap", method = RequestMethod.GET)
	public @ResponseBody List addCustomerActMap(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = null;
		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		List<TblEmpInfo> empListInfo = new ArrayList<TblEmpInfo>();
		
		 
		try {

			FileInputStream file = new FileInputStream(new File("/home/maddy/Documents/imgs/HrEasyDBDocs.xlsx"));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			
			//Import Employees
			Row row;
	        for(int i=1; i<=sheet.getLastRowNum(); i++){  //points to the starting of excel i.e excel first row
	            row = (Row) sheet.getRow(i);  //sheet number

	        
		            String empCode = null;
					if( row.getCell(0)!=null) 
						empCode= row.getCell(0).getStringCellValue();
					else
							break;

	                 int empType = 0;
					if( row.getCell(1)!= null) 
						empType = (int) row.getCell(1).getNumericCellValue();   

	                   int department = 0;
					if( row.getCell(2) != null) 
						department = (int) row.getCell(2).getNumericCellValue();
					
					 int designation = 0;
					if( row.getCell(3)!= null) 
		               designation = (int) row.getCell(3).getNumericCellValue();
					
					 int location = 0;
				   if( row.getCell(4) != null) 
			           location = (int) row.getCell(4).getNumericCellValue();
				   
				    String surname = null;
				   if( row.getCell(5) != null) 
				   		surname= row.getCell(5).getStringCellValue();
				   
				   String firstname = null;
				   if( row.getCell(6) != null)
				   		firstname= row.getCell(6).getStringCellValue();
				   
				   String middlename = null;
				   if( row.getCell(7) != null) 
				   		middlename= row.getCell(7).getStringCellValue();
				   
				   String pan = null;
				   if( row.getCell(8) != null)
				   		pan = row.getCell(8).toString();
				   
				   String pfno = null;
				   if( row.getCell(9)!= null)
				   		pfno = row.getCell(9).toString();
				   
				   String esicno = null;
				   if( row.getCell(10) != null) 
				   		esicno = row.getCell(10).toString();
				   
				   String aadhar = null;
				   if( row.getCell(11) != null) 
					   aadhar = row.getCell(11).toString();
				   
				   String uan = null;
				   if( row.getCell(12)!= null) 
				   		uan= row.getCell(12).toString();
				   
				   String empInfoMiddlename = null;
				   if( row.getCell(13) != null) 
					   empInfoMiddlename= row.getCell(13).getStringCellValue();


				   String middlenamerelation = null;
				   if( row.getCell(14) != null) 
					   middlenamerelation= row.getCell(14).getStringCellValue(); 
				   
				   String dob = null;
				   if( row.getCell(15) != null) 
					   dob= row.getCell(15).getStringCellValue(); 
					
				   String gender = null;
				   if( row.getCell(16) != null) 
					   gender= row.getCell(16).getStringCellValue(); 
					
				   String maritalStatus = null;
				   if( row.getCell(17) != null) 
					   maritalStatus= row.getCell(17).getStringCellValue();
				   
				   String address = null;
				   if( row.getCell(18) != null) 
					   address= row.getCell(18).getStringCellValue();
				   
				   String permamnentAddress = null;
				   if( row.getCell(19) != null) 
					   permamnentAddress= row.getCell(19).getStringCellValue();
				   
				   String name2 = null;
				   if( row.getCell(20) != null) 
					   name2= row.getCell(20).getStringCellValue();
				   
				   String relation2 = null;
				   if( row.getCell(20) != null) 
					   relation2= row.getCell(20).getStringCellValue();
				   
				   /*String name2 = null;
				   if( row.getCell(20) != null) 
					   name2= row.getCell(20).getStringCellValue();*/
				   
				   
				   
		    EmployeeMaster emp = new EmployeeMaster();
		    emp.setCmpCode(1);
		    emp.setEmpCode(empCode);
		    emp.setEmpType(empType);
		    emp.setDepartId(department);
		    emp.setDesignationId(designation);
		    emp.setLocationId(location);
		    emp.setSurname(surname);
		    emp.setFirstName(firstname);
		    emp.setMiddleName(middlename);
		    emp.setPanCardNo(pan);
		    emp.setPfNo(pfno);
		    emp.setEsicNo(esicno);
		    emp.setAadharNo(aadhar);
		    emp.setUan(uan);
		    emp.setDelStatus(1);
		   

			MultiValueMap<String, Object> mapEmp = new LinkedMultiValueMap<>();
			mapEmp.add("empCode", empCode);
		    EmployeeRelatedTbls checkEmpCode = Constants.getRestTemplate().postForObject(Constants.url + "/getEmpRelatedInfo", mapEmp,
		    		EmployeeRelatedTbls.class);
		    System.out.println("checkEmpCode Resp--------"+checkEmpCode);
		
		   
		    
		    if(checkEmpCode!=null) {
		    	emp.setEmpId(checkEmpCode.getEmpId());
		    }else {
		    	emp.setEmpId(0);
		    }
		     
		    EmployeeMaster empSaveResp =  Constants.getRestTemplate().postForObject(Constants.url + "/saveEmployee", emp,
					EmployeeMaster.class);
		    System.out.println("Emp Resp--------"+empSaveResp);
	   
				 
				TblEmpInfo empInfo = new TblEmpInfo();
				try {
					empInfo.setEmpId(empSaveResp.getEmpId());
					empInfo.setEmpInfoId(checkEmpCode.getEmpInfoId());
				} catch (Exception e) {
					empInfo.setEmpInfoId(0);
				}

				empInfo.setMiddleName(empInfoMiddlename);
				empInfo.setMiddleNameRelation(middlenamerelation);
				empInfo.setDob(dob);
				empInfo.setGender(gender);
				empInfo.setMaritalStatus(maritalStatus);
				empInfo.setAddress(address);
				empInfo.setPermanentAddress(permamnentAddress);
				empListInfo.add(empInfo);
				TblEmpInfo empInfoSave = Constants.getRestTemplate()
						.postForObject(Constants.url + "/saveEmployeeIdInfo", empInfo, TblEmpInfo.class);
				System.out.println("EmpInfo--------" + empInfoSave);

	        }
	        
	       
		
	 
			
			
			/*Row ro = null;
			System.err.println("sheet.getLastRowNum() " + sheet.getLastRowNum());
			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
				ro = sheet.getRow(i);

				/// int srNo = (int) ro.getCell(0).getNumericCellValue();
				String empNo = (String) ro.getCell(0).getStringCellValue();
				System.out.println("Value----" + empNo);

			} // end of for Loop Row Index;*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
