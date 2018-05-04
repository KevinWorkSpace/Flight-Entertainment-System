package application;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomePage extends Application {
	
	private Statement stmt;
	
	private Properties p;
	
	private Stage stage;
	
	private BorderPane rootLayout;
	
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
	
	public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException, SQLException {
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();
        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
		FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(HomePage.class.getResource("ChooseLanguage.fxml"));
        BorderPane root = (BorderPane) loader2.load();
        LanguageController languageController = loader2.getController();
        languageController.setHomePage(this);
        rootLayout.setCenter(root);
        primaryStage.setTitle("Choose language");
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
