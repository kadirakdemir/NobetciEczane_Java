package Utilities;

import java.io.File;

public class FileUtility {
    private void createFolder() {
        File dir = new File("Klasor");
        dir.mkdir(); // Klasör oluşturuluyor
    }
}
