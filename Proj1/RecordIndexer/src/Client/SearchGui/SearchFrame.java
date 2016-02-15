package Client.SearchGui;

import java.awt.*;

import javax.swing.*;

import SearchGui2.ParamsPanel;

@SuppressWarnings("serial")
public class SearchFrame extends JFrame {

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
	
	private TextBoxPanel _projectsPanel;
	private ParamsPanel _fieldsPanel;
	private ParamsPanel _valuesPanel;
	private JButton _executeButton;
	
	public SearchFrame() {
		super();
		
		setTitle("Search");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		add(Box.createRigidArea(DOUBLE_VSPACE));
		
		_projectsPanel = new TextBoxPanel("PROJECTS:");
		_projectsPanel.setMaximumSize(new Dimension(600, 250));
		add(_projectsPanel);
		
		add(Box.createRigidArea(TRIPLE_VSPACE));
		add(Box.createRigidArea(TRIPLE_VSPACE));
		
		_fieldsPanel = new ParamsPanel("FIELDS:");
		add(_fieldsPanel);
		
		add(Box.createRigidArea(DOUBLE_VSPACE));
		
		_valuesPanel = new ParamsPanel("VALUES:");
		add(_valuesPanel);
		
		add(Box.createRigidArea(new Dimension(0, VSPACING * 5)));
		
		_executeButton = new JButton("Execute");
		add(_executeButton);	
		add(Box.createRigidArea(DOUBLE_HSPACE));
		
		
		pack();
		
		setMinimumSize(getPreferredSize());
//		this.setResizable(false);
		
	}
	
}
