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
						<!-- <div class="mb-3">
							<h6 class="mb-0 font-weight-semibold">Hidden labels</h6>
							<span class="text-muted d-block">Inputs with empty values</span>
						</div> -->
						<!-- /title -->


						<div class="card">

							<div class="card-header header-elements-inline">
								<table width="100%">
									<tr width="100%">
										<td width="60%"><h5 class="card-title">Import
												Attendance</h5></td>
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
							<%-- <div class="card-body">
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
									action="${pageContext.request.contextPath}/submitImportExel"
									id="submitInsertLocaion1" method="post"
									enctype="multipart/form-data">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="doc">Select
											File <span style="color: red">* </span>:
										</label>
										<div class="col-lg-6">
											<input type="file" class="form-control"
												placeholder="Enter Location Name" id="doc" name="doc"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_locName"
												style="display: none;">This field is required.</span>
										</div>
									</div>



									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/showLocationList"><button
													type="button" class="btn btn-primary">
													<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
													Cancel
												</button></a>
										</div>
									</div>
								</form>
							</div> --%>


							<div class="card-body">

								<ul
									class="nav nav-tabs nav-tabs-solid nav-justified rounded border-0">
									<li class="nav-item mr-1"><a
										href="#solid-rounded-justified-tab1"
										class="nav-link active show" id="tabstep1" data-toggle="tab">Step
											1</a></li>
									<li class="nav-item mr-1"><a
										href="#solid-rounded-justified-tab2" class="nav-link "
										data-toggle="tab">Step 2 Upload Attendance File (csv)</a></li>
									<li class="nav-item mr-1"><a
										href="#solid-rounded-justified-tab3" class="nav-link "
										data-toggle="tab">Step 3 Finalize Attendance</a></li>
									<!--  -->
								</ul>

								<div class="tab-content">
									<div class="tab-pane fade show active"
										id="solid-rounded-justified-tab1">

										<!-- <div class=" text-muted">
											Records 1207 (16 %) Uploaded by file<br> If Total
											attendance expected is equal to Total added by step1 than you
											can go to next step
										</div> -->
										<form name="attendanceStep1" id="attendanceStep1"
											action="http://gfplphp.aaryatechindia.in/index.php/attendance/attendanceprocess"
											class="form-inline justify-content-center">

											<input type="hidden" name="mode" id="mode" value="submitform">
											<input type="hidden" name="month" id="month"
												class="form-control " value="${month}"> <input
												type="hidden" name="year" id="year" class="form-control "
												value="${year}">

											<c:if
												test="${((infoForUploadAttendance.dateDiff+1)*infoForUploadAttendance.totalEmp)!=infoForUploadAttendance.updatedByStep1}">

												<button type="button"
													class="mr-3 btn btn-primary   btnActStep1 "
													id="btnActStep1" data-toggle1="modal"
													data-target1="#modal_step1">
													Start <i class="icon-paperplane ml-2"></i>
												</button>

											</c:if>

											<button type="button"
												class=" btn btn-info next   btn_go_next_tab "
												id="btn_go_next_tab">
												Next Step <i class="icon-arrow-right8 ml-2 "></i>
											</button>


										</form>



									</div>

									<div class="tab-pane fade" id="solid-rounded-justified-tab2">

										<div class="rows">
											<div class="col-md-12">
												<div class="row">
													<div class="col-md-12">
														<form action="#" method="POST"
															enctype="multipart/form-data" accept-charset="utf-8"
															class="form-inline1 justify-content-center">


															<div class="form-group row ">
																<label class="col-md-2 col-form-label" for="doc">Attach
																	File:</label>
																<div class="col-md-6">
																	<input type="file" class="form-control"
																		placeholder="Enter Location Name" id="doc" name="doc"
																		autocomplete="off" onchange="trim(this)"> <span
																		class="form-text text-muted">Accepted formats:
																		CSV </span>
																</div>
																<div class="col-md-4">
																	<button type="button" id="btnUploadCSVSubmit"
																		name="btnUploadCSVSubmit" class="btn btn-primary">
																		Uplaod File <i class="icon-paperplane ml-2"></i>
																	</button>
																</div>

															</div>


														</form>
													</div>
													<div class="col-md-12 text-center mt-4">
														<button type="button"
															class=" btn btn-info prev text-center  btn_go_prev_tab "
															id="btn_go_prev_tab2">
															<i class="icon-arrow-left8  mr-2 "></i> Previous Step
														</button>
														<button type="button"
															class=" btn btn-info next text-center  btn_go_next_tab "
															id="btn_go_next_tab">
															Next Step <i class="icon-arrow-right8  ml-2 "></i>
														</button>
													</div>

												</div>

											</div>
										</div>







									</div>

									<div class="tab-pane fade" id="solid-rounded-justified-tab3">
										<form name="attendanceFinal" id="attendanceFinal"
											action="http://gfplphp.aaryatechindia.in/index.php/attendance/attendanceprocess"
											class="form-inline justify-content-center">

											<input type="hidden" name="mode" id="mode" value="finalstep">
											<input type="hidden" name="month" id="month"
												class="form-control " value="11">

											<button type="button"
												class=" btn btn-primary next   btnActStepFinal "
												id="btnActStepFinal" data-toggle1="modal"
												data-target1="#modal_Final">
												Finalize Attendance<i class="icon-paperplane ml-2"></i>
											</button>


										</form>
										<div class="row">
											<div class="col-md-12 text-center mt-4">
												<button type="button"
													class=" btn btn-info prev text-center  btn_go_prev_tab "
													id="btn_go_prev_tab2">
													<i class="icon-arrow-left8  mr-2 "></i> Previous Step
												</button>
												<!--   <button type="button" class=" btn btn-info next text-center  btn_go_next_tab " id="btn_go_next_tab">Next Step <i class="icon-arrow-right8  ml-2 "></i></button> -->
											</div>
										</div>
									</div>
								</div>

								<div
									class="sidebar sidebar-light bg-transparent sidebar-component sidebar-component-right wmin-300 border-0 shadow-0 order-1 order-md-2 sidebar-expand-md card">
									<div class="card-header bg-transparent header-elements-inline">
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
											<li class="nav-item"><i class="icon-grid52"></i> Total
												added by step1 <span
												class="badge bg-success badge-pill ml-auto"
												id="total_att_present">${infoForUploadAttendance.updatedByStep1}</span></li>
											<li class="nav-item"><i class="icon-grid52"></i> Total
												attendance uploaded <span
												class="badge bg-danger badge-pill ml-auto"
												id="by_file_updated">${infoForUploadAttendance.updatedByFile}</span></li>
										</ul>
									</div>

								</div>
								<span class="text-info"> <a
									href="http://gfplphp.aaryatechindia.in/uploads/att_template/attendance_sample.csv"
									target="_blank" id="genTemplate1" title=".csv Format"><i
										class="icon-file-download"></i> Download Template</a></span>
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

	<!-- Info modal -->
	<div id="modal_step1" class="modal fade " data-backdrop="false"
		tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header bg-info">
					<h6 class="modal-title">
						Attendance process for month <strong>${monthName}
							&nbsp;${year}</strong>
					</h6>
					<!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
				</div>

				<div class="modal-body">
					<h6 class="font-weight-semibold text-center">
						<h6>
							Please wait. Processing your request..<br /> It will take approx
							3 to 5 min
						</h6>
					</h6>

					<hr>
					<p class="text-center text-info">If it is taking time please
						reload the page</p>
				</div>

				<div class="modal-footer">
					<!--   <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button> -->

				</div>
			</div>
		</div>
	</div>
	<!-- /info modal -->
	<!-- /page content -->

	<script>
		/* $('#block-page').on('click', function() {
			$.blockUI({
				message : '<i class="icon-spinner4 spinner"></i>',
				timeout : 2000, //unblock after 2 seconds
				overlayCSS : {
					backgroundColor : '#1b2024',
					opacity : 0.8,
					cursor : 'wait'
				},
				css : {
					border : 0,
					color : '#fff',
					padding : 0,
					backgroundColor : 'transparent'
				}
			});
		}); */

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

		$("#btnActStep1")
				.click(
						function(e) {
							// getProgress();
							console.log('startProgress');
							var dataString = $("#attendanceStep1").serialize();
							$
									.ajax({
										type : 'POST',
										dataType : 'json',
										async : true, //IMPORTANT!
										data : dataString, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
										// contentType: false,       // The content type used when sending data to the server.
										// cache: false,             // To unable request pages to be cached
										//  processData:false,        // To send DOMDocument or non processed data file it is set to false
										url : '${pageContext.request.contextPath}/addDefaultAttendance',

										beforeSend : function() {
											/* $.blockUI({
											    message: '<h6>Please wait. Processing your request..<br /> It Will take approx 3 min</h6>'
											});   */

											$('#modal_step1').modal('show');

										},
										oncomplete : function(data, status) {
											console.log(data.responseText);
										},
										success : function(redata) {
											// $.unblockUI();
											//alert(redata.error);
											//$('.modal-body').html(redata);
											if (redata.error == false) {
												location.reload(true);
											} else {

											}
											// $('#modal_step1').modal('hide');
										},
										error : function(xhr, status, error) {
											// console.log(data);
											var err = eval("("
													+ xhr.responseText + ")");
											console.log(error);
											console.log(err);
										}
									});
							return false;
						});
	</script>



</body>
</html>