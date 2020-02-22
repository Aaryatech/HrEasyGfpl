<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>

</head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datepicker.css"
	type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-datepicker.js"></script>
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
								<h6 class="card-title">
									Attendance process for month <strong> ${monthName}
										&nbsp;${year}</strong>
								</h6><br/>
								<c:if test="${countSal>0}">
									<label class="text-danger"> Shift Are not
										Assigned To Employee </label>
								</c:if>
								<div class="header-elements"></div>
							</div>

							<div class="card-body">
								<div class="tab-content">
									<c:if test="${countSal==0}">
										<div class="" id="solid-rounded-justified-tab1">

											<div
												class="sidebar sidebar-light bg-transparent sidebar-component sidebar-component-right wmin-300 border-0 shadow-0 order-1 order-md-2 sidebar-expand-md card">
												<div
													class="card-header bg-transparent header-elements-inline">
													<span class="card-title font-weight-bold">Stats</span>

												</div>

												<div class="card-body p-0">
													<ul class="nav nav-sidebar my-2">
														<li class="nav-item"><i class="icon-users"></i> Total
															Employee <span class="badge bg-info badge-pill ml-auto"
															id="total_emp">${infoForUploadAttendance.totalEmp}</span></li>
														<li class="nav-item"><i class="icon-grid4"></i> Total
															attendance expected <span
															class="badge bg-info badge-pill ml-auto"
															id="total_attendce_expected">${(infoForUploadAttendance.dateDiff+1)*infoForUploadAttendance.totalEmp}</span></li>
														<li class="nav-item"><i class="icon-grid52"></i>
															Total added by step1 <span
															class="badge bg-success badge-pill ml-auto"
															id="total_att_present">${infoForUploadAttendance.updatedByStep1}</span></li>
														<li class="nav-item"><i class="icon-grid52"></i>
															Total attendance uploaded <span
															class="badge bg-danger badge-pill ml-auto"
															id="by_file_updated">${infoForUploadAttendance.updatedByFile}</span></li>
													</ul>
												</div>

											</div>
											<form name="attendanceStep1" id="attendanceStep1"
												action="${pageContext.request.contextPath}/attendenceImportExel"
												method="GET" class="form-inline justify-content-center">

												<input type="hidden" name="mode" id="mode"
													value="submitform">
												<div class="form-group ">
													<label for="staticEmail" class="col-md-12 col-form-label">Month
														: </label>


												</div>
												<div class="input-group mr-3">

													<input type="text" name="selectMonth" id="datepicker"
														class="form-control" required />
													<!-- <select name="selectMonth"
													data-placeholder="Please Select" id="selectMonth"
													class="form-control form-control-select21 select2-hidden-accessible1"
													tabindex="-1" aria-hidden="true" required="required">
													<option value="">Please Select</option>
													<option value="1">January</option>
													<option value="2">February</option>
													<option value="3">March</option>
													<option value="4">April</option>
													<option value="5">May</option>
													<option value="6">June</option>
													<option value="7">July</option>
													<option value="8">August</option>
													<option value="9">September</option>
													<option value="10">October</option>
													<option value="11">November</option>
													<option value="12">December</option>

												</select> -->
												</div>

												<button type="submit" class="btn btn-primary   btnActStep1 "
													id="btnActStep1" data-toggle1="modal"
													data-target1="#modal_step1">
													Submit <i class="icon-paperplane ml-2"></i>
												</button>

											</form>


										</div>
									</c:if>

								</div>
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
		$('.daterange-basic_new').daterangepicker({
			applyClass : 'bg-slate-600',

			cancelClass : 'btn-light',
			locale : {
				format : 'DD-MM-YYYY',
				separator : ' to '
			}
		});
		$(document).ready(function() {
			// month selector
			$('#datepicker').datepicker({
				autoclose : true,
				format : "mm-yyyy",
				viewMode : "months",
				minViewMode : "months"

			});

		});
	</script>

	<script>
		/* 	$(document).ready(function() {
				// month selector
				$('.datepicker').datepicker({
					autoclose : true,
					maxViewMode : 0,
					minViewMode : 1,
					format : 'mm',

				});
			}); */
		$(document)
				.ready(
						function($) {

							//btn_go_next_tab
							$(".btn_go_next_tab").click(
									function(e) {
										$('.nav-tabs > .nav-item > .active')
												.parent().next('li').find('a')
												.trigger('click');

									});
							$(".btn_go_prev_tab").click(
									function(e) {
										$('.nav-tabs > .nav-item > .active')
												.parent().prev('li').find('a')
												.trigger('click');

									});

							$("#submitInsertLocaion")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#locName").val()) {

													isError = true;

													$("#error_locName").show()
													//return false;
												} else {
													$("#error_locName").hide()
												}

												if (!$("#locShortName").val()) {

													isError = true;

													$("#error_locShortName")
															.show()

												} else {
													$("#error_locShortName")
															.hide()
												}

												if (!$("#add").val()) {

													isError = true;

													$("#error_locadd").show()

												} else {
													$("#error_locadd").hide()
												}

												if (!$("#prsnName").val()) {

													isError = true;

													$("#error_prsnName").show()

												} else {
													$("#error_prsnName").hide()
												}

												if (!$("#contactNo").val()
														|| !validateMobile($(
																"#contactNo")
																.val())) {

													isError = true;

													if (!$("#contactNo").val()) {
														document
																.getElementById("error_contactNo").innerHTML = "This field is required.";
													} else {
														document
																.getElementById("error_contactNo").innerHTML = "Enter valid Mobile No.";
													}

													$("#error_contactNo")
															.show()

												} else {
													$("#error_contactNo")
															.hide()
												}

												if (!$("#email").val()
														|| !validateEmail($(
																"#email").val())) {

													isError = true;

													if (!$("#email").val()) {
														document
																.getElementById("error_email").innerHTML = "This field is required.";
													} else {
														document
																.getElementById("error_email").innerHTML = "Enter valid email.";
													}

													$("#error_email").show()

												} else {
													$("#error_email").hide()
												}

												if (!isError) {

													var x = true;
													if (x == true) {

														document
																.getElementById("submtbtn").disabled = true;
														return true;
													}
													//end ajax send this to php page
												}
												return false;
											});
						});
		//
		$(function() {
			$("#datepicker").datepicker({
				changeMonth : true,
				changeYear : true,
				yearRange : "-50:+50",
				dateFormat : "mm-yy"
			});
		});
	</script>



</body>
</html>