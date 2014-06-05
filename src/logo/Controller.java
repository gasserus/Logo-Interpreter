package logo;

import java.util.ArrayList;

//import java.util.ArrayList;

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
	
	public void newProgram(){
		this.gui.setEditorText("");
		this.gui.setErrorOutput("Everything is just fine." );
		this.turtle.reset();
	}
	
	public void runningLogoInterpreter(){
		while ( true ){
			switch( this.gui.awaitButtonClick() ){
				case "Save": this.saveFile(); break;
				case "Load": this.loadFile(); break;
				case "Reset": this.newProgram(); break;
				case "Run": this.startExecution(); break;
				
			}
		
		}
		// insert Program Routine here
	}

	public void startExecution(){
		ArrayList<ArrayList<String>> parsedCommands = this.parser.parse( this.gui.getEditorText() );
		System.out.println( parsedCommands );
		this.interpreter.startInterpreter( parsedCommands );

	}
	
	public void saveFile(){
		this.fileHandler.writeFile( this.gui.openFileChooser( true ), this.gui.getEditorText() );
	}
	
	public void loadFile(){
		String content = this.fileHandler.loadFile( this.gui.openFileChooser( false ) );
		this.gui.setEditorText( content );
	}
	
	public void move( int steps ){
		System.out.println( steps );
		this.turtle.move( steps );
		this.gui.moveTurtle( this.turtle.getXPos(), this.turtle.getYPos(), this.turtle.getDirection() , this.turtle.getColor(), this.turtle.getVisible() );
	}
	
	public void turn( int degree ){
		this.turtle.turn( degree );
	}
	
	public void changeColor( int colorChoice ){
		this.turtle.changeColor( colorChoice );
	}
	
	public void sendError( String errorText ){
		System.out.println( errorText );
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controller control = new Controller();

//		Testing setup for the Parser and Interpreter (!!! uncomment import on top)
//		String[] testStrings = { "let var 12" };
//		String[] testStrings = { "repeat 16", "[", "forward 10", "left 20", "]" };	
//		ArrayList<ArrayList<String>> parsedCommands = control.parser.parse( testStrings );
//		control.interpreter.startInterpreter( parsedCommands );
//		control.gui.setEditorText("asdsadsad\nsadasdasd");
//		control.saveFile();
//		control.gui.setEditorText("");
//		control.loadFile();
	
		
	}

}
