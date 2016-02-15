package Client.Gui;

public class Cell {
	
	private int recordNum;
	private int fieldNum;
	
	public Cell() {
		recordNum = 0;
		fieldNum = 0;
	}

	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

	public int getFieldNum() {
		return fieldNum;
	}

	public void setFieldNum(int fieldNum) {
		this.fieldNum = fieldNum;
	}

}
