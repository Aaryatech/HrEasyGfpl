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
<title>Generated Payroll</title>
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



	<%-- <c:forEach items="${list}" var="item" varStatus="count"> --%>


	<!--  -->

	<%-- <p style="text-align: left; font-weight: normal;">
			Original / Duplicate(Acnt)/Triplicate(Purch)/Stroes <span
				style="float: right;">${documentBean.docIsoSerialNumber}</span>
		</p> --%>
	<!-- p -->


	 
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		style="border-top: 1px solid #313131; border-right: 1px solid #313131;">
		<tr>
			<td colspan="2"
				style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;"
				align="center"><table width="100%">
					<tr>
						<td width="22.33%"><img
							src="${pageContext.request.contextPath}/resources/img/monginislogo.png"
							width="95" height="60" /></td>

						<td width="53.33%" valign="top"
							style="font-weight: bold; margin: 0px;" align="center">
							<h4 align="center" style="font-size: 16px;">METAFORGE
								ENGINEERING (I) PVT. LTD.</h4>
							<h6 style="font-weight: bold; margin: 0px; font-size: 10px;"
								align="center">S.NO. 22/3, Dindori Road, Mhasrul, Nasik -
								422004</h6>
							<h6 style="font-weight: bold; margin: 0px; font-size: 10px;"
								align="center">Payslip for the month of November 2019</h6>
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
							Code: C001</td>

						<td width="53.33%" valign="top"
							style="color: #000; font-size: 12px;" align="center">Name:
							KAMINI DHANANJAY SONAWANE</td>

						<td width="22.33%" valign="top"
							style="color: #000; font-size: 12px;" align="right">Designation
							:OPERATOR</td>
					</tr>
				</table></td>
		</tr>

		<tr>
			<td colspan="2"
				style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;"
				align="center"><table width="100%">
					<tr>
						<td width="22.33%" style="color: #000; font-size: 12px;">Payable
							Days: 26.5</td>

						<td width="53.33%" valign="top"
							style="color: #000; font-size: 12px;" align="center">Present
							Days: 22</td>

						<td width="22.33%" valign="top"
							style="color: #000; font-size: 12px;" align="right">Weekly
							Off : 5</td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td colspan="2"
				style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;"
				align="center"><table width="100%">
					<tr>
						<td width="22.33%" style="color: #000; font-size: 12px;">Payable
							Days: 26.5</td>

						<td width="53.33%" valign="top"
							style="color: #000; font-size: 12px;" align="center">Present
							Days: 22</td>

						<td width="22.33%" valign="top"
							style="color: #000; font-size: 12px;" align="right">Weekly
							Off : 5</td>
					</tr>
				</table></td>
		</tr>
	</table>
	<!-- END Main Content -->

	<div style="page-break-after: always;"></div>
	<%-- </c:forEach> --%>
</body>
</html>