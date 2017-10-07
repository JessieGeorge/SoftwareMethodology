/**
 * @author Jessie George
 * @author Karl Xu
 */

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import java.util.*;
import backend.*;


public class SongLib extends Application 
{
	
	/*
	 * Fields
	 */
	public ArrayList<Song> songList;
	
	public static Stage primaryStage;
	
	/*
	 * Constructors
	 */
	public SongLib() {
		
		songList = new ArrayList<Song>();
	}
	
	/*
	 * Methods
	 */
	public void addSong(String name, String artist, String album, String year) {
		
		if(album.equals("")) 
		{
			album = "Unknown";
		}
		
		if(year.equals("")) 
		{
			year = "Unknown";
		}
		
		// add new song and sort song list by alphabetical order (name & artist)
		Song mySong = new Song(name, artist, album, year);
		songList.add(mySong);
		Collections.sort(songList, new SongComparator());
	}
	
	public void editSong(Song song, String name, String artist, String album, String year) {
		
		if(album.equals("")) 
		{
			album = "Unknown";
		}
		
		if(year.equals("")) 
		{
			year = "Unknown";
		}
		
		// edit song and sort song list by alphabetical order (name & artist)
		song.setName(name);
		song.setArtist(artist);
		song.setAlbum(album);
		song.setYear(year);
		Collections.sort(songList, new SongComparator());
	}
	
	public void deleteSong(Song song) {
		
		songList.remove(song);
	}
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/SongLibView.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		Controller controller = loader.getController();
		controller.start();
		
		Scene scene = new Scene(root, 1000, 600);

		primaryStage.setTitle("Song Library");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		try
		{
			Controller.readFromFile();
		}
		catch (Exception e)
		{
			System.out.println("Exception");
		}
		
		
		launch(args);
		
		
		try
		{
			Controller.writeToFile();
		}
		catch (Exception e)
		{
			System.out.println("Exception");
		}
		
	}
	
}