package Client.SearchGui;

import static servertester.views.Constants.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class HostPanel extends JPanel {
	
	private JTextField _hostTextField;
	private JTextField _portTextField;

	public HostPanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("HOST:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_hostTextField = new JTextField(8);
		_hostTextField.setMinimumSize(_hostTextField.getPreferredSize());
		add(_hostTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("PORT:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_portTextField = new JTextField(8);
		_portTextField.setMinimumSize(_portTextField.getPreferredSize());
		add(_portTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
	}
	
	public void setHost(String value) {
		_hostTextField.setText(value);
	}

	public String getHost() {
		return _hostTextField.getText();
	}

	public void setPort(String value) {
		_portTextField.setText(value);
	}

	public String getPort() {
		return _portTextField.getText();
	}
}
