<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>
<c:url value="/loanCalculation" var="loanCalculation"></c:url>
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
										<td width="60%"><h5 class="card-title">Add Loan</h5></td>
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
									action="${pageContext.request.contextPath}/submitInsertAdvance"
									id="submitInsertLocaion" method="post">
									<input type="hidden" value="${empPersInfo.empId}" id="empId"
										name="empId">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="empName">Employee
											Name <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${empPersInfoString}" id="empName"
												readonly="readonly" name="empName" autocomplete="off"
												onchange="trim(this)">

										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="grossSal">
											Gross Salary <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" id="grossSal"
												value="${empPersInfo.grossSalaryEst}" readonly="readonly"
												name="grossSal" autocomplete="off" onchange="trim(this)">
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="grossSal">
											Previous Loan Unpaid Amount <span style="color: red">*
										</span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" id="grossSal"
												readonly="readonly" name="grossSal"
												value="${prevLoan.currentOutstanding}" autocomplete="off"
												onchange="trim(this)">
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="grossSal">
											Loan EMI Per Month <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" id="grossSal"
												readonly="readonly" name="grossSal"
												value="${prevLoan.loanEmi}" autocomplete="off"
												onchange="trim(this)">
										</div>
									</div>

									<hr>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="voucherNo">Loan
											Application No. <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" readonly="readonly"
												id="appNo" value="${appNo}" name="appNo" autocomplete="off">

										</div>

										<label class="col-form-label col-lg-2" for="voucherNo">
											Today <span style="color: red">* </span>:
										</label> <label class="col-form-label col-lg-2" for="voucherNo">
											${todaysDate} </label>
									</div>



									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="advanceAmt">Loan
											Amount Rs <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Loan Amount" id="loanAmt" name="loanAmt"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_loanAmt"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">Rate
											of Interest <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" id="roi" name="roi"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_roi"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">Loan
											Tenure In Month <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" id="tenure"
												name="tenure" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_tenure"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="dob">Start
											Date of Cutting <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control datepickerclass"
												id="startDate" name="startDate" autocomplete="off"
												onchange="trim(this)"> <span
												class="hidedefault  validation-invalid-label"
												id="error_startDate" style="display: none;">This
												field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="dob"> End
											Date of Cutting <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control datepickerclass1"
												id="endDate" name="endDate" autocomplete="off"
												onchange="trim(this)"> <span
												class="hidedefault  validation-invalid-label"
												id="error_endDate" style="display: none;">This field
												is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">Total
											Repay Amount <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" id="repayAmt"
												name="repayAmt" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_repayAmt"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">Loan
											EMI <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" id="emi" name="emi"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_emi"
												style="display: none;">This field is required.</span>
										</div>
									</div>





									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">
											Remark <span style="color: red">*</span>:
										</label>
										<div class="col-lg-4">
											<textarea class="form-control"
												placeholder="Enter Reason / Remark" id="remark"
												name="remark" autocomplete="off" onchange="trim(this)"> </textarea>
											<span class="validation-invalid-label" id="error_remark"
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
												href="${pageContext.request.contextPath}/showEmpListToAddAdvance"><button
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

	<script type="text/javascript">
		function calAmt() {

			//alert("Hi View Orders  ");

			var roi = document.getElementById("roi").value;
			var tenure = document.getElementById("tenure").value;
			var loanAmt = document.getElementById("loanAmt").value;

			//alert("empId  "+empId);
			//alert("calYrId "+calYrId);

			$.getJSON('${loanCalculation}', {
				roi : roi,
				tenure : tenure,
				loanAmt : loanAmt,
				ajax : 'true',
			},

			function(data) {

			});

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

				if (!$("#emi").val()) {

					isError = true;

					$("#error_emi").show()
					//return false;
				} else {
					$("#error_emi").hide()
				}

				if (!$("#repayAmt").val()) {

					isError = true;

					$("#error_repayAmt").show()

				} else {
					$("#error_repayAmt").hide()
				}
				if (!$("#remark").val()) {

					isError = true;

					$("#error_remark").show()

				} else {
					$("#error_remark").hide()
				}
				if (!$("#tenure").val()) {

					isError = true;

					$("#error_tenure").show()

				} else {
					$("#error_tenure").hide()
				}

				if (!$("#roi").val()) {

					isError = true;

					$("#error_roi").show()

				} else {
					$("#error_roi").hide()
				}

				if (!$("#loanAmt").val()) {

					isError = true;

					$("#error_loanAmt").show()

				} else {
					$("#error_loanAmt").hide()
				}

				if (!$("#startDate").val()) {

					isError = true;

					$("#error_startDate").show()

				} else {
					$("#error_startDate").hide()
				}

				if (!$("#endDate").val()) {

					isError = true;

					$("#error_endDate").show()

				} else {
					$("#error_endDate").hide()
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

		$('.datepickerclass1').daterangepicker({
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});
	</script>

	<!-- <script type="text/javascript">
	$('#submtbtn').on('click', function() {
        swalInit({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger',
            buttonsStyling: false
        }).then(function(result) {
            if(result.value) {
                swalInit(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                );
            }
            else if(result.dismiss === swal.DismissReason.cancel) {
                swalInit(
                    'Cancelled',
                    'Your imaginary file is safe :)',
                    'error'
                );
            }
        });
    });
	
	</script> -->

</body>
</html>