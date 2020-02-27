<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Payment Slip</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->


<style type="text/css">
table {
	border-color: black;
	font-size: 12;
	width: 100%;
	page-break-inside: auto !important;
	display: block;
}

p {
	color: black;
	font-family: arial;
	font-size: 70%;
	margin-top: 0;
	padding: 0;
	font-weight: bold;
}

.pn {
	color: black;
	font-family: arial;
	font-size: 10%;
	margin-top: 0;
	padding: 0;
	font-weight: normal;
}

h4 {
	color: black;
	font-family: sans-serif;
	font-size: 100%;
	font-weight: bold;
	padding-bottom: 10px;
	margin: 0;
}

h5 {
	color: black;
	font-family: sans-serif;
	font-size: 70%;
	font-weight: normal;
	margin: 0;
}

h6 {
	color: black;
	font-family: arial;
	font-size: 60%;
	font-weight: normal;
	margin: 10%;
}

th {
	color: black;
}

hr {
	height: 1px;
	border: none;
	color: rgb(60, 90, 180);
	background-color: rgb(60, 90, 180);
}

.invoice-box table tr.information table td {
	padding-bottom: 0px;
	align-content: center;
}

.set-height td {
	position: relative;
	overflow: hidden;
	height: 2em;
}

.set-height t {
	position: relative;
	overflow: hidden;
	height: 2em;
}

.set-height p {
	position: absolute;
	margin: .1em;
	left: 0;
	top: 0;
}
</style>

</head>
<body>


	<c:set value="0" var="index"></c:set>
	<c:forEach items="${list}" var="list" varStatus="count">

		<c:set value="${index+1}" var="index"></c:set>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-top: 1px solid #313131; border-right: 1px solid #313131; margin-left: 60px; margin-right: 60px; margin-top: 20px;">
			<tr>
				<td colspan="2"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;"
					align="center"><table width="100%">

						<c:set value="" var="logo"></c:set>
						<c:set value="-" var="companyName"></c:set>
						<c:set value="-" var="longAdd1"></c:set>
						<c:forEach items="${companyList}" var="companyList">
							<c:if test="${companyList.companyId==list.subCmpId}">
								<c:set value="${companyList.logo}" var="logo"></c:set>
								<c:set value="${companyList.companyName}" var="companyName"></c:set>
								<c:set value="${companyList.longAdd1}" var="longAdd1"></c:set>
							</c:if>
						</c:forEach>
						<tr>
							<td width="22.33%"><img src="${logoUrl}${logo}" width="80"
								height="50" /></td>

							<td width="53.33%" valign="top"
								style="font-weight: bold; margin: 0px;" align="center">
								<h4 align="center" style="font-size: 16px;">${companyName}</h4>
								<h6 style="font-weight: bold; margin: 0px; font-size: 10px;"
									align="center">${longAdd1}</h6>
								<h5 style="font-weight: bold; margin: 0px; font-size: 14px;"
									align="center">Payment Slip for the month of ${monthName}
									${year}</h5>
							</td>

							<td width="22.33%" valign="top"
								style="font-weight: bold; margin: 0px;" align="right"></td>

						</tr>

					</table></td>
			</tr>
			<tr>
				<td colspan="2"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;"
					align="center"><table width="100%">
						<tr>
							<td width="22.33%" style="color: #000; font-size: 12px;">Emp
								Code: ${list.empCode}</td>

							<td width="53.33%" valign="top"
								style="color: #000; font-size: 12px;" align="center">Name:
								${list.name}</td>

							<td width="22.33%" valign="top"
								style="color: #000; font-size: 12px;" align="right">Designation
								:${list.designName}</td>
						</tr>
					</table></td>
			</tr>

			<tr>
				<td colspan="2"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;"
					align="center"><table width="100%">
						<tr>
							<td width="16.66%" style="color: #000; font-size: 12px;"
								align="center">Payable Days: ${list.payableDays}</td>

							<td width="16.66%" valign="top"
								style="color: #000; font-size: 12px;" align="center">Present
								Days: ${list.presentDays}</td>

							<td width="16.66%" valign="top"
								style="color: #000; font-size: 12px;" align="center">Weekly
								Off : ${list.weeklyOff}</td>
							<td width="16.66%" style="color: #000; font-size: 12px;">Paid
								Leave: ${list.paidLeave}</td>

							<td width="16.66%" valign="top"
								style="color: #000; font-size: 12px;" align="center">Holiday:
								${list.paidHoliday}</td>

							<td width="16.66%" valign="top"
								style="color: #000; font-size: 12px;" align="center">Absent:
								${list.unpaidLeave}</td>
						</tr>
					</table></td>
			</tr>

			<tr>
				<td
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;"
					align="center" valign="top"><table width="100%"
						style="border-top: 1px solid #313131; border-right: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131;">
						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; border-bottom: 1px solid #313131;"
								align="center">PAY HEAD</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-bottom: 1px solid #313131;"
								align="center">EARNINGS</td>
						</tr>

						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">Basic</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.basicCal}</td>

						</tr>
						<c:forEach items="${list.payrollAllownceList}"
							var="payrollAllownceList">
							<tr>
								<td width="50%" valign="top"
									style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
									align="left">${payrollAllownceList.shortName}</td>

								<td width="50%" valign="top"
									style="color: #000; font-size: 12px; padding-right: 5px;"
									align="right">${payrollAllownceList.allowanceValueCal}</td>

							</tr>
						</c:forEach>
						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">Claim ADD</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.miscExpAdd}</td>

						</tr>
						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">Performance Bonus</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.performanceBonus}</td>

						</tr>

						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">Production Incentive</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.productionInsentive}</td>

						</tr>
						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">Performance Incentive</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.otWages}</td>

						</tr>
						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">Reward</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.reward}</td>

						</tr>

					</table></td>

				<td
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;"
					align="center" valign="top">
					<table width="1000%"
						style="border-top: 1px solid #313131; border-right: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131;">
						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; border-bottom: 1px solid #313131;"
								align="center">DEDUCTION HEAD</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-bottom: 1px solid #313131;"
								align="center">DEDUTIONS</td>

						</tr>

						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">PROVIDENT FUND</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.employeePf}</td>

						</tr>

						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">PROF TAX</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.ptDed}</td>

						</tr>

						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">ESIC</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.esic}</td>

						</tr>

						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">ADVANCE</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.advanceDed}</td>

						</tr>

						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">TDS</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.itded}</td>

						</tr>

						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">SOCIETY CONTR</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.societyContribution}</td>

						</tr>

						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">LOAN</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.loanDed}</td>

						</tr>
						<tr>
							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; padding-left: 5px;"
								align="left">AB Deduction</td>

							<td width="50%" valign="top"
								style="color: #000; font-size: 12px; padding-right: 5px;"
								align="right">${list.abDeduction}</td>

						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 2px; color: #000; font-size: 12px;"
					align="center">
					<table width="100%">
						<tr>
							<td width="80%"
								style="color: #000; font-size: 12px; border-right: 1px solid #313131; text-transform: capitalize;">Amount
								in word - Rs : ${list.moneyInword}</td>

							<td width="20%" valign="top"
								style="color: #000; font-size: 12px;" align="right">Net
								Pay: ${list.netSalary}</td>

						</tr>
					</table>
				</td>
			</tr>
		</table>
		<p style="color: #000; font-size: 12px;" align="center">THIS IS A
			COMPUTER GENERATED PAYSLIP, NOT REQUIRING SIGNATURE</p>
		<!-- END Main Content -->
		<br>

		<c:if test="${index==2}">
			<c:set value="0" var="index"></c:set>
			<div style="page-break-after: always;"></div>
		</c:if>

	</c:forEach>
</body>
</html>