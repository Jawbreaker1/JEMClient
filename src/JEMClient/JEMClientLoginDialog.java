package JEMClient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;


public class JEMClientLoginDialog extends JDialog{
	
	private JTextField UserNameText;
	private JPasswordField PasswordText;
	private JTextField ServerIpText;
	private JTextField PortText;
	private JCheckBox saveBox;
	//private JEMClientGui tmpOwner;
	private JEMClientGui2 tmpOwner;

	//public JEMClientLoginDialog(JEMClientGui owner)
	public JEMClientLoginDialog(JEMClientGui2 owner)
	{
		//layout of dialog window with buttons and everything
		
		tmpOwner = owner;
		setLayout(null);
		setMinimumSize(new Dimension(200,230));
		this.setLocation(owner.getLocation().x+20,owner.getLocation().y+200);
		this.setTitle("JEM Login");
		JLabel FirstName = new JLabel("User:");
		FirstName.setBounds(5, 5, 75, 20);
		UserNameText = new JTextField();
		UserNameText.setBounds(80, 5, 80, 20);
		JLabel Password = new JLabel("Password:");
		Password.setBounds(5,35,75,20);
		PasswordText = new JPasswordField();
		PasswordText.setBounds(80, 35, 80, 20);
		JLabel ServerIp = new JLabel("Server IP:");
		ServerIp.setBounds(5, 65, 75, 20);
		ServerIpText = new JTextField();
		ServerIpText.setBounds(80, 65, 80, 20);
		JLabel port = new JLabel("Port:");
		port.setBounds(5,95,75,20);
		PortText = new JTextField();
		PortText.setBounds(80, 95, 40, 20);
		JLabel save = new JLabel("Save settings:");
		save.setBounds(5, 125, 80, 20);
		saveBox = new JCheckBox();
		saveBox.setBounds(76,125,20,20);
		saveBox.setSelected(true);
		
		JButton okButton = new JButton("Ok");
		okButton.setBounds(15, 155, 75, 25);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(100, 155, 75, 25);
		
		okButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                okButtonActionPerformed(e);
            }
        });
        
		cancelButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                cancelButtonActionPerformed(e);
            }
        });
		
		add(FirstName);
		add(UserNameText);
		add(Password);
		add(PasswordText);
		add(ServerIp);
		add(ServerIpText);
		add(port);
		add(okButton);
		add(cancelButton);
		add(PortText);
		add(save);
		add(saveBox);
		
		//read file and enter information. (if it contains anything)
		createLoginFromFile();
		
        pack();
        setVisible(true);
		
		

	}
	
	private void okButtonActionPerformed(ActionEvent e)
	{
		//control checkbox. If checked, write to file, if unchecked clear file
		if(saveBox.isSelected())
		{
			//control that all the fields contain information
			if( !(UserNameText.getText().equals("")) &&  !(PasswordText.getText().equals("")) && !(ServerIpText.getText().equals("")) && !(PortText.getText().equals("")))
			{
				//construct string from the entries.
				String saveText = ";"+UserNameText.getText()+";"+PasswordText.getText()+ ";"+ServerIpText.getText() + ";" + PortText.getText();
				//send string to file
				saveLoginToFile(saveText);
			}
			
			
		}else
		{
			DeleteFile("JEMLogin.txt");
		}
		if( !(UserNameText.getText().equals("")) &&  !(PasswordText.getText().equals("")) && !(ServerIpText.getText().equals("")) && !(PortText.getText().equals("")))
		{
			tmpOwner.setServer(ServerIpText.getText(), PortText.getText());
			tmpOwner.setUser(UserNameText.getText(), PasswordText.getText());
			setVisible(false);
		}
	}
	
	
	
	
	private void cancelButtonActionPerformed(ActionEvent e)
	{
		setVisible(false);
	}
	
	
	
	
	public void saveLoginToFile(String textToWrite)
	{
		BufferedWriter outputStream = null;

	    try
	    {
	    	 outputStream =  new BufferedWriter(new FileWriter("JEMLogin.txt",true));
	         outputStream.write(textToWrite);
	         outputStream.newLine();
		
    	}   
    	catch (IOException e) 
    	{
	        System.err.println("Caught IOException: " +  e.getMessage());
	    }

	    finally 
	    {
	    	if (outputStream != null) 
	    	{
	    		try 
	    		{
					outputStream.close();
				} catch (IOException e) {
			
					e.printStackTrace();
				}
	        }	
	    }
	    	
	}
	
	
	
	
	public void createLoginFromFile()
    {
    	
    	BufferedReader inputStream = null;

    	try
    	{
            inputStream = 
                new BufferedReader(new FileReader("JEMLogin.txt"));

            String l;
        	while ((l = inputStream.readLine()) != null) 
        	{
	            String tmpText = l;

	            StringTokenizer st = new StringTokenizer(l,";");
	           	String name = st.nextToken();
	            String password = st.nextToken();
	            String server = st.nextToken();
	            String port = st.nextToken();
	            
	            UserNameText.setText(name);
	            PasswordText.setText(password);
	            ServerIpText.setText(server);
	            PortText.setText(port);
	     		 
			}
             
           

    	}   
    	catch (IOException e) {
            System.err.println("Caught IOException: " 
                    +  e.getMessage());
    	}

    	finally 
    	{
            if (inputStream != null) {
            	try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }	
    	}   		
    }
	
    public void DeleteFile(String file) {
  	  
	    String fileName = file;
	    // A File object to represent the filename
	    File f = new File(fileName);

	    // Make sure the file or directory exists and isn't write protected
	    if (!f.exists())
	      throw new IllegalArgumentException(
	          "Delete: no such file or directory: " + fileName);

	    if (!f.canWrite())
	      throw new IllegalArgumentException("Delete: write protected: "
	          + fileName);

	    // If it is a directory, make sure it is empty
	    if (f.isDirectory()) {
	      String[] files = f.list();
	      if (files.length > 0)
	        throw new IllegalArgumentException(
	            "Delete: directory not empty: " + fileName);
	    }

	    // Attempt to delete it
	    boolean success = f.delete();

	    if (!success)
	      throw new IllegalArgumentException("Delete: deletion failed");
	  

	}
}
