<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Page Preloder -->
<div id="preloder">
	<div class="loader"></div>
</div>

<!-- Humberger Begin -->
<div class="humberger__menu__overlay"></div>
<div class="humberger__menu__wrapper">
	<div class="humberger__menu__logo">
		<a href="/home"><img
			src="<c:url value="/template/img/logo1.png"/>" alt=""></a>
	</div>
	<c:if test="${sessionScope.acc.role == 2}">
		<div class="humberger__menu__cart">
			<ul>
				<!-- <li><a href="#"><i class="fa fa-heart"></i> <span>1</span></a></li> -->
				<li><a href="/Web/cart"><i class="fa fa-shopping-bag"></i>
						<span>${countC}</span></a></li>
			</ul>
			<!-- <div class="header__cart__price">
				item: <span>$150.00</span>
			</div> -->
		</div>
	</c:if>
	<div class="humberger__menu__widget">
		<div class="header__top__right__language">
			<img src="<c:url value="/template/img/language.png"/>" alt="">
			<div>English</div>
			<span class="arrow_carrot-down"></span>
			<ul>
				<li><a href="#">Spanis</a></li>
				<li><a href="#">English</a></li>
			</ul>
		</div>
		<div class="header__top__right__auth">
			<c:if test="${sessionScope.acc != null}">
				<a href="#"><i class="fa fa-user"></i>sessionScope.acc.userName</a>
				<ul class="header__menu__dropdown">
					<li><a href="<c:url value="profile"/>">Profile</a></li>
					<li><a href="<c:url value="logout"/>">LogOut</a></li>
				</ul>
			</c:if>
			<c:if test="${sessionScope.acc == null}">
				<a href="<c:url value="/loginAccount"/>"><i class="fa fa-user"></i>
					Login</a>
			</c:if>
		</div>
	</div>
	<nav class="humberger__menu__nav mobile-menu">
		<ul>
			<li><a href="<c:url value="/home"/>">Home</a></li>
			<li><a href="/Web/shop?index=1">Sản phẩm</a>
				<ul class="header__menu__dropdown">
					<c:forEach items="${cList}" var="lcc">
						<li><a href="category?cid=${lcc.id}">${lcc.name}</a></li>
					</c:forEach>
				</ul></li>
			<li><a href="<c:url value="/order"/>">Đơn mua</a></li>
			<li><a href="<c:url value="/contact"/>">Contact</a></li>
		</ul>
	</nav>
	<div id="mobile-menu-wrap"></div>
	<div class="header__top__right__social">
		<a href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i
			class="fa fa-twitter"></i></a> <a href="#"><i class="fa fa-linkedin"></i></a>
		<a href="#"><i class="fa fa-pinterest-p"></i></a>
	</div>
	<div class="humberger__menu__contact">
		<ul>
			<li><i class="fa fa-envelope"></i> nhom15@gmail.com</li>
			<li>Free Shipping for all Order</li>
		</ul>
	</div>
</div>
<!-- Humberger End -->

<!-- Header Section Begin -->
<header class="header">
	<div class="header__top">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<div class="header__top__left">
						<ul>
							<li><i class="fa fa-envelope"></i> nhom11.com</li>
							<li>Free Shipping for all Order</li>
						</ul>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="header__top__right">
						<div class="header__top__right__social">
							<a href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i
								class="fa fa-twitter"></i></a> <a href="#"><i
								class="fa fa-linkedin"></i></a> <a href="#"><i
								class="fa fa-pinterest-p"></i></a>
						</div>
						<div class="header__top__right__language">
							<img src="<c:url value="/template/img/language.png"/>" alt="">
							<div>English</div>
							<span class="arrow_carrot-down"></span>
							<ul>
								<li><a href="#">Spanis</a></li>
								<li><a href="#">English</a></li>
							</ul>
						</div>
						<div class="header__top__right__auth">
							<c:if test="${sessionScope.acc.role == 2}">
								<a href="#"><i class="fa fa-user"></i>${sessionScope.acc.userName}</a>
								<ul class="header__menu__dropdown">
									<li><a href="<c:url value="/profile"/>">Profile</a></li>
									<li><a href="<c:url value="/logout"/>">LogOut</a></li>
								</ul>
							</c:if>
							<c:if test="${sessionScope.acc == null}">
								<a href="<c:url value="/loginAccount"/>"><i
									class="fa fa-user"></i> Login</a>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-lg-3">
				<div class="header__logo">
					<a href="<c:url value="/home"/>"><img
						src="<c:url value="/template/img/logo1.png"/>" alt=""></a>
				</div>
			</div>
			<div class="col-lg-6">
				<nav class="header__menu">
					<ul>
						<li><a href="<c:url value="/home"/>">Home</a></li>
						<li><a href="/Web/shop?index=1">Sản phẩm</a>
							<ul class="header__menu__dropdown">
								<c:forEach items="${cList}" var="lcc">
									<li><a href="category?cid=${lcc.id}">${lcc.name}</a></li>
								</c:forEach>
							</ul></li>
						<li><a href="<c:url value="/order"/>">Đơn mua</a></li>
						<li><a href="<c:url value="/contact"/>">Contact</a></li>
					</ul>
				</nav>
			</div>
			<c:if test="${sessionScope.acc.role == 2}">
				<div class="col-lg-3">
					<div class="header__cart">
						<ul>
							<li><a href="/Web/cart"><i class="fa fa-shopping-bag"></i>
									<span>${countC}</span></a></li>
						</ul>
						<!-- <div class="header__cart__price">
							item: <span>$150.00</span>
						</div> -->
					</div>
				</div>
			</c:if>
		</div>
		<div class="humberger__open">
			<i class="fa fa-bars"></i>
		</div>
	</div>
</header>
<!-- Header Section End -->

<!-- Hero Section Begin -->
<section class="hero hero-normal">
	<div class="container">
		<div class="row">
			<div class="col-lg-3">
				<div class="hero__categories">
					<div class="hero__categories__all">
						<i class="fa fa-bars"></i> <span>All departments</span>
					</div>
					<ul>
						<c:forEach items="${cList2}" var="c2">
							<li><a href="category?cid2=${c2.id}">${c2.name}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="col-lg-9">
				<div class="hero__search">
					<div class="hero__search__form">
						<form action="search" method="post">
							<input id="csrfTokenInput" name="csrf_token" type="hidden">
							<input name="txt" type="text" placeholder="What do you need?">
							<button onclick="addCsrfTokenToForm(event)" class="site-btn">SEARCH</button>
						</form>
					</div>
					<div class="hero__search__phone">
						<div class="hero__search__phone__icon">
							<i class="fa fa-phone"></i>
						</div>
						<div class="hero__search__phone__text">
							<h5>0942.430.101</h5>
							<span>Hỗ trợ 24/7</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Hero Section End -->