package application;

import java.io.File;

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
	
	public MenuItem getV1() {
		return v1;
	}
	
	public MenuItem getV2() {
		return v2;
	}
	
	public MenuItem getV3() {
		return v3;
	}
	
	public Menu getMenuLanguage() {
		return menuLanguage;
	}
	
	public MenuItem getEnglishVersion() {
		return englishVersion;
	}
	
	public MenuItem getChineseVersion() {
		return chineseVersion;
	}
	
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	public MenuBar getMenuBar() {
		return menuBar;
	}
	
	@FXML
	private void initialize() {
		menuBar.getMenus().clear();
		menuLanguage = new Menu("Language");
		menuBar.getMenus().add(menuLanguage);
		englishVersion = new MenuItem("English");
		englishVersion.setOnAction((ActionEvent t) -> {
            hp.setLanguage("English");
        });        
		chineseVersion = new MenuItem("中文");
		chineseVersion.setOnAction((ActionEvent t) -> {
            hp.setLanguage("Chinese");
        });
		menuLanguage.getItems().add(englishVersion);
		menuLanguage.getItems().add(chineseVersion);
//		menuCSS = new Menu("Style");
//		menuBar.getMenus().add(menuCSS);
//		v1 = new MenuItem("");
//		v2 = new MenuItem("");
//		v3 = new MenuItem("");
//		menuCSS.getItems().add(v1);
//		menuCSS.getItems().add(v2);
//		menuCSS.getItems().add(v3);
//		File file= new File("F:\\Science\\Ajava2\\Project\\CSS");
//		String[] filelist = file.list();
//		for(int i=0; i<3; i++) {
//			String name = filelist[i].substring(0,filelist[i].lastIndexOf("."));
//			menuCSS.getItems().get(i).setText(name);
//		}
	}
	
}
