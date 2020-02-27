<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="org.springframework.util.LinkedMultiValueMap"%>
<%@ page import="org.springframework.util.MultiValueMap"%>
<%@ page import="com.ats.hreasy.model.EmpSalInfoDaiyInfoTempInfo"%>
<%@ page import="com.ats.hreasy.common.Constants"%>
<%@ page import="com.ats.hreasy.common.ReportCostants"%>
<%@ page import="com.ats.hreasy.model.Setting"%><%@ page
	import="com.ats.hreasy.model.LoginResponse"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>
<!-- <link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css"> -->
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
						<h5 class="card-title">Generate Payroll ${date}</h5>
						<!-- <div class="header-elements">
							<div class="list-icons">
								<a class="list-icons-item" data-action="collapse"></a>
							</div> 
						</div>-->
					</div>

					<div class="card-body">

						<form
							action="${pageContext.request.contextPath}/insertFinalPayRollAndDeleteFroTemp"
							id="insertFinalPayRollAndDeleteFroTemp" method="post">

							<input type="hidden" name="searchDate" id="searchDate"
								value="${date}" /> <input type="hidden" name="empIds"
								id="empIds" value="${empIds}" />
							<div id="loader" style="display: none;">
								<img
									src="${pageContext.request.contextPath}/resources/assets/images/giphy.gif"
									width="150px" height="150px"
									style="display: block; margin-left: auto; margin-right: auto">
							</div>

							<div class="table-responsive">
								<table
									class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic1  datatable-button-print-columns1"
									id="bootstrap-data-table1">
									<thead>
										<tr class="bg-blue">
											<th width="5%" class="text-center">Sr.no</th>
											<th class="text-center">EMP Code</th>
											<th width="20%" class="text-center">EMP Name</th>
											<th class="text-center">Basic</th>
											<th class="text-center">Allowance</th>
											<th class="text-center">Absent Deduction</th>
											<th class="text-center">Gross Earning</th>
											<th class="text-center">Adv</th>
											<th class="text-center">Loan</th>
											<th class="text-center">IT Ded</th>
											<th class="text-center">Pay Ded</th>
											<th class="text-center">PT</th>
											<th class="text-center">PF</th>
											<th class="text-center">ESIC</th>
											<th class="text-center">MLWF</th>
											<th class="text-center">Society Contribution</th>
											<th class="text-center">Gross Ded</th>
											<th class="text-center">Claim ADD</th>
											<th class="text-center">Performance Bonus</th>
											<th class="text-center">Production Incentive</th>
											<th class="text-center">Performance Incentive <!-- (OT AMT) --></th>
											<th class="text-center">Reward <!-- (OT AMT) --></th>
											<th class="text-center">Net Salary</th>
										</tr>

									</thead>

									<tbody>

										<%
											try {

												session = request.getSession();
												LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

												MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
												map.add("month", request.getAttribute("month"));
												map.add("year", request.getAttribute("year"));
												map.add("empIds", request.getAttribute("empIds"));
												map.add("userId", userObj.getUserId());

												EmpSalInfoDaiyInfoTempInfo[] getSalDynamicTempRecord = Constants.getRestTemplate()
														.postForObject(Constants.url + "/calculateSalary", map, EmpSalInfoDaiyInfoTempInfo[].class);
												List<EmpSalInfoDaiyInfoTempInfo> list = new ArrayList<>(Arrays.asList(getSalDynamicTempRecord));

												map = new LinkedMultiValueMap<String, Object>();
												map.add("limitKey", "ammount_format_show");
												Setting getSettingByKey = Constants.getRestTemplate().postForObject(Constants.url + "/getSettingByKey",
														map, Setting.class);
												int amount_round = Integer.parseInt(getSettingByKey.getValue());
												session = request.getSession();
												session.setAttribute("payrollexelList", getSalDynamicTempRecord);
												session.setAttribute("amount_round", amount_round);
												session.setAttribute("monthAndYear",
														request.getAttribute("month") + "-" + request.getAttribute("year"));

												for (int i = 0; i < list.size(); i++) {
										%><tr>
											<td>
												<%
													out.println(i + 1);
												%>
											</td>
											<td>
												<%
													out.println(list.get(i).getEmpCode());
												%>
											</td>
											<td>
												<%
													out.println(list.get(i).getEmpName());
												%>
											</td>
											<td class="text-right">
												<%
													out.println(
																	String.format("%.2f", ReportCostants.castNumber(list.get(i).getBasicCal(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													double totalAllow = 0;
															for (int j = 0; j < list.get(i).getGetAllowanceTempList().size(); j++) {
																totalAllow = totalAllow + list.get(i).getGetAllowanceTempList().get(j).getAllowanceValueCal();
															}
															out.println(String.format("%.2f", ReportCostants.castNumber(totalAllow, amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f",
																	ReportCostants.castNumber(list.get(i).getAbDeduction(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f",
																	ReportCostants.castNumber(list.get(i).getGrossSalaryDytemp(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f",
																	ReportCostants.castNumber(list.get(i).getAdvanceDed(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(
																	String.format("%.2f", ReportCostants.castNumber(list.get(i).getLoanDed(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f", ReportCostants.castNumber(list.get(i).getItded(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(
																	String.format("%.2f", ReportCostants.castNumber(list.get(i).getPayDed(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f", ReportCostants.castNumber(list.get(i).getPtDed(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f",
																	ReportCostants.castNumber(list.get(i).getEmployeePf(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f", ReportCostants.castNumber(list.get(i).getEsic(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f", ReportCostants.castNumber(list.get(i).getMlwf(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f",
																	ReportCostants.castNumber(list.get(i).getSocietyContribution(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													double finalDed = list.get(i).getAdvanceDed() + list.get(i).getLoanDed() + list.get(i).getItded()
																	+ list.get(i).getPayDed() + list.get(i).getPtDed() + list.get(i).getEmployeePf()
																	+ list.get(i).getEsic() + list.get(i).getMlwf() + list.get(i).getSocietyContribution();
															out.println(String.format("%.2f", ReportCostants.castNumber(finalDed, amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f",
																	ReportCostants.castNumber(list.get(i).getMiscExpAdd(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f",
																	ReportCostants.castNumber(list.get(i).getPerformanceBonus(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(String.format("%.2f",
																	ReportCostants.castNumber(list.get(i).getProductionInsentive(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(
																	String.format("%.2f", ReportCostants.castNumber(list.get(i).getOtWages(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(
																	String.format("%.2f", ReportCostants.castNumber(list.get(i).getReward(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(
																	String.format("%.2f", ReportCostants.castNumber(list.get(i).getNetSalary(), amount_round)));
												%>
											</td>
											<%-- <td class="text-right">
												<%
													out.println(
																	String.format("%.2f", ReportCostants.castNumber(list.get(i).getEmployerEps(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(
																	String.format("%.2f", ReportCostants.castNumber(list.get(i).getEmployerPf(), amount_round)));
												%>
											</td>  
											<td class="text-right">
												<%
													out.println(
																	String.format("%.2f", ReportCostants.castNumber(list.get(i).getEpmloyerEpfDefault(), amount_round)));
												%>
											</td>
											<td class="text-right">
												<%
													out.println(
																	String.format("%.2f", ReportCostants.castNumber(list.get(i).getEpmloyerEpfExtra(), amount_round)));
												%>
											</td> --%>
										</tr>
										<%
											}
											} catch (Exception e) {
												e.printStackTrace();
											}
										%>

										<%-- <c:forEach items="${empList}" var="empList" varStatus="count">
											<tr>
												<td>${count.index+1}</td>
												<td>${empList.empCode}</td>
												<td>${empList.empName}</td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.basicCal}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.otWages}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.fund}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.grossSalaryDytemp}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.miscExpAdd}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.advanceDed}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.loanDed}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.itded}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.payDed}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.ptDed}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.epfWages}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.esic}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.mlwf}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><c:set
														value="${empList.advanceDed+empList.loanDed+empList.itded+empList.payDed+empList.ptDed+empList.epfWages+empList.esic+empList.mlwf}"
														var="finalDed"></c:set> <fmt:formatNumber type="number"
														groupingUsed="false" value="${finalDed}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.performanceBonus}"
														maxFractionDigits="2" minFractionDigits="2" /></td>
												<td class="text-right"><fmt:formatNumber type="number"
														groupingUsed="false" value="${empList.netSalary}"
														maxFractionDigits="2" minFractionDigits="2" /></td>

											</tr>
										</c:forEach> --%>

									</tbody>
								</table>
							</div>
							<br>
							<div class="text-center">

								<button type="button"
									class="btn bg-blue ml-3 legitRipple bootbox_custom"
									id="submtbtn">Generate Payroll</button>
								<button type="button" class="btn bg-blue ml-3 legitRipple"
									id="submtbtn" onclick="getProgReport(0,'exelForPayroll')">
									Excel</button>
							</div>

						</form>
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
		// Single picker
		$(function() {
			$("#datepicker").datepicker({
				changeMonth : true,
				changeYear : true,
				yearRange : "-50:+50",
				dateFormat : "mm-yy"
			});
		});
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
	</script>

	<script type="text/javascript">
		//use this function for all reports just get mapping form action name dynamically as like of prm from every report pdf,excel function	
		function getProgReport(prm, mapping) {

			window.open("${pageContext.request.contextPath}/" + mapping + "/",
					"_blank");

		}
	</script>

	<script>
		// Custom bootbox dialog
		$('.bootbox_custom')
				.on(
						'click',
						function() {
							//var uuid = $(this).data("uuid") // will return the number 123
							bootbox
									.confirm({
										title : 'Confirm ',
										message : 'Are you sure want to generate the payroll. Once it Gerenerated you will not able to edit any records/data like TDS,Advance, Loan etc?',
										buttons : {
											confirm : {
												label : 'Yes',
												className : 'btn-success'
											},
											cancel : {
												label : 'Cancel',
												className : 'btn-link'
											}
										},
										callback : function(result) {
											if (result) {
												document
														.getElementById(
																'insertFinalPayRollAndDeleteFroTemp')
														.submit();

											}
										}
									});
						});
	</Script>
</body>
</html>