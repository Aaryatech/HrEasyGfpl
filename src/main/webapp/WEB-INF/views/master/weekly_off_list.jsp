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

 
			</div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">
					 
					
						<div class="card-header header-elements-inline">
 						<table width="100%">
							<tr width="100%">
								<td width="60%"><h5 class="card-title">Weekly Off List</h5></td>
								<td width="40%" align="right">
							  	<c:if test="${addAccess == 0}">
								 <a
									href="${pageContext.request.contextPath}/addWeeklyOff"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary"> Add Weekly Off </button>
								</a></c:if> </td>
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
									<th>Location Name</th>
									<th>Weekly Off Type</th>
								<!-- 	<th>Weekly Off Presently</th> -->
									<th>Day</th>
									<th width="10%" class="text-center">Actions</th>
								</tr>
							</thead>
							<tbody>


								<c:forEach items="${weekOffList}" var="week" varStatus="count">
									<tr>
										<td>${count.index+1}</td>
										<td>${week.locName}</td>
										<td><c:choose>
												<c:when test="${week.woType==1}">
												
												Even

												</c:when>
												<c:when test="${week.woType==2}">
												
												Odd

												</c:when>
												<c:when test="${week.woType==3}">
												
												1st

												</c:when>
												<c:when test="${week.woType==4}">
												
												2nd

												</c:when>
												<c:when test="${week.woType==5}">
												
												3rd

												</c:when>
												<c:when test="${week.woType==6}">
												
												4th

												</c:when>
												<c:otherwise>
												All
												</c:otherwise>
											</c:choose></td>
									<%-- 	<td>${week.woPresently}</td> --%>
										<td><c:choose>
												<c:when test="${week.woDay==1}">
												
												MONDAY

												</c:when>
												<c:when test="${week.woDay==2}">
												
												TUESDAY

												</c:when>
												<c:when test="${week.woDay==3}">
												
												WEDNESDAY

												</c:when>
												<c:when test="${week.woDay==4}">
												
												THURSDAY

												</c:when>
												<c:when test="${week.woDay==5}">
												
												FRIDAY

												</c:when>
												<c:when test="${week.woDay==6}">
												
												SATURDAY

												</c:when>
												<c:otherwise>
												SUNDAY
												</c:otherwise>
											</c:choose></td>

										<td class="text-center">
										
														<c:if test="${editAccess == 0}">
														<a
															href="${pageContext.request.contextPath}/editWeeklyOff?woId=${week.exVar1}"
															title="Edit"><i class="icon-pencil7" style="color: black;"></i></a></c:if>
															<c:if
																test="${deleteAccess == 0}">
														<a
															href="${pageContext.request.contextPath}/deleteWeeklyOff?woId=${week.exVar1}"
															onClick="return confirm('Are you sure want to delete this record');"
															title="Delete"><i class="icon-trash" style="color: black;"></i>
															</a></c:if>
											
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