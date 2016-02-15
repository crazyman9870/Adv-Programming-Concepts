package SearchGui2;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import Shared.ModelClasses.*;

@SuppressWarnings("serial")
public class ProjectPanel  extends JPanel {
	
	private GridBagConstraints c;
	
	private JLabel label;
	private JCheckBox checkBox;
	
	public ProjectPanel() {
		label = null;
		checkBox = null;
	}
	
	public ProjectPanel(ArrayList<Field> fields) {
		super();
		
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
			Integer fieldId = fields.get(0).getProjectKey();
			label = new JLabel("Project " + fieldId.toString());
			c.gridx = 0;
			c.gridy = 0;
			label.setAlignmentX(LEFT_ALIGNMENT);
			this.add(label, c);
		
		for(int i = 0; i < fields.size(); ++i) {
			
			checkBox = new JCheckBox(fields.get(i).getTitle());
			c.gridx = i+1;
			checkBox.setAlignmentX(LEFT_ALIGNMENT);
			this.add(checkBox, c);
		}
		
		this.setMaximumSize(this.getPreferredSize());
		this.setMinimumSize(this.getPreferredSize());
	}
}
