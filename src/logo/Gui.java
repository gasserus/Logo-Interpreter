package logo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

/**
 * This class sets the Layout of the Gui and allows User Interaction
 * @author Marschall Steffen
 */
@SuppressWarnings("serial")
public class Gui extends JFrame implements ActionListener {

	private final String[] COMMANDS = new String[] { 
			"forward x: 			Turtle moves x forward",
			"backward x: 			Turtle moves x backwards",
			"right x: 				Turtle rotates x degrees right",
			"left x: 				Turtle rotates x degrees left",
			"reset: 				Turtle returns to Center",
			"clear: 				Previous painted Lines will be deleted",
			"penup: 				Turtle is now drawing a line when movin (standard)",
			"pendown: 				Turtle is not drawing when moving",
			"setcolor x: 			changes color of the Turtle ( 0 <= x <= 3 )",
			"repeat x [ action ]: 	repeats the action x times ( new Line before bracket )",
			"function name par1 par2 ... [ action ]: defines a block of commands ( new Line before bracket ); parameters are optional",
			"call functionName par1 par2 ... 	call a function; the amount of parameters should be as long as the amount of parameters defined in the function",
			"let variable x: 		set variable to x",
			"INFO: 					variable name is free ( as long it's not a number )",
			"increment variable x: 	adds x to variable",
			"decrement variable x: 	remove x from variable",
			"Comments: *Command* ; Comment "};
	
	private final int MAXIMUM_SPEED = 10; 		//JSlider Maximum Value
	private final int MINIMUM_SPEED = 0; 		//JSlider Minimum Value

	private final String[] CONTROL_BUTTONS = new String[] { "New", "Load", "Save", "Save Image", "Reset", "Run", "Step" };  // All Buttons which influence the Program
	private final String[] GUI_BUTTONS = new String[] { "Toggle Zoom", "?" }; //all Buttons which only influence the GUI
	
	private final int SPLITTING_CONTROL_BUTTONS_AT = 4;  // a Value to split the Control Buttons from the top bar and the run-options
	private final String TITLE = "LOGO-Interpreter";	// the title of the Frame which will be displayed
	
	private final Dimension WINDOW_SIZE = new Dimension( 800, 600 );  		// standard window size 
	private final Dimension WINDOW_MINIMUM_SIZE = new Dimension( 700, 320 );	// Sets the minimum size of the window, so the buttons will be displayed correct

	
	//************************************************************************ GUI Graphical Elements
	JScrollPane scrollGraph;
	GraphPane graph;
	JPanel controlPanel, controlButtonsPanel, fileButtonsPanel, graphOutputPanel, centerPane;
	
	JTextArea editor;
	
	JTextField lineCounter;
	
	JButton[] controlButton;
	JButton[] guiButton;
	
	JSlider speed;
	JLabel errorOutput;
	GuiListener buttonListener;
	JFileChooser fileChooser;
	
	
	/**
	 * Creates the GUI with all Components ( graphic, editor, control elements, Layouts etc. )
	 */
	public Gui( ){
		//********************************************** Window
		this.setSize( WINDOW_SIZE );
		this.setMinimumSize( WINDOW_MINIMUM_SIZE );
		this.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE ); //EXIT_ON_CLOSE
		
		// ask if user wants close the window
		WindowListener exitListener = new WindowAdapter(){
			@Override
			public void windowClosing( WindowEvent e ) {
				int confirm = JOptionPane.showOptionDialog(null, "Are you sure to close this application?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if ( confirm == 0 ) {
					System.exit( 0 );
				}
			}
		};
		this.addWindowListener( exitListener );
		
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
		this.controlButton = new JButton[ CONTROL_BUTTONS.length ];
		buttonListener = new GuiListener();
		
		for( int i = 0; i < CONTROL_BUTTONS.length; i++ ){
			this.controlButton[i] = new JButton();
			this.controlButton[i].setText( this.CONTROL_BUTTONS[i] );
			this.controlButton[i].addActionListener( this );
			if( i < SPLITTING_CONTROL_BUTTONS_AT ){
				this.fileButtonsPanel.add( this.controlButton[i] );
			}
			else{
				this.controlButtonsPanel.add( this.controlButton[i] );
			}
		}
		
		//********************************************** initialise additional GUI Buttons 
		this.guiButton = new JButton[ this.GUI_BUTTONS.length ];
		
		for( int i = 0; i < this.GUI_BUTTONS.length; i++ ){
			this.guiButton[i] = new JButton();
			this.guiButton[i].setText( this.GUI_BUTTONS[i] );
			this.guiButton[i].addActionListener( this );
		}
		//********************************************** add Zoom Button ( guiButton[0] to GUI
		this.fileButtonsPanel.add( this.guiButton[0] ); 
		
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
		
		//********************************************** add Help Button ( guiButton[1] to GUI
		this.fileButtonsPanel.add( this.guiButton[1] ); 	
		
		//********************************************** add line counter
		this.lineCounter = new JTextField();
		this.lineCounter.setEditable( false );
		this.controlButtonsPanel.add( this.lineCounter );
		this.lineCounter.setColumns( 9 );
		this.setActualLine( 0 );
		//********************************************** show
		this.setVisible(true);
	}

	

	
	/**
	 * Sends the turtle movement with all additional infos to the Graph
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
	 * @return selectedFile or null if nothing was selected
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
	 * gets the text of the last pressed Button. This method thread sleeps until a button is pressed.
	 * @return Text of the Pressed Button (e.g. Save)
	 */
	public String awaitButtonClick(){
		return this.buttonListener.awaitButtonClick();
	}

	/**
	 * Returns the speed, which is in the Speed Slider. 
	 * @return int Speed 
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
	
	/**
	 * sets the output of the lineCounter to "Line: 'line'".
	 * @param line
	 */
	public void setActualLine( int line ){
		this.lineCounter.setText( "Line: " + line );
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for( int i = 0; i < CONTROL_BUTTONS.length; i++ ){
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
					case "?": HelpPage help = new HelpPage( COMMANDS );break;
					default: System.out.println( "Gui Button not yet implemented" );
				}
			}
		}
	}
	
	
}