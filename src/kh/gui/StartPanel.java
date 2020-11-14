package kh.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kh.logic.FileTypeFilter;
import kh.logic.RequestFocusListener;

public class StartPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JLabel l1, l2, l3;
	private JButton b1, b2, minimize, close;
	private JPanel titlebar;
	
	private int tx, ty;
	
	public StartPanel()
	{
		setBackground(new Color(225, 225, 225));
		setLayout(null);
		setSize(500, 300);
		
		titlebar = new JPanel();
		titlebar.setBackground(new Color(225, 225, 225));
		titlebar.setBounds(0, 0, 400, 35);
		titlebar.addMouseListener(new titlebarMouseListener());
		titlebar.addMouseMotionListener(new titlebarDragListener());
		
		minimize = new JButton("_");
		minimize.setFont(new Font(null, Font.BOLD, 12));
		minimize.setBounds(410, -10, 45, 55);
		minimize.setBorderPainted(false);
		minimize.setOpaque(false);
		minimize.setBackground(new Color(238, 238, 238));
		minimize.addActionListener(this);
		
		close = new JButton("x");
		close.setFont(new Font(null, Font.BOLD, 12));
		close.setBounds(450, 0, 45, 45);
		close.setBorderPainted(false);
		close.setOpaque(false);
		close.setBackground(new Color(238, 238, 238));
		close.addActionListener(this);
		
		l1 = new JLabel("KeyHolder", JLabel.CENTER);
		l1.setFont(new Font(null, Font.BOLD, 30));
		l1.setBounds(130, 80, 200, 50);
		
		l2 = new JLabel("🔒", JLabel.CENTER);
		l2.setFont(new Font(null, Font.BOLD, 30));
		l2.setForeground(new Color(204, 136, 0));
		l2.setBounds(230, 80, 200, 50);

		b1 = new JButton("Load database");
		b1.setBounds(102, 175, 130, 30);
		b1.setOpaque(true);
		b1.setBackground(new Color(235, 235, 235));
	    b1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		b1.addActionListener(this);
		
		b2 = new JButton("Create database");
		b2.setBounds(266, 175, 130, 30);
		b2.setOpaque(true);
		b2.setBackground(new Color(235, 235, 235));
	    b2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		b2.addActionListener(this);
		
		l3 = new JLabel("or drop file here", JLabel.CENTER);
		l3.setFont(new Font(null, Font.BOLD, 13));
		l3.setForeground(new Color(50, 50, 50));
		l3.setBounds(150, 215, 200, 50);
		
		add(titlebar);
		add(minimize);
		add(close);
		add(l1);
		add(l2);
		add(b1);
		add(b2);
		add(l3);
	}
	
	public static void loadDatabase() throws Exception
	{
		System.out.println("Loading db:");

		File db = null;
		String name = null, pw = null;
		byte[] hashedpw = null;
		
		File ioFile = null;
		String key = "cipher.keyholder";
		
		JFileChooser fc = new JFileChooser();
		FileTypeFilter khfilter = new kh.logic.FileTypeFilter(".keyholder", "KeyHolder database");
		fc.addChoosableFileFilter(khfilter);
		fc.setFileFilter(khfilter);
		
		Action details = fc.getActionMap().get("viewTypeDetails");
		details.actionPerformed(null);

		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				ioFile = fc.getSelectedFile();
				db = kh.logic.Crypt.decrypt(key, ioFile, ioFile);
				name = db.getName();
				hashedpw = new byte[16];
				
				FileInputStream fis = null;
				fis = new FileInputStream(db);
				fis.read(hashedpw, 0, 16);
				fis.close();
				kh.logic.Crypt.encrypt(key, ioFile, ioFile);
			}
			catch (IOException e2)
			{
	    		JOptionPane.showMessageDialog(null, "File does not exist", "⚠", JOptionPane.PLAIN_MESSAGE);
	    		System.out.println("File does not exist");
	    		kh.logic.Write.WriteFile(ioFile);
	    		kh.logic.Crypt.encrypt(key, ioFile, ioFile);
				return;
			}
			
			String fileName = db.toString();
		    int index = fileName.lastIndexOf('.');
		    if(index > 0)
		    {
		    	String extension = fileName.substring(index + 1);
		    	System.out.println("File extension is " + extension);
		    	if (!"keyholder".equalsIgnoreCase(extension))
		    	{
		    		JOptionPane.showMessageDialog(null, "Choose a keyholder file", "⚠", JOptionPane.PLAIN_MESSAGE);
		    	}
		    }
			
			do
			{
				JTextField passwordField = new JPasswordField(10);
				JPanel myPanel = new JPanel();
				myPanel.setVisible(true);
				
				myPanel.add(new JLabel("password:"));
				myPanel.add(passwordField);
				
				passwordField.addAncestorListener(new RequestFocusListener());
				
				int result = JOptionPane.showConfirmDialog(null, myPanel, "🔒", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION)
				{			
					if(!passwordField.getText().equals(""))
					{
						pw = passwordField.getText();
					}
					else
					{
						JOptionPane.showConfirmDialog(null, "Empty fields","⚠", JOptionPane.OK_CANCEL_OPTION);
					}
				}
			}
			while(pw != null && ("".equals(pw)));
			
			if(pw != null)
			{
				try
				{
					kh.logic.Crypt.decrypt(key, ioFile, ioFile);
					if(kh.logic.LoadFun.LoadDB(db, hashedpw, pw))
					{
						System.out.println("\tName: "+ name + "\n\tPassword: " + pw);
						System.out.println("Database loaded");
						kh.logic.Write.WriteFile(ioFile);
						kh.logic.Crypt.encrypt(key, ioFile, ioFile);
						return;
					}
					else
					{
						JOptionPane.showConfirmDialog(null, "Wrong password","⚠", JOptionPane.OK_CANCEL_OPTION);
						System.out.println("Loading failed, wrong password");
						kh.logic.Crypt.encrypt(key, ioFile, ioFile);
						return;
					}
				}
				catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e1)
				{
					e1.printStackTrace();
					kh.logic.Write.WriteFile(ioFile);
					kh.logic.Crypt.encrypt(key, ioFile, ioFile);
					return;
				}
			}
			else
			{					
				System.out.println("Loading failed, no password");
			}
		}
		else
        {
			System.out.println("Loading failed, operation cancelled");
        }
	}
	
	public static void loadDatabaseByDrag(File db) throws HeadlessException, Exception
	{
		System.out.println("Loading db:");

		String name = null, pw = null;
		byte[] hashedpw = null;
		
		File ioFile = db;
		String key = "cipher.keyholder";
		
		name = db.getName();
		hashedpw = new byte[16];
		
		String fileName = db.toString();
	    int index = fileName.lastIndexOf('.');
	    if(index > 0)
	    {
	    	String extension = fileName.substring(index + 1);
	    	System.out.println("File extension is " + extension);
	    	if (!"keyholder".equalsIgnoreCase(extension))
	    	{
				JOptionPane.showConfirmDialog(null, "Not a keyholder file","⚠", JOptionPane.OK_CANCEL_OPTION);

	    		return;
	    	}
	    }
		
		do
		{
			try
			{
				ioFile = db;
				if(ioFile.isFile())
				{
					db = kh.logic.Crypt.decrypt(key, ioFile, ioFile);
					FileInputStream fis = null;
					fis = new FileInputStream(db);
					fis.read(hashedpw, 0, 16);
					fis.close();
					kh.logic.Crypt.encrypt(key, ioFile, ioFile);
				}
				else
				{
					JOptionPane.showConfirmDialog(null, "Unable to load a folder",  "⚠", JOptionPane.PLAIN_MESSAGE);
					kh.logic.Crypt.encrypt(key, ioFile, ioFile);
					return;
				}
			}
			catch (IOException e2)
			{
				e2.printStackTrace();
				kh.logic.Crypt.encrypt(key, ioFile, ioFile);
			}
			
			
			JTextField passwordField = new JPasswordField(10);
			JPanel myPanel = new JPanel();
			myPanel.setVisible(true);
			
			myPanel.add(new JLabel("password:"));
			myPanel.add(passwordField);
			
			passwordField.addAncestorListener(new RequestFocusListener());
			
			int result = JOptionPane.showConfirmDialog(null, myPanel, "🔒", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION)
			{			
				if(!passwordField.getText().equals(""))
				{
					pw = passwordField.getText();
				}
				else
				{
					JOptionPane.showConfirmDialog(null, "Empty fields","⚠", JOptionPane.OK_CANCEL_OPTION);
				}
			}
		}
		while(pw != null && ("".equals(pw)));
		
		if(pw != null)
		{
			try
			{
				kh.logic.Crypt.decrypt(key, ioFile, ioFile);
				if(kh.logic.LoadFun.LoadDB(db, hashedpw, pw))
				{
					System.out.println("\tName: "+ name + "\n\tPassword: " + pw);
					System.out.println("Database loaded");
					kh.logic.Write.WriteFile(ioFile);
					kh.logic.Crypt.encrypt(key, ioFile, ioFile);
				}
				else
				{
					JOptionPane.showConfirmDialog(null, "Wrong password","⚠", JOptionPane.OK_CANCEL_OPTION);
					System.out.println("Loading failed, wrong password");
					kh.logic.Crypt.encrypt(key, ioFile, ioFile);
					return;
				}
			}
			catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e1)
			{
				e1.printStackTrace();
				kh.logic.Crypt.encrypt(key, ioFile, ioFile);
				return;
			}
		}
		else
		{					
			System.out.println("Loading failed, no password");
		}
	}
	
	public static void createDatabase() throws Exception
	{
		System.out.println("Creating database:");
				
		JTextField nameField = new JTextField(10);
		JTextField passwordField = new JPasswordField(10);
		
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Name:"));
		myPanel.add(nameField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("password:"));
		myPanel.add(passwordField);
		
		nameField.addAncestorListener( new RequestFocusListener() );
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, "🔑", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION)
		{			
			if(!nameField.getText().equals("") && !passwordField.getText().equals(""))
			{
				try
				{
					kh.logic.CreateFun.CreateDB(nameField.getText(), passwordField.getText());
				}
				catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e1) {
					e1.printStackTrace();
				}
			}
			else
			{
				JOptionPane.showConfirmDialog(null, "Empty fields","⚠", JOptionPane.OK_CANCEL_OPTION);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		kh.main.Main.f.requestFocus();
		
		if(e.getActionCommand() == "Load database")
		{
			try
			{
				loadDatabase();
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		
		if(e.getActionCommand() == "Create database")
		{
			try
			{
				createDatabase();
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		
		if(e.getActionCommand() == "x")
		{
			kh.main.Main.f.dispose();
		}
		
		if(e.getActionCommand() == "_")
		{
			kh.main.Main.f.setState(Frame.ICONIFIED);
		}
	}
	
	public class titlebarMouseListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			tx = e.getX();
			ty = e.getY();
		}
	}
	
	public class titlebarDragListener extends MouseAdapter
	{	
		public void mouseDragged(MouseEvent e)
		{
			kh.main.Main.f.setLocation(e.getXOnScreen()-tx, e.getYOnScreen()-ty);
		}
	}
}
