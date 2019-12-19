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
						<a href="${pageContext.request.contextPath}/addClaimAuthority"
							class="breadcrumb-elements-item"> Add Claim Authority</a>
						<a
							href="${pageContext.request.contextPath}/editLeaveAuthority"
							class="breadcrumb-elements-item"> Edit Authority</a>

					</div>


				</div> --%>
			</div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">
				 
					<div class="card-header header-elements-inline">
 						<table width="100%">
							<tr width="100%">
								<td width="60%"><h5 class="card-title">Claim Authority List</h5></td>
								<td width="40%" align="right">
								 <a
									href="${pageContext.request.contextPath}/addClaimAuthority"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">Add Claim Authority </button>
								</a> </td>
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
						<table
							class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
							id="printtable1">
							<thead>
								<tr class="bg-blue">

									<th width="10%">Sr. No.</th>
									<th>Employee Code</th>
									<th>Employee Name</th>
									<th>Initial Authority</th>
									<th>Final Authority</th>
									<th>Reporting To</th>
									<th width="10%" class="text-center">Actions</th>
								</tr>
							</thead>
							<tbody>


								<c:forEach items="${empLeaveAuth}" var="leaveAuth"
									varStatus="count">
									<tr>

										<td>${count.index+1}</td>
										<td>${leaveAuth.empCode}</td>
										<td>${leaveAuth.empSname}&nbsp;${leaveAuth.empFname}</td>
										<td>${leaveAuth.iniEmpSname}&nbsp;${leaveAuth.iniEmpFname}</td>
										<td>${leaveAuth.finiEmpSname}&nbsp;${leaveAuth.finiEmpFname}</td>
										<td><c:forEach items="${leaveAuth.rePortingName}"
												var="rePortingName" varStatus="coun">

												<c:choose>
													<c:when test="${coun.last}"> 
															${rePortingName}
													</c:when>
													<c:otherwise> 
															${rePortingName},
													</c:otherwise>
												</c:choose>

											</c:forEach></td>

										<td class="text-center"><a
											href="${pageContext.request.contextPath}/editClaimAuthority?empId=${leaveAuth.exVar1}"
											title="Edit"><i class="icon-pencil7"
												style="color: black;"></i></a></td>
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
		$(document).ready(
				function() {
					$('#bootstrap-data-table-export').DataTable();

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);
							});
				});
	</script>



</body>
</html>