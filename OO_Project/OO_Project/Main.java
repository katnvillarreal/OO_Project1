/*
 * Name: Kathryn Villarreal
 * Course: OOSoftware 9AM 3381
 * CRN: 22199
 */

package OO_Project;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Weird Song Lookup");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MusicPanel panel = new MusicPanel();
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setVisible(true);
	}
}

