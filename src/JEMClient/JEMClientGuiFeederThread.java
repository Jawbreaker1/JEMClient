package JEMClient;


/*******************************************************
 * 
 * @author Johan Engwall
 * Class runs as a thread and acts as a feeder to the 
 * gui-class. If a new message is added to the recievequeue
 * the feeder will pick it up, and forward the message to the gui.
 *******************************************************/

public class JEMClientGuiFeederThread extends Thread{
	
	
	JEMClientGui2 Gui;
	JEMClientMessageHandler Input;
	boolean running = true;
	

	public JEMClientGuiFeederThread(JEMClientGui2 gui, JEMClientMessageHandler input)
	{
		Gui = gui;
		Input = input;
	}
	
	public void run()
	{
		//check the MessageHandler if there are any recieved messages.
		while(running)
		{
			if(!(Input.recievedIsEmpty()))
			{
				Gui.recieveJEMMessage((JEMMessage)Input.getRecieved());
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//if there are, send these to the GUI.
	}
	
	public void setRunning(boolean newVal)
	{
		running = newVal;
	}
	
}
