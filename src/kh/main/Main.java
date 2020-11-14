package kh.main;

import javax.swing.JFrame;
import kh.gui.Frame;

/**
 * Main function
 * @author antoniopelusi
 * @version 1.0
 *
 */
public class Main
{
	public static Frame f;

	/**
	 * Create a Frame object
	 * @param args are the main arguments
	 */
	public static void main(String[] args)
	{
		f = new Frame();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.requestFocus();
        f.setVisible(true);
		f.requestFocus();
	}
	
}
