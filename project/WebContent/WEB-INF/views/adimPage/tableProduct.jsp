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
	<thead>
        <tr>
            <th class="text-center">STT</th>
            <th class="text-center">Loại</th>
            <th class="text-center">Tên</th>
            <th class="text-center">Giá</th>
            <th class="text-center">Sale off</th>
            <th class="text-center">Số lượng</th>
            <th class="text-center">Hình ảnh</th>
            <th class="text-center"></th>
        </tr>
    </thead>
    <tbody>
	    <c:forEach items="${listProduct}" var="product">
	    	<tr>
	            <td>${product.id}</td>
	            <td>${product.catalog_id}</td>
	            <td class="text-center">
	            	${product.name}
	            </td>
	            <td>${product.priceNew}</td>
	            <td>${product.discount}</td>
	            <td>${product.quantity}</td>
	            <td>${product.image_list}</td>
	            <td class="notShow content">${product.content}</td>
	            <td class="text-center">
	                <button type="button" onclick="moveDataProduct(${product.id},${product.catalog_id},'${product.name}','${product.priceNew}',${product.discount},${product.quantity},'${product.image_list}','${product.trademark}','${product.content}')" class="btn btn-warning">
	                    <span class="fa fa-pencil mr-5"></span>&nbsp;Sửa
	                </button>
	                &nbsp;
	                <button type="button" onclick="removeProduct(${product.id})" class="btn btn-danger">
	                    <span class="fa fa-trash mr-5"></span>&nbsp;Xóa
	                </button>
	            </td>
	        </tr>
	    </c:forEach>
    </tbody>
</body>
</html>