package application;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TypeController {
	@FXML
	private Label chooseType_label;
	@FXML
	private Button funny_button;
	@FXML
	private Button fiction_button;
	@FXML
	private Button romance_button;
	@FXML
	private Button cartoon_button;
	@FXML
	private Button back_button;
	
	private HomePage hp;
	
	private Properties p;
	
	public Label getChooseType_label() {
		return chooseType_label;
	}

	public Button getFunny_button() {
		return funny_button;
	}

	public Button getFiction_button() {
		return fiction_button;
	}

	public Button getRomance_button() {
		return romance_button;
	}

	public Button getCartoon_button() {
		return cartoon_button;
	}

	public Button getBack_button() {
		return back_button;
	}

	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	public void setProperties(Properties p) {
		this.p = p;
	}
	
	@FXML
	public void enterFunnyMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/FunnyMovies");
		updateMovie();
	}
	
	@FXML
	public void enterFictionMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/FictionMovies");
		updateMovie();
	}
	
	@FXML
	public void enterRomanceMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/RomanceMovies");
		updateMovie();
	}

	@FXML
	public void enterCartoonMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/CartoonMovies");
		updateMovie();
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
	
}
