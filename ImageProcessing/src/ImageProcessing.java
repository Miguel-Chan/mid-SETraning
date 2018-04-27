import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageProcessing {
    private String filePath;

    private File file;

    ImageProcessing(String path) throws IOException {
        filePath = path;
        file = new File(path);
        FileInputStream fileInput = new FileInputStream(file);
    }

    public static void main(String[] argv) {
        System.out.println("aa");
    }
}