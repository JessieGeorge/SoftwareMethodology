package controller;

import application.Photos;
import model.*;

import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 * SpecifyTagsForSearchController to broker between front-end and back-end for SpecifyTagsForSearch
 */
public class SpecifyTagsForSearchController extends Application
{
	/**
	 * listOfTagTypes is the list view containing all the tag types
	 */
	@FXML
	protected ListView listOfTagTypes;
	
	/**
	 * listOfTagValues is the list view containing all the tag values
	 */
	@FXML
	protected ListView<String> listOfTagValues;
	
	/**
	 * tagTypes is the observable list that is tied to each of the combo boxes
	 */
	protected ObservableList tagTypes = FXCollections.observableArrayList();
	
	/**
	 * tagTypesForSearch is the list of tag types that the user selected for search criteria
	 */
	List<String> tagTypesForSearch;
	
	/**
	 * tagValuesForSearch is the list of tag values that the user entered for search criteria
	 */
	List<String> tagValuesForSearch;
	
	/**
	 * tagPairs is the list of tag pairs (i.e tag type = tag value is a tag pair)
	 */
	List<String> tagPairs;
	
	/**
	 * numTags is the number of tags for search criteria
	 */
	int numTags;
	
	/**
	 * searchResults are the search results
	 */
	static List<Photo> searchResults;
	
	/**
	 * setNumTags is to set the number of tags for search criteria
	 * @param t the number of tags for search criteria
	 */
	public void setNumTags(TextField t)
	{
		numTags = Integer.parseInt(t.getText().toString());
	}
	
	/**
	 * clickedDone is the method called when user clicks Done button
	 * @param event the action event
	 */
	@FXML
	private void clickedDone(ActionEvent event)
	{
		//System.out.println("clicked done"); //REMOVE
		
		tagTypesForSearch = listOfTagTypes.getItems();
		tagValuesForSearch = listOfTagValues.getItems();
		
		//System.out.println(tagTypesForSearch);
		//System.out.println(tagValuesForSearch);
		
		//a tag type is empty [STORYBOARD - E1]
		for(int i=0; i<tagTypesForSearch.size(); i++)
		{
			if(tagTypesForSearch.get(i).equals("Select Tag Type"+(i+1)))
			{
				Alert alert = new Alert(AlertType.ERROR);
				String s ="Tag type "+(i+1)+" is empty. Please select a tag type.";
				alert.setContentText(s);
				alert.showAndWait();
				return;
			}
		}
		
		//a tag value is empty [STORYBOARD - E1]
		for(int i=0; i<tagValuesForSearch.size(); i++)
		{
			if(tagValuesForSearch.get(i).equals("Enter Tag Value"+(i+1)))
			{
				Alert alert = new Alert(AlertType.ERROR);
				String s ="Tag value "+(i+1)+" is empty. Please enter a tag value.";
				alert.setContentText(s);
				alert.showAndWait();
				return;
			}
		}
		
		
		//go through the whole list and CHECK FOR INVALID tagValue I.E DOESN'T FOLLOW RULES [STORYBOARD - E2]
		
		//Redundant search - CHECK IF tag type = tag value ALREADY EXISTS [STORYBOARD - E8]
		tagPairs = new ArrayList<String>();
		for(int i=0; i<numTags; i++)
		{
			if(tagPairs.contains(tagTypesForSearch.get(i) +" = "+ tagValuesForSearch.get(i)))
			{
				Alert alert = new Alert(AlertType.ERROR);
				String s ="No two tag pairs can be equal because this makes search redundant. \nIf TagType1=TagType2, then ensure that TagValue1 is not equal to TagValue2 (or vice versa).";
				alert.setContentText(s);
				alert.showAndWait();
				return;
			}
				
			tagPairs.add(tagTypesForSearch.get(i) +" = "+ tagValuesForSearch.get(i));
		}
		
		//System.out.println("tagPairs = "+tagPairs); //REMOVE
		
		// CALL KARL'S METHOD TO SEARCH BY TAGS
		searchResults = UserList.currentUser.searchByTags(tagPairs);
		
		// System.out.println(tagPairs); //REMOVE

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
		
		//go to search by tags [STORYBOARD - 21]
		goToSearchByTags(event);
	}
	
	/**
	 * goToSearchByTags is the method to go to the search by tags UI
	 * @param event is the action event
	 */
	private void goToSearchByTags(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SearchByTags.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			SearchByTagsController searchByTagsController = (SearchByTagsController)loader.getController();
			searchByTagsController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Search by Tags");
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
			
			String sc = "Search Criteria: ";
			for(int i=0; i<tagPairs.size(); i++)
			{
				if(i==tagPairs.size()-1)
					sc += tagPairs.get(i);
				else
					sc += tagPairs.get(i)+" , ";
			}
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
			String sc = "";
			for(int i=0; i<tagPairs.size(); i++)
			{
				if(i==tagPairs.size()-1)
					sc += tagPairs.get(i);
				else
					sc += tagPairs.get(i)+" , ";
			}
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
		//System.out.println("inside specify tags for search controller start method"); //REMOVE
		
		//System.out.println("numTags = "+numTags); //REMOVE
		
		tagTypes.addAll(
	             "location", "person", "event", "animal", "food", "weather", "nature", "view", "color", "style", "miscellaneous"
	        );
		
		for(int i=0; i<numTags; i++)
		{
			listOfTagTypes.getItems().add("Select Tag Type"+(i+1));
			listOfTagValues.getItems().add("Enter Tag Value"+(i+1));
			//tagValues.add("Enter Tag Value"+(i+1));
		}
		
		listOfTagTypes.setEditable(true);
		listOfTagValues.setEditable(true);
		
		//listOfTagValues.setItems(tagValues);
		
		listOfTagTypes.setCellFactory(ComboBoxListCell.forListView(tagTypes));
		listOfTagValues.setCellFactory(TextFieldListCell.forListView());
		
		/*not needed:
		listOfTagValues.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
			@Override
			public void handle(ListView.EditEvent<String> t) {
				listOfTagValues.getItems().set(t.getIndex(), t.getNewValue());
				//System.out.println("setOnEditCommit"); //remove
			}
						
		});
		*/
		
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
