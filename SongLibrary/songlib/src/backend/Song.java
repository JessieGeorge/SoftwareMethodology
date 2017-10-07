/**
 * @author Jessie George
 * @author Karl Xu
 */

package backend;
 
public class Song {
	
	/*
	 * Fields
	 */
	public String name;
	public String artist;
	public String album;
	public String year;
	
	/*
	 * Constructors
	 */
	public Song(String name, String artist, String album, String year) {
		
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	/*
	 * Methods
	 */
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	public String getArtist() {
		
		return artist;
	}
	
	public void setArtist(String artist) {
		
		this.artist = artist;
	}
	
	public String getAlbum() {
		
		return album;
	}
	
	public void setAlbum(String album) {
		
		this.album = album;
	}
	
	public String getYear() {
		
		return year;
	}
	
	public void setYear(String year) {
		
		this.year = year;
	}
}