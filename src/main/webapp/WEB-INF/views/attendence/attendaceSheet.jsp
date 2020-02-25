<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

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
						<h5 class="card-title">Attendance Sheet</h5>
						<!-- <div class="header-elements">
							<div class="list-icons">
								<a class="list-icons-item" data-action="collapse"></a>
							</div> 
						</div>-->
					</div>

					<div class="card-body">

						<form action="${pageContext.request.contextPath}/attendaceSheet"
							id="submitInsertLeave" method="get">
							<div class="form-group row">
								<label
									class="col-form-label text-info font-weight-bold col-lg-2"
									for="date">Select Date <span style="color: red">*
								</span> :
								</label>
								<div class="col-md-2">
									<input type="text" class="form-control "
										placeholder="Select Date " id="datepicker" name="date"
										value="${date}" autocomplete="off">
								</div>



								<button type="submit" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn">
									Search <i class="icon-paperplane ml-2"></i>
								</button>

								<!-- <button type="button" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn"
									onclick="getProgReport(0,'exelForEmployeeTypeWiseClaim')">
									Excel <i class="icon-paperplane ml-2"></i>
								</button> -->

							</div>
						</form>
						<div id="loader" style="display: none;">
							<img
								src="${pageContext.request.contextPath}/resources/assets/images/giphy.gif"
								width="150px" height="150px"
								style="display: block; margin-left: auto; margin-right: auto">
						</div>
						<ul class="nav nav-tabs nav-tabs-highlight nav-justified1">
							<li class="nav-item"><a href="#highlighted-justified-tab1"
								class="nav-link active" data-toggle="tab">View Monthly
									Attendance</a></li>
							<li class="nav-item"><a href="#highlighted-justified-tab2"
								class="nav-link" data-toggle="tab">View Summary Attendance </a></li>

						</ul>

						<div class="tab-content">
							<div class="tab-pane fade show active"
								id="highlighted-justified-tab1">
								<div class="table-responsive">
									<table
										class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
										id="bootstrap-data-table">
										<thead>
											<tr class="bg-blue">
												<th width="10%" style="text-align: center;">Sr.no</th>
												<th style="text-align: center;">EMP Code</th>
												<th style="text-align: center;">EMP Name</th>
												<th style="text-align: center;">Month</th>
												<th style="text-align: center;">Year</th>
												<c:forEach items="${attendanceSheetData.dates}" var="dates"
													varStatus="count">
													<th style="text-align: center;">${count.index+1}</th>
												</c:forEach>
												<th style="text-align: center;">Edit</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${attendanceSheetData.infomationList}"
												var="infomationList" varStatus="count">
												<tr>
													<td>${count.index+1}</td>
													<td style="text-align: center;">${infomationList.empCode}</td>
													<td>${infomationList.empName}</td>
													<td style="text-align: right;">${month}</td>
													<td style="text-align: right;">${year}</td>
													<c:forEach items="${infomationList.sttsList}"
														var="sttsList">
														<td style="text-align: center;"><p
																title="In Time - ${sttsList.inTime}, Out Time - ${sttsList.outTime}, Wotking Hrs - ${sttsList.workingMin}, OT Min - ${sttsList.otMin}, Late Min - ${sttsList.lateMin}">${sttsList.status}</p></td>
													</c:forEach>
													<td class="text-center"><c:if
															test="${editAccess == 0}">
															<a
																href="${pageContext.request.contextPath}/attendanceEditEmpMonth?month=${month}&year=${year}&empId=${infomationList.empId}"
																class="list-icons-item text-primary-600"
																data-popup="tooltip" title="" data-original-title="Edit"><i
																class="icon-pencil7"></i></a>
														</c:if></td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
							<div class="tab-pane fade" id="highlighted-justified-tab2">

								<div class="table-responsive">
									<table
										class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
										id="bootstrap-data-table1">
										<thead>
											<tr class="bg-blue">
												<th width="5%" class="text-center">Sr.no</th>
												<th class="text-center">EMP Code</th>
												<th class="text-center">EMP Name</th>
												<th class="text-center">Working Days</th>
												<th class="text-center">Present Days</th>
												<th class="text-center">Absent Days</th>
												<th class="text-center">Weekly Off</th>
												<th class="text-center">Paid Holiday</th>
												<th class="text-center">Paid Leave</th>
												<th class="text-center">Unpaid Holiday</th>
												<th class="text-center">Unpaid Leave</th>
												<th class="text-center">Late Hrs</th>
												<th class="text-center">Late Days</th>
												<th class="text-center">Payable Days</th>
												<th class="text-center">NCP Days</th>
												<th class="text-center">Total Hrs</th>
												<th class="text-center">Total OT Hrs</th>
												<th class="text-center">Attendance Type</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${summrylist}" var="summrylist"
												varStatus="count">
												<tr>
													<td>${count.index+1}</td>
													<td>${summrylist.empCode}</td>
													<td>${summrylist.empName}</td>
													<td class="text-right">${summrylist.workingDays}</td>
													<td class="text-right">${summrylist.presentDays}</td>
													<td class="text-right">${summrylist.absentDays}</td>
													<td class="text-right">${summrylist.weeklyOff}</td>
													<td class="text-right">${summrylist.paidHoliday}</td>
													<td class="text-right">${summrylist.paidLeave}</td>
													<td class="text-right">${summrylist.unpaidHoliday}</td>
													<td class="text-right">${summrylist.unpaidLeave}</td>
													<td class="text-right">${summrylist.totlateMins}</td>
													<td class="text-right">${summrylist.totlateDays}</td>
													<td class="text-right">${summrylist.payableDays}</td>
													<td class="text-right">${summrylist.ncpDays}</td>
													<td class="text-right">${summrylist.totworkingHrs}</td>
													<td class="text-right">${summrylist.totOthr}</td>
													<td class="text-center">${summrylist.salBasis}</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
						</div>

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
		// Single picker
		/* $("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : "-50:+50",
			dateFormat : "mm-yy"
		}); */

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
</body>
</html>