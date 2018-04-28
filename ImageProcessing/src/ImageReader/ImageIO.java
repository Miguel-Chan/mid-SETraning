package ImageReader;

import java.awt.*;

public interface ImageIO {

    public Image myRead(String filePath);

    public Image myWrite(Image image, String filePath);
}
