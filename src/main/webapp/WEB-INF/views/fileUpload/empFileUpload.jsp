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


								<div class="rows">
									<div class="col-md-12">
										<div class="row">
											<div class="col-md-12">
												<form
													action="${pageContext.request.contextPath}/empDetailUploadCSV"
													method="POST" enctype="multipart/form-data" method="post"
													id="submitInsertLocaion"
													class="form-inline1 justify-content-center">


													<div class="form-group row ">
														<label class="col-md-2 col-form-label" for="doc">Attach
															File:</label>
														<div class="col-md-6">
															<div class="fallback">
																<input name="fileNew" type="file" /><span
																	class="form-text text-muted">Accepted formats:
																	xls </span> <span
																	class="hidedefault   validation-invalid-label"
																	style="display: none;" id="error_empCode">Please
																	Select File .</span>


															</div>
														</div>
														<div class="col-md-4">
															<button type="submit" id="btnUploadCSVSubmit"
																name="btnUploadCSVSubmit" class="btn btn-primary">
																Uplaod File <i class="icon-paperplane ml-2"></i>
															</button>
														</div>



													</div>


												</form>
											</div>
											<span class="text-info"> <a
												href="http://gfplphp.aaryatechindia.in/uploads/att_template/attendance_sample.csv"
												target="_blank" id="genTemplate1" title=".csv Format"><i
													class="icon-file-download"></i> Download Template</a></span>
										</div>

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

	<!-- <script>
		$(document).ready(function($) {

			$("#submitInsertLocaion").submit(function(e) {
				alert(1);

 				$("#error_empCode").hide()

				var isError = false;
				var errMsg = "";

				if ($("#doc").val() != "") {

					isError = true;
					alert(2);
  					$("#error_empCode").show()
					//return false;
				} else {
					alert(3);

					$("#error_empCode").hide()
				}

				if (!isError) {

					
					var x = true;
					if (x == true) {

						document.getElementById("btnUploadCSVSubmit").disabled = true;
						return true;
					}
					//end ajax send this to php page
				}
				return false;
			});
		});
		//
	</script>


 -->
</body>
</html>