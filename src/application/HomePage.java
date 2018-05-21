package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomePage extends Application {
	
	private Properties p;
	
	private Stage stage;
	
	private BorderPane rootLayout;
	
	private RootLayoutController rootLayoutController;
	
	private WelcomeController welcomeController;
	
	private boolean seenMovie;
	
	private String lastMovieName;
	
	private String filePath = "F:/Science/Ajava2/Project/Movies";
	
	private String cssPath = "F:/Science/Ajava2/Project/CSS";
	
	private static String moviePath;
	
	private Statement stmt;
	
	private String cssURL;
	/**
	 * <b>得到Statement对象</b>
	 * @return Statement对象
	 */
	public Statement getStatement() {
		return stmt;
	}
	/**
	 * <b>获得css文件夹的路径</b>
	 * @return css文件夹路径
	 */
	public String getCssPath() {
		return cssPath;
	}
	/**
	 * <b>获得movies文件夹的路径</b>
	 * @return movies文件夹的路径
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * <b>设置movies文件夹的路径</b>
	 * @param filePat movies文件夹的路径
	 */
	public void setFilePath(String filePat) {
		filePath = filePat;
	}
	/**
	 * <b>设置电影路径</b>
	 * @param moviePat 电影路径
	 */
	public void setMoviePath(String moviePat) {
		moviePath = moviePat;
	}
	/**
	 * <b>得到电影路径</b>
	 * @return 电影路径
	 */
	public String getMoviePath() {
		return moviePath;
	}
	/**
	 * <b>得到css文件路径</b>
	 * @return css文件路径
	 */
	public String getCssURL() {
		return cssURL;
	}
	/**
	 * <b>得到看的上一个电影的名字</b>
	 * @return 上一个电影的名字
	 */
	public String getLastMovieName() {
		return lastMovieName;
	}
	/**
	 * <b>设置看的上一个电影的名字</b>
	 * @param movieName 上一个电影的名字
	 */
	public void setLastMovieName(String movieName) {
		this.lastMovieName = movieName;
	}
	/**
	 * <b>是否已经看过电影</b>
	 * @return 是否看过电影
	 */
	public boolean haveSeenMovie() {
		return seenMovie;
	}
	/**
	 * <b>设置是否看过电影</b>
	 * @param isSeen 是否看过电影
	 */
	public void setSeenMovie(boolean isSeen) {
		seenMovie = isSeen;
	}
	/**
	 * <b>得到menubar的控制器类对象</b>
	 * @return menubar的控制器类对象
	 */
	public RootLayoutController getRootLayoutController() {
		return rootLayoutController;
	}
	/**
	 * <b>得到menubar的Pane</b>
	 * @return menubar的Pane
	 */
	public BorderPane getRootLayout() {
		return rootLayout;
	}
	/**
	 * <b>得到语言的Properties文件</b>	
	 * @return Properties文件
	 */
	public Properties getProperties() {
		return p;
	}
	/**
	 * <b>得到Stage</b>
	 * @return Stage
	 */
	public Stage getStage() {
		return stage;
	}
	/**
	 * <b>加载程序界面.</b>
	 * <p>
	 * 程序初始化, 实现对数据库的连接, 和对Properties文件的连接, 以及加载menubar和welcome界面<br>
	 * 并且实现了对menubar上的item的事件监听
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #reloadChineseVersion(Properties)}方法</li>
	 *   <li>调用了{@link #reloadEnglishVersion(Properties)}方法</li>
	 *   <li>调用了{@link #changeCSS(String, Scene)}方法</li>
	 * </ol>
	 */
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException, SQLException {
		Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/crashcourse?"
                + "user=root&password=Zhchming1&useUnicode=true&characterEncoding=UTF8";
 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (SQLException e) {
        	System.out.println("MySQL操作错误");
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
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
				reloadChineseVersion(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        rootLayoutController.getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadEnglishVersion(p);
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
	/**
	 * <b>改变风格</b>
	 * @param vname css文件的名字
	 * @param scene 当前的Scene
	 */
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
	/**
	 * <b>重载Welcome界面</b>
	 * @param p Properties文件
	 * @throws Exception 异常
	 */
	public void update(Properties p) throws Exception {
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
        this.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadChineseVersion(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        this.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadEnglishVersion(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
	}
	/**
	 * <b>重载英文版Welcome界面.</b>
	 * <p>
	 * 通过切换英文Properties文件, 并重新加载welcome界面
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #update(Properties)}方法</li>
	 * </ol>
	 * @param p Properties文件
	 * @throws Exception 异常
	 */
	public void reloadEnglishVersion(Properties p) throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        update(p);
	}
	/**
	 * <b>重载中文版Welcome界面.</b>
	 * <p>
	 * 通过切换中文Properties文件, 并重新加载welcome界面
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #update(Properties)}方法</li>
	 * </ol>
	 * @param p Properties文件
	 * @throws Exception 异常
	 */
	public void reloadChineseVersion(Properties p) throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        update(p);
	}
	/**
	 * <b>启动程序</b>
	 * @param args 参数
	 */
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * <b>重载选择电影类型界面</b>
	 * @param p Properties文件
	 * @throws Exception 异常
	 */
	public void updateType(Properties p) throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseType.fxml"));
        BorderPane root = (BorderPane)loader.load();
        TypeController typeController = loader.getController();
        typeController.setHomePage(this);
        typeController.setProperties(p);
        typeController.getBack_button().setText(p.getProperty("back_button"));
        typeController.getCartoon_button().setText(p.getProperty("cartoon_button"));
        typeController.getFiction_button().setText(p.getProperty("fiction_button"));
        typeController.getFunny_button().setText(p.getProperty("funny_button"));
        typeController.getRomance_button().setText(p.getProperty("romance_button"));
        typeController.getChooseType_label().setText(p.getProperty("chooseType_label"));
        typeController.getSearch_button().setText(p.getProperty("search_button"));
        this.getStage().setTitle(p.getProperty("typeStage_title"));
        this.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadTypeChineseVersion(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        this.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadTypeEnglishVersion(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        this.getRootLayout().setCenter(root);
	}
	/**
	 * <b>重载中文版选择电影类型界面.</b>
	 * <p>
	 * 通过切换中文Properties文件, 并重新加载选择电影类型界面
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #updateType(Properties)}方法</li>
	 * </ol>
	 * @param p Properties文件
	 * @throws Exception 异常
	 */
	public void reloadTypeChineseVersion(Properties p) throws Exception {
		  InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
		  p = new Properties(); 
		  p.load(in);
		  updateType(p);
		  this.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
		  this.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	/**
	 * <b>重载英文版选择电影类型界面.</b>
	 * <p>
	 * 通过切换英文Properties文件, 并重新加载选择电影类型界面
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #updateType(Properties)}方法</li>
	 * </ol>
	 * @param p Properties文件
	 * @throws Exception 异常
	 */
	public void reloadTypeEnglishVersion(Properties p) throws Exception {
		  InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
		  p = new Properties(); 
		  p.load(in);
		  updateType(p);
		  this.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
		  this.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	/**
	 * <b>重载选择电影界面.</b>
	 * @param p Properties文件
 	 * @throws Exception 异常
	 */
	public void updateMovie(Properties p) throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseMovie.fxml"));
        BorderPane moviePane = (BorderPane)loader.load();
        MovieController movieController = loader.getController();
        movieController.setHomePage(this);
        movieController.setProperties(p);
    	movieController.getBack_button().setText(p.getProperty("back_button"));
    	movieController.getMovieChoose_label().setText(p.getProperty("movieChoose_label"));
    	movieController.getPlay_button().setText(p.getProperty("play_button"));
    	movieController.getMovieInfo_label().setText("");
    	movieController.getInfo_label().setText(p.getProperty("info_label"));
    	//如果还没有看过电影
    	if(!this.haveSeenMovie()) {
    		movieController.getSeenBefore_label().setText(p.getProperty("seenBefore_label"));
    		movieController.getLastMovie_button().setText(p.getProperty("lastMovie_button"));
    	}
    	else {
    		movieController.getSeenBefore_label().setText(p.getProperty("seenBefore_label"));
    		movieController.getLastMovie_button().setText(this.getLastMovieName());
    	}
    	this.getStage().setTitle(p.getProperty("movieStage_title"));
        this.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadMovieChineseVersion(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        this.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadMovieEnglishVersion(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        this.getRootLayout().setCenter(moviePane);
	}
	/**
	 * <b>重载英文版选择电影界面.</b>
	 * <p>
	 * 通过切换英文Properties文件, 并重新加载选择电影界面
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #updateMovie(Properties)}方法</li>
	 * </ol>
	 * @param p Properties文件
	 * @throws Exception 异常
	 */
	public void reloadMovieEnglishVersion(Properties p) throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
        p = new Properties(); 
        p.load(in);
        updateMovie(p);
        this.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        this.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	/**
	 * <b>重载中文版选择电影界面.</b>
	 * <p>
	 * 通过切换中文Properties文件, 并重新加载选择电影界面
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #updateMovie(Properties)}方法</li>
	 * </ol>
	 * @param p Properties文件
	 * @throws Exception 异常
	 */
	public void reloadMovieChineseVersion(Properties p) throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        updateMovie(p);
        this.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
        this.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
}
