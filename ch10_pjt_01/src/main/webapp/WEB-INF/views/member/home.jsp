<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h3>MEMBER HOME</h3>

	<c:choose>
		<c:when test="${cookie.loginMember.value eq null}">
			<a href="<c:url value='/member/LoginForm'/>">로그인</a>
		</c:when>
		<c:otherwise>
			<a href="<c:url value='/member/LogoutForm'/>">로그아웃</a>
		</c:otherwise>
	</c:choose>
</body>
</html>
