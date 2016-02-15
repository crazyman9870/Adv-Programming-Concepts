package Client.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FieldHelpPanel extends JPanel implements ListenerInterface {

	private JEditorPane htmlViewer;
	private BatchState batchState;
	private int fieldNumber;
	
	public FieldHelpPanel(BatchState batchState) {
		super();
		
		this.batchState = batchState;
		this.fieldNumber = this.batchState.getFieldNumber();
		this.setLayout(new BorderLayout());
		
		htmlViewer = new JEditorPane();
		htmlViewer.setOpaque(true);
		htmlViewer.setBackground(Color.white);
		htmlViewer.setPreferredSize(new Dimension(200,250));
		htmlViewer.setEditable(false);
		htmlViewer.setContentType("text/html");
		
//		if(this.batchState.getCurrentBatch() != null) {
//			try {
//				htmlViewer.setPage("");
//				// TODO modify later to not be hard coded
//			} catch (IOException e) {
//				System.out.println("Caught in the FieldHelpPanel");
//				e.printStackTrace();
//			}
//			
//		}
		this.add(htmlViewer,BorderLayout.CENTER);
	}

	@Override
	public void setCell() {
		
		this.fieldNumber = this.batchState.getCurrentCell().getFieldNum();
		String link = this.batchState.getUrlPrefix()
				+ this.batchState.getFields().get(fieldNumber-1).getHelpPath();
		try {
			htmlViewer.setPage(link);
		} catch (IOException e) {
			System.out.println("Caught in the FieldHelpPanel");
			e.printStackTrace();
		}
		this.add(htmlViewer,BorderLayout.CENTER);
		
	}

	@Override
	public void setText(String text, int column, int row) {
		//Does nothing 	
	}

	@Override
	public void updateBatch(BatchState batchState) {
		this.batchState = batchState;
		this.fieldNumber = this.batchState.getCurrentCell().getFieldNum();
		
		htmlViewer = new JEditorPane();
		htmlViewer.setOpaque(true);
		htmlViewer.setBackground(Color.white);
		htmlViewer.setPreferredSize(new Dimension(200,250));
		htmlViewer.setEditable(false);
		htmlViewer.setContentType("text/html");
		
		if(this.batchState.getCurrentBatch() != null) {
			
			String link = this.batchState.getUrlPrefix()
					+ this.batchState.getFields().get(fieldNumber-1).getHelpPath();
			try {
				htmlViewer.setPage(link);
				// TODO modify later to not be hard coded
			} catch (IOException e) {
				System.out.println("Caught in the FieldHelpPanel");
				e.printStackTrace();
			}
			this.add(htmlViewer,BorderLayout.CENTER);
		}
	}
}
