package Shared.ModelClasses;

/**
 * This the model class of User objects
 * Modeled after the table in the database
 * Contains mainly just getters and setters
 * @author aconstan
 *
 */

public class User {
	
	private int id;
	private Credentials creds;
	private String firstName;
	private String lastName;
	private String email;
	private int recordsIndexed;
	private int batchKey;
	
	public User() {
		
		id = 0;
		creds = new Credentials();
		firstName = "";
		lastName = "";
		email = "";
		recordsIndexed = 0;
		batchKey = -1;
	}
	
	public User(String un, String pass, String fn, String ln, String em) {
		
		id = 0;
		creds = new Credentials();
		creds.setUsername(un);
		creds.setPassword(pass);
		firstName = fn;
		lastName = ln;
		email = em;
		recordsIndexed = 0;
		batchKey = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Credentials getCreds() {
		return creds;
	}

	public String getCredsName() {
		return creds.getUsername();
	}
	
	public String getCredsPassword() {
		return creds.getPassword();
	}
	
	public void setCreds(String username, String password) {
		this.creds.setUsername(username);
		this.creds.setPassword(password);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRecordsIndexed() {
		return recordsIndexed;
	}

	public void setRecordsIndexed(int recordsIndexed) {
		this.recordsIndexed = recordsIndexed;
	}

	public int getBatchKey() {
		return batchKey;
	}

	public void setBatchKey(int batchKey) {
		this.batchKey = batchKey;
	}

}
