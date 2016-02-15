package SearchGui2;

import java.awt.Dimension;

import javax.swing.*;

@SuppressWarnings("serial")
public class ParamsPanel extends JPanel {
	
	public static final int HSPACING = 5;
	public static final int VSPACING = 5;
	
	public static final Dimension SINGLE_HSPACE = new Dimension(HSPACING, 0);
	public static final Dimension DOUBLE_HSPACE = new Dimension(HSPACING * 2, 0);
	public static final Dimension TRIPLE_HSPACE = new Dimension(HSPACING * 3, 0);
	public static final Dimension MAX_HSPACE = new Dimension(10000, 0);
	
	public static final Dimension SINGLE_VSPACE = new Dimension(0, VSPACING);
	public static final Dimension DOUBLE_VSPACE = new Dimension(0, VSPACING * 2);
	public static final Dimension TRIPLE_VSPACE = new Dimension(0, VSPACING * 3);
	public static final Dimension MAX_VSPACE = new Dimension(0, 10000);
	
	protected JTextField textField;
	protected JButton searchButton;

	public ParamsPanel(String label) {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel(label));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		textField = new JTextField(60);
		textField.setMinimumSize(textField.getPreferredSize());
		add(textField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		searchButton = new JButton("Search");
		add(searchButton);
		
	}
	
	public void setText(String value) {
		textField.setText(value);
	}

	public String getText() {
		return textField.getText();
	}

}