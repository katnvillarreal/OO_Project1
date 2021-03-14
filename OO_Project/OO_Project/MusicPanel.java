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
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
	private ArrayList<String> playlists;
	private String genres[];
	private JLabel lblCurrentSong;
	private JButton btnNext;
	private JButton btnPrev;
	private JProgressBar progressBar;
	private JTextArea textArea;
	private JComboBox comboBox;
	private JToggleButton playButton;
	private JButton btnAddSong;
	private JButton btnEditSong;
	private JButton btnDeleteSong;
	
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
		playlists = new ArrayList<String>();
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
		genres = new String[playlists.size()+1];
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
		btnNext.setFont(new Font("Courier New", Font.PLAIN, 14));
		btnNext.setForeground(Color.WHITE);
		btnNext.setBackground(Color.DARK_GRAY);
		btnNext.setBounds(410, 11, 80, 57);
		btnNext.addActionListener(new NextListener());
		panel.add(btnNext);
		
		// Prev. Song Button 
		btnPrev = new JButton("Prev.");
		btnPrev.setFont(new Font("Courier New", Font.PLAIN, 14));
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
		playButton.setFont(new Font("Courier New", Font.PLAIN, 12));
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
		
		btnAddSong = new JButton("Add Song");
		btnAddSong.setFont(new Font("Courier New", Font.PLAIN, 12));
		btnAddSong.setForeground(Color.WHITE);
		btnAddSong.setBackground(Color.GRAY);
		btnAddSong.setBounds(348, 219, 131, 23);
		btnAddSong.addActionListener(new AddSongListener());
		add(btnAddSong);
		
		btnEditSong = new JButton("Edit Song");
		btnEditSong.setFont(new Font("Courier New", Font.PLAIN, 12));
		btnEditSong.setBackground(Color.GRAY);
		btnEditSong.setForeground(Color.WHITE);
		btnEditSong.setBounds(348, 253, 131, 23);
		btnEditSong.addActionListener(new EditSongListener());
		add(btnEditSong);
		
		btnDeleteSong = new JButton("Delete Song");
		btnDeleteSong.setFont(new Font("Courier New", Font.PLAIN, 12));
		btnDeleteSong.setForeground(Color.WHITE);
		btnDeleteSong.setBackground(Color.GRAY);
		btnDeleteSong.setBounds(348, 287, 131, 23);
		btnDeleteSong.addActionListener(new DeleteSongListener());
		add(btnDeleteSong);
		
		
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
	
	private class AddSongListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			boolean tryAgain = false;
			String id = new String();
			String newArtist = new String();
			String newGenre = new String();
			String newTitle = new String();
			String newAlbum = new String(); 
			String newYear = new String(); 
			String newLon = new String();
			int newId = 0;
			int year = 0;
			double lon = 0;
			do {
				try {
					// Get the attributes of the new song
					id = JOptionPane.showInputDialog("Enter the Song id");
					newArtist = JOptionPane.showInputDialog("Enter the artist's name");
					newGenre = JOptionPane.showInputDialog("Enter the song genre");
					newTitle = JOptionPane.showInputDialog("Enter the track title");
					newAlbum = JOptionPane.showInputDialog("Enter the album title");
					newYear = JOptionPane.showInputDialog("Enter the year of creation");
					newLon = JOptionPane.showInputDialog("Enter the longitude");
					newId = Integer.parseInt(id);
					year = Integer.parseInt(newYear);
					lon = Double.parseDouble(newLon);
					tryAgain = false;
				} catch (Exception e) {
					tryAgain = true;
				} 
			} while (tryAgain == true);
			//Add the song to all
			all.addSong(new Song(newId, newArtist, newGenre, newTitle, newAlbum, year, lon));
			//Update queue
			queue = setQueue(currentSong, currentPlaylist);
			textArea.setText(queue.toString());
			all.writeFile("./OO_Project/finalTracks.csv");
		}
	}
	
	private class DeleteSongListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			boolean tryAgain = false;
			String id = new String();
			int remId = 0;
			do {
				try {
					// Get the attributes of the new song
					id = JOptionPane.showInputDialog("Enter the Song id to be removed");
					remId = Integer.parseInt(id);
					tryAgain = false;
				} catch (Exception e) {
					tryAgain = true;
				} 
			} while (tryAgain == true);
			//Check if current song
			if(remId == currentSong.getTrackId()) {
				for(int i = currentSong.getTrackId() + 1; i < MAX_ID; i++) {
					try {
						currentSong = currentPlaylist.findSong(i);
						lblCurrentSong.setText("Current Song:" + currentSong.getTitle());
						break;
					}catch(Exception e){
						continue;
					}
				}
			}
			//Remove that song from all
			all.removeSong(remId);
			queue = setQueue(currentSong, currentPlaylist);
			textArea.setText(queue.toString());
			all.writeFile("./OO_Project/finalTracks.csv");
		}
	}
	
	private class EditSongListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			//Get song they want to edit id
			boolean tryAgain = false;
			String id = new String();
			int temp = 0;
			do {
				try {
					// Get the attributes of the new song
					id = JOptionPane.showInputDialog("Enter the Song id to edit");
					temp = Integer.parseInt(id);
					tryAgain = false;
				} catch (Exception e) {
					tryAgain = true;
				} 
			} while (tryAgain == true);
			final int remId = temp;
			// create a dialog Box 
			JDialog d = new JDialog(); 
			d.setLayout(null);
			d.setSize(300, 200);
			// set visibility of dialog 
            d.setVisible(true);
			//Create panel with the aspects of the song auto filled in an editable text area 
            JLabel idLabel = new JLabel("ID: " + remId);
            idLabel.setHorizontalAlignment(SwingConstants.LEFT);
            idLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
            idLabel.setBounds(5, 5, 50, 20);
            d.add(idLabel);
            
            JLabel artistLabel = new JLabel("Artist:");
            artistLabel.setHorizontalAlignment(SwingConstants.LEFT);
            artistLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
            artistLabel.setBounds(5, 20, 50, 35);
            d.add(artistLabel);
            JTextField a = new JTextField(all.findSong(remId).getArtist());
            a.setBounds(50, 25, 100, 20);
            d.add(a);
            
            JLabel genreLabel = new JLabel("Genre:");
            genreLabel.setHorizontalAlignment(SwingConstants.LEFT);
            genreLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
            genreLabel.setBounds(5, 35, 50, 50);
            d.add(genreLabel);
            JTextField g = new JTextField(all.findSong(remId).getGenre());
            g.setBounds(50, 47, 100, 20);
            d.add(g);
            
            JLabel trackLabel = new JLabel("Track:");
            trackLabel.setHorizontalAlignment(SwingConstants.LEFT);
            trackLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
            trackLabel.setBounds(5, 50, 50, 65);
            d.add(trackLabel);
            JTextField t = new JTextField(all.findSong(remId).getTitle());
            t.setBounds(50, 70, 100, 20);
            d.add(t);
            
            JLabel albumLabel = new JLabel("Album:");
            albumLabel.setHorizontalAlignment(SwingConstants.LEFT);
            albumLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
            albumLabel.setBounds(5, 65, 50, 80);
            d.add(albumLabel);
            JTextField al = new JTextField(all.findSong(remId).getAlbum());
            al.setBounds(50, 90, 100, 20);
            d.add(al);
            
            JLabel yearLabel = new JLabel("Year:");
            yearLabel.setHorizontalAlignment(SwingConstants.LEFT);
            yearLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
            yearLabel.setBounds(5, 80, 50, 95);
            d.add(yearLabel);
            String q = String.valueOf(all.findSong(remId).getYear());
            JTextField y = new JTextField(q);
            y.setBounds(50, 115, 100, 20);
            d.add(y);
            
            JLabel lonLabel = new JLabel("Longitude:");
            lonLabel.setHorizontalAlignment(SwingConstants.LEFT);
            lonLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
            lonLabel.setBounds(5, 95, 100, 110);
            d.add(lonLabel);
            q = String.valueOf(all.findSong(remId).getLon());
            JTextField l = new JTextField(q);
            l.setBounds(75, 140, 100, 20);
            d.add(l);
       
			// save the edited fields of the song
            JButton btnSave = new JButton("Save");
    		btnSave.setFont(new Font("Courier New", Font.PLAIN, 14));
    		btnSave.setBounds(200, 130, 75, 20);
    		btnSave.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent ae){
    				String tf_a = a.getText();
    				String tf_g = g.getText();
    				String tf_t = t.getText();
    				String tf_al = al.getText();
    				String tf_y = y.getText();
    				String tf_l = l.getText();
    				int newYear = Integer.parseInt(tf_y);
    				double newLon = Double.parseDouble(tf_l);
    				// update song in all 
    				all.editSong(new Song(remId, tf_a, tf_g, tf_t,tf_al, newYear, newLon));
    				// update queue 
    				queue = setQueue(currentSong, currentPlaylist);
    				textArea.setText(queue.toString());
    				all.writeFile("./OO_Project/finalTracks.csv");
    				d.dispose();
    			}
    		});
    		d.add(btnSave);     
		}
	}
}