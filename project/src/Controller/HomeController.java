package Controller;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import BEAN.Cart;
import BEAN.Catalog;
import BEAN.Product;
import BEAN.User;
import DB.DBConnection;
import jdk.nashorn.internal.runtime.Undefined;

@Controller
public class HomeController {
	private int itemInPage=50;
	private int pageCurrent=1;
	private int pageMax=0;
	private int catalogId=0;
	private String sortName="";
	private Cookie cookie;
	private User user=null;
	@RequestMapping(value="home")
	public String home(ModelMap model,HttpServletRequest req){
		catalogId=0;
		sortName="";
		Connection conn=DBConnection.createConnection();
		//add list catalog
		List<Catalog> listCatalog=new ArrayList<>();
		String sql="select * from CATALOG";
		try{
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Catalog catalog=new Catalog();
				catalog.setId(rs.getInt("id"));
				catalog.setName(rs.getString("name_catalog"));
				listCatalog.add(catalog);
			}
			model.addAttribute("listCatalog", listCatalog);
			//add list products
			List<Product> listProduct=new ArrayList<>();
			String sql1="select * from PRODUCT ORDER BY created_product DESC";
			PreparedStatement ps1=conn.prepareStatement(sql1);
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()){
				String priceOld=String.valueOf(Product.formatVND(rs1.getInt("price_old")));
				String priceNew=String.valueOf(Product.formatVND(rs1.getInt("price_new")));
				String[] imageList=rs1.getString("image_list").split(" ");
				String imgageShow=imageList[0];
				Product product=new Product(rs1.getInt("id"), rs1.getInt("catalog_id"), rs1.getString("name_product"),
						priceNew, priceOld, rs1.getString("content_product"), imgageShow, rs1.getInt("soluong"), 
						rs1.getInt("discount"), rs1.getString("trademark"), rs1.getInt("sold"));
				listProduct.add(product);
			} 	
			pageMax=listProduct.size()/itemInPage+(listProduct.size()%itemInPage>0?1:0);
			int[] page=new int[pageMax];
			for(int i=0;i<pageMax;i++){
				page[i]=i+1;
			}
			model.addAttribute("page", page);
			listProduct=listProduct.subList(0, itemInPage>listProduct.size()?listProduct.size():itemInPage);
			model.addAttribute("listProduct", listProduct);
			Cookie[] cookie=req.getCookies();
			if(cookie!=null){
				for(Cookie c:cookie){
					if(c.getName().equals("usernameid")){
						int userid=Integer.parseInt(c.getValue());
						System.out.println("userid: "+userid);
						List<Cart> listCart=new ArrayList<>();
						String sql2="select * from GIOHANG where user_id='"+userid+"'";
						PreparedStatement ps2=conn.prepareStatement(sql2);
						ResultSet rs2=ps2.executeQuery();
						int totalCartProduct=0;
						while(rs2.next()){
							String priceNew=String.valueOf(Product.formatVND(rs2.getInt("gia")));
							Cart cart=new Cart(rs2.getInt("product_id"),rs2.getString("tensp"), priceNew, rs2.getString("loaisp"), rs2.getInt("soluong"),rs2.getString("image_cart"),rs2.getBoolean("isbold"),"");
							listCart.add(cart);
							totalCartProduct+=rs2.getInt("soluong");
						};
						Statement stmt=conn.createStatement();
						ResultSet rsCheckUser=stmt.executeQuery("select * from TAIKHOAN where id="+userid);
						if(rsCheckUser.next()){
							user=new User(rsCheckUser.getInt("id"), rsCheckUser.getString("username"), 
									rsCheckUser.getString("email"), rsCheckUser.getString("address"), 
									rsCheckUser.getBoolean("authorize"));
						}
						model.addAttribute("listCart", listCart);
						model.addAttribute("listCartL", totalCartProduct);
						model.addAttribute("user", user);
						model.addAttribute("listCartL", totalCartProduct);
						break;
					}
				}
			}
			if(!model.containsAttribute("user")){
				model.addAttribute("user", null);
			}
			if(!model.containsAttribute("listCartL")){
				model.addAttribute("listCartL", 0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "homePage/home";
	}
	@RequestMapping(value="/product/{id}")
	public String product(ModelMap model ,@PathVariable Integer id,HttpServletRequest req){
		Connection conn=DBConnection.createConnection();
		String sql="select * from PRODUCT where id='"+id+"'";
		try{
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				model.addAttribute("priceInt", rs.getInt("price_new"));
				String priceOld=String.valueOf(Product.formatVND(rs.getInt("price_old")));
				String priceNew=String.valueOf(Product.formatVND(rs.getInt("price_new")));
				String[] imageList=rs.getString("image_list").split(" ");
				String imgageShow=imageList[0];
				model.addAttribute("imageAmount", imageList.length);
				Product product=new Product(rs.getInt("id"), rs.getInt("catalog_id"), rs.getString("name_product"),
						priceNew, priceOld, rs.getString("content_product"), imgageShow, rs.getInt("soluong"), 
						rs.getInt("discount"), rs.getString("trademark"), rs.getInt("sold"));
				model.addAttribute("product", product);
				model.addAttribute("imageList", imageList);
				String sql1="select * from CATALOG where id='"+rs.getInt("catalog_id")+"'";
				PreparedStatement ps1=conn.prepareStatement(sql1);
				ResultSet rs1=ps1.executeQuery();
				while(rs1.next()){
					String catalogName=rs1.getString("name_catalog");
					model.addAttribute("catalogName", catalogName);
				}
			}
			Cookie[] cookie=req.getCookies();
			if(cookie!=null){
				for(Cookie c:cookie){
					if(c.getName().equals("usernameid")){
						int userid=Integer.parseInt(c.getValue());
						System.out.println("userid: "+userid);
						List<Cart> listCart=new ArrayList<>();
						String sql2="select * from GIOHANG where user_id='"+userid+"'";
						PreparedStatement ps2=conn.prepareStatement(sql2);
						ResultSet rs2=ps2.executeQuery();
						int totalCartProduct=0;
						while(rs2.next()){
							String priceNew=String.valueOf(Product.formatVND(rs2.getInt("gia")));
							Cart cart=new Cart(rs2.getInt("product_id"),rs2.getString("tensp"), priceNew, rs2.getString("loaisp"), rs2.getInt("soluong"),rs2.getString("image_cart"),rs2.getBoolean("isbold"),"");
							listCart.add(cart);
							totalCartProduct+=rs2.getInt("soluong");
						};
						Statement stmt=conn.createStatement();
						ResultSet rsCheckUser=stmt.executeQuery("select * from TAIKHOAN where id="+userid);
						if(rsCheckUser.next()){
							user=new User(rsCheckUser.getInt("id"), rsCheckUser.getString("username"), 
									rsCheckUser.getString("email"), rsCheckUser.getString("address"), 
									rsCheckUser.getBoolean("authorize"));
						}
						model.addAttribute("listCart", listCart);
						model.addAttribute("listCartL", totalCartProduct);
						model.addAttribute("user", user);
						break;
					}
				}
			}
			if(!model.containsAttribute("user")){
				model.addAttribute("user", null);
			}
			if(!model.containsAttribute("listCartL")){
				model.addAttribute("listCartL", 0);
			}
			
		}catch(Exception e){
			System.out.println(e);
		}
		return "homePage/ProductPage";
	}
	@RequestMapping(value="/HomePage")
	public String homePage(HttpServletRequest request,ModelMap model){
		Connection conn=DBConnection.createConnection();
		pageCurrent=Integer.parseInt(request.getParameter("page"));
		List<Product> listProduct=new ArrayList<>();
		String sql1="select * from PRODUCT";
		try{
			PreparedStatement ps1=conn.prepareStatement(sql1);
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()){
				String priceOld=String.valueOf(Product.formatVND(rs1.getInt("price_old")));
				String priceNew=String.valueOf(Product.formatVND(rs1.getInt("price_new")));
				String[] imageList=rs1.getString("image_list").split(" ");
				String imgageShow=imageList[0];
				Product product=new Product(rs1.getInt("id"), rs1.getInt("catalog_id"), rs1.getString("name_product"),
						priceNew, priceOld, rs1.getString("content_product"), imgageShow, rs1.getInt("soluong"), 
						rs1.getInt("discount"), rs1.getString("trademark"), rs1.getInt("sold"));
				listProduct.add(product);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		pageMax=listProduct.size()/itemInPage+(listProduct.size()%itemInPage>0?1:0);
		int[] page=new int[pageMax];
		for(int i=0;i<pageMax;i++){
			page[i]=i+1;
		}
		model.addAttribute("page", page);
		int begin=(pageCurrent-1)*itemInPage;
		int end=(pageCurrent*itemInPage)>listProduct.size()?listProduct.size():(pageCurrent*itemInPage);
		listProduct=listProduct.subList(begin, end);
		model.addAttribute("listProduct", listProduct);
		return "homePage/listProduct";
	}
	@RequestMapping(value="/HomeCatalog")
	public String homeCatalog(HttpServletRequest request,ModelMap model) throws SQLException{
		pageCurrent=1;
		Connection conn=DBConnection.createConnection();
		List<Catalog> listCatalog=new ArrayList<>();
		String sql="select * from CATALOG";
		PreparedStatement ps=conn.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			Catalog catalog=new Catalog();
			catalog.setId(rs.getInt("id"));
			catalog.setName(rs.getString("name_catalog"));
			listCatalog.add(catalog);
		}
		model.addAttribute("listCatalog", listCatalog);
		List<Product> listProduct=new ArrayList<>();
		catalogId=Integer.parseInt(request.getParameter("catalogId"));
		String sql1="";
		if(sortName.equals("")){
			if(catalogId==0){
				sql1="select * from PRODUCT";
			}else{
				sql1="select * from PRODUCT where catalog_id='"+catalogId+"'";
			}
		}else{
			if(catalogId==0){
				sql1="select * from PRODUCT ORDER BY "+sortName;
			}else{
				sql1="select * from PRODUCT where catalog_id='"+catalogId+"' ORDER BY "+sortName;
			}
		}
		try{
			PreparedStatement ps1=conn.prepareStatement(sql1);
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()){
				String priceOld=String.valueOf(Product.formatVND(rs1.getInt("price_old")));
				String priceNew=String.valueOf(Product.formatVND(rs1.getInt("price_new")));
				String[] imageList=rs1.getString("image_list").split(" ");
				String imgageShow=imageList[0];
				Product product=new Product(rs1.getInt("id"), rs1.getInt("catalog_id"), rs1.getString("name_product"),
						priceNew, priceOld, rs1.getString("content_product"), imgageShow, rs1.getInt("soluong"), 
						rs1.getInt("discount"), rs1.getString("trademark"), rs1.getInt("sold"));
				listProduct.add(product);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		pageMax=listProduct.size()/itemInPage+(listProduct.size()%itemInPage>0?1:0);
		int[] page=new int[pageMax];
		for(int i=0;i<pageMax;i++){
			page[i]=i+1;
		}
		model.addAttribute("page", page);
		int begin=(pageCurrent-1)*itemInPage;
		int end=(pageCurrent*itemInPage)>listProduct.size()?listProduct.size():(pageCurrent*itemInPage);
		listProduct=listProduct.subList(begin, end);
		model.addAttribute("listProduct", listProduct);
		return "homePage/listProduct";
	}
	@RequestMapping(value="/HomeSort")
	public String homeSort(HttpServletRequest request,ModelMap model) throws SQLException{
		Connection conn=DBConnection.createConnection();
		List<Catalog> listCatalog=new ArrayList<>();
		String sql="select * from CATALOG";
		PreparedStatement ps=conn.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			Catalog catalog=new Catalog();
			catalog.setId(rs.getInt("id"));
			catalog.setName(rs.getString("name_catalog"));
			listCatalog.add(catalog);
		}
		model.addAttribute("listCatalog", listCatalog);
		List<Product> listProduct=new ArrayList<>();
		int sortBy=Integer.parseInt(request.getParameter("sortBy"));
		
		switch(sortBy){
			case 1:{
				sortName="created_product DESC";
				break;
			}
			case 2:{
				sortName="sold DESC";
				break;
			}
			case 3:{
				sortName="price_new";
				break;
			}
			case 4:{
				sortName="price_new DESC";
				break;
			}
		}
		String sql1="";
		if(catalogId==0){
			sql1="select * from PRODUCT ORDER BY "+sortName;
		}else{
			sql1="select * from PRODUCT where catalog_id='"+catalogId+"' ORDER BY "+sortName;
		}
		try{
			PreparedStatement ps1=conn.prepareStatement(sql1);
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()){
				String priceOld=String.valueOf(Product.formatVND(rs1.getInt("price_old")));
				String priceNew=String.valueOf(Product.formatVND(rs1.getInt("price_new")));
				String[] imageList=rs1.getString("image_list").split(" ");
				String imgageShow=imageList[0];
				Product product=new Product(rs1.getInt("id"), rs1.getInt("catalog_id"), rs1.getString("name_product"),
						priceNew, priceOld, rs1.getString("content_product"), imgageShow, rs1.getInt("soluong"), 
						rs1.getInt("discount"), rs1.getString("trademark"), rs1.getInt("sold"));
				listProduct.add(product);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("listProduct", listProduct);
		return "homePage/listProduct";
	}
	@RequestMapping(value="/HomeSearch")
	public String homeSearch(HttpServletRequest request,ModelMap model) throws UnsupportedEncodingException{
		Connection conn=DBConnection.createConnection();
		request.setCharacterEncoding("UTF-8");
		String search=request.getParameter("search");
		List<Product> listProduct=new ArrayList<>();
		String sql1="select * from PRODUCT where name_product like '%"+search+"%'";
		try{
			PreparedStatement ps1=conn.prepareStatement(sql1);
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()){
				String priceOld=String.valueOf(Product.formatVND(rs1.getInt("price_old")));
				String priceNew=String.valueOf(Product.formatVND(rs1.getInt("price_new")));
				String[] imageList=rs1.getString("image_list").split(" ");
				String imgageShow=imageList[0];
				Product product=new Product(rs1.getInt("id"), rs1.getInt("catalog_id"), rs1.getString("name_product"),
						priceNew, priceOld, rs1.getString("content_product"), imgageShow, rs1.getInt("soluong"), 
						rs1.getInt("discount"), rs1.getString("trademark"), rs1.getInt("sold"));
				listProduct.add(product);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("listProduct", listProduct);
		return "homePage/listProduct";
	}
	@RequestMapping(value="/loginPage")
	public String loginPage(){
		return "testLogin/loginPage";
	}
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(ModelMap model,HttpServletRequest request) throws SQLException{
		String email=request.getParameter("email");
		String username=request.getParameter("username");
		String address=request.getParameter("address");
		String password=request.getParameter("password");
		System.out.println("email:"+email);
		System.out.println("password:"+password);
		Connection conn=DBConnection.createConnection();
		Statement stmt=conn.createStatement();
		ResultSet rsCheckUser=stmt.executeQuery("select * from TAIKHOAN where email='"+email+"'");
		if(rsCheckUser.next()){
			model.addAttribute("check",false);
			model.addAttribute("msg", "Email đã tồn tại!");
			return "commons/Notification";
		}
		int rsKQ=stmt.executeUpdate("insert into TAIKHOAN values('"+username+"','"+password+"',0,'"+email+"','"+address+"')");
		System.out.println(rsKQ);
		model.addAttribute("check",true);
		model.addAttribute("msg", "Bạn đã đăng ký thành công!");
		return "commons/Notification";
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(ModelMap model,HttpServletRequest request, HttpServletResponse res,HttpServletResponse resp) throws SQLException{
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		Connection conn=DBConnection.createConnection();
		Statement stmt=conn.createStatement();
		ResultSet rsCheckUser=stmt.executeQuery("select * from TAIKHOAN where email='"+email+"' and password='"+password+"'");
		if(rsCheckUser.next()){
			user=new User(rsCheckUser.getInt("id"), rsCheckUser.getString("username"), 
					rsCheckUser.getString("email"), rsCheckUser.getString("address"), 
					rsCheckUser.getBoolean("authorize"));
			res.setStatus(HttpServletResponse.SC_OK);
			cookie=new Cookie("usernameid", String.valueOf(rsCheckUser.getInt("id")));
			cookie.setMaxAge(30000);
			resp.addCookie(cookie);
			List<Cart> listCart=new ArrayList<>();
			String sql2="select * from GIOHANG where user_id="+rsCheckUser.getInt("id");
			PreparedStatement ps2=conn.prepareStatement(sql2);
			ResultSet rs2=ps2.executeQuery();
			int totalCartProduct=0;
			while(rs2.next()){
				String priceNew=String.valueOf(Product.formatVND(rs2.getInt("gia")));
				Cart cart=new Cart(rs2.getInt("product_id"),rs2.getString("tensp"), priceNew, rs2.getString("loaisp"), rs2.getInt("soluong"),rs2.getString("image_cart"),rs2.getBoolean("isbold"),"");
				listCart.add(cart);
				totalCartProduct+=rs2.getInt("soluong");
			};
			model.addAttribute("listCart", listCart);
			model.addAttribute("listCartL", totalCartProduct);
			model.addAttribute("user", user);
			model.addAttribute("listCartL", totalCartProduct);
			return home(model,request);
		}
		model.addAttribute("check",false);
		model.addAttribute("msg", "Đăng nhập thất bại!");
		res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return "commons/Notification";
	}
	@RequestMapping(value="/logout")
	public String logout(ModelMap model,HttpServletRequest req,HttpServletResponse resp){
		Cookie[] cookie=req.getCookies();
		if(cookie!=null){
			for(Cookie c:cookie){
				if(c.getName().equals("usernameid")){
					c.setMaxAge(0);
					resp.addCookie(c);
				}
			}
		}
		return "redirect:/home.htm";
	}
	@RequestMapping(value="cartPage")
	public String cartShow(ModelMap model,HttpServletRequest req,HttpServletResponse resp) throws SQLException{
		Connection conn=DB.DBConnection.createConnection();
		Cookie[] cookie=req.getCookies();
		if(cookie!=null){
			for(Cookie c:cookie){
				if(c.getName().equals("usernameid")){
					int userid=Integer.parseInt(c.getValue());
					System.out.println("userid: "+userid);
					List<Cart> listCart=new ArrayList<>();
					String sql2="select * from GIOHANG where user_id='"+userid+"'";
					PreparedStatement ps2=conn.prepareStatement(sql2);
					ResultSet rs2=ps2.executeQuery();
					int totalCartProduct=0;
					int totalMoneyPay=0;
					while(rs2.next()){
						String priceNew=String.valueOf(Product.formatVND(rs2.getInt("gia")));
						if(rs2.getBoolean("isbold")){
							totalMoneyPay+=rs2.getInt("gia")*rs2.getInt("soluong");
						}
						String priceSL=String.valueOf(Product.formatVND(rs2.getInt("gia")*rs2.getInt("soluong")));
						Cart cart=new Cart(rs2.getInt("product_id"),rs2.getString("tensp"), priceNew, rs2.getString("loaisp"), rs2.getInt("soluong"),rs2.getString("image_cart"),rs2.getBoolean("isbold"),priceSL);
						listCart.add(cart);
						totalCartProduct+=rs2.getInt("soluong");
					};
					Statement stmt=conn.createStatement();
					ResultSet rsCheckUser=stmt.executeQuery("select * from TAIKHOAN where id="+userid);
					if(rsCheckUser.next()){
						user=new User(rsCheckUser.getInt("id"), rsCheckUser.getString("username"), 
								rsCheckUser.getString("email"), rsCheckUser.getString("address"), 
								rsCheckUser.getBoolean("authorize"));
					}
					model.addAttribute("listCart", listCart);
					model.addAttribute("listCartL", totalCartProduct);
					model.addAttribute("user", user);
					model.addAttribute("listCartL", totalCartProduct);
					String totalMoneyPayStr=String.valueOf(Product.formatVND(totalMoneyPay));
					model.addAttribute("totalMoneyPay", totalMoneyPayStr);
					return "homePage/cartPage";
				}
			}
		}
		return "redirect:/home.htm";
	}
	@RequestMapping(value="/addCart")
	public String addCart(ModelMap model,HttpServletRequest req) throws SQLException{
		if(req.getParameter("idUser")==null){
			model.addAttribute("check",false);
			model.addAttribute("msg", "Bạn cần đăng nhập vào tài khoản thì mới có thể mua hàng được");
			return "commons/Notification";
		}
		int idUser=Integer.parseInt(req.getParameter("idUser"));
		int idProduct=Integer.parseInt(req.getParameter("idProduct"));
		int number=Integer.parseInt(req.getParameter("number"));
		int price=Integer.parseInt(req.getParameter("price"));
		String name=req.getParameter("name");
		String loai=req.getParameter("loai");
		String image=req.getParameter("image");
		System.out.println(idUser+"\t"+idProduct+"\t"+number+"\t"+price+"\t"+name+"\t"+loai+"\t"+image);
		Connection conn=DB.DBConnection.createConnection();
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery("select * from GIOHANG where product_id="+idProduct);
		if(rs.next()){
			int soluong=number + rs.getInt("soluong");
			ResultSet rs1=stmt.executeQuery("select * from PRODUCT where id="+idProduct);
			if(rs1.next()){
				if(rs1.getInt("soluong")<soluong){
					model.addAttribute("check",false);
					model.addAttribute("msg", "Kho hàng của sản phẩm không đủ.");
					return "commons/Notification";
				}
			}
			stmt.executeUpdate("update GIOHANG set soluong="+soluong+" where product_id="+idProduct+" and user_id="+idUser);
		}else{
			stmt.executeUpdate("insert into GIOHANG values("+idProduct+","+idUser+
					","+number+","+price+",N'"+name+"',N'"+loai+"','"+image+"', 'true')");
		}
		int check=Integer.parseInt(req.getParameter("check"));
		System.out.println("check "+check);
		if(check==1){
			return "redirect:/cartPage.htm";
		}
		model.addAttribute("check",true);
		model.addAttribute("msg", "Thêm sản phẩm vào giỏ thành công!");
		return "commons/Notification";
	}
	@RequestMapping(value="checkbox")
	public String checkBox(ModelMap model,HttpServletRequest req) throws SQLException{
		int type=Integer.parseInt(req.getParameter("type"));
		Connection conn=DB.DBConnection.createConnection();
		Statement stmt=conn.createStatement();
		if(type==0){
			stmt.executeUpdate("update GIOHANG set isbold='false'");
		}else{
			stmt.executeUpdate("update GIOHANG set isbold='true'");
		}
		Cookie[] cookie=req.getCookies();
		if(cookie!=null){
			for(Cookie c:cookie){
				if(c.getName().equals("usernameid")){
					int userid=Integer.parseInt(c.getValue());
					List<Cart> listCart=new ArrayList<>();
					String sql2="select * from GIOHANG where user_id='"+userid+"'";
					PreparedStatement ps2=conn.prepareStatement(sql2);
					ResultSet rs2=ps2.executeQuery();
					int totalMoneyPay=0;
					while(rs2.next()){
						String priceSL=String.valueOf(Product.formatVND(rs2.getInt("gia")*rs2.getInt("soluong")));
						String priceNew=String.valueOf(Product.formatVND(rs2.getInt("gia")));
						Cart cart=new Cart(rs2.getInt("product_id"),rs2.getString("tensp"), priceNew, rs2.getString("loaisp"), rs2.getInt("soluong"),rs2.getString("image_cart"),rs2.getBoolean("isbold"),priceSL);
						listCart.add(cart);
						if(rs2.getBoolean("isbold")){
							totalMoneyPay+=rs2.getInt("gia")*rs2.getInt("soluong");
						}
					};
					model.addAttribute("listCart", listCart);
					model.addAttribute("type", type);
					model.addAttribute("idUser", userid);
					String totalMoneyPayStr=String.valueOf(Product.formatVND(totalMoneyPay));
					model.addAttribute("totalMoneyPay", totalMoneyPayStr);
				}
			}
		}
		return "homePage/listCart";
	}
	@RequestMapping(value="changecheckbox")
	public String changeCheckbox(ModelMap model, HttpServletRequest req) throws SQLException{
		int idProduct=Integer.parseInt(req.getParameter("idproduct"));
		int idUser=Integer.parseInt(req.getParameter("iduser"));
		Connection conn=DB.DBConnection.createConnection();
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery("select * from GIOHANG where user_id="+idUser+" and product_id="+idProduct);
		boolean check=true;
		if(rs.next()){
			check=!rs.getBoolean("isbold");
		}
		stmt.executeUpdate("update GIOHANG set isbold='"+check+"' where user_id="+idUser+" and product_id="+idProduct);
		List<Cart> listCart=new ArrayList<>();
		ResultSet rs2=stmt.executeQuery("select * from GIOHANG where user_id="+idUser);
		int totalMoneyPay=0;
		while(rs2.next()){
			String priceSL=String.valueOf(Product.formatVND(rs2.getInt("gia")*rs2.getInt("soluong")));
			String priceNew=String.valueOf(Product.formatVND(rs2.getInt("gia")));
			Cart cart=new Cart(rs2.getInt("product_id"),rs2.getString("tensp"), priceNew, rs2.getString("loaisp"), rs2.getInt("soluong"),rs2.getString("image_cart"),rs2.getBoolean("isbold"),priceSL);
			listCart.add(cart);
			if(rs2.getBoolean("isbold")){
				totalMoneyPay+=rs2.getInt("gia")*rs2.getInt("soluong");
			}
		};
		model.addAttribute("listCart", listCart);
		model.addAttribute("type", 0);
		model.addAttribute("idUser", idUser);
		String totalMoneyPayStr=String.valueOf(Product.formatVND(totalMoneyPay));
		model.addAttribute("totalMoneyPay", totalMoneyPayStr);
		return "homePage/listCart";
	}
	@RequestMapping(value="addslcart")
	public String addSLCart(ModelMap model, HttpServletRequest req,HttpServletResponse res) throws SQLException{
		int type=Integer.parseInt(req.getParameter("type"));
		int idProduct=Integer.parseInt(req.getParameter("idProduct"));
		int idUser=Integer.parseInt(req.getParameter("idUser"));
		System.out.println(type+idProduct+idUser);
		Connection conn=DB.DBConnection.createConnection();
		String sql="select * from GIOHANG where user_id="+idUser+" and product_id="+idProduct;
		PreparedStatement ps=conn.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		int soluongcart=0,soluongproduct=0;
		if(rs.next()){
			soluongcart=rs.getInt("soluong");
		}
		String sql1="select * from PRODUCT where id="+idProduct+"";
		PreparedStatement ps1=conn.prepareStatement(sql1);
		ResultSet rs1=ps1.executeQuery();
		if(rs1.next()){
			soluongproduct=rs1.getInt("soluong");
		}
		if(true){
			if(type==1){
				soluongcart=soluongcart==soluongproduct?soluongcart:soluongcart+1;
				 Statement stmt = conn.createStatement();
			     stmt.executeUpdate("update GIOHANG SET soluong="+soluongcart+" where product_id="+idProduct+" and user_id="+idUser);
			}else{
				 Statement stmt = conn.createStatement();
				 soluongcart=soluongcart==1?1:soluongcart-1;
			     stmt.executeUpdate("update GIOHANG SET soluong="+soluongcart+" where product_id="+idProduct+" and user_id="+idUser);
			}
			List<Cart> listCart=new ArrayList<>();
			String sql2="select * from GIOHANG where user_id="+idUser;
			PreparedStatement ps2=conn.prepareStatement(sql2);
			ResultSet rs2=ps2.executeQuery();
			int totalMoneyPay=0;
			while(rs2.next()){
				String priceSL=String.valueOf(Product.formatVND(rs2.getInt("gia")*rs2.getInt("soluong")));
				String priceNew=String.valueOf(Product.formatVND(rs2.getInt("gia")));
				Cart cart=new Cart(rs2.getInt("product_id"),rs2.getString("tensp"), priceNew, rs2.getString("loaisp"), rs2.getInt("soluong"),rs2.getString("image_cart"),rs2.getBoolean("isbold"),priceSL);
				listCart.add(cart);
				if(rs2.getBoolean("isbold")){
					totalMoneyPay+=rs2.getInt("gia")*rs2.getInt("soluong");
				}
			};
			model.addAttribute("listCart", listCart);
			model.addAttribute("type", 0);
			model.addAttribute("idUser", idUser);
			String totalMoneyPayStr=String.valueOf(Product.formatVND(totalMoneyPay));
			model.addAttribute("totalMoneyPay", totalMoneyPayStr);
			res.setStatus(HttpServletResponse.SC_OK);
			return "homePage/listCart";
		}
		res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		model.addAttribute("check",false);
		model.addAttribute("msg", "Kho hàng của sản phẩm không đủ.");
		return "commons/Notification";
	}
	@RequestMapping(value="removecart")
	public String removeCart(ModelMap model,HttpServletRequest req) throws SQLException{
		int idProduct=Integer.parseInt(req.getParameter("idproduct"));
		int idUser=Integer.parseInt(req.getParameter("iduser"));
		Connection conn=DB.DBConnection.createConnection();
		Statement stmt=conn.createStatement();
		stmt.executeUpdate("delete from GIOHANG where product_id="+idProduct+" and user_id="+idUser);
		List<Cart> listCart=new ArrayList<>();
		String sql2="select * from GIOHANG where user_id="+idUser;
		PreparedStatement ps2=conn.prepareStatement(sql2);
		ResultSet rs2=ps2.executeQuery();
		int totalMoneyPay=0;
		while(rs2.next()){
			String priceSL=String.valueOf(Product.formatVND(rs2.getInt("gia")*rs2.getInt("soluong")));
			String priceNew=String.valueOf(Product.formatVND(rs2.getInt("gia")));
			Cart cart=new Cart(rs2.getInt("product_id"),rs2.getString("tensp"), priceNew, rs2.getString("loaisp"), rs2.getInt("soluong"),rs2.getString("image_cart"),rs2.getBoolean("isbold"),priceSL);
			listCart.add(cart);
			if(rs2.getBoolean("isbold")){
				totalMoneyPay+=rs2.getInt("gia")*rs2.getInt("soluong");
			}
		};
		model.addAttribute("listCart", listCart);
		model.addAttribute("type", 0);
		model.addAttribute("idUser", idUser);
		String totalMoneyPayStr=String.valueOf(Product.formatVND(totalMoneyPay));
		model.addAttribute("totalMoneyPay", totalMoneyPayStr);
		return "homePage/listCart";
	}
	@RequestMapping(value="paymoney")
	public String payMoney(ModelMap model,HttpServletRequest req) throws SQLException{
		int idUser=Integer.parseInt(req.getParameter("iduser"));
		Connection conn=DB.DBConnection.createConnection();
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery("select * from GIOHANG where user_id="+idUser);
		List<Integer> cartList=new ArrayList();
		List<Integer> cartProduct=new ArrayList();
		while(rs.next()){
			int slCart=rs.getInt("soluong");
			cartList.add(slCart);
			int idProduct=rs.getInt("product_id");
			cartProduct.add(idProduct);
		}
		for (int i = 0; i < cartList.size(); i++) {
			int slcart=cartList.get(i);
			int idProduct=cartProduct.get(i);
			ResultSet rs1=stmt.executeQuery("select * from PRODUCT where id="+idProduct);
			if(rs1.next()){
				System.out.println("dsadas");
				int soluong=rs1.getInt("soluong")-slcart;
				stmt.executeUpdate("update PRODUCT SET soluong="+soluong+" where id="+idProduct);
			}
		}
		stmt.executeUpdate("delete from GIOHANG where user_id="+idUser +" and isbold='true'");
		model.addAttribute("check",false);
		model.addAttribute("msg", "Mua hàng thành công!");
		return "commons/Notification";
	}
	@RequestMapping(value="showNotification")
	public String showNotication(ModelMap model,HttpServletRequest req){
		String msg=req.getParameter("msg");
		model.addAttribute("check",true);
		model.addAttribute("msg", msg);
		return "commons/Notification";
	}
}
