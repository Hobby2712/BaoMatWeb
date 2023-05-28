<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content Start -->
<div class="content" style="background-color: #fff;">
	<nav
		class="navbar navbar-expand bg-white navbar-light sticky-top px-4 py-0"
		style="padding: 5px !important;">
		<a href="#" class="sidebar-toggler flex-shrink-0"> <i
			class="fa fa-bars"></i>
		</a>
	</nav>
	<section class="featured spad" style="padding-top: 10px;">
		<div class="container">
			<!-- Navbar End -->
			<div class="section-title product__discount__title"
				style="margin-bottom: 50px; margin-top: 20px;">
				<h2>Biểu đồ doanh số</h2>
			</div>

			<div class="form-group">
				<div class="col-sm-12">
					<select class="form-control" id="nam_thong_ke"
						style="padding-top: 0 !important;">
						<c:forEach items="${yearSelect}" var="y">
							<c:if test="${y==yearIndex}">
								<option selected="selected">${y}</option>
							</c:if>
							<c:if test="${y!=yearIndex}">
								<option>${y}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>

			<script>
                    var input = document.getElementById('nam_thong_ke')
                        input.change = function () {
                            var nam = input.options[input.selectedIndex].text;
                            if (nam != '0') {
                                var x = '/Web/seller/homeSeller?year=' + nam;
                                console.log(x);
                                location.href = x;
                            }
                        }
                </script>

			<div>
				<canvas id="mychart" style="width: 150%; height: 500px;"></canvas>
			</div>

			<div class="section-title product__discount__title"
				style="margin-bottom: 50px; margin-top: 50px;">
				<h2>Thống kê</h2>
			</div>
			<div class="col-lg-12" style="margin: auto;">
				<div class="shoping__cart__table">
					<table>
						<thead>
							<tr>
								<th>User</th>
								<th>Địa chỉ</th>
								<th>Mã Sản phẩm</th>
								<th>Số lượng</th>
								<th>Ngày mua</th>
								<th>Thành tiền</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${tList}" var="t">
								<tr>
									<td class="shoping__cart__price">${t.uName}</td>
									<td class="shoping__cart__price" style="width: 200px">
										${t.uAddress}</td>
									<td class="shoping__cart__price">${t.pId}</td>
									<td class="shoping__cart__price">${t.quantity}</td>
									<td class="shoping__cart__price">${t.DateFormat(t.creatDate)}</td>
									<td class="shoping__cart__price">${t.total}</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
			<c:if test="${endPage>1}">
				<div class="product__pagination">
					<c:if test="${tag>1}">
						<c:if test="${tag>16}">
							<a href="homeSeller?index=1&year=${yearIndex}"><<</a>
						</c:if>
						<a href="homeSeller?index=${tag-1}&year=${yearIndex}"><</a>
					</c:if>
					<c:forEach begin="1" end="${endPage}" var="i">
						<a class="${tag == i ?"
							active":""}" href="homeSeller?index=${i}&year=${yearIndex}">${i}</a>
					</c:forEach>
					<c:if test="${tag<endPage}">
						<a href="homeSeller?index=${tag+1}&year=${yearIndex}">></a>
						<c:if test="${tag<endPage-16}">
							<a href="homeSeller?index=${endPage}&year=${yearIndex}">>></a>
						</c:if>
					</c:if>

				</div>
			</c:if>
		</div>
	</section>
</div>

<!-- Content End -->
<script src="<c:url value="/template/js/chart.js" />"></script>3
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
	let info = [];
	<c:forEach items="${chart}" var="c">
		info.push(${c.total})
	</c:forEach>
	window.onload = function() {
		drawChart("mychart", info)
	}
</script>
