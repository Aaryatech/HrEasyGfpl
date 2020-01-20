<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<link
	href="https://fonts.googleapis.com/css?family=Raleway:400,300,600,800,900"
	rel="stylesheet" type="text/css">
<c:url var="searchManagerwiseCapacityBuilding"
	value="searchManagerwiseCapacityBuilding" />
<c:url var="clientwisetaskcostreport" value="clientwisetaskcostreport" />
<c:url var="showManagerDetail" value="showManagerDetail" />
<c:url var="getClientList" value="getClientList" />
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
			<div class="page-header page-header-light" style="display: none;">


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
					<div class="breadcrumb justify-content-center">
						<%-- <c:if test="${addAccess == 0}"> 
							<a href="${pageContext.request.contextPath}/service"
								class="breadcrumb-elements-item"> Add Service </a>
						</c:if> --%>

					</div>

				</div>
			</div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">
					<div class="card-header header-elements-inline">

						<h5 class="card-title">Reports</h5>
					</div>
					<form method="post" id="reportForm">
						<div class="card-body">

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


							</div>

							<table
								class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
								id="printtable1">
								<thead>
									<tr class="bg-blue">
										<th>Report Name</th>
										<th class="text-center" width="10%">Actions</th>
									</tr>
								</thead>

								<tbody>
									<tr>

										<td>Employee Advance Payment</td>
										<td class="text-center"><a href="#"
											onclick="getProgReport(0,'showAdvancePaymentRep')"
											title="excel"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a> <a href="#"
											onclick="getProgReport(1,'showAdvancePaymentRep')"
											title="PDF"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a></td>
									</tr>
									<tr>

										<td>Yearly Advance Amount</td>
										<td class="text-center"><a href="#"
											onclick="getProgReport(0,'showAdvancePaymentYearlyRep')"
											title="excel"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a> <a href="#"
											onclick="getProgReport(1,'showAdvancePaymentYearlyRep')"
											title="PDF"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a></td>
									</tr>

									<tr>
										<td>Employee Advance Skip Report</td>

									<td class="text-center"><a href="#"
											onclick="getProgReport(0,'showAdvanceSkipRep')"
											title="excel"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a> <a href="#"
											onclick="getProgReport(1,'showAdvanceSkipRep')"
											title="PDF"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a></td>
									</tr>

									<tr>

										<td>Inactive Task(Manager)</td>

										<td class="text-center">
											<!-- <a href="#" title="pdf"
											onclick="getProgReport(1,'showInactiveTaskRepForManager')" title="pdf"><i
												class="icon-file-pdf " style="color: black;"></i></a> &nbsp; -->
											<a href="#"
											onclick="getProgReport(0,'showInactiveTaskRepForManager')"
											title="excel"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a>
										</td>
									</tr>


									<tr>

										<td>Completed Task(Manager)</td>

										<td class="text-center">
											<!-- <a href="#" title="pdf"
											onclick="getProgReport(1,'showCompletedTaskRepForManager')" title="pdf"><i
												class="icon-file-pdf " style="color: black;"></i></a> &nbsp;  -->
											<a href="#"
											onclick="getProgReport(0,'showCompletedTaskRepForManager')"
											title="excel"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a>
										</td>
									</tr>

									<tr>

										<td>Employee And Manager Performance Hours(Manager)
											Header</td>

										<td class="text-center"><a href="#"
											onclick="getProgReport(0,'showMangPerfHeadList')"
											title="excel"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a></td>
									</tr>

									<tr>

										<td>Employee Partner Grid</td>
										<td class="text-center"><a href="#"
											onclick="getProgReport(0,'showEmployeePartnerGrid')"
											title="excel"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a></td>
									</tr>

									<tr>

										<td>Client Wise Cost and Revnue Reports</td>
										<td class="text-center"><a href="#"
											onclick="getProgReport(0,'clientwisetaskcostreport')"
											title="excel"><i class="icon-file-spreadsheet  "
												style="color: black;"></i></a></td>
									</tr>


								</tbody>
							</table>
							<input type="hidden" id="p" name="p" value="0"> <input
								type="hidden" id="emp_name" name="emp_name" value="0">
						</div>
					</form>
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
	<!-- /page content -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/global_assets/js/common_js/validation.js"></script>
	<!-- /page content -->


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


</body>
</html>