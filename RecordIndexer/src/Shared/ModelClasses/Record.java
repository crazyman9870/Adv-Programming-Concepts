package Shared.ModelClasses;

/**
 * This the model class of Record objects
 * Modeled after the table in the database
 * Contains mainly just getters and setters
 * @author aconstan
 *
 */

public class Record {
	
	private int id;
	private int rowNumber;
	private String data;
	private int batchKey;
	private int fieldKey;
	
	public Record() {
		id = 0;
		rowNumber = 0;
		data = "";
		batchKey = 0;
		fieldKey = 0;
	}
	
	public Record(int r, String data, int bk, int fk) {
		id = 0;
		rowNumber = r;
		this.data = data;
		batchKey = bk;
		fieldKey = fk;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getBatchKey() {
		return batchKey;
	}

	public void setBatchKey(int batchKey) {
		this.batchKey = batchKey;
	}

	public int getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(int fieldKey) {
		this.fieldKey = fieldKey;
	}

}
