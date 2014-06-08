package logo;

import java.util.ArrayList;
import java.util.HashMap;



public class Interpreter {
	private Controller control;
	private int currentLine = 0;
	private ArrayList<ArrayList<String>> allCommands;
	private ArrayList<ArrayList<Integer>> loops = new ArrayList<ArrayList<Integer>>();
	private HashMap<String,Integer> logoVariables = new HashMap<String,Integer>();
	private int stepIndex = 0;
	
	public Interpreter( Controller control ){
		this.control = control;
	}
	
	public void interpret( boolean step ){

		// step by step
		if( step == true ){
			if( currentLine == 0 ){
				allCommands = control.parse();
				currentLine = 0;
				stepIndex = 0;
			}
			
			if( stepIndex < allCommands.size() ){
				checkLoops();
				stepIndex = currentLine;

				if( stepIndex < allCommands.size() ){
					this.executeCommand( 0 );
				}
				currentLine++;
			}
			else {
				// start again
				currentLine = 0;
			}
			
		}
		else {
			allCommands = control.parse();
			currentLine = 0;
			
			for( int index = 0; index < allCommands.size(); index++ ){
				currentLine = index;
				checkLoops();
				index = currentLine;

				if( index < allCommands.size() ){
					this.executeCommand( this.control.getProgrammSpeedinMs() );
				}
				
			}
		}
		
	}
	
	private void checkLoops(){
		System.out.println( "Loop empty: "+!loops.isEmpty() );
		if( !loops.isEmpty() ){
			
			int amountLoops = loops.size() - 1;
			
			int loopStart = loops.get( amountLoops ).get( 0 );
			int loopEnd = loops.get( amountLoops ).get( 1 );
			int loopCount = loops.get( amountLoops ).get( 2 );
			int loopPointer = loops.get( amountLoops ).get( 3 );
			
			if( currentLine < loopPointer ){
				currentLine = loopPointer;
			}

			if( loopEnd == currentLine && ( loopCount - 1 ) <= 0 ){
				loops.remove( amountLoops );
				currentLine = loopEnd + 1;
				
				if( amountLoops > 0 ){
					checkLoops();
				}
				
			}
			else if( loopEnd == currentLine && loopCount > 0 ){
				currentLine = loopStart;
				loops.get( amountLoops ).set( 2 , --loopCount );
			}
		}
	}
	
	
	private void executeCommand( int time ){
		try {
		    Thread.sleep( time );
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		String command = allCommands.get( currentLine ).get( 0 );
		boolean success = false;
		
		switch( command ){
		case "forward": 	success = this.forward( allCommands.get( currentLine ) ); break;
		case "backward": 	success = this.backward( allCommands.get( currentLine ) ); break;
		case "left": 		success = this.left( allCommands.get( currentLine ) ); break;
		case "right": 		success = this.right( allCommands.get( currentLine ) ); break;
		case "repeat":		success = this.repeat(); break;
		case "reset": 		success = this.reset(); break;
		case "clear": 		success = this.clear(); break;
		case "pendown": 	success = this.pendown(); break;
		case "penup": 		success = this.penup(); break;
		case "setcolor": 	success = this.setcolor( allCommands.get( currentLine ) ); break;
		case "let":			success = this.let( allCommands.get( currentLine ) ); break;
		case "increment":	success = this.increment( allCommands.get( currentLine ) ); break;
		case "decrement":	success = this.decrement( allCommands.get( currentLine ) ); break;
		
		default: control.sendError( currentLine + ". Line. Unknown Command: "+ command);
		}
		
		if( success == false ){
			control.sendError( currentLine + ". Line.Error with command " + command );
		}
		
	}
	
	private boolean repeat(){

		if( allCommands.get( currentLine ).size() < 2 ){
			return false;
		}
		
		int loopRuns = getValue( allCommands.get( currentLine ),  1 );
		int loopStart = 0;
		int loopEnd = 0;
		
		// next line after "repeat X"
		currentLine++;
		
		int inLoop = 0;
		do {
			if( allCommands.get( currentLine ).get(0).equals( "[" ) ){
				// count all [ because of innerloops
				inLoop++;
				if( inLoop == 1 ){
					currentLine++;
					loopStart = currentLine;
				}
			}
			else if( inLoop > 0 && allCommands.get( currentLine ).get(0).equals( "]" ) ){
				inLoop--;	
				if( inLoop == 0 ){
					loopEnd = currentLine;
				}
			}
			
			currentLine++;
		}
		while( inLoop > 0 && currentLine < allCommands.size() );
		
		if( loopStart == 0 || loopRuns == 0 ){
			return false;
		}
		
		ArrayList<Integer> loop = new ArrayList<Integer>();
		
		loop.add( loopStart );
		loop.add( loopEnd );
		loop.add( loopRuns );
		loop.add( loopStart );
		
		loops.add( loop );
				
		System.out.println("ADD LOOP");
		
		return true;
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
		this.control.penDown( false );
		return true;
	}


	private boolean pendown() {
		this.control.penDown( true );
		return true;
	}


	private boolean clear() {
		this.control.clearProgram();
		return true;
	}

	
	private boolean reset() {
		this.control.resetTurtle();
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
	
	public int getCurrentPosition(){
		return currentLine;
	}

}
