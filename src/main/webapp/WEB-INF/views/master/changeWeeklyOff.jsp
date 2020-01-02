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
								<label class="col-form-label col-lg-2" for="locId">Select
									Location <span style="color: red">* </span>:
								</label>
								<div class="col-lg-10">
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
							</div>

							<div class="form-group row">
								<label class="col-form-label col-lg-2" for="month">
									Month <span style="color: red"> </span>:
								</label>
								<div class="col-lg-4">
									<select name="month" data-placeholder="Select Deduction Type"
										id="month"
										class="form-control form-control-select21 select2-hidden-accessible1">
										<option value=''>Select Month</option>
										<option value="1" ${month == 1 ? 'selected' : ''}>Janaury</option>
										<option value='2' ${month == 2 ? 'selected' : ''}>February</option>
										<option value='3' ${month == 3 ? 'selected' : ''}>March</option>
										<option value='4' ${month == 4 ? 'selected' : ''}>April</option>
										<option value='5' ${month == 5 ? 'selected' : ''}>May</option>
										<option value='6' ${month == 6 ? 'selected' : ''}>June</option>
										<option value='7' ${month == 7 ? 'selected' : ''}>July</option>
										<option value='8' ${month == 8 ? 'selected' : ''}>August</option>
										<option value='9' ${month == 9 ? 'selected' : ''}>September</option>
										<option value='10' ${month == 10 ? 'selected' : ''}>October</option>
										<option value='11' ${month == 11 ? 'selected' : ''}>November</option>
										<option value='12' ${month == 12 ? 'selected' : ''}>December</option>
									</select> <span class="validation-invalid-label" id="error_month"
										style="display: none;">This field is required.</span>
								</div>



								<label class="col-form-label col-lg-2" for="month"> Year
									<span style="color: red"> </span>:
								</label>
								<div class="col-lg-4">
									<select name="year" data-placeholder="Select Year" id="year"
										class="form-control form-control-select21 select2-hidden-accessible1">
										<option value=''>Select Year</option>

										<option value='2018' ${year == 2018 ? 'selected' : ''}>2018</option>
										<option value='2019' ${year == 2019 ? 'selected' : ''}>2019</option>
										<option value='2020' ${year == 2020 ? 'selected' : ''}>2020</option>

									</select><span class="validation-invalid-label" id="error_year"
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

							<input type="hidden" id="locIdTemp" name="locIdTemp"
								value="${locId}"> <input type="hidden" id="monthTemp"
								name="monthTemp" value="${month}"> <input type="hidden"
								id="yearTemp" name="yearTemp" value="${year}"> <input
								type="hidden" id="tempDate" name="tempDate" value="0">




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
														onchange="setValue(this.value)" value="${dateList}"
														id="dateFrom${count.index+1}" id="dateFrom" />${dateList}

													</td>

												</tr>
											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
							<div class="form-group row"></div>

							<div class="form-group row">
								<label class="col-form-label col-lg-2" for="changeDate">
									Date <span style="color: red">* </span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control datepickerclass "
										name="changeDate" id="changeDate" placeholder="Change Date">
									<span class="validation-invalid-label" id="error_changeDate"
										style="display: none;">This field is required.</span>
								</div>
							</div>


							<div class="form-group row">
								<label class="col-form-label col-lg-2" for="remark">
									Reason <span style="color: red">*</span>:
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
		function setValue(datexy) {
			//alert(datexy);

			document.getElementById("tempDate").value = datexy;

		}
	</script>

	<script type="text/javascript">
		function search() {
			var count = $('#printtable2 tr').length;
			alert(count);
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
			checkSame();
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
	<!-- 
	<script type="text/javascript">
		$(document).ready(function($) {
			$("#getWeeklyOffs").submit(function(e) {

				var isError = false;
				var errMsg = "";
				if (!$("#locId").val()) {

					isError = true;

					$("#error_locId").show()

				} else {
					$("#error_locId").hide()
				}

				if (!$("#month").val()) {

					isError = true;

					$("#error_month").show()

				} else {
					$("#error_month").hide()
				}
				if (!$("#year").val()) {

					isError = true;

					$("#error_year").show()

				} else {
					$("#error_year").hide()
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
	</script> -->


	<!-- 
	<script type="text/javascript">
		$(document).ready(function($) {
			$("#submitInsertEmp").submit(function(e) {
				alert($("#changeReason").val());
				var isError = false;
				var errMsg = "";

				if (!$("#changeReason").val()) {
					alert(1);
					isError = true;

					$("#error_changeReason").show()

				} else {
					alert(0);
					$("#error_changeReason").hide()
				}

				if (!isError) {

					var x = true;
					if (x == true) {

						document.getElementById("subBtn").disabled = true;

						return true;
					}
					//end ajax send this to php page
				}
				return false;
			});
		});
	</script> -->




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