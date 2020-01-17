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
								<td width="60%"><h5 class="card-title">Change Claim
										Paid Date</h5></td>
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



						<table
							class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
							id="printtable1">
							<thead>
								<tr class="bg-blue">
									<th width="10%">Sr.no</th>
									<th>Employee Code</th>
									<th>Employee Name</th>
									<th>Claim Title</th>
									<th>Claim Date</th>
									<th>Claim Amount</th>
									<th>Paid Date</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>


								<c:forEach items="${claimList1}" var="claimList"
									varStatus="count">

									<tr>
										<td>${count.index+1}</td>
										<td>${claimList.claimTitle}</td>
										<td>${claimList.exVar3}</td>
										<td>${claimList.exVar1}</td>
										<td>${claimList.cafromDt} to ${claimList.caToDt}</td>
										<td>${claimList.claimAmount}</td>
										<td>${claimList.month}-${claimList.year}</td>

										<td class="text-center"><c:if test="${editAccess == 0}">
												<a href="#"
													onclick="chkAssign('${claimList.caHeadId}','${claimList.claimTitle}','${claimList.claimAmount}','${claimList.exVar1}','${claimList.exVar3}','${claimList.cafromDt}','${claimList.caToDt}')"
													title="Edit"><i class="icon-pencil7"
													style="color: black;" data-toggle="modal"
													data-target="#modal_edit"></i></a>
											</c:if></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
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
	<div id="modal_scrollable" class="modal fade" data-backdrop="false"
		tabindex="-1">

		<div class="modal-dialog modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header pb-3">

					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<form action="${pageContext.request.contextPath}/updateClmPaidDate"
					id="submitInsertLeave" method="post">
					<div class="modal-body py-0">
						<h5 class="modal-title">Change Claim Paid Date</h5>
						<br> <input type="hidden" name="clmHeadId" id="clmHeadId"
							value="0">
						<div class="form-group row">
							<label class="col-form-label col-lg-3" for="empCode1">
								Employee Code : </label> <label class="col-form-label col-lg-2"
								id="empCode1" for="empCode1"> </label>

						</div>
						<div class="form-group row">
							<label class="col-form-label col-lg-3" for="empName1">
								Employee Name : </label> <label class="col-form-label col-lg-6"
								id="empName1" for="empName1"> </label>

						</div>

						<div class="form-group row">
							<label class="col-form-label col-lg-3" for="noOfDays">
								Claim Title : </label> <label class="col-form-label col-lg-3"
								id="claimTitle1" for="claimTitle1"> </label>

						</div>

						<div class="form-group row">
							<label class="col-form-label col-lg-3" for="noOfDays">
								Claim Date : </label> <label class="col-form-label col-lg-3"
								id="claimDate1" for="claimDate1"> </label>

						</div>
						<div class="form-group row">
							<label class="col-form-label col-lg-3" for="noOfDays">
								Claim Amount : </label> <label class="col-form-label col-lg-3"
								id="claimAmt1" for="claimAmt1"> </label>

						</div>


						<div class="form-group row">
							<label class="col-form-label col-lg-3"> Date : </label>
							<div class="col-lg-3">
								<input type="text" class="form-control datepickerclass"
									placeholder="Enter Work Date" id="workDate1" name="workDate1"
									autocomplete="off">
							</div>
						</div>

					</div>

					<div class="modal-footer pt-3">
						<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
						<button type="button" class="btn bg-primary" id="submtbtn"
							onclick="submitForm()">Submit</button>
					</div>

				</form>
			</div>
		</div>
	</div>

	<script>
		function submitForm() {
			$('#modal_scrollable').modal('hide');
			document.getElementById("submtbtn").disabled = true;
			document.getElementById("submitInsertLeave").submit();

		}
	</script>
	<script type="text/javascript">
		function chkAssign(id, title, amt, empName, empCode, fromDate, toDate) {
			//alert(1);
			document.getElementById("clmHeadId").value = id;
			$('#claimAmt1').html(amt);
			$('#empCode1').html(empCode);
			$('#empName1').html(empName);
			$('#claimTitle1').html(title);
			$('#claimDate1').html(fromDate + " To " + toDate);
			$('#modal_scrollable').modal('show');

		}
	</script>
	<!-- /page content -->

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
</body>
</html>