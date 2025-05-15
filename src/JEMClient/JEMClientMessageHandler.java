package JEMClient;

import java.util.ArrayList;

/*******************************************************************
 * 
 * @author Johan Engwall
 * All messages that are beeing sent or recieved
 * go through this class. Works as the "glue" between
 * the gui and the connectionThread containing the socketconnection.
 * Contains a send list and a recieve list.
 * 
 *******************************************************************/


public class JEMClientMessageHandler {
	
	private ArrayList recievedList;
	private ArrayList sendList;
	JEMMessage message;
	
	public JEMClientMessageHandler()
	{
		recievedList = new ArrayList();
		sendList = new ArrayList();
	}
	
	//adds a message to the sendlist
	public void addSend(JEMMessage send)
	{
		sendList.add(send);
	}
	
	//picks up the top message from the sendlist. (if it isnt empty)
	public JEMMessage getSend()
	{
		message = (JEMMessage)sendList.get(0);
		sendList.remove(0);
		return message;
	}
	
	//adds a message to the recievedlist
	public void addRecieved(JEMMessage recieved)
	{
		recievedList.add(recieved);
	}
	
	//picks up the top message from the recievedlist. (if it isnt empty)
	public JEMMessage getRecieved()
	{
		message = (JEMMessage)recievedList.get(0);
		recievedList.remove(0);
		return message;
	}
	
	public boolean recievedIsEmpty()
	{
		boolean empty = false;
		if(recievedList.size()==0)
		{
			empty = true;
		}
		return empty;
		
	}
	
	public boolean sendIsEmpty()
	{
		boolean empty = false;
		if(sendList.size()==0)
		{
			empty = true;
		}
		return empty;
		
	}
	

}
