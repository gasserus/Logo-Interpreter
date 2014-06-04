package logo;

import java.util.ArrayList;

public class Parser {
	
	public ArrayList<ArrayList<String>> parse( String[] input ){
		int inputLength = input.length;
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		
		for( int i = 0; i < inputLength; i++ ){
			ArrayList<String> command = new ArrayList<String>();
			
			String parsedComment = input[ i ].split( ";" )[ 0 ];
			
			String[] parsedCommand = parsedComment.split( " " );
			
			for( int j = 0; j < parsedCommand.length; j++ ){
				System.out.println(parsedCommand[ j ]);
				command.add( parsedCommand[ j ] );
			}
			
			
			output.add( command );
			
		}
		
		return output;
	}
	


}
