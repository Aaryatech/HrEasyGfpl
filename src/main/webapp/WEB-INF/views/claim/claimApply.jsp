<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>


<c:url var="addClaimDetailProcess" value="/addClaimDetailProcess" />

<c:url var="getClaimForEdit" value="/getClaimForEdit" />

<c:url var="getLeaveStructureForEdit" value="/getLeaveStructureForEdit" />
<c:url var="addStrDetail" value="/addStrDetail" />
<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>

<body onload="chkAssign()">
	<c:url value="/getClaimTypeById" var="getClaimTypeById"></c:url>

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
						<a href="${pageContext.request.contextPath}/showApplyForClaim"
							class="breadcrumb-elements-item"> Employee List</a>

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
								<td width="60%"><h5 class="card-title">Add Claim</h5></td>
								<td width="40%" align="right">
								<%--  <a
									href="${pageContext.request.contextPath}/showApplyForClaim"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary"> Employee List </button>
								</a> --%> </td>
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
								<span class="validation-invalid-label" id="error_assign"
									style="display: none;">Sorry You Can Not Apply for Claim
									as Claim Authorities Are not Assigned !!</span>


								<div class="form-group row">
									<label class="col-form-label col-lg-2" for="lvsName">
										Employee Code <span style="color: red">* </span>:
									</label>
									<div class="col-lg-10">
										<input type="text" class="form-control"
											placeholder="Enter Leave Structure Name" id="empCode"
											value="${editEmp.empCode}" name="lvsName" autocomplete="off"
											onchange="trim(this)" readonly>

									</div>
								</div>
								<div class="form-group row">
									<label class="col-form-label col-lg-2" for="lvsName">
										Employee Name <span style="color: red">* </span>:
									</label>
									<div class="col-lg-10">
										<input type="text" class="form-control"
											placeholder="Enter Leave Structure Name" id="empName"
											value="${editEmp.firstName} ${editEmp.middleName} ${editEmp.surname}   "
											name="lvsName" autocomplete="off" onchange="trim(this)"
											readonly>

									</div>
								</div>




								<form action="${pageContext.request.contextPath}/showClaimProof"
									id="submitInsertLeave" method="post">

<%-- 
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="projectTypeId">Select
											Project <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select name="projectTypeId"
												data-placeholder="Select Project" id="projectTypeId"
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true">
												<option></option>
												<c:forEach items="${projectTypeList}" var="proTypeList">
													<option value="${proTypeList.projectId}"
														data-prostrname="${proTypeList.projectTitle}">${proTypeList.projectTitle}</option>
												</c:forEach>
											</select> <span class="validation-invalid-label"
												id="error_projectTypeId" style="display: none;">This
												field is required.</span>
										</div>

									</div> --%>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="lvsName">
											Claim Remark <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" placeholder="Remark"
												id="claim_title" name="claim_title" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_claim_title"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2">Date Range<span
											style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control daterange-basic_new "
												name="claimDate" data-placeholder="Select Date"
												id="claimDate"> <span
												class="validation-invalid-label" id="error_Range"
												style="display: none;">This field is required.</span>

										</div>
									</div>

									<hr>
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="claimTypeId">Select
											Claim Type <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select name="claimTypeId"
												data-placeholder="Select Claim Type" id="claimTypeId"
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" onchange="setAmt()">
												<option></option>
												<c:forEach items="${claimTypeList}" var="claimTypeList">

													<option value="${claimTypeList.clmTypeId}"
														data-clstrname="${claimTypeList.claimTypeTitle}">${claimTypeList.claimTypeTitle}</option>

												</c:forEach>
											</select> <span class="validation-invalid-label"
												id="error_claimTypeId" style="display: none;">This
												field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="claimAmt">
											Claim Amount <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control numbersOnly"
												 placeholder="Amount of Claim in Rs. "
												id="claimAmt" name="claimAmt" autocomplete="off"> <span
												class="validation-invalid-label" id="error_claim_amt"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="lvngReson">Remark
											<span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Remark" onchange="trim(this)" id="claimRemark"
												name="claimRemark"> </textarea>
											<span class="validation-invalid-label" id="error_claimRemark"
												style="display: none;">This field is required.</span>
										</div>

									</div>
									<input type="hidden" id="isDelete" name="isDelete" value="0">
									<input type="hidden" name="isEdit" id="isEdit" value="0">
									<input type="hidden" name="index" id="index" value="0">
									<input type="hidden" name="tempAmt" id="tempAmt" value="0">
									<input type="hidden" name="tempAmtX" id="tempAmtX" value="0">

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">
											<input type="button" value="Add" class="btn btn-primary"
												style="align-content: center; width: 113px;" onclick="add()" />

										</div>
									</div>
									<br />

									<table
										class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
										id="printtable1">
										<thead>
											<tr class="bg-blue">
												<th width="10%">Sr.no</th>
												<th>Claim Type</th>
												<th>Amount</th>
												<th>Remark</th>
												<th class="text-center" width="10%">Actions</th>
											</tr>
										</thead>
										<tbody>
									</table>

									<br />


									<!-- 		Final Submit		 -->

									<input type="hidden" class="form-control numbersOnly"
										id="dataLen" value="0" name="dataLen"> <input
										type="hidden" class="form-control numbersOnly"
										value="${editEmp.empId}" id="empId" name="empId"
										autocomplete="off" readonly> <input type="hidden"
										class="form-control numbersOnly" id="auth"
										value="${authorityInformation.claimInitialAuth}" name="auth">

									<span class="validation-invalid-label" id="error_tbl"
										style="display: none;">Please Fill Claim Details
										Properly. </span>


									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>

											<a
												href="${pageContext.request.contextPath}/showApplyForClaim"><button
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

	<script type="text/javascript">
		function setAmt() {

			
			var claimTypeId = document.getElementById("claimTypeId").value;
			
			var empId = document.getElementById("empId").value;
			//alert("hii"+claimTypeId+empId);

			$.getJSON('${getClaimTypeById}', {
				claimTypeId : claimTypeId,
				empId : empId,
				ajax : 'true',
			},

			function(data) {

				document.getElementById("tempAmtX").value = data.clmAmt;

			});

		}
	</script>http://localhost:8088/hradmin/showClaimApply?empId=MQ==


	

	<script type="text/javascript">
		function add() {

			var valid = true;
			var claimTypeId = document.getElementById("claimTypeId").value;

			var claimAmt = document.getElementById("claimAmt").value;
			var claimRemark = document.getElementById("claimRemark").value;
		//	alert("hii" + claimTypeId + claimAmt);

		//	alert("final amt::" + y);

			if (claimTypeId == "") {

				$("#error_claimTypeId").show()

			} else {
				$("#error_claimTypeId").hide()

			}
			if (claimAmt == 0) {

				$("#error_claim_amt").show()

			} else {
				$("#error_claim_amt").hide()

			}
			if (claimRemark == "") {

				$("#error_claimRemark").show()

			} else {
				$("#error_claimRemark").hide()
			}

			if (claimTypeId == "" || claimAmt == 0 || claimRemark == "") {
				valid = false;

			} else {
				valid = true;

				var x = document.getElementById("tempAmt").value;
				var y = parseInt(x) + parseInt(claimAmt);
				document.getElementById("tempAmt").value = y;
				
				
				
				var tempAmt = document.getElementById("tempAmtX").value;
				var claimAmt = document.getElementById("claimAmt").value;
			 
				
				$('#lmAmtNew')
				.html(
						document
								.getElementById("tempAmtX").value);
 				if (parseFloat(claimAmt) > parseFloat(tempAmt)) {

				//	alert("Amonut Entered Exceeds the Limit Amount");
					$('#modal_scrollable1')
					.modal('show');
				}
 				
 				else{
 					
 					var valid = true;
 					
 				 
 					
 					var el = document.getElementById('claimTypeId');
 					var lvTypeName = el.options[el.selectedIndex].innerHTML;
 				//	alert("lvTypeName  " + lvTypeName);

 					var daterange = document.getElementById("claimDate").value;
 					var res = daterange.split(" to ");

 					var isEdit = document.getElementById("isEdit").value;
 					var isDelete = document.getElementById("isDelete").value;
 					var index = document.getElementById("index").value;
 					 if (valid == true) {
 						 
 						// alert("hii");

 						$
 								.getJSON(
 										'${addClaimDetailProcess}',
 										{

 											isDelete : isDelete,
 											isEdit : isEdit,
 											index : index,
 											claimAmt : claimAmt,
 											claimRemark : claimRemark,
 											lvTypeName : lvTypeName,
 											claimTypeId : claimTypeId,
 											ajax : 'true',

 										},

 										function(data) {
 											//alert(data.length);
 											document.getElementById("dataLen").value = data.length;

 											$("#printtable1 tbody").empty();
 											$("#loader").hide();

 											for (var i = 0; i < data.length; i++) {

 												var str = '<a href="#" class="action_btn" onclick="callDelete('
 														+ data[i].claimDetailId
 														+ ','
 														+ i
 														+ ')" style="color:black"><i class="fa fa-trash"></i></a>'
 												var tr_data = '<tr>' + '<td>' + (i + 1)
 														+ '</td>' + '<td>'
 														+ data[i].lvTypeName + '</td>'
 														+ '<td>' + data[i].claimAmount
 														+ '</td>' + '<td>'
 														+ data[i].remark
 														+ '</td><td>' + str + '</td>'
 														+ '</tr>';
 												$('#printtable1' + ' tbody').append(
 														tr_data);

 											}
 											$('#modal_scrollable1').modal('hide');
 											
 											
 										

 											
 										});

 						
 						document.getElementById("claimRemark").value = "";
 						document.getElementById("claimAmt").value = 0;

 						document.getElementById("isDelete").value = 0;
 						document.getElementById("isEdit").value = 0;
 						document.getElementById("index").value = 0;
 					} 
 					
 					 
 					
 				}
			}
 
		}
		
		
		function finalAdd() {
			var valid = true;
			
			var claimTypeId = document.getElementById("claimTypeId").value;

			var claimAmt = document.getElementById("claimAmt").value;
			var claimRemark = document.getElementById("claimRemark").value;
			
			var el = document.getElementById('claimTypeId');
			var lvTypeName = el.options[el.selectedIndex].innerHTML;
		//	alert("lvTypeName  " + lvTypeName);

			var daterange = document.getElementById("claimDate").value;
			var res = daterange.split(" to ");

			var isEdit = document.getElementById("isEdit").value;
			var isDelete = document.getElementById("isDelete").value;
			var index = document.getElementById("index").value;
			 if (valid == true) {
				 
				// alert("hii");

				$
						.getJSON(
								'${addClaimDetailProcess}',
								{

									isDelete : isDelete,
									isEdit : isEdit,
									index : index,
									claimAmt : claimAmt,
									claimRemark : claimRemark,
									lvTypeName : lvTypeName,
									claimTypeId : claimTypeId,
									ajax : 'true',

								},

								function(data) {
									//alert(data.length);
									document.getElementById("dataLen").value = data.length;

									$("#printtable1 tbody").empty();
									$("#loader").hide();

									for (var i = 0; i < data.length; i++) {

										var str = '<a href="#" class="action_btn" onclick="callDelete('
												+ data[i].claimDetailId
												+ ','
												+ i
												+ ')" style="color:black"><i class="fa fa-trash"></i></a>'
										var tr_data = '<tr>' + '<td>' + (i + 1)
												+ '</td>' + '<td>'
												+ data[i].lvTypeName + '</td>'
												+ '<td>' + data[i].claimAmount
												+ '</td>' + '<td>'
												+ data[i].remark
												+ '</td><td>' + str + '</td>'
												+ '</tr>';
										$('#printtable1' + ' tbody').append(
												tr_data);

									}
									$('#modal_scrollable1').modal('hide');
									
									
								

									
								});

				
				document.getElementById("claimRemark").value = "";
				document.getElementById("claimAmt").value = 0;

				document.getElementById("isDelete").value = 0;
				document.getElementById("isEdit").value = 0;
				document.getElementById("index").value = 0;
			} 
		}
		
	</script>

	<script type="text/javascript">
		function callEdit(claimDetailId, index) {

			document.getElementById("isEdit").value = "1";
			$.getJSON('${getClaimForEdit}', {
				claimDetailId : claimDetailId,
				index : index,
				ajax : 'true',

			}, function(data) {

				document.getElementById("index").value = index;

				document.getElementById("claimRemark").value = data.remark;
				document.getElementById("claimAmt").value = data.claimAmount;

				document.getElementById("claimTypeId").value = data.lvTypeName;

			});

		}
	</script>
	<script type="text/javascript">
		function callDelete(termDetailId, index) {

			//alert("hii");
			//document.getElementById("isEdit").value = 0;
			//alert("index" + index);
			$
					.getJSON(
							'${addClaimDetailProcess}',
							{
								isDelete : 1,
								isEdit : 0,
								key : index,
								ajax : 'true',

							},


							function(data) {
								 

								$("#printtable1 tbody").empty();
								$("#loader").hide();

								for (var i = 0; i < data.length; i++) {

									var str = '<a href="#" class="action_btn" onclick="callDelete('
											+ data[i].claimDetailId
											+ ','
											+ i
											+ ')" style="color:black"><i class="fa fa-trash"></i></a>'
									var tr_data = '<tr>' + '<td>' + (i + 1)
											+ '</td>' + '<td>'
											+ data[i].lvTypeName + '</td>'
											+ '<td>' + data[i].claimAmount
											+ '</td>' + '<td>'
											+ data[i].remark
											+ '</td><td>' + str + '</td>'
											+ '</tr>';
									$('#printtable1' + ' tbody').append(
											tr_data);

								}
								

								
							});
 
		}
	</script>

	<script type="text/javascript">
		function validate(s) {
			var rgx = /^[0-9]*\.?[0-9]*$/;
			return s.match(rgx);
		}
		function callAlert(msg) {
			alert(msg);
		}
	</script>



	<script type="text/javascript">
		// Single picker
		$('.datepickerclass').daterangepicker({
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});

		//daterange-basic_new
		// Basic initialization
		$('.daterange-basic_new').daterangepicker({
			applyClass : 'bg-slate-600',

			cancelClass : 'btn-light',
			locale : {
				format : 'DD-MM-YYYY',
				separator : ' to '
			}
		});
	</script>

	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			return;
		}

		$(document)
				.ready(
						function($) {

							$("#submitInsertLeave")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												/* if (!$("#projectTypeId").val()) {

													isError = true;

													$("#error_projectTypeId")
															.show()

												} else {
													$("#error_projectTypeId")
															.hide()
												}
 */
												if (!$("#claimDate").val()) {

													isError = true;

													$("#error_Range").show()

												} else {
													$("#error_Range").hide()
												}

												if (!$("#claim_title").val()) {

													isError = true;

													$("#error_claim_title")
															.show()

												} else {
													$("#error_claim_title")
															.hide()
												}

												if ($("#dataLen").val() == 0) {
													isError = true;
													$("#error_tbl").show()
												} else {
													$("#error_tbl").hide()
												}
												if (!isError) {

												/* 	var option1 = $(
															"#projectTypeId option:selected")
															.attr(
																	"data-prostrname");

													$('#proName').html(option1) */

													$('#claimAmt1')
															.html(
																	document
																			.getElementById("tempAmt").value);
													$('#empCode1')
															.html(
																	document
																			.getElementById("empCode").value);
													$('#empName1')
															.html(
																	document
																			.getElementById("empName").value);

													$('#claimDate1')
															.html(
																	document
																			.getElementById("claimDate").value);

													$('#modal_scrollable')
															.modal('show');

													//end ajax send this to php page
												}
												return false;
											});
						});
	</script>



	<script type="text/javascript">
		function chkAssign() {

			var auth = document.getElementById("auth").value;

			//alert("hii"+auth);
			if (auth == 0) {
				document.getElementById("submtbtn").disabled = true;

				$("#error_assign").show()
			} else {
				document.getElementById("submtbtn").disabled = false;
			}
		}
	</script>
	<script>
		function submitForm() {
			$('#modal_scrollable').modal('hide');
			document.getElementById("submtbtn").disabled = true;
			document.getElementById("submitInsertLeave").submit();

		}
	</script>
	<script type="text/javascript">
		function checkAmt() {

			//alert("hii");
			
		}
	</script>
	
	<div id="modal_scrollable1" class="modal fade" data-backdrop="false"
		tabindex="-1">
		<div class="modal-dialog modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header pb-3">
 					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
 				<div class="modal-body py-0">
					<h5 class="modal-title">Amount Entered Exceeds the Limit Amount </h5>
					<div class="form-group row">
						<label class="col-form-label col-lg-3" for="asd">
						Limit Amount : </label> <label class="col-form-label col-lg-2"
							id="lmAmtNew" for="lmAmt"> </label>

					</div>
 				</div>
 				<div class="modal-footer pt-3">
					<button type="button" class="btn btn-link" onclick="finalAdd()">Ok</button>
 				</div>
			</div>
		</div>
	</div>
	
	
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

				<!-- 	<div class="form-group row">
						<label class="col-form-label col-lg-3" for="proName">
							Project Name : </label> <label class="col-form-label col-lg-6"
							id="proName" for="proName"> </label>

					</div> -->


					<div class="form-group row">
						<label class="col-form-label col-lg-3" for="noOfDays">
							Claim Amount : </label> <label class="col-form-label col-lg-3"
							id="claimAmt1" for="claimAmt1"> </label>

					</div>
					<div class="form-group row">
						<label class="col-form-label col-lg-3" for="claimDate1">
							Claim Date : </label> <label class="col-form-label col-lg-7"
							id="claimDate1" for="claimDate1"> </label>

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