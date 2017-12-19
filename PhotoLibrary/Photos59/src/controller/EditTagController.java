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
 * EditPhotoController to broker between front-end and back-end for edit photo
 */
public class EditTagController extends Application
{
	/**
	 * tagValueInstructions is the instructions of a valid entry for tag value
	 */
	@FXML
	protected Text tagValueInstructions;
		
	/**
	 * tagTypeComboBox is the combo box containing all tag types and user can select one. (Example for location = London, location is the tag type)
	 */
	@FXML
	protected ComboBox<String> tagTypeComboBox;
		
	/**
	 * tagValue is the tag value entered by user. (Example for location = London, London is the tag value)
	 */
	@FXML
	protected TextField tagValue;
	
	/**
	 * selectedPhoto is the photo that the user clicked on
	 */
	protected static Photo selectedPhoto;
	
	/**
	 * selectedTag is the tag that the user clicked on
	 */
	protected static String selectedTag;
	
	/**
	 * oldTagName is the old tag name (i.e. type)
	 */
	protected static String oldTagName;
	
	/**
	 * oldTagValue is the old tag value
	 */
	protected static String oldTagValue;
	
	/**
	 * clickedDone is the method called when user clicks Done button
	 * @param event the action event
	 */
	@FXML
	private void clickedDone(ActionEvent event)
	{
		//System.out.println("clicked done"); //REMOVE
		
		String tagType = tagTypeComboBox.getSelectionModel().getSelectedItem();
		//System.out.println("tag type ="+tagType); //REMOVE
		
		//tag type is empty [STORYBOARD - E1]
		if(tagType == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Tag type cannot be empty. Please select a tag type.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//tag value is empty [STORYBOARD - E1]
		if(tagValue.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Tag value cannot be empty.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		String tagValueStr = tagValue.getText().toString();
		
		//CHECK FOR INVALID tagValue I.E DOESN'T FOLLOW RULES [STORYBOARD - E2]
		if(!selectedPhoto.isValidTagValue(tagValueStr))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Invalid tag value.";
			alert.setContentText(s);
			alert.showAndWait();
			return;	
		}
		
		//CHECK IF tag type = tag value ALREADY EXISTS [STORYBOARD - E3]
		if(selectedPhoto.tagExists(tagType, tagValueStr))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="This tag already exists for this photo. No duplicates allowed.";
			alert.setContentText(s);
			alert.showAndWait();
			return;	
		}
		
		//CALL KARL'S METHOD TO EDIT TAG
		selectedPhoto.editTag(oldTagName, oldTagValue, tagType, tagValueStr);
		
		//go to tag home [STORYBOARD - 9]
		goToTagHome(event);
		
	}
	
	/**
	 * clickedCancel is the method called when user clicks Cancel button
	 * @param event the action event
	 */
	@FXML
	private void clickedCancel(ActionEvent event)
	{
		//System.out.println("clicked cancel"); //REMOVE
		
		//go to tag home [STORYBOARD - 9]
		goToTagHome(event);
	}
	
	/**
	 * goToTagHome is the method to go to the tag home UI
	 * @param event is the action event
	 */
	private void goToTagHome(Event event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/TagHome.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			TagHomeController tagHomeController = (TagHomeController)loader.getController();
			tagHomeController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Tags");
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
		//System.out.println("inside add tag controller start method"); //REMOVE
		
		selectedPhoto = AlbumController.getSelectedPhoto();
		
		String s = "Tag Value can contain alphabets, digits and space. The maximum length of a Tag Value is "+Constant.MAX_LENGTH+" characters.";
		tagValueInstructions.setText(s);
		
		selectedTag = TagHomeController.getSelectedTag();
		oldTagName = selectedTag.substring(0,selectedTag.indexOf('=')-1);
		oldTagValue = selectedTag.substring(selectedTag.indexOf('=')+2);
		
		tagValue.setText(oldTagValue);
		
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
