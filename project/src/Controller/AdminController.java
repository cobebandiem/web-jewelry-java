package Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import BEAN.Catalog;
import BEAN.Product;

@Controller
public class AdminController {
	@RequestMapping(value="admin")
	public String admin(ModelMap model,HttpServletRequest req){
		Cookie[] cookie=req.getCookies();
		if(cookie!=null){
			for(Cookie c:cookie){
				if(c.getName().equals("usernameid")){
					int userid=Integer.parseInt(c.getValue());
					Connection conn=DB.DBConnection.createConnection();
					Statement stmt;
					try {
						stmt = conn.createStatement();
						ResultSet rsCheckUser=stmt.executeQuery("select * from TAIKHOAN where id="+userid);
						if(rsCheckUser.next()){
							if(rsCheckUser.getBoolean("authorize")==true){
								List<Catalog> listCatalog=new ArrayList<>();
								ResultSet rsLoai=stmt.executeQuery("select * from CATALOG");
								while(rsLoai.next()){
									Catalog catalog=new Catalog();
									catalog.setId(rsLoai.getInt("id"));
									catalog.setName(rsLoai.getString("name_catalog"));
									listCatalog.add(catalog);
								}
								model.addAttribute("listCatalog", listCatalog);
								List<Product> listProduct=new ArrayList<>();
								ResultSet rsproduct=stmt.executeQuery("select * from PRODUCT");
								while(rsproduct.next()){
									String priceOld=String.valueOf(Product.formatVND(rsproduct.getInt("price_old")));
									String priceNew=String.valueOf(Product.formatVND(rsproduct.getInt("price_new")));
									Product product=new Product(rsproduct.getInt("id"), rsproduct.getInt("catalog_id"), rsproduct.getString("name_product"),
											priceNew, priceOld, rsproduct.getString("content_product"), rsproduct.getString("image_list"), rsproduct.getInt("soluong"), 
											rsproduct.getInt("discount"), rsproduct.getString("trademark"), rsproduct.getInt("sold"));
									listProduct.add(product);
								}
								model.addAttribute("listProduct", listProduct);
								return "adimPage/admin";
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}	
				}
			}
		}
		HomeController homeController=new HomeController();
		return "redirect:/home.htm";
	}
	@RequestMapping(value="addproducttype")
	public String addProductType(ModelMap model,HttpServletRequest req) throws ServletException, IOException, SQLException{
//		req.setCharacterEncoding("UTF-8");
		String typeName=req.getParameter("typename");
		String codeName=req.getParameter("code");
		Connection conn=DB.DBConnection.createConnection();
		if(codeName==""){
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from CATALOG where name_catalog=N'"+typeName+"'");
			if(rs.next()){
				model.addAttribute("msg", "Tên loại sản phẩm đã tồn tại!");
				model.addAttribute("check",false);
				return "commons/Notification";
				
			}else{
				try {
					stmt.executeUpdate("insert into CATALOG values(N'"+typeName+"')");
					model.addAttribute("msg", "Thêm loại sản phẩm thành công!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}else{
			System.out.println(codeName);
			try {
				int id=Integer.parseInt(codeName);
				System.out.println("id  "+id);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("update CATALOG set name_catalog=N'"+typeName+"' where id="+id);
				model.addAttribute("msg", "Sửa loại sản phẩm thành công!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("check",true);
		return "commons/Notification";
	}
	@RequestMapping(value="tableCatalog")
	public String tableCatalog(ModelMap model){
		Connection conn=DB.DBConnection.createConnection();
		try {
			List<Catalog> listCatalog=new ArrayList<>();
			Statement stmt=conn.createStatement();
			ResultSet rsLoai=stmt.executeQuery("select * from CATALOG");
			while(rsLoai.next()){
				Catalog catalog=new Catalog();
				catalog.setId(rsLoai.getInt("id"));
				catalog.setName(rsLoai.getString("name_catalog"));
				listCatalog.add(catalog);
			}
			model.addAttribute("listCatalog", listCatalog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "adimPage/tableCatalog";
	}
	@RequestMapping(value="removetype")
	public String removeType(ModelMap model,HttpServletRequest req){
		String id=req.getParameter("id");
		Connection conn=DB.DBConnection.createConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from PRODUCT where catalog_id="+id);
			if(rs.next()){
				model.addAttribute("msg", "Không thể xóa, tồn lại sản phẩm của loại!");
				model.addAttribute("check",false);
				return "commons/Notification";
			}
			stmt.executeUpdate("delete from catalog where id="+id);
			model.addAttribute("check",true);
			model.addAttribute("msg", "Xóa loại sản phẩm thành công!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "commons/Notification";
	}
	@RequestMapping(value="addproduct")
	public String addProduct(ModelMap model,HttpServletRequest req){
		String idProduct=req.getParameter("idProduct");
		int idType=Integer.parseInt(req.getParameter("idType"));
		String name=req.getParameter("name");
		System.out.println("name -"+name);
		int priceOld=Integer.parseInt(req.getParameter("price"));
		String content=req.getParameter("content");
		System.out.println("content: -"+content);
		String images=req.getParameter("images");
		int soluong=Integer.parseInt(req.getParameter("soluog"));
		int discount=Integer.parseInt(req.getParameter("discout"));
		int priceNew=priceOld*(100-discount)/100;
		String trademark=req.getParameter("trademark");
		Connection conn=DB.DBConnection.createConnection();
		try {
			Statement stmt = conn.createStatement();
			if(idProduct==""){
				ResultSet rs=stmt.executeQuery("select * from PRODUCT where CONVERT(NVARCHAR(MAX), name_product)=N'"+name+"'");
				if(rs.next()){
					model.addAttribute("msg", "Tên sản phẩm đã tồn tại!");
					model.addAttribute("check",false);
					return "commons/Notification";
				}else{
					stmt.executeUpdate("insert into PRODUCT values("+idType+",N'"+name+"',"
							+priceNew+",N'"+content+"','"+images.trim()+"',"+soluong+","+discount+",N'"+trademark+"',"+0+",GETDATE(),"+priceOld+")");
								model.addAttribute("check",true);
								model.addAttribute("msg", "Thêm sản phẩm thành công!");
								return "commons/Notification";
				}
			}else{
				int id=Integer.parseInt(idProduct);
				stmt.executeUpdate("update PRODUCT set catalog_id="+idType+", name_product=N'"+name+"', price_new="+priceNew+""
						+ ", content_product=N'"+content+"', image_list='"+images+"', soluong="+soluong+", discount="+discount+", trademark=N'"+trademark+"',"
								+ " price_old="+priceOld+" where id="+id);
				model.addAttribute("check",true);
				model.addAttribute("msg", "Sửa sản phẩm thành công!");
				return "commons/Notification";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		model.addAttribute("check",false);
		model.addAttribute("msg", "Thêm/sửa thất bại!");
		return "commons/Notification";
	}
	@RequestMapping(value="tableProduct")
	public String tableProduct(ModelMap model){
		Connection conn=DB.DBConnection.createConnection();
		try {
			List<Product> listProduct=new ArrayList<>();
			Statement stmt=conn.createStatement();
			ResultSet rsproduct=stmt.executeQuery("select * from PRODUCT");
			while(rsproduct.next()){
				String priceOld=String.valueOf(Product.formatVND(rsproduct.getInt("price_old")));
				String priceNew=String.valueOf(Product.formatVND(rsproduct.getInt("price_new")));
				Product product=new Product(rsproduct.getInt("id"), rsproduct.getInt("catalog_id"), rsproduct.getString("name_product"),
						priceNew, priceOld, rsproduct.getString("content_product"), rsproduct.getString("image_list"), rsproduct.getInt("soluong"), 
						rsproduct.getInt("discount"), rsproduct.getString("trademark"), rsproduct.getInt("sold"));
				listProduct.add(product);
			}
			model.addAttribute("listProduct", listProduct);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "adimPage/tableProduct";
	}
	@RequestMapping(value="removeproduct")
	public String removeProduct(ModelMap model,HttpServletRequest req){
		String id=req.getParameter("id");
		Connection conn=DB.DBConnection.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("delete from product where id="+id);
			model.addAttribute("check",true);
			model.addAttribute("msg", "Xóa sản phẩm thành công!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "commons/Notification";
	}
	@RequestMapping(value="typeSearch")
	public String typeSearch(ModelMap model,HttpServletRequest req) throws UnsupportedEncodingException{
		req.setCharacterEncoding("UTF-8");
		String search=req.getParameter("search");
		Connection conn=DB.DBConnection.createConnection();
		try {
			List<Catalog> listCatalog=new ArrayList<>();
			Statement stmt=conn.createStatement();
			ResultSet rsLoai=stmt.executeQuery("select * from CATALOG where name_catalog like '%"+search+"%'");
			while(rsLoai.next()){
				Catalog catalog=new Catalog();
				catalog.setId(rsLoai.getInt("id"));
				catalog.setName(rsLoai.getString("name_catalog"));
				listCatalog.add(catalog);
			}
			model.addAttribute("listCatalog", listCatalog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "adimPage/tableCatalog";
	}
	@RequestMapping(value="productSearch")
	public String productSearch(ModelMap model,HttpServletRequest req) throws UnsupportedEncodingException{
		req.setCharacterEncoding("UTF-8");
		String search=req.getParameter("search");
		Connection conn=DB.DBConnection.createConnection();
		try {
			List<Product> listProduct=new ArrayList<>();
			Statement stmt=conn.createStatement();
			ResultSet rsproduct=stmt.executeQuery("select * from PRODUCT where name_product like '%"+search+"%'");
			while(rsproduct.next()){
				String priceOld=String.valueOf(Product.formatVND(rsproduct.getInt("price_old")));
				String priceNew=String.valueOf(Product.formatVND(rsproduct.getInt("price_new")));
				Product product=new Product(rsproduct.getInt("id"), rsproduct.getInt("catalog_id"), rsproduct.getString("name_product"),
						priceNew, priceOld, rsproduct.getString("content_product"), rsproduct.getString("image_list"), rsproduct.getInt("soluong"), 
						rsproduct.getInt("discount"), rsproduct.getString("trademark"), rsproduct.getInt("sold"));
				listProduct.add(product);
			}
			model.addAttribute("listProduct", listProduct);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "adimPage/tableProduct";
	}
}
