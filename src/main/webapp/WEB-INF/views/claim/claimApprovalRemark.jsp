<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
									<label class="col-form-label col-lg-2" for="compName">Employee
										Code : </label>
									<div class="col-lg-6">
										<input type="text" class="form-control"
											Value="${lvEmp.empCode}" id="empCode" name="compName"
											autocomplete="off" readonly>

									</div>
								</div>
								<div class="form-group row">
									<label class="col-form-label col-lg-2" for="compName">Employee
										Name : </label>
									<div class="col-lg-6">
										<input type="text" class="form-control"
											Value="${lvEmp.empFname} ${lvEmp.empSname}" id="empName"
											name="compName" autocomplete="off" readonly>

									</div>
								</div>

								<div class="form-group row">
									<label class="col-form-label col-lg-2" for="compName">Claim
										Title : </label>
									<div class="col-lg-6">
										<input type="text" class="form-control"
											Value="${lvEmp.claimTitle}" id="clName" name="compName"
											autocomplete="off" readonly>

									</div>
								</div>
								<div class="form-group row">
									<label class="col-form-label col-lg-2" for="compName">Claim
										Date : </label>
									<div class="col-lg-6">
										<input type="text" class="form-control"
											Value="${lvEmp.caFromDt} to ${lvEmp.caToDt} " id="cldate"
											name="compName" autocomplete="off" readonly>

									</div>
								</div>


								<div class="form-group row">
									<label class="col-form-label col-lg-2" for="compName">Claim
										Amount : </label>
									<div class="col-lg-6">
										<input type="text" class="form-control"
											Value="${lvEmp.claimAmount}" id="claimAmt" name="compName"
											autocomplete="off" readonly>

									</div>
								</div>

								<div class="form-group row">
									<label class="col-form-label col-lg-2" for="compName">Project
										Title : </label>
									<div class="col-lg-6">
										<input type="text" class="form-control"
											Value="${lvEmp.projectTitle}" id="proTitle" name="compName"
											autocomplete="off" readonly>

									</div>
								</div>
 
								<h6 class="card-title">Claim Detail</h6>
								<table
									class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
									id="printtable1">
									<thead>
										<tr class="bg-blue">
											<th width="10%">Sr.no</th>
											<th>Claim Type</th>
											<th>Amount</th>
											<th>Remark</th>
											<th>Limit</th>
										</tr>
									</thead>
									<tbody>


										<c:forEach items="${claimDetList}" var="lvTypeList"
											varStatus="count">
											<tr>
												<td>${count.index+1}</td>
												<td>${lvTypeList.claimTypeTitle}</td>
												<td>${lvTypeList.claimAmount}</td>
												<td>${lvTypeList.claimRemarks}</td>
												<c:forEach items="${claimTypeList}" var="claimTypeList">
													<c:if
														test="${claimTypeList.clmTypeId==lvTypeList.claimTypeId}">
														<td>${claimTypeList.clmAmt}</td>
													</c:if>
												</c:forEach>
											</tr>
										</c:forEach>

									</tbody>
								</table>

								<br />
								<h6 class="card-title">Claim Trail History</h6>

								<table
									class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
									id="printtable1">
									<thead>
										<tr class="bg-blue">
											<th width="10%">Sr.no</th>
											<!-- <th>Name</th> -->
											<th>Action By</th>
											<th>Remark</th>
											<th>Date</th>
											<th>Claim Status</th>
										</tr>
									</thead>
									<tbody>


										<c:forEach items="${employeeList}" var="empTrailList"
											varStatus="count">
											<tr>
												<td>${count.index+1}</td>
												<%-- <td>${empTrailList.empSname}&nbsp;${empTrailList.empFname}</td> --%>

												<td>${empTrailList.userName}</td>
												<c:choose>
													<c:when
														test="${empTrailList.empRemarks=='null' || empty empTrailList.empRemarks}">
														<td>-</td>
													</c:when>
													<c:otherwise>
														<td>${empTrailList.empRemarks}</td>
													</c:otherwise>
												</c:choose>
												<td>${empTrailList.makerEnterDatetime}</td>

												<c:if test="${empTrailList.claimStatus==1}">
													<td><span class="badge badge-info">Applied</span></td>
												</c:if>
												<c:if test="${empTrailList.claimStatus==2}">
													<td><span class="badge badge-secondary">Applied</span></td>
												</c:if>
												<c:if test="${empTrailList.claimStatus==3}">
													<td><span class="badge badge-success">Final
															Approved</span></td>
												</c:if>
												<c:if test="${empTrailList.claimStatus==7}">
													<td><span class="badge badge-danger">Leave
															Cancelled</span></td>
												</c:if>
												<c:if test="${empTrailList.claimStatus==8}">
													<td><span class="badge badge-danger"> Rejected</span></td>
												</c:if>
												<c:if test="${empTrailList.claimStatus==9}">
													<td><span class="badge badge-danger"> Rejected</span></td>
												</c:if>


											</tr>
										</c:forEach>

									</tbody>
								</table>

								<form
									action="${pageContext.request.contextPath}/approveClaimByAuth1"
									id="submitInsertCompany" method="post">


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">Any
											Remark:</label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Any Remark" onchange="trim(this)" id="remark"
												name="remark"></textarea>
											<span class="validation-invalid-label" id="error_remark"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									<input type="hidden" id="retun" name="retun" value="${retun}">
									<input type="hidden" id="empId" name="empId" value="${empId}">
									<input type="hidden" id="leaveId" name="claimId"
										value="${claimId}"> <input type="hidden" id="stat"
										name="stat" value="${stat}">

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">
											<!-- 											<button type="reset" class="btn btn-light legitRipple">Reset</button>
 -->
											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<c:choose>
												<c:when test="${retun==0}">
													<a
														href="${pageContext.request.contextPath}/showClaimApprovalByAdmin"><button
															type="button" class="btn bg-blue ml-3 legitRipple"
															id="cancel">
															Cancel<i class="icon-paperplane ml-2"></i>
														</button></a>
												</c:when>
												<c:otherwise>
													<a
														href="${pageContext.request.contextPath}/showClaimApprovalByAuthority"><button
															type="button" class="btn bg-blue ml-3 legitRipple"
															id="cancel">
															Cancel<i class="icon-paperplane ml-2"></i>
														</button></a>
												</c:otherwise>
											</c:choose>


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
		$(document)
				.ready(
						function($) {

							$("#submitInsertCompany")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";
												if ($("#stat").val() == 7
														|| $("#stat").val() == 8
														|| $("#stat").val() == 9) {
													if (!$("#remark").val()) {

														isError = true;

														$("#error_remark")
																.show()

													} else {
														$("#error_remark")
																.hide()
													}

												}

												if (!isError) {

													$('#clTitle1')
															.html(
																	document
																			.getElementById("clName").value);
													//alert("asdf");
													$('#claimAmt1')
															.html(
																	document
																			.getElementById("claimAmt").value);
													$('#empCode1')
															.html(
																	document
																			.getElementById("empCode").value);
													$('#empName1')
															.html(
																	document
																			.getElementById("empName").value);
													$('#proName')
															.html(
																	document
																			.getElementById("proTitle").value);

													$('#claimDate1')
															.html(
																	document
																			.getElementById("cldate").value);

													$('#modal_scrollable')
															.modal('show');

													//end ajax send this to php page
												}
												return false;
											});
						});
		//
	</script>
	<script>
		function submitForm() {
			$('#modal_scrollable').modal('hide');
			document.getElementById("submtbtn").disabled = true;
			document.getElementById("submitInsertCompany").submit();

		}
	</script>


	<!-- Scrollable modal -->
	<div id="modal_scrollable" class="modal fade" data-backdrop="false"
		tabindex="-1">
		<div class="modal-dialog modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header pb-3">

					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<div class="modal-body py-0">
					<h5 class="modal-title">Claim Details</h5>
					<br>
					<div class="form-group row">
						<label class="col-form-label col-lg-3" for="empCode1">
							Employee Code : </label> <label class="col-form-label col-lg-2"
							id="empCode1" for="empCode1"> </label>

					</div>
					<div class="form-group row">
						<label class="col-form-label col-lg-3" for="empName1">
							Employee Name : </label> <label class="col-form-label col-lg-6"
							id="empName1" for="empName1"> </label>

					</div>
					<div class="form-group row">
						<label class="col-form-label col-lg-3" for="clType"> Claim
							Title : </label> <label class="col-form-label col-lg-6" id="clTitle1"
							for="clTitle1"> </label>

					</div>
					<div class="form-group row">
						<label class="col-form-label col-lg-3" for="proName">
							Project Name : </label> <label class="col-form-label col-lg-6"
							id="proName" for="proName"> </label>

					</div>


					<div class="form-group row">
						<label class="col-form-label col-lg-3" for="noOfDays">
							Claim Amount : </label> <label class="col-form-label col-lg-3"
							id="claimAmt1" for="claimAmt1"> </label>

					</div>
					<div class="form-group row">
						<label class="col-form-label col-lg-3" for="claimDate1">
							Date : </label> <label class="col-form-label col-lg-7" id="claimDate1"
							for="claimDate1"> </label>

					</div>


				</div>

				<div class="modal-footer pt-3">
					<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn bg-primary" onclick="submitForm()">Submit</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /scrollable modal -->

</body>
</html>