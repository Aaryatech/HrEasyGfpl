<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<style>
.dropzone {
	min-height: 400px !important;
	border: 1px solid rgba(33, 33, 33, 0.1) !important;
	background: white !important;
	padding: 20px 20px !important;
	box-shadow: 0 2px 4px rgba(33, 33, 33, .2) !important;
	-webkit-box-shadow: 0 2px 4px rgba(33, 33, 33, .2) !important;
	-moz-box-shadow: 0 2px 4px rgba(33, 33, 33, .2) !important;
	-o-box-shadow: 0 2px 4px rgba(33, 33, 33, .2) !important;
	-ms-box-shadow: 0 2px 4px rgba(33, 33, 33, .2) !important;
}

.dropzone .dz-preview .dz-success-mark, .dropzone .dz-preview .dz-error-mark
	{
	pointer-events: none !important;
	opacity: 0 !important;
	z-index: 500 !important;
	position: absolute !important;
	display: block !important;
	top: 50% !important;
	left: 50% !important;
	margin-left: -27px !important;
	margin-top: -27px !important;
}
</style>
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
<!-- 

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


				</div> -->
			</div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">

				<!-- Form validation -->
				<div class="row">
					<div class="col-md-12">
						<!-- Title -->

						<!-- /title -->


						<div class="card">
							<div class="card-header header-elements-inline">
								<h6 class="card-title">Upload Documents</h6>
								<div class="header-elements">
									<div class="list-icons">
										<a class="list-icons-item" data-action="collapse"></a> <a
											class="list-icons-item" data-action="reload"></a> <a
											class="list-icons-item" data-action="remove"></a>
									</div>
								</div>
							</div>

							<div class="card-body">

								<div class="content">
									<div class="row">
										<div class="col-md-12">
											<form class="dropzone"
												action="${pageContext.request.contextPath}/uploadClaimProofAgain"
												method="post" enctype="multipart/form-data">
												<input name="isImage" value="1" type="hidden" />

												<div class="fallback">
													<input name="file" type="file" multiple />


												</div>
												<input type="hidden" name="claimId" id="claimId"
													value="${claimId}">


											</form>
										</div>
									</div>
								</div>
							</div>
						</div>


					</div>
				</div>


				<div class="card">
					<div class="card-body">

						<div class="content">

							<table
								class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
								id="printtable1">
								<thead>
									<tr class="bg-blue">
										<th width="10%">Sr.no</th>
										<th>Image Name</th> 
										<th class="text-center" width="10%">Actions</th>
									</tr>
								</thead>
								<tbody>


									<c:forEach items="${claimProofList1}" var="lvTypeList"
										varStatus="count">
										<tr>
											<td>${count.index+1}</td>
											<td><img src="${fileUrl}${lvTypeList.cpDocPath}"  width="150px" height="100px"> </td>
											 
											<td class="text-center"><a
												href="${pageContext.request.contextPath}/deleteClaimProof?claimProofId=${lvTypeList.exVar1}&claimId=${lvTypeList.exVar2}"
												onClick="return confirm('Are you sure want to Delete this Record');"
												 title="Delete Image"><i class="icon-trash "style="color: black;"></i></a></td>
										</tr>
									</c:forEach>

								</tbody>
							</table>
							
							
						</div>
					</div>
				</div>
				<!-- /form validation -->

			</div>
			<!-- /content area -->


			<!-- Footer -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
			<!-- /footer -->

		</div>
		<!-- /main content -->

	</div>
	<!-- /page content -->
	<script
		src="${pageContext.request.contextPath}/resources/global_assets/css/dropzone/dropzone.min.js"
		type="text/javascript"></script>

	<script>
		$(document)
				.on(
						"ready",
						function() {
							$("#input-701")
									.fileinput(
											{
												uploadUrl : "http://localhost:8081/hradmin/uploadOtherMediaProccess",
												uploadAsync : false,
												maxFileCount : 5
											});
						});
	</script>


</body>
</html>