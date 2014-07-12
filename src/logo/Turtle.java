package logo;

import java.awt.Color;

/**
 * calculates Positions, saves direction, Color and if the Turtle is Drawing.
 * @author Marschall Steffen
 */
public class Turtle {
	private final int CIRCLE_DEGREES = 360;
	private final int STARTING_DEGREE = 90;
	private final Color[] TURTLE_COLOR = new Color[] { Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW };
	
	private double xStart;
	private double yStart;
	private double xPos;
	private double yPos;
	private int degree;
	private int actualColor;
	private boolean isDrawing;
	
	/**
	 * creates new Turtle object with the given StartingPosition. Starting Angle is 90(Degree), color: 0, isDrawing: true
	 * @param xStartingPos
	 * @param yStartingPos
	 */
	public Turtle( double xStartingPos, double yStartingPos ){
		this.xPos = xStartingPos;
		this.yPos = yStartingPos;
		this.degree = STARTING_DEGREE;
		this.actualColor = 0;
		this.isDrawing = true;
	}
	
	/**
	 * calculates the new Position out of the angle and the amount of steps.
	 * @param steps
	 */
	public void move( int steps ){
		this.xPos += ( Math.cos( Math.PI/180 * degree ) * steps );
		this.yPos += ( Math.sin( Math.PI/180 * degree ) * steps );
		System.out.println( "Turtle :: move\ny=" + this.yPos + "\nx=" + this.xPos + "\n" );
	}
	
	/**
	 * calculates the new angle of the Turtle ( Range is +360 to -360 ).
	 * @param degree
	 */
	public void turn( int degree ){
		this.degree = this.degree + degree;
		this.degree = this.degree % CIRCLE_DEGREES;
		
		System.out.println( "Turtle :: turn\n" + this.degree + "\n" );
	}
	
	/**
	 * tries to set the new Color of the turtle, if choice is bigger than the amount of Colors, the color remains the previous one.
	 * @param choice
	 */
	public void changeColor( int choice){
		if( choice >= 0 && choice < this.TURTLE_COLOR.length ){
			this.actualColor = choice;	
		}
	}
	
	/**
	 * returns the actual selected Color of the turtle.
	 * @return actualColor
	 */
	public Color getColor(){
		return this.TURTLE_COLOR[ actualColor ];
	}
	
	/**
	 * returns if the Turtle is actually Drawing (true) or not (false).
	 * @return isDrawing
	 */
	public boolean getVisible(){
		return this.isDrawing;
	}
	
	/**
	 * returns the Actual x - Axis Position of the turtle.
	 * @return xPosition
	 */
	public double getXPos(){
		return this.xPos;
	}

	/**
	 * returns the Actual y - Axis Position of the turtle.
	 * @return yPosition
	 */	
	public double getYPos(){
		return this.yPos;
	}
	
	/**
	 * returns actual angle of the turtle (from -360 to +360).
	 * @return degree
	 */
	public int getDirection(){
		return this.degree;
	}
	
	/**
	 * sets if the turtle is now Drawing(true) or not (false).
	 * @param isDrawing
	 */
	public void setPen( boolean isDrawing ){
		this.isDrawing = isDrawing;
	}

	/**
	 * resets all Values of the turtle
	 */
	public void reset(){
		this.xPos = xStart;
		this.yPos = yStart;
		this.degree = STARTING_DEGREE;
		this.actualColor = 0;
	}
	
}
