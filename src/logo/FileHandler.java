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

/**
 * @author Marschall Steffen
 */
public class FileHandler {
	private final String LOGO_EXTENSION = ".logo";
	private final String IMAGE_EXTENSION = ".png";
	private final String[] LOGO_EXTENSIONS = new String[] { "logo", "LOGO", "Logo" };
	private final String[] IMAGE_EXTENSIONS = new String[] { "png", "PNG", "png" };
	
	
	/**
	 * writes a file out of the content. Non existing files will be created, existing file will be overwritten ( without asking). 
	 * Files without the defined extension are renamed to filename."'Extension'
	 * @param f
	 * @param content
	 */
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
	
	/**
	 * tries to read a file and returns the content as String[] each line gets a own arrayfield.
	 * @param f
	 * @return String[]
	 */
	public String loadFile( File f ){
		String output = "";
		String next;
		if( f != null  && f.getName().endsWith( this.LOGO_EXTENSION ) ){
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

	/**
	 * saves the Image to the file. Closing the program before saving the whole file will end up in a only partially painted image. 
	 * @param f
	 * @param bImage
	 */
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
	
	/**
	 * returns the, in the FileHandler defined, file Extensions
	 * @return String[]
	 */
	public String[] getLogoExtensions() {
		return LOGO_EXTENSIONS;
	}

	/**
	 * returns the, in the FileHandler defined, Picture Extensions
	 * @return String[] 
	 */
	public String[] getImageExtensions() {
		return IMAGE_EXTENSIONS;
	}
}
