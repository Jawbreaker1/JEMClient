package JEMClient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.util.ArrayList;


/**********************************************
 * 
 * @author Johan Engwall
 *
 *Class creates a multichat-window which contains a
 *chattextfield, an inputtextfield and a userlist.
 *After a chat with three users is started, more users can be invited
 *from the multichatwindow.
 *Users are able to join/leave "on the fly" without ending the chat.
 **********************************************/

public class JEMClientGuiMultichat extends JFrame{

	private JTextArea InputField;
	private JTextArea  ChatWindow;
	private DefaultListModel GUIlistModel = new DefaultListModel();
	private JList UserList;
	private JEMClientGui2 tmpOwner;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuItemExit;
	private JMenu menuAddUser;
	private ArrayList MultiChatMembers = new ArrayList();
	
	public JEMClientGuiMultichat(JEMClientGui2 owner, String User1, String User2,String userlist)
	{
		addUserToMultiChatMembersList(owner.getUserName());
		addUserToMultiChatMembersList(User1);
		addUserToMultiChatMembersList(User2);
		tmpOwner = owner;
		setTitle("JEMClient - "+owner.getUserName()+" MultiChat");
		getContentPane().setLayout(null);
		setMinimumSize(new Dimension(605,520));
		setVisible(true);
		setLocation(350, 250);
		
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		
		
		menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	disconnectActionPerformed();
            }
        });
		menuFile.add(menuItemExit);
		
		menuAddUser = new JMenu("Invite User to Conversation");
		menuBar.add(menuAddUser);
		
		StringTokenizer st = new StringTokenizer(userlist,",");
    	while (st.hasMoreTokens()) 
        {
			final String User = st.nextToken();
			String Status = st.nextToken();
			String Note = st.nextToken();
			String Image = st.nextToken();
			boolean alreadyInChat = false;
			
			//if user is already in this chat, go to next token
			for(int i = 0; i<MultiChatMembers.size();i++)
			{
				if(User.equals((String)MultiChatMembers.get(i)))
				{
					 alreadyInChat = true;
				}
			}
			
			
			if(!(Status.equals("Offline"))&&!(User.equals(owner.getUserName()))&&!(alreadyInChat))
			{
				JMenuItem menuItemNewUser = new JMenuItem(User + "(" + Status + ")");
				menuItemNewUser.addActionListener(new ActionListener() 
		        {
		            public void actionPerformed(ActionEvent e) 
		            {
		            	InviteUserActionPerformed(User);
		            }
		        });
			menuAddUser.add(menuItemNewUser);
			}
        }
		
		
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
		
		
		/*
		 * if((!getInputField().equals("")))
			{
			addText(getInputField());
			//create a JEMMessage and add it to the send queue
			//Get all the chatmembers and send the message to them
			//System.out.println("Sending to "+toUser);
				
			
			for (int i = 0; i<MultiChatMembers.size(); i++)
			{
				String tmpUser = (String)MultiChatMembers.get(i);
				if(!(tmpUser.equals(tmpOwner.getUserName())))
					{
					JEMMessage toSend = new JEMMessage(10,tmpOwner.getUserName(),tmpUser,getUserList().size()+","+listToString(getUserList())+getInputField());
					tmpOwner.ChatToMessageHandler(toSend);	
					}
			}
			InputField.setText("");
			}
		 */
		
		
		
		
		InputField.addKeyListener(new KeyAdapter() {
			     		public void keyPressed(KeyEvent e)
			     		{
			     			if(e.getKeyCode()==e.VK_ENTER)
			     			{
			     				if((!getInputField().equals("")))
			     					{
					     			addText(getInputField().trim());
					     				
					     			for (int i = 0; i<MultiChatMembers.size(); i++)
					     			{
					     				String tmpUser = (String)MultiChatMembers.get(i);
					     				if(!(tmpUser.equals(tmpOwner.getUserName())))
					     					{
					     					JEMMessage toSend = new JEMMessage(10,tmpOwner.getUserName(),tmpUser,getUserList().size()+","+listToString(getUserList())+getInputField());
					     					tmpOwner.ChatToMessageHandler(toSend);	
					     					}
					     			}
					     			InputField.setText("");
					     			InputField.setCaretPosition(1);
			     					}
			     			InputField.setCaretPosition(1);
			     		}
			     	}
		});	     		
		
		setUserList(User1+","+User2);
	//	feeder.start();
	
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
			//Get all the chatmembers and send the message to them
			//System.out.println("Sending to "+toUser);
				
			
			for (int i = 0; i<MultiChatMembers.size(); i++)
			{
				String tmpUser = (String)MultiChatMembers.get(i);
				if(!(tmpUser.equals(tmpOwner.getUserName())))
					{
					JEMMessage toSend = new JEMMessage(10,tmpOwner.getUserName(),tmpUser,getUserList().size()+","+listToString(getUserList())+getInputField());
					tmpOwner.ChatToMessageHandler(toSend);	
					}
			}
			InputField.setText("");
			}
	}
	
	//called when closing the window.
	//send leave message to all members of this multichat
	private void disconnectActionPerformed(){
		//send a "remove" message to all the members in this chat.
		tmpOwner.sendRemoveToMultichat(tmpOwner.getUserName(),MultiChatMembers);
		//set status to make sure everyone gets a list update.
		tmpOwner.setUserStatus("Online");
		this.setVisible(false);
	}
	
	//Should only contain the users active in this conversation
	//and should only be called when someone joins or leaves
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
					System.out.println("Adding user: "+ User);
					if(!(tmpOwner.getUserName().equals(User)))
					{
	            	GUIlistModel.addElement(User);
					}
	            }
	        	//UserList.setSelectedIndex(0);
	        }    
	    } );
	}
	
	
	public void addRemoteChatMember(String username)
	{
		addUserToMultiChatMembersList(username);
		setUserList(listToString(MultiChatMembers));
		updateInviteMenu(listToString(MultiChatMembers));
	}
	
	
	public void removeRemoteChatMember(String username)
	{
		for(int i=0;i<MultiChatMembers.size();i++)
		{
			String tmpUser = (String)MultiChatMembers.get(i);
			if(tmpUser.equals(username))
			{
				MultiChatMembers.remove(i);
			}
		}
		setUserList(listToString(MultiChatMembers));
		
		updateInviteMenu(listToString(MultiChatMembers));
		
	}
	
	
	public String listToString(ArrayList list)
	{
		String userList = "";
		for(int i=0;i<list.size();i++)
		{
			userList = userList + (String)list.get(i) + ",";
		}
		return userList;
	}
	
	//should be called whenever the maingui gets a list update.
	public void updateInviteMenu(final String userlist)
	{
		SwingUtilities.invokeLater( new Runnable()
	    {
	        public void run()
	        {
	        	menuAddUser.removeAll();
	        	StringTokenizer st = new StringTokenizer(userlist,",");
	        	while (st.hasMoreTokens()) 
	            {
	    			final String User = st.nextToken();
	    			String Status = st.nextToken();
	    			String Note = st.nextToken();
	    			String Image = st.nextToken();
	    			
	    			boolean alreadyInChat = false;
	    			
	    			//if user is already in this chat, go to next token
	    			for(int i = 0; i<MultiChatMembers.size();i++)
	    			{
	    				if(User.equals((String)MultiChatMembers.get(i)))
	    				{
	    					 alreadyInChat = true;
	    				}
	    			}
	    			
	    			if(!(Status.equals("Offline"))&&!(User.equals(tmpOwner.getUserName()))&&!(alreadyInChat))
	    			{
	    				JMenuItem menuItemNewUser = new JMenuItem(User + "(" + Status + ")");
	    				menuItemNewUser.addActionListener(new ActionListener() 
	    		        {
	    		            public void actionPerformed(ActionEvent e) 
	    		            {
	    		            	InviteUserActionPerformed(User);
	    		            }
	    		        });
	    			menuAddUser.add(menuItemNewUser);
	    			}
	            }
	        }    
	    } );
	}
	
	//should return a complete list of the users in this multichat instance.
	//used to identify the correct instance.
	public ArrayList getUserList()
	{
		return MultiChatMembers;
	}
	
	public int getNumberOfUsers(ArrayList list)
	{
		return list.size();
	}
	
	public void recievedMessage(JEMClientMultichatMessage message)
	{
		addText("<"+message.getSender()+">" + message.getMessageBody());
	}
	
	public void InviteUserActionPerformed(String User)
	{
		//tmpOwner.sendStartToMultiChat(tmpOwner.getUserName(),User);
		tmpOwner.sendAddToMultichat(tmpOwner.getUserName(),User,MultiChatMembers);
	}
	
	
	public void addUserToMultiChatMembersList(String user)
	{
		//first check that the user dont already exist
		boolean unique = true;
		for(int i = 0; i<MultiChatMembers.size();i++)
		{
			String tmpUser = (String)MultiChatMembers.get(i);
			if(tmpUser.equals(user))
			{
				unique=false;
			}
		}
		if(unique)
		{
			MultiChatMembers.add(user);
		}
		//then add
	}
	
}
