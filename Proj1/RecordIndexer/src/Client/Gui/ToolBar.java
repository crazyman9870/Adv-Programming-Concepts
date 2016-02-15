package Client.Gui;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ToolBar extends JPanel {
	
	protected JButton zoomIn;
	protected JButton zoomOut;
	protected JButton invert;
	protected JButton toggle;
	protected JButton save;
	protected JButton submit;
	
	protected ClientFacade facade;
	
	public ToolBar(ClientFacade facade) {
		super();
		this.facade = facade;
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		zoomIn = new JButton("Zoom In");
		this.add(zoomIn);
		
		zoomOut = new JButton("Zoom Out");
		this.add(zoomOut);
		
		invert = new JButton("Invert");
		this.add(invert);
		
		toggle = new JButton("Toggle Highlights");
		this.add(toggle);
		
		save = new JButton("Save");
		this.add(save);
		
		submit = new JButton("Submit");
		this.add(submit);
		
	}
	
	public void buttonsEnabled(boolean state) {
		zoomIn.setEnabled(state);
		zoomOut.setEnabled(state);
		invert.setEnabled(state);
		toggle.setEnabled(state);
		save.setEnabled(state);
		submit.setEnabled(state);
	}
	
	
	
}
