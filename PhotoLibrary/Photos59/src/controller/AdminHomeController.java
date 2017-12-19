package controller;

import application.Photos;
import model.*;

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
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 * AdminHomeController to broker between front-end and back-end for admin home
 */
public class AdminHomeController extends Application
{

	/**
	 * rootPane is the root pane on which every UI element is put
	 */
	@FXML
	protected AnchorPane rootPane;
	
	/**
	 * listOfUsers is the list view that shows all users
	 */
	@FXML
	protected ListView<String> listOfUsers;
	
	/**
	 * obsList is the observable list of all users that is connected to the ListView listOfUsers
	 */
	protected static ObservableList<String> obsList;
	
	/**
	 * clickedLogout is the method called when user clicks Logout button
	 * @param event the action event
	 */
	@FXML
	private void clickedLogout(ActionEvent event)
	{
		//System.out.println("clicked logout"); //REMOVE
		
		//return to login [STORYBOARD - 1]
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			LoginController loginController = (LoginController)loader.getController();
			loginController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Photos Login");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * clickedCreateNewUser is the method called when user clicks Create New User button
	 * @param event the action event
	 */
	@FXML
	private void clickedCreateNewUser(ActionEvent event)
	{
		//System.out.println("clicked create new user"); //REMOVE
		
		//over limit on the number of users [STORYBOARD - E5]
		if(UserList.usersOverLimit())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="You cannot add another user. Maximum number of users = "+Constant.MAX_USERS+". If you want to add new user please delete another user to make space.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//create new user [STORYBOARD - 3]
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/CreateNewUser.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			CreateNewUserController createNewUserController = (CreateNewUserController)loader.getController();
			createNewUserController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Create New User");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * clickedDeleteUser is the method called when user clicks Delete User button
	 * @param event the action event
	 */
	@FXML
	private void clickedDeleteUser(ActionEvent event)
	{
		//System.out.println("clicked delete user"); //REMOVE
		
		//can't delete because you haven't selected anything [STORYBOARD - E4]
		String usernameToDelete = listOfUsers.getSelectionModel().getSelectedItem();
		//System.out.println(usernameToDelete); //REMOVE
		if(usernameToDelete == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Please select a user to delete.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//can't delete admin [STORYBOARD - E4]
		if(usernameToDelete.equalsIgnoreCase("admin"))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="You cannot delete admin.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//can't delete stock [STORYBOARD - E4]
		if(usernameToDelete.equalsIgnoreCase("stock"))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="You cannot delete stock.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//delete confirmation [STORYBOARD - DC]
		Alert alert = new Alert(AlertType.CONFIRMATION);
        String s ="Are you sure you want to delete "+usernameToDelete+"? \nThis decision is irreversible.";
        alert.setContentText(s);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
      	  //System.out.println("OK"); //REMOVE
      	  
      	  UserList.deleteUser(usernameToDelete);
      	  obsList = FXCollections.observableArrayList(UserList.allUsernames);
      	  listOfUsers.setItems(obsList);
      	  
        } else {
            // ... user chose CANCEL or closed the dialog
      	  //System.out.println("CANCEL OR X"); //REMOVE
      	  
      	  //do nothing.
        }
	}
	
	/**
	 * start is the method to start this part of the application
	 * @param stage is the stage for application
	 */
	@Override
	public void start(Stage stage)
	{
		//System.out.println("inside adminhomecontroller start method"); //REMOVE
		
		obsList = FXCollections.observableArrayList(UserList.allUsernames);
		listOfUsers.setItems(obsList);
		
		Photos.needQuitConfirmation = false;
		Platform.setImplicitExit(true);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  //do nothing ... no quit confirmation
	          }
	      });
	}
}
