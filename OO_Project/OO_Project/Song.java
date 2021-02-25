package OO_Project;

public class Song {
	private int track_id;
	private String artist_name;
	private String genre;
	private String track_title;
	private String album_title;
	private int year_created;
	private double artist_longitude;
	
	public Song() {
		track_id = 0;
		artist_name = "not set";
		genre = "not set";
		track_title= "not set";
		album_title = "not_set";
		year_created = 0;
		artist_longitude = 0.0;
	}
	
	public Song(int id, String artist, String g, String track, String album, int year, double lon) {
		track_id = id;
		artist_name = artist;
		genre = g;
		track_title= track;
		album_title = album;
		year_created = year;
		artist_longitude = lon;
	}
	
	public int getTrackId() {
		return track_id;
	}
	public String getArtist() {
		return artist_name;
	}
	public String getGenre() {
		return genre;
	}
	public String getTitle() {
		return track_title;
	}
	public String getAlbum() {
		return album_title;
	}
	public int getYear() {
		return year_created;
	}
	public double getLon() {
		return artist_longitude;
	}
	
	public void setTrackId(int a) {
		track_id = a;
	}
	public void setArtist(String a) {
		artist_name = a;
	}
	public void setGenre(String a) {
		genre = a;
	}
	public void setTitle(String a) {
		track_title = a;
	}
	public void setAlbum(String a) {
		album_title = a;
	}
	public void setYear(int a) {
		year_created = a;
	}
	public void setLon(double a) {
		artist_longitude = a;
	}
	
	public String toString() {
		String toReturn = "";
		toReturn += "Track Id: " + track_id +"\n";
		toReturn += "Artist: " + artist_name +"\n";
		toReturn += "Genre: " + genre +"\n";
		toReturn += "Track: " + track_title +"\n";
		toReturn += "Album: " + album_title +"\n";
		toReturn += "Year: " + year_created +"\n";
		toReturn += "Artist Longitude: " + artist_longitude +"\n";
		
		return toReturn;
	}
}
