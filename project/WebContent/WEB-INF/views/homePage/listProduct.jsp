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
	<c:forEach items="${listProduct}" var="product">
		<div class="col l-2-4 m-4 c-6">
			<a href="product/${product.id}.htm" class="home-product-item">
				<div class="home-product-item__img" style="background-image: url(./assets/images/${product.image_list};"></div>
				<h4 class="home-product-item__name">${product.name}</h4>
				<div class="home-product-item__price">
					<c:if test="${product.discount!=0}">
						<span class="home-product-item__price-old">₫${product.priceOld}</span>
					</c:if>
					<span class="home-product-item__price-current">₫${product.priceNew}</span>
				</div>
				<div class="home-product-item__action">
					<span class="home-product-item__like">
						<!-- home-product-item__like-liked  -->
						<i class="home-product-item__like-empty far fa-heart"></i>
						<i class="home-product-item__like-fill fas fa-heart"></i>
					</span>
					<div class="home-product-item__rating">
						<i class="home-product-item__star--gold fas fa-star"></i>
						<i class="home-product-item__star--gold fas fa-star"></i>
						<i class="home-product-item__star--gold fas fa-star"></i>
						<i class="home-product-item__star--gold fas fa-star"></i>
						<i class="fas fa-star"></i>
					</div>
					<span class="home-product-item__sold">Đã bán ${product.sold}</span>
				</div>
				<div class="home-product-item__origin">
					<span class="home-product-item__brand">${product.trademark}</span>
					<span class="home-product-item__origin-name">Việt Nam</span>
				</div>
				<div class="home-product-item__favourite">
					<i class="fas fa-check"></i>
					Yêu thích
				</div>
				<c:if test="${product.discount!=0}">
					<div class="home-product-item__sale-off">
						<span class="home-product-item__sale-off-precent">${product.discount}%</span>
						<span class="home-product-item__sale-off-label">GIẢM</span>
					</div>
				</c:if>
			</a>
		</div>
	</c:forEach>
</body>
</html>