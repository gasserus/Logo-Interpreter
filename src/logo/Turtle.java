package logo;

public class Turtle {
	private int xPos = 0;
	private int yPos = 0;
	private int degree = 90;
	
	public void move( int steps ){
		this.yPos = yPos + steps;
		System.out.println( "Send Coords: y=" + this.yPos );
	}
	
	public void turn( int degree ){
		this.degree = this.degree + degree;
	}
	
}
