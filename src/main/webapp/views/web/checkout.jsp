<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
	<!-- Breadcrumb Section Begin -->
	<section class="breadcrumb-section set-bg"
		data-setbg="<c:url value="/template/img/breadcrumb.jpg"/>">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<div class="breadcrumb__text">
						<h2>Checkout</h2>
						<div class="breadcrumb__option">
							<a href="<c:url value="/views/web/index.jsp"/>">Home</a> <span>Checkout</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Breadcrumb Section End -->

	<!-- Checkout Section Begin -->
	<section class="checkout spad">
		<div class="container">
			<div class="checkout__form">
				<h4>Billing Details</h4>
				<form action="addOrder" method="post">
					<div class="row">
						<div class="col-lg-8 col-md-6">
							<input name="csrf_token" type="hidden" value="<%=session.getAttribute("csrf_token")%>">
							<div class="checkout__input">
								<p>
									Name<span>*</span>
								</p>
								<input type="text" placeholder="Name" name="name" value="${sessionScope.acc.fullName}"
									class="checkout__input__add">
							</div>
							<div class="checkout__input">
								<p>
									Address<span>*</span>
								</p>
								<input type="text" placeholder="Address" name="address" value="${sessionScope.acc.address}"
									class="checkout__input__add">
							</div>
							<div class="checkout__input">
								<p>
									Phone<span>*</span>
								</p>
								<input type="text" placeholder="Phone" name="phone" value="${sessionScope.acc.phone}"
									class="checkout__input__add">
							</div>
						</div>
						<div class="col-lg-4 col-md-6">
							<div class="checkout__order">
								<h4>Your Order</h4>
								<div class="checkout__order__products">
									Products <span>Total</span>
								</div>
								<ul>
									<c:forEach items="${cartList}" var="cart">
										<li style="font-size:70%;">${cart.p.name} x${cart.count}<span>${cart.p.tienTe(cart.totalprice)}</span></li>
									</c:forEach>
								</ul>
								<div class="checkout__order__total">
									Total <span>${totalP}</span>
								</div>
								<div class="checkout__input__checkbox">
									<label for="payment"> Thanh toán khi nhận hàng
									</label>
								</div>
								<button onclick="addCsrfTokenToForm(event)" class="site-btn">PLACE ORDER</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>
	<!-- Checkout Section End -->

</body>

</html>