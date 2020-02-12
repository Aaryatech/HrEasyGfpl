<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<c:url var="getShiftListByLocationIdAndSelftGroupId"
	value="/getShiftListByLocationIdAndSelftGroupId" />
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
								<h5 class="card-title">Add Driver Details</h5>
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


								<div class="form-group row">

									<label class="col-form-label col-lg-2" for="locId"> Emp
										Code: </label> <label class="col-form-label col-lg-2" for="locId"
										style="color: red;"> ${empDeatil.empCode}</label> <label
										class="col-form-label col-lg-2" for="locId"> Emp Name:
									</label> <label class="col-form-label col-lg-5" for="locId"
										style="color: red;">${empDeatil.surname}&nbsp;${empDeatil.middleName}&nbsp;${empDeatil.firstName}
									</label>
								</div>


								<div class="form-group row">

									<label class="col-form-label col-lg-2" for="locId"> Emp
										Designation: </label> <label class="col-form-label col-lg-2"
										for="locId" style="color: red;"> ${empDeatil.empDesgn}
									</label> <label class="col-form-label col-lg-2" for="locId">
										Emp Type: </label> <label class="col-form-label col-lg-2" for="locId"
										style="color: red;"> ${empDeatil.empTypeName} </label> <label
										class="col-form-label col-lg-2" for="locId"> Location:
									</label> <label class="col-form-label col-lg-2" for="locId"
										style="color: red;"> ${empDeatil.locName} </label>

								</div>

								<form
									action="${pageContext.request.contextPath}/submitEmpDriver"
									id="submitEmpDriver" method="post">
									<input type="hidden" name="driverEmpId" id="driverEmpId"
										value="${editEmpDriver.empDriverId}"> <input
										type="hidden" name="empId" id="empId"
										value="${empDeatil.empId}"> <input type="hidden"
										name="desnId" id="desnId" value="${empDeatil.designationId}">

									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold  col-lg-2"
											for="shiftName">License No. <span style="color: red">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
												value="${editEmpDriver.licenceNo}"
												placeholder="Driver License No." id="licenceNo"
												name="licenceNo" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_licenceNo"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold  col-lg-2"
											for="vehicleNo">Vehicle No. <span style="color: red">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
												placeholder="Vehicle No" id="vehicleNo" name="vehicleNo"
												value="${editEmpDriver.vehicleNo}" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_vehicleNo"
												style="display: none;">This field is required.</span>
										</div>
									</div>



									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="issueDate">Issue Date <span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control datepickerclass"
												placeholder="Issue Date" id="issueDate" name="issueDate"
												value="${editEmpDriver.licenceIssueDate}" autocomplete="off"
												onchange="trim(this)"> <span
												class="hidedefault  validation-invalid-label"
												id="error_issueDate" style="display: none;">This
												field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="expDate">Expiry Date <span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control datepickerclass"
												placeholder="Date of Expiry" id="expDate" name="expDate"
												value="${editEmpDriver.licenceExpDate}" autocomplete="off"
												onchange="trim(this)"> <span
												class="hidedefault  validation-invalid-label"
												id="error_expDate" style="display: none;">This field
												is required.</span>
										</div>
									</div>

<%-- 
									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold  col-lg-2"
											for="vehicleType">Select Vehicle Type <span
											style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<select name="vehicleType"
												data-placeholder="Select Vehicle Type" id="vehicleType"
												class="form-control form-control-select2 "
												aria-hidden="true">
												<option value="">Please Select</option>
												<option value="1"
													${editEmpDriver.vehicleType == 1 ? 'selected' : ''}>VEH
													TYPE1</option>

												<option value="2"
													${editEmpDriver.vehicleType == 2 ? 'selected' : ''}>VEH
													TYPE2</option>
											</select> <span class="validation-invalid-label"
												id="error_vehicleType" style="display: none;">This
												field is required.</span>
										</div>
									</div> --%>



									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/showDriverEmployeeList"><button
													type="button" class="btn btn-light">Back</button></a>
										</div>
									</div>
								</form>
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
		$('.datepickerclass').daterangepicker({
			"autoUpdateInput" : false,
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		}, function(start_date) {
			$(this.element).val(start_date.format('DD-MM-YYYY'));
		});

		$(document).ready(function($) {

			$("#submitEmpDriver").submit(function(e) {
				var isError = false;
				var errMsg = "";
				$("#error_licenceNo").hide();
				/* $("#error_vehicleType").hide(); */
				$("#error_expDate").hide();
				$("#error_issueDate").hide();
				$("#error_vehicleNo").hide();

				if (!$("#expDate").val()) {

					isError = true;
					$("#error_expDate").show()
				}

				if (!$("#licenceNo").val()) {

					isError = true;
					$("#error_licenceNo").show()
				}

				/* if (!$("#vehicleType").val()) {

					isError = true;
					$("#error_vehicleType").show()

				} */

				if (!$("#issueDate").val()) {

					isError = true;
					$("#error_issueDate").show()

				}

				if (!$("#vehicleNo").val()) {

					isError = true;
					$("#error_vehicleNo").show()
				}

				if (!isError) {

					document.getElementById("submtbtn").disabled = true;
					return true;

				}
				return false;
			});
		});
		//
	</script>



</body>
</html>