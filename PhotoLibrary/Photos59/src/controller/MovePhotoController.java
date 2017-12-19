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
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 * MovePhotoController to broker between front-end and back-end for move photo
 */
public class MovePhotoController extends Application
{
	/**
	 * movePhotoHeader is the header which tells the user which photo they are moving
	 */
	@FXML
	protected Text movePhotoHeader; 
	
	/**
	 * albumNamesComboBox is the combo box of all album names for a user
	 */
	@FXML
	protected ComboBox<String> albumNamesComboBox; 
	
	/**
	 * selectedAlbum is the album the user clicked on
	 */
	protected static Album selectedAlbum;
	
	/**
	 * selectedPhoto is the photo the user clicked on
	 */
	protected static Photo selectedPhoto;
	
	/**
	 * albumNames is the observable list that is connected to the combobox
	 */
	protected ObservableList<String> albumNames;
	
	/**
	 * clickedDone is the method called when user clicks Done button
	 * @param event the action event
	 */
	@FXML
	private void clickedDone(ActionEvent event)
	{
		//System.out.println("clicked done"); //REMOVE
		
		String moveToAlbumStr = albumNamesComboBox.getSelectionModel().getSelectedItem();
		//System.out.println("moveToAlbumStr ="+moveToAlbumStr); //REMOVE
		
		//moveToAlbum is empty [STORYBOARD - E1]
		if(moveToAlbumStr == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Please select an album to move the photo to.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		Album moveToAlbum = UserList.currentUser.getAlbumObject(moveToAlbumStr);
		
		//not needed? CHECK IF caption ALREADY EXISTS in moveToAlbum [STORYBOARD - E3]
		
		//CHECK IF YOU'RE OVER LIMIT IN moveToAlbum [STORYBOARD - E5]
		if(moveToAlbum.photosOverLimit())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="You cannot move to album "+moveToAlbumStr+" because it already contains "+Constant.MAX_PHOTOS+" photos. You can delete a photo in album "+moveToAlbumStr+" to make space for a new photo.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//Move Confirmation [STORYBOARD - MC]
		Alert alert = new Alert(AlertType.CONFIRMATION);
		String s ="Are you sure you want to move Photo "+selectedPhoto.caption+" from Album "+selectedAlbum.name+" to Album "+moveToAlbumStr+"?";
		alert.setContentText(s);
		        
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			// ... user chose OK
			//System.out.println("OK"); //REMOVE
			
			//CALL KARL'S METHOD TO MOVE PHOTO
			selectedPhoto.movePhoto(selectedAlbum.name, moveToAlbumStr);
		      	
		} else {
			// ... user chose CANCEL or closed the dialog
		    //System.out.println("CANCEL OR X"); //REMOVE
		      	  
		    //do nothing
		    
		    return;
		}		
		
		//go to album [STORYBOARD - 6]
		goToAlbum(event);
		
	}
	
	/**
	 * clickedCancel is the method called when user clicks Cancel button
	 * @param event the action event
	 */
	@FXML
	private void clickedCancel(ActionEvent event)
	{
		//System.out.println("clicked cancel"); //REMOVE
		
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
	 * start is the method to start this part of the application
	 * @param stage is the stage for application
	 */
	@Override
	public void start(Stage stage)
	{
		//System.out.println("inside move photo controller start method"); //REMOVE
		
		selectedAlbum = UserHomeController.getSelectedAlbum();
		selectedPhoto = AlbumController.getSelectedPhoto();
		
		String h = "You are moving Photo "+selectedPhoto.caption+" from Album "+selectedAlbum.name;
		movePhotoHeader.setText(h);
		
		albumNames = FXCollections.observableArrayList(UserList.currentUser.getAllAlbumsStr(selectedAlbum.name));
		albumNamesComboBox.setItems(albumNames);
		
		Photos.needQuitConfirmation = true;
		
		if(Photos.needQuitConfirmation)
		{
			Platform.setImplicitExit(false);
			
			//quit confirmation [STORYBOARD - QC]
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              ////System.out.println("Stage is closing"); //REMOVE
		              Alert alert = new Alert(AlertType.CONFIRMATION);
		              String s ="Are you sure you want to quit? \nWARNING: If you made changes and clicked quit without clicking done or cancel, then the changes you made will not be saved if you quit.";
		              alert.setContentText(s);
		              
		              Optional<ButtonType> result = alert.showAndWait();
		              if (result.get() == ButtonType.OK){
		                  // ... user chose OK
		            	  //System.out.println("OK"); //REMOVE
		            	  
		            	  stage.close();
		            	  
		              } else {
		                  // ... user chose CANCEL or closed the dialog
		            	  //System.out.println("CANCEL OR X"); //REMOVE
		            	  
		            	  we.consume(); //don't close the window
		              }
		          }
		      });
		} 
	}
}