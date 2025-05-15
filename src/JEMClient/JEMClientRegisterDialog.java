package JEMClient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*******************************************************
 * 
 * @author Johan Engwall
 * Displays a dialog for changing password
 * and then sends a changepassword message to the server.
 * Requires that the user is logged in.
 *******************************************************/

public class JEMClientRegisterDialog extends JDialog{
	
	private JEMClientGui2 tmpOwner;
	JTextField UsernameText;
	JTextField FirstNameText;
	JTextField LastNameText;
	JTextField NewPasswordText;
	JTextField RetypeText;
	JTextField ServerText;
	JTextField PortText;
	
	public JEMClientRegisterDialog(JEMClientGui2 owner)
	{
		tmpOwner = owner;
		setLayout(null);
		setMinimumSize(new Dimension(200,290));
		this.setLocation(owner.getLocation().x+20,owner.getLocation().y+200);
		this.setTitle("Register");
		JLabel Username = new JLabel("Username:");
		Username.setBounds(5, 5, 75, 20);
		UsernameText = new JTextField();
		UsernameText.setBounds(80, 5, 80, 20);
		JLabel FirstnameLabel = new JLabel("Firstname:");
		FirstnameLabel.setBounds(5,35,75,20);
		JLabel LastnameLabel = new JLabel("Lastname");
		LastnameLabel.setBounds(5,65,75,20 );
		FirstNameText = new JTextField();
		FirstNameText.setBounds( 80, 35, 80, 20 );
		LastNameText = new JTextField();
		LastNameText.setBounds( 80, 65,80,20 );
		JLabel NewPassword = new JLabel("Password:");
		NewPassword.setBounds(5,95,75,20);
		NewPasswordText = new JPasswordField();
		NewPasswordText.setBounds(80, 95, 80, 20);
		JLabel Retype = new JLabel("Re-enter:");
		Retype.setBounds(5, 125, 75, 20);
		RetypeText = new JPasswordField();
		RetypeText.setBounds(80, 125, 80, 20);
		JLabel ServerIP = new JLabel("Server IP:");
		ServerIP.setBounds(5,155,72,20);
		ServerText = new JTextField();
		ServerText.setBounds(80,155,80,20);
		JLabel ServerPort = new JLabel("Port:");
		ServerPort.setBounds(5,185,75,20);
		PortText = new JTextField();
		PortText.setBounds(80,185,40,20);
		
		
		JButton okButton = new JButton("Ok");
		okButton.setBounds(15, 220, 75, 25);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(100, 220, 75, 25);
		
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
		
		add(Username);
		add(UsernameText);
		add(FirstnameLabel);
		add(FirstNameText);
		add(LastnameLabel);
		add(LastNameText);
		add(NewPassword);
		add(NewPasswordText);
		add(Retype);
		add(RetypeText);
		add(okButton);
		add(cancelButton);
		add(ServerIP);
		add(ServerText);
		add(ServerPort);
		add(PortText);
		
	    pack();
	    setVisible(true);
	}
	
	private void okButtonActionPerformed(ActionEvent e)
	{
		//add checks to make sure no textfields are empty
		if( !(UsernameText.getText().equals("")) && !(UsernameText.getText().equals("")) && !(FirstNameText.getText().equals("")) && !(LastNameText.getText().equals("")) && !(RetypeText.getText().equals("")) && !(ServerText.getText().equals("")) && !(PortText.getText().equals("")))
		{
			if(NewPasswordText.getText().equals(RetypeText.getText()))
			{
				tmpOwner.sendRegistration(UsernameText.getText(),FirstNameText.getText(),LastNameText.getText(), NewPasswordText.getText(), null);
				tmpOwner.setServer(ServerText.getText(), PortText.getText());
				tmpOwner.setUserForRegistration(UsernameText.getText(), NewPasswordText.getText());
				setVisible(false);
			}
			else
			{
				JEMClientMessageDialog dontMatch = new JEMClientMessageDialog("Passwords do not match!",tmpOwner);
			}
		}
		else
		{
			JEMClientMessageDialog dontMatch = new JEMClientMessageDialog("Information missing!",tmpOwner);
		}
	}
	
	private void cancelButtonActionPerformed(ActionEvent e)
	{
		setVisible(false);
	}

}
