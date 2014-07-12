package logo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * Shows a Help Page. 
 * @author Marschall Steffen
 *
 */

@SuppressWarnings("serial")
public class HelpPage extends JFrame {

			private final String TITLE = "Commands - Help";
			private final Dimension WINDOW_SIZE = new Dimension( 500, 400 );
			private final Dimension MINIMUM_WINDOW_SIZE = new Dimension( 420, 300);
			private String[] commands;
			private final int FRAME_HEIGHT = 30;

/**
 * creates and view the HelpPage Window, expects a Stringarray with Commands and descriptions
 * @param commands (type string[])
 */
	public HelpPage( String[]  commands ){
		this.setSize( WINDOW_SIZE );
		this.setTitle( TITLE );
		this.setMinimumSize( MINIMUM_WINDOW_SIZE );
		this.commands = new String[ commands.length ];
		this.commands = commands;
		this.setVisible( true );
		
	}

	
	/**
	 * draws the HELP - Window
	 */
	public void paint( Graphics g ){
		for( int i = 0; i < commands.length; i++ ){
			if ( i % 2 == 0){
				g.setColor( Color.LIGHT_GRAY );
			}else{
				g.setColor( Color.WHITE );
			}
			int height = this.getLineHeight(); 
			g.fillRect(0, ( ( i * height ) + FRAME_HEIGHT ), this.getWidth(), height );
			g.setColor( Color.BLACK );
			
			g.drawString( commands[ i ], 10, ( ( ( i + 1 ) * height ) + ( FRAME_HEIGHT - 5 ) ) ) ;
		}
		
	}
	
	/**
	 * calculates the Maximum height per line
	 * @return Maximum Single-Column height
	 */
	private int getLineHeight(){
		return (int) ( (double) (this.getHeight() - FRAME_HEIGHT ) / commands.length );
	}
	
}
