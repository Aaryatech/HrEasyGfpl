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
								<td width="60%"><h5 class="card-title">Employee Shift
										Projection</h5></td>
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
							action="${pageContext.request.contextPath}/showEmpShiftDetails"
							id="showEmpListToAssignShift" method="get">

							<div class="form-group row">
								<label
									class="col-form-label text-info font-weight-bold col-lg-2"
									for="empId">Employee <span class="text-danger">*</span>:
								</label>

								<div class="col-lg-4">
									<select name="empId" data-placeholder="Select  " id="empId"
										class="form-control form-control-select2 select2-hidden-accessible">

										<option value="">Select Employee</option>
										<c:set var="selflag" value="0"></c:set>
										<c:if test="${empId==-1}">
											<option selected value="-1">All</option>
											<c:set var="selflag" value="1"></c:set>
										</c:if>
										<c:if test="${selFlag==0}">
 											<option value="-1">All</option>
										</c:if>
										<c:forEach items="${employeeInfoList}" var="empInfo">

											<c:choose>
												<c:when test="${empInfo.empId==empId}">
													<option selected value="${empInfo.empId}">${empInfo.surname}
														${empInfo.firstName} [${empInfo.empCode}]
														[${empInfo.empDesgn}]</option>
												</c:when>
												<c:otherwise>
													<option value="${empInfo.empId}">${empInfo.surname}
														${empInfo.firstName} [${empInfo.empCode}]
														[${empInfo.empDesgn}]</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select> <span class="validation-invalid-label" id="error_empId"
										style="display: none;">This field is required.</span>
								</div>



								<label
									class="col-form-label text-info font-weight-bold col-lg-2"
									for="select2">Select Date <span class="text-danger">*
								</span> :
								</label>
								<div class="col-md-2">
									<select name="date" data-placeholder="Select Employee"
										id="date" class="form-control form-control-select2 "
										tabindex="-1" aria-hidden="true">
										<c:set var="flag" value="0"></c:set>
										<c:if test="${date==1}">
											<option selected value="1">${today}</option>
											<c:set var="flag" value="1"></c:set>
										</c:if>
										<c:set var="flag1" value="0"></c:set>
										<c:if test="${date==2}">
											<option selected value="2">${nextMonthDay}</option>
											<c:set var="flag1" value="1"></c:set>
										</c:if>
										<c:if test="${flag==0}">
											<option value="1">${today}</option>
										</c:if>
										<c:if test="${flag1==0}">
											<option value="2">${nextMonthDay}</option>
										</c:if>
									</select> <span class="validation-invalid-label" id="error_empId"
										style="display: none;">This field is required.</span>
								</div>



								<!-- 	<label class="col-form-label col-lg-2" for="date">Select
									Date <span style="color: red">* </span> :
								</label>
								<div class="col-md-3">
									<input type="text" class="form-control "
										placeholder="Select Date " id="datepicker" name="date"
										autocomplete="off"> <span
										class="validation-invalid-label" id="error_month"
										style="display: none;">This field is required.</span>
								</div> -->




								<button type="submit" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn">
									Search <i class="icon-paperplane ml-2"></i>
								</button>

							</div>


							<%-- 
						<div class="form-group row">
							<label class="col-form-label col-lg-2" for="locId">
								Select Shift   <span style="color: red">* </span>:
							</label>
							<div class="col-lg-10">
								<select name="shiftId" data-placeholder="Select Shift"
									id="shiftId"
									class="form-control form-control-select2 select2-hidden-accessible"
									data-fouc="" aria-hidden="true">

									<option value="">Select Shift</option>

									<c:forEach items="${shiftList}" var="shiftList">
										<option value="${shiftList.id}">${shiftList.shiftname}</option>
									</c:forEach>
								</select> <span class="validation-invalid-label" id="error_shiftId"
									style="display: none;">This field is required.</span>
							</div>

						</div> --%>


						</form>

						<div class="table-responsive">
							<table
								class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
								id="bootstrap-data-table">
								<thead>
									<tr class="bg-blue">

										<th width="10%">Sr.no</th>
										<th>Employee Code</th>
										<th>Employee Name</th>
										<c:forEach begin="1" end="${days}" step="1" varStatus="count">
											<th>${count.index}</th>
										</c:forEach>
									</tr>
								</thead>
								<tbody>

									<c:forEach items="${empList}" var="empList" varStatus="count1">
										<tr>
											<td>${count1.index+1}</td>
											<td>${empList.empCode}</td>
											<td>${empList.firstName}${empList.surname}</td>
											<c:forEach begin="0" end="${days}" step="1"
												varStatus="count3">
												<c:forEach items="${daysList}" var="daysList"
													varStatus="count2">

													<c:if
														test="${daysList.empId==empList.empId && daysList.day==count3.index+1  && monthVal==daysList.month}">
														<td>${daysList.shiftName}</td>
													</c:if>
												</c:forEach>
											</c:forEach>
										</tr>

									</c:forEach>


								</tbody>
							</table>
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
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : "-50:+50",
			dateFormat : "mm-yy"
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function($) {
			$("#submitInsertEmp").submit(function(e) {

				var isError = false;
				var errMsg = "";
				var shiftId = $("#shiftId").val();

				var checked = $("#submitInsertEmp input:checked").length > 0;
				if (!checked) {
					$("#error_chk").show()
					isError = true;
				} else {
					$("#error_chk").hide()
					isError = false;
				}
				//alert("checked" +checked);
				if (shiftId == null || shiftId == "") {
					isError = true;
					$("#error_shiftId").show()
				} else {
					$("#error_shiftId").hide()
				}

				if (!isError) {

					var x = true;
					if (x == true) {

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