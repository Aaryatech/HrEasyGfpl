<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>
<style>
* {
	box-sizing: border-box;
}

.myInput {
	background-image: url('https://www.w3schools.com/css/searchicon.png');
	background-position: 8px 7px;
	background-repeat: no-repeat;
	width: 50%;
	font-size: 16px;
	padding: 5px 5px 5px 40px;
	border: 1px solid #ddd;
	margin-bottom: 12px;
}

#myTable {
	border-collapse: collapse;
	width: 100%;
	border: 1px solid #ddd;
	font-size: 18px;
}

#myTable th, #myTable td {
	text-align: left;
	padding: 12px;
}

#myTable tr {
	border-bottom: 1px solid #ddd;
}

#myTable tr.header, #myTable tr:hover {
	background-color: #f1f1f1;
}
</style>
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

				<%-- 
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
						<a href="${pageContext.request.contextPath}/leaveAuthorityList"
							class="breadcrumb-elements-item"> Authority List </a>

					</div>


				</div> --%>
			</div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">



					<div class="card-header header-elements-inline">
						<table width="100%">
							<tr width="100%">
								<td width="60%"><h5 class="card-title">Leave Authority</h5></td>
								<td width="40%" align="right"><a
									href="${pageContext.request.contextPath}/leaveAuthorityList"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">Authority
											List</button>
								</a></td>
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


						<form
							action="${pageContext.request.contextPath}/submitAuthorityList"
							method="post" id="frmAddLeaveAuthority">

							<div class="row">
								<div class="col-md-6">



									<table class="table datatable-scroll-y" width="100%"
										id="printtable1">
										<thead>
											<tr class="bg-blue">
												<th class="check" style="text-align: center; width: 5%;"><input
													type="checkbox" name="selAll" id="selAll" /></th>

												<th>Employee Name</th>
												<th>Employee Code</th>
												<th>Department</th>
												<th>Designation</th>


												<!-- <th>Designation</th> -->

											</tr>
										</thead>
										<tbody>


											<c:forEach items="${empListAuth}" var="emp" varStatus="count">
												<tr>
													<td class="text-center"><input type="checkbox"
														class="chk1" name="empIds" id="empIds${count.index+1}"
														value="${emp.empId}" /></td>
													<td>${emp.surname}&nbsp;${emp.firstName}</td>
													<td>${emp.empCode}</td>
													<td>${emp.deptName}</td>
 													<td>${emp.empDesgn}</td>

												</tr>
											</c:forEach>

										</tbody>
									</table>
									<span class="validation-invalid-label" id="error_table1"
										style="display: none;">Please select one employee.</span>
								</div>

								<div class="col-md-6">


									<table class="table datatable-scroll-y" width="100%"
										id="printtable2">
										<thead>
											<tr class="bg-blue">
												<th class="check" style="text-align: center;">Select
													Authority</th>

												<!-- <th width="10%">Sr. No.</th> -->
												<th>Employee Name</th>
												<th>Employee Code</th>
												<th>Department</th>
												<th>Designation</th>

												<!-- <th>Desgn</th> -->
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${empList}" var="emp" varStatus="count">
												<tr>
													<td><input type="radio" name="iniAuthEmpId"
														id="iniAuthEmpId${count.index+1}" value="${emp.empId}" />Initial
														<input type="radio" name="finAuthEmpId"
														id="finAuthEmpId${count.index+1}" value="${emp.empId}" />Final
														<input type="checkbox" class="chk" name="repToEmpIds"
														id="repToEmpIds${count.index+1}" value="${emp.empId}" />Reporting</td>

													<td>${emp.surname}&nbsp;${emp.firstName}</td>
													<td>${emp.empCode}</td>
													<td>${emp.deptName}</td>
													<td>${emp.empDesgn}</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>

								</div>
							</div>
							<br>
							<div class="col-md-12" style="text-align: center;">
								<input type="submit" class="btn btn-primary" value="Add"
									id="btnAddLeaveAuthority"
									style="align-content: center; width: 113px; margin-left: 40px;">
								<%-- <a href="${pageContext.request.contextPath}/leaveAuthorityList"><button
										type="button" class="btn btn-primary">
										<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
										Cancel
									</button></a> --%>
								<input type="hidden" name="intialChecked" id="intialChecked"
									value="0" />
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

	<script>
		function checkunckedinitial(index) {

			var value = document.getElementById("intialChecked").value;
			if (value == 0) {

				document.getElementById("iniAuthEmpId" + index).checked = true;
				document.getElementById("intialChecked").value = 1;
			} else {

				document.getElementById("iniAuthEmpId" + index).checked = false;
				document.getElementById("intialChecked").value = 0;
			}
		}
	</script>
	<script>
		function myFunction() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("myInput");
			filter = input.value.toUpperCase();
			table = document.getElementById("printtable1");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[2];
				if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}
	</script>
	<script>
		function myFunction1() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("myInput1");
			filter = input.value.toUpperCase();
			table = document.getElementById("printtable2");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[2];
				if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}
	</script>
	<script type="text/javascript">
		$(document).ready(
				function() {
					$('#bootstrap-data-table-export').DataTable();

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);
							});

				});
	</script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$("#frmAddLeaveAuthority")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";
												$("#error_table1").hide();
												//search
												// $("#frmAddLeaveAuthority :input[type='search']").val("");
												var table = $('#printtable1')
														.DataTable();
												table.search("").draw();
												var table = $('#printtable2')
														.DataTable();
												table.search("").draw();

												var checkedVals = $(
														'.chk1:checkbox:checked')
														.map(function() {
															return this.value;
														}).get();
												checkedVals = checkedVals
														.join(',');

												if (checkedVals == '') {
													$("#error_table1")
															.html(
																	"Please select one employee.");
													$("#error_table1").show();
													return false;
												}

												var off_payment_method = document
														.getElementsByName('iniAuthEmpId');
												var ischecked_method = false;
												for (var i = 0; i < off_payment_method.length; i++) {
													if (off_payment_method[i].checked) {
														ischecked_method = true;
														break;
													}
												}
												if (!ischecked_method) { //payment method button is not checked
													$("#error_table1")
															.html(
																	"Select one employee as Initial Authority.");
													$("#error_table1").show();
													return false;
												}

												var finAuthEmpId = document
														.getElementsByName('finAuthEmpId');
												var finAuthEmpId_method = false;
												for (var i = 0; i < finAuthEmpId.length; i++) {
													if (finAuthEmpId[i].checked) {
														finAuthEmpId_method = true;
														break;
													}
												}
												if (!finAuthEmpId_method) { //payment method button is not checked
													$("#error_table1")
															.html(
																	"Select one employee as Final Authority.");
													$("#error_table1").show();
													return false;
												}

												var checkedVals1 = $(
														'.chk:checkbox:checked')
														.map(function() {
															return this.value;
														}).get();
												checkedVals1 = checkedVals1
														.join(',');

												if (checkedVals1 == '') {

													$("#error_table1")
															.html(
																	"Select one employee as reporting.");
													$("#error_table1").show();
													return false;
												}

												//return false; //yaad rakhna to  remove it
												if (!isError) {

													var x = confirm("Do you really want to submit the form?");
													if (x == true) {

														document
																.getElementById("submtbtn").disabled = true;
														return true;
													}
													//end ajax send this to php page
												}
												return false;
											});

						});
	</script>
</body>
</html>