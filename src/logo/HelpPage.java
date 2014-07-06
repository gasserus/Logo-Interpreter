package logo;

import java.awt.Dimension;
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
			"penup: Turtle is now drawin a line when movin (standard)",
			"pendown: Turtle is not drawing when moving",
			"setcolor x: changes color of the Turtle ( 0 <= x <= 3 )",
			"repeat x[ action ]: repeats the action x times",
			"let variable x: set variable to x",
			"INFO: variable name is free ( as long it's not a number )",
			"increment variable x: adds x to variable",
			"decrement variable x: remove x from variable"
			};
			private final String TITLE = "HELP";
			private final Dimension WINDOW_SIZE = new Dimension( 400, 400 );
		
			JTextArea info;
	
	public HelpPage(){
		this.setSize( WINDOW_SIZE );
		this.setTitle( TITLE );
		
		
		
		info = new JTextArea();
		info.setEditable( false );
		for ( int i = 0; i < COMMANDS.length; i++ ){
			info.append( COMMANDS[ i ] + "\n" );
		}
		
		this.getContentPane().add( new JScrollPane( info ) );
		
		this.setVisible( true );
		
	}

}
