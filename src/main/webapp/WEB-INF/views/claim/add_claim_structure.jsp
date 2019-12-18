<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<c:url var="addStrDetail" value="/addStrDetail" />

<c:url var="getLeaveStructureForEdit" value="/getLeaveStructureForEdit" />

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
					 
						<div class="card">
							<div class="card-header header-elements-inline">
								<h6 class="card-title">Add Claim Structure</h6>
								<!-- <div class="header-elements">
									<div class="list-icons">
										<a class="list-icons-item" data-action="collapse"></a>
									</div>
								</div> -->
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
									action="${pageContext.request.contextPath}/insertClaimStructure"
									id="insertClaimStructure" method="post">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="lvsName">Claim
											Structure Name <span style="color:red">* </span>: </label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Claim Structure Name" id="clmsName"
												name="clmsName" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_clmsName"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									<hr>
									<div class="form-group row">
										<strong> Claim Types :</strong>
									</div>


									<c:forEach items="${claimTypeList}" var="clmType">

										<div class="form-group row">
											<label class="col-md-2" for="prsnName">${clmType.claimTypeTitle} <span style="color:red">* </span>: </label>
											<div class="col-md-6">
												<input type="text" class="form-control"
													placeholder="Claim Amount"
													id="amt${clmType.claimTypeId}" value="0"
													name="amt${clmType.claimTypeId}" autocomplete="off"
													onchange="trim(this)"> <span
													class="validation-invalid-label" id="error_prsnName"
													style="display: none;">This field is required.</span>
											</div>
										</div>

									</c:forEach>


									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/showClaimStructureList"><button
										type="button" class="btn btn-primary"><i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp; Cancel</button></a>
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

			$("#insertClaimStructure").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#clmsName").val()) {

					isError = true;

					$("#error_clmsName").show()
					 
				} else {
					$("#error_clmsName").hide()
				}
 
				
				if (!isError) {

					var x = true ;
					if (x == true) {

						document.getElementById("submtbtn").disabled = true;
						return true;
					}
					 
				}
				return false;
			});
		});
	 
	</script>

 
</body>
</html>