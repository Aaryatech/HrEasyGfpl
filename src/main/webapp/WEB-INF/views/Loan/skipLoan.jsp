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
										<td width="60%"><h5 class="card-title">Skip Loan</h5></td>
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
									action="${pageContext.request.contextPath}/submitSkipLoan"
									id="submitInsertLocaion" method="post">
									<input type="hidden" value="${empPersInfo.empId}" id="empId"
										name="empId"> <input type="hidden"
										value="${advList.id}" id="id" name="id">


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="empName">Employee
											Name : </label> <label class="col-form-label col-lg-4" for="empName">${empPersInfoString}
										</label>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="grossSal">Total
											Gross Salary: </label> <label class="col-form-label col-lg-2"
											for="grossSal">${empPersInfo.grossSalary} </label>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="voucherNo">Loan
											Application No. : </label> <label class="col-form-label col-lg-2"
											for="voucherNo"> ${advList.loanApplNo} </label>

									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="advanceAmt">Date
											 : </label> <label class="col-form-label col-lg-2" for="voucherNo">
											${advList.loanAddDate} </label>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">Loan
											Amount : </label> <label class="col-form-label col-lg-2"
											for="voucherNo"> ${advList.loanAmt} </label>
									</div>



									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">Reason
											/ Remark : </label> <label class="col-form-label col-lg-2"
											for="voucherNo"> ${advList.remark} </label>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">
											Prev Skipped Reason : </label>
										<c:if test="${advList.skipId==0}">
											<label class="col-form-label col-lg-2" for="voucherNo">
												${skipStr} </label>
										</c:if>
										<c:if test="${advList.skipId>0}">


											<label class="col-form-label col-lg-2" for="voucherNo">
												<c:forEach items="${listOfString}" var="listOfString"
													varStatus="count">${count.index+1}.${listOfString}<br>
												</c:forEach>
											</label>
										</c:if>

									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">Skipped
											Time : </label> <label class="col-form-label col-lg-2"
											for="voucherNo"> ${advList.skipId} </label>
									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">Last
											Skipped date: </label> <label class="col-form-label col-lg-2"
											for="voucherNo"> ${advList.skipLoginTime} </label>
									</div>


									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="remark">
											Skipping Reason <span style="color: red">*</span>:
										</label>
										<div class="col-lg-4">
											<textarea class="form-control"
												placeholder="Enter Reason / Remark" id="remark"
												name="remark" autocomplete="off" onchange="trim(this)"></textarea>
											<span class="validation-invalid-label" id="error_remark"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<!-- <button type="submit" class="btn btn-primary"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											 -->
											<input type="submit" class="btn btn-primary" value="Submit"
									id="submtbtn">
											 
											<a
												href="${pageContext.request.contextPath}/showLoanListForAction?empId=${encEmpId}"><button
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
		<!-- /main content -->

	</div>
	<!-- /page content -->

	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			checkSame();
			return;
		}

		$(document).ready(function($) {

			$("#submitInsertLocaion").submit(function(e) {

				var isError = false;
				var errMsg = "";

				if (!$("#remark").val()) {

					isError = true;

					$("#error_remark").show()

				} else {
					$("#error_remark").hide()
				}
				
			 
				if (!isError) {

				 
						$('#modal_scrollable')
						.modal('show');

 						return false;
						
					}
					//end ajax send this to php page
				
				return false;
			});
		});
		//
	</script>
<script>
		function submitForm() {
			$('#modal_scrollable').modal('hide');
			document.getElementById("submtbtn").disabled = true;
			document.getElementById("submitInsertLocaion").submit();

		}
	</script>
		<div id="modal_scrollable" class="modal fade" data-backdrop="false"
		tabindex="-1">
		<div class="modal-dialog modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header pb-3">

					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<div class="modal-body py-0">
					<h5 class="modal-title">Are You Sure You Want  To Submit This Record </h5>
					<br>
				 
				</div>

				<div class="modal-footer pt-3">
					<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn bg-primary" onclick="submitForm()">Submit</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>