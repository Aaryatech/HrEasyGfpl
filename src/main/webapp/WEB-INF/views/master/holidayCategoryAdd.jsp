<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>

<body>
	<c:url var="checkUniqueField" value="/checkUniqueField"></c:url>

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
								<td width="60%"><h5 class="card-title">${title}</h5></td>
								<td width="40%" align="right">
							  
								 <%-- <a
									href="${pageContext.request.contextPath}/showAddKra?empId=${editKra.exVar3}&finYrId=${editKra.exVar2}"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">KRA List </button>
								</a>  --%></td>
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

								<form
									action="${pageContext.request.contextPath}/submitInsertHolidayCategory"
									id="submitInsertHoliCat" method="post">
									<input type="hidden" value="${holi.hoCatId}" id="hoCatId" name="hoCatId">
									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="desination">Holiday Category 
											Name <span class="text-danger">* </span>:</label>
										<div class="col-lg-10">
											<input type="text" class="form-control" value="${holi.hoCatName}"
												placeholder="Enter Holiday Category" id="hoCatName"
												name="hoCatName" autocomplete="off"  onchange="checkUnique(this.value,3)">
											<span class="validation-invalid-label" id="error_hoCatName"
												style="display: none;">This field is required.</span>
												
												<span class="validation-invalid-label" id="error_unique"
												style="display: none;">Holiday Category  Name Already Exists.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="desigShortName">Short Name
											 <span class="text-danger">* </span>:</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Enter Short Name" id="hoShortName" value="${holi.hoCatShortName}"
												name="hoShortName" autocomplete="off" onchange="trim(this)"
												maxlength="10"> <span
												class="validation-invalid-label" id="error_hoShortName"
												style="display: none;">This field is required.</span>
												<span
												class="validation-invalid-label" id="error_sameName"
												style="display: none;">Holiday Category Short Name Can Not be same as Holiday Category  Name.</span>
												
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">Remark
											: </label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Any Remark" onchange="trim(this)" id="remark"
												name="remark">${holi.remark}</textarea>

										</div>
									</div>

									 <div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a
												href="${pageContext.request.contextPath}/showHolidayCatList"><button
													type="button" class="btn btn-light">Back</button></a>
										</div>
									</div>
								</form>
								<p class="desc text-danger fontsize11">Notice : * Fields are
									mandatory.</p>
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
	
	
	<script>
		function checkUnique(inputValue, valueType) {
			//${holi.hoCatId}
			var primaryKey =	document.getElementById("hoCatId").value ;
	 
			var isEdit = 0;
			if (primaryKey > 0) {
				isEdit = 1;
			}
	 
			var valid = false;
			if (inputValue == '' || inputValue == null) {
				valid = false;
			} else {
				valid = true;
			}
			//alert(valid);
			if (valid == true){
				
				
				$.getJSON('${checkUniqueField}', {

					inputValue : inputValue,
					valueType : valueType,
					primaryKey : primaryKey,
					isEdit : isEdit,
 					ajax : 'true',

				}, function(data) {

					//alert("Data  " +JSON.stringify(data));
					if (data.error == true) {

						document.getElementById("hoCatName").value = "";

						$("#error_unique").show()

					} else {
						$("#error_unique").hide()
					}
				});
			}
		}
	</script>



	<script>
	
	function checkSame(){
		x=document.getElementById("hoCatName").value;
		y=document.getElementById("hoShortName").value;
		//alert(x);
		
		if(x!== '' && y!== ''){
			if(x==y){
				$("#error_sameName").show()
				document.getElementById("hoShortName").value="";
			}
			else{
				$("#error_sameName").hide()
			}
	}
		
	}
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

							$("#submitInsertHoliCat")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#hoCatName").val()) {

													isError = true;

													$("#error_hoCatName").show()
													//return false;
												} else {
													$("#error_hoCatName").hide()
												}

												if (!$("#hoShortName").val()) {

													isError = true;

													$("#error_hoShortName")
															.show()

												} else {
													$("#error_hoShortName")
															.hide()
												}

												if (!isError) {

													var x = true;
													if (x == true) {

														document
																.getElementById("submtbtn").disabled = true;
														return true;
													}
													//end ajax send this to php page
												}
												return false;
											});
						});
		//
	</script>
	
	 

</body>
</html>