package logo;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class Gui extends JFrame {
	int width = 800;
	int height = 800;
	GraphPane graph;
	JPanel controlPanel;
	JTextArea editor;
	JScrollPane scrollPanel;
	
	public Gui(){
		
		this.setSize( width, height );
		this.setDefaultCloseOperation( EXIT_ON_CLOSE );
		 
		this.getContentPane().setLayout( new GridLayout( 1 , 2 ) );
		
		graph = new GraphPane();
		this.getContentPane().add( graph );
		
		controlPanel = new JPanel();
		controlPanel.setLayout( new GridLayout( 2, 1 ) );
		this.getContentPane().add( controlPanel );
				
		editor = new JTextArea();
		
		
		scrollPanel = new JScrollPane();			// check again!!
		scrollPanel.setAutoscrolls( true );
		scrollPanel.setVerticalScrollBar( new JScrollBar() );
		
		controlPanel.add( scrollPanel.add( editor ) );
		
		this.setVisible(true);
	}



	public ArrayList<String> getEditorText(){
		int startOffset;
		int lengthText;
		String textLine;
		ArrayList<String> output = new ArrayList<String>();
		
		for( int i = 0; i < editor.getLineCount(); i++ ){
			
			try {
				startOffset = editor.getLineStartOffset( i );
				lengthText = editor.getLineEndOffset( i ) - startOffset ;
				
				textLine = editor.getText( startOffset, lengthText );
				output.add( textLine );
				
			} catch (BadLocationException e) {
				System.out.println( "Error at Reading out Editor Text" );
				e.printStackTrace();
			}
		}
		return output;
		
	}
	
	public void moveTurtle( int xPos, int yPos, int direction ){
		int pos[] = new int[] { xPos, yPos };
		graph.moveTurtle(pos, direction);
	}

}