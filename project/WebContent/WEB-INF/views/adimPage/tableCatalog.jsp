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
             <th class="text-center">Mã loại</th>
             <th class="text-center">Tên loại</th>
             <th></th>
         </tr>
     </thead>
     <tbody>
     	<c:forEach items="${listCatalog}" var="catalog">
     		<tr>
              <td>${catalog.id}</td>
              <td>${catalog.name}</td>
				<td class="text-center">
                  <button type="button" onclick="moveDataType(${catalog.id},'${catalog.name}')" class="btn btn-warning">
                      <span class="fa fa-pencil mr-5"></span>&nbsp;Sửa
                  </button>
                  &nbsp;
                  <button type="button" onclick="removeType(${catalog.id})" class="btn btn-danger remove-type">
                      <span class="fa fa-trash mr-5"></span>&nbsp;Xóa
                  </button>
              </td>
          </tr>
     	</c:forEach>
     </tbody>
</body>
</html>