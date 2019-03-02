package Utilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;

public class QRUtility1 {
    public QRUtility1() {
        try {
            System.out.println("de");
            BitMatrix bitMatrix;
            Writer writer=new QRCodeWriter();
            bitMatrix=writer.encode("", BarcodeFormat.QR_CODE,200,200);
            MatrixToImageWriter.writeToStream(bitMatrix,"png",new FileOutputStream(new File("c://Deneme//deneme.png")));
            System.out.println("oluşturuluyor");

        }catch (Exception ex){}
    }
}
