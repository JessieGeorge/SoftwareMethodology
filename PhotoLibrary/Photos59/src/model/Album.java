package model;

import java.io.*;
import java.util.*;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for Album
 */
public class Album implements Serializable {
	
	/**
	 * the name of the album
	 */
	public String name;
	
	/**
	 * the list of photos in the album
	 */
	public List<Photo> photos;
	
	/**
	 * Constructor for album
	 * @param name the name of the album
	 */
	public Album(String name) {
		
		this.name = name;
		photos = new ArrayList<Photo>();
	}
	
	/**
	 * Get the date of a calendar object in MM/DD/YYYY HH:MM:SS string format
	 * @param date the given calendar object
	 * @return string representation of the date
	 */
	public String calendarToString(Calendar date) {
		
		String result = date.get(Calendar.MONTH) + 1 + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR) 
		+ " " + date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND);
		
		return result;
	}
	
	/**
	 * Get the date of the earliest photo in the album
	 * @return string representation of the earliest date
	 */
	public String getEarliestDate() {
		
		if(photos.size() == 0) {
			return "-";
		}
		
		Calendar earliestDate = photos.get(0).date;
		
		for(int i = 1; i < photos.size(); i++) {
			
			if(photos.get(i).date.before(earliestDate)) {
				earliestDate = photos.get(i).date;
			}
		}
		
		return calendarToString(earliestDate);
	}
	
	/**
	 * Get the date of the latest photo in the album
	 * @return string representation of the latest date
	 */
	public String getLatestDate() {
		
		if(photos.size() == 0) {
			return "-";
		}
		
		Calendar latestDate = photos.get(0).date;
		
		for(int i = 1; i < photos.size(); i++) {
			
			if(photos.get(i).date.after(latestDate)) {
				latestDate = photos.get(i).date;
			}
		}
		
		return calendarToString(latestDate);
	}
		
	/**
	 * Check to see if caption is valid
	 * @param caption the caption string being tested for validity
	 * @return true if the string is a valid caption
	 */
	public static boolean isValidCaption(String caption) {
		
		if(caption.length() > Constant.MAX_LENGTH) {
			return false;
		}
		
		for(int i = 0; i < caption.length(); i++) {
			
			if( !Character.isLetterOrDigit(caption.charAt(i)) && caption.charAt(i) != ' ' ) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Check to see if number of photos in album is at capacity
	 * @return true if at capacity
	 */
	public boolean photosOverLimit() {
		
		if(photos.size() == Constant.MAX_PHOTOS) {
			return true;
		}
		else {
			return false;
		}
	}
		
	/**
	 * Add a new photo to the album
	 * @param fileLocation the file location of the photo
	 * @param caption the caption of the photo
	 * @return true if the photo is added
	 */
	public boolean addPhoto(String fileLocation, String caption) {
		
		Photo myPhoto = new Photo(caption, fileLocation);
		
		photos.add(myPhoto);
		
		return true;
	}
	
	/**
	 * Check to see if the file location is valid, referring to a photo on the computer
	 * @param fileLocation the file location being tested
	 * @return true if the file location is valid
	 */
	public static boolean isValidFileLocation(String fileLocation) {
		
		File file = new File(fileLocation);
		
		if(!file.exists() || file.isDirectory()) {
			return false;
		}
		
		String lastThreeChars = fileLocation.substring(fileLocation.length() - 3);
		String lastFourChars = fileLocation.substring(fileLocation.length() - 4);
		
		if(!lastThreeChars.equalsIgnoreCase("png") && !lastThreeChars.equalsIgnoreCase("bmp") &&
			!lastThreeChars.equalsIgnoreCase("jpg") && !lastFourChars.equalsIgnoreCase("jpeg")) {
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get photo object for a given UUID object
	 * @param id the specified UUID object
	 * @return the photo object assigned to the UUID object
	 */
	public Photo getPhotoObject(UUID id) {
		
		for(int i = 0; i < photos.size(); i++) {

			if(photos.get(i).id.equals(id)) {
				return photos.get(i);
			}
		}

		return null; // shouldn't reach here
	}

	
	/**
	 * Check to see if album name is valid
	 * @param albumName the string being tested for validity
	 * @return true if the string is a valid album name
	 */
	public boolean removePhoto(UUID id) {
		
		for(int i = 0; i < photos.size(); i++) {
			
			if(photos.get(i).id.equals(id)) {
				
				photos.remove(i);
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Sort the list of photos by caption in lexicographical order
	 */
	public void sortPhotosByCaption() {
		
		Collections.sort(photos, new Comparator<Photo>(){
			public int compare(Photo p1, Photo p2){
				return p1.caption.compareToIgnoreCase(p2.caption);
			}
		});
	}
	
	/**
	 * Sort the list of photos by date in ascending order
	 */
	public void sortPhotosByDate() {
		
		Collections.sort(photos, new Comparator<Photo>(){
			public int compare(Photo p1, Photo p2){
				return p1.date.compareTo(p2.date);
			}
		});
	}
}



















