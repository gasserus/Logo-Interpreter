package logo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class FileHandler {

	
	public void writeFile( File f, String[] content ){
		if( f != null ){
			PrintWriter writer;
			
			try {
				writer = new PrintWriter( f.getPath() + ".logo", "UTF-8" );
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return output;
	}
}
