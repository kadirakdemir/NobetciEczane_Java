package Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class PromoController implements Initializable {
	   
    @FXML private MediaView promoMediaView;
    private MediaPlayer mp;
    private Media media;
 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        promoPlay();
    }

    public void promoPlay(){
            String path=new File("src/Promos/NobetciReklamlar.mp4").getAbsolutePath();
            if (path!=null){
                media=new Media(new File(path).toURI().toString());
                mp = new MediaPlayer(media);
                mp.setCycleCount(MediaPlayer.INDEFINITE);
                promoMediaView.setMediaPlayer(mp);
                mp.play();
                final DoubleProperty width=promoMediaView.fitWidthProperty();
                final DoubleProperty height=promoMediaView.fitHeightProperty();
                width.bind(Bindings.selectDouble(promoMediaView.sceneProperty(),"width"));
                height.bind(Bindings.selectDouble(promoMediaView.sceneProperty(),"height"));
            }

    }
}
