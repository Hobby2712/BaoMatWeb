<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
    <div id="logreg-forms">
        <form class="form-signin" action="${action}" method="post">
            <p></p>
            <h1 class="h3 mb-3 font-weight-normal" style="text-align: center">
                Verify Account</h1>
            <p></p>
            <p>Mã OTP đã được gửi đến ${email}</br>
                Vui lòng nhậP mã OTP gồm 6 chữ số.
            </p>
            <input name="csrf_token" type="hidden" value="<%=session.getAttribute("csrf_token")%>">
            <input name="user" type="hidden" value="${user}">
            <input name="pass" type="hidden" value="${pass}">
            <input name="email" id="email" type="hidden" value="${email}">
            <input name="otpSend" id="otpSend" type="hidden" value="${otpSend}">
            <input name="otp" type="text" id="otp" class="form-control" placeholder="Nhập OTP" required="" autofocus="">
            <p class="text-danger" id="mess">${mess}</p>
            <p></p>
            <div class="row">
                <div class="col-lg-6" style="margin-top: 5px;">
                    <a onclick="window.location.reload()" style="cursor: pointer;">Gửi lại?</a>
                </div>
                <div class="col-lg-6 float-right">
                    <input onclick="location.href='${cancel}';" type="button" class="btn btn-default"
                        style="background-color: #e79393; margin-left: 15px;" value="Cancel">
                    <input onclick="addCsrfTokenToForm(event)" class="btn btn-success" value="Continue">
                </div>
            </div>
        </form>

    </div>
</body>