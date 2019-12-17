<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>

<body>

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
								<c:set var="tabClass1" value="nav-link" />
								<c:set var="tabClass2" value="nav-link" />
								<c:choose>
									<c:when test="${flag==0}">
										<c:set var="tabClass1" value="nav-link active" />
										<c:set var="tabClass2" value="nav-link" />

									</c:when>
									<c:when test="${flag==1}">
										<c:set var="tabClass1" value="nav-link" />
										<c:set var="tabClass2" value="nav-link active" />
									</c:when>
								</c:choose>

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
										href="#highlighted-tab4" class="nav-link" data-toggle="tab">Employee Bank Details
											</br>Step 4
									</a></li>
									
									<li class="nav-item text-center"><a
										href="#highlighted-tab5" class="nav-link" data-toggle="tab">Employee Salary Details
											</br>Step 5
									</a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab6" class="nav-link" data-toggle="tab">Employee Documents
											</br>Step 6
									</a></li>
									
								</ul>

								<div class="tab-content">
									<div class="tab-pane fade show active" id="highlighted-tab1">



										<form
											action="${pageContext.request.contextPath}/insertEmployeeBasicInfo"
											id="submitInsertEmp" method="post"
											enctype="multipart/form-data">

											<input type="text" id="empId" name="empId"
												value="${emp.empId}">
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="empCode">Emp
													Code <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Employee Code." id="empCode" name="empCode"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_empCode">This field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="fname">
													Employee Name <span style="color: red">*</span>:
												</label>
												<div class="col-lg-3">
													<input type="text" class="form-control  "
														placeholder="First Name" id="fname" name="fname"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_fname">This field is required.</span>
												</div>


												<div class="col-lg-3">
													<input type="text" class="form-control  "
														placeholder="Middle Name" id="mname" name="mname"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_mname">This field is required.</span>
												</div>




												<div class="col-lg-3">
													<input type="text" class="form-control "
														placeholder="Last Name" id="sname" name="sname"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_sname">This field is required.</span>
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
														class="form-control form-control-select21 select2-hidden-accessible1">


														<c:forEach items="${locationList}" var="locationList">
															<option value="${locationList.locId}">${locationList.locName}</option>
														</c:forEach>
													</select> <span class="hidedefault   validation-invalid-label"
														id="error_locId">This field is required.</span>
												</div>
											</div>


											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="desigId">
													Designation <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="desigId"
														data-placeholder="Select Designation" id="desigId"
														class="form-control form-control-select21 select2-hidden-accessible1">

														<c:forEach items="${designationList}"
															var="designationList">
															<option value="${designationList.desigId}">${designationList.name}</option>
														</c:forEach>
													</select> <span class="hidedefault   validation-invalid-label"
														id="error_desigId">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="deptId">
													Department <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="deptId" data-placeholder="Select Department"
														id="deptId"
														class="form-control form-control-select21 select2-hidden-accessible1">


														<c:forEach items="${deptList}" var="deptList">
															<option value="${deptList.departId}">${deptList.name}</option>
														</c:forEach>
													</select> <span class="hidedefault   validation-invalid-label"
														id="error_deptId">This field is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="contractor">Contractor
													<span style="color: red"> </span>:
												</label>
												<div class="col-lg-4">
													<select name="contractor"
														data-placeholder="Select Contractor" id="contractor"
														class="form-control form-control-select21 select2-hidden-accessible1">


														<c:forEach items="${contractorsList}"
															var="contractorsList">
															<option value="${contractorsList.contractorId}">${contractorsList.orgName}</option>
														</c:forEach>

													</select> <span class="hidedefault   validation-invalid-label"
														id="error_contractor">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="empType">Emp
													Type <span style="color: red">* </span>:
												</label>
												<div class="col-lg-4">
													<select name="empType"
														data-placeholder="Select Employee Type" id="empType"
														class="form-control form-control-select21 select2-hidden-accessible1">
														<option value="1">Weekly Co Off</option>
														<option value="2">OT Applicable</option>
														<option value="3">Other</option>
													</select> <span class="hidedefault   validation-invalid-label"
														id="error_empType">This field is required.</span>
												</div>
											</div>



											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="mobile1">Contact
													No. <span style="color: red">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Mobile No." id="mobile1" name="mobile1"
														onchange="checkUnique(this.value,1)" autocomplete="off"
														onchange="trim(this)" maxlength="10"> <span
														class="hidedefault   validation-invalid-label"
														id="error_mobile1">This field is required.</span> <span
														class="hidedefault   validation-invalid-label"
														id="error_mobile1_unique">This Mobile No. is
														already exist.</span>
												</div>


												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Other Mobile No." id="mobile2" name="mobile2"
														autocomplete="off" onchange="trim(this)" maxlength="10">
													<span class="hidedefault   validation-invalid-label"
														id="error_emgContNo2_alt">This field is required.</span>
												</div>

												<!-- 
										<div class="col-lg-3">
											<input type="text" class="form-control"
												placeholder="Residence LandLine No" id="landline"
												name="landline" autocomplete="off" onchange="trim(this)">
											<span class="hidedefault validation-invalid-label"
												id="error_landline">This
												field is required.</span>
										</div> -->
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="empCat">Emp
													Category <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="empCat"
														data-placeholder="Select Emp Category" id="empCat"
														class="form-control form-control-select21 select2-hidden-accessible1">

														<option value="">Select Category</option>
														<option value="Muster">Muster</option>
														<option value="Voucher" selected="selected">Voucher</option>
														<option value="Contract">Contract</option>
														<option value="Trainee">Muster</option>
														<option value="Job">Job</option>

													</select> <span class="hidedefault   validation-invalid-label"
														id="error_empCat">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="uan">UAN
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="UAN"
														id="uan" name="uan" autocomplete="off"
														onchange="trim(this)"><span
														class="hidedefault   validation-invalid-label"
														id="error_uan">This field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="esic">ESIC
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="ESIC No." id="esic" name="esic"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_esic">This field is required.</span>
												</div>


												<label class="col-form-label col-lg-2" for="aadhar">Aadhar
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Aadhar Card No." id="aadhar" maxlength="12"
														name="aadhar" autocomplete="off" onchange="trim(this)">
													<span class="hidedefault   validation-invalid-label"
														id="error_aadhar">This field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="pan">PAN
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" maxlength="10"
														placeholder="PAN No." id="pan" name="pan"
														autocomplete="off" onchange="trim(this)"><span
														class="hidedefault  validation-invalid-label"
														id="error_pan">Please enter correct PAN No.</span>
												</div>

												<label class="col-form-label col-lg-2" for="pfNo">PF
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="PF No"
														id="pfNo" name="pfNo" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_pfNo">This field is required.</span>
												</div>
											</div>



											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a href="${pageContext.request.contextPath}/showEmpList"><button
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
											id="submitInsertOtherEmp" method="post">
											<div class="form-group row">
												<div class="col-lg-6">
													<input type="text" id="empId" name="empId"
														value="${emp.empId}">
												</div>
												<div class="col-lg-6">
													<input type="text" id="empOtherInfoId"
														name="empOtherInfoId" value="${empPersInfo.empInfoId}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="desigId">Middle
													Name <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Middle Name" id="midname" name="midname"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_midname">This field is required.</span>

												</div>

												<label class="col-form-label col-lg-2" for="relation">
													Relation <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="relation" data-placeholder="Select Relation"
														id="relation"
														class="form-control form-control-select21 select2-hidden-accessible1">

														<option value="father">Father</option>
														<option value="husband">Husband</option>
													</select> <span class="hidedefault   validation-invalid-label"
														id="error_relation">This field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="dob">Date
													of Birth <span style="color: red">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control datepickerclass"
														placeholder="Date of Birth" id="dob" name="dob"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="gender">Gender
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="gender" data-placeholder="Gender" id="gender"
														class="form-control form-control-select21 select2-hidden-accessible1">
														<option>Select Gender</option>
														<option value="m">Male</option>
														<option value="f">Female</option>
													</select> <span class="hidedefault   validation-invalid-label"
														id="error_gender">This field is required.</span>
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
														class="form-control form-control-select21 select2-hidden-accessible1">

														<option value="Single">Single</option>
														<option value="Married" selected="selected">Married</option>
														<option value="Widowed">Widowed</option>
														<option value="Divorced">Divorced</option>
														<option value="Separated">Separated</option>

													</select> <span class="hidedefault   validation-invalid-label"
														id="error_maritalstatus">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="email">Email
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="email" class="form-control "
														placeholder="Email" id="email" name="email"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span>

												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="caddress">Current
													Address <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Current Address" id="address" name="caddress"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_caddress">This field is required.</span>
												</div>


												<label class="col-form-label col-lg-2" for="paddress">Parmanent
													Address <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Parmanent Address" id="paddress"
														name="paddress" autocomplete="off" onchange="trim(this)">
													<span class="hidedefault   validation-invalid-label"
														id="error_paddress">This field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="qualification">Qualification
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Qualification" id="qualification"
														name="qualification" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_qualification">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="emergencyPerson">Name
													<span style="color: red">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Emergency Person Name" id="emergencyPerson"
														name="emergencyPerson" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_emergencyPerson">This field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="contact1">Emergency
													Contact 1 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Emergency Contact No. 1" id="contact1"
														name="contact1" autocomplete="off" onchange="trim(this)">
													<span class="hidedefault   validation-invalid-label"
														id="error_contact1">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="contact2">Emergency
													Contact 2 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">

													<input type="text" class="form-control"
														placeholder="Emergency Contact No. 2" id="contact2"
														name="contact2" autocomplete="off" onchange="trim(this)">
													<span class="hidedefault   validation-invalid-label"
														id="error_contact2">This field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2"
													for="emergencyPersonAddress">Emergency Person
													Address <span style="color: red"> </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Emergency Person Address"
														id="emergencyPersonAddress" name="emergencyPersonAddress"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="bloodgroup">Blood
													Group <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="bloodgroup"
														data-placeholder="Select Blood Group" id="bloodgroup"
														class="form-control form-control-select21 select2-hidden-accessible1">

														<option value="A+">A+</option>
														<option value="A-">A-</option>
														<option value="B+">B+</option>
														<option value="B-">B-</option>
														<option value="AB+">AB+</option>
														<option value="AB-">AB-</option>
														<option value="O+">O+</option>
														<option value="O-">O-</option>

													</select> <span class="hidedefault   validation-invalid-label"
														id="error_bloodgroup">This field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="uniformsize">Uniform
													Size <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="uniformsize"
														data-placeholder="Select Uniform Size" id="uniformsize"
														class="form-control form-control-select21 select2-hidden-accessible1">

														<option value="medium">MEDIUM</option>
														<option value="large">LARGE</option>
														<option value="xl">XL</option>
														<option value="xxl">XXL</option>
														<option value="xxxl">XXXL</option>
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
													<a href="${pageContext.request.contextPath}/showEmpList"><button
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
													<input type="text" id="empId" name="empId"
														value="${emp.empId}">
												</div>
												<div class="col-lg-6">
													<input type="text" id="empNomId"
														name="empNomId" value="${empNom.nomineeId}">
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
												
														<input type="text" name="name" value=""
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													
												</div>
												<div class="col-md-3">
													
														<input type="text" class="form-control datepickerclass"
														placeholder="Date of Birth" id="dob" name="dob"
														autocomplete="off" onchange="trim(this)">
													
												</div>
												<div class="col-md-3">
													
														<select name="relation" id="relation" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f">Father</option>
															<option value="m">Mother</option>
															<option value="s1">Spouse</option>
															<option value="b">Brother</option>
															<option value="s2">Sister</option>
															<option value="s3">Son</option>
															<option value="d">Daughter</option>
														</select>
													
												</div>
												<div class="col-md-3">
													
														<input type="text" name="occupation" id="occupation"
															value="" class="form-control"
															placeholder="Enter Occupation" />
														<div class="col-md-3"></div>
													
												</div>
											</div>
												<!--/row-->
											<div class="row">
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="name2" value=""
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" class="form-control datepickerclass"
														placeholder="Date of Birth" id="dob2" name="dob2"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<select name="relation2" id="relation2" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f">Father</option>
															<option value="m">Mother</option>
															<option value="s1">Spouse</option>
															<option value="b">Brother</option>
															<option value="s2">Sister</option>
															<option value="s3">Son</option>
															<option value="d">Daughter</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="occupation2" id="occupation2"
															value="" class="form-control"
															placeholder="Enter Occupation" />
														<div class="col-md-3"></div>
													</div>
												</div>
											</div>
												<!--/row-->
											<div class="row">
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="name3" value=""
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" class="form-control datepickerclass"
														placeholder="Date of Birth" id="dob3" name="dob3"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<select name="relation3" id="relation3" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f">Father</option>
															<option value="m">Mother</option>
															<option value="s1">Spouse</option>
															<option value="b">Brother</option>
															<option value="s2">Sister</option>
															<option value="s3">Son</option>
															<option value="d">Daughter</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="occupation3" id="occupation3"
															value="" class="form-control"
															placeholder="Enter Occupation" />
														<div class="col-md-3"></div>
													</div>
												</div>
											</div>
												<!--/row-->
											<div class="row">
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="name4" value=""
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" class="form-control datepickerclass"
														placeholder="Date of Birth" id="dob4" name="dob4"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<select name="relation4" id="relation4" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f">Father</option>
															<option value="m">Mother</option>
															<option value="s1">Spouse</option>
															<option value="b">Brother</option>
															<option value="s2">Sister</option>
															<option value="s3">Son</option>
															<option value="d">Daughter</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="occupation4" id="occupation4"
															value="" class="form-control"
															placeholder="Enter Occupation" />
														<div class="col-md-3"></div>
													</div>
												</div>
											</div>
												<!--/row-->
											<div class="row">
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="name5" value=""
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" class="form-control datepickerclass"
														placeholder="Date of Birth" id="dob5" name="dob5"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<select name="relation5" id="relation5" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f">Father</option>
															<option value="m">Mother</option>
															<option value="s1">Spouse</option>
															<option value="b">Brother</option>
															<option value="s2">Sister</option>
															<option value="s3">Son</option>
															<option value="d">Daughter</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="occupation" id="occupation5"
															value="" class="form-control"
															placeholder="Enter Occupation" />
														<div class="col-md-3"></div>
													</div>
												</div>
											</div>
												<!--/row-->
											<div class="row">
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="name6" value=""
															class="form-control" style="width: 200px;"
															placeholder="Enter Person Name" />
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" class="form-control datepickerclass"
														placeholder="Date of Birth" id="dob6" name="dob6"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault  validation-invalid-label"
														id="error_dob">This field is required.</span>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<select name="relation6" id="relation6" data-rel="chosen"
															style="width: 180px;" class="form-control">
															<option>Please Select</option>
															<option value="f">Father</option>
															<option value="m">Mother</option>
															<option value="s1">Spouse</option>
															<option value="b">Brother</option>
															<option value="s2">Sister</option>
															<option value="s3">Son</option>
															<option value="d">Daughter</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<input type="text" name="occupation6" id="occupation6"
															value="" class="form-control"
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
													<a href="${pageContext.request.contextPath}/showEmpList"><button
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
											id="submitInsertRelationEmp" method="post">
											
											<div class="form-group row">
												<div class="col-lg-6">
													<input type="text" id="empId" name="empId"
														value="${emp.empId}">
												</div>
												<div class="col-lg-6">
													<input type="text" id="empBankId"
														name="empBankId" value="${empBank.bankInfoId}">
												</div>
											</div>
								
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="accNo">Account No: *
													Contact 1 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Account No" id="accNo"
														name="accNo" autocomplete="off" onchange="trim(this)">
													<span class="hidedefault   validation-invalid-label"
														id="error_accNo">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="bankId">Bank :
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="bankId" data-placeholder="Select Bank"
														id="bankId"
														class="form-control form-control-select21 select2-hidden-accessible1">


														<c:forEach items="${bankList}" var="bankList">
															<option value="${bankList.bankId}">${bankList.name}</option>
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
													<a href="${pageContext.request.contextPath}/showEmpList"><button
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
											id="submitInsertEmp" method="post"
											enctype="multipart/form-data">

											
											<div class="form-group row">
												<div class="col-lg-4">
													<input type="text" id="empId" name="empId"
														value="${emp.empId}">
												</div>
												<div class="col-lg-4">
													<input type="text" id="empNomId"
														name="empNomId" value="${empBank.bankInfoId}">
												</div>
												
												<div class="col-lg-4">
													<input type="text" id="empSalAllownaceId"
														name="empSalAllownaceId" value="${empAllowanceId.empSalAllowanceId}">
												</div>
											</div>
											

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="company">Basic
													Rs.<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="Basic Rs."
													 name="basic" id="basic" onchange="trim(this)">												
												</div>
				
												<label class="col-form-label col-lg-2" for="societyContri">Society 
													Contribution Rs. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Society Contribution Rs." id="societyContri" 
														name="societyContri" autocomplete="off" onchange="trim(this)">
												
												</div>
												 
											</div>

 	
										<div class="row">
											 <c:forEach items="${allowanceList}" var="allowanceList">
											 <div class=" col-lg-6">
													<div class="form-group row">
														<label class="col-form-label col-lg-2" for="allownces">${allowanceList.shortName}
														 <span style="color: red"></span>:
														</label>
														<div class="col-lg-10">
															<input type="text" class="form-control"
																placeholder="${allowanceList.name}" id="allownces${allowanceList.allowanceId}"
																name="allownces${allowanceList.allowanceId}"
																autocomplete="off" onchange="trim(this)">
														</div>

													</div>
												</div>
											
											</c:forEach> 
										</div>
											
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="pan">PF Applicable
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="PF Applicable" id="pfApplicable" name="pfApplicable"
														autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="pfNo">PF Type
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="PF Type"
														id="pfType" name="pfType" autocomplete="off"
														onchange="trim(this)"> 
												</div>
											</div>
											
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="pfEmpPer">PF Employee Per
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="PF Employee Per" id="pfEmpPer" name="pfEmpPer"
														autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="pfNo">PF Employer Per
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="PF Employer Per"
														id="pfEmployerPer" name="pfEmployerPer" autocomplete="off"
														onchange="trim(this)"> 
												</div>
											</div>
											
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="esicApplicable">ESIC Applicable
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="ESIC Applicable" id="esicApplicable" name="esicApplicable"
														autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="mlwfApplicable">MLWF Applicable
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="MLWF Applicable"
														id="mlwfApplicable" name="mlwfApplicable" autocomplete="off"
														onchange="trim(this)"> 
												</div>
											</div>
											
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="empEsicPer">Employee Esic Percentage
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Employee Esic Percentage" id="empEsicPer" name="empEsicPer"
														autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="employerEsicPer">Employer Esic Percentage
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="Employer Esic Percentage"
														id="employerEsicPer" name="employerEsicPer" autocomplete="off"
														onchange="trim(this)"> 
												</div>
											</div>
											
											
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="ptApplicable">PT Applicable
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="PT Applicable" id="ptApplicable" name="ptApplicable"
														autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="epfJoinDate">EPF Joining Date
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
												
													 <input type="text" class="form-control datepickerclass" placeholder="EPF Joining Date"
														id="epfJoinDate" name="epfJoinDate" autocomplete="off"
														onchange="trim(this)"> 
												</div>
											</div>
											
											
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="salBasis">Salary Basis
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Salary Basis" id="salBasis" name="salBasis"
														autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="joinDate">Joining Date
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="Joining Date"
														id="joinDate" name="joinDate" autocomplete="off"
														onchange="trim(this)"> 
												</div>
											</div>
											
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="leaveDate">Leaving Date
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Leaving Date" id="leaveDate" name="leaveDate"
														autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="leaveReason">Leaving Reason
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="Leaving Reason"
														id="leaveReason" name="leaveReason" autocomplete="off"
														onchange="trim(this)"> 
												</div>
											</div>
											
												<div class="form-group row">
												<label class="col-form-label col-lg-2" for="salBasis">LR For ESIC
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="LR For ESIC" id="lrEsic" name="lrEsic"
														autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="lrForPF">LR For PF
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="LR For PF"
														id="lrForPF" name="lrForPF" autocomplete="off"
														onchange="trim(this)"> 
												</div>
											</div>
											
											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a href="${pageContext.request.contextPath}/showEmpList"><button
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
											action="${pageContext.request.contextPath}/submitEmpDocInfo"
											id="submitInsertRelationEmp" method="post">
											
											<div class="form-group row">
												<div class="col-lg-6">
													<input type="text" id="empId" name="empId"
														value="${emp.empId}">
												</div>
												<div class="col-lg-6">
													<input type="text" id="empBankId"
														name="empBankId" value="${empBank.bankInfoId}">
												</div>
											</div>
								
											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="accNo">Account No: *
													Contact 1 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Account No" id="accNo"
														name="accNo" autocomplete="off" onchange="trim(this)">
													<span class="hidedefault   validation-invalid-label"
														id="error_accNo">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="bankId">Bank :
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="bankId" data-placeholder="Select Bank"
														id="bankId"
														class="form-control form-control-select21 select2-hidden-accessible1">


														<c:forEach items="${bankList}" var="bankList">
															<option value="${bankList.bankId}">${bankList.name}</option>
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
													<a href="${pageContext.request.contextPath}/showEmpList"><button
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

		/* $('#sbtbtn4').click(function() {

			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/postIssueData",
				data : $("#modalfrm4").serialize(),
				dataType : 'json',
				success : function(data) {

				}
			}).done(function() {
				setTimeout(function() {
				}, 500);
			});
		}); */

		$(document).ready(function($) {

			$("#submitInsertEmp").submit(function(e) {
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

				/* 	if (!$("#desigId").val()) {

						isError = true;

						$("#error_desigId")
								.show()

					} else {
						$("#error_desigId")
								.hide()
					} */

				if (!$("#empCat").val()) {
					alert("6")
					isError = true;

					$("#error_empCat").show()

				} else {
					$("#error_empCat").hide()
				}

				/* if (!$("#empStatus").val()) {

					isError = true;

					$("#error_empStatus").show()

				} else {
					$("#error_empStatus").hide()
				} */

				if (!$("#empType").val()) {

					isError = true;

					$("#error_empType").show()

				} else {
					$("#error_empType").hide()
				}

				if (!$("#mobile1").val()) {

					isError = true;

					$("#error_mobile1").show()

				} else {
					$("#error_mobile1").hide()
				}

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

		$(document)
				.ready(
						function($) {

							$("#submitInsertOtherEmp")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#dob").val()) {

													isError = true;

													$("#error_dob").show()
													//return false;
												} else {
													$("#error_dob").hide()
												}

												if (!isError) {

													var x = true;
													if (x == true) {

														$
																.ajax(
																		{
																			type : "POST",
																			url : "${pageContext.request.contextPath}/submitEmpOtherInfo",
																			data : $(
																					"#submitInsertOtherEmp")
																					.serialize(),
																			dataType : 'json',
																			success : function(
																					data) {

																			}
																		})
																.done(
																		function() {
																			setTimeout(
																					function() {
																					},
																					500);
																		});
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
	</script>
</body>
</html>