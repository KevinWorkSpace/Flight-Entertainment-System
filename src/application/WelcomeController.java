package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class WelcomeController {
	
	private Properties p; 
	
	private FunctionController functionController;
	
	private BorderPane functionPane;
	
	private HomePage hp;
	
	public void setProperties(Properties p) {
		this.p = p;
	}
	
	public Properties getProperties() {
		return p;
	}
	
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	@FXML
	private Button enter_button;
	
	public Button getEnter_button() {
		return enter_button;
	}
	
	@FXML
	private Label welcome_label;
	
	public Label getWelcome_label() {
		return welcome_label;
	}
	
	@FXML
	public void enterSystem() {
		functionController.getAskFunction_label().setText(p.getProperty("askFunction_label"));
        functionController.getMovie_button().setText(p.getProperty("movie_button"));
        functionController.getBack_button().setText(p.getProperty("back_button"));
        hp.getStage().setTitle(p.getProperty("functionStage_title"));
        functionController.setHomePage(hp);
        functionController.setProperties(p);
        hp.getRootLayout().setCenter(functionPane);
        //改变语言, 重新加载界面
        hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadChineseVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadEnglishVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
	}
	
	public void update() {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseFunction.fxml"));
        try {
			functionPane = (BorderPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        functionController = loader.getController();
        functionController.setProperties(p);
        functionController.getAskFunction_label().setText(p.getProperty("askFunction_label"));
        functionController.getMovie_button().setText(p.getProperty("movie_button"));
        functionController.getBack_button().setText(p.getProperty("back_button"));
        functionController.setHomePage(hp);
        functionController.setLanguage("Chinese");
        hp.getRootLayout().setCenter(functionPane);
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
        hp.getStage().setTitle(p.getProperty("functionStage_title"));
	}
	
	public void reloadChineseVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        update();
	}
	
	public void reloadEnglishVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        update();
	}
	
	@FXML
	private void initialize() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseFunction.fxml"));
        functionPane = (BorderPane)loader.load();
        functionController = loader.getController();
        enter_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    enterSystem();
                }
            }
        });
	}
}
