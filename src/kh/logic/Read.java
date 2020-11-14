package kh.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Read
{
	public static void ReadFile(File db) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(db));
        Object[] tableLines = br.lines().toArray();
		
        if(tableLines.length > 2)
        {        	
        	kh.gui.EditPanel.pb.setMinimum(0);
        	kh.gui.EditPanel.pb.setMaximum(tableLines.length-2);
        	kh.gui.EditPanel.pb.setValue(0);
        	
        	for(int i = 0; i < tableLines.length-2; i++)
        	{
        		String line = tableLines[i+2].toString().trim();
        		String[] dataRow = line.split("\t");
        		kh.gui.EditPanel.tModel.addRow(dataRow);
        	}
        	br.close();
        }
	}
}