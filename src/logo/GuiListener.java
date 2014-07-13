package logo;

/**
 * This class handles the Button events, so that the Control can just await a button, instead of calling a method from the Gui directed to the Controller.
 * 
 * @author Poncho
 *
 */
public class GuiListener {
	private String lastPressedButton;
	
	
	
	public synchronized String awaitButtonClick(){
		try{
			this.wait();
		} catch (InterruptedException ex) {
            System.out.println("GuiListener Unavailable");
            ex.getStackTrace();
        }
		return this.lastPressedButton;
	}
	
	public void setLastPressedButton( String buttonName ){
		this.lastPressedButton = buttonName;
		synchronized( this ){
			this.notifyAll();
		}
		
	}
	
}
