package kh.logic;

import java.io.File;

public class LoadFun
{
	public static File database;
	public static String password;
	
	public static boolean LoadDB(File db, byte[] hashedpw, String pw) throws Exception
	{
		if(kh.logic.HashFun.encoder(hashedpw).equals(kh.logic.HashFun.encoder(kh.logic.HashFun.Hash(pw))))
		{
			password = pw;
			buildEditMode(db);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void buildEditMode(File db) throws Exception
	{
		kh.gui.EditPanel.buildPanel();
		kh.gui.EditPanel.l.setText("Database name: " + db.getName());
		kh.logic.Read.ReadFile(db);
		database = db;
		
		try
		{
			File ioFile = kh.logic.LoadFun.database;
			String key = "cipher.keyholder";
			
			kh.logic.Write.WriteFile(ioFile);
			kh.logic.Crypt.encrypt(key, ioFile, ioFile);
			kh.gui.EditPanel.t.clearSelection();
			kh.gui.EditPanel.pb.setValue(0);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}
}