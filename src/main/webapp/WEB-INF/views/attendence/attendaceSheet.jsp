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
								<label class="col-form-label col-lg-1" for="date">Select
									Date <span style="color: red">* </span> :
								</label>
								<div class="col-md-2">
									<input type="text" class="form-control datepickerclass"
										placeholder="Select Date " id="date" name="date"
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
							<div id="loader" style="display: none;">
								<img
									src="${pageContext.request.contextPath}/resources/assets/images/giphy.gif"
									width="150px" height="150px"
									style="display: block; margin-left: auto; margin-right: auto">
							</div>

							<div class="table-responsive">
								<table
									class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
									id="bootstrap-data-table">
									<thead>
										<tr class="bg-blue">
											<th width="10%" style="text-align: center;">Sr.no</th>
											<th style="text-align: center;">Employee Code</th>
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
												<td style="text-align: right;">${month}</td>
												<td style="text-align: right;">${year}</td>
												<c:forEach items="${infomationList.sttsList}" var="sttsList">
													<td style="text-align: center;"><p
															title="In Time - ${sttsList.inTime}, Out Time - ${sttsList.outTime}, Wotking Hrs - ${sttsList.workingMin}, OT Min - ${sttsList.otMin}, Late Min - ${sttsList.lateMin}">${sttsList.status}</p></td>
												</c:forEach>
												<td class="text-center"><a
													href="${pageContext.request.contextPath}/editWeeklyOff?woId=Mg=="
													class="list-icons-item text-primary-600"
													data-popup="tooltip" title="" data-original-title="Edit"><i
														class="icon-pencil7"></i></a></td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>

						</form>
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
</body>
</html>