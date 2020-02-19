<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
										<td width="60%"><h5 class="card-title">${title}</h5></td>
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
									action="${pageContext.request.contextPath}/submitInsertBank"
									id="submitInsertLocaion" method="post">
									<input type="hidden" value="${bank.bankId}" id="bankId"
										name="bankId">
									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="bank">Bank
											Name <span class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" value="${bank.name}"
												placeholder="Enter Bank" id="bankName" name="bankName"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_bank"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="address">Address
											<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Address" id="address"
												value="${bank.address}" name="address" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_address"
												style="display: none;">This field is required.</span>

										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="branchName">Branch
											Name <span class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Branch Name" id="branchName"
												value="${bank.branchName}" name="branchName"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_branchName"
												style="display: none;">This field is required.</span>

										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="micrCode">MICR
											Code <span class="text-danger"></span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${bank.micrCode}" placeholder="Enter MICR Code"
												id="micrCode" maxlength="9" name="micrCode"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_micrCode"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="ifscCode">IFSC
											Code <span class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${bank.ifscCode}" placeholder="Enter IFSC Code"
												id="ifscCode" maxlength="11" name="ifscCode"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_ifscCode"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/showBankList"><button
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
		 
			return;
		}

		function validateIfsc(temp) {

			var eml = /^[A-Za-z]{4}[a-zA-Z0-9]{7}$/;

			if (eml.test($.trim(temp)) == false) {

				return false;

			}

			return true;

		}

		function validateMicr(temp) {

			var eml = /^\d{9}$/;

			if (eml.test($.trim(temp)) == false) {

				return false;

			}

			return true;

		}

		$(document)
				.ready(
						function($) {

							$("#submitInsertLocaion")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#bankName").val()) {

													isError = true;

													$("#error_bank").show()
													//return false;
												} else {
													$("#error_bank").hide()
												}

												if (!$("#address").val()) {

													isError = true;

													$("#error_address").show()

												} else {
													$("#error_address").hide()
												}
												if (!$("#branchName").val()) {

													isError = true;

													$("#error_branchName")
															.show()

												} else {
													$("#error_branchName")
															.hide()
												}
												
												if($("#micrCode").val().length > 0){
													if (!$("#micrCode").val()
															|| !validateMicr($(
																	"#micrCode")
																	.val())) {

														isError = true;

														$("#error_micrCode").show()

													} else {
														$("#error_micrCode").hide()
													}
												}
												
												if (!$("#ifscCode").val()
														|| !validateIfsc($(
																"#ifscCode")
																.val())) {

													isError = true;

													$("#error_ifscCode").show()

												} else {
													$("#error_ifscCode").hide()
												}

												if (!isError) {

													var x = true;
													if (x == true) {

														document
																.getElementById("submtbtn").disabled = true;
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