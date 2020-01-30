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
										<td width="60%"><h5 class="card-title">Company
												Details</h5></td>
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
										data-toggle="tab">Company Information </br>Step 1
									</a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab2" class="nav-link" data-toggle="tab">Company
											Logo </br>Step 2
									</a></a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab3" class="nav-link" data-toggle="tab">Other
											Information </br>Step 3
									</a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab4" class="nav-link" data-toggle="tab">Bank
											Details </br>Step 4
									</a></li>

									<li class="nav-item text-center"><a
										href="#highlighted-tab5" class="nav-link" data-toggle="tab">Manager
											Details</br>Step 5
									</a></li>
									<!-- <li class="nav-item text-center"><a
										href="#highlighted-tab6" class="nav-link" data-toggle="tab">Employee Documents
											</br>Step 6
									</a></li> -->

								</ul>

								<div class="tab-content">
									<div class="tab-pane fade show active" id="highlighted-tab1">

										<form
											action="${pageContext.request.contextPath}/insertCompanyInfo"
											id="insertCompanyInfo" method="post">

											<input type="hidden" id="companyId" name="companyId"
												value="${company.companyId}">

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="companyName">Company
													Name<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.companyName}" id="companyName"
														onchange="trim(this)" placeholder="Company Name"
														name="companyName" autocomplete="off"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_company">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2"
													for="companyShortName">Company Short Name<span
													style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.nameSd}" id="companyShortName"
														onchange="trim(this)" placeholder="Company Short Name"
														name="companyShortName" autocomplete="off"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_companyShortName">This
														field is required.</span>
												</div>
											</div>


											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="companyAddress1">Address1
													<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.longAdd1}" id="companyAddress1"
														onchange="trim(this)" placeholder="Address1"
														name="companyAddress1" autocomplete="off"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_companyAddress1">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="companyAddress2">Address2
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.longAdd2}" id="companyAddress2"
														onchange="trim(this)" placeholder="Address2"
														name="companyAddress2" autocomplete="off">
												</div>


											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="companyAddress3">Address3
													<span style="color: red"> </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.longAdd3}" id="companyAddress3"
														onchange="trim(this)" placeholder="Address3"
														name="companyAddress3" autocomplete="off">
												</div>

												<label class="col-form-label col-lg-2" for="empType">Short
													Address<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.shortAddress}" id="shortAddress"
														onchange="trim(this)" placeholder="Short Address"
														name="shortAddress" autocomplete="off">
												</div>
											</div>



											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="landline1">Landine No. 1 <span style="color: red">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${company.landline1}" placeholder="Landline No."
														id="landline1" name="landline1" autocomplete="off"
														onchange="trim(this)" maxlength="10"> <span
														style="display: none;"
														class="hidedefault   validation-invalid-label"
														id="error_landline1">This field is required.</span>
												</div>


												<label class="col-form-label col-lg-2" for="landline2">Landine No. 2 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${company.landline2}"
														placeholder="Other Landline No." id="landline2"
														name="landline2" autocomplete="off" onchange="trim(this)"
														maxlength="10"> <span
														class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_landline2">This
														field is required.</span>
												</div>

											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="fac">FAX
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.faxNo}" placeholder="Fax" id="fax"
														name="fax" autocomplete="off" onchange="trim(this)">
												</div>

												<label class="col-form-label col-lg-2" for="pan">PAN
													No. <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" maxlength="10"
														placeholder="PAN No." id="pan" name="pan"
														value="${company.panNo}" autocomplete="off"
														onchange="trim(this)"><span
														class="hidedefault  validation-invalid-label"
														id="error_pan" style="display: none;">Please enter
														correct PAN No.</span>
												</div>
											</div>

											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showCompanyList"><button
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

									<!--***************************************** tab 2 *************************************-->
									<div class="tab-pane fade" id="highlighted-tab2">
										<form
											action="${pageContext.request.contextPath}/insertCompanyLogo"
											id="insertCompanyLogo" method="post"
											enctype="multipart/form-data">

											<input type="hidden" id="companyId" name="companyId"
												value="${company.companyId}">

											<div class="form-group row">



												<label class="col-form-label col-lg-2" for="pan">Logo
													 <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="file" id="logo" name="logo"
														style="padding-bottom: 8px"
														class="form-control"
														onchange="readURL(this); return Upload(logo)"
														title="Only jpg,png,gif">${company.logo}
												</div>

											</div>
											<div class="form-group col-lg-4">
												<img
													src="http://gfpl.aaryatechindia.in/uploads/steps/0d41EOj5stMETA LOGO2.png"
													height="150px" width="200px">

											</div>


											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showCompanyList"><button
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
									<!-- ********************************************Step 3********************************************** -->
									<div class="tab-pane fade" id="highlighted-tab3">
										

										<form
											action="${pageContext.request.contextPath}/insertCompanyFundsInfo"
											id="insertCompanyFundsInfo" method="post">

											<input type="hidden" id="companyId" name="companyId"
												value="${company.companyId}">

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="taxNo">TAN
													No. <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.tanNo}" id="tanNo" onchange="trim(this)"
														placeholder="TAN No" name="tanNo" autocomplete="off"
														maxlength="10"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_tanNo">This field
														is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="ptNo">PT
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.ptNo}" id="ptNo" autocomplete="off"
														onchange="trim(this)" placeholder="PT No." name="ptNo">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="serviceTaxNo">Service
													Tax No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.serviceTaxNo}" id="serviceTaxNo"
														onchange="trim(this)" placeholder="Service Tax No."
														name="serviceTaxNo" autocomplete="off"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_serviceTaxNo">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="vatNo">VAT
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${company.vatNo}" id="vatNo" autocomplete="off"
														onchange="trim(this)" placeholder="VAT No." name="vatNo">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="cstNo">CST
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.cstNo}" id="cstNo" onchange="trim(this)"
														placeholder="CST No" name="cstNo" autocomplete="off">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_tanNo">This field
														is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="gstNo">GST
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.gstNo}" id="gstNo" autocomplete="off"
														onchange="trim(this)" placeholder="GST No." name="gstNo"
														maxlength="15"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="pf">PF <span
													style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="isPfApplicable"
														data-placeholder="Select Designation" id="isPfApplicable"
														class="form-control form-control-select21 select2-hidden-accessible1">
														<option value="1"
															${company.isPfApplicable==1 ? 'selected' : ''}>Yes</option>
														<option value="0"
															${company.isPfApplicable==0 ? 'selected' : ''}>No</option>
													</select>
												</div>

												<label class="col-form-label col-lg-2" for="pfNo">PF
													No. <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.pfNo}" id="pfNo" autocomplete="off"
														onchange="trim(this)" placeholder="PF No." name="pfNo">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_pfNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="pfCoveregDate">PF
													Coverage Date<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control datepickerclass"
														value="${company.pfCoverageDate}" id="pfCoveregDate"
														onchange="trim(this)" placeholder="PF Coverage Date"
														name="pfCoveregDate" autocomplete="off"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_pfCoveregDate">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="ptNo">PF
													Signatory 1 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.pfSignatory1}" id="pfSignatory1"
														autocomplete="off" onchange="trim(this)"
														placeholder="PF Signatory1" name="pfSignatory1"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="pfSignatory2">PF
													Signatory2 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.pfSignatory2}" id="pfSignatory2"
														onchange="trim(this)" placeholder="PF Signatory2"
														name="pfSignatory2" autocomplete="off"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_tanNo">This field
														is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="pfSignatory3">PF
													Signatory3 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.pfSignatory3}" id="pfSignatory3"
														autocomplete="off" onchange="trim(this)"
														placeholder="PF Signatory3" name="pfSignatory3"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2"
													for="isEsicApplicable">ESIC Applicable <span
													style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<select name="isEsicApplicable"
														data-placeholder="Select Designation"
														id="isEsicApplicable"
														class="form-control form-control-select21 select2-hidden-accessible1">
														<option value="1"
															${company.isEsicApplicable==1 ? 'selected' : ''}>Yes</option>
														<option value="0"
															${company.isEsicApplicable==1 ? 'selected' : ''}>No</option>
													</select>
												</div>

												<label class="col-form-label col-lg-2" for="esicNo">ESIC
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.esicNo}" id="esicNo" autocomplete="off"
														onchange="trim(this)" placeholder="ESIC No." name="esicNo">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2"
													for="esicCoverageDate">ESIC Coverage Date <span
													style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control datepickerclass"
														value="${company.esicCoverageDate}" id="esicCoverageDate"
														 placeholder="PF Signatory2"
														name="esicCoverageDate"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_tanNo">This field
														is required.</span>
												</div>
												
												 
										

												<label class="col-form-label col-lg-2" for="esicSignatory1">ESIC
													Signatory 1 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.esicSignatory1}" id="esicSignatory1"
														autocomplete="off" onchange="trim(this)"
														placeholder="ESIC Signatory1" name="esicSignatory1">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="esicSignatory2">ESIC
													Signatory2 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.esicSignatory2}" id="esicSignatory2"
														onchange="trim(this)" placeholder="ESIC Signatory2"
														name="esicSignatory2" autocomplete="off"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_tanNo">This field
														is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="esicSignatory3">ESIC
													Signatory3 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.esicSignatory3}" id="esicSignatory1"
														autocomplete="off" onchange="trim(this)"
														placeholder="ESIC Signatory3" name="esicSignatory1">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showCompanyList"><button
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
									<!--***************************************** tab 4 *************************************-->
									<div class="tab-pane fade" id="highlighted-tab4">

										<form
											action="${pageContext.request.contextPath}/insertCompanyBankInfo"
											id="insertCompanyBankInfo" method="post">

											<input type="hidden" id="companyId" name="companyId"
												value="${company.companyId}">

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="person">Person
													Name <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.cpName}" id="person"
														onchange="trim(this)" placeholder="Person Name"
														name="person" autocomplete="off"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_person">This field
														is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="designation">Designation
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.cpDesignation}" id="designation"
														autocomplete="off" onchange="trim(this)"
														placeholder="Designation" name="designation"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="mobile">Mobile
													No. <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control numbersOnly"
														value="${company.cpMobile}" id="mobile"
														onchange="trim(this)" placeholder="Mobile No."
														name="mobile" autocomplete="off" maxlength="10"">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_mobile">This field
														is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="designation">Bank
													Account No <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.cmpBankAccount}" id="accno"
														autocomplete="off" onchange="trim(this)"
														placeholder="Bank Account No " name="accno" maxlength="17">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_accNoDigit">TInvalid
														Account No.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="email1">Email
													1 <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.cpEmail1}" id="email1"
														onchange="trim(this)" placeholder="Email 1" name="email1"
														autocomplete="off"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_email1">This field
														is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="email2">Email
													2 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.cpEmail2}" id="email2" autocomplete="off"
														onchange="trim(this)" placeholder="Email 2" name="email2">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showCompanyList"><button
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
									<!-- *****************************************Tab 5******************************************* -->
									<div class="tab-pane fade" id="highlighted-tab5">
										<form
											action="${pageContext.request.contextPath}/insertCompanyManagerInfo"
											id="insertCompanyManagerInfo" method="post">

											<input type="hidden" id="companyId" name="companyId"
												value="${company.companyId}">

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="manager">Managers
													Under Shop Act <span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.managerUnderAct}" id="manager"
														onchange="trim(this)"
														placeholder="Managers Under Shop Act " name="manager"
														autocomplete="off"> <span
														class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_manager">This
														field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="managerAddress">Manager
													Address <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control"
														value="${company.managerAddress}" id="managerAddress"
														autocomplete="off" onchange="trim(this)"
														placeholder="Manager Address" name="managerAddress">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_ptNo">This field
														is required.</span>
												</div>
											</div>
											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showCompanyList"><button
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
									<div class="tab-pane fade" id="highlighted-tab0"></div>
									<!-- *****************************************Tab 0******************************************* -->
									<div class="tab-pane fade" id="highlighted-tab0"></div>


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

			$("#insertCompanyInfo").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#companyName").val()) {

					isError = true;

					$("#error_company").show()
					//return false;
				} else {
					$("#error_company").hide()
				}

				if (!$("#companyShortName").val()) {

					isError = true;

					$("#error_companyShortName").show()

				} else {
					$("#error_companyShortName").hide()
				}
				if (!$("#companyAddress1").val()) {

					isError = true;

					$("#error_companyAddress1").show()

				} else {
					$("#error_companyAddress1").hide()
				}
				if (!$("#landline1").val()) {

					isError = true;

					$("#error_landline1").show()

				} else {
					$("#error_landline1").hide()
				}

				if (!$("#pan").val() || !validatePAN($("#pan").val())) {

					isError = true;

					$("#error_pan").show()

				} else {
					$("#error_pan").hide()
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
		//
		/* 2 Funds */
		$(document).ready(function($) {

			$("#insertCompanyFundsInfo").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#tanNo").val()) {

					isError = true;

					$("#error_tanNo").show()

				} else {
					$("#error_tanNo").hide()
				}

				if (!$("#pfNo").val()) {

					isError = true;

					$("#error_pfNo").show()

				} else {
					$("#error_pfNo").hide()
				}

				if (!$("#pfCoveregDate").val()) {

					isError = true;

					$("#error_pfCoveregDate").show()

				} else {
					$("#error_pfCoveregDate").hide()
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
		/* Bank */
		$(document).ready(function($) {

			$("#insertCompanyBankInfo").submit(function(e) {
				var isError = false;
				var errMsg = "";
				var acc = $("#accno").val();

				if (!$("#person").val()) {

					isError = true;

					$("#error_person").show()
					//return false;
				} else {
					$("#error_person").hide()
				}
				if (!$("#mobile").val()) {

					isError = true;

					$("#error_mobile").show()
					//return false;
				} else {
					$("#error_mobile").hide()
				}
				if (!$("#email1").val() || !validateEmail($("#email1").val())) {

					isError = true;

					$("#error_email1").show()
					//return false;
				} else {
					$("#error_email1").hide()
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

		/* Manager */
		$(document).ready(function($) {

			$("#insertCompanyManagerInfo").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#manager").val()) {

					isError = true;

					$("#error_manager").show()
					//return false;
				} else {
					$("#error_manager").hide()
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

	
	 

	<script type="text/javascript">
		function show(input) {
			debugger;
			var validExtensions = [ 'jpg', 'png', 'jpeg', 'pdf' ]; //array of valid extensions
			var fileName = input.files[0].name;
			var fileNameExt = fileName.substr(fileName.lastIndexOf('.') + 1);
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
		}
	</script>
</body>
</html>