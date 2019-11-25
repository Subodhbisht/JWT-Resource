package sb.resource.jwtresource.Bean;

public class UserResourceBean {	
	
	public UserResourceBean(String userName, String fullName, String country, String roles) {
		this.userName = userName;
		this.fullName = fullName;
		this.country = country;
		this.roles = roles;
	}

	private String userName;
	
	private String password;
	
	private String fullName;
	
	private String country;
	
	private String roles;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
}
