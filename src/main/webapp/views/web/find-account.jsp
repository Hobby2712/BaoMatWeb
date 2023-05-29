<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<body>
    <div id="logreg-forms">
        <form class="form-signin" action="/Web/findAccount" method="post">
            <p></p>
            <h1 class="h3 mb-3 font-weight-normal" style="text-align: center">
                Find Your Account</h1>
            <p></p>
            <p style="margin-bottom: 3px">
                Nhập Email hoặc Username để tìm tài khoản
            </p>
            <input id="csrfTokenInput" name="csrf_token" type="hidden">
            <input name="user" type="hidden">
            <input name="pass" type="hidden">
            <input name="email" type="hidden">
            <input name="username_email" type="text" id="user-pass" class="form-control" placeholder="Nhập Username hoặc Email..." required
                autofocus="">
            </br>
            <p class="text-danger" id="mess">${mess}</p>
            <div class="float-right" style="margin-top:5px;">
                <input onclick="location.href='/Web/loginAccount';" type="button" class="btn btn-default"
                    style="background-color: #e79393; margin-left: 15px;" value="Cancel">
                <input onclick="addCsrfTokenToForm(event)" class="btn btn-success" value="Continue">
            </div>
        </br></br>
        </form>

    </div>
</body>