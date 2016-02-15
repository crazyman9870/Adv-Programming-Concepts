package Client.Gui.Tables;

import javax.swing.table.AbstractTableModel;

import Client.Gui.BatchState;
import Client.Gui.ListenerInterface;


public class TableModel extends AbstractTableModel{
	protected boolean changeFromTable;
	private BatchState batchState;
	private String[][] entries;
	
	public TableModel(BatchState batchState) {
		this.batchState = batchState;
		changeFromTable = false;
		entries = batchState.getIndexedData();
	}
	
	@Override
	public int getRowCount() {
		if(batchState.getProject() == null)
		{
			return 0;
		}
		return (batchState.getProject().getRecordsPerBatch());
	}

	@Override
	public int getColumnCount() {
		if(batchState.getFields() == null)
		{
			return 0;
		}
		return (batchState.getFields().size() + 1);
	}
	
	@Override
	public String getColumnName(int column) {
		
		String result = null;
		
		if (column < getColumnCount() && column >= 0) {	
			if(column == 0) {
				result = "Num";
			}
			else {
				result = batchState.getFields().get(column - 1).getTitle();
			}
		}
		
		return result;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		
		if(column == 0)
			return false;
		
		return true;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Object result = null;
		if(rowIndex >= 0 && rowIndex < getRowCount() && columnIndex >= 1
				&& columnIndex < getColumnCount()) 
		{
			result = entries[columnIndex - 1][rowIndex];
		}
		else if(columnIndex == 0)
		{
			result = Integer.toString(rowIndex + 1);
		}
		return result;
	}
	
	@Override
	public void setValueAt(Object value, int row, int column) {
		if(row >= 0 && row < getRowCount() && column >= 0
				&& column < getColumnCount()) 
		{
			entries[column - 1][row] = (String) value;
			if(!changeFromTable)
				batchState.addText(column - 1, row, (String)value);
			changeFromTable = false;
			this.fireTableCellUpdated(row, column);
		} 
		else 
		{
			throw new IndexOutOfBoundsException();
		}
	}
}
