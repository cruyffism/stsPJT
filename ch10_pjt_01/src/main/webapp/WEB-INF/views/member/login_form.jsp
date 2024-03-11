<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>MEMBER LOGIN FORM</h3>

	<form action="<c:url value='/member/loginConfirm'/>" method="post">
		<imput type="text" name="m_id"><br>
		<imput type="password" name="m_pw"><br>
		<imput type="submit" name="login">
		<imput type="reset" name="reset">
	</form>

</body>
</html>