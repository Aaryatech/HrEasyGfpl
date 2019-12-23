<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>

<body>
<c:url var="getPayDeductionTypeRate" value="/getPayDeductionTypeRate"></c:url>
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
								<td width="60%"><h5 class="card-title">Add Employee Payment Deduction</h5></td>
								<td width="40%" align="right">
							  
								 <%-- <a
									href="${pageContext.request.contextPath}/showAddKra?empId=${editKra.exVar3}&finYrId=${editKra.exVar2}"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">KRA List </button>
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
									action="${pageContext.request.contextPath}/insertPayDeductDetail"
									id="insertPayDeductDetail" method="post">
								
								<div class="form-group row">
									<input type="hidden" value="${deduct.dedId}" id="dedId" name="dedId">
									<input type="hidden" value="${deduct.empId}" id="empId" name="empId">
									<input type="hidden" value="${currentYear}" id="year" name="year">
								</div>
								<div class="form-group row">
										<label class="col-form-label col-lg-2" for="dedRate">Employee
											 Code<span style="color:red"></span>:</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" value="${deduct.empCode}" readonly="readonly">
												
										</div>
									</div> 
									
								<div class="form-group row">
										<label class="col-form-label col-lg-2" for="dedRate">Employee Name
											 <span style="color:red"></span>:</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" value="${deduct.empName}" readonly="readonly"> 
										</div>
									</div>
								
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="dedTypeId">Select
													Deduction Type <span style="color: red"> </span>:
												</label>
												<div class="col-lg-4">
													<select name="dedTypeId"
														data-placeholder="Select Deduction Type" id="dedTypeId" onchange="getDeductRate(this.value)"
														class="form-control form-control-select21 select2-hidden-accessible1">
														<c:forEach items="${payDeductList}"
															var="payDeductList">	
															<c:choose>
																<c:when test="${deduct.dedTypeId==payDeductList.dedTypeId}">
																	<option selected="selected" value="${payDeductList.dedTypeId}">${payDeductList.typeName}</option>
																</c:when>
																<c:otherwise>																
																	<option value="${payDeductList.dedTypeId}">${payDeductList.typeName}</option>
																</c:otherwise>															
															</c:choose>														
																
													</c:forEach>
													</select> <span class="hidedefault   validation-invalid-label"
														style="display: none;" id="error_empType">This field is required.</span>
												</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="dedRate">Amount
											 <span style="color:red"></span>:</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
												placeholder="Enter Address" id="dedRate" value="${deduct.dedRate}"
												name="dedRate" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_address"
												style="display: none;">This field is required.</span>
												
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="month">Deduction Month
											 <span style="color:red"> </span>:</label>
										<div class="col-lg-4">
											<select name="month"
														data-placeholder="Select Deduction Type" id="month"
														class="form-control form-control-select21 select2-hidden-accessible1">
													 <option value=''>Select Month</option>
												    <option value='1' ${deduct.month==1 ? 'selected' : ''}>Janaury</option>
												    <option value='2' ${deduct.month==2 ? 'selected' : ''}>February</option>
												    <option value='3' ${deduct.month==3 ? 'selected' : ''}>March</option>
												    <option value='4' ${deduct.month==4 ? 'selected' : ''}>April</option>
												    <option value='5' ${deduct.month==5 ? 'selected' : ''}>May</option>
												    <option value='6' ${deduct.month==6 ? 'selected' : ''}>June</option>
												    <option value='7' ${deduct.month==7 ? 'selected' : ''}>July</option>
												    <option value='8' ${deduct.month==8 ? 'selected' : ''}>August</option>
												    <option value='9' ${deduct.month==9 ? 'selected' : ''}>September</option>
												    <option value='10' ${deduct.month==10 ? 'selected' : ''}>October</option>
												    <option value='11' ${deduct.month==11 ? 'selected' : ''}>November</option>
												    <option value='12' ${deduct.month==12 ? 'selected' : ''}>December</option>
											</select>
												
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">Reason / Remark :
											Name <span style="color:red"></span>:</label>
										<div class="col-lg-4">
											<textarea class="form-control"
												placeholder="Enter Reason / Remark" id="remark"
												name="remark" autocomplete="off" onchange="trim(this)">${deduct.dedRemark}</textarea>
											<span class="validation-invalid-label" id="error_micrCode"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">
										
											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/payDeductionDetails"><button
										type="button" class="btn btn-light"><i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp; Back</button></a>
										</div>
									</div>
								</form>
								<!-- <p class="desc text-danger fontsize11">Notice : * Fields are
									mandatory.</p> -->
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
	
	function getDeductRate(deductType) {
		$
			.getJSON(
					'${getPayDeductionTypeRate}',

					{
						 
						deductType : deductType,
						ajax : 'true'

					},
					function(data) {
						$('#dedRate').val(data.dedRate);

					});
	}
	
	function checkSame(){
		x=document.getElementById("locName").value;
		y=document.getElementById("locShortName").value;
		//alert(x);
		
		if(x!== '' && y!== ''){
			if(x==y){
				$("#error_sameName").show()
				document.getElementById("locShortName").value="";
			}
			else{
				$("#error_sameName").hide()
			}
	}
		
	}
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			checkSame();
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

							$("#submitInsertLocaion")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#bankName").val()) {

													isError = true;

													$("#error_bank").show()
													//return false;
												} else {
													$("#error_bank").hide()
												}

												if (!$("#address").val()) {

													isError = true;

													$("#error_address")
															.show()

												} else {
													$("#error_address")
															.hide()
												}
												if (!$("#branchName").val()) {

													isError = true;

													$("#error_branchName")
															.show()

												} else {
													$("#error_branchName")
															.hide()
												}
												if (!$("#micrCode").val()) {

													isError = true;

													$("#error_micrCode")
															.show()

												} else {
													$("#error_micrCode")
															.hide()
												}
												if (!$("#ifscCode").val()) {

													isError = true;

													$("#error_ifscCode")
															.show()

												} else {
													$("#error_ifscCode")
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
	
	
	
	<!-- <script type="text/javascript">
	$('#submtbtn').on('click', function() {
        swalInit({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger',
            buttonsStyling: false
        }).then(function(result) {
            if(result.value) {
                swalInit(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                );
            }
            else if(result.dismiss === swal.DismissReason.cancel) {
                swalInit(
                    'Cancelled',
                    'Your imaginary file is safe :)',
                    'error'
                );
            }
        });
    });
	
	</script> -->

</body>
</html>