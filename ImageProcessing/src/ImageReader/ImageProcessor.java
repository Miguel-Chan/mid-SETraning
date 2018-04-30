package ImageReader;

import java.awt.*;

public interface ImageProcessor {
    Image showChanelR(Image sourceImage);

    Image showChanelG(Image sourceImage);

    Image showChanelB(Image sourceImage);

    Image showGray(Image sourceImage);
}
