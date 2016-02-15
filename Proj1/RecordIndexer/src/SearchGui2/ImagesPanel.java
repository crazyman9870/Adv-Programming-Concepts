package SearchGui2;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.*;

import Shared.CommunicatingClasses.Information;

@SuppressWarnings("serial")
public class ImagesPanel extends JPanel {
	
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
	
	protected JComboBox<String> imageCombo;
	protected JButton viewButton;
	
	
	public ImagesPanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("Images"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		imageCombo = new JComboBox<>();
		add(imageCombo);
		
		viewButton = new JButton("View");
		add(viewButton);
	}
	
	public void update(ArrayList<Information> info) {
		
		TreeSet<String> checkDuplicate = new TreeSet<>();
		imageCombo.removeAllItems();
		if(info.size() > 0) {
			
			for(Information i : info) {
				String link = i.getLink();
				if(!checkDuplicate.contains(link)) {
					checkDuplicate.add(link);
					imageCombo.addItem(link);
				}
			}
		}

	}

}
