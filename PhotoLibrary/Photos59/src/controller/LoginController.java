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
 * LoginController to broker between front-end and back-end for Login
 */
public class LoginController extends Application
{
	/**
	 * rootPane is the root pane on which every UI element is put
	 */
	@FXML
	protected AnchorPane rootPane;
	
	/**
	 * username is the username given by the person trying to login
	 */
	@FXML
	protected TextField username; 
	
	/**
	 * clickedLogin is the method called when user clicks Login button
	 * @param event the action event
	 */
	@FXML
	private void clickedLogin(ActionEvent event)
	{
		//username is empty [STORYBOARD - E1]
		if(username.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Username cannot be empty.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		String usernameStr = username.getText().toString();
		
		//CHECK FOR INVALID USERNAME I.E DOESN'T EXIST IN USERNAME LIST [STORYBOARD - E2]
		if(!UserList.usernameExists(usernameStr))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Username does not exist. You have not been registered by admin.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		UserList.login(usernameStr);
		
		//System.out.println(username.getText().toString() + ", Consider it handled."); //REMOVE
	
		//admin [STORYBOARD - 2]
		if(username.getText().equalsIgnoreCase("admin"))
		{
			goToAdminHome(event);
		}
		
		//user [STORYBOARD - 4]
		else
		{
			goToUserHome(event);
		}
	}
	
	/**
	 * goToAdminHome is the method to go to the admin home UI
	 * @param event is the action event
	 */
	private void goToAdminHome(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AdminHome.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			AdminHomeController adminHomeController = (AdminHomeController)loader.getController();
			adminHomeController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("admin home");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
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
