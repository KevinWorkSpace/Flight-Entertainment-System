package application;

import java.time.temporal.ChronoUnit;
import java.util.Properties;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaControl extends BorderPane {
	
    private MediaPlayer mp;
    
    private HomePage hp;
    
    private MediaView mediaView;
    
    private final boolean repeat = false;
    
    private boolean stopRequested = false;
    
    private boolean atEndOfMedia = false;
    
    private Duration duration;
    
    private Slider timeSlider;
    
    private Label playTime;
    
    private Slider volumeSlider;
    
    private Button back_button;
    
    private HBox mediaBar;
    
    private Properties p;
    /**
     * <b>设置Properties文件</b>
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
     * <b>得到返回按钮</b>
     * @return 返回按钮
     */
    public Button getBack_button() {
    	return back_button;
    }
    /**
     * <b>控制媒体播放</b>
     * @param hp HomePage类的对象的引用
     * @param mp MediaPlayer类对象的引用
     * @param isContinue 是否续看
     */
    public MediaControl(HomePage hp, final MediaPlayer mp, boolean isContinue) {
        this.hp = hp;
    	this.mp = mp;
        setStyle("-fx-background-color: #bfc2c7;");
        mediaView = new MediaView(mp);
        Pane mvPane = new Pane() {                };
        mvPane.getChildren().add(mediaView);
        mvPane.setStyle("-fx-background-color: black;"); 
        setCenter(mvPane);
        mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);
        //Add Back button
        back_button = new Button("返回");
        back_button.setPrefWidth(50);
        back_button.setMinWidth(60);
        back_button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) { 
            	try {
					backToChooseMovie();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });
        back_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
					try {
						backToChooseMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
                if(event.getCode() == KeyCode.ADD) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 + 0.2);
            	}
            	if(event.getCode() == KeyCode.SUBTRACT) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 - 0.2);
            	}
            }
        });
        mediaBar.getChildren().add(back_button);
        Button backup = new Button("<<");
        backup.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
					try {
						backToChooseMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
                else if(event.getCode() == KeyCode.ADD) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 + 0.2);
            	}
                else if(event.getCode() == KeyCode.SUBTRACT) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 - 0.2);
            	}
            }
        });
        backup.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Duration d = Duration.millis(10000);
        		mp.seek(mp.getCurrentTime().subtract(d));
            }
    	});
        mediaBar.getChildren().add(backup);
        final Button playButton  = new Button(">");
        playButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
					try {
						backToChooseMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
                else if(event.getCode() == KeyCode.ADD) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 + 0.2);
            	}
                else if(event.getCode() == KeyCode.SUBTRACT) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 - 0.2);
            	}
            }
        });
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Status status = mp.getStatus();
         
                if (status == Status.UNKNOWN  || status == Status.HALTED)
                {
                   return;
                }
         
                  if ( status == Status.PAUSED
                     || status == Status.READY
                     || status == Status.STOPPED)
                  {
                     if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                     }
                     mp.play();
                     } else {
                       mp.pause();
                     }
                 }
           });
        mp.currentTimeProperty().addListener(new InvalidationListener() 
        {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });
 
        mp.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                    playButton.setText("||");
                }
            }
        });
 
        mp.setOnPaused(new Runnable() {
            public void run() {
                playButton.setText(">");
            }
        });
 
        mp.setOnReady(new Runnable() {
            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
            }
        });
 
        mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setText(">");
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
       });
        
        mediaBar.getChildren().add(playButton);
        Button stepon = new Button(">>");
        stepon.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
					try {
						backToChooseMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
                else if(event.getCode() == KeyCode.ADD) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 + 0.2);
            	}
                else if(event.getCode() == KeyCode.SUBTRACT) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 - 0.2);
            	}
            }
        });
        stepon.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Duration d = Duration.millis(10000);
        		mp.seek(mp.getCurrentTime().add(d));
            }
    	});
        mediaBar.getChildren().add(stepon);
        // Add spacer
        Label spacer = new Label("   ");
        mediaBar.getChildren().add(spacer);
         
        // Add Time label
        Label timeLabel = new Label("Time: ");
        mediaBar.getChildren().add(timeLabel);
         
        // Add time slider
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider,Priority.ALWAYS);
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(Double.MAX_VALUE);
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (timeSlider.isValueChanging()) {
                  mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
               }
            }
        });
        timeSlider.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	if(event.getCode() == KeyCode.ADD) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 + 0.2);
            	}
            	else if(event.getCode() == KeyCode.SUBTRACT) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 - 0.2);
            	}
            	else if(event.getCode() == KeyCode.ESCAPE) {
            		try {
						backToChooseMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
            	}
            	else if(event.getCode() == KeyCode.RIGHT) {
            		Duration d = Duration.millis(10000);
            		mp.seek(mp.getCurrentTime().add(d));
            	}
            	else if(event.getCode() == KeyCode.LEFT) {
            		Duration d = Duration.millis(10000);
            		mp.seek(mp.getCurrentTime().subtract(d));
            	}
            	else if(event.getCode() == KeyCode.UP) {
            		if(isContinue) {
                    	mp.seek(hp.getLastDuration());
                    }
            	}
            }
        });
        mediaBar.getChildren().add(timeSlider);
        
        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        mediaBar.getChildren().add(playTime);
         
        Label volumeLabel = new Label("Vol: ");
        mediaBar.getChildren().add(volumeLabel);
         
        volumeSlider = new Slider();        
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
        volumeSlider.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	if(event.getCode() == KeyCode.ADD) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 + 0.2);
            	}
            	else if(event.getCode() == KeyCode.SUBTRACT) {
            		mp.setVolume(volumeSlider.getValue() / 100.0 - 0.2);
            	}
            	else if(event.getCode() == KeyCode.ESCAPE) {
            		try {
						backToChooseMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
            	}
            }
        });
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (volumeSlider.isValueChanging()) {
                   mp.setVolume(volumeSlider.getValue() / 100.0);
               }
            }
        });
        mediaBar.getChildren().add(volumeSlider);
        setBottom(mediaBar); 
     }
    
    /**
     * <b>返回选择电影的界面</b>
     * @throws Exception 异常
     */
    public void backToChooseMovie() throws Exception {
		mediaView.getMediaPlayer().pause();
		hp.setLastDuration(mp.getCurrentTime());
		hp.updateMovie(p);
	}
	/**
	 * <b>更新播放器控件的Value</b>
	 */
	protected void updateValues() {
	  if (playTime != null && timeSlider != null && volumeSlider != null) {
	     Platform.runLater(new Runnable() {
	        @SuppressWarnings("deprecation")
			public void run() {
	          Duration currentTime = mp.getCurrentTime();
	          playTime.setText(formatTime(currentTime, duration));
	          timeSlider.setDisable(duration.isUnknown());
	          if (!timeSlider.isDisabled() 
	            && duration.greaterThan(Duration.ZERO) 
	            && !timeSlider.isValueChanging()) {
	              timeSlider.setValue(currentTime.divide(duration).toMillis()
	                  * 100.0);
	          }
	          if (!volumeSlider.isValueChanging()) {
	            volumeSlider.setValue((int)Math.round(mp.getVolume() 
	                  * 100));
	          }
	        }
	     });
	  }
	}

	private static String formatTime(Duration elapsed, Duration duration) {
	   int intElapsed = (int)Math.floor(elapsed.toSeconds());
	   int elapsedHours = intElapsed / (60 * 60);
	   if (elapsedHours > 0) {
	       intElapsed -= elapsedHours * 60 * 60;
	   }
	   int elapsedMinutes = intElapsed / 60;
	   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
	                           - elapsedMinutes * 60;
	 
	   if (duration.greaterThan(Duration.ZERO)) {
	      int intDuration = (int)Math.floor(duration.toSeconds());
	      int durationHours = intDuration / (60 * 60);
	      if (durationHours > 0) {
	         intDuration -= durationHours * 60 * 60;
	      }
	      int durationMinutes = intDuration / 60;
	      int durationSeconds = intDuration - durationHours * 60 * 60 - 
	          durationMinutes * 60;
	      if (durationHours > 0) {
	         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
	            elapsedHours, elapsedMinutes, elapsedSeconds,
	            durationHours, durationMinutes, durationSeconds);
	      } else {
	          return String.format("%02d:%02d/%02d:%02d",
	            elapsedMinutes, elapsedSeconds,durationMinutes, 
	                durationSeconds);
	      }
	      } else {
	          if (elapsedHours > 0) {
	             return String.format("%d:%02d:%02d", elapsedHours, 
	                    elapsedMinutes, elapsedSeconds);
	            } else {
	                return String.format("%02d:%02d",elapsedMinutes, 
	                    elapsedSeconds);
	            }
	        }
	    }
	}