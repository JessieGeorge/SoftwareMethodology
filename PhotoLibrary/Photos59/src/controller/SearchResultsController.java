package controller;

import application.Photos;
import model.Album;
import model.Constant;
import model.Photo;
import model.UserList;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 * SearchResultsController to broker between front-end and back-end for SearchResults
 */
public class SearchResultsController extends Application
{
	/**
	 * rootPane is the root pane on which every UI element is put
	 */
	@FXML
	protected AnchorPane rootPane;
	
	/**
	 * scrollPane is the pane to scroll through photos
	 */
	@FXML
	protected ScrollPane scrollPane;
	
	/**
	 * searchResults are the search results
	 */
	protected static List<Photo> searchResults;
	
	/**
	 * selectedAlbum is the album that the user clicked on
	 */
	static Album selectedAlbum;
	
	/**
	 * selectedPhoto is the photo that the user clicked on
	 */
	static Photo selectedPhoto;
	
	/**
	 * gridPane is the grid for photos. There will be 14 columns and as many rows as required.
	 */
	GridPane gridPane = new GridPane();
	
	/**
	 * thisStage is the stage we are currently on in UI
	 */
	Stage thisStage;
	
	/**
	 * searchCriteria is the searchCriteria
	 */
	static String searchCriteria;
	
	/**
	 * setSearchCriteria is the method set the search criteria
	 * @param sc is the search criteria string
	 */
	public void setSearchCriteria(String sc)
	{
		searchCriteria = sc;
	}
	
	/**
	 * clickedSeeSearchCriteria is called when the user clicks the link to see search criteria
	 * @param event the action event
	 */
	@FXML
	private void clickedSeeSearchCriteria(ActionEvent event)
	{
		//System.out.println("in clicked see search criteria"); //REMOVE
		
		goToSeeSearchCriteria(event);
	}
	
	/**
	 * goToUserHome is the method to go to the user home UI
	 * @param event is the action event
	 */
	private void goToSeeSearchCriteria(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SeeSearchCriteria.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			SeeSearchCriteria seeSearchCriteriaController = (SeeSearchCriteria)loader.getController();
			seeSearchCriteriaController.setSearchCriteria(searchCriteria);
			seeSearchCriteriaController.start(stage);
			
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
	 * clickedSlideshow is the method called when user clicks Slideshow button
	 * @param event the action event
	 */
	@FXML
	private void clickedSlideshow(ActionEvent event)
	{
		//System.out.println("clicked slideshow"); //REMOVE
		
		//slideshow [STORYBOARD - 16]
		openSlideshowForSearchWindow(event);
	}
	
	/**
	 * openSlideshowForSearchWindow is the method to open a window so user can see slideshow of photos for search results
	 * @param event is the event
	 */
	private void openSlideshowForSearchWindow(Event event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SlideshowForSearch.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage parentStage = (Stage) source.getScene().getWindow();
			
			Stage slideshowStage = new Stage();
			
			SlideshowForSearchController slideshowForSearchController = (SlideshowForSearchController)loader.getController();
			slideshowForSearchController.start(slideshowStage);
			
			Scene scene = new Scene(root, 900, 500);
			
			slideshowStage.setScene(scene);
			slideshowStage.setTitle("Slideshow For Search Results");
			slideshowStage.setResizable(false);
			
			//can't operate on main page until you close display
			slideshowStage.initOwner(parentStage);
			slideshowStage.initModality(Modality.APPLICATION_MODAL);
			slideshowStage.showAndWait();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * clickedCreateAlbumWithSearchResults is the method called when user clicks CreateAlbumWithSearchResults button
	 * @param event the action event
	 */
	@FXML
	private void clickedCreateAlbumWithSearchResults(ActionEvent event)
	{
		//System.out.println("clicked create album with search results"); //REMOVE
		
		//go to create album with search results [STORYBOARD - 27]
		goToCreateAlbumWithSearchResults(event);
	}
	
	/**
	 * goToCreateAlbumWithSearchResults is the method to go to the CreateAlbumWithSearchResults UI
	 * @param event is the action event
	 */
	private void goToCreateAlbumWithSearchResults(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/CreateAlbumWithSearchResults.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			CreateAlbumWithSearchResultsController createAlbumWithSearchResultsController = (CreateAlbumWithSearchResultsController)loader.getController();
			createAlbumWithSearchResultsController.setSearchResults(searchResults);
			createAlbumWithSearchResultsController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Create Album with Search Results");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getSelectedPhoto is to get the selected photo
	 * @return the selected photo
	 */
	public static Photo getSelectedPhoto()
	{
		return selectedPhoto;
	}
	
	/**
	 * clickedPhoto is the method called when user clicks a photo. it shows the menu for that photo
	 * @param event is the action event
	 * @param photo is the selected photo
	 * @param imageView is the image view of the thumbnail photo
	 */
	private void clickedPhoto(MouseEvent event, Photo thisPhoto, ImageView imageView)
	{
		//System.out.println("clicked photo"); //REMOVE
		
		selectedPhoto = thisPhoto;
		
		//show menu for photo
		final ContextMenu cm = new ContextMenu();
		
		MenuItem display = new MenuItem("Display");
		display.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked display"); //REMOVE
		        
		        //display [STORYBOARD - 8]
		        openDisplayForSearchWindow(event);
		    }
		});
		
		
		cm.getItems().add(display);
		
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
		    new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent e) {
		        	cm.show(imageView, e.getScreenX(), e.getScreenY());
		        }
		});
	}
	
	/**
	 * openDisplayForSearchWindow is the method to open a window so user can see display of the selected photo
	 * @param event is the event
	 */
	private void openDisplayForSearchWindow(Event event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/DisplayPhotoForSearch.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage parentStage = (Stage) source.getScene().getWindow();
			
			Stage displayStage = new Stage();
			
			DisplayPhotoForSearchController displayPhotoForSearchController = (DisplayPhotoForSearchController)loader.getController();
			displayPhotoForSearchController.start(displayStage);
			
			Scene scene = new Scene(root, 900, 500);
			
			displayStage.setScene(scene);
			displayStage.setTitle("Display Photo");
			displayStage.setResizable(false);
			
			//can't operate on main page until you close display
			displayStage.initOwner(parentStage);
			displayStage.initModality(Modality.APPLICATION_MODAL);
			displayStage.showAndWait();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * clickedOk is the method called when user clicks OK button
	 * @param event the action event
	 */
	@FXML
	private void clickedOk(ActionEvent event)
	{
		//System.out.println("clicked ok"); //REMOVE
		
		//return to user home [STORYBOARD - 4]
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
			stage.setTitle("User Home");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * setSearchResults sets the search results to the given list
	 * @param sr the search results
	 */
	public void setSearchResults(List<Photo> sr)
	{
		searchResults = sr;
	}
	
	/**
	 * getSearchResults gets the search results
	 * @return search results as list of photos
	 */
	public static List<Photo> getSearchResults()
	{
		return searchResults;
	}
	
	/**
	 * start is the method to start this part of the application
	 * @param stage is the stage for application
	 */
	@Override
	public void start(Stage stage)
	{
		//System.out.println("inside search results controller start method"); //REMOVE
		
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(12);
		
		gridPane.getChildren().clear();
		int gridIconRow = 0;
		
		int gridTextRow = gridIconRow+1;
		
		int gridCol = 0;
		
		for (int i = 0; i < searchResults.size(); i++) 
		{
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

			//this didn't work:
			//final Image image = new Image(selectedAlbum.photos.get(i).fileLocation, 48, 56, true, false);
			
			//I'm creating a file I hope that's okay!
			Photo thisPhoto = searchResults.get(i);
			File file = new File(thisPhoto.fileLocation);
		    Image image = new Image(file.toURI().toString(), 48, 56, true, false);
			final ImageView photo = new ImageView(image);
			
			Label photoCaption = new Label();
			photoCaption.setText(searchResults.get(i).caption);

			photo.setOnMouseClicked(new EventHandler<MouseEvent>(){
				 
	            @Override
	            public void handle(MouseEvent event) {
	                clickedPhoto(event, thisPhoto, photo);
	            }
	        });

			gridPane.add(photo, gridCol, gridIconRow);
			gridPane.add(photoCaption, gridCol, gridTextRow);
			gridPane.setHalignment(photoCaption, HPos.CENTER);

			gridCol++;

		}
		
		scrollPane.setContent(gridPane);
		
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
