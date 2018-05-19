package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	
	@FXML
	private TextField search_text;
	
	@FXML
	private Button search_button;
	
	private HomePage hp;
	
	private Properties p;
	/**
	 * <b>得到search按钮</b>
	 * @return search按钮的对象
	 */
	public Button getSearch_button() {
		return search_button;
	}
	/**
	 * <b>得到选择电影类型的标题</b>
	 * @return 标题的对象
	 */
	public Label getChooseType_label() {
		return chooseType_label;
	}
	/**
	 * <b>得到funny按钮</b>
	 * @return 按钮的对象
	 */
	public Button getFunny_button() {
		return funny_button;
	}
	/**
	 * <b>得到fiction按钮</b>
	 * @return 按钮的对象
	 */ 
	public Button getFiction_button() {
		return fiction_button;
	}
	/**
	 * <b>得到romance按钮</b>
	 * @return 按钮的对象
	 */
	public Button getRomance_button() {
		return romance_button;
	}
	/**
	 * <b>得到cartoon按钮</b>
	 * @return 按钮的对象
	 */
	public Button getCartoon_button() {
		return cartoon_button;
	}
	/**
	 * <b>得到back按钮</b>
	 * @return 按钮的对象
	 */
	public Button getBack_button() {
		return back_button;
	}
	/**
	 * <b>设置主页</b>
	 * @param hp 主页的对象
	 */
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	/**
	 * <b>设置Properties文件</b>
	 * @param p
	 */
	public void setProperties(Properties p) {
		this.p = p;
	}
	/**
	 * <b>进入funny类型的电影列表</b>
	 * @throws Exception
	 */
	@FXML
	public void enterFunnyMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/FunnyMovies");
		updateMovie();
	}
	/**
	 * <b>进入fiction类型的电影列表</b>
	 * @throws Exception
	 */
	@FXML
	public void enterFictionMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/FictionMovies");
		updateMovie();
	}
	/**
	 * <b>进入romance类型的电影列表</b>
	 * @throws Exception
	 */
	@FXML
	public void enterRomanceMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/RomanceMovies");
		updateMovie();
	}
	/**
	 * <b>进入cartoon类型的电影列表</b>
	 * @throws Exception
	 */
	@FXML
	public void enterCartoonMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/CartoonMovies");
		updateMovie();
	}
	/**
	 * <b>在Movies文件夹中搜索电影</b>
	 * <p>
	 * 首先对Movies文件夹中的四个不同电影类型的文件夹的遍历<br>
	 * 找到与搜索框中输入的电影名字一样的电影, 并跳转到电影列表界面
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #updateMovie()}方法
	 * </ol>
	 * @throws Exception
	 */
	@FXML
	public void search() throws Exception {
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
				if(search_text.getText().equals(name)) {
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
		if(flag) {
			this.hp.setMoviePath(hp.getFilePath() + "/" + movieType + "/" + movieName);
			updateMovie();
		}
	}
	/**
	 * <b>重载电影列表界面</b>
	 * @throws Exception
	 */
	public void updateMovie() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseMovie.fxml"));
        BorderPane moviePane = (BorderPane)loader.load();
        MovieController movieController = loader.getController();
        movieController.setHomePage(hp);
        movieController.setProperties(p);
    	movieController.getBack_button().setText(p.getProperty("back_button"));
    	movieController.getMovieChoose_label().setText(p.getProperty("movieChoose_label"));
    	movieController.getPlay_button().setText(p.getProperty("play_button"));
    	movieController.getMovieInfo_label().setText("");
    	movieController.getInfo_label().setText(p.getProperty("info_label"));
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
	/**
	 * <b>重载Welcome界面</b>
	 * @throws Exception
	 */
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
	/**
	 * <b>返回Welcome界面</b>
	 * @throws Exception
	 */
	@FXML
	public void backToWelcome() throws Exception {
		updateWelcome();
	}
	/**
	 * <b>重载Welcome的英文界面</b>
	 * @throws Exception
	 */
	private void reloadWelcomeEnglishVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        updateWelcome();
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	/**
	 * <b>重载Welcome的中文界面</b>
	 * @throws Exception
	 */
	private void reloadWelcomeChineseVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        updateWelcome();
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	/**
	 * <b>重载电影列表的英文界面</b>
	 * @throws Exception
	 */
	private void reloadMovieEnglishVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        updateMovie();
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	/**
	 * 重载电影列表的中文界面
	 * @throws Exception
	 */
	private void reloadMovieChineseVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        updateMovie();
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	@FXML
	private void initialize() {
		funny_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						enterFunnyMovieList();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
		this.fiction_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						enterFictionMovieList();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
		this.romance_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						enterRomanceMovieList();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
		this.cartoon_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						enterCartoonMovieList();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
	}
	
}
