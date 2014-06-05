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
	ArrayList<int[]> turtlePosHistory;
	ArrayList<Color> turtleColorHistory;
	ArrayList<Boolean> turtleVisibleHistory;
	int direction;
	Color actualColor;
	boolean isDrawing = true;
	int actualMax[] = new int[] { 100, 100 };
	Dimension preferredDimension;
	
	
	
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
				targetPos = turtlePosHistory.get( i + 1 );
				
				g.drawLine( ( xCenter + lastPos[0] ), ( yCenter + lastPos[1] ), ( xCenter + targetPos[0] ), (yCenter + targetPos[1]) );
		
			}
		}
		
		
		
		// draw Turtle
	}
	
	public GraphPane(){
		this.turtlePosHistory = new ArrayList<int[]>();
		this.turtleColorHistory = new ArrayList<Color>();
		this.turtleVisibleHistory = new ArrayList<Boolean>();
		this.turtlePosHistory.add( new int[] { 0, 0 } );
		this.turtleVisibleHistory.add( true );
		this.turtleColorHistory.add( Color.black );
		this.adjustPreferredSize( actualMax );
		
		this.setVisible( true );
	}
	
	/**
	 * 
	 * @param pos[]  pos[0] = xPos,  pos[1] = yPos
	 * @param direction
	 */
	public void moveTurtle( int pos[], int direction, Color c, boolean visible ){
		this.turtlePosHistory.add( pos );
		this.turtleColorHistory.add( c );
		this.turtleVisibleHistory.add( visible );
		this.adjustPreferredSize( pos );
		this.direction = direction;
		this.repaint();
	}
	
	public void adjustPreferredSize( int[] pos ){
		if( Math.abs( pos[0] ) > Math.abs( this.actualMax[0] ) ){
			this.actualMax[0] = pos[0];
		}
		if( Math.abs( pos[1] ) > Math.abs( this.actualMax[1] ) ){
			this.actualMax[1] = pos[1];
		}

		this.setPreferredSize( new Dimension( ( ( Math.abs( actualMax[0] ) * 2 ) + 10 ), ( ( Math.abs( actualMax[1] ) * 2 ) + 10 ) ) );
	}
	
	public void drawLine( boolean showLine ){
		this.isDrawing = showLine;
	}
}
