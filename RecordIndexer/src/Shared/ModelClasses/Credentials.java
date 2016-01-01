package Shared.ModelClasses;

public class Credentials {
	
	private String username;
	private String password;
	
	public Credentials() {
		username = null;
		password = null;
	}
	
	public Credentials(Credentials c) {
		username = c.getUsername();
		password = c.getPassword();
	}
	
	public Credentials(String un, String pass) {
		username = un;
		password = pass;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
