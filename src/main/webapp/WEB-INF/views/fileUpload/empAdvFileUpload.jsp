<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>

<body>
	<c:url var="checkUniqueField" value="/checkUniqueField"></c:url>

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
										<td width="60%"><h5 class="card-title">Bulk Advance
												Data Upload</h5></td>
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


								<div class="rows">
									<div class="col-md-12">


										<div class="rows">

											<form
												action="${pageContext.request.contextPath}/empAdvanceDetailUpload"
												method="POST" enctype="multipart/form-data" method="post"
												id="empAdvanceDetailUpload"
												class="form-inline1 justify-content-center">


												<div class="form-group row ">
													<label class="col-md-2 col-form-label" for="fileAdvance">Attach
														Advance File:</label>
													<div class="col-md-6">
														<div class="fallback">
															<input name="fileAdvance" type="file" id="fileAdvance" /><span
																class="form-text text-muted">Accepted formats:
																xls </span> <span
																class="hidedefault   validation-invalid-label"
																style="display: none;" id="error_fileAdvance">Please
																Select File .</span>


														</div>
													</div>
													<div class="col-md-4">
														<button type="submit" id="btnUploadAdvSubmit"
															name="btnUploadSubmit" class="btn btn-primary">
															Uplaod File <i class="icon-paperplane ml-2"></i>
														</button>


														<a
															href="${pageContext.request.contextPath}/showEmpListToAddAdvance"><button
																type="button" class="btn btn-primary">
																<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
																Cancel
															</button></a>
													</div>



												</div>


											</form>
										</div>
										<span class="text-info"> <a
											href="${templatePath}${fileName}" target="_blank"
											id="genTemplate1" title=".xls Format"><i
												class="icon-file-download"></i> Download Template</a></span>



									</div>
								</div>

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
		$(document)
				.ready(
						function($) {
							$("#empAdvanceDetailUpload")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if ($("#fileAdvance").val() != "") {
													$("#error_fileAdvance")
															.hide()
												} else {
													isError = true;
													$("#error_fileAdvance")
															.show()
												}
												if (!isError) {
													var x = true;
													if (x == true) {
														document
																.getElementById("btnUploadAdvSubmit").disabled = true;
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