package logo;

public class Controller {
	Gui gui;
	Interpreter interpreter;
	Parser parser;
	Turtle turtle;
	
	
	public Controller(){
		this.gui = new Gui();
		this.interpreter = new Interpreter();
		this.parser = new Parser();
		this.turtle = new Turtle();
	}
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controller control = new Controller();
		
	}

}
