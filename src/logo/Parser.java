package logo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Parses a String Array and remove comments ( ;comments ) and empty lines
 * 
 * @author Gasser Marcel
 */
public class Parser {
	/**
	 * Amount of empty and command lines
	 */
	private HashMap<Integer,Integer> emptyLinesBeforeCommand = new HashMap<Integer,Integer>();
	
	/**
	 * This method returns always an 2D ArrayList which contains only commands and values for the interpreters.
	 * That means: no comments(;COMMENT) and no empty lines
	 * 
	 * This method also fills the emptyLinesBeforeCommand HashMap
	 * 
	 * @param inputText		String array which contains one editor line in each element
	 * @return				ArrayList containing the commands
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
				// if it is a command add it to the output command list and add an entry to the emty lines ArrayList
				outputCommandList.add( command );
				emptyLinesBeforeCommand.put( outputCommandList.size() - 1, emptyLineCounter );
			}
			else {
				emptyLineCounter++;
			}

		}
		
		return outputCommandList;
	}
	
	
	/** 
	 * Returns a HashMap which contains for each command line a number with empty lines before
	 * 
	 * @return	HashMap with emtpyline logic
	 */
	public HashMap<Integer, Integer> getEmptyLinesBeforeCommand(){
		return emptyLinesBeforeCommand;
	}
	
	
}
