package logo;

import java.util.ArrayList;


public class Interpreter {
	Controller control;
	
	
	public Interpreter( Controller control ){
		this.control = control;
	}
	
	public String interpret( ArrayList<ArrayList<String>> allCommands ){
		
		for( int i = 0; i < allCommands.size(); i++ ){
			String command = allCommands.get( i ).get( 0 );
			
			boolean success = false;
			
			switch( command ){
				case "forward": success = this.forward( allCommands.get( i ) ); break;
				case "backward": success = this.backward( allCommands.get( i ) ); break;
			}
			
			if( success == false ){
				this.control.sendError( "Error in line " + ( i + 1 ) );
			}
			
		}
		
		
		return "";
	}
	
	private boolean forward( ArrayList<String> parameter ){
		parameter = this.removeCommand( parameter );
		
		if( parameter.size() == 1 ){
			this.control.move( Integer.parseInt( parameter.get( 0 ) ) );
			return true;
		}
		return false;
	}
	
	private boolean backward( ArrayList<String> parameter ){
		parameter = this.removeCommand( parameter );
		
		if( parameter.size() == 1 ){
			this.control.move( - Integer.parseInt( parameter.get( 0 ) ) );
			return true;
		}
		return false;
	}
	
	private ArrayList<String> removeCommand( ArrayList<String> parameter ){
		parameter.remove( 0 );
		return parameter;
	}

}
