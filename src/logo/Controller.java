package logo;

import java.util.ArrayList;

import logo.Interpreter.InterpreterException;


public class Controller {
	private final String NO_ERROR_STRING = "Everything is just fine.";
	
	Gui gui;
	Interpreter interpreter;
	Parser parser;
	Turtle turtle;
	FileHandler fileHandler;
	
	
	public Controller(){
		this.gui = new Gui();
		this.parser = new Parser();
		this.fileHandler = new FileHandler();
		
		this.newProgram();
		this.runningLogoInterpreter();
	}
	
	public void newProgram(){
		this.gui.setEditorText("");
		this.removeError();
		this.gui.graph.clearGraph();
		this.turtle = new Turtle();
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
				case "Toggle Zoom": this.gui.toggleZoom(); break;
				case "Save Image": this.saveImage(); break;
				default: System.out.println( "Button: '" + button + "' is not yet Implemented.");
			}
		
		}
		// insert Program Routine here
	}
	
	public void saveImage(){
		
		this.fileHandler.saveImage( this.gui.openFileChooser( true, this.fileHandler.getImageExtensions() ), this.gui.getImage() );
		
	}
	
	
	public void clearProgram(){
		
	}

	public void resetProgram(){
		this.gui.graph.clearGraph();
		this.resetTurtle();
		this.moveTurtle();
	}
	
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
	
		
	public void startInterpreter() throws InterpreterException{
		ArrayList<ArrayList<String>> parsedCommands;
		try {
			System.out.println( "Active: " + interpreter.isActive() );
			if( ! interpreter.isActive() ){
				this.removeError();
				parsedCommands = this.parser.parse( this.gui.getEditorText() );
				interpreter = new Interpreter( this, parsedCommands );
			}
		}
		catch( NullPointerException e ){
			this.removeError();
			parsedCommands = this.parser.parse( this.gui.getEditorText() );
			interpreter = new Interpreter( this, parsedCommands );
		}
	}
	
	
	public ArrayList<ArrayList<String>> parse(){
		ArrayList<ArrayList<String>> parsedCommands = this.parser.parse( this.gui.getEditorText() );
		return parsedCommands;
	}
	
	public void saveFile(){
		this.fileHandler.writeFile( this.gui.openFileChooser( true, this.fileHandler.getLogoExtensions() ), this.gui.getEditorText() );
	}
	
	public void loadFile(){
		try{
			String content = this.fileHandler.loadFile( this.gui.openFileChooser( false, this.fileHandler.getLogoExtensions() ) );
			this.gui.setEditorText( content );
		}catch( NullPointerException ex ){
			System.out.println( "No file selected - No Action will be done");
		}
	}
	
	public void move( int steps ){
		this.turtle.move( steps );
		this.moveTurtle();
	}
	
	public void turn( int degree ){
		this.turtle.turn( degree );
		this.moveTurtle();
	}
	
	public void changeColor( int colorChoice ){
		this.turtle.changeColor( colorChoice );
	}
	
	public void sendError( String errorText ){
		this.gui.setErrorOutput( errorText, true);
	}	
	
	public void removeError(){
		this.gui.setErrorOutput( NO_ERROR_STRING , false);
	}
	
	/**
	 * 
	 * @param isDrawing true is Drawing
	 */
	public void penDown( boolean isDrawing ){
		this.turtle.setPen( isDrawing );
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		
	Controller control = new Controller();
	}

}
