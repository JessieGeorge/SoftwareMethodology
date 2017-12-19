package controller;

import application.Photos;
import model.Album;
import model.Constant;
import model.Photo;

import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 * RecaptionPhotoController to broker between front-end and back-end for recaption photo
 */
public class RecaptionPhotoController extends Application
{
	/**
	 * captionInstructions is the instructions of a valid entry for caption
	 */
	@FXML
	protected Text captionInstructions;
	
	/**
	 * caption is the caption for the photo
	 */
	@FXML
	protected TextField caption;
	
	/**
	 * selectedAlbum is the album that the user clicked on
	 */
	protected static Album selectedAlbum;
	
	/**
	 * selectedPhoto is the photo that the user clicked on
	 */
	protected static Photo selectedPhoto;
	
	/**
	 * clickedDone is the method called when user clicks Done button
	 * @param event the action event
	 */
	@FXML
	private void clickedDone(ActionEvent event)
	{
		//System.out.println("clicked done"); //REMOVE
		
		//caption is empty [STORYBOARD - E1]
		if(caption.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Caption cannot be empty.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		String captionStr = caption.getText().toString();
		
		// CHECK FOR INVALID caption I.E DOESN'T FOLLOW RULES [STORYBOARD - E2]
		if (!Album.isValidCaption(captionStr)) {
			Alert alert = new Alert(AlertType.ERROR);
			String s = "Invalid caption";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//not needed? CHECK IF caption ALREADY EXISTS in this album [STORYBOARD - E3] ...don't compare to itself in case user clicks done without changing anything
		
		//CALL KARL'S METHOD TO RECAPTION PHOTO
		selectedPhoto.updateCaption(captionStr);
		
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
		//System.out.println("inside createnewusercontroller start method"); //REMOVE
		
		selectedAlbum = UserHomeController.getSelectedAlbum();
		selectedPhoto = AlbumController.getSelectedPhoto();
		
		String s ="Caption can contain alphabets, digits and space. \nThe maximum length of a caption is "+Constant.MAX_LENGTH+" characters.";
		captionInstructions.setText(s);
		
		caption.setText(selectedPhoto.caption);
		
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
