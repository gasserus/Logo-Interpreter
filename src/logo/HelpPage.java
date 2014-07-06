package logo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class HelpPage extends JFrame {
	
	private final String[] COMMANDS = new String[] { 
			"forward x: Turtle moves x forward",
			"backward x: Turtle moves x backwards",
			"right x: Turtle rotates x degrees right",
			"left x: Turtle rotates x degrees left",
			"reset: Turtle returns to Center",
			"clear: Previous painted Lines will be deleted",
			"penup: Turtle is now drawing a line when movin (standard)",
			"pendown: Turtle is not drawing when moving",
			"setcolor x: changes color of the Turtle ( 0 <= x <= 3 )",
			"repeat x [ action ]: repeats the action x times ( new Line before bracket )",
			"let variable x: set variable to x",
			"INFO: variable name is free ( as long it's not a number )",
			"increment variable x: adds x to variable",
			"decrement variable x: remove x from variable"
			};
			private final String TITLE = "HELP";
			private final Dimension WINDOW_SIZE = new Dimension( 500, 400 );
			private final Dimension MINIMUM_WINDOW_SIZE = new Dimension( 420, 300);
			
			private final int FRAME_HEIGHT = 30;
			
	
	public HelpPage(){
		this.setSize( WINDOW_SIZE );
		this.setTitle( TITLE );
		this.setMinimumSize( MINIMUM_WINDOW_SIZE );
	
		this.setVisible( true );
		
	}

	public void paint( Graphics g ){
		for( int i = 0; i < COMMANDS.length; i++ ){
			if ( i % 2 == 0){
				g.setColor( Color.LIGHT_GRAY );
			}else{
				g.setColor( Color.WHITE );
			}
			int height = this.getLineHeight(); 
			g.fillRect(0, ( ( i * height ) + FRAME_HEIGHT ), this.getWidth(), height );
			g.setColor( Color.BLACK );
			g.drawString( COMMANDS[ i ], 10, ( ( ( i + 1 ) * height ) + ( FRAME_HEIGHT - 5 ) ) ) ;
		}
		
	}
	
	private int getLineHeight(){
		return (int) ( (double) (this.getHeight() - FRAME_HEIGHT ) / COMMANDS.length );
	}
	
}
