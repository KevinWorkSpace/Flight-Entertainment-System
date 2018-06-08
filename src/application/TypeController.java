package application;

import java.io.File;
import java.util.Properties;
import java.util.regex.Pattern;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
	
	@FXML
	private Label notion_label;
	
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
	 * @param p Properties文件
	 */
	public void setProperties(Properties p) {
		this.p = p;
	}
	/**
	 * <b>进入funny类型的电影列表</b>
	 * @throws Exception 异常
	 */
	@FXML
	public void enterFunnyMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/FunnyMovies");
		hp.updateMovie(p);
	}
	/**
	 * <b>进入fiction类型的电影列表</b>
	 * @throws Exception 异常
	 */
	@FXML
	public void enterFictionMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/FictionMovies");
		hp.updateMovie(p);
	}
	/**
	 * <b>进入romance类型的电影列表</b>
	 * @throws Exception 异常
	 */
	@FXML
	public void enterRomanceMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/RomanceMovies");
		hp.updateMovie(p);
	}
	/**
	 * <b>进入cartoon类型的电影列表</b>
	 * @throws Exception 异常
	 */
	@FXML
	public void enterCartoonMovieList() throws Exception {
		hp.setMoviePath(hp.getFilePath() + "/CartoonMovies");
		hp.updateMovie(p);
	}
	/**
	 * <b>在Movies文件夹中搜索电影</b>
	 * <p>
	 * 首先对Movies文件夹中的四个不同电影类型的文件夹的遍历<br>
	 * 找到与搜索框中输入的电影名字一样的电影, 并跳转到电影列表界面
	 * </p>
	 * @throws Exception 异常
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
				boolean isMatch = Pattern.matches(".*" + search_text.getText() + ".*", name);
				if(isMatch) {
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
			this.hp.updateMovie(p);
		}
		else {
			this.notion_label.setText(p.getProperty("notion_label"));
		}
	}
	/**
	 * <b>返回Welcome界面</b>
	 * @throws Exception 异常
	 */
	@FXML
	public void backToWelcome() throws Exception {
		hp.update(p);
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
