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


				<!-- Highlighting rows and columns -->
				<div class="card">


					<div class="card-header header-elements-inline">
						<table width="100%">
							<tr width="100%">
								<td width="60%"><h5 class="card-title">Employee List</h5></td>
								<td width="40%" align="right">
								
								<a href="${pageContext.request.contextPath}/showEmpFileUpload"
											class="breadcrumb-elements-item">
											<button type="button" class="btn btn-success">Upload Data</button></a>
											
											
												<a href="${pageContext.request.contextPath}/showEmpSalUpload"
											class="breadcrumb-elements-item">
											<button type="button" class="btn btn-success">Upload Salary Data</button></a>
														
														
														<c:if test="${addAccess==0}">
										<a href="${pageContext.request.contextPath}/employeeAdd"
											class="breadcrumb-elements-item">
											<button type="button" class="btn btn-primary">Add
												Employee</button>
										</a>
									</c:if>
									
									
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
						<table
							class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
							id="printtable1">
							<thead>
								<tr class="bg-blue">

									<th width="10%">Sr. No.</th>
									<th>Emp Code</th>
									<th>Name</th>
									<th>Emp Type</th>
									<th>Dept.</th>
									<th>Desig</th>
									<th>Location</th>
									<th width="10%" class="text-center">Actions</th>
								</tr>
							</thead>
							<tbody>


								<c:forEach items="${empList}" var="empList" varStatus="count">
									<tr>
										<td>${count.index+1}</td>
										<td>${empList.empCode}</td>
										<td> <a href="#" onclick="getEmpData('${empList.exVar1}')"
											class="breadcrumb-elements-item" >
											${empList.firstName}&nbsp;${empList.middleName}&nbsp;${empList.surname}</a></td>
										<td>${empList.empTypeName}</td>
										<td>${empList.deptName}</td>
										<td>${empList.empDesgn}</td>
										<td>${empList.locName}</td>
										<%-- <td>${empList.micrCode}</td>
										<td>${empList.ifscCode}</td>  --%>

										<td class="text-center"><c:if test="${editAccess == 0}">
												<a
													href="${pageContext.request.contextPath}/employeeEdit?empId=${empList.exVar1}"
													class="list-icons-item text-primary-600"
													data-popup="tooltip" title="" data-original-title="Edit"><i
													class="icon-pencil7"></i></a>
											</c:if> <c:if test="${deleteAccess == 0}">

												<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${empList.exVar1}" data-popup="tooltip" title=""
													data-original-title="Delete"><i class="icon-trash"></i></a>
											</c:if>  <a	href="${pageContext.request.contextPath}/showEmpGraphs?empId=${empList.exVar1}"
													class="list-icons-item text-primary-600" data-popup="tooltip" title="" data-original-title="Graphs & Reports"><i class="icon-history"
														style="color: black;"></i></a> 
														
														
														
													 </td>
									</tr>
								</c:forEach>

							</tbody>
						</table>

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
		<!-- Large modal -->
	<div id="modal_large1" class="modal fade" tabindex="-1">
	 
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Employee Details</h5>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<div class="modal-body" id="modalbody">
					<!--  Profile Model -->
					<!--  Profile Model -->
					<!--  Profile Model -->
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					<!-- <button type="button" class="btn bg-primary">Save changes</button> -->
				</div>
			</div>
		</div>
	</div>
	<!-- /large modal -->
	<script>
		// Custom bootbox dialog
		$('.bootbox_custom')
				.on(
						'click',
						function() {
							var uuid = $(this).data("uuid") // will return the number 123
							bootbox
									.confirm({
										title : 'Confirm ',
										message : 'Are you sure you want to delete selected records ?',
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
												location.href = "${pageContext.request.contextPath}/deleteEmp?empId="
														+ uuid;

											}
										}
									});
						});
	</Script>
	<script type="text/javascript">
	function getEmpData(empId) {
		//alert(empId)
		
		//alert(var1+':'+var2);
		   
		   var strhref ="${pageContext.request.contextPath}/getProfile?empId="+empId;
		   $("#modalbody").load(strhref);
		   $("#modal_large1").modal("show");
		   $('#modal_large1').on('hidden.bs.modal', function () {
			 $("#modalbody").html("");
		   }); 
		   
		$(document).ready(function(){
 
		
		
			/* 	$.getJSON('${showEmployeeProfile}',

			{

				empId : empId,
				ajax : 'true'

			}, function(data) {
				$('#modal_large').modal('toggle');
				//alert(JSON.stringify( data));
				 document.getElementById("emp_code").innerHTML = data.empDtl.empCode;
				document.getElementById("emp_fname").innerHTML = data.empDtl.firstName;
				document.getElementById("emp_mname").innerHTML = data.empDtl.empMidName;
				document.getElementById("emp_lname").innerHTML = data.empDtl.surname;
				document.getElementById("emp_comp").innerHTML = data.empDtl.companyName;
				document.getElementById("emp_type").innerHTML = data.empDtl.empType;
				document.getElementById("emp_depart").innerHTML = data.empDtl.departName;
				document.getElementById("emp_desig").innerHTML = data.empDtl.desingnation;
				document.getElementById("emp_contractor").innerHTML = data.empDtl.contractorName;
				document.getElementById("emp_cat").innerHTML = data.empDtl.empCategory;
				document.getElementById("emp_skill").innerHTML = data.empDtl.skill;
				document.getElementById("emp_loc").innerHTML = data.empDtl.location;
				document.getElementById("emp_mob1").innerHTML = data.empDtl.mobileNo1;
				document.getElementById("emp_mob2").innerHTML = data.empDtl.mobileNo2;
				document.getElementById("emp_uan").innerHTML = data.empDtl.uan;
				document.getElementById("emp_aadhar").innerHTML = data.empDtl.aadharNo;
				document.getElementById("emp_esic").innerHTML = data.empDtl.esicNo;
				document.getElementById("emp_pan").innerHTML = data.empDtl.panCardNo;
				document.getElementById("emp_pf").innerHTML = data.empDtl.pfNo;
				document.getElementById("emp_access_loc").innerHTML = data.empDtl.acciessbleLocations;
				
				if(data.empDtl.authorityDesigType==0){
					document.getElementById("emp_desig_type").innerHTML = 'Employee';
				}else if(data.empDtl.authorityDesigType==1){
					document.getElementById("emp_desig_type").innerHTML = 'HOD';
				}else if(data.empDtl.authorityDesigType==2){
					document.getElementById("emp_desig_type").innerHTML = 'HR';
				}else{
					document.getElementById("emp_desig_type").innerHTML = 'NA';
				}
				
				
				// Personal Information 
				document.getElementById("mid_name").innerHTML = data.empDtl.middleName;
				document.getElementById("marital_status").innerHTML = data.empDtl.maritalStatus;
				document.getElementById("dob").innerHTML = data.empDtl.dob;
				document.getElementById("gender").innerHTML = data.empDtl.gender;
				if(data.empDtl.middleNameRelation=='father'){
					document.getElementById("relation").innerHTML = 'Father';
				}else if(data.empDtl.middleNameRelation=='husband'){
					document.getElementById("relation").innerHTML = 'Husband';
				}else{
					document.getElementById("relation").innerHTML = 'NA';
				}
				document.getElementById("email").innerHTML = data.empDtl.email;
				document.getElementById("curr_address").innerHTML = data.empDtl.address;
				document.getElementById("permnt_address").innerHTML = data.empDtl.permanentAddress;
				document.getElementById("qualification").innerHTML = data.empDtl.empQualification;
				document.getElementById("emerName").innerHTML = data.empDtl.emerName;
				document.getElementById("emerContact1").innerHTML = data.empDtl.emerContactNo1;
				document.getElementById("emerContact2").innerHTML = data.empDtl.emerContactNo2;
				document.getElementById("emerPersonAdd").innerHTML = data.empDtl.emerContactAddr;
				document.getElementById("blood_group").innerHTML = data.empDtl.bloodGroup;
				
				if(data.empDtl.uniformSize=='medium'){
					document.getElementById("uniform").innerHTML = 'MEDIUM';
				}else if(data.empDtl.uniformSize=='large'){
					document.getElementById("uniform").innerHTML = 'LARGE';
				}else if(data.empDtl.uniformSize=='xl'){
					document.getElementById("uniform").innerHTML = 'XL';
				}else if(data.empDtl.uniformSize=='xxl'){
					document.getElementById("uniform").innerHTML = 'XXL';
				}else if(data.empDtl.uniformSize=='xxxl'){
					document.getElementById("uniform").innerHTML = 'XXXL';
				}else{
					document.getElementById("uniform").innerHTML = 'NA';
				}
				document.getElementById("emp_img").innerHTML = data.empDtl.exVar1;
				
				
				// Relative Information 
				document.getElementById("person1").innerHTML = data.empDtl.name1;
				document.getElementById("person2").innerHTML = data.empDtl.name2;
				document.getElementById("person3").innerHTML = data.empDtl.name3;
				document.getElementById("person4").innerHTML = data.empDtl.name4;
				document.getElementById("person5").innerHTML = data.empDtl.name5;
				document.getElementById("person6").innerHTML = data.empDtl.name6;
				
				document.getElementById("nom_dob1").innerHTML = data.empDtl.dob1;
				document.getElementById("nom_dob2").innerHTML = data.empDtl.dob2;
				document.getElementById("nom_dob3").innerHTML = data.empDtl.dob3;
				document.getElementById("nom_dob4").innerHTML = data.empDtl.dob4;
				document.getElementById("nom_dob5").innerHTML = data.empDtl.dob5;
				document.getElementById("nom_dob6").innerHTML = data.empDtl.dob6;
				
				// Relation 1  
				if(data.empDtl.relation1=='f'){
					document.getElementById("relation1").innerHTML = 'Father';
				}else if(data.empDtl.relation1=='m'){
					document.getElementById("relation1").innerHTML = 'Mother';
				}else if(data.empDtl.relation1=='s1'){
					document.getElementById("relation1").innerHTML = 'Spouse';
				}else if(data.empDtl.relation1=='b'){
					document.getElementById("relation1").innerHTML = 'Brother';
				}else if(data.empDtl.relation1=='s2'){
					document.getElementById("relation1").innerHTML = 'Sister';
				}else if(data.empDtl.relation1=='s3'){
					document.getElementById("relation1").innerHTML = 'Son';
				}else if(data.empDtl.relation1=='d'){
					document.getElementById("relation1").innerHTML = 'Daughter';
				}else{
					document.getElementById("relation1").innerHTML = 'NA';
				}
				
				
				// Relation 2  			
				if(data.empDtl.relation2=='f'){
					document.getElementById("relation2").innerHTML = 'Father';
				}else if(data.empDtl.relation2=='m'){
					document.getElementById("relation2").innerHTML = 'Mother';
				}else if(data.empDtl.relation2=='s1'){
					document.getElementById("relation2").innerHTML = 'Spouse';
				}else if(data.empDtl.relation2=='b'){
					document.getElementById("relation2").innerHTML = 'Brother';
				}else if(data.empDtl.relation2=='s2'){
					document.getElementById("relation2").innerHTML = 'Sister';
				}else if(data.empDtl.relation2=='s3'){
					document.getElementById("relation2").innerHTML = 'Son';
				}else if(data.empDtl.relation2=='d'){
					document.getElementById("relation2").innerHTML = 'Daughter';
				}else{
					document.getElementById("relation2").innerHTML = 'NA';
				}
				
				// Relation 3  				
				if(data.empDtl.relation3=='f'){
					document.getElementById("relation3").innerHTML = 'Father';
				}else if(data.empDtl.relation3=='m'){
					document.getElementById("relation3").innerHTML = 'Mother';
				}else if(data.empDtl.relation3=='s1'){
					document.getElementById("relation3").innerHTML = 'Spouse';
				}else if(data.empDtl.relation3=='b'){
					document.getElementById("relation3").innerHTML = 'Brother';
				}else if(data.empDtl.relation3=='s2'){
					document.getElementById("relation3").innerHTML = 'Sister';
				}else if(data.empDtl.relation3=='s3'){
					document.getElementById("relation3").innerHTML = 'Son';
				}else if(data.empDtl.relation3=='d'){
					document.getElementById("relation3").innerHTML = 'Daughter';
				}else{
					document.getElementById("relation3").innerHTML = 'NA';
				}
				
				// Relation 4  
				if(data.empDtl.relation4=='f'){
					document.getElementById("relatio4n").innerHTML = 'Father';
				}else if(data.empDtl.relation4=='m'){
					document.getElementById("relation4").innerHTML = 'Mother';
				}else if(data.empDtl.relation4=='s1'){
					document.getElementById("relation4").innerHTML = 'Spouse';
				}else if(data.empDtl.relation4=='b'){
					document.getElementById("relation4").innerHTML = 'Brother';
				}else if(data.empDtl.relation4=='s2'){
					document.getElementById("relation4").innerHTML = 'Sister';
				}else if(data.empDtl.relation4=='s3'){
					document.getElementById("relation4").innerHTML = 'Son';
				}else if(data.empDtl.relation4=='d'){
					document.getElementById("relation4").innerHTML = 'Daughter';
				}else{
					document.getElementById("relation4").innerHTML = 'NA';
				}
				
				// Relation 5  
				if(data.empDtl.relation5=='f'){
					document.getElementById("relation5").innerHTML = 'Father';
				}else if(data.empDtl.relation5=='m'){
					document.getElementById("relation5").innerHTML = 'Mother';
				}else if(data.empDtl.relation5=='s1'){
					document.getElementById("relation5").innerHTML = 'Spouse';
				}else if(data.empDtl.relation5=='b'){
					document.getElementById("relation5").innerHTML = 'Brother';
				}else if(data.empDtl.relation5=='s2'){
					document.getElementById("relation5").innerHTML = 'Sister';
				}else if(data.empDtl.relation5=='s3'){
					document.getElementById("relation5").innerHTML = 'Son';
				}else if(data.empDtl.relation5=='d'){
					document.getElementById("relation5").innerHTML = 'Daughter';
				}else{
					document.getElementById("relation5").innerHTML = 'NA';
				}
				
				// Relation 6  
				if(data.empDtl.relation6=='f'){
					document.getElementById("relation6").innerHTML = 'Father';
				}else if(data.empDtl.relation6=='m'){
					document.getElementById("relation6").innerHTML = 'Mother';
				}else if(data.empDtl.relation6=='s1'){
					document.getElementById("relation6").innerHTML = 'Spouse';
				}else if(data.empDtl.relation6=='b'){
					document.getElementById("relation6").innerHTML = 'Brother';
				}else if(data.empDtl.relation6=='s2'){
					document.getElementById("relation6").innerHTML = 'Sister';
				}else if(data.empDtl.relation6=='s3'){
					document.getElementById("relation6").innerHTML = 'Son';
				}else if(data.empDtl.relation6=='d'){
					document.getElementById("relation6").innerHTML = 'Daughter';
				}else{
					document.getElementById("relation").innerHTML = 'NA';
				}
				
				document.getElementById("occupation1").innerHTML = data.empDtl.occupation1;
				document.getElementById("occupation2").innerHTML = data.empDtl.occupation2;
				document.getElementById("occupation3").innerHTML = data.empDtl.occupation3;
				document.getElementById("occupation4").innerHTML = data.empDtl.occupation4;
				document.getElementById("occupation5").innerHTML = data.empDtl.occupation5;
				document.getElementById("occupation6").innerHTML = data.empDtl.occupation6;
				
				
				
				// Bank Details  
				document.getElementById("ac_no").innerHTML = data.empDtl.accNo;
				document.getElementById("bank_name").innerHTML = data.empDtl.bankName;
				
				// Salary And Allowance Details  
				document.getElementById("basic_sal").innerHTML = data.empDtl.basic;
				document.getElementById("gross_sal").innerHTML = data.empDtl.grossSalary;
				document.getElementById("society_Contri").innerHTML = data.empDtl.societyContribution;
				
				var allowanceTtl = 0;;
				for (var i = 0; i < data.empAllowncDtl.length; i++) {
					
					if(data.empAllowncDtl[i].allowanceId==1){						
						document.getElementById("allwnce_DA").innerHTML = data.empAllowncDtl[i].allowanceValue;
						allowanceTtl = allowanceTtl+data.empAllowncDtl[i].allowanceValue;
					} 
					else if(data.empAllowncDtl[i].allowanceId==5){
						document.getElementById("allwnce_FA").innerHTML = data.empAllowncDtl[i].allowanceValue;
						allowanceTtl = allowanceTtl+data.empAllowncDtl[i].allowanceValue;
					}
					else if(data.empAllowncDtl[i].allowanceId==9){
						document.getElementById("allwnce_HRA").innerHTML = data.empAllowncDtl[i].allowanceValue;
						allowanceTtl = allowanceTtl+data.empAllowncDtl[i].allowanceValue;
					}
					else if(data.empAllowncDtl[i].allowanceId==11){
						document.getElementById("allwnce_CA").innerHTML = data.empAllowncDtl[i].allowanceValue;
						allowanceTtl = allowanceTtl+data.empAllowncDtl[i].allowanceValue;
					}
					else if(data.empAllowncDtl[i].allowanceId==14){
						document.getElementById("allwnce_EA").innerHTML = data.empAllowncDtl[i].allowanceValue;
						allowanceTtl = allowanceTtl+data.empAllowncDtl[i].allowanceValue;
					}
					else if(data.empAllowncDtl[i].allowanceId==19){
						document.getElementById("allwnce_OTH").innerHTML = data.empAllowncDtl[i].allowanceValue;
						allowanceTtl = allowanceTtl+data.empAllowncDtl[i].allowanceValue;
					}
					else if(data.empAllowncDtl[i].allowanceId==173){
						document.getElementById("allwnce_MA").innerHTML = data.empAllowncDtl[i].allowanceValue;
						allowanceTtl = allowanceTtl+data.empAllowncDtl[i].allowanceValue;
					}
					else if(data.empAllowncDtl[i].allowanceId==16){
						document.getElementById("allwnce_TA").innerHTML = data.empAllowncDtl[i].allowanceValue;
						allowanceTtl = allowanceTtl+data.empAllowncDtl[i].allowanceValue;
					} 
					
				}
				
				document.getElementById("alwncTtl").innerHTML =allowanceTtl;
				
				if(data.empDtl.pfApplicable=="yes"){
					document.getElementById("pf_applicable").innerHTML = 'YES';
				}else{
					document.getElementById("pf_applicable").innerHTML = 'NO';
				}
				document.getElementById("pf_type").innerHTML = data.empDtl.pfType;
				document.getElementById("pf_emp_per").innerHTML = data.empDtl.pfEmpPer;
				if(data.empDtl.esicApplicable=='yes'){
					document.getElementById("esic_applicable").innerHTML = 'YES';
				}else{
					document.getElementById("esic_applicable").innerHTML = 'NO';
				}
				
				if(data.empDtl.mlwfApplicable=='yes'){
					document.getElementById("mlwf_applicable").innerHTML = 'YES';
				}else{
					document.getElementById("mlwf_applicable").innerHTML = 'NO';	
				}
				
				if(data.empDtl.salBasis=='monthly'){
					document.getElementById("salary_basis").innerHTML = 'Monthly';
				}else{
					document.getElementById("salary_basis").innerHTML = 'Daily';
				}
				
				if(data.empDtl.ptApplicable=='yes'){
					document.getElementById("pt_applicable").innerHTML = 'YES';
				}else{
					document.getElementById("pt_applicable").innerHTML = 'NO';
				}
				
				document.getElementById("epf_join_date").innerHTML = data.empDtl.epfJoiningDate;				
				document.getElementById("cmp_join_date").innerHTML = data.empDtl.cmpJoiningDate;
				document.getElementById("cmp_leave_date").innerHTML = data.empDtl.cmpLeavingDate;
				document.getElementById("leave_reason").innerHTML = data.empDtl.leavingReason;
				
				if(data.empDtl.leavingReasonEsic==0){
					document.getElementById("esic_leave_reason").innerHTML = 'Without Reason';
				}else if(data.empDtl.leavingReasonEsic==1){
					document.getElementById("esic_leave_reason").innerHTML = 'On Leave';
				}else if(data.empDtl.leavingReasonEsic==2){
					document.getElementById("esic_leave_reason").innerHTML = 'Self Service';
				}else if(data.empDtl.leavingReasonEsic==3){
					document.getElementById("esic_leave_reason").innerHTML = 'Retired';
				}else if(data.empDtl.leavingReasonEsic==4){
					document.getElementById("esic_leave_reason").innerHTML = 'Out of Coverage';
				}else if(data.empDtl.leavingReasonEsic==5){
					document.getElementById("esic_leave_reason").innerHTML = 'Expired';
				}else if(data.empDtl.leavingReasonEsic==6){
					document.getElementById("esic_leave_reason").innerHTML = 'Non Implemented Area';
				}else if(data.empDtl.leavingReasonEsic==7){
					document.getElementById("esic_leave_reason").innerHTML = 'Compliance by immediate Employer';
				}else if(data.empDtl.leavingReasonEsic==8){
					document.getElementById("esic_leave_reason").innerHTML = 'Suspension Reason';
				}else if(data.empDtl.leavingReasonEsic==9){
					document.getElementById("esic_leave_reason").innerHTML = 'Strike/Lockout';
				}else if(data.empDtl.leavingReasonEsic==10){
					document.getElementById("esic_leave_reason").innerHTML = 'Retrenchment';
				}else if(data.empDtl.leavingReasonEsic==11){
					document.getElementById("esic_leave_reason").innerHTML = 'No Work';
				}else if(data.empDtl.leavingReasonEsic==12){
					document.getElementById("esic_leave_reason").innerHTML = 'Does not belong to this Employer';
				}
				
				
				if(data.empDtl.leavingReasonPf==1){
					document.getElementById("lr_pf").innerHTML = 'Cessation';
				}else if(data.empDtl.leavingReasonPf==2){
					document.getElementById("lr_pf").innerHTML = 'Superannuation';
				}else if(data.empDtl.leavingReasonPf==3){
					document.getElementById("lr_pf").innerHTML = 'Retirement';
				}else if(data.empDtl.leavingReasonPf==4){
					document.getElementById("lr_pf").innerHTML = 'Death in Service';
				}else if(data.empDtl.leavingReasonPf==5){
					document.getElementById("lr_pf").innerHTML = 'Permanent Disablement';
				} */
			});
	}
	
	</script>
</body>
</html>