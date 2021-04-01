package BEAN;

public class Cart {
	private Integer id;
	private String name;
	private String newPrice;
	private String loai;
	private Integer soluong;
	private String image;
	private Boolean isbold;
	private String priceSL;

	public Cart(Integer id, String name, String newPrice, String loai, Integer soluong, String image, Boolean isbold,
			String priceSL) {
		super();
		this.id = id;
		this.name = name;
		this.newPrice = newPrice;
		this.loai = loai;
		this.soluong = soluong;
		this.image = image;
		this.isbold = isbold;
		this.priceSL = priceSL;
	}
	public Boolean getIsbold() {
		return isbold;
	}
	public void setIsbold(Boolean isbold) {
		this.isbold = isbold;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}
	public String getLoai() {
		return loai;
	}
	public void setLoai(String loai) {
		this.loai = loai;
	}
	public Integer getSoluong() {
		return soluong;
	}
	public void setSoluong(Integer soluong) {
		this.soluong = soluong;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPriceSL() {
		return priceSL;
	}
	public void setPriceSL(String priceSL) {
		this.priceSL = priceSL;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
