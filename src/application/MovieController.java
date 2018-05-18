package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	
	@FXML
	private Button back_button;
	
	@FXML
	private ListView<String> movielist;
	
	@FXML
	private Label movieChoose_label;
	
	@FXML
	private Label seenBefore_label;
	
	@FXML
	private Button lastMovie_button;
	
	@FXML
	private Label movieInfo_label;
	
	@FXML
	private Button play_button;
	
	@FXML
	private Label info_label;
	
	public Label getInfo_label() {
		return info_label;
	}
	
	public Label getMovieInfo_label() {
		return movieInfo_label;
	}
	
	public Button getPlay_button() {
		return play_button;
	}
	
	public Button getBack_button() {
		return back_button;
	}
	
	public void setProperties(Properties p) {
		this.p = p;
	}
	
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	public Label getMovieChoose_label() {
		return movieChoose_label;
	}
	
	public Label getSeenBefore_label() {
		return seenBefore_label;
	}
	
	public Button getLastMovie_button() {
		return lastMovie_button;
	}
	
	@FXML
	public void selectMovie() throws Exception {
		if(movielist.getSelectionModel().getSelectedItem() != null) {
			movieName = movielist.getSelectionModel().getSelectedItem();
			Statement stmt = hp.getStatement();
			String sql = "select * from movie where name = " + "'" + movieName + "'"; 
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.getFetchSize() == 0) {
				this.movieInfo_label.setText(p.getProperty("noInfo_msg"));
			}
			else {
				StringBuffer text = new StringBuffer("");
				while (rs.next()){
					text.append(rs.getString(2) + "\n");
		            text.append(rs.getString(3) + "\n");
		            text.append(rs.getString(4) + "\n");
		            text.append(rs.getString(5) + "\n");
		            text.append(rs.getString(6) + "\n");
		        }
				this.movieInfo_label.setText(text.toString());
			}
		}
	}
	
	@FXML
	public void playMovie() {
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
		File file= new File(hp.getMoviePath());
		String MEDIA_URL = null;
		if(file.isDirectory()) {
			String[] filelist = file.list();
			for(int i=0; i<filelist.length; i++) {
				String name = filelist[i].substring(0,filelist[i].lastIndexOf("."));
				if(name.equals(movieName)) {
					MEDIA_URL = "file:///" + hp.getMoviePath() + "/" + filelist[i];
					break;
				}
			}
		}
		else {
			MEDIA_URL = "file:///" + hp.getMoviePath();
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
		File file= new File(hp.getMoviePath());
		if(file.isDirectory()) {
			String[] filelist = file.list();
			for(int i=0; i<filelist.length; i++) {
				String name = filelist[i].substring(0,filelist[i].lastIndexOf("."));
				items.add(name);
			}
		}
		else {
			String name = file.getName().substring(0,file.getName().lastIndexOf("."));
			items.add(name);
		}
		movielist.setItems(items);
		
		back_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						backToChooseType();
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
	
	public void backToChooseType() throws Exception {
		updateType();
	}
	
	public void updateType() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseType.fxml"));
        BorderPane root = (BorderPane)loader.load();
        TypeController typeController = loader.getController();
        typeController.setProperties(p);
        typeController.setHomePage(hp);
        typeController.getBack_button().setText(p.getProperty("back_button"));
        typeController.getCartoon_button().setText(p.getProperty("cartoon_button"));
        typeController.getFiction_button().setText(p.getProperty("fiction_button"));
        typeController.getFunny_button().setText(p.getProperty("funny_button"));
        typeController.getRomance_button().setText(p.getProperty("romance_button"));
        typeController.getChooseType_label().setText(p.getProperty("chooseType_label"));
        typeController.getSearch_button().setText(p.getProperty("search_button"));
        hp.getStage().setTitle(p.getProperty("typeStage_title"));
        hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadTypeChineseVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadTypeEnglishVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        hp.getRootLayout().setCenter(root);
	}
	
	public void reloadTypeChineseVersion() throws Exception {
		  InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
		  p = new Properties(); 
		  p.load(in);
		  updateType();
		  hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
		  hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	
	public void reloadTypeEnglishVersion() throws Exception {
		  InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
		  p = new Properties(); 
		  p.load(in);
		  updateType();
		  hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
		  hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}

	@FXML
	public void continueWatching() {
		movieName = hp.getLastMovieName();
		hp.setLastMovieName(movieName);
		hp.setSeenMovie(true);
		HomePage hp = new HomePage();
		File file= new File(hp.getFilePath());
		String[] filelist = file.list();
		String movieType = null;
		String movieName = null;
		boolean flag = false;
		for(int i=0; i<filelist.length; i++) {
			File f = new File(hp.getFilePath() + "/" + filelist[i]);
			String[] inFilelist = f.list();
			int j=0;
			for(j=0; j<inFilelist.length; j++) {
				String name = inFilelist[j].substring(0,inFilelist[j].lastIndexOf("."));
				if(this.movieName.equals(name)) {
					flag = true;
					movieType = filelist[i];
					movieName = inFilelist[j];
					break;
				}
			}
			if(j != inFilelist.length) {
				break;
			}
		}
		String MEDIA_URL = "file:///" + hp.getFilePath() + "/" + movieType + "/" + movieName;
		if(MEDIA_URL != null) {
			Media media = new Media(MEDIA_URL);
			mp = new MediaPlayer(media);
			mp.setAutoPlay(true);
			MediaControl mediaControl = new MediaControl(mp);
			this.hp.getRootLayout().setCenter(mediaControl);
			this.hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
	            try {
					reloadMediaChineseVersion();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        });
	        this.hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
	            try {
					reloadMediaEnglishVersion();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        });
	        mediaControl.setHomePage(hp);
	        mediaControl.setProperties(p);
        	mediaControl.getBack_button().setText(p.getProperty("back_button"));
        	this.hp.getStage().setTitle(p.getProperty("mediaStage_title") + " " + movieName);
		}
	}
}
