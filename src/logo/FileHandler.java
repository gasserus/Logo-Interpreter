package logo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

	
	public void writeFile( File f, ArrayList<String> content ){
		if( f != null ){
			PrintWriter writer;
			try {
				writer = new PrintWriter( f.getPath(), "UTF-8" );
				for( int i = 0; i < content.size(); i++ ){
					writer.println( content.get( i ) );
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
		if( f != null ){
			try {
				Scanner in = new Scanner(new FileReader( f ));
				while( in.hasNext() ){
					output = output + in.nextLine() + "\n";
				
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
