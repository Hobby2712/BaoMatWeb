<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="dec"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zxx">

<head>
<meta charset="UTF-8">
<meta name="description" content="Ogani Template">
<meta name="keywords" content="Ogani, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Ogani | Template</title>

<!-- Google Font -->
<link
	href="<c:url value="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap"/>"
	rel="stylesheet">

<!-- Css Styles -->
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
<link rel="stylesheet" href="<c:url value="/template/css/login.css" />"
	type="text/css">
<link rel="stylesheet"
	href="<c:url value="/template/css/profile.css" />" type="text/css">
</head>
<%@ include file="/common/web/header.jsp"%>

<!-- body -->
<dec:body />
<!-- body -->

<!--=== Footer v4 ===-->
<jsp:include page="/common/web/footer.jsp"></jsp:include>
<!--=== End Footer v4 ===-->
<!-- Js Plugins -->
<script src="<c:url value="/template/js/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/template/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/template/js/jquery.nice-select.min.js" />"></script>
<script src="<c:url value="/template/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/template/js/jquery.slicknav.js" />"></script>
<script src="<c:url value="/template/js/mixitup.min.js" />"></script>
<script src="<c:url value="/template/js/owl.carousel.min.js" />"></script>
<script src="<c:url value="/template/js/main.js" />"></script>
<script>           
            function addCart() {
				let num = document.getElementById("quantityP").value;
				let queryString = window.location.search;
				let urlParams = new URLSearchParams(queryString);
				let id = urlParams.get('pid');
				var x = '/Web/addCartDetail?pid=' + id;
				x = x + '&quantity=' + num;
				location.href = x;
			}
        </script>
<script type="text/javascript">
		/* $(window).scroll(function() {
			if($(window).scrollTop() + $(window).height() >= $(document).height()){
				loadMore();
			}
		}); */
        document.addEventListener("DOMContentLoaded", () => {
            //set up the IntersectionObserver to load more images if the footer is visible.
            //URL - https://gist.githubusercontent.com/prof3ssorSt3v3/1944e7ba7ffb62fe771c51764f7977a4/raw/c58a342ab149fbbb9bb19c94e278d64702833270/infinite.json
            let options = {
                root: null,
                rootMargins: "0px",
                threshold: 0.5
            };
            const observer = new IntersectionObserver(handleIntersect, options);
            observer.observe(document.querySelector(".banner"));
            //an initial load of some data
        });
        function handleIntersect(entries) {
            if (entries[0].isIntersecting) {
                console.warn("something is intersecting with the viewport");
                loadMore();
            }
        }
        function getCsrfToken() {
            return '<%=session.getAttribute("csrf_token")%>'
        }
        function loadMore() {
			/* tạo viên amount để Gọi và đếm classname là product */
			var amount = document.getElementsByClassName("featured__item").length;
			$.ajax({
					url : "/Web/ajax/load", //send to Controller
					type : "get", //send it through get method
				data : {
					exits : amount
				},
				success : function(data) {
					var row = document.getElementById("content")
					row.innerHTML += data;
				},
				error : function(xhr) {
					//Do Something to handle error
				}
			});
		};
	</script>
<script>
function addCsrfTokenToForm(event) {
    event.preventDefault();
    
    var csrfTokenInput = document.getElementById('csrfTokenInput');
    csrfTokenInput.value = getCsrfToken();
    
    var form = event.currentTarget.form;
    form.submit();
  }
</script>