package logo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class FileHandler {
	private final String FILE_EXTENSION = ".logo";
	
	public void writeFile( File f, String[] content ){
		if( f != null ){
			PrintWriter writer;
			
			try {
				
				if( f.getName().endsWith( FILE_EXTENSION ) ){
					writer = new PrintWriter( f.getPath(), "UTF-8" );
				}else{
					writer = new PrintWriter( f.getPath() + FILE_EXTENSION, "UTF-8" );
				}
				
				for( int i = 0; i < content.length; i++ ){
					writer.println( content[i] );
				}
				writer.close();
				
			} catch (FileNotFoundException e) {
				System.out.println( "Should not happen ");
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				System.out.println( "Unsupportet Encoding" );
				e.printStackTrace();
			}
		}
	}
	
	public String loadFile( File f ){
		String output = "";
		String next;
		if( f != null ){
			try {
				Scanner in = new Scanner(new FileReader( f ));
				while( in.hasNext() ){
					next = in.nextLine();
					if( !next.equals("") ){
						output = output + next + "\n";
					}
				
				}
				in.close();
			} catch (FileNotFoundException e) {
				System.out.println( "File not Found - should not happen" );
				e.printStackTrace();
			}
		}
		else{
			throw new NullPointerException();
		}
		
		return output;
	}
}
