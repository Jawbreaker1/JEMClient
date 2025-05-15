package JEMClient;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.Color;

/************************************************************************************
 * @author Johan Engwall
 * Main chatwindow that opens from the JEMClientGui2, either after a message is recieved
 * or after the user doubleclicks a user in the list.
 ************************************************************************************/

public class JEMClientGuiChat extends JFrame {
	
	
	//private JTextArea InputField;
	//private JTextArea  ChatWindow;
	private JTextPane InputField;
	private JTextPane  ChatWindow;
	private JMenuBar menuBar;
	private JMenuItem menuItemExit;
	private JMenu menuFile;
	private JMenu menuAddUser;
	private String UserName;
	private JEMClientGui2 owner;
	private JEMClientGuiStatusbar statusbar;
	private StyledDocument InputFieldDoc;
	private StyledDocument ChatWindowDoc;
	private JEMClientGuiChatFontmenu fontMenu;
	private JScrollPane ChatWindowScroll;
	private JScrollPane InputWindowScroll;
	private JLabel remoteUserImage;
	private JLabel myUserImage;
	private JLabel remoteUserFrame;
	private JLabel myUserFrame;
	private String[] smileyList = {":)" , ":-)" , ":(" , ":-(" , ">:)", ";)" ,";-)", ":>" , ":D" , ":'(" , ":P" , "=P"};
	
	
	public JEMClientGuiChat(String userName, JEMClientGui2 Owner,String userlist)
	{
		UserName = userName;
		owner = Owner;
		setTitle("JEMClient" + "-" + UserName);
		//getContentPane().setLayout(null);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		//c.fill = GridBagConstraints.HORIZONTAL;	
		
		//setMinimumSize(new Dimension(465,520));
		//setMaximumSize(new Dimension(465,520));
		this.setSize(new Dimension(440,450));
		setVisible(true);
		setLocation(350, 250);
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		menuAddUser = new JMenu("Invite User to Conversation");
		menuBar.add(menuFile);
		menuBar.add(menuAddUser);
		menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	exitMenuButtonActionPerformed(e);
            }
        });
		
		menuFile.add(menuItemExit);
		
		StringTokenizer st = new StringTokenizer(userlist,",");
    	while (st.hasMoreTokens()) 
        {
			final String User = st.nextToken();
			String Status = st.nextToken();
			String Note = st.nextToken();
			String Image = st.nextToken();
			if(!(Status.equals("Offline"))&&!(User.equals(owner.getUserName()))&&!(User.equals(userName)))
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
		
		
		//ChatWindow = new JTextArea();
    	ChatWindow = new JTextPane();
		ChatWindow.setEditable(false);
		//ChatWindow.setLineWrap(true);
		//ChatWindow.setWrapStyleWord(true);

		//final JScrollPane ChatWindowScroll = new JScrollPane(ChatWindow);
		JScrollPane ChatWindowScroll = new JScrollPane(ChatWindow,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		ChatWindowScroll.setAutoscrolls(true);
		ChatWindowScroll.setBorder(BorderFactory.createLoweredBevelBorder());
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weighty = 1.0;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.insets = new Insets(3,3,0,3);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		ChatWindowScroll.setPreferredSize(new Dimension(450, 50));
		add(ChatWindowScroll,c);
		
		fontMenu = new JEMClientGuiChatFontmenu(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weighty = 0.01;
		c.weightx = 0.1;
		c.gridheight = 1;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		add(fontMenu,c);
		
		
		
		
		
		InputField =new JTextPane();
		JScrollPane InputWindowScroll = new JScrollPane(InputField,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		ChatWindowScroll.setAutoscrolls(true);
		//InputField = new JTextArea();
		//InputField.setLineWrap(true);
		//InputField.setWrapStyleWord(true);
		InputWindowScroll.setBorder(BorderFactory.createLoweredBevelBorder());
		//InputField.setMinimumSize(new Dimension(350,100));
		//InputField.setPreferredSize(new Dimension(350,100));
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.009;
		c.weightx = 1.0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0,3,3,3);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		add(InputWindowScroll,c);
		
		final JButton SendButton = new JButton("Send");
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 50;
		c.ipady = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.weighty = 0.009;
		c.weightx = 0.05;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		add(SendButton,c);
		
		statusbar = new JEMClientGuiStatusbar(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0.01;
		c.weightx = 1.0;
		c.gridheight = 1;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		add(statusbar,c);
		//ChatWindowScroll.setBounds(5, 5, 450, 350);
		//SendButton.setBounds(360, 360,95,100);
		//InputField.setBounds(5, 360, 350,100 );
		
		
		remoteUserFrame = new JLabel();
		remoteUserFrame.setBorder(BorderFactory.createLoweredBevelBorder());
		remoteUserFrame.setPreferredSize(new Dimension(100,100));
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 2;
		c.gridy = 0;
		c.weighty = 0.5;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		//remoteUserFrame.setLayout(null);
		
		/*remoteUserImage = new JLabel();
		remoteUserImage.setBorder(BorderFactory.createLoweredBevelBorder());
		remoteUserImage.setBounds(10, 10, 90, 90);
		remoteUserImage.setLayout(null);
		remoteUserImage.setVisible(true);*/
		
		setRemoteImage();
		remoteUserFrame.setVisible(true);
		//remoteUserFrame.add(remoteUserImage);
		add(remoteUserFrame,c);
		//getRemoteUserImage
		//the remote users image needs to be handled through the server
		//once the correct image is found create
		
		myUserFrame = new JLabel();
		myUserFrame.setBorder(BorderFactory.createLoweredBevelBorder());
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 2;
		c.gridy = 2;
		c.weighty = 0.009;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0,3,3,3);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		
		/*myUserImage = new JLabel();
		myUserImage.setBorder(BorderFactory.createLoweredBevelBorder());
		myUserImage.setBounds(10, 10, 90, 90);
		myUserFrame.add(remoteUserImage);*/
		myUserFrame.setVisible(true);
		setMyImage();
		add(myUserFrame,c);
		//get the info from owner about my image
		//create
		
		setJMenuBar(menuBar);
		
		
		//add(SendButton);
		

		
		
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
     						addText("You wrote: ","14","Serif","ITALIC","Gray");
		     				addText(getInputField().trim(),fontMenu.getSelectedFontSize(), fontMenu.getSelectedFont(), fontMenu.getSelectedFontStyle(), fontMenu.getSelectedFontColor());
		     				JEMMessage toSend = new JEMMessage(2,owner.getUserName(),UserName,fontMenu.getSelectedFontSize()+","+fontMenu.getSelectedFont()+","+fontMenu.getSelectedFontStyle()+","+fontMenu.getSelectedFontColor()+","+getInputField());
		     				owner.ChatToMessageHandler(toSend);
		     				InputField.setText("");
		     				InputField.setCaretPosition(1);
     						}
     				InputField.setCaretPosition(1);
     			}
     		}
		});	  
	}
	
	
	/*******************************************
	 * Get the current text from the inputfield.
	 *******************************************/
	
	public String getInputField()
	{
		String tmpString = InputField.getText();
		return tmpString;
	}
	
	/*******************************************
	 * Add text to the inputfield.
	 *******************************************/
	
	public void addInputText(String textToAdd)
	{
		InputField.setText(InputField.getText()+" "+textToAdd);
	}
	
	
	/*******************************************
	 * Add text to the chatfield. Set the correct font for each added text.
	 * Also converts "smileys" to an actual image.
	 *******************************************/
	public void addText(String toAdd,String FontSize, String Font, String FontStyle, String FontColor)
	{
		boolean smiley = false;
		Color black = new Color(0,0,0);
		Color red = new Color(255,0,0);
		Color green = new Color(0,255,0);
		Color blue = new Color(0,0,255);
		Color pink = new Color(255,105,180);
		Color gray = new Color(169,169,169);
		Color selectedColor = black;
		
		int fontStyle = 0;
		if(FontStyle.equals("PLAIN"))
		{
			fontStyle=0;
		}
		else if(FontStyle.equals("BOLD"))
		{
			fontStyle=1;
		}
		else if(FontStyle.equals("ITALIC"))
		{
			fontStyle=2;
		}
		
		if(FontColor.equals("Black"))
		{
			selectedColor = black;
		}
		else if(FontColor.equals("Red"))
		{
			selectedColor = red;
		}
		else if(FontColor.equals("Green"))
		{
			selectedColor = green;
		}
		else if(FontColor.equals("Blue"))
		{
			selectedColor = blue;
		}
		else if(FontColor.equals("Pink"))
		{
			selectedColor = pink;
		}
		else if(FontColor.equals("Gray"))
		{
			selectedColor = gray;
		}
		
		Font nFont = new Font(Font,fontStyle,Integer.parseInt(FontSize));
		MutableAttributeSet attrs = ChatWindow.getInputAttributes();
	    StyleConstants.setFontFamily(attrs, nFont.getFamily());
	    StyleConstants.setFontSize(attrs, nFont.getSize());
	    StyleConstants.setItalic(attrs, (nFont.getStyle() & java.awt.Font.ITALIC) != 0);
	    StyleConstants.setBold(attrs, (nFont.getStyle() & java.awt.Font.BOLD) != 0);
	    
	    StyleConstants.setForeground(attrs, selectedColor);
	    
	    StyledDocument doc = ChatWindow.getStyledDocument();

	    doc.setCharacterAttributes(doc.getLength(), doc.getLength() + 1, attrs, false);
		
	    
	    Style Smileyface = doc.addStyle("SmileyFace", null);
	    
	    
		StringTokenizer st = new StringTokenizer(toAdd," ");
    	while (st.hasMoreTokens()) 
        {
		    String tmpString = st.nextToken();
		    for(int i=0; i<smileyList.length; i++)
		    {
		    	//this is where we translate the smiley from text to image!!
		    	if(tmpString.equals(smileyList[i]))
		    	{
		    		if(tmpString.equals( ":)" )||tmpString.equals( ":-)" ))
		    		{
		    			URL HappySmiley = getClass().getResource("images/JEMClient_HappySmily.GIF");
		    			StyleConstants.setIcon(Smileyface, new ImageIcon(HappySmiley));
		    		}
		    		else if(tmpString.equals( ":(" )||tmpString.equals( ":-(" ))
		    		{
		    			URL SadSmiley = getClass().getResource("images/JEMClient_SadSmily.GIF");
		    			StyleConstants.setIcon(Smileyface, new ImageIcon(SadSmiley));
		    		}
		    		else if(tmpString.equals( ":D" )||tmpString.equals( ":>" ))
		    		{
		    			URL vHappySmiley = getClass().getResource("images/JEMClient_VHappySmily.GIF");
		    			StyleConstants.setIcon(Smileyface, new ImageIcon(vHappySmiley));
		    		}
		    		else if(tmpString.equals( ":'(" ))
		    		{
		    			URL crySmiley = getClass().getResource("images/JEMClient_CrySmily.GIF");
		    			StyleConstants.setIcon(Smileyface, new ImageIcon(crySmiley));
		    		}
		    		else if(tmpString.equals( ";)" )||tmpString.equals( ";-)" ))
		    		{
		    			URL FlirtySmiley = getClass().getResource("images/JEMClient_FlirtySmily.GIF");
		    			StyleConstants.setIcon(Smileyface, new ImageIcon(FlirtySmiley));
		    		}
		    		else if(tmpString.equals( ">:)" ))
		    		{
		    			URL FlirtySmiley = getClass().getResource("images/JEMClient_DevilSmily.GIF");
		    			StyleConstants.setIcon(Smileyface, new ImageIcon(FlirtySmiley));
		    		}
		    		else if(tmpString.equals( ":P" )||tmpString.equals( "=P" ))
		    		{
		    			URL FlirtySmiley = getClass().getResource("images/JEMClient_BlahSmily.GIF");
		    			StyleConstants.setIcon(Smileyface, new ImageIcon(FlirtySmiley));
		    		}
		    		
		    		
		    		smiley=true;
		    	}
		    }
		    try {
			    if(smiley)
			    {
			    	doc.insertString(doc.getLength(), "image missing", Smileyface);
			    	//reset back to normal fontstyle
			    	nFont = new Font(Font,fontStyle,Integer.parseInt(FontSize));
					attrs = ChatWindow.getInputAttributes();
				    StyleConstants.setFontFamily(attrs, nFont.getFamily());
				    StyleConstants.setFontSize(attrs, nFont.getSize());
				    StyleConstants.setItalic(attrs, (nFont.getStyle() & java.awt.Font.ITALIC) != 0);
				    StyleConstants.setBold(attrs, (nFont.getStyle() & java.awt.Font.BOLD) != 0);
				    StyleConstants.setForeground(attrs, selectedColor);
				    doc = ChatWindow.getStyledDocument();
			    	doc.setCharacterAttributes(doc.getLength(), doc.getLength() + 1, attrs, false);
			    	doc.insertString(doc.getLength()," ",attrs);
			    	//end reset
			    	smiley=false;
			    }
			    else
			    {
			    	doc.insertString(doc.getLength(),tmpString+" ",attrs);
			    }
		    } catch (BadLocationException ble) {
	            System.err.println("Couldn't insert initial text into text pane.");
	        }
        }
    	//insert a newline after the last token
    	try 
    	{
    		doc.insertString(doc.getLength(),"\n",attrs);
    	} 
    	catch (BadLocationException ble) 
    	{
            System.err.println("Couldn't insert initial text into text pane.");
        }
		//ChatWindow.setText(ChatWindow.getText() + "\n" + toAdd);
		//ChatWindow.setCaretPosition(ChatWindow.getDocument().getLength() );
	    //ChatWindowScroll.getVerticalScrollBar().setValue(ChatWindowScroll.getVerticalScrollBar().getMaximum());
	    //ChatWindow.scrollRectToVisible(new Rectangle(0,ChatWindow.getHeight()-2,1,1));
	    ChatWindow.scrollRectToVisible(new Rectangle(0,ChatWindow.getHeight(),0,0));
	}
	
	
	
	/********************************
	 * Method is called when the "send" button is clicked.
	 * Adds text to the chatarea, and creates a JEMMessage
	 * with the typed text, and the set Font.
	 ********************************/
	private void sendButtonActionPerformed(ActionEvent e){
		
		if((!getInputField().equals("")))
			{
			addText("You wrote: ","14","Serif","ITALIC","Gray");
			addText(getInputField(),fontMenu.getSelectedFontSize(), fontMenu.getSelectedFont(), fontMenu.getSelectedFontStyle(), fontMenu.getSelectedFontColor());
			//create a JEMMessage and add it to the send queue
			JEMMessage toSend = new JEMMessage(2,owner.getUserName(),UserName,fontMenu.getSelectedFontSize()+","+fontMenu.getSelectedFont()+","+fontMenu.getSelectedFontStyle()+","+fontMenu.getSelectedFontColor()+","+getInputField());
			owner.ChatToMessageHandler(toSend);
			InputField.setText("");
			}
	}
	
	
	/*************************************
	 * Method is called when the "Exit" option is clicked 
	 * in the menu.
	 * Makes the window invisable, so that it does not need to be recreated if needed again.
	 **************************************/
	private void exitMenuButtonActionPerformed(ActionEvent e)
	{
		this.setVisible(false);
	}
	
	/*************************************
	 * Returns the username of this chatwindow
	 **************************************/
	public String getUserName()
	{
		return UserName;
	}
	
	/*************************************
	 * Method is called when the "invite user" option is clicked in the 
	 * menu. This will create a new window the the possibility of >2 people
	 * chatting with eachother.
	 **************************************/
	
	private void InviteUserActionPerformed(String User)
	{
		
		//Not quite sure how this will work yet :)
		//should change the window to a Multichat window
		//and send an invitation message to the stated user.
		//visible(false)
		owner.createMultiChatWindow(UserName, User,null);
		owner.sendStartToMultiChat(UserName,User);
		setVisible(false);
	}
	
	/****************************************
	 * Sets the fontstyle of the input area.
	 ***************************************/
	public void setTextStyle(String FontSize, String Font, String FontStyle, String FontColor)
	{
		Color black = new Color(0,0,0);
		Color red = new Color(255,0,0);
		Color green = new Color(0,255,0);
		Color blue = new Color(0,0,255);
		Color pink = new Color(255,105,180);
		Color selectedColor = black;
		
		int fontStyle = 0;
		if(FontStyle.equals("PLAIN"))
		{
			fontStyle=0;
		}
		else if(FontStyle.equals("BOLD"))
		{
			fontStyle=1;
		}
		else if(FontStyle.equals("ITALIC"))
		{
			fontStyle=2;
		}
		
		if(FontColor.equals("Black"))
		{
			selectedColor = black;
		}
		else if(FontColor.equals("Red"))
		{
			selectedColor = red;
		}
		else if(FontColor.equals("Green"))
		{
			selectedColor = green;
		}
		else if(FontColor.equals("Blue"))
		{
			selectedColor = blue;
		}
		else if(FontColor.equals("Pink"))
		{
			selectedColor = pink;
		}
		
		Font nFont = new Font(Font,fontStyle,Integer.parseInt(FontSize));
		MutableAttributeSet attrs = InputField.getInputAttributes();
	    StyleConstants.setFontFamily(attrs, nFont.getFamily());
	    StyleConstants.setFontSize(attrs, nFont.getSize());
	    StyleConstants.setItalic(attrs, (nFont.getStyle() & java.awt.Font.ITALIC) != 0);
	    StyleConstants.setBold(attrs, (nFont.getStyle() & java.awt.Font.BOLD) != 0);
	    
	    StyleConstants.setForeground(attrs, selectedColor);
	    
	    StyledDocument doc = InputField.getStyledDocument();

	    doc.setCharacterAttributes(0, doc.getLength() + 1, attrs, false);
    
		
	}
	
	/*******************************************
	 * Method called from JEMClientGui2 when a message is recieved from 
	 * a remote user.
	 * Adds the text to the chatarea with the correct font, and adds a timestamp 
	 * to the statusfield.
	 ******************************************/
	public void recieveMessage(String FromUser,String recievedText)
	{
		final String fromUser = FromUser;
		if (this.getExtendedState() == 1) 
			this.toFront();
			if ((this.getExtendedState() == 0) && (this.isFocused() == false))
			{
				this.setExtendedState(2);
				this.toFront();
			}
			
		//we need to get our textheaders from the recievedText first
			
			
		StringTokenizer st = new StringTokenizer(recievedText,",");	
		final String fontSize = st.nextToken();
		final String font = st.nextToken();
		final String fontstyle = st.nextToken();
		final String fontcolor = st.nextToken();
		final String message = st.nextToken();
		
	    addText(fromUser+" wrote: ","14","Serif","ITALIC","Gray");
		addText(message,fontSize,font,fontstyle,fontcolor);
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy h:mm a");
    	Date date = new Date();
		statusbar.setMessage("Last message recieved " + dateFormat.format(date));
	}
	
		
	/*********************************************
	 * Updates the users available to invite for a "multichat".
	 * Is updated when a user is logged on/off etc.
	 *********************************************/
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
	    			if(!(Status.equals("Offline"))&&!(User.equals(owner.getUserName()))&&!(User.equals(UserName)))
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
	
	public void setMyImage()
	{
		//String myImageName = owner.getMyImage();
		
		//wth är detta?????
		
		String myImageName = owner.getRemoteUserImage(owner.getUserName());
		String imagePath = owner.getUserImagePath(myImageName);
		URL myImagePath = getClass().getResource(imagePath);
		ImageIcon myImage = new ImageIcon(myImagePath);
		myUserFrame.setIcon(myImage);
	}
	
	public void setRemoteImage()
	{
		
		String imageName = owner.getRemoteUserImage(UserName);
		String imagePath = owner.getUserImagePath(imageName);
		URL remoteImagePath = getClass().getResource(imagePath);
		ImageIcon remoteImage = new ImageIcon(remoteImagePath);
		remoteUserFrame.setIcon(remoteImage);
	}
	
}
