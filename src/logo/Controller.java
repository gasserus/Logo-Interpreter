package logo;

import java.util.ArrayList;

public class Controller {
	Gui gui;
	Interpreter interpreter;
	Parser parser;
	Turtle turtle;
	FileHandler fileHandler;
	
	
	public Controller(){
		this.gui = new Gui();
		this.interpreter = new Interpreter( this );
		this.parser = new Parser();
		this.turtle = new Turtle();
		this.fileHandler = new FileHandler();
		
		this.runningLogoInterpreter();
	}
	
	public void resetProgram(){
		this.gui.setEditorText("");
		this.gui.setErrorOutput("Everything is just fine." );
		this.turtle.reset();
	}
	
	public void runningLogoInterpreter(){
		this.resetProgram();
		
		
		// insert Program Routine here
	}
	
	
	public void saveFile(){
		this.fileHandler.writeFile( this.gui.openFileChooser( true ), this.gui.getEditorText() );
	}
	
	public void loadFile(){
		String content = this.fileHandler.loadFile( this.gui.openFileChooser( false ) );
		this.gui.setEditorText( content );
	}
	
	public void move( int steps ){
		this.turtle.move( steps );
	}
	
	public void sendError( String errorText ){
		System.out.println( errorText );
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controller control = new Controller();
		
		control.gui.moveTurtle( 0, 0, 1);
		control.gui.moveTurtle( 10, -50, 1);
//		
//		ArrayList<ArrayList<String>> parsedCommands = control.parser.parse( testStrings );
//		
//		control.interpreter.interpret( parsedCommands );
		
	}

}
