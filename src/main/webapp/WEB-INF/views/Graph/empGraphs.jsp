<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>


<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">
	 
<script src="${pageContext.request.contextPath}/resources/assets/js/app.js"></script>
	<!-- Theme JS files -->
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/plugins/visualization/d3/d3.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/plugins/visualization/d3/d3_tooltip.js"></script>
 	
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/demo_pages/charts/d3/bars/bars_basic_vertical.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/demo_pages/charts/d3/bars/bars_basic_horizontal.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/demo_pages/charts/d3/bars/bars_basic_tooltip.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/demo_pages/charts/d3/bars/bars_basic_grouped.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/demo_pages/charts/d3/bars/bars_basic_stacked.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/demo_pages/charts/d3/bars/bars_basic_stacked_normalized.js"></script>

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
		 
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">
					<div class="card-header header-elements-inline">
						<h5 class="card-title">Grouped bar chart</h5>
						<div class="header-elements">
							<div class="list-icons">
		                		<a class="list-icons-item" data-action="collapse"></a>
		                		<a class="list-icons-item" data-action="reload"></a>
		                		<a class="list-icons-item" data-action="remove"></a>
		                	</div>
	                	</div>
					</div>

					<div class="card-body">
						<p class="mb-3">This <code>grouped</code> bar chart is constructed from a CSV file storing the demo data. The chart employs conventional margins and a number of D3 features: <code>d3.csv</code> - load and parse data; <code>d3.scale.ordinal</code> - x-position encoding and color encoding; <code>d3.scale.linear</code> - y-position encoding; <code>d3.format</code> - SI prefix formatting (e.g., “10M” for 10,000,000); <code>d3.max</code> - compute domains; <code>d3.keys</code> - compute column names; <code>d3.svg.axis</code> - display axes.</p>

						<div class="chart-container">
							<div class="chart" id="d3-bar-grouped"></div>
						</div>
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