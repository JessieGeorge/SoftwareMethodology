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
 * SearchForPhotoController to broker between front-end and back-end for SearchForPhoto
 */
public class SortPhotosController extends Application
{
	/**
	 * selectedAlbum is the album that user clicked on
	 */
	Album selectedAlbum;

	/**
	 * clickedSortByDate is the method called when user clicks SortByDate button
	 * @param event the action event
	 */
	@FXML
	private void clickedSortByDate(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
        String s ="Are you sure you want to sort photos by date in album "+selectedAlbum.name+"? This decision is irreversible.";
        alert.setContentText(s);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
      	  //System.out.println("OK"); //REMOVE
        	
        	selectedAlbum.sortPhotosByDate();
        	
        	//success info
    		Alert alertSuccess = new Alert(AlertType.INFORMATION);
    		alertSuccess.setTitle("Success Info");
            alertSuccess.setHeaderText(null);
            String suc ="You have successfully sorted photos by date in album "+selectedAlbum.name; //CHANGE THIS
            alertSuccess.setContentText(suc);
            alertSuccess.showAndWait();
        	
        	goToAlbum(event);
      	  
        } else {
            // ... user chose CANCEL or closed the dialog
      	  //System.out.println("CANCEL OR X"); //REMOVE
      	  
      	  //do nothing
        }
	}
	
	/**
	 * clickedSortByCaption is the method called when user clicks SortByCaption button
	 * @param event the action event
	 */
	@FXML
	private void clickedSortByCaption(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
        String s ="Are you sure you want to sort photos by caption in album "+selectedAlbum.name+"? This decision is irreversible.";
        alert.setContentText(s);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
      	  //System.out.println("OK"); //REMOVE
        	
        	selectedAlbum.sortPhotosByCaption();
        	
        	//success info
    		Alert alertSuccess = new Alert(AlertType.INFORMATION);
    		alertSuccess.setTitle("Success Info");
            alertSuccess.setHeaderText(null);
            String suc ="You have successfully sorted photos by caption in album "+selectedAlbum.name; //CHANGE THIS
            alertSuccess.setContentText(suc);
            alertSuccess.showAndWait();
        	
        	goToAlbum(event);
      	  
        } else {
            // ... user chose CANCEL or closed the dialog
      	  //System.out.println("CANCEL OR X"); //REMOVE
      	  
      	  //do nothing
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
		selectedAlbum = UserHomeController.getSelectedAlbum();
		
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
