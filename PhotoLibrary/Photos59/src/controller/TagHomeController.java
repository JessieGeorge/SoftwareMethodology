package controller;

import application.Photos;
import model.Album;
import model.Constant;
import model.Photo;
import model.UserList;

import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 * TagHomeController to broker between front-end and back-end for tag home
 */
public class TagHomeController extends Application
{
	/**
	 * tagHeader is the header which tells the user which photo they are tagging
	 */
	@FXML
	protected Text tagHeader;
	
	/**
	 * limitStatement is the statement that tells the user the max number of tags they are allowed for this photo
	 */
	@FXML
	protected Text limitStatement;
	
	/**
	 * listOfTags is the list view of all tags for this photo
	 */
	@FXML
	protected ListView<String> listOfTags;
	
	/**
	 * obsList is the observable list that is tied to the listOfTags list view
	 */
	protected static ObservableList<String> obsList;
	
	/**
	 * selectedPhoto is the photo that the user clicked on
	 */
	protected static Photo selectedPhoto;
	
	/**
	 * selectedAlbum is the album that the user clicked on
	 */
	protected static Album selectedAlbum;
	
	/**
	 * selectedTag is the tag that the user clicked on
	 */
	protected static String selectedTag;
	
	/**
	 * clickedBack is the method called when user clicks Back button
	 * @param event the action event
	 */
	@FXML
	private void clickedBack(ActionEvent event)
	{
		//System.out.println("clicked back"); //REMOVE
		
		//go to album [STORYBOARD - 6]
		goToAlbum(event);
	}
	
	/**
	 * goToAlbum is the method to go to the album UI
	 * @param event is the action event
	 */
	private void goToAlbum(ActionEvent event)
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Album.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			AlbumController albumController = (AlbumController)loader.getController();
			albumController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Album"); //CHANGE THIS
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * clickedAddTag is the method called when user clicks AddTag button
	 * @param event the action event
	 */
	@FXML
	private void clickedAddTag(ActionEvent event)
	{
		//System.out.println("clicked add tag"); //REMOVE
		
		//check over limit [STORYBOARD - E5]
		if(selectedPhoto.tagsOverLimit())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s = "You cannot add another tag to this photo. Maximum number of tags = "+Constant.MAX_TAGS+". If you want to add a new tag please delete another tag to make space.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//go to add tag [STORYBOARD - 10]
		goToAddTag(event);
		
		/*not needed?
		//update UI
		obsList = FXCollections.observableArrayList(selectedPhoto.getAllTagsStr());
		listOfTags.setItems(obsList);
		*/
	}
	
	/**
	 * goToAddTag is the method to go to the AddTag UI
	 * @param event is the action event
	 */
	private void goToAddTag(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AddTag.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			AddTagController addTagController = (AddTagController)loader.getController();
			addTagController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Add Tag");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getSelectedTag is to get the selected tag
	 * @return the selected tag as a string
	 */
	public static String getSelectedTag()
	{
		return selectedTag;
	}
	
	/**
	 * clickedEditTag is the method called when user clicks EditTag button
	 * @param event the action event
	 */
	@FXML
	private void clickedEditTag(ActionEvent event) 
	{
		//System.out.println("clicked edit tag"); //REMOVE

		// no tag to edit [STORYBOARD - E4]
		if(selectedPhoto.tags.size()==0)
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s = "No tag exists to edit.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}

		//can't delete because you haven't selected anything [STORYBOARD - E4]
		String tagToEdit = listOfTags.getSelectionModel().getSelectedItem();
		selectedTag = tagToEdit;
		//System.out.println(tagToEdit); //REMOVE

		if (tagToEdit == null) {
			Alert alert = new Alert(AlertType.ERROR);
			String s = "Please select a tag to edit.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//go to edit tag [STORYBOARD - 12]
		goToEditTag(event);
		
		/*not needed?
		//update UI
		obsList = FXCollections.observableArrayList(selectedPhoto.getAllTagsStr());
		listOfTags.setItems(obsList);
		*/
	}
	
	/**
	 * goToEditTag is the method to go to the EditTag UI
	 * @param event is the action event
	 */
	private void goToEditTag(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/EditTag.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			EditTagController editTagController = (EditTagController)loader.getController();
			editTagController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Edit Tag");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * clickedDeleteTag is the method called when user clicks DeleteTag button
	 * @param event the action event
	 */
	@FXML
	private void clickedDeleteTag(ActionEvent event) 
	{
		//System.out.println("clicked delete tag"); // REMOVE

		// no tag to delete [STORYBOARD - E4]
		if (selectedPhoto.tags.size() == 0) 
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s = "No tag exists to delete.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		// can't delete because you haven't selected anything [STORYBOARD - E4]
		String tagToDelete = listOfTags.getSelectionModel().getSelectedItem();
		//System.out.println(tagToDelete); // REMOVE
		
		if (tagToDelete == null) {
			Alert alert = new Alert(AlertType.ERROR);
			String s = "Please select a tag to delete.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}

		// delete confirmation [STORYBOARD - DC]
		Alert alert = new Alert(AlertType.CONFIRMATION);
		String s = "Are you sure you want to delete tag " + tagToDelete + "? \nThis decision is irreversible.";
		alert.setContentText(s);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// ... user chose OK
			//System.out.println("OK"); // REMOVE

			String tagName = tagToDelete.substring(0,tagToDelete.indexOf('=')-1);
			String tagValue = tagToDelete.substring(tagToDelete.indexOf('=')+2);
			
			selectedPhoto.deleteTag(tagName, tagValue);
			obsList = FXCollections.observableArrayList(selectedPhoto.getAllTagsStr());
			listOfTags.setItems(obsList);

		} else {
			// ... user chose CANCEL or closed the dialog
			//System.out.println("CANCEL OR X"); // REMOVE

			// do nothing.
		}
	}
	
	/**
	 * start is the method to start this part of the application
	 * @param stage is the stage for application
	 */
	@Override
	public void start(Stage stage)
	{
		//System.out.println("Hello i'm in tag home controller start"); //REMOVE
		
		selectedAlbum = UserHomeController.getSelectedAlbum();
		selectedPhoto = AlbumController.getSelectedPhoto();
		
		String h = "Tags for photo "+selectedPhoto.caption+" in album "+selectedAlbum.name;
		tagHeader.setText(h);
		
		String s = "Note: You can't have more than "+Constant.MAX_TAGS+" tags for this photo";
		limitStatement.setText(s);
		
		obsList = FXCollections.observableArrayList(selectedPhoto.getAllTagsStr());
		listOfTags.setItems(obsList);
		
		Photos.needQuitConfirmation = false;
		Platform.setImplicitExit(true);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  //do nothing ... no quit confirmation
	          }
	      });
	}

}
