<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function() {
	$("#check-button").click(function() {
		var email = $("#email").val();
		if(!email) {
			return;
		}
		
		$.ajax({
			url: "/mysite03/api/user/checkemail?email=" + email,
			type: "get",
			dataType: "json",
			success: function(response) {
				if(response.exist){
					alert("이메일이 존재합니다. 다른 이메일을 사용해 주세요.");
					$("#email").val("");
					$("#email").focus();
					return;
				}
				
				$("#check-img").show();
				$("#check-button").hide();
			}
		});
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">
				<form:form
					modelAttribute="userVo"
					id="join-form"
					name="joinForm"
					method="post"
					action="${pageContext.request.contextPath }/user/join">
					
					<label class="block-label" for="name"><spring:message code="user.join.label.name"/></label>
					<input id="name" name="name" type="text" value="${userVo.name }">
					<p style="padding: 0; text-align:left; color: #f00">
						<spring:hasBindErrors name="userVo">
							<c:if test='${errors.hasFieldErrors("name") }'>
								<spring:message code='${errors.getFieldError("name").codes[0] }' />
							</c:if>
						</spring:hasBindErrors>
					</p>

					<spring:message code="user.join.label.email.check" var="userJoinLabelEmailCheck" />
					<label class="block-label" for="email"><spring:message code="user.join.label.email"/></label>
					<form:input path="email" />
					<img id="check-img" src="${pageContext.request.contextPath }/assets/images/check.png" style="vertical-align:bottom; width:24px; display: none">
					<input id="check-button" type="button" value="${userJoinLabelEmailCheck }">
					<p style="padding: 0; text-align:left; color: #f00">
						<form:errors path="email" />
					</p>
					
					<label class="block-label"><spring:message code="user.join.label.password"/></label>
					<form:password path="password" />
					<p style="padding: 0; text-align:left; color: #f00">
						<form:errors path="password" />
					</p>
					
					<spring:message code="user.join.label.gender.male" var="userJoinLabelGenderMale" />
					<spring:message code="user.join.label.gender.female" var="userJoinLabelGenderFemale" />
					<fieldset>
						<legend>성별</legend>
						<form:radiobutton path="gender" value="female" label="${userJoinLabelGenderFemale }" checked="checked" />
						<form:radiobutton path="gender" value="male" label="${userJoinLabelGenderMale }" />
					</fieldset>
					
					<fieldset>
						<legend><spring:message code="user.join.label.terms"/></legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label><spring:message code="user.join.label.terms.message"/></label>
					</fieldset>
					
					<spring:message code="user.join.button.signup" var="userJoinButtonSignup" />
					<input type="submit" value="${userJoinButtonSignup }">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>

</html>