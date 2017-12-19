package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import controller.LoginController;
import model.*;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 * Photos is a single-user photo application. class Photos is for the main method.
 */
public class Photos extends Application {
	
	/**
	 * needQuitConfirmation is true if user needs to confirm that they are quitting the application.
	 * It is for safe quitting. It is set to true in cases where the user may lose some changes unless the click done.  
	 */
	public static boolean needQuitConfirmation = false;
	
	/**
	 * mainStage is the main stage of application
	 */
	Stage mainStage;
	
	/**
	 * method to start application
	 * @param primaryStage is the primary stage for application
	 */
	@Override
	public void start(Stage primaryStage) {
		
		UserList.initializeUserList();
		
		//System.out.println("users ="+UserList.users); //REMOVE
		
		mainStage = primaryStage;
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			LoginController loginController = (LoginController)loader.getController();
			loginController.start(mainStage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			mainStage.setScene(scene);
			mainStage.setTitle("Photos Login");
			mainStage.setResizable(false);
			mainStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * main method to launch application
	 * @param args arguments to the main method
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		UserList.readFromFile();
		UserList.readFromFile2();
		launch(args);
		UserList.writeToFile();
		UserList.writeToFile2();
		
		//System.out.println("users ="+UserList.users); //REMOVE
		//System.out.println("allUsernames ="+UserList.allUsernames); //REMOVE
	}
}
