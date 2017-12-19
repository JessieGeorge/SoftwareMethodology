package controller;

import application.Photos;
import model.*;

import java.util.List;
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
 * SearchByFilepathController to broker between front-end and back-end for SearchByFilepath
 */
public class SearchByFilepathController extends Application
{
	/**
	 * caption is the photo filepath for search criteria
	 */
	@FXML
	protected TextField filepath;
	
	/**
	 * captionStr is the filepath in string format
	 */
	protected static String filepathStr;
	
	/**
	 * searchResults are the search results
	 */
	static List<Photo> searchResults;
	
	/**
	 * clickedDone is the method called when user clicks Done button
	 * @param event the action event
	 */
	@FXML
	private void clickedDone(ActionEvent event)
	{
		//System.out.println("clicked done"); //REMOVE

		// filepath is empty [STORYBOARD - E1]
		if (filepath.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			String s = "Filepath cannot be empty.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}

		filepathStr = filepath.getText().toString();

		// CHECK FOR INVALID filepath I.E DOESN'T FOLLOW RULES [STORYBOARD - E2]
		if (!Album.isValidFileLocation(filepathStr)) {
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Invalid filepath. \nCheck that you entered the filepath correctly. Check that the photo format is correct. Check that the photo exists on your system.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		// CALL KARL'S METHOD TO SEARCH BY FILEPATH
		searchResults = UserList.currentUser.searchByFileLocation(filepathStr);

		// CHOOSE EITHER ONE BASED ON RESULTS:
		if (searchResults.size() == 0) {
			// no results [STORYBOARD - 23]
			goToNoResults(event);
		}

		else {
			// search results [STORYBOARD - 24]
			goToSearchResults(event);
		}
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
	 * goToNoResults is the method to go to the NoResults UI
	 * @param event is the action event
	 */
	private void goToNoResults(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/NoResults.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			NoResultsController noResultsController = (NoResultsController)loader.getController();
			
			String sc = "Search criteria: Filepath ="+filepathStr;
			noResultsController.setSearchCriteria(sc);
			noResultsController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Search Results");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * goToSearchForResults is the method to go to the SearchForResults UI
	 * @param event is the action event
	 */
	private void goToSearchResults(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			SearchResultsController searchResultsController = (SearchResultsController)loader.getController();
			String sc = "Filepath ="+filepathStr;
			searchResultsController.setSearchCriteria(sc);
			searchResultsController.setSearchResults(searchResults);
			searchResultsController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Search Results");
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
