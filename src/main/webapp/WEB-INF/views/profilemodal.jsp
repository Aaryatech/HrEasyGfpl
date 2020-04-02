<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
	 

	<!-- Page content -->
 
	 

		<!-- Main content -->
		

			<!-- Page header -->
			<div class="page-header page-header-light"></div>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">


				<!-- Highlighting rows and columns -->
				<div class="card">


					<div class="card-header header-elements-inline">
						<h5 class="card-title"> Emp Code : ${empInfo.empCode }</h5>
					</div>

					<div class="card-body fixed_height">
						<div class="row">
							<div class="col-md-4 prof_pic">
								<div class="prof_pic"><img src="https://buffer.com/library/wp-content/uploads/2015/03/adjust-tie-1024x683.jpeg" alt=""></div>
								<div class="prof_list">
									<ul>
										<li><a href="#section_one"><i class="fas fa-angle-right"></i> Basic Information </a><li>
										<li><a href="#section_two"><i class="fas fa-angle-right"></i> Personal Information </a><li>
										<li><a href="#section_three"><i class="fas fa-angle-right"></i> Relative Information </a><li>
										<li><a href="#section_four"><i class="fas fa-angle-right"></i> Employee Bank Details </a><li>
										<li><a href="#section_five"><i class="fas fa-angle-right"></i> Employee Salary Details </a></li>
									</ul>
								</div>								
							</div>
							<div class="col-md-8">
								
								<!-- basic information -->
								<div class="basic_info"id="section_one">
									<h3 class="info_title">Basic Information 
									<%-- <span>
										<a href="${pageContext.request.contextPath}/employeeEdit?empId=${empInfo.empId }" 
										data-popup="tooltip" title="" data-original-title="Edit"><i class="icon-pencil7"></i></a>
										<a href="${pageContext.request.contextPath}/showEmpGraphs?empId=${empInfo.empId}"
										data-original-title="Graphs & Reports"><i class="icon-history"></i></a>
									</span> --%></h3>
								
									<div class="row">
									
										<div class="col-md-4">
											<div class="profile_one">First Name : <span>${empInfo.firstName }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Middle Name : <span>${empInfo.empMidName }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Last Name : <span>${empInfo.surname }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Company : <span>${empInfo.companyName }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Location : <span>${empInfo.location }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Designation : <span>${empInfo.desingnation }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Department : <span>${empInfo.departName }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Contractor : <span>${empInfo.contractorName }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Emp Type : <span>${empInfo.empType }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Contact No. : <span>${empInfo.mobileNo1 }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Other Mobile No. : <span>${empInfo.mobileNo2 }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Emp Category (Access Role) : <span>${empInfo.empCategory }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">UAN Number : <span>${empInfo.uan }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">ESIC Number : <span>${empInfo.esicNo }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Aadhaar Number : <span>${empInfo.aadharNo }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">PAN Number :  <span>${empInfo.panCardNo }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">PF Number :  <span>${empInfo.pfNo }</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Accessible Location :  <span>${empInfo.acciessbleLocations }</span></div>
										</div>
										<div class="col-md-4">
										<c:set var="authority" value="NA"></c:set>
										<c:if test="${empInfo.authorityDesigType==0}">
											<c:set var="authority" value="Employee"></c:set>
										</c:if>
										<c:if test="${empInfo.authorityDesigType==1 }">
											<c:set var="authority" value="HOD"></c:set>
										</c:if>
										<c:if test="${empInfo.authorityDesigType==2 }">
											<c:set var="authority" value="HR"></c:set>
										</c:if>
											<div class="profile_one">Designation Type : <span>${authority}</span></div>
										</div>
										<div class="col-md-4">
											<div class="profile_one">Skill : <span>${empInfo.skill }</span></div>
										</div>
									</div>
									
									
								
								
							</div>
						</div>
					</div>
					
					<!-- basic information -->
					<div class="basic_info" id="section_two">
					<h3 class="info_title">Personal Information </h3>
					<div class="row">
						<div class="col-md-3"><div class="profile_one">Middle Name :  <span>${empInfo.middleName }</span></div></div>
						<c:set var="relation" value="NA"></c:set>
						<c:if test="${empInfo.middleNameRelation=='father'}">
							<c:set var="relation" value="Father"></c:set>
						</c:if>
						<c:if test="${empInfo.middleNameRelation=='husband'}">
							<c:set var="relation" value="Husband"></c:set>
						</c:if>
						<div class="col-md-3"><div class="profile_one">Relation : <span>${relation}</span></div></div>
						<div class="col-md-3"><div class="profile_one">Date of Birth : <span>${empInfo.dob }</span></div></div>
						<div class="col-md-3"><div class="profile_one">Gender : <span>${empInfo.gender }</span></div></div>
						
						<div class="col-md-3"><div class="profile_one">Marital Status :  <span>${empInfo.maritalStatus }</span></div></div>
						<div class="col-md-3"><div class="profile_one">Email Address : <span>${empInfo.email }</span></div></div>
						<div class="col-md-3"><div class="profile_one">Current Address : <span>${empInfo.address }</span></div></div>
						<div class="col-md-3"><div class="profile_one">Parmanent Address : <span>${empInfo.permanentAddress }</span></div></div>
						
						<div class="col-md-3"><div class="profile_one">Qualification : <span>${empInfo.empQualification }</span></div></div>
						<div class="col-md-3"><div class="profile_one">Name : <span>${empInfo.emerName }</span></div></div>
						<div class="col-md-3"><div class="profile_one">Emergency Contact 1 :  <span>${empInfo.emerContactNo1 }</span></div></div>
						<div class="col-md-3"><div class="profile_one">Emergency Contact 2 :  <span>${empInfo.emerContactNo2 }</span></div></div>
						
						<div class="col-md-3"><div class="profile_one">Emergency Person Address :  <span>${empInfo.emerContactAddr }</span></div></div>
						<div class="col-md-3"><div class="profile_one">Blood Group :  <span>${empInfo.bloodGroup }</span></div></div>
						<c:set var="size" value="NA"></c:set>
						<c:if test="${empInfo.uniformSize=='medium' }">
							<c:set var="size" value="MEDIUM"></c:set>
						</c:if>
						<c:if test="${empInfo.uniformSize=='large' }">
							<c:set var="size" value="LARGE"></c:set>
						</c:if>
						<c:if test="${empInfo.uniformSize=='xl' }">
							<c:set var="size" value="XL"></c:set>
						</c:if>
						<c:if test="${empInfo.uniformSize=='xxl' }">
							<c:set var="size" value="XXL"></c:set>
						</c:if>
						<c:if test="${empInfo.uniformSize=='xxxl' }">
							<c:set var="size" value="XXXL"></c:set>
						</c:if>
						<div class="col-md-3"><div class="profile_one">Uniform Size :  <span>${size}</span></div></div>
					</div>
				
				</div>
				
				<!-- basic information -->
				<div class="basic_info"id="section_three">
				<h3 class="info_title">Relative Information </h3>
				<div class="row">
					<div class="col-md-3"><div class="profile_one">Person Name :<span>${empInfo.name1}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Date of Birth :  <span>${empInfo.dob1}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Relation :  
					<span>${empInfo.relation1 == 'f' ? 'Father' : empInfo.relation1=='m' ? 'Mother' : empInfo.relation1=='s1' ? 'Spouse' : empInfo.relation1=='b' ? 'Brother' : empInfo.relation1=='s2' ? 'Sister' : empInfo.relation1=='s3' ? 'Son' :  empInfo.relation1=='d' ? 'Daughter' : ''}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Occupation :  <span>${empInfo.occupation1}</span></div></div>
					
					<div class="col-md-3"><div class="profile_one">Person Name :<span>${empInfo.name2}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Date of Birth :  <span>${empInfo.dob2}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Relation :  
					<span>${empInfo.relation2 == 'f' ? 'Father' : empInfo.relation2=='m' ? 'Mother' : empInfo.relation2=='s1' ? 'Spouse' : empInfo.relation2=='b' ? 'Brother' : empInfo.relation2=='s2' ? 'Sister' : empInfo.relation2=='s3' ? 'Son' :  empInfo.relation2=='d' ? 'Daughter' : ''}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Occupation :  <span>${empInfo.occupation2}</span></div></div>
					
					<div class="col-md-3"><div class="profile_one">Person Name :<span>${empInfo.name3}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Date of Birth :  <span>${empInfo.dob3}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Relation :  
					<span>${empInfo.relation3 == 'f' ? 'Father' : empInfo.relation3=='m' ? 'Mother' : empInfo.relation3=='s1' ? 'Spouse' : empInfo.relation3=='b' ? 'Brother' : empInfo.relation3=='s2' ? 'Sister' : empInfo.relation3=='s3' ? 'Son' :  empInfo.relation3=='d' ? 'Daughter' : ''}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Occupation :  <span>${empInfo.occupation3}</span></div></div>
					
					<div class="col-md-3"><div class="profile_one">Person Name :<span>${empInfo.name4}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Date of Birth :  <span>${empInfo.dob4}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Relation :  
					<span>${empInfo.relation4 == 'f' ? 'Father' : empInfo.relation4=='m' ? 'Mother' : empInfo.relation4=='s1' ? 'Spouse' : empInfo.relation4=='b' ? 'Brother' : empInfo.relation4=='s2' ? 'Sister' : empInfo.relation4=='s3' ? 'Son' :  empInfo.relation4=='d' ? 'Daughter' : ''}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Occupation :  <span>${empInfo.occupation4}</span></div></div>
					
					<div class="col-md-3"><div class="profile_one">Person Name :<span>${empInfo.name5}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Date of Birth :  <span>${empInfo.dob5}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Relation : 
					<span>${empInfo.relation5 == 'f' ? 'Father' : empInfo.relation5=='m' ? 'Mother' : empInfo.relation5=='s1' ? 'Spouse' : empInfo.relation5=='b' ? 'Brother' : empInfo.relation5=='s2' ? 'Sister' : empInfo.relation5=='s3' ? 'Son' :  empInfo.relation5=='d' ? 'Daughter' : ''}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Occupation :  <span>${empInfo.occupation5}</span></div></div>
					
				</div>
				</div>	
				
				<!-- Employee Bank Details -->
				<div class="basic_info" id="section_four">
				<h3 class="info_title">Employee Bank Details </h3>
				<div class="row">
					<div class="col-md-3"><div class="profile_one">Account No :<span>${empInfo.accNo}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Bank :  <span>${empInfo.bankName}</span></div></div>					
				</div>
				</div>
				
				<!-- Employee Salary Details -->
				<div class="basic_info" id="section_five">
				<h3 class="info_title">Employee Salary Details </h3>
				<div class="row">
					<div class="col-md-3"><div class="profile_one">Gross Salary Rs : <span>${empInfo.grossSalary}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Basic Rs :  <span>${empInfo.basic}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Society Contribution Rs. :   <span>${empInfo.societyContribution}</span></div></div>
					
					<c:set var="allwncTtl" value="0"></c:set>
					
					<c:forEach items="${empAllowncList}" var="allownce" varStatus="count">	
					
					<c:if test="${allownce.allowanceId==1}">
					<div class="col-md-3"><div class="profile_one">DA (0.0 %) :  <span>${allownce.allowanceValue}</span></div></div>
						<c:set var="allwncTtl" value="${allwncTtl + allownce.allowanceValue}"></c:set>
					</c:if> <c:if test="${allownce.allowanceId==5}">
					<div class="col-md-3"><div class="profile_one">FA (0.0 %) : <span>${allownce.allowanceValue}</span></div></div>
					<c:set var="allwncTtl" value="${allwncTtl + allownce.allowanceValue}"></c:set>
					</c:if>	<c:if test="${allownce.allowanceId==9}">
					<div class="col-md-3"><div class="profile_one">HRA (20.0 %) :  <span>${allownce.allowanceValue}</span></div></div>
					<c:set var="allwncTtl" value="${allwncTtl + allownce.allowanceValue}"></c:set>
					</c:if> <c:if test="${allownce.allowanceId==11}">
					<div class="col-md-3"><div class="profile_one">CA (0.0 %) :  <span>${allownce.allowanceValue}</span></div></div>
					<c:set var="allwncTtl" value="${allwncTtl + allownce.allowanceValue}"></c:set>
					</c:if> <c:if test="${allownce.allowanceId==14}">
					<div class="col-md-3"><div class="profile_one">EA (0.0 %) :  <span>${allownce.allowanceValue}</span></div></div>
					<c:set var="allwncTtl" value="${allwncTtl + allownce.allowanceValue}"></c:set>
					</c:if> <c:if test="${allownce.allowanceId==16}">
					<div class="col-md-3"><div class="profile_one">TA (0.0 %) : <span>${allownce.allowanceValue}</span></div></div>
					<c:set var="allwncTtl" value="${allwncTtl + allownce.allowanceValue}"></c:set>
					</c:if> <c:if test="${allownce.allowanceId==19}">
					<div class="col-md-3"><div class="profile_one">OTH (20.0 %) :  <span>${allownce.allowanceValue}</span></div></div>
					<c:set var="allwncTtl" value="${allwncTtl + allownce.allowanceValue}"></c:set>
					</c:if><c:if test="${allownce.allowanceId==173}">
					<div class="col-md-3"><div class="profile_one">MA (20.0 %) :  <span>${allownce.allowanceValue}</span></div></div>
					<c:set var="allwncTtl" value="${allwncTtl + allownce.allowanceValue}"></c:set>
					</c:if> 
					
					</c:forEach>
					
					
					
					<div class="col-md-3"><div class="profile_one">Total :  <span>${allwncTtl}</span></div></div>
					
					<div class="col-md-3"><div class="profile_one">PF Applicable :  <span>${empInfo.pfApplicable=='yes' ? 'YES' : 'NO' }</span></div></div>
					<div class="col-md-3"><div class="profile_one">PF Type :  <span>${empInfo.pfType}</span></div></div>
					<div class="col-md-3"><div class="profile_one">PF Employee Per :   <span>${empInfo.pfEmpPer}</span></div></div>
					<div class="col-md-3"><div class="profile_one">ESIC Applicable :  <span>${empInfo.esicApplicable=='yes' ? 'YES' : 'NO' }</span></div></div>
					
					<div class="col-md-3"><div class="profile_one">MLWF Applicable : <span>${empInfo.mlwfApplicable=='yes' ? 'YES' : 'NO' }</span></div></div>
					<div class="col-md-3"><div class="profile_one">PT Applicable :  <span>${empInfo.ptApplicable=='yes' ? 'YES' : 'NO' }</span></div></div>
					<div class="col-md-3"><div class="profile_one">Salary Basis :   <span>${empInfo.salBasis =='monthly' ? 'Monthly' : 'Daily'}</span></div></div>
					<div class="col-md-3"><div class="profile_one">EPF Joining Date :  <span>${empInfo.epfJoiningDate}</span></div></div>
					
					<div class="col-md-3"><div class="profile_one">Joining Date : <span>${empInfo.cmpJoiningDate}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Leaving Date :  <span>${empInfo.leavingReason}</span></div></div>
					<div class="col-md-3"><div class="profile_one">Leaving Reason :  <span>${empInfo.cmpLeavingDate}</span></div></div>
					<div class="col-md-3"><div class="profile_one">LR For ESIC :  <span>
					${empInfo.leavingReasonEsic==0 ? 'Without Reason' : empInfo.leavingReasonEsic==1 ? 'On Leave' : empInfo.leavingReasonEsic==2 ? 'Self Service' : 
					 empInfo.leavingReasonEsic==3 ? 'Retired' :  empInfo.leavingReasonEsic==4 ? 'Out of Coverage' :  empInfo.leavingReasonEsic==5 ? 'Expired' :
					  empInfo.leavingReasonEsic==6 ? 'Non Implemented Area' :  empInfo.leavingReasonEsic==7 ? 'Compliance by immediate Employer' : 
					  empInfo.leavingReasonEsic==8 ? 'Suspension Reason' : empInfo.leavingReasonEsic==9 ? 'Strike/Lockout' :
					  empInfo.leavingReasonEsic==10 ? 'Retrenchment' : empInfo.leavingReasonEsic==11 ? 'No Work' :
					  empInfo.leavingReasonEsic==12 ? 'Does not belong to this Employer' : ''}
					</span></div></div>
					
					<div class="col-md-3"><div class="profile_one">LR For PF : <span>
					${empInfo.leavingReasonPf == 1 ? 'Cessation' : empInfo.leavingReasonPf == 2 ? 'Superannuation' : empInfo.leavingReasonPf == 3 ? 'Retirement' : 
					empInfo.leavingReasonPf == 4 ? 'Death in Service' :  empInfo.leavingReasonPf == 5 ? 'Permanent Disablement' : ''}
					</span></div></div>
							
				</div>
				</div>
					

				</div>
				<!-- /highlighting rows and columns -->

			</div>
			<!-- /content area -->

 

		</div>
		<!-- /main content -->

	 
	 
	<!-- /page content -->
	
	<script type="text/javascript">
$(document).ready(function(){
  // Add smooth scrolling to all links
  $("a").on('click', function(event) {

    // Make sure this.hash has a value before overriding default behavior
    if (this.hash !== "") {
      // Prevent default anchor click behavior
      event.preventDefault();

      // Store hash
      var hash = this.hash;

      // Using jQuery's animate() method to add smooth page scroll
      // The optional number (800) specifies the number of milliseconds it takes to scroll to the specified area
      $('html, body').animate({
        scrollTop: $(hash).offset().top
      }, 800, function(){
   
        // Add hash (#) to URL when done scrolling (default click behavior)
        window.location.hash = hash;
      });
    } // End if
  });
});
</script>

</body>
</html>