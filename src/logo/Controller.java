package logo;

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
	
	public void resetProgram(){
		this.gui.setEditorText("");
		this.gui.setErrorOutput("Everything is just fine." );
		this.turtle.reset();
	}
	
	public void runningLogoInterpreter(){
		this.resetProgram();
		
		// this.interpreter.startInterpreter()
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
		
//		control.gui.moveTurtle( 0, 0, 1);
//		control.gui.moveTurtle( 10, -50, 1);
		
	}

}
