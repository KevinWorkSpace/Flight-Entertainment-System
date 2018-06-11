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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	/**
	 * <b>得到电影信息标题的label</b>
	 * @return 电影信息标题的label
	 */
	public Label getInfo_label() {
		return info_label;
	}
	/**
	 * <b>得到电影信息的label</b>
	 * @return 电影信息的label
	 */
	public Label getMovieInfo_label() {
		return movieInfo_label;
	}
	/**
	 * <b>得到播放按钮</b>
	 * @return 播放按钮
	 */
	public Button getPlay_button() {
		return play_button;
	}
	/**
	 * <b>得到后退按钮</b>
	 * @return 后退按钮
	 */
	public Button getBack_button() {
		return back_button;
	}
	/**
	 * <b>得到Properties文件</b>
	 * @param p Properties文件
	 */
	public void setProperties(Properties p) {
		this.p = p;
	}
	/**
	 * <b>设置HomePage</b>
	 * @param hp HomePage类的对象
	 */
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	/**
	 * <b>得到选择电影标题的label</b>
	 * @return 电影标题的label
	 */
	public Label getMovieChoose_label() {
		return movieChoose_label;
	}
	/**
	 * <b>设置是否看过电影的label</b>
	 * @return 是否看过电影的label
	 */
	public Label getSeenBefore_label() {
		return seenBefore_label;
	}
	/**
	 * <b>得到上一个电影的button</b>
	 * @return 上一个电影的button
	 */
	public Button getLastMovie_button() {
		return lastMovie_button;
	}
	/**
	 * <b>选择电影.</b>
	 * <p>
	 * 在电影列表中选中电影之后, 通过对数据库的查询找到对应的电影信息<br>
	 * 然后通过对电影信息的解析, 将信息分行显示到电影信息的label上
	 * </p>
	 * @throws Exception 异常
	 */
	@FXML
	public void selectMovie() throws Exception {
		if(movielist.getSelectionModel().getSelectedItem() != null) {
			movieName = movielist.getSelectionModel().getSelectedItem();
			Statement stmt = hp.getStatement();
			String sql = "select * from movies where name = " + "'" + movieName + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if(!rs.last()) {
				this.movieInfo_label.setText(p.getProperty("noInfo_msg"));
				rs.beforeFirst(); 
			}
			else {
				rs.beforeFirst();
				StringBuffer text = new StringBuffer("");
				while (rs.next()){
					text.append(rs.getString(2) + "\n");
					String info = rs.getString(3);
					int[] a = new int[7];
					int count = 0;
					while(count < 7) {
						for(int i=0; i<info.length()-1; i++) {
							String sb = "";
							for(int j=0; j<2; j++) {
								sb = sb + info.charAt(i+j);
							}
							if(sb.equals("编剧") && count <= 0) {
								a[0] = i;
								String front = "";
								String back = "";
								for(int j=0; j<i-1; j++) {
									front = front + info.charAt(j);
								}
								for(int j=i; j<info.length(); j++) {
									back = back + info.charAt(j);
								}
								info = front + "\n" + back;
								count++;
								break;
							}
							else if(sb.equals("主演") && count <= 1) {
								a[1] = i;
								String front = "";
								String back = "";
								for(int j=0; j<i-1; j++) {
									front = front + info.charAt(j);
								}
								for(int j=i; j<info.length(); j++) {
									back = back + info.charAt(j);
								}
								info = front + "\n" + back;
								count++;
								break;
							}
							else if(sb.equals("类型") && count <= 2) {
								a[2] = i;
								String front = "";
								String back = "";
								for(int j=0; j<i-1; j++) {
									front = front + info.charAt(j);
								}
								for(int j=i; j<info.length(); j++) {
									back = back + info.charAt(j);
								}
								info = front + "\n" + back;
								count++;
								break;
							}
							else if(sb.equals("制片") && count <= 3) {
								a[3] = i;
								String front = "";
								String back = "";
								for(int j=0; j<i-1; j++) {
									front = front + info.charAt(j);
								}
								for(int j=i; j<info.length(); j++) {
									back = back + info.charAt(j);
								}
								info = front + "\n" + back;
								count++;
								break;
							}
							else if(sb.equals("语言") && count <= 4) {
								a[4] = i;
								String front = "";
								String back = "";
								for(int j=0; j<i-1; j++) {
									front = front + info.charAt(j);
								}
								for(int j=i; j<info.length(); j++) {
									back = back + info.charAt(j);
								}
								info = front + "\n" + back;
								count++;
								break;
							}
							else if(sb.equals("上映") && count <= 5) {
								a[5] = i;
								String front = "";
								String back = "";
								for(int j=0; j<i-1; j++) {
									front = front + info.charAt(j);
								}
								for(int j=i; j<info.length(); j++) {
									back = back + info.charAt(j);
								}
								info = front + "\n" + back;
								count++;
								break;
							}
							else if(sb.equals("片长") && count <= 6) {
								a[6] = i;
								String front = "";
								String back = "";
								for(int j=0; j<i-1; j++) {
									front = front + info.charAt(j);
								}
								for(int j=i; j<info.length(); j++) {
									back = back + info.charAt(j);
								}
								info = front + "\n" + back;
								count++;
								break;
							}
						}
					}
					text.append(info);
		        }
				this.movieInfo_label.setText(text.toString());
			}
		}
	}
	/**
	 * <b>播放电影.</b>
	 * <p>
	 * 播放选中的电影
	 * </p>
	 * <ol>
	 *   <li>调用了{@link #updateMedia()}方法</li>
	 * </ol>
	 */
	@FXML
	public void playMovie() {
		if(movielist.getSelectionModel().getSelectedItem() != null) {
			hp.setSeenMovie(true);
			movieName = movielist.getSelectionModel().getSelectedItem();
			hp.setLastMovieName(movieName);
			updateMedia();
		}
	}
	/**
	 * <b>重载播放器</b>
	 */
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
					MEDIA_URL = "file:///" + hp.getMoviePath() + "/" + filelist[i].replaceAll(" ","%20");
					break;
				}
			}
		}
		else {
			MEDIA_URL = "file:///" + hp.getMoviePath().replaceAll(" ","%20");
		}
		Media media = new Media(MEDIA_URL);
		mp = new MediaPlayer(media);
		mp.setAutoPlay(true);
		MediaControl mediaControl = new MediaControl(hp, mp, false);
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
	/**
	 * <b>重载中文版媒体播放器</b>
	 * <ol>
	 *   <li>调用了({@link #updateMedia()}方法</li>
	 * </ol>
	 * @throws Exception 异常
	 */
	public void reloadMediaChineseVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("Chinese.properties")); 
        p = new Properties(); 
        p.load(in);
        updateMedia();
        hp.getRootLayoutController().getMenuCSS().setText(p.getProperty("menuCSS"));
        hp.getRootLayoutController().getMenuLanguage().setText(p.getProperty("menuLanguage"));
	}
	/**
	 * <b>重载英文版媒体播放器</b>
	 * <ol>
	 *   <li>调用了({@link #updateMedia()}方法</li>
	 * </ol>
	 * @throws Exception 异常
	 */
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
		lastMovie_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
					try {
						backToChooseType();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
		back_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
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
                if (event.getCode() == KeyCode.ESCAPE) {
					try {
						backToChooseType();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
                else if (event.getCode() == KeyCode.ENTER) {
					try {
						playMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
                else if (event.getCode() == KeyCode.SPACE) {
					try {
						selectMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
		
		play_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
					try {
						backToChooseType();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
	}
	/**
	 * <b>返回选择电影类型的界面</b>
	 * @throws Exception 异常
	 */
	public void backToChooseType() throws Exception {
		hp.updateType(p);
	}
	/**
	 * <b>观看上一个电影</b>
	 */
	@FXML
	public void continueWatching() {
		if(hp.getLastMovieName() == null) {
			return;
		}
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
		if(flag) {
			String MEDIA_URL = "file:///" + hp.getFilePath() + "/" + movieType + "/" + movieName.replaceAll(" ","%20");;
			if(MEDIA_URL != null) {
				Media media = new Media(MEDIA_URL);
				mp = new MediaPlayer(media);
				mp.setAutoPlay(true);
				MediaControl mediaControl = new MediaControl(this.hp, mp, true);
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
		        mediaControl.setProperties(p);
	        	mediaControl.getBack_button().setText(p.getProperty("back_button"));
	        	this.hp.getStage().setTitle(p.getProperty("mediaStage_title") + " " + movieName);
			}
		}
	}
}
