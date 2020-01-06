<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
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
			<div class="page-header page-header-light"></div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">
					<div class="card-header header-elements-inline">
						<h5 class="card-title">Employee Salary Information For
							Payroll process</h5>
						<!-- <div class="header-elements">
							<div class="list-icons">
								<a class="list-icons-item" data-action="collapse"></a>
							</div> 
						</div>-->
					</div>

					<div class="card-body">
						<form
							action="${pageContext.request.contextPath}/selectMonthForPayRoll"
							id="selectMonthForPayRoll" method="get">
							<div class="form-group row">
								<label class="col-form-label col-lg-1" for="datepicker">
									Select Month <span style="color: red">* </span> :
								</label>
								<div class="col-md-2">
									<input type="text" name="selectMonth" id="datepicker"
										class="form-control" value="${date}" required />
								</div>

								<button type="submit" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn">
									Search <i class="icon-paperplane ml-2"></i>
								</button>

							</div>
						</form>
						<form action="${pageContext.request.contextPath}/viewDynamicValue"
							id="submitInsertLeave" method="post">

							<input type="hidden" name="searchDate" id="searchDate"
								value="${date}" />
							<div id="loader" style="display: none;">
								<img
									src="${pageContext.request.contextPath}/resources/assets/images/giphy.gif"
									width="150px" height="150px"
									style="display: block; margin-left: auto; margin-right: auto">
							</div>

							<div class="table-responsive">
								<table
									class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
									id="bootstrap-data-table1">
									<thead>
										<tr class="bg-blue">
											<th width="5%" class="text-center">Sr.no</th>
											<th width="10%" class="text-center">EMP Code</th>
											<th class="text-center">EMP Name</th>
											<th class="text-center">EMP Type</th>
											<th class="text-center">Salary STR.</th>
											<th class="text-center">Department</th>
											<th class="text-center">Designation</th>
											<th class="text-center">Location</th>
											<th class="text-center">Salary Basis</th>
											<!-- <th class="text-center">PT</th>
											<th class="text-center">PF</th>
											<th class="text-center">ESIC</th>
											<th class="text-center">MLWF</th>
											<th class="text-center">Basic</th>
											<th class="text-center">DA</th>
											<th class="text-center">HRA</th>
											<th class="text-center">CONV</th>
											<th class="text-center">PER</th>
											<th class="text-center">Tel</th>
											<th class="text-center">Med</th>
											<th class="text-center">Edu</th>
											<th class="text-center">Veh</th>
											<th class="text-center">Oth1</th>
											<th class="text-center">Oth2</th>
											<th class="text-center">Gross Salary</th> -->

											<c:forEach items="${allownceList}" var="allownceList">
												<th class="text-center">${allownceList.shortName}</th>
											</c:forEach>
										</tr>


									</thead>

									<tbody>
										<c:forEach items="${empList}" var="empList" varStatus="count">
											<tr>
												<td>${count.index+1}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empName}</td>
												<td>${empList.empTypeName}</td>
												<td>${empList.salTypeName}</td>
												<td>${empList.deptName}</td>
												<td>${empList.designation}</td>
												<td>${empList.locName}</td>
												<td>${empList.salBasis}</td>
												<%-- <td>${empList.pfType}</td>
												<td>${empList.pfApplicable}</td>
												<td>${empList.esicApplicable}</td>
												<td>${empList.mlwfApplicable}</td>
												<td>${empList.basic}</td>
												<td>${empList.da}</td>
												<td>${empList.hra}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empCode}</td> --%>
												<c:forEach items="${allownceList}" var="allownceList">
													<th class="text-center"><c:forEach
															items="${empList.empAllowanceList}"
															var="empAllowanceList">
															<c:if
																test="${allownceList.allowanceId==empAllowanceList.allowanceId}">
															${empAllowanceList.value}
														</c:if>
														</c:forEach></th>
												</c:forEach>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>

							<c:if test="${date!=null}">
								<br>
								<div class="text-center">

									<button type="submit" class="btn bg-blue ml-3 legitRipple"
										id="submtbtn">
										Submit <i class="icon-paperplane ml-2"></i>
									</button>

								</div>
							</c:if>
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
		$(function() {
			$("#datepicker").datepicker({
				changeMonth : true,
				changeYear : true,
				yearRange : "-50:+50",
				dateFormat : "mm-yy"
			});
		});
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

		$('#selectAll').click(function(event) {
			if (this.checked) {
				// Iterate each checkbox
				$(':checkbox').each(function() {
					this.checked = true;
				});
			} else {
				$(':checkbox').each(function() {
					this.checked = false;
				});
			}
		});
	</script>
</body>
</html>