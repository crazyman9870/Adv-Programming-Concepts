package ModelClasses;

/**
 * This the model class of Field objects
 * Modeled after the table in the database
 * Contains mainly just getters and setters
 * @author aconstan
 *
 */

public class Field {
	
	private int id;
	private String title;
	private int xCoordinate;
	private int width;
	private String helpPath;
	private String dataPath;
	private int projectKey;
	
	public Field() {
		
		id = 0;
		title = "";
		xCoordinate = 0;
		width = 0;
		helpPath = "";
		dataPath = "";
		projectKey = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getHelpPath() {
		return helpPath;
	}

	public void setHelpPath(String helpPath) {
		this.helpPath = helpPath;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	public int getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(int projectKey) {
		this.projectKey = projectKey;
	}

}
