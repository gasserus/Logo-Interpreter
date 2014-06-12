package logo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphPane extends JPanel {
	/**
	 * 
	 */
	private final int TURTLE_SIZE = 35;
	private final String TURTLE_IMAGE_PATH = "icons/turtle.png";
	private Image turtleImg = null;

	ArrayList<int[]> turtlePosHistory;
	ArrayList<Color> turtleColorHistory;
	ArrayList<Boolean> turtleVisibleHistory;
	
	int direction;
	int actualCenter[] = new int [] { 0, 0};
	int actualMax[] = new int[] { 0, 0 };;
	Dimension visibleSize;
	boolean isScaling;
	
	/**
	 * creates new Graphic, sets the starting pos to turtle
	 */
	public GraphPane(){
		this.turtlePosHistory = new ArrayList<int[]>();
		this.turtleColorHistory = new ArrayList<Color>();
		this.turtleVisibleHistory = new ArrayList<Boolean>();
		this.turtleImg = getToolkit().createImage( TURTLE_IMAGE_PATH );
		
		this.visibleSize = new Dimension( 0, 0);
		this.isScaling = false;
		
		this.setVisible( true );
	}
	
	/**
	 * draws the whole turtle History
	 */
	public void paintComponent( Graphics g ){
		super.paintComponent( g );
		Graphics2D g2d=(Graphics2D)g;

		
		//**************************************************************************** scaling Output
		Dimension needed = this.getNeededSize( this.turtlePosHistory );
		double zoomFactor = this.calculateZoom( this.visibleSize, needed, this.isScaling);
		
		
		g2d.scale( zoomFactor, zoomFactor);
		
		if( this.isScaling ) {
			this.setPreferredSize( this.visibleSize );
		}else{
			this.setPreferredSize( this.getNeededSize( this.turtlePosHistory ) );
		}
		
		//**************************************************************************** draw
		
		this.drawImage( g2d, this.getSize(), zoomFactor );
		
		this.revalidate();
	}
	
	
	public void drawImage( Graphics2D g2d, Dimension size, double zoomFactor ){
		int[] lastPos;
		int[] targetPos;
		//**************************************************************************** get the actual center of the graphPanel 
		
		actualCenter[0] = ( int ) ( ( size.getWidth() / 2.0 ) * ( 1 / zoomFactor ) );
		actualCenter[1] = ( int ) ( ( size.getHeight() / 2.0 ) * ( 1 / zoomFactor ) );
		
		//**************************************************************************** draw History
		for( int i = 1; i < ( turtlePosHistory.size() ); i++ ){
			
			if( turtleVisibleHistory.get( i ) ){
				g2d.setColor( turtleColorHistory.get( i ) );
				lastPos = turtlePosHistory.get( i - 1 );
				targetPos = turtlePosHistory.get( i );
				
				g2d.drawLine( ( actualCenter[0] + lastPos[0] ), ( actualCenter[1] + lastPos[1] ), ( actualCenter[0] + targetPos[0] ), (actualCenter[1] + targetPos[1]) );
			}
		}
		//**************************************************************************** draw Turtle
		if( turtleImg != null  ){
			
			targetPos = this.turtlePosHistory.get( this.turtlePosHistory.size() -1 );
			
			int rotationXCenter =  (int) ( targetPos[0] + actualCenter[0]  );
			int rotationYCenter = (int) ( targetPos[1] + actualCenter[1]  );
			
			g2d.rotate( Math.toRadians( -direction ), rotationXCenter, rotationYCenter );
			g2d.drawImage( turtleImg,  ( int ) ( targetPos[0] + actualCenter[0] - ( 0.5 *  TURTLE_SIZE ) ), ( int ) ( targetPos[1] + actualCenter[1] - ( 0.5 *  TURTLE_SIZE ) ), TURTLE_SIZE, TURTLE_SIZE, this );	
		}
	}
	
	
	/**
	 * creates new turtle movement in history, repaints graph
	 * @param pos
	 * @param direction
	 * @param c
	 * @param visible
	 */
	public void moveTurtle( double pos[], int direction, Color c, boolean visible ){
		int posInt[] = new int[2];
		posInt[0] = ( int ) pos[0];
		posInt[1] = ( int ) pos[1];
		
		this.turtlePosHistory.add( posInt );
		this.turtleColorHistory.add( c );
		this.turtleVisibleHistory.add( visible );
		this.direction = direction;
		this.repaint();
	}
	
	/**
	 * clears graph, deletes all history entrys and sets the turtle start Position back.
	 */
	public void clearGraph(){
		this.turtleColorHistory.clear();
		this.turtlePosHistory.clear();
		this.turtleVisibleHistory.clear();
		actualMax[0] = 0;
		actualMax[1] = 0;
		this.setPreferredSize( this.getNeededSize( this.turtlePosHistory ) );
	}
	
	/**
	 * 
	 * @return the center in relation to the size of the pane
	 */
	public int[] getActualCenter(){
		return actualCenter;
	}
	
	
	/**
	 * sets the intern Variable, needed for correct zooming. You might want to task this method on the parents paint method
	 * @param size
	 */
	public void setVisibleSize( Dimension size ){
		this.visibleSize = new Dimension( ( int ) ( size.getWidth() - 20 ), ( int ) ( size.getHeight() - 20 ) );
	}
	
	
	/**
	 *  Calculates the ZoomFactor or restores the PreferredSize and zoomFactor. Toggles the isScaling boolean
	 */
	public void toggleZoom(){
		if( this.isScaling ){
			this.isScaling = false;
		}else{
			this.isScaling = true;
		}
		this.repaint();
	}
	
	/**
	 * 
	 * @param posHistory
	 * @return Dimension that is needed to display all Lines and keep the center in the middle of the pane
	 */
	public Dimension getNeededSize( ArrayList<int[]> posHistory ){
		int max[] = new int[] { 0 ,0 };
		
		for( int i = 0; i < posHistory.size(); i++ ){
			if( Math.abs( posHistory.get( i )[0] ) > Math.abs( max[0] ) ){
				max[0] = posHistory.get( i )[0];
			}
			if( Math.abs( posHistory.get( i )[1] ) > Math.abs( max[1] ) ){
				max[1] = posHistory.get( i )[1];
			}
		}
		return new Dimension( ( int ) ( ( Math.abs( max[0] ) * 2 ) + TURTLE_SIZE ), ( int )( ( Math.abs( max[1] ) * 2 ) + TURTLE_SIZE ) );	
	}
	
	/**
	 * 
	 * @param visible
	 * @param needed
	 * @param zooming
	 * @return zoomingfactor to fit all on the given visible Size (if zooming is false always 1.0 ). 
	 */
	public double calculateZoom( Dimension visible, Dimension needed, boolean zooming ){
		double zoom = 1.0;
	
		
		if( zooming ){
			zoom = (double) visible.getWidth() / needed.getWidth();
			
			if ( zoom > ( (double) ( visible.getHeight() ) / needed.getHeight() ) ){
				zoom =  (double) ( visible.getHeight() ) / needed.getHeight() ;
			}
		}
		return zoom; 
	}
	
	public BufferedImage saveImage(){
		Dimension size = this.getNeededSize( this.turtlePosHistory );
		BufferedImage bImage = new BufferedImage( ( int ) size.getWidth(), ( int ) size.getHeight(), BufferedImage.TYPE_INT_ARGB );
		Graphics2D g2d = bImage.createGraphics();
		this.drawImage( g2d, size, 1.0 );
	    g2d.dispose();
	    return bImage;
	}

}

	
	
