<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<c:url var="checkPass" value="checkPass" />
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


				<div
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
						<%-- <a href="${pageContext.request.contextPath}/showLocationList"
							class="breadcrumb-elements-item"> Location List</a> --%>

					</div>


				</div>
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
								<h6 class="card-title">Change Password</h6>
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
									action="${pageContext.request.contextPath}/submitUpdatePass"
									id="submitInsertLocaion" method="post">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="locName">Current
											Password <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="password" class="form-control"
												placeholder="Enter Your Current Password" id="currPass"
												name="currPass" autocomplete="off" onchange="trimmm(this)">
											<span class="validation-invalid-label" id="error_currPass"
												style="display: none;">Invalid Password</span>
										</div>
									</div>
									<div id="abc" style="display: none;">
										<div class="form-group row">
											<label class="col-form-label col-lg-2" for="password">New
												Password <span style="color: red">* </span>:
											</label>
											
											<div class="col-lg-4">
												<input type="password" class="form-control"
													placeholder="Enter New Password" id="password"
													name="password" autocomplete="off"
													onkeyup="return passwordChanged();"> <span
													id="strength">Type Password</span> <span
													class="validation-invalid-label" id="error_password"
													style="display: none;">This field is required.</span>
											</div>
											<div class="col-lg-6">
											<span class="form-text text-muted">contain minimum 8 letter,one capital letter,one small letter, one digit, one special character</span>
											</div>
										</div>
										
										<div class="form-group row">
											<label class="col-form-label col-lg-2" for="password">New
												Password <span style="color: red">* </span>:
											</label>
											
											<div class="col-lg-4">
												<input type="password" class="form-control"
													placeholder="Enter New Password" id="password1"
													name="password1" autocomplete="off">  
													<span
													class="validation-invalid-label" id="error_password1"
													style="display: none;">This field is required.</span>
											</div>
											 
										</div>
									</div>

									<input type="hidden" id="empId" name="empId" value="${empId}">
									<input type="hidden" id="allowPass" name="allowPass" value="0">

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/changePass"><button
													type="button" class="btn btn-primary">
													<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
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
		function trimmm(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			checkAdd();
			return;
		}

		$(document).ready(function($) {

			$("#submitInsertLocaion").submit(function(e) {
				var isError = false;
				var errMsg = "";
				var allowPass = document.getElementById("allowPass").value;
				$("#error_password").hide();
				$("#error_password1").hide();
				if (!$("#password").val()) {

					isError = true;

					$("#error_password").show();
					//return false;
				}  
				  
				if (!$("#password").val()) {

					isError = true;

					$("#error_password").show();
					//return false;
				} 
				if (allowPass==0) {

					isError = true;
					$("#error_password").html("password should be strong.");
					$("#error_password").show();
					//return false;
				}
				if (!$("#password1").val()) { 
					isError = true;
					$("#error_password1").html("This field is required.");
					$("#error_password1").show();
					return false;
				}
				if ($("#password").val()!=$("#password1").val()) {

					isError = true;
					$("#error_password1").html("password not matched.");
					$("#error_password1").show();
					//return false;
				}
				//return false;
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

	<script>
		function checkAdd() {

			//alert("hii..");
			var empId = document.getElementById("empId").value;
			var password = document.getElementById("currPass").value;
			//alert("hii.." + empId + password);

			if (password.length > 0) {

				$.post('${checkPass}', {
					empId : empId,
					password : password,
					ajax : 'true',

				}, function(data) {
					//alert(data.error);
					if (data.error == false) {

						document.getElementById("currPass").readOnly = true;
						document.getElementById("abc").style.display = "block";
						$("#error_currPass").hide();
						$("#error_password").hide();
						$("#error_password1").hide();

					} else {

						$("#error_currPass").show();
					}
				});
			} else {
				$("#error_currPass").hide();
			}

		}
	</script>

	<script>
		function passwordChanged() {
			/* var strength = document.getElementById("strength");
			$("#error_password").hide();
			var strongRegex = new RegExp(
					"^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$",
					"g");
			var mediumRegex = new RegExp(
					"^(?=.{6,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",
					"g");
			var enoughRegex = new RegExp("(?=.{6,}).*", "g");
			var pwd = document.getElementById("password").value;

			if (pwd.length == 0) {
				document.getElementById("strength").innerHTML = "Type Password";
				document.getElementById("allowPass").value=0;
			} else if (false == enoughRegex.test(pwd)) {
				document.getElementById("strength").innerHTML = "More Characters";
				document.getElementById("allowPass").value=0;
			} else if (strongRegex.test(pwd)) {
				document.getElementById("strength").innerHTML = "<span style='color:green'>Strong!</span>";
				document.getElementById("allowPass").value=1;
			} else if (mediumRegex.test(pwd)) {
				document.getElementById("strength").innerHTML = "<span style='color:orange'>Medium!</span>";
				document.getElementById("allowPass").value=0;
			} else {
				document.getElementById("strength").innerHTML = "<span style='color:red'>Weak!</span>";
				document.getElementById("allowPass").value=0;
			} */
			
			var strength = document.getElementById("strength");
			$("#error_password").hide();
			 
			
			var strongRegex = /^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\W).*$/;
			var mediumRegex = /^(?=.{6,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$/;
			var enoughRegex = /(?=.{6,}).*/;
			
			var pwd = document.getElementById("password").value;

			if (pwd.length == 0) {
				document.getElementById("strength").innerHTML = "Type Password";
				document.getElementById("allowPass").value=0;
			} else if (false == enoughRegex.test(pwd)) {
				document.getElementById("strength").innerHTML = "More Characters";
				document.getElementById("allowPass").value=0;
			} else if (strongRegex.test(pwd)) {
				document.getElementById("strength").innerHTML = "<span style='color:green'>Strong!</span>";
				document.getElementById("allowPass").value=1;
			} else if (mediumRegex.test(pwd)) {
				document.getElementById("strength").innerHTML = "<span style='color:orange'>Medium!</span>";
				document.getElementById("allowPass").value=0;
			} else {
				document.getElementById("strength").innerHTML = "<span style='color:red'>Weak!</span>";
				document.getElementById("allowPass").value=0;
			}
		}
	</script>

</body>
</html>