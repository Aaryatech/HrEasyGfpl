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
								<h5 class="card-title">Edit Bonus</h5>
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

								<form action="${pageContext.request.contextPath}/submitEditBonus"
									id="submitBonus" method="post">

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="shiftName">Bonus
											Period Title <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" placeholder="  Title"
												id="bonusTitle" name="bonusTitle" autocomplete="off" value="${editBonus.fyTitle}"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_bonusTitle"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2">Date Range<span
											style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control daterange-basic_new "
												name="leaveDateRange" data-placeholder="Select Date"  value="${dateString}"
												id="leaveDateRange" >
											<span class="validation-invalid-label" id="error_Range"
												style="display: none;">This field is required.</span> <span
												class="validation-invalid-label" id="error_insuf"
												style="display: none;">Insufficient Leaves.</span>

										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="shiftName">Bonus
											% <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="E.g. Bonus %" id="bonusPrcnt" name="bonusPrcnt" value="${editBonus.bonusPercentage}"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_bonusPrcnt"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									
									
									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="shiftName">Exgratia 
											% <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="E.g. Exgratia %" id="exgratiaPrcnt" name="exgratiaPrcnt"  value="${editBonus.exgratiaPercentage}"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_exgratiaPrcnt"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									
									
										<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="shiftName">Present Min Days
										  <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Minimum Bonus Days" id="minDays" name="minDays"  value="${editBonus.minDays}"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_minDays"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="lvngReson">Remark<span
											style="color: red">* </span> :
										</label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Remark" onchange="trim(this)" id="bonusRemark"
												name="bonusRemark">${editBonus.remark} </textarea>
										</div>
									</div>
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
				if (!$("#bonusTitle").val()) {

					isError = true;

					$("#error_bonusTitle").show()
				} else {
					$("#error_bonusTitle").hide()
				}

				if (!$("#bonusPrcnt").val()) {

					isError = true;

					$("#error_bonusPrcnt").show()
				} else {
					$("#error_bonusPrcnt").hide()
				}
				
				
				if (!$("#minDays").val()) {

					isError = true;

					$("#error_minDays").show()
				} else {
					$("#error_minDays").hide()
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