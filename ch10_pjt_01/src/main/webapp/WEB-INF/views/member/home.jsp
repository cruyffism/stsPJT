<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ page session="false"%> --%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h3>MEMBER HOME</h3>

	<c:choose>
		<c:when test="${cookie.loginMember.value eq null}"> <%--왼족 조건이 true이면 로그인 주소로 아니면 로그아웃 주소로 --%>
			<a href="<c:url value='/member/loginForm'/>">로그인</a>
		</c:when>
		<c:otherwise>
			<a href="<c:url value='/member/logoutForm'/>">로그아웃</a>
		</c:otherwise>
	</c:choose>
</body>
</html>
