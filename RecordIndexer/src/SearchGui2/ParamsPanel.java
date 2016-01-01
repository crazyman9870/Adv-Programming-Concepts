package SearchGui2;

import static servertester.views.Constants.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class ParamsPanel extends JPanel {
	
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