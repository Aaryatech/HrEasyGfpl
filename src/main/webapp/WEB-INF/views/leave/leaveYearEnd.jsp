<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<c:url var="getPreviousYearHistory" value="/getPreviousYearHistory" />
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


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">



					<div class="card-header header-elements-inline">
						<table width="100%">
							<tr width="100%">
								<td width="60%"><h5 class="card-title">Carry forward
										Leave</h5></td>
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
						<form action="${pageContext.request.contextPath}/leaveYearEnd"
							id="submitInsertProjectHeader" method="get">

							<div class="form-group row">
								<label class="col-form-label col-lg-2" for="select2">Select
									Employee <span style="color: red">* </span> :
								</label>
								<div class="col-md-2">
									<select name="empId" data-placeholder="Select Employee"
										id="empId"
										class="form-control form-control-select2 select2-hidden-accessible"
										tabindex="-1" aria-hidden="true">
										<option value="">Select Employee</option>
										<c:forEach items="${employeeInfoList}" var="empInfo">

											<c:choose>
												<c:when test="${empInfo.empId==empId}">
													<option value="${empInfo.empId}" selected>${empInfo.surname}
														${empInfo.firstName} ${empInfo.middleName}</option>
												</c:when>
												<c:otherwise>
													<option value="${empInfo.empId}">${empInfo.surname}
														${empInfo.firstName} ${empInfo.middleName}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</select> <span class="validation-invalid-label" id="error_empId"
										style="display: none;">This field is required.</span>
								</div>




							</div>


							<div class="form-group row">

								<label class="col-form-label col-lg-2" for="select2">Select
									Structure Allotment<span style="color: red">* </span> :
								</label>
								<div class="col-md-2">
									<select name="structId"
										data-placeholder="Select Structure Allotment" id="structId"
										class="form-control form-control-select2 select2-hidden-accessible"
										tabindex="-1" aria-hidden="true" required="required">
										<option value="">Select Structure Allotment</option>
										<c:forEach items="${lStrList}" var="lStrList">

											<c:choose>
												<c:when test="${lStrList.lvsId==structId}">
													<option value="${lStrList.lvsId}" selected>${lStrList.lvsName}
													</option>
												</c:when>
												<c:otherwise>
													<option value="${lStrList.lvsId}">${lStrList.lvsName}
													</option>
												</c:otherwise>
											</c:choose>


										</c:forEach>
									</select> <span class="validation-invalid-label" id="error_calYrId"
										style="display: none;">This field is required.</span>
								</div>


							</div>

							<div class="col-md-12">
								<div class="text-center">
									<input type="submit" class="btn bg-blue ml-3 legitRipple"
										id="searchh" value="Search" />

								</div>
							</div>
						</form>
						<br>
						<div id='loader' style='display: none;'>
							<img
								src='${pageContext.request.contextPath}/resources/assets/images/giphy.gif'
								width="150px" height="150px"
								style="display: block; margin-left: auto; margin-right: auto">
						</div>

						<c:if test="${previousleavehistorylist.size()>0}">
							<form
								action="${pageContext.request.contextPath}/submitYearEndAndAssignNewStructure"
								id="submitInsertProjectHeader" method="post">

								<div class="table-responsive">
									<table
										class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
										id="printtable1">


										<thead>
											<tr class="bg-blue" style="text-align: center;">

												<th>Leave Type</th>
												<th width="10%">Previous Year Opening Bal</th>
												<th width="10%">Previous Year Earned</th>
												<th width="10%">Previous Year Approved</th>
												<th width="10%">Previous Year Applied</th>
												<th width="10%">Previous Year Balanced</th>
												<!-- <th width="10%">InCash</th> -->
												<th width="10%">Carry Forward</th>
												<th width="10%">Current Year Earned</th>
												<th width="10%">Current Year Balanced</th>
											</tr>
										</thead>


										<tbody>
											<c:forEach items="${previousleavehistorylist}"
												var="previousleavehistorylist" varStatus="count">
												<tr>
													<c:set
														value="${previousleavehistorylist.balLeave+previousleavehistorylist.lvsAllotedLeaves-previousleavehistorylist.sactionLeave-previousleavehistorylist.aplliedLeaeve}"
														var="ballv"></c:set>
													<td>${previousleavehistorylist.lvTitle}</td>
													<td>${previousleavehistorylist.balLeave}</td>
													<td>${previousleavehistorylist.lvsAllotedLeaves}</td>
													<td>${previousleavehistorylist.sactionLeave}</td>
													<td>${previousleavehistorylist.aplliedLeaeve}</td>
													<td>${ballv}</td>
													<c:set var="carryForward" value="0"></c:set>
													<c:set var="currentEarn" value="0"></c:set>
													<c:set var="color" value=""></c:set>
													<c:forEach items="${leaveStructureById.detailList}"
														var="detailList">

														<c:if
															test="${detailList.lvTypeId==previousleavehistorylist.lvTypeId}">
															<c:set var="currentEarn"
																value="${detailList.lvsAllotedLeaves}"></c:set>
														</c:if>
													</c:forEach>

													<c:if
														test="${previousleavehistorylist.maxAccumulateCarryforward>0}">
														<c:choose>
															<c:when
																test="${(ballv+currentEarn)>previousleavehistorylist.maxAccumulateCarryforward && 
																ballv>previousleavehistorylist.maxAccumulateCarryforward}">
																<c:set var="carryForward"
																	value="${previousleavehistorylist.maxAccumulateCarryforward}"></c:set>
																<c:set var="color" value="red"></c:set>
															</c:when> 
															<c:otherwise>
																<c:set var="carryForward" value="${ballv}"></c:set>
															</c:otherwise>
														</c:choose>
													</c:if>
													<%-- <td><input
														id="inchashLv${previousleavehistorylist.lvTypeId}"
														name="inchashLv${previousleavehistorylist.lvTypeId}"
														value="0" class="form-control" type="number" required></td> --%>
													<td style="background-color: ${color};"><input
														id="carryfrwd${previousleavehistorylist.lvTypeId}"
														name="carryfrwd${previousleavehistorylist.lvTypeId}"
														value="${carryForward}" class="form-control" type="text"
														required></td>
													<td>${currentEarn}</td>
													<c:set var="currentyearbal"
														value="${carryForward+currentEarn}"></c:set>
													<td>${currentyearbal}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<br>

								<div class="col-md-12">
									<div style="text-align: center;">
										<input type="submit" class="btn bg-blue ml-3 legitRipple"
											id="submtbtn" value="Submit" />

									</div>
								</div>
							</form>
						</c:if>
					</div>

				</div>
				<!-- /highlighting rows and columns -->


				<!-- /content area -->
			</div>

			<!-- Footer -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
			<!-- /footer -->

		</div>
		<!-- /main content -->

	</div>
	<!-- /page content -->
	<script type="text/javascript">
		function search() {

			//alert("Hi View Orders  ");

			var empId = document.getElementById("empId").value;

			//alert(compId);

			var valid = true;

			if (empId == null || empId == "") {
				valid = false;
				alert("Please Select Employee");
			}

			if (valid == true) {
				$("#loader").show();
				$
						.getJSON(
								'${getPreviousYearHistory}',
								{
									empId : empId,
									ajax : 'true',
								},

								function(data) {

									$("#printtable1 tbody").empty();

									for (var i = 0; i < data.length; i++) {

										var ballv = data[i].balLeave
												+ data[i].lvsAllotedLeaves
												- data[i].sactionLeave
												- data[i].aplliedLeaeve;
										var tr_data = '<tr> <td  >'
												+ data[i].lvTitle
												+ '</td>'
												+ '<td  >'
												+ data[i].balLeave
												+ '</td>'
												+ '<td  >'
												+ data[i].lvsAllotedLeaves
												+ '</td>'
												+ '<td  >'
												+ data[i].sactionLeave
												+ '</td>'
												+ '<td  >'
												+ data[i].aplliedLeaeve
												+ '</td> <td  >'
												+ ballv
												+ '</td><td><input id="inchashLv'+data[i].lvTypeId+'" name="inchashLv'+data[i].lvTypeId+'" value="'+0+'" class="form-control" type="number" required></td>'
												+ '<td><input id="carryfrwd'+data[i].lvTypeId+'" name="carryfrwd'+data[i].lvTypeId+'" value="'+ballv+'" class="form-control" type="text" required></td></tr>';
										$('#printtable1' + ' tbody').append(
												tr_data);
									}

									$("#loader").hide();

								});

			}//end of if valid ==true

		}

		function callDetail(exVar1, empId) {
			alert(exVar1);
			window
					.open("${pageContext.request.contextPath}/empDetailHistory?empId="
							+ exVar1);

		}

		function callDelete(weighId) {
			window.open("${pageContext.request.contextPath}/deleteWeighing/"
					+ weighId);

		}
	</script>
	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			return;
		}

		function validateEmail(email) {

			var eml = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

			if (eml.test($.trim(email)) == false) {

				return false;

			}

			return true;

		}
		function validateMobile(mobile) {
			var mob = /^[1-9]{1}[0-9]{9}$/;

			if (mob.test($.trim(mobile)) == false) {

				//alert("Please enter a valid email address .");
				return false;

			}
			return true;

		}
		$(document).ready(function($) {

			$("#submtbtn").submit(function(e) {
				var isError = false;

				if (!isError) {

					var x = confirm("Do you really want to submit the form?");
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
</body>
</html>