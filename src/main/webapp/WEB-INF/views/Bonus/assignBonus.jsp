<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
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
			<div class="page-header page-header-light"></div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">


					<div class="card-header header-elements-inline">
						<table width="100%">
							<tr width="100%">
								<td width="60%"><h5 class="card-title">Employee Bonus
										Assignment</h5></td>
								<td width="40%" align="right"><a
									href="${pageContext.request.contextPath}/showBonusList"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">Bonus
											List</button>
								</a></td>
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


						<%-- 	<div class="form-group row">
								<label class="col-form-label col-lg-2" for="locId">
									Select Bonus to Assign <span style="color: red">* </span>:
								</label>
								<div class="col-lg-10">
									<select name="bonusId" data-placeholder="Select  "
										id="bonusId"
										class="form-control form-control-select2 select2-hidden-accessible"
										data-fouc="" aria-hidden="true">

										<option value="">Select </option>

										<c:forEach items="${bonusList}" var="bonusList">
											<option value="${bonusList.bonusId}">${bonusList.fyTitle}</option>
										</c:forEach>
									</select> <span class="validation-invalid-label" id="error_bonusId"
										style="display: none;">This field is required.</span>
								</div>
							</div> --%>


						<div class="form-group row">
							<label class="col-form-label text-info font-weight-bold col-lg-2" for="shiftName">Bonus
								Title <span style="color: red">* </span>:
							</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="bonusTitle"
									value="${bonusName}" readonly="readonly" name="bonusTitle"
									autocomplete="off">
							</div>
						</div>


						<ul class="nav nav-tabs nav-tabs-highlight nav-justified1">
							<li class="nav-item"><a href="#highlighted-justified-tab1"
								class="nav-link active" data-toggle="tab">Unassigned </a></li>
							<li class="nav-item"><a href="#highlighted-justified-tab2"
								class="nav-link" data-toggle="tab">Assigned</a></li>

						</ul>


						<div class="tab-content">
							<div class="tab-pane fade show active"
								id="highlighted-justified-tab1">
								<form
									action="${pageContext.request.contextPath}/submitAssignBonusToEmp"
									id="submitInsertEmp" method="post">

									<input type="hidden" id="bonusId" name="bonusId"
										value="${bonusId}">


									<table
										class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
										id="printtable1">
										<thead>
											<tr class="bg-blue">

												<th width="10%">Sr.no</th>

												<th><input type="checkbox" name="selAll" id="selAll" /></th>
												<th>Employee Code</th>
												<th>Employee Name</th>
												<th>Emp Type</th>
												<th>Department</th>
												<th>Designation</th>
												<th>Location</th>

											</tr>
										</thead>
										<tbody>


											<c:forEach items="${empdetList}" var="empdetList"
												varStatus="count">
												<tr>

													<td>${count.index+1}</td>
													<td><input type="checkbox"
														id="empId${empdetList.empId}" value="${empdetList.empId}"
														name="empId" class="select_all"></td>
													<td>${empdetList.empCode}</td>
													<td>${empdetList.surname}&nbsp;${empdetList.middleName}&nbsp;${empdetList.firstName}</td>
													<td>${empdetList.empTypeName}</td>
													<td>${empdetList.deptName}</td>
													<td>${empdetList.empDesgn}</td>
													<td>${empdetList.locName}</td>


												</tr>
											</c:forEach>

										</tbody>
									</table>

									<span class="validation-invalid-label" id="error_chk"
										style="display: none;">Please Select the Employee.</span>


									<div style="text-align: center;">
										<input type="submit" class="btn btn-primary" value="Assign"
											id="deleteId"
											style="align-content: center; width: 113px; margin-left: 40px;">
									</div>
								</form>


							</div>
							<div class="tab-pane fade" id="highlighted-justified-tab2">


								<table
									class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
									id="printtable1">
									<thead>
										<tr class="bg-blue">

											<th width="10%">Sr. No.</th>
											<th>Emp Code</th>
											<th>Emp Name</th>
											<th>Designation</th>
											<!-- 	<th>Total Days</th> -->
											<th>Total Wages</th>
											<th>Gross Bonus Amt</th>
											<th>Ded Bonus Puja Amt</th>
											<th>Ded Bonus Adv Amt</th>
											<th>Ded Bonus Loss_amt</th>
											<th>Net Bonus Amt</th>
											<th>Paid Bonus Amt</th>
											<th width="10%" class="text-center">Actions</th>
										</tr>
									</thead>
									<tbody>


										<c:forEach items="${bonusCalcList}" var="bonusList"
											varStatus="count">
											<tr>
												<td>${count.index+1}</td>
												<td>${bonusList.companyEmpCode}</td>
												<td>${bonusList.empName}</td>
												<td>${bonusList.currDesignation}</td>
												<%-- 										<td>${bonusList.totalBonusDays}</td>
 --%>
												<td>${bonusList.totalBonusWages}</td>
												<td>${bonusList.grossBonusAmt}</td>
												<td>${bonusList.dedBonusPujaAmt}</td>
												<td>${bonusList.dedBonusAdvAmt}</td>
												<td>${bonusList.dedBonusLossAmt}</td>
												<td>${bonusList.netBonusAmt}</td>
												<td>${bonusList.paidBonusAmt}</td>

												<%-- 												<c:if test="${payRollFinal==1}">
 --%>
												<td class="text-center"><a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${bonusList.exVar1}"
													data-abc="${bonusList.exVar2}" data-popup="tooltip"
													title="" data-original-title="Delete"><i
														class="icon-trash"></i></a></td>
												<%-- </c:if> --%>
											</tr>
										</c:forEach>

									</tbody>
								</table>


								<c:if test="${isfinalized ne '1'}">
									<form
										action="${pageContext.request.contextPath}/submitBonusApplicable"
										id="submitBonusApplicable" method="post">
										<input type="hidden" id="isFinal" name="isFinal"
											value="${isfinalized}"> <input type="hidden"
											id="bonusId" name="bonusIdNew" value="${bonusId}"> <input
											type="hidden" id="bonusAppId" name="bonusAppId"
											value="${bonusAppId}">
										<div class="form-group row">


											<label class="col-form-label text-info font-weight-bold col-lg-2" for="startDate">
												Pay Bonus In This Salary Month <span style="color: red">*
											</span>:
											</label>
											<div class="col-lg-4">
												<input type="text" class="form-control datepickerclass "
													name="startDate" id="startDate" placeholder="  Date">
												<span class="validation-invalid-label" id="error_startDate"
													style="display: none;">This field is required.</span>
											</div>


										</div>

										<div class="form-group row">
											<label class="col-form-label text-info font-weight-bold col-lg-2" for="remark">
												Remark <span style="color: red">*</span>:
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

												<button type="submit" class="btn bg-blue ml-3 legitRipple"
													id="submtbtn">
													Submit <i class="icon-paperplane ml-2"></i>
												</button>

											</div>
										</div>
									</form>
								</c:if>
							</div>
						</div>

					</div>

				</div>
				<!-- /highlighting rows and columns -->

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
		function submitForm() {
			$('#modal_scrollable').modal('hide');
			document.getElementById("deleteId").disabled = true;
			document.getElementById("submitInsertEmp").submit();

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
					<h5 class="modal-title">Are You Sure You Want  To Assign This Bonus </h5>
					<br>
				 
				</div>

				<div class="modal-footer pt-3">
					<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn bg-primary" onclick="submitForm()">Submit</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<script>
		function submitForm1() {
			$('#modal_scrollable').modal('hide');
			document.getElementById("submtbtn").disabled = true;
			document.getElementById("submitBonusApplicable").submit();

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
					<h5 class="modal-title">Are You Sure You Want  To  Finalize Bonus </h5>
					<br>
				 
				</div>

				<div class="modal-footer pt-3">
					<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn bg-primary" onclick="submitForm1()">Submit</button>
				</div>
			</div>
		</div>
	</div>


	<script type="text/javascript">
		$(document).ready(function($) {
			$("#submitInsertEmp").submit(function(e) {

				var isError = false;
				var errMsg = "";
				var shiftId = $("#shiftId").val();

				var checked = $("#submitInsertEmp input:checked").length > 0;
				if (!checked) {
					$("#error_chk").show()
					isError = true;
				} else {
					$("#error_chk").hide()
					isError = false;
				}
				//alert("checked" +checked);
				/* if (bonusId == null || bonusId == "") {
					isError = true;
					$("#error_bonusId").show()
				} else {
					$("#error_bonusId").hide()
				} */

				if (!isError) {
					 
					$('#modal_scrollable')
					.modal('show');

						return false;
 				}
				return false;
			});
		});
	</script>

	<script type="text/javascript">
		$(document).ready(
				function() {
					//	$('#printtable').DataTable();

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);
							});
				});
	</script>


	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			checkSame();
			return;
		}

		$(document)
				.ready(
						function($) {

							$("#submitBonusApplicable")
									.submit(
											function(e) {

												var isError = false;
												var errMsg = "";

												if ($("#isFinal").val() != 1) {

													if (!$("#startDate").val()) {

														isError = true;

														$("#error_startDate")
																.show()
														//return false;
													} else {
														$("#error_startDate")
																.hide()
													}

													if (!$("#remark").val()) {

														isError = true;

														$("#error_remark")
																.show()

													} else {
														$("#error_remark")
																.hide()
													}

													if (!isError) {
														 
														$('#modal_scrollable1')
														.modal('show');

															return false;
									 				}
												}
												return false;
											});
						});
		//
	</script>

	<script>
		// Custom bootbox dialog
		$('.bootbox_custom')
				.on(
						'click',
						function() {
							var uuid = $(this).data("uuid") // will return the number 123
							var abc = $(this).data("abc") // will return the number 123
							bootbox
									.confirm({
										title : 'Confirm ',
										message : 'Are you sure you want to delete selected records ?',
										buttons : {
											confirm : {
												label : 'Yes',
												className : 'btn-success'
											},
											cancel : {
												label : 'Cancel',
												className : 'btn-link'
											}
										},
										callback : function(result) {
											if (result) {
												location.href = "${pageContext.request.contextPath}/deleteBonusCalc?bonusCalcId="
														+ uuid
														+ "&bonusId="
														+ abc;

											}
										}
									});
						});
	</Script>

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
</body>
</html>