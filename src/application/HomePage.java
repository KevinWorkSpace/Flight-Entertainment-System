package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class HomePage extends Application {
	
	private Properties p;
	
	private Stage stage;
	
	private BorderPane rootLayout;
	
	private RootLayoutController rootLayoutController;
	
	private WelcomeController welcomeController;
	
	private boolean seenMovie;
	
	private String lastMovieName;
	
	private MediaView mediaView;
	
	private String vname;
	
	private String filePath = "F:/Science/Ajava2/Project/Movies";
	
	private String cssPath = "F:/Science/Ajava2/Project/CSS";
	
	public String getCssPath() {
		return cssPath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public String getVname() {
		return vname;
	}
	
	private String cssURL;
	
	public String getCssURL() {
		return cssURL;
	}
	
	public MediaView getMediaView() {
		return mediaView;
	}
	
	public void setMediaView(MediaView mediaView) {
		this.mediaView = mediaView;
	}
	
	public String getLastMovieName() {
		return lastMovieName;
	}
	
	public void setLastMovieName(String movieName) {
		this.lastMovieName = movieName;
	}
	
	public boolean haveSeenMovie() {
		return seenMovie;
	}
	
	public void setSeenMovie(boolean isSeen) {
		seenMovie = isSeen;
	}
	
	public RootLayoutController getRootLayoutController() {
		return rootLayoutController;
	}
	
	public BorderPane getRootLayout() {
		return rootLayout;
	}
		
	public Properties getProperties() {
		return p;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException, SQLException {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        File file= new File(this.getCssPath());
		String[] filelist = file.list();
		cssURL = "file:///" + this.getCssPath() + "/" + filelist[0];
        seenMovie = false;
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();
        rootLayoutController = loader.getController();
        rootLayoutController.setHomePage(this);
        rootLayoutController.getMenuCSS().setText(p.getProperty("menuCSS"));
        Scene scene = new Scene(rootLayout);
        scene.getStylesheets().add(cssURL);
        stage.setScene(scene);
		FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(HomePage.class.getResource("Welcome.fxml"));
        BorderPane root = (BorderPane) loader2.load();
        welcomeController = loader2.getController();
        welcomeController.setHomePage(this);
        welcomeController.setProperties(p);
        welcomeController.getEnter_button().setText(p.getProperty("enter_button"));
        welcomeController.getWelcome_label().setText(p.getProperty("welcome_label"));
        rootLayoutController.getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadChineseVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        rootLayoutController.getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadEnglishVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        rootLayout.setCenter(root);
        
        rootLayoutController.getV1().setOnAction((ActionEvent t) -> {
        	changeCSS(rootLayoutController.getV1().getText(), scene);
        });
        rootLayoutController.getV2().setOnAction((ActionEvent t) -> {
        	changeCSS(rootLayoutController.getV2().getText(), scene);
        });
        rootLayoutController.getV3().setOnAction((ActionEvent t) -> {
        	changeCSS(rootLayoutController.getV3().getText(), scene);
        });
        
        primaryStage.setTitle(p.getProperty("welcomeStage_title"));
        primaryStage.show();
	}
	
	public void changeCSS(String vname, Scene scene) {
		File file= new File(this.getCssPath());
		String[] filelist = file.list();
		String cssURL = null;
		for(int i=0; i<3; i++) {
			String name = filelist[i].substring(0,filelist[i].lastIndexOf("."));
			if(vname.equals(name)) {
				cssURL = "file:///" + this.getCssPath() + "/" + filelist[i];
			}
		}
		scene.getStylesheets().clear();
		scene.getStylesheets().add(cssURL);
	}
	
	public void update() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Welcome.fxml"));
        BorderPane root = (BorderPane)loader.load();
        welcomeController = loader.getController();
        welcomeController.setProperties(p);
        welcomeController.getEnter_button().setText(p.getProperty("enter_button"));
        welcomeController.getWelcome_label().setText(p.getProperty("welcome_label"));
        welcomeController.setHomePage(this);
        rootLayout.setCenter(root);
        rootLayoutController.getMenuCSS().setText(p.getProperty("menuCSS"));
        rootLayoutController.getMenuLanguage().setText(p.getProperty("menuLanguage"));
        this.getStage().setTitle(p.getProperty("welcomeStage_title"));
	}
	
	private void reloadEnglishVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        update();
	}

	private void reloadChineseVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        update();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
