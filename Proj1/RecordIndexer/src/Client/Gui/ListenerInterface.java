package Client.Gui;

public interface ListenerInterface {

	public void setCell();
	
	//public void getText();
	
	public void setText(String text, int column, int row);
	
	public void updateBatch(BatchState batchState);
}
