<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>
	<c:url value="/checkVoucherNo" var="checkVoucherNo"></c:url>
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
										<td width="60%"><h5 class="card-title">Add Advance</h5></td>
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
										<label class="col-form-label col-lg-2" for="grossSal">Total
											Gross Salary <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" id="grossSal"
												value="${empPersInfo.grossSalaryEst}" readonly="readonly"
												name="grossSal" autocomplete="off" onchange="trim(this)">
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="voucherNo">Voucher
											No. <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Voucher Number" id="voucherNo"
												name="voucherNo" autocomplete="off" onchange="uniqueVoucherNum()">
											<span class="validation-invalid-label" id="error_voucherNo"
												style="display: none;">This field is required.</span>
												<span class="validation-invalid-label" id="error_voucherNo1"
												style="display: none;">Voucher No. Already Exists</span>

										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="advanceAmt">Advance
											Amount <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Advance Amount" id="advanceAmt"
												name="advanceAmt" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_advanceAmt"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">Month
											<span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" id="month"
												name="month" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_month"
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
		function uniqueVoucherNum() {

			var voucherNo = $("#voucherNo").val();
			var valid = false;
			$.getJSON('${checkVoucherNo}', {
				voucherNo : voucherNo,
				ajax : 'true',
			}, function(data) {
				if (parseInt(data) == 1) {

					document.getElementById("submtbtn").disabled = true;
					document.getElementById("voucherNo").value = "";
					$("#error_voucherNo1").show();

				} else {
					valid = true;
					document.getElementById("submtbtn").disabled = false;
					$("#error_voucherNo1").hide();
				}

			});

			return valid;

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

				if (!$("#voucherNo").val()) {

					isError = true;

					$("#error_voucherNo").show()
					//return false;
				} else {
					$("#error_voucherNo").hide()
				}

				if (!$("#month").val()) {

					isError = true;

					$("#error_month").show()

				} else {
					$("#error_month").hide()
				}
				if (!$("#remark").val()) {

					isError = true;

					$("#error_remark").show()

				} else {
					$("#error_remark").hide()
				}
				if (!$("#advanceAmt").val()) {

					isError = true;

					$("#error_advanceAmt").show()

				} else {
					$("#error_advanceAmt").hide()
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