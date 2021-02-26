/*
 * Name: Kathryn Villarreal
 * Class: OO_Software 9AM
 */

package OO_Project;

public class ProjectTester {

	public static void main(String[] args) {
		//Read in song csv
		Song_Collection songList = new Song_Collection("./OO_Project/finalTracks.csv");
		
		//One input One song
		//Data I want: 029526,8bit Betty,Chiptune;Chip Music,Blast Off!,too bleep to bloop,2010,0.0
		Song foundById = songList.findSong(29526);
		System.out.println(foundById);
		System.out.println("------------------------------------------------------");
		//Edit a song
		foundById.setYear(2021);
		System.out.println(foundById);
		System.out.println("------------------------------------------------------");
		
		//Multiple inputs One song
		//Data I want: 029526,8bit Betty,Chiptune;Chip Music,Blast Off!,too bleep to bloop,2021,0.0
		Song foundByATA = songList.findSong("8bit Betty", "Blast Off!", "too bleep to bloop");
		System.out.println(foundByATA);
		System.out.println("------------------------------------------------------");
		
		//One input Multiple songs
		// All songs with "Big Blood" as the artist
		Song_Collection bloody = songList.findArtist("Big Blood");
		System.out.println(bloody);
		System.out.println("------------------------------------------------------");
		
		//Multiple inputs Multiple songs
		// All songs with the genre "Ambient Electronic;Trip-Hop;Chill-out" in the year 2010
		Song_Collection chill = songList.genreAndYear("Ambient Electronic;Trip-Hop;Chill-out", 2010);
		System.out.println(chill);
		System.out.println("------------------------------------------------------");
		
		//Delete a song
		songList.removeSong (36245);
		//That id is in the chill collection so if I were to query from songlist 
		//through genreAndYear again it should have one less song in Song_Collection
		Song_Collection updatedChill = songList.genreAndYear("Ambient Electronic;Trip-Hop;Chill-out", 2010);
		System.out.println(updatedChill);
		System.out.println("------------------------------------------------------");
		
		//Write new song csv
		songList.writeFile("./OO_Project/testWrite.csv");
	}
}
