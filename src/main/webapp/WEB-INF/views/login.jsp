<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>HR Management</title>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/global_assets/images/companylogo.png"
	type="image/x-icon" />
<!-- Global stylesheets -->
<link
	href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/global_assets/css/icons/icomoon/styles.min.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap_limitless.min.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/assets/css/layout.min.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/assets/css/components.min.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/assets/css/colors.min.css"
	rel="stylesheet" type="text/css">
<!-- /global stylesheets -->

<!-- Core JS files -->
<script
	src="${pageContext.request.contextPath}/resources/global_assets/js/main/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/global_assets/js/main/bootstrap.bundle.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/global_assets/js/plugins/loaders/blockui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/global_assets/js/plugins/ui/ripple.min.js"></script>
<!-- /core JS files -->

<!-- Theme JS files -->
<script
	src="${pageContext.request.contextPath}/resources/assets/js/app.js"></script>
<!-- /theme JS files -->

</head>
<style>
body {
	/* background-image:
		url("${pageContext.request.contextPath}/resources/global_assets/images/bg3.jpeg"); */
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
	position: relative;
}

.no-background {
	background-image:
		url("${pageContext.request.contextPath}/resources/global_assets/images/bg2.jpg");
}
</style>
<body>

	<!-- Main navbar -->
	<div
		class="navbar navbar-expand-md navbar-dark bg-indigo navbar-static">
		<div class="navbar-brand">
			<%-- <a href="index.html" class="d-inline-block">  <img
				src="${pageContext.request.contextPath}/resources/global_assets/images/logo_light.png"
				alt="">
			</a> --%>
			<img
				src="${pageContext.request.contextPath}/resources/global_assets/images/companylogo.png"
				alt="" style="height: 50px; width: 150px;">
		</div>

		<div class="d-md-none">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbar-mobile">
				<i class="icon-tree5"></i>
			</button>
		</div>

		<div class="collapse navbar-collapse" id="navbar-mobile">
			<ul class="navbar-nav">
			</ul>



		</div>
	</div>
	<!-- /main navbar -->


	<!-- Page content -->
	<div class="page-content">

		<!-- Main content -->
		<div class="content-wrapper">

			<!-- Content area -->
			<div class="content d-flex justify-content-center align-items-center">

				<!-- Login form -->
				<form class="login-form"
					action="${pageContext.request.contextPath}/loginProcess"
					id="submitInsertEmpType" method="post">
					<div class="card mb-0">
						<div class="card-body">
							<div class="text-center mb-3">
							<c:if test="${msg=null}">
									<span style="color: red;">${msg}</span>
									 
								</c:if>
								<c:if test="${sessionScope.errorPassMsg!=null}">
								<span style="color: red;">${sessionScope.errorPassMsg}</span>
								<%
									session.removeAttribute("errorPassMsg");
								%>
							</c:if>
								<i
									class="icon-reading icon-2x text-slate-300 border-slate-300 border-3 rounded-round p-3 mb-3 mt-1"></i>
								<h5 class="mb-0">Login to your account</h5>
								<span class="d-block text-muted">Enter your credentials
									below</span>
							</div>

							<div
								class="form-group form-group-feedback form-group-feedback-left">
								<input type="text" id="username" name="username"
									class="form-control" placeholder="Username">
								<div class="form-control-feedback">
									<i class="icon-user text-muted"></i>
								</div>
							</div>

							<div
								class="form-group form-group-feedback form-group-feedback-left">
								<input type="password" id="password" name="password"
									class="form-control" placeholder="Password">
								<div class="form-control-feedback">
									<i class="icon-lock2 text-muted"></i>
								</div>
								<c:if test="${sessionScope.errorMsg!=null}">
									<span style="color: red;">${sessionScope.errorMsg}</span>
									<%
										session.removeAttribute("errorMsg");
									%>
								</c:if>
							</div>

							<div class="form-group">
								<button type="submit" class="btn btn-primary btn-block">
									Sign in <i class="icon-circle-right2 ml-2"></i>
								</button>
							</div>

							<div class="text-center">
 								<a href="${pageContext.request.contextPath}/showForgotPass">Forgot password?</a>
<%--  								<a href="${pageContext.request.contextPath}/showForgotPass">sdfsdf</a>
 --%> 							</div>
						</div>
					</div>
				</form>
				<!-- /login form -->

			</div>
			<!-- /content area -->


			<!-- Footer -->
			<div class="navbar navbar-expand-lg navbar-light">
				<!-- <div class="text-center d-lg-none w-100">
					<button type="button" class="navbar-toggler dropdown-toggle"
						data-toggle="collapse" data-target="#navbar-footer">
						<i class="icon-unfold mr-2"></i> Footer
					</button>
				</div> -->

				<div class="navbar-collapse1 collapse1 text-center w-100"
					id="navbar-footer">
					<span class="navbar-text"> &copy; 2019 - 2022. <a href="#">Powered
					</a> by <a href="http://aaryatechindia.in/atsuc/" target="_blank">AARYA
							TECH SOLUTIONS</a>
					</span>&nbsp; <img
						src="${pageContext.request.contextPath}/resources/global_assets/images/powerdBy.png"
						width="60" height="50" alt="">


				</div>
			</div>
			<!-- /footer -->

		</div>
		<!-- /main content -->

	</div>
	<!-- /page content -->

</body>
</html>