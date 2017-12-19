package model;

import java.io.*;
import java.util.*;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for Photo
 */
public class Photo implements Serializable {
	
	/**
	 * universally unique identifier
	 */
	public UUID id;
	
	/**
	 * the caption of the photo
	 */
	public String caption;
	
	/**
	 * the date-time of the photo
	 */
	public Calendar date;
	
	/**
	 * the list of tags attached to this photo
	 */
	public List<Tag> tags;
	
	/**
	 * the file location of the photo
	 */
	public String fileLocation;
	
	/**
	 * Constructor for photo
	 * @param caption the photo's caption
	 * @param tags the photo's tags
	 * @param fileLocation the photo's file location
	 */
	public Photo(String caption, List<Tag> tags, String fileLocation) {
		
		id = UUID.randomUUID();
		this.caption = caption;
		date = Calendar.getInstance();
		date.set(Calendar.MILLISECOND, 0);
		this.tags = tags;
		this.fileLocation = fileLocation;
	}
	
	/**
	 * Constructor for photo
	 * @param caption the photo's caption
	 * @param fileLocation the photo's file location
	 */
	public Photo(String caption, String fileLocation) {
		
		id = UUID.randomUUID();
		this.caption = caption;
		date = Calendar.getInstance();
		date.set(Calendar.MILLISECOND, 0);
		tags = new ArrayList<Tag>();
		this.fileLocation = fileLocation;
	}
	
	/**
	 * Get the date of the photo in MM/DD/YYYY HH:MM:SS string format
	 * @return string representation of the date
	 */
	public String getDate() {
		
		String result = date.get(Calendar.MONTH) + 1 + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR) 
		+ " " + date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND);
		
		return result;
	}
	
	/**
	 * Get the string representation of all tag objects
	 * @return list of strings for the tags
	 */
	public List<String> getAllTagsStr()
	{
		ArrayList<String> allTagsStr = new ArrayList<String>();
		
		for(int i=0; i<tags.size(); i++)
		{
			String s = tags.get(i).tagName+" = "+tags.get(i).tagValue;
			allTagsStr.add(s);
		}
		
		return allTagsStr;
	}
	
	/**
	 * Check if the tag already exists in the photo's list of tags
	 * @param tagName the string tag name
	 * @param tagValue the string tag value
	 * @return true if it already exists
	 */
	public boolean tagExists(String tagName, String tagValue) {
		
		for(int i = 0; i < tags.size(); i++) {
			
			if( tags.get(i).tagName.equalsIgnoreCase(tagName) && tags.get(i).tagValue.equalsIgnoreCase(tagValue) ) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Check if the string tag is valid
	 * @param tagValue the tag being tested for validity
	 * @return true if the string is a valid tag
	 */
	public boolean isValidTagValue(String tagValue) {

		if (tagValue.length() > Constant.MAX_LENGTH) {
			return false;
		}

		for (int i = 0; i < tagValue.length(); i++) {

			if (!Character.isLetterOrDigit(tagValue.charAt(i)) && tagValue.charAt(i) != ' ') {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check if the number of tags attached to this photo is at capacity
	 * @return true if at capacity
	 */
	public boolean tagsOverLimit() {
		if (tags.size() == Constant.MAX_TAGS)
			return true;

		else
			return false;
	}
		
	/**
	 * Add a tag to this photo
	 * @param tagName the string tag name
	 * @param tagValue the string tag value
	 * @return true if the tag is added
	 */
	public boolean addTag(String tagName, String tagValue) {
		
		Tag myTag = new Tag(tagName, tagValue);
		tags.add(myTag);
		myTag.photos.add(this);
		
		return true;
	}
	
	/**
	 * Edit a tag attached to this photo
	 * @param oldTagName the tag name being edited
	 * @param oldTagValue the tag value being edited
	 * @param newTagName the new name of the tag
	 * @param newTagValue the new value of the tag
	 * @return if the tag is edited
	 */
	public boolean editTag(String oldTagName, String oldTagValue, String newTagName, String newTagValue) {
		
		for(int i = 0; i < tags.size(); i++) {
			
			if( tags.get(i).tagName.equalsIgnoreCase(oldTagName) && tags.get(i).tagValue.equalsIgnoreCase(oldTagValue) ) {
				
				tags.get(i).tagName = newTagName;
				tags.get(i).tagValue = newTagValue;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Delete a tag from this photo
	 * @param tagName the name of the tag being deleted
	 * @param tagValue the value of the tag being deleted
	 * @return true if tag is deleted
	 */
	public boolean deleteTag(String tagName, String tagValue) {
		
		for(int i = 0; i < tags.size(); i++) {
			
			if( tags.get(i).tagName.equalsIgnoreCase(tagName) && tags.get(i).tagValue.equalsIgnoreCase(tagValue) ) {
				
				tags.get(i).removePhotoFromTagList(this);
				tags.remove(i);
				return true;
			}
		}
		
		return false;
	}
		
	/**
	 * Update the caption of this photo
	 * @param caption the new caption
	 * @return true if caption is updated
	 */
	public boolean updateCaption(String caption) {
		
		this.caption = caption;
		
		return true;
	}
	
	/**
	 * Copy this photo to another album
	 * @param toAlbumName the name of the album this photo is being copied to
	 * @return true if photo is copied
	 */
	public boolean copyPhoto(String toAlbumName) {
		
		for(int i = 0; i < UserList.currentUser.albums.size(); i++) {
			
			if(UserList.currentUser.albums.get(i).name.equalsIgnoreCase(toAlbumName)) {
				
				Photo myPhoto = new Photo(this.caption, this.tags, this.fileLocation);
				UserList.currentUser.albums.get(i).photos.add(myPhoto);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Move this photo to another album
	 * @param fromAlbumName the name of the album this photo is being moved from
	 * @param toAlbumName the name of the album this photo is being moved to
	 * @return true if photo is moved
	 */
	public boolean movePhoto(String fromAlbumName, String toAlbumName) {
		
		for(int i = 0; i < UserList.currentUser.albums.size(); i++) {
			
			if(UserList.currentUser.albums.get(i).name.equalsIgnoreCase(fromAlbumName)) {
				
				for(int j = 0; j < UserList.currentUser.albums.get(i).photos.size(); j++) {
					
					if(UserList.currentUser.albums.get(i).photos.get(j).id.equals(this.id)) {
						
						UserList.currentUser.albums.get(i).photos.remove(j);
					}
				}
			}
		}
		
		for(int i = 0; i < UserList.currentUser.albums.size(); i++) {
			
			if(UserList.currentUser.albums.get(i).name.equalsIgnoreCase(toAlbumName)) {
				
				UserList.currentUser.albums.get(i).photos.add(this);
				return true;
			}
		}
		
		return false;
	}
}



















