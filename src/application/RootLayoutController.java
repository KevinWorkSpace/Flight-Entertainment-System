package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class RootLayoutController {
	
	@FXML
	private MenuBar menuBar;
	
	private HomePage hp;
	
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	public MenuBar getMenuBar() {
		return menuBar;
	}
	
	@FXML
	private void initialize() {
		menuBar.getMenus().clear();
		Menu menuLanguage = new Menu("Language");
		menuBar.getMenus().add(menuLanguage);
		MenuItem englishVersion = new MenuItem("English");
		englishVersion.setOnAction((ActionEvent t) -> {
            hp.setLanguage("English");
        });        
		MenuItem chineseVersion = new MenuItem("Chinese");
		chineseVersion.setOnAction((ActionEvent t) -> {
            hp.setLanguage("Chinese");
        });
		menuLanguage.getItems().add(englishVersion);
		menuLanguage.getItems().add(chineseVersion);
	}
	
}
