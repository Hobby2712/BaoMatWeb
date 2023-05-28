<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="dec"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>NKD Shop</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">


<!-- Customized Bootstrap Stylesheet -->
<link rel="stylesheet"
	href="<c:url value="/template/css/bootstrap.min.css" />"
	type="text/css">
<link rel="stylesheet"
	href="<c:url value="/template/css/font-awesome.min.css" />"
	type="text/css">
<link rel="stylesheet"
	href="<c:url value="/template/css/elegant-icons.css" />"
	type="text/css">
<link rel="stylesheet"
	href="<c:url value="/template/css/nice-select.css" />" type="text/css">
<link rel="stylesheet"
	href="<c:url value="/template/css/jquery-ui.min.css" />"
	type="text/css">
<link rel="stylesheet"
	href="<c:url value="/template/css/owl.carousel.min.css" />"
	type="text/css">
<link rel="stylesheet"
	href="<c:url value="/template/css/slicknav.min.css" />" type="text/css">
<link rel="stylesheet" href="<c:url value="/template/css/style.css" />"
	type="text/css">
<link rel="stylesheet" href="<c:url value="/template/css/admin.css" />"
	type="text/css">

</head>
<!-- Spinner Start -->
<div id="spinner"
	class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
	<div class="spinner-border text-primary"
		style="width: 3rem; height: 3rem;" role="status">
		<span class="sr-only">Loading...</span>
	</div>
</div>
<!-- Spinner End -->


<!-- Sidebar Start -->
<div class="sidebar pe-4 pb-3">
	<a href="index.html" class="navbar-brand mx-4 mb-3"> <img
		src="<c:url value="/template/img/logo1.png"/>" alt="">
	</a>
	<nav class="navbar bg-white navbar-light">
		<div class="navbar-nav w-100">
			<a href="/Web/shipper/homeShipper" class="nav-item nav-link"><i
				class="fa fa-file-text me-2" style="margin-right: 10px !important;"></i>Tất
				cả</a> <a href="/Web/shipper/homeShipper?status=6"
				class="nav-item nav-link"><i class="fa fa-file-text me-2"
				style="margin-right: 10px !important;"></i>Chờ nhận đơn</a> <a
				href="/Web/shipper/homeShipper?status=2" class="nav-item nav-link"><i
				class="fa fa-file-text me-2" style="margin-right: 10px !important;"></i>Chờ
				lấy hàng</a> <a href="/Web/shipper/homeShipper?status=3"
				class="nav-item nav-link"><i class="fa fa-file-text me-2"
				style="margin-right: 10px !important;"></i>Chờ đi giao</a> <a
				href="/Web/shipper/homeShipper?status=4" class="nav-item nav-link"><i
				class="fa fa-file-text me-2" style="margin-right: 10px !important;"></i>Đã
				giao</a> <a href="/Web/shipper/homeShipper?status=7"
				class="nav-item nav-link"><i class="fa fa-file-text me-2"
				style="margin-right: 10px !important;"></i>Trả hàng</a> <a
				href="/Web/logout" class="nav-item nav-link"><i
				class="fa fa-sign-out me-2" style="margin-right: 10px !important;"></i>LogOut</a>
		</div>
	</nav>
</div>
<!-- Content Start -->
<div class="content" style="background-color: #fff;">
	<nav
		class="navbar navbar-expand bg-white navbar-light sticky-top px-4 py-0"
		style="padding: 5px !important;">
		<a href="#" class="sidebar-toggler flex-shrink-0"> <i
			class="fa fa-bars"></i>
		</a>
	</nav>
	<!-- Navbar End -->
	<!-- Shoping Cart Section Begin -->
	<section class="shoping-cart spad" style="padding-top: 10px;">
		<div class="container">
			<div class="section-title product__discount__title"
				style="margin-bottom: 50px;">
				<h2>Đơn hàng</h2>
			</div>
			<div class="row featured__filter">
				<div class="col-lg-12">
					<div class="shoping__cart__table">
						<table>
							<thead>
								<tr>
									<th class="shoping__product">Sản phẩm</th>
									<th>Thành tiền</th>
									<th>Tình trạng</th>
									<th>Thao tác</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${oList}" var="o">
									<c:if test="${o.status == 6}">
										<tr class="mix xacnhan">
											<td class="shoping__cart__item"
												style="width: 400px !important;"><img
												src="<c:url value="${o.p.image}"/>" alt="">
												<h5>${o.p.name}(x${o.count})</h5></td>
											<td class="shoping__cart__price">${o.p.tienTe(o.totalprice)}</td>
											<td class="shoping__cart__total">Chờ nhận đơn</td>
											<td class="shoping__cart__total"><a
												href="/Web/shipper/updateStatus?id=${o.id}&status=2">Nhận
													đơn</a></td>
										</tr>
									</c:if>
									<c:if test="${o.status == 2}">
										<tr class="mix nhandon">
											<td class="shoping__cart__item"
												style="width: 400px !important;"><img
												src="<c:url value="${o.p.image}"/>" alt="">
												<h5>${o.p.name}(x${o.count})</h5></td>
											<td class="shoping__cart__price">${o.p.tienTe(o.totalprice)}</td>
											<td class="shoping__cart__total">Chờ lấy hàng</td>
											<td class="shoping__cart__total"><a
												href="/Web/shipper/updateStatus?id=${o.id}&status=3">Đã
													lấy</a></td>
										</tr>
									</c:if>
									<c:if test="${o.status == 3}">
										<tr class="mix layhang">
											<td class="shoping__cart__item"
												style="width: 400px !important;"><img
												src="<c:url value="${o.p.image}"/>" alt="">
												<h5>${o.p.name}(x${o.count})</h5></td>
											<td class="shoping__cart__price">${o.p.tienTe(o.totalprice)}</td>
											<td class="shoping__cart__total">Chờ đi giao</td>
											<td class="shoping__cart__total"><a
												href="/Web/shipper/updateStatus?id=${o.id}&status=4">Đã
													giao</a><a href="/Web/shipper/updateStatus?id=${o.id}&status=7">Trả
													hàng</a></td>
										</tr>
									</c:if>
									<c:if test="${o.status == 4}">
										<tr class="mix danggiao">
											<td class="shoping__cart__item"
												style="width: 400px !important;"><img
												src="<c:url value="${o.p.image}"/>" alt="">
												<h5>${o.p.name}(x${o.count})</h5></td>
											<td class="shoping__cart__price">${o.p.tienTe(o.totalprice)}</td>
											<td class="shoping__cart__total">Đã giao</td>
											<td class="shoping__cart__total"></td>
										</tr>
									</c:if>
									<c:if test="${o.status == 7}">
										<tr class="mix dagiao">
											<td class="shoping__cart__item"
												style="width: 400px !important;"><img
												src="<c:url value="${o.p.image}"/>" alt="">
												<h5>${o.p.name}(x${o.count})</h5></td>
											<td class="shoping__cart__price">${o.p.tienTe(o.totalprice)}</td>
											<td class="shoping__cart__total">Khách trả hàng</td>
											<td class="shoping__cart__total"></td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
<!-- Js Plugins -->
<script src="<c:url value="/template/js/jquery-3.7.0.min.js" />"></script>
<script src="<c:url value="/template/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/template/js/jquery.nice-select.min.js" />"></script>
<script src="<c:url value="/template/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/template/js/jquery.slicknav.js" />"></script>
<script src="<c:url value="/template/js/mixitup.min.js" />"></script>
<script src="<c:url value="/template/js/owl.carousel.min.js" />"></script>
<script src="<c:url value="/template/js/main.js" />"></script>
<script src="<c:url value="/template/js/admin.js" />"></script>