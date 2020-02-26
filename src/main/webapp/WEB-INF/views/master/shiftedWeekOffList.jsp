<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:url var="getWeekOffChangeDetails" value="/getWeekOffChangeDetails" />

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datepicker.css"
	type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-datepicker.js"></script>
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


				<!-- Highlighting rows and columns -->
				<div class="card">

					<div class="card-header header-elements-inline">
						<table width="100%">
							<tr width="100%">
								<td width="60%"><h5 class="card-title">Shifted Weekly
										Off List</h5></td>
								<td width="40%" align="right"><c:if
										test="${addAccess == 0}">
										<a href="${pageContext.request.contextPath}/showChangeWeekOff"
											class="breadcrumb-elements-item">
											<button type="button" class="btn btn-primary">Change
												Weekly Off</button>
										</a>
									</c:if></td>
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

						<div class="form-group row">

							<label class="col-form-label text-info font-weight-bold col-lg-2"
								for="month"> Month-Year <span class="text-danger">*</span>:
							</label>
							<div class="col-lg-3">
								<input type="text" name="monthyear" id="monthyear"
									class="form-control datepicker" autocomplete="off"
									data-min-view-mode="months" data-start-view="1"
									data-format="mm-yyyy"> <span
									class="validation-invalid-label" id="error_monthyear"
									style="display: none;">This field is required.</span>
							</div>

							<label class="col-form-label text-info font-weight-bold col-lg-2"
								for="empId"> Employee<span class="text-danger">*
							</span>:
							</label>
							<div class="col-lg-3">
								<select name="empId" data-placeholder="Select  " id="empId"
									class="form-control form-control-select2 select2-hidden-accessible">

									<option value="">Select Employee</option>

									<option selected value="-1">All</option>

									<c:forEach items="${employeeInfoList}" var="empInfo">

										<option value="${empInfo.empId}">${empInfo.surname}
											${empInfo.firstName} [${empInfo.empCode}]
											[${empInfo.empDesgn}]</option>

									</c:forEach>
								</select>

							</div>
							<div style="text-align: center;">
								<input type="button" class="btn btn-primary" value="Search"
									onclick="show()" id="deleteId"
									style="align-content: center; width: 113px; margin-left: 40px;">
							</div>

						</div>


						<table
							class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
							id="printtable1">
							<thead>
								<tr class="bg-blue">

									<th width="10%">Sr. No.</th>
									<th>From Date</th>
									<th>Shift Date</th>
									<th>Location</th>
									<th>Reason</th>

								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>

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
	<!-- /page content -->
	<script type="text/javascript">
		$(document).ready(function() {
			// month selector
			$('.datepicker').datepicker({
				autoclose : true,
				format : "mm-yyyy",
				viewMode : "months",
				minViewMode : "months"

			});

		});
	</script>
	<script type="text/javascript">
		function show() {

			var monthyear = document.getElementById("monthyear").value;
			var empId = document.getElementById("empId").value;

			var valid = true;

			if (monthyear == null || monthyear == "") {
				valid = false;
				alert("Please Select Year");
			}
			if (empId == null || empId == "") {
				valid = false;
				alert("Please Select Employee");
			}

			if (valid == true) {

				$.getJSON('${getWeekOffChangeDetails}', {
					monthyear : monthyear,
					empId : empId,
					ajax : 'true',
				},

				function(data) {

					var dataTable = $('#printtable1').DataTable();
					dataTable.clear().draw();

					$.each(data, function(i, v) {

						/* var acButton = '<a href="${pageContext.request.contextPath}/showLoanDetailHistory?empId='
								+ v.exVar1
								+ '&calYrId='
								+ v.exVar3
								+ '&status='
								+ v.exVar2
								+ '"><i class="icon-pencil7" title="Detail History" style="color: black;">';
						 */
						dataTable.row.add(
								[ i + 1, v.weekofffromdate, v.weekoffshiftdate,
										v.locName, v.reason,

								]).draw();
					});

				});

			}

		}
	</script>
</body>
</html>