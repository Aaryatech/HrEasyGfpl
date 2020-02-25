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
						<table width="100%">
							<tr width="100%">
								<td width="60%"><h5 class="card-title">Change
										WeeklyOff</h5></td>
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
							action="${pageContext.request.contextPath}/showChangeWeekOff"
							id="getWeeklyOffs" method="get">



							<div class="form-group row">

								<%-- <label
									class="col-form-label text-info font-weight-bold col-lg-2"
									for="locId"> Select Weekoff Category <span
									class="text-danger">* </span>:
								</label>
								<div class="col-lg-4">
									<select name="holiCatId"
										data-placeholder="Select Weekoff Category" id="holiCatId"
										class="form-control form-control-select2 select2-hidden-accessible"
										data-fouc="" aria-hidden="true">
										<option value="">Select Weekoff Category</option>


										<c:forEach items="${holiList}" var="holiList">
											<c:choose>
												<c:when test="${holiList.woCatId==weekoffCatId}">
													<option selected value="${holiList.woCatId}">${holiList.woCatName}</option>
												</c:when>
												<c:otherwise>
													<option value="${holiList.woCatId}">${holiList.woCatName}</option>
												</c:otherwise>

											</c:choose>
										</c:forEach>

									</select> <span class="validation-invalid-label" id="error_holiCatId"
										style="display: none;">This field is required.</span>
								</div>
								
								<label
									class="col-form-label text-info font-weight-bold col-lg-2"
									for="locId">Select Location <span class="text-danger">*
								</span>:
								</label>
								<div class="col-lg-4">
									<select name="locId" data-placeholder="Select Location"
										id="locId" class="form-control form-control-sm select"
										data-container-css-class="select-sm" data-fouc>
										<option value="">Select Location</option>
										<c:forEach items="${locationList}" var="location">

											<c:choose>
												<c:when test="${location.locId==locId}">

													<option selected value="${location.locId}">${location.locName}</option>

												</c:when>
												<c:otherwise>
													<option value="${location.locId}">${location.locName}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</select> <span class="validation-invalid-label" id="error_locId"
										style="display: none;">This field is required.</span>
								</div>
 --%>


								<label
									class="col-form-label text-info font-weight-bold col-lg-2"
									for="empId">Employee <span class="text-danger">*</span>:
								</label>

								<div class="col-lg-4">
									<select name="empId" data-placeholder="Select  " id="empId"
										class="form-control form-control-select2 select2-hidden-accessible">

										<option value="">Select Employee</option>
										 
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
									for="month"> Month-Year <span class="text-danger">*</span>:
								</label>
								<div class="col-lg-4">
									<input type="text" name="monthyear" id="monthyear"
										class="form-control datepicker" value="${monthyear}"
										autocomplete="off" data-min-view-mode="months"
										data-start-view="1" data-format="mm-yyyy"> <span
										class="validation-invalid-label" id="error_monthyear"
										style="display: none;">This field is required.</span>
								</div>
							</div>


							<div style="text-align: center;">
								<input type="submit" class="btn btn-primary" value="Search"
									id="deleteId"
									style="align-content: center; width: 113px; margin-left: 40px;">
							</div>
						</form>

						<div class="form-group row"></div>

						<form
							action="${pageContext.request.contextPath}/submitInsertWeeklyOffChange"
							id="submitInsertLocaion" method="post">

							<input type="hidden" id="empIdTemp" name="empIdTemp"
								value="${empId}"> <input type="hidden" id="monthTemp"
								name="monthTemp" value="${month}"> <input type="hidden"
								id="yearTemp" name="yearTemp" value="${year}"> <input
								type="hidden" id="tempDate" name="tempDate" value="">  




							<div class="col-md-12">
								<div class="table-responsive">
									<table
										class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
										id="printtable2">
										<thead>
											<tr class="bg-blue">
												<th>Sr No.</th>
												<th>From Date</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${dateList}" var="dateList"
												varStatus="count">
												<tr>
													<td>${count.index+1}</td>
													<td><input type="radio" name="dateFrom"
														onchange="setValue(this.value,${count.index+1})"
														value="${dateList}" id="dateFrom${count.index+1}" />${dateList}

													</td>

												</tr>
											</c:forEach>

										</tbody>
									</table>


									<span class="validation-invalid-label" id="error_tempDate"
										style="display: none;">No Date Selected For Change</span>

								</div>
							</div>
							<div class="form-group row"></div>

							<div class="form-group row">
								<label
									class="col-form-label text-info font-weight-bold col-lg-2"
									for="changeDate"> Date <span class="text-danger">*
								</span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control datepickerclass "
										name="changeDate" id="changeDate" placeholder="Change Date">
									<span class="validation-invalid-label" id="error_changeDate"
										style="display: none;">This field is required.</span>
								</div>
							</div>


							<div class="form-group row">
								<label
									class="col-form-label text-info font-weight-bold col-lg-2"
									for="remark"> Reason <span class="text-danger">*</span>:
								</label>
								<div class="col-lg-4">
									<textarea class="form-control"
										placeholder="Enter Reason / Remark" id="remark" name="reason"
										autocomplete="off" onchange="trim(this)"> </textarea>
									<span class="validation-invalid-label" id="error_remark"
										style="display: none;">This field is required.</span>
								</div>
							</div>



							<div style="text-align: center;">
								<input type="submit" class="btn btn-primary" value="Submit"
									id="submtbtn"
									style="align-content: center; width: 113px; margin-left: 40px;">

								<a href="${pageContext.request.contextPath}/showWeekOffShift"><button
										type="button" class="btn btn-light">Back</button></a>
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
		function setValue(datexy,count) {
		//alert(datexy);
			if (document.getElementById('dateFrom'+count).checked) {
				//alert(1);
				document.getElementById("tempDate").value = datexy;
				
				
				$("#error_tempDate").hide()
			}else{
				$("#error_tempDate").show()
			}

		}
	</script>

	<script type="text/javascript">
		function search() {
			var count = $('#printtable2 tr').length;
			//alert(count);
			if (parseInt(count) > 0) {
				document.getElementById("submtbtn").disabled = false;

			} else {
				document.getElementById("submtbtn").disabled = true;
			}

		}
	</script>

	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			//checkSame();
			return;
		}

		$(document).ready(function($) {

			$("#submitInsertLocaion").submit(function(e) {

				var isError = false;
				var errMsg = "";

				if (!$("#remark").val()) {

					isError = true;

					$("#error_remark").show()

				} else {
					$("#error_remark").hide()
				}
				
				
				if (!$("#remark").val()) {

					isError = true;

					$("#error_remark").show()

				} else {
					$("#error_remark").hide()
				}
				
 
				if (!$("#changeDate").val()) {

					isError = true;

					$("#error_changeDate").show()

				} else {
					$("#error_changeDate").hide()
				}
				
				
				if (!$("#tempDate").val()) {

					isError = true;

					$("#error_tempDate").show()

				} else {
					$("#error_tempDate").hide()
				}

				
				
			/* 	
				var isVisible = $('#error_tempDate').is(':visible');
				 
				if (isVisible == true) {
					  
					isError = true;
			    }  
				
				
				 */
				
				
				
				
				if (!isError) {

					var x = true;
					if (x == true) {

						document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//end ajax send this to php page
				}
				return false;
			});
		});
		//
	</script>

	<script type="text/javascript">
		$(document).ready(function($) {
			$("#getWeeklyOffs").submit(function(e) {

				var isError = false;
				var errMsg = "";
				  if (!$("#empId").val()) {

					isError = true;

					$("#error_empId").show()

				} else {
					$("#error_empId").hide()
				}  
				 
				if (!$("#monthyear").val()) {

					isError = true;

					$("#error_monthyear").show()

				} else {
					$("#error_monthyear").hide()
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
		// Single picker
		$('.datepickerclass').daterangepicker({
		    "autoUpdateInput": false,
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		}, function (start_date) {
		    $(this.element).val(start_date.format('DD-MM-YYYY'));
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
		
		 $(document).ready(function() {
		        // month selector
		        $('.datepicker').datepicker({
		            autoclose: true,
		            format: "mm-yyyy",
		            viewMode: "months",
		            minViewMode: "months"

		        });


		    });
	</script>

</body>
</html>