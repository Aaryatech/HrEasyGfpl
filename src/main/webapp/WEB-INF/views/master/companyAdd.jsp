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

				<!-- Form validation -->
				<div class="row">
					<div class="col-md-12">
						<!-- Title -->

						<!-- /title -->


						<div class="card">

							<div class="card-header header-elements-inline">
								<table width="100%">
									<tr width="100%">
										<td width="60%"><h5 class="card-title">Add Company</h5></td>
										<td width="40%" align="right">
											<%-- <a
									href="${pageContext.request.contextPath}/showAddKra?empId=${editKra.exVar3}&finYrId=${editKra.exVar2}"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">KRA List </button>
								</a>  --%>
										</td>
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
								
								<!-- Highlighted tabs -->
								<ul class="nav nav-tabs nav-tabs-highlight">
									<li class="nav-item text-center"><a
										href="#highlighted-tab1" class="nav-link active"
										data-toggle="tab">Basic Information </br>Step 1
									</a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab2" class="nav-link" data-toggle="tab">Company
											Information </br>Step 2
									</a></a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab3" class="nav-link" data-toggle="tab">Relative
											Information </br>Step 3
									</a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab4" class="nav-link" data-toggle="tab">Employee Bank Details
											</br>Step 4
									</a></li>
									
									<li class="nav-item text-center"><a
										href="#highlighted-tab5" class="nav-link" data-toggle="tab">Employee Salary Details
											</br>Step 5
									</a></li>
									<li class="nav-item text-center"><a
										href="#highlighted-tab6" class="nav-link" data-toggle="tab">Employee Documents
											</br>Step 6
									</a></li>
									
								</ul>

								<div class="tab-content">
									<div class="tab-pane fade show active" id="highlighted-tab1">
									
										<form
											action="${pageContext.request.contextPath}/insertEmployeeBasicInfo"
											id="submitInsertEmp" method="post">

											<input type="hidden" id="companyId" name="companyId"
												value="${company.companyId}">									

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="companyName">Company 
													Name<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" value="" id="companyName"
													onchange="trim(this)" placeholder="Company Name" name="companyName">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_company">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="companyShortName">Company 
													Short Name<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" value="" id="companyShortName"
													onchange="trim(this)" placeholder="Company Short Name" id="companyShortName">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_companyShortName">This field is required.</span>
												</div>
											</div>


											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="companyAddress1">Address1
													<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" value="" id="companyAddress1"
													onchange="trim(this)" placeholder="Address1" id="companyAddress1">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_companyShortName">This field is required.</span>
												</div>
												
												<label class="col-form-label col-lg-2" for="companyAddress2">Address2
													<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" value="" id="companyAddress2"
													onchange="trim(this)" placeholder="Address2" id="companyAddress2">
												</div>
												
												
											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="companyAddress3">Address3
													<span style="color: red"> </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" value="" id="companyAddress3"
													onchange="trim(this)" placeholder="Address3" id="companyAddress3">
												</div>

												<label class="col-form-label col-lg-2" for="empType">Short 
													Address<span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" value="" id="shortAddress"
													onchange="trim(this)" placeholder="Short Address" id="shortAddress3">
												</div>
											</div>



											<div class="form-group row">
												<label class="col-form-label col-lg-2" for="landline1">Landline 1
													No. <span style="color: red">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" value=""
														placeholder="Landline No." id="mobile1" name="landline1" autocomplete="off"
														onchange="trim(this)" maxlength="10"> <span style="display: none;"
														class="hidedefault   validation-invalid-label"
														id="error_landline1">This field is required.</span> 
												</div>


												<label class="col-form-label col-lg-2" for="landline2">Landline 2
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" value=""
														placeholder="Other Landline No." id="landline2" name="landline2"
														autocomplete="off" onchange="trim(this)" maxlength="10">
													<span class="hidedefault   validation-invalid-label" style="display: none;"
														id="error_landline2">This field is required.</span>
												</div>

											</div>

											<div class="form-group row">

												<label class="col-form-label col-lg-2" for="fac">FAX No.
													 <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
												<input type="text" class="form-control" value=""
														placeholder="Fax" id="fax" name="fax"
														autocomplete="off" onchange="trim(this)">
												</div>

											<label class="col-form-label col-lg-2" for="pan">PAN
													No. <span style="color: red"></span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" maxlength="10"
														placeholder="PAN No." id="pan" name="pan" value=""
														autocomplete="off" onchange="trim(this)"><span
														class="hidedefault  validation-invalid-label"
														id="error_pan" style="display: none;">Please enter correct PAN No.</span>
												</div>
											</div>

											<div class="form-group row mb-0">
												<div class="col-lg-10 ml-lg-auto">
													<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
													<button type="submit" class="btn bg-blue ml-3 legitRipple"
														id="submtbtn">
														Submit <i class="icon-paperplane ml-2"></i>
													</button>
													<a href="${pageContext.request.contextPath}/showEmpList"><button
															type="button" class="btn btn-primary">
															<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
															Cancel
														</button></a> <input type="hidden" id="mobile1Exist"
														name="mobile1Exist"><input type="hidden"
														id="emailExist" name="emailExist">
												</div>
											</div>
										</form>
									</div>
									<!-- ********************************************Step Two********************************************** -->
									<div class="tab-pane fade" id="highlighted-tab2">
										Step Two

										<div class="form-group row">

												<label class="col-form-label col-lg-2" for="taxNo">Tax No. 
													<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" value="" id="taxNo"
													onchange="trim(this)" placeholder="Tax No" name="taxNo">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_taxNoe">This field is required.</span>
												</div>

												<label class="col-form-label col-lg-2" for="companyShortName">Company 
													Short Name<span style="color: red">*</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" value="" id="companyShortName"
													onchange="trim(this)" placeholder="Company Short Name" id="companyShortName">
													<span class="hidedefault  validation-invalid-label"
														style="display: none;" id="error_companyShortName">This field is required.</span>
												</div>
											</div>
										
									</div>
									<!--***************************************** tab 3 *************************************-->
									<div class="tab-pane fade" id="highlighted-tab3">

										
									</div>
									<!-- *****************************************Tab 4******************************************* -->
									<div class="tab-pane fade" id="highlighted-tab4">

										
									</div>

									<!--********************************* Tab 5 *********************************-->
									<div class="tab-pane fade" id="highlighted-tab5">

									
									</div>
									<!-- *****************************************Tab 6******************************************* -->
									<div class="tab-pane fade" id="highlighted-tab6">

									
									</div>
									

								</div>
								<!-- /highlighted tabs -->

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


	<script
		src="${pageContext.request.contextPath}/resources/global_assets/js/footercommonjs.js"></script>

	<script>
		$(document).ready(function() {
			$("#pan").change(function() {
				var pan = $("#pan").val();

				var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
				if (regex1.test($.trim(pan)) == false) {
					$("#error_pan").show()
					return false;
				}
				$("#error_pan").hide()
				return true;
			});
		});

		/* $('#sbtbtn4').click(function() {

			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/postIssueData",
				data : $("#modalfrm4").serialize(),
				dataType : 'json',
				success : function(data) {

				}
			}).done(function() {
				setTimeout(function() {
				}, 500);
			});
		}); */

		$(document).ready(function($) {

			$("#submitInsertEmp").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#empCode").val()) {

					isError = true;

					$("#error_empCode").show()
					//return false;
				} else {
					$("#error_empCode").hide()
				}

				if (!$("#fname").val()) {

					isError = true;

					$("#error_fname").show()

				} else {
					$("#error_fname").hide()
				}
				if (!$("#mname").val()) {

					isError = true;

					$("#error_mname").show()

				} else {
					$("#error_mname").hide()
				}
				if (!$("#sname").val()) {

					isError = true;

					$("#error_sname").show()

				} else {
					$("#error_sname").hide()
				}
				if (!$("#locId").val()) {

					isError = true;

					$("#error_locId").show()

				} else {
					$("#error_locId").hide()
				}
				
				if (!$("#empCat").val()) {
					
					isError = true;

					$("#error_empCat").show()

				} else {
					$("#error_empCat").hide()
				}

				if (!$("#empType").val()) {

					isError = true;

					$("#error_empType").show()

				} else {
					$("#error_empType").hide()
				}

				if (!$("#mobile1").val()) {

					isError = true;

					$("#error_mobile1").show()

				} else {
					$("#error_mobile1").hide()
				}
				/* 	if (!$("#desigId").val()) {

						isError = true;

						$("#error_desigId")
								.show()

					} else {
						$("#error_desigId")
								.hide()
					} */

				

				/* if (!$("#empStatus").val()) {

					isError = true;

					$("#error_empStatus").show()

				} else {
					$("#error_empStatus").hide()
				} */

				

				/* if (!$("#mobile2").val()) {

					isError = true;

					$("#error_emgContNo2_alt").show()

				} else {
					$("#error_emgContNo2_alt").hide()
				} */

				/* if (!$("#landline").val()) {

					isError = true;

					$("#error_landline")
							.show()

				} else {
					$("#error_landline")
							.hide()
				} */

				/* if (!$("#esic").val()) {

					isError = true;

					$("#error_esic").show()

				} else {
					$("#error_esic").hide()
				}

				if (!$("#aadhar").val()) {

					isError = true;

					$("#error_aadhar").show()

				} else {
					$("#error_aadhar").hide()
				}

				if (!$("#uan").val()) {

					isError = true;

					$("#error_uan").show()

				} else {
					$("#error_uan").hide()
				} */

				/* if (!$("#pan").val()|| !validatePAN($(
				"#pan").val())) {

						isError = true;

						$("#error_pan").show()

					} else {
						$("#error_pan").hide()
					}
					 
					
					if (!$("#pfNo").val()) {

						isError = true;

						$("#error_pfNo").show()

					} else {
						$("#error_pfNo").hide()
					}
				 */
				if (!isError) {

					var x = true;
					if (x == true) {

						//document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//
				}
				return false;
			});
		});
		//
		/* Bank */
		$(document).ready(function($) {

			$("#submitEmpBankInfo").submit(function(e) {
				var isError = false;
				var errMsg = "";
				var acc = $("#accNo").val();
				
				if (!$("#accNo").val()) {

					isError = true;

					$("#error_accNo").show()
					
				} else {
					$("#error_accNo").hide()
				}

				if (acc.length<8 || acc.length>17) {

					isError = true;
					
					$("#error_accNoDigit").show()
					
				} else {
					$("#error_accNoDigit").hide()
				}
				if (!isError) {

					var x = true;
					if (x == true) {

						//document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//
				}
				return false;
			});
		});
		/* Employee Salary */
		$(document).ready(function($) {

			$("#insertEmployeeAllowancesInfo").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#basic").val()) {

					isError = true;

					$("#error_salBasis").show()
					//return false;
				} else {
					$("#error_salBasis").hide()
				}

				if (!isError) {

					var x = true;
					if (x == true) {

						//document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//
				}
				return false;
			});
		});
		
		
/* Personal Information */
		 $(document)
				.ready(
						function($) {

							$("#submitEmpOtherInfo")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#dob").val()) {

													isError = true;

													$("#error_empDob").show()
													//return false;
												} else {
													$("#error_empDob").hide()
												}

												if (!isError) {

													var x = true;
													if (x == true) {

														//document.getElementById("submtbtn").disabled = true;
														return true;
													}
													//
												}
												return false;
											});
						});
	
		
	</script>

	<!-- <script type="text/javascript">
	$('#submtbtn').on('click', function() {
        swalInit({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger',
            buttonsStyling: false
        }).then(function(result) {
            if(result.value) {
                swalInit(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                );
            }
            else if(result.dismiss === swal.DismissReason.cancel) {
                swalInit(
                    'Cancelled',
                    'Your imaginary file is safe :)',
                    'error'
                );
            }
        });
    });
	
	</script> -->
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
		
		function show(input) {
	        debugger;
	        var validExtensions = ['jpg','png','jpeg','pdf']; //array of valid extensions
	        var fileName = input.files[0].name;
	        var fileNameExt = fileName.substr(fileName.lastIndexOf('.') + 1);
	        if ($.inArray(fileNameExt, validExtensions) == -1) {
	            input.type = ''
	            input.type = 'file'
	            $('#user_img').attr('src',"");
	            alert("Only these file types are accepted : "+validExtensions.join(', '));
	            //$('#error_img').show()
	        }
	        else
	        {
	        	 //$('#error_img').hide()
	        if (input.files && input.files[0]) {
	            var filerdr = new FileReader();
	            filerdr.onload = function (e) {
	                $('#user_img').attr('src', e.target.result);
	            }
	            filerdr.readAsDataURL(input.files[0]);
	        }
	        }
	    }
	</script>
</body>
</html>