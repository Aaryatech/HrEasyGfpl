<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:url var="getLoanHistory" value="/getLoanHistory" />

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
								<td width="60%"><h5 class="card-title">Company Loan List
									</h5></td>

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

						<%-- <div class="form-group row">

							<label class="col-form-label col-lg-2" for="locId"> Year<span
								style="color: red">* </span>:
							</label>
							<div class="col-lg-4">
								<select name="calYrId" data-placeholder="Select  " id="calYrId"
									onchange="show()"
									class="form-control form-control-select2 select2-hidden-accessible"
									data-fouc="" aria-hidden="true">

									<option value="0">Select Year</option>

									<option value="2019">2019</option>
									<option value="2020">2020</option>
									<option value="2021">2021</option>
								</select>
							</div>


							<label class="col-form-label col-lg-2" for="status">
								Status<span style="color: red">* </span>:
							</label>
							<div class="col-lg-4">
								<select name="status" data-placeholder="Select  " id="status"
									onchange="show()"
									class="form-control form-control-select2 select2-hidden-accessible"
							calYrId : calYrId,
					status : status,		data-fouc="" aria-hidden="true">

									<option value="0">Select Status</option>

									<option value="Active">Active</option>
									<option value="Paid">Paid</option>
								</select>
							</div>

							<label class="col-form-label col-lg-2" for="empId">
								Employee<span style="color: red">* </span>:
								</label>
								<div class="col-lg-4">
									<select name="empId" data-placeholder="Select  "
										id="empId" onchange="show()"
										class="form-control form-control-select2 select2-hidden-accessible"
										data-fouc="" aria-hidden="true">

										<option value="0">Select Employee</option>

										<c:forEach items="${empdetList}" var="empdetList">
											<option value="${empdetList.empId}">${empdetList.empCode}&nbsp;${empdetList.surname}&nbsp;${empdetList.firstName}</option>
										</c:forEach>
									</select>  
								</div>




						</div>
 --%>

						<table
							class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
							id="printtable1">
							<thead>
								<tr class="bg-blue">

									<th width="10%">Sr. No.</th>
									<th>Emp Code</th>
									<th>Designation</th>
									<th>Name</th>
									<th>Loan Amount</th>
									<th>Repay Amount</th>
									<th>Loan EMI</th>
									<th>Current Outstanding</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
	 
								<c:forEach items="${loanList}" var="empdetList" varStatus="count">
									<tr>
										<td>${count.index+1}</td>
										<td>${empdetList.empCode}</td>
										<td>${empdetList.designation}</td>
										<td>${empdetList.surname}&nbsp;${empdetList.middleName}&nbsp;${empdetList.firstName}</td>
										<td>${empdetList.loanAmt}</td>
										<td>${empdetList.loanRepayAmt}</td>
										<td>${empdetList.loanEmi}</td>
										<td>${empdetList.currentOutstanding}</td>
										<td><c:if test="${editAccess == 0}"><a
													href="${pageContext.request.contextPath}/showLoanListForAction?empId=${empdetList.exVar1}"
													class="list-icons-item text-primary-600" data-popup="tooltip" title="Loan Details" data-original-title="Edit"><i class="icon-paragraph-justify2"
													 ></i></a></c:if></td>
										
 									</tr>
								</c:forEach>

							</tbody>
						</table>

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
		function show() { 
				$.getJSON('${getLoanHistory}', {
					 
					ajax : 'true',
				},

				function(data) {

					//	alert("Data " +JSON.stringify(data));

					var dataTable = $('#printtable1').DataTable();
					dataTable.clear().draw();

					$.each(data, function(i, v) {
						
						var acButton = '<a href="${pageContext.request.contextPath}/showLoanListForAction?empId='+v.exVar1+'&calYrId='+v.exVar3+'&status='+v.exVar2+'"><i class="icon-pencil7" title="Detail History" style="color: black;">';	


						dataTable.row.add(
								[ i + 1, v.empCode, v.designation, v.surname+' '+v.firstName,
										v.loanAmt, v.loanRepayAmt, v.loanEmi,
										v.currentOutstanding,acButton

								]).draw();
					});

				});
 	 

		}
	</script>
</body>
</html>