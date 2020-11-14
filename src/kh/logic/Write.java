package kh.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Write
{
	public static void WriteFile(File db) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		
		
        kh.gui.EditPanel.pb.setValue(0);
        kh.gui.EditPanel.pb.setMinimum(0);
        kh.gui.EditPanel.pb.setMaximum(kh.gui.EditPanel.t.getRowCount()*kh.gui.EditPanel.t.getColumnCount());
        
        OutputStream out = new FileOutputStream(db);
		out.write(kh.logic.HashFun.Hash(kh.logic.LoadFun.password));
		out.flush();
		out.close();
		
		FileWriter fw = new FileWriter(db, true);
	    PrintWriter pw = new PrintWriter(fw);
	    
	    pw.print("\n\n");
	    
	    if(kh.gui.EditPanel.t.getRowCount() == 0)
	    {
	    	kh.gui.EditPanel.pb.setMaximum(1);
            kh.gui.EditPanel.pb.setValue(1);
	    }
	    
        for (int row = 0; row < kh.gui.EditPanel.t.getRowCount(); row++) 
        {
            for (int col = 0; col < kh.gui.EditPanel.t.getColumnCount(); col++)
            {
                pw.print(kh.gui.EditPanel.t.getValueAt(row, col));
                if(col != kh.gui.EditPanel.t.getColumnCount()-1)
                {
                	pw.print('\t');                	
                }
                kh.gui.EditPanel.pb.setValue(kh.gui.EditPanel.pb.getValue() + 1);
            }
            if(row != kh.gui.EditPanel.t.getRowCount()-1)
            {
            	pw.print('\n');
            }
            
            kh.gui.EditPanel.pb.setValue(kh.gui.EditPanel.pb.getValue() + 1);
        }
		pw.close();
	}
}
