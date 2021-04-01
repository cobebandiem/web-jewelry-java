<%@ page 
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title></title>
</head>
<body>
	<!-- Modal layout-->
	<c:if test="${check==true}">
		<div class="modal-noti">
		<div class="notification">
			<i class="notification--icon far fa-check-circle"></i>
			<span class="notificatio--content">${msg}</span>
		</div>
	</div>
	</c:if>
	<c:if test="${check==false}">
		<div class="modal-noti">
		<div class="notification-err">
			<i class="notification--icon far fa-check-circle"></i>
			<span class="notificatio--content">${msg}</span>
		</div>
	</div>
	</c:if>
</body>
</html>