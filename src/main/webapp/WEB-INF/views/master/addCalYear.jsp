<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datepicker.css"
	type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-datepicker.js"></script>

</head>

<body>
	 <c:url var="checkUniqueDates" value="/checkUniqueDates"></c:url><!-- checkUniqueField -->

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

								<form
									action="${pageContext.request.contextPath}/submitInsertCalYear"
									id="submitInsertLocaion" method="post">
									<input type="hidden" value="${calYear.calYrId}" id="calYearId"
										name="calYearId">
									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="fromDate">From
											Date <span class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control datepickerclass" value="${calYear.calYrFromDate}"
												data-placeholder="Select Date" name="from_date" autocomplete="off" 
												onchange="checkUnique(this.value,1)" id="from_date">
												<span class="validation-invalid-label"
												id="error_getFrom_date" style="display: none;">This
												field is required.</span>
												<span class="validation-invalid-label" id="error_from_date"
												style="display: none;">From Date must be smaller than To Date .</span>												
												<span class="validation-invalid-label" id="error_year_fromDate"
												style="display: none;">Invalid date for a financial year.</span>
												<span class="validation-invalid-label" id="error_fromunique"
												style="display: none;">From Date Already Exist.</span>
												
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label text-info font-weight-bold col-lg-2" for="toDate">To 
											Date <span class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control datepickerclass" value="${calYear.calYrToDate}"
												data-placeholder="Select Date" name="to_date" autocomplete="off" 
												 id="to_date" onchange="checkUnique(this.value,2)">
												<span class="validation-invalid-label"
												id="error_getTo_date" style="display: none;">This
												field is required.</span>
												<span class="validation-invalid-label"
												id="error_to_date" style="display: none;">To Date must be greater than From Date.</span>	
											 	<span class="validation-invalid-label" id="error_year_toDate"
												style="display: none;">Invalid date for a financial year.</span>
												<span class="validation-invalid-label" id="error_tounique"
												style="display: none;">To Date Already Exist.</span>

										</div>
									</div>
									<c:if test="${calYear.calYrId>0}">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="isCurrent">Is Current 
											Year:
										</label>
										<div class="col-lg-10">
											<input type="checkbox" id="check_yes" name="check_year" value="1">Yes
												<!-- <input type="checkbox" id="check_no" name="check_year" value="0">No -->
										</div>
									</div></c:if>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">Remark
											: </label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Any Remark" onchange="trim(this)" id="remark"
												name="remark">${calYear.exVar1}</textarea>

										</div>
									</div>

									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">

											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a
												href="${pageContext.request.contextPath}/showCalYearList"><button
													type="button" class="btn btn-light">
													<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
													Back
												</button></a>
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
	<script type="text/javascript">
		// Single picker
		/* $("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : "-50:+50",
			dateFormat : "mm-yy"
		}); */

		$(document).ready(function() {
			// month selector
			$('.datepickerclass').datepicker({
				autoclose : true,
				format : "dd-mm-yyyy",
				

			});

		});
	</script>
	<script>
		//Single picker
		
		$('.datepickerclass1').daterangepicker({
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			
			locale : {
				format : 'DD-MM-YYYY'
			}
		});
	</script>

	<script>
		function checkUnique(inputValue, valueType) {
		//	alert("Primary key"+inputValue+" "+valueType);
			var primaryKey = ${calYear.calYrId};
		//	alert("Primary key"+primaryKey);
			var isEdit = 0;
			if (primaryKey > 0) {
				isEdit = 1;
			}
		//	alert("Is Edit " +isEdit);
			var valid = false;
			if (inputValue == '' || inputValue == null) {
				valid = false;
			} else {
				valid = true;
			}
			//alert(valid);
			if (valid == true){
				
				
				$.getJSON('${checkUniqueDates}', {

					inputValue : inputValue,
					valueType : valueType,
					primaryKey : primaryKey,
					isEdit : isEdit,
 					ajax : 'true',

				}, function(data) {

					//alert("Data  " +JSON.stringify(data));
					if (data.error == true) {

						document.getElementById("from_date").value = "";
						document.getElementById("to_date").value = "";

						$("#error_fromunique").show()
						$("#error_tounique").show()

					} else {
						$("#error_fromunique").hide()
						$("#error_tounique").hide()
					}
				});
			}
		}
	</script>


	<script>
		 
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			checkSame();
			return;
		}

		 
		$(document).ready(function($) {

			$("#submitInsertLocaion").submit(function(e) {
				var isError = false;
				var errMsg = "";
				
				var from_date = $("#from_date").val();
  				var to_date = $("#to_date").val();
  				
  				var fromdate = from_date.split('-');
 		        from_date = new Date();
 		        from_date.setFullYear(fromdate[2],fromdate[1]-1,fromdate[0]);
 		        var todate = to_date.split('-');
 		        to_date = new Date();
 		        to_date.setFullYear(todate[2],todate[1]-1,todate[0]);
 		        if (from_date > to_date ) 
 		        {
 		           /// alert("Invalid Date Range!\nStart Date cannot be after End Date!")
					$("#error_from_date").show();
				 	$("#error_to_date").show();
				 	
				 	$("#error_getFrom_date").hide()
				 	$("#error_getTo_date").hide()
				 	
 		            return false;
 		           
 		        }else {
 					$("#error_from_date").hide();
 					$("#error_to_date").hide();
 				}

				if (!$("#from_date").val()) {

					isError = true;

					$("#error_getFrom_date").show()
					//return false;
				} else {
					$("#error_getFrom_date").hide()
				}

				if (!$("#to_date").val()) {

					isError = true;

					$("#error_getTo_date").show()

				} else {
					$("#error_getTo_date").hide()
				}
				
				var fromDate = $("#from_date").val();
				var toDate = $("#to_date").val();  
				var DMY =fromDate.split('-'); 
				var DMY1 =toDate.split('-');
				 
				var day= DMY[0];
				var month=DMY[1];
				var year=DMY[2];
				 
				var day1= DMY1[0];
				var month1=DMY1[1];
				var year1=DMY1[2];
				 
				var dateTemp1=new Date(year, (parseInt(month)-1),day);
				var dateTemp2=new Date(year1, (parseInt(month1)-1),day1);
				
				var days= Math.abs(((dateTemp2.getTime()-dateTemp1.getTime())/(1000*60*60*24)));
				//alert("Days-----------"+days);
				if(days>=364 && days<=365){
					$("#error_year_fromDate").hide()
					$("#error_year_toDate").hide()
				}else{
					isError = true;
					$("#error_year_fromDate").show()
					$("#error_year_toDate").show()
				}
				if (!isError) {

					var x = true;
					if (x == true) {

						document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//end ajax send this to php page
				}
				return false;
			});
		});
		//
	</script>



	<script type="text/javascript">		
		/* $('#submtbtn').on('click', function() {
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
		}); */
	</script>
</body>
</html>