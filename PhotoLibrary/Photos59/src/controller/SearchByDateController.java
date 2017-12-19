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
 * SearchByDateController to broker between front-end and back-end for SearchByDate
 */
public class SearchByDateController extends Application
{
	/**
	 * fromDate is the starting date for search criteria
	 */
	@FXML
	protected TextField fromDate;
	
	/**
	 * toDate is the ending date for search criteria
	 */
	@FXML
	protected TextField toDate;
	
	/**
	 * searchResults are the search results
	 */
	static List<Photo> searchResults;
	
	/**
	 * isValidDate return true if the date is valid
	 * @param date the date
	 * @return true if the date is valid else false
	 */
	private static boolean isValidDate(String date)
	{
		//format is mm/dd/yyyy
		
		if(date.length()!=10)
			return false;
		
		for(int i=0; i<10; i++)
		{
			if(i==2 || i==5)
			{
				if(date.charAt(i)!='/')
				{
					return false;
				}
			}
			
			else
			{
				if(!Character.isDigit(date.charAt(i)))
					return false;
			}
			
		}
		
		int month = Integer.parseInt(date.substring(0, 2));
		int day = Integer.parseInt(date.substring(3, 5));
		int year = Integer.parseInt(date.substring(6));
		
		if(month > 12 || month < 1)
		{
			return false;
		}
		
		if(day < 1)
			return false;
		
		//allowing for leap year ... double check with karl that nothing bad happens if you give feb 29 when it's not a leap year, will Calendar class throw error?
		if(month == 2 && day > 29) 
		{
			return false;
		}
		
		//30 days have september, april, june and november
		else if(month == 9 || month == 4 || month == 6 || month == 11)
		{
			if(day > 30)
				return false;
		}
		
		else
		{
			if(day > 31)
				return false;
		}
		
		if(year < 1)
			return false;
		
		
		return true; //good date
	}
	
	/**
	 * clickedDone is the method called when user clicks Done button
	 * @param event the action event
	 */
	@FXML
	private void clickedDone(ActionEvent event)
	{
		//System.out.println("clicked done"); //REMOVE
		
		//date is empty [STORYBOARD - E1]
		if(fromDate.getText().isEmpty() || toDate.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Dates cannot be empty.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//invalid date [STORYBOARD - E2]
		if(!isValidDate(fromDate.getText().toString()) || !isValidDate(toDate.getText().toString()))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Invalid Date.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		String fromDateStr = fromDate.getText().toString();
		String toDateStr = toDate.getText().toString();
		
		//CALL KARL'S METHOD TO SEARCH BY DATE
		searchResults = UserList.currentUser.searchByDateRange(fromDateStr, toDateStr);
		
		//CHOOSE EITHER ONE BASED ON RESULTS:
		if(searchResults.size()==0)
		{
			//no results [STORYBOARD - 23]
			goToNoResults(event);
		}
		
		else
		{
			//search results [STORYBOARD - 24]
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
			
			String sc = "Search criteria: \nFrom date:"+fromDate.getText().toString()+" \nTo Date:"+toDate.getText().toString();
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
			String sc = "From date:"+fromDate.getText().toString()+" \nTo Date:"+toDate.getText().toString();
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
