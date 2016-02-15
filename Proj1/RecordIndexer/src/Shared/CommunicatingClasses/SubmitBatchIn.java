package Shared.CommunicatingClasses;

/**
 * This class is designed to package all the information on the batch
 * The client can now submit to the server
 * 
 * @author aconstan
 *
 */

public class SubmitBatchIn {
	
	private String username;
	private String password;
	private int batchKey;
	private String input;
	
	public SubmitBatchIn(String username, String password, int key, String input) {
		this.username = username;
		this.password = password;
		this.batchKey = key;
		this.input = input;
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

	public int getBatchKey() {
		return batchKey;
	}

	public void setBatchKey(int batchKey) {
		this.batchKey = batchKey;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
}
