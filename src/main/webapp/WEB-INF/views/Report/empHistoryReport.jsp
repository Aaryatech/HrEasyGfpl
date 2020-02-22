<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<c:url var="empInfoHistoryReportList" value="/empInfoHistoryReportList" />
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

			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">

					<div class="card-header header-elements-inline">
						<table width="100%">
							<tr width="100%">
								<td width="60%">
									<h5 class="card-title">Employee Leave History</h5>
								</td>
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
								session.removeAttribute("errorMsg");
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
								session.removeAttribute("successMsg");
							%>
						</div>
						<%
							session.removeAttribute("successMsg");
							}
						%>

						<form method="post" id="reportForm">
							<div class="form-group row">
								<label class="col-form-label text-info font-weight-bold col-lg-2" for="select2">Select
									Employee <span style="color: red">* </span> :
								</label>
								<div class="col-md-2">
									<select name="empId" data-placeholder="Select Employee"
										id="empId"
										class="form-control form-control-select2 select2-hidden-accessible"
										tabindex="-1" aria-hidden="true">
										<option value="">Select Employee</option>
										<option value="-1">All</option>
										<c:forEach items="${employeeInfoList}" var="empInfo">
											<option value="${empInfo.empId}">${empInfo.surname}
												${empInfo.firstName}</option>
										</c:forEach>
									</select> <span class="validation-invalid-label" id="error_empId"
										style="display: none;">This field is required.</span>
								</div>


								<label class="col-form-label text-info font-weight-bold col-lg-2" for="select2">Select
									Year <span style="color: red">* </span> :
								</label>
								<div class="col-md-2">
									<select name="calYrId" data-placeholder="Select Year"
										id="calYrId"
										class="form-control form-control-select2 select2-hidden-accessible"
										tabindex="-1" aria-hidden="true">
										<option value="">Select Calendar Year</option>
										<c:forEach items="${calYearList}" var="calYear">
											<option value="${calYear.calYrId}">${calYear.calYrFromDate}
												to ${calYear.calYrToDate}</option>
										</c:forEach>
									</select> <span class="validation-invalid-label" id="error_calYrId"
										style="display: none;">This field is required.</span>
								</div>




								<!-- <button type="button" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn" onclick="show()">
									Submit <i class="icon-paperplane ml-2"></i>
								</button>
								
								
 -->

								<button type="button" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn"
									onclick="getProgReport(1,'showEmpLeaveHistoryRep')">
									PDF <i class="icon-paperplane ml-2"></i>
								</button>

								<button type="button" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn"
									onclick="getProgReport(0,'showEmpLeaveHistoryRep')">
									Excel <i class="icon-paperplane ml-2"></i>
								</button>
							</div>
							<div id='loader' style='display: none;'>
								<img
									src='${pageContext.request.contextPath}/resources/assets/images/giphy.gif'
									width="150px" height="150px"
									style="display: block; margin-left: auto; margin-right: auto">
							</div>
							<!-- <table
								class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
								id="bootstrap-data-table">
								<thead>
									<tr class="bg-blue">
										<th width="10%">Sr.no</th>
										<th>Leave Type</th>
										<th>Carry Forward</th>
										<th>Earned</th>
										<th>Approved</th>
										<th>Applied</th>
										<th>Balanced</th>


									</tr>
								</thead>
							</table> -->
							<input type="hidden" id="p" name="p" value="0"> <input
								type="hidden" id="cal_yr" name="cal_yr" value="0">



						</form>
					</div>

				</div>
				<!-- /highlighting rows and columns -->


				<!-- /content area -->
			</div>

			<!-- Footer -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
			<!-- /footer -->

		</div>
		<!-- /main content -->

	</div>
	<!-- /page content -->


	<script type="text/javascript">
		//use this function for all reports just get mapping form action name dynamically as like of prm from every report pdf,excel function	
		function getProgReport(prm, mapping) {
			if (prm == 1) {
				document.getElementById("p").value = "1";
			}

			if ($("#calYrId").length > 0) {
				var elm = document.getElementById('calYrId');
				var text = elm.options[elm.selectedIndex].innerHTML;
				document.getElementById("cal_yr").value = text;
			}

			var form = document.getElementById("reportForm");

			form.setAttribute("target", "_blank");
			form.setAttribute("method", "post");

			form.action = ("${pageContext.request.contextPath}/" + mapping + "/");

			form.submit();

			document.getElementById("p").value = "0";
		}
	</script>
	<!-- <script type="text/javascript">
		function show() {

			//alert("Hi View Orders  ");

			var empId = document.getElementById("empId").value;
			var calYrId = document.getElementById("calYrId").value;
			//var toDate = document.getElementById("to_date").value;

			//alert(compId);

			var valid = true;

			if (empId == null || empId == "") {
				valid = false;
				alert("Please Select Employee");
			}

			var valid = true;
			if (calYrId == null || calYrId == "") {
				valid = false;
				alert("Please Select Year");

				var dataTable = $('#bootstrap-data-table').DataTable();
				dataTable.clear().draw();

			}
			$("#loader").show();

			if (valid == true) {

				$.getJSON('${empInfoHistoryReportList}', {
					empId : empId,
					calYrId : calYrId,
					ajax : 'true',
				},

				function(data) {

					var dataTable = $('#bootstrap-data-table').DataTable();
					dataTable.clear().draw();

					$.each(data, function(i, v) {
						
					 
						var str=v.balLeave + v.lvsAllotedLeaves
						- v.sactionLeave
						- v.aplliedLeaeve ;
						dataTable.row.add(
								[
										i + 1,
										v.lvTitle,
										v.balLeave.toFixed([2]),
										v.lvsAllotedLeaves.toFixed([2]),
										v.sactionLeave.toFixed([2]),
										v.aplliedLeaeve.toFixed([2]),
										str.toFixed([2])
										]).draw();
					});
					$("#loader").hide();

				});

			}//end of if valid ==true

		}
	</script> -->
	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			return;
		}

		function validateEmail(email) {

			var eml = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

			if (eml.test($.trim(email)) == false) {

				return false;

			}

			return true;

		}
		function validateMobile(mobile) {
			var mob = /^[1-9]{1}[0-9]{9}$/;

			if (mob.test($.trim(mobile)) == false) {

				//alert("Please enter a valid email address .");
				return false;

			}
			return true;

		}
		$(document).ready(function($) {

			$("#submtbtn").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#clYrId").val()) {

					isError = true;

					$("#error_clYrId").show()

				} else {
					$("#error_clYrId").hide()
				}

				if (!$("#empId").val()) {

					isError = true;

					$("#error_empId").show()

				} else {
					$("#error_empId").hide()
				}

				/* if (!$("#calYrId").val()) {

					isError = true;

					$("#error_calYrId").show()

				} else {
					$("#error_calYrId").hide()
				} */

				if (!$("#dateRange").val()) {

					isError = true;

					$("#error_Range").show()

				} else {
					$("#error_Range").hide()
				}

				if (!isError) {

					var x = confirm("Do you really want to submit the form?");
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
</body>
</html>