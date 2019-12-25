<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*"
	import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="en">
<head>

<c:url var="addStrDetail" value="/addStrDetail" />


<c:url var="chkNumber" value="/chkNumber" />

<c:url var="getLeaveStructureForEdit" value="/getLeaveStructureForEdit" />
<c:url var="getHolidayAndWeeklyOffList"
	value="/getHolidayAndWeeklyOffList" />
<c:url var="calholidayWebservice" value="/calholidayWebservice" />
<c:url var="checkDatesRange" value="/checkDatesRange" />
<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>

<body onload="chkAssign()">


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
										<td width="60%"><h5 class="card-title">Employee
												Attendance Details for ${year}-${month}</h5></td>
										<td width="40%" align="right">
											<%-- <a
									href="${pageContext.request.contextPath}/showApplyForLeave"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">Employee List</button>
								</a>  --%>
										</td>
									</tr>
								</table>
							</div>

							<div class="card-body">



								<div class="form-group row">
									<label class="col-form-label col-lg-2"> Employee Code :
									</label> <label class="col-form-label col-lg-2">
										${summaryAttendance.empCode}</label> <label
										class="col-form-label col-lg-2"> Employee Name : </label> <label
										class="col-form-label col-lg-2">${summaryAttendance.empName}</label>
								</div>

								<div class="form-group row">
									<label class="col-form-label col-lg-1"> Total Days : </label> <label
										class="col-form-label col-lg-1">${summaryAttendance.totalDaysInmonth}</label>
									<label class="col-form-label col-lg-1"> Working Days :
									</label> <label class="col-form-label col-lg-1">${summaryAttendance.workingDays}</label>
									<label class="col-form-label col-lg-1"> Present : </label> <label
										class="col-form-label col-lg-1">${summaryAttendance.presentDays}</label>
									<label class="col-form-label col-lg-1"> Weekly Off : </label> <label
										class="col-form-label col-lg-1">${summaryAttendance.weeklyOff}</label>
									<label class="col-form-label col-lg-1"> Holiday : </label> <label
										class="col-form-label col-lg-1">${summaryAttendance.paidHoliday}</label>
								</div>

								<div class="form-group row">
									<label class="col-form-label col-lg-1"> Paid Leave : </label> <label
										class="col-form-label col-lg-1">${summaryAttendance.paidLeave}</label>
									<label class="col-form-label col-lg-1"> Unpaid Leave :
									</label> <label class="col-form-label col-lg-1">${summaryAttendance.unpaidLeave}</label>
									<label class="col-form-label col-lg-1"> Total Late Mark
										: </label> <label class="col-form-label col-lg-1">${summaryAttendance.totlateDays}</label>
									<label class="col-form-label col-lg-1"> Payable Days :
									</label> <label class="col-form-label col-lg-1">${summaryAttendance.payableDays}</label>
									<label class="col-form-label col-lg-1"> Total OT Hrs: </label>
									<label class="col-form-label col-lg-1">${summaryAttendance.totOthr}</label>
								</div>

								<hr>
								<div class="table-responsive">
									<table
										class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
										id="printtable1">


										<thead>
											<tr class="bg-blue" style="text-align: center;">

												<th class="text-center">Date</th>
												<th class="text-center">Status</th>
												<th class="text-center">In Time</th>
												<th class="text-center">Out Time</th>
												<th class="text-center">Late Mark</th>
												<th class="text-center">Late MIN</th>
												<th class="text-center">WR. Hrs</th>
												<th class="text-center">OT Hrs</th>
												<th class="text-center">Deduction HR</th>
												<th class="text-center">OShift/Loc</th>
												<th class="text-center">Last Modified</th>
												<th class="text-center">Action</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${dailyrecordList}" var="dailyrecordList">
												<tr>
													<td class="text-center">${dailyrecordList.attDate}</td>
													<td class="text-center">${dailyrecordList.attStatus}</td>

													<c:choose>
														<c:when test="${dailyrecordList.inTime eq '00:00:00'}">
															<td class="text-center" style="background-color: #FFA8A8">${dailyrecordList.inTime}</td>
														</c:when>
														<c:otherwise>
															<td class="text-center">${dailyrecordList.inTime}</td>
														</c:otherwise>
													</c:choose>

													<c:choose>
														<c:when test="${dailyrecordList.outTime eq '00:00:00'}">
															<td class="text-center" style="background-color: #FFA8A8">${dailyrecordList.outTime}</td>
														</c:when>
														<c:otherwise>
															<td class="text-center">${dailyrecordList.outTime}</td>
														</c:otherwise>
													</c:choose>


													<c:choose>
														<c:when test="${dailyrecordList.lateMark==1}">
															<td class="text-center" style="background-color: #FF9">Yes</td>
														</c:when>
														<c:otherwise>
															<td class="text-center">No</td>
														</c:otherwise>
													</c:choose>
													<td class="text-right">${dailyrecordList.lateMin}</td>
													<td class="text-right">${dailyrecordList.workingHrs}</td>
													<td class="text-right">${dailyrecordList.otHr}</td>
													<td>-</td>
													<td>${dailyrecordList.currentShiftname}</td>
													<td>-</td>
													<td>-</td>
												</tr>
											</c:forEach>




										</tbody>
									</table>
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





	<!-- Scrollable modal -->

	<!-- /scrollable modal -->
</body>
</html>