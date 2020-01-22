<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>

<body>
	<c:url var="getUniqEmpCodeResp" value="/getUniqEmpCodeResp"></c:url>

	<!-- Main navbar -->
	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<!-- /main navbar -->


	<!-- Page content -->
	<div class="page-content">

		<!-- Main sidebar -->
		<jsp:include page="/WEB-INF/views/include/left.jsp"></jsp:include>
		<!-- /main sidebar -->


		<!-- Main content -->
		<div class="content-wrapper">

			<!-- Page header -->
			<div class="page-header page-header-light"></div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">

				<!-- Form validation -->
				<div class="row">
					<div class="col-md-12">
						<!-- Title -->

						<!-- /title -->


						<div class="card">

							<div class="card-header header-elements-inline">
								<table width="100%">
									<tr width="100%">
										<td width="60%"><h5 class="card-title">Add Employee</h5></td>
										<td width="40%" align="right">
											<%-- <a
									href="${pageContext.request.contextPath}/showAddKra?empId=${editKra.exVar3}&finYrId=${editKra.exVar2}"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">KRA List </button>
								</a>  --%>
										</td>
									</tr>
								</table>
							</div>
							<div class="card-body">
								<%
									if (session.getAttribute("errorMsg") != null) {
								%>
								<div
									class="alert bg-danger text-white alert-styled-left alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">
										<span>×</span>
									</button>
									<span class="font-weight-semibold">Oh snap!</span>
									<%
										out.println(session.getAttribute("errorMsg"));
									%>
								</div>

								<%
									session.removeAttribute("errorMsg");
									}
								%>
								<%
									if (session.getAttribute("successMsg") != null) {
								%>
								<div
									class="alert bg-success text-white alert-styled-left alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">
										<span>×</span>
									</button>
									<span class="font-weight-semibold">Well done!</span>
									<%
										out.println(session.getAttribute("successMsg"));
									%>
								</div>
								<%
									session.removeAttribute("successMsg");
									}
								%>

								<!-- Highlighted tabs -->
								<ul class="nav nav-tabs nav-tabs-highlight">
									<li class="nav-item text-center"><a
										href="#highlighted-tab1" class="nav-link active"
										data-toggle="tab">Basic Information </br>Step 1
									</a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab2" class="nav-link" data-toggle="tab">Personal
											Information </br>Step 2
									</a></a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab3" class="nav-link" data-toggle="tab">Relative
											Information </br>Step 3
									</a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab4" class="nav-link" data-toggle="tab">Employee
											Bank Details </br>Step 4
									</a></li>

									<li class="nav-item text-center"><a
										href="#highlighted-tab5" class="nav-link" data-toggle="tab">Employee
											Salary Details </br>Step 5
									</a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab6" class="nav-link" data-toggle="tab">Employee
											Documents </br>Step 6
									</a></li>

								</ul>

								<div class="tab-content">
									<div class="tab-pane fade show active" id="highlighted-tab1">

										<form
											action="${pageContext.request.contextPath}/insertEmployeeBasicInfo"
											id="submitInsertEmp" method="post">

											<input type="hidden" id="empId" name="empId"
												value="${emp.empId}">
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="empCode">Emp
													Code <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${emp.empCode}" placeholder="Employee Code."
														id="empCode" name="empCode"
														style="text-transform: uppercase;" autocomplete="off"
														onchange="trim(this)" maxlength="5"> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="unique_user">Employee
														Code Already Exist.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="fname">
													Employee Name <span style="color: red">*</span>:
												</label>
												<div class="col-lg-3">
													<input type="text" class="form-control"
														value="${emp.firstName}" placeholder="First Name"
														id="fname" name="fname" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_fname">This field
														is required.</span>
												</div>


												<div class="col-lg-3">
													<input type="text" class="form-control"
														value="${emp.middleName}" placeholder="Middle Name"
														id="mname" name="mname" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_mname">This field
														is required.</span>
												</div>




												<div class="col-lg-3">
													<input type="text" class="form-control"
														value="${emp.surname}" placeholder="Last Name" id="sname"
														name="sname" autocomplete="off" onchange="trim(this)">
													<span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_sname">This field
														is required.</span>
												</div>
											</div>


											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="company">Company
													<span style="color: red"> </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control "
														readonly="readonly" value="GFPL">
												</div>

												<label class="col-form-label col-lg-2" for="locId">Location
													<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="locId" data-placeholder="Select Location"
														id="locId"
														class="form-control form-control-select2 select2-hidden-accessible">


														<c:forEach items="${locationList}" var="locationList">
															<c:choose>
																<c:when test="${locationList.locId==emp.locationId}">
																	<option selected="selected"
																		value="${locationList.locId}">${locationList.locName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${locationList.locId}">${locationList.locName}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_locId">This field
														is required.</span>
												</div>
											</div>


											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="desigId">
													Designation <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="desigId"
														data-placeholder="Select Designation" id="desigId"
														class="form-control form-control-select2 select2-hidden-accessible">

														<c:forEach items="${designationList}"
															var="designationList">
															<c:choose>
																<c:when
																	test="${designationList.desigId==emp.designationId}">
																	<option selected="selected"
																		value="${designationList.desigId}">${designationList.name}</option>
																</c:when>
																<c:otherwise>
																	<option value="${designationList.desigId}">${designationList.name}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_desigId">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="deptId">
													Department <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="deptId" data-placeholder="Select Department"
														id="deptId"
														class="form-control form-control-select2 select2-hidden-accessible">


														<c:forEach items="${deptList}" var="deptList">
															<c:choose>
																<c:when test="${deptList.departId==emp.departId}">
																	<option selected="selected"
																		value="${deptList.departId}">${deptList.name}</option>
																</c:when>
																<c:otherwise>
																	<option value="${deptList.departId}">${deptList.name}</option>
																</c:otherwise>
															</c:choose>

														</c:forEach>
													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_deptId">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="contractor">Contractor
													<span style="color: red"> </span>:
												</label>
												<div class="col-lg-4">
													<select name="contractor"
														data-placeholder="Select Contractor" id="contractor"
														class="form-control form-control-select2 select2-hidden-accessible">


														<c:forEach items="${contractorsList}"
															var="contractorsList">
															<c:choose>
																<c:when
																	test="${contractorsList.contractorId==emp.contractorId}">
																	<option selected="selected"
																		value="${contractorsList.contractorId}">${contractorsList.orgName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${contractorsList.contractorId}">${contractorsList.orgName}</option>
																</c:otherwise>
															</c:choose>

														</c:forEach>

													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_contractor">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="empType">Emp
													Type <span style="color: red">* </span>:
												</label>
												<div class="col-lg-4">
													<select name="empType"
														data-placeholder="Select Employee Type" id="empType"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="1" ${emp.empType==1 ? selected : ''}>Weekly
															Co Off</option>
														<option value="2" ${emp.empType==2 ? selected : ''}>OT
															Applicable</option>
														<option value="3" ${emp.empType==3 ? selected : ''}>Other</option>
													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_empType">This
														field is required.</span>
												</div>
											</div>



											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="mobile1">Contact
													No. <span style="color: red">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${emp.mobileNo1}" placeholder="Mobile No."
														id="mobile1" name="mobile1" autocomplete="off"
														onchange="trim(this)" maxlength="10"> <span
														style="display: none;"
														class="hidedefault   validation-invalid-label"
														id="error_mobile1">This field is required.</span> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_mobile1_unique">This
														Mobile No. is already exist.</span>
												</div>


												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${emp.mobileNo2}" placeholder="Other Mobile No."
														id="mobile2" name="mobile2" autocomplete="off"
														onchange="trim(this)" maxlength="10"> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_emgContNo2_alt">This
														field is required.</span>
												</div>

											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="empCat">Emp
													Category <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="empCat"
														data-placeholder="Select Emp Category" id="empCat"
														class="form-control form-control-select2 select2-hidden-accessible">

														<option value="">Select Category</option>
														<option value="Muster"
															${emp.empCategory=='Muster' ? 'selected' : ''}>Muster</option>
														<option value="Voucher"
															${emp.empCategory=='Voucher' ? 'selected' : ''}>Voucher</option>
														<option value="Contract"
															${emp.empCategory=='Contract' ? 'selected' : ''}>Contract</option>
														<option value="Trainee"
															${emp.empCategory=='Trainee' ? 'selected' : ''}>Trainee</option>
														<option value="Job"
															${emp.empCategory=='Job' ? 'selected' : ''}>Job</option>

													</select> <span class="hidedefault   validation-invalid-label"
														id="error_empCat" style="display: none;">This field
														is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="uan">UAN
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="UAN"
														id="uan" name="uan" autocomplete="off" value="${emp.uan}"
														onchange="trim(this)"><span
														class="hidedefault   validation-invalid-label"
														id="error_uan" style="display: none;">This field is
														required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="esic">ESIC
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${emp.esicNo}" placeholder="ESIC No." id="esic"
														name="esic" autocomplete="off" onchange="trim(this)">
													<span class="hidedefault   validation-invalid-label"
														id="error_esic" style="display: none;">This field
														is required.</span>
												</div>


												<label class="col-form-label col-lg-2" for="aadhar">Aadhar
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${emp.aadharNo}" placeholder="Aadhar Card No."
														id="aadhar" maxlength="12" name="aadhar"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_aadhar" style="display: none;">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="pan">PAN
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" maxlength="10"
														placeholder="PAN No." id="pan" name="pan"
														value="${emp.panCardNo}" autocomplete="off"
														onchange="trim(this)"><span
														class="hidedefault  validation-invalid-label"
														id="error_pan" style="display: none;">Please enter
														correct PAN No.</span>
												</div>

												<label class="col-form-label col-lg-2" for="pfNo">PF
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="PF No"
														id="pfNo" name="pfNo" autocomplete="off"
														value="${emp.pfNo}" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_pfNo" style="display: none;">This field
														is required.</span>
												</div>
											</div>


											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="pan">Accessable
													Location<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="locId_list"
														data-placeholder="Select Location" id="locId_list"
														multiple="multiple"
														class="form-control form-control-sm select"
														data-container-css-class="select-sm" data-fouc>

														<c:forEach items="${locationList}" var="locationList">
															<c:set value="0" var="find"></c:set>
															<c:forEach items="${locationIds}" var="locationIds">
																<c:if test="${locationList.locId==locationIds}">
																	<option selected="selected"
																		value="${locationList.locId}">${locationList.locName}</option>
																	<c:set value="1" var="find"></c:set>
																</c:if>
															</c:forEach>
															<c:if test="${find==0}">
																<option value="${locationList.locId}">${locationList.locName}</option>
															</c:if>
														</c:forEach>
													</select> <span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_locId_list">This
														field is required.</span>
												</div>
											</div>



											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-primary">
															<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
															Cancel
														</button></a> <input type="hidden" id="mobile1Exist"
														name="mobile1Exist"><input type="hidden"
														id="emailExist" name="emailExist">
												</div>
											</div>
										</form>
									</div>
									<!-- ********************************************Step Two********************************************** -->
									<div class="tab-pane fade" id="highlighted-tab2">
										Step Two


										<form
											action="${pageContext.request.contextPath}/submitEmpOtherInfo"
											id="submitEmpOtherInfo" method="post">
											<div class="form-group row">
												<div class="col-lg-6">
													<input type="hidden" id="empId" name="empId"
														value="${emp.empId}">
												</div>
												<div class="col-lg-6">
													<input type="hidden" id="empOtherInfoId"
														name="empOtherInfoId" value="${empPersInfo.empInfoId}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="desigId">Middle
													Name <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empPersInfo.middleName}"
														placeholder="Middle Name" id="midname" name="midname"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_midname">This
														field is required.</span>

												</div>

												<label class="col-form-label col-lg-2" for="relation">
													Relation <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="relation" data-placeholder="Select Relation"
														id="relation"
														class="form-control form-control-select2 select2-hidden-accessible">

														<option value="father"
															${empPersInfo.middleNameRelation=='father' ? 'selected' : ''}>Father</option>
														<option value="husband"
															${empPersInfo.middleNameRelation=='husband' ? 'selected' : ''}>Husband</option>
													</select> <span class="hidedefault   validation-invalid-label"
														id="error_relation" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="dob">Date
													of Birth <span style="color: red">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control datepickerclass"
														placeholder="Date of Birth" id="dob" name="dob"
														value="${empPersInfo.dob}" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_empDob" style="display: none;">This field
														is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="gender">Gender
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="gender" data-placeholder="Gender" id="gender"
														class="form-control form-control-select2 select2-hidden-accessible">
														<!-- <option>Select Gender</option> -->
														<option value="m"
															${empPersInfo.gender=='m' ? 'selected' : ''}>Male</option>
														<option value="f"
															${empPersInfo.gender=='f' ? 'selected' : ''}>Female</option>
													</select> <span class="hidedefault   validation-invalid-label"
														id="error_gender" style="display: none;">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="maritalstatus">Marital
													Status <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="maritalstatus"
														data-placeholder="Select Marital Status"
														id="maritalstatus"
														class="form-control form-control-select2 select2-hidden-accessible">

														<option value="Single"
															${empPersInfo.maritalStatus=='Single' ? 'selected' : ''}>Single</option>
														<option value="Married"
															${empPersInfo.maritalStatus=='Married' ? 'selected' : ''}>Married</option>
														<option value="Widowed"
															${empPersInfo.maritalStatus=='Widowed' ? 'selected' : ''}>Widowed</option>
														<option value="Divorced"
															${empPersInfo.maritalStatus=='Divorced' ? 'selected' : ''}>Divorced</option>
														<option value="Separated"
															${empPersInfo.maritalStatus=='Separated' ? 'selected' : ''}>Separated</option>

													</select> <span class="hidedefault   validation-invalid-label"
														id="error_maritalstatus" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="email">Email
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="Email"
														id="email" name="email" value="${empPersInfo.email}"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_persnlInfoEmail" style="display: none;">Please
														enter correct email.</span>

												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="caddress">Current
													Address <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empPersInfo.address}"
														placeholder="Current Address" id="address" name="caddress"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_caddress" style="display: none;">This
														field is required.</span>
												</div>


												<label class="col-form-label col-lg-2" for="paddress">Parmanent
													Address <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Parmanent Address" id="paddress"
														value="${empPersInfo.permanentAddress}" name="paddress"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_paddress" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="qualification">Qualification
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empPersInfo.empQualification}"
														placeholder="Qualification" id="qualification"
														name="qualification" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_qualification" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="emergencyPerson">Name
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empPersInfo.emerName}"
														placeholder="Emergency Person Name" id="emergencyPerson"
														name="emergencyPerson" autocomplete="off"
														onchange="trim(this)"> <span
														style="display: none;"
														class="hidedefault   validation-invalid-label"
														id="error_emergencyPerson">This field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="contact1">Emergency
													Contact 1 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${empPersInfo.emerContactNo1}"
														placeholder="Emergency Contact No. 1" id="contact1"
														maxlength="10" name="contact1" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_contact1" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="contact2">Emergency
													Contact 2 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">

													<input type="text" class="form-control numbersOnly"
														value="${empPersInfo.emerContactNo2}"
														placeholder="Emergency Contact No. 2" id="contact2"
														maxlength="10" name="contact2" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_contact2" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2"
													for="emergencyPersonAddress">Emergency Person
													Address <span style="color: red"> </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empPersInfo.emerContactAddr}"
														placeholder="Emergency Person Address"
														id="emergencyPersonAddress" name="emergencyPersonAddress"
														autocomplete="off" onchange="trim(this)"> <span
														style="display: none;"
														class="hidedefault   validation-invalid-label">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="bloodgroup">Blood
													Group <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="bloodgroup"
														data-placeholder="Select Blood Group" id="bloodgroup"
														class="form-control form-control-select2 select2-hidden-accessible">

														<option value="A+"
															${empPersInfo.bloodGroup=='A+' ? 'selected' : ''}>A+</option>
														<option value="A-"
															${empPersInfo.bloodGroup=='A-' ? 'selected' : ''}>A-</option>
														<option value="B+"
															${empPersInfo.bloodGroup=='B+' ? 'selected' : ''}>B+</option>
														<option value="B-"
															${empPersInfo.bloodGroup=='B-' ? 'selected' : ''}>B-</option>
														<option value="AB+"
															${empPersInfo.bloodGroup=='AB+' ? 'selected' : ''}>AB+</option>
														<option value="AB-"
															${empPersInfo.bloodGroup=='AB-' ? 'selected' : ''}>AB-</option>
														<option value="O+"
															${empPersInfo.bloodGroup=='O+' ? 'selected' : ''}>O+</option>
														<option value="O-"
															${empPersInfo.bloodGroup=='O-' ? 'selected' : ''}>O-</option>

													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_bloodgroup">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="uniformsize">Uniform
													Size <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="uniformsize"
														data-placeholder="Select Uniform Size" id="uniformsize"
														class="form-control form-control-select2 select2-hidden-accessible">

														<option value="medium"
															${empPersInfo.uniformSize=='medium' ? 'selected' : ''}>MEDIUM</option>
														<option value="large"
															${empPersInfo.uniformSize=='large' ? 'selected' : ''}>LARGE</option>
														<option value="xl"
															${empPersInfo.uniformSize=='xl' ? 'selected' : ''}>XL</option>
														<option value="xxl"
															${empPersInfo.uniformSize=='xxl' ? 'selected' : ''}>XXL</option>
														<option value="xxxl"
															${empPersInfo.uniformSize=='xxxl' ? 'selected' : ''}>XXXL</option>
													</select>
												</div>
											</div>

											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-primary">
															<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
															Cancel
														</button></a> <input type="hidden" id="mobile1Exist"
														name="mobile1Exist"><input type="hidden"
														id="emailExist" name="emailExist">
												</div>
											</div>
										</form>

									</div>
									<!--***************************************** tab 3 *************************************-->
									<div class="tab-pane fade" id="highlighted-tab3">

										<form
											action="${pageContext.request.contextPath}/submitEmpRelationInfo"
											id="submitInsertRelationEmp" method="post">

											<div class="form-group row">
												<div class="col-lg-6">
													<input type="hidden" id="empId" name="empId"
														value="${emp.empId}">
												</div>
												<div class="col-lg-6">
													<input type="hidden" id="empNomId" name="empNomId"
														value="${empNom.nomineeId}">
												</div>
											</div>

											<div class="row ">
												<div class="col-md-3">
													<div class="form-group">
														<label class="control-label ">Person Name </label>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<label class="control-label ">Date of Birth</label>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<label class="control-label ">Relation</label>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<label class="control-label">Occupation</label>
														<div class="col-md-3"></div>
													</div>
												</div>


											</div>
											<!--/row-->
											<div class="form-group row">
												<div class="col-md-3">

													<input type="text" name="name" value="${empNom.name}"
														class="form-control" style="width: 200px;"
														placeholder="Enter Person Name" />

												</div>
												<div class="col-md-3">

													<input type="text" class="form-control datepickerclass"
														placeholder="Date of Birth" id="dob" name="dob"
														value="${empNom.dob}" autocomplete="off"
														onchange="trim(this)">

												</div>
												<div class="col-md-3">

													<select name="relation" id="relation" data-rel="chosen"
														style="width: 180px;" class="form-control">
														<option>Please Select</option>
														<option value="f"
															${empNom.relation == 'f' ? 'selected' : ''}>Father</option>
														<option value="m"
															${empNom.relation == 'm' ? 'selected' : ''}>Mother</option>
														<option value="s1"
															${empNom.relation == 's1' ? 'selected' : ''}>Spouse</option>
														<option value="b"
															${empNom.relation == 'b' ? 'selected' : ''}>Brother</option>
														<option value="s2"
															${empNom.relation == 's2' ? 'selected' : ''}>Sister</option>
														<option value="s3"
															${empNom.relation == 's3' ?'selected' : ''}>Son</option>
														<option value="d"
															${empNom.relation == 'd' ? 'selected' : ''}>Daughter</option>
													</select>

												</div>
												<div class="col-md-3">

													<input type="text" name="occupation" id="occupation"
														value="${empNom.occupation1}" class="form-control"
														placeholder="Enter Occupation" />
													<div class="col-md-3"></div>

												</div>
											</div>
											<!--/row-->
											<div class="row">
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="name2" value="${empNom.name2}"
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" class="form-control datepickerclass"
															placeholder="Date of Birth" id="dob2" name="dob2"
															value="${empNom.dob2}" autocomplete="off"
															onchange="trim(this)">
														<!-- <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span> -->
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<select name="relation2" id="relation2" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f"
																${empNom.relation2 == 'f' ? 'selected' : ''}>Father</option>
															<option value="m"
																${empNom.relation2 == 'm' ? 'selected' : ''}>Mother</option>
															<option value="s1"
																${empNom.relation2 == 's1' ? 'selected' : ''}>Spouse</option>
															<option value="b"
																${empNom.relation2 == 'b' ? 'selected' : ''}>Brother</option>
															<option value="s2"
																${empNom.relation2 == 's2' ? 'selected' : ''}>Sister</option>
															<option value="s3"
																${empNom.relation2 == 's3' ?'selected' : ''}>Son</option>
															<option value="d"
																${empNom.relation2 == 'd' ? 'selected' : ''}>Daughter</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="occupation2" id="occupation2"
															value="${empNom.occupation2}" class="form-control"
															placeholder="Enter Occupation" />
														<div class="col-md-3"></div>
													</div>
												</div>
											</div>
											<!--/row-->
											<div class="row">
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="name3" value="${empNom.name3}"
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" class="form-control datepickerclass"
															placeholder="Date of Birth" id="dob3" name="dob3"
															value="${empNom.dob3}" autocomplete="off"
															onchange="trim(this)">
														<!-- <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span> -->
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<select name="relation3" id="relation3" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f"
																${empNom.relation3 == 'f' ? 'selected' : ''}>Father</option>
															<option value="m"
																${empNom.relation3 == 'm' ? 'selected' : ''}>Mother</option>
															<option value="s1"
																${empNom.relation3 == 's1' ? 'selected' : ''}>Spouse</option>
															<option value="b"
																${empNom.relation3 == 'b' ? 'selected' : ''}>Brother</option>
															<option value="s2"
																${empNom.relation3 == 's2' ? 'selected' : ''}>Sister</option>
															<option value="s3"
																${empNom.relation3 == 's3' ? 'selected' : ''}>Son</option>
															<option value="d"
																${empNom.relation3 == 'd' ? 'selected' : ''}>Daughter</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="occupation3" id="occupation3"
															value="${empNom.occupation3}" class="form-control"
															placeholder="Enter Occupation" />
														<div class="col-md-3"></div>
													</div>
												</div>
											</div>
											<!--/row-->
											<div class="row">
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="name4" value="${empNom.name4}"
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" class="form-control datepickerclass"
															placeholder="Date of Birth" id="dob4" name="dob4"
															value="${empNom.dob4}" autocomplete="off"
															onchange="trim(this)">
														<!--  <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span> -->
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<select name="relation4" id="relation4" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f"
																${empNom.relation4 == 'f' ? 'selected' : ''}>Father</option>
															<option value="m"
																${empNom.relation4 == 'm' ? 'selected' : ''}>Mother</option>
															<option value="s1"
																${empNom.relation4 == 's1' ? 'selected' : ''}>Spouse</option>
															<option value="b"
																${empNom.relation4 == 'b' ? 'selected' : ''}>Brother</option>
															<option value="s2"
																${empNom.relation4 == 's2' ? 'selected' : ''}>Sister</option>
															<option value="s3"
																${empNom.relation4 == 's3' ?'selected' : ''}>Son</option>
															<option value="d"
																${empNom.relation4 == 'd' ? 'selected' : ''}>Daughter</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="occupation4" id="occupation4"
															value="${empNom.occupation4}" class="form-control"
															placeholder="Enter Occupation" />
														<div class="col-md-3"></div>
													</div>
												</div>
											</div>
											<!--/row-->
											<div class="row">
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="name5" value="${empNom.name5}"
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" class="form-control datepickerclass"
															placeholder="Date of Birth" id="dob5" name="dob5"
															value="${empNom.dob5}" autocomplete="off"
															onchange="trim(this)">
														<!-- <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span> -->
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<select name="relation5" id="relation5" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f"
																${empNom.relation5 == 'f' ? 'selected' : ''}>Father</option>
															<option value="m"
																${empNom.relation5 == 'm' ? 'selected' : ''}>Mother</option>
															<option value="s1"
																${empNom.relation5 == 's1' ? 'selected' : ''}>Spouse</option>
															<option value="b"
																${empNom.relation5 == 'b' ? 'selected' : ''}>Brother</option>
															<option value="s2"
																${empNom.relation5 == 's2' ? 'selected' : ''}>Sister</option>
															<option value="s3"
																${empNom.relation5 == 's3' ?'selected' : ''}>Son</option>
															<option value="d"
																${empNom.relation5 == 'd' ? 'selected' : ''}>Daughter</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="occupation5" id="occupation5"
															value="${empNom.occupation5}" class="form-control"
															placeholder="Enter Occupation" />
														<div class="col-md-3"></div>
													</div>
												</div>
											</div>
											<!--/row-->
											<div class="row">
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="name6" value="${empNom.name6}"
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" class="form-control datepickerclass"
															placeholder="Date of Birth" id="dob6" name="dob6"
															value="${empNom.dob6}" autocomplete="off"
															onchange="trim(this)">
														<!-- <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span> -->
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<select name="relation6" id="relation6" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f"
																${empNom.relation6 == 'f' ? 'selected' : ''}>Father</option>
															<option value="m"
																${empNom.relation6 == 'm' ? 'selected' : ''}>Mother</option>
															<option value="s1"
																${empNom.relation6 == 's1' ? 'selected' : ''}>Spouse</option>
															<option value="b"
																${empNom.relation6 == 'b' ? 'selected' : ''}>Brother</option>
															<option value="s2"
																${empNom.relation6 == 's2' ? 'selected' : ''}>Sister</option>
															<option value="s3"
																${empNom.relation6 == 's3' ?'selected' : ''}>Son</option>
															<option value="d"
																${empNom.relation6 == 'd' ? 'selected' : ''}>Daughter</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="occupation6" id="occupation6"
															class="form-control" value="${empNom.occupation6}"
															placeholder="Enter Occupation" />
														<div class="col-md-3"></div>
													</div>
												</div>
											</div>

											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-primary">
															<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
															Cancel
														</button></a> <input type="hidden" id="mobile1Exist"
														name="mobile1Exist"><input type="hidden"
														id="emailExist" name="emailExist">
												</div>
											</div>
										</form>
									</div>
									<!-- *****************************************Tab 4******************************************* -->
									<div class="tab-pane fade" id="highlighted-tab4">

										<form
											action="${pageContext.request.contextPath}/submitEmpBankInfo"
											id="submitEmpBankInfo" method="post">

											<div class="form-group row">
												<div class="col-lg-6">
													<input type="hidden" id="empId" name="empId"
														value="${emp.empId}">
												</div>
												<div class="col-lg-6">
													<input type="hidden" id="empBankId" name="empBankId"
														value="${empBank.bankInfoId}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="accNo">Account
													No: <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empBank.accNo}" placeholder="Account No"
														id="accNo" maxlength="17" name="accNo" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_accNo">This field
														is required.</span> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_accNoDigit">Invalid
														Account No.</span>
												</div>

												<label class="col-form-label col-lg-2" for="bankId">Bank
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="bankId" data-placeholder="Select Bank"
														id="bankId"
														class="form-control form-control-select2 select2-hidden-accessible">


														<c:forEach items="${bankList}" var="bankList">
															<c:choose>
																<c:when test="${bankList.bankId==empBank.bankId}">
																	<option selected value="${bankList.bankId}">${bankList.name}</option>
																</c:when>
																<c:otherwise>
																	<option value="${bankList.bankId}">${bankList.name}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</div>
											</div>

											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-primary">
															<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
															Cancel
														</button></a> <input type="hidden" id="mobile1Exist"
														name="mobile1Exist"><input type="hidden"
														id="emailExist" name="emailExist">
												</div>
											</div>
										</form>
									</div>

									<!--********************************* Tab 5 *********************************-->
									<div class="tab-pane fade" id="highlighted-tab5">

										<form
											action="${pageContext.request.contextPath}/insertEmployeeAllowancesInfo"
											id="insertEmployeeAllowancesInfo" method="post"
											enctype="multipart/form-data">


											<div class="form-group row">
												<div class="col-lg-4">
													<input type="hidden" id="empId" name="empId"
														value="${emp.empId}">
												</div>

												<div class="col-lg-4">
													<input type="hidden" id="empSalId" name="empSalId"
														value="${empAllowanceId.salaryInfoId}">
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="company">Basic
													Rs.<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														placeholder="Basic Rs." name="basic" id="basic"
														onchange="trim(this)" value="${empAllowanceId.basic}">
													<span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_salBasis">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="societyContri">Society
													Contribution Rs. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${empAllowanceId.societyContribution}"
														placeholder="Society Contribution Rs." id="societyContri"
														name="societyContri" autocomplete="off"
														onchange="trim(this)">

												</div>

											</div>


											<div class="row">

												<c:forEach items="${allowanceList}" var="allowanceList">
													<c:set var="allowanceValue" value="0"></c:set>
													<c:set var="empSalAlwncId" value="0"></c:set>

													<c:forEach items="${empAllowncList}" var="empAllowncList">

														<c:choose>
															<c:when
																test="${empAllowncList.allowanceId==allowanceList.allowanceId}">
																<c:set var="allowanceValue"
																	value="${empAllowncList.allowanceValue}"></c:set>
																<c:set var="empSalAlwncId"
																	value="${empAllowncList.empSalAllowanceId}"></c:set>
															</c:when>
															<%-- 	<c:otherwise>
											 		<c:set var="allowanceValue" value="0"></c:set>
											 	</c:otherwise> --%>

														</c:choose>
													</c:forEach>
													<div class=" col-lg-6">
														<div class="form-group row">
															<label class="col-form-label col-lg-2" for="allownces">${allowanceList.shortName}
																<span style="color: red"></span>:
															</label>
															<div class="col-lg-10">
																<input type="text" class="form-control numbersOnly"
																	value="${allowanceValue}"
																	placeholder="${allowanceList.name}"
																	id="allownces${allowanceList.allowanceId}"
																	name="allownces${allowanceList.allowanceId}"
																	autocomplete="off" onchange="trim(this)"> <input
																	type="hidden"
																	id="empSalAllownaceId${allowanceList.allowanceId}"
																	name="empSalAllownaceId${allowanceList.allowanceId}"
																	value="${empSalAlwncId}">
															</div>

														</div>
													</div>

												</c:forEach>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="pan">PF
													Applicable <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empAllowanceId.pfApplicable}"
														placeholder="PF Applicable" id="pfApplicable"
														name="pfApplicable" autocomplete="off"
														onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="pfNo">PF
													Type <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="PF Type" id="pfType" name="pfType"
														autocomplete="off" value="${empAllowanceId.pfType}"
														onchange="trim(this)">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="pfEmpPer">PF
													Employee Per <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${empAllowanceId.pfEmpPer}"
														placeholder="PF Employee Per" id="pfEmpPer"
														name="pfEmpPer" autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="pfNo">PF
													Employer Per <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														placeholder="PF Employer Per" id="pfEmployerPer"
														name="pfEmployerPer" autocomplete="off"
														onchange="trim(this)" value="${empAllowanceId.pfEmplrPer}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="esicApplicable">ESIC
													Applicable <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empAllowanceId.esicApplicable}"
														placeholder="ESIC Applicable" id="esicApplicable"
														name="esicApplicable" autocomplete="off"
														onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="mlwfApplicable">MLWF
													Applicable <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="MLWF Applicable" id="mlwfApplicable"
														name="mlwfApplicable" autocomplete="off"
														onchange="trim(this)"
														value="${empAllowanceId.mlwfApplicable}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="empEsicPer">Employee
													Esic Percentage <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${empAllowanceId.employeeEsicPercentage}"
														placeholder="Employee Esic Percentage" id="empEsicPer"
														name="empEsicPer" autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="employerEsicPer">Employer
													Esic Percentage <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														placeholder="Employer Esic Percentage"
														id="employerEsicPer" name="employerEsicPer"
														autocomplete="off" onchange="trim(this)"
														value="${empAllowanceId.employerEsicPercentage}">
												</div>
											</div>


											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="ptApplicable">PT
													Applicable <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empAllowanceId.ptApplicable}"
														placeholder="PT Applicable" id="ptApplicable"
														name="ptApplicable" autocomplete="off"
														onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="salBasis">Salary
													Basis <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="salBasis"
														data-placeholder="Select Designation" id="salBasis"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="monthly"
															${empAllowanceId.salBasis=='monthly' ? 'selected' : ''}>Monthly</option>
														<option value="daily"
															${empAllowanceId.salBasis=='daily' ? 'selected' : ''}>Daily</option>
													</select>
												</div>
											</div>


											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="epfJoinDate">EPF
													Joining Date <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">

													<input type="text" class="form-control datepickerclass"
														placeholder="EPF Joining Date" id="epfJoinDate"
														name="epfJoinDate" autocomplete="off"
														onchange="trim(this)"
														value="${empAllowanceId.epfJoiningDate}">
												</div>

												<label class="col-form-label col-lg-2" for="joinDate">Joining
													Date <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Joining Date" id="joinDate" name="joinDate"
														autocomplete="off" onchange="trim(this)"
														value="${empAllowanceId.cmpJoiningDate}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="leaveDate">Leaving
													Date <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empAllowanceId.cmpLeavingDate}"
														placeholder="Leaving Date" id="leaveDate" name="leaveDate"
														autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="leaveReason">Leaving
													Reason <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Leaving Reason" id="leaveReason"
														name="leaveReason" autocomplete="off"
														onchange="trim(this)"
														value="${empAllowanceId.leavingReason}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="salBasis">LR
													For ESIC <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empAllowanceId.leavingReasonEsic}"
														placeholder="LR For ESIC" id="lrEsic" name="lrEsic"
														autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="lrForPF">LR
													For PF <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="LR For PF" id="lrForPF" name="lrForPF"
														autocomplete="off" onchange="trim(this)"
														value="${empAllowanceId.leavingReasonPf}">
												</div>
											</div>

											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-primary">
															<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
															Cancel
														</button></a> <input type="hidden" id="mobile1Exist"
														name="mobile1Exist"><input type="hidden"
														id="emailExist" name="emailExist">
												</div>
											</div>
										</form>
									</div>
									<!-- *****************************************Tab 6******************************************* -->
									<div class="tab-pane fade" id="highlighted-tab6">

										<form
											action="${pageContext.request.contextPath}/submitInsertEmpDoc"
											id="submitInsertRelationEmp" method="post"
											enctype="multipart/form-data">

											<div class="form-group row">
												<div class="col-lg-6">
													<input type="hidden" id="empId" name="empId"
														value="${emp.empId}">
												</div>
												<!-- <div class="col-lg-6">
													<input type="text" id="empDocId"
														name="empDocId">
												</div> -->
											</div>

											<c:forEach items="${empDocList}" var="empDocList">

												<c:set var="docName" value=""></c:set>
												<c:set var="docId" value="0"></c:set>

												<c:forEach items="${docList}" var="docList">

													<c:choose>
														<c:when test="${empDocList.doctypeId==docList.doctypeId}">
															<c:set var="docName" value="${docList.docImage}"></c:set>
															<c:set var="docId" value="${docList.docId}"></c:set>

														</c:when>
													</c:choose>
												</c:forEach>

												<div class="form-body">
													<h4 align="center">${empDocList.doctypeName}</h4>

													<div class="row">
														<div class="col-md-6">
															<div class="form-group">
																<label class="control-label col-md-3"><img
																	id="blah" src="${imgUrl}${docName}" alt="Emp Photo"
																	width="300px" /></label>
																<div class="col-md-9" style="padding-left: 200px">
																	<input id="doc${empDocList.doctypeId}" type="file"
																		name="doc" class="nocheck" onchange="show(this)">


																	<input type="hidden"
																		name="docType${empDocList.doctypeId}"
																		id="docType${empDocList.doctypeId}"
																		value="${empDocList.doctypeId}"> <input
																		type="hidden" id="empDocId${empDocList.doctypeId}"
																		name="empDocId${empDocList.doctypeId}"
																		value="${docId}"> (only jpg,png,gif,pdf) <span
																		style="display: none;"
																		class="hidedefault  validation-invalid-label"
																		id="error_img">Only these file types are
																		accepted : jpg,png,gif,pdf</span>


																</div>
															</div>
														</div>

													</div>

												</div>

											</c:forEach>

											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-primary">
															<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
															Cancel
														</button></a> <input type="hidden" id="mobile1Exist"
														name="mobile1Exist"><input type="hidden"
														id="emailExist" name="emailExist">
												</div>
											</div>
										</form>
									</div>


								</div>
								<!-- /highlighted tabs -->

								<p class="desc text-danger fontsize11">Notice : * Fields are
									mandatory.</p>
							</div>
						</div>


					</div>
				</div>

			</div>
			<!-- /content area -->


			<!-- Footer -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
			<!-- /footer -->

		</div>
		<!-- /main content -->

	</div>
	<!-- /page content -->


	<script
		src="${pageContext.request.contextPath}/resources/global_assets/js/footercommonjs.js"></script>

	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines 
			return;
		}
		$(document).ready(function() {
			$("#pan").change(function() {
				var pan = $("#pan").val();

				var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
				if (regex1.test($.trim(pan)) == false) {
					$("#error_pan").show()
					return false;
				}
				$("#error_pan").hide()
				return true;
			});
		});

		$(document).ready(function() {

			$("#empCode").change(function() {
				var empCode = $('#empCode').val();

				$.getJSON('${getUniqEmpCodeResp}',

				{

					empCode : empCode,
					ajax : 'true'

				}, function(data) {
					//alert(JSON.stringify(data));
					if (data == 0) {
						$("#unique_user").show()
					} else {
						$("#unique_user").hide()
					}

				});
			});
		});

		$(document)
				.ready(
						function($) {

							$("#submitInsertEmp")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#empCode").val()) {

													isError = true;

													$("#error_empCode").show()
													//return false;
												} else {
													$("#error_empCode").hide()
												}

												if (!$("#fname").val()) {

													isError = true;

													$("#error_fname").show()

												} else {
													$("#error_fname").hide()
												}
												if (!$("#mname").val()) {

													isError = true;

													$("#error_mname").show()

												} else {
													$("#error_mname").hide()
												}
												if (!$("#sname").val()) {

													isError = true;

													$("#error_sname").show()

												} else {
													$("#error_sname").hide()
												}
												if (!$("#locId").val()) {

													isError = true;

													$("#error_locId").show()

												} else {
													$("#error_locId").hide()
												}

												if ($("#locId_list").val() == "") {

													isError = true;

													$("#error_locId_list")
															.show()

												} else {
													$("#error_locId_list")
															.hide()
												}

												if (!$("#empCat").val()) {

													isError = true;

													$("#error_empCat").show()

												} else {
													$("#error_empCat").hide()
												}

												if (!$("#empType").val()) {

													isError = true;

													$("#error_empType").show()

												} else {
													$("#error_empType").hide()
												}

												if (!$("#mobile1").val()
														|| !validateMobile($(
																"#mobile1")
																.val())) {

													isError = true;

													$("#error_mobile1").show()

												} else {
													$("#error_mobile1").hide()
												}

												if (validatePAN($("#pan").val())) {

													isError = true;

													$("#error_pan").show()

												} else {
													$("#error_pan").hide()
												}
												/* 	if (!$("#desigId").val()) {

														isError = true;

														$("#error_desigId")
																.show()

													} else {
														$("#error_desigId")
																.hide()
													} */

												/* if (!$("#empStatus").val()) {

													isError = true;

													$("#error_empStatus").show()

												} else {
													$("#error_empStatus").hide()
												} */

												/* if (!$("#mobile2").val()) {

													isError = true;

													$("#error_emgContNo2_alt").show()

												} else {
													$("#error_emgContNo2_alt").hide()
												} */

												/* if (!$("#landline").val()) {

													isError = true;

													$("#error_landline")
															.show()

												} else {
													$("#error_landline")
															.hide()
												} */

												/* if (!$("#esic").val()) {

													isError = true;

													$("#error_esic").show()

												} else {
													$("#error_esic").hide()
												}

												if (!$("#aadhar").val()) {

													isError = true;

													$("#error_aadhar").show()

												} else {
													$("#error_aadhar").hide()
												}

												if (!$("#uan").val()) {

													isError = true;

													$("#error_uan").show()

												} else {
													$("#error_uan").hide()
												} */

												/* if (!$("#pan").val()|| !validatePAN($(
												"#pan").val())) {

														isError = true;

														$("#error_pan").show()

													} else {
														$("#error_pan").hide()
													}
													 
													
													if (!$("#pfNo").val()) {

														isError = true;

														$("#error_pfNo").show()

													} else {
														$("#error_pfNo").hide()
													}
												 */
												if (!isError) {

													var x = true;
													if (x == true) {

														//document.getElementById("submtbtn").disabled = true;
														return true;
													}
													//
												}
												return false;
											});
						});
		//
		/* Bank */
		$(document).ready(function($) {

			$("#submitEmpBankInfo").submit(function(e) {
				var isError = false;
				var errMsg = "";
				var acc = $("#accNo").val();

				if (!$("#accNo").val()) {

					isError = true;

					$("#error_accNo").show()

				} else {
					$("#error_accNo").hide()
				}

				if (acc.length<8 || acc.length>17) {

					isError = true;

					$("#error_accNoDigit").show()

				} else {
					$("#error_accNoDigit").hide()
				}
				if (!isError) {

					var x = true;
					if (x == true) {

						//document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//
				}
				return false;
			});
		});
		/* Employee Salary */
		$(document).ready(function($) {

			$("#insertEmployeeAllowancesInfo").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#basic").val()) {

					isError = true;

					$("#error_salBasis").show()
					//return false;
				} else {
					$("#error_salBasis").hide()
				}

				if (!isError) {

					var x = true;
					if (x == true) {

						//document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//
				}
				return false;
			});
		});

		/* Personal Information */
		$(document).ready(function($) {

			$("#submitEmpOtherInfo").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#dob").val()) {

					isError = true;

					$("#error_empDob").show()
					//return false;
				} else {
					$("#error_empDob").hide()
				}

				if (validateEmail($("#email").val())) {

					isError = true;

					$("#error_persnlInfoEmail").show()
					//return false;
				} else {
					$("#error_persnlInfoEmail").hide()
				}

				if (!isError) {

					var x = true;
					if (x == true) {

						//document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//
				}
				return false;
			});
		});
	</script>

	<!-- <script type="text/javascript">
	$('#submtbtn').on('click', function() {
        swalInit({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger',
            buttonsStyling: false
        }).then(function(result) {
            if(result.value) {
                swalInit(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                );
            }
            else if(result.dismiss === swal.DismissReason.cancel) {
                swalInit(
                    'Cancelled',
                    'Your imaginary file is safe :)',
                    'error'
                );
            }
        });
    });
	
	</script> -->
	<script type="text/javascript">
		// Single picker
		$('.datepickerclass').daterangepicker({
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});

		//daterange-basic_new
		// Basic initialization
		$('.daterange-basic_new').daterangepicker({
			applyClass : 'bg-slate-600',

			cancelClass : 'btn-light',
			locale : {
				format : 'DD-MM-YYYY',
				separator : ' to '
			}
		});

		function show(input) {

			try {

				debugger;
				var validExtensions = [ 'jpg', 'png', 'jpeg', 'pdf' ]; //array of valid extensions
				var fileName = input.files[0].name;
				var fileNameExt = fileName
						.substr(fileName.lastIndexOf('.') + 1);
				if ($.inArray(fileNameExt, validExtensions) == -1) {
					input.type = ''
					input.type = 'file'
					$('#user_img').attr('src', "");
					alert("Only these file types are accepted : "
							+ validExtensions.join(', '));
					//$('#error_img').show()
				} else {
					//$('#error_img').hide()
					if (input.files && input.files[0]) {
						var filerdr = new FileReader();
						filerdr.onload = function(e) {
							$('#user_img').attr('src', e.target.result);
						}
						filerdr.readAsDataURL(input.files[0]);
					}
				}
			} catch (err) {

			}
		}
	</script>
</body>
</html>