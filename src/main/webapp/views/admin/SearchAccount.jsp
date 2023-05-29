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
					<h2>Quản lý tài khoản</h2>
				</div>			
				<div class="row justify-content-end">
					<div class="col-2">
						<button onclick="sortTable()" class="btn btn-dark">Sort
							By Name</button>
					</div>
					<div class="col-3">
						<a href="#addEmployeeModal" class="buttonAdd btn btn-success"
							data-toggle="modal"> <i class="fa fa-plus-circle"></i> <span>Add
								New Account</span>
						</a>
					</div>
				</div>
				<div class="hero__search">
					<div class="hero__search__form">
						<form action="search" method="post">
							<input id="csrfTokenInputNoImg" name="csrf_token" type="hidden">
							<input name="txt" type="text" placeholder="Search for name">
							<button onclick="addCsrfTokenToFormWithOutImg(event)" class="site-btn">SEARCH</button>
						</form>
					</div>
				</div>
				<br />
				<c:if test="${not empty message}">
					<div class="row"
						style="background-color: rgb(17, 201, 17); border-radius: 12px;">
						<p
							style="font-size: 130%; padding: 9px; margin-bottom: 0px; padding-left: 20px; color: black; font-weight: 600;">${message}</p>
					</div>
					</br>
				</c:if>
				<div class="row">
					<div class="col-lg-12">
						<div class="shoping__cart__table">
							<table id="myTable">
								<thead>
									<tr>
										<th class="shoping__product">User</th>
										<th>Role</th>
										<th>Email</th>
										<th>Phone</th>
										<th>Address</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${aList}" var="a">
										<tr>
											<td class="shoping__cart__item"><c:if
													test="${a.image == null}">
													<img
														src="https://st3.depositphotos.com/1767687/16607/v/450/depositphotos_166074422-stock-illustration-default-avatar-profile-icon-grey.jpg"
														alt="">
												</c:if> <c:if test="${a.image != null}">
													<img src="<c:url value="/image?fname=${a.image}"/>" alt="">
												</c:if>

												<h5>${a.fullName}</h5></td>
											<c:if test="${a.role == 1}">
												<td class="shoping__cart__quantity">Admin</td>
											</c:if>
											<c:if test="${a.role == 2}">
												<td class="shoping__cart__quantity">User</td>
											</c:if>
											<c:if test="${a.role == 3}">
												<td class="shoping__cart__quantity">Shipper</td>
											</c:if>
											<c:if test="${a.role == 4}">
												<td class="shoping__cart__quantity">Vendor</td>
											</c:if>
											<td class="shoping__cart__quantity">${a.email}</td>
											<td class="shoping__cart__quantity">${a.phone}</td>
											<td class="shoping__cart__quantity">${a.address}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<script>
								function sortTable() {
									var table, rows, switching, i, x, y, shouldSwitch;
									table = document.getElementById("myTable");
									switching = true;
									/*Make a loop that will continue until
									no switching has been done:*/
									while (switching) {
										//start by saying: no switching is done:
										switching = false;
										rows = table.rows;
										/*Loop through all table rows (except the
										first, which contains table headers):*/
										for (i = 1; i < (rows.length - 1); i++) {
											//start by saying there should be no switching:
											shouldSwitch = false;
											/*Get the two elements you want to compare,
											one from current row and one from the next:*/
											x = rows[i]
													.getElementsByTagName("TD")[0];
											y = rows[i + 1]
													.getElementsByTagName("TD")[0];
											//check if the two rows should switch place:
											if (x.innerHTML.toLowerCase() > y.innerHTML
													.toLowerCase()) {
												//if so, mark as a switch and break the loop:
												shouldSwitch = true;
												break;
											}
										}
										if (shouldSwitch) {
											/*If a switch has been marked, make the switch
											and mark that a switch has been done:*/
											rows[i].parentNode.insertBefore(
													rows[i + 1], rows[i]);
											switching = true;
										}
									}
								}
							</script>
						</div>
					</div>
				</div>
				<c:if test="${endPage>1}">
					<div class="product__pagination">
						<c:if test="${tag>1}">
							<c:if test="${tag>16}">
								<a href="search?index=1&&txt=${search}"><<</a>
							</c:if>
							<a href="search?index==${tag-1}&&txt=${search}"><</a>
						</c:if>
						<c:forEach begin="1" end="${endPage}" var="i">
							<a class="${tag == i ?"
								active":""}" href="search?index=${i}&&txt=${search}">${i}</a>
						</c:forEach>
						<c:if test="${tag<endPage}">
							<a href="search?index=${tag+1}&&txt=${search}">></a>
							<c:if test="${tag<endPage-16}">
								<a href="search?index=${endPage}&&txt=${search}">>></a>
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
									<h4 class="modal-title">Add Account</h4>
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
								</div>
								<input id="csrfTokenInput" name="csrf_token" type="hidden">
								<div class="modal-body">
									<div class="form-group">
										<label>Username</label> <input name="username" type="text"
											class="form-control" required>
									</div>
									<div class="form-group">
										<label>Full Name</label> <input name="fullname" type="text"
											class="form-control" required>
									</div>
									<div class="form-group">
										<label>Email</label> <input name="email" type="email"
											class="form-control" required>
									</div>
									<div class="form-group">
										<label>Address</label> <input name="address" type="text"
											class="form-control" required>
									</div>
									<div class="form-group">
										<label>Password</label> <input name="password" type="text"
											class="form-control" required>
									</div>
									<div class="form-group">
										<label>Phone</label> <input name="phone" type="text"
											class="form-control" required>
									</div>
									<div class="form-group">
										<label>Role</label> <select name="role">
											<option value="1">Admin</option>
											<option value="2">User</option>
											<option value="3">Shipper</option>
											<option value="4">Vendor</option>
										</select>
									</div>
									<div class="form-group">
										<label>Image</label> <input type="file" id="image" name="image"
											class="form-control" required>
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