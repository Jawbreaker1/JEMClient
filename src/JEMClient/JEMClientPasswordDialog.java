package JEMClient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



/****************************************
 * 										*
 * @author Johan Engwall				*
 * creates a dialogwindow for changing	*
 * password in JEM						*
 * 										*
 ****************************************/

public class JEMClientPasswordDialog extends JDialog{
	
	private JEMClientGui2 tmpOwner;
	JTextField OldPasswordText;
	JTextField NewPasswordText;
	JTextField RetypeText;
	
	public JEMClientPasswordDialog(JEMClientGui2 owner, String user)
	{
		tmpOwner = owner;
		setLayout(null);
		setMinimumSize(new Dimension(200,230));
		this.setLocation(owner.getLocation().x+20,owner.getLocation().y+200);
		this.setTitle("New Password");
		JLabel OldPassword = new JLabel("Old Password:");
		OldPassword.setBounds(5, 5, 75, 20);
		OldPasswordText = new JPasswordField();
		OldPasswordText.setBounds(80, 5, 80, 20);
		JLabel NewPassword = new JLabel("New Password:");
		NewPassword.setBounds(5,35,75,20);
		NewPasswordText = new JPasswordField();
		NewPasswordText.setBounds(80, 35, 80, 20);
		JLabel Retype = new JLabel("Re-enter:");
		Retype.setBounds(5, 65, 75, 20);
		RetypeText = new JPasswordField();
		RetypeText.setBounds(80, 65, 80, 20);
		
		
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
		
		add(OldPassword);
		add(OldPasswordText);
		add(NewPassword);
		add(NewPasswordText);
		add(Retype);
		add(RetypeText);
		add(okButton);
		add(cancelButton);
		
	    pack();
	    setVisible(true);
		
	}

	private void okButtonActionPerformed(ActionEvent e)
	{
		//kolla så att de båda "new password" fälten stämmer överens innan vi gör något.
		JEMClientUser tmpUser = tmpOwner.getUser();
		
		if(tmpUser.getPassword().equals(OldPasswordText.getText()))
		{
			if(NewPasswordText.getText().equals(RetypeText.getText()))
			{
				//tell owner to create a change password message and send to server.
				tmpOwner.sendPasswordChange(NewPasswordText.getText());
			}
			else
			{
				JEMClientMessageDialog dontMatch = new JEMClientMessageDialog("New password does not match!", tmpOwner);	
			}
		}
		else
		{
			JEMClientMessageDialog dontMatch = new JEMClientMessageDialog("Incorrect old password!", tmpOwner);
		}
		
		setVisible(false);
	}
	
	private void cancelButtonActionPerformed(ActionEvent e)
	{
		setVisible(false);
	}
}
