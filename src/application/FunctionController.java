package application;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.event.ActionEvent;
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
	
	public void setProperties(Properties p) {
		this.p = p;
	}
	
	public Properties getProperties() {
		return p;
	}
	
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
	
	public void updateWelcome() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Welcome.fxml"));
        BorderPane root = (BorderPane)loader.load();
        WelcomeController welcomeController = loader.getController();
        welcomeController.setProperties(p);
    	welcomeController.getEnter_button().setText(p.getProperty("enter_button"));
    	welcomeController.getWelcome_label().setText(p.getProperty("welcome_label"));
    	hp.getStage().setTitle(p.getProperty("welcomeStage_title"));
        hp.getRootLayout().setCenter(root);
        hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadWelcomeChineseVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadWelcomeEnglishVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        welcomeController.setHomePage(hp);
	}
	
	@FXML
	public void backToWelcome() throws Exception {
		updateWelcome();
	}
	
	private void reloadWelcomeEnglishVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        updateWelcome();
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}

	private void reloadWelcomeChineseVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        updateWelcome();
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	
	public void updateMovie() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseMovie.fxml"));
        BorderPane moviePane = (BorderPane)loader.load();
        MovieController movieController = loader.getController();
        movieController.setHomePage(hp);
        movieController.setProperties(p);
    	movieController.getBack_button().setText(p.getProperty("back_button"));
    	movieController.getMovieChoose_label().setText(p.getProperty("movieChoose_label"));
    	//如果还没有看过电影
    	if(!hp.haveSeenMovie()) {
    		movieController.getSeenBefore_label().setText(p.getProperty("seenBefore_label"));
    		movieController.getLastMovie_button().setText(p.getProperty("lastMovie_button"));
    	}
    	else {
    		movieController.getSeenBefore_label().setText(p.getProperty(hp.getLastMovieName()));
    		movieController.getLastMovie_button().setText(hp.getLastMovieName());
    	}
    	hp.getStage().setTitle(p.getProperty("movieStage_title"));
        hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadMovieChineseVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadMovieEnglishVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        hp.getRootLayout().setCenter(moviePane);
	}
	
	@FXML
	public void watchMovie() throws Exception {
		updateMovie();
	}
	
	private void reloadMovieEnglishVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        updateMovie();
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}

	private void reloadMovieChineseVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        updateMovie();
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}

	@FXML
	private void initialize() throws Exception {
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
						backToWelcome();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
	}
}
