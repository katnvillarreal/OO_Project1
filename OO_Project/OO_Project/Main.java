package OO_Project;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

