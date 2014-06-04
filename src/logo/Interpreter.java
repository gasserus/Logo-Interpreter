package logo;

import java.util.ArrayList;
import java.util.HashMap;



public class Interpreter {
	Controller control;
	private HashMap<String,Integer> logoVariables = new HashMap<String,Integer>();
	
	
	public Interpreter( Controller control ){
		this.control = control;
	}
	

	public void startInterpreter( ArrayList<ArrayList<String>> allCommands ){
		logoVariables.clear();
		this.interpret( allCommands );
	}
	
	
	private String interpret( ArrayList<ArrayList<String>> allCommands ){
		for( int i = 0; i < allCommands.size(); i++ ){
			String command = allCommands.get( i ).get( 0 );
			
			boolean success = false;
			
			// The command list for our logo interpreter
			switch( command ){
				case "forward": 	success = this.forward( allCommands.get( i ) ); break;
				case "backward": 	success = this.backward( allCommands.get( i ) ); break;
				case "left": 		success = this.left( allCommands.get( i ) ); break;
				case "right": 		success = this.right( allCommands.get( i ) ); break;
				case "repeat":		i = this.repeat( allCommands, i ); success=true; break;
				case "reset": 		success = this.reset(); break;
				case "clear": 		success = this.clear(); break;
				case "pendown": 	success = this.pendown(); break;
				case "penup": 		success = this.penup(); break;
				case "setcolor": 	success = this.setcolor( allCommands.get( i ) ); break;
				case "let":			success = this.let( allCommands.get( i ) ); break;
				case "increment":	success = this.increment( allCommands.get( i ) ); break;
				case "decrement":	success = this.decrement( allCommands.get( i ) ); break;
				
				default: control.sendError( "unknown Command: "+ command);
			}
			
			if( success == false ){
				this.control.sendError( "Error in line " + ( i + 1 ) );
			}
			
		}
		
		return "";
	}
	
	
	private boolean setcolor( ArrayList<String> parameter ) {
		
		if( parameter.size() == 2 ){
			int setValue = getValue( parameter, 1 );
			if( setValue >= 0 && setValue <= 3 ){
				this.control.changeColor( setValue );
				return true;
			}
		}
		
		return false;
	}


	private boolean penup() {
		return false;
	}


	private boolean pendown() {
		return false;
	}


	private boolean clear() {
		return false;
	}

	
	private boolean reset() {
		this.control.resetProgram();
		return true;
	}

	
	private boolean let( ArrayList<String> parameter ){
		
		if( parameter.size() == 3 && !logoVariables.containsKey( parameter.get( 1 ) ) ){

			logoVariables.put( parameter.get( 1 ) , getValue( parameter, 2 ) );
			
			return true;
		}
		return false;
	}
	
	private int getValue( ArrayList<String> parameter, int index ){
		
		int setValue;
		if( logoVariables.containsKey( parameter.get( index ) ) ){
			setValue = logoVariables.get( parameter.get( index ) );
		}
		else {
			try {
				setValue = Integer.parseInt( parameter.get( index ) );
			}
			catch( NumberFormatException e ){
				setValue = 0;
			}
		}
		
		return setValue;
	}
	
	
	private boolean increment( ArrayList<String> parameter ){
		
		if( parameter.size() == 3 && logoVariables.containsKey( parameter.get( 1 ) ) ){

			int actualValue = logoVariables.get( parameter.get( 1 ) );
			
			logoVariables.put( parameter.get( 1 ) , actualValue + getValue( parameter, 2 ) );
			
			return true;
		}
		return false;
	}
	
	
	private boolean decrement( ArrayList<String> parameter ){
		
		if( parameter.size() == 3 && logoVariables.containsKey( parameter.get( 1 ) ) ){
			
			int actualValue = logoVariables.get( parameter.get( 1 ) );
			
			logoVariables.put( parameter.get( 1 ) , actualValue - getValue( parameter, 2 ) );
			
			return true;
		}
		return false;
	}

	private boolean forward( ArrayList<String> parameter ){
		
		if( parameter.size() == 2 ){
			this.control.move( getValue( parameter, 1 ) );
			return true;
		}
		return false;
	}
	
	
	private boolean left( ArrayList<String> parameter ){

		
		if( parameter.size() == 2 ){
			this.control.turn( getValue( parameter, 1 ) );
			return true;
		}
		return false;
	}
	
	private boolean right( ArrayList<String> parameter ){
		if( parameter.size() == 2 ){
			this.control.turn( - getValue( parameter, 1 ) );
			return true;
		}
		return false;
	}
	
	
	private boolean backward( ArrayList<String> parameter ){
		
		if( parameter.size() == 2 ){		
			this.control.move( - getValue( parameter, 1 ) );
			return true;
		}
		return false;
	}
	
	
	private int repeat( ArrayList<ArrayList<String>> allCommands, int actualPos ){
		int startPos = actualPos;
		
		// if no amount of loops is defined skip the loop input
		boolean skipLoop = false;
		if( allCommands.get( startPos ).size() < 2 ){
			skipLoop = true;
		}
		
		actualPos++;
		
		ArrayList<ArrayList<String>> loopArray =  new ArrayList<ArrayList<String>>();
		
		int inLoop = 0;
		// if loop isn't closed repeat all form bracket open till end
		do {
			if( allCommands.get( actualPos ).get(0).equals( "[" ) ){
				// count all [ because of innerloops
				inLoop++;
				if( inLoop == 1 ){
					actualPos++;
				}
			}
			else if( inLoop > 0 && allCommands.get( actualPos ).get(0).equals( "]" ) ){
				inLoop--;
			}
			
			if( inLoop > 0 && skipLoop == false ){
				loopArray.add( allCommands.get( actualPos ) );
			}
			
			actualPos++;
		}
		while( inLoop > 0 && actualPos < allCommands.size() );
		
		if( skipLoop == false ){
			if( loopArray.size() > 0 ){
				for( int l = 0; l < Integer.parseInt( allCommands.get( startPos ).get( 1 ) ); l++){
					this.interpret( loopArray );
				}
			}
			else {
				actualPos -= 2;
			}
		}
			
		return actualPos;
	}
	
}
