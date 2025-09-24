<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute( "newLine", "\n" ); %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="contents-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${boardVo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
							${fn:replace(boardVo.contents, newLine, "<br>") }					
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?p=${param.p }&kwd=${param.kwd }">글목록</a>
					<sec:authorize access="isAuthenticated()">
						<a href="${pageContext.request.contextPath }/board/reply/${boardVo.id }?p=${param.p }&kwd=${param.kwd }">답글 달기</a>
						<sec:authentication property="principal" var="authUser"/>
						<c:if test="${authUser.id == boardVo.userId }">
							<a href="${pageContext.request.contextPath }/board/modify/${boardVo.id }?p=${param.p }&kwd=${param.kwd }">글수정</a>
						</c:if>
					</sec:authorize>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
