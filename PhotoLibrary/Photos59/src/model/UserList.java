package model;

import java.io.*;
import java.util.*;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for UserList
 */
public class UserList implements Serializable {
	
	/**
	 * list of all user objects
	 */
	public static List<User> users = new ArrayList<User>();
	
	/**
	 * current active user
	 */
	public static User currentUser;
	
	/**
	 * list of all users' usernames in strings
	 */
	public static List<String> allUsernames;
	
	/**
	 * method to initialize the users and usernames upon starting the application
	 */
	public static void initializeUserList() {
		
		if(allUsernames == null)
			allUsernames = new ArrayList<String>();
		
		if(!usernameExists("admin"))
			addUser("admin");
		
		if(!usernameExists("stock"))
			addUser("stock");
	}
	
	/**
	 * Check to see if the username string meets the conditions for a valid username
	 * @param username the string being tested to see if it is a valid username
	 * @return true if it is a valid username
	 */
	public static boolean isValidUsername(String username) {
		
		if(username.length() > Constant.MAX_LENGTH) {
			return false;
		}
		
		for(int i = 0; i < username.length(); i++) {
			
			if( !Character.isLetterOrDigit(username.charAt(i)) && username.charAt(i) != ' ' ) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Check to see if the username string already exists in the application
	 * @param username the string being tested to see if it already exists
	 * @return true if it already exists
	 */
	public static boolean usernameExists(String username) {
		
		for(int i = 0; i < users.size(); i++) {
			
			if(users.get(i).username.equalsIgnoreCase(username)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Attempt to login under some username string
	 * @param username the string that is being logged in under
	 * @return true if username login is successful
	 */
	public static boolean login(String username) {
		
		for(int i = 0; i < users.size(); i++) {
			
			if(users.get(i).username.equalsIgnoreCase(username)) {
				currentUser = users.get(i);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check to see if the number of users in the application is at capacity
	 * @return true if no more users can be added
	 */
	public static boolean usersOverLimit()
	{
		if(users.size() == Constant.MAX_USERS)
			return true;

		else
			return false;
	}
	
	/**
	 * Add new user to the application for given username string
	 * @param username the new username being registed
	 * @return true if username is added
	 */
	public static boolean addUser(String username) {
		
		User myUser = new User(username);
		
		users.add(myUser);
		allUsernames.add(username);
		
		return true;
	}
	
	/**
	 * Delete user from the application for given username string
	 * @param username the username being deleted
	 * @return true if username is deleted
	 */
	public static boolean deleteUser(String username) {
		
		for(int i = 0; i < users.size(); i++) {
			
			if(users.get(i).username.equalsIgnoreCase(username)) {
				
				users.remove(i);
				allUsernames.remove(i);
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Writes user objects to text file - serialization
	 */
	public static void writeToFile() throws Exception {
		
		try {
			
			FileOutputStream fos = new FileOutputStream("Users.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
			oos.close();
		}
		catch(Exception e) {
			
		}
	}
	
	/**
	 * Writes username strings to text file - serialization
	 */
	public static void writeToFile2() throws Exception {
		
		try {
			
			FileOutputStream fos = new FileOutputStream("Usernames.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(allUsernames);
			oos.close();
		}
		catch(Exception e) {
			
		}
	}
	
	/**
	 * Reads user objects from text file - serialization
	 */
	public static void readFromFile() throws Exception {
		
		try {
			
			FileInputStream fis = new FileInputStream("Users.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			users = (ArrayList<User>) ois.readObject();
			ois.close();
		}
		catch(Exception e) {
			
			
		}
	}
	
	/**
	 * Reads username strings from text file - serialization
	 */
	public static void readFromFile2() throws Exception {
		
		try {
			
			FileInputStream fis = new FileInputStream("Usernames.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			allUsernames = (ArrayList<String>) ois.readObject();
			ois.close();
		}
		catch(Exception e) {
			
			
		}
	}
}



















