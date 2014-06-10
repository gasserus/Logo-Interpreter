package logo;

import java.util.ArrayList;


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
		while ( true ){
			switch( this.gui.awaitButtonClick() ){
				case "Save": this.saveFile(); break;
				case "Load": this.loadFile(); break;
				case "Reset": this.resetProgram(); break;
				case "Run": this.startInterpreter(); interpreter.run(); break;
				case "Step": this.startInterpreter(); interpreter.step(); break;
				case "Clear": this.gui.clearGraph(); break;
				case "New": this.newProgram(); break;
				
			}
		
		}
		// insert Program Routine here
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
	
	
	public void resetTurtle(){
		this.turtle.reset();
	}
	
		
	public void startInterpreter(){
		ArrayList<ArrayList<String>> parsedCommands;
		try {
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
		this.fileHandler.writeFile( this.gui.openFileChooser( true ), this.gui.getEditorText() );
	}
	
	public void loadFile(){
		try{
			String content = this.fileHandler.loadFile( this.gui.openFileChooser( false ) );
			this.gui.setEditorText( content );
		}catch( NullPointerException ex ){
			System.out.println( "No file selected - No Action will be done");
		}
	}
	
	public void move( int steps ){
		System.out.println( steps );
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
