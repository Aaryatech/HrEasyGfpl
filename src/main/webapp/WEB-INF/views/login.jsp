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
	href="${pageContext.request.contextPath}/resources/assets/css/loginstyle.css"
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

</head>
<style>
body1 {
	/* background-image:
		url("${pageContext.request.contextPath}/resources/global_assets/images/bg3.jpeg"); */
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
	position: relative;
}

.no-background1 {
	background-image:
		url("${pageContext.request.contextPath}/resources/global_assets/images/bg2.jpg");
}
</style>
<body>
<div class="page_loader"></div>

<!-- Login 17 start -->
<div class="login-17">
    <div class="container">
        <div class="col-md-12 pad-0">
            <div class="row login-box-6">
                <div class="col-lg-5 col-md-12 col-sm-12 col-pad-0 bg-img align-self-center none-992">
                    <a href="http://aaryatechindia.in/" target="_blank">
                        <img src="${pageContext.request.contextPath}/resources/global_assets/images/companylogo.png" class="logo" alt="logo">
                    </a>
                    <p><span class="navbar-text"> &copy; 2019 - 2022. <a href="#">Powered
					</a> by <a href="http://aaryatechindia.in/" class="navbar-text" target="_blank">AARYA
							TECH SOLUTIONS</a></p>
                    <a href="http://aaryatechindia.in/" target="_blank" ><img
						src="${pageContext.request.contextPath}/resources/global_assets/images/powerdBy.png"
						width="60" height="50" alt=""></a>
                    
                </div>
                <div class="col-lg-7 col-md-12 col-sm-12 col-pad-0 align-self-center">
                    <div class="login-inner-form">
                        <div class="details">
                        <img
						src="${pageContext.request.contextPath}/resources/global_assets/images/companylogo.png"
						  alt="">
                            <h3>Sign into your account</h3>
                           <c:if test="${msg=null}">
									<div class="alert alert-danger" >${msg}</div>
									 
								</c:if>
								<c:if test="${sessionScope.errorPassMsg!=null}">
								<div class="alert alert-danger" >${sessionScope.errorPassMsg}</div>
								 
								<%
									session.removeAttribute("errorPassMsg");
								%>
							</c:if>
						
                            <form class="login-form"
					action="${pageContext.request.contextPath}/loginProcess"
					id="submitInsertEmpType" method="post">
						<c:if test="${sessionScope.errorMsg!=null}">
							<div class="alert alert-danger" >${sessionScope.errorMsg}</div>
									 
									<%
										session.removeAttribute("errorMsg");
									%>
								</c:if>
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
								
							</div>
                                <div class="checkbox clearfix">
                                     
                                    <a href="${pageContext.request.contextPath}/showForgotPass">Forgot Password?</a>
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn-md btn-theme btn-block">Login</button>
                                </div>
                                	<div class="d-lg-none">
                                	<span class="navbar-text"> &copy; 2019 - 2022. <a href="#">Powered
					</a> by <a href="http://aaryatechindia.in/" class="navbar-text" target="_blank">AARYA
							TECH SOLUTIONS</a></p>
                    <a href="http://aaryatechindia.in/" target="_blank" ><img
						src="${pageContext.request.contextPath}/resources/global_assets/images/powerdBy.png"
						width="60" height="50" alt=""></a>
                                	</div>
                            </form>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Login 17 end -->

	 

</body>
</html>