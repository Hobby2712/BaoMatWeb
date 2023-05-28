<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Edit Modal HTML -->
<div class="content" style="background-color: #fff;">
	<nav
		class="navbar navbar-expand bg-white navbar-light sticky-top px-4 py-0"
		style="padding: 5px !important;">
		<a href="#" class="sidebar-toggler flex-shrink-0"> <i
			class="fa fa-bars"></i>
		</a>
	</nav>
	<!-- Navbar End -->


	<div id="editEmployeeModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="edit" method="post" enctype="multipart/form-data" onsubmit="return validateImage()">
					<div class="modal-header">
						<h4 class="modal-title">Edit Product</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<input id="csrfTokenInput" name="csrf_token" type="hidden">
					<div class="modal-body">
						<div class="form-group">
							<label>ID</label> <input value="${detail.id}" name="id"
								type="text" class="form-control" readonly required>
						</div>
						<div class="form-group">
							<label>Name</label> <input value="${detail.name}" name="name"
								type="text" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Price</label> <input value="${detail.price}" name="price"
								type="text" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Image</label><br> <img src="/Web${detail.image}"
								width="100" height="120" alt=""> <input type="file"
								id="image" name="image" class="form-control">
						</div>
						<div class="form-group">
							<label>Description</label>
							<textarea name="description" class="form-control" required>${detail.description}</textarea>
						</div>
						<div class="form-group">
							<label>Quantity</label> <input value="${detail.quantity}"
								name="quantity" type="text" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Category</label> <select name="category"
								class="form-select" aria-label="Default select example">
								<c:forEach items="${cList2}" var="o">
									<c:if test="${o.id==detail.cateId}">
										<option selected="selected" value="${o.id}">${o.name}</option>
									</c:if>
									<c:if test="${o.id!=detail.cateId}">
										<option value="${o.id}">${o.name}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<input onclick="location.href='managerP';" type="button"
							class="btn btn-default" style="background-color: #e79393;"
							value="Cancel"> <input onclick="addCsrfTokenToForm(event)"
							class="btn btn-success" value="Edit">
					</div>
				</form>
			</div>
		</div>
	</div>
</div>