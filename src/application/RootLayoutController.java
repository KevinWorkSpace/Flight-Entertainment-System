package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class RootLayoutController {
	
	@FXML
	private MenuBar menuBar;
	
	private Menu menuLanguage;
	
	private Menu menuCSS;
	
	private MenuItem v1;
	
	private MenuItem v2;
	
	private MenuItem v3;
	
	private HomePage hp;
	
	private MenuItem englishVersion;
	
	private MenuItem chineseVersion;
	/**
	 * <b>获取CSS的Menu</b>
	 * @return CSS的Menu对象
	 */
	public Menu getMenuCSS() {
		return menuCSS;
	}
	/**
	 * <b>得到第一个CSS的MenuItem</b>
	 * @return 第一个CSS的MenuItem对象
	 */
	public MenuItem getV1() {
		return v1;
	}
	/**
	 * <b>得到第二个CSS的MenuItem</b>
	 * @return 第二个CSS的MenuItem对象
	 */
	public MenuItem getV2() {
		return v2;
	}
	/**
	 * <b>得到第三个CSS的MenuItem</b>
	 * @return 第三个CSS的MenuItem对象
	 */
	public MenuItem getV3() {
		return v3;
	}
	/**
	 * <b>得到语言的Menu</b>
	 * @return 语言的Menu对象
	 */
	public Menu getMenuLanguage() {
		return menuLanguage;
	}
	/**
	 * <b>得到英文版的MenuItem</b>
	 * @return 英文版的MenuItem对象
	 */
	public MenuItem getEnglishVersion() {
		return englishVersion;
	}
	/**
	 * <b>得到中文版的MenuItem</b>
	 * @return 中文版的MenuItem对象
	 */
	public MenuItem getChineseVersion() {
		return chineseVersion;
	}
	/**
	 * <b>设置HomePage</b>
	 * @param hp HomePage类的对象
	 */
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	@FXML
	private void initialize() {
		menuBar.getMenus().clear();
		menuLanguage = new Menu("语言");
		menuBar.getMenus().add(menuLanguage);
		englishVersion = new MenuItem("English");
		chineseVersion = new MenuItem("中文");
		menuLanguage.getItems().add(englishVersion);
		menuLanguage.getItems().add(chineseVersion);
		menuCSS = new Menu("Style");
		menuBar.getMenus().add(menuCSS);
		v1 = new MenuItem("");
		v2 = new MenuItem("");
		v3 = new MenuItem("");
		menuCSS.getItems().add(v1);
		menuCSS.getItems().add(v2);
		menuCSS.getItems().add(v3);
		HomePage hp = new HomePage();
		File file= new File(hp.getCssPath());
		String[] filelist = file.list();
		for(int i=0; i<3; i++) {
			String name = filelist[i].substring(0,filelist[i].lastIndexOf("."));
			menuCSS.getItems().get(i).setText(name);
		}
	}
	
}
