package controller;

import application.Photos;
import model.*;

import java.io.File;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
 * DisplayPhotoController to broker between front-end and back-end for display photo
 */
public class DisplayPhotoController extends Application
{
	/**
	 * rootPane is the root pane on which every UI element is put
	 */
	@FXML
	protected AnchorPane rootPane;
	
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
	Photo selectedPhoto;
	
	/**
	 * selectedAlbum is the album the user clicked on 
	 */
	Album selectedAlbum;
	
	/**
	 * start is the method to start this part of the application
	 * @param stage is the stage for application
	 */
	@Override
	public void start(Stage stage)
	{
		//System.out.println("inside display start method"); //REMOVE
		
		selectedAlbum = UserHomeController.getSelectedAlbum();
		selectedPhoto = AlbumController.getSelectedPhoto();
		
		String s = "Album name:	"+selectedAlbum.name+"\nCaption:		"+selectedPhoto.caption+"\nDate-Time:	"+selectedPhoto.getDate();
		photoInfo.setText(s);
		
		obsList = FXCollections.observableArrayList(selectedPhoto.getAllTagsStr());
		listOfTags.setItems(obsList);
		
		//I'm creating a file I hope that's okay!
		File file = new File(selectedPhoto.fileLocation);
	    Image image = new Image(file.toURI().toString(), 463, 307, true, false);
		
	    photo.setImage(image);
	    
	}
}
