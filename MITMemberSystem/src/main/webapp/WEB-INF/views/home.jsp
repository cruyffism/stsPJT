<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<style>
.container {
	display: flex;
	flex-direction: row; /* 가로 배치*/
}

form {
	margin: 0 5px; /* 폼끼리 간격 설정 */
}
</style>
</head>
<body>

	<h1>Welcome!</h1>
	<div class=container>
		<form action="/member/signUp">
			<input type="submit" value="Register">
		</form>
		<form action="/member/signIn">
			<input type="submit" value="LOGIN">
		</form>
	</div>
	<P>The time on the server is ${serverTime}.</P>

</body>
</html>
