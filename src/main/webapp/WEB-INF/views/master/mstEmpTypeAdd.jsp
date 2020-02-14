<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
						<!-- <div class="mb-3">
							<h6 class="mb-0 font-weight-semibold">Hidden labels</h6>
							<span class="text-muted d-block">Inputs with empty values</span>
						</div> -->
						<!-- /title -->


						<div class="card">


							<div class="card-header header-elements-inline">
								<table width="100%">
									<tr width="100%">
										<td width="60%"><h5 class="card-title">Add Employee
												Type</h5></td>
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

								<form
									action="${pageContext.request.contextPath}/submitMstEmpTypeAdd"
									id="submitInsertWeeklyOff" method="post">
									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="typeName"> Type Name<span class="text-danger">
												* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Type Name" id="typeName" name="typeName"
												autocomplete="off" onchange="trim(this)" maxlength="100">
											<span class="validation-invalid-label" id="error_typeName"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="minWorkRule"> Minimum Work Hour Rule Applicable
											<span class="text-danger"> *</span>:
										</label>
										<div class="col-lg-4">
											<select name="minWorkRule" data-placeholder="Please Select"
												id="minWorkRule"
												class="form-control form-control-select2 select2-hidden-accessible"
												tabindex="-1" aria-hidden="true">
												<option value="">Please Select</option>
												<option value="1">Yes</option>
												<option value="0">No</option>



											</select> <span class="validation-invalid-label"
												id="error_minWorkRule" style="display: none;">This
												field is required.</span>
										</div>

										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="halfDayDed"> Half Day Deduction <span
											class="text-danger"> *</span>:
										</label>
										<div class="col-lg-4">
											<select name="halfDayDed" data-placeholder="Please Select"
												id="halfDayDed"
												class="form-control form-control-select2 select2-hidden-accessible"
												tabindex="-1" aria-hidden="true">
												<option value="">Please Select</option>
												<option value="1">Yes</option>
												<option value="0">No</option>



											</select> <span class="validation-invalid-label" id="error_halfDayDed"
												style="display: none;">This field is required.</span>
										</div>

									</div>
 
									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="otApplicable"> Performance Incentive Applicable <span
											class="text-danger"> *</span>:
										</label>
										<div class="col-lg-4">
											<select name="otApplicable" data-placeholder="Please Select"
												id="otApplicable" onchange="setDate()"
												class="form-control form-control-select2 select2-hidden-accessible"
												tabindex="-1" aria-hidden="true">
												<option value="">Please Select</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>



											</select> <span class="validation-invalid-label"
												id="error_otApplicable" style="display: none;">This
												field is required.</span>
										</div>
									</div>
									<div id="abc" style="display: none;">
										<div class="form-group row">

											<label
												class="col-form-label text-info font-weight-bold col-lg-2"
												for="otType"> Performance Incentive Type<span class="text-danger">
													* </span>:
											</label>
											<div class="col-lg-4">
												<select name="otType" data-placeholder="Please Select"
													id="otType"
													class="form-control form-control-select2 select2-hidden-accessible"
													tabindex="-1" aria-hidden="true">
													<option value="">Please Select</option>
													<option value="0">No</option>
													<option value="1">1 HR of Gross Salary x 1</option>
													<option value="1.5">1 HR of Gross Salary x 1.5</option>
													<option value="2">1 HR of Gross Salary x 2</option>

												</select> <span class="validation-invalid-label" id="error_otType"
													style="display: none;">This field is required.</span>
											</div>
										</div>
									</div>


									<div class="form-group row">
									
										<label
												class="col-form-label text-info font-weight-bold col-lg-2"
												for="prodApplicable">Production Incentive Applicable<span
												class="text-danger"> *</span>:
											</label>
											<div class="col-lg-4">
												<select name="prodApplicable"
													data-placeholder="Please Select" id="prodApplicable"
													onchange="setDate()"
													class="form-control form-control-select2 select2-hidden-accessible"
													tabindex="-1" aria-hidden="true">
													<option value="">Please Select</option>
													<option value="1">Yes</option>
													<option value="0">No</option>



												</select> <span class="validation-invalid-label"
													id="error_prodApplicable" style="display: none;">This
													field is required.</span>
											</div>
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="weekOffWork"> Weekly off Holiday Work <span
											class="text-danger"> * </span>:
										</label>
										<div class="col-lg-4">
											<select name="weekOffWork" data-placeholder="Please Select"
												id="weekOffWork"
												class="form-control form-control-select2 select2-hidden-accessible"
												tabindex="-1" aria-hidden="true">
												<option value="">Please Select</option>
												<option value="Compoff">Compoff</option>
												<option value="OT">OT</option>
												<option value="NA">NA</option>

											</select> <span class="validation-invalid-label"
												id="error_weekOffWork" style="display: none;">This
												field is required.</span>
										</div>
									 
										
										</div>

 									<input type="hidden" id="minHr" name="minHr" value="0">


									<div class="form-group row">


										<!-- 	<label class="col-form-label text-info font-weight-bold col-lg-2" for="minHr">
											Minimum Work Hour(in Min)<span class="text-danger">* </span>:
										</label> 
										<div class="col-lg-4">
											<input type="hidden" class="form-control numbersOnly"
												placeholder="Enter Min Hours" id="minHr" name="minHr" value="0"
												autocomplete="off" onchange="trim(this)" maxlength="20">
											<span class="validation-invalid-label" id="error_minHr"
												style="display: none;">This field is required.</span>
										</div>
										 
-->
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="lateMark"> Late Mark Entry<span
											class="text-danger"> * </span>:
										</label>
										<div class="col-lg-4">
											<select name="lateMark" data-placeholder="Please Select"
												id="lateMark"
												class="form-control form-control-select2 select2-hidden-accessible"
												tabindex="-1" aria-hidden="true">
												<option value="">Please Select</option>
												<option value="1">Yes</option>
												<option value="0">No</option>

											</select> <span class="validation-invalid-label" id="error_lateMark"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label  col-lg-2" for="woRemarks">Remark
											: </label>
										<div class="col-lg-4">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Any Remark" onchange="trim(this)"
												id="woRemarks" name="woRemarks"></textarea>
											<span class="validation-invalid-label" id="error_woRemarks"
												style="display: none;">This field is required.</span>

										</div>
									</div>


									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a
												href="${pageContext.request.contextPath}/showMstEmpTypeList"><button
													type="button" class="btn btn-light">Back</button></a>
										</div>
									</div>
								</form>
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
	<script type="text/javascript">
		function setDate() {

			var value = document.getElementById("otApplicable").value;
			//alert("Value " + value)
			if (value == 'Yes') {
				//	document.getElementById("abc").style.display = "block";
				$("#abc").show()
			} else {

				//	document.getElementById("abc").style.display = "none";
				$("#abc").hide()
			}

		}
	</script>
	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			return;
		}

		$(document).ready(function($) {

			$("#submitInsertWeeklyOff").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#typeName").val()) {

					isError = true;

					$("#error_typeName").show()
					//return false;
				} else {
					$("#error_typeName").hide()
				}

				if (!$("#typeName").val()) {

					isError = true;

					$("#error_typeName").show()

				} else {
					$("#error_typeName").hide()
				}

				/* if (!$("#minHr").val()) {

					isError = true;

					$("#error_minHr").show()

				} else {
					$("#error_minHr").hide()
				} */
				if (!$("#minWorkRule").val()) {

					isError = true;

					$("#error_minWorkRule").show()

				} else {
					$("#error_minWorkRule").hide()
				}

				if (!$("#otApplicable").val()) {

					isError = true;

					$("#error_otApplicable").show()
					//return false;
				} else {
					$("#error_otApplicable").hide()
				}

				if ($("#otApplicable").val() == 'Yes') {
					if (!$("#otType").val()) {

						isError = true;

						$("#error_otType").show()

					} else {
						$("#error_otType").hide()
					}

				}

				if (!$("#weekOffWork").val()) {

					isError = true;

					$("#error_weekOffWork").show()

				} else {
					$("#error_weekOffWork").hide()
				}

				if (!$("#lateMark").val()) {

					isError = true;

					$("#error_lateMark").show()

				} else {
					$("#error_lateMark").hide()
				}
				if (!$("#halfDayDed").val()) {

					isError = true;

					$("#error_halfDayDed").show()

				} else {
					$("#error_halfDayDed").hide()
				}

				if (!$("#prodApplicable").val()) {

					isError = true;

					$("#error_prodApplicable").show()

				} else {
					$("#error_prodApplicable").hide()
				}

				if (!isError) {

					var x = true;
					if (x == true) {

						document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//end ajax send this to php page
				}
				return false;
			});
		});
		//
	</script>



</body>
</html>