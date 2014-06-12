package logo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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

/**
 * @author Marschall Steffen
 */
@SuppressWarnings("serial")
public class Gui extends JFrame implements ActionListener {
	private final int MAXIMUM_SPEED = 10;
	private final int MINIMUM_SPEED = 0; 

	private final String[] GUI_BUTTONS = new String[] { "Toggle Zoom" };
	
	private final int SPLITTING_CONTROL_BUTTONS_AT = 4;
	private final String TITLE = "LOGO-Interpreter";
	
	private final Dimension WINDOW_SIZE = new Dimension( 800, 800 );
	private final Dimension WINDOW_MINIMUM_SIZE = new Dimension( 740, 320 );
	
	String[] controlButtons;
	
	JScrollPane scrollGraph;
	GraphPane graph;
	JPanel controlPanel, controlButtonsPanel, fileButtonsPanel, graphOutputPanel, centerPane;
	
	JTextArea editor;
	
	JButton[] controlButton;
	JButton[] guiButton;
	
	JSlider speed;
	JLabel errorOutput;
	GuiListener buttonListener;
	JFileChooser fileChooser;
	
	
	/**
	 * Creates the GUI with all Components ( graphic, editor, control elements, Layouts etc. )
	 */
	public Gui( String[] buttons ){
		this.controlButtons = buttons;
		//********************************************** Window
		this.setSize( WINDOW_SIZE );
		this.setMinimumSize( WINDOW_MINIMUM_SIZE );
		this.setDefaultCloseOperation( EXIT_ON_CLOSE );
		this.getContentPane().setLayout( new BorderLayout() );
		this.setTitle( TITLE );
		
		//********************************************** FileButtonsPane
		this.fileButtonsPanel = new JPanel();
		this.getContentPane().add( this.fileButtonsPanel, BorderLayout.NORTH );
		this.fileButtonsPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		
		//********************************************** CenterPanel (Graphic, editor, programflow buttons	
		this.centerPane = new JPanel();
		this.centerPane.setLayout( new GridLayout( 1, 2 ) );
		this.getContentPane().add( this.centerPane, BorderLayout.CENTER );
		
		//********************************************** GraphicsPanel		
		graphOutputPanel = new JPanel();
		graphOutputPanel.setLayout( new BorderLayout() );
		this.centerPane.add( this.graphOutputPanel );
				
		//********************************************** TurtleGraph
		this.graph = new GraphPane();
		scrollGraph = new JScrollPane( this.graph );
		graphOutputPanel.add( this.scrollGraph, BorderLayout.CENTER );
		
		//********************************************** add error Output Label
		this.errorOutput = new JLabel();
		this.graphOutputPanel.add( errorOutput, BorderLayout.SOUTH );
		
		//********************************************** controlPanel
		this.controlPanel = new JPanel();
		this.controlPanel.setLayout( new BorderLayout() );
		this.centerPane.add( this.controlPanel );
		
		//********************************************** Editor
		this.editor = new JTextArea();
		this.editor.setTabSize( 2 );
		this.controlPanel.add( new JScrollPane( this.editor ), BorderLayout.CENTER );
		
		//********************************************** controlButtonsPanel
		this.controlButtonsPanel = new JPanel();
		this.controlPanel.add( this.controlButtonsPanel, BorderLayout.SOUTH );
		this.controlButtonsPanel.setLayout( new FlowLayout(  ) );
		

		//********************************************** initialise Control Buttons -> add to Design
		this.controlButton = new JButton[ controlButtons.length ];
		buttonListener = new GuiListener();
		
		for( int i = 0; i < controlButtons.length; i++ ){
			this.controlButton[i] = new JButton();
			this.controlButton[i].setText( this.controlButtons[i] );
			this.controlButton[i].addActionListener( this );
			if( i < SPLITTING_CONTROL_BUTTONS_AT ){
				this.fileButtonsPanel.add( this.controlButton[i] );
			}
			else{
				this.controlButtonsPanel.add( this.controlButton[i] );
			}
		}
		
		//********************************************** initialiseadditional GUI Buttons -> add to Design
		this.guiButton = new JButton[ this.GUI_BUTTONS.length ];
		
		for( int i = 0; i < this.GUI_BUTTONS.length; i++ ){
			this.guiButton[i] = new JButton();
			this.guiButton[i].setText( this.GUI_BUTTONS[i] );
			this.guiButton[i].addActionListener( this );
			this.fileButtonsPanel.add( this.guiButton[i] );
		}
		 
		//********************************************** add JSlider for Speed with Labels 
		this.speed = new JSlider( JSlider.HORIZONTAL, this.MINIMUM_SPEED, this.MAXIMUM_SPEED, 0 );
		this.speed.setMinorTickSpacing( 1 );
		this.speed.setPaintTicks( true );
		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        labels.put(0, new JLabel("0"));
        labels.put(5, new JLabel("Millisec/Step"));
        labels.put(10, new JLabel("1000"));
        this.speed.setLabelTable( labels );
        this.speed.setPaintLabels( true );
		this.fileButtonsPanel.add( this.speed );
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

		scrollGraph.getHorizontalScrollBar().setValue( ( int ) ( this.graph.getActualCenter()[0] + xPos ) );
		scrollGraph.getVerticalScrollBar().setValue( ( int ) ( this.graph.getActualCenter()[1] - yPos ) );
	}

	/**
	 * Sets output as new content of the ErrorOutputLabel, overwrites previous content.
	 * Critical output will be displayed in White on Red Background
	 * @param critical
	 * @param output
	 */
	public void setErrorOutput( String output, boolean critical ){
		this.errorOutput.setText( output );
		
		if( critical ){
			this.graphOutputPanel.setBackground( Color.RED );
			this.errorOutput.setForeground( Color.WHITE );
		}else{
			this.graphOutputPanel.setBackground( null );
			this.errorOutput.setForeground( Color.BLACK );
		}
		
	}
	
	/**
	 * Opens a FileChooser for Saving or Loading a file
	 * @param saving (true: Save, false: Load) 
	 * @return selectedFile or null if nothing selected
	 */
	public File openFileChooser( boolean saving, String[] extensions ){
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter( new FileNameExtensionFilter( extensions[0], extensions[1], extensions[2] ) );
		
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
	 * returns actual editor text.
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
				textLine[i].trim();
				
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
	
	/**
	 * toggles the zoom (unzoomed or fit-on-Size zoom)
	 */
	public void toggleZoom(){
		this.graph.toggleZoom();
	}
	
	/**
	 * draws all Component and gives the graph the "new" size it can use.
	 */
	public void paint( Graphics g ){
		super.paintComponents( g );
		this.graph.setVisibleSize( this.scrollGraph.getSize() );
		
	}
	
	
	/**
	 * gets the Picture of the GraphPane as BufferedImage
	 * @return BufferedImage
	 */
	public BufferedImage getImage(){
		return this.graph.saveImage();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for( int i = 0; i < controlButtons.length; i++ ){
			if( e.getSource().equals( this.controlButton[i] ) ){
				this.buttonListener.setLastPressedButton( controlButton[i].getText() );
				synchronized( this.buttonListener ){
					this.buttonListener.notifyAll();
				}
			}
		}
		for( int i = 0; i < this.GUI_BUTTONS.length; i++ ){
			if ( e.getSource().equals( this.guiButton[i] ) ){
				switch( this.GUI_BUTTONS[i] ){
					case "Toggle Zoom": this.toggleZoom(); break;
					default: System.out.println( "Gui Button not yet implemented" );
				}
			}
		}
	}
	
	
}