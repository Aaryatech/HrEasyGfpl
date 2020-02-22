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
								<h5 class="card-title">Edit Exgretia</h5>
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
									action="${pageContext.request.contextPath}/submitEditExgratia"
									id="submitBonus" method="post">



									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="shiftName">Employee
											Name </label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="exgratiaAmt"
												name="exgratiaAmt" readonly="readonly"
												value="${editEmp.empCode}" autocomplete="off"
												onchange="trim(this)">
										</div>
										<label class="col-form-label col-lg-2" for="shiftName">Employee
											Code : </label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="exgratiaAmt"
												name="exgratiaAmt" readonly="readonly"
												value="${editEmp.surname} ${editEmp.firstName} ${editEmp.middleName}"
												autocomplete="off" onchange="trim(this)">
										</div>
									</div>



									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="shiftName">Exgratia
											Amt : </label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="exgratiaAmt"
												name="exgratiaAmt" readonly="readonly"
												value="${editBonusCalc.totalExgretiaWages}"
												autocomplete="off" onchange="trim(this)">
										</div>
										<label class="col-form-label col-lg-2" for="shiftName">Gross
											Exgratia Amt: </label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="exgratiaAmt"
												name="exgratiaAmt" readonly="readonly"
												value="${editBonusCalc.grossExgretiaAmt}" autocomplete="off"
												onchange="trim(this)">
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="shiftName">Paid
											Exgratia Amt : </label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="exgratiaAmt"
												name="exgratiaAmt" readonly="readonly"
												value="${editBonusCalc.paidExgretiaAmt}" autocomplete="off"
												onchange="trim(this)">
										</div>

										<label class="col-form-label text-info font-weight-bold col-lg-2" for="shiftName">Exgratia
											% <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
												placeholder="E.g. Exgratia %" id="exgratiaPrcnt"
												name="exgratiaPrcnt" value="${editBonusCalc.exgratiaPrcnt}"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_exgratiaPrcnt"
												style="display: none;">This field is required.</span>
										</div>
									</div>




									<input type="hidden" id="bonusId" name="bonusId"
										value="${bonusId}"> <input type="hidden"
										id="bonusCalcId" name="bonusCalcId"
										value="${editBonusCalc.bonusCalcId}">

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/showBonusList"><button
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
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			return;
		}

		$(document).ready(function($) {

			$("#submitBonus").submit(function(e) {
				var isError = false;
				var errMsg = "";
				if (!$("#exgratiaPrcnt").val()) {

					isError = true;

					$("#error_exgratiaPrcnt").show()
				} else {
					$("#error_exgratiaPrcnt").hide()
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