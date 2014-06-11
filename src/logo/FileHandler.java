package logo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class FileHandler {
	private final String LOGO_EXTENSION = ".logo";
	private final String IMAGE_EXTENSION = ".png";
	private final String[] LOGO_EXTENSIONS = new String[] { "logo", "LOGO", "Logo" };
	private final String[] IMAGE_EXTENSIONS = new String[] { "png", "PNG", "png" };
	
	public void writeFile( File f, String[] content ){
		if( f != null ){
			PrintWriter writer;
			
			try {
				
				if( f.getName().endsWith( LOGO_EXTENSION ) ){
					writer = new PrintWriter( f.getPath(), "UTF-8" );
				}else{
					writer = new PrintWriter( f.getPath() + LOGO_EXTENSION, "UTF-8" );
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

	public void saveImage( File f, BufferedImage bImage ){
		if ( f != null ){
			if( ! f.getName().endsWith( IMAGE_EXTENSION ) ){
				f = new File( f.getAbsolutePath() + IMAGE_EXTENSION );
			}
					try {
						ImageIO.write( bImage, IMAGE_EXTENSIONS[0], f );
						System.out.println( "save Image" );	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		}
	}
	
	
	public String[] getLogoExtensions() {
		return LOGO_EXTENSIONS;
	}

	public String[] getImageExtensions() {
		return IMAGE_EXTENSIONS;
	}
}
