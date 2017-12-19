package controller;

import application.Photos;
import model.*;

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
 * AddPhotoController to broker between front-end and back-end for add photo
 */
public class AddPhotoController extends Application
{
	/**
	 * addPhotoInstructions is the instructions of the valid way to add a photo
	 */
	@FXML
	protected Text addPhotoInstructions;
	
	/**
	 * filepath is the filepath of the photo on the user's system
	 */
	@FXML
	protected TextField filepath;
	
	/**
	 * caption is the caption of the photo
	 */
	@FXML
	protected TextField caption;
	
	/**
	 * selectedAlbum is the album that user clicked on
	 */
	Album selectedAlbum;
	
	/**
	 * clickedDone is the method called when user clicks Done button
	 * @param event the action event
	 */
	@FXML
	private void clickedDone(ActionEvent event)
	{
		//System.out.println("clicked done"); //REMOVE
		
		//filepath or caption are empty [STORYBOARD - E1]
		if(filepath.getText().isEmpty() || caption.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Filepath and caption cannot be empty.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		String filepathStr = filepath.getText().toString();
		String captionStr = caption.getText().toString();
		
		//CHECK FOR INVALID filepath I.E DOESN'T FOLLOW RULES [STORYBOARD - E2]
		if(!Album.isValidFileLocation(filepathStr))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Invalid filepath. \nCheck that you entered the filepath correctly. Check that the photo format is correct. Check that the photo exists on your system.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//CHECK FOR INVALID caption I.E DOESN'T FOLLOW RULES [STORYBOARD - E2]
		if(!Album.isValidCaption(captionStr))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Invalid caption";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//CALL KARL'S METHOD TO ADD PHOTO
		selectedAlbum.addPhoto(filepathStr, captionStr);
		
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
			stage.setTitle("Album "+selectedAlbum.name);
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
		
		String s ="The filepath is the location of your photo. \nFor example, C:\\Users\\Johndoe\\Desktop\\cat.png for Windows or /home/johndoe/Desktop/cat.png for Mac or Linux. \nValid photo formats are png, jpeg, jpg or bmp. \n\nCaption can contain alphabets, digits and space. \nThe maximum length of a caption is "+Constant.MAX_LENGTH+" characters.";
		addPhotoInstructions.setText(s);
		
		Photos.needQuitConfirmation = true;
		
		if(Photos.needQuitConfirmation)
		{
			Platform.setImplicitExit(false);
			
			//quit confirmation [STORYBOARD - QC]
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              //System.out.println("Stage is closing"); //REMOVE
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
