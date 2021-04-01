package BEAN;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Product {
	private int id;
	private int catalog_id;
	private String name;
	private String priceNew;
	private String priceOld;
	private String content;
	private String image_list;
	private int quantity;
	private int discount;
	private String trademark;
	private int sold;
	public Product(int id, int catalog_id, String name, String priceNew, String priceOld, String content,
			String image_list, int quantity, int discount, String trademark, int sold) {
		super();
		this.id = id;
		this.catalog_id = catalog_id;
		this.name = name;
		this.priceNew = priceNew;
		this.priceOld = priceOld;
		this.content = content;
		this.image_list = image_list;
		this.quantity = quantity;
		this.discount = discount;
		this.trademark = trademark;
		this.sold = sold;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(int catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPriceNew() {
		return priceNew;
	}
	public void setPriceNew(String priceNew) {
		this.priceNew = priceNew;
	}
	public String getPriceOld() {
		return priceOld;
	}
	public void setPriceOld(String priceOld) {
		this.priceOld = priceOld;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage_list() {
		return image_list;
	}
	public void setImage_list(String image_list) {
		this.image_list = image_list;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getTrademark() {
		return trademark;
	}
	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
	public static String formatVND(int price){
		Locale localeVN = new Locale("vi", "VN");
		DecimalFormat currencyVN =(DecimalFormat)DecimalFormat.getCurrencyInstance(localeVN);
		DecimalFormatSymbols formatSymbols=new DecimalFormatSymbols();
		formatSymbols.setCurrencySymbol("");
		formatSymbols.setGroupingSeparator('.');
		currencyVN.setDecimalFormatSymbols(formatSymbols);
		String str1 = currencyVN.format(price);
		return str1;
	}
}
