package controller;

import application.Photos;
import model.Constant;

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
 * SearchByTagsController to broker between front-end and back-end for SearchByTags
 */
public class SearchByTagsController extends Application 
{
	/**
	 * numberOfTags is the number of tags for search criteria
	 */
	@FXML
	protected TextField numberOfTags;
	
	/**
	 * limitStatement is the statement which tells user about max number of tags per photo
	 */
	@FXML
	protected Text limitStatement = new Text();
	
	/**
	 * getNumberOfTags is to get the number of tags
	 * @return number of tags as TextField
	 */
	public TextField getNumberOfTags()
	{
		return numberOfTags;
	}
	
	/**
	 * isValidNumber returns true if the number is valid
	 * @param num is the number as String
	 * @return true if the number is valid
	 */
	private static boolean isValidNumber(String num)
	{
		for(int i=0; i<num.length(); i++)
		{
			if(!Character.isDigit(num.charAt(i)))
				return false;
		}
		
		int number = Integer.parseInt(num);
		
		if(number < 1 || number > Constant.MAX_TAGS)
			return false;
		
		return true;
	}
	
	/**
	 * clickedDone is the method called when user clicks Done button
	 * @param event the action event
	 */
	@FXML
	private void clickedDone(ActionEvent event)
	{
		//System.out.println("clicked done"); //REMOVE
		
		////System.out.println("numberOfTags = "+numberOfTags); //REMOVE
		////System.out.println("string version = "+numberOfTags.getText().toString()); //REMOVE
		
		//numberOfTags is empty [STORYBOARD - E1]
		if(numberOfTags.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Number of tags cannot be empty.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//invalid numberOfTags [STORYBOARD - E2]
		if(!isValidNumber(numberOfTags.getText().toString()))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Invalid number of tags.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//specify tags for search [STORYBOARD - 22]
		goToSpecifyTagsForSearch(event);
	}
	
	/**
	 * clickedCancel is the method called when user clicks Cancel button
	 * @param event the action event
	 */
	@FXML
	private void clickedCancel(ActionEvent event)
	{
		//System.out.println("clicked cancel"); //REMOVE
		
		//go to search for photo [STORYBOARD - 19]
		goToSearchForPhoto(event);
				
	}
	
	/**
	 * goToSearchForPhoto is the method to go to the SearchForPhoto UI
	 * @param event is the action event
	 */
	private void goToSearchForPhoto(ActionEvent event)
	{	
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SearchForPhoto.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
					
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
					
			SearchForPhotoController searchForPhotoController = (SearchForPhotoController)loader.getController();
			searchForPhotoController.start(stage);
					
			Scene scene = new Scene(root, 1000, 600);
					
			stage.setScene(scene);
			stage.setTitle("Search for Photo");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * goToSpecifyTagsForSearch is the method to go to the SpecifyTagsForSearch UI
	 * @param event is the action event
	 */
	private void goToSpecifyTagsForSearch(ActionEvent event)
	{	
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SpecifyTagsForSearch.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
					
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
					
			SpecifyTagsForSearchController specifyTagsForSearchController = (SpecifyTagsForSearchController)loader.getController();
			
			specifyTagsForSearchController.setNumTags(numberOfTags);
			
			specifyTagsForSearchController.start(stage);
					
			Scene scene = new Scene(root, 1000, 600);
					
			stage.setScene(scene);
			stage.setTitle("Specify Tags for Search Criteria");
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
		//System.out.println("inside search by date start method"); //REMOVE
		
		String s = "Enter any number between 1 and "+Constant.MAX_TAGS+", inclusive.";
		limitStatement.setText(s);
		
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
