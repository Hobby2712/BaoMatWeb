<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
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
					style="margin-bottom: 20px;">
					<h2>Quản lý sản phẩm</h2>
				</div>
				<div class="row justify-content-end">
					<a href="#addEmployeeModal" class="buttonAdd btn btn-success"
						data-toggle="modal"><i class="fa fa-plus-circle"></i> <span>Add
							New Product</span></a>
				</div>
				<br />
				<div class="hero__search">
					<div class="hero__search__form">
						<form action="search" method="post">
							<input name="txt" type="text" placeholder="What do yo u need?">
							<button onclick="addCsrfTokenToFormWithOutImg(event)" class="site-btn">SEARCH</button>
						</form>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="shoping__cart__table">
							<table>
								<thead>
									<tr>
										<th class="shoping__product">Products</th>
										<th>Price</th>
										<th>Quantity</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pList}" var="p">
										<tr>
											<td class="shoping__cart__item"><img
												src="<c:url value="/image?fname=${p.image}"/>" alt="">
												<h5>${p.name}</h5></td>
											<td class="shoping__cart__price">${p.tienTe(p.price)}</td>
											<td class="shoping__cart__quantity">${p.quantity}</td>
											<td class="shoping__cart__total"><a
												href="loadEdit?pid=${p.id}" class="edit"><i
													class="fa fa-pencil"></i></a> <a href="del?pid=${p.id}"
												class="delete"><i class="fa fa-trash"></i></a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<c:if test="${endPage>1}">
					<div class="product__pagination">
						<c:if test="${tag>1}">
							<c:if test="${tag>16}">
								<a href="managerP?index=1"><<</a>
							</c:if>
							<a href="managerP?index=${tag-1}"><</a>
						</c:if>
						<c:forEach begin="1" end="${endPage}" var="i">
							<a class="${tag == i ?" active":""}" href="managerP?index=${i}">${i}</a>
						</c:forEach>
						<c:if test="${tag<endPage}">
							<a href="managerP?index=${tag+1}">></a>
							<c:if test="${tag<endPage-16}">
								<a href="managerP?index=${endPage}">>></a>
							</c:if>
						</c:if>

					</div>
				</c:if>
				<br></br>
				<!-- Shoping Cart Section End -->

				<!-- Add Modal HTML -->
				<div id="addEmployeeModal" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<form action="add" method="post" enctype="multipart/form-data" onsubmit="return validateImage()">
								<div class="modal-header">
									<h4 class="modal-title">Add Product</h4>
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
								</div>
								<input id="csrfTokenInput" name="csrf_token" type="hidden">
								<div class="modal-body">
									<div class="form-group">
										<label>Name</label> <input name="name" type="text"
											class="form-control" required>
									</div>
									<div class="form-group">
										<label>Price</label> <input name="price" type="text"
											class="form-control" required>
									</div>
									<div class="form-group">
										<label>Image</label> <input type="file" id="image" name="image"
											class="form-control" required>
									</div>
									<div class="form-group">
										<label>Description</label>
										<textarea name="description" class="form-control" required></textarea>
									</div>
									<div class="form-group">
										<label>Quantity</label> <input name="quantity" type="text"
											class="form-control" required>
									</div>
									<div class="form-group">
										<label>Category</label> <select name="category"
											class="form-select" aria-label="Default select example">
											<c:forEach items="${cList2}" var="o">
												<option value="${o.id}">${o.name}</option>
											</c:forEach>
										</select>
									</div>

								</div>
								<div class="modal-footer">
									<input type="button" class="btn btn-default"
										data-dismiss="modal" style="background-color: #e79393;"
										value="Cancel"> <input onclick="addCsrfTokenToForm(event)"
										class="btn btn-success" value="Add">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>

</body>