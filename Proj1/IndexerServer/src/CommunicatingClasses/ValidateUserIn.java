package CommunicatingClasses;

/**
 * This class sends the client's login information to the server
 * @author aconstan
 *
 */

public class ValidateUserIn {
	
	private String userName;
	private String password;
	
	/**
	 * Constructor 
	 * Encapsulates the info needed to log in, and sends it to the server
	 * 
	 * @param username
	 * @param password
	 */
	
	public ValidateUserIn (String username, String password)
	{
		this.userName = username;
		this.password = password;
	}
	
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



}
