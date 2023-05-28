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

<%@ include file="/common/admin/left.jsp"%>
<!-- body -->
<dec:body />
<!-- body -->

<!-- Js Plugins -->
<script>
	function validateImage() {
		var fileInput = document.getElementById('image');
		var filePath = fileInput.value;
		var allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;
		if (!allowedExtensions.exec(filePath)) {
			alert('Chỉ được chọn định dạng hình ảnh jpg, jpeg, png');
			fileInput.value = '';
			return false;
		}
		return true;
	}
	function getCsrfToken() {
        return '<%=session.getAttribute("csrf_token")%>'
    }

function addCsrfTokenToForm(event) {
event.preventDefault();

var csrfTokenInput = document.getElementById('csrfTokenInput');
csrfTokenInput.value = getCsrfToken();
if(validateImage()){
	var form = event.currentTarget.form;
	form.submit();
}

}
function addCsrfTokenToFormWithOutImg(event) {
    event.preventDefault();
    
    var csrfTokenInput = document.getElementById('csrfTokenInput');
    csrfTokenInput.value = getCsrfToken();
    var form = event.currentTarget.form;
    form.submit();
    
  }
</script>
<script src="<c:url value="/template/js/jquery-3.7.0.min.js" />"></script>
<script src="<c:url value="/template/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/template/js/jquery.nice-select.min.js" />"></script>
<script src="<c:url value="/template/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/template/js/jquery.slicknav.js" />"></script>
<script src="<c:url value="/template/js/mixitup.min.js" />"></script>
<script src="<c:url value="/template/js/owl.carousel.min.js" />"></script>
<script src="<c:url value="/template/js/main.js" />"></script>
<script src="<c:url value="/template/js/admin.js" />"></script>