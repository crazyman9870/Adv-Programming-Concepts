package Client.Gui.Images;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import Client.Gui.BatchState;
import Client.Gui.ListenerInterface;;

@SuppressWarnings("serial")
public class ImageNavigator extends JComponent implements ListenerInterface
{
	private BatchState batchState;
	private BufferedImage image;
	private double scale;
	private int imageDx;
	private int imageDy;
	private double viewScale;
	private int viewWidth;
	private int viewHeight;

	private int currentX;
	private int currentY;
	
	public ImageNavigator(BatchState batchState)
	{
		this.batchState = batchState;
		this.image = null;
		this.scale = 1.0;
		this.currentX = 0;
		this.currentY  = 0;
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		
		if(image != null)
		{
			setScale(g2);
			setImage(g2);
			setRect(g2);
		}		
	}
	
	private void drawBackground(Graphics2D g2)
	{
		g2.setColor(new Color(104, 122, 135));
		g2.fillRect(0,  0, getWidth(), getHeight());
	}
	
	private void setScale(Graphics2D g2)
	{
		g2.translate(getWidth() / 2.0, getHeight() / 2.0);
		double height = this.getHeight();
		float avgHeight = ((float)height / (float)image.getHeight());
		double width = this.getWidth();
		float avgWidth = ((float)width / (float)image.getWidth());
		scale = Math.min(avgHeight, avgWidth);
		g2.scale(scale, scale);
	}
	
	private void setImage(Graphics2D g2)
	{
		AffineTransform transform = g2.getTransform();
		g2.translate(-(image.getWidth() / 2), -(image.getHeight() / 2));
		g2.drawImage(image, 0, 0, null);
		g2.setTransform(transform);
	}
	
	private void setRect(Graphics2D g2)
	{
		float boxScale = (float) (1.0f / viewScale);
		g2.scale(boxScale, boxScale);
		g2.setColor(new Color(91, 222, 141, 95));
		int x = (int) (imageDx * viewScale + viewWidth / 2);
		int y = (int) (imageDy * viewScale + viewHeight / 2);
		g2.fillRect(-x, -y, viewWidth, viewHeight);
	}
	
	@Override
	public void setCell() {}
	@Override
	public void setText(String text, int column, int row) {}
	@Override
	public void updateBatch(BatchState batchS) 
	{
		this.batchState = batchS;
		image = this.batchState.getImage();
		this.imageDx = batchState.getImageComponent().dx;
		this.imageDy = batchState.getImageComponent().dy;
		this.viewScale = batchState.getImageComponent().scale;
		this.viewHeight = batchState.getImageComponent().getHeight();
		this.viewWidth = batchState.getImageComponent().getWidth();
		repaint();
	}
	
	public void updateRectangle(int viewHeight, int viewWidth, double viewScale, int dx, int dy)
	{
		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
		this.viewScale = viewScale;

		this.imageDx = dx + currentX;
		this.imageDy = dy + currentY;
		
		repaint();
	}
	
	public void finishedDragging() {
		this.currentX = this.imageDx;
		this.currentY = this.imageDy;
	}
}