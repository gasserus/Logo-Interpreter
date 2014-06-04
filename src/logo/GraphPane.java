package logo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPane extends JPanel {
	ArrayList<int[]> turtlePosHistory;
	ArrayList<Color> turtleColorHistory;
	ArrayList<Boolean> turtleVisibleHistory;
	int direction;
	Color actualColor;
	boolean isDrawing = true;
	
	public void paint( Graphics g ){
		int[] lastPos;
		int[] targetPos;
		
		// draw History
		for( int i = 0; i < ( turtlePosHistory.size() - 1 ); i++ ){
			
			if( turtleVisibleHistory.get( i ) ){
				g.setColor( turtleColorHistory.get( i ) );
				
				lastPos = turtlePosHistory.get( i );
				targetPos = turtlePosHistory.get( i + 1 );
				
				g.drawLine( lastPos[0], lastPos[1], targetPos[0], targetPos[1] );
		
			}
		}
		
		
		
		// draw Turtle
	}
	
	public GraphPane(){
		turtlePosHistory = new ArrayList<int[]>();
		turtleColorHistory = new ArrayList<Color>();
		turtleVisibleHistory = new ArrayList<Boolean>();
		this.repaint();
	}
	
	/**
	 * 
	 * @param pos[]  pos[0] = xPos,  pos[1] = yPos
	 * @param direction
	 */
	public void moveTurtle( int pos[], int direction ){
		turtlePosHistory.add( pos );
		turtleColorHistory.add( actualColor );
		turtleVisibleHistory.add( isDrawing );
		this.direction = direction;
	}
	
	public void drawLine( boolean showLine ){
		this.isDrawing = showLine;
	}
}
