package OO_Project;

import java.io.*;
import java.util.*;

public class Song_Collection {
	private int size;
	private static final int DEFAULT_SIZE = 10;
	private Song[] songs;
	private String fileName;
	
	public Song_Collection() {
		// default constructor
		size = 0;
		songs = new Song[DEFAULT_SIZE];
		fileName = null;
	}
	public Song_Collection(String fn) {
		// constructor when given a filename
		this();
		fileName = fn;
		readFile();
	}	
	
	public int getSize() {
		return size;
	}
	
	public Song getFirst() {
		return songs[0];
	}
	
	public Song[] getSongs() {
		return songs;
	}
	public void addSong(Song s) {
		//Adding a song to the collection
		if (size >= songs.length)
			doubleArray();
		songs[size] = s;
		size++;
	}
	
	public void removeSong (int id) {
		//Removing a song from the collection
		for(int i = 0; i < size; i++) {
			if(songs[i].getTrackId()==id) {
				songs[i] = songs[size-1];
				size--;
			}
		}
	}
	
	public void editSong(Song s) {
		//Editing a song
		//Based on the track id given by s change the song to reflect the song qualities given
		for(int i = 0; i < size; i++) {
			if(songs[i].getTrackId() == s.getTrackId()) {
				songs[i] = s;
			}
		}
	}
	private void doubleArray () {
		//increase the size of the array
		Song[] newSongs = new Song[songs.length*2];
		for (int i = 0; i < size; i++) {
			newSongs[i] = songs[i];
		}		
		songs = newSongs;
	}
	
	public Iterator<Song> iterator() {
		// create an iterator
		return new SongIterator<Song>(songs,size);
	}
	
	public Song findSong(int id) {
		// look up a song based on the song id given
		for (int i = 0; i < size; i++) {
			if(songs[i].getTrackId() == id) {
				return songs[i];
			}
		}
		throw new NoSuchElementException("not present");
	}
	public Song_Collection findArtist(String artist) {
		// based on the sring given return a collection with all the songs that have this artist
		Song_Collection newSet = new Song_Collection();
		for (int i = 0; i < size; i++) {
			if(songs[i].getArtist().equals(artist)) {
				newSet.addSong(songs[i]);
			}
		}
		return newSet;
	}
	
	public Song findSong(String artist, String track, String album) {
		//find a song based on the song title, artist name and album name
		for (int i = 0; i < size; i++) {
			if(songs[i].getArtist().equals(artist)&&
			   songs[i].getTitle().equals(track)&&
			   songs[i].getAlbum().equals(album)) {
				return songs[i];
			}
		}
		throw new NoSuchElementException("not present");
	}
	
	public Song_Collection genreAndYear(String g, int y) {
		//give genre and year of release return a collection with all the songs that 
		// have the same genre and release year
		Song_Collection newSet = new Song_Collection();
		for (int i = 0; i < size; i++) {
			if(songs[i].getGenre().equals(g)&&
			   songs[i].getYear()==y) {
				newSet.addSong(songs[i]);
			}
		}
		return newSet;
	}
	
	public Song_Collection getGenre(String g) {
		//give genre and year of release return a collection with all the songs that 
		// have the same genre and release year
		Song_Collection newSet = new Song_Collection();
		for (int i = 0; i < size; i++) {
			if(songs[i].getGenre().equals(g)) {
				newSet.addSong(songs[i]);
			}
		}
		return newSet;
	}
	
	public String toString() {
		//print the collection
		String toReturn = "";
		for (int i = 0; i < size; i++) {
			toReturn += songs[i]+"\n";
		}
		return toReturn;
	}

	private void readFile () {
		//read in a given file
		BufferedReader lineReader = null;
		try {
			FileReader fr = new FileReader(fileName);
			lineReader = new BufferedReader(fr);
			String line = null;
			while ((line = lineReader.readLine())!=null) {
				String[] a = line.split(",");
				int track_id = Integer.parseInt(a[0]);
				String artist = a[1];
				String genre = a[2];
				String track = a[3];
				String album = a[4];
				int year = Integer.parseInt(a[5]);
				double lon = Double.parseDouble(a[6]);
				addSong(new Song(track_id, artist, genre, track, album, year, lon));
			}
		} 
		catch (Exception e) {
			System.err.println("there was a problem with the file reader, try different read type.");
			try {
				lineReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName.substring(1))));
				String line = null;
				while ((line = lineReader.readLine())!=null) {
					String[] a = line.split(",");
					int track_id = Integer.parseInt(a[0]);
					String artist = a[1];
					String genre = a[2];
					String track = a[3];
					String album = a[4];
					int year = Integer.parseInt(a[5]);
					double lon = Double.parseDouble(a[6]);
					addSong(new Song(track_id, artist, genre, track, album, year, lon));
				}
			} 
			catch (Exception e2) {
				System.err.println("there was a problem with the file reader, try again.  either no such file or format error");
			} 
			finally {
				if (lineReader != null)
					try {
						lineReader.close();
					} catch (IOException e2) {
						System.err.println("could not close BufferedReader");
					}
			}			
		} 
		finally {
			if (lineReader != null)
				try {
					lineReader.close();
				} catch (IOException e) {
					System.err.println("could not close BufferedReader");
				}
		}
	} // end of readFile method	
	
	public void writeFile () {
		// overloaded method: this calls doWrite with file used to read data
		// use this for saving data between runs
		doWrite(fileName);
	} // end of writeFile method

	public void writeFile(String altFileName) {
		// overloaded method: this calls doWrite with different file name 
		// use this for testing write
		doWrite(altFileName);		
	}// end of writeFile method
	
	private void doWrite(String fn) {
		// this method writes all of the data in the persons array to a file
		try
		{
			FileWriter fw = new FileWriter(fn);
			BufferedWriter myOutfile = new BufferedWriter(fw);			
			
			for (int i = 0; i < size; i++) {
				Song s = songs[i];
				if (s instanceof Song) {
					myOutfile.write (s.getTrackId()+",");
					myOutfile.write (s.getArtist()+",");
					myOutfile.write (s.getGenre()+",");
					myOutfile.write (s.getTitle()+",");
					myOutfile.write (s.getAlbum()+",");
					myOutfile.write (s.getYear()+",");
					myOutfile.write (s.getLon()+"\n");
				}
				else {
					System.err.println("error in array, instance type not found");
				}
			}
			myOutfile.flush();
			myOutfile.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Didn't save to " + fn);
		}
	}
}
