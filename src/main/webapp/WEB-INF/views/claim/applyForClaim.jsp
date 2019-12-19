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


			<%-- 	<div
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
						<a href="${pageContext.request.contextPath}/employeeAdd"
							class="breadcrumb-elements-item"> Add Employee </a>

					</div>


				</div> --%>
			</div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">
					<div class="card-header header-elements-inline">
						<h5 class="card-title">Employee List</h5>
						<!-- <div class="header-elements">
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
						<table
							class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
							id="printtable1">
							<thead>
								<tr class="bg-blue">
									<th width="5%">Sr.no</th>
									<th>Name</th>
									<th>Work Description</th>

									<th>Email</th>
									<th>Mobile</th>

									<!-- <th>Rate Per Hour</th> -->


									<th class="text-center" width="10%">Actions</th>
								</tr>
							</thead>
							<tbody>

								<tr>
								<td>1</td>
										<td>${tempList.empSname}&nbsp;${tempList.empFname}</td>
										<td>${tempList.empCatShortName}-
											${tempList.empTypeShortName} -
											${tempList.empDeptShortName}</td>

										<td>${tempList.empEmail}</td>
										<td>${tempList.empMobile1}</td>
										<%-- <td>${tempList.empRatePerhr}</td> --%>
										
										
										<td class="text-center">
											 <a
											href="${pageContext.request.contextPath}/showClaimApply?empId=${tempList.exVar1}"
											title="Add Claim"><i class="icon-diff-added"
												style="color: black;"></i></a> <a
											href="${pageContext.request.contextPath}/showClaimList?empId=${tempList.exVar1}"
											title="Claim History"><i class="icon-history"
												style="color: black;"></i></a>

										</td>
								
								</tr>
								<c:set var="index" value="2"></c:set>
								<c:forEach items="${empList}" var="lvTypeList"  >
									<tr>
										<td>${index}</td>
										<c:set var="index" value="${index+1}"></c:set>
										
<td>${lvTypeList.empSname}&nbsp;${lvTypeList.empFname}</td>
										<td>${lvTypeList.empCatShortName}-
											${lvTypeList.empTypeShortName} -
											${lvTypeList.empDeptShortName}</td>

										<td>${lvTypeList.empEmail}</td>
										<td>${lvTypeList.empMobile1}</td>
										<%-- <td>${lvTypeList.empRatePerhr}</td> --%>
										

										<td class="text-center">
											 <a
											href="${pageContext.request.contextPath}/showClaimApply?empId=${lvTypeList.exVar1}"
											title="Add Claim"><i class="icon-diff-added"
												style="color: black;"></i></a> <a
											href="${pageContext.request.contextPath}/showClaimList?empId=${lvTypeList.exVar1}"
											title="Claim History"><i class="icon-history"
												style="color: black;"></i></a>

										</td>
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

</body>
</html>