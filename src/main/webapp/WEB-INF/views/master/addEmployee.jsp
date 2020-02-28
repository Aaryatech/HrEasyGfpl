<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>
<style type="text/css">
.select2-selection--multiple .select2-selection__rendered{border-bottom:1px solid #ddd;}
</style>

<body>
	<c:url var="getUniqEmpCodeResp" value="/getUniqEmpCodeResp"></c:url>
	<c:url var="getBasicSalCalc" value="/getBasicSalCalc"></c:url>




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
								<h5 class="card-title">
									<c:choose>
										<c:when test="${emp.empId!=0}">Edit Employee</c:when>
										<c:otherwise>Add Employee</c:otherwise>
									</c:choose>
								</h5>
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
								<%-- <c:set  var="empTab" value="<%out.println(session.getAttribute("empTab"));%>"></c:set> --%>
								<!-- Highlighted tabs -->



								<ul class="nav nav-tabs nav-tabs-highlight">


									<li class="nav-item text-center"><a
										href="#highlighted-tab1"
										class="${empTab==1 ? 'nav-link active' : 'nav-link'}"
										data-toggle="tab">Basic Information </a></li>
									<c:if test="${emp.empId!=0}">
										<li class="nav-item text-center"><a
											href="#highlighted-tab2"
											class="${empTab==2 ? 'nav-link active' : 'nav-link'}"
											data-toggle="tab">Personal Information </a></li>


										<li class="nav-item text-center"><a
											href="#highlighted-tab3"
											class="${empTab==3 ? 'nav-link active' : 'nav-link'}"
											data-toggle="tab">Relative Information </a></li>


										<li class="nav-item text-center"><a
											href="#highlighted-tab4"
											class="${empTab==4 ? 'nav-link active' : 'nav-link'}"
											data-toggle="tab">Employee Bank Details </a></li>

										<li class="nav-item text-center"><a
											href="#highlighted-tab5"
											class="${empTab==5 ? 'nav-link active' : 'nav-link'}"
											data-toggle="tab">Employee Salary Details </a></li>
										<li class="nav-item text-center"><a
											href="#highlighted-tab6"
											class="${empTab==6 ? 'nav-link active' : 'nav-link'}"
											data-toggle="tab">Employee Documents </a></li>
										<li class="nav-item text-center"><a
											href="#highlighted-tab7"
											class="${empTab==7 ? 'nav-link active' : 'nav-link'}"
											data-toggle="tab">Login Details </a></li>

									</c:if>


								</ul>

								<div class="tab-content">

									<div
										class="${empTab==1 ? 'tab-pane fade show active' : 'tab-pane fade'}"
										id="highlighted-tab1">

										<form
											action="${pageContext.request.contextPath}/insertEmployeeBasicInfo"
											id="submitInsertEmp" method="post">

											<input type="hidden" id="empId" name="empId"
												value="${emp.empId}">
											<div class="form-group row">
												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="empCode">Emp Code <span class="text-danger">*</span>:
												</label>

												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${emp.empCode}" placeholder="Employee Code."
														id="empCode" name="empCode"
														${emp.empId!=0 ? 'readonly' : ''}
														style="text-transform: uppercase;" autocomplete="off"
														onchange="trim(this)" maxlength="5"> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="unique_user">Employee
														Code Already Exist.</span> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_empCode">This
														field is required.</span>
												</div>

											</div>

											<div class="form-group row">
												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="fname"> Employee Name <span
													class="text-danger">*</span>:
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


												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="subCmpId">Company <span class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="subCmpId" data-placeholder="Select Company"
														id="subCmpId"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="">Select Sub Company</option>
														<c:forEach items="${companySubList}" var="companySubList">
															<c:choose>
																<c:when test="${companySubList.companyId==emp.subCmpId}">
																	<option selected="selected"
																		value="${companySubList.companyId}">${companySubList.companyName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${companySubList.companyId}">${companySubList.companyName}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_subCmpId">This
														field is required.</span>
												</div>

												<%-- <label class="col-form-label col-lg-2" for="company">Company
													: </label>
												<div class="col-lg-4">
													<input type="text" class="form-control "
														readonly="readonly" value="${comp.companyName}">
												</div>
 --%>
												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="locId">Location <span class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="locId" data-placeholder="Select Location"
														id="locId"
														class="form-control form-control-select2 select2-hidden-accessible">

														<option value="">Select Location</option>
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
												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="desigId"> Designation <span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="desigId"
														data-placeholder="Select Designation" id="desigId"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="">Select Designation</option>
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

												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="deptId"> Department <span class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="deptId" data-placeholder="Select Department"
														id="deptId"
														class="form-control form-control-select2 select2-hidden-accessible">

														<option value="">Select Department</option>
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
													<span class="text-danger"></span>:
												</label>
												<div class="col-lg-4">
													<select name="contractor"
														data-placeholder="Select Contractor" id="contractor"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="">Select Contractor</option>
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

												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="empType">Emp Type <span class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="empType"
														data-placeholder="Select Employee Type" id="empType"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="">Select Employee Type</option>
														<c:forEach items="${empTypeList}" var="empTypeList">
															<c:choose>
																<c:when test="${empTypeList.empTypeId==emp.empType}">
																	<option selected="selected"
																		value="${empTypeList.empTypeId}">${empTypeList.name}</option>
																</c:when>
																<c:otherwise>
																	<option value="${empTypeList.empTypeId}">${empTypeList.name}</option>
																</c:otherwise>
															</c:choose>

														</c:forEach>
														<%-- <option value="1" ${emp.empType==1 ? selected : ''}>Weekly
															Co Off</option>
														<option value="2" ${emp.empType==2 ? selected : ''}>OT
															Applicable</option>
														<option value="3" ${emp.empType==3 ? selected : ''}>Other</option> --%>
													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_empType">This
														field is required.</span>
												</div>
											</div>



											<div class="form-group row">
												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="mobile1">Contact No. <span class="text-danger">*</span>:
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

												<label class="col-form-label col-lg-2" for="mobile2">Other
													Mobile No. : </label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${emp.mobileNo2}" placeholder="Other Mobile No."
														id="mobile2" name="mobile2" autocomplete="off"
														onchange="trim(this)" maxlength="10"> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_mobile2">This
														field is required.</span>
												</div>

											</div>

											<div class="form-group row">

												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="empCat">Emp Category (Access Role) <span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">

													<select name="empCat"
														data-placeholder="Select Emp Category" id="empCat"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="">Select Category</option>
														<c:forEach items="${accessRoleList}" var="accessRoleList">
															<c:choose>
																<c:when
																	test="${accessRoleList.empTypeId==emp.empCategory}">
																	<option selected="selected"
																		value="${accessRoleList.empTypeId}">${accessRoleList.empTypeName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${accessRoleList.empTypeId}">${accessRoleList.empTypeName}</option>
																</c:otherwise>
															</c:choose>

														</c:forEach>

													</select> <span class="hidedefault   validation-invalid-label"
														id="error_empCat" style="display: none;">This field
														is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="uan">UAN
													No. : </label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="UAN"
														maxlength="12" id="uan" name="uan" autocomplete="off"
														value="${emp.uan}" onchange="trim(this)"><span
														class="hidedefault   validation-invalid-label"
														id="error_uan" style="display: none;">This field is
														required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="esic">ESIC
													No. : </label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${emp.esicNo}" placeholder="ESIC No." id="esic"
														maxlength="17" name="esic" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_esic" style="display: none;">This field
														is required.</span>
												</div>


												<label class="col-form-label col-lg-2" for="aadhar">Aadhaar
													No. : </label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${emp.aadharNo}" placeholder="Aadhar Card No."
														id="aadhar" maxlength="12" name="aadhar"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_aadhar" style="display: none;">Enter
														Valid Aadhaar Number </span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="pan">PAN
													No. : </label>
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
													No. : </label>
												<div class="col-lg-4">
													<input type="text" class="form-control" placeholder="PF No"
														id="pfNo" name="pfNo" autocomplete="off" maxlength="20"
														value="${emp.pfNo}" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_pfNo" style="display: none;">This field
														is required.</span>
												</div>
											</div>


											<div class="form-group row">

												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="locId_list"> Accessible Location <span
													class="text-danger">*</span>:
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

												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="ishod"> Designation Type <span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="ishod" id="ishod"
														class="form-control form-control-select2 select2-hidden-accessible"
														onchange="opencloseDepthodDrop(this.value)">
														<option value="99" ${emp.exInt1==99 ? 'selected' : ''}>Please
															Select</option>
														<option value="0" ${emp.exInt1==0 ? 'selected' : ''}>Employee</option>
														<option value="1" ${emp.exInt1==1 ? 'selected' : ''}>HOD</option>
														<option value="2" ${emp.exInt1==2 ? 'selected' : ''}>HR</option>


													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_ishod">This field
														is required.</span>
												</div>

											</div>
											<div id="hodDeptDiv" style="display: none;">
												<div class="form-group row">

													<label class="col-form-label col-lg-2" for="hoddeptId">
														Department : </label>
													<div class="col-lg-4">
														<select name="hoddeptId"
															data-placeholder="Select Department" id="hoddeptId"
															multiple="multiple"
															class="form-control form-control-sm select" data-fouc>
															<c:forEach items="${deptList}" var="deptList">

																<c:set value="0" var="find"></c:set>
																<c:forEach items="${hodDeptIds}" var="hodDeptIds">
																	<c:if test="${deptList.departId==hodDeptIds}">
																		<option selected="selected"
																			value="${deptList.departId}">${deptList.name}</option>
																		<c:set value="1" var="find"></c:set>
																	</c:if>
																</c:forEach>
																<c:if test="${find==0}">
																	<option value="${deptList.departId}">${deptList.name}</option>
																</c:if>
															</c:forEach>
														</select> <span class="hidedefault   validation-invalid-label"
															style="display: none;" id="error_hoddeptId">This
															field is required.</span>
													</div>
												</div>
											</div>



											<div class="form-group row">

												<!-- <label
												class="col-form-label text-info font-weight-bold col-lg-2"
												for="plCalcBase">Basic Day <span class="text-danger">*</span>:
											</label>
											<div class="col-lg-4"> -->
												<input type="hidden" class="form-control"
													placeholder="Basic Day" id="plCalcBase" name="plCalcBase"
													autocomplete="off" value="24" onchange="trim(this)">
												<span class="hidedefault   validation-invalid-label"
													id="error_plCalcBase" style="display: none;">This
													field is required.</span>
												<!-- 	</div> -->
											</div>


											<!-- <div class="form-group text-center">
												<div class="col-lg-12">
													<button type="reset" class="btn btn-light legitRipple">Reset</button>

													<button type="button"
														class=" btn btn-info next   btn_go_next_tab "
														id="btn_go_next_tab">
														Next Step <i class="icon-arrow-right8 ml-2 "></i>
													</button>
												</div>
											</div> -->
											<div class="form-group text-center">
												<div class="col-lg-12">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-light">Back</button></a> <input
														type="hidden" id="mobile1Exist" name="mobile1Exist"><input
														type="hidden" id="emailExist" name="emailExist">
												</div>
											</div>
										</form>
									</div>
									<!-- ********************************************Step Two********************************************** -->
									<div
										class="${empTab==2 ? 'tab-pane fade show active' : 'tab-pane fade'}"
										id="highlighted-tab2">

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
												<label class="col-form-label col-lg-2" for="midname">Middle
													Name : </label>
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
													Relation : </label>
												<div class="col-lg-4">
													<select name="relation" data-placeholder="Select Relation"
														id="relation"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="0"
															${empPersInfo.middleNameRelation=='0' ? 'selected' : ''}>Please
															Select</option>
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
												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="dob">Date of Birth <span class="text-danger">*</span>:
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
													: </label>
												<div class="col-lg-4">
													<select name="gender" data-placeholder="Gender" id="gender"
														class="form-control form-control-select2 select2-hidden-accessible">
														<!-- <option>Select Gender</option> -->


														<option value="0"
															${empPersInfo.gender=='0' ? 'selected' : ''}>Please
															Select</option>
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
													Status : </label>
												<div class="col-lg-4">
													<select name="maritalstatus"
														data-placeholder="Select Marital Status"
														id="maritalstatus"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="0"
															${empPersInfo.maritalStatus=='0' ? 'selected' : ''}>Please
															Select</option>
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
													: </label>
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
												<label class="col-form-label col-lg-2" for="address">Current
													Address : </label>
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
													Address : </label>
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
													: </label>
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
													: </label>
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
													Contact 1 : </label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${empPersInfo.emerContactNo1}"
														placeholder="Emergency Contact No. 1" id="contact1"
														maxlength="10" name="contact1" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_contact1" style="display: none;">Enter
														Valid Contact Number.</span>
												</div>

												<label class="col-form-label col-lg-2" for="contact2">Emergency
													Contact 2 : </label>
												<div class="col-lg-4">

													<input type="text" class="form-control numbersOnly"
														value="${empPersInfo.emerContactNo2}"
														placeholder="Emergency Contact No. 2" id="contact2"
														maxlength="10" name="contact2" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														id="error_contact2" style="display: none;">Enter
														Valid Contact Number.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2"
													for="emergencyPersonAddress">Emergency Person
													Address : </label>
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
													Group : </label>
												<div class="col-lg-4">
													<select name="bloodgroup"
														data-placeholder="Select Blood Group" id="bloodgroup"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="0"
															${empPersInfo.bloodGroup=='0' ? 'selected' : ''}>Please
															Select</option>
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
													Size : </label>
												<div class="col-lg-4">
													<select name="uniformsize"
														data-placeholder="Select Uniform Size" id="uniformsize"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="0"
															${empPersInfo.uniformSize=='0' ? 'selected' : ''}>Please
															Select</option>
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

											<!-- <div class="form-group text-center">
												<div class="col-lg-12">
													<button type="reset" class="btn btn-light legitRipple">Reset</button>
													<button type="button"
														class=" btn btn-info prev text-center  btn_go_prev_tab "
														id="btn_go_prev_tab2">
														<i class="icon-arrow-left8  mr-2 "></i> Previous Step
													</button>
													<button type="button"
														class=" btn btn-info next   btn_go_next_tab "
														id="btn_go_next_tab">
														Next Step <i class="icon-arrow-right8 ml-2 "></i>
													</button>
												</div>
											</div> -->

											<div class="form-group text-center">
												<div class="col-lg-12">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->

													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-light">Back</button></a> <input
														type="hidden" id="mobile1Exist" name="mobile1Exist"><input
														type="hidden" id="emailExist" name="emailExist">

												</div>
											</div>
										</form>

									</div>
									<!--***************************************** tab 3 *************************************-->
									<div
										class="${empTab==3 ? 'tab-pane fade show active' : 'tab-pane fade'}"
										id="highlighted-tab3">


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

											<!-- <div class="form-group text-center">
												<div class="col-lg-12">
													<button type="reset" class="btn btn-light legitRipple">Reset</button>
													<button type="button"
														class=" btn btn-info prev text-center  btn_go_prev_tab "
														id="btn_go_prev_tab2">
														<i class="icon-arrow-left8  mr-2 "></i> Previous Step
													</button>
													<button type="button"
														class=" btn btn-info next   btn_go_next_tab "
														id="btn_go_next_tab">
														Next Step <i class="icon-arrow-right8 ml-2 "></i>
													</button>
												</div>
											</div> -->
											<div class="form-group text-center">
												<div class="col-lg-12">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-light">Back</button></a> <input
														type="hidden" id="mobile1Exist" name="mobile1Exist"><input
														type="hidden" id="emailExist" name="emailExist">

												</div>
											</div>
										</form>
									</div>
									<!-- *****************************************Tab 4******************************************* -->
									<div
										class="${empTab==4 ? 'tab-pane fade show active' : 'tab-pane fade'}"
										id="highlighted-tab4">

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
												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="accNo">Account No <span class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${empBank.accNo}" placeholder="Account No"
														id="accNo" maxlength="17" name="accNo" autocomplete="off"
														onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_accNo">Enter Valid
														Bank Account Number.</span>

												</div>



												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="bankId">Bank <span class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="bankId" data-placeholder="Select Bank"
														id="bankId"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="0" ${empBank.bankId=='0' ? 'selected' : ''}>Please
															Select</option>

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
													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_bankId">This Field
														is Required</span>
												</div>
											</div>

											<!-- <div class="form-group text-center">
												<div class="col-lg-12">
													<button type="reset" class="btn btn-light legitRipple">Reset</button>
													<button type="button"
														class=" btn btn-info prev text-center  btn_go_prev_tab "
														id="btn_go_prev_tab2">
														<i class="icon-arrow-left8  mr-2 "></i> Previous Step
													</button>
													<button type="button"
														class=" btn btn-info next   btn_go_next_tab "
														id="btn_go_next_tab">
														Next Step <i class="icon-arrow-right8 ml-2 "></i>
													</button>
												</div>
											</div> -->
											<div class="form-group text-center">
												<div class="col-lg-12">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtnB">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-light">Back</button></a> <input
														type="hidden" id="mobile1Exist" name="mobile1Exist"><input
														type="hidden" id="emailExist" name="emailExist">

												</div>
											</div>
										</form>
									</div>

									<!--********************************* Tab 5 *********************************-->
									<div
										class="${empTab==5 ? 'tab-pane fade show active' : 'tab-pane fade'}"
										id="highlighted-tab5">


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
														value="${empAllowanceId.salaryInfoId}"> <input
														type="hidden" id="salaryTypeId" name="salaryTypeId"
														value="${empAllowanceId.salaryTypeId}">
												</div>
											</div>


											<div class="form-group row">

												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="grossSal">Gross Salary Rs. <span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${empAllowanceId.grossSalary}"
														placeholder="Gross Salary Rs." name="grossSal"
														id="grossSal" onchange="calAllValues(this.value)">
													<span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_grossSal">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="basic">Basic Rs. <span class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly" readonly
														placeholder="Basic Rs." name="basic" id="basic"  
														onchange="trim(this)" value="${empAllowanceId.basic}">
													<!-- <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_salBasis">This
														field is required.</span> -->
												</div>

												<label class="col-form-label col-lg-2" for="societyContri">Society
													Contribution Rs. :</label>
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

														</c:choose>
													</c:forEach>
													<div class=" col-lg-6">
														<div class="form-group row">
															<label class="col-form-label col-lg-2"
																for="allownces${allowanceList.allowanceId}">${allowanceList.shortName}
																(${allowanceList.grossSalPer} %): </label>
															<div class="col-lg-10">
																<input type="text" class="form-control numbersOnly"
																	value="${allowanceValue}"
																	id="allowncesVal${allowanceList.allowanceId}"
																	name="allowncesVal${allowanceList.allowanceId}"
																	autocomplete="off" onchange="trim(this)"> <input
																	type="hidden"
																	id="empSalAllownaceId${allowanceList.allowanceId}"
																	name="empSalAllownaceId${allowanceList.allowanceId}"
																	value="${empSalAlwncId}">


																<%-- Per:<input type="text" class="form-control numbersOnly"
																	value="${allowanceValue}"
																	placeholder="${allowanceList.name}"
																	id="allownces${allowanceList.allowanceId}"
																	name="allownces${allowanceList.allowanceId}"
																	autocomplete="off" onchange="trim(this)">Val: --%>

															</div>

														</div>
													</div>

												</c:forEach>
											</div>

											<div class="form-group row">
												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="pfApplicable">PF Applicable <span
													class="text-danger">*</span>:
												</label>

												<div class="col-lg-4">
													<select name="pfApplicable" id="pfApplicable"
														data-rel="chosen" onchange="setDate()"
														class="form-control">
														<option value="0"
															${empAllowanceId.pfApplicable == '0' ? 'selected' : ''}>Please
															Select</option>

														<option value="no"
															${empAllowanceId.pfApplicable == 'no' ? 'selected' : ''}>NO</option>
														<option value="yes"
															${empAllowanceId.pfApplicable == 'yes' ? 'selected' : ''}>YES</option>

													</select> <span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_pfApplicable">This
														field is required.</span>
												</div>

												<label
													class="pf_block col-form-label text-info font-weight-bold col-lg-2"
													for="pfType">PF Type <span class="text-danger">*</span>:
												</label>
												<div class=" pf_block col-lg-4">
													<select name="pfType" id="pfType" data-rel="chosen"
														class="form-control">
														<option value="0"
															${empAllowanceId.pfType == '0' ? 'selected' : ''}>Please
															Select</option>
														<option value="statutory"
															${empAllowanceId.pfType == 'statutory' ? 'selected' : ''}>statutory</option>
														<option value="voluntary"
															${empAllowanceId.pfType == 'voluntary' ? 'selected' : ''}>voluntary</option>

													</select> <span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_pfType">This field
														is required.</span>
												</div>

											</div>
											<div id="pf_block" class="pf_block" style="display: none;">


												<div class="form-group row">
													<label
														class="col-form-label text-info font-weight-bold col-lg-2"
														for="pfEmpPer">PF Employee Per <span
														class="text-danger">*</span>:
													</label>
													<div class="col-lg-4">
														<input type="text" class="form-control numbersOnly"
															value="${empAllowanceId.pfEmpPer}"
															placeholder="PF Employee Per" id="pfEmpPer"
															name="pfEmpPer" autocomplete="off" onchange="trim(this)">
														<span class="hidedefault  validation-invalid-label"
															style="display: none;" id="error_pfEmpPer">This
															field is required.</span>
													</div>

													<%-- <label
														class="col-form-label text-info font-weight-bold col-lg-2"
														for="pfEmployerPer">PF Employer Per <span
														class="text-danger">*</span>:
													</label>
													<div class="col-lg-4">
														<input type="text" class="form-control numbersOnly"
															placeholder="PF Employer Per" id="pfEmployerPer"
															name="pfEmployerPer" autocomplete="off"
															onchange="trim(this)"
															value="${empAllowanceId.pfEmplrPer}"> <span
															class="hidedefault  validation-invalid-label"
															style="display: none;" id="error_pfEmployerPer">This
															field is required.</span>
													</div> --%>


													<input type="hidden" id="pfEmployerPer"
														name="pfEmployerPer" value="0.0">
												</div>

											</div>

											<div class="form-group row">
												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="esicApplicable">ESIC Applicable <span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">

													<select name="esicApplicable" id="esicApplicable"
														onchange="setDateEsic()" data-rel="chosen"
														class="form-control">
														<option value="0"
															${empAllowanceId.pfApplicable == '0' ? 'selected' : ''}>Please
															Select</option>
														<option value="no"
															${empAllowanceId.esicApplicable == 'no' ? 'selected' : ''}>NO</option>
														<option value="yes"
															${empAllowanceId.esicApplicable == 'yes' ? 'selected' : ''}>YES</option>

													</select> <span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_esicApplicable">This
														field is required.</span>
												</div>

												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="mlwfApplicable">MLWF Applicable <span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">

													<select name="mlwfApplicable" id="mlwfApplicable"
														data-rel="chosen" class="form-control">
														<option value="0"
															${empAllowanceId.mlwfApplicable == '0' ? 'selected' : ''}>Please
															Select</option>
														<option value="no"
															${empAllowanceId.mlwfApplicable == 'no' ? 'selected' : ''}>NO</option>
														<option value="yes"
															${empAllowanceId.mlwfApplicable == 'yes' ? 'selected' : ''}>YES</option>

													</select> <span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_mlwfApplicable">This
														field is required.</span>
												</div>
											</div>
											<%-- <div id="esic_block" style="display: none;">



												<div class="form-group row">
													<label
														class="col-form-label text-info font-weight-bold col-lg-2"
														for="empEsicPer">Employee Esic Percentage <span
														class="text-danger">*</span>:
													</label>
													<div class="col-lg-4">
														<input type="text" class="form-control numbersOnly"
															value="${empAllowanceId.employeeEsicPercentage}"
															placeholder="Employee Esic Percentage" id="empEsicPer"
															name="empEsicPer" autocomplete="off"
															onchange="trim(this)"> <span
															class="hidedefault  validation-invalid-label"
															style="display: none;" id="error_empEsicPer">This
															field is required.</span>
													</div>

													<label
														class="col-form-label text-info font-weight-bold col-lg-2"
														for="employerEsicPer">Employer Esic Percentage <span
														class="text-danger">*</span>:
													</label>
													<div class="col-lg-4">
														<input type="text" class="form-control numbersOnly"
															placeholder="Employer Esic Percentage"
															id="employerEsicPer" name="employerEsicPer"
															autocomplete="off" onchange="trim(this)"
															value="${empAllowanceId.employerEsicPercentage}">
														<span class="hidedefault  validation-invalid-label"
															style="display: none;" id="error_employerEsicPer">This
															field is required.</span>
													</div>
												</div>
											</div> --%>


											<input type="hidden" value="0" id="empEsicPer"
												name="empEsicPer"> <input type="hidden"
												id="employerEsicPer" name="employerEsicPer" value="0">



											<div class="form-group row">

												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="ptApplicable">PT Applicable <span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="ptApplicable" id="ptApplicable"
														data-rel="chosen" class="form-control">
														<option value="0"
															${empAllowanceId.ptApplicable == '0' ? 'selected' : ''}>Please
															Select</option>
														<option value="no"
															${empAllowanceId.ptApplicable == 'no' ? 'selected' : ''}>NO</option>
														<option value="yes"
															${empAllowanceId.ptApplicable == 'yes' ? 'selected' : ''}>YES</option>


													</select> <span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptApplicable">This
														field is required.</span>
												</div>



												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="salBasis">Salary Basis <span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<select name="salBasis" data-placeholder="Select "
														id="salBasis"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="0"
															${empAllowanceId.salBasis == '0' ? 'selected' : ''}>Please
															Select</option>
														<option value="monthly"
															${empAllowanceId.salBasis=='monthly' ? 'selected' : ''}>Monthly</option>
														<option value="daily"
															${empAllowanceId.salBasis=='daily' ? 'selected' : ''}>Daily</option>
													</select> <span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_salBasis1">This
														field is required.</span>
												</div>

											</div>


											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="epfJoinDate">EPF
													Joining Date : </label>
												<div class="col-lg-4">

													<input type="text" class="form-control datepickerclass"
														placeholder="EPF Joining Date" id="epfJoinDate"
														name="epfJoinDate" autocomplete="off"
														onchange="trim(this)"
														value="${empAllowanceId.epfJoiningDate}">
												</div>



												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="joinDate">Joining Date <span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control datepickerclass"
														placeholder="Joining Date" id="joinDate" name="joinDate"
														autocomplete="off" onchange="trim(this)"
														value="${empAllowanceId.cmpJoiningDate}"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_joinDate">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="leaveDate">Leaving
													Date : </label>
												<div class="col-lg-4">
													<input type="text" class="form-control datepickerclass"
														value="${empAllowanceId.cmpLeavingDate}"
														placeholder="Leaving Date" id="leaveDate" name="leaveDate"
														autocomplete="off" onchange1="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="leaveReason">Leaving
													Reason : </label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														placeholder="Leaving Reason" id="leaveReason"
														name="leaveReason" autocomplete="off"
														onchange="trim(this)"
														value="${empAllowanceId.leavingReason}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="lrEsic">LR
													For ESIC : </label>
												<div class="col-lg-4">
													<select name="lrEsic"
														data-placeholder="Select Uniform Size" id="lrEsic"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="-1"
															${empAllowanceId.leavingReasonEsic == '-1' ? 'selected' : ''}>Please
															Select</option>
														<option value="0"
															${empAllowanceId.leavingReasonEsic=='0' ? 'selected' : ''}>0-Without
															Reason</option>
														<option value="1"
															${empAllowanceId.leavingReasonEsic=='1' ? 'selected' : ''}>1-On
															Leave</option>
														<option value="2"
															${empAllowanceId.leavingReasonEsic=='2' ? 'selected' : ''}>2-Self
															Service</option>
														<option value="3"
															${empAllowanceId.leavingReasonEsic=='3' ? 'selected' : ''}>3-Retired</option>
														<option value="4"
															${empAllowanceId.leavingReasonEsic=='4' ? 'selected' : ''}>4-Out
															of coverage</option>

														<option value="5"
															${empAllowanceId.leavingReasonEsic=='5' ? 'selected' : ''}>5-Expired</option>
														<option value="6"
															${empAllowanceId.leavingReasonEsic=='6' ? 'selected' : ''}>6-Non
															Implemented Area</option>
														<option value="7"
															${empAllowanceId.leavingReasonEsic=='7' ? 'selected' : ''}>7-Compliance
															by immediate Employer</option>
														<option value="8"
															${empAllowanceId.leavingReasonEsic=='8' ? 'selected' : ''}>8-Suspension
															of work</option>
														<option value="9"
															${empAllowanceId.leavingReasonEsic=='9' ? 'selected' : ''}>9-Strike/Lockout</option>

														<option value="10"
															${empAllowanceId.leavingReasonEsic=='10' ? 'selected' : ''}>10-Retrenchment</option>
														<option value="11"
															${empAllowanceId.leavingReasonEsic=='11' ? 'selected' : ''}>11-No
															Work</option>
														<option value="12"
															${empAllowanceId.leavingReasonEsic=='12' ? 'selected' : ''}>12-Does
															not belong to this Employer</option>


													</select>
												</div>


												<label class="col-form-label col-lg-2" for="lrForPF">LR
													For PF : </label>

												<div class="col-lg-4">
													<select name="lrForPF"
														data-placeholder="Select Uniform Size" id="lrForPF"
														class="form-control form-control-select2 select2-hidden-accessible">
														<option value="0"
															${empAllowanceId.leavingReasonPf == '0' ? 'selected' : ''}>Please
															Select</option>

														<option value="1"
															${empAllowanceId.leavingReasonPf=='1' ? 'selected' : ''}>C-Cessation</option>
														<option value="2"
															${empAllowanceId.leavingReasonPf=='2' ? 'selected' : ''}>S-Superannuation</option>
														<option value="3"
															${empAllowanceId.leavingReasonPf=='3' ? 'selected' : ''}>R-Retirement</option>
														<option value="4"
															${empAllowanceId.leavingReasonPf=='4' ? 'selected' : ''}>D-Death
															in Service</option>
														<option value="5"
															${empAllowanceId.leavingReasonPf=='5' ? 'selected' : ''}>P-Permanent
															Disablement</option>

													</select>
												</div>

											</div>


											<!-- div class="form-group text-center">
												<div class="col-lg-12">
													<button type="reset" class="btn btn-light legitRipple">Reset</button>
													<button type="button"
														class=" btn btn-info prev text-center  btn_go_prev_tab "
														id="btn_go_prev_tab2">
														<i class="icon-arrow-left8  mr-2 "></i> Previous Step
													</button>
													<button type="button"
														class=" btn btn-info next   btn_go_next_tab "
														id="btn_go_next_tab">
														Next Step <i class="icon-arrow-right8 ml-2 "></i>
													</button>
												</div>
											</div> -->
											<div class="form-group text-center">
												<div class="col-lg-12">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-light">Back</button></a> <input
														type="hidden" id="mobile1Exist" name="mobile1Exist"><input
														type="hidden" id="emailExist" name="emailExist">

												</div>
											</div>
										</form>
									</div>
									<!-- *****************************************Tab 6******************************************* -->
									<div
										class="${empTab==6 ? 'tab-pane fade show active' : 'tab-pane fade'}"
										id="highlighted-tab6">


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

													<%-- <div class="form-group">
														<label class="control-label col-md-3"><img
															id="blah" src="${imgUrl}${docName}" alt="Emp Photo"
															width="300px" /></label>
														<div class="col-md-2">${empDocList.doctypeName}</div>
														<div class="col-md-7" style="padding-left: 200px">
															<input id="doc${empDocList.doctypeId}" type="file"
																name="doc" class="nocheck" onchange="show(this)">


															<input type="hidden"
																name="docType${empDocList.doctypeId}"
																id="docType${empDocList.doctypeId}"
																value="${empDocList.doctypeId}"> <input
																type="hidden" id="empDocId${empDocList.doctypeId}"
																name="empDocId${empDocList.doctypeId}" value="${docId}">
															(only jpg,png,gif,pdf) <span style="display: none;"
																class="hidedefault  validation-invalid-label"
																id="error_img">Only these file types are accepted
																: jpg,png,gif,pdf</span>


														</div>
													</div> --%>

													<div class="form-group row">
														<label class="col-form-label col-lg-2"
															for="doc${empDocList.doctypeId}">
															${empDocList.doctypeName} </label>
														<div class="col-lg-5">

															<input type="file" class="form-control"
																id="doc${empDocList.doctypeId}" type="file" name="doc"
																accept=".jpg,.png,.gif,.doc,.xls,.pdf"> <span
																class="form-text text-muted">Only
																.jpg,.png,.gif,.doc,.xls,.pdf</span> <span
																class="validation-invalid-label" id="error_doc1"
																style="display: none;">This field is required.</span>



														</div>
														<c:if test="${docName != ''}">
															<div class="col-lg-5">
																<a href="${imgUrl}${docName}" target="_blank"
																	title="Open File"> <i
																	class="far fa-file-alt mr-3 fa-2x"
																	style="color: black;"></i>
																</a>
															</div>
														</c:if>
														<input type="hidden" name="docType${empDocList.doctypeId}"
															id="docType${empDocList.doctypeId}"
															value="${empDocList.doctypeId}"> <input
															type="hidden" id="empDocId${empDocList.doctypeId}"
															name="empDocId${empDocList.doctypeId}" value="${docId}">
														<input type="hidden"
															id="empDocName${empDocList.doctypeId}"
															name="empDocName${empDocList.doctypeId}"
															value="${docName}">
														<!-- <span class="validation-invalid-label"
															id="error_fileName0" style="display: none;">This
															field is required.</span> -->
													</div>




												</div>

											</c:forEach>
											<!-- <div class="form-group text-center">
												<div class="col-lg-12">
													<button type="reset" class="btn btn-light legitRipple">Reset</button>
													<button type="button"
														class=" btn btn-info prev text-center  btn_go_prev_tab "
														id="btn_go_prev_tab2">
														<i class="icon-arrow-left8  mr-2 "></i> Previous Step
													</button>
												</div>
											</div> -->
											<div class="form-group text-center">
												<div class="col-lg-12">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-light">Back</button></a> <input
														type="hidden" id="mobile1Exist" name="mobile1Exist"><input
														type="hidden" id="emailExist" name="emailExist">
												</div>
											</div>
										</form>
									</div>

									<!-- *****************************************Tab 7******************************************* -->
									<div
										class="${empTab==7 ? 'tab-pane fade show active' : 'tab-pane fade'}"
										id="highlighted-tab7">

										<form
											action="${pageContext.request.contextPath}/submitEmpLogDetails"
											id="submitEmpLogDetails" method="post">

											<div class="form-group row">
												<div class="col-lg-6">
													<input type="hidden" id="empId" name="empId"
														value="${emp.empId}">
												</div>
												<div class="col-lg-6">
													<input type="hidden" id="userInfoId" name="userInfoId"
														value="${userRes.user_id}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="accNo">User
													Name <span class="text-danger"></span>:
												</label>
												<div class="col-lg-5">
													<input type="text" class="form-control" readonly="readonly"
														value="${userRes.userName}" id="uname" name="uname"
														autocomplete="off">
												</div>
											</div>

											<div class="form-group row">

												<label
													class="col-form-label text-info font-weight-bold col-lg-2"
													for="upass">Update User Password <span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-5">
													<input type="text" class="form-control"
														placeholder="User Password" id="upass" name="upass"
														autocomplete="off" onchange="trim(this)"> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_upass">This Field
														is Required.</span>
												</div>

											</div>

											<div class="form-group text-center">
												<div class="col-lg-12">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtnUser">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showEmployeeList"><button
															type="button" class="btn btn-light">Back</button></a>

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




	<script type="text/javascript">
		function setDate() {

			var value = document.getElementById("pfApplicable").value;

			if (value == 'yes') {

				$(".pf_block").show()
			} else {

				$(".pf_block").hide()
			}

		}
	</script>


	<script type="text/javascript">
		function setDateEsic() {

			var value = document.getElementById("esicApplicable").value;

			if (value == 'yes') {

				$("#esic_block").show()
			} else {

				$("#esic_block").hide()
			}

		}
	</script>

	<script type="text/javascript">
		function setDateEsicOnload() {

		}
	</script>

	<script type="text/javascript">
		function calAllValues(grossSal) {
	 
		 
			$.getJSON('${getBasicSalCalc}',

			{

				grossSal : grossSal,
				ajax : 'true'

			}, function(data) {

				var x = 0.0;
				$.each(data,
						function(key, dt) {
					
 
							document.getElementById("allowncesVal"
									+ dt.allowanceId).value = dt.exVar1;

							x = parseFloat(x) + parseFloat(dt.exVar1);
								 
						})
						
						
						document.getElementById("basic").value = (parseFloat(grossSal)
						- parseFloat(x)).toFixed(2);

			});
		 

		}
	</script>

	<script type="text/javascript">
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines 
			return;
		}
		$(document).ready(function() {

			var isEsicApplicable = '${empAllowanceId.esicApplicable}';

			var isPfApplicable = '${empAllowanceId.pfApplicable}';

			if (isEsicApplicable == 'yes') {

				$("#esic_block").show()
			} else {

				$("#esic_block").hide()
			}

			if (isPfApplicable == 'yes') {

				$(".pf_block").show()
			} else {

				$(".pf_block").hide()
			}

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

												if (!$("#plCalcBase").val()) {

													isError = true;

													$("#error_plCalcBase")
															.show()

												} else {
													$("#error_plCalcBase")
															.hide()
												}

												if (!$("#subCmpId").val()) {

													isError = true;

													$("#error_subCmpId").show()

												} else {
													$("#error_subCmpId").hide()
												}

												var isVisible = $(
														'#unique_user').is(
														':visible');

												if (isVisible == true) {

													isError = true;
												}
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
												/* if (!$("#mname").val()) {

													isError = true;

													$("#error_mname").show()

												} else {
													$("#error_mname").hide()
												} */
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
												if (!$("#deptId").val()) {

													isError = true;

													$("#error_deptId").show()

												} else {
													$("#error_deptId").hide()
												}

												if ($("#locId_list").val() == "") {

													isError = true;

													$("#error_locId_list")
															.show()

												} else {
													$("#error_locId_list")
															.hide()
												}

												if (!$("#ishod").val()
														|| parseInt($("#ishod")
																.val()) == 99) {

													isError = true;

													$("#error_ishod").show();

												} else {
													$("#error_ishod").hide();
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

												if ($("#mobile2").val().trim() > 0) {
													if (!$("#mobile2").val()
															|| !validateMobile($(
																	"#mobile2")
																	.val())) {

														isError = true;

														$("#error_mobile2")
																.show()

													} else {
														$("#error_mobile2")
																.hide()
													}
												}

												if ($("#aadhar").val().trim() > 0) {
													if (!$("#aadhar").val()
															|| $("#aadhar")
																	.val().length != 12) {

														isError = true;

														$("#error_aadhar")
																.show()

													} else {
														$("#error_aadhar")
																.hide()
													}
												}

												if (validatePAN($("#pan").val())) {

													isError = true;

													$("#error_pan").show()

												} else {
													$("#error_pan").hide()
												}
												if ($("#ishod").val() == 1) {
													if ($("#hoddeptId").val() == "") {
														isError = true;
														$("#error_hoddeptId")
																.show()
													} else {
														$("#error_hoddeptId")
																.hide()
													}
												} else {
													$("#error_hoddeptId")
															.hide()
												}

												if (!$("#desigId").val()) {

													isError = true;

													$("#error_desigId").show()

												} else {
													$("#error_desigId").hide()
												}
												/* 
												 if (!$("#contractor").val()) {

												 isError = true;

												 $("#error_contractor")
												 .show()

												 } else {
												 $("#error_contractor")
												 .hide()
												 } */

												/* if (!$("#ishod").val()) {

													isError = true;

													$("#error_ishod").show()

												} else {
													$("#error_ishod").hide()
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
		/* Bank  */
		$(document).ready(function($) {

			$("#submitEmpBankInfo").submit(function(e) {
				var isError = false;
				var errMsg = "";
				var acc = $("#accNo").val();

				if (!$("#accNo").val() || acc.length<8 || acc.length>17) {

					isError = true;

					$("#error_accNo").show()

				} else {
					$("#error_accNo").hide()
				}

				var bank1 = document.getElementById("bankId").value;

				if (!$("#bankId").val() || parseInt(bank1) == 0) {

					isError = true;

					$("#error_bankId").show()

				} else {
					$("#error_bankId").hide()
				}

				if (!isError) {

					var x = true;
					if (x == true) {

						document.getElementById("submtbtnB").disabled = true;
						return true;
					}
					//
				}
				return false;
			});
		});
		/* Employee Salary */
		$(document)
				.ready(
						function($) {

							$("#insertEmployeeAllowancesInfo")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												/* if (!$("#basic").val()
														|| parseFloat($(
																"#basic").val()) <= 0) {

													isError = true;

													$("#error_salBasis").show()
													//return false;
												} else {
													$("#error_salBasis").hide()
												}
												 */
												
												if (!$("#grossSal").val()
														|| parseFloat($(
																"#grossSal").val()) <= 0) {

													isError = true;

													$("#error_grossSal").show()
													//return false;
												} else {
													$("#error_grossSal").hide()
												}
												
												
												

												var x = document
														.getElementById("pfApplicable").value;

												if (!$("#pfApplicable").val()
														|| parseInt(x) == 0) {

													isError = true;

													$("#error_pfApplicable")
															.show()
													//return false;
												} else {
													$("#error_pfApplicable")
															.hide()
												}

												if ($("#pfApplicable").val() == 'yes') {

													var a = document
															.getElementById("pfType").value;

													if (!$("#pfType").val()
															|| parseInt(a) == 0) {

														isError = true;

														$("#error_pfType")
																.show()
														//return false;
													} else {
														$("#error_pfType")
																.hide()
													}

													if (!$("#pfEmpPer").val()
															|| parseFloat($(
																	"#pfEmpPer")
																	.val()) <= 0) {

														isError = true;

														$("#error_pfEmpPer")
																.show()
														//return false;
													} else {
														$("#error_pfEmpPer")
																.hide()
													}

													/* if (!$("#pfEmployerPer")
															.val()
															|| parseFloat($(
																	"#pfEmployerPer")
																	.val()) <= 0) {

														isError = true;

														$(
																"#error_pfEmployerPer")
																.show()
														//return false;
													} else {
														$(
																"#error_pfEmployerPer")
																.hide()
													} */
												}
												var y = document
														.getElementById("esicApplicable").value;

												if (!$("#esicApplicable").val()
														|| parseInt(y) == 0) {

													isError = true;

													$("#error_esicApplicable")
															.show()
													//return false;
												} else {
													$("#error_esicApplicable")
															.hide()
												}

												/* 	if ($("#esicApplicable").val() == 'yes') {

														if (!$("#empEsicPer").val()
																|| parseFloat($(
																		"#empEsicPer")
																		.val()) <= 0) {

															isError = true;

															$("#error_empEsicPer")
																	.show()
															//return false;
														} else {
															$("#error_empEsicPer")
																	.hide()
														}

														if (!$("#employerEsicPer")
																.val()
																|| parseFloat($(
																		"#employerEsicPer")
																		.val()) <= 0) {

															isError = true;

															$(
																	"#error_employerEsicPer")
																	.show()
															//return false;
														} else {
															$(
																	"#error_employerEsicPer")
																	.hide()
														}

													} */

												var b = document
														.getElementById("mlwfApplicable").value;

												if (!$("#mlwfApplicable").val()
														|| parseInt(b) == 0) {

													isError = true;

													$("#error_mlwfApplicable")
															.show()
													//return false;
												} else {
													$("#error_mlwfApplicable")
															.hide()
												}

												var c = document
														.getElementById("ptApplicable").value;

												if (!$("#ptApplicable").val()
														|| parseInt(c) == 0) {

													isError = true;

													$("#error_ptApplicable")
															.show()
													//return false;
												} else {
													$("#error_ptApplicable")
															.hide()
												}

												var j = document
														.getElementById("salBasis").value;

												if (!$("#salBasis").val()
														|| parseInt(j) == 0) {

													isError = true;

													$("#error_salBasis1")
															.show()
													//return false;
												} else {
													$("#error_salBasis1")
															.hide()
												}

												if (!$("#joinDate").val()) {

													isError = true;

													$("#error_joinDate").show()
													//return false;
												} else {
													$("#error_joinDate").hide()
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

				if ($("#contact2").val().length > 0) {
					if (!validateMobile($("#contact2").val())) {
						//alert($("#email").val());
						isError = true;
						$("#error_contact2").show()

					} else {
						$("#error_contact2").hide()
					}
				}

				if ($("#contact1").val().length > 0) {
					if (!validateMobile($("#contact1").val())) {
						//alert($("#email").val());
						isError = true;
						$("#error_contact1").show()

					} else {
						$("#error_contact1").hide()
					}
				}

				if ($("#email").val().length > 0) {
					if (!validateEmail($("#email").val())) {
						//alert($("#email").val());
						isError = true;
						$("#error_persnlInfoEmail").show()

					} else {
						$("#error_persnlInfoEmail").hide()
					}
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

		//************Personal Info****************//

		$(document)
				.ready(
						function($) {

							$("#submitEmpLogDetails")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#upass").val()) {

													isError = true;

													$("#error_upass").show()

												} else {
													$("#error_upass").hide()
												}

												if (!isError) {

													var x = true;
													if (x == true) {

														document
																.getElementById("submtbtnUser").disabled = true;
														return true;
													}
													//
												}
												return false;
											});
						});
	</script>

	<script>
		function validateEmail(email) {

			//	alert(111);

			var eml = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

			if (eml.test($.trim(email)) == false) {

				return false;

			}

			return true;

		}
		function validateMobile(mobile) {
			var mob = /^[1-9]{1}[0-9]{9}$/;

			if (mob.test($.trim(mobile)) == false) {

				//alert("Please enter a valid email address .");
				return false;

			}
			return true;

		}
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
		$(document).ready(
				function($) {

					//btn_go_next_tab
					$(".btn_go_next_tab").click(
							function(e) {
								$('.nav-tabs > .nav-item > .active').parent()
										.next('li').find('a').trigger('click');

							});
					$(".btn_go_prev_tab").click(
							function(e) {
								$('.nav-tabs > .nav-item > .active').parent()
										.prev('li').find('a').trigger('click');

							});

				});
		// Single picker
		/* $('#leaveDate').daterangepicker({
		    "autoUpdateInput": false,
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		}, function (start_date) {
		    $('#leaveDate').val(start_date.format('DD-MM-YYYY'));
		}); */
		$('.datepickerclass').daterangepicker({
			"autoUpdateInput" : false,
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		}, function(start_date) {
			$(this.element).val(start_date.format('DD-MM-YYYY'));
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

		function opencloseDepthodDrop(value) {

			//alert(value);
			if (value == 1) {
				document.getElementById("hodDeptDiv").style.display = "block";

			} else {
				document.getElementById("hodDeptDiv").style.display = "none";

			}
		}
	</script>
</body>
</html>