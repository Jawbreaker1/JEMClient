package JEMClient;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import java.util.StringTokenizer;

import javax.swing.SwingUtilities;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import javax.swing.BorderFactory;


import java.util.Properties;

/*****************************************************
 * 
 * This class is no longer used and will be removed.
 * Replaced by "JEMClientGui2".
 *
 *****************************************************/

public class JEMClientGui extends JFrame  {
	
	private JEMClientUser myUser;
	private JTextArea InputField;
	private JTextArea  ChatWindow;
	private JMenuBar menuBar;
	private JMenuItem menuItemDisconnect;
	private JMenuItem menuItemConnect;
	private JMenuItem menuItemExit;
	private JMenu menuFile;
	private JMenu menuStatus;
	private JMenuItem menuItemAway;
	private JMenuItem menuItemBusy;
	private JMenuItem menuItemOnline;
	private JMenuItem menuItemTimeStamp;
	private JMenu menuSettings;
	private DefaultListModel GUIlistModel = new DefaultListModel();
	private JEMClientMessageHandler messageHandler;
	private JEMClientGuiFeederThread feeder;
	private JList UserList;
	private String serverIp = "localhost";
	private int port = 80;
	private JEMClientConnectionThread ConnectionThread;
	private JEMClientLoginDialog loginDialog;
	private String userStatus;
	private boolean initiated = false;
	
	public JEMClientGui(JEMClientMessageHandler MessageHandler)
	{
		
	    /*Properties systemSettings = System.getProperties();
		systemSettings.put("http.proxyHost", "pxgot3.srv.volvo.com");
		systemSettings.put("http.proxyPort", "8080");
		System.setProperties(systemSettings);*/
			
		setTitle("JEMClient");
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		getContentPane().setLayout(null);
		setMinimumSize(new Dimension(605,520));
		setVisible(true);
		setLocation(350, 250);
		messageHandler = MessageHandler;
		//feeder = new JEMClientGuiFeederThread(this, messageHandler);
		
		//Start creating menu
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuItemConnect = new JMenuItem("Connect");
		menuItemConnect.addActionListener(new ActionListener() 
        
		{
            public void actionPerformed(ActionEvent e) 
            {
                connectActionPerformed(e);
            }
        });
		menuFile.add(menuItemConnect);
		
		menuItemDisconnect = new JMenuItem("Disconnect");
		menuItemDisconnect.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                disconnectActionPerformed();
            }
        });
		menuFile.add(menuItemDisconnect);
		
		menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	disconnectActionPerformed();
            	System.exit(0);
            }
        });
		
		menuFile.add(menuItemExit);
		
		
		menuStatus = new JMenu("Status");
		menuItemOnline = new JMenuItem("Online");
		menuItemOnline.addActionListener(new ActionListener() 
        
		{
            public void actionPerformed(ActionEvent e) 
            {
                setUserStatus("Online");
                setUserMenuStatus("Online");
            }
        });
		
		menuItemAway = new JMenuItem("Away");
		menuItemAway.addActionListener(new ActionListener() 
        
		{
            public void actionPerformed(ActionEvent e) 
            {
            	setUserStatus("Away");
            	setUserMenuStatus("Away");
            }
        });
		
		menuItemBusy = new JMenuItem("Busy");
		menuItemBusy.addActionListener(new ActionListener() 
        
		{
            public void actionPerformed(ActionEvent e) 
            {
            	setUserStatus("Busy");
            	setUserMenuStatus("Busy");
            }
        });
		
		menuStatus.add(menuItemOnline);
		menuStatus.add(menuItemAway);
		menuStatus.add(menuItemBusy);
		
		menuBar.add(menuStatus);
		
		
		menuSettings = new JMenu("Settings");
		menuItemTimeStamp = new JMenuItem("Timestamp on/off");
		menuSettings.add(menuItemTimeStamp);
		
		menuBar.add(menuSettings);
		//End creating menu
		
		ChatWindow = new JTextArea();
		ChatWindow.setEditable(false);
		ChatWindow.setLineWrap(true);
		ChatWindow.setWrapStyleWord(true);
		
		JScrollPane ChatWindowScroll = new JScrollPane(ChatWindow);
		ChatWindowScroll.setAutoscrolls(true);
		
		InputField = new JTextArea();
		InputField.setLineWrap(true);
		InputField.setWrapStyleWord(true);
		InputField.setBorder(BorderFactory.createLoweredBevelBorder());
		
		JButton SendButton = new JButton("Send");
		UserList = new JList(GUIlistModel);
		JScrollPane userListScroll = new JScrollPane(UserList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		
		
		ChatWindowScroll.setBounds(5, 5, 450, 350);
		SendButton.setBounds(360, 360,95,100);
		InputField.setBounds(5, 360, 350,100 );
		userListScroll.setBounds(460, 5, 135, 455);
		
		this.setJMenuBar(menuBar);
		this.add(InputField);
		this.add(ChatWindowScroll);
		this.add(SendButton);
		this.add(userListScroll);
		this.addWindowListener(new WindowListener()
		{
			
			public void windowClosing(WindowEvent arg0) {
				disconnectActionPerformed();
			    //System.exit(0);
			  }

			  public void windowOpened(WindowEvent arg0) {
			  }

			  public void windowClosed(WindowEvent arg0) {
			  }

			  public void windowIconified(WindowEvent arg0) {
			  }

			  public void windowDeiconified(WindowEvent arg0) {
			  }

			  public void windowActivated(WindowEvent arg0) {
			  }

			  public void windowDeactivated(WindowEvent arg0) {
			  }

		});
		
		//loginDialog = new JEMClientLoginDialog(this); //add ClientGui to the DialogConstructor
		
		SendButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                sendButtonActionPerformed(e);
            }
        });
		
		InputField.addKeyListener(new KeyAdapter() {
			     		public void keyPressed(KeyEvent e)
			     		{
			     			if(e.getKeyCode()==e.VK_ENTER)
			     			{
			     				if((!getInputField().equals("")))
			     						{
					     				addText(getInputField().trim());
					     				String tmpUser = (String)UserList.getSelectedValue();
					     				StringTokenizer st = new StringTokenizer(tmpUser);
					     				String toUser = st.nextToken();
					     				JEMMessage toSend = new JEMMessage(2,myUser.getUsername(),toUser,getInputField().trim());
					     				messageHandler.addSend(toSend);
					     				InputField.setText("");
					     				InputField.setCaretPosition(1);
			     						}
			     				InputField.setCaretPosition(1);
			     			}
			     		}
		});	     		
		
		feeder.start();
	}
	
	public void setUser(String FirstName, String Password)
	{
		initiated = true;
		myUser = new JEMClientUser(FirstName, Password);
		this.setTitle("JEMClient - "+ FirstName);
		JEMMessage toSend = new JEMMessage(1,FirstName,Password,null);
		messageHandler.addSend(toSend);
		
    	//ConnectionThread = new JEMClientConnectionThread(serverIp,port, this, messageHandler);
    	new Thread( ConnectionThread ).start();
    	//ConnectionThread.start();
		
	}
	
	public void setUserMenuStatus(String newStatus)
	{
		this.setTitle("JEMClient - " + myUser.getUsername() + "(" + newStatus + ")");
	}
	
	public void setServer(String Ip, String Port)
	{
		serverIp = Ip;
		port = Integer.parseInt(Port);
	}
	
	public String getInputField()
	{
		String tmpString = InputField.getText();
		return tmpString;
	}
	
	public void addText(String toAdd)
	{
		ChatWindow.setText(ChatWindow.getText() + "\n" + toAdd);
		ChatWindow.setCaretPosition(ChatWindow.getDocument().getLength() );

	}
	
	
	private void sendButtonActionPerformed(ActionEvent e){
	
		if((!getInputField().equals("")))
			{
			addText(getInputField());
			//create a JEMMessage and add it to the send queue
			String tmpUser = (String)UserList.getSelectedValue();
			StringTokenizer st = new StringTokenizer(tmpUser);
			String toUser = st.nextToken();
			//System.out.println("Sending to "+toUser);
			JEMMessage toSend = new JEMMessage(2,myUser.getUsername(),toUser,getInputField());
			messageHandler.addSend(toSend);
			InputField.setText("");
			}
	}
	
	private void setUserStatus(String newStatus)
	{
		setUserMenuStatus(newStatus);
		JEMMessage toSend = new JEMMessage(7,myUser.getUsername(),newStatus,null);
		messageHandler.addSend(toSend);
	}
	
	private void disconnectActionPerformed(){
		//send a disconnect message to the server
		if(initiated == true)
		{
			System.out.println("Disconnecting!");
			JEMMessage toSend = new JEMMessage(6,myUser.getUsername(),myUser.getUsername(),null);
			messageHandler.addSend(toSend);
			//wait a few ms to send the message
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ConnectionThread.stopSending();
			addText("**Disconnected from server!**");
			setUserList("");
		}
	}
	
	private void connectActionPerformed(ActionEvent e){
		//start the connectThread
		loginDialog.setVisible(true);
		System.out.println("Connecting");
	}
	
	
	
	public void recieveJEMMessage(JEMMessage message)
	{
		//1 = Login		1;user;password;null
		if(message.getTypeOfMessage()==1)
		{
			//either login is successful or it failed
			addText(message.getMessageBody());
		}
		//2 = Message		2;fromUser;toUser;message
		if(message.getTypeOfMessage()==2)
		{
			//should be easy. Just add text to the chatwindow
			addText("<"+message.getHeader1()+">"+message.getMessageBody());
			//will make the window "blink" at recieved message
			if (this.getExtendedState() == 1) 
			this.toFront();
			if ((this.getExtendedState() == 0) && (this.isFocused() == false))
			{
				this.setExtendedState(2);
				this.toFront();
			} 
			
			
			
			
		}
		//3 = KeepAlive    3;null;null;null
		if(message.getTypeOfMessage()==3)
		{
			//return a message of the same type
			addText(message.getMessageBody());
		}
		//4 = List			4;null;null;user1.user2.user3.userN
		if(message.getTypeOfMessage()==4)
		{
			//add users to the userlist
			System.out.println("List update recieved");
			setUserList(message.getMessageBody());

		}
		//5 = Register		5;user;password;null
		if(message.getTypeOfMessage()==5)
		{
			addText(message.getMessageBody());
		}
		
		//send to the correct method
	}
	
	public JEMClientUser getUser()
	{
		return myUser;
	}
	
	
	public void setUserList( final String list )
	{
	    SwingUtilities.invokeLater( new Runnable()
	    {
	        public void run()
	        {
	        	GUIlistModel.clear();
	        	StringTokenizer st = new StringTokenizer(list,",");
	        	while (st.hasMoreTokens()) 
	            {
					String User = st.nextToken();
					String Status = st.nextToken();
					System.out.println("Adding user: "+ User);
					if(!(myUser.getUsername().equals(User)))
					{
	            	GUIlistModel.addElement(User + " (" + Status+")");
					}
	            }
	        	UserList.setSelectedIndex(0);
	        }    
	    } );
	}


	

}
