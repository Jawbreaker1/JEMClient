package JEMClient;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;



/********************************************************
 * @author Johan Engwall
 * Class is used to create a font-menu for the chatwindow.
 ********************************************************/

public class JEMClientGuiChatFontmenu extends JPanel{

	private JComboBox FontSizeCombo;
	private JComboBox FontCombo;
	private JComboBox FontColorCombo;
	private JComboBox FontStyleCombo;
	private JComboBox imageCombo;
	private String[] FontSizeList = {"8","12","16","20","24"};
	private String[] FontList = {"Serif","Monospaced","SansSerif"};
	private String[] FontStyleList = {"PLAIN","BOLD","ITALIC"};
	private String[] ColorList = {"Black","Red","Green","Blue","Pink"};
	private String[] imageList = {":)", ":(", ">:)", ";)",":D",":'(",":P"};
	private JEMClientGuiChat owner;
	
	public JEMClientGuiChatFontmenu(JEMClientGuiChat Owner)
	{
		owner = Owner;
		setLayout(new FlowLayout());
		FontSizeCombo = new JComboBox(FontSizeList);
		FontSizeCombo.setSelectedIndex(2);
		FontSizeCombo.setEditable(false);
		FontSizeCombo.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	FontSizeComboActionPerformed(e);
            }
        });
		FontCombo = new JComboBox(FontList);
		FontCombo.setEditable(false);
		FontCombo.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	FontComboActionPerformed(e);
            }
        });
		FontColorCombo  = new JComboBox(ColorList);
		FontColorCombo.setEditable(false);
		FontColorCombo.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	FontColorComboActionPerformed(e);
            }
        });
		FontStyleCombo = new JComboBox(FontStyleList);
		FontStyleCombo.setEditable(false);
		FontStyleCombo.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	FontStyleComboActionPerformed(e);
            }
        });
		imageCombo = new JComboBox(imageList);
		imageCombo.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	imageComboActionPerformed(e);
            }
        });
		imageCombo.setRenderer(new MyImageCellRenderer());
		this.add(FontSizeCombo);
		this.add(FontCombo);
		this.add(FontColorCombo);	
		this.add(FontStyleCombo);
		//owner.setTextStyle("20", "Serif", "PLAIN", "Black");
		this.add(imageCombo);					
	}
	
	public String getSelectedFontSize()
	{
		String size = (String)FontSizeCombo.getSelectedItem();
		return size;
	}
	public String getSelectedFont()
	{
		String font = (String)FontCombo.getSelectedItem();
		return font;
	}
	public String getSelectedFontColor()
	{
		String color = (String)FontColorCombo.getSelectedItem();
		return color;
	}
	public String getSelectedFontStyle()
	{
		String style = (String)FontStyleCombo.getSelectedItem();
		return style;
	}
	
	
	public void FontSizeComboActionPerformed(ActionEvent e)
	{
		owner.setTextStyle(getSelectedFontSize(), getSelectedFont(), getSelectedFontStyle(), getSelectedFontColor());
	}
	
	public void FontComboActionPerformed(ActionEvent e)
	{
		owner.setTextStyle(getSelectedFontSize(), getSelectedFont(), getSelectedFontStyle(), getSelectedFontColor());
	}
	
	public void FontColorComboActionPerformed(ActionEvent e)
	{
		owner.setTextStyle(getSelectedFontSize(), getSelectedFont(), getSelectedFontStyle(), getSelectedFontColor());
	}
	
	public void FontStyleComboActionPerformed(ActionEvent e)
	{
		owner.setTextStyle(getSelectedFontSize(), getSelectedFont(), getSelectedFontStyle(), getSelectedFontColor());
	}
	
	public void imageComboActionPerformed(ActionEvent e)
	{
		String tmpVal = (String)imageCombo.getSelectedItem();
		owner.addInputText(tmpVal);
		//owner.addText(tmpVal, getSelectedFontSize(), getSelectedFont(), getSelectedFontStyle(), getSelectedFontColor());
	}
	
	
	
}


/***********************************
 * Overrides the cellrenderer for the image combobox.
 ***********************************/
class MyImageCellRenderer extends DefaultListCellRenderer
{
	URL HappySmily = getClass().getResource("images/JEMClient_HappySmily.GIF");
	URL SadSmily = getClass().getResource("images/JEMClient_SadSmily.GIF");
	URL VHappySmily = getClass().getResource("images/JEMClient_VHappySmily.GIF");
	URL CrySmily = getClass().getResource("images/JEMClient_CrySmily.GIF");
	URL FlirtySmily = getClass().getResource("images/JEMClient_FlirtySmily.GIF");
	URL DevilSmily = getClass().getResource("images/JEMClient_DevilSmily.GIF");
	URL BlahSmily = getClass().getResource("images/JEMClient_BlahSmily.GIF");
	
	
	Icon HappySmilyICON = new ImageIcon( HappySmily);
	Icon SadSmilyICON = new ImageIcon( SadSmily);
	Icon VHappySmilyICON = new ImageIcon( VHappySmily);
	Icon CrySmilyICON = new ImageIcon( CrySmily);
	Icon FlirtySmilyICON = new ImageIcon( FlirtySmily);
	Icon DevilSmilyICON = new ImageIcon( DevilSmily);
	Icon BlahSmilyICON = new ImageIcon( BlahSmily);
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,int index, boolean isSelected, boolean cellHasFocus) {

		Component ret = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if ( ret instanceof JLabel )
		{
			if ( value != null && value.toString().equals(":)"))
			{
				((JLabel)ret).setIcon( HappySmilyICON );
			}
			else if ( value != null && value.toString().equals(":("))
			{
				((JLabel)ret).setIcon( SadSmilyICON );
			}
			else if ( value != null && value.toString().equals(":D"))
			{
				((JLabel)ret).setIcon( VHappySmilyICON );
			}
			else if ( value != null && value.toString().equals(":'("))
			{
				((JLabel)ret).setIcon( CrySmilyICON );
			}
			else if ( value != null && value.toString().equals(";)"))
			{
				((JLabel)ret).setIcon( FlirtySmilyICON );
			}
			else if ( value != null && value.toString().equals(">:)"))
			{
				((JLabel)ret).setIcon( DevilSmilyICON );
			}
			else if ( value != null && value.toString().equals(":P"))
			{
				((JLabel)ret).setIcon( BlahSmilyICON );
			}
		}
		return ret;
	}
}


