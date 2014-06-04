package logo;

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
	}
	
}
