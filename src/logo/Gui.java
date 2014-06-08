package logo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

public class Gui extends JFrame implements ActionListener {
	private final int MAXIMUM_SPEED = 10;
	private final int MINIMUM_SPEED = 1; 
	private final int AMOUNT_BUTTONS = 6;
	private final String[] BUTTON_TEXT = new String[] { "New", "Load", "Save", "Reset", "Run", "Step" };
	private final String TITLE = "LOGO-Interpreter";
	
	private final Dimension WINDOW_SIZE = new Dimension( 800, 800 );
	private final Dimension WINDOW_MINIMUM_SIZE = new Dimension( 740, 320 );
	
	JScrollPane scrollGraph;
	GraphPane graph;
	JPanel controlPanel, controlButtonsPanel, fileButtonsPanel;
	JTextArea editor;
	JButton[] controlButton = new JButton[ AMOUNT_BUTTONS ];
	JSlider speed;
	JLabel errorOutput;
	GuiListener buttonListener;
	JFileChooser fileChooser;
	
	
	/**
	 * Creates the GUI with all Components ( graphic, editor, control elements, Layouts etc. )
	 */
	public Gui(){
		
		//********************************************** Window
		this.setSize( WINDOW_SIZE );
		this.setMinimumSize( WINDOW_MINIMUM_SIZE );
		this.setDefaultCloseOperation( EXIT_ON_CLOSE );
		this.getContentPane().setLayout( new GridLayout( 1 , 2 ) );
		this.setTitle( TITLE );
		
		
		//********************************************** TurtleGraph
		this.graph = new GraphPane();
		scrollGraph = new JScrollPane( this.graph );
		this.getContentPane().add( scrollGraph );
		
		//********************************************** controlPanel
		this.controlPanel = new JPanel();
		this.controlPanel.setLayout( new BorderLayout() );
		this.getContentPane().add( this.controlPanel );
		
		//********************************************** Editor
		this.editor = new JTextArea();
		this.controlPanel.add( new JScrollPane( this.editor ), BorderLayout.CENTER );
		
		//********************************************** controlButtonsPanel
		this.controlButtonsPanel = new JPanel();
		this.controlPanel.add( this.controlButtonsPanel, BorderLayout.SOUTH );
		this.controlButtonsPanel.setLayout( new FlowLayout() );
		
		this.fileButtonsPanel = new JPanel();
		this.controlPanel.add( this.fileButtonsPanel, BorderLayout.NORTH );
		this.fileButtonsPanel.setLayout( new FlowLayout() );
		
		
		//********************************************** initialise Buttons -> add to Design
		buttonListener = new GuiListener();
		
		for( int i = 0; i < AMOUNT_BUTTONS; i++ ){
			this.controlButton[i] = new JButton();
			this.controlButton[i].setText( this.BUTTON_TEXT[i] );
			this.controlButton[i].addActionListener( this );
			if( i < 4 ){
				this.fileButtonsPanel.add( this.controlButton[i] );
			}
			else{
				this.controlButtonsPanel.add( this.controlButton[i] );
			}
		}
		 
		//********************************************** add JSlider for Speed with Labels 
		this.speed = new JSlider( JSlider.HORIZONTAL, this.MINIMUM_SPEED, this.MAXIMUM_SPEED, 10 );
		this.speed.setMinorTickSpacing( 1 );
		this.speed.setPaintTicks( true );
		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        labels.put(1, new JLabel("1"));
        labels.put(5, new JLabel("Speed"));
        labels.put(10, new JLabel("10"));
        this.speed.setLabelTable( labels );
        this.speed.setPaintLabels( true );
		this.controlButtonsPanel.add( this.speed );
		
		//********************************************** add error Output Label
		
		this.errorOutput = new JLabel();
		this.controlButtonsPanel.add( errorOutput );

		//********************************************** show
		this.setVisible(true);
	}

	

	
	/**
	 * Sends the graph the turtle movement with all additional infos 
	 * @param xPos
	 * @param yPos
	 * @param direction
	 * @param c
	 * @param visible
	 */
	public void moveTurtle( double xPos, double yPos, int direction, Color c, boolean visible ){
		double pos[] = new double[] { xPos, - yPos };
		this.graph.moveTurtle( pos, direction, c, visible );
		
	}

	/**
	 * Sets output as new content of the ErrorOutputLabel, overwrites previous content 
	 * @param output
	 */
	public void setErrorOutput( String output ){
		this.errorOutput.setText( output );
	}
	
	/**
	 * Opens a FileChooser for Saving or Loading a file
	 * @param saving (true: Save, false: Load) 
	 * @return selectedFile or null if nothing selected
	 */
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

	/**
	 * 
	 * @return actual Speed 
	 */
	public int getSpeed(){
		return this.speed.getValue();
	}
	
	/**
	 * Replaces the editor content with the new text 
	 * @param text
	 */
	public void setEditorText( String text ){
		this.editor.setText( text );
	}
	
	/**
	 * returns actual editor text. Attention return array still has newLine commands
	 * @return String[] - Each line in a new array field
	 */
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
	
	/**
	 * clears the graph (deletes all saved history Entrys of the graph) 
	 */
	public void clearGraph(){
		this.graph.clearGraph();
		this.repaint();
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