<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
						<a href="${pageContext.request.contextPath}/showClaimTypeList"
							class="breadcrumb-elements-item"> Claim Type List </a>

					</div>
				</div> --%></div>
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
								<td width="60%"><h5 class="card-title">Edit Claim Type</h5></td>
								<td width="40%" align="right">
								<%--  <a
									href="${pageContext.request.contextPath}/showClaimTypeList"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">Claim Type List  </button>
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
											session.removeAttribute("errorMsg");
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
											session.removeAttribute("successMsg");
										%>
									</div>
									<%
										session.removeAttribute("successMsg");
										}
									%>

									<form
										action="${pageContext.request.contextPath}/submitEditClaimType"
										id="submitEditClaimType" method="post">


										<div class="form-group row">
											<label class="col-form-label col-lg-2" for="calimTypeTitle">
												Title <span style="color:red">* </span>: </label>
											<div class="col-lg-10">
												<input type="text" class="form-control"
													placeholder="Enter Claim Type Title" id="calimTypeTitle"
													name="calimTypeTitle" autocomplete="off"
													value="${editClaimType.claimTypeTitle}"
													onchange="trim(this)"> <span
													class="validation-invalid-label" id="error_claimType"
													style="display: none;">This field is required.</span>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-form-label col-lg-2"
												for="claimShortTypeTitle"> Short Title <span style="color:red">* </span>: </label>
											<div class="col-lg-10">
												<input type="text" class="form-control"
													placeholder="Enter Claim Short Name "
													value="${editClaimType.claimTypeTitleShort}"
													id="claimShortTypeTitle" name="claimShortTypeTitle"
													autocomplete="off" onchange="trim(this)"> <span
													class="validation-invalid-label" id="error_claimScType"
													style="display: none;">This field is required.</span>
													<span
												class="validation-invalid-label" id="error_sameName"
												style="display: none;">Claim Type Short Name Can Not be same as  Claim Type Name. </span>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-form-label col-lg-2">Color <span style="color:red">* </span>: </label>
											<div class="col-lg-10">
												<input type="text" id="claimColor" name="claimColor"
													class="form-control colorpicker-show-input"
													value="${editClaimType.claimTypeColor}"
													data-preferred-format="hex" value="#f75d1c" data-fouc>
											</div>
										</div>



										<div class="form-group row">
											<label class="col-form-label col-lg-2" for="remark">Any
												Remark:</label>
											<div class="col-lg-10">
												<textarea rows="3" cols="3" class="form-control"
													placeholder="Any Remark" onchange="trim(this)" id="remark"
													name="remark">${editClaimType.claimTypeRemarks}</textarea>
											</div>
										</div>

										<div class="form-group row mb-0">
											<div class="col-lg-10 ml-lg-auto">

												<button type="submit" class="btn bg-blue ml-3 legitRipple"
													id="submtbtn">
													Submit <i class="icon-paperplane ml-2"></i>
												</button>
												
												<a href="${pageContext.request.contextPath}/showClaimTypeList"><button
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
<script type="text/javascript">
		function checkSame() {
			//alert("hii");
			x = document.getElementById("claimShortTypeTitle").value;
			y = document.getElementById("calimTypeTitle").value;
			//alert(x);

			if (x !== '' && y !== '') {
				if (x == y) {
					$("#error_sameName").show()
					document.getElementById("claimShortTypeTitle").value = "";
				} else {
					$("#error_sameName").hide()
				}
			}

		}
	</script>
		<script>
			function trim(el) {
				el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
				replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
				replace(/\n +/, "\n"); // Removes spaces after newlines
				checkSame();
				return;
			}

			function numbersOnlyNotZero(id_number) {

				var mob = /^[1-9][0-9]+$/;

				if (mob.test($.trim(id_number)) == false) {

					//alert("Please enter a valid email address .");
					return false;

				}
				return true;
			}

			$(document)
					.ready(
							function($) {

								$("#submitEditClaimType")
										.submit(
												function(e) {
													var isError = false;
													var errMsg = "";

													if (!$("#calimTypeTitle")
															.val()) {

														isError = true;

														$("#error_claimType")
																.show()
														//return false;
													} else {
														$("#error_claimType")
																.hide()
													}

													if (!$(
															"#claimShortTypeTitle")
															.val()) {

														isError = true;

														$("#error_claimScType")
																.show()
														//return false;
													} else {
														$("#error_claimScType")
																.hide()
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
</body>
</html>