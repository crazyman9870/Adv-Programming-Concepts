package Client.Gui.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SimpleImagePanel extends JPanel{
	
	private BufferedImage image;
	private int width;
	private int height;
	
	public JButton ok;
	
	
	public SimpleImagePanel(URL url) {
		super();
	
		this.setLayout(new BorderLayout());
		
		try {
			InputStream input = url.openStream();
			image = ImageIO.read(input);
			
			//height = image.getHeight();
			//width = image.getWidth();
			//this.setSize(new Dimension(image.getWidth()/2, image.getHeight()/2));
			this.setPreferredSize(new Dimension(image.getWidth()/2, image.getHeight()/2));
			width = image.getWidth()/2;
			height = image.getHeight()/2;
		} catch (IOException e) {
			System.out.println("Caught in the SampleImagePanel");
			e.printStackTrace();
		}
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.PAGE_AXIS));
		ok = new JButton("Ok");
		ok.setAlignmentX(CENTER_ALIGNMENT);
		southPanel.add(ok);
		
		this.add(southPanel, BorderLayout.SOUTH);
		
	}	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, width, height, null);
    }

}
