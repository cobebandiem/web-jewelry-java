<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <script src="//code.jquery.com/jquery.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <title>Document</title>
    <style>
        .admin{
            max-width: 1500px;
            margin: 0 auto;
        }
        .flex{
            display: flex;
        } 
        .flex-space-bw{
            justify-content: space-between;
        }
        td{
            max-width: 200px;
            overflow: scroll;
        }
        td::-webkit-scrollbar{
            display: none;
        }
        .notShow{
            display: none;
        }
        .is-invalid{
        	border-color: #dc3545;
		    padding-right: calc(1.5em + .75rem);
		    background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='15' height='15' fill='none' stroke='%23dc3545' viewBox='0 0 12 12'%3e%3ccircle cx='6' cy='6' r='4.5'/%3e%3cpath stroke-linejoin='round' d='M5.8 3.6h.4L6 6.5z'/%3e%3ccircle cx='6' cy='8.2' r='.6' fill='%23dc3545' stroke='none'/%3e%3c/svg%3e");
		    background-repeat: no-repeat;
		    background-position: right calc(.375em + .1875rem) center;
		    background-size: calc(.75em + .375rem) calc(.75em + .375rem);
        }
        .for-message{
		    color:#dc3545;
		    display: block;
		    padding-top: 10px;
		    font-size: 1.3rem;
		    font-weight: 300; 
		}
		.modal-noti{
		    position: fixed;
		    top:0;
		    right:0;
		    bottom:0;
		    left:0;
		    animation:fadeIn ease 0.1s;
		    z-index:2;
		}
		.notification{
		    position: absolute;
		    top:50%;
		    left:50%;
		    width:350px;
		    height:200px;
		    background-color:rgba(0,0,255,0.5);
		    transform: translate(-50%,-50%);
		    display: flex;
		    flex-direction: column;
		    align-items: center;
		    border-radius: 5px;
		    opacity: 0.7;
		}
		.notification-err{
		    position: absolute;
		    top:50%;
		    left:50%;
		    width:350px;
		    height:200px;
		    background-color:#d0011b;
		    transform: translate(-50%,-50%);
		    display: flex;
		    flex-direction: column;
		    align-items: center;
		    border-radius: 5px;
		    opacity: 0.7;
		}
		.notification--icon{
		    margin-top: 30px;
		    font-size: 6rem;
		    color:white;
		    font-weight: 300;
		}
		.notificatio--content{
		    font-size: 1.8rem;
		    font-weight: 400;
		    padding-top: 25px;
		    color:white;
		}
		.dispnone{
			display:none;
		}
		.back-home{
			margin-top: 35px;
			display: inline-bock;
			width:250px;
		}
		.back-to-top {
		    position: fixed;
		    top: auto;
		    left: auto;
		    right: 15px;
		    bottom: 15px;
		    font-size: 50px;
		    opacity: 0.8;
		    z-index: 9999;
		    cursor: pointer;
		}
		
		.back-to-top:hover {
		    opacity: 1;
		}
    </style>
</head>
<body>
    <div class="admin">
    	<a href="home.htm" type="button" class="btn btn-primary back-home">Về Trang Chủ</a>
        <div class="text-center h1-line">
            <h1>Quản Lý Loại Sản Phẩm</h1>
            <hr/>
        </div>
        <div class="row">
            <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" id="form-add">
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h3 id="panel-title-type" class="panel-title flex flex-space-bw">
                            Thêm Loại Sản Phẩm
                            <span class="fa fa-times-circle text-right"></span>
                        </h3>
                    </div>
                    <div class="panel-body">
                        <form id="form-1">
                            <div class="form-group">
                                <label>Mã loại:</label>
                                <input name="codeName" id="codeName" type="text" class="form-control" readonly placeholder="Mã sẽ tự động tăng"/>
                            </div>
                            <div class="form-group">
                                <label>Tên loại:</label>
                                <input name="typeName" id="typeName" type="text" class="form-control"/>
                                <span class="for-message invalid-feedback"></span>
                            </div>
                            <div class="text-center">
                            	<button id="edit-product-type" type="submit" class="btn btn-warning dispnone">Sửa loại SP</button>
                                <button id="add-product-type" type="submit" class="btn btn-warning">Thêm loại SP</button>&nbsp;
                                <button onclick="resetType()" type="button" class="btn btn-danger">Hủy Bỏ</button>
                            </div>
                        </form>
                    </div> 
                  </div>
            </div>
            <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9" id="form-show">
                <button onclick="resetType()" type="button" class="btn btn-primary" id="btn__add--product">
                    <span class="fa fa-plus mr-5"></span>&nbsp;Thêm Loại Sản Phẩm
                </button>
                <br><br>
                <div class="row mt-15">
                    <div class="col-xs-7 col-sm-7 col-md-7 col-lg-7">
                        <div class="input-group">
                            <input type="text" name="keyWord" id="search-type" class="form-control" placeholder="Nhập từ khóa..."/>
                            <span class="input-group-btn">
                              <button class="btn btn-primary" onClick={this.onSearch} type="button">
                                  <span class="fa fa-search mr-5"></span>Tìm
                              </button>
                            </span>
                        </div>
                    </div>
                    <div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
                    </div>
                </div>
                <br>
                <div class="row mt-15">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <table id="table-catalog" class="table table-bordered table-hover mt-15">
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
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="progress">
            <div class="progress-bar active" role="progressbar"
            aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width:100%">
            </div>
          </div>
        <div class="text-center">
            <h1>Quản Lý Sản Phẩm</h1>
            <hr/>
        </div>
        <div class="row">
            <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" id="form-add">
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h3 id="panel-title-product"  class="panel-title flex flex-space-bw">
                            Thêm Sản Phẩm
                            <span class="fa fa-times-circle text-right"></span>
                        </h3>
                    </div>
                    <div class="panel-body">
                        <form id="form-2">
                            <div class="form-group">
                                <label>Mã Sản phẩm:</label>
                                <input name="codeProduct" id="codeProduct" type="text" class="form-control" readonly placeholder="Mã sẽ tự động tăng"/>
                            	<span class="for-message invalid-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label>Loại :</label>
                                <select name="statusProduct" id="statusProduct" class="form-control">
                                	<c:forEach items="${listCatalog}" var="catalog">
                                		 <option value=${catalog.id}>${catalog.name}</option>
                                	</c:forEach>
                                </select>
                                <span class="for-message invalid-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label>Tên :</label>
                                <input name="nameProduct" id="nameProduct" type="text" class="form-control"/>
                            	<span class="for-message invalid-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label>Nhãn hiệu :</label>
                                <input name="trademarkProduct" id="trademarkProduct" type="text" class="form-control"/>
                            	<span class="for-message invalid-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label>Giá :</label>
                                <input name="priceProduct" id="priceProduct" type="number" class="form-control"/>
                            	<span class="for-message invalid-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label>Sale off :</label>
                                <input name="discountProduct" id="discountProduct" type="number" min="0" max="99" class="form-control"/>
                            	<span class="for-message invalid-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label>Số lượng :</label>
                                <input name="qualityProduct" id="qualityProduct" type="number" class="form-control"/>
                            	<span class="for-message invalid-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label>Hình ảnh :</label>
                                <input name="imagesProduct" id="imagesProduct" type="file" class="form-control" multiple="multiple"/>
                            	<span class="for-message invalid-feedback"></span>
                            </div>
                            <div class="form-group dispnone">
                                <label>Hình ảnh :</label>
                                <input name="trademarkProduct" id="imagesProductText" type="text" class="form-control" disabled/>
                            	<span class="for-message invalid-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label>Nội dung :</label>
                                <textarea class="form-control" name="contentProduct" id="contentProduct" rows="6"></textarea>
                            	<span class="for-message invalid-feedback"></span>
                            </div>
                            <br>
                            <div class="text-center">
                            	<button id="edit-product" type="submit" class="btn btn-warning dispnone">Sửa SP</button>
                                <button id="add-product" type="submit" class="btn btn-warning">Thêm SP</button>&nbsp;
                                <button type="button" onClick="resetProduct()" class="btn btn-danger">Hủy Bỏ</button>
                            </div>
                        </form>
                    </div> 
                  </div>
            </div>
            <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9" id="form-show">
                <button type="button" onClick="resetProduct()" class="btn btn-primary" id="btn__add--product">
                    <span class="fa fa-plus mr-5"></span>&nbsp;Thêm Sản Phẩm
                </button>
                <br><br>
                <div class="row mt-15">
                    <div class="col-xs-7 col-sm-7 col-md-7 col-lg-7">
                        <div class="input-group">
                            <input type="text" name="keyWord" class="form-control" placeholder="Nhập từ khóa..."/>
                            <span class="input-group-btn">
                              <button class="btn btn-primary" onClick={this.onSearch} type="button">
                                  <span class="fa fa-search mr-5"></span>Tìm
                              </button>
                            </span>
                        </div>
                    </div>
                    <div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
                    </div>
                </div>
                <br>
                <div class="row mt-15">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <table id="table-products" class="table table-bordered table-hover mt-15">
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
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="back-to-top" class="back-to-top" data-toggle="tooltip" data-placement="left" title="Trở lên đầu trang"><span class="glyphicon glyphicon-circle-arrow-up text-primary"></span></div>
    <div class="showIntication"></div>
    <script src="assets/js/Validator2.js"></script>
    <script type="text/javascript">
    
    function loadPage(link,name){
    	var xhttp;
		var url = link+".htm";
		if (window.XMLHttpRequest) 
		{        
		    xhttp = new XMLHttpRequest(); 
		} 
		else
		{            
			xhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xhttp.onreadystatechange= function() 
		{
			if (xhttp.readyState == 4)
			{
				let datars = xhttp.responseText;
				document.querySelector(name).innerHTML=datars;
			}
		}
		xhttp.open("POST",url,true);
		xhttp.send();
    }
    function removeType(id){
    	var xhttp;
        var url = "removetype.htm?id="+id;
        if (window.XMLHttpRequest) 
        {        
            xhttp = new XMLHttpRequest(); 
        } 
        else
        {            
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.onreadystatechange= function() 
        {
            if (xhttp.readyState == 4)
            {
                let datars = xhttp.responseText;
                document.querySelector('.showIntication').innerHTML=datars;
                setTimeout(()=>{
                    document.querySelector('.showIntication').innerHTML='';
                    loadPage('tableCatalog','#table-catalog');
                },500);
            }
        }
        xhttp.open("POST",url,true);
        xhttp.send();
    }
    function moveDataType(id,name){
    	document.querySelector("#panel-title-type").textContent="Sửa Loại Sản Phẩm";
    	document.querySelector("#edit-product-type").classList.remove("dispnone");
    	document.querySelector("#add-product-type").classList.add("dispnone");
    	document.querySelector("#codeName").value=id;
    	document.querySelector("#typeName").value=name;
    }
    function resetType(){
    	document.querySelector("#panel-title-type").textContent="Thêm Loại Sản Phẩm";
    	document.querySelector("#edit-product-type").classList.add("dispnone");
    	document.querySelector("#add-product-type").classList.remove("dispnone");
    	document.querySelector("#codeName").value='';
    	document.querySelector("#typeName").value='';
    }
    function resetProduct(){
    	document.querySelector("#panel-title-product").textContent="Thêm Sản Phẩm";
    	document.querySelector("#edit-product").classList.add("dispnone");
    	document.querySelector("#add-product").classList.remove("dispnone");

    	
    	document.querySelector("#codeProduct").value="";
    	document.querySelector("#statusProduct").value="1";
    	document.querySelector("#nameProduct").value="";
    	document.querySelector("#trademarkProduct").value="";
    	document.querySelector("#priceProduct").value="0";
    	document.querySelector("#discountProduct").value="";
    	document.querySelector("#qualityProduct").value="";
    	document.querySelector("#contentProduct").value="";
    }
    function moveDataProduct(id,typeId,name,price,discount,soluong,images,trademark,content){
    	console.log(id);
    	document.querySelector("#panel-title-product").textContent="Sửa Sản Phẩm";
    	document.querySelector("#edit-product").classList.remove("dispnone");
    	document.querySelector("#add-product").classList.add("dispnone");
    	document.querySelector("#codeProduct").value=id;
    	document.querySelector("#statusProduct").value=typeId;
    	document.querySelector("#nameProduct").value=name;
    	document.querySelector("#trademarkProduct").value=trademark;
    	let price1 = parseInt(price.split('.').join(""));
    	let dis=parseInt(discount);
    	document.querySelector("#priceProduct").value=price1/((100-dis)/100);
    	document.querySelector("#discountProduct").value=discount;
    	document.querySelector("#qualityProduct").value=soluong;
    	document.querySelector("#contentProduct").value=content;
    }
    function removeProduct(id){
    	var xhttp;
        var url = "removeproduct.htm?id="+id;
        if (window.XMLHttpRequest) 
        {        
            xhttp = new XMLHttpRequest(); 
        } 
        else
        {            
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.onreadystatechange= function() 
        {
            if (xhttp.readyState == 4)
            {
                let datars = xhttp.responseText;
                document.querySelector('.showIntication').innerHTML=datars;
                setTimeout(()=>{
                    document.querySelector('.showIntication').innerHTML='';
                    loadPage('tableProduct','#table-products');
                },500);
            }
        }
        xhttp.open("POST",url,true);
        xhttp.send();
    }
    
    Validator({
	      form:'#form-1',
	      messageElement:'.for-message',
	      rules:[
	        Validator.isRequired('#typeName')
	      ],
	      onSubmit: function(data){
	    	  let{typeName,codeName}=data;
	    	  var xhttp;
				var url = "addproducttype.htm?typename="+typeName+"&code="+codeName;
				if (window.XMLHttpRequest) 
				{        
				    xhttp = new XMLHttpRequest(); 
				} 
				else
				{            
					xhttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
				xhttp.onreadystatechange= function() 
				{
					if (xhttp.readyState == 4)
					{
						let datars = xhttp.responseText;
		                document.querySelector('.showIntication').innerHTML=datars;
		                setTimeout(()=>{
		                    document.querySelector('.showIntication').innerHTML='';
		                    loadPage('tableCatalog','#table-catalog');
		                    resetType();
		                },500);
					}
				}
				xhttp.open("POST",url,true);
				xhttp.send();
	       }
	    });    
    Validator({
		      form:'#form-2',
		      messageElement:'.for-message',
		      rules:[
		        Validator.isRequired('#statusProduct'),
		        Validator.isRequired('#nameProduct'),
		        Validator.isRequired('#priceProduct'),
		        Validator.isRequired('#discountProduct'),
		        Validator.isRequired('#qualityProduct'),
		        Validator.isRequired('#imagesProduct'),
		        Validator.isRequired('#contentProduct'),
		        Validator.isRequired('#trademarkProduct')
		      ],
		      onSubmit: function(data){
		    	  let{codeProduct,statusProduct,nameProduct,
		    		  priceProduct,discountProduct,contentProduct,
		    		  qualityProduct,trademarkProduct}=data;
		    	  let images=Array.from(data.imagesProduct).reduce((acc,cur)=>{
		    		  return acc+" "+cur.name;
		    	  },'');
		    	  var xhttp;
					var url = "addproduct.htm?idType="+statusProduct+"&idProduct="+codeProduct+"&name="+nameProduct
							+"&content="+contentProduct+"&images="+images+"&soluog="+qualityProduct+"&discout="+discountProduct+
							"&trademark="+trademarkProduct+"&price="+priceProduct;
					if (window.XMLHttpRequest) 
					{        
					    xhttp = new XMLHttpRequest(); 
					} 
					else
					{            
						xhttp = new ActiveXObject("Microsoft.XMLHTTP");
					}
					xhttp.onreadystatechange= function() 
					{
						if (xhttp.readyState == 4)
						{
							let datars = xhttp.responseText;
							document.querySelector('.showIntication').innerHTML=datars;
							setTimeout(()=>{
			                    document.querySelector('.showIntication').innerHTML='';
			                    loadPage('tableProduct','#table-products');
			                    resetProduct();
			                },500);
						}
					}
					xhttp.open("POST",url,true);
					xhttp.send();
		       }
		    });
		    const search=document.querySelector("#search-type");
			search.addEventListener('change',function(e){
				e.preventDefault();
				var xhttp;
				var search = e.target.value;
				console.log(search);
				var url = "typeSearch.htm?search="+search;
				if (window.XMLHttpRequest) 
				{        
				    xhttp = new XMLHttpRequest(); 
				} 
				else
				{            
					xhttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
				xhttp.onreadystatechange= function() 
				{
					if (xhttp.readyState == 4)
					{
						var datars = xhttp.responseText;
						document.querySelector('#table-catalog').innerHTML=datars;
					}
				}
				xhttp.open("POST",url,true);
				xhttp.send();
			});
			$("#back-to-top").click(function(){return $("body, html").animate({scrollTop:0},400),!1});
			$(function(){$('[data-toggle="tooltip"]').tooltip()});
    </script>
</body>
</html>