package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
	
	private Properties p;
	
	private MediaPlayer mp;
	
	private String movieName;
	
	public void setProperties(Properties p) {
		this.p = p;
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
	private Label seenBefore_label;
	
	@FXML
	private Button lastMovie_button;
	
	public Label getSeenBefore_label() {
		return seenBefore_label;
	}
	
	public Button getLastMovie_button() {
		return lastMovie_button;
	}
	
	@FXML
	public void selectMovie() throws Exception {
		if(movielist.getSelectionModel().getSelectedItem() != null) {
			hp.setSeenMovie(true);
			movieName = movielist.getSelectionModel().getSelectedItem();
			hp.setLastMovieName(movieName);
			updateMedia();
		}
	}
	
	public void updateMedia() {
		if(mp != null)
			mp.pause();
		File file= new File(hp.getFilePath());
		String[] filelist = file.list();
		String MEDIA_URL = null;
		for(int i=0; i<filelist.length; i++) {
			String name = filelist[i].substring(0,filelist[i].lastIndexOf("."));
			if(name.equals(movieName)) {
				MEDIA_URL = "file:///" + hp.getFilePath() + "/" + filelist[i];
				break;
			}
		}
		Media media = new Media(MEDIA_URL);
		mp = new MediaPlayer(media);
		mp.setAutoPlay(true);
		MediaControl mediaControl = new MediaControl(mp);
		mediaControl.setProperties(p);
		hp.getRootLayout().setCenter(mediaControl);
		hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadMediaChineseVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadMediaEnglishVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        mediaControl.setHomePage(hp);
        mediaControl.setProperties(p);
        mediaControl.getBack_button().setText(p.getProperty("back_button"));
    	hp.getStage().setTitle(p.getProperty("mediaStage_title") + " " + movieName);
	}
	
	public void reloadMediaChineseVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        updateMedia();
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
	}
	
	public void reloadMediaEnglishVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        updateMedia();
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
	}
	
	
	@FXML
	private void initialize() throws Exception {
		ObservableList<String> items =FXCollections.observableArrayList();
		HomePage hp = new HomePage();
		File file= new File(hp.getFilePath());
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
	
	public void updateFunction() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseFunction.fxml"));
        BorderPane root = (BorderPane)loader.load();
        FunctionController functionController = loader.getController();
        functionController.setProperties(p);
    	functionController.getAskFunction_label().setText(p.getProperty("askFunction_label"));
    	functionController.getBack_button().setText(p.getProperty("back_button"));
    	functionController.getMovie_button().setText(p.getProperty("movie_button"));
    	hp.getStage().setTitle(p.getProperty("functionStage_title"));
        functionController.setHomePage(hp);
      //改变语言, 重新加载界面
        hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadFunctionChineseVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadFunctionEnglishVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        hp.getRootLayout().setCenter(root);
	}
	
	@FXML
	public void backToChooseFunction() throws Exception {
		updateFunction();
	}
	
	public void reloadFunctionChineseVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        updateFunction();
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	
	public void reloadFunctionEnglishVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        updateFunction();
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}

	@FXML
	public void continueWatching() {
		movieName = hp.getLastMovieName();
		hp.setLastMovieName(movieName);
		hp.setSeenMovie(true);
		File file= new File(hp.getFilePath());
		String[] filelist = file.list();
		String MEDIA_URL = null;
		for(int i=0; i<filelist.length; i++) {
			String name = filelist[i].substring(0,filelist[i].lastIndexOf("."));
			if(name.equals(movieName)) {
				MEDIA_URL = "file:///" + hp.getFilePath() + "/" + filelist[i];
				break;
			}
		}
		if(MEDIA_URL != null) {
			Media media = new Media(MEDIA_URL);
			mp = new MediaPlayer(media);
			mp.setAutoPlay(true);
			MediaControl mediaControl = new MediaControl(mp);
			hp.getRootLayout().setCenter(mediaControl);
			hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
	            try {
					reloadMediaChineseVersion();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        });
	        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
	            try {
					reloadMediaEnglishVersion();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        });
	        mediaControl.setHomePage(hp);
	        mediaControl.setProperties(p);
        	mediaControl.getBack_button().setText(p.getProperty("back_button"));
        	hp.getStage().setTitle(p.getProperty("mediaStage_title") + " " + movieName);
		}
	}
}
