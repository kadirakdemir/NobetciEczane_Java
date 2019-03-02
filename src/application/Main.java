package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Main extends Application {

    @FXML public static Parent _root;
    @FXML public static Stage _stage;
	@FXML public static Scene _scene;

	@Override
	public void start(Stage primaryStage) {
		try {
			    _root = FXMLLoader.load(getClass().getResource("/View/nobetci.fxml"));
		        primaryStage.setTitle("MK Yazılım");
		        _scene=new Scene(_root,1100,550);
		        _scene.getStylesheets().add(getClass().getResource("/Content/application.css").toExternalForm());
		        primaryStage.setScene(_scene);
		        _stage=primaryStage;
		        primaryStage.setFullScreen(false);
		        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	 public void fullScrean(){
	        if (_stage.isFullScreen()){
	            _stage.setFullScreen(false);
	        }else {
	            _stage.setFullScreen(true);
	        }
	    }
	public static void main(String[] args) {
		launch(args);
	}
}
