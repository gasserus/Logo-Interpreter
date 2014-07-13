package logo;

/**
 * This class handles the Button events, so that the Control can just await a button, instead of calling a method from the Gui directed to the Controller.
 * 
 * @author Poncho
 *
 */
public class GuiListener {
	private String lastPressedButton;
	
	
	/**
	 * 
	 * @return the last setted String in setLastpressedButton, waits until "setlastButton" is called.
	 */
	public synchronized String awaitButtonClick(){
		try{
			this.wait();
		} catch (InterruptedException ex) {
            System.out.println("GuiListener Unavailable");
            ex.getStackTrace();
        }
		return this.lastPressedButton;
	}
	
	/**
	 * sets the last pressed button, notifies the "awaitButtinClicK" thread.
	 * @param buttonName
	 */
	public void setLastPressedButton( String buttonName ){
		this.lastPressedButton = buttonName;
		synchronized( this ){
			this.notifyAll();
		}
		
	}
	
}
