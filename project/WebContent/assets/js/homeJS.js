const linksPage=document.querySelectorAll('.pagination-item__link-click');
const categorysItem=document.querySelectorAll('.category-item');
const categoryItemLink=document.querySelectorAll('.category-item__link');
const sortsList=document.querySelectorAll('.sort_list');
function run(){
	linksPage.forEach(function(btn){
		btn.addEventListener('click',function(e){
			e.preventDefault();
			var xhttp;
			var page = e.target.textContent;
			var url = "HomePage.htm?page="+page;
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
					var data = xhttp.responseText;
					var listProduct=document.getElementById("listProduct");
					listProduct.innerHTML=data;
					var list=e.target.parentElement.parentElement.children;
					for (var i = 0; i < list.length; i++) {
						list[i].classList.remove("pagination-item--active");
					}
					e.target.parentElement.classList.add("pagination-item--active");
				}
			}
			xhttp.open("POST",url,true);
			xhttp.send();
		});
	})
	categorysItem.forEach(function(item){
		item.addEventListener('click',function(e){
			e.preventDefault();
			var xhttp;
			var catalogId = e.target.getAttribute("id");
			console.log(catalogId);
			var url = "HomeCatalog.htm?catalogId="+catalogId;
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
					var data = xhttp.responseText;
					document.querySelector("#listProduct").innerHTML=data;
					categoryItemLink.forEach(function(link){
						link.style.color="#333333";
						link.style.fontSize="1.3rem";
					});
					e.target.style.color="#0000ff80";
					e.target.style.fontSize="1.5rem";
				}
			}
			xhttp.open("POST",url,true);
			xhttp.send();
		});
	});
	sortsList.forEach(function(btn){
		btn.addEventListener('click',function(e){
			e.preventDefault();
			var xhttp;
			var sortBy = e.target.getAttribute("id");
			var num=1;
			switch(sortBy){
				case 'newSort':{
					num=1;
					break;
				};
				case 'soldSort':{
					num=2;
					break;
				};
				case 'increated':{
					num=3;
					break;
				};
				case 'descrease':{
					num=4;
					break;
				};
			}
			var url = "HomeSort.htm?sortBy="+num;
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
					var data = xhttp.responseText;
					document.querySelector("#listProduct").innerHTML=data;
					sortsList.forEach(function(btn){
						btn.classList.remove("btn--primary");
					});
					e.target.classList.add("btn--primary");
				}
			}
			xhttp.open("POST",url,true);
			xhttp.send();
		});
	});
	const search=document.querySelector(".header__search-input");
	search.addEventListener('change',function(e){
		e.preventDefault();
		var xhttp;
		var search = e.target.value;
		console.log(search);
		var url = "HomeSearch.htm?search="+search;
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
				var data = xhttp.responseText;
				document.querySelector("#listProduct").innerHTML=data;
			}
		}
		xhttp.open("POST",url,true);
		xhttp.send();
	});
	//switch 2 page login and register
}
run();
(()=>{
    const toLogin=document.querySelector('#to_login');
    const toRegister=document.querySelector('#to_register');
    const loginCheck=document.querySelector('#login_check');
    const registerCheck=document.querySelector('#register_check');
    toLogin.addEventListener('click',()=>{
        loginCheck.checked=true;
        registerCheck.checked=false;
    });
    toRegister.addEventListener('click',()=>{
        loginCheck.checked=false;
        registerCheck.checked=true;
    })
})()