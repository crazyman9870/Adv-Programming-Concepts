package Client.SearchGui;

import static servertester.views.Constants.DOUBLE_HSPACE;
import static servertester.views.Constants.DOUBLE_VSPACE;
import static servertester.views.Constants.SINGLE_HSPACE;
import static servertester.views.Constants.TRIPLE_VSPACE;
import static servertester.views.Constants.VSPACING;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

import SearchGui2.ParamsPanel;

@SuppressWarnings("serial")
public class ResultFrame extends JFrame {

	private TextBoxPanel _imagesPanel;
	private ParamsPanel _viewPanel;
	private JButton _executeButton;
	private GridBagConstraints c;
	
	private ResultsPanel _projFieldPanel;

	
	public ResultFrame() {
		super("Result");
		
//		setTitle("Result");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		//setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		_projFieldPanel = new ResultsPanel();
		c.gridx = 0;
		c.gridy = 0;
		add(_projFieldPanel, c);
		
		//=================
		
		
		
		
		//================
		
		
		/*
		_imagesPanel = new TextBoxPanel("IMAGES:");
		add(_imagesPanel);
		
		add(Box.createRigidArea(TRIPLE_VSPACE));
		add(Box.createRigidArea(TRIPLE_VSPACE));
		
		_viewPanel = new ParamsPanel("VIEW:");
		add(_viewPanel);
		
		add(Box.createRigidArea(new Dimension(0, VSPACING * 5)));
		
		_executeButton = new JButton("Execute");
		add(_executeButton);	
		add(Box.createRigidArea(DOUBLE_HSPACE));
		*/
		
		pack();
		
		setMinimumSize(getPreferredSize());
//		this.setResizable(false);
		
		
	}
}
