package model;

import java.io.*;
import java.util.*;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for Tag
 */
public class Tag implements Serializable {
	
	/**
	 * tag name
	 */
	public String tagName;
	
	/**
	 * tag value
	 */
	public String tagValue;
	
	/**
	 * list of photos using this tag
	 */
	public List<Photo> photos;
	
	/**
	 * Constructor for tag
	 * @param tagName tag name of this tag
	 * @param tagValue tag value of this tag
	 */
	public Tag(String tagName, String tagValue) {
		
		this.tagName = tagName;
		this.tagValue = tagValue;
		photos = new ArrayList<Photo>();
	}
	
	/**
	 * Remove a photo from list of photos using this tag
	 * @param photo the photo object being removed
	 * @return true if it is removed
	 */
	public boolean removePhotoFromTagList(Photo photo) {
		
		for(int i = 0; i < photos.size(); i++) {
			
			if(photos.get(i).id.equals(photo.id)) {
				
				photos.remove(i);
				return true;
			}
		}
		
		return false;
	}
}