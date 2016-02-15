package CommunicatingClasses;

/**
 * This class sends the request from the client to get info on the projects from the database
 * @author aconstan
 *
 */

public class GetProjectIn {
	
	private String username;
	private String password;
	
	public GetProjectIn(String username, String password)
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
