package CommunicatingClasses;

/**
 * This class informs the user if he/she was successfully logged into the program
 * @author aconstan
 *
 */

public class ValidateUserOut {
	
	private boolean loggedIn;
	private String firstName;
	private String lastName;
	private int recordNumber;
	private String output;

	/**
	 * Constructor 
	 * Encapsulates the info needed to log in, to be sent to the client
	 * 
	 * @param firstName
	 * @param lastName
	 * @param recordNumber
	 * @param status
	 */
	
	public ValidateUserOut(String firstName, String lastName, int recordNumber, int status)
	{
		loggedIn = false;
		this.firstName = firstName;
		this.lastName = lastName;
		this.recordNumber = recordNumber;
		output = toString(status, firstName, lastName, recordNumber);
	}

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
	
	public String getOutput() {
		return output;
	}
	
	/**
	 * toString() method called in the constructor prepares a string to be used in the client
	 * @param status
	 * @return
	 */
	
	
	public String toString(int status, String firstName, String lastName, int recordNumber) {
		StringBuilder sb = new StringBuilder();
		if(status == 0)
		{
			sb.append("TRUE\n" + firstName + "\n" + lastName + "\n" + recordNumber + "\n");
		}
		if(status == 1)
		{
			sb.append("FALSE\n");
		}
		if(status == 2)
		{
			sb.append("FAILED\n");
		}
		
		return sb.toString();
	}

}
