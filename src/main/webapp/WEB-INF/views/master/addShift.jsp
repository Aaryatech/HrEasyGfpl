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
								<h5 class="card-title">Add Shift</h5>
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
									action="${pageContext.request.contextPath}/submitShiftTiming"
									id="submitShiftTiming" method="post">
									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="locId">Select
											Location <span style="color: red"> *</span>:
										</label>
										<div class="col-lg-10">
											<select name="locId" data-placeholder="Select Location"
												id="locId"
												class="form-control form-control-select2 select2-hidden-accessible"
												aria-hidden="true"
												onchange="getShiftListByLocationIdAndSelftGroupId()">
												<option value="">Please Select</option>
												<c:forEach items="${locationList}" var="location">
													<c:forEach items="${locationAccess}" var="locationAccess">
														<c:if test="${location.locId==locationAccess}">
															<option value="${location.locId}">${location.locName}</option>
														</c:if>
													</c:forEach>
												</c:forEach>
											</select> <span class="validation-invalid-label" id="error_locId"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold  col-lg-2" for="shiftName">Shift
											Name <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Shift Name" id="shiftName" name="shiftName"
												autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_shiftName"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold  col-lg-2" for="intime">In
											Time<span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="time" class="form-control timehour24 " id="intime" data-mask="23:59"
												name="intime" autocomplete="off"> <span
												class="validation-invalid-label" id="error_intime"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									 
									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold  col-lg-2" for="outtime">Out
											Time<span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="time" class="form-control timehour24" id="outtime" data-mask="23:59"
												name="outtime" autocomplete="off"> <span
												class="validation-invalid-label" id="error_outtime"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold  col-lg-2" for="hfdayhour">Shift
											Half Day Hour<span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="time" class="form-control timehour24" id="hfdayhour" data-mask="23:59"
												name="hfdayhour" autocomplete="off"> <span
												class="validation-invalid-label" id="error_hfdayhour"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2 text-info font-weight-bold" for="othour">Shift
											OT Hour<span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="time" class="form-control timehour24" id="othour" data-mask="23:59"
												name="othour" autocomplete="off"> <span
												class="validation-invalid-label" id="error_othour"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									 

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="lateMin">Late
											Allowed in MIN <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control numbersOnly"
												id="lateMin" name="lateMin" autocomplete="off"> <span
												class="validation-invalid-label" id="error_lateMin"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold  col-lg-2" for="groupId">Select
											Group <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<select name="groupId" data-placeholder="Select Group"
												id="groupId"
												class="form-control form-control-select2 select2-hidden-accessible"
												aria-hidden="true"
												onchange="getShiftListByLocationIdAndSelftGroupId()">
												<option value="">Please Select</option>
												<c:forEach items="${selfGroupList}" var="selfGroupList">
													<option value="${selfGroupList.selftGroupId}">${selfGroupList.name}</option>
												</c:forEach>
											</select> <span class="validation-invalid-label" id="error_groupId"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label   text-info font-weight-bold col-lg-2" for="ischange">Is
											Changeable<span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
											<input type="checkbox" id="ischange" name="ischange"
												onchange="changeIsChange()">
										</div>

									</div>
									<div id="changeHideShow" style="display: none;">
										<div class="form-group row">

											<label class="col-form-label col-lg-2" for="changeWith">Change
												With : </label>
											<div class="col-lg-3">
												<select name="changeWith" data-placeholder="Change With"
													id="changeWith"
													class="form-control form-control-select2 select2-hidden-accessible"
													aria-hidden="true">


												</select>
											</div>
										</div>
									</div>


									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/getshiftList"><button
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
	
	 // Time picker
    $('.timehour24').AnyTime_picker({
        format: '%H:%i'
    });
		function changeIsChange() {

			if (document.getElementById("ischange").checked == true) {
				$("#changeHideShow").show();
				document.getElementById("ischange").value = 1;
			} else {
				$("#changeHideShow").hide();
				document.getElementById("ischange").value = 0;
			}
		}
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
		$(document).ready(function($) {

			$("#submitShiftTiming").submit(function(e) {
				var isError = false;
				var errMsg = "";
				$("#error_locId").hide();
				$("#error_shiftName").hide();
				$("#error_intime").hide();
				$("#error_outtime").hide();
				$("#error_hfdayhour").hide();
				$("#error_othour").hide();
				$("#error_lateMin").hide();
				$("#error_groupId").hide();

				if (!$("#locId").val()) {

					isError = true;
					$("#error_locId").show()
				}

				if (!$("#shiftName").val()) {

					isError = true;
					$("#error_shiftName").show()
				}

				if (!$("#intime").val()) {

					isError = true;
					$("#error_intime").show()

				}

				if (!$("#outtime").val()) {

					isError = true;
					$("#error_outtime").show()

				}

				if (!$("#hfdayhour").val()) {

					isError = true;
					$("#error_hfdayhour").show()
				}

				if (!$("#othour").val()) {

					isError = true;
					$("#error_othour").show()

				}

				if (!$("#lateMin").val()) {

					isError = true;
					$("#error_lateMin").show()

				}

				if (!$("#groupId").val()) {

					isError = true;
					$("#error_groupId").show()

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
		function getShiftListByLocationIdAndSelftGroupId() {

			var locationId = $("#locId").val()
			var groupId = $("#groupId").val()

			$.getJSON('${getShiftListByLocationIdAndSelftGroupId}', {
				locationId : locationId,
				groupId : groupId,
				ajax : 'true',
			},

			function(data) {
				var html;

				html += '<option  value="" > Change With</option>';

				var temp = 0;

				var len = data.length;
				for (var i = 0; i < len; i++) {

					html += '<option   value="' + data[i].id + '">'
							+ data[i].shiftname + '</option>';

				}

				$('#changeWith').html(html);
				$("#changeWith").trigger("chosen:updated");

			});

		}
	</script>

</body>
</html>