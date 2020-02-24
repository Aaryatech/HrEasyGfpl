<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<script
	src="${pageContext.request.contextPath}/resources/global_assets/js/demo_pages/components_modals.js"></script>

</head>
<style>
* {
	box-sizing: border-box;
}

.myInput {
	background-image: url('https://www.w3schools.com/css/searchicon.png');
	background-position: 8px 7px;
	background-repeat: no-repeat;
	width: 20%;
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
			<%-- <div class="page-header page-header-light">


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
						<a href="${pageContext.request.contextPath}/holidayAdd"
							class="breadcrumb-elements-item"> Add Holiday </a>

					</div>


				</div>
			</div> --%>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">

				<div class="card">


					<div class="card-header header-elements-inline">
						<table width="100%">
							<tr width="100%">
								<td width="60%"><h5 class="card-title">Leave Structure
										Allotment List</h5></td>
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
						<ul class="nav nav-tabs nav-tabs-highlight nav-justified1">
							<li class="nav-item"><a href="#highlighted-justified-tab1"
								class="nav-link active" data-toggle="tab">Allocation Pending</a></li>
							<li class="nav-item"><a href="#highlighted-justified-tab2"
								class="nav-link" data-toggle="tab">Allocated</a></li>

						</ul>

						<div class="tab-content">
							<div class="tab-pane fade show active"
								id="highlighted-justified-tab1">
								<form
									action="${pageContext.request.contextPath}/submitStructureList"
									method="post" id="assignstuct">

									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="lvsId">Select Structure <span style="color: red">*
										</span>:
										</label>
										<div class="col-lg-10">
											<select name="lvsId" data-placeholder="Select Structure"
												id="lvsId"
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true">
												<option></option>
												<c:forEach items="${lStrList}" var="str">

													<option value="${str.lvsId}" id="${str.lvsId}"
														data-leavestrname="${str.lvsName}">${str.lvsName}</option>


												</c:forEach>
											</select> <span class="validation-invalid-label" id="error_lvsId"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<table class="table datatable-scroll-y" width="100%"
										id="printtable1">
										<thead>
											<tr class="bg-blue">
												<th class="check" style="text-align: center; width: 5%;"><input
													type="checkbox" name="selAll" id="selAll" /></th>

												<th width="5%">Sr. No.</th>
												<th width="10%">Employee Code</th>
												<th>Employee Name</th>
												<th>Department</th>
												<th>Designation</th>

											</tr>
										</thead>
										<tbody>
											<c:set var="index" value="0"></c:set>
											<c:forEach items="${lvStructureList}" var="structure"
												varStatus="count">

												<c:set var="countOf" value="0"></c:set>
												<c:forEach items="${calAllotList}" var="calender"
													varStatus="count1">
													<c:if test="${calender.empId == structure.empId}">
														<c:set var="countOf" value="1"></c:set>
													</c:if>


												</c:forEach>
												<c:choose>
													<c:when test="${countOf==1}">

													</c:when>
													<c:otherwise>
														<tr>


															<td><input type="checkbox" class="chk" name="empIds"
																id="empIds${structure.empId}" value="${structure.empId}"
																data-empcode="${structure.empCode}"
																data-name="${structure.empSname} ${structure.empFname}"
																data-depname="${structure.empDeptName}" /></td>
															<td>${index+1}</td>
															<c:set var="index" value="${index+1}"></c:set>
															<td>${structure.empCode}</td>
															<td>${structure.empSname}&nbsp;${structure.empFname}</td>
															<td>${structure.empDeptName}</td>
															<td>${structure.empCatName}</td>
														</tr>
													</c:otherwise>
												</c:choose>
											</c:forEach>

										</tbody>
									</table>
									<span class="validation-invalid-label" id="error_table1"
										style="display: none;">Please select one employee.</span> <br>

									<div class="form-group text-center ">
										<input type="submit" class="btn btn-primary" value="Add"
											id="btnassignstuct">

									</div>



								</form>
							</div>

							<div class="tab-pane fade" id="highlighted-justified-tab2">


								<input type="text" id="myInput1" class="myInput"
									onkeyup="myFunction1()" placeholder="Search for employee.."
									title="Type in a name">
								<div class="table-responsive">
									<table
										class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
										id="printtable2">
										<thead>
											<tr class="bg-blue">

												<th width="5%">Sr. No.</th>
												<th width="10%">Employee Code</th>
												<th>Employee Name</th>
												<th>Department</th>
												<th>Designation</th>
												<th width="20%">Structure</th>



											</tr>
										</thead>
										<tbody>

											<c:set var="index" value="0"></c:set>
											<c:forEach items="${lvStructureList}" var="structure"
												varStatus="count">




												<c:set var="countOf" value="0"></c:set>
												<c:forEach items="${calAllotList}" var="calender"
													varStatus="count1">
													<c:if test="${calender.empId == structure.empId}">
														<c:set var="countOf" value="1"></c:set>
													</c:if>


												</c:forEach>



												<c:choose>
													<c:when test="${countOf==1}">
														<tr>
															<td>${index+1}</td>
															<c:set var="index" value="${index+1}"></c:set>
															<td>${structure.empCode}</td>
															<td>${structure.empSname}&nbsp;${structure.empFname}</td>
															<td>${structure.empDeptName}</td>
															<td>${structure.empCatName}</td>
															<td>${structure.lvsName}</td>

														</tr>
													</c:when>
												</c:choose>





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
		$(document).ready(
				function() {
					//$('#bootstrap-data-table-export').DataTable();

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);
							});
				});

		$(document)
				.ready(
						function($) {

							$("#assignstuct")
									.submit(
											function(e) {

												var table = $('#printtable1')
														.DataTable();
												table.search("").draw();
												$("#error_lvsId").hide();
												$("#error_table1").hide();

												var isError = false;
												var errMsg = "";

												if ($("#lvsId").val() == "") {

													isError = true;

													$("#error_lvsId").show();
													//return false;
												}

												var checkedVals = $(
														'.chk:checkbox:checked')
														.map(function() {
															return this.value;
														}).get();
												checkedVals = checkedVals
														.join(',');

												if (checkedVals == '') {
													$("#error_table1").show();
													return false;
												}

												if (!isError) {

													$("#table_grid1 tbody")
															.empty();

													$('.chk:checkbox:checked')
															.each(
																	function(i) {
																		var val = $(
																				this)
																				.val();

																		if (val != 'on') {
																			var name = $(
																					"#empIds"
																							+ val)
																					.attr(
																							'data-name');
																			var empcode = $(
																					"#empIds"
																							+ val)
																					.attr(
																							'data-empcode');
																			var dept = $(
																					"#empIds"
																							+ val)
																					.attr(
																							'data-depname');

																			var tr_data = '<tr id="tritem' + val + '">'
																					+ '<td id="itemCount' + val + '">'
																					+ empcode
																					+ '</td>'
																					+ '<td  >'
																					+ name
																					+ '</td> </tr>';
																			$(
																					'#table_grid1'
																							+ ' tbody')
																					.append(
																							tr_data);
																		}
																	});

													var option = $(
															"#lvsId option:selected")
															.attr(
																	"data-leavestrname");
													$("#showLeaveStruct").html(
															option);
													$('#modal_scrollable')
															.modal('show');
													return false;
													/* var x = confirm("Do you really want to submit the form?");
													if (x == true) {

														document.getElementById("btnassignstuct").disabled = true;
														return true;
													} */
													//end ajax send this to php page
												}
												return false;
											});
						});
	</script>
	<script>
		function submitForm() {
			$('#modal_scrollable').modal('hide');
			document.getElementById("btnassignstuct").disabled = true;
			document.getElementById("assignstuct").submit();

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
	<!-- Scrollable modal -->
	<div id="modal_scrollable" class="modal fade" data-backdrop="false"
		tabindex="-1">
		<div class="modal-dialog modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header pb-3">

					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<div class="modal-body py-0">
					<h5 class="modal-title">
						Allocated Leave Structure: <b><span id="showLeaveStruct"></span></b>
					</h5>
					<br>
					<table class="table table-bordered table-hover" id="table_grid1">
						<thead>
							<tr class="bgpink">
								<th width="5%">Code</th>

								<th>Employee Name</th>

							</tr>
						</thead>
						<tbody>

						</tbody>

					</table>
				</div>

				<div class="modal-footer pt-3">
					<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn bg-primary" onclick="submitForm()">Submit</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /scrollable modal -->
</body>
</html>