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
						<a
							href="${pageContext.request.contextPath}/showLeaveStructureList"
							class="breadcrumb-elements-item"> Leave Structure List</a>

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
												Structure</h5></td>
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
									action="${pageContext.request.contextPath}/insertLeaveStructure"
									id="insertLeaveStructure" method="post">
									<div class="form-group row">
										<label
											class="col-form-label text-info font-weight-bold col-lg-2"
											for="lvsName">Leave Structure Name <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Leave Structure Name" id="lvsName"
												name="lvsName" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label" id="error_lvsName"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									<div class="form-group row">

										<label
											class="col-form-label text-info font-weight-bold col-lg-3"
											for="select2">Calculation Cash AMT of Leave Balance <span
											style="color: red">* </span> :
										</label>
										<div class="col-lg-7">
											<select name="allowanceIds"
												data-placeholder="Select Structure Allotment"
												id="allowanceIds"
												class="form-control form-control-select2 select2-hidden-accessible"
												tabindex="-1" aria-hidden="true" multiple>
												<c:forEach items="${allowanceList}" var="allowanceList">

													<option value="${allowanceList.allowanceId}">${allowanceList.name}</option>

												</c:forEach>
											</select>
										</div>
									</div>
									<hr>
									<div class="form-group row">
										<strong> Leaves Types :</strong>
									</div>


									<c:forEach items="${leaveTypeList}" var="leaveType">

										<div class="form-group row">
											<label
												class="col-form-label text-info font-weight-bold col-lg-2		"
												for="noOfLeaves${leaveType.lvTypeId}">${leaveType.lvTitle}
												<span class="text-danger">* </span>:
											</label>
											<div class="col-md-1">
												<input type="text" class="form-control numbersOnly"
													placeholder="No.of Leaves Peryear"
													id="noOfLeaves${leaveType.lvTypeId}" value="0"
													name="noOfLeaves${leaveType.lvTypeId}" autocomplete="off"
													onchange="trim(this)">
											</div>

											<label
												class="col-form-label text-info font-weight-bold col-lg-1"
												for="min${leaveType.lvTypeId}">Min Days <span
												class="text-danger">* </span>:
											</label>
											<div class="col-md-1">
												<input type="text" class="form-control numbersOnly"
													id="min${leaveType.lvTypeId}" value="0"
													name="min${leaveType.lvTypeId}" autocomplete="off"
													onchange="chkVal(${leaveType.lvTypeId})">
											</div>

											<label
												class="col-form-label text-info font-weight-bold col-lg-1"
												for="max${leaveType.lvTypeId}">Max Days<span
												class="text-danger">* </span>:
											</label>
											<div class="col-md-1">
												<input type="text" class="form-control numbersOnly"
													id="max${leaveType.lvTypeId}" value="0"
													name="max${leaveType.lvTypeId}" autocomplete="off"
													onchange="chkVal(${leaveType.lvTypeId})"> <span
													class="validation-invalid-label"
													id="error_prsnName${leaveType.lvTypeId}"
													style="display: none;">Min. Days Should be Less than
													Max. Days .</span> <span class="validation-invalid-label"
													id="error_prsnName1${leaveType.lvTypeId}"
													style="display: none;">Min. Days & Max. Days Should
													be Less than Leave Type Days.</span>
											</div>

											<label
												class="col-form-label text-info font-weight-bold col-lg-1"
												for="maxCarryForword${leaveType.lvTypeId}">Max Carry
												Forward<span class="text-danger">* </span>:
											</label>
											<div class="col-md-1">
												<input type="text" class="form-control numbersOnly"
													id="maxCarryForword${leaveType.lvTypeId}" value="0"
													name="maxCarryForword${leaveType.lvTypeId}"
													autocomplete="off">
											</div>

											<label
												class="col-form-label text-info font-weight-bold col-lg-1"
												for="isInCash${leaveType.lvTypeId}">In Cash : </label>
											<div class="col-md-1">
												<input type="checkbox" id="isInCash${leaveType.lvTypeId}"
													value="0" name="isInCash${leaveType.lvTypeId}"
													autocomplete="off">
											</div>

										</div>
									</c:forEach>
									<input type="hidden" name="err_flag" id="err_flag" value="0">

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue mr-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a
												href="${pageContext.request.contextPath}/showLeaveStructureList"><button
													type="button" class="btn btn-light">
													<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>Back
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
		function  chkVal(id){
			
			var minDays=document.getElementById("min"+id).value;
			var maxDays=document.getElementById("max"+id).value;
			var Days=document.getElementById("noOfLeaves"+id).value;
 			
			/* if(parseFloat(maxDays)< parseFloat(minDays)){
				$("#error_prsnName"+id).show()
				
				document.getElementById("err_flag").value=1;
				

			}else{
				$("#error_prsnName"+id).hide()
				document.getElementById("err_flag").value=0;


			}
			 
			if(parseFloat(Days)< parseFloat(minDays)  || parseFloat(Days)< parseFloat(maxDays)){
				$("#error_prsnName1"+id).show()
				
				document.getElementById("err_flag").value=1;
				

			}else{
				$("#error_prsnName1"+id).hide()
				document.getElementById("err_flag").value=0;


			} */
		}
	
			</script>
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

			$("#insertLeaveStructure").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#lvsName").val()) {

					isError = true;

					$("#error_lvsName").show()
					//return false;
				} else {
					$("#error_lvsName").hide()
				}
				

				if ($("#err_flag").val()==1) {
 					isError = true;
 
				}  

			 
				if (!isError) {

					var x = true ;
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