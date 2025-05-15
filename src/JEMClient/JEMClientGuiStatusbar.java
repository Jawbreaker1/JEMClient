package JEMClient;

import java.awt.Dimension;

import javax.swing.JLabel;

public class JEMClientGuiStatusbar extends JLabel{

	public JEMClientGuiStatusbar(JEMClientGuiChat owner)
	{
		super();
        super.setPreferredSize(new Dimension(100, 16));
        setMessage("Ready");	
	}
	
	public void setMessage(String message) {
        setText(" "+message);        
    }    
	
	
}
