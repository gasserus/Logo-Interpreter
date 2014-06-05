package logo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

public class Gui extends JFrame implements ActionListener {
	private final int MAXIMUM_SPEED = 10;
	private final int MINIMUM_SPEED = 1; 
	private final int AMOUNT_BUTTONS = 6;
	private final String[] BUTTON_TEXT = new String[] { "New", "Load", "Save", "Reset", "Run", "Step" };
	private final String TITLE = "LOGO-Interpreter";
	
	private final Dimension WINDOW_SIZE = new Dimension( 800, 800 );
	private final Dimension WINDOW_MINIMUM_SIZE = new Dimension( 420, 400 );
	
	GraphPane graph;
	JPanel controlPanel, controlButtonsPanel;
	JTextArea editor;
	JButton[] controlButton = new JButton[ AMOUNT_BUTTONS ];
	JSlider speed;
	JLabel errorOutput;
	GuiListener buttonListener;
	JFileChooser fileChooser;
	
	public Gui(){
		
		//********************************************** Window
		this.setSize( WINDOW_SIZE );
		this.setDefaultCloseOperation( EXIT_ON_CLOSE );
		this.getContentPane().setLayout( new GridLayout( 1 , 2 ) );
		this.setTitle( TITLE );
		this.setMinimumSize( WINDOW_MINIMUM_SIZE );
		
		//********************************************** TurtleGraph
		this.graph = new GraphPane();
		JScrollPane scrollGraph = new JScrollPane( this.graph );
		//scrollGraph.getViewport().setScrollMode( JViewport.SIMPLE_SCROLL_MODE );
		this.getContentPane().add( scrollGraph );
		
		//********************************************** controlPanel
		this.controlPanel = new JPanel();
		this.controlPanel.setLayout( new GridLayout( 2, 1 ) );
		this.getContentPane().add( this.controlPanel );
		
		//********************************************** Editor
		this.editor = new JTextArea();

		this.controlPanel.add( new JScrollPane( this.editor ) );
		
		
		//********************************************** controlButtonsPanel
		this.controlButtonsPanel = new JPanel();
		this.controlPanel.add( controlButtonsPanel );
		controlButtonsPanel.setLayout( new FlowLayout() );
		
		//********************************************** initialise Buttons, Slider, Label
		buttonListener = new GuiListener();
		
		for( int i = 0; i < AMOUNT_BUTTONS; i++ ){
			this.controlButton[i] = new JButton();
			this.controlButton[i].setText( this.BUTTON_TEXT[i] );
			this.controlButton[i].addActionListener( this );
			this.controlButtonsPanel.add( this.controlButton[i] );
		}
		
		this.speed = new JSlider();
		this.speed.setMaximum( this.MAXIMUM_SPEED );
		this.speed.setMinimum( this.MINIMUM_SPEED );
		this.controlButtonsPanel.add( this.speed );
		
		this.errorOutput = new JLabel();
		this.controlButtonsPanel.add( errorOutput );

		//********************************************** show
		this.setVisible(true);
	}

	public String[] getEditorText(){
		int startOffset;
		int lengthText;
		String textLine[] = new String[editor.getLineCount()];
		
		for( int i = 0; i < editor.getLineCount(); i++ ){
			
			try {
				startOffset = editor.getLineStartOffset( i );
				lengthText = editor.getLineEndOffset( i ) - startOffset ;
				
				textLine[i] = editor.getText( startOffset, lengthText );
				
			} catch (BadLocationException e) {
				System.out.println( "Error at Reading out Editor Text" );
				e.printStackTrace();
			}
		}
		return textLine;
		
	}
	
	public void moveTurtle( int xPos, int yPos, int direction, Color c, boolean visible ){
		int pos[] = new int[] { xPos, - yPos };
		this.graph.moveTurtle( pos, direction, c, visible );
	}

	public void setErrorOutput( String output ){
		this.errorOutput.setText( output );
	}
	
	public File openFileChooser( boolean saving ){
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter( new FileNameExtensionFilter( "logo", "LOGO", "Logo" ) );
		
		if( saving ){
			fileChooser.showSaveDialog(this.getFocusOwner() );
		}
		else{
			fileChooser.showOpenDialog( this.getFocusOwner() );
		}
		return fileChooser.getSelectedFile();	    
	}
	
	
	
	/**
	 * 
	 * @return Text of the Pressed Button (e.g. Save)
	 */
	public String awaitButtonClick(){
		return this.buttonListener.awaitButtonClick();
	}


	public int getSpeed(){
		return this.speed.getValue();
	}
	
	public void setEditorText( String text ){
		this.editor.setText( text );
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		for( int i = 0; i < AMOUNT_BUTTONS; i++ ){
			if( e.getSource().equals( this.controlButton[i] ) ){
				this.buttonListener.setLastPressedButton( controlButton[i].getText() );
				System.out.println(controlButton[i].getText()  );
				synchronized( this.buttonListener ){
					this.buttonListener.notifyAll();
				}
			}
		}
	}
	
	
}