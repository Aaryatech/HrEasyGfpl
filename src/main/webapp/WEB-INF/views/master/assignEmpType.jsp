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
						<table width="100%">
							<tr width="100%">
								<td width="60%"><h5 class="card-title">Employee
										Type Assignment</h5></td>
								<td width="40%" align="right"></td>
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
							action="${pageContext.request.contextPath}/submitAssignEmpTypeToEmp"
							id="submitInsertEmp" method="post">

							<div class="form-group row">

								<label
									class="col-form-label text-info font-weight-bold col-lg-2"
									for="empType">Emp Type <span class="text-danger">*</span>:
								</label>
								<div class="col-lg-4">
									<select name="empType" data-placeholder="Select Employee Type"
										id="empType"
										class="form-control form-control-select2 select2-hidden-accessible">
										<option value="">Select Employee Type</option>
										<c:forEach items="${empTypeList}" var="empTypeList">
											<option value="${empTypeList.empTypeId}">${empTypeList.name} </option>
										</c:forEach>

									</select> <span class="hidedefault   validation-invalid-label"
										style="display: none;" id="error_empType">This field is
										required.</span>
								</div>
							</div>

							<div class="table-responsive">
								<table class="table datatable-scroll-y" width="100%"
									id="printtable1">
									<thead>
										<tr class="bg-blue">

											<th width="10%">Sr.no</th>

											<th><input type="checkbox" name="selAll" id="selAll" /></th>
											<th>Employee Code</th>
											<th>Employee Name</th>
											<th>Employee Type</th>
											<th>Department</th>
											<th>Designation</th>
											<th>Location</th>
											<th>Company</th>


										</tr>
									</thead>
									<tbody>


										<c:forEach items="${empdetList}" var="empdetList"
											varStatus="count">
											<tr>

												<td>${count.index+1}</td>
												<td><input type="checkbox"
													id="empId${empdetList.empId}" value="${empdetList.empId}"
													name="empId" class="select_all"></td>
												<td>${empdetList.empCode}</td>
												<td>${empdetList.surname}&nbsp;${empdetList.middleName}&nbsp;${empdetList.firstName}</td>
												<td>${empdetList.empTypeName}</td>
												<td>${empdetList.deptName}</td>
												<td>${empdetList.empDesgn}</td>
												<td>${empdetList.locName}</td>
												<td>${empdetList.subCompName}</td>

											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
							<br /> <span class="validation-invalid-label" id="error_chk"
								style="display: none;">Please Select the Employee.</span>


							<div style="text-align: center;">
								<input type="submit" class="btn btn-primary" value="Assign"
									id="deleteId"
									style="align-content: center; width: 113px; margin-left: 40px;">
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
		$(document).ready(function($) {
			$("#submitInsertEmp").submit(function(e) {

				var isError = false;
				var errMsg = "";
				var empType = $("#empType").val();

				var checked = $("#submitInsertEmp input:checked").length > 0;
				if (!checked) {
					$("#error_chk").show()
					isError = true;
				} else {
					$("#error_chk").hide()
					isError = false;
				}
				//alert("checked" +checked);
				if (empType == null || empType == "") {
					isError = true;
					$("#error_empType").show()
				} else {
					$("#error_empType").hide()
				}

				if (!isError) {

					var x = true;
					if (x == true) {
						var table = $('#printtable1').DataTable();
						table.search("").draw();
						document.getElementById("deleteId").disabled = true;

						return true;
					}
					//end ajax send this to php page
				}
				return false;
			});
		});
	</script>

	<script type="text/javascript">
		$(document).ready(
				function() {
					//	$('#printtable').DataTable();

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);
							});
				});
	</script>

</body>
</html>