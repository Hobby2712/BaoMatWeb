<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<body>
    <div id="logreg-forms">
        <form class="form-signin" action="changePassForgot" method="post">
            <p></p>
            <h1 class="h3 mb-3 font-weight-normal" style="text-align: center">
                Change Password</h1>
            <p></p>
            <p style="margin-bottom: 3px">
                Nhập mật khẩu mới
            </p>
            <input id="csrfTokenInput" name="csrf_token" type="hidden">
            <input name="username_email" type="hidden" value="${username_email}">
            <input name="pass" type="password" id="user-pass" class="form-control" placeholder="New Password" required
                autofocus="">
            <div class="float-right" style="margin-top:5px;">
                <input onclick="location.href='/Web/loginAccount';" type="button" class="btn btn-default"
                    style="background-color: #e79393; margin-left: 15px;" value="Back">
                <input onclick="addCsrfTokenToForm(event)" class="btn btn-success" value="Continue">
            </div>
        </br></br>
        </form>

    </div>
</body>