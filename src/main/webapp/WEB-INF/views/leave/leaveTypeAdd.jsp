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
	<c:url value="/checkUniqueLeaveType" var="checkUniqueLeaveType"></c:url>
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

				<%-- 
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
						<a href="${pageContext.request.contextPath}/showLeaveTypeList"
							class="breadcrumb-elements-item">  Leave Type List </a>

				</div>
			</div> --%>
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
										<td width="60%"><h5 class="card-title">Add Leave
												Type</h5></td>
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
								<form
									action="${pageContext.request.contextPath}/submitInsertLeaveType"
									id="submitInsertCompany" method="post">


									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="leaveTypeTitle"> Leave Type<span
											class="text-danger"> *</span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Leave Type" id="leaveTypeTitle"
												name="leaveTypeTitle" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_leaveType"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="leaveShortTypeTitle"> Short Name <span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Leave Type Short Name "
												id="leaveShortTypeTitle" name="leaveShortTypeTitle"
												autocomplete="off" onchange="trim(this)"
												oninput="checkUniqueShortName(this.value)"> <span
												class="validation-invalid-label" id="error_leaveScType"
												style="display: none;">This field is required.</span> <span
												class="validation-invalid-label" id="error_leaveShortname"
												style="display: none;">Already exist.</span> <span
												class="validation-invalid-label" id="error_sameName"
												style="display: none;">Leave Type Short Name Can Not
												be same as Leave Type Name. </span>



										</div>
									</div>

									<!-- 
								<div class="form-group row">
										<label class="col-form-label col-lg-2" for="leaveWorlHrs">Working Hours<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Leave Working Hours " id="leaveWorlHrs"
												name="leaveWorlHrs" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_leaveWHours"
												style="display: none;">This field is required.</span>
										</div>
									</div>

 -->

									<div class="form-group row">
										<label class="col-form-label col-lg-2">Color:</label>
										<div class="col-lg-10">
											<input type="text" id="leaveColor" name="leaveColor"
												class="form-control colorpicker-show-input"
												data-preferred-format="hex" value="#f75d1c" data-fouc>
										</div>
									</div>
								<!-- 	<div class="form-group row">
										<label class="col-form-label col-lg-2" for="comoffallowed">Is
											Structured <span style="color: red"> </span>:
										</label>
										<div class="form-check form-check-inline">
											<label class="form-check-label"> <input type="radio"
												class="form-check-input" name="isStructured"
												id="isStructured" checked value="1"> Yes
											</label>
										</div>
										<div class="form-check form-check-inline">
											<label class="form-check-label"> <input type="radio"
												class="form-check-input" name="isStructured"
												id="isStructured" value="0"> No
											</label>
										</div>
									</div> -->
									
									<input type="hidden" id="isStructured" name="isStructured" value="1">
 


									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="isFile">Is File Upload Required <span style="color: red">*</span>:
										</label>
										<div class="form-check form-check-inline">
											<label class="form-check-label"> <input type="radio"
												class="form-check-input" name="isFile" id="isFile" checked
												value="1"> Yes
											</label>
										</div>
										<div class="form-check form-check-inline">
											<label class="form-check-label"> <input type="radio"
												class="form-check-input" name="isFile" id="isFile" value="0">
												No
											</label>
										</div>

										<span class="validation-invalid-label" id="error_isFile"
											style="display: none;">This field is required.</span>
									</div>



									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="select2">Leave
											Summary:</label>
										<div class="col-lg-10">
											<select name="summId" data-placeholder="Select a Summary"
												id="summId"
												class="form-control form-control-select2 select2-hidden-accessible"
												required data-fouc="" tabindex="-1" aria-hidden="true">
												<option value="0">Please Select</option>
												<c:forEach items="${sumList}" var="sumList">

													<option value="${sumList.lvSumupId}">${sumList.lvSumupTitle}</option>

												</c:forEach>


											</select>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">Any
											Remark:</label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Any Remark" onchange="trim(this)" id="remark"
												name="remark"></textarea>
										</div>
									</div>

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">
											<!-- 										
 -->
											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a
												href="${pageContext.request.contextPath}/showLeaveTypeList"><button
													type="button" class="btn btn-light">
													<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
													Back
												</button></a>
										</div>
									</div>
								</form>
								<p class="desc text-danger fontsize11">Notice : * Fields are
									mandatory.</p>
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
	</div>

	<!-- /main content -->


	<!-- /page content -->

	<script type="text/javascript">
		function checkUniqueShortName(valueType) {
			//alert("hi");

			document.getElementById("submtbtn").disabled = false;

			var valid = false;

			if (valueType != '') {
				valid = true;

			} else {
				valid = false;
			}
			if (valid == true)

				$
						.getJSON(
								'${checkUniqueLeaveType}',
								{
									valueType : valueType,
									ajax : 'true',

								},
								function(data) {
									$("#error_leaveShortname").hide()
									//alert("data" + +JSON.stringify(data));
									if (data.error == false) {
										//alert("Already exist")
										$("#error_leaveShortname").show()
										document
												.getElementById("leaveShortTypeTitle").value = "";
									}

								});

		}
	</script>
	<script type="text/javascript">
		function checkSame() {
			x = document.getElementById("leaveTypeTitle").value;
			y = document.getElementById("leaveShortTypeTitle").value;
			//alert(x);

			if (x !== '' && y !== '') {
				if (x == y) {
					$("#error_sameName").show()
					document.getElementById("leaveShortTypeTitle").value = "";
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
			replace(/\n +/, "\n"); // Removes spaces after newlines\
			checkSame()
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

		$(document).ready(function($) {

			$("#submitInsertCompany").submit(function(e) {
				var isError = false;
				var errMsg = "";
				$("#error_leaveShortname").hide()
				if (!$("#leaveTypeTitle").val()) {

					isError = true;

					$("#error_leaveType").show()
					//return false;
				} else {
					$("#error_leaveType").hide()
				}

				if (!$("#leaveShortTypeTitle").val()) {

					isError = true;

					$("#error_leaveScType").show()
					//return false;
				} else {
					$("#error_leaveScType").hide()
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