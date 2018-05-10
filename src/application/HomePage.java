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
	
	private Statement stmt;
	
	private Properties p;
	
	private Stage stage;
	
	private String language;
	
	private BorderPane rootLayout;
	
	private RootLayoutController rootLayoutController;
	
	private WelcomeController welcomeController;
	
	private boolean seenMovie;
	
	private String lastMovieName;
	
	private MediaView mediaView;
	
	private String vname;
	
	private String filePath = "F:/Science/Ajava2/Project/Movies";
	
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
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public RootLayoutController getRootLayoutController() {
		return rootLayoutController;
	}
	
	public BorderPane getRootLayout() {
		return rootLayout;
	}
		
	public Statement getStatement() {
		return stmt;
	}
	
	public Properties getProperties() {
		return p;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException, SQLException {
		InputStream in = new BufferedInputStream(new FileInputStream("test.properties")); 
        p = new Properties(); 
        p.load(in);
        File file= new File("F:\\Science\\Ajava2\\Project\\CSS");
		String[] filelist = file.list();
		cssURL = "file:///F:/Science/Ajava2/Project/CSS/" + filelist[0];
        seenMovie = false;
		language = "Chinese";
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();
        rootLayoutController = loader.getController();
        rootLayoutController.setHomePage(this);
        Scene scene = new Scene(rootLayout);
        scene.getStylesheets().add(cssURL);
        stage.setScene(scene);
		FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(HomePage.class.getResource("Welcome.fxml"));
        BorderPane root = (BorderPane) loader2.load();
        welcomeController = loader2.getController();
        welcomeController.setHomePage(this);
        rootLayoutController.getChineseVersion().setOnAction((ActionEvent t) -> {
            reloadChineseVersion();
        });
        rootLayoutController.getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadEnglishVersion();
			} catch (IOException e) {
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
        
        primaryStage.setTitle("Welcome");
        primaryStage.show();
	}
	
	public void changeCSS(String vname, Scene scene) {
		File file= new File("F:\\Science\\Ajava2\\Project\\CSS");
		String[] filelist = file.list();
		String cssURL = null;
		for(int i=0; i<3; i++) {
			String name = filelist[i].substring(0,filelist[i].lastIndexOf("."));
			if(vname.equals(name)) {
				cssURL = "file:///F:/Science/Ajava2/Project/CSS/" + filelist[i];
			}
		}
		scene.getStylesheets().clear();
		scene.getStylesheets().add(cssURL);
	}
	
	private void reloadEnglishVersion() throws IOException {
		this.setLanguage("English");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Welcome.fxml"));
        BorderPane root = (BorderPane)loader.load();
        welcomeController = loader.getController();
        welcomeController.getEnter_button().setText(p.getProperty("enter_button_US"));
        welcomeController.getWelcome_label().setText(p.getProperty("welcome_label_US"));
        welcomeController.setHomePage(this);
        welcomeController.setLanguage("English");
        rootLayout.setCenter(root);
        rootLayoutController.getMenuLanguage().setText(p.getProperty("menuLanguage_CN"));
        this.getStage().setTitle(p.getProperty("welcomeStage_title_US"));
	}

	private void reloadChineseVersion() {
		this.setLanguage("Chinese");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Welcome.fxml"));
        BorderPane root = null;
        try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        welcomeController = loader.getController();
        welcomeController.getEnter_button().setText(p.getProperty("enter_button_CN"));
        welcomeController.getWelcome_label().setText(p.getProperty("welcome_label_CN"));
        welcomeController.setHomePage(this);
        welcomeController.setLanguage("Chinese");
        rootLayout.setCenter(root);
        rootLayoutController.getMenuLanguage().setText(p.getProperty("menuLanguage_US"));
        this.getStage().setTitle(p.getProperty("welcomeStage_title_CN"));
	}

	public static void main(String[] args) {
		launch(args);
	}
}
