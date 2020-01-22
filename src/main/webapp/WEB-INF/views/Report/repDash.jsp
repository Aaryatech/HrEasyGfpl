<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>

<c:url var="empInfoHistoryList" value="/empInfoHistoryList" />
<style type="text/css">
.dataTables_wrapper {
	margin-left: 15px;
	margin-right: 15px;
}
</style>
</head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
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
			<div class="page-header page-header-light">


				<div
					class="breadcrumb-line breadcrumb-line-light header-elements-md-inline">
					<div class="d-flex">
						<div class="breadcrumb">
							<a href="index.html" class="breadcrumb-item"><i
								class="icon-home2 mr-2"></i> Home</a> <span
								class="breadcrumb-item active">Dashboard</span>
						</div>

						<a href="#" class="header-elements-toggle text-default d-md-none"><i
							class="icon-more"></i></a>
					</div>
				</div>
			</div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">
					<div class="card-body">
						<div class="card-header header-elements-inline">
							<h3 class="card-title">Report Dashboard</h3>
						</div>



						<!-- Extra large table -->

						<!-- <div class="card-header header-elements-inline">
							<h5 class="card-title">Reports</h5>
							<div class="header-elements">
								<div class="list-icons"></div>
							</div>
						</div>
 -->




						<div class="card-body">

							<form method="post" id="reportForm">

								<div class="form-group row">
									<label class="col-form-label col-lg-1" for="date">Select
										Date <span style="color: red">* </span> :
									</label>
									<div class="col-md-2">
										<input type="text" class="form-control "
											placeholder="Select Date " id="datepicker" name="date"
											value="${date}" autocomplete="off"> <span
											class="validation-invalid-label" id="error_datepicker"
											style="display: none;">Please Select Date. </span>
									</div>
									
									
									
									<label class="col-form-label col-lg-2">Date Range<span
											style="color: red">* </span>:
										</label>
										<div class="col-lg-5">
											<input type="text" class="form-control daterange-basic_new "
												name="leaveDateRange" data-placeholder="Select Date"
												id="leaveDateRange" onchange="calholidayWebservice()">
											<span class="validation-invalid-label" id="error_Range"
												style="display: none;">This field is required.</span> <span
												class="validation-invalid-label" id="error_insuf"
												style="display: none;">Insufficient Leaves.</span>

										</div>


								</div>

								<div class="row">

									<div class="col-sm-12 col-md-4">
										<div class="card">
											<div
												class="card-header bg-primary text-white header-elements-inline">
												<h6 class="card-title">Advance Reports</h6>
												<div class="header-elements"></div>
											</div>

											<div class="card-body" align="left">


												 Employee Advance Payment <a href="#"
													onclick="getProgReport(0,'showAdvancePaymentRep')"
													title="excel"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a> <a href="#"
													onclick="getProgReport(1,'showAdvancePaymentRep')"
													title="PDF"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a> <br /> Yearly Advance Amount <a
													href="#"
													onclick="getProgReport(0,'showAdvancePaymentYearlyRep')"
													title="excel"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a> <a href="#"
													onclick="getProgReport(1,'showAdvancePaymentYearlyRep')"
													title="PDF"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a><br /> Employee Advance Skip
												Report <a href="#"
													onclick="getProgReport(0,'showAdvanceSkipRep')"
													title="excel"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a> <a href="#"
													onclick="getProgReport(1,'showAdvanceSkipRep')" title="PDF"><i
													class="icon-file-spreadsheet  " style="color: black;"></i></a>

											</div>
										</div>
									</div>


									<div class="col-sm-12 col-md-4">
										<div class="card">
											<div
												class="card-header bg-primary text-white header-elements-inline">
												<h6 class="card-title">Attendence Reports</h6>
												<div class="header-elements"></div>
											</div>

											<div class="card-body" align="left">


												Attendance Register <a href="#"
													onclick="getProgReport(0,'showEmpAttendRegisterRep')"
													title="excel"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a> <a href="#"
													onclick="getProgReport(1,'showEmpAttendRegisterRep')"
													title="PDF"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a> <br /> DAILY ATTENDANCE REPORT <a
													href="#"
													onclick="getProgReport(0,'showEmpAttendanceRep')"
													title="excel"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a> <a href="#"
													onclick="getProgReport(1,'showEmpAttendanceRep')"
													title="PDF"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a><br /> Employee Advance Skip
												Report <a href="#"
													onclick="getProgReport(0,'showAdvanceSkipRep')"
													title="excel"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a> <a href="#"
													onclick="getProgReport(1,'showAdvanceSkipRep')" title="PDF"><i
													class="icon-file-spreadsheet  " style="color: black;"></i></a>

											</div>
										</div>
									</div>



									<div class="col-sm-12 col-md-4">
										<div class="card">
											<div
												class="card-header bg-primary text-white header-elements-inline">

												<div class="header-elements">
													<div class="list-icons"></div>
												</div>
											</div>

											<div class="card-body" align="left">Estimated Hours</div>
										</div>
									</div>

								</div>

								<input type="hidden" id="p" name="p" value="0"> <input
									type="hidden" id="emp_name" name="emp_name" value="0">
							</form>
						</div>


						<!-- /extra large table -->





					</div>

				</div>
				<!-- /highlighting rows and columns -->

			</div>
			<!-- /content area -->


			<!-- Footer -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
			<!-- /footer -->

		</div>
		<!-- /main content -->

	</div>



	<script type="text/javascript">
		//use this function for all reports just get mapping form action name dynamically as like of prm from every report pdf,excel function	
		function getProgReport(prm, mapping) {
			var x = document.getElementById("datepicker").value;
			//alert(x);
			if (prm == 1) {

				document.getElementById("p").value = "1";
			}

			if (x.length == 0) {

				$("#error_datepicker").show();

			} else {
				//	alert(0);
				$("#error_datepicker").hide();

				var form = document.getElementById("reportForm");

				form.setAttribute("target", "_blank");
				form.setAttribute("method", "get");
				form.action = ("${pageContext.request.contextPath}/" + mapping + "/");
				form.submit();
				document.getElementById("p").value = "0";
			}

		}
	</script>

	<script type="text/javascript">
		// Single picker
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : "-50:+50",
			dateFormat : "mm-yy"
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


	<!-- /page content -->

	<!-- /full width modal -->
</body>
</html>