package JEMClient;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.Calendar;




/***********************************************************************************
 * 
 * @author Johan Engwall
 * Handles the connection to the server by creating a socket
 * Runs as a thread as long as we are connected.
 * If a message is recieved it is added to the recievequeue in the messagehandler.
 * And if a message is ready to be sent, it is retrieved from the sendqueue.
 * Also this class handles keepalive.
 **********************************************************************************/


public class JEMClientConnectionThread implements Runnable
{
	
	private Socket serverSocket;
	private int ServerPort;
	private int myPort;
	private String serverIp;
	private JEMClientMessageHandler messageHandler;
	private boolean sending;
	private JEMClientGui2 Gui;
	
	
	public JEMClientConnectionThread(String serverIP, int serverPort, JEMClientGui2 gui,JEMClientMessageHandler MessageHandler){
		serverIp = serverIP;
		ServerPort = serverPort;
		Gui = gui;
		messageHandler = MessageHandler;
	}
	
	public void setServerPort(int port)
	{
		ServerPort = port;
	}
	
	public void setMyPort(int port)
	{
		myPort = port;
	}
	
	public int setServerPort()
	{
		return ServerPort;
	}
	
	public int setMyPort()
	{
		return myPort;
	}
	
	
	public void setSocket(Socket clientSocket) 
	{
	      this.serverSocket = clientSocket;
	}
	
	public void sendMessage(JEMMessage toSend)
	{
		//add a JEMMessage (from the messagehandler) to the sendlist
	}
	
	public void recieveMessage(String recievedText)
	{
		//create a JEMMessage from the recieved message and add this to the messagehandler.
	}
	
	public void stopSending()
	{
		sending = false;
	}
	
	public void run() 
	{
		Calendar cal = Calendar.getInstance();
		Socket socket = null;
		int timeToSendKeepalive = 0;
		long timeOfLastRecievedKeepAlive = 0;
		try 
		{
			
			socket = new Socket(serverIp, ServerPort);
			//socket.setSoTimeout(1000);
			//socket.setKeepAlive(true);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);		
			//ObjectOutputStream out =new ObjectOutputStream(socket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sending=true;
			Gui.setUserMenuStatus("Online");
			timeOfLastRecievedKeepAlive = cal.getTimeInMillis();
			while(sending) //and last keepalive message isnt too old
			{
				
				while( !(messageHandler.sendIsEmpty()) )
				{
					JEMMessage tmpSendMessage = messageHandler.getSend();
					String tmpMessage = convertMessageToString(tmpSendMessage);
					out.println(tmpMessage);
					timeToSendKeepalive=0;
					Thread.sleep(100);
				}
				
				if(in.ready())
				{
					String tmpMessage = in.readLine();
					JEMMessage tmpRecieveMessage = convertStringToMessage(tmpMessage);
					//check if this was a keepalive message! If it was, update the alive-time, and do NOT add the message to the messagehandler.
					if(tmpRecieveMessage.getTypeOfMessage()==3)
					{
						//keepalive recieved from server. Update the time from the last keepalive.
						cal = Calendar.getInstance();
						//System.out.println("Keep Alive recieved from server at: "+cal.getTimeInMillis());
						timeOfLastRecievedKeepAlive=cal.getTimeInMillis();
					}
					//if this is a normal message add to the addRecieved queue.
					if(!(tmpRecieveMessage.getTypeOfMessage()==3))
					{
						cal = Calendar.getInstance();
						timeOfLastRecievedKeepAlive=cal.getTimeInMillis();
						messageHandler.addRecieved(tmpRecieveMessage);
					}
					Thread.sleep(100);
				}
				Thread.sleep(100);
				timeToSendKeepalive++;
				if(timeToSendKeepalive==30)
				{
					timeToSendKeepalive=0;
					String myUser = Gui.getUserName();
					String keepAliveMessage = "3¤"+myUser+"¤Server¤null";
					out.println(keepAliveMessage);
				}
				//every 20 rounds we send a keepalive
				cal = Calendar.getInstance();
				long testTimeout = cal.getTimeInMillis() - timeOfLastRecievedKeepAlive;
				if(testTimeout>10000)
				{
					//System.out.println("server may have died!!");
					sending = false;
					JEMClientMessageDialog connectionFailed = new JEMClientMessageDialog("Server not responding!",Gui);
					Gui.disconnectActionPerformed();
				}
			}		
			out.close();
			in.close();
			socket.close();
			System.out.println("Connection to server killed");
			
		}

		catch (Exception e) 
		{
				//create a gui dialog telling the user the server is not responding
			JEMClientMessageDialog connectionFailed = new JEMClientMessageDialog("Server not responding!",Gui);
			Gui.disconnectActionPerformed();
			   e.printStackTrace();
		}
		
	}
	
	private String convertMessageToString(JEMMessage message)
	{
		String ret = message.getTypeOfMessage()+ "¤" + message.getHeader1() + "¤" + message.getHeader2() + "¤" + message.getMessageBody();
		return ret;
	}
	
	private JEMMessage convertStringToMessage(String message)
	{
		//here we have some converting to do. 
		StringTokenizer st = new StringTokenizer(message, "¤");
		int mType = Integer.parseInt(st.nextToken());
		String Header1 = st.nextToken();;
		String Header2 = st.nextToken();
		String body = st.nextToken();
		JEMMessage tmpJEMMessage = new JEMMessage(mType,Header1,Header2,body);
		return tmpJEMMessage;
	}
		
}
