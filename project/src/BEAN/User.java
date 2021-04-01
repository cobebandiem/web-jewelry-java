package BEAN;

public class User {
	private int id;
	private String username;
	private String email;
	private String address;
	private Boolean authorize;
	public User(int id, String username, String email, String address, Boolean authorize) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.address = address;
		this.authorize = authorize;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getAuthorize() {
		return authorize;
	}
	public void setAuthorize(Boolean authorize) {
		this.authorize = authorize;
	}
}
