package JEMClient;

/******************************************
 * This messageclass is only used during multichat
 * sessions.
 ******************************************/

import java.util.ArrayList;
import java.util.StringTokenizer;

public class JEMClientMultichatMessage {
	
	private String sender;
	private int nrOfUsers;
	private ArrayList users = new ArrayList();
	private String messageBody;
	
	public JEMClientMultichatMessage(String message, String Sender)
	{
		sender = Sender;
		StringTokenizer st = new StringTokenizer(message,",");
		nrOfUsers = Integer.parseInt(st.nextToken());
		for(int i=0; i<nrOfUsers;i++)
		{
			users.add(st.nextToken());
		}
		messageBody = st.nextToken();
		
	}

	public int getNrOfUsers()
	{
		return nrOfUsers;
	}
	
	public ArrayList getUsers()
	{
		return users;
	}
	
	public String getMessageBody()
	{
		return messageBody;
	}
	
	public String getSender()
	{
		return sender;
	}
}
