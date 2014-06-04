package logo;

//import java.util.ArrayList;

public class Controller {
	Gui gui;
	Interpreter interpreter;
	Parser parser;
	Turtle turtle;
	
	
	public Controller(){
		this.gui = new Gui();
		this.interpreter = new Interpreter( this );
		this.parser = new Parser();
		this.turtle = new Turtle();
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
		
	}

}
