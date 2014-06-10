package logo;

import java.util.ArrayList;
import java.util.HashMap;


public class Interpreter {
	private ArrayList<ArrayList<String>> parsedCommands;
	private ArrayList<Object> loop = new ArrayList<Object>();
	private HashMap<String,Integer> variables = new HashMap<String,Integer>();
	private int currentLine = 0;
	private boolean active = false;
	private Controller control;
	
	public Interpreter( Controller control, ArrayList<ArrayList<String>> parsedCommands ){
		this.setParsedCommands( parsedCommands );
		this.control = control;
		this.setActive( true );
	}
	
	public void run() {
		while( this.isActive() ){
			this.step();
		}
	}
	
	public void step( ) {

		if( ! this.loop.isEmpty() ){
			int runs = (Integer) loop.get( 1 );
			
			Interpreter loopInterpreter = (Interpreter) loop.get( 0 );
			loopInterpreter.variables = this.variables;
			loopInterpreter.step();
			this.variables = loopInterpreter.variables;

			if( ! loopInterpreter.isActive() ){
				runs--;
				loopInterpreter.setActive( true );
			}
				
			if( runs < 1 ){
				// loop is finished
				loop.clear();
				if( this.getCurrentLine() == 0 ){
					this.setActive( false );
				}
			} 
			else {
				// save new runs
				loop.set( 1, runs );
			}
		}
		else {
			int line = this.getCurrentLine();
			
			this.executeCommand( this.getParsedCommands().get( line ) );
						
			int lineAfterExec = this.getCurrentLine();
			
			line++;
			// check if a repeat has manipulated the position
			if( lineAfterExec > line ){
				this.setCurrentLine( lineAfterExec +1 );
				this.setActive( true );
			}
			else {
				this.setCurrentLine( line );
			}
		}
	}
	
	
	public void executeCommand( ArrayList<String> command ) {
		// get the first element, because this should be the command
		String commandName = command.get( 0 );
		ArrayList<String> parameters = (ArrayList<String>) command.clone();
		parameters.remove( 0 );
		
		System.out.println( "COMMAND: " + commandName );
		
		switch( commandName ){
			case "forward": 	this.forward( parameters ); break;
			case "backward": 	this.backward( parameters ); break;
			case "left": 		this.left( parameters ); break;
			case "right": 		this.right( parameters ); break;
			case "reset": 		this.reset(); break;
			case "clear": 		this.clear(); break;
			case "pendown": 	this.pendown(); break;
			case "penup": 		this.penup(); break;
			case "setcolor": 	this.setcolor( parameters ); break;
			case "repeat":		this.repeat( parameters ); break;
			case "let":			this.let( parameters ); break;
			case "increment":	this.increment( parameters ); break;
			case "decrement":	this.decrement( parameters ); break;
		}
	}
	
	
	private void forward( ArrayList<String> parameter ) {
		if( checkParameterSize( parameter, 1 ) ){
				int parameterValue = this.parseValueForVariables( parameter.get( 0 ) );
				this.control.move( parameterValue );
		}
	}
	
	
	private void backward( ArrayList<String> parameter ){
		if( checkParameterSize( parameter, 1 ) ){
				int parameterValue = this.parseValueForVariables( parameter.get( 0 ) );
				this.control.move( - parameterValue );
		}
	}
	
	
	private void left( ArrayList<String> parameter ){
		if( checkParameterSize( parameter, 1 ) ){
				int parameterValue = this.parseValueForVariables( parameter.get( 0 ) );
				this.control.turn( parameterValue );
		}
	}
	
	
	private void right( ArrayList<String> parameter ){
		if( checkParameterSize( parameter, 1 ) ){
				int parameterValue = this.parseValueForVariables( parameter.get( 0 ) );
				this.control.turn( - parameterValue );
		}
	}
	
	
	private void setcolor( ArrayList<String> parameter ) {
		if( checkParameterSize( parameter, 1 ) ){
				int parameterValue = this.parseValueForVariables( parameter.get( 0 ) );
				this.control.changeColor( parameterValue );
		}
	}
	
	
	private void repeat( ArrayList<String> parameter ){
		if( checkParameterSize( parameter, 1 ) ){
			int loopRuns = this.parseValueForVariables( parameter.get( 0 ) );
			
			int line = this.getCurrentLine();
			
			ArrayList<ArrayList<String>> allCommands = this.getParsedCommands();
			ArrayList<ArrayList<String>> loopCommands = new ArrayList<ArrayList<String>>();
		
			// next line after "repeat X"
			line++;
			
			int inLoop = 0;
			do {
				if( allCommands.get( line ).get(0).equals( "[" ) ){
					// count all [ because of innerloops
					inLoop++;
					if( inLoop == 1 ){
						line++;
					}
				}
				else if( inLoop > 0 && allCommands.get( line ).get(0).equals( "]" ) ){
					inLoop--;
				}
				
				if( inLoop > 0 ){
					loopCommands.add( allCommands.get( line ) );
				}
				
				line++;
			}
			while( inLoop > 0 && line < allCommands.size() );
			
			Interpreter loopInterpreter = new Interpreter( this.control, loopCommands );
			
			this.loop.add( loopInterpreter );
			this.loop.add( loopRuns );
			

			
			// set line pointer to ] of loop
			this.setCurrentLine( line - 1);
			this.setActive( true );
		}
	}
	
	
	private void let( ArrayList<String> parameter ){
		if( checkParameterSize( parameter, 2 ) ){
				String name = parameter.get( 0 );
				int value = this.parseValueForVariables( parameter.get( 1 ) );
				this.addVariable( name, value );
		}
	}
	

	private void increment( ArrayList<String> parameter ){
		if( checkParameterSize( parameter, 2 ) ){
				String name = parameter.get( 0 );
				int value = this.parseValueForVariables( parameter.get( 1 ) );
				int variableValue = getVariableValue( name );
				updateVariableValue( name, ( variableValue + value ) );
		}
	}
	
	
	private void decrement( ArrayList<String> parameter ){
		if( checkParameterSize( parameter, 2 ) ){
				String name = parameter.get( 0 );
				int value = this.parseValueForVariables( parameter.get( 1 ) );
				int variableValue = getVariableValue( name );
				this.updateVariableValue( name, ( variableValue - value ) );
		}
	}


	private void penup() {
		this.control.penDown( false );
	}


	private void pendown() {
		this.control.penDown( true );
	}


	private void clear() {
		this.control.clearProgram();
	}

	
	private void reset() {
		this.control.resetTurtle();
	}
	
	
	private boolean checkParameterSize( ArrayList<String> parameter, int size ) {
		int parameterSize = parameter.size();
		
		if( parameterSize == size ){
				return true;
		}
		else if( parameterSize > size ){
			// throw exception
		}
		else {
			// throw exception
		}
		return false;
	}
	
	
	private void addVariable( String key, int value ) {
		if( ! variables.containsKey( key ) && ! isNumeric( key ) ){
			variables.put( key, value );
		}
		else {
			// throw error
		}
	}
	

	private int getVariableValue( String key ){
		if( variables.containsKey( key ) ){
			return variables.get( key );
		}
		else {
			// throw error
		}
		return 0;
	}
	
	private int parseValueForVariables( String value ){
		if( this.variables.containsKey( value ) ){
			return this.variables.get( value );
		}
		else {
			try {
				return Integer.parseInt( value );
			}
			catch( NumberFormatException e ){
				// throw error
				return 0;
			}
		}
	}
	
	private void updateVariableValue( String key, int value ) {
		if( this.variables.containsKey( key ) ){
			this.variables.put( key, value );
			System.out.println( variables );
		}
		else {
			// throw error
		}
	}
	
	
	public static boolean isNumeric( String value ){  
		try {  
			double d = Double.parseDouble( value );  
		}  
		catch( NumberFormatException nfe ) {  
			return false;  
		}  
		return true;  
	}
	

	public int getCurrentLine() {
		return currentLine;
	}
	
	public void setCurrentLine( int newCurrentLine ){
		if( this.getParsedCommands().size() > newCurrentLine ){
			this.currentLine = newCurrentLine;
		}
		else {
			this.currentLine = 0;
			this.setActive( false );
		}
	}

	private ArrayList<ArrayList<String>> getParsedCommands() {
		return parsedCommands;
	}

	private void setParsedCommands( ArrayList<ArrayList<String>> parsedCommands ) {
		this.parsedCommands = parsedCommands;
	}

	public boolean isActive() {
		return active;
	}
	
	private void setActive( boolean active ) {
		this.active = active;
	}
	

}
