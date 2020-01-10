<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
						<h5 class="card-title">Generate Payroll ${date}</h5>
						<!-- <div class="header-elements">
							<div class="list-icons">
								<a class="list-icons-item" data-action="collapse"></a>
							</div> 
						</div>-->
					</div>

					<div class="card-body">

						<form action="${pageContext.request.contextPath}/viewDynamicValue"
							id="submitInsertLeave" method="post">

							<input type="hidden" name="searchDate" id="searchDate"
								value="${date}" /> <input type="hidden" name="empIds"
								id="empIds" value="${empIds}" />
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
											<th class="text-center">EMP Code</th>
											<th width="20%" class="text-center">EMP Name</th>
											<th class="text-center">Basic</th>
											<th class="text-center">OT AMT</th>
											<th class="text-center">Fund</th>
											<th class="text-center">Gross Earning</th>
											<th class="text-center">Claim ADD</th>
											<th class="text-center">Adv</th>
											<th class="text-center">Loan</th>
											<th class="text-center">IT Ded</th>
											<th class="text-center">Pay Ded</th>
											<th class="text-center">PT</th>
											<th class="text-center">PF</th>
											<th class="text-center">ESIC</th>
											<th class="text-center">MLWF</th>
											<th class="text-center">Gross Ded</th>
											<th class="text-center">Performance Bonus</th>
											<th class="text-center">Net Salary</th>
										</tr>



									</thead>

									<tbody>
										<c:forEach items="${empList}" var="empList" varStatus="count">
											<tr>
												<td>${count.index+1}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empName}</td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.basicCal}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.otWages}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.fund}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.grossSalaryDytemp}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.miscExpAdd}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.advanceDed}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.loanDed}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.itded}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.payDed}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.ptDed}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.epfWages}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.esic}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.mlwf}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><c:set
														value="${empList.advanceDed+empList.loanDed+empList.itded+empList.payDed+empList.ptDed+empList.epfWages+empList.esic+empList.mlwf}"
														var="finalDed"></c:set> <fmt:formatNumber type="number"
														groupingUsed="false" value="${finalDed}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.performanceBonus}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.netSalary}"
														maxFractionDigits="2" minFractionDigits="2" /></td>

											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
							<br>
							<div class="text-center">

								<button type="submit" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn">
									Process Pay Roll <i class="icon-paperplane ml-2"></i>
								</button>

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
	</script>

	<script type="text/javascript">
		function editBonus(tempSalDaynamicId) {

			var fd = new FormData();
			fd.append('tempSalDaynamicId', tempSalDaynamicId);

			$
					.ajax({
						url : '${pageContext.request.contextPath}/editBonus',
						type : 'post',
						dataType : 'json',
						data : fd,
						contentType : false,
						processData : false,
						success : function(response) {

							document.getElementById("editBonusDiv").style.display = 'block';
							document.getElementById("tempSalDaynamicId").value = response.id;
							document.getElementById("empCode").value = response.empCode;
							document.getElementById("itAmt").value = response.itded;
							document.getElementById("perBonus").value = response.performanceBonus;
						},
					});

		}
		function closeEditDetailTab() {

			document.getElementById("editBonusDiv").style.display = 'none';
		}

		function saveBonusDetail() {

			var tempSalDaynamicId = document
					.getElementById("tempSalDaynamicId").value;
			var itAmt = document.getElementById("itAmt").value;
			var perBonus = document.getElementById("perBonus").value;
			var flag = 0;
			if (itAmt == "") {
				alert("Enter IT Ammount");
				flag = 1;
			} else if (perBonus == "") {
				alert("Enter Bonus Ammount");
				flag = 1;
			}

			if (flag == 0) {
				var fd = new FormData();
				fd.append('tempSalDaynamicId', tempSalDaynamicId);
				fd.append('itAmt', itAmt);
				fd.append('perBonus', perBonus);

				$.ajax({
					url : '${pageContext.request.contextPath}/saveBonusDetail',
					type : 'post',
					dataType : 'json',
					data : fd,
					contentType : false,
					processData : false,
					success : function(response) {

						location.reload(true);
					},
				});
			}
		}
	</script>
</body>
</html>