package logo;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPane extends JPanel {
	ArrayList<int[]> turtleHistory;
	int direction;
	
	public void paint( Graphics g ){
		int[] lastPos;
		int[] targetPos;
		
		// draw History
		for( int i = 0; i < ( turtleHistory.size() - 1 ); i++ ){
			lastPos = turtleHistory.get( i );
			targetPos = turtleHistory.get( i + 1 );
			
			g.drawLine( lastPos[0], lastPos[1], targetPos[0], targetPos[1] );
		}
		
		
		
		// draw Turtle
	}
	
	public GraphPane(){
		turtleHistory = new ArrayList<int[]>();
		this.repaint();
	}
	
	/**
	 * 
	 * @param pos[]  pos[0] = xPos,  pos[1] = yPos
	 * @param direction
	 */
	public void moveTurtle( int pos[], int direction ){
		turtleHistory.add( pos );
		this.direction = direction;
		
	}
}
