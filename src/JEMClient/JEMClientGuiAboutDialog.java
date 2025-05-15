package JEMClient;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;



/***************************************************
 * @author Johan Engwall
 *Messagedialog class used when events occur that
 *the user needs to be informed about. 
 *Such as lost connection, incorrect login and so on.
 ***************************************************/


public class JEMClientGuiAboutDialog extends JDialog{
	public JEMClientGuiAboutDialog(JEMClientGui2 owner)
	{
		setLayout(null);
		setMinimumSize(new Dimension(200,150));
		Point ownerPos = owner.getLocation();
		setLocation(ownerPos.x+10, ownerPos.y+150);
		setTitle("About");
		JTextArea TextMessage = new JTextArea("Author: Johan Engwall \nEmail: johan@holo.net");
		TextMessage.setEditable(false);

		TextMessage.setBounds(5, 10, 180, 60);
		JButton okButton = new JButton("Ok");
		okButton.setBounds(15, 80, 75, 25);
		okButton.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	            okButtonActionPerformed(e);
	        }
	    });
		
		add(TextMessage);
		add(okButton);
		
	    pack();
	    setVisible(true);
		
	}
	
	private void okButtonActionPerformed(ActionEvent e)
	{
		setVisible(false);
	}
}
