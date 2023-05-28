<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Ogani Template">
    <meta name="keywords" content="Ogani, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Ogani | Template</title>

    <link rel="stylesheet" href="<c:url value="/template/css/login.css" />" type="text/css">
</head>
<body>
	<div id="logreg-forms">
		<form class="form-signin" action="login" method="post">
			<h1 class="h3 mb-3 font-weight-normal" style="text-align: center">
				Sign in</h1>
			<p class="text-danger" style="text-align: center;">${mess}</p>
			<input id="csrfTokenInput" name="csrf_token" type="hidden" value="<%=session.getAttribute("csrf_token")%>">
			<input name="user" type="text" id="inputEmail" class="form-control"
				placeholder="Username" required="" autofocus=""> <input
				name="pass" type="password" id="inputPassword" class="form-control"
				placeholder="Password" required="">

			<div class="form-group form-check">
				<input name="remember" value="true" type="checkbox"
					class="form-check-input" id="exampleCheck1"> <label
					class="form-check-label" for="exampleCheck1">Remember me</label>
			</div>

			<button class="btn btn-success btn-block" type="submit">
				<i class="fa fa-sign-in"></i> Sign in
			</button>
			<hr>
			<button onclick="location.href='/Web/signUpAccount';" class="btn btn-primary btn-block" type="button">
				<i class="fa fa-user-plus"></i> Sign up New Account
			</button>
			<p></p>
			<a class="forgot-pass" href="/Web/forgotAccountPass"> Forgot password?</a>
		</form>


	</div>
</body>
</html>