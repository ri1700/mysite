<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<div id="header">
			<h1>관리자페이지</h1>
			<ul>
				<li><a href="${pageContext.request.contextPath }">사이트 메인</a><li>
				<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a><li>
			</ul>
		</div>