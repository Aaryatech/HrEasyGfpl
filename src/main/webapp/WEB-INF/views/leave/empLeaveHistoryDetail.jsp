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
								<td width="60%"><h5 class="card-title">Leave Trail
										List</h5></td>
								<td width="40%" align="right"><a
									href="${pageContext.request.contextPath}/showLeaveHistList?empId=${empId1}"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">Employee
											Leave History</button>
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
								<input type="text" class="form-control" Value="${lvEmp.empCode}"
									name="compName" autocomplete="off" readonly>

							</div>
						</div>
						<div class="form-group row">
							<label class="col-form-label col-lg-2" for="compName">Employee
								Name : </label>
							<div class="col-lg-6">
								<input type="text" class="form-control" Value="${lvEmp.empName}"
									name="compName" autocomplete="off" readonly>

							</div>
						</div>
						<div class="form-group row">
							<label class="col-form-label col-lg-2" for="compName">Leave
								Type : </label>
							<div class="col-lg-6">
								<input type="text" class="form-control"
									Value="${lvEmp.leaveTitle}" name="compName" autocomplete="off"
									readonly>

							</div>
						</div>

						<div class="form-group row">
							<label class="col-form-label col-lg-2" for="compName">From
								date : </label>
							<div class="col-lg-6">
								<input type="text" class="form-control"
									Value="${lvEmp.leaveFromdt}" name="compName" autocomplete="off"
									readonly>

							</div>
						</div>
						<div class="form-group row">
							<label class="col-form-label col-lg-2" for="compName">To
								Date : </label>
							<div class="col-lg-6">
								<input type="text" class="form-control"
									Value="${lvEmp.leaveTodt}" name="compName" autocomplete="off"
									readonly>

							</div>
						</div>

						<div class="form-group row">
							<label class="col-form-label col-lg-2" for="compName">No.
								of Days : </label>
							<div class="col-lg-6">
								<input type="text" class="form-control"
									Value="${lvEmp.leaveNumDays}" name="compName"
									autocomplete="off" readonly>

							</div>
						</div>
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

									<th>Leave Status</th>


								</tr>
							</thead>
							<tbody>


								<c:forEach items="${employeeList}" var="empTrailList"
									varStatus="count">
									<tr>
										<td>${count.index+1}</td>
										<%-- <td>${empTrailList.empSname}${empTrailList.empFname}</td> --%>
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

										<c:if test="${empTrailList.leaveStatus==1}">
											<td><span class="badge badge-info">Applied</span></td>
										</c:if>
										<c:if test="${empTrailList.leaveStatus==2}">
											<td><span class="badge badge-secondary">Initial
													Approved</span></td>
										</c:if>
										<c:if test="${empTrailList.leaveStatus==3}">
											<td><span class="badge badge-success"> Final
													Approved</span></td>
										</c:if>
										<c:if test="${empTrailList.leaveStatus==7}">
											<td><span class="badge badge-danger">Leave
													Cancelled</span></td>
										</c:if>
										<c:if test="${empTrailList.leaveStatus==8}">
											<td><span class="badge badge-danger">Initial
													Rejected</span></td>
										</c:if>
										<c:if test="${empTrailList.leaveStatus==9}">
											<td><span class="badge badge-danger"> Final
													Rejected</span></td>
										</c:if>

										<%-- 	<td class="text-center">
											<div class="list-icons">
												<div class="dropdown">
													<a href="#" class="list-icons-item" data-toggle="dropdown">
														<i class="icon-menu9"></i>
													</a>

													<div class="dropdown-menu dropdown-menu-right">
														<a
															href="${pageContext.request.contextPath}/editEmp?typeId=${lvTypeList.exVar1}"
															class="dropdown-item"><i class="icon-pencil7"></i>Edit</a>
														<a
															href="${pageContext.request.contextPath}/deleteEmployee?typeId=${lvTypeList.exVar1}"
															class="dropdown-item"><i class="icon-trash"></i>
															Delete</a> <a
															href="${pageContext.request.contextPath}/uploadDocument?empId=${lvTypeList.exVar1}"
															class="dropdown-item"><i class="icon-file-upload"></i> 
															Document upload</a>

													</div>
												</div>
											</div>
										</td> --%>
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