package Client.SearchGui;

import static servertester.views.Constants.*;

import java.awt.*;

import javax.swing.*;

public class ResultsPanel extends JPanel {
	
	private JCheckBox _projFieldBox;
	private JPanel _projsAndFields;
	private GridBagConstraints c;
	
	private JLabel _label;
	
	public ResultsPanel() {
		super();
		
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		_label = new JLabel("Project 1");
		c.gridy = 0;
		c.gridx = 0;
		add(_label, c);
		
		//===========================================================
		
		_projFieldBox = new JCheckBox("Last Name");
		c.gridy = 1;
		c.gridx = 0;
		add(_projFieldBox, c);
		
		_projFieldBox = new JCheckBox("First Name");
		c.gridy = 1;
		c.gridx = 1;
		add(_projFieldBox, c);
		
		_projFieldBox = new JCheckBox("Gender");
		c.gridy = 1;
		c.gridx = 2;
		add(_projFieldBox, c);
		
		_projFieldBox = new JCheckBox("Age");
		c.gridy = 1;
		c.gridx = 3;
		add(_projFieldBox, c);
		
		//===========================================================

		_label = new JLabel("Project 2");
		c.gridy = 3;
		c.gridx = 0;
		add(_label, c);
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		//===========================================================
		
		_projFieldBox = new JCheckBox("Gender");
		c.gridy = 4;
		c.gridx = 0;
		add(_projFieldBox, c);
		
		_projFieldBox = new JCheckBox("Age");
		c.gridy = 4;
		c.gridx = 1;
		add(_projFieldBox, c);
		
		_projFieldBox = new JCheckBox("Last Name");
		c.gridy = 4;
		c.gridx = 2;
		add(_projFieldBox, c);
		
		_projFieldBox = new JCheckBox("First Name");
		c.gridy = 4;
		c.gridx = 3;
		add(_projFieldBox, c);
		
		_projFieldBox = new JCheckBox("Ethnicity");
		c.gridy = 4;
		c.gridx = 4;
		add(_projFieldBox, c);		
		
		//===========================================================
		
		_label = new JLabel("Project 3");
		c.gridy = 5;
		c.gridx = 0;
		add(_label, c);

		//===========================================================
		
		_projFieldBox = new JCheckBox("Last Name");
		c.gridy = 6;
		c.gridx = 0;
		add(_projFieldBox, c);
		
		_projFieldBox = new JCheckBox("First Name");
		c.gridy = 6;
		c.gridx = 1;
		add(_projFieldBox, c);
		
		_projFieldBox = new JCheckBox("Age");
		c.gridy = 6;
		c.gridx = 2;
		add(_projFieldBox, c);
		
		_projFieldBox = new JCheckBox("Ethinicity");
		c.gridy = 6;
		c.gridx = 3;
		add(_projFieldBox, c);
	}
}
