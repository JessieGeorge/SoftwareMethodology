package controller;

import application.Photos;
import model.Album;
import model.Constant;
import model.Photo;
import model.UserList;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 * SlideshowController to broker between front-end and back-end for slideshow
 */
public class SlideshowForSearchController extends Application
{
	//NOTE: WHEN YOU DO THIS FOR SEARCH IT MAY BE MORE COMPLICATED BECAUSE THERE IS NO SELECTED ALBUM
	
	/**
	 * photo is the image view to display photo
	 */
	@FXML
	protected ImageView photo;
	
	/**
	 * photoInfo is the info for photo like album name, photo caption, date-time and tags
	 */
	@FXML
	protected Text photoInfo;
	
	/**
	 * listOfTags is the list view for tags
	 */
	@FXML
	protected ListView listOfTags;
	
	/**
	 * obsList is the observable list tied to the listOfTags list view
	 */
	protected static ObservableList<String> obsList;
	
	/**
	 * selectedPhoto is the photo the user clicked on
	 */
	protected static Photo selectedPhoto;
	
	/**
	 * selectedAlbumName is the name of the album for the photo being displayed now
	 */
	protected static String selectedAlbumName;
	
	/**
	 * position is the position in the album at which we are displaying (i.e we will move forward or backward from position during slideshow)
	 */
	protected static int position;
	
	/**
	 * searchResults are the search results
	 */
	protected static List<Photo> searchResults;
	
	/**
	 * clickedBackwardArrow is the method called when user clicks BackwardArrow button
	 * @param event the action event
	 */
	@FXML
	private void clickedBackwardArrow(ActionEvent event)
	{
		//System.out.println("clicked backward arrow"); //REMOVE
		
		position--;
		
		if(position==-1)
			position = searchResults.size()-1; //loop to back
		
		selectedPhoto = searchResults.get(position);
		selectedAlbumName = UserList.currentUser.getAlbumPhotoExistsIn(selectedPhoto);
		
		String s = "Album name:	"+selectedAlbumName+"\nCaption:		"+selectedPhoto.caption+"\nDate-Time:	"+selectedPhoto.getDate();
		photoInfo.setText(s);
		
		obsList = FXCollections.observableArrayList(selectedPhoto.getAllTagsStr());
		listOfTags.setItems(obsList);
		
		//I'm creating a file I hope that's okay!
		File file = new File(selectedPhoto.fileLocation);
	    Image image = new Image(file.toURI().toString(), 463, 307, true, false);
		
	    photo.setImage(image);
	}
	
	/**
	 * clickedForwardArrow is the method called when user clicks ForwardArrow button
	 * @param event the action event
	 */
	@FXML
	private void clickedForwardArrow(ActionEvent event)
	{
		//System.out.println("clicked forward arrow"); //REMOVE
		
		position++;
		
		if(position == searchResults.size())
			position = 0; //loop to front
		
		selectedPhoto = searchResults.get(position);
		selectedAlbumName = UserList.currentUser.getAlbumPhotoExistsIn(selectedPhoto);
		
		String s = "Album name:	"+selectedAlbumName+"\nCaption:		"+selectedPhoto.caption+"\nDate-Time:	"+selectedPhoto.getDate();
		photoInfo.setText(s);
		
		obsList = FXCollections.observableArrayList(selectedPhoto.getAllTagsStr());
		listOfTags.setItems(obsList);
		
		//I'm creating a file I hope that's okay!
		File file = new File(selectedPhoto.fileLocation);
	    Image image = new Image(file.toURI().toString(), 463, 307, true, false);
		
	    photo.setImage(image);
	}
	
	/**
	 * start is the method to start this part of the application
	 * @param stage is the stage for application
	 */
	@Override
	public void start(Stage stage)
	{
		//System.out.println("inside display start method"); //REMOVE
		
		position = 0; //start with first position
		
		searchResults = SearchResultsController.getSearchResults(); 
		selectedPhoto = searchResults.get(position);
		selectedAlbumName = UserList.currentUser.getAlbumPhotoExistsIn(selectedPhoto);
		
		String s = "Album name:	"+selectedAlbumName+"\nCaption:		"+selectedPhoto.caption+"\nDate-Time:	"+selectedPhoto.getDate();
		photoInfo.setText(s);
		
		obsList = FXCollections.observableArrayList(selectedPhoto.getAllTagsStr());
		listOfTags.setItems(obsList);
		
		//I'm creating a file I hope that's okay!
		File file = new File(selectedPhoto.fileLocation);
	    Image image = new Image(file.toURI().toString(), 463, 307, true, false);
		
	    photo.setImage(image);
		

		/* don't need this because it's another window?
		Photos.needQuitConfirmation = false;
		Platform.setImplicitExit(true);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  //do nothing ... no quit confirmation
	          }
	      });
	    */
	}
}
