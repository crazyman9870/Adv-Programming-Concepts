package Shared.CommunicatingClasses;

import Shared.ModelClasses.Credentials;

/**
 * This class sends the client's login information to the server
 * @author aconstan
 *
 */

public class ValidateUserIn {
	
	private String username;
	private String password;
	
	public ValidateUserIn (String username, String password)
	{
		this.username = username;
		this.password = password;
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
