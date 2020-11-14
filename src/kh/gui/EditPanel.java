package kh.gui;

import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import kh.logic.RequestFocusListener;

public class EditPanel extends JFrame implements ActionListener, ItemListener
{
	private static final long serialVersionUID = 1L;
	
	private static Object[] header = {"name", "email", "password"};
	
	public static DefaultTableModel tModel = new DefaultTableModel(header, 0);
	
	public static JTable t = new JTable(tModel);

	public static EditPanel ep;
	
	public static JLabel l = new JLabel();
	public static JProgressBar pb = new JProgressBar();
	public JButton b1, b2, b3, b4, b5, b6, minimize, close;
	public JCheckBox c = new JCheckBox("Show passwords");
	
	private int tx, ty;
	
	public EditPanel()
	{
		super();
		
		addMouseListener(new panelListener());
		setTitle("KeyHolder password manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(700, 500);
		setLocationRelativeTo(null);
		setResizable(false);
	    setUndecorated(true);
	    getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		
		setLayout(null);
		
		tModel = new DefaultTableModel()
		{
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column)
			{
			      return false;
			}
		};
		tModel.setColumnIdentifiers(header);
		
		JPanel titlebar = new JPanel();
		titlebar.setBackground(new Color(238, 238, 238));
		titlebar.setBounds(0, 0, 600, 35);
		titlebar.addMouseListener(new titlebarMouseListener());
		titlebar.addMouseMotionListener(new titlebarDragListener());
		
		t = new JTable(tModel);
		t.setBackground(null);
		t.setRowHeight(20);
		t.getTableHeader().setReorderingAllowed(false);
        t.setRowSelectionAllowed(true);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        t.getTableHeader().setBackground(new Color(225, 225, 225));
        
        t.getColumnModel().getColumn(0).setMinWidth(133);
		t.getColumnModel().getColumn(0).setMaxWidth(133);
		t.getColumnModel().getColumn(0).setPreferredWidth(133);
		
		t.getColumnModel().getColumn(1).setMinWidth(231);
		t.getColumnModel().getColumn(1).setMaxWidth(231);
		t.getColumnModel().getColumn(1).setPreferredWidth(231);
		
		t.getColumnModel().getColumn(2).setMinWidth(0);
		t.getColumnModel().getColumn(2).setMaxWidth(0);
		t.getColumnModel().getColumn(2).setPreferredWidth(0);

        JScrollPane sp = new JScrollPane(t);
        sp.setBounds(8, 35, 500, 410);
        
        minimize = new JButton("_");
		minimize.setFont(new Font(null, Font.BOLD, 12));
		minimize.setBounds(610, -10, 45, 55);
		minimize.setBorderPainted(false);
		minimize.setOpaque(true);
		minimize.setBackground(new Color(238, 238, 238));
		minimize.addActionListener(this);
		
		close = new JButton("x");
		close.setFont(new Font(null, Font.BOLD, 12));
		close.setBounds(650, 0, 45, 45);
		close.setBorderPainted(false);
		close.setOpaque(true);
		close.setBackground(new Color(238, 238, 238));
		close.addActionListener(this);
        
        b1 = new JButton("Add");
        b1.setBounds(552, 60, 100, 35);
        b1.setOpaque(true);
		b1.setBackground(new Color(245, 245, 245));
		b1.addActionListener(this);
		
		b2 = new JButton("Delete");
	    b2.setBounds(552, 120, 100, 35);
	    b2.setOpaque(true);
	    b2.setBackground(new Color(245, 245, 245));
	    b2.addActionListener(this);
        
	    b3 = new JButton("Edit");
	    b3.setBounds(552, 180, 100, 35);
	    b3.setOpaque(true);
	    b3.setBackground(new Color(245, 245, 245));
	    b3.addActionListener(this);
		
		b5 = new JButton("↑");
		b5.setBounds(582, 255, 40, 40);
		b5.setOpaque(true);
		b5.setBackground(new Color(245, 245, 245));
		b5.addActionListener(this);
		
		b6 = new JButton("↓");
		b6.setBounds(582, 310, 40, 40);
		b6.setOpaque(true);
		b6.setBackground(new Color(245, 245, 245));
		b6.addActionListener(this);
		
		b4 = new JButton("Save");
        b4.setBounds(552, 390, 100, 35);
        b4.setOpaque(true);
		b4.setBackground(new Color(245, 245, 245));
		b4.addActionListener(this);
		
	    JSeparator s = new JSeparator();
		s.setBackground(Color.lightGray);
		s.setForeground(Color.lightGray);
        s.setOrientation(JSeparator.HORIZONTAL); 
        s.setBounds(0, 452, 1000, 1);
        
        l.setBounds(20, 465, 500, 20);
		l.setFont(new Font(null, Font.PLAIN, 12));
		
		c.setBounds(385, 465, 150, 20);
		c.setOpaque(false);
		c.addItemListener(this);
		
		pb.setValue(0);
        pb.setStringPainted(true);
        pb.setBackground(null);
        pb.setBounds(542, 467, 120, 18);
        
        add(titlebar);
        add(minimize);
		add(close);
        add(sp);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(s);
        add(l);
        add(pb);
        add(c);
	}
	
	public static void buildPanel() throws Exception
	{
		ep = new EditPanel();
		ep.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				kh.main.Main.f.setVisible(true);
			}
		});
		
		kh.main.Main.f.setVisible(false);
		ep.setVisible(true);
		ep.requestFocus();
	}
	
	public void moveUpwards()
	{
	    moveRowBy(-1);
	}

	public void moveDownwards()
	{
	    moveRowBy(1);
	}

	private void moveRowBy(int by)
	{
	    DefaultTableModel model = (DefaultTableModel) t.getModel();
	    if(t.getSelectedRow() == -1)
	    {
			JOptionPane.showConfirmDialog(null, "Select a row", "⚠", JOptionPane.PLAIN_MESSAGE);
	    }
	    else
	    {
	    	int[] rows = t.getSelectedRows();
	    	int destination = rows[0] + by;
	    	int rowCount = model.getRowCount();
	    	
	    	if (destination < 0 || destination >= rowCount)
	    	{
	    		return;
	    	}
	    	
	    	model.moveRow(rows[0], rows[rows.length - 1], destination);
	    	t.setRowSelectionInterval(rows[0] + by, rows[rows.length - 1] + by);
	    }
	}
	
	public void addrow()
	{
		JTextField nameField = new JTextField(10);
		JTextField emailField = new JTextField(20);
		JTextField passwordField = new JPasswordField(10);
		
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("name:"));
		myPanel.add(nameField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("email:"));
		myPanel.add(emailField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("password:"));
		myPanel.add(passwordField);
		
		nameField.addAncestorListener( new RequestFocusListener() );
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, "🔑", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION)
		{
			if(!nameField.getText().equals("") && !emailField.getText().equals("") && !passwordField.getText().equals(""))
			{
				tModel.addRow(new Object[]{nameField.getText(), emailField.getText(), passwordField.getText()});				
			}
			else
			{
				JOptionPane.showConfirmDialog(null, "Empty fields","⚠", JOptionPane.OK_CANCEL_OPTION);
			}
		}
	}
	
	public boolean addrow(String name, String email, String password)
	{
		JTextField nameField = new JTextField(name, 10);
		JTextField emailField = new JTextField(email, 20);
		JTextField passwordField = new JPasswordField(password, 10);
		
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("name:"));
		myPanel.add(nameField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("email:"));
		myPanel.add(emailField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("password:"));
		myPanel.add(passwordField);
		
		nameField.addAncestorListener(new RequestFocusListener());

		int result = JOptionPane.showConfirmDialog(null, myPanel, "🔑", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION)
		{
			if(!nameField.getText().equals("") && !emailField.getText().equals("") && !passwordField.getText().equals(""))
			{
				tModel.addRow(new Object[]{nameField.getText(), emailField.getText(), passwordField.getText()});
				return true;
			}
			else
			{
				JOptionPane.showConfirmDialog(null, "Empty fields","⚠", JOptionPane.OK_CANCEL_OPTION);
			}
		}
		return false;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		ep.requestFocus();
        kh.gui.EditPanel.pb.setValue(0);

		if(e.getActionCommand() == "Add")
		{
			addrow();
			t.clearSelection();
		}
		
		if(e.getActionCommand() == "Delete")
		{
			if(t.getSelectedRow() != -1)
			{
				tModel.removeRow(t.getSelectedRow());
				
				t.clearSelection();
			}
			else
			{
				JOptionPane.showConfirmDialog(null, "Select a row", "⚠", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
		if(e.getActionCommand() == "Edit")
		{
			if(t.getSelectedRow() != -1)
			{
				int pos = t.getSelectedRow()+1;
				int tot = t.getRowCount();
				
				String strName = (String) t.getValueAt(t.getSelectedRow(), 0);
				String strEmail = (String) t.getValueAt(t.getSelectedRow(), 1);
				String strPassword = (String) t.getValueAt(t.getSelectedRow(), 2);
				
				if(addrow(strName, strEmail, strPassword))
				{
					tModel.removeRow(t.getSelectedRow());
					t.setRowSelectionInterval(tot-1, tot-1);
					moveRowBy(-(tot-pos));
				}
			}
			else
			{
				JOptionPane.showConfirmDialog(null, "Select a row", "⚠", JOptionPane.PLAIN_MESSAGE);
			}
			t.clearSelection();
		}
		
		if(e.getActionCommand() == "↑")
		{
			moveRowBy(-1);
		}
		
		if(e.getActionCommand() == "↓")
		{
			moveRowBy(1);
		}
		
		if(e.getActionCommand() == "Save")
		{
			try
			{
				File ioFile = kh.logic.LoadFun.database;
				String key = "cipher.keyholder";
				
				kh.logic.Write.WriteFile(ioFile);
				kh.logic.Crypt.encrypt(key, ioFile, ioFile);
				t.clearSelection();
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		
		if(e.getActionCommand() == "x")
		{
			ep.dispose();
			kh.main.Main.main(null);
		}
		
		if(e.getActionCommand() == "_")
		{
			ep.setState(Frame.ICONIFIED);
		}
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getItemSelectable() == c)
		{
			ep.requestFocus();
			t.clearSelection();
			
			if(c.isSelected() == true)
			{
				t.getColumnModel().getColumn(2).setMinWidth(133);
				t.getColumnModel().getColumn(2).setMaxWidth(133);
				t.getColumnModel().getColumn(2).setPreferredWidth(133);
			}
			
			else
			{
				t.getColumnModel().getColumn(2).setMinWidth(0);
				t.getColumnModel().getColumn(2).setMaxWidth(0);
				t.getColumnModel().getColumn(2).setPreferredWidth(0);
			}
		}
	}
	
	public class panelListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			t.clearSelection();
			requestFocus();
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
			ep.setLocation(e.getXOnScreen()-tx, e.getYOnScreen()-ty);
		}
	}
}
