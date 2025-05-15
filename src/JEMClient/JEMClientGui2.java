package JEMClient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.StringTokenizer;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import java.awt.Color;

//imports for minimize
import java.net.URL;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.AWTException;
import static java.awt.SystemTray.getSystemTray;
//end imports for minimize


import java.awt.Font;


public class JEMClientGui2 extends JFrame {
	
	private JEMClientUser myUser;
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
	private JMenuItem menuItemSearch;
	private JMenuItem menuItemChangePassword;
	private JMenuItem menuItemRegisterNew;
	private JMenuItem menuItemAbout;
	private JEMClientGuiSearchDialog searchDialog;
	private JMenu menuSettings;
	private JMenu menuHelp;
	
	private JPopupMenu popupUserMenu;
	
	private DefaultListModel GUIlistModel = new DefaultListModel();
	private JEMClientMessageHandler messageHandler;
	private JEMClientGuiFeederThread feeder;
	private JList UserList;
	private String serverIp;
	private int port = 80;
	private JEMClientConnectionThread ConnectionThread;
	private String userStatus;
	private boolean initiated = false;
	//private JLabel UserLabel;
	private JEMClientGuiUserPanel UserPanel;
	private ArrayList ChatWindowsList = new ArrayList();
	private ArrayList MultiChatWindowsList;
	private ArrayList UserImageList = new ArrayList();
	private String ChatWindowMenuUserList = "";
	private JEMClientGuiLoginPanel loginDialog;
	private JScrollPane userListScroll;
	private String myCurrentUserImage = "no image";
	
	
	/*****************************
	 * Main chatwindow. Keeps a list of all available users.
	 * Doubleclick on a user, and a GuiChat window will open.
	 * Constructor creates the gui, and initially displays a loginscreen.
	 * After a successful login this is replaced by a userlist.
	 *****************************/
	
	public JEMClientGui2(JEMClientMessageHandler MessageHandler)
	{
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		addUserImage("images/JEMClient_UserImage_NoImage.jpg","no image");
		addUserImage("images/JEMClient_UserImage_Duck.jpg","duck");
		addUserImage("images/JEMClient_UserImage_leaves.jpg","leaves");
		addUserImage("images/JEMClient_UserImage_Louigi.jpg","luigi");
		addUserImage("images/JEMClient_UserImage_Manga.jpg","manga");
		addUserImage("images/JEMClient_UserImage_Mario.jpg","mario");
		addUserImage("images/JEMClient_UserImage_VolvoIT.jpg","volvo it");
		
		MultiChatWindowsList = new ArrayList();
		setTitle("JEMClient");
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		setMinimumSize(new Dimension(215,350));
		setVisible(true);
		setLocation(350, 250);
		messageHandler = MessageHandler;
		feeder = new JEMClientGuiFeederThread(this, messageHandler);
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuItemRegisterNew = new JMenuItem("Register New User");
		menuItemRegisterNew.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	registerUserActionPerformed();
            }
        });
		menuFile.add(menuItemRegisterNew);
		
		menuItemChangePassword = new JMenuItem("Change Password");
		menuItemChangePassword.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                changePasswordActionPerformed();
            }
        });
		menuFile.add(menuItemChangePassword);
		
		
		
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
		menuItemSearch = new JMenuItem("Add a contact");
		
		menuItemSearch.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	if(initiated){
                searchDialog = new JEMClientGuiSearchDialog(JEMClientGui2.this);
            	}
            	else
            	{
            	JEMClientMessageDialog notRunning = new JEMClientMessageDialog("You are not logged in!",JEMClientGui2.this);
            	}
            	
            }
        });
		menuSettings.add(menuItemSearch);
		menuSettings.add(menuItemTimeStamp);
		
		menuBar.add(menuSettings);
		
		menuHelp = new JMenu("Help");
		menuItemAbout = new JMenuItem("About");
		menuItemAbout.addActionListener(new ActionListener() 
        
		{
            public void actionPerformed(ActionEvent e) 
            {
            	JEMClientGuiAboutDialog about = new JEMClientGuiAboutDialog(JEMClientGui2.this);
            	//JEMClientMessageDialog about = new JEMClientMessageDialog("Author: Johan Engwall", JEMClientGui2.this);
            	//about.setTitle("About");
            }
        });
		menuHelp.add(menuItemAbout);
		menuBar.add(menuHelp);
		
		popupUserMenu = new JPopupMenu();
		JMenuItem menuItemRemoveUser = new JMenuItem("Delete User");
		menuItemRemoveUser.addActionListener(new ActionListener() 
      {
            public void actionPerformed(ActionEvent e) 
            {
              //send a removefriend message to the server 
               String userToRemove = (String)UserList.getSelectedValue();
               StringTokenizer st = new StringTokenizer(userToRemove);
               String userToRemoveName = st.nextToken();
               JEMMessage toSend = new JEMMessage(18,myUser.getUsername(),userToRemoveName,null);
               messageHandler.addSend( toSend );
            }
        });
		popupUserMenu.add( menuItemRemoveUser );
		
		//End creating menu
		UserPanel = new JEMClientGuiUserPanel(this);
		//UserPanel.setMinimumSize(new Dimension(230,60));
		UserPanel.setBorder(BorderFactory.createEtchedBorder());
		//UserLabel = new JLabel();
		//UserLabel.setPreferredSize(new Dimension(230,40));
		//Font userLabelFont = new Font("Arial",3,25);
		//UserLabel.setFont(userLabelFont);
		//UserLabel.setBorder(BorderFactory.createEtchedBorder());
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weighty = 0.001;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.insets = new Insets(3,3,3,3);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		//add(UserLabel,c);
		//UserLabel.setVisible(false);
		add(UserPanel,c);
		UserPanel.setVisible(false);
		
		UserList = new JList(GUIlistModel);
		//System.out.println( UserList.getCellRenderer() );
		UserList.setCellRenderer( new MyCellRenderer() );
		UserList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) 
            {
               
            	if(e.getClickCount()==2&&e.getButton()==1)
            	{
            	//get the name of the selected user
            	String tmpUser = (String)UserList.getSelectedValue();
     				StringTokenizer sT = new StringTokenizer(tmpUser);
     				String toUser = sT.nextToken();
     				//then run the createChatWindow method
     				openChatWindow(toUser);
            	}
            	
            	if(e.getButton()==3)
            	{
            	   //open a popup menu with the option to remove a user.
            	   UserList.setSelectedIndex( UserList.locationToIndex( e.getPoint() ) );
                  int selectedComponentNr = UserList.getSelectedIndex();
                  popupUserMenu.show(UserList,e.getX(),e.getY());
            	}
            	
            }
        });
		
		
		
		
		userListScroll = new JScrollPane(UserList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		userListScroll.setBounds(10, 75, 225, 360);
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.9;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		add(userListScroll,c);
		userListScroll.setVisible(false);
		this.setJMenuBar(menuBar);
		
		
		
		loginDialog = new JEMClientGuiLoginPanel(this);
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		c.weightx = 1;
		c.gridheight = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(loginDialog,c);
		
		URL resource = getClass().getResource("images/JEMClient_tray.GIF");
		Image image = Toolkit.getDefaultToolkit().getImage(resource);
		setIconImage(image);
				

		
		if (SystemTray.isSupported()) {
			final PopupMenu popup = new PopupMenu();
            final TrayIcon icon = new TrayIcon(image);
            MenuItem MaximizeItem = new MenuItem("Open JEM");
            MaximizeItem.addActionListener(new ActionListener() 
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	            	JEMClientGui2.this.setVisible(true);
                	JEMClientGui2.this.setExtendedState(JEMClientGui2.NORMAL);
                    getSystemTray().remove(icon);
	            }
	        });
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(new ActionListener() 
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	               	disconnectActionPerformed();
	            	System.exit(0);
	            }
	        });
            popup.add(MaximizeItem);
            popup.add(exitItem);
            icon.setToolTip("JEM");
            icon.setPopupMenu(popup);
            icon.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                	JEMClientGui2.this.setVisible(true);
                	JEMClientGui2.this.setExtendedState(JEMClientGui2.NORMAL);
                    getSystemTray().remove(icon);
                }

            });
            addWindowListener(new WindowAdapter() {

                @Override
                public void windowIconified(WindowEvent e) {
                	JEMClientGui2.this.setVisible(false);
                    try {
                        getSystemTray().add(icon);
                    } catch (AWTException e1) {
                        e1.printStackTrace();
                    }
                }
                
                @Override
                public void windowClosing(WindowEvent e) {
                	windowIconified(e);
                	/*JEMClientGui2.this.setVisible(false);
                    try {
                        getSystemTray().add(icon);
                    } catch (AWTException e1) {
                        e1.printStackTrace();
                    }*/
                }

            });
        }
		
		feeder.start();
	}
	
		/******************************
		 * This method is run after the login
		 * window is closed.
		 * Initiates a connection to the server.
		 ******************************/
		public void setUser(String FirstName, String Password)
		{
			//UserLabel.setVisible(true);
			UserPanel.setVisible(true);
			userListScroll.setVisible(true);
			
			initiated = true;
			myUser = new JEMClientUser(FirstName, Password);
			this.setTitle("JEMClient - "+ FirstName);
			//UserLabel.setText(FirstName);
			UserPanel.setUserName(FirstName);
			JEMMessage toSend = new JEMMessage(1,FirstName,Password,null);
			messageHandler.addSend(toSend);
			
	    	ConnectionThread = new JEMClientConnectionThread(serverIp,port, this, messageHandler);
	    	new Thread( ConnectionThread ).start();
			
		}
		
		
		/******************************
		 * This method is run after the login
		 * window is closed.
		 * Initiates a connection to the server.
		 ******************************/
		public void setUserForRegistration(String FirstName, String Password)
		{
			//UserLabel.setVisible(true);
			//userListScroll.setVisible(true);
			
			initiated = true;
			myUser = new JEMClientUser(FirstName, Password);
			//this.setTitle("JEMClient - "+ FirstName);
			//UserLabel.setText(FirstName);
			//JEMMessage toSend = new JEMMessage(1,FirstName,Password,null);
			//messageHandler.addSend(toSend);
			
	    	ConnectionThread = new JEMClientConnectionThread(serverIp,port, this, messageHandler);
	    	new Thread( ConnectionThread ).start();
			
		}
		
			
		/*****************************
		 * Changes the Menu titlebar with a new status
		 *****************************/
		public void setUserMenuStatus(String newStatus)
		{
			this.setTitle("JEMClient - " + myUser.getUsername() + "(" + newStatus + ")");
			//UserLabel.setText(myUser.getUsername() + "(" + newStatus + ")");
			UserPanel.setStatus(newStatus);
		}
		
		/*****************************
		 * Set the serverIP to connect to.
		 *****************************/
		public void setServer(String Ip, String Port)
		{
			serverIp = Ip;
			port = Integer.parseInt(Port);
		}
		
		/*****************************
		 * Set user status (Online, Away or Busy)
		 *****************************/
		public void setUserStatus(String newStatus)
		{
			setUserMenuStatus(newStatus);
			JEMMessage toSend = new JEMMessage(7,myUser.getUsername(),newStatus,null);
			messageHandler.addSend(toSend);
		}
		
		
		/*****************************
		 * Set user Note (free text)
		 *****************************/
		public void setUserNote(String newStatus)
		{
			JEMMessage toSend = new JEMMessage(14,myUser.getUsername(),newStatus,null);
			messageHandler.addSend(toSend);
		}
		
		
		/*****************************
		 * Send search user message
		 *****************************/
		public void sendSearch(String searchArguments)
		{
			JEMMessage toSend = new JEMMessage(16,myUser.getUsername(),"server",searchArguments);
			messageHandler.addSend(toSend);
		}
		
		/*****************************
		 * recieve search user message
		 *****************************/
		public void recieveSearchResults(String result)
		{
			String nick =""; 
    		String first = "";
    		String last="";
			System.out.println("result recieved!!");
			StringTokenizer ST = new StringTokenizer(result,",");
        	while (ST.hasMoreTokens()) 
            {
        		 nick = ST.nextToken();
        		 first = ST.nextToken();
        		 last = ST.nextToken();
            }
			searchDialog.addToTable(nick,first,last);
			
		}
		
		/*****************************
		 * Tell server to add this user to the userlist
		 *****************************/
		public void sendAddUser(String userToAdd)
		{
			JEMMessage toSend = new JEMMessage(17,myUser.getUsername(),userToAdd,null);
			messageHandler.addSend(toSend);
		}
		
		/*****************************
		 * Set user Image
		 *****************************/
		public void setMyImage(String newImage)
		{
			myCurrentUserImage = newImage;
			JEMMessage toSend = new JEMMessage(15,myUser.getUsername(),newImage,null);
			messageHandler.addSend(toSend);
		}
		
		public String getMyImage()
		{
			return myCurrentUserImage;
		}
		
		/*****************************
		 * Disconnects from the server by sending
		 * a "logoff" message.
		 * Then wait 1 second for the message to be handled,
		 * and then end the connectionThread.
		 *****************************/
		public void disconnectActionPerformed(){
			//send a disconnect message to the server
			if(initiated == true)
			{
				System.out.println("Disconnecting!");
				setUserMenuStatus("Offline");
				JEMMessage toSend = new JEMMessage(6,myUser.getUsername(),myUser.getUsername(),null);
				messageHandler.addSend(toSend);
				//wait a few ms for the message to be delivered
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ConnectionThread.stopSending();
				//UserLabel.setVisible(false);
				UserPanel.setVisible(false);
				userListScroll.setVisible(false);
				loginDialog.setVisible(true);
				initiated = false;
				setUserList("");
			}
		}
		
		/*****************************
		 * Create a login dialog. No longer used.
		 *****************************/
		private void connectActionPerformed(ActionEvent e){
			//start the connectThread
			loginDialog.setVisible(true);
			System.out.println("Connecting");
		}
		
		/*****************************
		 * Create a password change dialog
		 *****************************/
		private void changePasswordActionPerformed()
		{
			//need to be logged in in order to change password
			if(initiated)
			{
			JEMClientPasswordDialog passwordDialog = new JEMClientPasswordDialog(this, myUser.getUsername());
			}
			else
			{
				JEMClientMessageDialog failedPasswordChange = new JEMClientMessageDialog("Need to be logged in!", this);
			}
			
		}
		
		/*****************************
		 * Create and send a password change message
		 * after closing the passwordChange dialog
		 *****************************/
		public void sendPasswordChange(String newPassword)
		{
			JEMMessage newPasswordMessage = new JEMMessage(8,myUser.getUsername(),newPassword,null);
			messageHandler.addSend(newPasswordMessage);
		}
		
		/*****************************
		 * Create and send a user registration message
		 * after closing the registration dialog
		 *****************************/
		public void sendRegistration(String userName,String firstName,String lastName, String password,String userInfo)
		{
		   String name = firstName + ","+lastName;
			JEMMessage RegistrationMessage = new JEMMessage(9,userName,password,name);
			messageHandler.addSend(RegistrationMessage);
		}
		
		/*****************************
		 * Create a registration dialog
		 *****************************/
		private void registerUserActionPerformed()
		{
			if(initiated)
			{
				JEMClientMessageDialog failedPasswordChange = new JEMClientMessageDialog("Please log out first!", this);
			}
			else
			{
				JEMClientRegisterDialog registerDialog = new JEMClientRegisterDialog(this);
			}
		}
		
		/*****************************
		 * Method for handling recieved messages
		 *****************************/
		public void recieveJEMMessage(JEMMessage message)
		{
			//1 = Login		1;user;password;null
			if(message.getTypeOfMessage()==1)
			{
				//either login is successful or it failed
				setUserMenuStatus("Offline");
				JEMClientMessageDialog failedLogin = new JEMClientMessageDialog("Incorrect login, or already logged in!", this);
				ConnectionThread.stopSending();
				//UserLabel.setVisible(false);
				UserPanel.setVisible(false);
				userListScroll.setVisible(false);
				loginDialog.setVisible(true);
			}
			//2 = Message		2;fromUser;toUser;message
			if(message.getTypeOfMessage()==2)
			{
				//should be easy. Just add text to the chatwindow
				getRemoteUserImage(message.getHeader1());
				//imagetest
				String tmpName = message.getHeader1();
				openChatWindow(tmpName);
				addTextToChat(tmpName,message.getMessageBody());	
			}
			//3 = Info    3;null;null;null
			if(message.getTypeOfMessage()==3)
			{
				//return a message of the same type
				//addText(message.getMessageBody())
			}
			//4 = List			4;null;null;user1.user2.user3.userN
			if(message.getTypeOfMessage()==4)
			{
				//add users to the userlist
				//System.out.println("List update recieved");
				setUserList(message.getMessageBody());
				chatWinUpdateUsers(message.getMessageBody());
				chatWinUpdateRemoteImage();
				MultichatWinUpdateUsers(message.getMessageBody());

			}
			
			if(message.getTypeOfMessage()==14)
			{
				//add users to the userlist
				//System.out.println("List update recieved");
				setUserList(message.getMessageBody());

			}
			
			
			
			
			if(message.getTypeOfMessage()==8)
			{
				//return a message of the same type
				//addText(message.getMessageBody());
				JEMClientMessageDialog passwordChangeReply = new JEMClientMessageDialog(message.getMessageBody(), this);
			}
			//9 = Register		9;user;password;null
			if(message.getTypeOfMessage()==9)
			{
				//addText(message.getMessageBody());
				JEMClientMessageDialog registration = new JEMClientMessageDialog(message.getMessageBody(), this);
				disconnectActionPerformed();
			}
			
			
			//10 = Multichat message	10;fromUser;toUser;NumberOfUsers,user1,user2..userN,message
			if(message.getTypeOfMessage()==10)
			{
				//addText(message.getMessageBody());
				//find the correct multichat window
				//get the users in the messagebody first. (add number too?)
				//find a chatwindow that match this number of users
				//then check its the correct users
				//then forward the message there.
				//done :)
				//multichatMessage = NumberOfUsers,user1,user2..userN,message
				String SmultichatMessage = message.getMessageBody();
				JEMClientMultichatMessage multichatMessage = new JEMClientMultichatMessage(SmultichatMessage, message.getHeader1());
				for(int i=0; i<MultiChatWindowsList.size(); i++)
				{
					JEMClientGuiMultichat tmpMultiChatWin = (JEMClientGuiMultichat)MultiChatWindowsList.get(i);
					ArrayList tmpUserList = tmpMultiChatWin.getUserList();
					ArrayList tmpMessageUserList = multichatMessage.getUsers();
					
					if(tmpUserList.size()==multichatMessage.getNrOfUsers())
					{
						int foundUsers = 0;
					//if we got here we got a window with the correct number of users. Now lets see if its our users
						for(int j=0; j<multichatMessage.getNrOfUsers();j++)
						{
							String tmpUser = (String)tmpMessageUserList.get(j);
							for(int k=0; k<multichatMessage.getNrOfUsers();k++)
							{
								if(tmpUser.equals((String)tmpUserList.get(k)))
										{
										foundUsers++;
										}
							}
						}
						if(foundUsers==multichatMessage.getNrOfUsers())
						{
							//we found our multichatwindow!! lets forward our message.
							tmpMultiChatWin.recievedMessage(multichatMessage);
							
						}
					}
				}
				//System.out.println("multichat message recieved.");
			}
			
			
			//11 = Invite multichat    11;fromUser;toUser;user1,user2..userN
			if(message.getTypeOfMessage()==11)
			{
				String remainingUsers = "";
				JEMClientMessageDialog multiNotice = new JEMClientMessageDialog(message.getHeader1()+" invited you to a multichat.", this);
				multiNotice.setTitle("Invitation");
				//use first user in the messageBody to create a multiChat window, and then add the rest
				StringTokenizer st = new StringTokenizer(message.getMessageBody(),",");
				String User1 = st.nextToken();
				String User2 = st.nextToken();
				while (st.hasMoreTokens()) 
	            {
					remainingUsers = remainingUsers + st.nextToken()+ ",";
	            }
				createMultiChatWindow(User1,User2,remainingUsers);
			}
			
			//12 = Leave multichat		12;fromUser;toUser;NumberOfUsers,user1,user2..userN,userToRemove
			if(message.getTypeOfMessage()==12)
			{
				String SmultichatMessage = message.getMessageBody();
				JEMClientMultichatMessage multichatMessage = new JEMClientMultichatMessage(SmultichatMessage,message.getHeader1());
				for(int i=0; i<MultiChatWindowsList.size(); i++)
				{
					JEMClientGuiMultichat tmpMultiChatWin = (JEMClientGuiMultichat)MultiChatWindowsList.get(i);
					ArrayList tmpUserList = tmpMultiChatWin.getUserList();
					ArrayList tmpMessageUserList = multichatMessage.getUsers();
					
					if(tmpUserList.size()==multichatMessage.getNrOfUsers())
					{
						int foundUsers = 0;
					//if we got here we got a window with the correct number of users. Now lets see if its our users
						for(int j=0; j<multichatMessage.getNrOfUsers();j++)
						{
							String tmpUser = (String)tmpMessageUserList.get(j);
							for(int k=0; k<multichatMessage.getNrOfUsers();k++)
							{
								if(tmpUser.equals((String)tmpUserList.get(k)))
										{
										foundUsers++;
										}
							}
						}
						if(foundUsers==multichatMessage.getNrOfUsers())
						{
							//we found our multichatwindow!! lets forward our message.
							//tmpMultiChatWin.addText("<"+message.getHeader1()+">");
							//tmpMultiChatWin.recievedMessage(multichatMessage);
							tmpMultiChatWin.addText("<"+message.getHeader1()+" left>");
							tmpMultiChatWin.removeRemoteChatMember(multichatMessage.getMessageBody());
							//tmpMultiChatWin.addRemoteChatMember(multichatMessage.getMessageBody());
						}
					}
				}
			}
			
			//13 = Add multichatuser	13;toUser;userToAdd;NumberOfUsers,user1,user2..userN
			if(message.getTypeOfMessage()==13)
			{
				String SmultichatMessage = message.getMessageBody();
				JEMClientMultichatMessage multichatMessage = new JEMClientMultichatMessage(SmultichatMessage,message.getHeader1());
				for(int i=0; i<MultiChatWindowsList.size(); i++)
				{
					JEMClientGuiMultichat tmpMultiChatWin = (JEMClientGuiMultichat)MultiChatWindowsList.get(i);
					ArrayList tmpUserList = tmpMultiChatWin.getUserList();
					ArrayList tmpMessageUserList = multichatMessage.getUsers();
					
					if(tmpUserList.size()==multichatMessage.getNrOfUsers())
					{
						int foundUsers = 0;
					//if we got here we got a window with the correct number of users. Now lets see if its our users
						for(int j=0; j<multichatMessage.getNrOfUsers();j++)
						{
							String tmpUser = (String)tmpMessageUserList.get(j);
							for(int k=0; k<multichatMessage.getNrOfUsers();k++)
							{
								if(tmpUser.equals((String)tmpUserList.get(k)))
										{
										foundUsers++;
										}
							}
						}
						if(foundUsers==multichatMessage.getNrOfUsers())
						{
							//we found our multichatwindow!! lets forward our message.
							//tmpMultiChatWin.addText("<"+message.getHeader1()+">");
							//tmpMultiChatWin.recievedMessage(multichatMessage);
							tmpMultiChatWin.addText("<"+message.getHeader1()+" joined>");
							tmpMultiChatWin.addRemoteChatMember(multichatMessage.getMessageBody());
						}
					}
				}
			}
			if(message.getTypeOfMessage()==16)
			{
				recieveSearchResults(message.getMessageBody());
			}
			//send to the correct method
			if(message.getTypeOfMessage()==20)
         {
			   JEMClientMessageDialog messageDialog = new JEMClientMessageDialog(message.getMessageBody(), this);
			   messageDialog.setTitle( "Attention!" );
         }
			
		}
		
		
		
		/*****************************
		 * Creates or updates the GUI userlist
		 *****************************/
		public void setUserList( final String list )
		{
			ChatWindowMenuUserList = list;
		    SwingUtilities.invokeLater( new Runnable()
		    {
		        public void run()
		        {
		        	Font Online = new Font("Arial",1,15);
		        	Font Offline = new Font ("Arial",1,15);
	        		ArrayList onlineUsers = new ArrayList();
	        		ArrayList offlineUsers = new ArrayList();
		        	UserList.setFont(Offline);
		        	GUIlistModel.clear();
		        	//lets create a list for offline users, and then a list for online.
		        	StringTokenizer ST = new StringTokenizer(list,",");
		        	while (ST.hasMoreTokens()) 
		            {

						String User = ST.nextToken();
						String Status = ST.nextToken();
						String Note = ST.nextToken();
						String Image = ST.nextToken();
						
						
						//go through the chatwindow and set the image here?
						
						if(!(myUser.getUsername().equals(User)))
						{
							if(Status.equals("Offline"))
								{
								offlineUsers.add(User + " (" + Status+") "+ Note);
								}
							else
							{
								onlineUsers.add(User + " (" + Status+") " + Note);
							}
						}
						else
						{
							
							UserPanel.setImage(getUserImagePath(Image));
							//setMyImage(Image);
						}
		            }
		        	//add the online list
		        	for(int i=0; i<onlineUsers.size();i++)
		        	{
		        		GUIlistModel.addElement(onlineUsers.get(i));
		        	}
		        	//add the offline list
		        	for(int i=0; i<offlineUsers.size();i++)
		        	{
		        		GUIlistModel.addElement(offlineUsers.get(i));
		        	}
		        	//to make sure someone always is selected
		        	UserList.setSelectedIndex(0);
		        }    
		    } );
		}
		
		
		/*****************************
		 * Creates or opens a chatwindow to the specified user.
		 *****************************/
		public void openChatWindow(String Username)
		{
			final String uname = Username;
			boolean needToCreate = true;
			for(int i=0; i<ChatWindowsList.size(); i++)
			{
				JEMClientGuiChat tmpChatWindow = (JEMClientGuiChat)ChatWindowsList.get(i);
				String tmpName = tmpChatWindow.getUserName();
				if(tmpName.equals(Username))
				{
					tmpChatWindow.setVisible(true);
					needToCreate = false;
				}
			}
			
			if(needToCreate)
			{  
				SwingUtilities.invokeLater( new Runnable()
			    {
			        public void run()
			        {
			        	JEMClientGuiChat newChatWindow = new JEMClientGuiChat(uname,JEMClientGui2.this,ChatWindowMenuUserList);
			        	ChatWindowsList.add(newChatWindow); 
					} 
			        
			    } );
				//sleeping enough time to make sure there has been a repaint
				//otherwise this window wont be found when adding the initial message
//				try {
//					Thread.sleep(300);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
			
			
			//check if this user already has a chatwindow.
			//in that case, just make it visible.
			//otherwise we create one
		}
		
		/*****************************
		 * Creates and opens a MultiChatwindow to the specified user.
		 *****************************/
		public void createMultiChatWindow(String User1, String User2,String Others)
		{
			final String user1 = User1;
			final String user2 = User2;
			final String others = Others;
			if(!(others==null))
			{
				
				SwingUtilities.invokeLater( new Runnable()
			    {
			        public void run()
			        {
			        	JEMClientGuiMultichat newMultiChat = new JEMClientGuiMultichat(JEMClientGui2.this,user1,user2,ChatWindowMenuUserList);
			        	StringTokenizer st = new StringTokenizer(others,",");
			        	while(st.hasMoreTokens())
						{
							String tmpUser = st.nextToken();
							newMultiChat.addRemoteChatMember(tmpUser);
						}
			        	MultiChatWindowsList.add(newMultiChat);
			        } 
		   
			    } );
				
				
			}
			else
			{
				SwingUtilities.invokeLater( new Runnable()
			    {
			        public void run()
			        {
			        	JEMClientGuiMultichat newMultiChat = new JEMClientGuiMultichat(JEMClientGui2.this,user1,user2,ChatWindowMenuUserList);
			        	MultiChatWindowsList.add(newMultiChat);
			        } 
		   
			    } );
				
			}
			//MultiChatWindowsList.add(newMultiChat);
		}
		
		
		/*****************************************
		 * Invite users to a new multichat-session
		 *****************************************/
		
		public void sendStartToMultiChat(String user1, String user2)
		{
			JEMMessage inviteToMulti = new JEMMessage(11,myUser.getUsername(),user1,myUser.getUsername()+","+user1+","+user2);
			JEMMessage inviteToMulti2 = new JEMMessage(11,myUser.getUsername(),user2,myUser.getUsername()+","+user1+","+user2);
			messageHandler.addSend(inviteToMulti);
			messageHandler.addSend(inviteToMulti2);
		}
		
		
		/*******************************************************
		 * Leave multichat session and inform the other participants
		 *******************************************************/
		public void sendRemoveToMultichat(String userToRemove,ArrayList CurrentMultiChatMembers)
		{
			String otherUserList="";
			for(int i=0; i<CurrentMultiChatMembers.size();i++)
			{
				otherUserList = otherUserList+","+(String)CurrentMultiChatMembers.get(i);
			}
			for(int i=0; i<CurrentMultiChatMembers.size();i++)
			{
				//12 = Leave multichat		12;fromUser;toUser;NumberOfUsers,user1,user2..userN,userToRemove
				JEMMessage removeUser = new JEMMessage(12,userToRemove,(String)CurrentMultiChatMembers.get(i),CurrentMultiChatMembers.size() + otherUserList + "," + userToRemove); 
				messageHandler.addSend(removeUser);
			}
		}
		
		
		/*****************************************
		 * Invite users to an existing multichat-session
		 *****************************************/
		public void sendAddToMultichat(String inviter,String userToInvite,ArrayList CurrentMultiChatMembers)
		{
			//send an invite to the "userToInvite" and then a "add multichatuser" to the rest of the users
			String otherUserList="";
			for(int i=0; i<CurrentMultiChatMembers.size();i++)
			{
				otherUserList = otherUserList+","+(String)CurrentMultiChatMembers.get(i);
			}
			JEMMessage inviteToMulti = new JEMMessage(11,inviter,userToInvite,otherUserList);
			messageHandler.addSend(inviteToMulti);
			for(int i=0; i<CurrentMultiChatMembers.size();i++)
			{
				//13 = Add multichatuser	13;toUser;userToAdd;NumberOfUsers,user1,user2..userN,userToAdd
				JEMMessage addInvitedUser = new JEMMessage(13,inviter,(String)CurrentMultiChatMembers.get(i),CurrentMultiChatMembers.size() + otherUserList + "," + userToInvite); 
				messageHandler.addSend(addInvitedUser);
			}
			
		}
		
		
		/*****************************
		 * Sends chat-text to a chatwindow
		 *****************************/
//		public void addTextToChat(String Username, String text)
//		{
//			for(int i=0; i<ChatWindowsList.size(); i++)
//			{
//				JEMClientGuiChat tmpChatWindow = (JEMClientGuiChat)ChatWindowsList.get(i);
//				String tmpName = tmpChatWindow.getUserName();
//				if(tmpName.equals(Username))
//				{
//
//					tmpChatWindow.recieveMessage(Username,text);
//				}
//			}
//		}
      public void addTextToChat(final String Username, final String text)
      {
         SwingUtilities.invokeLater( new Runnable()
         {
             public void run()
             {
                for(int i=0; i<ChatWindowsList.size(); i++)
                {
                   JEMClientGuiChat tmpChatWindow = (JEMClientGuiChat)ChatWindowsList.get(i);
                   String tmpName = tmpChatWindow.getUserName();
                   if(tmpName.equals(Username))
                   {

                      tmpChatWindow.recieveMessage(Username,text);
                   }
                }
           } 
             
         } );
      }
		
		/************************
		 * Return the current user.
		 ************************/
		public JEMClientUser getUser()
		{
			return myUser;
		}
		
		/************************
		 * Return the loginname of the
		 * current user.
		 ************************/
		public String getUserName()
		{
			return myUser.getUsername();
		}
		
		
		/************************
		 * Add a message to the send queue
		 ************************/
		public void ChatToMessageHandler(JEMMessage toSend)
		{
			messageHandler.addSend(toSend);
		}
		
		/***********************************
		 * Update the userlist in the JEM gui.
		 ***********************************/
		public void chatWinUpdateUsers(String list)
		{
			for(int i=0; i<ChatWindowsList.size();i++)
			{
				JEMClientGuiChat tmpWin = (JEMClientGuiChat)ChatWindowsList.get(i);
				tmpWin.updateInviteMenu(list);
			}
		}
		
		/***********************************
		 * Update the images in the chatwindows.
		 ***********************************/
		public void chatWinUpdateRemoteImage()
		{
			for(int i=0; i<ChatWindowsList.size();i++)
			{
				JEMClientGuiChat tmpWin = (JEMClientGuiChat)ChatWindowsList.get(i);
				tmpWin.setMyImage();
				tmpWin.setRemoteImage();
			}
		}
		
		
		
		/***********************************
		 * Update the userlist in the JEM multichat gui.
		 ***********************************/
		public void MultichatWinUpdateUsers(String list)
		{
			for(int i=0; i<MultiChatWindowsList.size();i++)
			{
				JEMClientGuiMultichat tmpWin = (JEMClientGuiMultichat)MultiChatWindowsList.get(i);
				tmpWin.updateInviteMenu(list);
			}
		}
		
		/***********************************
		 * Add a new userimage to the list for display in chatwindows and the main gui.
		 ***********************************/
		public void addUserImage(String path, String name)
		{
			JEMClientUserImage userImage = new JEMClientUserImage(name,path);
			UserImageList.add(userImage);
			//also add to userPanel imagemenu?
		}
		
		public String getUserImagePath(String name)
		{
			JEMClientUserImage tmpUserImage;
			String returnPath = "";
			for(int i=0; i<UserImageList.size();i++)
			{
				tmpUserImage = (JEMClientUserImage)UserImageList.get(i);
				if(tmpUserImage.getImageName().equals(name))
				{
					returnPath = tmpUserImage.getImagePath();
				}
			}
		return returnPath;
		}
		
		public String getUserImageList()
		{
			String returnString = "";
			for(int i=0; i<UserImageList.size();i++)
			{
				JEMClientUserImage tmpUserImage = (JEMClientUserImage)UserImageList.get(i);
				returnString = returnString + ","+tmpUserImage.getImageName();
			}
			return returnString;
		}
	
		public String getRemoteUserImage(String remoteUser)
		{
			//go through our list and find the correct user
			String imageName = "";
			String User = "";
        	StringTokenizer ST = new StringTokenizer(ChatWindowMenuUserList,",");
        	while (ST.hasMoreTokens()) 
            {

				User = ST.nextToken();
				String Status = ST.nextToken();
				String Note = ST.nextToken();
				String Image = ST.nextToken();
				if(User.equals(remoteUser))
				{
					imageName = Image;
				}
            }
        	System.out.println("Remote user: "+User+" has image: "+imageName);
			return imageName;
		}
}


/***********************************
 * Overrides the cellrenderer for the gui user-list.
 * Used to be able to use images and different fonts in the 
 * same list.
 ***********************************/
class MyCellRenderer extends DefaultListCellRenderer
{
	URL OnResource = getClass().getResource("images/JEMClient_Offline.GIF");
	URL OffResource = getClass().getResource("images/JEMClient_Online.GIF");
	//Icon OfflineICON = new ImageIcon( "/images/JEMClient_Offline.GIF");
	//Icon OnlineICON = new ImageIcon( "/images/JEMClient_Online.GIF");
	Icon OfflineICON = new ImageIcon( OnResource);
	Icon OnlineICON = new ImageIcon( OffResource);
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,int index, boolean isSelected, boolean cellHasFocus) {

		Component ret = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if ( ret instanceof JLabel )
		{
			//if ( value != null && value.toString().endsWith("Offline)"))
			if ( value != null && value.toString().indexOf("Offline")>0)
			{
				//System.out.println( value );
				ret.setFont( new Font("sansserif", Font.ITALIC, 12));
				//ret.setForeground(Color.orange);
				//((JLabel)ret).setText( value.toString() );
				((JLabel)ret).setIcon( OfflineICON );
			}
			else if(value != null)
			{
				((JLabel)ret).setIcon( OnlineICON );
			}
		}
		return ret;
	}
}