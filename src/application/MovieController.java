package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MovieController {
	
	private HomePage hp;
	
	private String language;
	
	private Properties p;
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@FXML
	private Button back_button;
	
	public Button getBack_button() {
		return back_button;
	}

	@FXML
	private ListView<String> movielist;
	
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	@FXML
	private Label movieChoose_label;
	
	public Label getMovieChoose_label() {
		return movieChoose_label;
	}
	
	@FXML
	public void selectMovie() throws Exception {
		String movieName = null;
		if(movielist.getSelectionModel().getSelectedItem() != null) {
			movieName = movielist.getSelectionModel().getSelectedItem();
		File file= new File("Movies");
		String[] filelist = file.list();
		String MEDIA_URL = null;
		for(int i=0; i<filelist.length; i++) {
			String name = filelist[i].substring(0,filelist[i].lastIndexOf("."));
			if(name.equals(movieName)) {
				MEDIA_URL = "file:///F:/Science/Ajava2/Project/Movies/" + filelist[i];
				break;
			}
		}
		Media media = new Media(MEDIA_URL);
		MediaPlayer mp = new MediaPlayer(media);
		mp.setAutoPlay(true);
		
//			FXMLLoader loader = new FXMLLoader();
//	        loader.setLocation(getClass().getResource("Media.fxml"));
//	        BorderPane mediaPane = (BorderPane)loader.load();
//	        MediaController mediaController = loader.getController();
			MediaControl mediaControl = new MediaControl(mp);
			hp.getRootLayout().setCenter(mediaControl);
	        mediaControl.setHomePage(hp);
	        mediaControl.setLanguage(language);
	        if(language.equals("Chinese")) {
	        	mediaControl.getBack_button().setText(p.getProperty("back_button_CN"));
	        	hp.getStage().setTitle(p.getProperty("mediaStage_title_CN") + " " + movieName);
	        }
	        else if(language.equals("English")) {
	        	mediaControl.getBack_button().setText(p.getProperty("back_button_US"));
	        	hp.getStage().setTitle(p.getProperty("mediaStage_title_US") + " " + movieName);
	        }
		}
	}
	
	
	@FXML
	private void initialize() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("test.properties")); 
        p = new Properties(); 
        p.load(in);
		ObservableList<String> items =FXCollections.observableArrayList();
		File file= new File("Movies");
		String[] filelist = file.list();
		for(int i=0; i<filelist.length; i++) {
			String name = filelist[i].substring(0,filelist[i].lastIndexOf("."));
			items.add(name);
		}
		movielist.setItems(items);
		back_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						backToChooseFunction();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
		movielist.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						selectMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
	}
	
	@FXML
	public void backToChooseFunction() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseFunction.fxml"));
        BorderPane root = (BorderPane)loader.load();
        FunctionController functionController = loader.getController();
        if(language.equals("Chinese")) {
        	functionController.getAskFunction_label().setText(p.getProperty("askFunction_label_CN"));
        	functionController.getBack_button().setText(p.getProperty("back_button_CN"));
        	functionController.getMovie_button().setText(p.getProperty("movie_button_CN"));
        	hp.getStage().setTitle(p.getProperty("functionStage_title_CN"));
        }
        else if(language.equals("English")) {
        	functionController.getAskFunction_label().setText(p.getProperty("askFunction_label_US"));
        	functionController.getBack_button().setText(p.getProperty("back_button_US"));
        	functionController.getMovie_button().setText(p.getProperty("movie_button_US"));
        	hp.getStage().setTitle(p.getProperty("functionStage_title_US"));
        }
        functionController.setLanguage(language);
        functionController.setHomePage(hp);
        hp.getRootLayout().setCenter(root);
	}
	
}
