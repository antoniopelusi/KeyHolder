package kh.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CreateFun
{
	public static void CreateDB(String name, String pw) throws Exception
	{
		File db = new File(name + ".keyholder");
		
		FileChooser fc = new FileChooser();
		FileTypeFilter khfilter = new kh.logic.FileTypeFilter(".keyholder", "KeyHolder database");
		fc.addChoosableFileFilter(khfilter);
		fc.setFileFilter(khfilter);
		fc.setSelectedFile(db);
		fc.setDialogTitle("Save file");
		
		if(fc.showSaveDialog(fc) == JFileChooser.APPROVE_OPTION)
		{
			byte[] hashedpw = kh.logic.HashFun.Hash(pw);
			OutputStream out = new FileOutputStream(fc.getSelectedFile());
			out.write(hashedpw);
			out.flush();
			out.close();
			
			JOptionPane.showConfirmDialog(null, "Database created", "🔑", JOptionPane.PLAIN_MESSAGE);
			
			System.out.println("\tName: "+ name + "\n\tPassword: " + pw);
			System.out.println("Database created");
			
			kh.logic.LoadFun.LoadDB(fc.getSelectedFile(), hashedpw, pw);
		}
		else
		{
			System.out.println("Create failed, operation cancelled");
		}
	}
}
