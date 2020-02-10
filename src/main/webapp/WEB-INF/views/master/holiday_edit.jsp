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
			<div class="page-header page-header-light">


				<%-- <div
					class="breadcrumb-line breadcrumb-line-light header-elements-md-inline">
					<div class="d-flex">
						<div class="breadcrumb">
							<a href="index.html" class="breadcrumb-item"><i
								class="icon-home2 mr-2"></i> Home</a> <span
								class="breadcrumb-item active">Dashboard</span>
						</div>

						<a href="#" class="header-elements-toggle text-default d-md-none"><i
							class="icon-more"></i></a>
					</div>

					<div class="breadcrumb justify-content-center">
						<a href="${pageContext.request.contextPath}/showHolidayList"
							class="breadcrumb-elements-item"> Holiday List</a>

					</div>


				</div> --%>
			</div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">

				<!-- Form validation -->
				<div class="row">
					<div class="col-md-12">


						<div class="card">




							<div class="card-header header-elements-inline">
								<table width="100%">
									<tr width="100%">
										<td width="60%"><h5 class="card-title">Edit Holiday</h5></td>
										<td width="40%" align="right">
											<%-- <a
											href="${pageContext.request.contextPath}/showHolidayList"
											class="breadcrumb-elements-item">
												<button type="button" class="btn btn-primary">Holiday
													List</button>
										</a> --%>
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
									action="${pageContext.request.contextPath}/submitEditHoliday"
									id="submitInsertHoliday" method="post">


									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="locShortName">Holiday Title <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Holiday Title" id="holidayTitle"
												value="${editHoliday.exVar2}" name="holidayTitle"
												autocomplete="off" onchange="trim(this)" maxlength="100">
											<span class="validation-invalid-label"
												id="error_holidayTitle" style="display: none;">This
												field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="hoCatId">Select Holiday Category <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<select name="hoCatId" data-placeholder="Select "
												id="hoCatId"
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true">

												<option value="0">Select</option>

												<c:forEach items="${holiList}" var="holiList">


													<c:choose>
														<c:when test="${editHoliday.exInt1==holiList.hoCatId}">
															<option selected value="${holiList.hoCatId}">${holiList.hoCatName}</option>
														</c:when>
														<c:otherwise>
															<option value="${holiList.hoCatId}">${holiList.hoCatName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label" id="error_hoCatId"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="select2">Select Location <span
											style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<select name="locId" data-placeholder="Select Location"
												id="locId"
												class="form-control form-control-select2 select2-hidden-accessible"
												multiple="multiple" tabindex="-1" aria-hidden="true">
												<option></option>


												<c:forEach items="${locationList}" var="location">
													<c:set var="flag" value="0"></c:set>
													<c:forEach items="${locIdList}" var="selFr"
														varStatus="count2">
														<c:choose>
															<c:when test="${selFr==location.locId}">
																<option selected value="${location.locId}"><c:out
																		value="${location.locName}" /></option>
																<c:set var="flag" value="1"></c:set>
															</c:when>
															<c:otherwise>

															</c:otherwise>
														</c:choose>
													</c:forEach>
													<c:choose>
														<c:when test="${flag==0}">
															<option value="${location.locId}"><c:out
																	value="${location.locName}" /></option>
														</c:when>
													</c:choose>
												</c:forEach>
												<%-- <c:forEach items="${locationList}" var="location">
													<c:choose>
														<c:when test="${location.locId == editHoliday.companyId}">
															<option value="${location.locId}" selected="selected">${location.locName}</option>
														</c:when>
														<c:otherwise>
															<option value="${location.locId}">${location.locName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach> --%>
											</select> <span class="validation-invalid-label" id="error_locId"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<%-- <div class="form-group row">
										<label class="col-form-label col-lg-2" for="calYrId">Select
											year :</label>
										<div class="col-lg-10">
											<select name="calYrId" data-placeholder="Select Year"
												id="calYrId"
												class="form-control form-control-select2 select2-hidden-accessible"
												required="" data-fouc="" tabindex="-1" aria-hidden="true">
												<option></option>
												<c:forEach items="${yearList}" var="year">
													<c:choose>
														<c:when test="${year.calYrId == editHoliday.calYrId}">
															<option value="${year.calYrId}" selected="selected">${year.calYrFromDate}_${year.calYrToDate}</option>
														</c:when>
														<c:otherwise>
															<option value="${year.calYrId}">${year.calYrFromDate}_${year.calYrToDate}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label" id="error_calYrId"
												style="display: none;">This field is required.</span>
										</div>
									</div> --%>

									<div class="form-group row">
										<label class="col-form-label col-lg-2">Date Range:</label>
										<div class="col-lg-10">
											<input type="text" class="form-control daterange-basic_new "
												name="dateRange"
												value="${editHoliday.holidayFromdt} to ${editHoliday.holidayTodt} "
												data-placeholder="Select Date" id="dateRange"> <span
												class="validation-invalid-label" id="error_dateRange"
												style="display: none;">This field is required.</span>

										</div>
									</div>




									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">
											Remark : </label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Any Remark for Holiday" onchange="trim(this)"
												id="holidayRemark" name="holidayRemark">${editHoliday.holidayRemark}</textarea>

										</div>
									</div>

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>

											<a href="${pageContext.request.contextPath}/showHolidayList"><button
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
		$(document)
				.ready(
						function($) {

							$("#submitInsertHoliday")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#holidayTitle").val()) {

													isError = true;

													$("#error_holidayTitle")
															.show()

												} else {
													$("#error_holidayTitle")
															.hide()
												}

												if ($("#locId").val() == "") {

													isError = true;

													$("#error_locId").show()

												} else {
													$("#error_locId").hide()
												}

												if (!$("#dateRange").val()) {

													isError = true;

													$("#error_Range").show()

												} else {
													$("#error_Range").hide()
												}

												if (!$("#hoCatId").val()
														|| parseInt($(
																"#hoCatId")
																.val()) == 0) {

													isError = true;

													$("#error_hoCatId").show()

												} else {
													$("#error_hoCatId").hide()
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