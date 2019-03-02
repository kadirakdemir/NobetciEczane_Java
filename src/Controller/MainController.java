package Controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.*;

import DataConnection.NobetciDBConnection;
import Models.Nobetci;
import Services.AyarService;
import Services.NobetciService;
import Utilities.DateUtility;
import Utilities.HttpDownloadUtility;
import application.Main;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.sun.prism.PhongMaterial;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MainController implements Initializable, MapComponentInitializedListener {


    //region Description
    private NobetciDBConnection _connection;
    private AyarService _ayarService = new AyarService(_connection);
    private NobetciService _nobetciService = new NobetciService(_connection, _ayarService);

    @FXML
    public Label ilceLabel = new Label();

    @FXML
    public Label tarihLabel = new Label();
    public Label clockLabel = new Label();
    public Label eczaneAdLabel=new Label();
    public Label eczaneAdresLabel =new Label();
    public Label eczaneTelLabel =new Label();

    @FXML public ImageView qrImageView;
    @FXML public VBox vBox;

    @FXML
    public GridPane gridPane = new GridPane();

    @FXML
    public ContextMenu contextMenu = new ContextMenu();

    @FXML
    private GoogleMapView mapWebView;

    private GoogleMap map;

    @FXML
    public MediaView promoMediaView;
    public MediaPlayer mp;
    public Media media;
    int sayac1 = 0;
    int sayac2=0;
    int nobetciSayisi=0;
    List<Nobetci> nobetciList=new ArrayList<Nobetci>();
    List<VBox> listVBox=new ArrayList<>();
  // List<Nobetci> nobetciList;
 //  List<VBox> listVBox;
    VBox vBoxC;
    File files[];
    public ArrayList<Image> qrImageList;
    boolean flag=false;

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _connection = new NobetciDBConnection();
        _connection.Connect();

        mapWebView.addMapInializedListener(this);

        Task<Void> task = new GuiUpdater(this);
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        promoControl();
        QRUtility();
        sqlEkran2();


    }

    @Override
    public void mapInitialized() {
        LatLong fredWilkieLocation = new LatLong(47.6597, -122.3357);
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);
        map = mapWebView.createMap(mapOptions);


    }

    private class GuiUpdater extends Task<Void> {

        MainController ctrl;

        GuiUpdater(MainController ctrl) {
            this.ctrl = ctrl;
        }

        @Override
        protected Void call() throws Exception {
            makeChanges();
            return null;
        }

        private void makeChanges() {
            while (true) {
                try {
                    Platform.runLater(() -> {
                        Calendar calendar = new GregorianCalendar();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        ctrl.clockLabel.setText(hour+":"+minute);
                    });
                    Thread.sleep(1000);
                } catch (Exception ex) {
                }
            }
        }
    }

    private class SqlUpdater extends Task<Void> {

        FlagClass flagClass;

        SqlUpdater(FlagClass flagClass) {
            this.flagClass = flagClass;
        }

        @Override
        protected Void call() throws Exception {
            makeChanges();
            return null;
        }

        private void makeChanges() {
            while (true){
             try
             {
                 synchronized (flagClass)
                 {
                     if (flag==false)
                     {
                          Platform.runLater(() -> {
                              if (sayac2==nobetciSayisi)
                              {
                                  sayac2=0;
                              }
                              if(nobetciSayisi!=0)
                              {
                                  eczaneAdLabel.setText(nobetciList.get(sayac2).nobetciAd);
                                  eczaneAdresLabel.setWrapText(true);
                                  eczaneAdresLabel.setText(nobetciList.get(sayac2).nobetciAdres);
                                  eczaneTelLabel.setText(nobetciList.get(sayac2).nobetciTel);

                                  qrImageView.setImage(qrImageList.get(sayac2));

                                  for (int sutun = 0; sutun < nobetciSayisi; sutun++)
                                  {
                                      if (sayac1 == sayac2)
                                      {
                                        vBoxC = listVBox.get(sayac1);
                                        vBoxC.setStyle("-fx-background-image: url('/images/cerceve1.7.png');" + "-fx-max-width: 235;"+"-fx-translate-x: 0;"+"-fx-translate-y: 0;");
                                      }
                                      else
                                      {
                                         vBoxC = listVBox.get(sayac1);
                                         vBoxC.setStyle("-fx-background-image: url('/images/cerceve2.png');" + "-fx-max-width: 235;"+"-fx-translate-x: 0;"+"-fx-translate-y: 0;");
                                      }
                                     sayac1++;
                                  }
                                  sayac1=0;
                                  sayac2++;
                              }
                          });
                     }
                 }
                Thread.sleep(5000);
             } catch (Exception ex) {
            }
         }
        }
    }

    private class NobetciScrean extends Task<Void> {

        FlagClass flagClass;

        public NobetciScrean(FlagClass flagClass) {
            this.flagClass = flagClass;
        }

        @Override
       protected Void call() throws Exception {
           makeChanges();
           return null;
       }
       private void makeChanges() {
           while (true) {
               try {
                   synchronized (flagClass)
                   {
                       if (flag==false)
                       {
                           Platform.runLater(() -> {
                               Calendar cal1 = Calendar.getInstance();
                               Calendar cal2 = Calendar.getInstance();
                               cal2.set(Calendar.SECOND, 15);
                               // labelClock.setText("Time"+hour+":"+minute+":"+second+"  Tarih  "+year+"/"+month+"/"+day);
                                if (cal1.equals(cal2))
                                {
                                    promoControl();
                                    promoView();
                                    flag=true;
                                    Task<Void> task = new PromoScrean(flagClass);
                                    Thread thread = new Thread(task);
                                    thread.setDaemon(true);
                                    thread.start();
                                }
                           });
                       }
                   }

                   Thread.sleep(1000);
               } catch (Exception ex)
               {

               }
           }
       }
    }

    private class PromoScrean extends Task<Void> {
        FlagClass flagClass;

        public PromoScrean(FlagClass flagClass) {
            this.flagClass = flagClass;
        }

        @Override
       protected Void call() throws Exception {
           makeChanges();
           return null;
       }
       private void makeChanges() {
           while (true) {
               try {
                   synchronized (MainController.class)
                   {
                       if (flag==true)
                       {
                       Platform.runLater(() -> {
                       Calendar cal1 = Calendar.getInstance();
                       Calendar cal2 = Calendar.getInstance();
                       cal2.set(Calendar.SECOND, 46);

                       if (cal1.equals(cal2)) {
                           mp.stop();
                           promoMediaView.setVisible(false);
                           flag=false;
                           sqlEkran();
                         //  Thread.interrupted();
                       }
                   });}}

                   Thread.sleep(1000);
               } catch (Exception ex) {
               }
           }
       }
    }

    private class FlagClass{
        boolean flag=false;
    }

    void promoView() {
        String path = new File("Promos/NobetciReklamlar.mp4").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(media);
        mp.setCycleCount(MediaPlayer.INDEFINITE);
        promoMediaView.setMediaPlayer(mp);
        promoMediaView.setVisible(true);
        mp.play();
        final DoubleProperty width = promoMediaView.fitWidthProperty();
        final DoubleProperty height = promoMediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(promoMediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(promoMediaView.sceneProperty(), "height"));
    }

    private void sqlEkran() {
        try {

            if (_connection.Connect()==true) {
                ilceLabel.setText(_ayarService.getAyarIlceAd());
                DateUtility dateUtility = new DateUtility();
                tarihLabel.setText(dateUtility.getDateAll());
                int sayac = 0;
                nobetciSayisi=_nobetciService.getSelectedSize();

                if (nobetciSayisi==0){

                }

                listVBox.clear();
                nobetciList.clear();
              //  listVBox=new ArrayList<>();
             //   nobetciList=new ArrayList<Nobetci>();

                int gridPaneWith = 245 * _nobetciService.getSelectedSize();
                gridPane.setMinHeight(107.0);
                gridPane.setMinWidth(gridPaneWith);
                gridPane.setHgap(11);

                for (int sutun = 0; sutun < _nobetciService.getSelectedSize(); sutun++) {
                    //   int deg = _nobetciService.getSelectedSize();


                    Nobetci nobetci = _nobetciService.getNobetciAyarIlceTarihAll().get(sayac);
                    nobetciList.add(nobetci);
                    Label nobetciAdLabel = new Label(nobetci.getNobetciAd());
                    nobetciAdLabel.setId("nobetciAdLabel" + sayac);
                    nobetciAdLabel.setMinSize(235, 50);
                    nobetciAdLabel.setMaxSize(235, 50);
                    nobetciAdLabel.setAlignment(Pos.BOTTOM_CENTER);
                    nobetciAdLabel.setTextAlignment(TextAlignment.CENTER);
                    nobetciAdLabel.setWrapText(true);
                    nobetciAdLabel.setTextFill(Color.WHITE);
                    nobetciAdLabel.setFont(new Font("Arial", 18));


                    Label nobetciTelLabel = new Label(nobetci.getNobetciTel());
                    nobetciTelLabel.setId("nobetciTelLabel" + sayac);
                    nobetciTelLabel.setMinSize(235, 50);
                    nobetciTelLabel.setMaxSize(235, 50);
                    nobetciTelLabel.setAlignment(Pos.TOP_CENTER);
                    nobetciTelLabel.setTextAlignment(TextAlignment.CENTER);
                    nobetciTelLabel.setTextFill(Color.WHITE);
                    nobetciTelLabel.setWrapText(true);
                    nobetciTelLabel.setFont(new Font("Arial", 18));
                    vBox= new VBox();
                    vBox.setId("vbox"+sayac1);
                    listVBox.add(vBox);
                    if (sayac == 0) {
                        vBox.setStyle("-fx-background-image: url('/images/cerceve1.7.png');" + "-fx-max-width: 235;"+"-fx-translate-x:0;");
                    } else {
                        vBox.setStyle("-fx-background-image: url('/images/cerceve2.png');" + "-fx-max-width: 235;"+"-fx-translate-x:0;");
                    }

                    vBox.getChildren().addAll(nobetciAdLabel, nobetciTelLabel);
                    gridPane.setTranslateX(20);
                    gridPane.setPadding(new Insets(40, 0, 0, 0));

                    gridPane.add(vBox, sutun, 0);
                    sayac++;
                }
                sayac = 0;


            }
            else {
                // Burası güncellenecek ;
                _connection.Connect();
            }
            // _connection.Kapat();
        } catch (Exception e) {
        }

    }

    private void sqlEkran2() {
        try {

            if (_connection.Connect()==true) {
                ilceLabel.setText(_ayarService.getAyarIlceAd());
                DateUtility dateUtility = new DateUtility();
                tarihLabel.setText(dateUtility.getDateAll());
                int sayac = 0;
                nobetciSayisi=_nobetciService.getSelectedSize();
                nobetciList = new ArrayList<Nobetci>();
                listVBox=new ArrayList<>();

                int gridPaneWith = 245 * _nobetciService.getSelectedSize();
                gridPane.setMinHeight(107.0);
                gridPane.setMinWidth(gridPaneWith);
                gridPane.setHgap(11);

                for (int sutun = 0; sutun < _nobetciService.getSelectedSize(); sutun++) {
                    //   int deg = _nobetciService.getSelectedSize();


                    Nobetci nobetci = _nobetciService.getNobetciAyarIlceTarihAll().get(sayac);
                    nobetciList.add(nobetci);
                    Label nobetciAdLabel = new Label(nobetci.getNobetciAd());
                    nobetciAdLabel.setId("nobetciAdLabel" + sayac);
                    nobetciAdLabel.setMinSize(235, 50);
                    nobetciAdLabel.setMaxSize(235, 50);
                    nobetciAdLabel.setAlignment(Pos.BOTTOM_CENTER);
                    nobetciAdLabel.setTextAlignment(TextAlignment.CENTER);
                    nobetciAdLabel.setWrapText(true);
                    nobetciAdLabel.setTextFill(Color.WHITE);
                    nobetciAdLabel.setFont(new Font("Arial", 18));


                    Label nobetciTelLabel = new Label(nobetci.getNobetciTel());
                    nobetciTelLabel.setId("nobetciTelLabel" + sayac);
                    nobetciTelLabel.setMinSize(235, 50);
                    nobetciTelLabel.setMaxSize(235, 50);
                    nobetciTelLabel.setAlignment(Pos.TOP_CENTER);
                    nobetciTelLabel.setTextAlignment(TextAlignment.CENTER);
                    nobetciTelLabel.setTextFill(Color.WHITE);
                    nobetciTelLabel.setWrapText(true);
                    nobetciTelLabel.setFont(new Font("Arial", 18));
                    vBox= new VBox();
                    vBox.setId("vbox"+sayac1);
                    listVBox.add(vBox);
                    if (sayac == 0) {
                        vBox.setStyle("-fx-background-image: url('/images/cerceve1.7.png');" + "-fx-max-width: 235;"+"-fx-translate-x:0;");
                    } else {
                        vBox.setStyle("-fx-background-image: url('/images/cerceve2.png');" + "-fx-max-width: 235;"+"-fx-translate-x:0;");
                    }

                    vBox.getChildren().addAll(nobetciAdLabel, nobetciTelLabel);
                    gridPane.setTranslateX(20);
                    gridPane.setPadding(new Insets(40, 0, 0, 0));

                    gridPane.add(vBox, sutun, 0);
                    sayac++;
                }
                sayac = 0;

                FlagClass flagClass=new FlagClass();
                Task<Void> task = new SqlUpdater(flagClass);
                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
                Task<Void> task2 = new NobetciScrean(flagClass);
                Thread thread2 = new Thread(task2);
                thread2.setDaemon(true);
                thread2.start();

            }
            else {
                // Burası güncellenecek ;
                _connection.Connect();
            }
            // _connection.Kapat();
        } catch (Exception e) {
        }

    }

    public void QRUtility() {
        try {
            String saveDir = System.getProperty("user.dir") + File.separator + "QRCode";
            File dir = new File("QRCode");
            if (!dir.exists()) {
                dir.mkdir(); // Klasör oluşturuluyor
                saveDir = System.getProperty("user.dir") + File.separator + "QRCode";
            }
          for (int i=0;i<_nobetciService.getSelectedSize();i++){

              BitMatrix bitMatrix;
              Writer writer=new QRCodeWriter();
              bitMatrix=writer.encode(_nobetciService.getNobetciAyarIlceAll().get(i).nobetciHaritaAdres, BarcodeFormat.QR_CODE,200,200);
              MatrixToImageWriter.writeToStream(bitMatrix,"png",new FileOutputStream(new File(saveDir+"//"+i+".png")));
             // System.out.println("oluşturuluyor");
          }
            files=dir.listFiles();//files array stores the list of files

            qrImageList=new ArrayList<>();
           for (int j=0;j<files.length;j++){
               Image image=new Image(new FileInputStream(files[j].getAbsolutePath()));
               qrImageList.add(image);
           }

        }catch (Exception ex){}
    }

    @FXML
    private void fullScrean(ActionEvent event) {
        Main main = new Main();
        main.fullScrean();
    }

    @FXML
    private void yenile(ActionEvent event) {
        sqlEkran2();
    }

    @FXML
    private void reklamAc(ActionEvent event) {
        try {
            String path = new File("Promos/NobetciReklamlar.mp4").getAbsolutePath();

            File file = new File(path);

            if (file.exists()) {
                String mediaPath = file.toURI().toString();
                media = new Media(mediaPath);
                mp = new MediaPlayer(media);
                mp.setCycleCount(MediaPlayer.INDEFINITE);
                promoMediaView.setMediaPlayer(mp);
                promoMediaView.setVisible(true);
                mp.play();
                final DoubleProperty width = promoMediaView.fitWidthProperty();
                final DoubleProperty height = promoMediaView.fitHeightProperty();
                width.bind(Bindings.selectDouble(promoMediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(promoMediaView.sceneProperty(), "height"));
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText("Dikkat, Açılacak bir reklam yok.");
                alert.setContentText("Reklam indirilsin mi?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    reklamIndir();
                } else {
                    // ... user chose CANCEL or closed the dialog
                    alert.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void promoOpen() {
        try {
            String path = new File("Promos/NobetciReklamlar.mp4").getAbsolutePath();
            File file = new File(path);
            if (file.exists()) {
                String mediaPath = file.toURI().toString();
                media = new Media(mediaPath);
                mp = new MediaPlayer(media);
                mp.setCycleCount(MediaPlayer.INDEFINITE);
                promoMediaView.setMediaPlayer(mp);
                promoMediaView.setVisible(true);
                mp.play();
                final DoubleProperty width = promoMediaView.fitWidthProperty();
                final DoubleProperty height = promoMediaView.fitHeightProperty();
                width.bind(Bindings.selectDouble(promoMediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(promoMediaView.sceneProperty(), "height"));
            } else {
                reklamIndir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void promoControl() {
        try {
            String saveDir = System.getProperty("user.dir") + File.separator + "Promos";
            File dir = new File("Promos");
            if (!dir.exists()) {
              promoDownload();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void reklamIndir() {
        try {
            String fileURL = "http://78.191.231.150:3596/Video/Uploads/Reklamlar/NobetciReklamlar.mp4";
            String saveDir = System.getProperty("user.dir") + File.separator + "Promos";
            File dir = new File("Promos");
            if (!dir.exists()) {

                dir.mkdir(); // Klasör oluşturuluyor
                saveDir = System.getProperty("user.dir") + File.separator + "Promos";
            }
            HttpDownloadUtility.downloadFile(fileURL, saveDir);
            promoOpen();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void reklamKapat(ActionEvent event) {
        try {
            mp.stop();
            promoMediaView.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void promoDownloadAction(ActionEvent event) {
        try {
            String fileURL = "http://78.191.231.150:3596/Video/Uploads/Reklamlar/NobetciReklamlar.mp4";
            String saveDir = System.getProperty("user.dir") + File.separator + "Promos";
            File dir = new File("Promos");
            if (!dir.exists()) {
                dir.mkdir(); // Klasör oluşturuluyor
                saveDir = System.getProperty("user.dir") + File.separator + "Promos";
            }
            HttpDownloadUtility.downloadFile(fileURL, saveDir);
            promoOpen();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void promoDownload(){
        try {
            String fileURL = "http://78.191.231.150:3596/Video/Uploads/Reklamlar/NobetciReklamlar.mp4";
            String saveDir = System.getProperty("user.dir") + File.separator + "Promos";
            File dir = new File("Promos");
            if (!dir.exists()) {
                dir.mkdir(); // Klasör oluşturuluyor
                saveDir = System.getProperty("user.dir") + File.separator + "Promos";
            }
            HttpDownloadUtility.downloadFile(fileURL, saveDir);
            promoOpen();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void denemeEvent(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.showAndWait();
    }
}

