package Client.SearchGui;

import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class TextBoxPanel extends JPanel {

	protected JTextArea projectsTextArea;
	protected JButton search;

	public TextBoxPanel(String label) {
		super();
		
		setBorder(BorderFactory.createTitledBorder(label));
		
		setLayout(new BorderLayout());
		
		projectsTextArea = new JTextArea(10, 60);
		add(new JScrollPane(projectsTextArea), BorderLayout.CENTER);
		
		
	}
	
	public void setText(String value) {
		projectsTextArea.setText(value);
	}
	
	public String getText() {
		return projectsTextArea.getText();
	}
	
}
