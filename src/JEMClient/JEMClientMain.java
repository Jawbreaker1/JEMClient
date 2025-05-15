package JEMClient;

import javax.swing.SwingUtilities;
import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class JEMClientMain {
	
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }
    
    private static void createAndShowGUI(){
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	//create main window
    	JEMClientMessageHandler MessageQueue = new JEMClientMessageHandler();
    	JEMClientGui2 clientGui = new JEMClientGui2(MessageQueue);
    }
}
