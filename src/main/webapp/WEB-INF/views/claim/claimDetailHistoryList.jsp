<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		<%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<!-- <div class="page-header page-header-light">


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


				</div>
			</div> -->
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
								<h6 class="card-title"></h6>
							<!-- 	<div class="header-elements">
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
<div class="form-group row">
										<label class="col-form-label col-lg-2" for="compName">Employee Code
											: </label>
										<div class="col-lg-6">
											<input type="text" class="form-control"
												 Value="${lvEmp.empCode}"
												name="compName" autocomplete="off" readonly>
											
										</div>
									</div>
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="compName">Employee Name
											: </label>
										<div class="col-lg-6">
											<input type="text" class="form-control"
												 Value="${lvEmp.empFname} ${lvEmp.empSname}"
												name="compName" autocomplete="off" readonly>
											
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="compName">Claim Title
											: </label>
										<div class="col-lg-6">
											<input type="text" class="form-control"
												 Value="${lvEmp.claimTitle}"
												name="compName" autocomplete="off" readonly>
											
										</div>
									</div>
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="compName">Claim Date
											: </label>
										<div class="col-lg-6">
											<input type="text" class="form-control"
												  Value="${lvEmp.caFromDt} to ${lvEmp.caToDt} "
												name="compName" autocomplete="off" readonly>
											
										</div>
									</div>
									
						
						<div class="form-group row">
										<label class="col-form-label col-lg-2" for="compName">Claim Amount
											: </label>
										<div class="col-lg-6">
											<input type="text" class="form-control"
												 Value="${lvEmp.claimAmount}"
												name="compName" autocomplete="off" readonly>
											
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="compName">Project Title
											: </label>
										<div class="col-lg-6">
											<input type="text" class="form-control"
												 Value="${lvEmp.projectTitle}"
												name="compName" autocomplete="off" readonly>
											
										</div>
									</div>
						
					<h6 class="card-title">Claim Trail History</h6>
								
<table
							class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
							id="printtable1">
							<thead>
								<tr class="bg-blue">
									<th width="10%">Sr.no</th>
									<th>Name</th>								
									<th>Remark</th>
									<th>Date</th>
									<th>Action By</th>
									<th>Claim Status</th>
 								</tr>
							</thead>
							<tbody>


								<c:forEach items="${employeeList}" var="empTrailList" varStatus="count">
									<tr>
										<td>${count.index+1}</td>
										<td>${empTrailList.empSname} ${empTrailList.empFname}</td>
									
										
										<c:choose>
										<c:when test="${empTrailList.empRemarks=='null' || empty empTrailList.empRemarks}">
											<td>-</td>
										</c:when>
										<c:otherwise>
										<td>${empTrailList.empRemarks}</td>
										</c:otherwise>
										</c:choose>									
										<td>${empTrailList.makerEnterDatetime}</td>
										<td>${empTrailList.userName}</td>
										<c:if test="${empTrailList.claimStatus==1}">
										<td><span class="badge badge-info">Initial Pending</span></td>
										</c:if>
										<c:if test="${empTrailList.claimStatus==2}">
										<td><span class="badge badge-secondary">Final Pending</span></td>
										</c:if>
											<c:if test="${empTrailList.claimStatus==3}">
										<td><span class="badge badge-success">Final Approved</span></td>
										</c:if>
											<c:if test="${empTrailList.claimStatus==7}">
										<td><span class="badge badge-danger">Leave Cancelled</span></td>
										</c:if>
											<c:if test="${empTrailList.claimStatus==8}">
										<td><span class="badge badge-danger">Initial Rejected</span></td>
										</c:if>
											<c:if test="${empTrailList.claimStatus==9}">
										<td><span class="badge badge-danger">Final Reject</span></td>
										</c:if>
	
									
									</tr>
								</c:forEach>

							</tbody>
						</table>
						
								
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

			$("#submitInsertCompany").submit(function(e) {
				var isError = false;
				var errMsg = "";

				
				if (!isError) {

					var x = confirm("Do you really want to submit the form?");
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