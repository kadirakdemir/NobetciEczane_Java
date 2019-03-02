package application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Promo extends Application {
    @FXML private static Parent _root;
	@FXML private static Stage _stage;
	@FXML private static Scene _scene;
	@Override
	public void start(Stage primaryStage) throws Exception{
    
	}
	
	public void fullScrean()
	{
	   if (_stage.isFullScreen())
	   {
	      _stage.setFullScreen(false);
       }else 
       {
	      _stage.setFullScreen(true);
       }
	}
}
