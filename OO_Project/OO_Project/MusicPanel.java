package OO_Project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class MusicPanel extends JPanel{
	private static final int DEFAULT_SIZE = 3;
	private static final int MAX_ID = 999999;
	private Song currentSong;
	private Song_Collection all;
	private Song_Collection currentPlaylist;
	private Song_Collection queue;
	private JLabel lblCurrentSong;
	private JButton btnNext;
	private JButton btnPrev;
	private JProgressBar progressBar;
	private JTextArea textArea;
	private JComboBox comboBox;
	private JToggleButton playButton;
	
	public MusicPanel() {
		setForeground(Color.DARK_GRAY);
		setBorder(new BevelBorder(BevelBorder.RAISED, new Color(128, 128, 128), Color.GRAY, null, null));
		setLayout(null);
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(500, 400));
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(2, 0, 0, 0, (Color) new Color(192, 192, 192)));
		panel.setBackground(Color.DARK_GRAY);
		panel.setForeground(Color.DARK_GRAY);
		panel.setBounds(0, 321, 500, 79);
		add(panel);
		panel.setLayout(null);
		//Set private data members
		all = new Song_Collection("./OO_Project/finalTracks.csv");
		currentPlaylist = all;
		ArrayList<String> playlists = new ArrayList<String>();
		for (int i = 0; i < all.getSongs().length; i++) {
			try {
				Song s = all.getSongs()[i];
				if(!playlists.contains(s.getGenre())) {
					playlists.add(s.getGenre());
				} 
			}catch(Exception e) {
				continue;
			}
		}
		String genres[] = new String[playlists.size()+1];
		genres[0] = "All";
		for(int j =1;j<playlists.size()+1;j++){
		  genres[j] = playlists.get(j-1);
		}
		
		
		// Get the first song of the all playlist as the default song
		for(int i = 0; i < MAX_ID; i++) {
			try {
				currentSong = all.findSong(i);
				break;
			}catch(Exception e){
				continue;
			}
		}
		
		queue = setQueue(currentSong, currentPlaylist);
		
		// Current Song Label
		lblCurrentSong = new JLabel("Current Song:" + currentSong.getTitle());
		lblCurrentSong.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentSong.setForeground(Color.WHITE);
		lblCurrentSong.setFont(new Font("Consolas", Font.BOLD, 16));
		lblCurrentSong.setBackground(Color.DARK_GRAY);
		lblCurrentSong.setBounds(100, 11, 300, 39);
		panel.add(lblCurrentSong);
		
		// Next Song Button
		btnNext = new JButton("Next");
		btnNext.setForeground(Color.WHITE);
		btnNext.setBackground(Color.DARK_GRAY);
		btnNext.setBounds(410, 11, 80, 57);
		btnNext.addActionListener(new NextListener());
		panel.add(btnNext);
		
		// Prev. Song Button 
		btnPrev = new JButton("Prev.");
		btnPrev.setBackground(Color.DARK_GRAY);
		btnPrev.setForeground(Color.WHITE);
		btnPrev.setBounds(10, 15, 80, 53);
		btnPrev.addActionListener(new PrevListener());
		panel.add(btnPrev);
		
		// Song Progress Bar
		progressBar = new JProgressBar(0,60);
		progressBar.setBounds(100, 61, 300, 7);
		progressBar.setValue(0);
		panel.add(progressBar);
		
		// Weird Song Label
		JLabel lblWeirdSongLookup = new JLabel("Weird Song Lookup");
		lblWeirdSongLookup.setBackground(Color.DARK_GRAY);
		lblWeirdSongLookup.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeirdSongLookup.setForeground(Color.WHITE);
		lblWeirdSongLookup.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 18));
		lblWeirdSongLookup.setBounds(118, 11, 241, 40);
		add(lblWeirdSongLookup);
		
		// Queue Area
		textArea = new JTextArea(queue.toString());
		textArea.setForeground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setBackground(Color.GRAY);
		textArea.setBounds(10, 72, 184, 242);
		add(textArea);
		
		// Queue Label
		JLabel lblQueuedSongs = new JLabel("Queue");
		lblQueuedSongs.setBackground(Color.GRAY);
		lblQueuedSongs.setHorizontalAlignment(SwingConstants.CENTER);
		lblQueuedSongs.setForeground(Color.WHITE);
		lblQueuedSongs.setFont(new Font("Consolas", Font.BOLD, 14));
		lblQueuedSongs.setBounds(57, 48, 100, 27);
		add(lblQueuedSongs);
		
		// Playlist choosing 
		comboBox = new JComboBox(genres);
		comboBox.setFont(new Font("Consolas", Font.PLAIN, 15));
		comboBox.setBounds(304, 74, 175, 22);
		comboBox.addActionListener(new PlaylistListener());
		add(comboBox);
		
		playButton = new JToggleButton("Play/Pause");
		playButton.setForeground(Color.WHITE);
		playButton.setBackground(Color.GRAY);
		playButton.setBounds(204, 290, 112, 27);
		add(playButton);
		
		
		// Playlists Label
		JLabel lblPlaylists = new JLabel("Playlists");
		lblPlaylists.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlaylists.setForeground(Color.WHITE);
		lblPlaylists.setFont(new Font("Consolas", Font.BOLD, 14));
		lblPlaylists.setBounds(319, 52, 136, 19);
		add(lblPlaylists);
		
		
		// Background Image
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MusicPanel.class.getResource("/OO_Project/c67b3e7190011eb6c73fbdb23a39ab88.png")));
		label.setBounds(0, -23, 500, 447);
		add(label);
		
	}
	
	public void setPlaylist(Song_Collection p) {
		currentPlaylist = p;
		currentSong = currentPlaylist.getFirst();
		lblCurrentSong.setText("Current Song:" + currentSong.getTitle());
		queue = setQueue(currentSong, currentPlaylist);
		textArea.setText(queue.toString());
	}
	public Song_Collection setQueue(Song s, Song_Collection p) {
		Song_Collection q = new Song_Collection();
		for(int i = s.getTrackId()+1; i < MAX_ID; i++) {
			try {
				if (q.getSize()<3) {
					q.addSong(p.findSong(i));
				}
			}catch(Exception e){
				continue;
			}
			
		}
		return q;
	}
	
	private class NextListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			for(int i = currentSong.getTrackId() + 1; i < MAX_ID; i++) {
				try {
					currentSong = currentPlaylist.findSong(i);
					lblCurrentSong.setText("Current Song:" + currentSong.getTitle());
					queue = setQueue(currentSong, currentPlaylist);
					textArea.setText(queue.toString());
					break;
				}catch(Exception e){
					continue;
				}
			}
		}
	}
	
	private class PrevListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			for(int i = currentSong.getTrackId()-1; i > 0; i--) {
				try {
					currentSong = currentPlaylist.findSong(i);
					lblCurrentSong.setText("Current Song:" + currentSong.getTitle());
					queue = setQueue(currentSong, currentPlaylist);
					textArea.setText(queue.toString());
					break;
				}catch(Exception e){
					continue;
				}
			}
		}
	}
	
	private class PlaylistListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			Object genre = comboBox.getSelectedItem();
			setPlaylist(all.getGenre((String)genre));
		}
	}
	
}