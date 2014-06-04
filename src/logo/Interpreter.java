package logo;

import java.util.ArrayList;
import java.util.HashMap;



public class Interpreter {
	Controller control;
	private HashMap<String,Integer> logoVariables = new HashMap<String,Integer>();
	
	
	public Interpreter( Controller control ){
		this.control = control;
	}
	

	//
	public void startInterpreter( ArrayList<ArrayList<String>> allCommands ){
		logoVariables.clear();
		this.interpret( allCommands );
	}
	
	
	private String interpret( ArrayList<ArrayList<String>> allCommands ){
		for( int i = 0; i < allCommands.size(); i++ ){
			String command = allCommands.get( i ).get( 0 );
			
			boolean success = false;
			
			// commandlist
			switch( command ){
				case "forward": 	success = this.forward( allCommands.get( i ) ); break;
				case "backward": 	success = this.backward( allCommands.get( i ) ); break;
				case "left": 		success = this.left( allCommands.get( i ) ); break;
				case "right": 		success = this.right( allCommands.get( i ) ); break;
				case "repeat":		i = this.repeat( allCommands, i ); success=true; break;
				case "reset": 		break;
				case "clear": 		break;
				case "pendown": 	break;
				case "penup": 		break;
				case "setcolor": 	break;
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
	
	
	/**
	 * Add a variable
	 * @param parameter
	 * @return
	 */
	private boolean let( ArrayList<String> parameter ){
		
		if( parameter.size() == 3 && !logoVariables.containsKey( parameter.get( 1 ) ) ){
			
			int setValue;
			if( logoVariables.containsKey( parameter.get( 2 ) ) ){
				setValue = logoVariables.get( parameter.get( 2 ) );
			}
			else {
				setValue = Integer.parseInt( parameter.get( 2 ) );
			}
			
			logoVariables.put( parameter.get( 1 ) , setValue );
			
			System.out.println( logoVariables );
			
			return true;
		}
		return false;
	}
	
	
	private boolean increment( ArrayList<String> parameter ){
		
		if( parameter.size() == 3 && logoVariables.containsKey( parameter.get( 1 ) ) ){
			int setValue;
			if( logoVariables.containsKey( parameter.get( 2 ) ) ){
				setValue = logoVariables.get( parameter.get( 2 ) );
			}
			else {
				setValue = Integer.parseInt( parameter.get( 2 ) );
			}
			
			int actualValue = logoVariables.get( parameter.get( 1 ) );
			
			logoVariables.put( parameter.get( 1 ) , actualValue + setValue );
			
			System.out.println( logoVariables );
			
			return true;
		}
		return false;
	}
	
	private boolean decrement( ArrayList<String> parameter ){
		
		if( parameter.size() == 3 && logoVariables.containsKey( parameter.get( 1 ) ) ){
			int setValue;
			if( logoVariables.containsKey( parameter.get( 2 ) ) ){
				setValue = logoVariables.get( parameter.get( 2 ) );
			}
			else {
				setValue = Integer.parseInt( parameter.get( 2 ) );
			}
			
			int actualValue = logoVariables.get( parameter.get( 1 ) );
			
			logoVariables.put( parameter.get( 1 ) , actualValue - setValue );
			
			return true;
		}
		return false;
	}

	private boolean forward( ArrayList<String> parameter ){
		
		if( parameter.size() == 2 ){
			this.control.move( Integer.parseInt( parameter.get( 1 ) ) );
			return true;
		}
		return false;
	}
	
	private boolean left( ArrayList<String> parameter ){

		
		if( parameter.size() == 2 ){
			this.control.turn( Integer.parseInt( parameter.get( 1 ) ) );
			return true;
		}
		return false;
	}
	
	private boolean right( ArrayList<String> parameter ){
		if( parameter.size() == 2 ){
			this.control.turn( - Integer.parseInt( parameter.get( 1 ) ) );
			return true;
		}
		return false;
	}
	
	private boolean backward( ArrayList<String> parameter ){
		
		if( parameter.size() == 2 ){
			this.control.move( - Integer.parseInt( parameter.get( 1 ) ) );
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
