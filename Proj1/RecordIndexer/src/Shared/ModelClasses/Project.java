package Shared.ModelClasses;

import java.util.ArrayList;

/**
 * This the model class of Project objects
 * Modeled after the table in the database
 * Contains mainly just getters and setters
 * @author aconstan
 *
 */

public class Project {

	private int id;
	private String title;
	private int recordsPerBatch;
	private int yCoordinate;
	private int height;
	
	public ArrayList<Field> fields = new ArrayList<Field>();
	public ArrayList<Batch> batches = new ArrayList<Batch>();
	
	public Project() {
		id = 0;
		title = null;
		recordsPerBatch = 0;
		yCoordinate = 0;
		height = 0;
	}
	
	public Project(String title, int rec, int y, int h) {
		id = 0;
		this.title = title;
		recordsPerBatch = rec;
		yCoordinate = y;
		height = h;
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

	public int getRecordsPerBatch() {
		return recordsPerBatch;
	}

	public void setRecordsPerBatch(int recordsPerBatch) {
		this.recordsPerBatch = recordsPerBatch;
	}

	public int getYCoordinate() {
		return yCoordinate;
	}

	public void setYCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
