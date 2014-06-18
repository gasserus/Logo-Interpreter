package logo;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
	private HashMap<Integer,Integer> emptyLinesBeforeCommand = new HashMap<Integer,Integer>();
	
	/**
	 * This method returns always an 2D ArrayList which contains only commands and values for the interpreters.
	 * That means: no comments(;COMMENT) and no empty lines
	 * 
	 * @param input		is an array which contains one editor line in each element
	 * @return			the output 2D ArrayList
	 */
	public ArrayList<ArrayList<String>> parse( String[] inputText ){
		ArrayList<ArrayList<String>> outputCommandList = new ArrayList<ArrayList<String>>();
		int inputTextLineNumber = inputText.length;
		
		int emptyLineCounter = 0;
		
		for( int line = 0; line < inputTextLineNumber; line++ ){
			ArrayList<String> command = new ArrayList<String>();
			
			// remove comments
			inputText[ line ] = inputText[ line ].split( ";" )[ 0 ];
			
			// split command name form value
			String[] parsedCommand = inputText[ line ].split( " " );
				
			for( int i = 0; i < parsedCommand.length; i++ ){
				// remove empty lines
				if( !parsedCommand[ i ].trim().equals( "" ) ){
					command.add( parsedCommand[ i ].trim() );
				}
			}
			
			
			if( command.size() > 0 ){
				outputCommandList.add( command );
				emptyLinesBeforeCommand.put( outputCommandList.size() - 1, emptyLineCounter );
			}
			else {
				emptyLineCounter++;
			}

		}
		
		return outputCommandList;
	}
	
	
	public HashMap<Integer, Integer> getEmptyLinesBeforeCommand(){
		return emptyLinesBeforeCommand;
	}
	
	
}
