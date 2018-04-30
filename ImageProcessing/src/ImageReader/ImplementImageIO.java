package ImageReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImplementImageIO implements ImageIO {
    private int[] sourceBuffer;

    private byte[] fileHeader = new byte[FILE_HEADER_SIZE];

    private static final int SKIP_LENGTH = 18;
    private static final int DOUBLE_WORD_LENGTH = 4;
    private static final int FILE_HEADER_SIZE = 54;
    private static final int WORD_LENGTH = 2;
    private static final int PIXEL_SIZE = 3;

    @Override
    public Image myRead(String path) {

        try {
            FileInputStream file = new FileInputStream(path);

            file.read(fileHeader);

            byte[] widthBytes = new byte[DOUBLE_WORD_LENGTH];
            byte[] heightBytes = new byte[DOUBLE_WORD_LENGTH];
            byte[] sizeBytes = new byte[DOUBLE_WORD_LENGTH];
            for (int i = 0; i < DOUBLE_WORD_LENGTH; i++) {
                widthBytes[i] = fileHeader[18 + i];
                heightBytes[i] = fileHeader[22 + i];
                sizeBytes[i] = fileHeader[34 + i];
            }

            int width = DoubleWordToInt(widthBytes);
            int height = DoubleWordToInt(heightBytes);
            int size = DoubleWordToInt(sizeBytes);

            sourceBuffer = new int[width * height];
            byte[] imageBuffer = new byte[size];
            int skipNum = (size / height - width * 3) % 4;

            file.read(imageBuffer);
            int currentIndex = 0;

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    byte[] currentPixel = new byte[PIXEL_SIZE];
                    for (int k = 0; k < PIXEL_SIZE; k++) {
                        currentPixel[k] = imageBuffer[currentIndex + k];
                    }

                    sourceBuffer[(height - i - 1) * width + j] = PixelToInt(currentPixel);
                    currentIndex += 3;
                }

                currentIndex += skipNum;
            }

            return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(
                    width, height, sourceBuffer, 0, width));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage ImageToBufferedImage(Image image) {
        BufferedImage result = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = result.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        return result;
    }

    @Override
    public Image myWrite(Image image, String filePath) {
        try {
            File outputFile = new File(filePath);
            BufferedImage buffer = ImageToBufferedImage(image);

            javax.imageio.ImageIO.write(buffer, "bmp", outputFile);

            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static int DoubleWordToInt(byte[] doubleWord) {
        int byte0 = doubleWord[3] & 0xff;
        int byte1 = doubleWord[2] & 0xff;
        int byte2 = doubleWord[1] & 0xff;
        int byte3 = doubleWord[0] & 0xff;

        int result = byte0 << 24 | byte1 << 16 | byte2 << 8 | byte3;
        return result;
    }

    public static int PixelToInt(byte[] pixel) {
        int byte0 = pixel[2] & 0xff;
        int byte1 = pixel[1] & 0xff;
        int byte2 = pixel[0] & 0xff;

        int result = (255 & 0xff) << 24 | byte0 << 16 | byte1 << 8 | byte2;
        return result;
    }

    public static int WordToInt(byte[] doubleWord) {
        int byte0 = doubleWord[1] & 0xff;
        int byte1 = doubleWord[0] & 0xff;

        int result = byte0 << 8 | byte1;
        return result;
    }
}
