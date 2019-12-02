<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="sidebar sidebar-light sidebar-main sidebar-expand-md">
	<c:url var="setSubModId" value="/setSubModId" />
	<!-- Sidebar mobile toggler -->
	<div class="sidebar-mobile-toggler text-center">
		<a href="#" class="sidebar-mobile-main-toggle"> <i
			class="icon-arrow-left8"></i>
		</a> <span class="font-weight-semibold">Navigation</span> <a href="#"
			class="sidebar-mobile-expand"> <i class="icon-screen-full"></i> <i
			class="icon-screen-normal"></i>
		</a>
	</div>
	<!-- /sidebar mobile toggler -->


	<!-- Sidebar content -->
	<div class="sidebar-content">

		<!-- User menu -->
		<div class="sidebar-user-material">
			<div class="sidebar-user-material-body">
				<div class="card-body text-center">
					<c:choose>
						<c:when test="${not empty sessionScope.profilePic}">

							<a
								href="${sessionScope.logoUrl}${sessionScope.profilePic}"
								target="_blank"> <img
								src="${sessionScope.logoUrl}${sessionScope.profilePic}"
								class="img-fluid rounded-circle shadow-1 mb-3" width="80"
								height="80" alt="">
							</a>
						</c:when>
						<c:otherwise>

							<a href="#"> <img
								src="${pageContext.request.contextPath}/resources/global_assets/images/noimageteam.png"
								class="img-fluid rounded-circle shadow-1 mb-3" width="80"
								height="80" alt="">
							</a>
						</c:otherwise>
					</c:choose>

					<h6 class="mb-0 text-white text-shadow-dark">${sessionScope.UserDetail.empSname}
						${sessionScope.UserDetail.empFname}</h6>
					<!-- <span class="font-size-sm text-white text-shadow-dark">Santa
						Ana, CA</span> -->
				</div>
			</div>
		</div>
		<!-- /user menu -->


		<!-- Main navigation -->
		<div class="card card-sidebar-mobile">
			<ul class="nav nav-sidebar" data-nav-type="accordion">

				<!-- Main -->
				<!-- <li class="nav-item-header"><div class="text-uppercase font-size-xs line-height-xs">Main</div> <i class="icon-menu" title="Main"></i></li> -->
				<li class="nav-item"><a href="${pageContext.request.contextPath}/dashboard"
					class="nav-link active"> <i class="icon-home4"></i> <span>
							Dashboard </span>
				</a></li>
				<%-- <li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Master</span></a>

					<ul class="nav nav-group-sub" data-submenu-title="Layouts">

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showCompanyList"
							class="nav-link">Company List</a></li>
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showLocationList"
							class="nav-link">Location List</a></li>

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showEmpList"
							class="nav-link">Employee List</a></li>
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showEmpTypeList"
							class="nav-link">Employee Type List</a></li>
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showEmpCatList"
							class="nav-link">Employee Category List</a></li>
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showEmpDeptList"
							class="nav-link">Employee Department List</a></li>

					</ul></li>


				<li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Leave</span></a>

					<ul class="nav nav-group-sub" data-submenu-title="Layouts">

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showHolidayList"
							class="nav-link">Holiday List</a></li>


						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showLeaveTypeList"
							class="nav-link">Leave Type List</a></li>

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showLeaveStructureList"
							class="nav-link">Leave Structure List</a></li>

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/leaveStructureAllotment"
							class="nav-link">Leave Allotment</a></li>

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/addLeaveAuthority"
							class="nav-link">Leave Authority</a></li>

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showApplyForLeave"
							class="nav-link">Apply For Leave</a></li>

					</ul></li> --%>


				<c:forEach items="${sessionScope.moduleJsonList}"
					var="moduleJsonList" varStatus="count">
					<c:choose>
						<c:when test="${moduleJsonList.moduleId==sessionModuleId}">
							<li class="nav-item nav-item-submenu nav-item-open">
						</c:when>
						<c:otherwise>
							<li class="nav-item nav-item-submenu">
						</c:otherwise>
					</c:choose>

					<a href="#" class="nav-link">${moduleJsonList.iconDiv}<span>${moduleJsonList.moduleName}</span></a>

					<c:choose>
						<c:when test="${moduleJsonList.moduleId==sessionModuleId}">
							<ul class="nav nav-group-sub"
								data-submenu-title="${moduleJsonList.moduleName}"
								style="display: block;">
						</c:when>
						<c:otherwise>
							<ul class="nav nav-group-sub"
								data-submenu-title="${moduleJsonList.moduleName}">
						</c:otherwise>
					</c:choose>

					<c:forEach items="${moduleJsonList.accessRightSubModuleList}"
						var="accessRightSubModuleList">
						<li class="nav-item"><c:choose>
								<c:when
									test="${accessRightSubModuleList.subModuleId==sessionSubModuleId}">
									<a
										href="${pageContext.request.contextPath}/${accessRightSubModuleList.subModuleMapping}"
										class="nav-link active legitRipple"
										onclick="selectSubMod(${accessRightSubModuleList.subModuleId},${accessRightSubModuleList.moduleId})">${accessRightSubModuleList.subModulName}</a></li>
						</c:when>
						<c:otherwise>
							<a
								href="${pageContext.request.contextPath}/${accessRightSubModuleList.subModuleMapping}"
								class="nav-link"
								onclick="selectSubMod(${accessRightSubModuleList.subModuleId},${accessRightSubModuleList.moduleId})">${accessRightSubModuleList.subModulName}</a>
							</li>
						</c:otherwise>
						</c:choose>
					</c:forEach>
			</ul>
			</li>

			</c:forEach>

			<%-- <li class="nav-item"><a class="nav-link"
				href="${pageContext.request.contextPath}/logout"><i
					class="icon-width"></i> <span>Logout</span></a></li> --%>
			<!-- /main -->


			</ul>
		</div>
		<!-- /main navigation -->

	</div>
	<!-- /sidebar content -->

</div>

<script>
			function selectSubMod(subModId, modId) {

				$.getJSON('${setSubModId}', {
					subModId : subModId,
					modId : modId,
					ajax : 'true'
				});

			}
		</script>
