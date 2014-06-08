package logo;

import java.awt.Color;

public class Turtle {
	private final int CIRCLE_DEGREES = 360;
	private final int[] STARTING_POSITION = new int[] { 0, 0 };
	private final int STARTING_DEGREE = 90;
	private final Color[] TURTLE_COLOR = new Color[] { Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW };
	
	private double xPos;
	private double yPos;
	private int degree;
	private int actualColor;
	private boolean isDrawing;
	
	
	public Turtle(){
		this.xPos = STARTING_POSITION[0];
		this.yPos = STARTING_POSITION[1];
		this.degree = STARTING_DEGREE;
		this.actualColor = 0;
		this.isDrawing = true;
	}
	
	
	public void move( int steps ){
		this.xPos += ( Math.cos( Math.PI/180 * degree ) * steps );
		this.yPos += ( Math.sin( Math.PI/180 * degree ) * steps );
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
		return this.TURTLE_COLOR[ actualColor ];
	}
	
	public boolean getVisible(){
		return this.isDrawing;
	}
	
	public double getXPos(){
		return this.xPos;
	}
	
	public double getYPos(){
		return this.yPos;
	}
	
	public int getDirection(){
		return this.degree;
	}
	
	public void setPen( boolean isDrawing ){
		this.isDrawing = isDrawing;
	}

	
	public void reset(){
		this.xPos = 0;
		this.yPos = 0;
		//this.degree = 90;
	}
	
}
