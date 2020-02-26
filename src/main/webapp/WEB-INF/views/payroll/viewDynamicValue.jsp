<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>
<!-- <link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css"> -->
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

					<div id="editBonusDiv" style="display: none;">
						<div class="card-header header-elements-inline">
							<h5 class="card-title">Edit Attendance Detail</h5>
						</div>
						<div class="card-body">
							<div class="form-group row">
								<label class="col-form-label col-lg-2" for="empCode">
									EMP Code : </label>
								<div class="col-lg-2">
									<input type="text" class="form-control" placeholder="EMP Code"
										id="empCode" name="empCode" autocomplete="off" disabled>

								</div>

							</div>

							<div class="form-group row">
								<label class="col-form-label col-lg-2" for="itAmt"> IT
									Ammount : </label>
								<div class="col-lg-2">
									<input type="text" class="form-control numbersOnly"
										placeholder="IT Ammount" id="itAmt" name="itAmt"
										autocomplete="off">

								</div>
								<div class="col-lg-1"></div>
								<label class="col-form-label col-lg-2" for="perBonus">
									Performance Bonus : </label>
								<div class="col-lg-2">
									<input type="text" class="form-control numbersOnly"
										placeholder="Status" id="perBonus" name="perBonus"
										autocomplete="off">

								</div>
							</div>

							<!-- <div class="form-group row">
								<label class="col-form-label col-lg-2" for="comnt">
									Comments : </label>
								<div class="col-lg-2">
									<input type="text" class="form-control" placeholder="Comments"
										id="comnt" name="comnt" autocomplete="off">

								</div>
							</div> -->

							<div class="col-md-12 text-center">
								<button type="submit" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn" onclick="saveBonusDetail()">Save</button>
								<button type="button" class="btn btn-primary"
									onclick="closeEditDetailTab()">Cancel</button>
								<input type="hidden" class="form-control" id="tempSalDaynamicId"
									name="tempSalDaynamicId" autocomplete="off">
							</div>


						</div>
						<hr>
					</div>
					<div class="card-header header-elements-inline">
						<h5 class="card-title">Enter Dynamic Amount ${date}</h5>
						<!-- <div class="header-elements">
							<div class="list-icons">
								<a class="list-icons-item" data-action="collapse"></a>
							</div> 
						</div>-->
					</div>

					<div class="card-body">

						<form action="${pageContext.request.contextPath}/generatePayRoll"
							id="submitInsertLeave" method="post">

							<input type="hidden" name="searchDate" id="searchDate"
								value="${date}" /> <input type="hidden" name="empIds"
								id="empIds" value="${empIds}" />
							<div id="loader" style="display: none;">
								<img
									src="${pageContext.request.contextPath}/resources/assets/images/giphy.gif"
									width="150px" height="150px"
									style="display: block; margin-left: auto; margin-right: auto">
							</div>

							<div class="table-responsive">
								<table
									class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
									id="bootstrap-data-table1">
									<thead>
										<tr class="bg-blue">
											<th width="5%" class="text-center">Sr.no</th>
											<th class="text-center">EMP Code</th>
											<th width="20%" class="text-center">EMP Name</th>
											<th class="text-center">Salary STR.</th>
											<th class="text-center">Designation</th>
											<th class="text-center">Misc/Claim Add</th>
											<th class="text-center">Advance</th>
											<th class="text-center">Loan EMI</th>
											<th class="text-center">Pay Ded</th>
											<th class="text-center">IT Ded</th>
											<th class="text-center">Performance Bonus</th>
											<th class="text-center">Reward</th>
											<th class="text-center">Edit</th>
										</tr>

									</thead>

									<tbody>
										<c:forEach items="${empList}" var="empList" varStatus="count">
											<tr>
												<td>${count.index+1}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empName}</td>
												<td>${empList.salTypeName}</td>
												<td>${empList.designation}</td>
												<td class="text-right">${empList.miscExpAdd}</td>
												<td class="text-right">${empList.advanceDed}</td>
												<td class="text-right">${empList.loanDed}</td>
												<td class="text-right">${empList.payDed}</td>
												<td class="text-right">${empList.itded}</td>
												<td class="text-right">${empList.performanceBonus}</td>
												<td class="text-right">${empList.reward}</td>
												<td class="text-center"><a href="#"
													onclick="editBonus(${empList.id})"
													data-original-title="Edit"><i class="icon-pencil7"></i></a></td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
							<br>
							<div class="text-center">

								<button type="submit" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn">
									Process Pay Roll <i class="icon-paperplane ml-2"></i>
								</button>

							</div>

						</form>
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
	<script type="text/javascript">
		// Single picker
		$(function() {
			$("#datepicker").datepicker({
				changeMonth : true,
				changeYear : true,
				yearRange : "-50:+50",
				dateFormat : "mm-yy"
			});
		});
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

	<script type="text/javascript">
		 
		function editBonus(tempSalDaynamicId) {
 
			
			
			var fd = new FormData();
			fd.append('tempSalDaynamicId', tempSalDaynamicId);
			
			$
			.ajax({
				url : '${pageContext.request.contextPath}/editBonus',
				type : 'post',
				dataType : 'json',
				data : fd,
				contentType : false,
				processData : false,
				success : function(response) {

					document.getElementById("editBonusDiv").style.display = 'block';  
					document.getElementById("tempSalDaynamicId").value = response.id; 
					document.getElementById("empCode").value = response.empCode; 
					document.getElementById("itAmt").value = response.itded; 
					document.getElementById("perBonus").value = response.performanceBonus; 
				},
			});

		} 
		function closeEditDetailTab() {

			document.getElementById("editBonusDiv").style.display = 'none';
		}
		
		function saveBonusDetail() {
			  
			var tempSalDaynamicId = document.getElementById("tempSalDaynamicId").value;  
			var itAmt = document.getElementById("itAmt").value;  
			var perBonus = document.getElementById("perBonus").value; 
			var flag=0;
			if(itAmt==""){
				alert("Enter IT Ammount");
				flag=1;
			}else if(perBonus==""){
				alert("Enter Bonus Ammount");
				flag=1;
			}
			
			if(flag==0){
				var fd = new FormData();
				fd.append('tempSalDaynamicId', tempSalDaynamicId);
				fd.append('itAmt', itAmt);
				fd.append('perBonus', perBonus); 
				
				  $
				.ajax({
					url : '${pageContext.request.contextPath}/saveBonusDetail',
					type : 'post',
					dataType : 'json',
					data : fd,
					contentType : false,
					processData : false,
					success : function(response) {

						location.reload(true);
					},
				}); 
			}
			}
	</script>
</body>
</html>