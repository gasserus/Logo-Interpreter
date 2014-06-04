package logo;

import java.util.ArrayList;

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
	
	public void sendError( String errorText ){
		System.out.println( errorText );
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controller control = new Controller();
		
		control.turtle.turn(-660);
		control.turtle.move(20);
//		
//		ArrayList<ArrayList<String>> parsedCommands = control.parser.parse( testStrings );
//		
//		control.interpreter.interpret( parsedCommands );
		
	}

}
