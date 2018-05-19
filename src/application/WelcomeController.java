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
	
	private TypeController typeController;
	
	private BorderPane typePane;
	
	private HomePage hp;
	
	@FXML
	private Label welcome_label;
	
	@FXML
	private Button enter_button;
	/**
	 * <b>设置语言的Properties文件</b>
	 * @param p Properties文件
	 */
	public void setProperties(Properties p) {
		this.p = p;
	}
	/**
	 * <b>得到语言的Properties文件</b>
	 * @return Properties文件
	 */
	public Properties getProperties() {
		return p;
	}
	/**
	 * <b>设置主页的引用</b>
	 * @param hp 主页对象的引用
	 */
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	/**
	 * <b>得到Enter按钮的对象</b>
	 * @return Enter按钮的对象
	 */
	public Button getEnter_button() {
		return enter_button;
	}
	/**
	 * <b>得到Welcome标题的对象</b>
	 * @return Welcome标题的对象
	 */
	public Label getWelcome_label() {
		return welcome_label;
	}
	/**
	 * <b>进入选择电影类型界面.</b>
	 * <ol>
	 *   <li>调用了{@link #updateType()}方法
	 * </ol>
	 * @throws Exception
	 */
	@FXML
	public void enterSystem() throws Exception {
		updateType();
	}
	/**
	 * <b>重载选择电影类型的界面</b>
	 * @throws Exception
	 */
	public void updateType() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseType.fxml"));
        BorderPane root = (BorderPane)loader.load();
        TypeController typeController = loader.getController();
        typeController.setHomePage(hp);
        typeController.setProperties(p);
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
	/**
	 * <b>重载选择电影类型的中文界面.</b>
	 * <p>
	 * 通过切换中文Properties文件, 并重新加载Type界面
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #updateType()}方法</li>
	 * </ol>
	 * @throws Exception
	 */
	public void reloadTypeChineseVersion() throws Exception {
		  InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
		  p = new Properties(); 
		  p.load(in);
		  updateType();
		  hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
		  hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	/**
	 * <b>重载选择电影类型的中文界面.</b>
	 * <p>
	 * 通过切换中文Properties文件, 并重新加载Type界面
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #updateType()}方法</li>
	 * </ol>
	 * @throws Exception
	 */
	public void reloadTypeEnglishVersion() throws Exception {
		  InputStream in = new BufferedInputStream(new FileInputStream("English.properties")); 
		  p = new Properties(); 
		  p.load(in);
		  updateType();
		  hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
		  hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
	}
	/**
	 * <b>Welcome界面初始化</b>
	 * @throws Exception
	 */
	@FXML
	private void initialize() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseType.fxml"));
        typePane = (BorderPane)loader.load();
        typeController = loader.getController();
        enter_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
						enterSystem();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
	}
}
