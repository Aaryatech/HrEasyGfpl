<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

				<!-- Form validation -->
				<div class="row">
					<div class="col-md-12">
						<!-- Title -->
						<!-- <div class="mb-3">
							<h6 class="mb-0 font-weight-semibold">Hidden labels</h6>
							<span class="text-muted d-block">Inputs with empty values</span>
						</div> -->
						<!-- /title -->


						<div class="card">

							<div class="card-header header-elements-inline">
								<table width="100%">
									<tr width="100%">
										<td width="60%"><h5 class="card-title">Foreclose
												Loan</h5></td>
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

								<form
									action="${pageContext.request.contextPath}/submitLoanForeClose"
									id="submitData" method="post">
									<input type="hidden" value="${empPersInfo.empId}" id="empId"
										name="empId"> <input type="hidden"
										value="${advList.id}" id="id" name="id">


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="locId">
											Emp Name: </label> <label class="col-form-label col-lg-2" for="locId"
											style="color: red;">${empPersInfoString} </label> <label
											class="col-form-label col-lg-2" for="locId"> Total
											Gross Salary: : </label> <label class="col-form-label col-lg-2"
											for="locId" style="color: red;">
											${empPersInfo.grossSalary} </label>

									</div>




									<div class="form-group row">

										<label class="col-form-label col-lg-2" for="locId">
											Application No. : </label> <label class="col-form-label col-lg-2"
											for="locId" style="color: red;">${advList.loanApplNo}
										</label> <label class="col-form-label col-lg-2" for="locId">
											Loan Amount: </label> <label class="col-form-label col-lg-2"
											for="locId" style="color: red;">${advList.loanAmt} </label> <label
											class="col-form-label col-lg-2" for="locId"> Rate of
											Interest Amount: </label> <label class="col-form-label col-lg-2"
											for="locId" style="color: red;">${advList.loanRoi} </label>


									</div>

									<div class="form-group row">

										<label class="col-form-label col-lg-2" for="locId">
											Loan Tenure: </label> <label class="col-form-label col-lg-2"
											for="locId" style="color: red;">${advList.loanTenure}
										</label> <label class="col-form-label col-lg-2" for="locId">
											Start Date : </label> <label class="col-form-label col-lg-2"
											for="locId" style="color: red;">
											${advList.loanRepayStart} </label> <label
											class="col-form-label col-lg-2" for="locId"> End
											Date: </label> <label class="col-form-label col-lg-2" for="locId"
											style="color: red;"> ${advList.loanRepayEnd} </label>

									</div>


									<div class="form-group row">

										<label class="col-form-label col-lg-2" for="locId">
											Loan Repay Amount: </label> <label class="col-form-label col-lg-2"
											for="locId" style="color: red;">
											${advList.loanRepayAmt} </label> <label
											class="col-form-label col-lg-2" for="locId"> Paid
											Amount : </label> <label class="col-form-label col-lg-2" for="locId"
											style="color: red;"> ${advList.currentTotpaid} </label> <label
											class="col-form-label col-lg-2" for="locId">Outstanding
											Amount: </label> <label class="col-form-label col-lg-2" for="locId"
											style="color: red;"> ${advList.currentOutstanding} </label>


									</div>

									<div class="form-group row">

										<label class="col-form-label col-lg-2" for="locId">
											EMI Amount: </label> <label class="col-form-label col-lg-2"
											for="locId" style="color: red;"> ${advList.loanEmi} </label>
										<label class="col-form-label col-lg-2" for="locId">EMI+Interest
											: </label> <label class="col-form-label col-lg-2" for="locId"
											style="color: red;"> ${advList.loanEmiIntrest} </label>


									</div>

									<hr>
									
									
									<div class="col-md-12">
										<h6 class="card-title">Paid Status</h6>
										<div class="table-responsive">
											<table
												class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
												id="printtable2">
												<thead>
													<tr class="bg-blue">
														<th>Month</th>
														<th>Amount</th>
														<th>Type</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${laonDetalList}" var="laonDetalList"
														varStatus="count">
														<tr>
															<td>${laonDetalList.loginName}-${laonDetalList.years}</td>
															<td>${laonDetalList.amountEmi}</td>
															<td>${laonDetalList.payType}</td>
														</tr>
													</c:forEach>

												</tbody>
											</table>
										</div>
									</div>
									<hr>
									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="advanceAmt">Foreclose Amount
											  (Rs) <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly"
												placeholder="Enter  Amount" id="foreclose_amt" name="foreclose_amt"
												value="${advList.currentOutstanding}" autocomplete="off" onchange="calAmt()">  
										</div>
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="joiningDate"> 
											Date <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control datepickerclass "
												name="joiningDate" id="joiningDate" disabled="disabled"
												placeholder="Joining Date"> <span
												class="validation-invalid-label" id="error_joiningDate"
												style="display: none;">This field is required.</span>
										</div>
										
										
									</div>
									

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="remark">
											  Remark <span style="color: red">*</span>:
										</label>
										<div class="col-lg-4">
											<textarea class="form-control"
												placeholder="Enter Reason / Remark" id="reason"
												name="remark" autocomplete="off" onchange="trim(this)"></textarea>
											<span class="validation-invalid-label" id="error_reason"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
										<a
												href="${pageContext.request.contextPath}/showLoanListForAction?empId=${encEmpId}"><button
													type="button" class="btn btn-light">
													<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
													Back
												</button></a>
										</div>
									</div>
								</form>
								<p class="desc text-danger fontsize11">Notice : * Fields are
									mandatory.</p>
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

	 <script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			checkSame();
			return;
		}

		$(document).ready(function($) {

			$("#submitData").submit(function(e) {
 				var isError = false;
				var errMsg = "";

				if (!$("#reason").val()) {
 					isError = true;

					$("#error_reason").show()

				} else {
 					$("#error_reason").hide()
				}

				if (!isError) {

					$('#modal_scrollable')
					.modal('show');

						return false;
				}
				return false;
			});
		});
		//
	</script>
<script>
		function submitForm() {
			$('#modal_scrollable').modal('hide');
			document.getElementById("submtbtn").disabled = true;
			document.getElementById("submitData").submit();

		}
	</script>
		<div id="modal_scrollable" class="modal fade" data-backdrop="false"
		tabindex="-1">
		<div class="modal-dialog modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header pb-3">

					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<div class="modal-body py-0">
					<h5 class="modal-title">Are You Sure You Want  To Submit This Record </h5>
					<br>
				 
				</div>

				<div class="modal-footer pt-3">
					<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn bg-primary" onclick="submitForm()">Submit</button>
				</div>
			</div>
		</div>
	</div>

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