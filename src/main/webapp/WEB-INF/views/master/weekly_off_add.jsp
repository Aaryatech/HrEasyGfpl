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

 
			</div>
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
								<td width="60%"><h5 class="card-title">Add Weekly Off</h5></td>
								<td width="40%" align="right">
							  
								 <%-- <a
									href="${pageContext.request.contextPath}/showAddKra?empId=${editKra.exVar3}&finYrId=${editKra.exVar2}"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">KRA List </button>
								</a>  --%></td>
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
									action="${pageContext.request.contextPath}/submitInsertWeeklyOff"
									id="submitInsertWeeklyOff" method="post">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="select2">Select
											Location <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<select name="locId" data-placeholder="Select Location"
												id="locId"
												class="form-control form-control-select2 select2-hidden-accessible"
												aria-hidden="true">
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
										<label class="col-form-label col-lg-2" for="select2">Select
											Weekly Off Type <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<select name="woType" data-placeholder="Please Select"
												id="woType"
												class="form-control form-control-select2 select2-hidden-accessible"
												tabindex="-1" aria-hidden="true">
												<option value="">Please Select</option>
												<option value="0">All</option>
												<option value="1">Even</option>
												<option value="2">Odd</option>
												<option value="3">1st</option>
												<option value="4">2nd</option>
												<option value="5">3rd</option>
												<option value="6">4th</option>


											</select> <span class="validation-invalid-label" id="error_woType"
												style="display: none;">This field is required.</span>
										</div>
									</div>



									<!-- <div class="form-group row">
										<label class="col-form-label col-lg-2" for="select2">Select
											Weekly Off Presently <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<select name="woPresently" data-placeholder="Please Select"
												id="woPresently"
												class="form-control form-control-select2 select2-hidden-accessible"
												tabindex="-1" aria-hidden="true">
												<option value="">Please Select</option>
												<option value="FULL DAY">FULL DAY</option>
												<option value="HALF DAY">HALF DAY</option>


											</select> <span class="validation-invalid-label"
												id="error_woPresently" style="display: none;">This
												field is required.</span>
										</div>
									</div> -->
									
									
									<input type="hidden" value="1" name="woPresently">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="select2">Select
											Weekly Off Day <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<select name="woDay" data-placeholder="Please Select"
												id="woDay"
												class="form-control form-control-select2 select2-hidden-accessible"
												tabindex="-1" aria-hidden="true">
												<option value="">Please Select</option>
												<option value="0">SUNDAY</option>
												<option value="1">MONDAY</option>
												<option value="2">TUESDAY</option>
												<option value="3">WEDNESDAY</option>
												<option value="4">THURSDAY</option>
												<option value="5">FRIDAY</option>
												<option value="6">SATURDAY</option>
											</select> <span class="validation-invalid-label" id="error_woDay"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">Remark
											: </label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Any Remark" onchange="trim(this)"
												id="woRemarks" name="woRemarks"></textarea>

										</div>
									</div>

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a
												href="${pageContext.request.contextPath}/showWeeklyOffList"><button
													type="button" class="btn btn-primary"> 
													Cancel
												</button></a>
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
		$(document).ready(function($) {

			$("#submitInsertWeeklyOff").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#locId").val()) {

					isError = true;

					$("#error_locId").show()
					//return false;
				} else {
					$("#error_locId").hide()
				}

				if (!$("#woType").val()) {

					isError = true;

					$("#error_woType").show()

				} else {
					$("#error_woType").hide()
				}

				

				if (!$("#woDay").val()) {

					isError = true;

					$("#error_woDay").show()

				} else {
					$("#error_woDay").hide()
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