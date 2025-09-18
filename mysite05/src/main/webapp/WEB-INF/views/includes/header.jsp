<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
window.addEventListener("load", function(){
	var anchors = document.querySelectorAll('#languages a');
	
	anchors.forEach(function(el){
		el.addEventListener("click", function(e) {
			e.preventDefault();
			document.cookie = 
				"lang=" + this.getAttribute("data-lang") + ";" + 
				"path=" + "${pageContext.request.contextPath}" + ";" + 
				"max-age=" + (30*24*60*60);
			location.reload();	
		});
	});
	
});
</script>

		<div id="header">
			<!--h1>${siteVo.title }</h1-->
			<h1>${site.title }</h1>
			<div id="languages">
				<c:choose>
					<c:when test='${lang == "ko"}'>
						<a href="" data-lang="ko" class="active">KO</a><a href="" data-lang="en">EN</a>
					</c:when>
					<c:otherwise>
						<a href="" data-lang="ko">KO</a><a href="" data-lang="en" class="active">EN</a>
					</c:otherwise>
				</c:choose>
			</div>			
			<ul>
				<c:choose>
					<c:when test="${empty authUser }" >
						<li><a href="${pageContext.request.contextPath }/user/login">로그인</a></li>
						<li><a href="${pageContext.request.contextPath }/user/join">회원가입</a></li>
					</c:when>
					<c:otherwise>			
						<li><a href="${pageContext.request.contextPath }/user/update">회원정보수정</a></li>
						<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a></li>
						<li>${authUser.name }님 안녕하세요 ^^;</li>
					</c:otherwise>
				</c:choose>	
			</ul>
		</div>

