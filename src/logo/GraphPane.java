package logo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int[] STARTING_POSITION = new int[] { 0, 0 };
	ArrayList<int[]> turtlePosHistory;
	ArrayList<Color> turtleColorHistory;
	ArrayList<Boolean> turtleVisibleHistory;

	int direction;
	Color actualColor;
	boolean isDrawing = true;
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
				System.out.println( turtleColorHistory.get( i ));
				lastPos = turtlePosHistory.get( i );
				targetPos = turtlePosHistory.get( i + 1 );
				
				g.drawLine( ( xCenter + lastPos[0] ), ( yCenter + lastPos[1] ), ( xCenter + targetPos[0] ), (yCenter + targetPos[1]) );
		
			}
		}
		
		
		
		// draw Turtle
	}
	
	
	
	
	/**
	 * creates new turtle movement in history, repaints graph
	 * @param pos
	 * @param direction
	 * @param c
	 * @param visible
	 */
	public void moveTurtle( int pos[], int direction, Color c, boolean visible ){
		this.turtlePosHistory.add( pos );
		this.turtleColorHistory.add( c );
		this.turtleVisibleHistory.add( visible );
		this.adjustPreferredSize( pos );
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
		this.setPreferredSize( new Dimension( ( ( Math.abs( actualMax[0] ) * 2 ) + 10 ), ( ( Math.abs( actualMax[1] ) * 2 ) + 10 ) ) );
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
	
}
