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
										<td width="60%"><h5 class="card-title">Add
												Contractor</h5></td>
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
									action="${pageContext.request.contextPath}/insertContractor"
									id="submitInsertLocaion" method="post">
									<input type="hidden" value="${contract.contractorId}"
										id="contractorId" name="contractorId">

									<%-- <div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="service">Service
											 <span class="text-danger">* </span>:</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" value="${contract.service}"
												placeholder="Enter Service" id="service"
												name="service" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_service"
												style="display: none;">This field is required.</span>
										</div>
									</div> --%>

									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="organisation">Organization <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Organisation Name" id="organisation"
												value="${contract.orgName}" name="organisation"
												autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label"
												id="error_organisation" style="display: none;">This
												field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="desination">Licence
											No. <span class="text-danger"> </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.licenceNo}"
												placeholder="Enter Licence No." id="licenceNo"
												name="licenceNo" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_licenceNo"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="vat">GST
											No. <span class="text-danger"></span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.vatNo}" placeholder="Enter GST No."
												id="vat" maxlength="25" name="vat" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_vat"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="pan">PAN
											No. <span class="text-danger"> </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.panNo}" placeholder="Enter PAN No."
												id="panNo" maxlength="10" style="text-transform: uppercase;"
												name="panNo" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_pan"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="pf">PF.No.
											<span class="text-danger"> </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.pfNo}" placeholder="Enter PF. No." id="pf"
												maxlength="25" name="pf" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_pf"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="esic">ESIC
											No. <span class="text-danger"> </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.esicNo}" placeholder="Enter ESIC No."
												id="esic" maxlength="25" name="esic" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_esic"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="address">Address
											<span class="text-danger"></span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.address}" placeholder="Enter Address"
												id="address" name="address" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_address"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="officeNo">Office
											No. <span class="text-danger"> </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.officeNo}" placeholder="Enter Office No."
												id="officeNo" maxlength="25" name="officeNo"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_officeNo"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="email">Email
											<span class="text-danger"> </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.email}" placeholder="Enter Email"
												id="email" name="email" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_email"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="owner">Owner
											Name <span class="text-danger"></span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.owner}" placeholder="Enter Owner"
												id="owner" name="owner" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_owner"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="mobileNo">Mob
											No. <span class="text-danger"> </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.mobileNo}" placeholder="Enter Mobile No."
												id="mobileNo" maxlength="10" name="mobileNo"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_mobileNo"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="desination">Duration
											<span class="text-danger"> </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												value="${contract.duration}" placeholder="Enter Duration"
												id="duration" name="duration" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_duration"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">Remark
											: </label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Any Remark" onchange="trim(this)" id="remark"
												name="remark">${contract.remark}</textarea>

										</div>
									</div>

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a
												href="${pageContext.request.contextPath}/showContractorsList"><button
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
			//	checkSame();
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
		function validatePAN(pan) {
			var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
			if (regex1.test($.trim(pan)) == false) {
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

												if (!$("#organisation").val()) {

													isError = true;

													$("#error_organisation")
															.show()

												} else {
													$("#error_organisation")
															.hide()
												}

												/*  if (!$("#licenceNo").val()) { 

													isError = true;

													$("#error_licenceNo")
															.show()

												} else {
													$("#error_licenceNo")
															.hide()
												} */
												/* 
												if (!$("#vat").val()) { 

													isError = true;

													$("#error_vat")
															.show()

												} else {
													$("#error_vat")
															.hide()
												}
												 */
												if ($("#panNo").val().length > 0) {
													if (!$("#panNo").val()
															|| !validatePAN($(
																	"#panNo")
																	.val())) {

														isError = true;
														$("#error_pan").show()
													} else {
														$("#error_pan").hide()
													}
												}

												/* if (!$("#pf").val()) { 
													isError = true;

													$("#error_pf")
															.show()
												} else {
													$("#error_pf")
															.hide()
												}
												 */
												/* if (!$("#esic").val()) { 
													isError = true;

													$("#error_esic")
															.show()

												} else {
													$("#error_esic")
															.hide()
												} */
												/* if (!$("#address").val()) { 
													isError = true;

													$("#error_address")
															.show()

												} else {
													$("#error_address")
															.hide()
												} */
												/* if (!$("#officeNo").val()) { 
													isError = true;

													$("#error_officeNo")
															.show()

												} else {
													$("#error_officeNo")
															.hide()
												}
												 */

												if ($("#mobileNo").val().length > 0) {
													if (!$("#mobileNo").val()
															|| !validateMobile($(
																	"#mobileNo")
																	.val())) {

														isError = true;
														$("#error_mobileNo")
																.show()

													} else {
														$("#error_mobileNo")
																.hide()
													}
												}

												if ($("#email").val().length > 0) {
													if (!$("#email").val()
															|| !validateEmail($(
																	"#email")
																	.val())) {

														isError = true;

														$("#error_email")
																.show()

													} else {
														$("#error_email")
																.hide()
													}
												}

												/* 	if (!$("#owner").val()) { 
															isError = true;

															$("#error_owner")
																	.show()

														} else {
															$("#error_owner")
																	.hide()
														} */
												/* 	if (!$("#duration").val()) { 
															isError = true;

															$("#error_duration")
																	.show()

														} else {
															$("#error_duration")
																	.hide()
														}  */

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