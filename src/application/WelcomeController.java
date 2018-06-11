package application;

import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WelcomeController {
	
	private Properties p; 
	
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
	 * @throws Exception 异常
	 */
	@FXML
	public void enterSystem() throws Exception {
		hp.updateType(p);
	}
}
