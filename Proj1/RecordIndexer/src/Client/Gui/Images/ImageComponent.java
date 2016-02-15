package Client.Gui.Images;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import Client.Gui.BatchState;
import Client.Gui.Cell;
import Client.Gui.ListenerInterface;
import Shared.ModelClasses.*;

@SuppressWarnings("serial")
public class ImageComponent extends JComponent implements ListenerInterface {
	
	private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	
	private int w_centerX;
	private int w_centerY;
	protected double scale;
	protected int dx;
	protected int dy;
	
	private boolean toggleHighlights;
	private DrawingRect highlight;
	private DrawingRect invisHighlight;
	
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;

	
	private BatchState batchState;
	
	private ArrayList<DrawingShape> shapes;
	
	public ImageComponent(BatchState batchState) {
		
		this.batchState = batchState;
		toggleHighlights = false;
		w_centerX = 0;
		w_centerY = 0;
		scale = 1.0;
		dx = 0;
		dy = 0;
		
		initDrag();
		
		//invisible rectangle used to toggle the highlight
		invisHighlight = new DrawingRect(new Rectangle2D.Double(845, 199, 120, 60), 	
				new Color(244, 255, 41, 0));

		shapes = new ArrayList<DrawingShape>();
				
		this.setBackground(new Color(104, 122, 135));
		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		this.addComponentListener(componentAdapter);
		
		if(this.batchState.getImage() != null) {
			//change this to load previous image, or none if there is no batch checked out
			//batchState.setImage((BufferedImage) loadImage("/users/guest/a/aconstan/CS240/Proj1/RecordIndexer/Records/images/1890_image10.png"));
			shapes.add(new DrawingImage(batchState.getImage(), new Rectangle2D.Double(0, 0,
					batchState.getImage().getWidth(null), batchState.getImage().getHeight(null))));
			highlight = new DrawingRect(new Rectangle2D.Double(845, 199, 120, 60), 	
					new Color(91, 222, 141, 0)); 
			shapes.add(highlight);
			w_centerX = batchState.getImage().getWidth(null) / 2;
			w_centerY = batchState.getImage().getHeight(null) / 2;
		}
	}
	
	private void initDrag() {
		
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
		dx = 0;
		dy = 0;
	}
	
	private Image loadImage(String imageFile) {
		
		try {
			return ImageIO.read(new File(imageFile));
		}
		catch (Exception e)	{
			return NULL_IMAGE;
		}
	}
	
	public void zoomInPressed()	{
		
		setScale(scale + 0.1);
	}
	
	public void zoomOutPressed() {
		
		setScale(scale - 0.1);
	}
	
	public void toggleHighlight() {
		
		if(!toggleHighlights) {
			
			shapes.remove(highlight);
			shapes.add(invisHighlight);
		}
		else {
			shapes.remove(invisHighlight);
			shapes.add(highlight);
		}
		toggleHighlights = !toggleHighlights;
		repaint();
	}
	
	public void setScale(double newScale) {
		if(newScale < 0.3)
		{
			newScale = 0.3;
		}
		if(newScale > 1.7)
		{
			newScale = 1.7;
		}
		scale = newScale;
		this.repaint();
	}
	
	public void invertImage() {
		RescaleOp op = new RescaleOp(-1.0f, 255f, null);
		batchState.setImage(op.filter(batchState.getImage(), null));
		shapes.clear();
		shapes.add(new DrawingImage(batchState.getImage(), new Rectangle2D.Double(0, 0, 
				batchState.getImage().getWidth(null), batchState.getImage().getHeight(null))));
		if(!toggleHighlights) {
			shapes.add(highlight);
		}
		else {
			shapes.add(invisHighlight);
		}
		repaint();
	}
	
	public void setOrigin(int w_newOriginX, int w_newOriginY) {
		
		w_centerX = w_newOriginX;
		w_centerY = w_newOriginY;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		
		g2.translate(getWidth() / 2.0, getHeight() / 2.0);
		g2.scale(scale, scale);
		g2.translate(-w_centerX, -w_centerY);
		
		drawShapes(g2);
		
		batchState.updateNav(this.getHeight(), this.getWidth(), scale, dx, dy);
	}
	
	private void drawBackground(Graphics2D g2) {
		
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.translate(getWidth() / 2.0, getHeight() / 2.0);
			transform.scale(scale, scale);
			transform.translate(-w_centerX, -w_centerY);
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			
			try	{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (Exception ex) //NoninvertibleTransformException
			{
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			if(hitShape) {
				
				Point3D y = getY(w_Y);
				Point3D x = getX(w_X);
				boolean outOfBounds = false;
				if(y.z == -1 || x.z == -1) {
					outOfBounds = true;
				}
				if(!outOfBounds) {
					batchState.updateCell(x.z, y.z);
				}
				repaint();
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.translate(getWidth() / 2.0, getHeight() / 2.0);
			transform.scale(scale, scale);
			transform.translate(-w_centerX, -w_centerY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try	{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (Exception ex) //NoninvertibleTransformException
			{
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			
			if (hitShape) {
				
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartOriginX = w_centerX;
				w_dragStartOriginY = w_centerY;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			
			if (dragging) {
				
				int d_X = e.getX();
				int d_Y = e.getY();
				
				AffineTransform transform = new AffineTransform();
				transform.translate(getWidth() / 2.0, getHeight() / 2.0);
				transform.scale(scale, scale);
				transform.translate(-w_dragStartOriginX, -w_dragStartOriginY);
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try	{
					transform.inverseTransform(d_Pt, w_Pt);
				}
				catch (Exception ex) //NoninvertibleTransformException
				{
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				dx = w_deltaX;
				dy = w_deltaY;
				
				w_centerX = w_dragStartOriginX - w_deltaX;
				w_centerY = w_dragStartOriginY - w_deltaY;
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)	{
			batchState.getImageNavigator().finishedDragging();
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			setScale(scale - (e.getPreciseWheelRotation() / 10));
			return;
		}	
		
		private Point3D getY(int YCoord) {
			
			Project proj = batchState.getProject();
			int stop = proj.getYCoordinate() + proj.getRecordsPerBatch() * proj.getHeight();
			int row = 0;
			for(int i = proj.getYCoordinate(); i < stop; i+=(proj.getHeight()))	{
				row++;
				if(i <= YCoord & YCoord < i + proj.getHeight())	{
					return new Point3D(i, (proj.getHeight()), row - 1);
				}
			}
			return new Point3D(-1, -1, -1);
		}
		
		private Point3D getX(int XCoord) {
			
			ArrayList<Field> fields = batchState.getFields();
			int column = 0;
			for(Field f : fields) {
				column++;
				if(f.getxCoordinate() <= XCoord & XCoord < (f.getxCoordinate() + f.getWidth()))	{
					return new Point3D(f.getxCoordinate(), f.getWidth(), column);
				}
			}
			return new Point3D (-1, -1, -1);
		}
	};

	private ComponentAdapter componentAdapter = new ComponentAdapter() {

		@Override
		public void componentHidden(ComponentEvent e) {
			return;
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			return;
		}

		@Override
		public void componentResized(ComponentEvent e) {

		}

		@Override
		public void componentShown(ComponentEvent e) {
			return;
		}	
	};
	
	/////////////////
	// Drawing Shape
	/////////////////
	
	interface DrawingShape {
		
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
	}


	class DrawingRect implements DrawingShape {

		private Rectangle2D rect;
		private Color color;
		
		public DrawingRect(Rectangle2D rect, Color color) {
			this.rect = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2)	{
			g2.setColor(color);
			g2.fill(rect);
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2)	{
			return rect.getBounds2D();
		}
	}

	class DrawingImage implements DrawingShape {

		private Image image;
		private Rectangle2D rect;
		
		public DrawingImage(Image image, Rectangle2D rect) {
		
			this.image = image;
			this.rect = rect;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2)	{
			return rect.getBounds2D();
		}
	}
	
	public void updateBatchState(BatchState newBatchState) {
		this.batchState = newBatchState;
		
		if(this.batchState.getImage() != null) {
			//change this to load previous image, or none if there is no batch checked out
			//batchState.setImage((BufferedImage) loadImage("/users/guest/a/aconstan/CS240/Proj1/RecordIndexer/Records/images/1890_image10.png"));
			shapes.add(new DrawingImage(batchState.getImage(), new Rectangle2D.Double(0, 0,
					batchState.getImage().getWidth(null), batchState.getImage().getHeight(null))));
			highlight = new DrawingRect(new Rectangle2D.Double(845, 199, 120, 60), 	
					new Color(91, 222, 141, 0)); 
			shapes.add(highlight);
			w_centerX = batchState.getImage().getWidth(null) / 2;
			w_centerY = batchState.getImage().getHeight(null) / 2;
		}
		repaint();		
		
	}

	@Override
	public void setCell() {
		// TODO Determines which cell will be repainted
		Cell cell = batchState.getCurrentCell();
		Project proj = batchState.getProject();
		Field field = null;
		int count = 0;
		for(Field f : batchState.getFields()) {
			count++;
			if(count == cell.getFieldNum()) {
				field = f;
				break;
			}
		}
		int Y = proj.getYCoordinate() + ((cell.getRecordNum()) * proj.getHeight());
		int X = field.getxCoordinate();
		int height = proj.getHeight();
		int width = field.getWidth();
		if(!toggleHighlights) {
			shapes.remove(highlight);
			//initialize new cell							//starting x, starting y, finishing x, finishing y 
			highlight = new DrawingRect(new Rectangle2D.Double(X, Y, width, height), 	
					new Color(91, 222, 141, 95)); 
			shapes.add(highlight);
		}
		else {
			//initialize new cell							//starting x, starting y, finishing x, finishing y 
			highlight = new DrawingRect(new Rectangle2D.Double(X, Y, width, height), 	
					new Color(91, 222, 141, 95)); 
		}
		repaint();
		
	}

	@Override
	public void setText(String text, int column, int row) {
		//Does nothing
	}

	@Override
	public void updateBatch(BatchState batchState) {
		this.batchState = batchState;
		
		if(this.batchState.getImage() != null) {
			//change this to load previous image, or none if there is no batch checked out
			//batchState.setImage((BufferedImage) loadImage("/users/guest/a/aconstan/CS240/Proj1/RecordIndexer/Records/images/1890_image10.png"));
			shapes.add(new DrawingImage(batchState.getImage(), new Rectangle2D.Double(0, 0,
					batchState.getImage().getWidth(null), batchState.getImage().getHeight(null))));
			highlight = new DrawingRect(new Rectangle2D.Double(845, 199, 120, 60), 	
					new Color(91, 222, 141, 0)); 
			shapes.add(highlight);
			w_centerX = batchState.getImage().getWidth(null) / 2;
			w_centerY = batchState.getImage().getHeight(null) / 2;
		}
		repaint();	
	}
}