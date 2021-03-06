package controller;

import application.Photos;
import model.Album;
import model.Constant;
import model.UserList;

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
 * RenameAlbumController to broker between front-end and back-end for rename album
 */
public class RenameAlbumController extends Application
{
	/**
	 * albumName is the name of the album
	 */
	@FXML
	protected TextField albumName;
	
	/**
	 * albumNameInstructions is the instructions of a valid entry for album name
	 */
	@FXML
	protected Text albumNameInstructions;
	
	/**
	 * selectedAlbum is the album that the user clicked on
	 */
	Album selectedAlbum;
	
	/**
	 * oldAlbumName is the old album name before rename
	 */
	String oldAlbumName;
	
	/**
	 * clickedDone is the method called when user clicks Done button
	 * @param event the action event
	 */
	@FXML
	private void clickedDone(ActionEvent event)
	{
		//System.out.println("clicked done"); //REMOVE
		
		//albumName is empty [STORYBOARD - E1]
		if(albumName.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Album name cannot be empty.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		String newAlbumNameStr = albumName.getText().toString();
		
		//CHECK FOR INVALID albumName I.E DOESN'T FOLLOW RULES [STORYBOARD - E2]
		if(!UserList.currentUser.isValidAlbum(newAlbumNameStr))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Invalid album name.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//CHECK IF albumName ALREADY EXISTS [STORYBOARD - E3]
		if(UserList.currentUser.albumNameExists(newAlbumNameStr))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s = "Album name already exists. No duplicates allowed.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		
		//CALL KARL'S METHOD TO RENAME ALBUM
		UserList.currentUser.renameAlbum(oldAlbumName, newAlbumNameStr);
		
		//System.out.println("You added "+albumName.getText().toString()); //REMOVE
		
		//go to user home [STORYBOARD - 4]
		goToUserHome(event);
		
	}
	
	/**
	 * clickedCancel is the method called when user clicks Cancel button
	 * @param event the action event
	 */
	@FXML
	private void clickedCancel(ActionEvent event)
	{
		//System.out.println("clicked cancel"); //REMOVE
		
		//go to user home [STORYBOARD - 4]
		goToUserHome(event);
	}
	
	/**
	 * goToUserHome is the method to go to the user home UI
	 * @param event is the action event
	 */
	private void goToUserHome(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/UserHome.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			UserHomeController userHomeController = (UserHomeController)loader.getController();
			userHomeController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle(UserList.currentUser.username+"'s Home");
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
		
		String s ="Album name can contain alphabets, digits and space. The maximum length of an album name is "+Constant.MAX_LENGTH+" characters.";
		albumNameInstructions.setText(s);
		
		selectedAlbum = UserHomeController.getSelectedAlbum();
		oldAlbumName = selectedAlbum.name;
		albumName.setText(oldAlbumName);
		
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
