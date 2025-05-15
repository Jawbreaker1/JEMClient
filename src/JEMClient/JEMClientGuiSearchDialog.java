package JEMClient;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class JEMClientGuiSearchDialog extends JDialog{
	
	private JTextField firstName;
	private JTextField lastName;
	private JTextField nickName;
	private JEMClientGui2 owner;
	private String[] columnNames = {"First Name","Last Name","Nickname"};
	private DefaultTableModel tableModel;
	private JButton addButton;
	private JTable searchOutputTable;
	
	
	
	public JEMClientGuiSearchDialog(JEMClientGui2 Owner)
	{
		owner = Owner;
		this.setLayout(null);
		this.setTitle("Add user");
		this.setSize(400, 400);
	   Point ownerPos = owner.getLocation();
	   setLocation(ownerPos.x+10, ownerPos.y+150);
		JLabel infoTextLabel = new JLabel("Type information in at least one field and click Search to start searching.");
		infoTextLabel.setBounds(10, 10, 380, 30);
		this.add(infoTextLabel);
		JLabel firstNameLabel = new JLabel("First name:");
		firstNameLabel.setBounds(10, 70, 100, 20);
		this.add(firstNameLabel);
		JLabel lastNameLabel = new JLabel("Last name:");
		lastNameLabel.setBounds(10,120,100,20);
		this.add(lastNameLabel);
		JLabel nickNameLabel = new JLabel("JEM Nickname:");
		nickNameLabel.setBounds(10,170,100,20);
		this.add(nickNameLabel);
		JLabel resultLabel = new JLabel("Search Result:");
		resultLabel.setBounds(10, 210, 100, 20);
		this.add(resultLabel);
		firstName = new JTextField();
		firstName.setText("");
		firstName.setBounds(120,70,150,20);
		this.add(firstName);
		lastName = new JTextField();
		lastName.setText("");
		lastName.setBounds(120,120,150,20);
		this.add(lastName);
		nickName = new JTextField();
		nickName.setText("");
		nickName.setBounds(120, 170, 150, 20);
		this.add(nickName);
		

		tableModel = new DefaultTableModel();
		tableModel.setColumnCount(3);
		tableModel.setColumnIdentifiers(columnNames);
        searchOutputTable = new JTable(tableModel);
        searchOutputTable.addMouseListener(new MouseAdapter(){
        	public void mousePressed(MouseEvent e)
			{
        		addButton.setEnabled(true);
			}
        });
        JScrollPane searchOutputTableScroll = new JScrollPane(searchOutputTable);
        searchOutputTableScroll.setBounds(10,240,270,80);
        this.add(searchOutputTableScroll);
        
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(290, 70, 80, 25);
		searchButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	search();
            }
        });
		this.add(searchButton);
		addButton = new JButton("Add");
		addButton.setEnabled(false);
		addButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	//make sure we have a selection in the search output area. otherwise create a messagedialog
            	int SelectedRow = searchOutputTable.getSelectedRow();
            	String userToAdd = (String)searchOutputTable.getValueAt(SelectedRow, 2);
            	owner.sendAddUser(userToAdd);
            	//send an "add-user" message to the server.
            }
        });
		addButton.setBounds(10, 330, 80, 25);
		this.add(addButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(200,330,80,25);
		cancelButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	JEMClientGuiSearchDialog.this.setVisible(false);
            }
        });
		this.add(cancelButton);
		this.setVisible(true);
	}
	
	public void addToTable(String firstName, String lastName, String nickName)
	{
		String[] toAdd = {firstName,lastName,nickName};
		tableModel.addRow(toAdd);
	}
	
	private void search()
	{
		//clear the table
		int nrOfRows = tableModel.getRowCount();
		for(int i = 0; i<nrOfRows;i++)
		{
			tableModel.removeRow(i);
		}
		//make sure all fields are not empty
		if(!firstName.getText().equals("")||!lastName.getText().equals("")||!nickName.getText().equals(""))
    	{
			String searchRequest = "";
			//check which of the fields we are going to use for the search
			if(!firstName.getText().equals(""))
			{
				searchRequest = searchRequest + firstName.getText() + ",";
			}
			else
			{
				searchRequest = searchRequest + " ,";
			}
			if(!lastName.getText().equals(""))
			{
				searchRequest = searchRequest + lastName.getText() + ",";
			}
			else
			{
				searchRequest = searchRequest + " ,";
			}
			if(!nickName.getText().equals(""))
			{
				searchRequest = searchRequest + nickName.getText() + ",";
			}
			else
			{
				searchRequest = searchRequest + " ,";
			}
			owner.sendSearch(searchRequest);
        	//send a searchmessage to the server
    	}
	}

}
