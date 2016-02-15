package ModelClasses;

/**
 * This the model class of Batch objects
 * Modeled after the table in the database
 * Contains mainly just getters and setters
 * @author aconstan
 *
 */

public class Batch {
	
	private int id;
	private String imagepath;
	private int batchState;
	private int projectKey;
	
	public Batch() {
		id = 0;
		imagepath = "";
		batchState = 0;
		projectKey = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public int getBatchState() {
		return batchState;
	}

	public void setBatchState(int batchState) {
		this.batchState = batchState;
	}

	public int getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(int projectKey) {
		this.projectKey = projectKey;
	}

}
