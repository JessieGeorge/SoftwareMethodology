package model;

import java.io.*;
import java.util.*;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for User
 */
public class User implements Serializable {
	
	/**
	 * isAdmin is true if user type is admin
	 */
	public boolean isAdmin;
	
	/**
	 * isStock is true if user type is stock
	 */
	public boolean isStock;
	
	/**
	 * the username of the user
	 */
	public String username;
	
	/**
	 * list of all the user's albums
	 */
	public List<Album> albums;
	
	/**
	 * Constructor for user
	 * @param username the user's username
	 */
	public User(String username) {
		
		if(username.equalsIgnoreCase("admin")) {
			isAdmin = true;
			isStock = false;
		}
		else if(username.equalsIgnoreCase("stock")) {
			isAdmin = false;
			isStock = true;
		}
		else {
			isAdmin = false;
			isStock = false;
		}
		
		this.username = username;
		albums = new ArrayList<Album>();
	}

	/**
	 * returns all albums except the selected album and stock album in string format
	 * @param exceptMe the selected album
	 * @return string names of all albums other than the selected one
	 */
	public List<String> getAllAlbumsStr(String exceptMe) {
		ArrayList<String> allAlbumsStr = new ArrayList<String>();

		for (int i = 0; i < albums.size(); i++) {
			String s = albums.get(i).name;
			if(s.equalsIgnoreCase(exceptMe)||s.equalsIgnoreCase("stock"))
				continue;
			
			allAlbumsStr.add(s);
		}

		return allAlbumsStr;
	}
		
	/**
	 * Check to see if album name is valid
	 * @param albumName the string being tested for validity
	 * @return true if the string is a valid album name
	 */
	public boolean isValidAlbum(String albumName) {
		
		if(albumName.length() > Constant.MAX_LENGTH) {
			return false;
		}
		
		for(int i = 0; i < albumName.length(); i++) {
			
			if( !Character.isLetterOrDigit(albumName.charAt(i)) && albumName.charAt(i) != ' ' ) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Check to see if album name already exists in application
	 * @param albumName the string being tested for existence
	 * @return true if the string already exists
	 */
	public boolean albumNameExists(String albumName) {
		
		for(int i = 0; i < albums.size(); i++) {
			
			if(albums.get(i).name.equalsIgnoreCase(albumName)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check to see if the number of albums for user is at capacity
	 * @return true if at capacity
	 */
	public boolean albumsOverLimit() {
		
		if(albums.size() == Constant.MAX_ALBUMS) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Create a new album with the given album name
	 * @param albumName the given album name string
	 * @return true if album is created
	 */
	public boolean createAlbum(String albumName) {
		
		/*
		if(albums.size() >= Constant.MAX_ALBUMS) {
			return false;
		}
		
		if(!isValidAlbum(albumName)) {
			return false;
		}
		
		if(albumNameExists(albumName)) {
			return false;
		}
		*/
		
		Album myAlbum = new Album(albumName);
		
		albums.add(myAlbum);
		
		return true;
	}
	
	/**
	 * Get the album object for a given album name
	 * @param albumName the given album name
	 * @return the album object for the album name string
	 */
	public Album getAlbumObject(String albumName)
	{
		for(int i = 0; i < albums.size(); i++) 
		{
			
			if(albums.get(i).name.equalsIgnoreCase(albumName)) 
			{
				return albums.get(i);
			}
		}
		
		return null; //shouldn't reach here
	}
	
	/**
	 * Delete the album for the given album name string
	 * @param albumName the given album name string
	 * @return true if the album is deleted
	 */
	public boolean deleteAlbum(String albumName) {
		
		for(int i = 0; i < albums.size(); i++) {
			
			if(albums.get(i).name.equalsIgnoreCase(albumName)) {
				
				albums.remove(i);
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Rename the album
	 * @param oldAlbumName the album name of the album being renamed
	 * @param newAlbumName the new album name
	 * @return true if it is renamed
	 */
	public boolean renameAlbum(String oldAlbumName, String newAlbumName) {
		
		if(!isValidAlbum(newAlbumName)) {
			return false;
		}
		
		for(int i = 0; i < albums.size(); i++) {
			
			if(albums.get(i).name.equalsIgnoreCase(oldAlbumName)) {
				
				albums.get(i).name = newAlbumName;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Search all photos for those that contain given tags
	 * @param stringTags the tags being searched on in string form
	 * @return a list of photos containing all the specified tags
	 */
	public List<Photo> searchByTags(List<String> stringTags) {
		
		List<Photo> result = new ArrayList<Photo>();
		
		Map<Photo, Integer> map = new HashMap<Photo, Integer>();
		
		for(int i = 0; i < stringTags.size(); i++) {
			
			String stringTagName = stringTags.get(i).substring(0, stringTags.get(i).indexOf('=')-1);
			String stringTagValue = stringTags.get(i).substring(stringTags.get(i).indexOf('=')+2);
			
			for(int j = 0; j < albums.size(); j++) {
				
				for(int k = 0; k < albums.get(j).photos.size(); k++) {
					
					for(int l = 0; l < albums.get(j).photos.get(k).tags.size(); l++) {
						
						String testTagName = albums.get(j).photos.get(k).tags.get(l).tagName;
						String testTagValue = albums.get(j).photos.get(k).tags.get(l).tagValue;
						
						if(testTagName.equalsIgnoreCase(stringTagName) && testTagValue.equalsIgnoreCase(stringTagValue))
						{
							
							Photo key = albums.get(j).photos.get(k);
							Integer value = map.get(key);
							
							if(value == null) {
								map.put(key, 1);
							}
							else {
								map.put(key, value + 1);
							}
							
							break;
						}
					}
				}
			}
		}
		
		for(Map.Entry<Photo, Integer> entry : map.entrySet()) {
			
			Photo key = entry.getKey();
			Integer value = entry.getValue();
			
			if(value == stringTags.size()) {
				
				result.add(key);
			}
		}
		
		return result;
	}
	
	/**
	 * Search all photos for those that fall in the given date range
	 * @param start the starting date range
	 * @param end the ending date range
	 * @return a list of photos that fall in the specified date range
	 */
	public List<Photo> searchByDateRange(String start, String end) {
		
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		startDate.set(Integer.parseInt(start.substring(6)), Integer.parseInt(start.substring(0, 2)) - 1, Integer.parseInt(start.substring(3,5)), 0, 0, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		
		endDate.set(Integer.parseInt(end.substring(6)), Integer.parseInt(end.substring(0, 2)) - 1, Integer.parseInt(end.substring(3,5)), 23, 59, 59);
		endDate.set(Calendar.MILLISECOND, 0);
		
		List<Photo> result = new ArrayList<Photo>();
		
		for(int i = 0; i < albums.size(); i++) {
			
			for(int j = 0; j < albums.get(i).photos.size(); j++) {
				
				Photo testPhoto = albums.get(i).photos.get(j);
				
				if(testPhoto.date.compareTo(startDate) >= 0 && testPhoto.date.compareTo(endDate) <= 0) {
					
					result.add(testPhoto);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Search all photos for those that have a given caption
	 * @param caption the caption being searched on
	 * @return a list of photos containing the specified caption
	 */
	public List<Photo> searchByCaption(String caption) {
		
		List<Photo> result = new ArrayList<Photo>();
		
		for(int i = 0; i < albums.size(); i++) {
			
			for(int j = 0; j < albums.get(i).photos.size(); j++) {
				
				Photo testPhoto = albums.get(i).photos.get(j);
				
				if(testPhoto.caption.equalsIgnoreCase(caption)) {
					
					result.add(testPhoto);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Search all photos for those that have a given file location
	 * @param fileLocation the file location being searched on
	 * @return a list of photos with the specified file location
	 */
	public List<Photo> searchByFileLocation(String fileLocation) {
		
		List<Photo> result = new ArrayList<Photo>();
		
		for(int i = 0; i < albums.size(); i++) {
			
			for(int j = 0; j < albums.get(i).photos.size(); j++) {
				
				Photo testPhoto = albums.get(i).photos.get(j);
				
				if(testPhoto.fileLocation.equalsIgnoreCase(fileLocation)) {
					
					result.add(testPhoto);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Creates an album and adds all photos from a given list to the album
	 * @param myPhotos the list of photos being added to the album
	 * @param albumName the name of the album being created
	 * @return true if the album is created
	 */
	public boolean createAlbumFromSearch(List<Photo> myPhotos, String albumName) {
		
		createAlbum(albumName);
		
		for(int i = 0; i < albums.size(); i++) {
			
			if(albums.get(i).name.equalsIgnoreCase(albumName)) {
				
				for(int j = 0; j < myPhotos.size(); j++) {
					
					Photo myPhoto = new Photo(myPhotos.get(j).caption, myPhotos.get(j).tags, myPhotos.get(j).fileLocation);
					albums.get(i).photos.add(myPhoto);
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Get the name of the album that a photo exists in
	 * @param myPhoto the photo whose album is being located
	 * @return string album name containing specified photo
	 */
	public String getAlbumPhotoExistsIn(Photo myPhoto) {
		
		for(int i = 0; i < albums.size(); i++) {
			
			for(int j = 0; j < albums.get(i).photos.size(); j++) {
				
				if(albums.get(i).photos.get(j).id.equals(myPhoto.id)) {
					
					return albums.get(i).name;
				}
			}
		}
		
		return null;
	}
}



















