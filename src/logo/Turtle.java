package logo;

import java.awt.Color;

public class Turtle {
	private final int CIRCLE_DEGREES = 360;
	
	private int xPos = 0;
	private int yPos = 0;
	private int degree = 90;
	private int actualColor = 0;
	private boolean isDrawing = true;
	private Color[] turtleColor = new Color[] { Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN };
	
	
	
	public void move( int steps ){
		this.xPos += ( int )( Math.cos( Math.PI/180 * degree ) * steps );
		this.yPos += ( int )( Math.sin( Math.PI/180 * degree ) * steps );
		System.out.println( "Send Coords: y=" + this.yPos + "x=" + this.xPos );
	}
	
	public void turn( int degree ){
		this.degree = this.degree + degree;
		this.degree = this.degree % CIRCLE_DEGREES;
		
		System.out.println( "Send direction =" + this.degree );
	}
	
	public boolean changeColor( int choice){
		if( choice >= 0 && choice < 4){
			this.actualColor = choice;
			return true;
		}else{
			return false;
		}
	}
	
	public Color getColor(){
		return this.turtleColor[ actualColor ];
	}
	
	public boolean getVisible(){
		return this.isDrawing;
	}
	
	public int getXPos(){
		return this.xPos;
	}
	
	public int getYPos(){
		return this.yPos;
	}
	
	public int getDirection(){
		return this.degree;
	}
	
	
	public void reset(){
		this.xPos = 0;
		this.yPos = 0;
		this.actualColor = 0;
		this.degree = 90;
	}
	
}
