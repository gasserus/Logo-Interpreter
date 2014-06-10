package logo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GraphPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int TURTLE_SIZE = 35;
	private final String TURTLE_IMAGE_PATH = "icons/turtle.png";
	private Image turtleImg = null;

	ArrayList<int[]> turtlePosHistory;
	ArrayList<Color> turtleColorHistory;
	ArrayList<Boolean> turtleVisibleHistory;
	
	double zoomFactor = 1.0;
	int direction;
	int actualCenter[] = new int [] { 0, 0};
	int actualMax[] = new int[] { 0, 0 };
	Dimension zoomPreference;
	Dimension visibleSize;
	boolean isScaling;
	
	/**
	 * creates new Graphic, sets the starting pos to turtle
	 */
	public GraphPane(){
		this.turtlePosHistory = new ArrayList<int[]>();
		this.turtleColorHistory = new ArrayList<Color>();
		this.turtleVisibleHistory = new ArrayList<Boolean>();
		this.adjustPreferredSize( actualMax[0], actualMax[1] );
		this.turtleImg = getToolkit().createImage( TURTLE_IMAGE_PATH );
		
		this.zoomPreference = new Dimension( 0, 0 );
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
		int[] lastPos;
		int[] targetPos;
		
		//**************************************************************************** scaling Output
		if ( isScaling ){
			g2d.scale( zoomFactor, zoomFactor);
			this.setPreferredSize( this.visibleSize );
			//this.setSize( this.visibleSize );
		}
	
		//**************************************************************************** get the actual center of the graphPanel 
		
		actualCenter[0] = ( int ) ( ( this.getWidth() / 2.0 ) * ( 1 / this.zoomFactor ) );
		actualCenter[1] = ( int ) ( ( this.getHeight() / 2.0 ) * ( 1 / this.zoomFactor ) );
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
		this.adjustPreferredSize( posInt[0], posInt[1] );
		this.direction = direction;
		this.repaint();
	}
	
	/**
	 * For the ScrollPane, calculates the new preferred size of the graph.
	 * @param pos
	 */
	public void adjustPreferredSize( int xPos, int yPos ){
		if( Math.abs( xPos ) > Math.abs( this.actualMax[0] ) ){
			this.actualMax[0] = xPos;
		}
		if( Math.abs( yPos ) > Math.abs( this.actualMax[1] ) ){
			this.actualMax[1] = yPos;
		}
		this.setPreferredSize( new Dimension( ( int ) ( ( Math.abs( actualMax[0] ) * 2 ) + TURTLE_SIZE ), ( int )( ( Math.abs( actualMax[1] ) * 2 ) + TURTLE_SIZE ) ) );
		this.revalidate();
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
		this.adjustPreferredSize( actualMax[0], actualMax[1] );
	}
	
	/**
	 * 
	 * @return the center in relation to the size of the pane
	 */
	public int[] getActualCenter(){
		return actualCenter;
	}
	
	/**
	 * resets Turtle to its Starting position, without drawing a line
	 */
	public void turtleReset(){
		this.turtleColorHistory.add( Color.BLACK );
		this.turtleVisibleHistory.add( false );
	}
	
	/**
	 * sets the intern Variable, needed for correct zooming. You might want to task this method on the parents paint method
	 * @param size
	 */
	public void setVisibleSize( Dimension size ){
		this.visibleSize = new Dimension( ( int ) ( size.getWidth() - 10 ), ( int ) ( size.getHeight() - 10 ) );
	}
	
	
	/**
	 *  Calculates the ZoomFactor or restores the PreferredSize and zoomFactor. Toggles the isScaling boolean
	 */
	public void toggleZoom(){
		if( this.isScaling ){
			this.isScaling = false;
			this.zoomFactor = 1;
			for( int i = 0; i < this.turtlePosHistory.size(); i++ ){
				this.adjustPreferredSize( this.turtlePosHistory.get( i )[0], this.turtlePosHistory.get( i )[1] );
			}
			System.out.println( "Preferred Size adjusted" );
			
		}
		else{
			zoomFactor = (double) this.visibleSize.getWidth() / this.getSize().getWidth();
			
			if ( zoomFactor > ( (double) this.visibleSize.getHeight() / this.getSize().getHeight() ) ){
				zoomFactor =  (double) this.visibleSize.getHeight() / this.getSize().getHeight() ;
			}
			System.out.println( "ZoomFactor:" + this.zoomFactor );
			this.isScaling = true;
		}
		this.repaint();
		this.revalidate();
	}
}

	
