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
 * SeeSearchCriteria is the controller to broker between front-end and back-end for SeeSearchCriteria
 */
public class SeeSearchCriteria extends Application
{
	/**
	 * searchCriteria is the search criteria the user entered
	 */
	@FXML 
	Text searchCriteria;
	
	/**
	 * setSearchCriteria is the method set the search criteria text
	 * @param sc is the search criteria string
	 */
	public void setSearchCriteria(String sc)
	{
		searchCriteria.setText(sc);
	}
	
	/**
	 * clickedBack is the method called when user clicks Back button
	 * @param event the action event
	 */
	@FXML
	private void clickedBack(ActionEvent event)
	{
		//System.out.println("clicked back"); //REMOVE
		
		//go to add tag [STORYBOARD - 10]
		goToSearchResults(event);
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
		Photos.needQuitConfirmation = true;
		
		if(Photos.needQuitConfirmation)
		{
			Platform.setImplicitExit(false);
			
			//search quit confirmation [STORYBOARD - SQC]
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              ////System.out.println("Stage is closing"); //REMOVE
		              Alert alert = new Alert(AlertType.CONFIRMATION);
		              String s ="Are you sure you want to quit? \nWARNING: Your search results won’t be saved unless you click “Create album with search results.” If you quit now you will lose your search results.";
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
