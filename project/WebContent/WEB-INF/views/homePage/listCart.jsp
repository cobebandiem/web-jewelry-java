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
	<c:forEach items="${listCart}" var="cart">
   		<div class="cart__item">
           <div class="cart__check">
               <input class="cart__check--input" onchange="changeCheckbox(${cart.id},${idUser})" type="checkbox" ${cart.isbold==true?'checked':''}/>
           </div>
           <div class="cart__info">
               <div class="cart__img--containers">
                   <img class="cart__img" src="./assets//images/${cart.image}" alt="">
               </div>
               <div class="cart__name">${cart.name}</div>                           
           </div>
           <div class="cart__price">
               <span class="cart__price--curent">₫${cart.newPrice}</span>
           </div>
           <div class="cart__amount">
               <span class="cart__amount--operator" onclick="addSLCart(0,${cart.id},${idUser})" id="minus"><svg enable-background="new 0 0 10 10" viewBox="0 0 10 10" x="0" y="0" class="shopee-svg-icon "><polygon points="4.5 4.5 3.5 4.5 0 4.5 0 5.5 3.5 5.5 4.5 5.5 10 5.5 10 4.5"></polygon></svg></span>
               <span class="cart__amount--current">${cart.soluong}</span>
               <span class="cart__amount--operator" onclick="addSLCart(1,${cart.id},${idUser})" id="plus"><svg enable-background="new 0 0 10 10" viewBox="0 0 10 10" x="0" y="0" class="shopee-svg-icon icon-plus-sign"><polygon points="10 4.5 5.5 4.5 5.5 0 4.5 0 4.5 4.5 0 4.5 0 5.5 4.5 5.5 4.5 10 5.5 10 5.5 5.5 10 5.5"></polygon></svg></span>  
           </div>
           <div class="cart__total primary-color">
               ₫${cart.priceSL}
           </div>
           <div class="cart__edit">
               <span class="cart__edit--remove" onclick="reomoveCart(${idUser},${cart.id})">xóa</span>
           </div>
       </div>
   	</c:forEach>
   	                <div class="cart__footer">
                    <div class="cart__check">
                        <input class="cart__check--input check-all" onchange="checkAll(2)" ${type==1?'checked':''} name="check-all" type="checkbox"/>
                    </div>
                    <div class="cart__all--product">
                        Tất Cả
                    </div>
                    <div class="cart__total--products">
                        <span>Tổng tiền:</span>
                        <span class="cart__total--vnd">₫${totalMoneyPay}</span>
                    </div>
                    <div class="btn buy-now" onclick="payMoney(${idUser})">
                        Mua Hàng
                    </div>
                </div>
</body>
</html>