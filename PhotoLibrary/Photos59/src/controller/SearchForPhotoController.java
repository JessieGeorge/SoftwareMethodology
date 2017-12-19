package controller;

import application.Photos;
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
 * SearchForPhotoController to broker between front-end and back-end for SearchForPhoto
 */
public class SearchForPhotoController extends Application
{

	/**
	 * clickedSearchByDate is the method called when user clicks SearchByDate button
	 * @param event the action event
	 */
	@FXML
	private void clickedSearchByDate(ActionEvent event)
	{
		//System.out.println("Clicked search by date"); //REMOVE
		
		//go to search by date [STORYBOARD - 20]
		goToSearchByDate(event);
	}
	
	/**
	 * goToSearchByDate is the method to go to the search by date UI
	 * @param event is the action event
	 */
	private void goToSearchByDate(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SearchByDate.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			SearchByDateController searchByDateController = (SearchByDateController)loader.getController();
			searchByDateController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Search by Date");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * clickedSearchByTags is the method called when user clicks SearchByTags button
	 * @param event the action event
	 */
	@FXML
	private void clickedSearchByTags(ActionEvent event)
	{
		//System.out.println("Clicked search by tags"); //REMOVE
		
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
	 * clickedSearchByCaption is the method called when user clicks SearchByCaption button
	 * @param event the action event
	 */
	@FXML
	private void clickedSearchByCaption(ActionEvent event)
	{
		//System.out.println("Clicked search by tags"); //REMOVE
		
		//go to search by caption
		goToSearchByCaption(event);
	}
	
	/**
	 * goToSearchByCaption is the method to go to the search by tags UI
	 * @param event is the action event
	 */
	private void goToSearchByCaption(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SearchByCaption.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			SearchByCaptionController searchByCaptionController = (SearchByCaptionController)loader.getController();
			searchByCaptionController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Search by Caption");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * clickedSearchByFilepath is the method called when user clicks SearchByFilepath button
	 * @param event the action event
	 */
	@FXML
	private void clickedSearchByFilepath(ActionEvent event)
	{
		//go to search by filepath
		goToSearchByFilepath(event);
	}
	
	/**
	 * goToSearchByFilepath is the method to go to the search by filepath UI
	 * @param event is the action event
	 */
	private void goToSearchByFilepath(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SearchByFilepath.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			SearchByFilepathController searchByFilepathController = (SearchByFilepathController)loader.getController();
			searchByFilepathController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Search by Filepath");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
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
		//System.out.println("Hello i'm in login controller start"); //REMOVE
		Photos.needQuitConfirmation = false;
		Platform.setImplicitExit(true);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  //do nothing ... no quit confirmation
	          }
	      });
	}
}
