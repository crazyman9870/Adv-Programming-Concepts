package Client.SearchGui;

import static servertester.views.Constants.*;

import java.awt.Dimension;

import javax.swing.*;

public class ImageFrame extends JFrame {

	public ImageFrame() {
		
		setTitle("Image");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		pack();
		
		setMinimumSize(new Dimension(400,400));
	}
}
