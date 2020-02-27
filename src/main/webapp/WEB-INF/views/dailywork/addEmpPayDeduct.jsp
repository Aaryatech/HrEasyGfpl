<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datepicker.css"
	type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-datepicker.js"></script>
</head>

<body>
	<c:url var="getPayDeductionTypeRate" value="/getPayDeductionTypeRate"></c:url>
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
										<td width="60%"><h5 class="card-title">Add Employee
												Payment Deduction</h5></td>
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
									action="${pageContext.request.contextPath}/insertPayDeductDetail"
									id="insertPayDeductDetail" method="post">

									<div class="form-group row">
										<input type="hidden" value="${empId}" id="empId" name="empId">


										<input type="hidden" value="0" id="dedId" name="dedId">
									</div>
									
									
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="dedRate">Employee
											 Code<span style="color:red"></span>:</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" value="${emp.empCode}" readonly="readonly">
												
										</div>
									</div> 
									
								<div class="form-group row">
										<label class="col-form-label col-lg-2" for="dedRate">Employee Name
											 <span style="color:red"></span>:</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" value="${emp.firstName} ${emp.surname}" readonly="readonly"> 
										</div>
									</div>
									
									
									<div class="form-group row">


										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="dedTypeId"> Deduction Type <span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<select name="dedTypeId"
												data-placeholder="Select Deduction Type" id="dedTypeId"
												onchange="getDeductRate(this.value)"
												class="form-control form-control-select2 select2-hidden-accessible">

												<option value="">Select</option>
												<c:forEach items="${payDeductList}" var="payDeductList">
													<option value="${payDeductList.dedTypeId}">${payDeductList.typeName}</option>
												</c:forEach>
											</select> <span class="hidedefault   validation-invalid-label"
												style="display: none;" id="error_dedTypeId">This
												field is required.</span>
										</div>
									</div>

									<div class="form-group row">



										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="dedRate"> Amount <span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
												placeholder="Enter Address" id="dedRate" name="dedRate"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_dedRate"
												style="display: none;">This field is required.</span>

										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="month"> Deduction on Month <span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" name="monthyear" id="monthyear"
												class="form-control datepicker" autocomplete="off"
												data-min-view-mode="months" data-start-view="1"
												data-format="mm-yyyy"> <span
												class="validation-invalid-label" id="error_monthyear"
												style="display: none;">This field is required.</span>
										</div>

									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">Reason
											/ Remark <span style="color: red"></span>:
										</label>
										<div class="col-lg-4">
											<textarea class="form-control"
												placeholder="Enter Reason / Remark" id="remark"
												name="remark" autocomplete="off" onchange="trim(this)"> </textarea>

										</div>
									</div>


									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/viewPayDeduction"><button
													type="button" class="btn btn-light">
													<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
													Back
												</button></a>
										</div>
									</div>
								</form>
								<!-- <p class="desc text-danger fontsize11">Notice : * Fields are
									mandatory.</p> -->
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
		$(document).ready(function() {
			// month selector
			$('.datepicker').datepicker({
				autoclose : true,
				format : "mm-yyyy",
				viewMode : "months",
				minViewMode : "months"

			});

		});
	</script>
	<script>
		function getDeductRate(deductType) {
			$.getJSON('${getPayDeductionTypeRate}',

			{

				deductType : deductType,
				ajax : 'true'

			}, function(data) {
				$('#dedRate').val(data.dedRate);

			});
		}

		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines

			return;
		}

		$(document).ready(function($) {

			$("#insertPayDeductDetail").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#dedTypeId").val()) {

					isError = true;

					$("#error_dedTypeId").show()
					//return false;
				} else {
					$("#error_dedTypeId").hide()
				}

				if (!$("#dedRate").val()) {

					isError = true;

					$("#error_dedRate").show()

				} else {
					$("#error_dedRate").hide()
				}
				if (!$("#monthyear").val()) {

					isError = true;

					$("#error_monthyear").show()

				} else {
					$("#error_monthyear").hide()
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




</body>
</html>