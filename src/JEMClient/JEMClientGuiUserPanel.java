package JEMClient;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JPopupMenu;

public class JEMClientGuiUserPanel extends JPanel{

	private String userName;
	private String userStatus;
	private String userNote;
	private JLabel nameLabel;
	private JLabel statusLabel;
	private JTextField noteTextField;
	private JPopupMenu popupStatusMenu;
	private JPopupMenu popupImageMenu;
	private JButton changeStatusButton;
	private JEMClientGui2 owner;
	private URL ImagePath;
	private ImageIcon placeHolder;
	private JLabel imageLabel;
	
	public JEMClientGuiUserPanel(JEMClientGui2 Owner)
	{
		owner = Owner;
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//setLayout(null);
		//setMinimumSize(new Dimension(200,230));
		
		nameLabel = new JLabel("name");
		//nameLabel.setBounds(2,2,200,200);
		Font userLabelFont = new Font("Arial",3,25);
		nameLabel.setFont(userLabelFont);
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.8;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(nameLabel,c);
		nameLabel.setVisible(true);
	
		statusLabel = new JLabel("status");
		Font statusLabelFont = new Font("Arial",2,15);
		statusLabel.setFont(statusLabelFont);
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weighty = 0.2;
		c.weightx = 0.05;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(statusLabel,c);
		statusLabel.setVisible(true);
	
		
		changeStatusButton = new JButton("");
		
		//changeStatusButton.setPreferredSize(new Dimension(15,15));
		c.fill = GridBagConstraints.NONE;
		c.ipady = 5;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.insets = new Insets(10,10,10,10);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		URL buttonImagePath = getClass().getResource("images\\JEMClient_Button_DownArrow.jpg");
		ImageIcon placeHolder =  new ImageIcon(buttonImagePath);
		changeStatusButton.setIcon(placeHolder);
		
		add(changeStatusButton,c);
		changeStatusButton.setVisible(true);
		
		changeStatusButton.addActionListener(new ActionListener() 
        
		{
            public void actionPerformed(ActionEvent e) 
            {
            	showPopup();
            }
        });
		
		
		popupStatusMenu = new JPopupMenu();
		
		JMenuItem menuItemOnline = new JMenuItem("Online");
		menuItemOnline.addActionListener(new ActionListener() 
        
		{
            public void actionPerformed(ActionEvent e) 
            {
            	setNewStatus("Online");
                //setUserStatus("Online");
                //setUserMenuStatus("Online");
            }
        });
		
		JMenuItem menuItemAway = new JMenuItem("Away");
		menuItemAway.addActionListener(new ActionListener() 
        
		{
            public void actionPerformed(ActionEvent e) 
            {
            	setNewStatus("Away");
            	//setUserStatus("Away");
            	//setUserMenuStatus("Away");
            }
        });
		
		JMenuItem menuItemBusy = new JMenuItem("Busy");
		menuItemBusy.addActionListener(new ActionListener() 
        
		{
            public void actionPerformed(ActionEvent e) 
            {
            	setNewStatus("Busy");
            	//setUserStatus("Busy");
            	//setUserMenuStatus("Busy");
            }
        });
		
		popupStatusMenu.add(menuItemOnline);
		popupStatusMenu.add(menuItemAway);
		popupStatusMenu.add(menuItemBusy);
		
		
		
		noteTextField = new JTextField("note");
		Font noteTextFont = new Font("Arial",2,12);
		noteTextField.setFont(noteTextFont);
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.1;
		c.weightx = 1;
		c.gridheight = 1;
		c.gridwidth = 2;
		c.insets = new Insets(3,3,3,3);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(noteTextField,c);
		noteTextField.setVisible(true);
		noteTextField.setBackground(this.getBackground());
		
		noteTextField.addKeyListener(new KeyAdapter() {
     		public void keyPressed(KeyEvent e)
     		{
     			if(e.getKeyCode()==e.VK_ENTER)
     			{
     				if(noteTextField.getText().equals("note")||noteTextField.getText().equals(""))
     				{
     					noteTextField.setText("note");
     					owner.setUserNote(" ");
     				}
     				else
     				{
     					owner.setUserNote(noteTextField.getText());
     				}
     			}
     		}
		});	  
		
		noteTextField.addFocusListener(new FocusListener() {
			
			public void focusGained(FocusEvent e) 
			{
			}

     		public void focusLost(FocusEvent e)
     		{
     			if(noteTextField.getText().equals("note")||noteTextField.getText().equals(""))
     			{
     				noteTextField.setText("note");
     				owner.setUserNote(" ");
     			}
     			else
     			{
     				owner.setUserNote(noteTextField.getText());
     			}
     			
     		}
		});
		
		

		//user enter as actionlistener for the textfield. (or lost focus?)
	
		//ImagePath = getClass().getResource("images/JEMClient_UserImage_leaves.jpg");
		//placeHolder =  new ImageIcon(ImagePath);
		
		imageLabel = new JLabel();
		//imageLabel.setIcon(placeHolder);
		c.fill = GridBagConstraints.NONE;
		c.ipady = 0;
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weighty = 1;
		c.weightx = 0.1;
		c.gridheight = 3;
		c.insets = new Insets(3,3,3,3);
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		//imageLabel.setMinimumSize(new Dimension(100,100));
		imageLabel.setBorder(BorderFactory.createEtchedBorder());
		imageLabel.setVisible(true);
		add(imageLabel,c);
		setImage(owner.getUserImagePath("no image"));
		//rightclick listener for popupmenu to change image.
		
		popupImageMenu = new JPopupMenu();
		String userImages = owner.getUserImageList();
		
		StringTokenizer st = new StringTokenizer(userImages,",");
    	while (st.hasMoreTokens()) 
        {
    		final String thisImage = st.nextToken();
    		JMenuItem menuItemImage = new JMenuItem(thisImage);
    		menuItemImage.addActionListener(new ActionListener() 
            
    		{
                public void actionPerformed(ActionEvent e) 
                {
                	setImage(JEMClientGuiUserPanel.this.owner.getUserImagePath(thisImage));
                    owner.setMyImage(thisImage); 
                }
            });
    		popupImageMenu.add(menuItemImage);
        }
		
    	imageLabel.addMouseListener(new MouseAdapter() 
        
		{
            public void mousePressed(MouseEvent e) 
            {
            	showImagePopup();
            }
        });
		
		 
		
	}
	
	public void setUserName(String name)
	{
		nameLabel.setText(name);
		userName = name;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setStatus(String newStatus)
	{
		statusLabel.setText(newStatus);
		userStatus = newStatus;
	}
	
	public String getStatus()
	{
		return userStatus;
	}
	
	public void setNote(String newNote)
	{
		userNote = newNote;
	}
	
	public String getNote()
	{
		return userNote;
	}
	
	public void showPopup()
	{
		popupStatusMenu.show(changeStatusButton,0,changeStatusButton.getHeight());
	}
	
	public void showImagePopup()
	{
		popupImageMenu.show(imageLabel,0,imageLabel.getHeight());
	}
	
	public void setNewStatus(String newStatus)
	{
		owner.setUserStatus(newStatus);
	}
	
	public void setImage(String path)
	{
		ImagePath = getClass().getResource(path);
		placeHolder =  new ImageIcon(ImagePath);
		imageLabel.setIcon(placeHolder);
	}
}
