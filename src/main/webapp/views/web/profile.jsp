<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="<c:url value="/template/css/login.css" />"
	type="text/css">

<body>
	<!-- Checkout Section Begin -->
	<section class="checkout spad" style="padding-top: 20px !important">
		<div class="container">
			<div class="checkout__form">
				<form action="#">
					<div class="row">
						<div class="profile-nav col-md-3">
							<div class="panel">
								<div class="user-heading round">
									<a href="#"> <c:if test="${sessionScope.acc.image == null}">
											<img
												src="https://st3.depositphotos.com/1767687/16607/v/450/depositphotos_166074422-stock-illustration-default-avatar-profile-icon-grey.jpg"
												alt="">
										</c:if> <c:if test="${sessionScope.acc.image != null}">
											<img
												src="<c:url value="/image?fname=${sessionScope.acc.image}"/>"
												alt="">
										</c:if>
									</a>
									<h1>${sessionScope.acc.fullName}</h1>
									<p>${sessionScope.acc.email}</p>
								</div>

								<ul class="nav nav-pills flex-column">
									<li><a href="#" class="item_profile active"> <i
											class="fa fa-user" id="icon"></i> Profile
									</a></li>
									<li><a href="#" class="item_profile"> <i
											class="fa fa-edit" id="icon"></i> Edit profile
									</a></li>
									<li><a href="#" class="item_profile"> <i
											class="fa fa-key" id="icon"></i> Change PassWord
									</a></li>
								</ul>
							</div>
						</div>
						<div class="col-lg-8 col-md-6">
							<form></form>
							<form class="form_profile active">
								<div class="panel">
									<div class="bio-graph-heading">Thông tin cá nhân</div>
									<div class="panel-body bio-graph-info">
										<div class="row">
											<div class="bio-row">
												<p>
													<span>Name </span>: ${sessionScope.acc.fullName}
												</p>
											</div>
											<div class="bio-row">
												<p>
													<span>Adress </span>: ${sessionScope.acc.address}
												</p>
											</div>
											<div class="bio-row">
												<p>
													<span>Email </span>: ${sessionScope.acc.email}
												</p>
											</div>
											<div class="bio-row">
												<p>
													<span>Phone </span>: ${sessionScope.acc.phone}
												</p>
											</div>
										</div>
									</div>
								</div>
							</form>
							<form class="form_profile" action="/Web/editProfile">
								<div class="bio-graph-heading">Chỉnh sửa hồ sơ</div>
								<div class="checkout__input">
									<p style="margin-bottom: 3px">
										Name<span></span>
									</p>
									<input type="text" value="${sessionScope.acc.fullName}"
										name="name" required maxlength="200">
								</div>
								<div class="checkout__input">
									<p style="margin-bottom: 3px">
										Phone<span></span>
									</p>
									<input type="text" value="${sessionScope.acc.phone}"
										name="phone" required maxlength="11">
								</div>
								<div class="checkout__input">
									<p style="margin-bottom: 3px">
										Address<span></span>
									</p>
									<input type="text" value="${sessionScope.acc.address}"
										name="address" required maxlength="200">
								</div>
								<div class="row justify-content-end" style="padding: 15px">
									<input type="submit" class="btn btn-success" value="Chỉnh sửa">
								</div>
							</form>
							<form class="form_profile" action="/Web/changePassword">
								<div class="bio-graph-heading">Đổi mật khẩu</div>
								<div class="checkout__input">
									<p style="margin-bottom: 3px">
										Old PassWord<span>*</span>
									</p>
									<input name="oldPass" type="password" required="">
								</div>
								<div class="checkout__input">
									<p style="margin-bottom: 3px">
										New PassWord<span>*</span>
									</p>
									<input name="newPass" type="password" required="">
								</div>
								<div class="checkout__input">
									<p style="margin-bottom: 3px">
										Repeat New PassWord<span>*</span>
									</p>
									<input name="repeatNewPass" type="password" required="">
								</div>
								<div class="row justify-content-end" style="padding: 15px">
									<input type="submit" class="btn btn-success" value="Thay đổi">
								</div>
							</form>
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>
	<!-- Checkout Section End -->
	<!-- Js Plugins -->
	<script>
        const $ = document.querySelector.bind(document);
        const $$ = document.querySelectorAll.bind(document);

        const tabs = $$(".item_profile");
        const panes = $$(".form_profile");
		
        $(".item_profile.active").classList.add("active");
        console.log(tabs,panes)
        tabs.forEach((tab, index) => {
            const pane = panes[index];

            tab.onclick = function () {
                $(".item_profile.active").classList.remove("active");
                $(".form_profile.active").classList.remove("active");
                this.classList.add("active");
                pane.classList.add("active");
            }
            
        })
    </script>
</body>
</html>