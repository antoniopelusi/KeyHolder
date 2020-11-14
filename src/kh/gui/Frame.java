package kh.gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;

public class Frame extends JFrame
{
	public static StartPanel startpanel;
	
	private static final long serialVersionUID = 1L;
	
	public Frame()
	{
		super();

		JComponent cp = (JComponent) getContentPane();
		cp.setTransferHandler(new MyFileTransferHandler());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setUndecorated(true);
	    getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
	    
		setTitle(" KeyHolder password manager");
		super.setSize(500, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		setLayout(null);
		
		startpanel = new StartPanel();
		
		add(startpanel);
	}
	
	class MyFileTransferHandler extends TransferHandler
	{
		private static final long serialVersionUID = 1L;
		
		public boolean canImport(JComponent arg0, DataFlavor[] arg1)
		{
			for (int i = 0; i < arg1.length; i++)
			{
				DataFlavor flavor = arg1[i];
				if (flavor.equals(DataFlavor.javaFileListFlavor))
				{
					return true;
				}
				if (flavor.equals(DataFlavor.stringFlavor)) {
					return true;
				}
			}
			return false;
		}
		
		public boolean importData(JComponent comp, Transferable t)
		{
			DataFlavor[] flavors = t.getTransferDataFlavors();
			
			for (int i = 0; i < flavors.length; i++)
			{
				DataFlavor flavor = flavors[i];
				try
				{
					if (flavor.equals(DataFlavor.javaFileListFlavor))
					{
						System.out.println("importData: FileListFlavor");
						
						List<?> l = (List<?>) t.getTransferData(DataFlavor.javaFileListFlavor);
						Iterator<?> iter = l.iterator();
						
						while (iter.hasNext())
						{
							File file = (File) iter.next();
							
							System.out.println("GOT FILE: " + file.getCanonicalPath());
						
							if(iter.hasNext())
				            {
								JOptionPane.showConfirmDialog(null, "unable to import multiple databases", "", JOptionPane.PLAIN_MESSAGE);
								return false;
				            }
							
							kh.gui.StartPanel.loadDatabaseByDrag(file);
						}
						return true;
					}
					else if (flavor.equals(DataFlavor.stringFlavor)) {
						System.out.println("importData: String Flavor");
						String fileOrURL = (String) t.getTransferData(flavor);
						System.out.println("GOT STRING: " + fileOrURL);
						
						try {
							URL url = new URL(fileOrURL);
							System.out.println("Valid URL: " + url.toString());
							
							return true;
						} catch (MalformedURLException ex) {
							System.err.println("Not a valid URL");
							return false;
						}
						
					}

				}
				catch (IOException ex)
				{
					System.err.println("IOError getting data: " + ex);
				}
				catch (UnsupportedFlavorException e)
				{
					System.err.println("Unsupported Flavor: " + e);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			Toolkit.getDefaultToolkit().beep();
			return false;
		}
	}
}
