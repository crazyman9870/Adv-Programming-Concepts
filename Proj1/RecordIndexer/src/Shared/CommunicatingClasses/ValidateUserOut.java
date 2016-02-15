package Shared.CommunicatingClasses;

/**
 * This class informs the user if he/she was successfully logged into the program
 * @author aconstan
 *
 */

import Shared.ModelClasses.*;

public class ValidateUserOut {
	
	//private boolean loggedIn;
	//private String firstName;
	//private String lastName;
	//private int recordNumber;
	private User user;
	private String output;
	private boolean loggedIn;

	/**
	 * Constructor 
	 * Encapsulates the info needed to log in, to be sent to the client
	 * 
	 * @param firstName
	 * @param lastName
	 * @param recordNumber
	 * @param status
	 */
	
	public ValidateUserOut(User user, int status)
	{
		//loggedIn = false;
		//this.firstName = user.getFirstName();
		//this.lastName = user.getLastName();
		//this.recordNumber = user.getRecordsIndexed();
		this.user = user;
		output = toString(status);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOutput() {
		return output;
	}
		
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * toString() method called in the constructor prepares a string to be used in the client
	 * @param status
	 * @return
	 */
	
	
	public String toString(int status) {
		StringBuilder sb = new StringBuilder();
		if(status == 0)
		{
			sb.append("TRUE\n" + user.getFirstName() + "\n" + user.getLastName() + "\n" + user.getRecordsIndexed() + "\n");
			loggedIn = true;
		}
		else if(status == 1)
		{
			sb.append("FALSE\n");
			loggedIn = false;
		}
		else //if(status == 2)
		{
			sb.append("FAILED\n");
			loggedIn = false;
		}
		
		return sb.toString();
	}

}

/*	
	public boolean isLoggedIn() {
		return loggedIn;
	}
		
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
		
	public String getFirstName() {
		return firstName;
	}
		
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
		
	public String getLastName() {
		return lastName;
	}
		
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
		
	public int getRecordNumber() {
		return recordNumber;
	}
		
	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}
*/
