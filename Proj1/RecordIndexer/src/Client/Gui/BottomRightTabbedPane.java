package Client.Gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import Client.Gui.Images.ImageNavigator;

@SuppressWarnings("serial")
public class BottomRightTabbedPane extends JTabbedPane{
	
	private FieldHelpPanel helpPanel;
	private ImageNavigator imageNavigator;
	
	public BottomRightTabbedPane(BatchState batchState) {
		super();
		
		helpPanel = new FieldHelpPanel(batchState);
		JScrollPane scroll = new JScrollPane(helpPanel);
		
		this.add("Field Help", scroll);
		
		imageNavigator = new ImageNavigator(batchState);
		this.add("Image Navigation", imageNavigator);
	}

	public FieldHelpPanel getHelpPanel() {
		return helpPanel;
	}

	public void setHelpPanel(FieldHelpPanel helpPanel) {
		this.helpPanel = helpPanel;
	}

	public ImageNavigator getImageNavigator() {
		return imageNavigator;
	}

	public void setImageNavigator(ImageNavigator imageNavigator) {
		this.imageNavigator = imageNavigator;
	}
	
}
