<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<!-- Content Start -->
	<div class="content" style="background-color: #fff;">
		<!-- Shoping Cart Section Begin -->
		<section class="shoping-cart spad" style="padding-top: 10px;">
			<div class="container">
				<div class="section-title product__discount__title"
					style="margin-bottom: 20px;">
					<h2>Quản lý danh mục</h2>
				</div>
				<div class="row justify-content-end">

					<div class="col-sm-2">
						<a href="#addEmployeeModal" class="buttonAdd btn btn-success"
							data-toggle="modal" style="width: 200px"><i
							class="fa fa-plus-circle"></i> <span>Add New Category</span></a>
					</div>

				</div>
				<div class="hero__search">
					<div class="hero__search__form">
						<form action="searchC" method="post">
							<input name="txt" type="text" placeholder="Search for name">
							<button type="submit" class="site-btn">SEARCH</button>
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

				</c:if>
				<br />
				<div class="row">
					<div class="col-lg-12">
						<div class="shoping__cart__table">
							<table>
								<thead>
									<tr>
										<th>Name</th>
										<th>Image</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${cList}" var="c">
										<tr>
											<td>${c.name}</td>
											<td class="shoping__cart__quantity"><img
												src="<c:url value="/image?fname=${c.image}"/>" alt="">
											</td>
											<td><a href="delcategory?cid=${c.id}" class="delete">
													<i class="fa fa-trash"></i>
											</a></td>
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
								<a href="searchC?index=1&&txt=${search}"><<</a>
							</c:if>
							<a href="searchC?index==${tag-1}&&txt=${search}"><</a>
						</c:if>
						<c:forEach begin="1" end="${endPage}" var="i">
							<a class="${tag == i ?"
								active":""}" href="searchC?index=${i}&&txt=${search}">${i}</a>
						</c:forEach>
						<c:if test="${tag<endPage}">
							<a href="searchC?index=${tag+1}&&txt=${search}">></a>
							<c:if test="${tag<endPage-16}">
								<a href="searchC?index=${endPage}&&txt=${search}">>></a>
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
							<form action="addcategory" method="post"
								enctype="multipart/form-data" onsubmit="return validateImage()">
								<div class="modal-header">
									<h4 class="modal-title">Add Category</h4>
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<label>Name</label> <input name="name" type="text"
											class="form-control" required>
									</div>
									<div class="form-group">
										<select class="form-control" name="parent_category"
											style="padding-top: 0 !important;">
											<option value="0">Parent Category</option>
											<c:forEach items="${parentCategory}" var="y">
												<option value='${y.id}'>${y.name}</option>
											</c:forEach>
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
										value="Cancel"> <input type="submit"
										class="btn btn-success" value="Add">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>