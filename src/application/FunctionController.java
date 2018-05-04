package application;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class FunctionController {
	
	@FXML
	private Button movie_button;
	
	@FXML
	private Label askFunction_label;
	
	@FXML
	private Button back_button;
	
	private String language;
	
	private Properties p;
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public Button getBack_button() {
		return back_button;
	}
	
	public Button getMovie_button() {
		return movie_button;
	}

	public Label getAskFunction_label() {
		return askFunction_label;
	}

	
	private HomePage hp;
	
	
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	@FXML
	public void backToChooseLanguage() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseLanguage.fxml"));
        BorderPane root = (BorderPane)loader.load();
        LanguageController languageController = loader.getController();
        hp.getRootLayout().setCenter(root);
        languageController.setHomePage(hp);
        hp.getStage().setTitle(p.getProperty("Choose language"));
	}
	
	@FXML
	public void watchMovie() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseMovie.fxml"));
        BorderPane moviePane = (BorderPane)loader.load();
        MovieController movieController = loader.getController();
        movieController.setHomePage(hp);
        if(language.equals("Chinese")) {
        	movieController.getBack_button().setText(p.getProperty("back_button_CN"));
        	movieController.getMovieChoose_label().setText(p.getProperty("movieChoose_label_CN"));
        	movieController.setLanguage("Chinese");
        	hp.getStage().setTitle(p.getProperty("movieStage_title_CN"));
        }
        else if(language.equals("English")) {
        	movieController.getBack_button().setText(p.getProperty("back_button_US"));
        	movieController.getMovieChoose_label().setText(p.getProperty("movieChoose_label_US"));
        	movieController.setLanguage("English");
        	hp.getStage().setTitle(p.getProperty("movieStage_title_US"));
        }
        hp.getRootLayout().setCenter(moviePane);
	}
	
	@FXML
	private void initialize() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("test.properties")); 
        p = new Properties(); 
        p.load(in);
        movie_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						watchMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
        back_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						backToChooseLanguage();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
	}
	
}
