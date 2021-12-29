package model;

public class User {
	private int userId;
	private String userName;
    private String passWord;
    private String authority;
	public User(int userId, String userName, String passWord, String authority) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.passWord = passWord;
		this.authority = authority;
	}
	public User() {
		super();
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
    
    
    
    
}
