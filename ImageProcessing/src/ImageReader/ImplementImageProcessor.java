package ImageReader;

import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

public class ImplementImageProcessor implements ImageProcessor {

    private static final int RED_RGB_VALUE = 0x00ff0000;
    private static final int GREEN_RGB_VALUE = 0x0000ff00;
    private static final int BLUE_RGB_VALUE = 0x000000ff;
    private static final int OPACITY_VALUE = 0xff000000;

    private static final double RED_WEIGHT  = 0.299;
    private static final double GREEN_WEIGHT  = 0.587;
    private static final double BLUE_WEIGHT  = 0.114;

    @Override
    public Image showChanelR(Image sourceImage) {
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(
                sourceImage.getSource(),
                new channelFilter(RED_RGB_VALUE)
        ));
    }

    @Override
    public Image showChanelG(Image sourceImage) {
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(
                sourceImage.getSource(),
                new channelFilter(GREEN_RGB_VALUE)
        ));
    }

    @Override
    public Image showChanelB(Image sourceImage) {
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(
                sourceImage.getSource(),
                new channelFilter(BLUE_RGB_VALUE)
        ));
    }

    @Override
    public Image showGray(Image sourceImage) {
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(
                sourceImage.getSource(),
                new grayFilter()
        ));
    }

    private class channelFilter extends RGBImageFilter {

        int filterValue;

        public channelFilter(int val) {
            filterValue = val;
            canFilterIndexColorModel = true;
        }

        @Override
        public int filterRGB(int x, int y, int rgb) {
            return rgb & filterValue | OPACITY_VALUE;
        }
    }

    private class grayFilter extends RGBImageFilter {
        public grayFilter() { canFilterIndexColorModel = true; }

        @Override
        public int filterRGB(int x, int y, int rgb) {
            int red = (int)(((rgb & RED_RGB_VALUE) >> 16) * RED_WEIGHT);
            int green = (int)(((rgb & GREEN_RGB_VALUE) >> 8) * GREEN_WEIGHT);
            int blue = (int)((rgb & BLUE_RGB_VALUE) * BLUE_WEIGHT);
            int gray = red + green + blue;

            return 0xff000000 | gray << 16 | gray << 8 | gray;
        }
    }
}

