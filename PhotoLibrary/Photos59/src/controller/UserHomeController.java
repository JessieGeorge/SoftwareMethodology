package controller;

import application.Photos;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 * UserHomeController to broker between front-end and back-end for user home
 */
public class UserHomeController extends Application 
{
	//YOU HAVE TO CHECK IF ALBUM NAME IS STOCK AND CHANGE A COUPLE THINGS ... SEE SLIDE 54
	//or maybe it should be album belongs to stock? In case stock makes more than one album? See question.
	//all of stocks albums should be in users albums so it can be displayed and so that if user tries to name an album stock it will throw an error
	
	/**
	 * rootPane is the root pane on which every UI element is put
	 */
	@FXML
	protected AnchorPane rootPane;
	
	/**
	 * scrollPane is the pane to scroll through albums
	 */
	@FXML
	protected ScrollPane scrollPane;
	
	/**
	 * gridPane is the grid for albums. There will be 14 columns and as many rows as required.
	 */
	GridPane gridPane = new GridPane();
	
	/**
	 * selectedAlbum is the album that the user clicked on
	 */
	static Album selectedAlbum;
	
	/**
	 * thisStage is the stage we are currently on in UI
	 */
	Stage thisStage;
	
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
	 * clickedCreateAlbum is the method called when user clicks CreateAlbum button
	 * @param event the action event
	 */
	@FXML
	private void clickedCreateAlbum(ActionEvent event)
	{
		//System.out.println("clicked create album"); //REMOVE
		
		//over limit on the number of albums [STORYBOARD - E5]
		if(UserList.currentUser.albumsOverLimit())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="You cannot create another album. Maximum number of albums = "+Constant.MAX_ALBUMS+". If you want to create a new album please delete another album to make space.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//go to create album [STORYBOARD - 5]
		goToCreateAlbum(event);
	}
	
	/**
	 * goToCreateAlbum is the method to go to the CreateAlbum UI
	 * @param event is the action event
	 */
	private void goToCreateAlbum(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/CreateAlbum.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			CreateAlbumController createAlbumController = (CreateAlbumController)loader.getController();
			createAlbumController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Create Album");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * clickedSearchForPhoto is the method called when user clicks SearchForPhoto button
	 * @param event the action event
	 */
	@FXML
	private void clickedSearchForPhoto(ActionEvent event)
	{
		//System.out.println("clicked search for photo"); //REMOVE
		
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
	 * getSelectedAlbum is to get the selected album
	 * @return selectedAlbum as string
	 */
	public static Album getSelectedAlbum()
	{
		return selectedAlbum;
	}
	
	/**
	 * showMenu is to show the menu when a user clicks on an album
	 * @param event is the event
	 * @param l is the label of the selected album
	 */
	private void showMenu(ActionEvent event, Label l)
	{
		String albumName = l.getText().toString();
		selectedAlbum = UserList.currentUser.getAlbumObject(albumName);
		
		final ContextMenu cm = new ContextMenu();
		
		MenuItem open = new MenuItem("Open");
		open.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked open"); //REMOVE
		        
		        //go to album [STORYBOARD - 6]
		        goToAlbum(event);
		    }
		});
		
		MenuItem info = new MenuItem("Info");
		info.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked info"); //REMOVE
		        
		      //album info [STORYBOARD - 17]
		        Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Album Info");
		        alert.setHeaderText(null);
		        
		        String rangeOfDates = "";
		        if(selectedAlbum.photos.size() == 0)
		        	rangeOfDates = "N/A";
		        else
		        	rangeOfDates = selectedAlbum.getEarliestDate()+" - "+selectedAlbum.getLatestDate();
		        
		        String s = "Album name: "+selectedAlbum.name+" \nNumber of photos: "+selectedAlbum.photos.size()+" \nRange of dates: "+rangeOfDates; //CHANGE THIS
		        alert.setContentText(s);
		        alert.showAndWait();
		    }
		});
		
		MenuItem rename = new MenuItem("Rename");
		rename.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked rename"); //REMOVE
		        
		      //go to rename album [STORYBOARD - 18]
		      goToRenameAlbum(event);
		    }
		});
		
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked delete"); //REMOVE
		        
		        //delete confirmation [STORYBOARD - DC]
		        Alert alert = new Alert(AlertType.CONFIRMATION);
		        String s ="Are you sure you want to delete "+selectedAlbum.name+"? \nThis decision is irreversible.";
		        alert.setContentText(s);
		        
		        Optional<ButtonType> result = alert.showAndWait();
		        if (result.get() == ButtonType.OK){
		            // ... user chose OK
		      	  //System.out.println("OK"); //REMOVE
		      	  
		      	  UserList.currentUser.deleteAlbum(selectedAlbum.name);
		      	  
		      	  start(thisStage); //to update UI.
		      	  
		        } else {
		            // ... user chose CANCEL or closed the dialog
		      	  //System.out.println("CANCEL OR X"); //REMOVE
		      	  
		      	  //do nothing.
		        }
		    }
		});
		
		cm.getItems().add(open);
		cm.getItems().add(info);
		cm.getItems().add(rename);
		cm.getItems().add(delete);
		
		Button albumIcon = (Button)event.getSource();
		albumIcon.addEventHandler(MouseEvent.MOUSE_CLICKED,
		    new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent e) {
		        	cm.show(albumIcon, e.getScreenX(), e.getScreenY());
		        }
		});
		
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
			stage.setTitle("Album"); //CHANGE THIS
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * goToRenameAlbum is the method to go to the rename album UI
	 * @param event is the action event
	 */
	private void goToRenameAlbum(ActionEvent event)
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/RenameAlbum.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			RenameAlbumController renameAlbumController = (RenameAlbumController)loader.getController();
			renameAlbumController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Rename Album");
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
		//System.out.println("Hello i'm in user home controller start"); //REMOVE
		
		//System.out.println("SIZE="+UserList.currentUser.albums.size()); //REMOVE
		
		thisStage = stage;
		
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(12);
		
		gridPane.getChildren().clear();
		int gridIconRow = 0;
		
		int gridTextRow = gridIconRow+1;
		
		int gridCol = 0;

		for (int i = 0; i < UserList.currentUser.albums.size(); i++) {
			//System.out.println("ENTERED THE FOR LOOP"); // REMOVE

			// columns go from zero to 13
			if (gridCol == 14) {
				gridCol = 0;
				gridIconRow += 2;
				gridTextRow += 2;
			}

			ColumnConstraints colconst = new ColumnConstraints();
			colconst.setMaxWidth(60.0);
			gridPane.getColumnConstraints().add(colconst);

			final ImageView graphic = new ImageView(new Image("/image/FolderIcon.png", 40, 40, true, false));
			Button albumIcon = new Button(null, graphic);

			Label albumName = new Label();
			albumName.setText(UserList.currentUser.albums.get(i).name);

			albumIcon.setOnAction(e -> showMenu(e, albumName));

			gridPane.add(albumIcon, gridCol, gridIconRow);
			gridPane.add(albumName, gridCol, gridTextRow);
			gridPane.setHalignment(albumName, HPos.CENTER);

			gridCol++;

		}
		
		scrollPane.setContent(gridPane);
		
		Photos.needQuitConfirmation = false;
		Platform.setImplicitExit(true);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  //do nothing ... no quit confirmation
	          }
	      });
	}
}
