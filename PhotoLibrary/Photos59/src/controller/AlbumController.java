package controller;

import application.Photos;
import model.*;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
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
 * AlbumController to broker between front-end and back-end for album
 */
public class AlbumController extends Application
{
	//YOU HAVE TO CHECK IF ALBUM NAME IS STOCK AND CHANGE A COUPLE OF THINGS ... SEE SLIDE 56
	
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
	 * albumName is the name of album
	 */
	@FXML
	Text albumName = new Text(); //for the heading
	
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
	 * clickedBack is the method called when user clicks Back button
	 * @param event the action event
	 */
	@FXML
	private void clickedBack(ActionEvent event)
	{
		//System.out.println("clicked back"); //REMOVE
		
		//return to user home [STORYBOARD - 4]
		goToUserHome(event);
	}
	
	/**
	 * clickedAddPhoto is the method called when user clicks Add Photo button
	 * @param event the action event
	 */
	@FXML
	private void clickedAddPhoto(ActionEvent event)
	{
		//System.out.println("clicked add photo"); //REMOVE
		
		//check if number of photos equal limit [STORYBOARD - E5]
		if(selectedAlbum.photosOverLimit())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="You cannot add another photo to this album. Maximum number of photos per album = "+Constant.MAX_PHOTOS+". If you want to add a new photo please delete another photo to make space.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//go to add photo [STORYBOARD - 7]
		goToAddPhoto(event);
	}
	
	/**
	 * clickedSlideshow is the method called when user clicks Slideshow button
	 * @param event the action event
	 */
	@FXML
	private void clickedSlideshow(ActionEvent event)
	{
		//System.out.println("clicked slideshow"); //REMOVE
		
		//check if album empty, no photos for slideshow [STORYBOARD - E7]
		if(selectedAlbum.photos.size() == 0)
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="This album is empty. No photos for slideshow.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		//slideshow [STORYBOARD - 16]
		openSlideshowWindow(event);
	}
	
	/**
	 * clickedSortPhotos is the method called when user clicks Sort Photos button
	 * @param event the action event
	 */
	@FXML
	private void clickedSortPhotos(ActionEvent event)
	{
		// System.out.println("clicked add photo"); //REMOVE

		// check if album empty, no photos for sort [STORYBOARD - E7]
		if (selectedAlbum.photos.size() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			String s = "This album is empty. No photos to sort.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
				
		//go to add photo [STORYBOARD - 7]
		goToSortPhotos(event);
	}
	
	/**
	 * clickedPhoto is the method called when user clicks a photo. it shows the menu for that photo
	 * @param event is the action event
	 * @param photoId is the id of the clicked photo
	 * @param imageView is the image view of the thumbnail photo
	 */
	private void clickedPhoto(MouseEvent event, UUID photoId, ImageView imageView)
	{
		//System.out.println("clicked photo"); //REMOVE
		
		selectedPhoto = selectedAlbum.getPhotoObject(photoId);
		
		//show menu for photo
		final ContextMenu cm = new ContextMenu();
		
		MenuItem display = new MenuItem("Display");
		display.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked display"); //REMOVE
		        
		        //display [STORYBOARD - 8]
		        openDisplayWindow(event);
		    }
		});
		
		MenuItem tags = new MenuItem("Tags");
		tags.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked tags"); //REMOVE
		        
		      //tags [STORYBOARD - 9]
		      goToTagHome(event);
		        
		    }
		});
		
		MenuItem copy = new MenuItem("Copy");
		copy.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked copy"); //REMOVE
		        
		        //no other album [STORYBOARD - E4]
		        List<String> allOtherAlbumNames = UserList.currentUser.getAllAlbumsStr(selectedAlbum.name);
		        if(allOtherAlbumNames.size()==0)
		        {
		        	Alert alert = new Alert(AlertType.ERROR);
					String s ="You cannot copy photo because there is no other album to paste it into.";
					alert.setContentText(s);
					alert.showAndWait();
					return;
		        }
		        
		      //copy photo [STORYBOARD - 13]
			  goToCopyPhoto(event);
		    }
		});
		
		MenuItem moveTo = new MenuItem("Move To");
		moveTo.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked moveTo"); //REMOVE

				// no other album [STORYBOARD - E4]
				List<String> allOtherAlbumNames = UserList.currentUser.getAllAlbumsStr(selectedAlbum.name);
				if (allOtherAlbumNames.size() == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					String s = "You cannot move photo because there is no other album to move it to.";
					alert.setContentText(s);
					alert.showAndWait();
					return;
				}
						        
		      //move photo [STORYBOARD - 14]
		      goToMovePhoto(event);
		    }
		});
		
		MenuItem recaption = new MenuItem("Recaption");
		recaption.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked recaption"); //REMOVE
		        
		      //go to recaption photo [STORYBOARD - 15]
		      goToRecaptionPhoto(event);
		    }
		});
		
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        //System.out.println("Clicked delete"); //REMOVE
		        
		        //delete confirmation [STORYBOARD - DC]
		        Alert alert = new Alert(AlertType.CONFIRMATION);
		        String s ="Are you sure you want to delete "+selectedPhoto.caption+"? \nThis decision is irreversible.";
		        alert.setContentText(s);
		        
		        Optional<ButtonType> result = alert.showAndWait();
		        if (result.get() == ButtonType.OK){
		            // ... user chose OK
		      	  //System.out.println("OK"); //REMOVE
		      	  
		      	  selectedAlbum.removePhoto(selectedPhoto.id);
		      	  
		      	  start(thisStage); //to update UI.
		      	  
		        } else {
		            // ... user chose CANCEL or closed the dialog
		      	  //System.out.println("CANCEL OR X"); //REMOVE
		      	  
		      	  //do nothing.
		        }
		    }
		});
		
		cm.getItems().add(display);
		cm.getItems().add(tags);
		cm.getItems().add(copy);
		cm.getItems().add(moveTo);
		cm.getItems().add(recaption);
		cm.getItems().add(delete);
		
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
		    new EventHandler<MouseEvent>() {
		        @Override 
		        public void handle(MouseEvent e) {
		        	cm.show(imageView, e.getScreenX(), e.getScreenY());
		        }
		});
	}
	
	/**
	 * getSelectedPhoto to get the selected photo
	 * @return Photo the selected photo
	 */
	public static Photo getSelectedPhoto()
	{
		return selectedPhoto;
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
	 * goToAddPhoto is the method to go to the AddPhoto UI
	 * @param event is the action event
	 */
	private void goToAddPhoto(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AddPhoto.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			AddPhotoController addPhotoController = (AddPhotoController)loader.getController();
			addPhotoController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Add Photo");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * openSlideshowWindow is the method to open a window so user can see slideshow of photos in this album
	 * @param event is the event
	 */
	private void openSlideshowWindow(Event event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Slideshow.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage parentStage = (Stage) source.getScene().getWindow();
			
			Stage slideshowStage = new Stage();
			
			SlideshowController slideshowController = (SlideshowController)loader.getController();
			slideshowController.start(slideshowStage);
			
			Scene scene = new Scene(root, 900, 500);
			
			slideshowStage.setScene(scene);
			slideshowStage.setTitle("Slideshow");
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
	 * goToSortPhotos is the method to go to the SortPhotos UI
	 * @param event is the action event
	 */
	private void goToSortPhotos(ActionEvent event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SortPhotos.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			SortPhotosController sortPhotosController = (SortPhotosController)loader.getController();
			sortPhotosController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Sort Photos");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * openDisplayWindow is the method to open a window so user can see display of the selected photo
	 * @param event is the event
	 */
	private void openDisplayWindow(Event event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/DisplayPhoto.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage parentStage = (Stage) source.getScene().getWindow();
			
			Stage displayStage = new Stage();
			
			DisplayPhotoController displayPhotoController = (DisplayPhotoController)loader.getController();
			displayPhotoController.start(displayStage);
			
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
	 * goToTagHome is the method to go to the tag home UI
	 * @param event is the action event
	 */
	private void goToTagHome(Event event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/TagHome.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			TagHomeController tagHomeController = (TagHomeController)loader.getController();
			tagHomeController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Tags");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * goToCopyPhoto is the method to go to the Copy Photo UI
	 * @param event is the action event
	 */
	private void goToCopyPhoto(Event event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/CopyPhoto.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			CopyPhotoController copyPhotoController = (CopyPhotoController)loader.getController();
			copyPhotoController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Copy Photo");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * goToMovePhoto is the method to go to the Move Photo UI
	 * @param event is the action event
	 */
	private void goToMovePhoto(Event event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/MovePhoto.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			MovePhotoController movePhotoController = (MovePhotoController)loader.getController();
			movePhotoController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Move Photo");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * goToRecaptionPhoto is the method to go to the Recaption Photo UI
	 * @param event is the action event
	 */
	private void goToRecaptionPhoto(Event event)
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/RecaptionPhoto.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			
			RecaptionPhotoController recaptionPhotoController = (RecaptionPhotoController)loader.getController();
			recaptionPhotoController.start(stage);
			
			Scene scene = new Scene(root, 1000, 600);
			
			stage.setScene(scene);
			stage.setTitle("Recaption Photo");
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
		//System.out.println("Hello i'm in album controller start"); //REMOVE
		
		selectedAlbum = UserHomeController.getSelectedAlbum();
		
		thisStage = stage;
		
		//for the heading
		String albumNameStr = selectedAlbum.name;
		String s = "Album "+albumNameStr;
		albumName.setText(s);
		
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(12);
		
		gridPane.getChildren().clear();
		int gridIconRow = 0;
		
		int gridTextRow = gridIconRow+1;
		
		int gridCol = 0;
		
		for (int i = 0; i < selectedAlbum.photos.size(); i++) {
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
			Photo thisPhoto = selectedAlbum.photos.get(i);
			File file = new File(thisPhoto.fileLocation);
		    Image image = new Image(file.toURI().toString(), 48, 56, true, false);
			final ImageView photo = new ImageView(image);
			
			Label photoCaption = new Label();
			photoCaption.setText(selectedAlbum.photos.get(i).caption);

			photo.setOnMouseClicked(new EventHandler<MouseEvent>(){
				 
	            @Override
	            public void handle(MouseEvent event) {
	                clickedPhoto(event, thisPhoto.id, photo);
	            }
	        });

			gridPane.add(photo, gridCol, gridIconRow);
			gridPane.add(photoCaption, gridCol, gridTextRow);
			gridPane.setHalignment(photoCaption, HPos.CENTER);

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
