package logo;

import java.util.ArrayList;

import logo.Interpreter.InterpreterException;


public class Controller {
	private final String NO_ERROR_STRING = "Everything is just fine.";
	private final double[] STARTING_POSITION = new double[] { 0.0, 0.0 };
	
	Gui gui;
	Interpreter interpreter;
	Parser parser;
	Turtle turtle;
	FileHandler fileHandler;
	
	
	public Controller(){
		this.gui = new Gui( CONTROL_COMMANDS );
		this.parser = new Parser();
		this.fileHandler = new FileHandler();
		
		this.newProgram();
		this.runningLogoInterpreter();
	}
	
	
	/**
	 * the routine for the program start and when the new Button is clicked.
	 */
	public void newProgram(){
		this.gui.setEditorText("");
		this.removeError();
		this.gui.graph.clearGraph();
		this.turtle = new Turtle( STARTING_POSITION[0], STARTING_POSITION[1] );
		this.moveTurtle();
		
	}
	
	public int getProgrammSpeedinMs(){
		return this.gui.getSpeed() * 100;
	}
	
	public void runningLogoInterpreter(){
		String button;
		while ( true ){
			button = this.gui.awaitButtonClick();
			
			switch( button ){
				case "Save":	this.saveFile(); break;
				case "Load":	this.loadFile(); break;
				case "Reset":	this.resetProgram(); break;
				case "Run":		try {
									this.startInterpreter(); 
									interpreter.run();
								}
								catch (InterpreterException e) {
									this.sendError( e.getMessage() );
									System.out.println( e.getMessage() );
								}
								break;
				case "Step":	try {
									this.startInterpreter();
									interpreter.step();
								}
								catch (InterpreterException e) {
									this.sendError( e.getMessage() );
									System.out.println( e.getMessage() );
								}
								break;
				case "New":		this.newProgram(); break;
				case "Save Image": this.saveImage(); break;
				default: System.out.println( "Button: '" + button + "' is not yet Implemented.");
			}
		
		}
		// insert Program Routine here
	}
	
	/**
	 * runs the routine to save a Picture
	 */
	public void saveImage(){
		this.fileHandler.saveImage( this.gui.openFileChooser( true, this.fileHandler.getImageExtensions() ), this.gui.getImage() );
	}
	
	/**
	 * clears the Program (Command).
	 */
	public void clearProgram(){
		
	}

	/**
	 * resets the Program (Button)
	 */
	public void resetProgram(){
		this.interpreter = null;
		this.gui.graph.clearGraph();
		this.resetTurtle();
		this.moveTurtle();
	}
	
	/**
	 * informations from the Turtle object will be sent to the gui to create a new movement history entry
	 */
	public void moveTurtle(){
		this.gui.moveTurtle( this.turtle.getXPos(), this.turtle.getYPos(), this.turtle.getDirection() , this.turtle.getColor(), this.turtle.getVisible() );
	}
	
	/**
	 * resets Turtle to its Starting Position, without Drawing (restores also Color 0 and the StartingDirection of the turtle).
	 */
	public void resetTurtle(){
		this.turtle.reset();
		this.turtle.setPen( false );
		this.moveTurtle();
		this.turtle.setPen( true );
	}
	
	public void showActualLine( int line ){
		this.gui.setActualLine( line );
	}
		
	public void startInterpreter() throws InterpreterException{
		ArrayList<ArrayList<String>> parsedCommands;
		try {
			if( ! interpreter.isActive() ){
				this.removeError();
				parsedCommands = this.parser.parse( this.gui.getEditorText() );
				interpreter = new Interpreter( this, parsedCommands, this.parser.getEmptyLinesBeforeCommand() );
			}
		}
		catch( NullPointerException e ){
			this.removeError();
			parsedCommands = this.parser.parse( this.gui.getEditorText() );
			interpreter = new Interpreter( this, parsedCommands, this.parser.getEmptyLinesBeforeCommand() );
		}
	}
	
	
	public ArrayList<ArrayList<String>> parse(){
		ArrayList<ArrayList<String>> parsedCommands = this.parser.parse( this.gui.getEditorText() );
		return parsedCommands;
	}
	
	/**
	 * saves the content of the Editor to a file
	 */
	public void saveFile(){
		this.fileHandler.writeFile( this.gui.openFileChooser( true, this.fileHandler.getLogoExtensions() ), this.gui.getEditorText() );
	}
	
	/**
	 * loads the content of a file 
	 */
	public void loadFile(){
		try{
			String content = this.fileHandler.loadFile( this.gui.openFileChooser( false, this.fileHandler.getLogoExtensions() ) );
			this.gui.setEditorText( content );
		}catch( NullPointerException ex ){
			System.out.println( "No file selected - No Action will be done");
		}
	}
	
	/**
	 * give turtle new forward/backward movement
	 * @param steps
	 */
	public void move( int steps ){
		this.turtle.move( steps );
		this.moveTurtle();
	}
	
	/**
	 * gives the turtle a new turning ( +/- degrees)
	 * @param degree
	 */
	public void turn( int degree ){
		this.turtle.turn( degree );
		this.moveTurtle();
	}
	
	/**
	 * changes the actual Color in the Turtle object
	 * @param colorChoice
	 */
	public void changeColor( int colorChoice ){
		this.turtle.changeColor( colorChoice );
	}
	
	/**
	 * sends a message to the error Output 
	 * @param errorText
	 */
	public void sendError( String errorText ){
		this.gui.setErrorOutput( errorText, true);
	}	
	
	/**
	 * removes the error from the error output
	 */
	public void removeError(){
		this.gui.setErrorOutput( NO_ERROR_STRING , false);
	}
	
	/**
	 * sets the turtles isDrawing Variable new.
	 * @param isDrawing 
	 */
	public void penDown( boolean isDrawing ){
		this.turtle.setPen( isDrawing );
	}
	

	public static void main(String[] args) {
		@SuppressWarnings("unused")
	Controller control = new Controller();
	}

}
