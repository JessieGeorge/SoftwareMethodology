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
 * CreateNewUserController to broker between front-end and back-end for create new user
 */
public class CreateNewUserController extends Application {

	/**
	 * newUsername is the new username to be registered
	 */
	@FXML
	protected TextField newUsername;
	
	/**
	 * usernameInstructions is the instructions for valid username
	 */
	@FXML
	protected Text usernameInstructions;
	
	/**
	 * clickedDone is the method called when user clicks Done button
	 * @param event the action event
	 */
	@FXML
	private void clickedDone(ActionEvent event)
	{
		//System.out.println("clicked done"); //REMOVE
		
		//newUsername is empty [STORYBOARD - E1]
		if(newUsername.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Username cannot be empty.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		String newUsernameStr = newUsername.getText().toString();
		
		//CHECK FOR INVALID NEWUSERNAME I.E DOESN'T FOLLOW RULES [STORYBOARD - E2]
		if(!UserList.isValidUsername(newUsernameStr))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Invalid Username";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//CHECK IF NEWUSERNAME ALREADY EXISTS [STORYBOARD - E3]
		if(UserList.usernameExists(newUsernameStr))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Username already exists";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//CALL KARL'S METHOD TO ADD NEW USERNAME
		UserList.addUser(newUsernameStr);
		
		//System.out.println("You added "+newUsername.getText().toString()); //REMOVE
		
		//go to admin home [STORYBOARD - 2]
		goToAdminHome(event);
		
	}
	
	/**
	 * clickedCancel is the method called when user clicks Cancel button
	 * @param event the action event
	 */
	@FXML
	private void clickedCancel(ActionEvent event)
	{
		//System.out.println("clicked cancel"); //REMOVE
		
		//go to admin home [STORYBOARD - 2]
		goToAdminHome(event);
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
	 * start is the method to start this part of the application
	 * @param stage is the stage for application
	 */
	@Override
	public void start(Stage stage)
	{
		//System.out.println("inside createnewusercontroller start method"); //REMOVE
		
		String s = "Username can contain alphabets, digits and space. The maximum length of a username is "+Constant.MAX_LENGTH+" characters.";
		usernameInstructions.setText(s);
		Photos.needQuitConfirmation = true;
		
		if(Photos.needQuitConfirmation)
		{
			Platform.setImplicitExit(false);
			
			//quit confirmation [STORYBOARD - QC]
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              //System.out.println("Stage is closing"); //REMOVE
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
