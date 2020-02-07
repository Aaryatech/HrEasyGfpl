<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>



<c:url var="getEmpAttnGraph" value="/getEmpAttnGraph" />
<c:url var="getEmpAdvanceGraph" value="/getEmpAdvanceGraph" />
<c:url var="getEmpLoanGraph" value="/getEmpLoanGraph" />



<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

<body onload="getGraphs(1)">

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

						<!-- /title -->


						<div class="card">

							<div class="card-header header-elements-inline">
								<table width="100%">
									<tr width="100%">
										<td width="60%"><h5 class="card-title">Employee
												Details</h5></td>
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

								<!-- Highlighted tabs -->
								<c:set var="flag" value="${sessionScope.tabEmpFlag}"></c:set>


								<ul class="nav nav-tabs nav-tabs-highlight">
									<li class="nav-item text-center"><a
										href="#highlighted-tab1"
										class="${flag==0 ? 'nav-link active' : 'nav-link'}"
										data-toggle="tab">Graphs </a></li>

									<li class="nav-item text-center"><a
										href="#highlighted-tab2"
										class="${flag==1 ? 'nav-link active' : 'nav-link'}"
										data-toggle="tab">Reports </a></li>

								</ul>


								<div class="tab-content">
									<div
										class="${flag==0 ? 'tab-pane fade show active' : 'tab-pane fade'}"
										id="highlighted-tab1">

										<form
											action="${pageContext.request.contextPath}/insertSubCompanyInfo"
											id="insertCompanyInfo" method="post">

											<input type="hidden" id="companyId" name="companyId"
												value="${company.companyId}">

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="date">Select
													From <span style="color: red">* </span> :
												</label>
												<div class="col-md-3">
													<input type="text" class="form-control datepickerclass" value="${fromDate}"
														placeholder="Select Date " id="datepickerFrom"
														name="datepickerFrom" autocomplete="off">
												</div>


												<label class="col-form-label col-lg-2" for="date">Select
													To <span style="color: red">* </span> :
												</label>
												<div class="col-md-3">
													<input type="text" class="form-control datepickerclass "
														placeholder="Select Date " id="datepickerTo" value="${toDate}"
														name="datepickerTo" autocomplete="off">
												</div>

											</div>
											
											<input type="hidden" id="empId" name="empId" value="${empId}">
											 
											 
											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="button" class="btn bg-blue ml-3 legitRipple" onclick="getGraphs()"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													 
												</div>
											</div>
											<div class="row">
											
											<div class="col-md-6" id="attn_div">
												<div class="box box-primary">
													<div class="box-header with-border">
														<h3 class="box-title">Employee Attendance Graph</h3>

													</div>
													<div class="box-body chart-responsive">
														<div class="chart" id="emp_attn_graph"
															style="height: 300px;"></div>
													</div>

												</div>
											</div>




<div class="col-md-6">
												<div class="box box-primary">
													<div class="box-header with-border">
														<h3 class="box-title">Employee Advance Graph</h3>

													</div>
													<div class="box-body chart-responsive">
														<div class="chart" id="emp_adv_graph"
															style="height: 300px;"></div>
													</div>

												</div>
											</div>
											
											
											
<div class="col-md-6">
												<div class="box box-primary">
													<div class="box-header with-border">
														<h3 class="box-title">Employee Loan Graph</h3>

													</div>
													<div class="box-body chart-responsive">
														<div class="chart" id="emp_loan_graph"
															style="height: 300px;"></div>
													</div>

												</div>
											</div>
											</div>


										</form>
									</div>

									<!--***************************************** tab 2 *************************************-->

									<div
										class="${flag==1 ? 'tab-pane fade show active' : 'tab-pane fade'}"
										id="highlighted-tab2">

										<form
											action="${pageContext.request.contextPath}/insertSubCompanyLogo"
											id="insertCompanyLogo" method="post"
											enctype="multipart/form-data">

											<input type="hidden" id="companyId" name="companyId"
												value="${company.companyId}">

											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="date">Select
													From <span style="color: red">* </span> :
												</label>
												<div class="col-md-3">
													<input type="text" class="form-control datepickerclass "
														placeholder="Select Date " id="datepickerFromRep"
														name="datepickerFromRep" autocomplete="off">
												</div>


												<label class="col-form-label col-lg-2" for="date">Select
													To <span style="color: red">* </span> :
												</label>
												<div class="col-md-3">
													<input type="text" class="form-control datepickerclass"
														placeholder="Select Date " id="datepickerToRep"
														name="datepickerToRep" autocomplete="off">
												</div>

											</div>
											
											<table
							class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
							id="printtable1">
							<thead>
								<tr class="bg-blue">

									<th width="10%">Sr. No.</th>
									<th>Report Name </th>
									 
									<th width="10%" class="text-center">Actions</th>
								</tr>
							</thead>
							<tbody>
 									<tr>
										 <td>1</td>
										<td>Employee Attendance Report</td>
 										<td class="text-center"><a href="#"
													onclick="getProgReport(0,'showEmployeeAttnRep')"
													title="excel"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a> <a href="#"
													onclick="getProgReport(1,'showEmployeeAttnRep')"
													title="PDF"><i class="icon-file-spreadsheet  "
													style="color: black;"></i></a>
												 </td>
									</tr>
							 
							</tbody>
						</table>
<input type="hidden" id="p" name="p" value="0">

											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="button" class="btn bg-blue ml-3 legitRipple" onclick="getGraphs()"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a
														href="${pageContext.request.contextPath}/showSubCompanyList"><button
															type="button" class="btn btn-light">
															<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
															Back
														</button></a>
												</div>
											</div>
										</form>
									</div>


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
		// Single picker
		$(".datepickerclass").datepicker({
			
			changeMonth : true,
			changeYear : true,
			yearRange : "-50:+50",
			dateFormat : "mm-yy"
		});
 
	</script>
 <!-- 
		<script type="text/javascript">
		// Single picker
		$("#datepickerFromRep").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : "-50:+50",
			dateFormat : "mm-yy"
		});

		//daterange-basic_new
		// Basic initialization
		$("#datepickerToRep").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : "-50:+50",
			dateFormat : "mm-yy"
		});
	</script> -->
	
	
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>
	<!-- Morris.js charts -->
	<script
		src="${pageContext.request.contextPath}/resources/dashb/raphael.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/resources/dashb/morris.min.js"
		type="text/javascript"></script>
		
		
		
	<script type="text/javascript">
		//use this function for all reports just get mapping form action name dynamically as like of prm from every report pdf,excel function	
		function getProgReport(prm, mapping) {
		  
			//alert(x);
			if (prm == 1) {

				document.getElementById("p").value = "1";
			}
 
				var form = document.getElementById("reportForm");

				form.setAttribute("target", "_blank");
				form.setAttribute("method", "get");
				form.action = ("${pageContext.request.contextPath}/" + mapping + "/");
				form.submit();
				document.getElementById("p").value = "0";
			 
		}
	</script>
	
	<script type="text/javascript">
 
	function getGraphs() {
		
		var empId = document.getElementById("empId").value;
		var fromDate = document.getElementById("datepickerFrom").value;
		var toDate = document.getElementById("datepickerTo").value;
	 
					$
							.getJSON(
									'${getEmpAttnGraph}',

									{
										empId : empId,
										toDate:toDate,
										fromDate:fromDate,
										ajax : 'true'

									},
									function(data) {
										
										
									
										if(data.length >6){
											
										//	alert(data.length);
											$('#attn_div').removeClass().addClass( "col-md-12" );
										}else{
											$('#attn_div').removeClass().addClass( "col-md-6" );
										}

										google.charts.load('current', {
											'packages' : [ 'corechart' ]
										});
										google.charts
												.setOnLoadCallback(drawChart);

										function drawChart() {
											 
											var dataTable = new google.visualization.DataTable();

											dataTable.addColumn('string',
													'month Year'); // Implicit domain column.

											dataTable.addColumn('number',
													'Working Days');
											dataTable.addColumn('number',
													'Present days');
											dataTable.addColumn('number',
											'Paid Holiday');
											dataTable.addColumn('number',
											'Unpaid Holiday');
											dataTable.addColumn('number',
											'Paid Leave');
											dataTable.addColumn('number',
											'Unpaid Leave');
											dataTable.addColumn('number',
											'Month Days');
											
											
 
											$
													.each(
															data,
															function(key, dt) {

																dataTable
																		.addRows([

																		[
																				dt.date,
																				dt.workingDays,
																				dt.presentdays,dt.paidHoliday,dt.unpaidHoliday,dt.paidLeave ,dt.unpaidLeave,dt.monthDays]

																		]);

															})

											/* slantedTextAngle: 60 */
											var options = {
												hAxis : {
													title : "Month Year",
													textPosition : 'out',
													slantedText : true
												},
												vAxis : {
													title : 'Days',
													minValue : 0,
													viewWindow : {
														min : 0
													},
													format : '0',
												},
												colors : [ 'orange', 'blue','gray','pink','black','red','green' ],
												theme : 'material'
											};
											var chart = new google.visualization.ColumnChart(
													document
															.getElementById('emp_attn_graph'));

											chart.draw(dataTable, options);
										}

									});
					
					
					
					
					$
					.getJSON(
							'${getEmpAdvanceGraph}',

							{
								empId : empId,
								toDate:toDate,
								fromDate:fromDate,
								ajax : 'true'

							},
							function(data) {

								google.charts.load('current', {
									'packages' : [ 'corechart' ]
								});
								google.charts
										.setOnLoadCallback(drawChart);

								function drawChart() {
									 
									var dataTable = new google.visualization.DataTable();

									dataTable.addColumn('string',
											'Month-Year'); // Implicit domain column.

									dataTable.addColumn('number',
											'Advance Amt');
									 
									
									

									$
											.each(
													data,
													function(key, dt) {

														dataTable
																.addRows([

																[
																		dt.date,
																		dt.advanceAmt,
																		]

																]);

													})

									/* slantedTextAngle: 60 */
									var options = {
										hAxis : {
											title : "Month Year",
											textPosition : 'out',
											slantedText : true
										},
										vAxis : {
											title : 'Advance Amt',
											minValue : 0,
											viewWindow : {
												min : 0
											},
											format : '0',
										},
										colors : [ 'blue' ],
										theme : 'material'
									};
									var chart = new google.visualization.ColumnChart(
											document
													.getElementById('emp_adv_graph'));

									chart.draw(dataTable, options);
								}

							});
					
					
					
					$
					.getJSON(
							'${getEmpLoanGraph}',

							{
								empId : empId,
								toDate:toDate,
								fromDate:fromDate,
								ajax : 'true'

							},
							function(data) {

								google.charts.load('current', {
									'packages' : [ 'corechart' ]
								});
								google.charts
										.setOnLoadCallback(drawChart);

								function drawChart() {
									 
									var dataTable = new google.visualization.DataTable();

									dataTable.addColumn('string',
											'Month-Year'); // Implicit domain column.

									dataTable.addColumn('number',
											'Loan Amt');
									  

									$
											.each(
													data,
													function(key, dt) {

														dataTable
																.addRows([

																[
																		dt.date,
																		dt.loanAmt,
																		]

																]);

													})

									/* slantedTextAngle: 60 */
									var options = {
										hAxis : {
											title : "Month Year",
											textPosition : 'out',
											slantedText : true
										},
										vAxis : {
											title : 'Loan Amount',
											minValue : 0,
											viewWindow : {
												min : 0
											},
											format : '0',
										},
										colors : [ 'blue' ],
										theme : 'material'
									};
									var chart = new google.visualization.ColumnChart(
											document
													.getElementById('emp_loan_graph'));

									chart.draw(dataTable, options);
								}

							});
					
	}
			 
				 
		</script>



</body>
</html>