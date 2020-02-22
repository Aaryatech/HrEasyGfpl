<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*"
	import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="en">
<head>

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
								<h5 class="card-title">Employee Attendance Details for
									${year}-${month}</h5>
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
								<div class="form-group row">
									<label class="col-form-label col-lg-1"> NCP Days : </label> <label
										class="col-form-label col-lg-1">${summaryAttendance.ncpDays}</label>

								</div>

							</div>
							<hr>

							<div id="editAttanceDiv" style="display: none;">
								<div class="card-header header-elements-inline">
									<h5 class="card-title">Edit Attendance Detail</h5>
								</div>
								<div class="card-body">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="attDate">
											Date : </label>
										<div class="col-lg-2">
											<input type="text" class="form-control"
												placeholder="Attendace Date" id="attDate" name="attDate"
												autocomplete="off" disabled>

										</div>
										<div class="col-lg-1"></div>
										<label class="col-form-label col-lg-2" for="attStatus">
											Status : </label>
										<div class="col-lg-2">
											<input type="text" class="form-control" placeholder="Status"
												id="attStatus" name="attStatus" autocomplete="off" disabled>

										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="selectUpdateBy">
											Update By : </label>
										<div class="col-lg-2">
											<input type="radio" name="selectUpdateBy" id="byStatus"
												value="1" checked onclick="openHideDiv(1)">By Status
											<input type="radio" name="selectUpdateBy" id="byIntime"
												value="2" onclick="openHideDiv(2)">In Time and Out
											Time

										</div>

									</div>
									<div id="byStatusDive">
										<div class="form-group row">
											<label class="col-form-label col-lg-2" for="selectStatus">
												Select Status : </label>
											<div class="col-lg-2">
												<select name="selectStatus"
													data-placeholder="Select Leave Type" id="selectStatus"
													class="form-control form-control-select2 select2-hidden-accessible">
													<option value="0">Select Status</option>
													<c:forEach items="${lvTypeList}" var="lvTypeList">
														<%-- data-leavestrname="${leaveHistoryList.lvTitle}" --%>
														<option value="${lvTypeList.lvSumupId}">${lvTypeList.nameSd}</option>

													</c:forEach>
												</select>
											</div>
											<div class="col-lg-1"></div>
											<label class="col-form-label col-lg-2" for="lateMark">
												Late Mark : </label>
											<div class="col-lg-2">
												<input type="checkbox" class="chk" name="lateMark"
													id="lateMark">
											</div>

										</div>

										<div class="form-group row">
											<label class="col-form-label col-lg-2" for="otHours">
												OT Hours : </label>
											<div class="col-lg-2">
												<input type="time" class="form-control"
													placeholder="OT Hours" id="otHours" name="otHours"
													autocomplete="off">

											</div>

										</div>
									</div>
									<div id="byIntimeDive" style="display: none;">
										<div class="form-group row">
											<label class="col-form-label col-lg-2" for="inTime">
												In Time : </label>
											<div class="col-lg-2">
												<input type="time" class="form-control"
													placeholder="In Time" id="inTime" name="inTime"
													autocomplete="off">

											</div>
											<div class="col-lg-1"></div>
											<label class="col-form-label col-lg-2" for="outTime">
												Out Time : </label>
											<div class="col-lg-2">
												<input type="time" class="form-control"
													placeholder="Out Time" id="outTime" name="outTime"
													autocomplete="off">

											</div>
										</div>

									</div>



									<div class="col-md-12 text-center">
										<button type="submit" class="btn bg-blue ml-3 legitRipple"
											id="submtbtn" onclick="saveAttendanceDetail()">Save</button>
										<button type="button" class="btn btn-primary"
											onclick="closeEditDetailTab()">Cancel</button>
										<input type="hidden" class="form-control"
											placeholder="Out Time" id="dailyId" name="dailyId"
											autocomplete="off"> <input type="hidden"
											class="form-control" placeholder="Out Time" id="dailyId"
											name="dailyId" autocomplete="off"> <input
											type="hidden" id="year" name="year" value="${year}">
										<input type="hidden" id="month" name="month" value="${month}">
									</div>


								</div>
								<hr>
							</div>
							<div class="card-body">
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
												<th class="text-center">OShift/Loc</th>
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
													<td>${dailyrecordList.currentShiftname}</td>
													<td class="text-center"><c:if
															test="${dailyrecordList.isFixed==0}">
															<a href="#"
																onclick="editAttendanceDetail(${dailyrecordList.id})"
																class="list-icons-item text-primary-600"
																data-popup="tooltip" title=""><i
																class="icon-pencil7"></i></a>
														</c:if></td>
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

	<script type="text/javascript">
		function openHideDiv(value) {

			if (value == 1) {

				document.getElementById("byIntimeDive").style.display = 'none';
				document.getElementById("byStatusDive").style.display = 'block';

			} else {
				document.getElementById("byIntimeDive").style.display = 'block';
				document.getElementById("byStatusDive").style.display = 'none';
			}
		}

		function closeEditDetailTab() {

			document.getElementById("editAttanceDiv").style.display = 'none';
		}

		function editAttendanceDetail(dailyId) {
 
			
			
			var fd = new FormData();
			fd.append('dailyId', dailyId);
			
			$
			.ajax({
				url : '${pageContext.request.contextPath}/editDailyRecord',
				type : 'post',
				dataType : 'json',
				data : fd,
				contentType : false,
				processData : false,
				success : function(response) {

					document.getElementById("editAttanceDiv").style.display = 'block';
					document.getElementById("byIntimeDive").style.display = 'none';
					document.getElementById("byStatusDive").style.display = 'block';
					document.getElementById("byStatus").checked = true;
					document.getElementById("dailyId").value = response.id;
					document.getElementById("attDate").value = response.attDate;
					document.getElementById("attStatus").value = response.attStatus;
					 
					if(response.otHr.length==4){
						document.getElementById("otHours").value = 0+response.otHr;
					}else{
						document.getElementById("otHours").value = response.otHr;
					}
					

					document.getElementById("inTime").value = response.inTime.substr(0, 5);
					document.getElementById("outTime").value = response.outTime.substr(0, 5);
					
					
					if(response.lateMark==1){
						document.getElementById("lateMark").checked = true;
					}else{
						document.getElementById("lateMark").checked = false;
					}
					 
				},
			});

		}
		
	function saveAttendanceDetail() {
  
		var selectStatus = document.getElementById("selectStatus").value;  
		var selectStatusText = $("#selectStatus option:selected").text();
		var otHours = document.getElementById("otHours").value;  
		var dailyId = document.getElementById("dailyId").value;
		var inTime = document.getElementById("inTime").value;
		var outTime = document.getElementById("outTime").value;
		var byStatus=1;
		var lateMark=0;
		var month = document.getElementById("month").value;
		var year = document.getElementById("year").value;
		
		
		if(document.getElementById("byIntime").checked==true){
			byStatus=2;
		}
		if(document.getElementById("lateMark").checked==true){
			lateMark=1;
		}
		 
			var fd = new FormData();
			fd.append('dailyId', dailyId);
			fd.append('otHours', otHours);
			fd.append('selectStatus', selectStatus);
			fd.append('byStatus', byStatus);
			fd.append('lateMark', lateMark);
			fd.append('inTime', inTime);
			fd.append('outTime', outTime);
			fd.append('selectStatusText', selectStatusText);
			fd.append('month', month);
			fd.append('year', year);
			
			  $
			.ajax({
				url : '${pageContext.request.contextPath}/submitAttendanceDetail',
				type : 'post',
				dataType : 'json',
				data : fd,
				contentType : false,
				processData : false,
				success : function(response) {

					location.reload(true);
				},
			});  

		}
	</script>



	<!-- Scrollable modal -->

	<!-- /scrollable modal -->
</body>
</html>