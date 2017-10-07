/**
 * @author Jessie George
 * @author Karl Xu
 */

package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.collections.ObservableList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import application.SongLib;
import backend.*;
import java.util.*;
import java.io.*;

public class Controller 
{
	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private ListView<String> listView;
	
	/*
	 * user enters in text field
	 */
	
	@FXML
	private TextField songName;
	
	@FXML
	private TextField artist;
	
	@FXML
	private TextField album;
	
	@FXML
	private TextField year;
	
	/*
	 * text for display
	 */
	@FXML
	private Text songNameText;
	
	@FXML
	private Text artistText;
	
	@FXML
	private Text albumText;
	
	@FXML
	private Text yearText;
	
	@FXML
	private Pane detailsPane;
	
	/*
	 * for the delete scene
	 */
	@FXML
	private Text delsongNameText;
	
	@FXML
	private Text delartistText;
	
	@FXML
	private Text delalbumText;
	
	@FXML
	private Text delyearText;
	
	private static ObservableList<String> obsList;
	
	private static AnchorPane pane;
	
	private static SongLib myLib = new SongLib();
	
	private static int indexSelected;
	
	private static boolean beginning = true;
	
	private static Controller c;
	
	public boolean checkValidInput(String name, String artist, String album, String year)
	{
		int maxInputLength = 100;
		
		if(name==null || name.isEmpty() || artist==null || artist.isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Song Name and Artist cannot be empty.";
			alert.setContentText(s);
			alert.showAndWait();
			return false;
		}
		
		if(name.length()>maxInputLength)
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Song Name must be max "+maxInputLength+" characters.";
			alert.setContentText(s);
			alert.showAndWait();
			return false;
		}
		
		if(artist.length()>maxInputLength)
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Artist must be max "+maxInputLength+" characters.";
			alert.setContentText(s);
			alert.showAndWait();
			return false;
		}
		
		if(name.contains("|"))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Name cannot contain pipe character |.";
			alert.setContentText(s);
			alert.showAndWait();
			return false;
		}
		
		if(artist.contains("|"))
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Artist cannot contain pipe character |.";
			alert.setContentText(s);
			alert.showAndWait();
			return false;
		}
		
		if(album!=null && !album.isEmpty())
		{
			if(album.length()>maxInputLength)
			{
				Alert alert = new Alert(AlertType.ERROR);
				String s ="Album must be max "+maxInputLength+" characters.";
				alert.setContentText(s);
				alert.showAndWait();
				return false;
			}
			
			if(album.contains("|"))
			{
				Alert alert = new Alert(AlertType.ERROR);
				String s ="Album cannot contain pipe character |.";
				alert.setContentText(s);
				alert.showAndWait();
				return false;
			}
			
		}
		
		if(year!=null && !year.isEmpty())
		{
			if(!year.equalsIgnoreCase("Unknown"))
			{
				if(year.length()!=4)
				{
					Alert alert = new Alert(AlertType.ERROR);
					String s ="Year must be YYYY format.";
					alert.setContentText(s);
					alert.showAndWait();
					return false;
				}
				
				for(int i=0; i<4; i++)
				{
					if(!Character.isDigit(year.charAt(i)))
					{
						Alert alert = new Alert(AlertType.ERROR);
						String s ="Year must be only digits in YYYY format.";
						alert.setContentText(s);
						alert.showAndWait();
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public boolean checkAddUniqueInput(String name, String artist)
	{	
		for(int i = 0; i < myLib.songList.size(); i++) {
			String otherName = myLib.songList.get(i).getName();
			String otherArtist = myLib.songList.get(i).getArtist();
			
			if(name.equalsIgnoreCase(otherName) && artist.equalsIgnoreCase(otherArtist)) 
			{
				Alert alert = new Alert(AlertType.ERROR);
				String s ="The given song by the given artist already exists in the song library. No duplicates allowed.";
				alert.setContentText(s);
				alert.showAndWait();
				return false;	 
			}
		}
		
		return true;
	}
	
	public boolean checkEditUniqueInput(String name, String artist, int index)
	{
		for(int i = 0; i < myLib.songList.size(); i++) {
			String otherName = myLib.songList.get(i).getName();
			String otherArtist = myLib.songList.get(i).getArtist();
			
			if(i!=index && name.equalsIgnoreCase(otherName) && artist.equalsIgnoreCase(otherArtist)) 
			{
				Alert alert = new Alert(AlertType.ERROR);
				String s ="The given song by the given artist already exists in the song library. No duplicates allowed.";
				alert.setContentText(s);
				alert.showAndWait();
				return false;	 
			}
		}
		
		return true;
	}
	
	public void onSelectListItem()throws IOException //on mouseclick or mousepress
	{
		indexSelected = listView.getSelectionModel().getSelectedIndex();
		displaySongDetails(myLib.songList.get(indexSelected)); 
	}
	
	public void onAddButtonAction(ActionEvent event)throws IOException
	{
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		pane = FXMLLoader.load(SongLib.class.getResource("/view/AddSongView.fxml"));
		stage.setScene(new Scene(pane, 1000, 600));
		stage.show();	
	}
	
	public void handleCancelAdd(ActionEvent event)throws IOException
	{
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		FXMLLoader l = new FXMLLoader();
		l.setLocation(SongLib.class.getResource("/view/SongLibView.fxml"));
		AnchorPane backToRoot = (AnchorPane)l.load();
		c = l.getController();
		c.start();
		Scene scene = new Scene(backToRoot, 1000, 600);
		
		if(indexSelected != -1)
		{
			c.displaySongDetails(myLib.songList.get(indexSelected));
			c.listView.getSelectionModel().select(indexSelected); //auto-select the given item
		}
		
		stage.setScene(scene);
		stage.show();
	}
	
	public int findNewSongIndex(String newlyAddedName, String newlyAddedArtist)
	{
		if(myLib.songList.isEmpty()) //adding first song
			return 0;
		
		for(int i = 0; i < myLib.songList.size(); i++) 
		{
			String songName = myLib.songList.get(i).getName();
			String artist = myLib.songList.get(i).getArtist();
			if(songName.equalsIgnoreCase(newlyAddedName) && artist.equalsIgnoreCase(newlyAddedArtist))
				return i;
		}
		
		return -1;
			
	}
	
	public void handleDoneAdd(ActionEvent event)throws IOException
	{
		if(!checkValidInput(songName.getText().toString(), artist.getText().toString(), album.getText().toString(), year.getText().toString()))
			return;
		
		if(!checkAddUniqueInput(songName.getText().toString(), artist.getText().toString()))
			return;
		
		myLib.addSong(songName.getText().toString(), artist.getText().toString(), album.getText().toString(), year.getText().toString());
		
		
		indexSelected = findNewSongIndex(songName.getText().toString(), artist.getText().toString());
				
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		FXMLLoader l = new FXMLLoader();
		l.setLocation(SongLib.class.getResource("/view/SongLibView.fxml"));
		
		AnchorPane backToRoot = (AnchorPane)l.load();
		c = l.getController();
		c.start(); 
		
		if(indexSelected != -1)
		{
			c.displaySongDetails(myLib.songList.get(indexSelected));
			c.listView.getSelectionModel().select(indexSelected); //auto-select the given item
		}
		
		Scene scene = new Scene(backToRoot, 1000, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	public void onEditButtonAction(ActionEvent event)throws IOException
	{
		if(myLib.songList.isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Cannot edit empty song library.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		indexSelected = listView.getSelectionModel().getSelectedIndex();
		
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		
		FXMLLoader l = new FXMLLoader();
		l.setLocation(SongLib.class.getResource("/view/EditSongView.fxml"));
		pane = (AnchorPane)l.load();
		c = l.getController();
		Song S = myLib.songList.get(indexSelected);
		
		//populate text field so user can edit it
		c.songName.setText(S.getName());
		c.artist.setText(S.getArtist());
		c.album.setText(S.getAlbum());
		c.year.setText(S.getYear());
		
		stage.setScene(new Scene(pane, 1000, 600));
		stage.show();
		
	}
	
	public void handleCancelEdit(ActionEvent event)throws IOException
	{
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		FXMLLoader l = new FXMLLoader();
		l.setLocation(SongLib.class.getResource("/view/SongLibView.fxml"));
		AnchorPane backToRoot = (AnchorPane)l.load();
		c = l.getController();
		c.start();
		
		if(indexSelected != -1)
		{
			c.displaySongDetails(myLib.songList.get(indexSelected));
			c.listView.getSelectionModel().select(indexSelected); //auto-select the given item
		}
		
		Scene scene = new Scene(backToRoot, 1000, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	public void handleDoneEdit(ActionEvent event)throws IOException
	{
		if(!checkValidInput(songName.getText().toString(), artist.getText().toString(), album.getText().toString(), year.getText().toString()))
			return;
		
		if(!checkEditUniqueInput(songName.getText().toString(), artist.getText().toString(), indexSelected))
			return;
		
		myLib.editSong(myLib.songList.get(indexSelected),songName.getText().toString(), artist.getText().toString(), album.getText().toString(), year.getText().toString());
		
		indexSelected = findNewSongIndex(songName.getText().toString(), artist.getText().toString());
				
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		FXMLLoader l = new FXMLLoader();
		l.setLocation(SongLib.class.getResource("/view/SongLibView.fxml"));
		AnchorPane backToRoot = (AnchorPane)l.load();
		c = l.getController();
		c.start();
		
		if(indexSelected != -1)
		{
			c.displaySongDetails(myLib.songList.get(indexSelected));
			c.listView.getSelectionModel().select(indexSelected); //auto-select the given item
		}
		
		Scene scene = new Scene(backToRoot, 1000, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	public void onDeleteButtonAction(ActionEvent event)throws IOException
	{
		if(myLib.songList.isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			String s ="Cannot delete from empty song library.";
			alert.setContentText(s);
			alert.showAndWait();
			return;
		}
		
		indexSelected = listView.getSelectionModel().getSelectedIndex();
		
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		
		FXMLLoader l = new FXMLLoader();
		l.setLocation(SongLib.class.getResource("/view/DeleteSongView.fxml"));
		pane = (AnchorPane)l.load();
		c = l.getController();
		Song S = myLib.songList.get(indexSelected);
		
		c.delsongNameText.setText("Song Name: "+S.getName());
		c.delartistText.setText("Artist: "+S.getArtist());
		c.delalbumText.setText("Album: "+S.getAlbum());
		c.delyearText.setText("Year: "+S.getYear());
		
		ObservableValue<Integer> deleteDetailsPaneWidth = new ReadOnlyObjectWrapper<>(905); //hard coded from scene builder
		c.delsongNameText.wrappingWidthProperty().bind(deleteDetailsPaneWidth);
		c.delartistText.wrappingWidthProperty().bind(deleteDetailsPaneWidth);
		c.delalbumText.wrappingWidthProperty().bind(deleteDetailsPaneWidth);
		c.delyearText.wrappingWidthProperty().bind(deleteDetailsPaneWidth);
		
		
		stage.setScene(new Scene(pane, 1000, 600));
		stage.show();
		
		
	}
	
	public void handleNoDelete(ActionEvent event)throws IOException
	{
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		FXMLLoader l = new FXMLLoader();
		l.setLocation(SongLib.class.getResource("/view/SongLibView.fxml"));
		AnchorPane backToRoot = (AnchorPane)l.load();
		c = l.getController();
		c.start();
		Scene scene = new Scene(backToRoot, 1000, 600);
		
		if(indexSelected != -1)
		{
			c.displaySongDetails(myLib.songList.get(indexSelected));
			c.listView.getSelectionModel().select(indexSelected); //auto-select the given item
		}
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void handleYesDelete(ActionEvent event)throws IOException
	{
		myLib.deleteSong(myLib.songList.get(indexSelected));
		
		if(myLib.songList.isEmpty())//deleted the only song
		{
			indexSelected = -1; //display nothing
		}
		else if(indexSelected == myLib.songList.size()) //last song NOTE: we didn't put -1 because indexSelected was initialized before delete, but size will be after delete i.e size will already be reduced by 1.
		{
			indexSelected--; //auto select previous song for display
		}
		else
		{
			//auto select next song for display  NOTE: we didn't put +1 because indexSelected was initialized before delete
		}
		
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		FXMLLoader l = new FXMLLoader();
		l.setLocation(SongLib.class.getResource("/view/SongLibView.fxml"));
		AnchorPane backToRoot = (AnchorPane)l.load();
		c = l.getController();
		c.start();
		
		if(indexSelected != -1)
		{
			c.displaySongDetails(myLib.songList.get(indexSelected));
			c.listView.getSelectionModel().select(indexSelected); //auto-select the given item
		}
		
		Scene scene = new Scene(backToRoot, 1000, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	public ArrayList<String> populateObsList() //makes arraylist of songs into arraylist of strings for display
	{
		ArrayList<String> AL = new ArrayList<String>();
		if(!myLib.songList.isEmpty())
		{
			for(int i = 0; i < myLib.songList.size(); i++) 
			{
				Song song = myLib.songList.get(i);
				AL.add(song.name + " by " + song.artist);
			}
		}
			
		return AL;
	}
	
	public void displaySongDetails(Song S)
	{
		songNameText.wrappingWidthProperty().bind(detailsPane.widthProperty());
		artistText.wrappingWidthProperty().bind(detailsPane.widthProperty());
		albumText.wrappingWidthProperty().bind(detailsPane.widthProperty());
		yearText.wrappingWidthProperty().bind(detailsPane.widthProperty());
		
		songNameText.setText("Song Name: "+S.getName());
		artistText.setText("Artist: "+S.getArtist());
		albumText.setText("Album: "+S.getAlbum());
		yearText.setText("Year: "+S.getYear());
		
		
	}
	
	public static void writeToFile() throws Exception {
		
		File file = new File("./src/view/SongList.txt");
		PrintWriter writer = new PrintWriter(file);
		
		for(int i = 0; i < myLib.songList.size(); i++) {
			Song song = myLib.songList.get(i);
			String songString = song.getName() + "|" + song.getArtist() + "|" + song.getAlbum() + "|" + song.getYear();
			
			writer.println(songString);
		}
		
		writer.close();
	}
	
	public static void readFromFile() throws Exception {
		
		File file = new File("./src/view/SongList.txt");
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNext()) {
			StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine(), "|");
			String nameToken = tokenizer.nextToken();
			String artistToken = tokenizer.nextToken();
			String albumToken = tokenizer.nextToken();
			String yearToken = tokenizer.nextToken();
			
			myLib.addSong(nameToken, artistToken, albumToken, yearToken);
		}
		
		scanner.close();
	}
	
	public void start()
	{
		obsList = FXCollections.observableArrayList(populateObsList()); //change this to variable that stores songs .. see lambda expression slide 
 		
		listView.setItems(obsList);
		
		if(beginning && !obsList.isEmpty())
		{
			listView.getSelectionModel().select(0); //auto-select the first item
			indexSelected = listView.getSelectionModel().getSelectedIndex();
			displaySongDetails(myLib.songList.get(indexSelected));
			beginning = false;
		}
		
	}
	
}