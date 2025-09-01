<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="header"></div>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<tr>
						<td>7</td>
						<td style="text-align:left; padding-left:${(1-3) * 20}px"><a
							href="">내일은 주말</a></td>
						<td>박준성</td>
						<td>3</td>
						<td>2025-08-29 17:24:32</td>
						<td><a href="" class="del"
							style='background:url("${pageContext.request.contextPath }/assets/images/recycle.png") no-repeat 0 0;'>삭제</a>
						</td>
					</tr>
					<tr>
						<td>6</td>
						<td style="text-align:left; padding-left:${(2-3) * 20}px"><img
							src="${pageContext.request.contextPath }/assets/images/reply.png">
							<a href="">ㅅㅅ</a></td>
						<td>누구게</td>
						<td>3</td>
						<td>2025-08-29 17:25:32</td>
						<td><a href="" class="del"
							style='background:url("${pageContext.request.contextPath }/assets/images/recycle.png") no-repeat 0 0;'>삭제</a>
						</td>
					</tr>
					<tr>
						<td>5</td>
						<td style="text-align:left; padding-left:${(1-2) * 20}px"><a
							href="">ㅎㅇ</a></td>
						<td>박준성</td>
						<td>3</td>
						<td>2024-09-25 07:24:32</td>
						<td><a href="" class="del"
							style='background:url("${pageContext.request.contextPath }/assets/images/recycle.png") no-repeat 0 0;'>삭제</a>
						</td>
					</tr>
					<tr>
						<td>4</td>
						<td style="text-align:left; padding-left:${(2-2) * 20}px"><img
							src="${pageContext.request.contextPath }/assets/images/reply.png">
							<a href="">어서오고</a></td>
						<td>박준성</td>
						<td>3</td>
						<td>2024-09-25 07:26:32</td>
						<td><a href="" class="del"
							style='background:url("${pageContext.request.contextPath }/assets/images/recycle.png") no-repeat 0 0;'>삭제</a>
						</td>
					</tr>
					<tr>
						<td>3</td>
						<td style="text-align:left; padding-left:${(1-1) * 20}px"><a
							href="">저녁 뭐 먹지?</a></td>
						<td>박준성</td>
						<td>3</td>
						<td>2015-10-11 12:04:20</td>
						<td><a href="" class="del"
							style='background:url("${pageContext.request.contextPath }/assets/images/recycle.png") no-repeat 0 0;'>삭제</a>
						</td>
					</tr>
					<tr>
						<td>2</td>
						<td style="text-align:left; padding-left:${(2-1) * 20}px"><img
							src="${pageContext.request.contextPath }/assets/images/reply.png">
							<a href="">라면?</a></td>
						<td>박준성</td>
						<td>3</td>
						<td>2015-10-02 12:04:12</td>
						<td><a href="" class="del"
							style='background:url("${pageContext.request.contextPath }/assets/images/recycle.png") no-repeat 0 0;'>삭제</a>
						</td>
					</tr>
					<tr>
						<td>1</td>
						<td style="text-align:left; padding-left:${(2-1) * 20}px"><img
							src="${pageContext.request.contextPath }/assets/images/reply.png">
							<a href="">ㅗ</a></td>
						<td>박준성</td>
						<td>3</td>
						<td>2015-09-25 07:24:32</td>
						<td><a href="" class="del"
							style='background:url("${pageContext.request.contextPath }/assets/images/recycle.png") no-repeat 0 0;'>삭제</a>
						</td>
					</tr>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li class="selected">1</li>
						<li><a href="">2</a></li>
						<li><a href="">3</a></li>
						<li><a href="">4</a></li>
						<li><a href="">5</a></li>
						<li><a href="">▶</a></li>
					</ul>
				</div>
				<!-- pager 추가 -->
				<c:url var="writeUrl" value="/board">
					<c:param name="a" value="write" />
				</c:url>
				<div class="bottom">
					<a href="${pageContext.request.contextPath}/board?a=write">글쓰기</a>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>