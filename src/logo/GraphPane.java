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
	private final int[] STARTING_POSITION = new int[] { 0, 0 };
	private final int TURTLE_SIZE = 35;

	Image turtleImg = null;
	ArrayList<int[]> turtlePosHistory;
	ArrayList<Color> turtleColorHistory;
	ArrayList<Boolean> turtleVisibleHistory;
	

	int direction;
	int actualMax[] = new int[] { 80, 100 };
	Dimension preferredDimension;
	
	/**
	 * creates new Graphic, sets the starting pos to turtle
	 */
	public GraphPane(){
		this.turtlePosHistory = new ArrayList<int[]>();
		this.turtleColorHistory = new ArrayList<Color>();
		this.turtleVisibleHistory = new ArrayList<Boolean>();
		this.turtlePosHistory.add( STARTING_POSITION );
		this.adjustPreferredSize( actualMax );
		turtleImg = getToolkit().getImage( "icons/turtle.png" );
		
		this.setVisible( true );
	}
	
	/**
	 * draws the whole turtle History
	 */
	public void paintComponent( Graphics g ){
		super.paintComponent( g );
		
		int[] lastPos;
		int[] targetPos;
		
		// get the actual center of the graphPanel 
		int xCenter = ( int ) ( this.getWidth() / 2.0 );
		int yCenter = ( int ) ( this.getHeight() / 2.0 );
		
		
		
		// draw History
		for( int i = 0; i < ( turtlePosHistory.size() - 1 ); i++ ){
			
			if( turtleVisibleHistory.get( i ) ){
				g.setColor( turtleColorHistory.get( i ) );
				lastPos = turtlePosHistory.get( i );
				targetPos =   turtlePosHistory.get( i + 1 );
				
				g.drawLine( ( xCenter + lastPos[0] ), ( yCenter + lastPos[1] ), ( xCenter + targetPos[0] ), (yCenter + targetPos[1]) );
		
			}
		}
		
		// draw Turtle
		
//		if( turtleImg != null  ){
//			Graphics2D g2d=(Graphics2D)g;
//			// direction from degree to radians
//			int directionRadians = (int) ( this.direction * ( Math.PI / 180 ) );
//			
//			
//			targetPos = this.turtlePosHistory.get( this.turtlePosHistory.size() -1 );
//			g2d.rotate( directionRadians );
//			//g2d.drawImage( turtleImg,  ( int ) ( targetPos[0] + xCenter - ( 0.5 *  TURTLE_SIZE ) ), ( int ) ( targetPos[1] + yCenter - ( 0.5 *  TURTLE_SIZE ) ), TURTLE_SIZE, TURTLE_SIZE, this );
//			g2d.drawImage( turtleImg, 5, 5 , 50, 50, this);
//		}
		
		
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
		this.adjustPreferredSize( posInt );
		this.direction = direction;
		this.repaint();
	}
	
	/**
	 * For the ScrollPane, calculates the new preferred size of the graph.
	 * @param pos
	 */
	public void adjustPreferredSize( int[] pos ){
		System.out.println(" test preferred " );
		if( Math.abs( pos[0] ) > Math.abs( this.actualMax[0] ) ){
			this.actualMax[0] = pos[0];
		}
		if( Math.abs( pos[1] ) > Math.abs( this.actualMax[1] ) ){
			this.actualMax[1] = pos[1];
		}
		this.setPreferredSize( new Dimension( ( int ) ( ( Math.abs( actualMax[0] ) * 2 ) + 10 ), ( int )( ( Math.abs( actualMax[1] ) * 2 ) + 10 ) ) );
		this.revalidate();
	}
	
	/**
	 * clears graph, deletes all history entrys and sets the turtle start Position back.
	 */
	public void clearGraph(){
		this.turtleColorHistory.clear();
		this.turtlePosHistory.clear();
		this.turtleVisibleHistory.clear();
		this.turtlePosHistory.add( STARTING_POSITION );
	}
	
	/**
	 * resets Turtle to its Starting position, without drawing a line
	 */
	public void turtleReset(){
		this.turtlePosHistory.add( STARTING_POSITION );
		this.turtleColorHistory.add( Color.BLACK );
		this.turtleVisibleHistory.add( false );
	}
}
