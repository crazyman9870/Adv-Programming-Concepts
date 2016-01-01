package SearchGui2;

import static servertester.views.Constants.*;

import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.*;

import Shared.CommunicatingClasses.Information;

@SuppressWarnings("serial")
public class ImagesPanel extends JPanel {
	
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
