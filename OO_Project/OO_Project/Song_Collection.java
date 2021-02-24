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
		this();
		fileName = fn;
		readFile();
	}	
	public void addSong(Song s) {
		if (size >= songs.length)
			doubleArray();
		songs[size] = s;
		size++;
	}
	
	public void removeSong (int id) {
		for(int i = 0; i < size; i++) {
			if(songs[i].getTrackId()==id) {
				songs[i] = songs[size-1];
				size--;
			}
		}
	}
	private void doubleArray () {
		Song[] newSongs = new Song[songs.length*2];
		for (int i = 0; i < size; i++) {
			newSongs[i] = songs[i];
		}		
		songs = newSongs;
	}
	public String toString() {
		String toReturn = "";
		for (int i = 0; i < size; i++) {
			toReturn += songs[i]+"\n";
		}
		return toReturn;
	}

	private void readFile () {
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
				double lon = Double.parseDouble(a[5]);
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
					double lon = Double.parseDouble(a[5]);
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
